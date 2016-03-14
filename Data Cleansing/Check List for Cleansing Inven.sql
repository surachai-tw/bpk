-- 1. No expire
-- ��¡�÷���ըӹǹ��������� ��������ѹ������� �� lot number �������� Expire ���֧����� Expire �������Ѵ stock �͡仡�͹ Lot ���� 
-- ��¡������ҹ�� �Ҩ����� Lot �ѧ����� �ջѭ�����١�Ѵ Stock ���к� ��Ѻ仵Ѵ Stock Lot ������������ �����ѹ������� 
-- �ҡ��û�Ъ�� �դ��������� ������ Expire date 31/10/2015
-- ��ѧ Update ���� �Ҩ�з�����բ����� ��¡���Թ������������� �ҡ��� 
SELECT stock.stock_id "Stock Code", stock.stock_name "Stock Name", item.item_code "Item Code", item.common_name "Item Name", COALESCE(lot_number, '') "LOT", tmp.cur_quantity "Quantity", COALESCE(base_unit.description_th, '') "˹��¢��"
FROM 
(
    SELECT * FROM stock_mgnt WHERE CAST(cur_quantity AS FLOAT)<>0 AND (expire_date='' OR expire_date IS NULL)
) tmp 
INNER JOIN stock ON tmp.stock_id=stock.stock_id 
INNER JOIN item ON tmp.item_id = item.item_id AND item.active='1' 
LEFT JOIN base_unit ON item.base_unit_id=base_unit.base_unit_id 
ORDER BY stock.stock_name COLLATE "th_TH", item.common_name COLLATE "th_TH", COALESCE(lot_number, '') COLLATE "th_TH"

-- 2. Item code duplicated
-- item_code ��� UNIQUE
-- ��¡���Թ��ҷء��Դ ��ͧ�� Item Code ��е�ͧ UNIQUE 
-- �������¡�ê�Դ��� �Ҩ������Դ�����������˹��§ҹ�Դ��Ҵ�� 
SELECT * FROM 
(
    SELECT DISTINCT tmp.*, main_item.common_name "Item Name", bpkget_item_type_by_id(main_item.fix_item_type_id) "Item Type", 
    (SELECT SUM(CAST(COALESCE(stock_mgnt.cur_quantity, '0'::VARCHAR(255)) AS FLOAT)) FROM stock_mgnt INNER JOIN stock ON stock_mgnt.stock_id=stock.stock_id INNER JOIN item ON stock_mgnt.item_id=item.item_id AND item.active='1' AND item.item_id=main_item.item_id) "Quantity", 
    (SELECT array_to_string(array(SELECT DISTINCT stock_name FROM item 
                                      INNER JOIN stock_mgnt ON item.item_id = stock_mgnt.item_id AND item.active='1' AND CAST(stock_mgnt.cur_quantity AS FLOAT)>0 AND item.item_id=main_item.item_id
                                      INNER JOIN stock ON stock_mgnt.stock_id = stock.stock_id 
                                  ), ',')) "Stock"
    FROM 
    (
        SELECT item_code "Item Code" FROM item WHERE active='1' AND fix_item_type_id IN 
            (SELECT DISTINCT item.fix_item_type_id FROM item INNER JOIN stock_mgnt ON item.item_id=stock_mgnt.item_id AND item.active='1') 
        GROUP BY item_code HAVING Count(DISTINCT item_id)>1 
    ) tmp 
    INNER JOIN item main_item ON tmp."Item Code" = main_item.item_code AND main_item.fix_item_type_id IN (SELECT DISTINCT item.fix_item_type_id FROM item INNER JOIN stock_mgnt ON item.item_id=stock_mgnt.item_id AND item.active='1')
    AND main_item.active='1' 
) tmp2 
WHERE tmp2."Stock"<>'' 
ORDER BY "Item Code", "Item Name"   

