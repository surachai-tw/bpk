package com.bpk.pgToSqlServer.dao;

import com.bpk.pgToSqlServer.dto.DiagnosisVO;
import com.bpk.pgToSqlServer.dto.PatientVO;
import com.bpk.pgToSqlServer.dto.VisitVO;
import com.bpk.pgToSqlServer.utility.Utility;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author surachai.tw
 */
public class DocScanDAO extends SqlServerToPgDAO
{

    public void updatePatient(PatientVO aPatientVO)
    {
        Connection connDest = null;
        Statement stmtDest = null;
        StringBuilder sqlCmd = new StringBuilder();
        ResultSet rst = null;

        try
        {
            connDest = this.getDestinationConnection();
            stmtDest = connDest.createStatement();

            sqlCmd.append("SELECT * FROM patient WHERE hn='").append(aPatientVO.getHn()).append("'");
            rst = stmtDest.executeQuery(sqlCmd.toString());
            if (!rst.next())
            {
                sqlCmd = new StringBuilder();
                sqlCmd.append("INSERT INTO patient(patient_id, hn, prename, firstname, lastname, firstname_en, lastname_en, fix_gender_id, birthdate, birthdate_true, fix_nationality_id, active, is_death, register_date, register_time, modify_date, modify_time) VALUES('");
                sqlCmd.append(aPatientVO.getPatientId()).append("', '");
                sqlCmd.append(aPatientVO.getHn()).append("', '");
                sqlCmd.append(Utility.addSingleQuote(aPatientVO.getPrename())).append("', '");
                sqlCmd.append(Utility.addSingleQuote(aPatientVO.getFirstname())).append("', '");
                sqlCmd.append(Utility.addSingleQuote(aPatientVO.getLastname())).append("', '");
                sqlCmd.append(Utility.addSingleQuote(aPatientVO.getFirstnameEn())).append("', '");
                sqlCmd.append(Utility.addSingleQuote(aPatientVO.getLastnameEn())).append("', '");
                sqlCmd.append(aPatientVO.getFixGenderId()).append("', '");
                sqlCmd.append(aPatientVO.getBirthdate()).append("', '");
                sqlCmd.append(aPatientVO.getBirthdateTrue()).append("', '");
                sqlCmd.append(aPatientVO.getFixNationalityId()).append("', '");
                sqlCmd.append(aPatientVO.getActive()).append("', '");
                sqlCmd.append(aPatientVO.getIsDeath()).append("', '");
                sqlCmd.append(aPatientVO.getRegisterDate()).append("', '");
                sqlCmd.append(aPatientVO.getRegisterTime()).append("', '");
                sqlCmd.append(aPatientVO.getUpdateDate()).append("', '");
                sqlCmd.append(aPatientVO.getUpdateTime()).append("')");

                // Utility.printCoreDebug(this, sqlCmd.toString());
                stmtDest.executeUpdate(sqlCmd.toString());
            }
            rst.close();
            stmtDest.close();
            connDest.close();
        } catch (Exception ex)
        {
            Utility.printCoreDebug(this, sqlCmd.toString());
            Utility.keepLog(sqlCmd.toString());
            ex.printStackTrace();
        } finally
        {
            connDest = null;
            stmtDest = null;
            sqlCmd = null;
        }
    }

