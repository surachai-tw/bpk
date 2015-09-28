SELECT * FROM 
( 
    SELECT 
    acccredit.visit_date "Visit Date", 
    acccredit.visit_time "Visit Time", 
    acccredit.financial_discharge_date "Discharge Date", 
    acccredit.financial_discharge_time "Discharge Time", 
    acccredit.hn "HN", 
    acccredit.vn "VN", 
    acccredit.patient_name "Patient", 
    SUM(CASE WHEN acccredit.account_id='01' THEN acccredit.linecost ELSE 0 END)::FLOAT "ต้นทุนยา", 
    SUM(CASE WHEN acccredit.account_id='02' THEN acccredit.linecost ELSE 0 END)::FLOAT "ต้นทุนเวชภัณฑ์", 
    SUM(CASE WHEN acccredit.account_id<>'01' AND acccredit.account_id<>'02' THEN acccredit.linecost ELSE 0 END)::FLOAT "ต้นทุนอื่นๆ", 
    SUM(CASE WHEN acccredit.account_id='01' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่ายา", 
    SUM(CASE WHEN acccredit.account_id='02' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่าเวชภัณฑ์",  
    SUM(CASE WHEN acccredit.account_id='03' THEN acccredit.linesale ELSE 0 END)::FLOAT "Lab",  
    SUM(CASE WHEN acccredit.account_id='04' THEN acccredit.linesale ELSE 0 END)::FLOAT "X-ray",  
    SUM(CASE WHEN acccredit.account_id='05' THEN acccredit.linesale ELSE 0 END)::FLOAT "ทันตกรรม",  
    SUM(CASE WHEN acccredit.account_id='06' THEN acccredit.linesale ELSE 0 END)::FLOAT "กายภาพ",  
    SUM(CASE WHEN acccredit.account_id='07' THEN acccredit.linesale ELSE 0 END)::FLOAT "ล้างไต",  
    SUM(CASE WHEN acccredit.account_id='08' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่าอุปกรณ์",  
    SUM(CASE WHEN acccredit.account_id='09' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่าแพทย์",  
    SUM(CASE WHEN acccredit.account_id='10' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่าบริการพยาบาล",  
    SUM(CASE WHEN acccredit.account_id='11' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่าบริการโรงพยาบาล",  
    SUM(CASE WHEN acccredit.account_id='12' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่าห้อง",  
    SUM(CASE WHEN acccredit.account_id='13' THEN acccredit.linesale ELSE 0 END)::FLOAT "ค่าอาหาร",  
    SUM(CASE WHEN acccredit.account_id='14' THEN acccredit.linesale ELSE 0 END)::FLOAT "เหมาจ่าย",  
    SUM(CASE WHEN acccredit.account_id='15' THEN acccredit.linesale ELSE 0 END)::FLOAT "อื่นๆ",  
    SUM(acccredit.linesale)::FLOAT "รวมค่าบริการ (ORDERED)", 
    COALESCE(accdebit."รวมค่าบริการ (BILLED)", 0) "รวมค่าบริการ (BILLED)", 
    accdebit.all_plan "สิทธิการรักษาทั้งหมด"
    FROM bpk_account_credit_detail acccredit
    LEFT JOIN 
        (
            SELECT
                receive_date,  
                visit_id, 
                all_plan, 
                SUM(linesale)::FLOAT "รวมค่าบริการ (BILLED)"          
            FROM bpk_account_debit_detail 
            WHERE receive_date BETWEEN '$P!{dBeginDate}' AND '$P!{dEndDate}' 
            GROUP BY receive_date, visit_id, all_plan 
        ) accdebit ON acccredit.visit_id=accdebit.visit_id AND accdebit.receive_date BETWEEN '$P!{dBeginDate}' AND '$P!{dEndDate}' 
    WHERE acccredit.verify_date BETWEEN '$P!{dBeginDate}' AND '$P!{dEndDate}' AND acccredit.fix_visit_type_id='0' 
    GROUP BY acccredit.visit_date, acccredit.visit_time, acccredit.financial_discharge_date, acccredit.financial_discharge_time, 
    acccredit.hn, acccredit.vn, acccredit.patient_name, "รวมค่าบริการ (BILLED)", accdebit.all_plan 
    ORDER BY acccredit.visit_date, acccredit.visit_time
) tmp
WHERE tmp."รวมค่าบริการ (ORDERED)"<>tmp."รวมค่าบริการ (BILLED)" 
ORDER BY tmp."Visit Date", tmp."Visit Time" 
