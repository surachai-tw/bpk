SELECT TOP 100 * FROM bpk_daily_patient_revenue ORDER BY FromDate DESC 
SELECT Count(*) FROM bpk_daily_stock_mgnt_his
SELECT TOP 10 * FROM bpk_daily_stock_mgnt_his

--TRUNCATE TABLE bpk_daily_stock_mgnt_his

DELETE FROM bpk_daily_stock_mgnt_his WHERE begin_date='2015-12-04'
SELECT begin_date, Count(id) FROM bpk_daily_stock_mgnt_his GROUP BY begin_date ORDER BY begin_date 
SELECT bpk_id, Count(id) FROM bpk_daily_stock_mgnt_his GROUP BY bpk_id ORDER BY bpk_id 
SELECT dbo.fBpkGetBpkNameById(bpk_id) 'Detail', SUM(cost) SUMMARY FROM bpk_daily_stock_mgnt_his GROUP BY bpk_id ORDER BY bpk_id 

--DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=1

SELECT * FROM 
(
	SELECT bpk_id, stock_name, item_description, SUM(cost) SUMMARY FROM bpk_daily_stock_mgnt_his GROUP BY bpk_id, stock_name, item_description
) tmp 
ORDER BY SUMMARY DESC 

 -- ORDER BY bpk_id, stock_name 

 SELECT * FROM 
 (
 	SELECT bpk_id, stock_name, SUM(cost) SUMMARY FROM bpk_daily_stock_mgnt_his GROUP BY bpk_id, stock_name 
 ) tmp 
 ORDER BY SUMMARY DESC 

 SELECT * FROM bpk_daily_stock_mgnt_his WHERE bpk_id=8 AND stock_name='OR' ORDER BY cost DESC 
 DELETE FROM bpk_daily_stock_mgnt_his WHERE quantity=88888 AND bpk_id=8 
 DELETE FROM bpk_daily_stock_mgnt_his WHERE quantity IN (88893, 88889, 88938) AND bpk_id=8 

 SELECT * FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9 AND stock_name='ศูนย์ความงาม' ORDER BY cost DESC 
 DELETE FROM bpk_daily_stock_mgnt_his WHERE quantity=9999 AND bpk_id=9 

 SELECT * FROM bpk_daily_stock_mgnt_his WHERE bpk_id=8 AND stock_name='ER อาคาร 1' ORDER BY cost DESC 
 DELETE FROM bpk_daily_stock_mgnt_his WHERE quantity IN (88831, 88887, 88894, 88890, 88892, 88872, 88887, 88908, 88887, 88847, 88890, 88885, 88886, 88887, 88870, 88988, 88887, 88887, 88885, 87946, 88988, 88789, 88887, 89288, 88988, 88886, 89488, 88988, 89488, 88988, 93188, 88422, 91188, 88667, 89938, 77027, 89588, 89088, 89088, 89088, 89088, 89488, 89488, 81001) AND bpk_id=8

 SELECT * FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9 AND stock_name='ห้องผ่าตัด' ORDER BY cost DESC 
 DELETE FROM bpk_daily_stock_mgnt_his WHERE quantity IN (9998, 9997, 9997) AND bpk_id=9

 SELECT stock_mgnt.cur_quantity, item.item_code, item.common_name, item.base_unit_id FROM stock_mgnt 
INNER JOIN item ON stock_mgnt.item_id=item.item_id AND item.active='1' 
WHERE stock_id=(SELECT stock_id FROM stock WHERE stock_name='ER อาคาร 1') AND stock_mgnt.active='1' AND Cast(cur_quantity AS FLOAT)>8888

 SELECT * FROM bpk_daily_stock_mgnt_his WHERE bpk_id=8 AND stock_name='ER อาคาร 2' ORDER BY cost DESC 
 DELETE FROM bpk_daily_stock_mgnt_his WHERE quantity IN (88904, 88903, 88842, 88896, 88898, 89062, 88902, 88898, 88902, 88897, 88896, 88896, 88895, 88895, 88895, 88891, 88895, 88898, 88904, 88903, 88891, 88901, 88865, 89032, 89104, 89068, 89324, 88901, 88898, 88899, 88891, 88891, 88891, 88891, 88896, 88910, 88898, 88898, 88900, 88936, 88899, 88902, 88865, 88903, 88901, 88903, 88902, 90088, 88918, 89138, 88907, 88902, 88904, 88904, 88904, 88904, 88900, 88652, 89152, 92488, 89388, 88875, 130091, 93088, 93488, 104488, 90388, 94980, 104432, 90564, 93638, 91388, 98238, 93488, 92088, 90788, 91488, 91788, 89688, 89188, 92888, 90388) AND bpk_id=8

  SELECT * FROM bpk_daily_stock_mgnt_his WHERE bpk_id=1 AND stock_name='Central Supply' ORDER BY cost DESC 
  DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=1 AND item_id='SA00171' -- AVG Cost น่าจะผิด

  SELECT * FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9 AND stock_name='แผนกห้องปฏิบัติการ' ORDER BY cost DESC
  DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9 AND item_id='SA00166' -- AVG Cost น่าจะผิด

  SELECT * FROM bpk_daily_stock_mgnt_his WHERE bpk_id=8 AND stock_name='ICU' ORDER BY cost DESC
  DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=8 AND quantity IN (88880, 88854, 88807, 88880, 89838, 89408, 89338, 88806)

  SELECT * FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9 AND stock_name='Happy Long Life Center' ORDER BY cost DESC
  SELECT * FROM bpk_daily_stock_mgnt_his WHERE bpk_id=1 AND stock_name='ไตเทียม' ORDER BY cost DESC
  
 
