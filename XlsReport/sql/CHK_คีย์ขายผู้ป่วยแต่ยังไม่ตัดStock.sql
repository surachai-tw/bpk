SELECT "HN", "VN/AN", "Visit Date", "Visit Time", "รายการสั่งตรวจ/รักษา",verify_date, verify_time,  verify_eid, verify_spid, ordered_by_service_point, verify_deptid, ordered_by_department,
quantity "จำนวน", "หน่วย", unit_price_cost "ราคาทุน/หน่วย", quantity*unit_price_cost "มูลค่าราคาทุน", unit_price_sale "ราคาขาย/หน่วย", quantity*unit_price_sale "มูลค่าราคาขาย"
FROM 
(
    SELECT 
        format_hn(patient.hn) "HN", 
        CASE WHEN visit.fix_visit_type_id='0' THEN format_vn(visit.vn) ELSE format_an(admit.an) END "VN/AN", 
        visit.visit_date "Visit Date", 
        visit.visit_time "Visit Time", 
        item.common_name "รายการสั่งตรวจ/รักษา", 
        order_item.verify_date, 
        order_item.verify_time, 
        order_item.verify_eid, 
        order_item.verify_spid verify_spid, 
        bpkget_service_description(order_item.verify_spid) ordered_by_service_point, 
        bpkget_department_id_by_spid(order_item.verify_spid) verify_deptid, 
        bpkget_department_description_by_spid(order_item.verify_spid) ordered_by_department, 
        CAST(order_item.quantity AS FLOAT) quantity, 
       COALESCE((SELECT description_th FROM base_unit WHERE base_unit_id=order_item.base_unit_id LIMIT 1), '') "หน่วย", 
        CAST(order_item.unit_price_cost AS FLOAT) unit_price_cost, 
        CAST(order_item.unit_price_sale AS FLOAT) unit_price_sale 
    FROM 
    order_item 
    LEFT JOIN item ON order_item.item_id=item.item_id 
    INNER JOIN visit ON order_item.visit_id=visit.visit_id 
    INNER JOIN admit ON visit.visit_id=admit.visit_id 
    INNER JOIN patient ON visit.patient_id=patient.patient_id 
    WHERE 
    order_item.fix_item_type_id IN ('0', '4') 
    AND order_item.fix_order_status_id='1' 
    AND verify_date BETWEEN '$P!{dBeginDate}' AND '$P!{dEndDate}'
) tmp 
ORDER BY "HN", "Visit Date", "Visit Time", verify_date, verify_time