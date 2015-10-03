DELETE FROM bpk_stock_mgnt_his WHERE id IN 
(
    SELECT Min(id) 
    FROM bpk_stock_mgnt_his 
    WHERE quantity=0 
    AND begin_date='$P!{FromDate}'
    GROUP BY begin_date, item_id, stock_id HAVING Count(id)>2 
);
