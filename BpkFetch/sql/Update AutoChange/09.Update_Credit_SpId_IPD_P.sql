UPDATE bpk_account_credit_detail 
SET
pverify_spid = bpkget_bop_forward_service_point_id(visit_id, verify_spid, order_item_id) 
WHERE fix_visit_type_id='1' 
AND financial_discharge_date BETWEEN '$P!{FromDate}' AND '$P!{FromDate}'
