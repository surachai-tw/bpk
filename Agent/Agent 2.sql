-- CREATE TABLE bpk_temp_2015_agent_without_nation AS 
INSERT INTO bpk_temp_2015_agent_without_nation ("Type", "DischargeDate", "OPDVisit", "�����OPD", "��ǹŴOPD", "�ط��OPD", "IPDVisit", "�����IPD", "��ǹŴIPD", "�ط��IPD") 
SELECT 
';.' "Type", 
"Discharge Date" "DischargeDate", 
"OPD Visit" "OPDVisit", 
"����� OPD" "�����OPD", 
"��ǹŴ OPD" "��ǹŴOPD", 
"�ط�� OPD" "�ط��OPD", 
"IPD Visit" "IPDVisit", 
"����� IPD" "�����IPD", 
"��ǹŴ IPD" "��ǹŴIPD", 
"�ط�� IPD" "�ط��IPD"
FROM bpk_agent_without_nation_th_total('2015-11-01', '2015-11-30', ';.')


SELECT * FROM bpk_temp_2015_agent_without_nation ORDER BY "DischargeDate"

SELECT 
substr("DischargeDate", 1, 7) "Year-Month", SUM("OPDVisit") "OPDVisit", SUM("�����OPD") "�����OPD", SUM("��ǹŴOPD") "��ǹŴOPD", SUM("�ط��OPD") "�ط��OPD", SUM("IPDVisit") "IPDVisit", SUM("�����IPD") "�����IPD", SUM("��ǹŴIPD") "��ǹŴIPD", SUM("�ط��IPD") "�ط��IPD"
FROM 
bpk_temp_2015_agent_without_nation
GROUP BY substr("DischargeDate", 1, 7)
ORDER BY substr("DischargeDate", 1, 7) 

