UPDATE item SET unit_price_cost='0' WHERE trim(unit_price_cost)='';
UPDATE item SET unit_price_cost='0' WHERE unit_price_cost='NaN';
UPDATE item SET unit_price_cost='0' WHERE isnumeric(unit_price_cost)=FALSE;

UPDATE stock_mgnt SET unit_price='0' WHERE trim(unit_price)='';
UPDATE stock_mgnt SET unit_price='0' WHERE unit_price='NaN';
UPDATE stock_mgnt SET unit_price='0' WHERE isnumeric(unit_price)=FALSE;

UPDATE order_item SET unit_price_cost='0' WHERE trim(unit_price_cost)='' AND verify_date=Cast(CURRENT_DATE AS VARCHAR(10));
UPDATE order_item SET unit_price_cost='0' WHERE unit_price_cost='NaN' AND verify_date=Cast(CURRENT_DATE AS VARCHAR(10));
UPDATE order_item SET unit_price_cost='0' WHERE isnumeric(unit_price_cost)=FALSE AND verify_date=Cast(CURRENT_DATE AS VARCHAR(10));

