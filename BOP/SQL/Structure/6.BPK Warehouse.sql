--DROP TABLE bpk_account_credit_detail;
CREATE TABLE bpk_account_credit_detail
(
	bpk_id SMALLINT DEFAULT 0, 
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    order_item_id NVARCHAR(255) DEFAULT '',
    verify_date Date,
    verify_time NVARCHAR(255) DEFAULT '',
    hn NVARCHAR(255) DEFAULT '',
    vn NVARCHAR(255) DEFAULT '',
    visit_id NVARCHAR(255) DEFAULT '',
    visit_spid NVARCHAR(255) DEFAULT '',
    visit_date Date,
    visit_time NVARCHAR(255) DEFAULT '',
    fix_visit_type_id NVARCHAR(255) DEFAULT '',
    base_patient_type_id NVARCHAR(255) DEFAULT '',
    financial_discharge NVARCHAR(255) DEFAULT '',
    financial_discharge_date Date,
    financial_discharge_time NVARCHAR(255) DEFAULT '',
    main_doctor_id NVARCHAR(255) DEFAULT '',
    patient_name NVARCHAR(255) DEFAULT '',
    base_billing_group_id NVARCHAR(255) DEFAULT '',
    billing_description NVARCHAR(255) DEFAULT '',
    account_id NVARCHAR(255) DEFAULT '',
    account_description NVARCHAR(255) DEFAULT '',
    item_id NVARCHAR(255) DEFAULT '',
    item_description NVARCHAR(255) DEFAULT '',
    verify_eid NVARCHAR(255) DEFAULT '',
    overify_spid NVARCHAR(255) DEFAULT '',
    oordered_by_service_point NVARCHAR(255) DEFAULT '',
    overify_deptid NVARCHAR(255) DEFAULT '',
    oordered_by_department NVARCHAR(255) DEFAULT '',
    verify_spid NVARCHAR(255) DEFAULT '',
    ordered_by_service_point NVARCHAR(255) DEFAULT '',
    verify_deptid NVARCHAR(255) DEFAULT '',
    ordered_by_department NVARCHAR(255) DEFAULT '',
    pverify_spid NVARCHAR(255) DEFAULT '',
    pordered_by_service_point NVARCHAR(255) DEFAULT '',
    pverify_deptid NVARCHAR(255) DEFAULT '',
    pordered_by_department NVARCHAR(255) DEFAULT '',
    execute_date Date,
    execute_time NVARCHAR(255) DEFAULT '',
    execute_eid NVARCHAR(255) DEFAULT '',
    execute_spid NVARCHAR(255) DEFAULT '',
    execute_by_service_point NVARCHAR(255) DEFAULT '',
    execute_deptid NVARCHAR(255) DEFAULT '',
    execute_by_department NVARCHAR(255) DEFAULT '',
    fix_order_status_id NVARCHAR(255) DEFAULT '',
    order_status_description NVARCHAR(255) DEFAULT '',
    fix_item_type_id NVARCHAR(255) DEFAULT '',
    quantity FLOAT DEFAULT 0,
    base_unit_id NVARCHAR(255) DEFAULT '',
    unit_price_cost FLOAT DEFAULT 0,
    unit_price_sale FLOAT DEFAULT 0,
    linecost FLOAT DEFAULT 0,
    linesale FLOAT DEFAULT 0,
    wlinediscount FLOAT DEFAULT 0,
    wlinepaid FLOAT DEFAULT 0,
    an NVARCHAR(255) DEFAULT ''
);
CREATE INDEX idx_credit_detail_hn ON bpk_account_credit_detail(hn);
CREATE INDEX idx_credit_detail_vn ON bpk_account_credit_detail(vn);
CREATE INDEX idx_credit_detail_an ON bpk_account_credit_detail(an);
CREATE INDEX idx_credit_detail_visit_id ON bpk_account_credit_detail(visit_id);
CREATE INDEX idx_credit_detail_fdisch_date ON bpk_account_credit_detail(financial_discharge_date);
CREATE INDEX idx_credit_detail_order ON bpk_account_credit_detail(order_item_id);
CREATE INDEX idx_credit_detail_item_id ON bpk_account_credit_detail(item_id);
CREATE INDEX idx_credit_detail_verify_spid ON bpk_account_credit_detail(verify_spid);
CREATE INDEX idx_credit_detail_overify_spid ON bpk_account_credit_detail(overify_spid);
CREATE INDEX idx_credit_detail_pverify_spid ON bpk_account_credit_detail(pverify_spid);
CREATE INDEX idx_credit_detail_verify_deptid ON bpk_account_credit_detail(verify_deptid);
CREATE INDEX idx_credit_detail_overify_deptid ON bpk_account_credit_detail(overify_deptid);
CREATE INDEX idx_credit_detail_pverify_deptid ON bpk_account_credit_detail(pverify_deptid);
CREATE INDEX idx_credit_detail_billinggrpid ON bpk_account_credit_detail(base_billing_group_id);
CREATE INDEX idx_credit_detail_accid ON bpk_account_credit_detail(account_id);

ALTER TABLE bpk_account_credit_detail ADD base_plan_group_id NVARCHAR(255) DEFAULT '';
ALTER TABLE bpk_account_credit_detail ADD nation NVARCHAR(255) DEFAULT '';
ALTER TABLE bpk_account_credit_detail ADD patient_id NVARCHAR(255) DEFAULT '';

CREATE TABLE bpk_daily_patient_revenue
(
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
	FromDate varchar(10),
	ToDate varchar(10),
	Daily nvarchar(255),
	BPK FLOAT,
	[Growth BPK] FLOAT,
	BPK1 FLOAT,
	[Growth BPK1] FLOAT,
	BPK3 FLOAT,
	[Growth BPK3] FLOAT,
	BPK8 FLOAT,
	[Growth BPK8] FLOAT,
	BPK9 FLOAT,
	[Growth BPK9] FLOAT
);	

DROP TABLE bpk_daily_stock_mgnt_his;
CREATE TABLE bpk_daily_stock_mgnt_his 
(
	bpk_id SMALLINT DEFAULT 0, 
    begin_date VARCHAR(255) NOT NULL, 
    id BIGINT IDENTITY(1,1) PRIMARY KEY, 
    fix_item_type_description NVARCHAR(255) DEFAULT '',
    base_drug_type_description NVARCHAR(255) DEFAULT '',
    item_id NVARCHAR(255) DEFAULT '', 
    item_description NVARCHAR(255) DEFAULT '', 
    stock_id NVARCHAR(255) DEFAULT '', 
    stock_name NVARCHAR(255) DEFAULT '', 
	is_consign VARCHAR(1) DEFAULT 'N', 
    quantity FLOAT DEFAULT 0, 
    small_unit_description NVARCHAR(255) DEFAULT '', 
    unit_avg_cost FLOAT DEFAULT 0, 
	cost FLOAT DEFAULT 0 
);

DROP TABLE bpk_stock_movement_summary;
CREATE TABLE bpk_stock_movement_summary 
(
	bpk_id SMALLINT DEFAULT 0, 
    movement_date VARCHAR(255) NOT NULL, 
    id BIGINT IDENTITY(1,1) PRIMARY KEY, 
    movement_type NVARCHAR(255) DEFAULT '',
    summary_cost FLOAT DEFAULT 0
);
