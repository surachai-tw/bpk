SELECT tmp.*, ROUND(CAST((tmp."รวมค่าบริการ"-tmp."ส่วนลด"-tmp."ยอดสุทธิ") AS NUMERIC), 2) "DIFF"
FROM 
(
    SELECT 
    pverify_deptid "รหัสแผนก", pordered_by_department "ชื่อแผนก", 
    SUM(CASE WHEN account_id='01' THEN (quantity*unit_price_cost) ELSE 0 END)::FLOAT "ต้นทุนยา", 
    SUM(CASE WHEN account_id='02' THEN (quantity*unit_price_cost) ELSE 0 END)::FLOAT "ต้นทุนเวชภัณฑ์", 
    SUM(CASE WHEN account_id<>'01' AND account_id<>'02' THEN (quantity*unit_price_cost) ELSE 0 END)::FLOAT "ต้นทุนอื่นๆ", 
    SUM(CASE WHEN account_id='01' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่ายา", 
    SUM(CASE WHEN account_id='02' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่าเวชภัณฑ์",  
    SUM(CASE WHEN account_id='03' THEN acccredit.linesale ELSE 0 END)::FLOAT "Lab",  
    SUM(CASE WHEN account_id='04' THEN acccredit.linesale ELSE 0 END)::FLOAT "X-ray",  
    SUM(CASE WHEN account_id='05' THEN acccredit.linesale ELSE 0 END)::FLOAT "ทันตกรรม",  
    SUM(CASE WHEN account_id='06' THEN acccredit.linesale ELSE 0 END)::FLOAT "กายภาพ",  
    SUM(CASE WHEN account_id='07' THEN acccredit.linesale ELSE 0 END)::FLOAT "ล้างไต",  
    SUM(CASE WHEN account_id='08' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่าอุปกรณ์",  
    SUM(CASE WHEN account_id='09' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่าแพทย์",  
    SUM(CASE WHEN account_id='10' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่าบริการพยาบาล",  
    SUM(CASE WHEN account_id='11' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่าบริการโรงพยาบาล",  
    SUM(CASE WHEN account_id='12' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่าห้อง",  
    SUM(CASE WHEN account_id='13' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่าอาหาร",  
    SUM(CASE WHEN account_id='14' THEN acccredit.linesale ELSE 0 END)::FLOAT "เหมาจ่าย",  
    SUM(CASE WHEN account_id='15' THEN acccredit.linesale ELSE 0 END)::FLOAT "อื่นๆ",  
    SUM(ROUND(CAST(acccredit.linesale AS NUMERIC), 2))::FLOAT "รวมค่าบริการ", 
    SUM(ROUND(CAST(acccredit.wlinediscount AS NUMERIC), 2))::FLOAT "ส่วนลด", 
    SUM(ROUND(CAST(acccredit.wlinepaid AS NUMERIC), 2))::FLOAT "ยอดสุทธิ"
    FROM bpk_account_credit_detail acccredit
    WHERE financial_discharge_date BETWEEN '$P!{dBeginDate}' AND '$P!{dEndDate}' AND fix_visit_type_id='1'
    GROUP BY pverify_deptid, pordered_by_department
) tmp 
ORDER BY "ชื่อแผนก" COLLATE "th_TH"
