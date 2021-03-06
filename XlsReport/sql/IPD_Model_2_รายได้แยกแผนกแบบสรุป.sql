SELECT tmp.*, ROUND(CAST((tmp."�����Һ�ԡ��"-tmp."��ǹŴ"-tmp."�ʹ�ط��") AS NUMERIC), 2) "DIFF"
FROM 
(
    SELECT 
    pverify_deptid "����Ἱ�", pordered_by_department "����Ἱ�", 
    SUM(CASE WHEN account_id='01' THEN (quantity*unit_price_cost) ELSE 0 END)::FLOAT "�鹷ع��", 
    SUM(CASE WHEN account_id='02' THEN (quantity*unit_price_cost) ELSE 0 END)::FLOAT "�鹷ع�Ǫ�ѳ��", 
    SUM(CASE WHEN account_id<>'01' AND account_id<>'02' THEN (quantity*unit_price_cost) ELSE 0 END)::FLOAT "�鹷ع����", 
    SUM(CASE WHEN account_id='01' THEN acccredit.linesale ELSE 0 END)::FLOAT "�����", 
    SUM(CASE WHEN account_id='02' THEN acccredit.linesale ELSE 0 END)::FLOAT "����Ǫ�ѳ��",  
    SUM(CASE WHEN account_id='03' THEN acccredit.linesale ELSE 0 END)::FLOAT "Lab",  
    SUM(CASE WHEN account_id='04' THEN acccredit.linesale ELSE 0 END)::FLOAT "X-ray",  
    SUM(CASE WHEN account_id='05' THEN acccredit.linesale ELSE 0 END)::FLOAT "�ѹ�����",  
    SUM(CASE WHEN account_id='06' THEN acccredit.linesale ELSE 0 END)::FLOAT "����Ҿ",  
    SUM(CASE WHEN account_id='07' THEN acccredit.linesale ELSE 0 END)::FLOAT "��ҧ�",  
    SUM(CASE WHEN account_id='08' THEN acccredit.linesale ELSE 0 END)::FLOAT "����ػ�ó�",  
    SUM(CASE WHEN account_id='09' THEN acccredit.linesale ELSE 0 END)::FLOAT "���ᾷ��",  
    SUM(CASE WHEN account_id='10' THEN acccredit.linesale ELSE 0 END)::FLOAT "��Һ�ԡ�þ�Һ��",  
    SUM(CASE WHEN account_id='11' THEN acccredit.linesale ELSE 0 END)::FLOAT "��Һ�ԡ���ç��Һ��",  
    SUM(CASE WHEN account_id='12' THEN acccredit.linesale ELSE 0 END)::FLOAT "�����ͧ",  
    SUM(CASE WHEN account_id='13' THEN acccredit.linesale ELSE 0 END)::FLOAT "��������",  
    SUM(CASE WHEN account_id='14' THEN acccredit.linesale ELSE 0 END)::FLOAT "���Ҩ���",  
    SUM(CASE WHEN account_id='15' THEN acccredit.linesale ELSE 0 END)::FLOAT "����",  
    SUM(ROUND(CAST(acccredit.linesale AS NUMERIC), 2))::FLOAT "�����Һ�ԡ��", 
    SUM(ROUND(CAST(acccredit.wlinediscount AS NUMERIC), 2))::FLOAT "��ǹŴ", 
    SUM(ROUND(CAST(acccredit.wlinepaid AS NUMERIC), 2))::FLOAT "�ʹ�ط��"
    FROM bpk_account_credit_detail acccredit
    WHERE financial_discharge_date BETWEEN '$P!{dBeginDate}' AND '$P!{dEndDate}' AND fix_visit_type_id='1'
    GROUP BY pverify_deptid, pordered_by_department
) tmp 
ORDER BY "����Ἱ�" COLLATE "th_TH"
