UPDATE bpk_account_credit_detail 
SET 
ordered_by_department = bpkget_department_description_by_spid(verify_spid)
WHERE fix_visit_type_id='1' 
AND financial_discharge_date BETWEEN '$P!{FromDate}' AND '$P!{FromDate}'
