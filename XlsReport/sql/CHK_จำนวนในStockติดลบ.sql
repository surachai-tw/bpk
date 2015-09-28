SELECT "Code", "Item Name", "��ѧ", "�鹷ع", "Quantity" 
FROM 
(
    SELECT item.item_id, item.item_code "Code", common_name AS "Item Name", stock.stock_name "��ѧ", 
        CAST(item.unit_price_cost AS FLOAT) "�鹷ع", COALESCE(SUM(CAST(stock_mgnt.cur_quantity AS FLOAT)), 0) "Quantity" 
    FROM item 
    LEFT JOIN stock_mgnt ON item.item_id=stock_mgnt.item_id 
    LEFT JOIN stock ON stock_mgnt.stock_id=stock.stock_id 
    WHERE item.active='1' 
    GROUP BY item.item_id, "Code", "Item Name", "��ѧ", "�鹷ع" 
) tmp
WHERE 
tmp."Quantity" < 0
ORDER BY "Code" COLLATE "th_TH", "Item Name" COLLATE "th_TH", "��ѧ" COLLATE "th_TH"
