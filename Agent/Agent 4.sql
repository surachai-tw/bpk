-- DROP TABLE vwbpk_tmp_2015_agent_model2_with_nation;
-- CREATE TABLE vwbpk_tmp_2015_agent_model2_with_nation AS  
INSERT INTO vwbpk_tmp_2015_agent_model2_with_nation ("Discharge Date", "HN", "Visit Type", "Nation", "Group", "Agent Code", "Agent Name", "pverify_deptid", "pordered_by_department", "รวมค่าบริการ", "ส่วนลด", "ยอดสุทธิ")
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
SUM(ROUND(CAST(acccredit.linesale AS NUMERIC), 2))::FLOAT "รวมค่าบริการ", 
SUM(ROUND(CAST(acccredit.wlinediscount AS NUMERIC), 2))::FLOAT "ส่วนลด", 
SUM(ROUND(CAST(acccredit.wlinepaid AS NUMERIC), 2))::FLOAT "ยอดสุทธิ"
FROM 
visit 
INNER JOIN patient ON visit.patient_id = patient.patient_id AND patient.active='1' 
INNER JOIN fix_nationality ON patient.fix_nationality_id=fix_nationality.fix_nationality_id
INNER JOIN bpk_account_credit_detail acccredit ON visit.visit_id = acccredit.visit_id 
            AND acccredit.financial_discharge_date BETWEEN '2015-11-01' AND '2015-11-30'
WHERE visit.active='1' 
AND visit.visit_id IN 
(
    SELECT DISTINCT visit.visit_id FROM visit 
    INNER JOIN visit_payment ON visit.visit_id = visit_payment.visit_id 
    INNER JOIN plan ON visit_payment.plan_id = plan.plan_id AND plan.base_plan_group_id IN ('AT', 'AY')
    WHERE financial_discharge_date BETWEEN '2015-11-01' AND '2015-11-30'
) 
GROUP BY visit.visit_id, acccredit.hn, acccredit.financial_discharge_date, acccredit.fix_visit_type_id, fix_nationality.description, 
acccredit.pverify_deptid, acccredit.pordered_by_department
ORDER BY "Discharge Date", "Visit Type", "Group", "Nation" 