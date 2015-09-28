SELECT 
    execute_date "�ѹ������", 
    execute_time "���ҷ�����", 
    verify_date "�ѹ������", 
    verify_time "���ҷ�����", 
    item_description "��¡���Թ���", 
    billing_description "Billing Group", 
    account_description "Account Group", 
    hn "HN", 
    vn "VN/AN", 
    patient_name "����-ʡ�� ����", 
    bpkget_employee_name(execute_eid) "������", 
    execute_by_service_point "��ѧ������", 
    execute_deptid "����Ἱ�������", 
    execute_by_department "Ἱ�������", 
    bpkget_employee_name(verify_eid) "���ͼ����� Order", 
    ordered_by_service_point "�ش������ Order", 
    verify_deptid "����Ἱ������� Order", 
    ordered_by_department "Ἱ������� Order", 
    quantity "�ӹǹ",  
    COALESCE((SELECT description_th FROM base_unit WHERE base_unit_id=acccredit.base_unit_id LIMIT 1), '') "˹���", 
    unit_price_cost "�Ҥҷع/˹���",
    (quantity*unit_price_cost) AS "��Ť���Ҥҷع", 
    unit_price_sale "�ҤҢ��/˹���", 
    (quantity*unit_price_sale) AS "��Ť���ҤҢ��"
FROM 
bpk_account_credit_detail acccredit 
WHERE 
acccredit.execute_date BETWEEN '$P!{dBeginDate}' AND '$P!{dEndDate}' 
AND acccredit.fix_visit_type_id='0' 
AND acccredit.fix_item_type_id IN ('0', '4')
ORDER BY execute_date, execute_time
