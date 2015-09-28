-- DROP TABLE bpk_fix_order_status;
CREATE TABLE bpk_fix_order_status 
(
    fix_order_status_id VARCHAR(255) PRIMARY KEY, 
    description VARCHAR(255), 
    display_order VARCHAR(2)
);
INSERT INTO bpk_fix_order_status VALUES('0', 'NOT_VERIFY', '00');
INSERT INTO bpk_fix_order_status VALUES('1', 'VERIFIED', '01');
INSERT INTO bpk_fix_order_status VALUES('2', 'EXECUTED', '02');
INSERT INTO bpk_fix_order_status VALUES('3', 'DISPENSED', '03');
INSERT INTO bpk_fix_order_status VALUES('4', 'REPORTED', '04');
INSERT INTO bpk_fix_order_status VALUES('5', 'RETURNED', '05');
INSERT INTO bpk_fix_order_status VALUES('6', 'REMAIN_REPORT', '06');
INSERT INTO bpk_fix_order_status VALUES('7', 'IGNORE_REPORT', '07');
INSERT INTO bpk_fix_order_status VALUES('8', 'SUBMIT_IGNORE', '08');
INSERT INTO bpk_fix_order_status VALUES('9', 'NOT_COMPLETE', '09');

-- DROP TABLE bpk_fix_receipt_status;
CREATE TABLE bpk_fix_receipt_status 
(
    fix_receipt_status_id VARCHAR(255) PRIMARY KEY, 
    description VARCHAR(255), 
    display_order VARCHAR(2)
);
INSERT INTO bpk_fix_receipt_status VALUES('0', 'CANCEL', '0');
INSERT INTO bpk_fix_receipt_status VALUES('1', 'PREPARE_CANCEL', '1');
INSERT INTO bpk_fix_receipt_status VALUES('2', 'NORMAL', '2');
-- SELECT * FROM bpk_fix_receipt_status

-- DROP TABLE bpk_fix_receipt_type;
CREATE TABLE bpk_fix_receipt_type  
(
    fix_receipt_type_id VARCHAR(255) PRIMARY KEY, 
    description VARCHAR(255), 
    display_order VARCHAR(2)
);
INSERT INTO bpk_fix_receipt_type VALUES('1', 'GENERAL', '01');
INSERT INTO bpk_fix_receipt_type VALUES('2', 'DEPOSIT_MEDICAL_EQUIPMENT', '02');
INSERT INTO bpk_fix_receipt_type VALUES('3', 'DEPOSIT_PAYMENT', '03');
INSERT INTO bpk_fix_receipt_type VALUES('4', 'RETURN_DEPOSIT_MEDICAL_EQUIPMENT', '04');
INSERT INTO bpk_fix_receipt_type VALUES('5', 'RETURN_DEPOSIT_PAYMENT', '05');
INSERT INTO bpk_fix_receipt_type VALUES('6', 'DEBT', '06');
INSERT INTO bpk_fix_receipt_type VALUES('7', 'INVOICE', '07');
INSERT INTO bpk_fix_receipt_type VALUES('8', 'FAX_CLAIM', '08');
INSERT INTO bpk_fix_receipt_type VALUES('9', 'RETURN_DRUG', '09');
INSERT INTO bpk_fix_receipt_type VALUES('10', 'INVOICE_COST', '10');
INSERT INTO bpk_fix_receipt_type VALUES('11', 'INVOICE_DEBT', '11');
INSERT INTO bpk_fix_receipt_type VALUES('70', 'DEBT_ZERO', '12');
INSERT INTO bpk_fix_receipt_type VALUES('71', 'PAY_INVOICE', '13');
INSERT INTO bpk_fix_receipt_type VALUES('72', 'PAY_INVOICE_MERGE_DETAIL', '14');
-- SELECT * FROM bpk_fix_receipt_type

