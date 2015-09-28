SELECT * FROM 
(
    SELECT item_code "Code", common_name AS "Item Name", base_billing_group.description_th "Billing Group", 
        CAST(item.unit_price_cost AS FLOAT) "�鹷ع"
    FROM item 
    LEFT JOIN base_billing_group ON item.base_billing_group_opd_id=base_billing_group.base_billing_group_id 
    WHERE item.active='1' 
) tmp
WHERE tmp."�鹷ع"<0
ORDER BY "Billing Group" COLLATE "th_TH", "Item Name" COLLATE "th_TH"