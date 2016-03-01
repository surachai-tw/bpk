INSERT INTO bpk_stock_movement_summary(bpk_id,movement_date,id,movement_type,summary_cost) 
SELECT 9 bpk_id, movement_date, movement_type, ABS(SUM(cost)) AS summary_cost 
FROM 
(
    SELECT movement_date, movement_type, item_id, stock_id, SUM(quantity*unit_avg_cost) AS cost 
    FROM bpk_stock_card_his 
    WHERE movement_date BETWEEN '$P{dBeginDate}' AND '$P{dBeginDate}'
    GROUP BY movement_date, movement_type, item_id, stock_id 
) tmp 
GROUP BY movement_type, movement_date
ORDER BY movement_date, movement_type;
