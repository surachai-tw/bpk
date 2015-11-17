-- 1. No expire

BEGIN 

UPDATE stock_mgnt SET expire_date='2015-10-31' WHERE stock_mgnt_id IN 
(
    SELECT DISTINCT tmp.stock_mgnt_id 
    FROM 
    (
        SELECT * FROM stock_mgnt WHERE CAST(cur_quantity AS FLOAT)<>0 AND (expire_date='' OR expire_date IS NULL)
    ) tmp 
    INNER JOIN stock ON tmp.stock_id=stock.stock_id 
    INNER JOIN item ON tmp.item_id = item.item_id AND item.active='1' 
    LEFT JOIN base_unit ON item.base_unit_id=base_unit.base_unit_id 
    -- ORDER BY stock.stock_name COLLATE "th_TH", item.common_name COLLATE "th_TH", COALESCE(lot_number, '') COLLATE "th_TH"
); 

ROLLBACK 
COMMIT

-- 5. มีการเก็บข้อมูลไว้แต่ ไม่มี Stock Id อยู่จริงในระบบ 
SELECT * INTO bpk_stock_mgnt_backup_no_stock_id FROM stock_mgnt WHERE stock_mgnt_id IN 
(
    SELECT stock_mgnt_id FROM 
    (
        SELECT 
        DISTINCT stock_mgnt.stock_mgnt_id, stock_mgnt.stock_id, stock.stock_name, item.common_name, stock_mgnt.cur_quantity 
        FROM 
        stock_mgnt 
        LEFT JOIN stock ON stock_mgnt.stock_id = stock.stock_id 
        LEFT JOIN item ON stock_mgnt.item_id = item.item_id 
    ) tmp 
    WHERE tmp.stock_name IS NULL OR tmp.common_name IS NULL 
    ORDER BY stock_id
);

SELECT * FROM bpk_stock_mgnt_backup_no_stock_id; 

BEGIN 

DELETE FROM stock_mgnt WHERE stock_mgnt_id IN 
(
    SELECT stock_mgnt_id FROM 
    (
        SELECT 
        DISTINCT stock_mgnt.stock_mgnt_id, stock_mgnt.stock_id, stock.stock_name, item.common_name, stock_mgnt.cur_quantity 
        FROM 
        stock_mgnt 
        LEFT JOIN stock ON stock_mgnt.stock_id = stock.stock_id 
        LEFT JOIN item ON stock_mgnt.item_id = item.item_id 
    ) tmp 
    WHERE tmp.stock_name IS NULL OR tmp.common_name IS NULL 
    ORDER BY stock_id
);

COMMIT

-- 6. Mid unit rate ไม่ใช่ตัวเลข --> ทำให้เป็น 1 
BEGIN 

UPDATE stock_mgnt SET mid_unit_rate='1' WHERE stock_mgnt_id IN 
(
    SELECT DISTINCT stock_mgnt_id FROM 
    (
        SELECT stock_mgnt.stock_mgnt_id, stock_mgnt.stock_id "Stock Code", stock.stock_name "Stock Name", item.item_code "Item Code", item.common_name "Item Name", CAST(stock_mgnt.cur_quantity AS FLOAT) "Quantity"
        FROM stock_mgnt 
                INNER JOIN item ON stock_mgnt.item_id=item.item_id AND item.active='1' 
                INNER JOIN stock ON stock_mgnt.stock_id=stock.stock_id 
        WHERE stock_mgnt.active='1' AND NOT isnumeric(mid_unit_rate) AND isnumeric(stock_mgnt.cur_quantity)
        ORDER BY "Stock Name", "Item Name"
    ) tmp 
) 

COMMIT 



-- 7. Unit rate ไม่ใช่ตัวเลข --> ทำให้เป็น 1
BEGIN 

