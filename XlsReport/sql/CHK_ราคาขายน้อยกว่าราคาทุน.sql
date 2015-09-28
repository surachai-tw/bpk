SELECT "Code", "Item Name", "Billing Group", "�鹷ع", "�ҤҢ�� OPD", "���Ҥ��颳����", 
COALESCE(Max(verify_date), '') AS "�������ش�����" 
FROM 
(
    SELECT item_id, item_code "Code", common_name AS "Item Name", base_billing_group.description_th "Billing Group", 
        CAST(item.unit_price_cost AS FLOAT) "�鹷ع", CAST(replace(COALESCE(get_last_item_price(item_id, '1'), '0'), ',', '') AS FLOAT)::FLOAT "�ҤҢ�� OPD", 
        CASE WHEN item.edit_price='1' THEN 'YES' ELSE 'NO' END "���Ҥ��颳����"
    FROM item 
    LEFT JOIN base_billing_group ON item.base_billing_group_opd_id=base_billing_group.base_billing_group_id 
    WHERE item.active='1' 
    AND item.hospital_item='1'
) tmp
LEFT JOIN order_item ON tmp.item_id=order_item.item_id 
WHERE tmp."�鹷ع">=tmp."�ҤҢ�� OPD"
GROUP BY tmp.item_id, "Code", "Item Name", "Billing Group", "�鹷ع", "�ҤҢ�� OPD", "���Ҥ��颳����"
ORDER BY "Billing Group" COLLATE "th_TH", "Item Name" COLLATE "th_TH"
