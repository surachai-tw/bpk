SELECT "Code", "Item Name", "Billing Group", "ต้นทุน", "ราคาขาย OPD", "แก้ราคาได้ขณะสั่ง", 
COALESCE(Max(verify_date), '') AS "สั่งล่าสุดเมื่อ" 
FROM 
(
    SELECT item_id, item_code "Code", common_name AS "Item Name", base_billing_group.description_th "Billing Group", 
        CAST(item.unit_price_cost AS FLOAT) "ต้นทุน", CAST(replace(COALESCE(get_last_item_price(item_id, '1'), '0'), ',', '') AS FLOAT)::FLOAT "ราคาขาย OPD", 
        CASE WHEN item.edit_price='1' THEN 'YES' ELSE 'NO' END "แก้ราคาได้ขณะสั่ง"
    FROM item 
    LEFT JOIN base_billing_group ON item.base_billing_group_opd_id=base_billing_group.base_billing_group_id 
    WHERE item.active='1' 
    AND item.hospital_item='1'
) tmp
LEFT JOIN order_item ON tmp.item_id=order_item.item_id 
WHERE tmp."ต้นทุน">=tmp."ราคาขาย OPD"
GROUP BY tmp.item_id, "Code", "Item Name", "Billing Group", "ต้นทุน", "ราคาขาย OPD", "แก้ราคาได้ขณะสั่ง"
ORDER BY "Billing Group" COLLATE "th_TH", "Item Name" COLLATE "th_TH"
