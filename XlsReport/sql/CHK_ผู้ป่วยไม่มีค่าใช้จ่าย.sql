SELECT 
format_hn(patient.hn) "HN", 
bpkget_patient_name(visit.patient_id) "Patient",
CASE WHEN visit.fix_visit_type_id='0' THEN format_vn(visit.vn) ELSE format_an(admit.an) END "VN/AN", 
CASE WHEN visit.fix_visit_type_id='0' THEN 'OPD' ELSE 'IPD' END "Type", 
visit.visit_date "Visit Date", 
visit.visit_time "Visit Time", 
(SELECT array_to_string(array(select ('['||plan.plan_code||'] '||plan.description) FROM visit_payment INNER JOIN plan ON visit_payment.plan_id=plan.plan_id WHERE visit_payment.visit_id=tmp.visit_id ORDER BY priority), ', ')) "สิทธิการรักษา" 
FROM 
(
    SELECT 
    visit.visit_id, 
    Count(order_item.order_item_id) Cnt 
    FROM 
    visit LEFT JOIN order_item ON visit.visit_id=order_item.visit_id 
    WHERE 
    visit.visit_date BETWEEN '$P!{dBeginDate}' AND '$P!{dEndDate}'
    AND visit.active='1' 
    GROUP BY visit.visit_id
) tmp 
LEFT JOIN visit ON tmp.visit_id=visit.visit_id 
LEFT JOIN admit ON tmp.visit_id=admit.visit_id 
LEFT JOIN patient ON visit.patient_id=patient.patient_id 
WHERE tmp.Cnt=0
ORDER BY vn 