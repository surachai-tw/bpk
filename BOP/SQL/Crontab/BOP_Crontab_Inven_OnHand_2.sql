INSERT INTO bpk_stock_mgnt_his(begin_date, stock_mgnt_id, item_id, stock_id, lot_number, lot_number_id, quantity, small_unit_id, unit_lot_cost, unit_avg_cost)
SELECT begin_date, stock_mgnt_id, item_id, stock_id, lot_number, lot_number_id, quantity, small_unit_id, unit_lot_cost, unit_avg_cost 
FROM bpk_stock_mgnt_his_working 
ORDER BY id;

