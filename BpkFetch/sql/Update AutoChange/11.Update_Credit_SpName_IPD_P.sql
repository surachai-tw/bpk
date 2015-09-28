UPDATE bpk_account_credit_detail 
SET 
pordered_by_service_point = bpkget_service_description(pverify_spid)
WHERE fix_visit_type_id='1' 
AND financial_discharge_date BETWEEN '$P!{FromDate}' AND '$P!{FromDate}'
