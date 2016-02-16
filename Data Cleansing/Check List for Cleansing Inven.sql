-- 1. No expire
-- รายการที่มีจำนวนเหลืออยู่ แต่ไม่มีวันหมดอายุ โดย lot number ที่ไม่มี Expire ให้ดึงมาใส่ Expire เพื่อให้ตัด stock ออกไปก่อน Lot อื่นๆ 
-- รายการเหล่านี้ อาจทำให้ Lot ดังกล่าว มีปัญหาไม่ถูกคัด Stock แต่ระบบ กลับไปตัด Stock Lot ที่เข้ามาใหม่ แต่มีวันหมดอายุ 
-- จากการประชุม มีความเห็นว่า ให้ใส่ Expire date 31/10/2015
-- หลัง Update แล้ว อาจจะทำให้มีข้อมูล รายการสินค้าใกล้หมดอายุ มากขึ้น 
SELECT stock.stock_id "Stock Code", stock.stock_name "Stock Name", item.item_code "Item Code", item.common_name "Item Name", COALESCE(lot_number, '') "LOT", tmp.cur_quantity "Quantity", COALESCE(base_unit.description_th, '') "หน่วยขาย"
FROM 
(
    SELECT * FROM stock_mgnt WHERE CAST(cur_quantity AS FLOAT)<>0 AND (expire_date='' OR expire_date IS NULL)
) tmp 
INNER JOIN stock ON tmp.stock_id=stock.stock_id 
INNER JOIN item ON tmp.item_id = item.item_id AND item.active='1' 
LEFT JOIN base_unit ON item.base_unit_id=base_unit.base_unit_id 
ORDER BY stock.stock_name COLLATE "th_TH", item.common_name COLLATE "th_TH", COALESCE(lot_number, '') COLLATE "th_TH"

-- 2. Item code duplicated
-- item_code ไม่ UNIQUE
-- รายการสินค้าทุกชนิด ต้องมี Item Code และต้อง UNIQUE 
-- ถ้ามีรายการชนิดนี้ อาจทำให้เกิดการสื่อสารในหน่วยงานผิดพลาดได้ 
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
-- item_name ไม่ UNIQUE
-- รายการสินค้าทุกชนิด ต้องมี Item Name และต้อง UNIQUE 
-- ถ้ามีรายการชนิดนี้ อาจทำให้เกิดการสื่อสารในหน่วยงานผิดพลาดได้ 
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

-- 4. หน่วยขาย กับ หน่วยย่อยของ Stock ไม่ตรงกัน 
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

-- 5. มีการเก็บข้อมูลไว้แต่ ไม่มี Stock Id อยู่จริงในระบบ 
-- อาจไม่มีผลต่อหน้าจอการทำงาน แต่อาจมีผลต่อ Report ของระบบที่ดึงข้อมูลโดยทาง site เอง
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

-- 6. Mid unit rate ไม่ใช่ตัวเลข --> ทำให้เป็น 1 
SELECT stock_mgnt.stock_id "Stock Code", stock.stock_name "Stock Name", item.item_code "Item Code", item.common_name "Item Name", CAST(stock_mgnt.cur_quantity AS FLOAT) "Quantity"
FROM stock_mgnt 
        INNER JOIN item ON stock_mgnt.item_id=item.item_id AND item.active='1' 
        INNER JOIN stock ON stock_mgnt.stock_id=stock.stock_id 
WHERE stock_mgnt.active='1' AND NOT isnumeric(mid_unit_rate) AND isnumeric(stock_mgnt.cur_quantity)
ORDER BY "Stock Name", "Item Name"

-- 7. Unit rate ไม่ใช่ตัวเลข --> ทำให้เป็น 1
SELECT stock_mgnt.stock_id "Stock Code", stock.stock_name "Stock Name", item.item_code "Item Code", item.common_name "Item Name", CAST(stock_mgnt.cur_quantity AS FLOAT) "Quantity"
FROM stock_mgnt 
        INNER JOIN item ON stock_mgnt.item_id=item.item_id AND item.active='1' 
        INNER JOIN stock ON stock_mgnt.stock_id=stock.stock_id 
WHERE stock_mgnt.active='1' AND NOT isnumeric(unit_rate) AND isnumeric(stock_mgnt.cur_quantity)
ORDER BY "Stock Name", "Item Name"

-- 8. หน่วยใหญ่ กับ หน่วยย่อยของ Stock เป็นหน่วยเดียวกัน แต่ขนาดบรรจุ ไม่ใช่ 1:1
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

-- 9. item ที่ไม่เคลื่อนไหว หรือ ไม่ได้ใช้งานเป็นเวลานาน หรือ ไม่อยู่ใน Store ใดๆเลย 
-- การตรวจสอบว่าไม่เคลื่อนไหว ให้ดูจาก stock card 
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

