SELECT * FROM information_schema.columns WHERE column_name='item_id' AND is_updatable='YES' ORDER BY table_name 

SELECT fix_item_type_id, count(item_id) cnt FROM item WHERE item_id LIKE '%-%' GROUP BY fix_item_type_id
SELECT fix_item_type_id, count(item_id) cnt FROM item WHERE item_id LIKE '% %' GROUP BY fix_item_type_id
SELECT fix_item_type_id, count(item_id) cnt FROM item WHERE item_id LIKE '%.%' GROUP BY fix_item_type_id
SELECT fix_item_type_id, count(item_id) cnt FROM item WHERE item_id LIKE '%/%' GROUP BY fix_item_type_id


SELECT * FROM information_schema.columns WHERE column_name='item_id' AND is_updatable='YES' ORDER BY table_name 

SELECT * FROM bpk_track_delete_stock_card LIMIT 100 
SELECT min(delete_date) FROM bpk_track_delete_stock_card

DELETE FROM bpk_track_delete_stock_card WHERE delete_date='2016-04-30'

SELECT * FROM bpk_item_average_cost LIMIT 100 

DELETE FROM bpk_item_average_cost WHERE item_id NOT IN (SELECT item_id FROM item)

SELECT * FROM item_discount LIMIT 100 
SELECT * FROM item_discount WHERE item_id NOT IN (SELECT item_id FROM item) LIMIT 100 
SELECT count(item_discount_id) FROM item_discount WHERE item_id NOT IN (SELECT item_id FROM item)
SELECT * FROM item WHERE item_id='110082721563457001' 

SELECT * FROM item_discount WHERE item_id='' OR item_id IS NULL 

SELECT * FROM 
(
    SELECT item_discount.*, item.common_name FROM item_discount 
    LEFT JOIN item ON item_discount.item_id = item.item_id 
) tmp 
WHERE tmp.common_name IS NOT NULL 


BEGIN 

DELETE FROM item_discount WHERE item_id NOT IN (SELECT item_id FROM item)

ROLLBACK 
COMMIT 

SELECT * FROM stock_adjust LIMIT 100 

DELETE FROM stock_adjust WHERE item_id NOT IN (SELECT item_id FROM item)
DELETE FROM stock_adjust WHERE stock_id NOT IN (SELECT stock_id FROM stock)

SELECT count(*) FROM stock_adjust 
SELECT * FROM stock_adjust 
ROLLBACK 

SELECT * FROM itemwetcha 

SELECT * FROM nt_order LIMIT 100 

SELECT DISTINCT nt_order.visit_id, visit.vn FROM nt_order 
LEFT JOIN visit ON nt_order.visit_id=visit.visit_id 
ORDER BY nt_order.visit_id

SELECT count(nt_order_id) FROM nt_order WHERE visit_id NOT IN (SELECT visit_id FROM visit) 

SELECT count(nt_order_id) FROM nt_order WHERE order_item_id NOT IN (SELECT order_item_id FROM order_item) 

BEGIN 

DELETE FROM nt_order WHERE visit_id NOT IN (SELECT visit_id FROM visit) 

COMMIT

SELECT * FROM track_order_item LIMIT 100 

SELECT count(track_order_item_id) FROM track_order_item WHERE order_item_id NOT IN (SELECT order_item_id FROM order_item)
SELECT count(track_order_item_id) FROM track_order_item WHERE visit_id NOT IN (SELECT visit_id FROM visit)

SELECT count(*) FROM itemwetcha 
SELECT * FROM itemwetcha 

