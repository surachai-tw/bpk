CREATE TABLE bpk_stock_mgnt_his_working 
(
    begin_date VARCHAR(255) NOT NULL,  
    id INTEGER PRIMARY KEY, 
    stock_mgnt_id VARCHAR(255) DEFAULT '', 
    item_id VARCHAR(255) DEFAULT '', 
    stock_id VARCHAR(255) DEFAULT '', 
    lot_number VARCHAR(255) DEFAULT '', 
    lot_number_id VARCHAR(255) DEFAULT '', 
    quantity FLOAT DEFAULT 0, 
    small_unit_id VARCHAR(255) DEFAULT '', 
    unit_lot_cost FLOAT DEFAULT 0, 
    unit_avg_cost FLOAT DEFAULT 0
);
CREATE INDEX idx_stock_mgnt_his_wk_begin_date ON bpk_stock_mgnt_his_working(begin_date);
CREATE INDEX idx_stock_mgnt_his_wk_stock_mgnt_id ON bpk_stock_mgnt_his_working(stock_mgnt_id);
CREATE INDEX idx_stock_mgnt_his_wk_item_id ON bpk_stock_mgnt_his_working(item_id);
CREATE INDEX idx_stock_mgnt_his_wk_wk_stock_id ON bpk_stock_mgnt_his_working(stock_id);
CREATE INDEX idx_stock_mgnt_his_wk_lot_number ON bpk_stock_mgnt_his_working(lot_number);
CREATE INDEX idx_stock_mgnt_his_wk_lot_number_id ON bpk_stock_mgnt_his_working(lot_number_id);

DROP TABLE bpk_stock_mgnt_his;
-- For snapshot data inventory at 00:00 
CREATE TABLE bpk_stock_mgnt_his 
(
    begin_date VARCHAR(255) NOT NULL,  
    id INTEGER PRIMARY KEY, 
    stock_mgnt_id VARCHAR(255) DEFAULT '', 
    item_id VARCHAR(255) DEFAULT '', 
    stock_id VARCHAR(255) DEFAULT '', 
    lot_number VARCHAR(255) DEFAULT '', 
    lot_number_id VARCHAR(255) DEFAULT '', 
    quantity FLOAT DEFAULT 0, 
    small_unit_id VARCHAR(255) DEFAULT '', 
    unit_lot_cost FLOAT DEFAULT 0, 
    unit_avg_cost FLOAT DEFAULT 0
);
CREATE INDEX idx_stock_mgnt_his_begin_date ON bpk_stock_mgnt_his(begin_date);
CREATE INDEX idx_stock_mgnt_his_stock_mgnt_id ON bpk_stock_mgnt_his(stock_mgnt_id);
CREATE INDEX idx_stock_mgnt_his_item_id ON bpk_stock_mgnt_his(item_id);
CREATE INDEX idx_stock_mgnt_his_stock_id ON bpk_stock_mgnt_his(stock_id);
CREATE INDEX idx_stock_mgnt_his_lot_number ON bpk_stock_mgnt_his(lot_number);
CREATE INDEX idx_stock_mgnt_his_lot_number_id ON bpk_stock_mgnt_his(lot_number_id);

DROP TABLE bpk_stock_card_his;
CREATE TABLE bpk_stock_card_his 
(
    movement_date VARCHAR(255) NOT NULL, 
    movement_type VARCHAR(255) DEFAULT '', 
    id INTEGER PRIMARY KEY, 
    item_id VARCHAR(255) DEFAULT '', 
    stock_id VARCHAR(255) DEFAULT '', 
    lot_number VARCHAR(255) DEFAULT '', 
    lot_number_id VARCHAR(255) DEFAULT '', 
    quantity FLOAT DEFAULT 0, 
    small_unit_id VARCHAR(255) DEFAULT '', 
    unit_lot_cost FLOAT DEFAULT 0, 
    unit_avg_cost FLOAT DEFAULT 0
);
CREATE INDEX idx_stock_card_his_movement_date ON bpk_stock_card_his(movement_date);
CREATE INDEX idx_stock_card_his_movement_type ON bpk_stock_card_his(movement_type);
CREATE INDEX idx_stock_card_his_item_id ON bpk_stock_card_his(item_id);
CREATE INDEX idx_stock_card_his_stock_id ON bpk_stock_card_his(stock_id);
CREATE INDEX idx_stock_card_his_lot_number ON bpk_stock_card_his(lot_number);
CREATE INDEX idx_stock_card_his_lot_number_id ON bpk_stock_card_his(lot_number_id);

