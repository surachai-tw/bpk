SELECT 
tmp.*, 
Round(CAST(tmp."Revenue"/tmp."CountVisit" AS NUMERIC), 2) "Average revenue per head"
FROM 
(
    SELECT 
        substr(acccredit.financial_discharge_date, 1, 7) "Year-Month", 
        CASE WHEN acccredit.fix_visit_type_id='0' THEN 'OPD' ELSE 'IPD' END "Type", 
        COALESCE(fix_nationality.description, 'N/A') "Nation", 
        SUM(wlinepaid) "Revenue", 
        Count(DISTINCT acccredit.visit_id) "CountVisit"
    FROM 
    bpk_account_credit_detail acccredit 
    INNER JOIN visit ON acccredit.visit_id = visit.visit_id 
    INNER JOIN patient ON visit.patient_id = patient.patient_id AND patient.active='1' 
    LEFT JOIN fix_nationality ON patient.fix_nationality_id = fix_nationality.fix_nationality_id 
    INNER JOIN visit_payment ON visit_payment.visit_id = visit.visit_id AND visit_payment.priority='1' 
               AND visit_payment.plan_id IN (SELECT plan_id FROM plan WHERE base_plan_group_id NOT IN ('A', 'AB', 'AV', 'AW', 'B', 'D', 'F', 'VIP', 'SW'))
    WHERE acccredit.financial_discharge_date BETWEEN '2015-01-01' AND '2015-10-31'
    GROUP BY acccredit.fix_visit_type_id, fix_nationality.description, substr(acccredit.financial_discharge_date, 1, 7)
) tmp 
ORDER BY "Year-Month", "Type", "Nation" COLLATE "th_TH"

