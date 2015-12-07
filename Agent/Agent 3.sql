SELECT "Discharge Date", "HN", "Visit Type", "Nation", "Group", "Agent Code", "Agent Name", "pverify_deptid", "pordered_by_department", "รวมค่าบริการ", "ส่วนลด", "ยอดสุทธิ" FROM vwbpk_tmp_2015_agent_model2_with_nation LIMIT 1

SELECT * FROM vwbpk_tmp_2015_agent_model2_with_nation


SELECT 
main.pordered_by_department, 
COALESCE((SELECT Count(DISTINCT "HN") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'ไทย' AND "Group"='อพ.' AND pordered_by_department=main.pordered_by_department), 0) "OPD Visit", 
COALESCE((SELECT SUM("รวมค่าบริการ") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'ไทย' AND "Group"='อพ.' AND pordered_by_department=main.pordered_by_department), 0) "รายได้ OPD", 
COALESCE((SELECT SUM("ส่วนลด") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'ไทย' AND "Group"='อพ.' AND pordered_by_department=main.pordered_by_department), 0) "ส่วนลด OPD", 
COALESCE((SELECT SUM("ยอดสุทธิ") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='OPD' AND "Nation"<>'ไทย' AND "Group"='อพ.' AND pordered_by_department=main.pordered_by_department), 0) "สุทธิ OPD", 
COALESCE((SELECT Count(DISTINCT "HN") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'ไทย' AND "Group"='อพ.' AND pordered_by_department=main.pordered_by_department), 0) "IPD Visit", 
COALESCE((SELECT SUM("รวมค่าบริการ") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'ไทย' AND "Group"='อพ.' AND pordered_by_department=main.pordered_by_department), 0) "รายได้ IPD", 
COALESCE((SELECT SUM("ส่วนลด") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'ไทย' AND "Group"='อพ.' AND pordered_by_department=main.pordered_by_department), 0) "ส่วนลด IPD", 
COALESCE((SELECT SUM("ยอดสุทธิ") FROM vwbpk_tmp_2015_agent_model2_with_nation WHERE "Visit Type"='IPD' AND "Nation"<>'ไทย' AND "Group"='อพ.' AND pordered_by_department=main.pordered_by_department), 0) "สุทธิ IPD" 
FROM 
(SELECT DISTINCT pordered_by_department FROM vwbpk_tmp_2015_agent_model2_with_nation) main
ORDER BY pordered_by_department COLLATE "th_TH"