-- 3. Item name duplicated
-- item_name ��� UNIQUE
-- ��¡���Թ��ҷء��Դ ��ͧ�� Item Name ��е�ͧ UNIQUE 
-- �������¡�ê�Դ��� �Ҩ������Դ�����������˹��§ҹ�Դ��Ҵ�� 
SELECT * FROM 
(
    SELECT DISTINCT main_item.item_code "Item Code", main_item.common_name "Item Name", bpkget_item_type_by_id(main_item.fix_item_type_id) "Item Type", 
    COALESCE((SELECT SUM(CAST(COALESCE(stock_mgnt.cur_quantity, '0'::VARCHAR(255)) AS FLOAT)) FROM stock_mgnt INNER JOIN stock ON stock_mgnt.stock_id=stock.stock_id INNER JOIN item ON stock_mgnt.item_id=item.item_id AND item.active='1' AND item.item_id=main_item.item_id), 0) "Quantity", 
    (SELECT array_to_string(array(SELECT DISTINCT stock_name FROM item 
                                      INNER JOIN stock_mgnt ON item.item_id = stock_mgnt.item_id AND item.active='1' AND CAST(stock_mgnt.cur_quantity AS FLOAT)>0 AND item.item_id=main_item.item_id
                                      INNER JOIN stock ON stock_mgnt.stock_id = stock.stock_id 
                                  ), ',')) "Stock"
    FROM 
    (
        SELECT common_name "Item Name" FROM item WHERE active='1' AND fix_item_type_id IN 
            (SELECT DISTINCT item.fix_item_type_id FROM item INNER JOIN stock_mgnt ON item.item_id=stock_mgnt.item_id AND item.active='1') 
        GROUP BY common_name HAVING Count(DISTINCT item_id)>1 
    ) tmp 
    INNER JOIN item main_item ON tmp."Item Name" = main_item.common_name AND main_item.fix_item_type_id IN (SELECT DISTINCT item.fix_item_type_id FROM item INNER JOIN stock_mgnt ON item.item_id=stock_mgnt.item_id AND item.active='1')
    AND main_item.active='1' 
) tmp2 
ORDER BY "Item Name", "Item Code"  

-- 4. ˹��¢�� �Ѻ ˹������¢ͧ Stock ���ç�ѹ 
SELECT tmp.stock_id "Stock Code", tmp.stock_name "Stock Name", tmp.item_code "Item Code", tmp.common_name "Item Name", lot_number "LOT", bpkget_base_unit_by_id(tmp.base_unit_id) "Base Unit Master", bpkget_base_unit_by_id(tmp.small_unit_id) "Small Unit" FROM 
(
    SELECT DISTINCT stock.stock_id, stock.stock_name, item.item_code, item.common_name, stock_mgnt.lot_number, COALESCE(item.base_unit_id, '') base_unit_id, COALESCE(stock_mgnt.small_unit_id, '') small_unit_id 
    FROM 
    item 
    INNER JOIN stock_mgnt ON item.item_id = stock_mgnt.item_id 
    INNER JOIN stock ON stock_mgnt.stock_id = stock.stock_id 
    WHERE item.active='1'  
) tmp
WHERE tmp.base_unit_id<>tmp.small_unit_id 
ORDER BY tmp.stock_name COLLATE "th_TH", tmp.common_name COLLATE "th_TH", tmp.lot_number COLLATE "th_TH"

-- 5. �ա���红���������� ����� Stock Id �����ԧ��к� 
-- �Ҩ����ռŵ��˹�Ҩ͡�÷ӧҹ ���Ҩ�ռŵ�� Report �ͧ�к����֧�������·ҧ site �ͧ
SELECT * FROM 
(
    SELECT 
    DISTINCT stock_mgnt.stock_id, stock.stock_name, item.common_name, stock_mgnt.cur_quantity 
    FROM 
    stock_mgnt 
    LEFT JOIN stock ON stock_mgnt.stock_id = stock.stock_id 
    LEFT JOIN item ON stock_mgnt.item_id = item.item_id 
) tmp 
WHERE tmp.stock_name IS NULL OR tmp.common_name IS NULL 
ORDER BY stock_id

-- 6. Mid unit rate ��������Ţ --> ������� 1 
SELECT stock_mgnt.stock_id "Stock Code", stock.stock_name "Stock Name", item.item_code "Item Code", item.common_name "Item Name", CAST(stock_mgnt.cur_quantity AS FLOAT) "Quantity"
FROM stock_mgnt 
        INNER JOIN item ON stock_mgnt.item_id=item.item_id AND item.active='1' 
        INNER JOIN stock ON stock_mgnt.stock_id=stock.stock_id 
WHERE stock_mgnt.active='1' AND NOT isnumeric(mid_unit_rate) AND isnumeric(stock_mgnt.cur_quantity)
ORDER BY "Stock Name", "Item Name"

-- 7. Unit rate ��������Ţ --> ������� 1
SELECT stock_mgnt.stock_id "Stock Code", stock.stock_name "Stock Name", item.item_code "Item Code", item.common_name "Item Name", CAST(stock_mgnt.cur_quantity AS FLOAT) "Quantity"
FROM stock_mgnt 
        INNER JOIN item ON stock_mgnt.item_id=item.item_id AND item.active='1' 
        INNER JOIN stock ON stock_mgnt.stock_id=stock.stock_id 
