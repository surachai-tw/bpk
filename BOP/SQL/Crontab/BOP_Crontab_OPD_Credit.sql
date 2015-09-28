INSERT INTO bpk_account_credit_detail(order_item_id, verify_date, verify_time, hn, vn, visit_id, visit_spid, visit_date, visit_time, fix_visit_type_id, base_patient_type_id, financial_discharge, financial_discharge_date, financial_discharge_time, main_doctor_id, patient_name, base_billing_group_id, billing_description, account_id, account_description, item_description, verify_eid, overify_spid, oordered_by_service_point, overify_deptid, oordered_by_department, verify_spid, ordered_by_service_point, verify_deptid, ordered_by_department, execute_date, execute_time, execute_eid, execute_spid, execute_by_service_point, execute_deptid, execute_by_department, fix_order_status_id, order_status_description, fix_item_type_id, quantity, base_unit_id, unit_price_cost, unit_price_sale, linecost, linesale, item_id) 
SELECT order_item_id, verify_date, verify_time, hn, vn, visit_id, visit_spid, visit_date, visit_time, fix_visit_type_id, base_patient_type_id, financial_discharge, financial_discharge_date, financial_discharge_time, main_doctor_id, patient_name, base_billing_group_id, billing_description, account_id, account_description, item_description, verify_eid, overify_spid, oordered_by_service_point, overify_deptid, oordered_by_department, verify_spid, ordered_by_service_point, verify_deptid, ordered_by_department, execute_date, execute_time, execute_eid, 
(CASE WHEN tmp.fix_order_status_id IN ('2', '3') AND tmp.execute_spid='' THEN verify_spid ELSE execute_spid END) execute_spid, 
(CASE WHEN tmp.fix_order_status_id IN ('2', '3') AND tmp.execute_spid='' THEN ordered_by_service_point ELSE execute_by_service_point END) execute_by_service_point, 
(CASE WHEN tmp.fix_order_status_id IN ('2', '3') AND tmp.execute_deptid='' THEN verify_deptid ELSE execute_deptid END) execute_deptid, 
(CASE WHEN tmp.fix_order_status_id IN ('2', '3') AND tmp.execute_deptid='' THEN ordered_by_department ELSE execute_by_department END) execute_by_department, 
fix_order_status_id, order_status_description, fix_item_type_id, quantity, base_unit_id, unit_price_cost, unit_price_sale, linecost, linesale, item_id 
FROM 
(
    SELECT 
        order_item.order_item_id, 
        order_item.verify_date, 
        order_item.verify_time,     
        format_hn(visit.hn) hn, 
        format_vn(visit.vn) vn, 
        visit.visit_id, 
        visit.visit_spid, 
        visit.visit_date, 
        visit.visit_time, 
        visit.fix_visit_type_id, 
        visit.base_patient_type_id, 
        visit.financial_discharge, 
        visit.financial_discharge_date, 
        visit.financial_discharge_time, 
        (SELECT employee_id FROM attending_physician WHERE attending_physician.priority='1' AND attending_physician.visit_id=visit.visit_id LIMIT 1) main_doctor_id, 
        bpkget_patient_name(visit.patient_id) patient_name, 
        order_item.base_billing_group_id, 
        billgrp.description_th billing_description, 
        billgrp.acc_group account_id, 
        bpk_account_group.description account_description, 
        item.common_name item_description, 
        order_item.verify_eid, 
        order_item.verify_spid overify_spid, 
        bpkget_service_description(order_item.verify_spid) oordered_by_service_point, 
        bpkget_department_id_by_spid(order_item.verify_spid) overify_deptid, 
        bpkget_department_description_by_spid(order_item.verify_spid) oordered_by_department, 
        bpkget_bop_change_service_point_id(visit.visit_id, order_item.verify_spid, order_item.verify_date||' '||order_item.verify_time, order_item.order_item_id) verify_spid, 
        bpkget_service_description(bpkget_bop_change_service_point_id(visit.visit_id, order_item.verify_spid, order_item.verify_date||' '||order_item.verify_time, order_item.order_item_id)) ordered_by_service_point, 
        bpkget_department_id_by_spid(bpkget_bop_change_service_point_id(visit.visit_id, order_item.verify_spid, order_item.verify_date||' '||order_item.verify_time, order_item.order_item_id)) verify_deptid, 
        bpkget_department_description_by_spid(bpkget_bop_change_service_point_id(visit.visit_id, order_item.verify_spid, order_item.verify_date||' '||order_item.verify_time, order_item.order_item_id)) ordered_by_department, 
        COALESCE(order_item.execute_date, COALESCE(order_item.dispense_date, '')) execute_date, 
        COALESCE(order_item.execute_time, COALESCE(order_item.dispense_time, '')) execute_time, 
        COALESCE(order_item.execute_eid, COALESCE(order_item.dispense_eid, '')) execute_eid, 
        COALESCE(order_item.execute_spid, COALESCE(order_item.dispense_spid, '')) execute_spid, 
        bpkget_service_description(COALESCE(order_item.execute_spid, COALESCE(order_item.dispense_spid, ''))) execute_by_service_point, 
        bpkget_department_id_by_spid(COALESCE(order_item.execute_spid, COALESCE(order_item.dispense_spid, ''))) execute_deptid, 
        bpkget_department_description_by_spid(COALESCE(order_item.execute_spid, COALESCE(order_item.dispense_spid, ''))) execute_by_department, 
        bpk_fix_order_status.fix_order_status_id, 
        bpk_fix_order_status.description order_status_description, 
        order_item.fix_item_type_id, 
        CAST(order_item.quantity AS FLOAT) quantity, 
        COALESCE(order_item.base_unit_id, '') base_unit_id, 
        CAST(order_item.unit_price_cost AS FLOAT) unit_price_cost, 
        CAST(order_item.unit_price_sale AS FLOAT) unit_price_sale, 
        (CAST(quantity AS FLOAT)*CAST(order_item.unit_price_cost AS FLOAT))::FLOAT linecost, 
        (CAST(quantity AS FLOAT)*CAST(order_item.unit_price_sale AS FLOAT))::FLOAT linesale, 
        order_item.item_id 
    FROM 
    order_item 
    LEFT JOIN bpk_fix_order_status ON order_item.fix_order_status_id=bpk_fix_order_status.fix_order_status_id 
    LEFT JOIN base_billing_group billgrp ON order_item.base_billing_group_id=billgrp.base_billing_group_id 
    LEFT JOIN bpk_account_group ON billgrp.acc_group=bpk_account_group.id 
    LEFT JOIN item ON order_item.item_id=item.item_id 
    INNER JOIN visit ON order_item.visit_id=visit.visit_id AND visit.fix_visit_type_id='0'
    WHERE
    verify_date=Cast(CURRENT_DATE-1 AS VARCHAR(10))
) tmp 
ORDER BY tmp.verify_date, tmp.verify_time;
