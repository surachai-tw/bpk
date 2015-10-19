UPDATE bpk_account_credit_detail SET pverify_spid = bpkget_bop_forward_service_point_id(visit_id, verify_spid, order_item_id) WHERE fix_visit_type_id='0' AND verify_date=Cast(CURRENT_DATE-1 AS VARCHAR(10));
UPDATE bpk_account_credit_detail SET pverify_spid = bpkget_bop_forward_service_point_id(visit_id, verify_spid, order_item_id) WHERE fix_visit_type_id='1' AND financial_discharge_date=Cast(CURRENT_DATE-1 AS VARCHAR(10));
UPDATE bpk_account_credit_detail SET pordered_by_service_point = bpkget_service_description(pverify_spid) WHERE fix_visit_type_id='0' AND verify_date=Cast(CURRENT_DATE-1 AS VARCHAR(10));
UPDATE bpk_account_credit_detail SET pordered_by_service_point = bpkget_service_description(pverify_spid) WHERE fix_visit_type_id='1' AND financial_discharge_date=Cast(CURRENT_DATE-1 AS VARCHAR(10));
UPDATE bpk_account_credit_detail SET pverify_deptid = bpkget_department_id_by_spid(pverify_spid) WHERE fix_visit_type_id='0' AND verify_date=Cast(CURRENT_DATE-1 AS VARCHAR(10));
UPDATE bpk_account_credit_detail SET pverify_deptid = bpkget_department_id_by_spid(pverify_spid) WHERE fix_visit_type_id='1' AND financial_discharge_date=Cast(CURRENT_DATE-1 AS VARCHAR(10));
UPDATE bpk_account_credit_detail SET pordered_by_department = bpkget_department_description_by_spid(pverify_spid) WHERE fix_visit_type_id='0' AND verify_date=Cast(CURRENT_DATE-1 AS VARCHAR(10));
UPDATE bpk_account_credit_detail SET pordered_by_department = bpkget_department_description_by_spid(pverify_spid) WHERE fix_visit_type_id='1' AND financial_discharge_date=Cast(CURRENT_DATE-1 AS VARCHAR(10));
UPDATE bpk_account_credit_detail SET an=format_an(visit.an) FROM visit WHERE bpk_account_credit_detail.visit_id = visit.visit_id AND bpk_account_credit_detail.fix_visit_type_id='1' AND bpk_account_credit_detail.financial_discharge_date BETWEEN Cast(CURRENT_DATE-1 AS VARCHAR(10)) AND Cast(CURRENT_DATE-1 AS VARCHAR(10));