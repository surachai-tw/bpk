UPDATE bpk_account_credit_detail 
SET 
pverify_deptid = bpkget_department_id_by_spid(pverify_spid)
WHERE fix_visit_type_id='0' 
AND verify_date BETWEEN '$P!{FromDate}' AND '$P!{FromDate}'
