SELECT * FROM bpk_agent_with_nation('2015-09-01', '2015-09-30')

EXECUTE bpk_agent_with_nation('2015-09-01', '2015-09-30')

-- DROP FUNCTION bpk_agent_with_nation(fromdate text, todate text); 

-- For List Detail of Agent with nation
CREATE OR REPLACE FUNCTION bpk_agent_with_nation(fromdate text, todate text) 
RETURNS TABLE ( 
    "Discharge Date" VARCHAR(255), 
    "HN" VARCHAR(255), 
    "Visit Type" VARCHAR(255), 
    "Nation" VARCHAR(255), 
    "Group" VARCHAR(255), 
    "Agent Code" VARCHAR(255), 
    "Agent Name" VARCHAR(255), 
    "�����Һ�ԡ��" FLOAT, 
    "��ǹŴ" FLOAT, 
    "�ʹ�ط��" FLOAT 
)  
AS '

    SELECT DISTINCT 
    acccredit.financial_discharge_date "Discharge Date", 
    acccredit.hn "HN", 
    CASE WHEN acccredit.fix_visit_type_id=''0'' THEN ''OPD'' ELSE ''IPD'' END "Visit Type", 
    fix_nationality.description "Nation", 
    (SELECT base_plan_group.description FROM base_plan_group 
        INNER JOIN plan ON base_plan_group.base_plan_group_id=plan.base_plan_group_id 
        INNER JOIN visit_payment ON visit_payment.plan_id=plan.plan_id AND plan.base_plan_group_id IN (''AT'', ''AY'') 
        WHERE visit_payment.visit_id=visit.visit_id LIMIT 1)  "Group", 
    (SELECT plan.plan_code FROM visit_payment INNER JOIN plan ON visit_payment.plan_id=plan.plan_id AND plan.base_plan_group_id IN (''AT'', ''AY'') WHERE visit_payment.visit_id=visit.visit_id LIMIT 1)  "Agent Code", 
    (SELECT plan.description FROM visit_payment INNER JOIN plan ON visit_payment.plan_id=plan.plan_id AND plan.base_plan_group_id IN (''AT'', ''AY'') WHERE visit_payment.visit_id=visit.visit_id LIMIT 1)  "Agent Name", 
    -- visit.visit_id, 
    SUM(ROUND(CAST(acccredit.linesale AS NUMERIC), 2))::FLOAT "�����Һ�ԡ��", 
    SUM(ROUND(CAST(acccredit.wlinediscount AS NUMERIC), 2))::FLOAT "��ǹŴ", 
    SUM(ROUND(CAST(acccredit.wlinepaid AS NUMERIC), 2))::FLOAT "�ʹ�ط��"
    FROM 
    visit 
    INNER JOIN patient ON visit.patient_id = patient.patient_id AND patient.active=''1'' 
    INNER JOIN fix_nationality ON patient.fix_nationality_id=fix_nationality.fix_nationality_id
    INNER JOIN bpk_account_credit_detail acccredit ON visit.visit_id = acccredit.visit_id 
                AND acccredit.financial_discharge_date BETWEEN $1 AND $2  
    WHERE visit.active=''1'' 
    AND visit.visit_id IN 
    (
        SELECT DISTINCT visit.visit_id FROM visit 
        INNER JOIN visit_payment ON visit.visit_id = visit_payment.visit_id 
        INNER JOIN plan ON visit_payment.plan_id = plan.plan_id AND plan.base_plan_group_id IN (''AT'', ''AY'')
        WHERE financial_discharge_date BETWEEN $1 AND $2 
    ) 
    GROUP BY visit.visit_id, acccredit.hn, acccredit.financial_discharge_date, acccredit.fix_visit_type_id, fix_nationality.description 
    ORDER BY "Discharge Date", "Visit Type", "Group", "Nation";

' LANGUAGE SQL;