UPDATE stock_mgnt SET unit_rate='1' WHERE stock_mgnt_id IN 
(
    SELECT DISTINCT stock_mgnt_id FROM 
    (
        SELECT stock_mgnt.stock_mgnt_id, stock_mgnt.stock_id "Stock Code", stock.stock_name "Stock Name", item.item_code "Item Code", item.common_name "Item Name", CAST(stock_mgnt.cur_quantity AS FLOAT) "Quantity"
        FROM stock_mgnt 
                INNER JOIN item ON stock_mgnt.item_id=item.item_id AND item.active='1' 
                INNER JOIN stock ON stock_mgnt.stock_id=stock.stock_id 
        WHERE stock_mgnt.active='1' AND NOT isnumeric(unit_rate) AND isnumeric(stock_mgnt.cur_quantity)
        ORDER BY "Stock Name", "Item Name"
    ) tmp 
) 

COMMIT 

-- 10. Lot ที่เก่ามาก และคาดว่าจะไม่มีผลต่อการทำงานแล้ว ควรย้ายข้อมูลไปไว้ที่ส่วนอื่น อาจจะสืบค้นได้โดย IT แต่ค้นทางหน้าจอไม่ได้ 
-- เพื่อลดข้อมูลในระบบที่ไม่จำเป็น และป้องกันไม่ให้ user นำ Lot ดังกล่าวมาใช้งานอีก และช่วยเพิ่มเรื่อง Performance ระบบ ให้ทำงานได้เร็วขึ้น
DROP TABLE tmp_bpk_stock_mgnt_old_lot
GO

SELECT * INTO tmp_bpk_stock_mgnt_old_lot FROM 
(
    SELECT 
    mainmgnt.stock_mgnt_id, 
    mainitem.item_id, 
    mainitem.item_code "Item Code", 
    mainitem.common_name "Item Name", 
    COALESCE(mainmgnt.lot_number, '') "LOT", 
    COALESCE(mainmgnt.expire_date, '') "Expire Date", 
    Count(mainmgnt.stock_mgnt_id) "Count Record Lot", 
    (SELECT SUM(CAST(cur_quantity AS FLOAT)) FROM stock_mgnt WHERE item_id=mainitem.item_id AND lot_number=mainmgnt.lot_number AND stock_mgnt.stock_mgnt_id=mainmgnt.stock_mgnt_id) "Quantity", 
    bpkget_base_unit_by_id(mainitem.base_unit_id) "Base Unit"
    FROM stock_mgnt mainmgnt
    INNER JOIN item mainitem ON mainmgnt.item_id = mainitem.item_id AND mainitem.active='1' 
    WHERE mainmgnt.stock_id IN (SELECT stock_id FROM stock)
    GROUP BY mainmgnt.stock_mgnt_id, mainitem.item_id, mainitem.item_code, mainitem.common_name, mainmgnt.lot_number, mainmgnt.expire_date 
) tmp 
ORDER BY tmp."Item Name" COLLATE "th_TH", tmp."Quantity" DESC, "Count Record Lot" DESC, "Expire Date" DESC 

DELETE FROM tmp_bpk_stock_mgnt_old_lot WHERE "Quantity" <> 0

SELECT Count(*) FROM tmp_bpk_stock_mgnt_old_lot 
SELECT SUM("Quantity") FROM tmp_bpk_stock_mgnt_old_lot 
SELECT * FROM tmp_bpk_stock_mgnt_old_lot LIMIT 10 

-- Fet stock_mgnt_id for check 
SELECT stock_mgnt_id INTO tmp_bpk_stock_mgnt_old_lot_del_8oct15 FROM tmp_bpk_stock_mgnt_old_lot WHERE stock_mgnt_id NOT IN (SELECT stock_mgnt_id FROM stock_card) 
GO
SELECT * INTO tmp_bpk_stock_mgnt_del_8oct15 FROM stock_mgnt WHERE stock_mgnt_id IN (SELECT stock_mgnt_id FROM tmp_bpk_stock_mgnt_old_lot_del_8oct15)
GO

SELECT * FROM tmp_bpk_stock_mgnt_del_8oct15 