SELECT * FROM 
(
SELECT 'appointment_order_item', count(*) AS cnt FROM appointment_order_item UNION 
SELECT 'base_anes_airway', count(*) AS cnt FROM base_anes_airway UNION 
SELECT 'base_anes_anesthetic_agent', count(*) AS cnt FROM base_anes_anesthetic_agent UNION 
SELECT 'base_dt_item', count(*) AS cnt FROM base_dt_item UNION 
SELECT 'base_item_xray_film', count(*) AS cnt FROM base_item_xray_film UNION 
SELECT 'base_lab_thalas_item', count(*) AS cnt FROM base_lab_thalas_item UNION 
SELECT 'base_nt_supplement_type', count(*) AS cnt FROM base_nt_supplement_type UNION 
SELECT 'base_room_item', count(*) AS cnt FROM base_room_item UNION 
SELECT 'base_room_observe_item', count(*) AS cnt FROM base_room_observe_item UNION 
SELECT 'base_template_operation', count(*) AS cnt FROM base_template_operation UNION 
SELECT 'bb_base_product_pack_cell', count(*) AS cnt FROM bb_base_product_pack_cell UNION 
SELECT 'bpk_account_credit_detail', count(*) AS cnt FROM bpk_account_credit_detail UNION 
SELECT 'bpk_item_average_cost', count(*) AS cnt FROM bpk_item_average_cost UNION 
SELECT 'bpk_item_not_show_mar', count(*) AS cnt FROM bpk_item_not_show_mar UNION 
SELECT 'bpk_package_detail', count(*) AS cnt FROM bpk_package_detail UNION 
SELECT 'bpk_package_order_detail', count(*) AS cnt FROM bpk_package_order_detail UNION 
SELECT 'bpk_stock_card_his', count(*) AS cnt FROM bpk_stock_card_his UNION 
SELECT 'bpk_stock_mgnt_his', count(*) AS cnt FROM bpk_stock_mgnt_his UNION 
SELECT 'bpk_stock_mgnt_his_working', count(*) AS cnt FROM bpk_stock_mgnt_his_working UNION 
SELECT 'bpk_track_delete_stock_card', count(*) AS cnt FROM bpk_track_delete_stock_card UNION 
SELECT 'dental_order', count(*) AS cnt FROM dental_order UNION 
SELECT 'dental_plan_order', count(*) AS cnt FROM dental_plan_order UNION 
SELECT 'df_approve', count(*) AS cnt FROM df_approve UNION 
SELECT 'df_approve_tmp', count(*) AS cnt FROM df_approve_tmp UNION 
SELECT 'df_order_item', count(*) AS cnt FROM df_order_item UNION 
SELECT 'df_order_item_bak', count(*) AS cnt FROM df_order_item_bak UNION 
SELECT 'df_order_item_set', count(*) AS cnt FROM df_order_item_set UNION 
SELECT 'drug_specialist_only', count(*) AS cnt FROM drug_specialist_only UNION 
SELECT 'dt_treatment', count(*) AS cnt FROM dt_treatment UNION 
SELECT 'dt_treatment_external', count(*) AS cnt FROM dt_treatment_external UNION 
SELECT 'dt_treatment_plan', count(*) AS cnt FROM dt_treatment_plan UNION 
SELECT 'feature_order_short_key', count(*) AS cnt FROM feature_order_short_key UNION 
SELECT 'health_promotion_xray_result', count(*) AS cnt FROM health_promotion_xray_result UNION 
SELECT 'item', count(*) AS cnt FROM item UNION 
SELECT 'item_discount', count(*) AS cnt FROM item_discount UNION 
SELECT 'item_dispensed', count(*) AS cnt FROM item_dispensed UNION 
SELECT 'item_image', count(*) AS cnt FROM item_image UNION 
SELECT 'item_plan_group', count(*) AS cnt FROM item_plan_group UNION 
SELECT 'item_price', count(*) AS cnt FROM item_price UNION 
SELECT 'item_set', count(*) AS cnt FROM item_set UNION 
SELECT 'itemwetcha', count(*) AS cnt FROM itemwetcha UNION 
SELECT 'lab_result', count(*) AS cnt FROM lab_result UNION 
SELECT 'lis_order', count(*) AS cnt FROM lis_order UNION 
SELECT 'mig_bfw', count(*) AS cnt FROM mig_bfw UNION 
SELECT 'mig_repair_bfw', count(*) AS cnt FROM mig_repair_bfw UNION 
SELECT 'nt_order', count(*) AS cnt FROM nt_order UNION 
SELECT 'order_continue', count(*) AS cnt FROM order_continue UNION 
SELECT 'order_continue_prepared', count(*) AS cnt FROM order_continue_prepared UNION 
SELECT 'order_drug_interaction', count(*) AS cnt FROM order_drug_interaction UNION 
SELECT 'order_item', count(*) AS cnt FROM order_item UNION 
SELECT 'order_set_multi_visit', count(*) AS cnt FROM order_set_multi_visit UNION 
SELECT 'order_set_multi_visit_child', count(*) AS cnt FROM order_set_multi_visit_child UNION 
SELECT 'patient_self_drug', count(*) AS cnt FROM patient_self_drug UNION 
SELECT 'plan_auto_order', count(*) AS cnt FROM plan_auto_order UNION 
SELECT 'plan_item_share_limit', count(*) AS cnt FROM plan_item_share_limit UNION 
SELECT 'prepack_item', count(*) AS cnt FROM prepack_item UNION 
SELECT 'receipt_order', count(*) AS cnt FROM receipt_order UNION 
SELECT 'report_group_item', count(*) AS cnt FROM report_group_item UNION 
SELECT 'return_drug', count(*) AS cnt FROM return_drug UNION 
SELECT 'stock_adjust', count(*) AS cnt FROM stock_adjust UNION 
SELECT 'stock_card', count(*) AS cnt FROM stock_card UNION 
SELECT 'stock_cssd', count(*) AS cnt FROM stock_cssd UNION 
SELECT 'stock_cssd_produce', count(*) AS cnt FROM stock_cssd_produce UNION 
SELECT 'stock_cssd_sub_item', count(*) AS cnt FROM stock_cssd_sub_item UNION 
SELECT 'stock_dispense', count(*) AS cnt FROM stock_dispense UNION 
SELECT 'stock_dispense_other_item', count(*) AS cnt FROM stock_dispense_other_item UNION 
SELECT 'stock_exchange', count(*) AS cnt FROM stock_exchange UNION 
SELECT 'stock_exchange_detail', count(*) AS cnt FROM stock_exchange_detail UNION 
SELECT 'stock_free_item', count(*) AS cnt FROM stock_free_item UNION 
SELECT 'stock_item_set', count(*) AS cnt FROM stock_item_set UNION 
SELECT 'stock_item_trade_name', count(*) AS cnt FROM stock_item_trade_name UNION 
SELECT 'stock_mgnt', count(*) AS cnt FROM stock_mgnt UNION 
SELECT 'stock_mgnt_period', count(*) AS cnt FROM stock_mgnt_period UNION 
SELECT 'stock_order', count(*) AS cnt FROM stock_order UNION 
SELECT 'stock_order_tmp', count(*) AS cnt FROM stock_order_tmp UNION 
SELECT 'stock_prepack_item', count(*) AS cnt FROM stock_prepack_item UNION 
SELECT 'stock_request', count(*) AS cnt FROM stock_request UNION 
SELECT 'stock_request_order', count(*) AS cnt FROM stock_request_order UNION 
SELECT 'stock_request_order_backup', count(*) AS cnt FROM stock_request_order_backup UNION 
SELECT 'stock_request_produce', count(*) AS cnt FROM stock_request_produce UNION 
SELECT 'stock_return', count(*) AS cnt FROM stock_return UNION 
SELECT 'stock_setup_order', count(*) AS cnt FROM stock_setup_order UNION 
SELECT 'stock_template_produce', count(*) AS cnt FROM stock_template_produce UNION 
SELECT 'stock_used_in_stock_or_produce', count(*) AS cnt FROM stock_used_in_stock_or_produce UNION 
SELECT 'template_appointment_order', count(*) AS cnt FROM template_appointment_order UNION 
SELECT 'template_lab_item', count(*) AS cnt FROM template_lab_item UNION 
SELECT 'template_lab_item_tube', count(*) AS cnt FROM template_lab_item_tube UNION 
SELECT 'template_lab_type', count(*) AS cnt FROM template_lab_type UNION 
SELECT 'template_nt_default_order', count(*) AS cnt FROM template_nt_default_order UNION 
SELECT 'track_df_approve', count(*) AS cnt FROM track_df_approve UNION 
SELECT 'track_order_item', count(*) AS cnt FROM track_order_item UNION 
SELECT 'track_stock_card', count(*) AS cnt FROM track_stock_card UNION 
SELECT 'xray_result', count(*) AS cnt FROM xray_result  
) tmp 
ORDER BY tmp.cnt DESC 
