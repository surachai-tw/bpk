INSERT INTO bpk_stock_card_his (movement_date, movement_type, item_id, stock_id, lot_number, lot_number_id, quantity, small_unit_id, unit_lot_cost, unit_avg_cost) 
SELECT 
update_date, 
'RCVPUR'::VARCHAR(255) movement_type, 
item_id, 
stock_id, 
lot_number, 
lot_number_id, 
SUM(CAST((CASE WHEN isnumeric(qty) THEN qty ELSE '0' END) AS FLOAT)) quantity,  
small_unit_id, 
CASE WHEN isnumeric(small_unit_price_cost) THEN CAST(small_unit_price_cost AS FLOAT) ELSE 0 END small_unit_price_cost, 
CASE WHEN isnumeric(avg_unit_price_cost) THEN CAST(avg_unit_price_cost AS FLOAT) ELSE 0 END avg_unit_price_cost 
FROM stock_card 
WHERE update_date=Cast(CURRENT_DATE-1 AS VARCHAR(10))
AND fix_stock_method_id IN ('+2')
GROUP BY update_date, item_id, stock_id, lot_number, lot_number_id, small_unit_id, small_unit_price_cost, avg_unit_price_cost;

INSERT INTO bpk_stock_card_his (movement_date, movement_type, item_id, stock_id, lot_number, lot_number_id, quantity, small_unit_id, unit_lot_cost, unit_avg_cost) 
SELECT 
update_date, 
'RCVOTH'::VARCHAR(255) movement_type, 
item_id, 
stock_id, 
lot_number, 
lot_number_id, 
SUM(CAST((CASE WHEN isnumeric(qty) THEN qty ELSE '0' END) AS FLOAT)) quantity,  
small_unit_id, 
CASE WHEN isnumeric(small_unit_price_cost) THEN CAST(small_unit_price_cost AS FLOAT) ELSE 0 END small_unit_price_cost, 
CASE WHEN isnumeric(avg_unit_price_cost) THEN CAST(avg_unit_price_cost AS FLOAT) ELSE 0 END avg_unit_price_cost 
FROM stock_card 
WHERE update_date=Cast(CURRENT_DATE-1 AS VARCHAR(10))
AND fix_stock_method_id LIKE '+%' AND fix_stock_method_id NOT IN ('+2')
GROUP BY update_date, item_id, stock_id, lot_number, lot_number_id, small_unit_id, small_unit_price_cost, avg_unit_price_cost;

INSERT INTO bpk_stock_card_his (movement_date, movement_type, item_id, stock_id, lot_number, lot_number_id, quantity, small_unit_id, unit_lot_cost, unit_avg_cost) 
SELECT 
update_date, 
'ISSPTNT'::VARCHAR(255) movement_type, 
item_id, 
stock_id, 
lot_number, 
lot_number_id, 
SUM(CAST((CASE WHEN isnumeric(qty) THEN qty ELSE '0' END) AS FLOAT)) quantity,  
small_unit_id, 
CASE WHEN isnumeric(small_unit_price_cost) THEN CAST(small_unit_price_cost AS FLOAT) ELSE 0 END small_unit_price_cost, 
CASE WHEN isnumeric(avg_unit_price_cost) THEN CAST(avg_unit_price_cost AS FLOAT) ELSE 0 END avg_unit_price_cost 
FROM stock_card 
WHERE update_date=Cast(CURRENT_DATE-1 AS VARCHAR(10))
AND fix_stock_method_id IN ('-5')
GROUP BY update_date, item_id, stock_id, lot_number, lot_number_id, small_unit_id, small_unit_price_cost, avg_unit_price_cost;

INSERT INTO bpk_stock_card_his (movement_date, movement_type, item_id, stock_id, lot_number, lot_number_id, quantity, small_unit_id, unit_lot_cost, unit_avg_cost) 
SELECT 
update_date, 
'ISSDEPT'::VARCHAR(255) movement_type, 
item_id, 
stock_id, 
lot_number, 
lot_number_id, 
SUM(CAST((CASE WHEN isnumeric(qty) THEN qty ELSE '0' END) AS FLOAT)) quantity,  
small_unit_id, 
CASE WHEN isnumeric(small_unit_price_cost) THEN CAST(small_unit_price_cost AS FLOAT) ELSE 0 END small_unit_price_cost, 
CASE WHEN isnumeric(avg_unit_price_cost) THEN CAST(avg_unit_price_cost AS FLOAT) ELSE 0 END avg_unit_price_cost 
FROM stock_card 
WHERE update_date=Cast(CURRENT_DATE-1 AS VARCHAR(10))
AND fix_stock_method_id IN ('-4')
GROUP BY update_date, item_id, stock_id, lot_number, lot_number_id, small_unit_id, small_unit_price_cost, avg_unit_price_cost;

INSERT INTO bpk_stock_card_his (movement_date, movement_type, item_id, stock_id, lot_number, lot_number_id, quantity, small_unit_id, unit_lot_cost, unit_avg_cost) 
SELECT 
update_date, 
'ISSOTH'::VARCHAR(255) movement_type, 
item_id, 
stock_id, 
lot_number, 
lot_number_id, 
SUM(CAST((CASE WHEN isnumeric(qty) THEN qty ELSE '0' END) AS FLOAT)) quantity,  
small_unit_id, 
CASE WHEN isnumeric(small_unit_price_cost) THEN CAST(small_unit_price_cost AS FLOAT) ELSE 0 END small_unit_price_cost, 
CASE WHEN isnumeric(avg_unit_price_cost) THEN CAST(avg_unit_price_cost AS FLOAT) ELSE 0 END avg_unit_price_cost 
FROM stock_card 
WHERE update_date=Cast(CURRENT_DATE-1 AS VARCHAR(10))
AND fix_stock_method_id LIKE '-%'  AND fix_stock_method_id NOT IN ('-4', '-5')
GROUP BY update_date, item_id, stock_id, lot_number, lot_number_id, small_unit_id, small_unit_price_cost, avg_unit_price_cost;

