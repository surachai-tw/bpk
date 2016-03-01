SELECT 
Patient_ID patient_id, 
Original_HN hn, 
isnull(Title_TH,'') prename, 
isnull(FirstName_TH,'') firstname, 
isnull(LastName_TH,'') lastname, 
isnull(FirstName_ENG,'') firstname_en, 
isnull(LastName_ENG,'') lastname_en, 
fix_gender_id, 
BirthDateTime birthdate,
'1' birthdate_true,  
NationalityCode fix_nationality_id, 
Active active, 
CASE WHEN DeadDateTime IS NULL THEN '0' ELSE '1' END is_death, 
MakeDateTime register_date, 
'00:00:00' register_time 
FROM
(
SELECT	TOP 10000
        a.HN AS Patient_ID,
		a.HN AS Original_HN,
		a.HN AS HN,
		Convert(nvarchar(100),CASE WHEN (charindex('\', RIGHT(b.FirstName, len(b.FirstName) - 1)) > 0) THEN RIGHT(b.FirstName, (len(b.FirstName) - charindex('\', RIGHT(b.FirstName,len(b.FirstName) - 1))) - 1)
				WHEN b.InitialNameCode IS NOT NULL  THEN b.InitialNameCode
				WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 99 THEN '004'
				WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 1 THEN '004'
				WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 2 THEN '005'
				WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 3 THEN '004'
				WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 4 THEN '005'
				WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 5 THEN '005'
				WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 6 THEN '863'
				WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 7 THEN '863'
				WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 8 THEN '863'
				WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) < 15 THEN '002'
				WHEN b.InitialNameCode IS NULL AND a.Gender = 2 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 6 THEN '1009'
				WHEN b.InitialNameCode IS NULL AND a.Gender = 2 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 7 THEN '1009'
				WHEN b.InitialNameCode IS NULL AND a.Gender = 2 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 8 THEN '838'
				WHEN b.InitialNameCode IS NULL AND a.Gender = 2 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 THEN '003' 
				WHEN b.InitialNameCode IS NULL AND a.Gender = 2 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) < 15 THEN '001'
		END)AS Title_TH,
		Convert(nvarchar(100),CASE WHEN (charindex('\', RIGHT(c.FirstName, len(c.FirstName) - 1)) > 0) THEN RIGHT(c.FirstName, (len(c.FirstName) - charindex('\', RIGHT(c.FirstName,len(c.FirstName) - 1))) - 1)
				WHEN c.InitialNameCode IS NOT NULL  THEN c.InitialNameCode
				WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 99 THEN '014'
				WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 1 THEN '014'
				WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 2 THEN '005'
				WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 3 THEN '014'
				WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 4 THEN '005'
				WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 5 THEN '005'
				WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 6 THEN '153'
				WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 7 THEN '153'
				WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 8 THEN '153'
				WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) < 15 THEN '963'
				WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 6 THEN '10091'
				WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 7 THEN '10091'
				WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 8 THEN '838'
				WHEN c.InitialNameCode IS NULL AND a.Gender = 2 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 THEN '015' 
				WHEN c.InitialNameCode IS NULL AND a.Gender = 2 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) < 15 THEN '926'
		END)AS Title_ENG,
		CASE	WHEN (charindex('\', RIGHT(b.FirstName, len(b.FirstName) - 1)) > 0) THEN LEFT((RIGHT(b.FirstName,len(b.FirstName) - 1)), (charindex('\', RIGHT(b.FirstName, len(b.FirstName) - 1))) - 1)
				WHEN (charindex('\', RIGHT(b.FirstName, len(b.FirstName) - 1)) = 0) THEN RIGHT(b.FirstName,LEN(b.FirstName)-1) 
		END AS FirstName_TH,
		RIGHT(b.LastName,LEN(b.LastName)-1) AS LastName_TH,
		RIGHT(c.FirstName,LEN(c.FirstName)-1) AS FirstName_ENG,
		RIGHT(c.LastName,LEN(c.LastName)-1) AS LastName_ENG,
		CASE WHEN a.Gender = 1 THEN '2' WHEN a.Gender = 2 THEN '1' ELSE '3' END AS fix_gender_id,
		convert(nvarchar(MAX), (a.BirthDateTime), 23) AS BirthDateTime,
		a.NationalityCode AS NationalityCode,
		CASE WHEN a.DeadDateTime IS NULL AND a.FileDeletedDate IS NULL THEN '1' ELSE '0' END AS Active,
		convert(nvarchar(MAX), (a.DeadDateTime), 23) AS DeadDateTime,
		convert(nvarchar(MAX), (a.FileDeletedDate), 23) AS FileDeletedDate,
		convert(nvarchar(MAX), (a.MakeDateTime), 23) AS MakeDateTime

FROM	DNHOSPITAL.dbo.HNPAT_INFO AS a left join
		DNHOSPITAL.dbo.HNPAT_NAME AS b on a.HN = b.HN and b.SuffixSmall = 0 left join
		DNHOSPITAL.dbo.HNPAT_NAME AS c on a.HN = c.HN and c.SuffixSmall = 1 left join
		DNHOSPITAL.dbo.DNSYSCONFIG AS d ON b.InitialNameCode = d.Code AND d.CtrlCode = '10241' LEFT JOIN
		DNHOSPITAL.dbo.DNSYSCONFIG AS e ON c.InitialNameCode = e.Code AND e.CtrlCode = '10241'
) AS tmp 