SELECT
DISTINCT 
main."Discharge Date", 
CASE WHEN "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' THEN Count(*) ELSE 0 END "OPD Visit", 
CASE WHEN "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' THEN SUM("�����Һ�ԡ��") ELSE 0 END "����� OPD", 
CASE WHEN "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' THEN SUM("��ǹŴ") ELSE 0 END "��ǹŴ OPD", 
CASE WHEN "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' THEN SUM("�ʹ�ط��") ELSE 0 END "�ط�� OPD", 
CASE WHEN "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' THEN Count(*) ELSE 0 END "IPD Visit", 
CASE WHEN "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' THEN SUM("�����Һ�ԡ��") ELSE 0 END "����� IPD", 
CASE WHEN "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' THEN SUM("��ǹŴ") ELSE 0 END "��ǹŴ IPD", 
CASE WHEN "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' THEN SUM("�ʹ�ط��") ELSE 0 END "�ط�� IPD" 
FROM 
bpk_agent_with_nation('2015-09-01', '2015-09-30') main
GROUP BY main."Discharge Date", main."Visit Type", main."Nation", main."Group" 
ORDER BY "Discharge Date";

-- DROP FUNCTION bpk_agent_with_nation_total(text, text);

-- For Sum Agent with nation
CREATE OR REPLACE FUNCTION bpk_agent_without_nation_th_total(fromdate text, todate text, agtgroup text) 
RETURNS TABLE ( 
    "Discharge Date" VARCHAR(255), 
    "OPD Visit" BIGINT, 
    "����� OPD" FLOAT, 
    "��ǹŴ OPD" FLOAT, 
    "�ط�� OPD" FLOAT, 
    "IPD Visit" BIGINT, 
    "����� IPD" FLOAT, 
    "��ǹŴ IPD" FLOAT, 
    "�ط�� IPD" FLOAT 
)  
LANGUAGE plpgsql AS '
DECLARE 
    p_fromdate ALIAS FOR $1; 
    p_todate ALIAS FOR $2;
    p_agtgroup ALIAS FOR $3;
    v_create TEXT;
    v_select TEXT;
