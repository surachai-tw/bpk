package com.bpk.core.emrcore.dao;

import com.bpk.persistence.emrdto.BpkDocumentScanVO;
import com.bpk.persistence.emrdto.FolderVO;
import com.bpk.persistence.emrdto.PatientVO;
import com.bpk.persistence.emrdto.VisitVO;
import com.bpk.utility.EventNames;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.bpk.utility.Utility;
import com.bpk.utility.XPersistent;
import java.text.NumberFormat;

/**
 *
 * @author SURACHAI.TO
 */
public class DocScanDAO
{

    protected DocScanDAO()
    {
    }

    public boolean isPatientExistByHn(String hn)
    {
        boolean result = false;
        if(hn != null)
        {
            Connection conn = DocScanDAOFactory.getConnection();
            Statement stmt = null;
            ResultSet rs = null;
            StringBuffer sqlCmd = null;

            try
            {
                // Find patient_id
                if (hn.indexOf("-") != -1)
                {
                    sqlCmd = new StringBuffer("SELECT patient_id FROM patient WHERE active='1' AND format_hn(hn)='").append(hn).append("' LIMIT 1");
                }
                else
                {
                    sqlCmd = new StringBuffer("SELECT patient_id FROM patient WHERE active='1' AND hn='").append(hn).append("' LIMIT 1");
                }

                stmt = conn.createStatement();
                Utility.printCoreDebug(this, sqlCmd.toString());
                rs = stmt.executeQuery(sqlCmd.toString());

                if (rs.next())
                {
                    result = rs.getString("patient_id")!=null ? true : false;
                    
                    rs.close();
                }

                stmt.close();
                conn.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                sqlCmd = null;
                rs = null;
                stmt = null;
                conn = null;
            }
        }

        return result;
    }

    public String getNextImageFileName(String folderName)
    {
        String result = null;
        if(folderName != null)
        {
            Connection conn = DocScanDAOFactory.getConnection();
            Statement stmt = null;
            ResultSet rs = null;
            StringBuffer sqlCmd = null;

            NumberFormat nf4 = NumberFormat.getInstance();
            nf4.setMaximumFractionDigits(0);
            nf4.setMinimumFractionDigits(0);
            nf4.setMaximumIntegerDigits(4);
            nf4.setMinimumIntegerDigits(4);
            nf4.setGroupingUsed(false);
            String seqValue = null;
            String fileNamePrefix = null;
            
            try
            {
                sqlCmd = new StringBuffer("SELECT seq_value, file_name_prefix FROM bpk_patient_image_folder WHERE folder_name='").append(folderName).append("'");

                stmt = conn.createStatement();
                Utility.printCoreDebug(this, sqlCmd.toString());
                rs = stmt.executeQuery(sqlCmd.toString());

                if (rs.next())
                {
                    seqValue = rs.getString("seq_value");
                    fileNamePrefix = rs.getString("file_name_prefix");

                    rs.close();

                    if(!Utility.isNumber(seqValue))
                    {
                        seqValue = "0";
                    }

                    if(Utility.isNull(fileNamePrefix))
                    {
                        fileNamePrefix = "";
                    }
                }
                else
                {
                    seqValue = "0";
                    fileNamePrefix = "";
                }

                int intSeqValue = Integer.parseInt(seqValue)+1;
                seqValue = String.valueOf(intSeqValue);

                sqlCmd = new StringBuffer("UPDATE bpk_patient_image_folder SET seq_value='").append(seqValue).append("' WHERE folder_name='").append(folderName).append("'");
                Utility.printCoreDebug(this, sqlCmd.toString());
                stmt.executeUpdate(sqlCmd.toString());
                
                stmt.close();
                conn.close();

                String cDate = Utility.getCurrentDate();
                cDate = cDate.substring(5);
                cDate = cDate.replaceAll("-", "");
                
                result = fileNamePrefix+Utility.getCurrentYearInBuddhist()+cDate+nf4.format(intSeqValue);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                sqlCmd = null;
                rs = null;
                stmt = null;
                conn = null;
            }
        }

        return result;
    }

