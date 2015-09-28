CREATE TABLE bpk_track_delete_stock_card 
(
    id SERIAL PRIMARY KEY, 
    delete_date VARCHAR(255) DEFAULT '', 
    delete_time VARCHAR(255) DEFAULT '', 
    stock_card_id VARCHAR(255) DEFAULT '', 
    stock_mgnt_id VARCHAR(255) DEFAULT '', 
    fix_stock_method_id VARCHAR(255) DEFAULT '', 
    request_or_order_id VARCHAR(255) DEFAULT '', 
    invoice_number VARCHAR(255) DEFAULT '', 
    in_id VARCHAR(255) DEFAULT '', 
    out_id VARCHAR(255) DEFAULT '', 
    manufacturer_id VARCHAR(255) DEFAULT '', 
    stock_id VARCHAR(255) DEFAULT '', 
    item_id VARCHAR(255) DEFAULT '', 
    lot_number VARCHAR(255) DEFAULT '', 
    update_date VARCHAR(255) DEFAULT '', 
    update_time VARCHAR(255) DEFAULT '', 
    update_eid VARCHAR(255) DEFAULT '', 
    previous_qty VARCHAR(255) DEFAULT '', 
    previous_qty_lot VARCHAR(255) DEFAULT '', 
    max_qty VARCHAR(255) DEFAULT '', 
    min_qty VARCHAR(255) DEFAULT '', 
    qty VARCHAR(255) DEFAULT '', 
    free_qty VARCHAR(255) DEFAULT '', 
    update_qty VARCHAR(255) DEFAULT '', 
    update_qty_lot VARCHAR(255) DEFAULT '', 
    is_arrear VARCHAR(255) DEFAULT '', 
    arrear_qty VARCHAR(255) DEFAULT '', 
    small_unit_price_cost VARCHAR(255) DEFAULT '', 
    small_unit_price_sale VARCHAR(255) DEFAULT '', 
    small_unit_id VARCHAR(255) DEFAULT '', 
    big_unit_price_cost VARCHAR(255) DEFAULT '', 
    cost_purchase_no_discount VARCHAR(255) DEFAULT '', 
    cost_purchase VARCHAR(255) DEFAULT '', 
    discount VARCHAR(255) DEFAULT '', 
    discount_percent VARCHAR(255) DEFAULT '', 
    big_unit_id VARCHAR(255) DEFAULT '', 
    unit_rate VARCHAR(255) DEFAULT '', 
    mid_unit_rate VARCHAR(255) DEFAULT '', 
    fiscal_year VARCHAR(255) DEFAULT '', 
    comments VARCHAR(255) DEFAULT '', 
    is_transfer VARCHAR(255) DEFAULT '', 
    transfer_date_from VARCHAR(255) DEFAULT '', 
    transfer_time_from VARCHAR(255) DEFAULT '', 
    transfer_date_to VARCHAR(255) DEFAULT '', 
    transfer_time_to VARCHAR(255) DEFAULT '', 
    have_lot_analyse VARCHAR(255) DEFAULT '', 
    have_fda VARCHAR(255) DEFAULT '', 
    fda_number VARCHAR(255) DEFAULT '', 
    have_gmp VARCHAR(255) DEFAULT '', 
    gmp_number VARCHAR(255) DEFAULT '', 
    return_befor_expire_day VARCHAR(255) DEFAULT '', 
    expire_date VARCHAR(255) DEFAULT '', 
    receive_date VARCHAR(255) DEFAULT '', 
    receive_time VARCHAR(255) DEFAULT '', 
    receive_order_number VARCHAR(255) DEFAULT '', 
    update_date_time VARCHAR(255) DEFAULT '', 
    order_item_id VARCHAR(255) DEFAULT '', 
    bill_number VARCHAR(255) DEFAULT '', 
    book_number VARCHAR(255) DEFAULT '', 
    stock_dispense_number VARCHAR(255) DEFAULT '', 
    avg_unit_price_cost VARCHAR(255) DEFAULT '', 
    fix_stock_reason_adjust VARCHAR(255) DEFAULT '', 
    produce_date VARCHAR(255) DEFAULT '', 
    range_to_expire VARCHAR(255) DEFAULT '', 
    stock_exchange_id VARCHAR(255) DEFAULT '', 
    stock_request_produce_id VARCHAR(255) DEFAULT '', 
    loss_qty VARCHAR(255) DEFAULT '', 
    produce_eid VARCHAR(255) DEFAULT '', 
    stock_cssd_machine_id VARCHAR(255) DEFAULT '', 
    is_merge_po VARCHAR(255) DEFAULT '', 
    seq_machine VARCHAR(255) DEFAULT '', 
    pr_number VARCHAR(255) DEFAULT '', 
    blue_slip_number VARCHAR(255) DEFAULT '', 
    smu VARCHAR(255) DEFAULT '', 
    financial_category VARCHAR(255) DEFAULT '', 
    fund_name VARCHAR(255) DEFAULT '', 
    financial_number VARCHAR(255) DEFAULT '', 
    lot_number_id VARCHAR(255) DEFAULT '', 
    payment_date VARCHAR(255) DEFAULT '', 
    payment_time VARCHAR(255) DEFAULT '', 
    item_trade_name VARCHAR(255) DEFAULT '', 
    total_discount_per_item VARCHAR(255) DEFAULT '', 
    total_discount VARCHAR(255) DEFAULT ''
);
CREATE INDEX idx_bpk_track_delstkcd_deldate ON bpk_track_delete_stock_card(delete_date);
CREATE INDEX idx_bpk_track_delstkcd_deltime ON bpk_track_delete_stock_card(delete_time);
CREATE INDEX idx_bpk_track_delstkcd_stock_card_id ON bpk_track_delete_stock_card(stock_card_id);
CREATE INDEX idx_bpk_track_delstkcd_stock_id ON bpk_track_delete_stock_card(stock_id);
CREATE INDEX idx_bpk_track_delstkcd_item_id ON bpk_track_delete_stock_card(item_id);
CREATE INDEX idx_bpk_track_delstkcd_invoice_number ON bpk_track_delete_stock_card(invoice_number);
CREATE INDEX idx_bpk_track_delstkcd_requestororderid ON bpk_track_delete_stock_card(request_or_order_id);

