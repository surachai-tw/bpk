INSERT INTO bpk_account_debit_detail(receipt_id, receive_date, receive_time, hn, vn, visit_id, visit_spid, visit_date, visit_time, fix_visit_type_id, base_patient_type_id, financial_discharge, financial_discharge_date, financial_discharge_time, main_doctor_id, patient_name, fix_receipt_type_id, receipt_type_description, fix_receipt_status_id, receipt_status_description, original_receipt_number, receipt_number, base_billing_group_id, billing_description, account_id, account_description, linesale, linediscount, linepaid, sale, discount, special_discount, decimal_discount, paid, plan_id, plan_code, plan_description, receive_eid, all_plan)
SELECT
    receipt.receipt_id, 
    receipt.receive_date, 
    receipt.receive_time, 
    format_hn(visit.hn) hn, 
    format_an(visit.vn) vn, 
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
    receipt.fix_receipt_type_id, 
    bpk_fix_receipt_type.description receipt_type_description, 
    receipt.fix_receipt_status_id, 
    bpk_fix_receipt_status.description receipt_status_description, 
    receipt.receipt_number original_receipt_number, 
    format_receipt(receipt.receipt_number, receipt.fix_visit_type_id, receipt.fix_receipt_type_id) receipt_number, 
    receipt_billing_group.base_billing_group_id, 
    billgrp.description_th billing_description, 
    billgrp.acc_group account_id, 
    bpk_account_group.description account_description, 
    CASE WHEN receipt.fix_receipt_status_id<>'2' THEN -1*CAST(receipt_billing_group.cost AS FLOAT) ELSE CAST(receipt_billing_group.cost AS FLOAT) END linesale, 
    CASE WHEN receipt.fix_receipt_status_id<>'2' THEN -1*CAST(receipt_billing_group.discount AS FLOAT) ELSE CAST(receipt_billing_group.discount AS FLOAT) END linediscount, 
    CASE WHEN receipt.fix_receipt_status_id<>'2' THEN -1*CAST(receipt_billing_group.paid AS FLOAT) ELSE CAST(receipt_billing_group.paid AS FLOAT) END linepaid, 
    CASE WHEN receipt.fix_receipt_status_id<>'2' THEN -1*CAST(receipt.cost AS FLOAT) ELSE CAST(receipt.cost AS FLOAT) END sale, 
    CASE WHEN receipt.fix_receipt_status_id<>'2' THEN -1*CAST(receipt.discount AS FLOAT) ELSE CAST(receipt.discount AS FLOAT) END discount, 
    CASE WHEN receipt.fix_receipt_status_id<>'2' THEN -1*CAST(receipt.special_discount AS FLOAT) ELSE CAST(receipt.special_discount AS FLOAT) END special_discount, 
    CASE WHEN receipt.fix_receipt_status_id<>'2' THEN -1*CAST(receipt.decimal_discount AS FLOAT) ELSE CAST(receipt.decimal_discount AS FLOAT) END decimal_discount, 
    CASE WHEN receipt.fix_receipt_status_id<>'2' THEN -1*CAST(receipt.paid AS FLOAT) ELSE CAST(receipt.paid AS FLOAT) END paid, 
    receipt.plan_id, 
    plan.plan_code, 
    plan.description plan_description, 
    receipt.receive_eid, 
    (SELECT array_to_string(array(select ('['||plan.plan_code||'] '||plan.description) FROM visit_payment INNER JOIN plan ON visit_payment.plan_id=plan.plan_id WHERE visit_payment.visit_id=visit.visit_id ORDER BY priority), ', ')) all_plan
FROM 
receipt 
LEFT JOIN bpk_fix_receipt_type ON receipt.fix_receipt_type_id=bpk_fix_receipt_type.fix_receipt_type_id 
LEFT JOIN bpk_fix_receipt_status ON receipt.fix_receipt_status_id=bpk_fix_receipt_status.fix_receipt_status_id 
INNER JOIN receipt_billing_group ON receipt.receipt_id=receipt_billing_group.receipt_id 
LEFT JOIN base_billing_group billgrp ON receipt_billing_group.base_billing_group_id=billgrp.base_billing_group_id 
LEFT JOIN bpk_account_group ON billgrp.acc_group=bpk_account_group.id 
LEFT JOIN plan ON receipt.plan_id=plan.plan_id 
INNER JOIN visit ON receipt.visit_id=visit.visit_id AND visit.fix_visit_type_id='0'
WHERE receive_date='$P!{FromDate}'
AND receipt.fix_receipt_type_id IN ('1', '6', '7') 
AND receipt.fix_receipt_status_id='2'
AND CAST(receipt.paid AS FLOAT)<>0 
ORDER BY receive_date, receipt.visit_id, receipt.receipt_id, billgrp.base_billing_group_id; 
