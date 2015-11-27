SELECT 
"Stock ID",
"Stock Name",   
"Type", 
"บ้านเลขที่", 
"Item ID", 
"Item Code", 
"Item Name", 
"Big Unit", 
"Rate1", 
"Rate2", 
"QTY in Big Unit", 
"Big Unit",
"QTY ที่เหลือ in Small Unit", 
"Small Unit", 
"Quantity" "ตามบัญชี", 
"AvgCost" 
FROM 
(
    SELECT 
    CASE WHEN tmp."Rate1"<>0 AND tmp."Rate2"<>0 THEN floor(tmp."Quantity"/(tmp."Rate1"*tmp."Rate2")) ELSE 0 END "QTY in Big Unit", 
    CASE WHEN tmp."Rate1"<>0 AND tmp."Rate2"<>0 THEN CAST(tmp."Quantity" AS INTEGER)%(tmp."Rate1"*tmp."Rate2") ELSE tmp."Quantity" END "QTY ที่เหลือ in Small Unit"
    , tmp.* 
    FROM 
    (
        SELECT 
        stock.stock_id "Stock ID", 
        stock.stock_name "Stock Name", 
        stock_mgnt.item_id "Item ID", 
        COALESCE(base_drug_type.base_drug_type_id, '') "Type ID", 
        COALESCE(base_drug_type.description, '') "Type", 
        COALESCE(stock_place.place, '') "บ้านเลขที่", 
        COALESCE(item.item_code, '') "Item Code", 
        COALESCE(item.common_name, '') "Item Name", 
        bpkget_base_unit_by_id(stock_mgnt.big_unit_id) "Big Unit", 
        CASE WHEN isnumeric(stock_mgnt.mid_unit_rate) THEN CAST(stock_mgnt.mid_unit_rate AS INTEGER) ELSE 1 END "Rate1", 
        CASE WHEN isnumeric(stock_mgnt.unit_rate) THEN CAST(stock_mgnt.unit_rate AS INTEGER) ELSE 1 END "Rate2", 
        SUM(CASE WHEN isnumeric(stock_mgnt.cur_quantity) THEN CAST(stock_mgnt.cur_quantity AS FLOAT) ELSE 0 END) "Quantity", 
        bpkget_base_unit_by_id(item.base_unit_id) "Small Unit", 
        CASE WHEN isnumeric(item.unit_price_cost) THEN CAST(item.unit_price_cost AS FLOAT) ELSE 0 END "AvgCost"
        FROM 
        stock_mgnt 
        INNER JOIN stock ON stock_mgnt.stock_id = stock.stock_id AND stock.stock_id IN ('WF0027', 'WF0044', 'WB0007', 'WF0038', 'WB0032', 'WF0042', 'WB0005', 'WB0013', 'WF0039', 'WF0089', 'WF0006', 'WF0045', 'WF0094', 'WF0095', 'WF0096', 'WF0097')
        INNER JOIN item ON stock_mgnt.item_id = item.item_id  
        LEFT JOIN base_drug_type ON item.base_drug_type_id = base_drug_type.base_drug_type_id
        LEFT JOIN stock_place ON stock_mgnt.place = stock_place.stock_place_id 
        WHERE stock_mgnt.active='1'
        GROUP BY stock.stock_id, stock.stock_name, stock_mgnt.item_id, base_drug_type.base_drug_type_id, base_drug_type.description, stock_place.place
        , item.item_code, item.common_name, bpkget_base_unit_by_id(stock_mgnt.big_unit_id), 
        CASE WHEN isnumeric(stock_mgnt.mid_unit_rate) THEN CAST(stock_mgnt.mid_unit_rate AS INTEGER) ELSE 1 END, 
        CASE WHEN isnumeric(stock_mgnt.unit_rate) THEN CAST(stock_mgnt.unit_rate AS INTEGER) ELSE 1 END, 
        bpkget_base_unit_by_id(item.base_unit_id), 
        CASE WHEN isnumeric(item.unit_price_cost) THEN CAST(item.unit_price_cost AS FLOAT) ELSE 0 END
    ) tmp 
) tmp2 
ORDER BY tmp2."Stock ID", "Type" COLLATE "th_TH", "บ้านเลขที่" COLLATE "th_TH", "Item Name" COLLATE "th_TH"
