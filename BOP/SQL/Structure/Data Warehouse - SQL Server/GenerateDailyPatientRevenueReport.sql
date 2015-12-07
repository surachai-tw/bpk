Declare @V_StartDate varchar(10) = convert(varchar(10), '$P{BeginDate}', 120)
Declare @V_EndDate varchar(10) = convert(varchar(10), DateAdd(day, -1, Convert(Date, @V_StartDate, 120)), 120)

Exec pBpkRevenueCompareInRange @V_StartDate, @V_StartDate, @V_EndDate, @V_EndDate
