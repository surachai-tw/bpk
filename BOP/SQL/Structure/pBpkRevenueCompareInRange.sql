SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- ต้องดึงข้อมูลมาเทียบกัน โดยใช้ pBpkRevenueInRange
ALTER PROCEDURE pBpkRevenueCompareInRange 
(
	@V_BeginDateTarget varchar(10),
	@V_EndDateTarget varchar(10), 
	@V_BeginDatePast varchar(10), 
	@V_EndDatePast varchar(10)
) 
AS

BEGIN 

	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	BEGIN
	CREATE TABLE #TEMP_FIRST 
	(
		FromDate varchar(10), 
		ToDate varchar(10), 
		Daily nvarchar(255), 
		BPK FLOAT, 
		BPK1 FLOAT, 
		BPK3 FLOAT, 
		BPK8 FLOAT, 
		BPK9 FLOAT
	)
	INSERT INTO #TEMP_FIRST EXEC pBpkRevenueInRange @V_BeginDateTarget, @V_EndDateTarget

	CREATE TABLE #TEMP_SECOND 
	(
		FromDate varchar(10), 
		ToDate varchar(10), 
		Daily nvarchar(255), 
		BPK FLOAT, 
		BPK1 FLOAT, 
		BPK3 FLOAT, 
		BPK8 FLOAT, 
		BPK9 FLOAT
	)
	INSERT INTO #TEMP_SECOND EXEC pBpkRevenueInRange @V_BeginDatePast, @V_EndDatePast

	INSERT INTO bpk_daily_patient_revenue (FromDate, ToDate, Daily, BPK, [Growth BPK], BPK1, [Growth BPK1], BPK3, [Growth BPK3], BPK8, [Growth BPK8], BPK9, [Growth BPK9]) 
	SELECT 
	#TEMP_FIRST.FromDate, 
	#TEMP_FIRST.ToDate, 
	#TEMP_FIRST.Daily, 
	#TEMP_FIRST.BPK, 
	CASE WHEN #TEMP_SECOND.BPK<>0 THEN Round(100*(#TEMP_FIRST.BPK - #TEMP_SECOND.BPK)/#TEMP_SECOND.BPK, 2) ELSE 0 END [Growth BPK], 
	#TEMP_FIRST.BPK1, 
	CASE WHEN #TEMP_SECOND.BPK1<>0 THEN Round(100*(#TEMP_FIRST.BPK1 - #TEMP_SECOND.BPK1)/#TEMP_SECOND.BPK1, 2) ELSE 0 END [Growth BPK1], 
	#TEMP_FIRST.BPK3, 
	CASE WHEN #TEMP_SECOND.BPK3<>0 THEN Round(100*(#TEMP_FIRST.BPK3 - #TEMP_SECOND.BPK3)/#TEMP_SECOND.BPK3, 2) ELSE 0 END [Growth BPK3], 
	#TEMP_FIRST.BPK8, 
	CASE WHEN #TEMP_SECOND.BPK8<>0 THEN Round(100*(#TEMP_FIRST.BPK8 - #TEMP_SECOND.BPK8)/#TEMP_SECOND.BPK8, 2) ELSE 0 END [Growth BPK8], 
	#TEMP_FIRST.BPK9, 
	CASE WHEN #TEMP_SECOND.BPK9<>0 THEN Round(100*(#TEMP_FIRST.BPK9 - #TEMP_SECOND.BPK9)/#TEMP_SECOND.BPK9, 2) ELSE 0 END [Growth BPK9] 
	FROM #TEMP_FIRST 
	LEFT JOIN #TEMP_SECOND ON #TEMP_FIRST.Daily=#TEMP_SECOND.Daily

	END

	SELECT * FROM bpk_daily_patient_revenue WHERE FromDate = @V_BeginDateTarget AND ToDate = @V_EndDateTarget

END