SELECT * FROM 
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
ORDER BY tmp3.imed_type COLLATE "th_TH", "Item Name" COLLATE "th_TH"

-- 10. Lot ที่เก่ามาก และคาดว่าจะไม่มีผลต่อการทำงานแล้ว ควรย้ายข้อมูลไปไว้ที่ส่วนอื่น อาจจะสืบค้นได้โดย IT แต่ค้นทางหน้าจอไม่ได้ 
-- เพื่อลดข้อมูลในระบบที่ไม่จำเป็น และป้องกันไม่ให้ user นำ Lot ดังกล่าวมาใช้งานอีก และช่วยเพิ่มเรื่อง Performance ระบบ ให้ทำงานได้เร็วขึ้น
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

-- 11. Setup สั่งซื้อ ไม่ตรงกับ Master ของ Item 
-- หน่วยย่อย ของ 2 หน้าจอ ไม่ตรงกัน 
SELECT * FROM 
(
    SELECT 
        item.item_code "Item Code", 
        item.common_name "Item Name", 
        stock_setup_order.item_trade_name "Trade Name", 
        distributor.distributor_name "Vender Name", 
        CASE WHEN stock_setup_order.active='1' THEN 'Active' ELSE 'In-Active' END "Active Status", 
        stock_setup_order_detail.active_date "มีผลตั้งแต่วันที่", 
        bpkget_base_unit_by_id(stock_setup_order_detail.big_unit_id) "หน่วยใหญ่ Setupสั่งซื้อ", 
        stock_setup_order_detail.mid_unit_rate, 
        stock_setup_order_detail.unit_rate, 
        bpkget_base_unit_by_id(stock_setup_order_detail.small_unit_id) "หน่วยย่อย Setupสั่งซื้อ", 
        bpkget_base_unit_by_id(item.base_unit_id) "หน่วยย่อย Master" 
    FROM 
    stock_setup_order 
    INNER JOIN item ON stock_setup_order.item_id = item.item_id AND item.active='1' 
    INNER JOIN stock_setup_order_detail ON stock_setup_order.stock_setup_order_id = stock_setup_order_detail.stock_setup_order_id 
    LEFT JOIN distributor ON stock_setup_order.distributor_id = distributor.distributor_id 
) tmp 
WHERE tmp."หน่วยย่อย Setupสั่งซื้อ"<>tmp."หน่วยย่อย Master"
ORDER BY tmp."Item Name" COLLATE "th_TH", tmp."Active Status", tmp."มีผลตั้งแต่วันที่"


item ที่ยังไม่มีหน่วย

ล้าง Stock Card ที่เกิน 18 เดือน  
ล้าง Lot number ที่เกิน 18 เดือน  

item code มีหลายขนาด บรรจุ (Pack size) ต้องมี 2 หน่วย และมี 2 ระดับ --> อนุญาตให้ มีมากกว่า 1 Pack size ได้ ตาม Vendor แต่หน่วยย่อยต้องตรงกัน
และให้เช็กที่ส่วนของจัดซื้อ ด้วย กรณีที่มีหลายๆเคส

ส่วนที่ควรปรับปรุงหรือพัฒนาโปรแกรมเพิ่ม

*** ควรมีหน้าจอที่ค้นได้ทั้งหมดทุกคลัง 

*** Update unit_price cost เป็น avg สำหรับที่ qty=0

*** ประวัติการเปลี่ยนทุน 

-- วันที่เก่าที่สุดใน movement 
--  2013-12-15 

*** ของฝากขาย ต้องอยู่ที่ฝากขาย ห้ามอยู่ที่คลัง Own (Code เดียวกัน ชื่อเดียวกัน)

Cost เป็นค่าว่าง


-- SUM(CAST(COALESCE(qty, '0') AS FLOAT))
SELECT qty FROM stock_card 
WHERE item_id='SUP3000652' AND stock_id='WB0007' 
AND update_date BETWEEN '2015-08-05' AND '2015-08-20'

-----------------------------------------------------------------------

1. ข้อมูลที่ Sense ยา+โรค ให้แสดงที่สั่ง (Alert) และ ตอนพิมพ์ Sticker ให้แสดงให้แพทย์ เภสัช เห็น 
2. DI มีไว้แล้ว ปัญหาคือ พยาบาลสั่งพิมพ์ได้ เช่น ใบ MAR, Sticker ยา 
3. Current Medication --> แสดงยาที่คนไข้ใช้อยู่ อยากให้แสดงในหน้าจอ OPD (Med Reconcile)
4. Max Dose ยังไม่มี 
5. Scan Med reconcile --> ถ้ามี 3 จะไม่ต้องทำข้อ 5 
6. ระบบล็อก ห้ามสั่ง