    /** ขอรายการ Prefix ทั้งหมดจาก patient.hn */
    public List listPrefix(int numDigit)
    {
        List list = new ArrayList();
        if (numDigit > 0)
        {
            Connection connSrc = this.getDestinationConnection();
            Statement stmtSrc = null;
            ResultSet rsSrc = null;
            StringBuilder sqlCmd = null;

            try
            {
                sqlCmd = new StringBuilder("SELECT substring(hn, 1, ").append(numDigit).append(") yyy, count(hn) Cnt FROM patient GROUP BY substring(hn, 1, ").append(numDigit).append(") ORDER BY substring(hn, 1, ").append(numDigit).append(")");
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

    /** List รายการ HN ทั้งหมด ที่นำหน้าด้วย Parameter */
    public List listPatientVOByHnWithPrefix(String prefix)
    {
        List list = new ArrayList();
        if (prefix != null)
        {
            Connection connSrc = this.getDestinationConnection();
            Statement stmtSrc = null;
            ResultSet rsSrc = null;
            StringBuilder sqlCmd = new StringBuilder();

            try
            {
                sqlCmd.append(" SELECT patient_id, hn ");
                sqlCmd.append(" FROM patient WHERE hn LIKE '").append(prefix).append("%'");
                stmtSrc = connSrc.createStatement();
                // Utility.printCoreDebug(this, sqlCmd.toString());
                rsSrc = stmtSrc.executeQuery(sqlCmd.toString());
                for (; rsSrc.next();)
                {
                    PatientVO aPatientVO = new PatientVO();

                    aPatientVO.setPatientId(rsSrc.getString("patient_id"));
                    aPatientVO.setHn(rsSrc.getString("hn"));

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

    /** ขอรายการ Prefix ทั้งหมดจาก patient.hn */
    public List listVisitDate(int backDate)
    {
        List list = new ArrayList();
        if (backDate > 0)
        {
            Connection connSrc = this.getDestinationConnection();
            Statement stmtSrc = null;
            ResultSet rsSrc = null;
            StringBuilder sqlCmd = null;

            try
            {
                sqlCmd = new StringBuilder("SELECT DISTINCT visit_date FROM visit ORDER BY visit_date DESC LIMIT " + backDate);
                stmtSrc = connSrc.createStatement();
                // Utility.printCoreDebug(this, sqlCmd.toString());
                rsSrc = stmtSrc.executeQuery(sqlCmd.toString());
                for (; rsSrc.next();)
                {
                    list.add(rsSrc.getString("visit_date"));
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

    /** List รายการ Visit ทั้งหมดในแต่ละวัน */
    public List listVisitByVisitDate(String visitDate)
    {
        List list = new ArrayList();
        if (visitDate != null)
        {
            Connection connSrc = this.getDestinationConnection();
            Statement stmtSrc = null;
            ResultSet rsSrc = null;
            StringBuilder sqlCmd = new StringBuilder();

            try
            {
                sqlCmd.append(" SELECT visit_id, vn, hn ");
                sqlCmd.append(" FROM visit WHERE visit_date='").append(visitDate).append("' ORDER BY vn");
                stmtSrc = connSrc.createStatement();
                // Utility.printCoreDebug(this, sqlCmd.toString());
                rsSrc = stmtSrc.executeQuery(sqlCmd.toString());
                for (; rsSrc.next();)
                {
                    VisitVO aVisitVO = new VisitVO();

                    aVisitVO.setVisitId(rsSrc.getString("visit_id"));
                    aVisitVO.setVn(rsSrc.getString("vn"));
                    aVisitVO.setHn(rsSrc.getString("hn"));

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

    public void updateVisit(VisitVO aVisitVO)
    {
        Connection connDest = null;
        Statement stmtDest = null;
        ResultSet rst = null;
        StringBuilder sqlCmd = new StringBuilder();

        try
        {
            connDest = this.getDestinationConnection();
            stmtDest = connDest.createStatement();

            sqlCmd.append("SELECT * FROM visit WHERE visit_id='").append(aVisitVO.getVisitId()).append("' AND patient_id='").append(aVisitVO.getPatientId()).append("'");
            Utility.printCoreDebug(this, sqlCmd.toString());
            rst = stmtDest.executeQuery(sqlCmd.toString());
            if (!rst.next())
            {
                sqlCmd = new StringBuilder();
                sqlCmd.append("INSERT INTO visit(patient_id, hn, visit_id, vn, visit_date, visit_time, an, active, modify_eid, modify_date, modify_time) VALUES('");
                sqlCmd.append(aVisitVO.getPatientId()).append("', '");
                sqlCmd.append(aVisitVO.getHn()).append("', '");
                sqlCmd.append(aVisitVO.getVisitId()).append("', '");
                sqlCmd.append(aVisitVO.getOriginalVn()).append("', '");
                sqlCmd.append(aVisitVO.getVisitDate()).append("', '");
                sqlCmd.append(aVisitVO.getVisitTime()).append("', '");
                sqlCmd.append(aVisitVO.getOriginalAn()).append("', '");
                sqlCmd.append(aVisitVO.getActive()).append("', '");
                sqlCmd.append("docscan").append("', '");
                sqlCmd.append(Utility.getCurrentDate()).append("', '");
                sqlCmd.append(Utility.getCurrentTime()).append("')");

                Utility.printCoreDebug(this, sqlCmd.toString());
                stmtDest.executeUpdate(sqlCmd.toString());
            }
            rst.close();            
            stmtDest.close();
            connDest.close();
        } catch (Exception ex)
        {
            Utility.printCoreDebug(this, sqlCmd.toString());
            Utility.keepLog(sqlCmd.toString());
            ex.printStackTrace();
        } finally
        {
            connDest = null;
            stmtDest = null;
            sqlCmd = null;
        }
    }

    public void updateIssuedFile(VisitVO aVisitVO)
    {
        Connection connDest = null;
        Statement stmtDest = null;
        ResultSet rst = null;
        StringBuilder sqlCmd = new StringBuilder();

        try
        {
            connDest = this.getDestinationConnection();
            stmtDest = connDest.createStatement();

            sqlCmd.append("SELECT * FROM bpk_patient_file_issue WHERE issue_id='").append(aVisitVO.getVn()).append("' AND patient_id='").append(aVisitVO.getPatientId()).append("'");
            rst = stmtDest.executeQuery(sqlCmd.toString());
            if (!rst.next())
            {
                sqlCmd = new StringBuilder();
                sqlCmd.append("INSERT INTO bpk_patient_file_issue(bpk_patient_file_issue_id, patient_id, issue_id, issue_eid, issue_date, issue_time, issue_to_eid, fix_file_issue_status_id, modify_eid, modify_date, modify_time) VALUES('");
                sqlCmd.append(Utility.generateObjectID()).append("', '");
                sqlCmd.append(aVisitVO.getPatientId()).append("', '");
                sqlCmd.append(aVisitVO.getVn()).append("', '");
                sqlCmd.append("docscan").append("', '");
                sqlCmd.append(aVisitVO.getVisitDate()).append("', '");
                sqlCmd.append(aVisitVO.getVisitTime()).append("', '");
                sqlCmd.append(aVisitVO.getDoctorCode()).append("', '");
                sqlCmd.append("1").append("', '");
                sqlCmd.append("docscan").append("', '");
                sqlCmd.append(Utility.getCurrentDate()).append("', '");
                sqlCmd.append(Utility.getCurrentTime()).append("')");

                // Utility.printCoreDebug(this, sqlCmd.toString());
                stmtDest.executeUpdate(sqlCmd.toString());
            }

            rst.close();
            stmtDest.close();
            connDest.close();
        } catch (Exception ex)
        {
            Utility.printCoreDebug(this, sqlCmd.toString());
            Utility.keepLog(sqlCmd.toString());
            ex.printStackTrace();
        } finally
        {
            connDest = null;
            stmtDest = null;
            sqlCmd = null;
        }
    }

    public void updateDiagnosis(DiagnosisVO aDiagnosisVO)
    {
        Connection connDest = null;
        Statement stmtDest = null;
        ResultSet rst = null;
        StringBuilder sqlCmd = new StringBuilder();

        try
        {
            connDest = this.getDestinationConnection();
            stmtDest = connDest.createStatement();

            sqlCmd.append("SELECT * FROM diagnosis_icd10 WHERE visit_id='").append(aDiagnosisVO.getVisitId()).append("' AND icd10_code<>'' AND icd10_code='").append(aDiagnosisVO.getIcd10Code()).append("'");
            rst = stmtDest.executeQuery(sqlCmd.toString());
            if (!rst.next())
            {
                sqlCmd = new StringBuilder();
                sqlCmd.append("INSERT INTO diagnosis_icd10(diagnosis_icd10_id, visit_id, beginning_diagnosis, beginning_diagnosis_th, icd10_code, icd10_description, fix_diagnosis_type_id, modify_eid, modify_date, modify_time) VALUES('");
                sqlCmd.append(Utility.generateObjectID()).append("', '");
                sqlCmd.append(aDiagnosisVO.getVisitId()).append("', '");
                sqlCmd.append(Utility.addSingleQuote(aDiagnosisVO.getBeginningDiagnosis())).append("', '");
                sqlCmd.append(Utility.addSingleQuote(aDiagnosisVO.getBeginningDiagnosis())).append("', '");
                sqlCmd.append(aDiagnosisVO.getIcd10Code()).append("', '");
                sqlCmd.append(Utility.addSingleQuote(aDiagnosisVO.getIcd10Description())).append("', '");
                sqlCmd.append(aDiagnosisVO.getFixDiagnosisTypeId()).append("', '");
                sqlCmd.append("docscan").append("', '");
                sqlCmd.append(Utility.getCurrentDate()).append("', '");
                sqlCmd.append(Utility.getCurrentTime()).append("')");

                // Utility.printCoreDebug(this, sqlCmd.toString());
                stmtDest.executeUpdate(sqlCmd.toString());
            }
            rst.close();
            stmtDest.close();
            connDest.close();
        } catch (Exception ex)
        {
            Utility.printCoreDebug(this, sqlCmd.toString());
            Utility.keepLog(sqlCmd.toString());
            ex.printStackTrace();
        } finally
        {
            connDest = null;
            stmtDest = null;
            sqlCmd = null;
        }
    }
}
