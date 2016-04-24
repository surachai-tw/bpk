CREATE OR REPLACE FUNCTION pyh_convert_bdate_to_cdate(bdmy text) RETURNS text 
LANGUAGE plpgsql 
AS '
DECLARE 
    bdmy ALIAS FOR $1;
    cymd VARCHAR(10) DEFAULT '''';
BEGIN 

    IF isnumeric(substr(bdmy, strposrev(bdmy, ''/'')+1)) AND isnumeric(substr(bdmy, strpos(bdmy, ''/'')+1, strposrev(bdmy, ''/'')-strpos(bdmy, ''/'')-1))
       AND isnumeric(substr(bdmy, 1, strpos(bdmy, ''/'')-1)) AND LENGTH(bdmy)<=10
       THEN 

    cymd := CAST(substr(bdmy, strposrev(bdmy, ''/'')+1) AS INTEGER)-543
    ||''-''||to_char(CAST(substr(bdmy, strpos(bdmy, ''/'')+1, strposrev(bdmy, ''/'')-strpos(bdmy, ''/'')-1) AS INTEGER), ''FM09'')
    ||''-''||to_char(CAST(substr(bdmy, 1, strpos(bdmy, ''/'')-1) AS INTEGER), ''FM09'');

    END IF;

    RETURN cymd;
END';