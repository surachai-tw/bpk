package com.bpk.pgToSqlServer.dao;

import com.bpk.pgToSqlServer.dto.DiagnosisVO;
import com.bpk.pgToSqlServer.dto.PatientVO;
import com.bpk.pgToSqlServer.dto.VisitVO;
import com.bpk.pgToSqlServer.utility.Utility;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author surachai.tw
 */
public class SsbDAO extends SqlServerToPgDAO
{

    public SsbDAO()
    {
    }

    /** ขอรายการ Prefix ทั้งหมดจาก DNHOSPITAL.dbo.HNPAT_INFO.HN */
    public List listPrefix(int numDigit)
    {
        List list = new ArrayList();
        if (numDigit > 0)
        {
            Connection connSrc = this.getSourceConnection();
            Statement stmtSrc = null;
            ResultSet rsSrc = null;
            StringBuilder sqlCmd = null;

            try
            {
                sqlCmd = new StringBuilder("SELECT substring(HN, 1, ").append(numDigit).append(") yyy, count(HN) Cnt FROM DNHOSPITAL.dbo.HNPAT_INFO GROUP BY substring(HN, 1, ").append(numDigit).append(") ORDER BY substring(HN, 1, ").append(numDigit).append(")");
                stmtSrc = connSrc.createStatement();
                // Utility.printCoreDebug(this, sqlCmd.toString());
                rsSrc = stmtSrc.executeQuery(sqlCmd.toString());
                for (; rsSrc.next();)
                {
                    list.add(rsSrc.getString("yyy"));
                }
                rsSrc.close();
                stmtSrc.close();
                connSrc.close();
            } catch (Exception ex)
            {
                Utility.printCoreDebug(this, sqlCmd.toString());
                Utility.keepLog(sqlCmd.toString());
                ex.printStackTrace();
            } finally
            {
                connSrc = null;
                stmtSrc = null;
                rsSrc = null;
            }
        }
        return list;
    }

    /** ขอรายการ Prefix ทั้งหมดจาก DNHOSPITAL.dbo.HNPAT_INFO.HN */
    public List listHnByLastVisitDate(int numBackDate)
    {
        List list = new ArrayList();
        if (numBackDate > 0)
        {
            Connection connSrc = this.getSourceConnection();
            Statement stmtSrc = null;
            ResultSet rsSrc = null;
            StringBuilder sqlCmd = null;

            try
            {
                sqlCmd = new StringBuilder("SELECT HN FROM DNHOSPITAL.dbo.HNPAT_INFO WHERE Convert(varchar(10), LastVisitDateTime, 120) BETWEEN Convert(varchar(10), getdate()-").append(numBackDate).append(", 120) AND Convert(varchar(10), getdate(), 120) ORDER BY LastVisitDateTime DESC ");
                stmtSrc = connSrc.createStatement();
                // Utility.printCoreDebug(this, sqlCmd.toString());
                rsSrc = stmtSrc.executeQuery(sqlCmd.toString());
                for (; rsSrc.next();)
                {
                    list.add(rsSrc.getString("HN"));
                }
                rsSrc.close();
                stmtSrc.close();
                connSrc.close();
            } catch (Exception ex)
            {
                Utility.printCoreDebug(this, sqlCmd.toString());
                Utility.keepLog(sqlCmd.toString());
                ex.printStackTrace();
            } finally
            {
                connSrc = null;
                stmtSrc = null;
                rsSrc = null;
            }
        }
        return list;
    }