CREATE OR REPLACE FUNCTION bpk_track_delete_stock_card() RETURNS TRIGGER  
LANGUAGE plpgsql 
AS '
DECLARE 
    p_stock_card_id VARCHAR(255):='''';
    rec RECORD;
BEGIN 

    INSERT INTO bpk_track_delete_stock_card (delete_date, delete_time, stock_card_id, stock_mgnt_id, fix_stock_method_id, request_or_order_id, invoice_number, in_id, out_id, manufacturer_id, stock_id, item_id, lot_number, update_date, update_time, update_eid, previous_qty, previous_qty_lot, max_qty, min_qty, qty, free_qty, update_qty, update_qty_lot, is_arrear, arrear_qty, small_unit_price_cost, small_unit_price_sale, small_unit_id, big_unit_price_cost, cost_purchase_no_discount, cost_purchase, discount, discount_percent, big_unit_id, unit_rate, mid_unit_rate, fiscal_year, comments, is_transfer, transfer_date_from, transfer_time_from, transfer_date_to, transfer_time_to, have_lot_analyse, have_fda, fda_number, have_gmp, gmp_number, return_befor_expire_day, expire_date, receive_date, receive_time, receive_order_number, update_date_time, order_item_id, bill_number, book_number, stock_dispense_number, avg_unit_price_cost, fix_stock_reason_adjust, produce_date, range_to_expire, stock_exchange_id, stock_request_produce_id, loss_qty, produce_eid, stock_cssd_machine_id, is_merge_po, seq_machine, pr_number, blue_slip_number, smu, financial_category, fund_name, financial_number, lot_number_id, payment_date, payment_time, item_trade_name, total_discount_per_item, total_discount) 
    VALUES (Cast(CURRENT_DATE AS VARCHAR(10)), Cast(CURRENT_TIME AS VARCHAR(8)), OLD.stock_card_id, OLD.stock_mgnt_id, OLD.fix_stock_method_id, OLD.request_or_order_id, OLD.invoice_number, OLD.in_id, OLD.out_id, OLD.manufacturer_id, OLD.stock_id, OLD.item_id, OLD.lot_number, OLD.update_date, OLD.update_time, OLD.update_eid, OLD.previous_qty, OLD.previous_qty_lot, OLD.max_qty, OLD.min_qty, OLD.qty, OLD.free_qty, OLD.update_qty, OLD.update_qty_lot, OLD.is_arrear, OLD.arrear_qty, OLD.small_unit_price_cost, OLD.small_unit_price_sale, OLD.small_unit_id, OLD.big_unit_price_cost, OLD.cost_purchase_no_discount, OLD.cost_purchase, OLD.discount, OLD.discount_percent, OLD.big_unit_id, OLD.unit_rate, OLD.mid_unit_rate, OLD.fiscal_year, OLD.comments, OLD.is_transfer, OLD.transfer_date_from, OLD.transfer_time_from, OLD.transfer_date_to, OLD.transfer_time_to, OLD.have_lot_analyse, OLD.have_fda, OLD.fda_number, OLD.have_gmp, OLD.gmp_number, OLD.return_befor_expire_day, OLD.expire_date, OLD.receive_date, OLD.receive_time, OLD.receive_order_number, OLD.update_date_time, OLD.order_item_id, OLD.bill_number, OLD.book_number, OLD.stock_dispense_number, OLD.avg_unit_price_cost, OLD.fix_stock_reason_adjust, OLD.produce_date, OLD.range_to_expire, OLD.stock_exchange_id, OLD.stock_request_produce_id, OLD.loss_qty, OLD.produce_eid, OLD.stock_cssd_machine_id, OLD.is_merge_po, OLD.seq_machine, OLD.pr_number, OLD.blue_slip_number, OLD.smu, OLD.financial_category, OLD.fund_name, OLD.financial_number, OLD.lot_number_id, OLD.payment_date, OLD.payment_time, OLD.item_trade_name, OLD.total_discount_per_item, OLD.total_discount);

    RETURN NEW;
END';

CREATE TRIGGER bpktrg_stock_card_track_delete 
AFTER DELETE ON stock_card 
FOR EACH ROW
EXECUTE PROCEDURE bpk_track_delete_stock_card();
