UPDATE bpk_account_credit_detail 
SET 
verify_deptid = bpkget_department_id_by_spid(verify_spid)
WHERE fix_visit_type_id='1' 
AND financial_discharge_date BETWEEN '$P!{FromDate}' AND '$P!{FromDate}'
