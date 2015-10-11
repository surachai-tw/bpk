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