WHERE stock_mgnt.active='1' AND NOT isnumeric(unit_rate) AND isnumeric(stock_mgnt.cur_quantity)
ORDER BY "Stock Name", "Item Name"

-- 8. ˹����˭� �Ѻ ˹������¢ͧ Stock ��˹������ǡѹ �袹Ҵ��è� ����� 1:1
SELECT tmp.stock_id "Stock Code", tmp.stock_name "Stock Name", tmp.item_code "Item Code", tmp.common_name "Item Name", lot_number "LOT", bpkget_base_unit_by_id(tmp.big_unit_id) "Big Unit", mid_unit_rate, unit_rate, bpkget_base_unit_by_id(tmp.small_unit_id) "Small Unit" FROM 
(
    SELECT DISTINCT stock.stock_id, stock.stock_name, item.item_code, item.common_name, stock_mgnt.lot_number, COALESCE(stock_mgnt.big_unit_id, '') big_unit_id, mid_unit_rate, unit_rate, COALESCE(stock_mgnt.small_unit_id, '') small_unit_id 
    FROM 
    item 
    INNER JOIN stock_mgnt ON item.item_id = stock_mgnt.item_id 
    INNER JOIN stock ON stock_mgnt.stock_id = stock.stock_id 
    WHERE item.active='1' AND (isnumeric(mid_unit_rate) AND isnumeric(unit_rate))
    AND (mid_unit_rate IS NOT NULL) AND (unit_rate IS NOT NULL)    
) tmp
WHERE tmp.big_unit_id=tmp.small_unit_id -- AND CAST(mid_unit_rate AS FLOAT)<>CAST(unit_rate AS FLOAT) 
ORDER BY tmp.stock_name COLLATE "th_TH", tmp.common_name COLLATE "th_TH", tmp.lot_number COLLATE "th_TH"

-- 9. item ����������͹��� ���� �������ҹ�����ҹҹ ���� �������� Store ����� 
-- ��õ�Ǩ�ͺ����������͹��� ���٨ҡ stock card 
-- SELECT * FROM tmp_chk_item_last_movement
-- DROP TABLE tmp_chk_item_last_movement;
SELECT main_item.item_id, main_item.item_code "Item Code", main_item.common_name "Item Name", bpkget_hospital_item_by_id(hospital_item) "Type in hospital", 
COALESCE(max(update_date), '') "Last movement", 
(SELECT array_to_string(array(SELECT DISTINCT stock_name FROM item 
                                      INNER JOIN stock_mgnt ON item.item_id = stock_mgnt.item_id AND item.active='1' AND item.item_id=main_item.item_id
                                      INNER JOIN stock ON stock_mgnt.stock_id = stock.stock_id 
                                  ), ',')) "Stock" 
INTO tmp_chk_item_last_movement 
FROM item main_item 
LEFT JOIN stock_card ON stock_card.item_id=main_item.item_id 
WHERE main_item.active='1' AND main_item.fix_item_type_id IN 
(SELECT DISTINCT item.fix_item_type_id FROM item INNER JOIN stock_mgnt ON item.item_id=stock_mgnt.item_id AND item.active='1') 
GROUP BY main_item.item_id, main_item.item_code, main_item.common_name, hospital_item
ORDER BY "Item Name";

DROP TABLE tmp_chk_item_last_order; 
SELECT main_item.item_id , main_item.item_code "Item Code", main_item.common_name "Item Name", bpkget_hospital_item_by_id(hospital_item) "Type in hospital", 
COALESCE(max(verify_date), '') "Last order", 
(SELECT array_to_string(array(SELECT DISTINCT stock_name FROM item 
                                      INNER JOIN stock_mgnt ON item.item_id = stock_mgnt.item_id AND item.active='1' AND item.item_id=main_item.item_id
                                      INNER JOIN stock ON stock_mgnt.stock_id = stock.stock_id 
                                  ), ',')) "Stock" 
INTO tmp_chk_item_last_order 
FROM item main_item 
LEFT JOIN order_item ON order_item.item_id=main_item.item_id 
WHERE main_item.active='1' 
GROUP BY main_item.item_id, main_item.item_code, main_item.common_name, hospital_item
ORDER BY "Item Name";

SELECT DISTINCT tmp.item_id, tmp_chk_item_last_movement.*, tmp_chk_item_last_order.*  
FROM 
(
    SELECT item_id FROM tmp_chk_item_last_movement UNION 
    SELECT item_id FROM tmp_chk_item_last_order 
) tmp
LEFT JOIN tmp_chk_item_last_movement ON tmp.item_id = tmp_chk_item_last_movement.item_id 
LEFT JOIN tmp_chk_item_last_order ON tmp.item_id = tmp_chk_item_last_order.item_id;

