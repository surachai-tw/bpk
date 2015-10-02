SELECT * FROM bpk_account_group 
GO
-- SELECT * FROM base_billing_group ORDER BY base_billing_group_id 
SELECT * FROM base_service_point WHERE description LIKE '%ตรวจ%' ORDER BY description COLLATE "th_TH"
GO

SELECT * FROM base_service_point WHERE description LIKE '%เวชระ%' OR description LIKE '%เงินะ%' OR description LIKE '%ICS%3ะ%'
SELECT * FROM base_department WHERE description LIKE '%เวชระ%' OR description LIKE '%เงินะ%' OR description LIKE '%ICS%3ะ%'
SELECT * FROM base_department ORDER BY description COLLATE "th_TH"
SELECT * FROM base_department WHERe description LIKE '%เงิน%'
SELECT * FROM base_department WHERe description LIKE '%ICS%'
SELECT * FROM base_service_point WHERE base_department_id='F0088'

SELECT * FROM bpk_department_services WHERE base_department_id IN ('F0051', 'F0088', 'B0001', 'B0002')


SELECT * FROM item WHERE common_name LIKE '%อาหาร%' ORDER BY common_name COLLATE "th_TH"

SELECT * FROM visit WHERE format_an(an)='58/005376'

SELECT verify_date, verify_time, order_item.item_id, common_name, quantity, unit_price_sale, verify_spid, (SELECT description FROM base_service_point WHERE base_service_point_id=verify_spid), base_billing_group_id 
FROM order_item INNER JOIN item ON order_item.item_id=item.item_id 
WHERE visit_id='115061110213508501'
AND base_billing_group_id IN ('02.03.01.00.00.00', '02.03.02.00.00.00')
ORDER BY verify_date, verify_time

SELECT * FROM base_billing_group WHERE base_billing_group_id='02.03.02.00.00.00'

SELECT base_billing_group_opd_id, * FROM item WHERE base_billing_group_opd_id IN (SELECT base_billing_group_id FROM base_billing_group WHERE acc_group='13')
ORDER BY common_name COLLATE "th_TH"

SELECT * FROM bpk_account_group WHERE id='13'

SELECT NOT FALSE 

SELECT * FROM bpk_account_debit_detail WHERE visit_id='115090723105200501' ORDER BY id 
GO
SELECT * FROM bpk_account_credit_detail WHERE visit_id='115090723105200501' ORDER BY id 
GO

SELECT SUM(wlinepaid)+SUM(wlinediscount) FROM bpk_account_debit_detail WHERE visit_id='115090922510364501' 
GO
SELECT SUM(wlinepaid)+SUM(wlinediscount) FROM bpk_account_credit_detail WHERE visit_id='115090922510364501' 
GO

SELECT * FROM 
(
    SELECT hn, visit_id, financial_discharge_date, overify_spid, oordered_by_service_point, overify_deptid, oordered_by_department, 
    verify_spid, ordered_by_service_point, verify_deptid, ordered_by_department, 
    pverify_spid, pordered_by_service_point, pverify_deptid, pordered_by_department, 
    quantity, wlinepaid, item_description, verify_date, verify_time  
    FROM bpk_account_credit_detail WHERE vn='851/070958' -- AND financial_discharge_date='2015-08-05' 
    -- AND item_description LIKE 'RECEIVE Leukocyte Poor Packed Red cells (LPRC)'
) tmp ORDER BY verify_date, verify_time 
-- WHERE tmp.oordered_by_service_point LIKE '%AW%'

SELECT (SELECT description FROM base_service_point WHERE base_service_point_id=visit_queue.next_location_spid), * FROM visit_queue WHERE visit_id='115071719491524401'

SELECT * FROM bpk_account_credit_detail WHERE order_item_id='113073015000316701'
GO 
SELECT * FROM bpk_account_credit_detail WHERE base_billing_group_id='01.01.01.03.00.00' AND  visit_id='115090310011257301'
GO 
SELECT * FROM bpk_account_credit_detail WHERE base_billing_group_id='01.01.01.01.00.00' AND  visit_id='115090310011257301'
GO 
SELECT * FROM bpk_account_debit_detail WHERE visit_id='115090310011257301' AND base_billing_group_id='01.01.01.03.00.00'
GO 
SELECT * FROM bpk_account_debit_detail WHERE visit_id='115090310011257301' AND base_billing_group_id='01.01.01.01.00.00'
GO 
-- visit_id='115020310590404201' vn 339/030258 ยุพา --> ดึงข้อมูลเป็น OPD แต่มี AN และกลุ่มใบเสร็จ เป็น IPD แต่ใน Order Item ยังเป็น OPD 
-- visit_id='115021007222699601' vn 60/100258 
-- visit_id='115030411103405501' vn 292/040358 ยุพา 
-- 31/080358, 59/270358, 316/250358, 432/210458, 1/290458, 174/090558, 168/270558
-- 8/190658, 115/100658, 144/170658
-- 249/200758, 115/290758, 279/310758
-- 736/270758, 52/250858, 59/110858, 315/290858, 64/030858 
BEGIN 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='339/030258' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='60/100258' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='292/040358' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='31/080358' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='59/270358' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='316/250358' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='432/210458' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='1/290458' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='174/090558' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='168/270558' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='8/190658' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='115/100658' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='144/170658' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='249/200758' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='115/290758' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='279/310758' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='736/270758' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='52/250858' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='59/110858' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='315/290858' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='64/030858' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.01.00', billing_description='ยาผู้ป่วยใน(ไม่ลด)' WHERE vn='432/210458' AND base_billing_group_id='01.01.01.03.01.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.01.00', billing_description='ยาผู้ป่วยใน(ไม่ลด)' WHERE vn='1/290458' AND base_billing_group_id='01.01.01.03.01.00' 

UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='158/080958' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='55/220958' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='79/010958' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='373/240958' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='146/060958' AND base_billing_group_id='01.01.01.03.00.00' 
UPDATE bpk_account_credit_detail SET base_billing_group_id='01.01.01.01.00.00', billing_description='ยาผู้ป่วยใน' WHERE vn='293/030958' AND base_billing_group_id='01.01.01.03.00.00' 
ROLLBACK
COMMIT 
SELECT * FROM base_billing_group WHERE base_billing_group_id='01.01.01.03.00.00'

BEGIN TRANSACTION 

-- รายการข้ามวัน ที่ยกเลิกค่า ออกในอีกวันหนึ่ง
DELETE FROM bpk_account_credit_detail WHERE vn IN ('613/060958', '766/090958', '753/030958', '754/030958', '802/240958', '448/250958', '656/270958', '657/270958', '658/270958', '659/270958', '660/270958', '661/270958', '662/270958', '663/270958', '664/270958', '665/270958', '666/270958', '851/070958') 
AND item_id IS NULL 

COMMIT

SELECT * FROM bpk_account_credit_detail WHERE visit_id='113073015000316701'
GO
SELECT * FROM bpk_account_debit_detail WHERE visit_id='113073015000316701'
GO

SELECT * FROM bpk_account_credit_detail WHERE visit_id=(SELECT visit_id FROM visit WHERE format_vn(vn)='962/200756')
GO
SELECT * FROM bpk_account_debit_detail WHERE visit_id=(SELECT visit_id FROM visit WHERE format_vn(vn)='962/200756')
GO

SELECT * FROM bpk_account_credit_detail WHERE visit_id=(SELECT visit_id FROM admit WHERE format_an(an)='58/002649')
GO
SELECT * FROM bpk_account_debit_detail WHERE visit_id=(SELECT visit_id FROM admit WHERE format_an(an)='58/002649')
GO

SELECT SUM(wlinepaid) FROM bpk_account_credit_detail WHERE visit_id='115032110383078101'
GO
SELECT SUM(wlinepaid) FROM bpk_account_debit_detail WHERE visit_id='115032110383078101'
GO

SELECT * FROM bpk_account_credit_detail WHERE vn='254/260758' 
GO 
SELECT * FROM bpk_account_debit_detail WHERE vn='254/260758' 
GO
SELECT DISTINCT receipt_id FROM bpk_account_debit_detail WHERE vn='254/260758' 
GO
SELECT SUM(linesale) FROM bpk_account_credit_detail WHERE vn='254/260758' 
GO 
SELECT SUM(linesale) FROM bpk_account_debit_detail WHERE vn='254/260758' 
GO
SELECT SUM(wlinepaid) FROM bpk_account_credit_detail WHERE vn='254/260758' 
GO 
SELECT SUM(wlinepaid) FROM bpk_account_debit_detail WHERE vn='254/260758' 
GO

SELECT * FROM receipt WHERE visit_id='115072610161034801'
SELECT * FROM bpk_account_debit_detail WHERE receipt_id='115080519382527601' 
UPDATE bpk_account_debit_detail SET vn='254/260758' WHERE visit_id='115072610161034801'
DELETE FROM bpk_account_debit_detail WHERE id IN (871649, 871650, 871651, 871652, 871653, 871654, 871655, 871656, 871657, 871658, 871659, 871660, 871661, 871662)
UPDATE bpk_account_debit_detail SET receive_date='2015-07-30' WHERE receipt_id='115080519382527601' 

-- At Here
SELECT * FROM bpk_account_credit_detail WHERE base_billing_group_id='01.01.01.03.00.00' AND fix_visit_type_id='1'