-- SELECT * FROM base_department 
-- DROP FUNCTION bpkget_department_description_by_spid(id text);
CREATE FUNCTION bpkget_department_description_by_spid(id text) RETURNS text 
LANGUAGE plpgsql 
AS '
DECLARE 
    id ALIAS FOR $1;
    rec RECORD;
    rec_desc VARCHAR(255):= '''';
BEGIN 
    SELECT INTO rec TRIM(COALESCE(dept.description, '''')) AS description FROM base_service_point sp 
    LEFT JOIN base_department dept ON sp.base_department_id=dept.base_department_id 
    WHERE base_service_point_id=id;

    rec_desc := COALESCE(rec.description, '''');

    RETURN rec_desc;
END';
-- SELECT bpkget_department_description_by_spid('001');

-- DROP FUNCTION bpkget_department_id_by_spid(id text);
CREATE FUNCTION bpkget_department_id_by_spid(id text) RETURNS text 
LANGUAGE plpgsql 
AS '
DECLARE 
    id ALIAS FOR $1;
    rec RECORD;
    rec_desc VARCHAR(255):= '''';
BEGIN 
    SELECT INTO rec TRIM(COALESCE(sp.base_department_id, '''')) AS deptid FROM base_service_point sp 
    WHERE base_service_point_id=id;

    rec_desc := COALESCE(rec.deptid, '''');

    RETURN rec_desc;
END';
-- SELECT bpkget_department_id_by_spid('001');

DROP TABLE bpk_account_group;
CREATE TABLE bpk_account_group 
(
    id VARCHAR(255) PRIMARY KEY, 
    description VARCHAR(255)
);

-- SELECT * FROM bpk_account_group 
-- SELECT * FROM base_drug_type 
-- SELECT * FROM base_billing_group WHERE acc_group='' OR acc_group IS NULL 

-- DROP TABLE bpk_department_services;
CREATE TABLE bpk_department_services 
(
    id SERIAL PRIMARY KEY, 
    base_department_id VARCHAR(255) UNIQUE, 
    is_revenue_allow VARCHAR(255) DEFAULT '1' 
);
INSERT INTO bpk_department_services (base_department_id) SELECT base_department_id FROM base_department ORDER BY base_department_id 
SELECT * FROM base_department ORDER BY base_department_id;
-- UPDATE bpk_department_services SET is_revenue_allow='0' WHERE 
-- base_department_id IN 
-- ('B0003', 'B0004', 'B0006', 'B0007', 'B0008', 'B0009', 'B0010', 'B0011', 'B0012', 'B0014', 'B0015', 'B0016', 'B0017', 'B0018', 'B0020', 'F0042', 'F0044', 'F0052', 'F0054', 'F0055', 'F0057', 'F0058', 'F0059', 'F0064', 'F0066', 'F0067', 'F0078', 'F0089', 'F0093', 'F0094', 'S0001')

-- bpkget_bop_change_service_point_id
-- To change service point that allow to have revenue 
-- DROP FUNCTION bpkget_bop_change_service_point_id(visit_id text, spid text, visit_datetime text, order_item_id text);
CREATE OR REPLACE FUNCTION bpkget_bop_change_service_point_id(visit_id text, spid text, visit_datetime text, order_item_id text) RETURNS text 
LANGUAGE plpgsql 
AS '
DECLARE 
    p_visit_id ALIAS FOR $1;
    p_spid ALIAS FOR $2;
    p_datetime ALIAS FOR $3; -- for identified date time 
    p_order_item_id ALIAS FOR $4; -- for check return case
    v_fix_visit_type_id VARCHAR(255):=''0'';
    v_is_return BOOLEAN;
    rec RECORD;
    rec_id VARCHAR(255):='''';
    rec_desc VARCHAR(255):= '''';
    location_type VARCHAR(255):='''';
    chk_acc_group VARCHAR(255):='''';
    visit_location_id VARCHAR(255):='''';
    food_location_id VARCHAR(255):='''';
    support_location_id VARCHAR(255):='''';
    order_datetime VARCHAR(255):='''';
BEGIN 
    
    -- If Item is Lab, X-ray, Nutrition --> Return to service point 
    SELECT DISTINCT INTO rec acc_group FROM order_item INNER JOIN base_billing_group ON order_item.base_billing_group_id=base_billing_group.base_billing_group_id 
        AND base_billing_group.acc_group IN (''03'', ''04'', ''06'', ''13'') AND order_item.order_item_id = p_order_item_id;
    chk_acc_group := rec.acc_group;
    IF chk_acc_group IS NOT NULL AND chk_acc_group<>'''' THEN 
        IF chk_acc_group = ''03'' THEN 
            SELECT INTO rec base_service_point_id FROM base_service_point WHERE fix_service_point_group_id=''5'' ORDER BY base_service_point_id LIMIT 1;
            support_location_id := rec.base_service_point_id;
            IF support_location_id IS NOT NULL AND support_location_id<>'''' THEN 
                RETURN support_location_id;
            END IF;
        ELSEIF chk_acc_group = ''04'' THEN 
            SELECT INTO rec base_service_point_id FROM base_service_point WHERE fix_service_point_group_id=''6'' ORDER BY base_service_point_id LIMIT 1;
            support_location_id := rec.base_service_point_id;
            IF support_location_id IS NOT NULL AND support_location_id<>'''' THEN 
                RETURN support_location_id;
            END IF;
        ELSEIF chk_acc_group = ''06'' THEN 
            -- 
            SELECT INTO rec base_service_point_id FROM base_service_point WHERE description LIKE ''%กายภาพ%'' ORDER BY base_service_point_id LIMIT 1;
            support_location_id := rec.base_service_point_id;
            IF support_location_id IS NOT NULL AND support_location_id<>'''' THEN 
                RETURN support_location_id;
            END IF;
        ELSEIF chk_acc_group = ''13'' THEN 
            SELECT INTO rec visit.visit_spid FROM visit INNER JOIN base_service_point ON 
                    visit.visit_spid = base_service_point.base_service_point_id 
                    AND base_service_point.fix_service_point_group_id<>''0'' 
                    AND base_service_point.fix_service_point_group_id<>''10'' 
                    WHERE visit.visit_id = p_visit_id LIMIT 1;
            visit_location_id := rec.visit_spid;
            
            SELECT INTO rec order_item.verify_spid FROM order_item INNER JOIN item ON 
                    order_item.item_id=item.item_id AND item.item_code ILIKE ''FD%'' AND order_item.order_item_id = p_order_item_id LIMIT 1;
            food_location_id := rec.verify_spid;  

            -- Automatic key
            -- Visti SpId and Order SpId is the same
            -- Food Item (item code in front of FD)
            IF lower(p_spid) = ''imed'' OR 
               visit_location_id = p_spid OR 
               (food_location_id IS NOT NULL AND food_location_id<>'''') THEN 
                SELECT INTO rec base_service_point_id FROM base_service_point WHERE description LIKE ''%โภช%'' ORDER BY base_service_point_id LIMIT 1;
                support_location_id := rec.base_service_point_id;
                IF support_location_id IS NOT NULL AND support_location_id<>'''' THEN 
                    RETURN support_location_id;
                END IF;
            END IF;
        END IF;
    END IF;

    -- Check visit type
    SELECT INTO rec fix_visit_type_id FROM visit WHERE visit.visit_id = p_visit_id;
    v_fix_visit_type_id := rec.fix_visit_type_id; 

    -- Check is return drug
    SELECT INTO rec (CASE WHEN quantity LIKE ''-%'' THEN TRUE ELSE FALSE END) is_return FROM order_item WHERE order_item.order_item_id = p_order_item_id;
    v_is_return := rec.is_return; 

    -- Check Is order by automatic task ?
    IF v_fix_visit_type_id=''1'' AND lower(p_spid) = ''imed'' THEN 
        SELECT INTO rec bed_management.base_service_point_id FROM bed_management INNER JOIN admit ON bed_management.admit_id=admit.admit_id WHERE admit.visit_id = p_visit_id AND p_datetime BETWEEN bed_management.move_date||'' ''||bed_management.move_time AND CASE WHEN bed_management.move_out_date<>'''' THEN bed_management.move_out_date||'' ''||bed_management.move_out_time ELSE CAST(CURRENT_TIMESTAMP AS VARCHAR(19)) END ORDER BY bed_management.modify_date DESC, bed_management.modify_time DESC LIMIT 1;
        rec_id := COALESCE(rec.base_service_point_id, p_spid);
        IF rec_id<>''imed'' AND rec_id<>p_spid AND rec_id<>'''' THEN 
            RETURN rec_id; 
        ELSE
            SELECT INTO rec bed_management.base_service_point_id FROM bed_management INNER JOIN admit ON bed_management.admit_id=admit.admit_id WHERE admit.visit_id = p_visit_id ORDER BY bed_management.modify_date DESC, bed_management.modify_time DESC LIMIT 1;
            IF rec.base_service_point_id IS NOT NULL AND rec.base_service_point_id<>'''' THEN 
                RETURN rec.base_service_point_id;
            ELSE 
                SELECT INTO rec next_location_spid FROM visit_queue 
                    INNER JOIN visit ON visit_queue.visit_id=visit.visit_id 
                    INNER JOIN base_service_point ON visit_queue.next_location_spid=base_service_point.base_service_point_id 
                    INNER JOIN base_department ON base_service_point.base_department_id=base_department.base_department_id 
                    INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                          AND bpk_department_services.is_revenue_allow=''1''   
                    WHERE visit_queue.visit_id = p_visit_id 
                    AND base_service_point.fix_service_point_group_id<>''0'' AND base_service_point.fix_service_point_group_id<>''10''
                    ORDER BY next_location_date, next_location_time 
                    LIMIT 1;
                RETURN COALESCE(rec.next_location_spid, p_spid);
            END IF;
        END IF;
    END IF;    

    -- Check Is return drug ?
    IF v_fix_visit_type_id=''1'' AND v_is_return THEN                 

        SELECT INTO rec bed_management.base_service_point_id FROM bed_management INNER JOIN admit ON bed_management.admit_id=admit.admit_id WHERE admit.visit_id = p_visit_id AND p_datetime BETWEEN bed_management.move_date||'' ''||bed_management.move_time AND bed_management.move_out_date||'' ''||bed_management.move_out_time ORDER BY bed_management.modify_date DESC, bed_management.modify_time DESC LIMIT 1;
        rec_id := COALESCE(rec.base_service_point_id, p_spid);

        SELECT INTO rec fix_service_point_type_id FROM base_service_point WHERE base_service_point_id = rec_id;
        location_type := rec.fix_service_point_type_id;

        IF location_type=''1'' THEN 
            -- In case IPD, return anyway 
            RETURN rec_id;
        ELSEIF rec_id <> p_spid THEN
            -- If not IPD, check not equals with input then return 
            RETURN rec_id; 
        ELSE 
            -- Else check maximum qty then return 
            SELECT INTO rec ord.verify_spid FROM order_item ord INNER JOIN base_service_point ON ord.verify_spid=base_service_point.base_service_point_id INNER JOIN bpk_department_services ON base_service_point.base_department_id=bpk_department_services.base_department_id AND bpk_department_services.is_revenue_allow=''1'' WHERE CAST(ord.quantity AS FLOAT)>0 AND ord.visit_id = p_visit_id AND ord.item_id IN (SELECT item_id FROM order_item WHERE order_item.order_item_id = p_order_item_id) GROUP BY ord.verify_spid ORDER BY SUM(CAST(ord.quantity AS FLOAT)) DESC LIMIT 1;
            rec_id := COALESCE(rec.verify_spid, p_spid);
            IF rec_id <> p_spid THEN
                RETURN rec_id; 
            END IF;
        END IF;
    END IF;    

    IF v_fix_visit_type_id=''0'' AND v_is_return THEN 
        -- If OPD and in case of return drug 
        SELECT INTO rec next_location_spid FROM visit_queue 
            INNER JOIN visit ON visit_queue.visit_id=visit.visit_id 
            INNER JOIN base_service_point ON visit_queue.next_location_spid=base_service_point.base_service_point_id 
            INNER JOIN base_department ON base_service_point.base_department_id=base_department.base_department_id 
            INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                  AND bpk_department_services.is_revenue_allow=''1''   
            WHERE visit_queue.visit_id = p_visit_id 
            AND base_service_point.fix_service_point_group_id<>''0'' AND base_service_point.fix_service_point_group_id<>''10''
            ORDER BY next_location_date, next_location_time 
            LIMIT 1;
        RETURN COALESCE(rec.next_location_spid, p_spid);
    END IF;

    -- Check Order by Service Point is allow to has revenue 
    SELECT INTO rec bpk_department_services.is_revenue_allow FROM base_service_point 
        INNER JOIN base_department ON base_service_point.base_department_id=base_department.base_department_id 
        INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id         
        WHERE base_service_point.base_service_point_id = p_spid
        ORDER BY base_department.base_department_id 
        LIMIT 1;
    IF rec.is_revenue_allow=''1'' THEN 
        RETURN p_spid;
    END IF;

    -- If not allow to has revenue, check visit by, if the same then return anyway
    SELECT INTO rec visit.visit_spid FROM visit INNER JOIN base_service_point ON 
            visit.visit_spid = base_service_point.base_service_point_id 
            AND base_service_point.fix_service_point_group_id<>''0'' 
            AND base_service_point.fix_service_point_group_id<>''10'' 
            WHERE visit.visit_id = p_visit_id LIMIT 1;
    IF rec.visit_spid = p_spid THEN 
        RETURN p_spid;
    END IF;
    
    IF v_fix_visit_type_id=''1'' THEN 
        -- If IPD see current bed in range 
        SELECT INTO rec bed_management.base_service_point_id FROM bed_management INNER JOIN admit ON bed_management.admit_id=admit.admit_id WHERE admit.visit_id = p_visit_id AND p_datetime BETWEEN bed_management.move_date||'' ''||bed_management.move_time AND bed_management.move_out_date||'' ''||bed_management.move_out_time ORDER BY bed_management.modify_date DESC, bed_management.modify_time DESC LIMIT 1;
        IF rec.base_service_point_id IS NOT NULL AND rec.base_service_point_id<>'''' THEN 
            RETURN rec.base_service_point_id; 
        ELSE 
            SELECT INTO rec verify_date||'' ''||verify_time AS order_datetime FROM order_item WHERE order_item.order_item_id = p_order_item_id;
            order_datetime := rec.order_datetime;
            
            -- If order date time out of range, check with min move date if p_datetime less than min move date select first ward 
            SELECT INTO rec bed_management.base_service_point_id FROM bed_management INNER JOIN admit ON bed_management.admit_id=admit.admit_id WHERE admit.visit_id = p_visit_id AND order_datetime<=(bed_management.move_date||'' ''||bed_management.move_time) ORDER BY bed_management.modify_date, bed_management.modify_time LIMIT 1;
            IF rec.base_service_point_id IS NOT NULL AND rec.base_service_point_id<>'''' THEN
                RETURN rec.base_service_point_id;
            ELSE
                -- If order date time out of range, check with max move date if p_datetime more than max move date select last ward 
                SELECT INTO rec bed_management.base_service_point_id FROM bed_management INNER JOIN admit ON bed_management.admit_id=admit.admit_id WHERE admit.visit_id = p_visit_id AND order_datetime>=(bed_management.move_out_date||'' ''||bed_management.move_out_time) ORDER BY bed_management.modify_date DESC, bed_management.modify_time DESC LIMIT 1;
                RETURN COALESCE(rec.base_service_point_id, p_spid);                 
            END IF;
        END IF;
    ELSE 
        -- If not equals in all previous condition, Find first service point that revenue allow and return 
        SELECT INTO rec next_location_spid FROM visit_queue 
            INNER JOIN visit ON visit_queue.visit_id=visit.visit_id 
            INNER JOIN base_service_point ON visit_queue.next_location_spid=base_service_point.base_service_point_id 
            INNER JOIN base_department ON base_service_point.base_department_id=base_department.base_department_id 
            INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                  AND bpk_department_services.is_revenue_allow=''1''   
            WHERE visit_queue.visit_id = p_visit_id 
            AND base_service_point.fix_service_point_group_id<>''0'' AND base_service_point.fix_service_point_group_id<>''10''
            ORDER BY next_location_date, next_location_time 
            LIMIT 1;
        IF rec.next_location_spid IS NOT NULL AND rec.next_location_spid<>'''' THEN 
            RETURN rec.next_location_spid;
        END IF;
    END IF;

    -- In case of not found any department that allow from setup bpk_department_services, check again for Lab, X-ray, Rehab, Nutri to force to be allow dept
    SELECT INTO rec order_item.verify_spid FROM order_item 
        INNER JOIN (SELECT * FROM base_service_point WHERE  base_service_point.fix_service_point_group_id IN (''5'', ''6'') OR 
                      base_service_point.description LIKE ''%กายภาพ%'' OR 
                      base_service_point.description LIKE ''%โภช%'') AS sp ON order_item.verify_spid = sp.base_service_point_id 
        WHERE order_item.visit_id = p_visit_id ORDER BY order_item.verify_date, order_item.verify_time LIMIT 1;
    IF rec.verify_spid IS NOT NULL AND rec.verify_spid<>'''' THEN 
        RETURN rec.verify_spid;
    END IF;

    RETURN p_spid;

END';

-- To forward service point ward+OR to product  
-- DROP FUNCTION bpkget_bop_forward_service_point_id(visit_id text, spid text);
CREATE OR REPLACE FUNCTION bpkget_bop_forward_service_point_id(visit_id text, spid text, order_item_id text) RETURNS text 
LANGUAGE plpgsql 
AS '
DECLARE 
    p_visit_id ALIAS FOR $1;
    p_spid ALIAS FOR $2;
    p_order_item_id ALIAS FOR $3;
    rec RECORD;
    location_type VARCHAR(255):='''';
    is_visit_ipd bool:=FALSE;
    is_er bool:=FALSE;
    is_or bool:=FALSE;
    is_lab bool:=FALSE;
    is_xray bool:=FALSE;
    is_nutri bool:=FALSE;
    is_rehab bool:=FALSE;
    is_checkup bool:=FALSE;
    is_residence bool:=FALSE;
    is_happylonglife bool:=FALSE;
    is_spa bool:=FALSE;
    is_besmartcenter bool:=FALSE;
    weight_spid VARCHAR(255):='''';
    queue_spid VARCHAR(255):='''';
    ipdattd_spid VARCHAR(255):='''';
    attd_spid VARCHAR(255):='''';
    dx_spid VARCHAR(255):='''';
BEGIN 

    -- Check Visit Type Is IPD?
    SELECT INTO rec visit.fix_visit_type_id FROM visit WHERE visit.visit_id = p_visit_id;
    IF rec.fix_visit_type_id=''1'' THEN 
        is_visit_ipd := TRUE;
    END IF;

    -- Check Service Point Type Is IPD?
    SELECT INTO rec fix_service_point_type_id FROM base_service_point WHERE base_service_point_id = p_spid;
    location_type := rec.fix_service_point_type_id;

    -- Check Is ER?
    SELECT INTO rec description FROM base_service_point WHERE base_service_point_id = p_spid AND description LIKE ''% ER%'';
    IF rec.description IS NOT NULL THEN 
        is_er := TRUE;
    END IF;

    -- Check Is OR?
    SELECT INTO rec description FROM base_service_point WHERE base_service_point_id = p_spid AND description LIKE ''%ผ่าตัด%'';
    IF rec.description IS NOT NULL THEN 
        is_or := TRUE;
    END IF;

    -- Check Is Lab?
    SELECT INTO rec description FROM base_service_point WHERE base_service_point_id = p_spid AND fix_service_point_group_id=''5'';
    IF rec.description IS NOT NULL THEN 
        is_lab := TRUE;
    END IF;

    -- Check Is Xray?
    SELECT INTO rec description FROM base_service_point WHERE base_service_point_id = p_spid AND fix_service_point_group_id=''6'';
    IF rec.description IS NOT NULL THEN 
        is_xray := TRUE;
    END IF;

    -- Check Is Nutrition?
    SELECT INTO rec description FROM base_service_point WHERE base_service_point_id = p_spid AND description LIKE ''%โภช%'';
    IF rec.description IS NOT NULL THEN 
        is_nutri := TRUE;
    END IF;

    -- Check Is Rehab?
    SELECT INTO rec description FROM base_service_point WHERE base_service_point_id = p_spid AND description LIKE ''%กายภาพ%'';
    IF rec.description IS NOT NULL THEN 
        is_rehab := TRUE;
    END IF;

    -- Check Is Check up?
    SELECT INTO rec description FROM base_service_point WHERE base_service_point_id = p_spid 
        AND (base_service_point.description LIKE ''%ตรวจสุขภาพ%'' OR base_service_point.description ILIKE ''%Check%up%'');
    IF rec.description IS NOT NULL THEN 
        is_checkup := TRUE;
    END IF;

    -- Check Is Residence?
    SELECT INTO rec description FROM base_service_point WHERE base_service_point_id = p_spid 
        AND (base_service_point.description ILIKE ''%Residence%'');
    IF rec.description IS NOT NULL THEN 
        is_residence := TRUE;
    END IF;

    -- Check Is Happy Long Life?
    SELECT INTO rec description FROM base_service_point WHERE base_service_point_id = p_spid 
        AND base_department_id IN (SELECT base_department_id FROM base_department WHERE base_department.description ILIKE ''%Happy%Long%Life%'');
    IF rec.description IS NOT NULL THEN 
        is_happylonglife := TRUE;
    END IF;

    -- Check Is Spa?
    SELECT INTO rec description FROM base_service_point WHERE base_service_point_id = p_spid 
        AND (base_service_point.description LIKE ''%แผนไทยประยุก%'');
    IF rec.description IS NOT NULL THEN 
        is_spa := TRUE;
    END IF;

    -- Check Is Be Smart Center?
    SELECT INTO rec description FROM base_service_point WHERE base_service_point_id = p_spid 
        AND (base_service_point.description ILIKE ''%Be%Smart%Center%'');
    IF rec.description IS NOT NULL THEN 
        is_besmartcenter := TRUE;
    END IF;

    -- Check IPD and OR Service Point Forward BACK 
    IF is_visit_ipd AND (location_type=''1'' OR is_or OR is_er OR is_lab OR is_xray 
                         OR is_nutri OR is_rehab OR is_checkup OR is_residence 
                         OR is_happylonglife OR is_spa OR is_besmartcenter) 
        THEN 
        -- select by most PAID in service point that allow revenue  
        SELECT INTO rec verify_spid, SUM(wlinepaid) paid FROM bpk_account_credit_detail 
            INNER JOIN base_service_point ON bpk_account_credit_detail.verify_spid=base_service_point.base_service_point_id 
                                                  AND (base_service_point.fix_service_point_group_id IN (''1'', ''2'', ''9'', ''11'') 
                                                       OR base_service_point.description LIKE ''%LR%'') -- For LR set to IPD, but allow to has revenue
                                                  AND base_service_point.description NOT LIKE ''%ผ่าตัด%'' 
                                                  AND base_service_point.description NOT LIKE ''% ER%''
                                                  AND base_service_point.description NOT LIKE ''%โภช%''
                                                  AND base_service_point.description NOT LIKE ''%กายภาพ%''
                                                  AND (base_service_point.description NOT LIKE ''%ตรวจสุขภาพ%'' AND base_service_point.description NOT ILIKE ''%Check%up%'')
                                                  AND base_service_point.description NOT ILIKE ''%Residence%'' 
                                                  AND base_service_point.base_department_id NOT IN (SELECT base_department_id FROM base_department WHERE base_department.description ILIKE ''%Happy%Long%Life%'')
                                                  AND base_service_point.description NOT LIKE ''%แผนไทยประยุก%'' 
                                                  AND base_service_point.description NOT ILIKE ''%Be%Smart%Center%'' 
            INNER JOIN base_department ON base_department.base_department_id=base_service_point.base_department_id 
            INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                  AND bpk_department_services.is_revenue_allow=''1''   
        WHERE bpk_account_credit_detail.visit_id=p_visit_id GROUP BY verify_spid ORDER BY paid DESC LIMIT 1;
        weight_spid := rec.verify_spid;
        IF weight_spid IS NOT NULL AND weight_spid<>'''' THEN 
            RETURN weight_spid;
        END IF;

        -- If not equals in all previous condition, Find first service point that revenue allow and return 
        SELECT INTO rec next_location_spid FROM visit_queue 
            INNER JOIN visit ON visit_queue.visit_id=visit.visit_id 
            INNER JOIN base_service_point ON visit_queue.next_location_spid=base_service_point.base_service_point_id 
            INNER JOIN base_department ON base_service_point.base_department_id=base_department.base_department_id 
            INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                  AND bpk_department_services.is_revenue_allow=''1''   
            WHERE visit_queue.visit_id = p_visit_id 
            AND (base_service_point.fix_service_point_group_id IN (''1'', ''2'', ''9'', ''11'')
                 OR base_service_point.description LIKE ''%LR%'') -- For LR set to IPD, but allow to has revenue
            AND base_service_point.description NOT LIKE ''%ผ่าตัด%'' 
            AND base_service_point.description NOT LIKE ''% ER%'' 
            AND base_service_point.description NOT LIKE ''%โภช%''
            AND base_service_point.description NOT LIKE ''%กายภาพ%''
            AND (base_service_point.description NOT LIKE ''%ตรวจสุขภาพ%'' AND base_service_point.description NOT ILIKE ''%Check%up%'')
            AND base_service_point.description NOT ILIKE ''%Residence%'' 
            AND base_service_point.base_department_id NOT IN (SELECT base_department_id FROM base_department WHERE base_department.description ILIKE ''%Happy%Long%Life%'')
            AND base_service_point.description NOT LIKE ''%แผนไทยประยุก%'' 
            AND base_service_point.description NOT ILIKE ''%Be%Smart%Center%'' 
            ORDER BY next_location_date, next_location_time 
            LIMIT 1;
        queue_spid := rec.next_location_spid;
        IF queue_spid IS NOT NULL AND queue_spid<>'''' THEN
            RETURN queue_spid;
        ELSE 
            -- If not found in queue, find in ipd_attending_physician  
            SELECT INTO rec 
                employee.base_service_point_id 
                    FROM employee 
                    INNER JOIN base_service_point ON employee.base_service_point_id=base_service_point.base_service_point_id 
                    INNER JOIN base_department ON base_department.base_department_id=base_service_point.base_department_id 
                    INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                          AND bpk_department_services.is_revenue_allow=''1''   
                WHERE employee_id=(SELECT employee_id FROM ipd_attending_physician WHERE ipd_attending_physician.admit_id=(SELECT admit_id FROM admit WHERE admit.visit_id=p_visit_id LIMIT 1) ORDER BY priority, begin_date, begin_time LIMIT 1); 
            ipdattd_spid := rec.base_service_point_id;
            IF ipdattd_spid IS NOT NULL AND ipdattd_spid<>'''' THEN 
                RETURN ipdattd_spid;
            ELSE 
                -- If not found in queue+ipd_attending_physician, find in attending_physician 
                SELECT INTO rec 
                    employee.base_service_point_id 
                        FROM employee 
                        INNER JOIN base_service_point ON employee.base_service_point_id=base_service_point.base_service_point_id 
                        INNER JOIN base_department ON base_department.base_department_id=base_service_point.base_department_id 
                        INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                              AND bpk_department_services.is_revenue_allow=''1''   
                    WHERE employee_id=(SELECT employee_id FROM attending_physician WHERE attending_physician.visit_id=p_visit_id ORDER BY priority, begin_date, begin_time LIMIT 1); 
                attd_spid := rec.base_service_point_id;
                IF attd_spid IS NOT NULL AND attd_spid<>'''' THEN 
                    RETURN attd_spid;
                ELSE 
                    -- If not found in queue+ipd_attending_physician+attending_physician, find in diagnosis_icd10
                    SELECT INTO rec employee.base_service_point_id 
                        FROM employee 
                        INNER JOIN base_service_point ON employee.base_service_point_id=base_service_point.base_service_point_id 
                        INNER JOIN base_department ON base_department.base_department_id=base_service_point.base_department_id 
                        INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                              AND bpk_department_services.is_revenue_allow=''1''   
                    WHERE employee_id=(SELECT doctor_eid FROM diagnosis_icd10 WHERE diagnosis_icd10.visit_id=p_visit_id AND fix_diagnosis_type_id=''1'' ORDER BY diagnosis_icd10.modify_date, diagnosis_icd10.modify_time LIMIT 1);
                    dx_spid := rec.base_service_point_id;
                    IF dx_spid IS NOT NULL AND dx_spid<>'''' THEN 
                        RETURN dx_spid;
                    ELSE
                        -- If not found in queue+ipd_attending_physician+attending_physician+diagnosis_icd10 --> return input 
                        RETURN p_spid;
                    END IF;
                END IF;
            END IF;
        END IF;
    END IF; 

    -- If not allow to has revenue, check visit by, if the same then return anyway
    SELECT INTO rec visit.visit_spid FROM visit INNER JOIN base_service_point ON 
            visit.visit_spid = base_service_point.base_service_point_id 
            AND base_service_point.fix_service_point_group_id<>''0'' 
            AND base_service_point.fix_service_point_group_id<>''10'' 
            WHERE visit.visit_id = p_visit_id LIMIT 1;
    IF rec.visit_spid = p_spid THEN 
        RETURN p_spid;
    END IF;

    -- Check OPD and OR Service Point Forward BACK 
    IF NOT is_visit_ipd THEN 
        SELECT INTO rec order_item.verify_spid FROM order_item 
                INNER JOIN base_service_point ON order_item.verify_spid=base_service_point.base_service_point_id 
                                                  AND (base_service_point.fix_service_point_group_id IN (''1'', ''2'', ''9'', ''11'') 
                                                       OR base_service_point.description LIKE ''%LR%'') -- For LR set to IPD, but allow to has revenue
                                                  AND base_service_point.description NOT LIKE ''%ผ่าตัด%'' 
                                                  AND base_service_point.description NOT LIKE ''% ER%''
                                                  AND base_service_point.description NOT LIKE ''%โภช%''
                                                  AND base_service_point.description NOT LIKE ''%กายภาพ%''
                INNER JOIN base_department ON base_department.base_department_id=base_service_point.base_department_id 
                INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                  AND bpk_department_services.is_revenue_allow=''1''   
             WHERE order_item.order_item_id = p_order_item_id LIMIT 1;
         IF rec.verify_spid IS NOT NULL THEN 
            RETURN rec.verify_spid;
         END IF;

        -- select by most PAID in service point that allow revenue  
        SELECT INTO rec overify_spid, SUM(wlinepaid) paid FROM bpk_account_credit_detail 
            INNER JOIN base_service_point ON bpk_account_credit_detail.overify_spid=base_service_point.base_service_point_id 
                                                  AND (base_service_point.fix_service_point_group_id IN (''1'', ''2'', ''9'', ''11'') 
                                                       OR base_service_point.description LIKE ''%LR%'') -- For LR set to IPD, but allow to has revenue
                                                  AND base_service_point.description NOT LIKE ''%ผ่าตัด%'' 
                                                  AND base_service_point.description NOT LIKE ''% ER%''
                                                  AND base_service_point.description NOT LIKE ''%โภช%''
                                                  AND base_service_point.description NOT LIKE ''%กายภาพ%''
            INNER JOIN base_department ON base_department.base_department_id=base_service_point.base_department_id 
            INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                  AND bpk_department_services.is_revenue_allow=''1''   
        WHERE bpk_account_credit_detail.visit_id=p_visit_id GROUP BY overify_spid ORDER BY paid DESC LIMIT 1;
        weight_spid := rec.overify_spid;
        IF weight_spid IS NOT NULL AND weight_spid<>'''' THEN 
            RETURN weight_spid;
        END IF;

        -- If not equals in all previous condition, Find first service point that revenue allow and return 
        SELECT INTO rec next_location_spid FROM visit_queue 
            INNER JOIN visit ON visit_queue.visit_id=visit.visit_id 
            INNER JOIN base_service_point ON visit_queue.next_location_spid=base_service_point.base_service_point_id 
            INNER JOIN base_department ON base_service_point.base_department_id=base_department.base_department_id 
            INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                  AND bpk_department_services.is_revenue_allow=''1''   
            WHERE visit_queue.visit_id = p_visit_id 
                AND (base_service_point.fix_service_point_group_id IN (''1'', ''2'', ''9'', ''11'')
                     OR base_service_point.description LIKE ''%LR%'') -- For LR set to IPD, but allow to has revenue
                AND base_service_point.description NOT LIKE ''%ผ่าตัด%'' 
                AND base_service_point.description NOT LIKE ''% ER%'' 
                AND base_service_point.description NOT LIKE ''%โภช%''
                AND base_service_point.description NOT LIKE ''%กายภาพ%''
            ORDER BY next_location_date, next_location_time 
            LIMIT 1;
        queue_spid := rec.next_location_spid;
        IF queue_spid IS NOT NULL AND queue_spid<>'''' THEN
            RETURN queue_spid;
        ELSE 
            -- If not found in queue, find in ipd_attending_physician  
            SELECT INTO rec 
                employee.base_service_point_id 
                    FROM employee 
                    INNER JOIN base_service_point ON employee.base_service_point_id=base_service_point.base_service_point_id 
                    INNER JOIN base_department ON base_department.base_department_id=base_service_point.base_department_id 
                    INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                          AND bpk_department_services.is_revenue_allow=''1''   
                WHERE employee_id=(SELECT employee_id FROM ipd_attending_physician WHERE ipd_attending_physician.admit_id=(SELECT admit_id FROM admit WHERE admit.visit_id=p_visit_id LIMIT 1) ORDER BY priority, begin_date, begin_time LIMIT 1); 
            ipdattd_spid := rec.base_service_point_id;
            IF ipdattd_spid IS NOT NULL AND ipdattd_spid<>'''' THEN 
                RETURN ipdattd_spid;
            ELSE 
                -- If not found in queue+ipd_attending_physician, find in attending_physician 
                SELECT INTO rec 
                    employee.base_service_point_id 
                        FROM employee 
                        INNER JOIN base_service_point ON employee.base_service_point_id=base_service_point.base_service_point_id 
                        INNER JOIN base_department ON base_department.base_department_id=base_service_point.base_department_id 
                        INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                              AND bpk_department_services.is_revenue_allow=''1''   
                    WHERE employee_id=(SELECT employee_id FROM attending_physician WHERE attending_physician.visit_id=p_visit_id ORDER BY priority, begin_date, begin_time LIMIT 1); 
                attd_spid := rec.base_service_point_id;
                IF attd_spid IS NOT NULL AND attd_spid<>'''' THEN 
                    RETURN attd_spid;
                ELSE 
                    -- If not found in queue+ipd_attending_physician+attending_physician, find in diagnosis_icd10
                    SELECT INTO rec employee.base_service_point_id 
                        FROM employee 
                        INNER JOIN base_service_point ON employee.base_service_point_id=base_service_point.base_service_point_id 
                        INNER JOIN base_department ON base_department.base_department_id=base_service_point.base_department_id 
                        INNER JOIN bpk_department_services ON base_department.base_department_id=bpk_department_services.base_department_id 
                                                              AND bpk_department_services.is_revenue_allow=''1''   
                    WHERE employee_id=(SELECT doctor_eid FROM diagnosis_icd10 WHERE diagnosis_icd10.visit_id=p_visit_id AND fix_diagnosis_type_id=''1'' ORDER BY diagnosis_icd10.modify_date, diagnosis_icd10.modify_time LIMIT 1);
                    dx_spid := rec.base_service_point_id;
                    IF dx_spid IS NOT NULL AND dx_spid<>'''' THEN 
                        RETURN dx_spid;
                    ELSE
                        -- If not found in queue+ipd_attending_physician+attending_physician+diagnosis_icd10 --> return input 
                        RETURN p_spid;
                    END IF;
                END IF;
            END IF;
        END IF;
    END IF;

    -- In case of not found any department that allow from setup bpk_department_services, check again for 
    -- Lab, X-ray, Rehab, Nutri, Check up to force to be allow dept
    SELECT INTO rec order_item.verify_spid FROM order_item 
        INNER JOIN ( SELECT * FROM base_service_point WHERE  base_service_point.fix_service_point_group_id IN (''5'', ''6'') 
                        OR base_service_point.description LIKE ''%กายภาพ%'' 
                        OR base_service_point.description LIKE ''%โภช%'' 
                        OR base_service_point.description LIKE ''%ผ่าตัด%''  
                        OR base_service_point.description LIKE ''% ER%''  
                        OR base_service_point.description LIKE ''%ตรวจสุขภาพ%''  
                        OR base_service_point.description ILIKE ''%Check%up%''
                        OR base_service_point.description ILIKE ''%Residence%'' 
                        OR base_service_point.base_department_id IN (SELECT base_department_id FROM base_department WHERE base_department.description ILIKE ''%Happy%Long%Life%'')
                        OR base_service_point.description LIKE ''%แผนไทยประยุก%'' 
                        OR base_service_point.description ILIKE ''%Be%Smart%Center%'' 
                    ) AS sp ON order_item.verify_spid = sp.base_service_point_id 
        WHERE order_item.visit_id = p_visit_id ORDER BY order_item.verify_date, order_item.verify_time LIMIT 1;
    IF rec.verify_spid IS NOT NULL AND rec.verify_spid<>'''' THEN 
        RETURN rec.verify_spid;
    END IF;

    RETURN p_spid;

END';

-- DROP FUNCTION bpkget_employee_name(TEXT);
CREATE FUNCTION bpkget_employee_name(eid text) RETURNS text
LANGUAGE plpgsql
AS '
DECLARE
    eid ALIAS FOR $1;
    emp RECORD;
    emp_name VARCHAR(255):= '''';
BEGIN
    SELECT INTO emp TRIM(COALESCE(prename, '''')||'' ''||COALESCE(firstname, '''')||'' ''||COALESCE(lastname, '''')) AS newname FROM employee WHERE employee_id=eid;

    emp_name := emp.newname;

    RETURN emp_name;
END';

-- DROP FUNCTION bpkget_patient_name(patid text);
CREATE FUNCTION bpkget_patient_name(patid text) RETURNS text
LANGUAGE plpgsql
AS '
DECLARE
    patid ALIAS FOR $1;
    pat RECORD;
    pat_name VARCHAR(255):= '''';
BEGIN
    SELECT INTO pat TRIM(COALESCE(prename, '''')||'' ''||COALESCE(firstname, '''')||'' ''||COALESCE(lastname, '''')) AS newname FROM patient WHERE patient_id=patid;

    pat_name := pat.newname;

    RETURN pat_name;
END';

-- DROP FUNCTION bpkget_service_description(id text);
CREATE FUNCTION bpkget_service_description(id text) RETURNS text
LANGUAGE plpgsql
AS '
DECLARE
    id ALIAS FOR $1;
    rec RECORD;
    rec_desc VARCHAR(255):= '''';
BEGIN
    SELECT INTO rec TRIM(COALESCE(description, '''')) AS description FROM base_service_point WHERE base_service_point_id=id;

    rec_desc := COALESCE(rec.description, '''');

    RETURN rec_desc;
END';

