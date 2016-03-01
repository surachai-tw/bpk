SELECT 9 bpk_id,'$P{dBeginDate}' begin_date,
item_id,bpkget_item_name_by_id(item_id) item_description,
stock_id, bpkget_stock_name_by_id(stock_id) stock_name, bpkget_is_consign_by_id(stock_id) is_consign, 
SUM(quantity) quantity,bpkget_base_unit_by_id(small_unit_id) small_unit_description,unit_avg_cost, 
SUM(quantity*unit_avg_cost) AS cost 
FROM bpk_stock_mgnt_his
WHERE begin_date='$P{dBeginDate}'
GROUP BY item_id, stock_id, unit_avg_cost, small_unit_id
ORDER BY stock_id, bpkget_item_name_by_id(item_id)