SELECT SUM(Cast(cur_quantity AS FLOAT)) FROM tmp_bpk_stock_mgnt_del_8oct15

SELECT * FROM tmp_bpk_stock_mgnt_del_8oct15 WHERE Cast(cur_quantity AS FLOAT)>0

-- เติมข้อมูลส่วนที่ถูกลบไป แต่ qty มากกว่า 0 
-- INSERT INTO stock_mgnt 
-- SELECT * FROM tmp_bpk_stock_mgnt_del_8oct15 WHERE Cast(cur_quantity AS FLOAT)>0 
-- GO
-- DELETE FROM tmp_bpk_stock_mgnt_del_8oct15 WHERE Cast(cur_quantity AS FLOAT)>0 
-- GO 

BEGIN 

DELETE FROM stock_mgnt WHERE stock_mgnt_id IN (SELECT stock_mgnt_id FROM tmp_bpk_stock_mgnt_old_lot_del_8oct15)

COMMIT 

SELECT * INTO bpk_stock_mgnt_lot_zero FROM tmp_bpk_stock_mgnt_del_8oct15 
GO

SELECT * FROM bpk_stock_mgnt_lot_zero

ALTER TABLE bpk_stock_mgnt_lot_zero ADD COLUMN cdate VARCHAR(255) DEFAULT '';
ALTER TABLE bpk_stock_mgnt_lot_zero ADD COLUMN ctime VARCHAR(255) DEFAULT '';

UPDATE bpk_stock_mgnt_lot_zero SET cdate=CURRENT_DATE;
UPDATE bpk_stock_mgnt_lot_zero SET ctime=CAST(CURRENT_TIME AS VARCHAR(8));


SELECT table_name 
FROM information_schema.columns 
WHERE column_name='stock_mgnt_id';
 
Check stock_mgnt_id in table below
 stock_adjust                      
 stock_card                        
 stock_dispense                    
 stock_exchange                                             
 stock_prepack_item                
 stock_return    

SELECT table_name 
FROM information_schema.columns 
WHERE column_name='stock_mgnt_id';

SELECT min(update_date) FROM stock_card 
SELECT Count(*) FROM stock_adjust
GO
SELECT Count(*) FROM stock_card
GO 
SELECT Count(*) FROM stock_dispense
GO
SELECT Count(*) FROM stock_exchange
GO                                          
SELECT Count(*) FROM stock_prepack_item
GO
SELECT Count(*) FROM stock_return
GO 

-- 9. item ที่ไม่เคลื่อนไหว หรือ ไม่ได้ใช้งานเป็นเวลานาน หรือ ไม่อยู่ใน Store ใดๆเลย 
-- การตรวจสอบว่าไม่เคลื่อนไหว ให้ดูจาก stock card 


SELECT item_id FROM bpk_item_inactive WHERE item_id IN (SELECT DISTINCT item_id FROM stock_mgnt WHERE active='1');
SELECT item_id FROM bpk_item_inactive WHERE item_id IN (SELECT DISTINCT item_id FROM stock_mgnt);
DELETE FROM bpk_item_inactive WHERE item_id='913092714281978601';

SELECT (SELECT stock_name FROM stock WHERE stock_id=order_from_stock_id), * FROM stock_order WHERE item_id IN (SELECT DISTINCT item_id FROM bpk_item_inactive);
SELECT (SELECT stock_name FROM stock WHERE stock_id=stock_mgnt.stock_id), * FROM stock_mgnt WHERE item_id IN (SELECT DISTINCT item_id FROM bpk_item_inactive);

SELECT * FROM stock_order WHERE item_id IN (SELECT DISTINCT item_id FROM bpk_item_inactive) 

-- **** พบใน stock_order **** เป็นพวก Consign แต่ไม่ต้องสนใจ ให้เอาออกไปเลย 
SELECT order_from_stock_id, (SELECT stock_name FROM stock WHERE stock_id=order_from_stock_id), purchasing_request_number "PR", purchasing_offer_number "PO", (SELECT common_name FROM item WHERE item_id=stock_order.item_id) item_name, purchasing_offer_note FROM stock_order WHERE item_id IN (SELECT DISTINCT item_id FROM bpk_item_inactive) 
ORDER BY stock_name, purchasing_offer_number, item_name

