SELECT * FROM bpk_stock_movement_summary

SELECT dbo.fBpkGetBpkNameById(bpk_id) Site, count(id) Cnt FROM bpk_stock_movement_summary GROUP BY bpk_id 

SELECT dbo.fBpkGetBpkNameById(bpk_id) Site, movement_type, count(id) Cnt 
FROM bpk_stock_movement_summary 
GROUP BY bpk_id, movement_type
ORDER BY bpk_id, movement_type

SELECT dbo.fBpkGetBpkNameById(bpk_id) [Site], movement_type [Movement Type], SUM(summary_cost) [Summary Cost] 
FROM bpk_stock_movement_summary 
WHERE movement_date BETWEEN '2015-11-01' AND '2015-11-01'
GROUP BY bpk_id, movement_type
ORDER BY bpk_id, movement_type

SELECT DISTINCT movement_type FROM bpk_stock_movement_summary

SELECT 
DISTINCT 
dbo.fBpkGetBpkNameById(bpk_id) [Site], 
isnull((SELECT SUM(summary_cost) FROM bpk_stock_movement_summary WHERE movement_date BETWEEN '2015-01-01' AND '2015-01-31' AND movement_type IN ('ADJ') AND bpk_id=main.bpk_id), 0) [ADJ],  
isnull((SELECT SUM(summary_cost) FROM bpk_stock_movement_summary WHERE movement_date BETWEEN '2015-01-01' AND '2015-01-31' AND movement_type IN ('RCVPUR') AND bpk_id=main.bpk_id), 0) [RCVPUR],  
isnull((SELECT SUM(summary_cost) FROM bpk_stock_movement_summary WHERE movement_date BETWEEN '2015-01-01' AND '2015-01-31' AND movement_type IN ('ISSPTNT') AND bpk_id=main.bpk_id), 0) [ISSPTNT],  
isnull((SELECT SUM(summary_cost) FROM bpk_stock_movement_summary WHERE movement_date BETWEEN '2015-01-01' AND '2015-01-31' AND movement_type IN ('ISSDEPT') AND bpk_id=main.bpk_id), 0) [ISSDEPT],  
isnull((SELECT SUM(summary_cost) FROM bpk_stock_movement_summary WHERE movement_date BETWEEN '2015-01-01' AND '2015-01-31' AND movement_type IN ('TRN') AND bpk_id=main.bpk_id), 0) [TRN],  
isnull((SELECT SUM(summary_cost) FROM bpk_stock_movement_summary WHERE movement_date BETWEEN '2015-01-01' AND '2015-01-31' AND movement_type IN ('RCVTRN') AND bpk_id=main.bpk_id), 0) [RCVTRN],  
isnull((SELECT SUM(summary_cost) FROM bpk_stock_movement_summary WHERE movement_date BETWEEN '2015-01-01' AND '2015-01-31' AND movement_type IN ('RCVOTH') AND bpk_id=main.bpk_id), 0) [RCVOTH],  
isnull((SELECT SUM(summary_cost) FROM bpk_stock_movement_summary WHERE movement_date BETWEEN '2015-01-01' AND '2015-01-31' AND movement_type IN ('ISSOTH') AND bpk_id=main.bpk_id), 0) ISSOTH,  
isnull((SELECT SUM(summary_cost) FROM bpk_stock_movement_summary WHERE movement_date BETWEEN '2015-01-01' AND '2015-01-31' AND movement_type IN ('RCVMIX') AND bpk_id=main.bpk_id), 0) [RCVMIX],  
isnull((SELECT SUM(summary_cost) FROM bpk_stock_movement_summary WHERE movement_date BETWEEN '2015-01-01' AND '2015-01-31' AND movement_type IN ('RCVMIX') AND bpk_id=main.bpk_id), 0) [ISSMIX]  
FROM bpk_stock_movement_summary main
WHERE movement_date BETWEEN '2015-01-01' AND '2015-01-31' 
GROUP BY bpk_id, movement_type

CASE WHEN movement_type='ISSPTNT' THEN SUM(summary_cost) ELSE 0 END [ISSPTNT], 
CASE WHEN movement_type='ISSDEPT' THEN SUM(summary_cost) ELSE 0 END [ISSDEPT]