    public HashMap readPatient(String hn)
    {
        HashMap result = new HashMap();
        if (hn != null)
        {
            Connection conn = DocScanDAOFactory.getConnection();
            Statement stmt = null;
            ResultSet rs = null;
            StringBuilder sqlCmd = null;
            Integer cntVisit = 0;
            Integer cntDocScan = 0;
            PatientVO aPatientVO = null;

            try
            {
                // Find patient_id
                if (hn.indexOf("-") != -1)
                {
                    sqlCmd = new StringBuilder("SELECT patient_id, hn AS original_hn, format_hn(hn) AS hn, bpkget_patient_name(patient_id) AS patient_name FROM patient WHERE active='1' AND format_hn(hn)='").append(hn).append("' LIMIT 1");
                }
                else
                {
                    sqlCmd = new StringBuilder("SELECT patient_id, hn AS original_hn, format_hn(hn) AS hn, bpkget_patient_name(patient_id) AS patient_name FROM patient WHERE active='1' AND hn='").append(hn).append("' LIMIT 1");
                }

                stmt = conn.createStatement();
                Utility.printCoreDebug(this, sqlCmd.toString());
                rs = stmt.executeQuery(sqlCmd.toString());

                if (rs.next())
                {
                    aPatientVO = new PatientVO();

                    aPatientVO.setObjectID(rs.getString("patient_id"));
                    aPatientVO.setHn(rs.getString("hn"));
                    aPatientVO.setOriginalHn(rs.getString("original_hn"));
                    aPatientVO.setPatientName(rs.getString("patient_name"));

                    rs.close();
                }
                stmt.close();

                if (aPatientVO != null)
                {
                    // Count visit
                    sqlCmd = new StringBuilder("SELECT Count(DISTINCT visit_id) AS cntVisit FROM document_scan WHERE patient_id='").append(aPatientVO.getObjectID()).append("'");

                    stmt = conn.createStatement();
                    Utility.printCoreDebug(this, sqlCmd.toString());
                    rs = stmt.executeQuery(sqlCmd.toString());

                    if (rs.next())
                    {
                        cntVisit = rs.getInt("cntVisit");
                        rs.close();
                    }
                    stmt.close();

                    // Count doc scan
                    sqlCmd = new StringBuilder("SELECT Count(document_scan_id) AS cntDocScan FROM document_scan WHERE patient_id='").append(aPatientVO.getObjectID()).append("'");

                    stmt = conn.createStatement();
                    Utility.printCoreDebug(this, sqlCmd.toString());
                    rs = stmt.executeQuery(sqlCmd.toString());

                    if (rs.next())
                    {
                        cntDocScan = rs.getInt("cntDocScan");
                        rs.close();
                    }
                    stmt.close();

                    result.put("COUNT_VISIT", cntVisit);
                    result.put("COUNT_DOC_SCAN", cntDocScan);
                    result.put(EventNames.RESULT_DATA, aPatientVO);
                }
                else
                {
                }

                conn.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                sqlCmd = null;
                rs = null;
                stmt = null;
                conn = null;
            }
        }
        return result;
    }

