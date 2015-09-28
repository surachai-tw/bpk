UPDATE bpk_account_credit_detail 
SET 
verify_spid = bpkget_bop_change_service_point_id(visit_id, overify_spid, verify_date||' '||verify_time, order_item_id)
WHERE fix_visit_type_id='1' 
AND financial_discharge_date BETWEEN '$P!{FromDate}' AND '$P!{FromDate}'
