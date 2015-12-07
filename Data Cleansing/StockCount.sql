SELECT 
"Stock ID",
"Stock Name",   
"Type", 
"��ҹ�Ţ���", 
"Item ID", 
"Item Code" "����", 
"Item Name" "��¡��", 
"Big Unit" "˹����˭�", 
"Rate1", 
'x' "Multiply", 
"Rate2", 
'' "QTY in Big Unit", 
"Big Unit",
'' "QTY �������� in Small Unit", 
"Small Unit", 
"Quantity" "����ѭ��", 
"AvgCost" "�Ҥ�/˹���", 
"Quantity"*"AvgCost" "�ӹǹ�Թ"
FROM 
(
    SELECT 
    tmp."Type", 
    COALESCE((SELECT COALESCE(stock_mgnt.place, '') FROM stock_mgnt INNER JOIN stock_place ON stock_mgnt.place=stock_place.stock_place_id WHERE stock_mgnt.active='1' AND CAST(stock_mgnt.cur_quantity AS FLOAT)>0 AND stock_mgnt.place IS NOT NULL AND stock_mgnt.place<>'' AND stock_mgnt.stock_id="Stock ID" AND stock_mgnt.item_id="Item ID" LIMIT 1), '') "��ҹ�Ţ���", 
    tmp."Stock ID", 
    tmp."Stock Name", 
    tmp."Item ID", 
    tmp."Item Code", 
    tmp."Item Name", 
    tmp."Quantity", 
    tmp."AvgCost"
    FROM 
    (
        SELECT 
        stock.stock_id "Stock ID", 
        stock.stock_name "Stock Name", 
        stock_mgnt.item_id "Item ID", 
        COALESCE(base_drug_type.base_drug_type_id, '') "Type ID", 
        COALESCE(base_drug_type.description, '') "Type", 
        COALESCE(item.item_code, '') "Item Code", 
        COALESCE(item.common_name, '') "Item Name", 
        SUM(CASE WHEN isnumeric(stock_mgnt.cur_quantity) THEN CAST(stock_mgnt.cur_quantity AS FLOAT) ELSE 0 END) "Quantity", 
        CASE WHEN isnumeric(item.unit_price_cost) THEN CAST(item.unit_price_cost AS FLOAT) ELSE 0 END "AvgCost"
        FROM 
        stock_mgnt 
        INNER JOIN stock ON stock_mgnt.stock_id = stock.stock_id AND stock.stock_id IN ('WF0027', 'WF0044', 'WB0007', 'WF0038', 'WB0032', 'WF0042', 'WB0005', 'WB0013', 'WF0039', 'WF0089', 'WF0006', 'WF0045', 'WF0094', 'WF0095', 'WF0096', 'WF0097')
        INNER JOIN item ON stock_mgnt.item_id = item.item_id AND item.active='1'  
        LEFT JOIN base_drug_type ON item.base_drug_type_id = base_drug_type.base_drug_type_id
        WHERE stock_mgnt.active='1'
        GROUP BY stock.stock_id, stock.stock_name, stock_mgnt.item_id, base_drug_type.base_drug_type_id, base_drug_type.description, 
        item.item_code, item.common_name, CASE WHEN isnumeric(item.unit_price_cost) THEN CAST(item.unit_price_cost AS FLOAT) ELSE 0 END
    ) tmp 
) tmp2 
ORDER BY tmp2."Stock ID", "Type" COLLATE "th_TH", "��ҹ�Ţ���" COLLATE "th_TH", "Item Name" COLLATE "th_TH"


SELECT * FROM 
(
    SELECT 
    tmp."Stock ID", 
    tmp."Stock Name", 
    tmp."Type", 
    COALESCE((SELECT COALESCE(stock_place.place, '') FROM stock_mgnt INNER JOIN stock_place ON stock_mgnt.place=stock_place.stock_place_id WHERE stock_mgnt.active='1' AND CAST(stock_mgnt.cur_quantity AS FLOAT)>0 AND stock_mgnt.place IS NOT NULL AND stock_mgnt.place<>'' AND stock_mgnt.stock_id="Stock ID" AND stock_mgnt.item_id="Item ID" LIMIT 1), '') "��ҹ�Ţ���", 
    tmp."Item ID", 
    tmp."Item Code" "����", 
    tmp."Item Name" "��¡��", 
    bpkget_base_unit_by_id(vw_packsize.big_unit_id) "Big Unit", 
    COALESCE(vw_packsize.mid_unit_rate, '1') "Rate1", 
    COALESCE(vw_packsize.unit_rate, '1') "Rate2", 
    tmp."Small Unit", 
    '' "Total", 
    tmp."Quantity" "����ѭ��", 
    tmp."AvgCost" "�Ҥ�/Small Unit", 
    tmp."Quantity"*tmp."AvgCost" "�ӹǹ�Թ", 
    '' "�����˵�"
    FROM 
    (
        SELECT 
        stock.stock_id "Stock ID", 
        stock.stock_name "Stock Name", 
        stock_mgnt.item_id "Item ID", 
        COALESCE(base_drug_type.base_drug_type_id, '') "Type ID", 
        COALESCE(base_drug_type.description, '') "Type", 
        COALESCE(item.item_code, '') "Item Code", 
        COALESCE(item.common_name, '') "Item Name", 
        SUM(CASE WHEN isnumeric(stock_mgnt.cur_quantity) THEN CAST(stock_mgnt.cur_quantity AS FLOAT) ELSE 0 END) "Quantity", 
        CASE WHEN isnumeric(item.unit_price_cost) THEN CAST(item.unit_price_cost AS FLOAT) ELSE 0 END "AvgCost", 
        bpkget_base_unit_by_id(item.base_unit_id) "Small Unit"
        FROM 
        stock_mgnt 
        INNER JOIN stock ON stock_mgnt.stock_id = stock.stock_id AND stock.stock_id IN ('WF0027', 'WF0044', 'WB0007', 'WF0038', 'WB0032', 'WF0042', 'WB0005', 'WB0013', 'WF0039', 'WF0089', 'WF0006', 'WF0045', 'WF0094', 'WF0095', 'WF0096', 'WF0097')
        INNER JOIN item ON stock_mgnt.item_id = item.item_id AND item.active='1'  
        LEFT JOIN base_drug_type ON item.base_drug_type_id = base_drug_type.base_drug_type_id
        WHERE stock_mgnt.active='1'
        GROUP BY stock.stock_id, stock.stock_name, stock_mgnt.item_id, base_drug_type.base_drug_type_id, base_drug_type.description, 
        item.item_code, item.common_name, CASE WHEN isnumeric(item.unit_price_cost) THEN CAST(item.unit_price_cost AS FLOAT) ELSE 0 END, 
        bpkget_base_unit_by_id(item.base_unit_id)
    ) tmp 
    LEFT JOIN vw_packsize ON tmp."Item ID" = vw_packsize.item_id 
) finalQuery 
ORDER BY "Stock ID" COLLATE "th_TH", "Type" COLLATE "th_TH", "��ҹ�Ţ���" COLLATE "th_TH", "��¡��" COLLATE "th_TH"