SELECT * FROM stock_order_tmp LIMIT 1 

SELECT * FROM stock_request WHERE item_id IN (SELECT DISTINCT item_id FROM bpk_item_inactive) 

-- **** พบใน stock_request_order **** เป็นพวก Consign แต่ไม่ต้องสนใจ ให้เอาออกไปเลย 
SELECT * FROM stock_request_order WHERE item_id IN (SELECT DISTINCT item_id FROM bpk_item_inactive) 

SELECT * FROM stock_request_order WHERE item_id IN (SELECT DISTINCT item_id FROM bpk_item_inactive) 
-- **** พบใน stock_request_order **** เป็นพวก Consign แต่ไม่ต้องสนใจ ให้เอาออกไปเลย 
SELECT * FROM stock_return WHERE item_id IN (SELECT DISTINCT item_id FROM bpk_item_inactive) 


-------------------------------------------------------
-- ส่วนของโภชนาการ 

SELECT * FROM stock_order 
INNER JOIN stock_setup_order ON stock_order.distributor_id = stock_setup_order.distributor_id 
INNER JOIN stock_setup_order_detail ON stock_setup_order.stock_setup_order_id = stock_setup_order_detail.stock_setup_order_id 
WHERE stock_order.fix_stock_order_status_id IN  ('1', '2', '3')


-- รอบหลัง ทำการ Update หน่วยของ item.base_unit ให้ตรงกับ small_unit ถ้า item.base_unit ว่าง 

-- 4. หน่วยขาย กับ หน่วยย่อยของ Stock ไม่ตรงกัน 
BEGIN TRANSACTION;

UPDATE item SET 
base_unit_id = cleansing."Small Unit"
FROM 
(
    SELECT clean_item.item_id, (SELECT common_name FROM item WHERE item_id=clean_item.item_id), new_base_unit."Small Unit" FROM 
    (
        SELECT item_id FROM 
        (
            SELECT DISTINCT tmp.item_code "Item Code", tmp.common_name "Item Name", (tmp.base_unit_id) "Base Unit Master", (tmp.small_unit_id) "Small Unit", item_id 
            FROM 
            (
                SELECT DISTINCT item.item_code, item.common_name, COALESCE(item.base_unit_id, '') base_unit_id, COALESCE(stock_mgnt.small_unit_id, '') small_unit_id, item.item_id  
                FROM 
                item 
                INNER JOIN stock_mgnt ON item.item_id = stock_mgnt.item_id 
                INNER JOIN stock ON stock_mgnt.stock_id = stock.stock_id 
                WHERE item.active='1'  
            ) tmp
            WHERE tmp.base_unit_id<>tmp.small_unit_id 
            AND tmp.base_unit_id='' 
        ) tmp2
        GROUP BY item_id HAVING Count(DISTINCT "Small Unit")=1 
    ) clean_item 
    LEFT JOIN 
    (
        SELECT DISTINCT tmp.item_code "Item Code", tmp.common_name "Item Name", (tmp.base_unit_id) "Base Unit Master", (tmp.small_unit_id) "Small Unit", item_id 
        FROM 
        (
            SELECT DISTINCT item.item_code, item.common_name, COALESCE(item.base_unit_id, '') base_unit_id, COALESCE(stock_mgnt.small_unit_id, '') small_unit_id, item.item_id  
            FROM 
            item 
            INNER JOIN stock_mgnt ON item.item_id = stock_mgnt.item_id 
            INNER JOIN stock ON stock_mgnt.stock_id = stock.stock_id 
            WHERE item.active='1'  
        ) tmp
        WHERE tmp.base_unit_id<>tmp.small_unit_id 
        AND tmp.base_unit_id='' 
    ) new_base_unit ON clean_item.item_id = new_base_unit.item_id 
) cleansing 
WHERE item.item_id=cleansing.item_id 
AND item.base_unit_id='' 

