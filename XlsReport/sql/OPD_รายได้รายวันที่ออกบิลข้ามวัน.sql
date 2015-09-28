SELECT "Visit Date", "Visit Time", "Discharge Date", "Discharge Time", "HN", "VN", "Patient", "Receive Date", "Receive Time", "Receipt No", "�����Һ�ԡ�� (BILLED)", "��ǹŴ", "��ǹŴ�����", "��ǹŴ�Ѵ���", "�ط��", "�Է�ԡ���ѡ�ҷ�����" 
FROM 
( 
    SELECT DISTINCT 
    accdebit.visit_date "Visit Date", 
    accdebit.visit_time "Visit Time", 
    accdebit.financial_discharge_date "Discharge Date", 
    accdebit.financial_discharge_time "Discharge Time", 
    accdebit.hn "HN", 
    accdebit.vn "VN", 
    accdebit.patient_name "Patient",     
    accdebit.receive_date "Receive Date", 
    accdebit.receive_time "Receive Time", 
    accdebit.receipt_number "Receipt No", 
    sale "�����Һ�ԡ�� (BILLED)",  
    discount "��ǹŴ",  
    special_discount "��ǹŴ�����",  
    decimal_discount "��ǹŴ�Ѵ���",  
    paid "�ط��",  
    accdebit.all_plan "�Է�ԡ���ѡ�ҷ�����", 
    acccredit.acccredit_visit_id 
    FROM bpk_account_debit_detail accdebit
    LEFT JOIN 
        (
            SELECT DISTINCT 
                visit_id AS acccredit_visit_id 
            FROM bpk_account_credit_detail 
            WHERE verify_date BETWEEN '$P!{dBeginDate}' AND '$P!{dEndDate}' 
        ) acccredit ON acccredit.acccredit_visit_id=accdebit.visit_id AND accdebit.receive_date BETWEEN '$P!{dBeginDate}' AND '$P!{dEndDate}' 
    WHERE accdebit.receive_date BETWEEN '$P!{dBeginDate}' AND '$P!{dEndDate}' AND accdebit.fix_visit_type_id='0' 
) tmp
WHERE tmp.acccredit_visit_id IS NULL 
ORDER BY tmp."Receive Date", tmp."Receive Time", tmp."Visit Date", tmp."Visit Time" 