    public List listVisitByPatientId(String patientId) throws Exception
    {
        List listVisitVO = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rst = null;
        StringBuffer sqlCmd = null;

        if (Utility.isNotNull(patientId))
        {
            try
            {
                sqlCmd = new StringBuffer(
                        "SELECT DISTINCT visit.visit_id, format_vn(visit.vn) AS vn, format_an(visit.an) AS an, visit.fix_visit_type_id, visit.visit_date, visit.visit_time ");
                sqlCmd.append(" FROM visit ");
                sqlCmd.append(" INNER JOIN bpk_document_scan ON visit.patient_id=bpk_document_scan.patient_id AND visit.visit_id=bpk_document_scan.visit_id ");
                sqlCmd.append(" WHERE visit.active='1' AND visit.patient_id='").append(patientId).append("' ");
                sqlCmd.append(" ORDER BY visit.visit_date DESC, visit.visit_time DESC");

                conn = DocScanDAOFactory.getConnection();
                stmt = conn.createStatement();

                Utility.printCoreDebug(this, sqlCmd.toString());
                rst = stmt.executeQuery(sqlCmd.toString());
                for (; rst.next();)
                {
                    VisitVO aVisitVO = new VisitVO();

                    aVisitVO.setObjectID(rst.getString("visit_id"));
                    aVisitVO.setVn(rst.getString("vn"));
                    aVisitVO.setAn(rst.getString("an"));
                    aVisitVO.setVisitDate(rst.getString("visit_date"));
                    aVisitVO.setVisitTime(rst.getString("visit_time"));
                    aVisitVO.setFixVisitTypeId(rst.getString("fix_visit_type_id"));

                    aVisitVO.setListFolderVO(this.listFolderBpkDocumentScanVO(aVisitVO.getObjectID()));
                    List listFolder = aVisitVO.getListFolderVO();
                    for (int i = 0, sizei = listFolder.size(); i < sizei; i++)
                    {
                        FolderVO tmpFolderVO = (FolderVO) listFolder.get(i);

                        tmpFolderVO.setListBpkDocumentScanVO(this.listBpkDocumentScanVO(aVisitVO.getObjectID(), tmpFolderVO.getObjectID()));
                    }

                    listVisitVO.add(aVisitVO);
                }

                rst.close();
                stmt.close();
                conn.close();
            }
            catch (Exception ex)
            {
                if (conn != null)
                {
                    try
                    {
                        conn.close();
                    }
                    catch (Exception ex2)
                    {
                    }
                }
                ex.printStackTrace();
                throw ex;
            }
            finally
            {
                conn = null;
                stmt = null;
                rst = null;
                sqlCmd = null;
            }
        }
        else
        {
            return null;
        }
        return listVisitVO;
    }

    /** ถ้า VisitId เป็นค่าว่าง หมายถึง ขอข้อมูล ภาพที่ไม่ผูกกับ Visit */
    public List listFolderBpkDocumentScanVO(String visitId) throws Exception
    {
        List listFolder = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rst = null;
        StringBuffer sqlCmd = null;

        Utility.printCoreDebug(this, "listFolderBpkDocumentScanVO(" + visitId + ")");
        if (Utility.isNotNull(visitId))
        {
            try
            {
                sqlCmd = new StringBuffer("SELECT DISTINCT bpk_document_scan.folder_name ");
                sqlCmd.append(" FROM visit ");
                sqlCmd.append(" INNER JOIN bpk_document_scan ON visit.patient_id=bpk_document_scan.patient_id AND visit.visit_id=bpk_document_scan.visit_id ");
                sqlCmd.append(" WHERE visit.active='1' AND bpk_document_scan.visit_id='").append(visitId).append("' ");
                sqlCmd.append(" ORDER BY bpk_document_scan.folder_name");

                conn = DocScanDAOFactory.getConnection();
                stmt = conn.createStatement();

                Utility.printCoreDebug(this, sqlCmd.toString());
                rst = stmt.executeQuery(sqlCmd.toString());
                for (; rst.next();)
                {
                    FolderVO aFolderVO = new FolderVO();
                    aFolderVO.setObjectID(rst.getString("folder_name"));

                    listFolder.add(aFolderVO);
                }

                rst.close();
                stmt.close();
                conn.close();
            }
            catch (Exception ex)
            {
                if (conn != null)
                {
                    try
                    {
                        conn.close();
                    }
                    catch (Exception ex2)
                    {
                    }
                }
                ex.printStackTrace();
                throw ex;
            }
            finally
            {
                conn = null;
                stmt = null;
                rst = null;
                sqlCmd = null;
            }
        }
        else
        {
            return null;
        }
        return listFolder;
    }