SELECT tmp3.*, 
COALESCE((SELECT SUM(CAST(cur_quantity AS FLOAT)) FROM stock_mgnt WHERE stock_mgnt.item_id=tmp3.item_id AND isnumeric(stock_mgnt.cur_quantity)), '0') AS QTY, 
item.unit_price_cost  
FROM 
(
    SELECT bpkget_item_type_by_id((SELECT fix_item_type_id FROM item WHERE item.item_id=tmp2.item_id)) imed_type, * FROM 
    (
        SELECT DISTINCT tmp_chk_item_last_movement.*, tmp_chk_item_last_order."Last order"  
        FROM 
        (
            SELECT item_id FROM tmp_chk_item_last_movement UNION 
            SELECT item_id FROM tmp_chk_item_last_order 
        ) tmp
        LEFT JOIN tmp_chk_item_last_movement ON tmp.item_id = tmp_chk_item_last_movement.item_id 
        LEFT JOIN tmp_chk_item_last_order ON tmp.item_id = tmp_chk_item_last_order.item_id
    ) tmp2
    WHERE ((tmp2."Last order" IS NULL OR tmp2."Last order"='') AND (tmp2."Last movement" IS NULL OR tmp2."Last movement"='')) 
    OR (tmp2."Last order" < tmp2."Last movement" AND tmp2."Last movement"<='2015-02-16')
    OR (tmp2."Last order" > tmp2."Last movement" AND tmp2."Last order"<='2015-02-16')
) tmp3
INNER JOIN item ON tmp3.item_id=item.item_id 
ORDER BY tmp3.imed_type COLLATE "th_TH", "Item Name" COLLATE "th_TH"


SELECT tmp3.*, 
stock.stock_id, 
stock.stock_name, 
SUM(CAST(cur_quantity AS FLOAT)) AS QTY, 
item.unit_price_cost  
FROM 
(
    SELECT bpkget_item_type_by_id((SELECT fix_item_type_id FROM item WHERE item.item_id=tmp2.item_id)) imed_type, item_id, "Item Code", "Item Name", "Type in hospital", "Last movement", "Last order" FROM 
    (
        SELECT DISTINCT tmp_chk_item_last_movement.*, tmp_chk_item_last_order."Last order"  
        FROM 
        (
            SELECT item_id FROM tmp_chk_item_last_movement UNION 
            SELECT item_id FROM tmp_chk_item_last_order 
        ) tmp
        LEFT JOIN tmp_chk_item_last_movement ON tmp.item_id = tmp_chk_item_last_movement.item_id 
        LEFT JOIN tmp_chk_item_last_order ON tmp.item_id = tmp_chk_item_last_order.item_id
    ) tmp2
    WHERE ((tmp2."Last order" IS NULL OR tmp2."Last order"='') AND (tmp2."Last movement" IS NULL OR tmp2."Last movement"='')) 
    OR (tmp2."Last order" < tmp2."Last movement" AND tmp2."Last movement"<='2015-02-16')
    OR (tmp2."Last order" > tmp2."Last movement" AND tmp2."Last order"<='2015-02-16')
) tmp3
INNER JOIN item ON tmp3.item_id=item.item_id 
INNER JOIN stock_mgnt ON item.item_id=stock_mgnt.item_id AND stock_mgnt.active='1' AND isnumeric(stock_mgnt.cur_quantity)
INNER JOIN stock ON stock_mgnt.stock_id = stock.stock_id AND stock.stock_id IN ('WB0007', 'WF0038', 'WF0039', 'WF0077', 'WF0089')
GROUP BY imed_type, tmp3.item_id, "Item Code", "Item Name", "Type in hospital", "Last movement", "Last order", stock.stock_id, stock.stock_name, item.unit_price_cost 
ORDER BY tmp3.imed_type COLLATE "th_TH", "Item Name" COLLATE "th_TH"


