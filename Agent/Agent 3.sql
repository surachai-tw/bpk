SELECT "Discharge Date", "HN", "Visit Type", "Nation", "Group", "Agent Code", "Agent Name", "pverify_deptid", "pordered_by_department", "�����Һ�ԡ��", "��ǹŴ", "�ʹ�ط��" FROM vwbpk_tmp_2015_agent_model2_with_nation LIMIT 1

SELECT * FROM vwbpk_tmp_2015_agent_model2_with_nation


SELECT 
main.pordered_by_department, 
COALESCE((SELECT Count(DISTINCT "HN") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department), 0) "OPD Visit", 
COALESCE((SELECT SUM("�����Һ�ԡ��") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department), 0) "����� OPD", 
COALESCE((SELECT SUM("��ǹŴ") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department), 0) "��ǹŴ OPD", 
COALESCE((SELECT SUM("�ʹ�ط��") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department), 0) "�ط�� OPD", 
COALESCE((SELECT Count(DISTINCT "HN") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department), 0) "IPD Visit", 
COALESCE((SELECT SUM("�����Һ�ԡ��") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department), 0) "����� IPD", 
COALESCE((SELECT SUM("��ǹŴ") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department), 0) "��ǹŴ IPD", 
COALESCE((SELECT SUM("�ʹ�ط��") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'��' AND "Group"=';.' AND pordered_by_department=main.pordered_by_department), 0) "�ط�� IPD" 
FROM 
(SELECT DISTINCT pordered_by_department FROM vwbpk_tmp_2015_agent_model2_with_nation) main
ORDER BY pordered_by_department COLLATE "th_TH"
