SELECT 
    execute_date "วันที่จ่าย", 
    execute_time "เวลาที่จ่าย", 
    verify_date "วันที่สั่ง", 
    verify_time "เวลาที่สั่ง", 
    item_description "รายการสินค้า", 
    billing_description "Billing Group", 
    account_description "Account Group", 
    hn "HN", 
    vn "VN/AN", 
    patient_name "ชื่อ-สกุล คนไข้", 
    bpkget_employee_name(execute_eid) "ผู้จ่าย", 
    execute_by_service_point "คลังที่จ่าย", 
    execute_deptid "รหัสแผนกที่จ่าย", 
    execute_by_department "แผนกที่จ่าย", 
    bpkget_employee_name(verify_eid) "ชื่อผู้คีย์ Order", 
    ordered_by_service_point "จุดที่คีย์ Order", 
    verify_deptid "รหัสแผนกที่คีย์ Order", 
    ordered_by_department "แผนกที่คีย์ Order", 
    quantity "จำนวน",  
    COALESCE((SELECT description_th FROM base_unit WHERE base_unit_id=acccredit.base_unit_id LIMIT 1), '') "หน่วย", 
    unit_price_cost "ราคาทุน/หน่วย",
    (quantity*unit_price_cost) AS "มูลค่าราคาทุน", 
    unit_price_sale "ราคาขาย/หน่วย", 
    (quantity*unit_price_sale) AS "มูลค่าราคาขาย"
FROM 
bpk_account_credit_detail acccredit 
WHERE 
acccredit.execute_date BETWEEN '$P!{dBeginDate}' AND '$P!{dEndDate}' 
AND acccredit.fix_visit_type_id='0' 
AND acccredit.fix_item_type_id IN ('0', '4')
ORDER BY execute_date, execute_time