    public List listBpkDocumentScanVO(String visitId, String folder) throws Exception
    {
        List listBpkDocumentScanVO = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rst = null;
        StringBuffer sqlCmd = null;

        if (Utility.isNotNull(visitId) && Utility.isNotNull(folder))
        {
            try
            {
                sqlCmd = new StringBuffer("SELECT DISTINCT bpk_document_scan.bpk_document_scan_id, bpk_document_scan.image_file_name ");
                sqlCmd.append(" FROM visit ");
                sqlCmd.append(" INNER JOIN bpk_document_scan ON visit.patient_id=bpk_document_scan.patient_id AND visit.visit_id=bpk_document_scan.visit_id ");
                sqlCmd.append(" AND bpk_document_scan.folder_name='").append(folder).append("' ");
                sqlCmd.append(" WHERE visit.active='1' AND visit.visit_id='").append(visitId).append("' ");
                sqlCmd.append(" ORDER BY bpk_document_scan.image_file_name");

                conn = DocScanDAOFactory.getConnection();
                stmt = conn.createStatement();

                Utility.printCoreDebug(this, sqlCmd.toString());
                rst = stmt.executeQuery(sqlCmd.toString());
                for (; rst.next();)
                {
                    BpkDocumentScanVO aBpkDocumentScanVO = new BpkDocumentScanVO();

                    aBpkDocumentScanVO.setObjectID(rst.getString("bpk_document_scan_id"));
                    aBpkDocumentScanVO.setImageFileName(rst.getString("image_file_name"));

                    listBpkDocumentScanVO.add(aBpkDocumentScanVO);
                }

                rst.close();
                stmt.close();
                conn.close();
            }
            catch (Exception ex)
            {
                if (conn != null)
                {
                    try
                    {
                        conn.close();
                    }
                    catch (Exception ex2)
                    {
                    }
                }
                ex.printStackTrace();
                throw ex;
            }
            finally
            {
                conn = null;
                stmt = null;
                rst = null;
                sqlCmd = null;
            }
        }
        else
        {
            return null;
        }
        return listBpkDocumentScanVO;
    }

    public BpkDocumentScanVO createBpkDocumentScan(BpkDocumentScanVO newBpkDocumentScanVO) throws Exception
    {
        Connection conn = null;
        Statement stmt = null;
        StringBuffer sqlCmd = null;

        if (newBpkDocumentScanVO!=null && Utility.isNotNull(newBpkDocumentScanVO.getPatientId()) && Utility.isNotNull(newBpkDocumentScanVO.getFolderName()))
        {
            try
            {
                sqlCmd = new StringBuffer("INSERT INTO bpk_document_scan (bpk_document_scan_id, patient_id, visit_id, folder_name, image_file_name, scan_date, scan_time, update_date, update_time) VALUES('");
                newBpkDocumentScanVO.setObjectID(XPersistent.generateObjectID());
                newBpkDocumentScanVO.setScanDate(Utility.getCurrentDate());
                newBpkDocumentScanVO.setScanTime(Utility.getCurrentTime());
                newBpkDocumentScanVO.setUpdateDate(Utility.getCurrentDate());
                newBpkDocumentScanVO.setUpdateTime(Utility.getCurrentTime());
                newBpkDocumentScanVO.setImageFileName(getNextImageFileName(newBpkDocumentScanVO.getFolderName())+".pdf");
                sqlCmd.append(newBpkDocumentScanVO.getObjectID()).append("', '").append(newBpkDocumentScanVO.getPatientId()).append("', (SELECT visit_id FROM visit AS v WHERE v.active='1' AND v.patient_id='").append(newBpkDocumentScanVO.getPatientId()).append("' ORDER BY visit_date DESC, visit_time DESC LIMIT 1), '");
                sqlCmd.append(newBpkDocumentScanVO.getFolderName()).append("', '").append(newBpkDocumentScanVO.getImageFileName()).append("', '");
                sqlCmd.append(newBpkDocumentScanVO.getScanDate()).append("', '").append(newBpkDocumentScanVO.getScanTime()).append("', '");
                sqlCmd.append(newBpkDocumentScanVO.getUpdateDate()).append("', '").append(newBpkDocumentScanVO.getUpdateTime()).append("')");

                conn = DocScanDAOFactory.getConnection();
                stmt = conn.createStatement();

                Utility.printCoreDebug(this, sqlCmd.toString());
                stmt.executeUpdate(sqlCmd.toString());

                stmt.close();
                conn.close();
            }
            catch (Exception ex)
            {
                if (conn != null)
                {
                    try
                    {
                        conn.close();
                    }
                    catch (Exception ex2)
                    {
                    }
                }
                ex.printStackTrace();
                throw ex;
            }
            finally
            {
                conn = null;
                stmt = null;
                sqlCmd = null;
            }
        }
        else
        {
            newBpkDocumentScanVO.setObjectID(null);
            return newBpkDocumentScanVO;
        }
        return newBpkDocumentScanVO;
    }
}
