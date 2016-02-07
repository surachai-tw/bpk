Declare @V_StartDate varchar(10) = convert(varchar(10), '$P{BeginDate}', 120)
Declare @V_EndDate varchar(10) = convert(varchar(10), DateAdd(day, -1, DateAdd(month, 1, Convert(Date, @V_StartDate, 120))), 120)

Declare @V_PrevMonthStartDate varchar(10) = convert(varchar(10), DateAdd(month, -1, @V_StartDate), 120)
Declare @V_PrevMonthEndDate varchar(10) = convert(varchar(10), DateAdd(day, -1, Convert(Date, @V_StartDate, 120)), 120)

print @V_StartDate
print @V_EndDate
print @V_PrevMonthStartDate
print @V_PrevMonthEndDate

Exec pBpkRevenueCompareInRange @V_StartDate, @V_EndDate, @V_PrevMonthStartDate, @V_PrevMonthEndDate
