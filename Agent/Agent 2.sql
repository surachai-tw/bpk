-- CREATE TABLE bpk_temp_2015_agent_without_nation AS 
INSERT INTO bpk_temp_2015_agent_without_nation ("Type", "DischargeDate", "OPDVisit", "รายได้OPD", "ส่วนลดOPD", "สุทธิOPD", "IPDVisit", "รายได้IPD", "ส่วนลดIPD", "สุทธิIPD") 
SELECT 
'อพ.' "Type", 
"Discharge Date" "DischargeDate", 
"OPD Visit" "OPDVisit", 
"รายได้ OPD" "รายได้OPD", 
"ส่วนลด OPD" "ส่วนลดOPD", 
"สุทธิ OPD" "สุทธิOPD", 
"IPD Visit" "IPDVisit", 
"รายได้ IPD" "รายได้IPD", 
"ส่วนลด IPD" "ส่วนลดIPD", 
"สุทธิ IPD" "สุทธิIPD"
FROM bpk_agent_without_nation_th_total('2015-11-01', '2015-11-30', 'อพ.')


SELECT * FROM bpk_temp_2015_agent_without_nation ORDER BY "DischargeDate"

SELECT 
substr("DischargeDate", 1, 7) "Year-Month", SUM("OPDVisit") "OPDVisit", SUM("รายได้OPD") "รายได้OPD", SUM("ส่วนลดOPD") "ส่วนลดOPD", SUM("สุทธิOPD") "สุทธิOPD", SUM("IPDVisit") "IPDVisit", SUM("รายได้IPD") "รายได้IPD", SUM("ส่วนลดIPD") "ส่วนลดIPD", SUM("สุทธิIPD") "สุทธิIPD"
FROM 
bpk_temp_2015_agent_without_nation
GROUP BY substr("DischargeDate", 1, 7)
ORDER BY substr("DischargeDate", 1, 7) 

