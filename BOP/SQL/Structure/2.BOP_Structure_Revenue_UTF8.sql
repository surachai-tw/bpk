CREATE OR REPLACE FUNCTION isnumeric(text) RETURNS BOOLEAN AS '
DECLARE x NUMERIC;
BEGIN
    x = $1::NUMERIC;
    RETURN TRUE;
EXCEPTION WHEN others THEN
    RETURN FALSE;
END;
' LANGUAGE plpgsql IMMUTABLE; 

UPDATE item SET unit_price_cost='0' WHERE trim(unit_price_cost)='';
UPDATE item SET unit_price_cost='0' WHERE unit_price_cost='NaN';
UPDATE item SET unit_price_cost='0' WHERE unit_price_cost='unit_price_cost';

-- DROP TABLE bpk_account_credit_detail;
CREATE TABLE bpk_account_credit_detail
(
    id SERIAL PRIMARY KEY, 
	order_item_id VARCHAR(255) DEFAULT '', 
	verify_date VARCHAR(255) DEFAULT '', 
	verify_time VARCHAR(255) DEFAULT '', 
	hn VARCHAR(255) DEFAULT '', 
	vn VARCHAR(255) DEFAULT '', 
    visit_id VARCHAR(255) DEFAULT '', 
    visit_spid VARCHAR(255) DEFAULT '', 
	visit_date VARCHAR(255) DEFAULT '', 
	visit_time VARCHAR(255) DEFAULT '', 
	fix_visit_type_id VARCHAR(255) DEFAULT '', 
	base_patient_type_id VARCHAR(255) DEFAULT '', 
	financial_discharge VARCHAR(255) DEFAULT '', 
	financial_discharge_date VARCHAR(255) DEFAULT '', 
	financial_discharge_time VARCHAR(255) DEFAULT '', 
	main_doctor_id VARCHAR(255) DEFAULT '', 
	patient_name VARCHAR(255) DEFAULT '', 
	base_billing_group_id VARCHAR(255) DEFAULT '', 
	billing_description VARCHAR(255) DEFAULT '', 
    account_id VARCHAR(255) DEFAULT '', 
	account_description VARCHAR(255) DEFAULT '', 
	item_description VARCHAR(255) DEFAULT '', 
	verify_eid VARCHAR(255) DEFAULT '', 
	overify_spid VARCHAR(255) DEFAULT '', 
	oordered_by_service_point VARCHAR(255) DEFAULT '', 
	overify_deptid VARCHAR(255) DEFAULT '', 
	oordered_by_department VARCHAR(255) DEFAULT '', 
	verify_spid VARCHAR(255) DEFAULT '', 
	ordered_by_service_point VARCHAR(255) DEFAULT '', 
	verify_deptid VARCHAR(255) DEFAULT '', 
	ordered_by_department VARCHAR(255) DEFAULT '', 
    execute_date VARCHAR(255) DEFAULT '', 
    execute_time VARCHAR(255) DEFAULT '', 
	execute_eid VARCHAR(255) DEFAULT '', 
	execute_spid VARCHAR(255) DEFAULT '', 
	execute_by_service_point VARCHAR(255) DEFAULT '', 
	execute_deptid VARCHAR(255) DEFAULT '', 
	execute_by_department VARCHAR(255) DEFAULT '', 
	fix_order_status_id VARCHAR(255) DEFAULT '', 
	order_status_description VARCHAR(255) DEFAULT '', 
    fix_item_type_id VARCHAR(255) DEFAULT '', 
	quantity FLOAT DEFAULT 0, 
    base_unit_id VARCHAR(255) DEFAULT '', 
	unit_price_cost FLOAT DEFAULT 0, 
	unit_price_sale FLOAT DEFAULT 0, 
    linecost FLOAT DEFAULT 0, 
    linesale FLOAT DEFAULT 0, 
    wlinediscount FLOAT DEFAULT 0, 
    wlinepaid FLOAT DEFAULT 0
);
CREATE INDEX idx_credit_detail_visit_id ON bpk_account_credit_detail(visit_id);
CREATE INDEX idx_credit_detail_verify_date ON bpk_account_credit_detail(verify_date);

-- DROP TABLE bpk_account_debit_detail;
CREATE TABLE bpk_account_debit_detail 
(
    id SERIAL PRIMARY KEY, 
    receipt_id VARCHAR(255) DEFAULT '', 
    receive_date VARCHAR(255) DEFAULT '', 
    receive_time VARCHAR(255) DEFAULT '', 
    hn VARCHAR(255) DEFAULT '', 
    vn VARCHAR(255) DEFAULT '', 
    visit_id VARCHAR(255) DEFAULT '', 
    visit_spid VARCHAR(255) DEFAULT '', 
    visit_date VARCHAR(255) DEFAULT '', 
    visit_time VARCHAR(255) DEFAULT '', 
    fix_visit_type_id VARCHAR(255) DEFAULT '', 
    base_patient_type_id VARCHAR(255) DEFAULT '', 
    financial_discharge VARCHAR(255) DEFAULT '', 
    financial_discharge_date VARCHAR(255) DEFAULT '', 
    financial_discharge_time VARCHAR(255) DEFAULT '', 
    main_doctor_id VARCHAR(255) DEFAULT '', 
    patient_name VARCHAR(255) DEFAULT '', 
    fix_receipt_type_id VARCHAR(255) DEFAULT '', 
    receipt_type_description VARCHAR(255) DEFAULT '', 
    fix_receipt_status_id VARCHAR(255) DEFAULT '', 
    receipt_status_description VARCHAR(255) DEFAULT '', 
    original_receipt_number VARCHAR(255) DEFAULT '', 
    receipt_number VARCHAR(255) DEFAULT '', 
    base_billing_group_id VARCHAR(255) DEFAULT '', 
    billing_description VARCHAR(255) DEFAULT '', 
    account_id VARCHAR(255) DEFAULT '', 
    account_description VARCHAR(255) DEFAULT '', 
    linesale FLOAT DEFAULT 0, 
    linediscount FLOAT DEFAULT 0, 
    wlinediscount FLOAT DEFAULT 0, 
    linepaid FLOAT DEFAULT 0, 
    wlinepaid FLOAT DEFAULT 0, 
    sale FLOAT DEFAULT 0, 
    discount FLOAT DEFAULT 0, 
    special_discount FLOAT DEFAULT 0, 
    decimal_discount FLOAT DEFAULT 0, 
    paid FLOAT DEFAULT 0, 
    plan_id VARCHAR(255) DEFAULT '', 
    plan_code VARCHAR(255) DEFAULT '', 
    plan_description VARCHAR(255) DEFAULT '', 
    receive_eid VARCHAR(255) DEFAULT '', 
    all_plan VARCHAR(2000) DEFAULT ''
);
CREATE INDEX idx_debit_detail_visit_id ON bpk_account_debit_detail(visit_id);
CREATE INDEX idx_debit_detail_receive_date ON bpk_account_debit_detail(receive_date);