    /** List รายการ HN ทั้งหมด ที่นำหน้าด้วย Parameter */
    public List listPatientByHnWithPrefix(String prefix)
    {
        List list = new ArrayList();
        if (prefix != null)
        {
            Connection connSrc = this.getSourceConnection();
            Statement stmtSrc = null;
            ResultSet rsSrc = null;
            StringBuilder sqlCmd = new StringBuilder();

            try
            {
                sqlCmd.append(" SELECT ");
                sqlCmd.append(" Patient_ID patient_id, ");
                sqlCmd.append(" Original_HN hn, ");
                sqlCmd.append(" isnull(Title_TH,'') prename, ");
                sqlCmd.append(" isnull(FirstName_TH,'') firstname, ");
                sqlCmd.append(" isnull(LastName_TH,'') lastname, ");
                sqlCmd.append(" isnull(FirstName_ENG,'') firstname_en, ");
                sqlCmd.append(" isnull(LastName_ENG,'') lastname_en, ");
                sqlCmd.append(" fix_gender_id, ");
                sqlCmd.append(" BirthDateTime birthdate,");
                sqlCmd.append(" '1' birthdate_true,  ");
                sqlCmd.append(" NationalityCode fix_nationality_id, ");
                sqlCmd.append(" Active active, ");
                sqlCmd.append(" CASE WHEN DeadDateTime IS NULL THEN '0' ELSE '1' END is_death, ");
                sqlCmd.append(" MakeDateTime register_date, ");
                sqlCmd.append(" '00:00:00' register_time, ");
                sqlCmd.append(" LastUpdateDateTime update_date, ");
                sqlCmd.append(" '00:00:00' update_time ");
                sqlCmd.append(" FROM");
                sqlCmd.append(" (");
                sqlCmd.append("     SELECT	");
                sqlCmd.append("         a.HN AS Patient_ID,");
                sqlCmd.append(" 	a.HN AS Original_HN,");
                sqlCmd.append(" 	a.HN AS HN,");
                sqlCmd.append(" 	Convert(nvarchar(100),CASE WHEN (charindex('\', RIGHT(b.FirstName, len(b.FirstName) - 1)) > 0) THEN RIGHT(b.FirstName, (len(b.FirstName) - charindex('\', RIGHT(b.FirstName,len(b.FirstName) - 1))) - 1)");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NOT NULL  THEN b.InitialNameCode");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 99 THEN '004'");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 1 THEN '004'");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 2 THEN '005'");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 3 THEN '004'");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 4 THEN '005'");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 5 THEN '005'");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 6 THEN '863'");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 7 THEN '863'");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 8 THEN '863'");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) < 15 THEN '002'");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 2 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 6 THEN '1009'");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 2 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 7 THEN '1009'");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 2 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 8 THEN '838'");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 2 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 THEN '003' ");
                sqlCmd.append(" 		WHEN b.InitialNameCode IS NULL AND a.Gender = 2 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) < 15 THEN '001'");
                sqlCmd.append(" 		END)AS Title_TH,");
                sqlCmd.append(" 	Convert(nvarchar(100),CASE WHEN (charindex('\', RIGHT(c.FirstName, len(c.FirstName) - 1)) > 0) THEN RIGHT(c.FirstName, (len(c.FirstName) - charindex('\', RIGHT(c.FirstName,len(c.FirstName) - 1))) - 1)");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NOT NULL  THEN c.InitialNameCode");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 99 THEN '014'");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 1 THEN '014'");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 2 THEN '005'");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 3 THEN '014'");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 4 THEN '005'");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 5 THEN '005'");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 6 THEN '153'");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 7 THEN '153'");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 8 THEN '153'");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) < 15 THEN '963'");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 6 THEN '10091'");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 7 THEN '10091'");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 1 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 AND a.MaritalStatus = 8 THEN '838'");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 2 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) >= 15 THEN '015' ");
                sqlCmd.append(" 		WHEN c.InitialNameCode IS NULL AND a.Gender = 2 AND DATEDIFF(YEAR,a.BirthDateTime,GETDATE()) < 15 THEN '926'");
                sqlCmd.append(" 		END)AS Title_ENG,");
                sqlCmd.append(" 	CASE	WHEN (charindex('\', RIGHT(b.FirstName, len(b.FirstName) - 1)) > 0) THEN LEFT((RIGHT(b.FirstName,len(b.FirstName) - 1)), (charindex('\', RIGHT(b.FirstName, len(b.FirstName) - 1))) - 1)");
                sqlCmd.append(" 		WHEN (charindex('\', RIGHT(b.FirstName, len(b.FirstName) - 1)) = 0) THEN RIGHT(b.FirstName,LEN(b.FirstName)-1) ");
                sqlCmd.append(" 		END AS FirstName_TH,");
                sqlCmd.append(" 	RIGHT(b.LastName,LEN(b.LastName)-1) AS LastName_TH,");
                sqlCmd.append(" 	RIGHT(c.FirstName,LEN(c.FirstName)-1) AS FirstName_ENG,");
                sqlCmd.append(" 	RIGHT(c.LastName,LEN(c.LastName)-1) AS LastName_ENG,");
                sqlCmd.append(" 	CASE WHEN a.Gender = 1 THEN '2' WHEN a.Gender = 2 THEN '1' ELSE '3' END AS fix_gender_id,");
                sqlCmd.append(" 	convert(nvarchar(MAX), (a.BirthDateTime), 23) AS BirthDateTime,");
                sqlCmd.append(" 	a.NationalityCode AS NationalityCode,");
                sqlCmd.append(" 	CASE WHEN a.DeadDateTime IS NULL AND a.FileDeletedDate IS NULL THEN '1' ELSE '0' END AS Active,");
                sqlCmd.append(" 	convert(nvarchar(MAX), (a.DeadDateTime), 23) AS DeadDateTime,");
                sqlCmd.append(" 	convert(nvarchar(MAX), (a.FileDeletedDate), 23) AS FileDeletedDate,");
                sqlCmd.append(" 	convert(nvarchar(MAX), (a.MakeDateTime), 23) AS MakeDateTime,");
                sqlCmd.append("		convert(nvarchar(MAX), (a.LastUpdateDateTime), 23) AS LastUpdateDateTime");
                sqlCmd.append("     FROM	");
                sqlCmd.append("         DNHOSPITAL.dbo.HNPAT_INFO AS a left join");
                sqlCmd.append(" 	DNHOSPITAL.dbo.HNPAT_NAME AS b on a.HN = b.HN and b.SuffixSmall = 0 left join");
                sqlCmd.append(" 	DNHOSPITAL.dbo.HNPAT_NAME AS c on a.HN = c.HN and c.SuffixSmall = 1 left join");
                sqlCmd.append(" 	DNHOSPITAL.dbo.DNSYSCONFIG AS d ON b.InitialNameCode = d.Code AND d.CtrlCode = '10241' LEFT JOIN");
                sqlCmd.append(" 	DNHOSPITAL.dbo.DNSYSCONFIG AS e ON c.InitialNameCode = e.Code AND e.CtrlCode = '10241'");
                sqlCmd.append("     WHERE a.HN LIKE '").append(prefix).append("%'");
                sqlCmd.append(" ) AS tmp ");
                stmtSrc = connSrc.createStatement();
                // Utility.printCoreDebug(this, sqlCmd.toString());
                rsSrc = stmtSrc.executeQuery(sqlCmd.toString());
                for (; rsSrc.next();)
                {
                    PatientVO aPatientVO = new PatientVO();

                    aPatientVO.setPatientId(rsSrc.getString("patient_id"));
                    aPatientVO.setHn(rsSrc.getString("hn"));
                    aPatientVO.setPrename(rsSrc.getString("prename"));
                    aPatientVO.setFirstname(rsSrc.getString("firstname"));
                    aPatientVO.setLastname(rsSrc.getString("lastname"));
                    aPatientVO.setFirstnameEn(rsSrc.getString("firstname_en"));
                    aPatientVO.setLastnameEn(rsSrc.getString("lastname_en"));
                    aPatientVO.setFixGenderId(rsSrc.getString("fix_gender_id"));
                    aPatientVO.setBirthdate(rsSrc.getString("birthdate"));
                    aPatientVO.setBirthdateTrue(rsSrc.getString("birthdate_true"));
                    aPatientVO.setFixNationalityId(rsSrc.getString("fix_nationality_id"));
                    aPatientVO.setActive(rsSrc.getString("active"));
                    aPatientVO.setIsDeath(rsSrc.getString("is_death"));
                    aPatientVO.setRegisterDate(rsSrc.getString("register_date"));
                    aPatientVO.setRegisterTime(rsSrc.getString("register_time"));
                    aPatientVO.setUpdateDate(rsSrc.getString("update_date"));
                    aPatientVO.setUpdateTime(rsSrc.getString("update_time"));

                    list.add(aPatientVO);
                }
                rsSrc.close();
                stmtSrc.close();
                connSrc.close();
            } catch (Exception ex)
            {
                Utility.printCoreDebug(this, sqlCmd.toString());
                Utility.keepLog(sqlCmd.toString());
                ex.printStackTrace();
            } finally
            {
                connSrc = null;
                stmtSrc = null;
                rsSrc = null;
            }
        }
        return list;
    }

    /** List visit ทั้งหมดของแต่ละ HN */
    public List listVisitByHn(String hn)
    {
        List list = new ArrayList();
        if (hn != null)
        {
            Connection connSrc = this.getSourceConnection();
            Statement stmtSrc = null;
            ResultSet rsSrc = null;
            StringBuilder sqlCmd = new StringBuilder();

            try
            {
                sqlCmd.append(" SELECT	a.HN AS patient_id,");
                sqlCmd.append(" 	convert(nvarchar(MAX), (a.VisitDate), 112) + convert(varchar(3),a.VN) + convert(varchar(2),c.PrescriptionNo) AS visit_id,");
                sqlCmd.append(" 	convert(varchar(3),a.VN) AS original_vn,");
                sqlCmd.append(" 	convert(varchar(3),a.VN) + '/' + convert(varchar(2),c.PrescriptionNo) AS vn,");
                sqlCmd.append(" 	convert(nvarchar(MAX), (a.VisitDate), 112) AS visit_date,");
                sqlCmd.append(" 	convert(nvarchar(MAX), (c.MakeDateTime), 8) AS visit_time,");
                sqlCmd.append(" 	c.Doctor AS doctor_code,");
                sqlCmd.append(" 	CASE WHEN e.LocalName is not null THEN");
                sqlCmd.append(" 		(SELECT CASE WHEN (charindex('\' , RIGHT (e.localname , len(e.localname) - 1)) > 0) THEN RIGHT (e.localname , (len(e.localname) - charindex('\' , RIGHT (e.localname , len(e.localname) - 1))) - 1) + ' ' + LEFT ((RIGHT (e.localname , len(e.localname) - 1)) , (charindex('\' , RIGHT (e.localname , len(e.localname) - 1))) - 1) ELSE RIGHT (e.localname , len(e.localname) - 1) END AS Expr1)");
                sqlCmd.append(" 	ELSE ");
                sqlCmd.append(" 		(SELECT CASE WHEN (charindex('\' , RIGHT (e.englishname , len(e.englishname) - 1)) > 0) THEN RIGHT (e.englishname , (len(e.englishname) - charindex('\' , RIGHT (e.englishname , len(e.englishname) - 1))) - 1) + ' ' + LEFT ((RIGHT (e.englishname , len(e.englishname) - 1)) , (charindex('\' , RIGHT (e.englishname , len(e.englishname) - 1))) - 1) ELSE RIGHT (e.englishname , len(e.englishname) - 1) END AS Expr1)");
                sqlCmd.append(" 	END AS doctor,");
                sqlCmd.append(" 	CASE WHEN c.PrescriptionNo = 1 THEN a.AN END AS original_an,");
                sqlCmd.append(" 	CASE WHEN c.PrescriptionNo = 1 THEN a.AN END AS an,");
                sqlCmd.append(" 	CASE WHEN c.PrescriptionNo = 1 THEN convert(nvarchar(MAX), (b.AdmDateTime), 112) END AS admit_date,");
                sqlCmd.append(" 	CASE WHEN c.PrescriptionNo = 1 THEN convert(nvarchar(MAX), (b.AdmDateTime), 8) END AS admit_time,");
                sqlCmd.append(" 	CASE WHEN c.CloseVisitCode in ('999','006','004') THEN '0' ELSE '1' END AS active");
                sqlCmd.append(" FROM	DNHOSPITAL.dbo.HNOPD_MASTER AS a left join");
                sqlCmd.append(" 	DNHOSPITAL.dbo.HNIPD_MASTER AS b ON a.AN = b.AN left join");
                sqlCmd.append(" 	DNHOSPITAL.dbo.HNOPD_PRESCRIP AS c ON a.VisitDate = c.VisitDate and a.VN = c.VN left join");
                sqlCmd.append(" 	DNHOSPITAL.dbo.HNDOCTOR_MASTER AS e ON c.Doctor = e.Doctor");
                sqlCmd.append(" WHERE	a.HN = '").append(hn).append("' ");
                sqlCmd.append(" ORDER BY a.VisitDate, c.MakeDateTime");
                stmtSrc = connSrc.createStatement();
                Utility.printCoreDebug(this, sqlCmd.toString());
                rsSrc = stmtSrc.executeQuery(sqlCmd.toString());
                for (; rsSrc.next();)
                {
                    VisitVO aVisitVO = new VisitVO();

                    aVisitVO.setPatientId(rsSrc.getString("patient_id"));
                    aVisitVO.setHn(hn);
                    aVisitVO.setVisitId(rsSrc.getString("visit_id"));
                    aVisitVO.setOriginalVn(rsSrc.getString("original_vn"));
                    aVisitVO.setVn(rsSrc.getString("vn"));
                    aVisitVO.setVisitDate(rsSrc.getString("visit_date"));
                    aVisitVO.setVisitTime(rsSrc.getString("visit_time"));
                    aVisitVO.setDoctorCode(rsSrc.getString("doctor_code"));
                    aVisitVO.setDoctor(rsSrc.getString("doctor"));
                    aVisitVO.setOriginalAn(rsSrc.getString("original_an"));
                    aVisitVO.setAn(rsSrc.getString("an"));
                    aVisitVO.setAdmitDate(rsSrc.getString("admit_date"));
                    aVisitVO.setAdmitTime(rsSrc.getString("admit_time"));
                    aVisitVO.setActive(rsSrc.getString("active"));

                    list.add(aVisitVO);
                }
                rsSrc.close();
                stmtSrc.close();
                connSrc.close();

            } catch (Exception ex)
            {
                Utility.printCoreDebug(this, sqlCmd.toString());
                Utility.keepLog(sqlCmd.toString());
                ex.printStackTrace();
            } finally
            {
                connSrc = null;
                stmtSrc = null;
                rsSrc = null;
            }
        }
        return list;
    }

    public List listDxByHnAndVisitId(String hn, String visitId)
    {
        List list = new ArrayList();
        if (hn!=null && visitId!=null)
        {
            Connection connSrc = this.getSourceConnection();
            Statement stmtSrc = null;
            ResultSet rsSrc = null;
            StringBuilder sqlCmd = new StringBuilder();

            try
            {
                sqlCmd.append(" SELECT * FROM ");
                sqlCmd.append(" ( ");
                sqlCmd.append("     SELECT	a.HN AS patient_id,");
                sqlCmd.append("             convert(nvarchar(MAX), (a.VisitDate), 112) + convert(varchar(3),a.VN) + convert(varchar(2),b.PrescriptionNo) AS visit_id,");
                sqlCmd.append("             isnull(c.ICDSimilarName, '') AS beginning_diagnosis,");
                sqlCmd.append("             isnull(c.ICDCode, '') AS icd10_code,");
                sqlCmd.append("             isnull(d.EnglishSearchText, '') AS icd10_description,");
                sqlCmd.append("             isnull(CASE WHEN c.DiagnosisRecordType = 1 THEN '1' WHEN c.DiagnosisRecordType = 4 THEN '2' WHEN c.DiagnosisRecordType = 2 THEN '3' WHEN c.DiagnosisRecordType IN ('5','0') THEN '0' END, '') AS fix_diagnosis_type_id,");
                sqlCmd.append("             isnull(CASE WHEN c.DiagnosisRecordType = 1 THEN 'Primary Diagnosis' WHEN c.DiagnosisRecordType = 4 THEN 'Comorbidity' WHEN c.DiagnosisRecordType = 2 THEN 'Complication' WHEN c.DiagnosisRecordType IN ('5','0') THEN 'Other' END, '') AS diagnosis_type");
                sqlCmd.append("     FROM    DNHOSPITAL.dbo.HNOPD_MASTER AS a left join");
                sqlCmd.append("             DNHOSPITAL.dbo.HNOPD_PRESCRIP AS b ON a.VisitDate = b.VisitDate and a.VN = b.VN  left join");
                sqlCmd.append("             DNHOSPITAL.dbo.HNOPD_PRESCRIP_DIAG AS c ON b.VisitDate = c.VisitDate and b.PrescriptionNo = c.PrescriptionNo and b.VN = c.VN left join");
                sqlCmd.append("             DNHOSPITAL.dbo.HNICD_MASTER AS d on c.ICDCode = d.IcdCode");
                sqlCmd.append("     WHERE a.HN='").append(hn).append("'");
                sqlCmd.append(" ) tmp ");
                sqlCmd.append(" WHERE tmp.visit_id='").append(visitId).append("'");
                sqlCmd.append(" AND (CAST(tmp.beginning_diagnosis AS VARCHAR(5))<>'' OR CAST(tmp.icd10_code AS VARCHAR(5))<>'' OR CAST(tmp.icd10_description AS VARCHAR(5))<>'') ");
                stmtSrc = connSrc.createStatement();
                // Utility.printCoreDebug(this, sqlCmd.toString());
                rsSrc = stmtSrc.executeQuery(sqlCmd.toString());
                for (; rsSrc.next();)
                {
                    DiagnosisVO aDiagnosisVO = new DiagnosisVO();

                    aDiagnosisVO.setPatientId(rsSrc.getString("patient_id"));
                    aDiagnosisVO.setVisitId(rsSrc.getString("visit_id"));
                    aDiagnosisVO.setBeginningDiagnosis(rsSrc.getString("beginning_diagnosis"));
                    aDiagnosisVO.setIcd10Code(rsSrc.getString("icd10_code"));
                    aDiagnosisVO.setIcd10Description(rsSrc.getString("icd10_description"));
                    aDiagnosisVO.setFixDiagnosisTypeId(rsSrc.getString("fix_diagnosis_type_id"));
                    aDiagnosisVO.setDiagnosisType(rsSrc.getString("diagnosis_type"));

                    list.add(aDiagnosisVO);
                }
                rsSrc.close();
                stmtSrc.close();
                connSrc.close();
            } catch (Exception ex)
            {
                Utility.printCoreDebug(this, sqlCmd.toString());
                Utility.keepLog(sqlCmd.toString());
                ex.printStackTrace();
            } finally
            {
                connSrc = null;
                stmtSrc = null;
                rsSrc = null;
            }
        }
        return list;
    }


}
