INSERT INTO bpk_stock_mgnt_his(begin_date, stock_mgnt_id, item_id, stock_id, lot_number, lot_number_id, quantity, small_unit_id, unit_lot_cost, unit_avg_cost)
SELECT Cast(CURRENT_DATE AS VARCHAR(10)), stock_mgnt.stock_mgnt_id, stock_mgnt.item_id, stock_mgnt.stock_id, stock_mgnt.lot_number, stock_mgnt.lot_number_id,  
(CASE WHEN isnumeric(stock_mgnt.cur_quantity) THEN CAST(stock_mgnt.cur_quantity AS FLOAT) ELSE 0 END) quantity, 
stock_mgnt.small_unit_id, 
(CASE WHEN isnumeric(stock_mgnt.unit_price) THEN CAST(stock_mgnt.unit_price AS FLOAT) ELSE 0 END) unit_lot_cost, 
(CASE WHEN isnumeric(item.unit_price_cost) THEN CAST(item.unit_price_cost AS FLOAT) ELSE 0 END) unit_avg_cost
FROM 
stock_mgnt 
INNER JOIN item ON stock_mgnt.item_id=item.item_id 
INNER JOIN stock ON stock_mgnt.stock_id=stock.stock_id 
WHERE stock_mgnt.active='1' 
ORDER BY stock_mgnt.stock_id, item_id, stock_mgnt_id;