SELECT bed_management.* FROM bed_management INNER JOIN admit ON bed_management.admit_id=admit.admit_id 
WHERE admit.visit_id = '115080307365468201' AND '2015-08-08	16:03:13' BETWEEN bed_management.move_date||' '||bed_management.move_time 
AND bed_management.move_out_date||' '||bed_management.move_out_time ORDER BY bed_management.modify_date DESC, bed_management.modify_time DESC;

SELECT * FROM bed_management WHERE admit_id=(SELECT admit_id FROM admit WHERE visit_id='115080307365468201')

-- 680/170758   Day Care
-- 70/030858    Key ห้องย้อนหลัง --> เลือก Ward สุดท้าย 
-- 735/030858   ยังหาไม่เจอ 

-- 64/030858 Key ว่า ค่าอาหาร ตรงๆ



 SELECT visit.visit_spid FROM visit INNER JOIN base_service_point ON 
            visit.visit_spid = base_service_point.base_service_point_id 
            AND base_service_point.fix_service_point_group_id<>'0' 
            AND base_service_point.fix_service_point_group_id<>'10' 
            WHERE visit.visit_id = '115080316035341001' 

SELECT * FROM bpk_department_services WHERE base_department_id='B0002'

    SELECT order_item.verify_spid, (SELECT description FROM base_service_point WHERE base_service_point_id=order_item.verify_spid) FROM order_item 
        INNER JOIN (SELECT * FROM base_service_point WHERE  base_service_point.fix_service_point_group_id IN ('5', '6') OR 
                      base_service_point.description LIKE '%กายภาพ%' OR 
                      base_service_point.description LIKE '%โภช%') AS sp ON order_item.verify_spid = sp.base_service_point_id 
        WHERE order_item.visit_id = '115082212392535101' ORDER BY order_item.verify_date, order_item.verify_time;

SELECT bed_management.base_service_point_id FROM bed_management INNER JOIN admit ON bed_management.admit_id=admit.admit_id WHERE admit.visit_id = p_visit_id AND order_datetime<=(bed_management.move_date||'' ''||bed_management.move_time) ORDER BY bed_management.modify_date, bed_management.modify_time LIMIT 1;

SELECT * FROM base_service_point WHERE base_service_point_id IN ('011', '013', '033')

SELECT verify_spid, SUM(wlinepaid) paid FROM bpk_account_credit_detail 
            INNER JOIN base_service_point ON bpk_account_credit_detail.verify_spid=base_service_point.base_service_point_id 
                                                  AND base_service_point.fix_service_point_group_id IN ('1', '2', '9', '11') 
                                                  AND base_service_point.description NOT LIKE '%ผ่าตัด%' 
                                                  AND base_service_point.description NOT LIKE '% ER%'
                                                  AND base_service_point.description NOT LIKE '%โภช%'
                                                  AND base_service_point.description NOT LIKE '%กายภาพ%'
                                                  AND (base_service_point.description NOT LIKE '%ตรวจสุขภาพ%' AND base_service_point.description NOT ILIKE '%Check%up%')
            INNER JOIN base_department ON base_department.base_department_id=base_service_point.base_department_id 
            INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                  AND bpk_department_services.is_revenue_allow='1'   
        WHERE bpk_account_credit_detail.visit_id='113010509184038401' GROUP BY verify_spid ORDER BY paid DESC ;


        SELECT (SELECT description FROM base_service_point WHERE base_service_point_id=overify_spid), overify_spid, SUM(wlinepaid) paid FROM bpk_account_credit_detail 
            INNER JOIN base_service_point ON bpk_account_credit_detail.overify_spid=base_service_point.base_service_point_id 
                                                  AND base_service_point.fix_service_point_group_id IN ('1', '2', '9', '11') 
                                                  AND base_service_point.description NOT LIKE '%ผ่าตัด%' 
                                                  AND base_service_point.description NOT LIKE '% ER%'
                                                  AND base_service_point.description NOT LIKE '%โภช%'
                                                  AND base_service_point.description NOT LIKE '%กายภาพ%'
            INNER JOIN base_department ON base_department.base_department_id=base_service_point.base_department_id 
            INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                  AND bpk_department_services.is_revenue_allow='1'   
        WHERE bpk_account_credit_detail.visit_id='113010509184038401' GROUP BY overify_spid ORDER BY paid DESC

SELECT (SELECT description FROM base_service_point WHERE base_service_point_id=next_location_spid), * FROM visit_queue WHERE visit_id='115050701441468201'

SELECT visit_id, financial_discharge_date FROM bpk_account_credit_detail WHERE vn='109/130858' 

