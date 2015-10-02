CREATE OR REPLACE FUNCTION isnumeric(text) RETURNS BOOLEAN AS '
DECLARE x NUMERIC;
BEGIN
    x = $1::NUMERIC;
    RETURN TRUE;
EXCEPTION WHEN others THEN
    RETURN FALSE;
END;
' LANGUAGE plpgsql IMMUTABLE; 

-- DROP TABLE bpk_account_credit_detail;
CREATE TABLE bpk_account_credit_detail (
    id integer NOT NULL PRIMARY KEY,
    order_item_id character varying(255) DEFAULT ''::character varying,
    verify_date character varying(255) DEFAULT ''::character varying,
    verify_time character varying(255) DEFAULT ''::character varying,
    hn character varying(255) DEFAULT ''::character varying,
    vn character varying(255) DEFAULT ''::character varying,
    visit_id character varying(255) DEFAULT ''::character varying,
    visit_spid character varying(255) DEFAULT ''::character varying,
    visit_date character varying(255) DEFAULT ''::character varying,
    visit_time character varying(255) DEFAULT ''::character varying,
    fix_visit_type_id character varying(255) DEFAULT ''::character varying,
    base_patient_type_id character varying(255) DEFAULT ''::character varying,
    financial_discharge character varying(255) DEFAULT ''::character varying,
    financial_discharge_date character varying(255) DEFAULT ''::character varying,
    financial_discharge_time character varying(255) DEFAULT ''::character varying,
    main_doctor_id character varying(255) DEFAULT ''::character varying,
    patient_name character varying(255) DEFAULT ''::character varying,
    base_billing_group_id character varying(255) DEFAULT ''::character varying,
    billing_description character varying(255) DEFAULT ''::character varying,
    account_id character varying(255) DEFAULT ''::character varying,
    account_description character varying(255) DEFAULT ''::character varying,
    item_id character varying(255) DEFAULT ''::character varying,
    item_description character varying(255) DEFAULT ''::character varying,
    verify_eid character varying(255) DEFAULT ''::character varying,
    overify_spid character varying(255) DEFAULT ''::character varying,
    oordered_by_service_point character varying(255) DEFAULT ''::character varying,
    overify_deptid character varying(255) DEFAULT ''::character varying,
    oordered_by_department character varying(255) DEFAULT ''::character varying,
    verify_spid character varying(255) DEFAULT ''::character varying,
    ordered_by_service_point character varying(255) DEFAULT ''::character varying,
    verify_deptid character varying(255) DEFAULT ''::character varying,
    ordered_by_department character varying(255) DEFAULT ''::character varying,
    pverify_spid character varying(255) DEFAULT ''::character varying,
    pordered_by_service_point character varying(255) DEFAULT ''::character varying,
    pverify_deptid character varying(255) DEFAULT ''::character varying,
    pordered_by_department character varying(255) DEFAULT ''::character varying,
    execute_date character varying(255) DEFAULT ''::character varying,
    execute_time character varying(255) DEFAULT ''::character varying,
    execute_eid character varying(255) DEFAULT ''::character varying,
    execute_spid character varying(255) DEFAULT ''::character varying,
    execute_by_service_point character varying(255) DEFAULT ''::character varying,
    execute_deptid character varying(255) DEFAULT ''::character varying,
    execute_by_department character varying(255) DEFAULT ''::character varying,
    fix_order_status_id character varying(255) DEFAULT ''::character varying,
    order_status_description character varying(255) DEFAULT ''::character varying,
    fix_item_type_id character varying(255) DEFAULT ''::character varying,
    quantity double precision DEFAULT 0,
    base_unit_id character varying(255) DEFAULT ''::character varying,
    unit_price_cost double precision DEFAULT 0,
    unit_price_sale double precision DEFAULT 0,
    linecost double precision DEFAULT 0,
    linesale double precision DEFAULT 0,
    wlinediscount double precision DEFAULT 0,
    wlinepaid double precision DEFAULT 0,
    an character varying(255) DEFAULT ''::character varying
);
CREATE INDEX idx_credit_detail_accid ON bpk_account_credit_detail USING btree (account_id);
CREATE INDEX idx_credit_detail_an ON bpk_account_credit_detail USING btree (an);
CREATE INDEX idx_credit_detail_billinggrpid ON bpk_account_credit_detail USING btree (base_billing_group_id);
CREATE INDEX idx_credit_detail_fdisch_date ON bpk_account_credit_detail USING btree (financial_discharge_date);
CREATE INDEX idx_credit_detail_hn ON bpk_account_credit_detail USING btree (hn);
CREATE INDEX idx_credit_detail_item_id ON bpk_account_credit_detail USING btree (item_id);
CREATE INDEX idx_credit_detail_order ON bpk_account_credit_detail USING btree (order_item_id);
CREATE INDEX idx_credit_detail_overify_deptid ON bpk_account_credit_detail USING btree (overify_deptid);
CREATE INDEX idx_credit_detail_overify_spid ON bpk_account_credit_detail USING btree (overify_spid);
CREATE INDEX idx_credit_detail_pverify_deptid ON bpk_account_credit_detail USING btree (pverify_deptid);
CREATE INDEX idx_credit_detail_pverify_spid ON bpk_account_credit_detail USING btree (pverify_spid);
CREATE INDEX idx_credit_detail_verify_date ON bpk_account_credit_detail USING btree (verify_date);
CREATE INDEX idx_credit_detail_verify_deptid ON bpk_account_credit_detail USING btree (verify_deptid);
CREATE INDEX idx_credit_detail_verify_spid ON bpk_account_credit_detail USING btree (verify_spid);
CREATE INDEX idx_credit_detail_visit_date ON bpk_account_credit_detail USING btree (visit_date);
CREATE INDEX idx_credit_detail_visit_id ON bpk_account_credit_detail USING btree (visit_id);
CREATE INDEX idx_credit_detail_vn ON bpk_account_credit_detail USING btree (vn);