BEGIN  

    DROP TABLE IF EXISTS bpk_temp_agent_with_nation;

    v_create := ''CREATE TEMP TABLE bpk_temp_agent_with_nation AS SELECT * FROM bpk_agent_with_nation( ''''''||p_fromdate||'''''', ''''''||p_todate||'''''')'';
    RAISE NOTICE ''%'', v_create;
    EXECUTE v_create;

    v_select := ''
    SELECT
    DISTINCT 
    main."Discharge Date", 
    COALESCE((SELECT Count(*) FROM bpk_temp_agent_with_nation WHERE "Visit Type"=''''OPD'''' AND "Nation"<>''''��'''' AND "Group"=''''''||p_agtgroup||'''''' AND "Discharge Date"=main."Discharge Date"), 0::BIGINT) "OPD Visit", 
    COALESCE((SELECT SUM("�����Һ�ԡ��") FROM bpk_temp_agent_with_nation WHERE "Visit Type"=''''OPD'''' AND "Nation"<>''''��'''' AND "Group"=''''''||p_agtgroup||'''''' AND "Discharge Date"=main."Discharge Date"), 0.0) "����� OPD", 
    COALESCE((SELECT SUM("��ǹŴ") FROM bpk_temp_agent_with_nation WHERE "Visit Type"=''''OPD'''' AND "Nation"<>''''��'''' AND "Group"=''''''||p_agtgroup||'''''' AND "Discharge Date"=main."Discharge Date"), 0.0) "��ǹŴ OPD", 
    COALESCE((SELECT SUM("�ʹ�ط��") FROM bpk_temp_agent_with_nation WHERE "Visit Type"=''''OPD'''' AND "Nation"<>''''��'''' AND "Group"=''''''||p_agtgroup||'''''' AND "Discharge Date"=main."Discharge Date"), 0.0) "�ط�� OPD", 
    COALESCE((SELECT Count(*) FROM bpk_temp_agent_with_nation WHERE "Visit Type"=''''IPD'''' AND "Nation"<>''''��'''' AND "Group"=''''''||p_agtgroup||'''''' AND "Discharge Date"=main."Discharge Date"), 0::BIGINT) "IPD Visit", 
    COALESCE((SELECT SUM("�����Һ�ԡ��") FROM bpk_temp_agent_with_nation WHERE "Visit Type"=''''IPD'''' AND "Nation"<>''''��'''' AND "Group"=''''''||p_agtgroup||'''''' AND "Discharge Date"=main."Discharge Date"), 0.0) "����� IPD", 
    COALESCE((SELECT SUM("��ǹŴ") FROM bpk_temp_agent_with_nation WHERE "Visit Type"=''''IPD'''' AND "Nation"<>''''��'''' AND "Group"=''''''||p_agtgroup||'''''' AND "Discharge Date"=main."Discharge Date"), 0.0) "��ǹŴ IPD", 
    COALESCE((SELECT SUM("�ʹ�ط��") FROM bpk_temp_agent_with_nation WHERE "Visit Type"=''''IPD'''' AND "Nation"<>''''��'''' AND "Group"=''''''||p_agtgroup||'''''' AND "Discharge Date"=main."Discharge Date"), 0.0) "�ط�� IPD" 
    FROM 
    (SELECT CAST(generate_series AS VARCHAR(10)) "Discharge Date" FROM generate_series(CAST(''''''||p_fromdate||'''''' AS TIMESTAMP), CAST(''''''||p_todate||'''''' AS TIMESTAMP), ''''1 day'''')) main
    ORDER BY "Discharge Date"'';
    RAISE NOTICE ''%'', v_select;
    RETURN QUERY EXECUTE v_select;

    DROP TABLE bpk_temp_agent_with_nation;

    RETURN ;
END';

-- SELECT * FROM bpk_agent_with_nation_total('2015-09-01', '2015-09-30')
SELECT * FROM bpk_agent_without_nation_th_total('2015-09-01', '2015-09-30', ';.')
-- DROP TABLE bpk_temp_agent_with_nation

SELECT * FROM bpk_account_credit_detail LIMIT 10 

-- ��������ͧ �ٹ����ᾷ���������������
-- CREATE OR REPLACE VIEW vwbpk_tmp_agent_model2_with_nation AS  
DROP VIEW vwbpk_tmp_agent_model2_with_nation;
CREATE TABLE vwbpk_tmp_agent_model2_with_nation AS  
SELECT DISTINCT 
acccredit.financial_discharge_date "Discharge Date", 
acccredit.hn "HN", 
CASE WHEN acccredit.fix_visit_type_id='0' THEN 'OPD' ELSE 'IPD' END "Visit Type", 
fix_nationality.description "Nation", 
(SELECT base_plan_group.description FROM base_plan_group 
    INNER JOIN plan ON base_plan_group.base_plan_group_id=plan.base_plan_group_id 
    INNER JOIN visit_payment ON visit_payment.plan_id=plan.plan_id AND plan.base_plan_group_id IN ('AT', 'AY') 
    WHERE visit_payment.visit_id=visit.visit_id LIMIT 1)  "Group", 
(SELECT plan.plan_code FROM visit_payment INNER JOIN plan ON visit_payment.plan_id=plan.plan_id AND plan.base_plan_group_id IN ('AT', 'AY') WHERE visit_payment.visit_id=visit.visit_id LIMIT 1)  "Agent Code", 
(SELECT plan.description FROM visit_payment INNER JOIN plan ON visit_payment.plan_id=plan.plan_id AND plan.base_plan_group_id IN ('AT', 'AY') WHERE visit_payment.visit_id=visit.visit_id LIMIT 1)  "Agent Name", 
acccredit.pverify_deptid, 
acccredit.pordered_by_department, 
-- visit.visit_id, 
SUM(ROUND(CAST(acccredit.linesale AS NUMERIC), 2))::FLOAT "�����Һ�ԡ��", 
SUM(ROUND(CAST(acccredit.wlinediscount AS NUMERIC), 2))::FLOAT "��ǹŴ", 
SUM(ROUND(CAST(acccredit.wlinepaid AS NUMERIC), 2))::FLOAT "�ʹ�ط��"
FROM 
visit 
INNER JOIN patient ON visit.patient_id = patient.patient_id AND patient.active='1' 
INNER JOIN fix_nationality ON patient.fix_nationality_id=fix_nationality.fix_nationality_id
INNER JOIN bpk_account_credit_detail acccredit ON visit.visit_id = acccredit.visit_id 
            AND acccredit.financial_discharge_date BETWEEN '2015-09-01' AND '2015-09-31'
WHERE visit.active='1' 
AND visit.visit_id IN 
(
    SELECT DISTINCT visit.visit_id FROM visit 
    INNER JOIN visit_payment ON visit.visit_id = visit_payment.visit_id 
    INNER JOIN plan ON visit_payment.plan_id = plan.plan_id AND plan.base_plan_group_id IN ('AT', 'AY')
    WHERE financial_discharge_date BETWEEN '2015-09-01' AND '2015-09-31'
) 
GROUP BY visit.visit_id, acccredit.hn, acccredit.financial_discharge_date, acccredit.fix_visit_type_id, fix_nationality.description, 
acccredit.pverify_deptid, acccredit.pordered_by_department
ORDER BY "Discharge Date", "Visit Type", "Group", "Nation" 


-- ��ػ ;.+�ٹ����ᾷ�� ����ѹ 
SELECT 
main."Discharge Date", 
main.pordered_by_department, 
(SELECT Count(DISTINCT "HN") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' AND "Discharge Date"=main."Discharge Date" AND pordered_by_department=main.pordered_by_department) "OPD Visit", 
(SELECT SUM("�����Һ�ԡ��") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' AND "Discharge Date"=main."Discharge Date" AND pordered_by_department=main.pordered_by_department) "����� OPD", 
(SELECT SUM("��ǹŴ") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' AND "Discharge Date"=main."Discharge Date" AND pordered_by_department=main.pordered_by_department) "��ǹŴ OPD", 
(SELECT SUM("�ʹ�ط��") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' AND "Discharge Date"=main."Discharge Date" AND pordered_by_department=main.pordered_by_department) "�ط�� OPD", 
(SELECT Count(DISTINCT "HN") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' AND "Discharge Date"=main."Discharge Date" AND pordered_by_department=main.pordered_by_department) "IPD Visit", 
(SELECT SUM("�����Һ�ԡ��") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' AND "Discharge Date"=main."Discharge Date" AND pordered_by_department=main.pordered_by_department) "����� IPD", 
(SELECT SUM("��ǹŴ") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' AND "Discharge Date"=main."Discharge Date" AND pordered_by_department=main.pordered_by_department) "��ǹŴ IPD", 
(SELECT SUM("�ʹ�ط��") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' AND "Discharge Date"=main."Discharge Date" AND pordered_by_department=main.pordered_by_department) "�ط�� IPD" 
FROM 
(SELECT DISTINCT "Discharge Date", pordered_by_department FROM vwbpk_tmp_agent_model2_with_nation) main
ORDER BY "Discharge Date", pordered_by_department COLLATE "th_TH"

-- ��ػ ;.+�ٹ����ᾷ��
SELECT 
main.pordered_by_department, 
(SELECT Count(DISTINCT "HN") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department) "OPD Visit", 
(SELECT SUM("�����Һ�ԡ��") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department) "����� OPD", 
(SELECT SUM("��ǹŴ") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department) "��ǹŴ OPD", 
(SELECT SUM("�ʹ�ط��") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department) "�ط�� OPD", 
(SELECT Count(DISTINCT "HN") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department) "IPD Visit", 
(SELECT SUM("�����Һ�ԡ��") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department) "����� IPD", 
(SELECT SUM("��ǹŴ") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department) "��ǹŴ IPD", 
(SELECT SUM("�ʹ�ط��") FROM vwbpk_tmp_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department) "�ط�� IPD" 
FROM 
(SELECT DISTINCT pordered_by_department FROM vwbpk_tmp_agent_model2_with_nation) main
ORDER BY pordered_by_department COLLATE "th_TH"
