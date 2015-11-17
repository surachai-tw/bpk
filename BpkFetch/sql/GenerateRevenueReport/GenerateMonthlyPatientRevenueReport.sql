Declare @V_StartDate1 varchar(10) = convert(varchar(10), '2015-10-01', 120)
Declare @V_StartDate2 varchar(10) = convert(varchar(10), DateAdd(month, -1, Convert(Date, @V_StartDate1, 120)), 120)
Declare @V_EndDate1 varchar(10) = convert(varchar(10), DateAdd(day, -1, DateAdd(month, 1, Convert(Date, @V_StartDate1, 120))), 120)
Declare @V_EndDate2 varchar(10) = convert(varchar(10), DateAdd(day, -1, DateAdd(month, 1, Convert(Date, @V_StartDate2, 120))), 120)

print '@V_StartDate1 = '+@V_StartDate1
print '@V_EndDate1 = '+@V_EndDate1
print '@V_StartDate2 = '+@V_StartDate2
print '@V_EndDate2 = '+@V_EndDate2

Exec pBpkRevenueCompareInRange @V_StartDate1, @V_EndDate1, @V_StartDate2, @V_EndDate2