-- 10. Lot �������ҡ ��ФҴ��Ҩ�����ռŵ�͡�÷ӧҹ���� ������¢�������������ǹ��� �Ҩ���׺������ IT ��鹷ҧ˹�Ҩ������ 
-- ����Ŵ��������к���������� ��л�ͧ�ѹ������ user �� Lot �ѧ���������ҹ�ա ��Ъ�����������ͧ Performance �к� ���ӧҹ�����Ǣ��
SELECT * FROM 
(
    SELECT 
    mainitem.item_id, 
    mainitem.item_code "Item Code", 
    mainitem.common_name "Item Name", 
    COALESCE(mainmgnt.lot_number, '') "LOT", 
    COALESCE(mainmgnt.expire_date, '') "Expire Date", 
    Count(mainmgnt.stock_mgnt_id) "Count Record Lot", 
    (SELECT SUM(CAST(cur_quantity AS FLOAT)) FROM stock_mgnt WHERE item_id=mainitem.item_id AND lot_number=mainmgnt.lot_number) "Quantity", 
    bpkget_base_unit_by_id(mainitem.base_unit_id) "Base Unit"
    FROM stock_mgnt mainmgnt
    INNER JOIN item mainitem ON mainmgnt.item_id = mainitem.item_id AND mainitem.active='1' 
    WHERE mainmgnt.stock_id IN (SELECT stock_id FROM stock)
    GROUP BY mainitem.item_id, mainitem.item_code, mainitem.common_name, mainmgnt.lot_number, mainmgnt.expire_date 
) tmp 
ORDER BY tmp."Item Name" COLLATE "th_TH", tmp."Quantity" DESC, "Count Record Lot" DESC, "Expire Date" DESC;

-- 11. Setup ��觫��� ���ç�Ѻ Master �ͧ Item 
-- ˹������� �ͧ 2 ˹�Ҩ� ���ç�ѹ 
SELECT * FROM 
(
    SELECT 
        item.item_code "Item Code", 
        item.common_name "Item Name", 
        stock_setup_order.item_trade_name "Trade Name", 
        distributor.distributor_name "Vender Name", 
        CASE WHEN stock_setup_order.active='1' THEN 'Active' ELSE 'In-Active' END "Active Status", 
        stock_setup_order_detail.active_date "�ռŵ�����ѹ���", 
        bpkget_base_unit_by_id(stock_setup_order_detail.big_unit_id) "˹����˭� Setup��觫���", 
        stock_setup_order_detail.mid_unit_rate, 
        stock_setup_order_detail.unit_rate, 
        bpkget_base_unit_by_id(stock_setup_order_detail.small_unit_id) "˹������� Setup��觫���", 
        bpkget_base_unit_by_id(item.base_unit_id) "˹������� Master" 
    FROM 
    stock_setup_order 
    INNER JOIN item ON stock_setup_order.item_id = item.item_id AND item.active='1' 
    INNER JOIN stock_setup_order_detail ON stock_setup_order.stock_setup_order_id = stock_setup_order_detail.stock_setup_order_id 
    LEFT JOIN distributor ON stock_setup_order.distributor_id = distributor.distributor_id 
) tmp 
WHERE tmp."˹������� Setup��觫���"<>tmp."˹������� Master"
ORDER BY tmp."Item Name" COLLATE "th_TH", tmp."Active Status", tmp."�ռŵ�����ѹ���"


item ����ѧ�����˹���

��ҧ Stock Card ����Թ 18 ��͹  
��ҧ Lot number ����Թ 18 ��͹  

item code �����¢�Ҵ ��è� (Pack size) ��ͧ�� 2 ˹��� ����� 2 �дѺ --> ͹حҵ��� ���ҡ���� 1 Pack size �� ��� Vendor ��˹������µ�ͧ�ç�ѹ
�������硷����ǹ�ͧ�Ѵ���� ���� �óշ�����������

��ǹ����û�Ѻ��ا���;Ѳ�����������

*** �����˹�Ҩͷ�����������ء��ѧ 

*** Update unit_price cost �� avg ����Ѻ��� qty=0

*** ����ѵԡ������¹�ع 

-- �ѹ�����ҷ���ش� movement 
--  2013-12-15 

*** �ͧ�ҡ��� ��ͧ������ҡ��� �����������ѧ Own (Code ���ǡѹ �������ǡѹ)

Cost �繤����ҧ


-- SUM(CAST(COALESCE(qty, '0') AS FLOAT))
SELECT qty FROM stock_card 
WHERE item_id='SUP3000652' AND stock_id='WB0007' 
AND update_date BETWEEN '2015-08-05' AND '2015-08-20'

-----------------------------------------------------------------------

1. �����ŷ�� Sense ��+�ä ����ʴ������� (Alert) ��� �͹����� Sticker ����ʴ����ᾷ�� ���Ѫ ��� 
2. DI ��������� �ѭ�Ҥ�� ��Һ����觾������ �� � MAR, Sticker �� 
3. Current Medication --> �ʴ��ҷ�褹�������� ��ҡ����ʴ��˹�Ҩ� OPD (Med Reconcile)
4. Max Dose �ѧ����� 
5. Scan Med reconcile --> ����� 3 ������ͧ�Ӣ�� 5 
6. �к���͡ �������








