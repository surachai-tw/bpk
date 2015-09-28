SELECT 
"วันที่รับบริการ", "เวลาที่รับบริการ", "HN", "VN", "AN", "ชื่อ-สกุล", "รหัสแผนก", "ชื่อแผนก", "รายการสั่งตรวจ/รักษา", "ต้นทุนยา", "ต้นทุนเวชภัณฑ์", "ต้นทุนอื่นๆ", "ค่ายา", "ค่าเวชภัณฑ์", "Lab", "X-ray", "ทันตกรรม", "กายภาพ", "ล้างไต", "ค่าอุปกรณ์", "ค่าแพทย์", "ค่าบริการพยาบาล", "ค่าบริการโรงพยาบาล", "ค่าห้อง", "ค่าอาหาร", "เหมาจ่าย", "อื่นๆ", "รวมค่าบริการ", "ส่วนลด", "ยอดสุทธิ", 
tmp."รวมค่าบริการ"-tmp."ส่วนลด"-tmp."ยอดสุทธิ" "DIFF", "สิทธิเรียกเก็บทั้งหมด" 
FROM 
(
    SELECT 
    acccredit.visit_id, 
    acccredit.visit_date "วันที่รับบริการ", 
    acccredit.visit_time "เวลาที่รับบริการ", 
    acccredit.hn "HN", 
    acccredit.vn "VN", 
    acccredit.an "AN", 
    acccredit.patient_name "ชื่อ-สกุล", 
    pverify_deptid "รหัสแผนก", 
    pordered_by_department "ชื่อแผนก", 
    item_description "รายการสั่งตรวจ/รักษา", 
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
    SUM(ROUND(CAST(acccredit.wlinepaid AS NUMERIC), 2))::FLOAT "ยอดสุทธิ", 
    (SELECT all_plan FROM bpk_account_debit_detail WHERE visit_id=acccredit.visit_id LIMIT 1) "สิทธิเรียกเก็บทั้งหมด"
    FROM bpk_account_credit_detail acccredit    
    WHERE financial_discharge_date BETWEEN '$P!{dBeginDate}' AND '$P!{dEndDate}' AND fix_visit_type_id='1'
    GROUP BY visit_id, acccredit.visit_date, visit_time, acccredit.hn, acccredit.vn, acccredit.an, 
    acccredit.patient_name, pverify_deptid, pordered_by_department, item_description 
) tmp 
ORDER BY "วันที่รับบริการ", "เวลาที่รับบริการ", "AN", "ชื่อแผนก" COLLATE "th_TH", "รายการสั่งตรวจ/รักษา" COLLATE "th_TH"