SELECT * FROM 
(
	SELECT item_description, SUM(cost) SUMMARY FROM bpk_daily_stock_mgnt_his WHERE stock_id='WB0007' GROUP BY item_description
) tmp 
ORDER BY SUMMARY 
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='(CLOSE)ALCOHOL 95 % 18 lit / gallon'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9 AND item_description='(CLOSE)ALCOHOL 95 % 18 lit / gallon' AND (quantity<0 OR unit_avg_cost<0)

SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='SALBUTAMOL (VENTOLIN(L)) 2 mg.(UV)'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9 AND item_description='SALBUTAMOL (VENTOLIN(L)) 2 mg.(UV)' AND (quantity<0 OR unit_avg_cost<0)

SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='ไม้พันสำลี 3 นิ้ว 3" NO.S, M, L'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9 AND item_description='ไม้พันสำลี 3 นิ้ว 3" NO.S, M, L' AND (quantity<0 OR unit_avg_cost<0)

SELECT * FROM 
(
	SELECT item_description, SUM(cost) SUMMARY FROM bpk_daily_stock_mgnt_his WHERE stock_id='WF0028' GROUP BY item_description
) tmp 
ORDER BY SUMMARY 
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='ถุงร้อน 5" x 8"'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=8 AND item_description='ถุงร้อน 5" x 8"' AND (quantity<0 OR unit_avg_cost<0)
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='เนสกาแฟ 3 IN 1 (27 ซอง/แพค)'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=8 AND item_description='เนสกาแฟ 3 IN 1 (27 ซอง/แพค)' AND quantity<0

SELECT * FROM 
(
	SELECT bpk_id, item_description, SUM(quantity) qty, SUM(cost) SUMMARY FROM bpk_daily_stock_mgnt_his WHERE stock_id='DEN1' GROUP BY bpk_id, item_description
) tmp 
ORDER BY SUMMARY 
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='AMOXYCILLIN 500 MG.'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=3 AND item_description='AMOXYCILLIN 500 MG.' AND (quantity<0 OR unit_avg_cost<0)
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='PARACETAMOL 500 MG.'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=3 AND item_description='PARACETAMOL 500 MG.' AND (quantity<0 OR unit_avg_cost<0)
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='METRONIDAZOLE 400 MG.'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=3 AND item_description='METRONIDAZOLE 400 MG.' AND (quantity<0 OR unit_avg_cost<0)
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='IBUPROFEN 400 MG.'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=3 AND item_description='IBUPROFEN 400 MG.' AND (quantity<0 OR unit_avg_cost<0)
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='ERYTROMYCIN 250 MG. ฆ่าเชื้อ'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=3 AND item_description='ERYTROMYCIN 250 MG. ฆ่าเชื้อ' AND (quantity<0 OR unit_avg_cost<0)
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='PARACETAMAL 60 ML.'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=3 AND item_description='PARACETAMAL 60 ML.' AND (quantity<0 OR unit_avg_cost<0)
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='TRIAMCINOLONE 1%ORAL PASTE 1GM/BAG'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=3 AND item_description='TRIAMCINOLONE 1%ORAL PASTE 1GM/BAG' AND (quantity<0 OR unit_avg_cost<0)
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='IBIAMOX SYR 125MG, 60ML.'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=3 AND item_description='IBIAMOX SYR 125MG, 60ML.' AND (quantity<0 OR unit_avg_cost<0)
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='GLOVE NO.S'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=3 AND item_description='GLOVE NO.S' AND (quantity<0 OR unit_avg_cost<0)
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='IBIAMOX SYR 250 MG/60 ML'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=3 AND item_description='IBIAMOX SYR 250 MG/60 ML' AND (quantity<0 OR unit_avg_cost<0)
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='PARACETAMOL 325 MG.'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=3 AND item_description='PARACETAMOL 325 MG.' AND (quantity<0 OR unit_avg_cost<0)

SELECT * FROM 
(
	SELECT item_description, SUM(cost) SUMMARY FROM bpk_daily_stock_mgnt_his WHERE stock_id='DEV1' GROUP BY item_description
) tmp 
ORDER BY SUMMARY 
SELECT * FROM bpk_daily_stock_mgnt_his WHERE item_description='TONER EP#73N C13T105190 สีดำ'
DELETE FROM bpk_daily_stock_mgnt_his WHERE bpk_id=3 AND item_description='TONER EP#73N C13T105190 สีดำ' AND (quantity<0 OR unit_avg_cost<0)