SELECT (SELECT COALESCE(description, ') FROM base_service_point WHERE base_service_point_id=verify_spid), verify_spid, SUM(wlinepaid) paid FROM bpk_account_credit_detail 
WHERE visit_id='115050701441468201' AND financial_discharge_date='2015-06-08' GROUP BY verify_spid ORDER BY paid DESC 

SELECT (SELECT COALESCE(description, ') FROM base_service_point WHERE base_service_point_id=verify_spid), verify_spid, SUM(wlinepaid) paid FROM bpk_account_credit_detail 
WHERE visit_id='115081307390919801' AND financial_discharge_date='2015-08-16' GROUP BY verify_spid ORDER BY paid DESC 

SELECT (SELECT COALESCE(description, ') FROM base_service_point WHERE base_service_point_id=next_location_spid) sent_to, 
bpkget_employee_name(assign_operate_eid) sent_by, 
COALESCE((SELECT COALESCE(description, ') FROM base_service_point WHERE base_service_point_id=assign_location_spid), ') sent_from, 
* FROM visit_queue WHERE visit_id='115081307390919801'
ORDER BY next_location_date, next_location_time   

SELECT * FROM base_service_point WHERE base_department_id='B0005'
SELECT * FROM base_service_point WHERE fix_service_point_group_id='9'

SELECT (SELECT description FROM base_department WHERE base_department.base_department_id=base_service_point.base_department_id), 
(SELECT is_revenue_allow FROM bpk_department_services WHERE bpk_department_services.base_department_id=base_service_point.base_department_id) is_revenue_allow,
* 
FROM base_service_point WHERE description LIKE 'จุดซักประวัติต่างประเทศ (ICS)'
GO

SELECT * FROM base_department WHERE description LIKE '%โภช%' OR description LIKE '%รังสี%' OR description LIKE '%ปฏิ%' 
GO 

SELECT * FROM bpk_department_services WHERE base_department_id IN (SELECT base_department_id FROM base_department WHERE description LIKE '%โภช%' OR description LIKE '%รังสี%' OR description LIKE '%ปฏิ%')
GO

SELECT * FROM bpk_department_services WHERE id='1'
UPDATE bpk_department_services SET is_revenue_allow='0' WHERE id='1'


        SELECT next_location_spid FROM visit_queue 
            INNER JOIN visit ON visit_queue.visit_id=visit.visit_id 
            INNER JOIN base_service_point ON visit_queue.next_location_spid=base_service_point.base_service_point_id 
            INNER JOIN base_department ON base_service_point.base_department_id=base_department.base_department_id 
            INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                  AND bpk_department_services.is_revenue_allow='1'   
            WHERE visit_queue.visit_id = '115050701441468201' 
            AND base_service_point.fix_service_point_group_id IN ('1', '2', '9', '11')
            AND base_service_point.description NOT LIKE '%ผ่าตัด%' 
            AND base_service_point.description NOT LIKE '% ER%' 
            ORDER BY next_location_date, next_location_time 

SELECT * FROM order_item WHERE unit_price_cost='' OR unit_price_cost IS NULL 
SELECT id, order_item_id, unit_price_cost FROM bpk_account_credit_detail WHERE fix_visit_type_id='1' AND financial_discharge_date LIKE '2015-02%' AND verify_deptid='F0027' ORDER BY unit_price_cost DESC 

SELECT * FROM order_item WHERE order_item_id='114121116334033001' 

UPDATE item SET unit_price_cost='0' WHERE trim(unit_price_cost)='';
UPDATE item SET unit_price_cost='0' WHERE unit_price_cost='NaN';
UPDATE item SET unit_price_cost='0' WHERE isnumeric(unit_price_cost)=FALSE;

UPDATE stock_mgnt SET unit_price='0' WHERE trim(unit_price)='';
UPDATE stock_mgnt SET unit_price='0' WHERE unit_price='NaN';
UPDATE stock_mgnt SET unit_price='0' WHERE isnumeric(unit_price)=FALSE;

UPDATE order_item SET unit_price_cost='0' WHERE trim(unit_price_cost)='';
UPDATE order_item SET unit_price_cost='0' WHERE unit_price_cost='NaN';
UPDATE order_item SET unit_price_cost='0' WHERE isnumeric(unit_price_cost)=FALSE;

SELECT * FROM order_item WHERE isnumeric(unit_price_cost)=FALSE;

SELECT * FROM bpk_account_credit_detail LIMIT 10 

UPDATE bpk_account_credit_detail 
SET unit_price_cost = CAST(order_item.unit_price_cost AS FLOAT), 
linecost =  CAST(order_item.unit_price_cost AS FLOAT)*bpk_account_credit_detail.quantity 
FROM order_item 
WHERE bpk_account_credit_detail.order_item_id = order_item.order_item_id 
AND bpk_account_credit_detail.order_item_id IN ('915060523531137501', '114121116334033001', '115042118090896801', '115042118090898501', '115041109462721701')

-- AND isnumeric(bpk_account_credit_detail.unit_price_cost)=FALSE 

SELECT order_item_id, unit_price_cost, linecost FROM bpk_account_credit_detail ORDER BY unit_price_cost DESC LIMIT 100


