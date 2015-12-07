SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE pBpkRevenueInRange 
(
	@V_BeginDate varchar(10), 
	@V_EndDate varchar(10)
) 
AS

BEGIN 
-- Declare @V_BeginDate varchar(10) = '2015-01-01'
-- Declare @V_EndDate varchar(10) = '2015-01-31'

	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED                                          

	SELECT 
	@V_BeginDate [FromDate], 
	@V_EndDate [ToDate], 
	'1.Total OPD' Daily, 
	Count(DISTINCT visit_id) [BPK], 
	Count(DISTINCT (CASE WHEN bpk_id=1 THEN visit_id ELSE NULL END)) [BPK1], 
	Count(DISTINCT (CASE WHEN bpk_id=3 THEN visit_id ELSE NULL END)) [BPK3], 
	Count(DISTINCT (CASE WHEN bpk_id=8 THEN visit_id ELSE NULL END)) [BPK8], 
	Count(DISTINCT (CASE WHEN bpk_id=9 THEN visit_id ELSE NULL END)) [BPK9]
	FROM 
	bpk_account_credit_detail 
	WHERE 
	fix_visit_type_id=0 
	AND financial_discharge_date BETWEEN @V_BeginDate AND @V_EndDate  

	UNION 

	SELECT 
	@V_BeginDate [FromDate], 
	@V_EndDate [ToDate], 
	'2.OPD Revenue' Daily, 
	SUM(wlinepaid) [BPK], 
	SUM(CASE WHEN bpk_id=1 THEN wlinepaid ELSE 0 END) [BPK1], 
	SUM(CASE WHEN bpk_id=3 THEN wlinepaid ELSE 0 END) [BPK3], 
	SUM(CASE WHEN bpk_id=8 THEN wlinepaid ELSE 0 END) [BPK8], 
	SUM(CASE WHEN bpk_id=9 THEN wlinepaid ELSE 0 END) [BPK9]
	FROM 
	bpk_account_credit_detail 
	WHERE 
	fix_visit_type_id=0 
	AND financial_discharge_date BETWEEN @V_BeginDate AND @V_EndDate  
	AND base_plan_group_id NOT IN ('A', 'AB', 'AV', 'AW', 'B', 'D', 'F', 'VIP', 'SW')
 
	UNION 

	SELECT 
	@V_BeginDate [FromDate], 
	@V_EndDate [ToDate], 
	'3.OPD Revenue per visit' Daily, 
	CASE WHEN Count(DISTINCT visit_id)<>0 THEN 
		SUM(wlinepaid)/Count(DISTINCT visit_id) 
		ELSE 0 END [BPK], 
	CASE WHEN Count(DISTINCT (CASE WHEN bpk_id=1 THEN visit_id ELSE NULL END))<>0 THEN 
		SUM(CASE WHEN bpk_id=1 AND base_plan_group_id NOT IN ('A', 'AB', 'AV', 'AW', 'B', 'D', 'F', 'VIP', 'SW') THEN wlinepaid ELSE 0 END)/Count(DISTINCT (CASE WHEN bpk_id=1 THEN visit_id ELSE NULL END)) 
		ELSE 0 END [BPK1], 
	CASE WHEN Count(DISTINCT (CASE WHEN bpk_id=3 THEN visit_id ELSE NULL END))<>0 THEN 
		SUM(CASE WHEN bpk_id=3 AND base_plan_group_id NOT IN ('A', 'AB', 'AV', 'AW', 'B', 'D', 'F', 'VIP', 'SW') THEN wlinepaid ELSE 0 END)/Count(DISTINCT (CASE WHEN bpk_id=3 THEN visit_id ELSE NULL END)) 
		ELSE 0 END [BPK3], 
	CASE WHEN Count(DISTINCT (CASE WHEN bpk_id=8 THEN visit_id ELSE NULL END))<>0 THEN 
		SUM(CASE WHEN bpk_id=8 AND base_plan_group_id NOT IN ('A', 'AB', 'AV', 'AW', 'B', 'D', 'F', 'VIP', 'SW') THEN wlinepaid ELSE 0 END)/Count(DISTINCT (CASE WHEN bpk_id=8 THEN visit_id ELSE NULL END)) 
		ELSE 0 END [BPK8], 
	CASE WHEN Count(DISTINCT (CASE WHEN bpk_id=9 THEN visit_id ELSE NULL END))<>0 THEN 
		SUM(CASE WHEN bpk_id=9 AND base_plan_group_id NOT IN ('A', 'AB', 'AV', 'AW', 'B', 'D', 'F', 'VIP', 'SW') THEN wlinepaid ELSE 0 END)/Count(DISTINCT (CASE WHEN bpk_id=9 THEN visit_id ELSE NULL END)) 
		ELSE 0 END [BPK9]
	FROM 
	bpk_account_credit_detail 
	WHERE 
	fix_visit_type_id=0 
	AND financial_discharge_date BETWEEN @V_BeginDate AND @V_EndDate  
 
	UNION 


	SELECT 
	@V_BeginDate [FromDate], 
	@V_EndDate [ToDate], 
	'4.Total IPD' Daily, 
	Count(DISTINCT visit_id) [BPK], 
	Count(DISTINCT (CASE WHEN bpk_id=1 THEN visit_id ELSE NULL END)) [BPK1], 
	Count(DISTINCT (CASE WHEN bpk_id=3 THEN visit_id ELSE NULL END)) [BPK3], 
	Count(DISTINCT (CASE WHEN bpk_id=8 THEN visit_id ELSE NULL END)) [BPK8], 
	Count(DISTINCT (CASE WHEN bpk_id=9 THEN visit_id ELSE NULL END)) [BPK9]
	FROM 
	bpk_account_credit_detail 
	WHERE 
	fix_visit_type_id=1 
	AND financial_discharge_date BETWEEN @V_BeginDate AND @V_EndDate  

	UNION 

	SELECT 
	@V_BeginDate [FromDate], 
	@V_EndDate [ToDate], 
	'5.IPD Revenue' Daily, 
	SUM(wlinepaid) [BPK], 
	SUM(CASE WHEN bpk_id=1 THEN wlinepaid ELSE 0 END) [BPK1], 
	SUM(CASE WHEN bpk_id=3 THEN wlinepaid ELSE 0 END) [BPK3], 
	SUM(CASE WHEN bpk_id=8 THEN wlinepaid ELSE 0 END) [BPK8], 
	SUM(CASE WHEN bpk_id=9 THEN wlinepaid ELSE 0 END) [BPK9]
	FROM 
	bpk_account_credit_detail 
	WHERE 
	fix_visit_type_id=1 
	AND financial_discharge_date BETWEEN @V_BeginDate AND @V_EndDate  
	AND base_plan_group_id NOT IN ('A', 'AB', 'AV', 'AW', 'B', 'D', 'F', 'VIP', 'SW')
 
	UNION 

	SELECT 
	@V_BeginDate [FromDate], 
	@V_EndDate [ToDate], 
	'6.IPD Revenue per visit' Daily, 
	CASE WHEN Count(DISTINCT visit_id)<>0 THEN 
		SUM(wlinepaid)/Count(DISTINCT visit_id) 
		ELSE 0 END [BPK], 
	CASE WHEN Count(DISTINCT (CASE WHEN bpk_id=1 THEN visit_id ELSE NULL END))<>0 THEN 
		SUM(CASE WHEN bpk_id=1 AND base_plan_group_id NOT IN ('A', 'AB', 'AV', 'AW', 'B', 'D', 'F', 'VIP', 'SW') THEN wlinepaid ELSE 0 END)/Count(DISTINCT (CASE WHEN bpk_id=1 THEN visit_id ELSE NULL END)) 
		ELSE 0 END [BPK1], 
	CASE WHEN Count(DISTINCT (CASE WHEN bpk_id=3 THEN visit_id ELSE NULL END))<>0 THEN 
		SUM(CASE WHEN bpk_id=3 AND base_plan_group_id NOT IN ('A', 'AB', 'AV', 'AW', 'B', 'D', 'F', 'VIP', 'SW') THEN wlinepaid ELSE 0 END)/Count(DISTINCT (CASE WHEN bpk_id=3 THEN visit_id ELSE NULL END)) 
		ELSE 0 END [BPK3], 
	CASE WHEN Count(DISTINCT (CASE WHEN bpk_id=8 THEN visit_id ELSE NULL END))<>0 THEN 
		SUM(CASE WHEN bpk_id=8 AND base_plan_group_id NOT IN ('A', 'AB', 'AV', 'AW', 'B', 'D', 'F', 'VIP', 'SW') THEN wlinepaid ELSE 0 END)/Count(DISTINCT (CASE WHEN bpk_id=8 THEN visit_id ELSE NULL END)) 
		ELSE 0 END [BPK8], 
	CASE WHEN Count(DISTINCT (CASE WHEN bpk_id=9 THEN visit_id ELSE NULL END))<>0 THEN 
		SUM(CASE WHEN bpk_id=9 AND base_plan_group_id NOT IN ('A', 'AB', 'AV', 'AW', 'B', 'D', 'F', 'VIP', 'SW') THEN wlinepaid ELSE 0 END)/Count(DISTINCT (CASE WHEN bpk_id=9 THEN visit_id ELSE NULL END)) 
		ELSE 0 END [BPK9]
	FROM 
	bpk_account_credit_detail 
	WHERE 
	fix_visit_type_id=1 
	AND financial_discharge_date BETWEEN @V_BeginDate AND @V_EndDate  
 
	UNION 

	SELECT 
	@V_BeginDate [FromDate], 
	@V_EndDate [ToDate], 
	'7.Total Revenue' Daily, 
	SUM(wlinepaid) [BPK], 
	SUM(CASE WHEN bpk_id=1 THEN wlinepaid ELSE 0 END) [BPK1], 
	SUM(CASE WHEN bpk_id=3 THEN wlinepaid ELSE 0 END) [BPK3], 
	SUM(CASE WHEN bpk_id=8 THEN wlinepaid ELSE 0 END) [BPK8], 
	SUM(CASE WHEN bpk_id=9 THEN wlinepaid ELSE 0 END) [BPK9]
	FROM 
	bpk_account_credit_detail 
	WHERE 
	financial_discharge_date BETWEEN @V_BeginDate AND @V_EndDate  
	AND base_plan_group_id NOT IN ('A', 'AB', 'AV', 'AW', 'B', 'D', 'F', 'VIP', 'SW')

END