ROLLBACK;
COMMIT

SELECT stock_mgnt_id, mid_unit_rate, unit_rate FROM stock_mgnt WHERE stock_id='WF0042' AND item_id=(SELECT item_id FROM item WHERE item_code='TENO')

BEGIN TRANSACTION 
UPDATE stock_mgnt SET mid_unit_rate='1', unit_rate='1' WHERE stock_mgnt_id='WF0042110100109121836901';
COMMIT



-- 11. Setup สั่งซื้อ ไม่ตรงกับ Master ของ Item 
-- หน่วยย่อย ของ 2 หน้าจอ ไม่ตรงกัน 
BEGIN;

UPDATE item SET base_unit_id = tmp.small_unit_id 
FROM 
(
    SELECT * FROM 
    (
        SELECT 
            item.item_id, 
            item.item_code "Item Code", 
            item.common_name "Item Name", 
            stock_setup_order.item_trade_name "Trade Name", 
            distributor.distributor_name "Vender Name", 
            CASE WHEN stock_setup_order.active='1' THEN 'Active' ELSE 'In-Active' END "Active Status", 
            stock_setup_order_detail.active_date "มีผลตั้งแต่วันที่", 
            bpkget_base_unit_by_id(stock_setup_order_detail.big_unit_id) "หน่วยใหญ่ Setupสั่งซื้อ", 
            stock_setup_order_detail.mid_unit_rate, 
            stock_setup_order_detail.unit_rate, 
            item.base_unit_id, 
            stock_setup_order_detail.small_unit_id, 
            bpkget_base_unit_by_id(stock_setup_order_detail.small_unit_id) "หน่วยย่อย Setupสั่งซื้อ", 
            bpkget_base_unit_by_id(item.base_unit_id) "หน่วยย่อย Master" 
        FROM 
        stock_setup_order 
        INNER JOIN item ON stock_setup_order.item_id = item.item_id AND item.active='1' 
        INNER JOIN stock_setup_order_detail ON stock_setup_order.stock_setup_order_id = stock_setup_order_detail.stock_setup_order_id 
        LEFT JOIN distributor ON stock_setup_order.distributor_id = distributor.distributor_id 
    ) tmp 
    WHERE tmp."หน่วยย่อย Setupสั่งซื้อ"<>tmp."หน่วยย่อย Master"
    AND tmp."หน่วยย่อย Master"='' 
    AND tmp."Active Status" = 'Active'
    ORDER BY tmp."Item Name" COLLATE "th_TH", tmp."Active Status", tmp."มีผลตั้งแต่วันที่"
) tmp 
WHERE item.item_id=tmp.item_id 

ROLLBACK 
COMMIT

SELECT * FROM base_unit WHERE base_unit_id='ROL' 


-- Update Expire for 'WB0005', 'WB0013'
BEGIN 

UPDATE stock_mgnt SET expire_date='2015-11-17' WHERE stock_mgnt_id IN 
(
    SELECT DISTINCT tmp.stock_mgnt_id 
    FROM 
    (
        SELECT (SELECT common_name FROM item WHERE item_id=stock_mgnt.item_id), * FROM stock_mgnt WHERE CAST(cur_quantity AS FLOAT)<>0 AND (expire_date='' OR expire_date IS NULL) AND stock_id IN ('WB0005', 'WB0013') AND active='1'
    ) tmp 
    INNER JOIN stock ON tmp.stock_id=stock.stock_id 
    INNER JOIN item ON tmp.item_id = item.item_id AND item.active='1' 
    LEFT JOIN base_unit ON item.base_unit_id=base_unit.base_unit_id 
    -- ORDER BY stock.stock_name COLLATE "th_TH", item.common_name COLLATE "th_TH", COALESCE(lot_number, '') COLLATE "th_TH"
); 

ROLLBACK 
COMMIT