-----------------------------

SELECT * FROM 
(
	SELECT stock_id, SUM(cost) SUMMARY FROM bpk_daily_stock_mgnt_his GROUP BY stock_id  
) tmp 
ORDER BY SUMMARY 

SELECT dbo.fBpkGetBpkNameById(bpk_id) [Detail], SUM(cost) [Stock Balance Amount] FROM bpk_daily_stock_mgnt_his GROUP BY bpk_id ORDER BY bpk_id 

SELECT
'Stock Balance Amount' Detail,
SUM(CASE WHEN bpk_id=1 THEN cost ELSE 0 END) [BPK1], 
SUM(CASE WHEN bpk_id=3 THEN cost ELSE 0 END) [BPK3], 
SUM(CASE WHEN bpk_id=8 THEN cost ELSE 0 END) [BPK8], 
SUM(CASE WHEN bpk_id=9 THEN cost ELSE 0 END) [BPK9] 
FROM bpk_daily_stock_mgnt_his 

SELECT TOP 10 * FROM bpk_daily_stock_mgnt_his


SELECT main.*, main.[BALANCE PERCENT]+sub.[BALANCE PERCENT] [COMMU] 
FROM 
(
	SELECT ROW_NUMBER() OVER (ORDER BY [Stock Balance Amount] DESC) id, tmp.*, round((100*tmp.[Stock Balance Amount]/tmp.SUMMARY), 0) [BALANCE PERCENT] 
	FROM 
	(
		SELECT stock_name [WAREHOUSE], SUM(cost) [Stock Balance Amount], (SELECT SUM(cost) FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9) SUMMARY FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9
		GROUP BY stock_name 
	) tmp
) main
INNER JOIN 
(
	SELECT main.id-1 [id],main.WAREHOUSE,main.[Stock Balance Amount],main.SUMMARY,main.[BALANCE PERCENT]
	FROM 
	(
		SELECT ROW_NUMBER() OVER (ORDER BY [Stock Balance Amount] DESC) id, tmp.*, round((100*tmp.[Stock Balance Amount]/tmp.SUMMARY), 0) [BALANCE PERCENT] 
		FROM 
		(
			SELECT stock_name [WAREHOUSE], SUM(cost) [Stock Balance Amount], (SELECT SUM(cost) FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9) SUMMARY FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9
			GROUP BY stock_name 
		) tmp
	) main
) sub ON main.id = sub.id 
ORDER BY main.id 

select t1.id, t1.SomeNumt, SUM(t2.SomeNumt) as sum
from @t t1
inner join @t t2 on t1.id >= t2.id
group by t1.id, t1.SomeNumt
order by t1.id

SELECT t1.id, t1.[WAREHOUSE], t1.[Stock Balance Amount], t1.[BALANCE PERCENT], SUM(t2.[BALANCE PERCENT]) [COMMULATIVE]
FROM 
(
	SELECT ROW_NUMBER() OVER (ORDER BY [Stock Balance Amount] DESC) id, tmp.*, round((100*tmp.[Stock Balance Amount]/tmp.SUMMARY), 0) [BALANCE PERCENT] 
	FROM 
	(
		SELECT stock_name [WAREHOUSE], SUM(cost) [Stock Balance Amount], (SELECT SUM(cost) FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9) SUMMARY FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9
		GROUP BY stock_name 
	) tmp	
) t1
INNER JOIN 
(
	SELECT ROW_NUMBER() OVER (ORDER BY [Stock Balance Amount] DESC) id, tmp.*, round((100*tmp.[Stock Balance Amount]/tmp.SUMMARY), 0) [BALANCE PERCENT] 
	FROM 
	(
		SELECT stock_name [WAREHOUSE], SUM(cost) [Stock Balance Amount], (SELECT SUM(cost) FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9) SUMMARY FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9
		GROUP BY stock_name 
	) tmp
)
 t2 on t1.id >= t2.id
GROUP BY t1.id, t1.[WAREHOUSE], t1.[Stock Balance Amount], t1.[BALANCE PERCENT]
ORDER BY t1.id


SELECT stock_name [WAREHOUSE], base_drug_type_description [TYPE], SUM(cost) [Stock Balance Amount] FROM bpk_daily_stock_mgnt_his WHERE bpk_id=9
GROUP BY stock_name, base_drug_type_description ORDER BY [Stock Balance Amount] DESC, base_drug_type_description

SELECT * FROM bpk_daily_stock_mgnt_his WHERE cost<0

-- สำหรับดึงรายการมาเคลียร์ยอดลบ ทั้งหมด 
SELECT DISTINCT bpk_id, item_id, item_description FROM bpk_daily_stock_mgnt_his WHERE cost<0

DELETE FROM bpk_daily_stock_mgnt_his WHERE cost<0