-- DROP TABLE bpk_account_debit_detail;
CREATE TABLE bpk_account_debit_detail (
    id integer NOT NULL PRIMARY KEY,
    receipt_id character varying(255) DEFAULT ''::character varying,
    receive_date character varying(255) DEFAULT ''::character varying,
    receive_time character varying(255) DEFAULT ''::character varying,
    hn character varying(255) DEFAULT ''::character varying,
    vn character varying(255) DEFAULT ''::character varying,
    visit_id character varying(255) DEFAULT ''::character varying,
    visit_spid character varying(255) DEFAULT ''::character varying,
    visit_date character varying(255) DEFAULT ''::character varying,
    visit_time character varying(255) DEFAULT ''::character varying,
    fix_visit_type_id character varying(255) DEFAULT ''::character varying,
    base_patient_type_id character varying(255) DEFAULT ''::character varying,
    financial_discharge character varying(255) DEFAULT ''::character varying,
    financial_discharge_date character varying(255) DEFAULT ''::character varying,
    financial_discharge_time character varying(255) DEFAULT ''::character varying,
    main_doctor_id character varying(255) DEFAULT ''::character varying,
    patient_name character varying(255) DEFAULT ''::character varying,
    fix_receipt_type_id character varying(255) DEFAULT ''::character varying,
    receipt_type_description character varying(255) DEFAULT ''::character varying,
    fix_receipt_status_id character varying(255) DEFAULT ''::character varying,
    receipt_status_description character varying(255) DEFAULT ''::character varying,
    original_receipt_number character varying(255) DEFAULT ''::character varying,
    receipt_number character varying(255) DEFAULT ''::character varying,
    base_billing_group_id character varying(255) DEFAULT ''::character varying,
    billing_description character varying(255) DEFAULT ''::character varying,
    account_id character varying(255) DEFAULT ''::character varying,
    account_description character varying(255) DEFAULT ''::character varying,
    linesale double precision DEFAULT 0,
    linediscount double precision DEFAULT 0,
    wlinediscount double precision DEFAULT 0,
    linepaid double precision DEFAULT 0,
    wlinepaid double precision DEFAULT 0,
    sale double precision DEFAULT 0,
    discount double precision DEFAULT 0,
    special_discount double precision DEFAULT 0,
    decimal_discount double precision DEFAULT 0,
    paid double precision DEFAULT 0,
    plan_id character varying(255) DEFAULT ''::character varying,
    plan_code character varying(255) DEFAULT ''::character varying,
    plan_description character varying(255) DEFAULT ''::character varying,
    receive_eid character varying(255) DEFAULT ''::character varying,
    all_plan character varying(2000) DEFAULT ''::character varying
);
CREATE INDEX idx_debit_detail_receive_date ON bpk_account_debit_detail USING btree (receive_date);
CREATE INDEX idx_debit_detail_visit_id ON bpk_account_debit_detail USING btree (visit_id);
