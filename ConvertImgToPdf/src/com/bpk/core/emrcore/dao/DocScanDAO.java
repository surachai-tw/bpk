package com.bpk.core.emrcore.dao;

import com.bpk.persistence.careplandto.AdmitVO;
import com.bpk.persistence.emrdto.BaseServicePointVO;
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
import com.bpk.utility.dto.EmployeeRoleVO;
import com.bpk.utility.fix.FixDocumentScanStatus;
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
        if (hn != null)
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
                    sqlCmd = new StringBuffer("SELECT patient_id FROM patient WHERE active='1' AND hn='").append(Utility.formatHnToDb(hn)).append("' LIMIT 1");
                } else
                {
                    sqlCmd = new StringBuffer("SELECT patient_id FROM patient WHERE active='1' AND hn='").append(hn).append("' LIMIT 1");
                }

                stmt = conn.createStatement();
                Utility.printCoreDebug(this, sqlCmd.toString());
                rs = stmt.executeQuery(sqlCmd.toString());

                if (rs.next())
                {
                    result = rs.getString("patient_id") != null ? true : false;

                    rs.close();
                }

                stmt.close();
                conn.close();
            } catch (Exception ex)
            {
                ex.printStackTrace();
            } finally
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
        if (folderName != null)
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

                    if (!Utility.isNumber(seqValue))
                    {
                        seqValue = "0";
                    }

                    if (Utility.isNull(fileNamePrefix))
                    {
                        fileNamePrefix = "";
                    }
                } else
                {
                    seqValue = "0";
                    fileNamePrefix = "";
                }

                int intSeqValue = Integer.parseInt(seqValue) + 1;
                seqValue = String.valueOf(intSeqValue);

                sqlCmd = new StringBuffer("UPDATE bpk_patient_image_folder SET seq_value='").append(seqValue).append("' WHERE folder_name='").append(folderName).append("'");
                Utility.printCoreDebug(this, sqlCmd.toString());
                stmt.executeUpdate(sqlCmd.toString());

                stmt.close();
                conn.close();

                String cDate = Utility.getCurrentDate();
                cDate = cDate.substring(5);
                cDate = cDate.replaceAll("-", "");

                result = fileNamePrefix + Utility.getCurrentYearInBuddhist() + cDate + nf4.format(intSeqValue);
            } catch (Exception ex)
            {
                ex.printStackTrace();
            } finally
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
                    sqlCmd = new StringBuilder("SELECT patient_id, hn AS original_hn, format_hn(hn) AS hn, bpkget_patient_name(patient_id) AS patient_name FROM patient WHERE active='1' AND hn='").append(Utility.formatHnToDb(hn)).append("' LIMIT 1");
                } else
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
                } else
                {
                }

                conn.close();
            } catch (Exception ex)
            {
                ex.printStackTrace();
            } finally
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
                        "SELECT DISTINCT visit.visit_id, format_vn(visit.vn) AS vn, format_an(visit.an) AS an, visit.fix_visit_type_id, visit.visit_date, visit.visit_time, get_first_pdx_name(visit.visit_id) AS pdx ");
                sqlCmd.append(" FROM visit ");
                sqlCmd.append(" LEFT JOIN admit ON visit.visit_id=admit.visit_id AND admit.active='1' ");
                sqlCmd.append(" LEFT JOIN bpk_document_scan ON visit.patient_id=bpk_document_scan.patient_id AND visit.visit_id=bpk_document_scan.visit_id ");
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
                    aVisitVO.setPDx(rst.getString("pdx"));

                    /*
                    aVisitVO.setListFolderVO(this.listFolderBpkDocumentScanVO(aVisitVO.getObjectID()));
                    List listFolder = aVisitVO.getListFolderVO();
                    for (int i = 0, sizei = listFolder.size(); i < sizei; i++)
                    {
                    FolderVO tmpFolderVO = (FolderVO) listFolder.get(i);

                    tmpFolderVO.setListBpkDocumentScanVO(this.listBpkDocumentScanVO(aVisitVO.getObjectID(), tmpFolderVO.getObjectID()));
                    }
                     */
                    listVisitVO.add(aVisitVO);
                }

                rst.close();
                stmt.close();
                conn.close();
            } catch (Exception ex)
            {
                if (conn != null)
                {
                    try
                    {
                        conn.close();
                    } catch (Exception ex2)
                    {
                    }
                }
                ex.printStackTrace();
                throw ex;
            } finally
            {
                conn = null;
                stmt = null;
                rst = null;
                sqlCmd = null;
            }
        } else
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

        // Utility.printCoreDebug(this, "listFolderBpkDocumentScanVO(" + visitId + ")");
        if (Utility.isNotNull(visitId))
        {
            try
            {
                sqlCmd = new StringBuffer("SELECT DISTINCT folder_name, display_order FROM ");
                sqlCmd.append("( SELECT bpk_document_scan.folder_name, bpk_patient_image_folder.display_order ");
                sqlCmd.append("  FROM visit ");
                sqlCmd.append("  INNER JOIN bpk_document_scan ON visit.patient_id=bpk_document_scan.patient_id AND visit.visit_id=bpk_document_scan.visit_id ");
                sqlCmd.append("  INNER JOIN bpk_patient_image_folder ON bpk_document_scan.folder_name=bpk_patient_image_folder.folder_name");
                sqlCmd.append("  WHERE visit.active='1' AND bpk_document_scan.visit_id='").append(visitId).append("' ");
                sqlCmd.append("  AND bpk_document_scan.fix_document_scan_status_id<>'").append(FixDocumentScanStatus.HISTORY_FILE).append("' ");
                sqlCmd.append("  AND bpk_document_scan.fix_document_scan_status_id<>'").append(FixDocumentScanStatus.DELETE_FILE).append("' ");
                sqlCmd.append(") AS tmp ORDER BY display_order");

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
            } catch (Exception ex)
            {
                if (conn != null)
                {
                    try
                    {
                        conn.close();
                    } catch (Exception ex2)
                    {
                    }
                }
                ex.printStackTrace();
                throw ex;
            } finally
            {
                conn = null;
                stmt = null;
                rst = null;
                sqlCmd = null;
            }
        } else
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
                sqlCmd.append(" AND bpk_document_scan.fix_document_scan_status_id<>'").append(FixDocumentScanStatus.HISTORY_FILE).append("' ");
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
            } catch (Exception ex)
            {
                if (conn != null)
                {
                    try
                    {
                        conn.close();
                    } catch (Exception ex2)
                    {
                    }
                }
                ex.printStackTrace();
                throw ex;
            } finally
            {
                conn = null;
                stmt = null;
                rst = null;
                sqlCmd = null;
            }
        } else
        {
            return null;
        }
        return listBpkDocumentScanVO;
    }

    public List checkDocumentIsAlreadyExist(BpkDocumentScanVO newBpkDocumentScanVO) throws Exception
    {
        List listId = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rst = null;
        StringBuffer sqlCmd = null;

        try
        {
            sqlCmd = new StringBuffer("SELECT bpk_document_scan_id FROM bpk_document_scan WHERE fix_document_scan_status_id<>'").append(FixDocumentScanStatus.HISTORY_FILE).append("' AND ");
            sqlCmd.append("patient_id=(SELECT patient_id FROM patient WHERE active='1' AND hn='").append(newBpkDocumentScanVO.getOriginalHn()).append("' LIMIT 1) AND ");
            sqlCmd.append("visit_id=(SELECT visit_id FROM visit WHERE active='1' AND vn='").append(newBpkDocumentScanVO.getVn()).append("' LIMIT 1) AND ");
            sqlCmd.append("folder_name='").append(newBpkDocumentScanVO.getFolderName()).append("' AND ");
            sqlCmd.append("document_name='").append(newBpkDocumentScanVO.getDocumentName()).append("' AND ");
            sqlCmd.append("print_date='").append(newBpkDocumentScanVO.getPrintDate()).append("' AND ");
            sqlCmd.append("print_time='").append(newBpkDocumentScanVO.getPrintTime()).append("' AND ");
            sqlCmd.append("doctor_eid='").append(newBpkDocumentScanVO.getDoctorEid()).append("'");

            conn = DocScanDAOFactory.getConnection();
            stmt = conn.createStatement();

            Utility.printCoreDebug(this, sqlCmd.toString());
            rst = stmt.executeQuery(sqlCmd.toString());
            if (rst.next())
            {
                if (listId == null)
                {
                    listId = new ArrayList();
                }

                String id = rst.getString("bpk_document_scan_id");
                listId.add(id);
            }

            rst.close();
            stmt.close();
            conn.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            sqlCmd = null;
            rst = null;
            stmt = null;
            conn = null;
        }
        return listId;
    }

    public void updateToHistory(String id) throws Exception
    {
        Connection conn = null;
        Statement stmt = null;
        StringBuffer sqlCmd = null;

        try
        {
            sqlCmd = new StringBuffer("UPDATE bpk_document_scan SET fix_document_scan_status_id='");
            sqlCmd.append(FixDocumentScanStatus.HISTORY_FILE);
            sqlCmd.append("' WHERE bpk_document_scan_id='").append(id).append("'");

            conn = DocScanDAOFactory.getConnection();
            stmt = conn.createStatement();

            Utility.printCoreDebug(this, sqlCmd.toString());
            stmt.executeUpdate(sqlCmd.toString());

            stmt.close();
            conn.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            sqlCmd = null;
            stmt = null;
            conn = null;
        }
    }

    public BpkDocumentScanVO createBpkDocumentScan(BpkDocumentScanVO newBpkDocumentScanVO) throws Exception
    {
        Connection conn = null;
        Statement stmt = null;
        StringBuffer sqlCmd = null;

        if (newBpkDocumentScanVO != null && Utility.isNotNull(newBpkDocumentScanVO.getPatientId()) && Utility.isNotNull(newBpkDocumentScanVO.getFolderName()))
        {
            try
            {
                List listOldId = checkDocumentIsAlreadyExist(newBpkDocumentScanVO);
                if (Utility.isNotNull(listOldId))
                {
                    for (int i = 0, sizei = listOldId.size(); i < sizei; i++)
                    {
                        updateToHistory((String) listOldId.get(i));
                    }
                }

                sqlCmd = new StringBuffer("INSERT INTO bpk_document_scan (bpk_document_scan_id, patient_id, visit_id, folder_name, image_file_name, scan_eid, scan_date, scan_time, update_eid, update_date, update_time, document_name, print_date, print_time, doctor_eid, fix_document_scan_status_id, bpk_fix_ordersheet_type_id, is_send_to_pharmacy, is_send_to_nutrition) VALUES('");
                newBpkDocumentScanVO.setObjectID(XPersistent.generateObjectID());
                newBpkDocumentScanVO.setScanDate(Utility.getCurrentDate());
                newBpkDocumentScanVO.setScanTime(Utility.getCurrentTime());
                newBpkDocumentScanVO.setUpdateDate(Utility.getCurrentDate());
                newBpkDocumentScanVO.setUpdateTime(Utility.getCurrentTime());
                newBpkDocumentScanVO.setImageFileName(getNextImageFileName(newBpkDocumentScanVO.getFolderName()) + ".PDF");
                sqlCmd.append(newBpkDocumentScanVO.getObjectID()).append("', '").append(newBpkDocumentScanVO.getPatientId()).append("', ");
                if (Utility.isNull(newBpkDocumentScanVO.getVn()))
                {
                    // กรณีที่ไม่ได้ส่งค่า VN ให้ใช้ VN ล่าสุด
                    // sqlCmd.append(" (SELECT visit_id FROM visit AS v WHERE v.active='1' AND v.patient_id='").append(newBpkDocumentScanVO.getPatientId()).append("' ORDER BY visit_date DESC, visit_time DESC LIMIT 1), '");
                    // กรณีที่ไม่ได้ส่งค่า VN ให้ใช้ VisitId เป็นค่าว่าง ส่วนของโปรแกรม Viewer จะมองเป็น NO VN
                    Utility.printCoreDebug(this, "Utility.isNull(newBpkDocumentScanVO.getVn())");
                    sqlCmd.append("'', '");
                } else
                {
                    if (VisitVO.NO_VN.equals(newBpkDocumentScanVO.getVn()))
                    {
                        Utility.printCoreDebug(this, "VisitVO.NO_VN.equals(newBpkDocumentScanVO.getVn())");
                        sqlCmd.append("'', '");
                    } else
                    {
                        if (newBpkDocumentScanVO.getVn().indexOf("-") != -1 || newBpkDocumentScanVO.getVn().indexOf("/") != -1)
                        {
                            Utility.printCoreDebug(this, "newBpkDocumentScanVO.getVn().indexOf(\"-\") != -1");
                            sqlCmd.append(" (SELECT v.visit_id FROM visit AS v WHERE v.active='1' AND v.hn='").append(newBpkDocumentScanVO.getOriginalHn()).append("' AND format_vn(v.vn)='").append(newBpkDocumentScanVO.getVn()).append("' ORDER BY visit_date DESC, visit_time DESC LIMIT 1), '");
                        } else
                        {
                            Utility.printCoreDebug(this, "else");
                            sqlCmd.append(" (SELECT v.visit_id FROM visit AS v WHERE v.active='1' AND v.hn='").append(newBpkDocumentScanVO.getOriginalHn()).append("' AND v.vn='").append(newBpkDocumentScanVO.getVn()).append("' ORDER BY visit_date DESC, visit_time DESC LIMIT 1), '");
                        }
                    }
                }
                sqlCmd.append(newBpkDocumentScanVO.getFolderName()).append("', '").append(newBpkDocumentScanVO.getImageFileName()).append("', '");
                sqlCmd.append(newBpkDocumentScanVO.getScanEid()).append("', '").append(newBpkDocumentScanVO.getScanDate()).append("', '").append(newBpkDocumentScanVO.getScanTime()).append("', '");
                sqlCmd.append(newBpkDocumentScanVO.getUpdateEid()).append("', '").append(newBpkDocumentScanVO.getUpdateDate()).append("', '").append(newBpkDocumentScanVO.getUpdateTime()).append("', '");
                sqlCmd.append(newBpkDocumentScanVO.getDocumentName()).append("', '").append(newBpkDocumentScanVO.getPrintDate()).append("', '").append(newBpkDocumentScanVO.getPrintTime()).append("', '");
                sqlCmd.append(validateEmployeeId(newBpkDocumentScanVO.getDoctorEid())).append("', '");
                sqlCmd.append(FixDocumentScanStatus.BEGIN_SCAN).append("', '");
                sqlCmd.append(newBpkDocumentScanVO.getBpkFixOrdersheetTypeId()).append("', '");
                sqlCmd.append(newBpkDocumentScanVO.getIsSendToPharmacy()).append("', '");
                sqlCmd.append(newBpkDocumentScanVO.getIsSendToNutrition()).append("')");

                conn = DocScanDAOFactory.getConnection();
                stmt = conn.createStatement();

                Utility.printCoreDebug(this, sqlCmd.toString());
                stmt.executeUpdate(sqlCmd.toString());

                stmt.close();
                conn.close();
            } catch (Exception ex)
            {
                if (conn != null)
                {
                    try
                    {
                        conn.close();
                    } catch (Exception ex2)
                    {
                    }
                }
                ex.printStackTrace();
                throw ex;
            } finally
            {
                conn = null;
                stmt = null;
                sqlCmd = null;
            }
        } else
        {
            newBpkDocumentScanVO.setObjectID(null);
            return newBpkDocumentScanVO;
        }
        return newBpkDocumentScanVO;
    }

    public List listBpkPatientImageFolder()
    {
        List listFolderName = new ArrayList();
        Connection conn = DocScanDAOFactory.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sqlCmd = null;

        try
        {

            sqlCmd = new StringBuffer("SELECT folder_name FROM bpk_patient_image_folder ORDER BY display_order ");

            stmt = conn.createStatement();
            Utility.printCoreDebug(this, sqlCmd.toString());
            rs = stmt.executeQuery(sqlCmd.toString());

            for (; rs.next();)
            {
                listFolderName.add(rs.getString("folder_name"));
            }
            rs.close();

            stmt.close();
            conn.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            sqlCmd = null;
            rs = null;
            stmt = null;
            conn = null;
        }

        return listFolderName;
    }

    public List listDocumentNameInFolder(String folderName)
    {
        List listDocumentName = new ArrayList();
        Connection conn = DocScanDAOFactory.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sqlCmd = null;

        try
        {
            sqlCmd = new StringBuffer("SELECT document_name FROM bpk_patient_image_document WHERE bpk_patient_image_folder_id=(SELECT bpk_patient_image_folder_id FROM bpk_patient_image_folder WHERE folder_name='").append(folderName).append("') ORDER BY document_name ");

            stmt = conn.createStatement();
            Utility.printCoreDebug(this, sqlCmd.toString());
            rs = stmt.executeQuery(sqlCmd.toString());

            for (; rs.next();)
            {
                listDocumentName.add(rs.getString("document_name"));
            }
            rs.close();

            stmt.close();
            conn.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            sqlCmd = null;
            rs = null;
            stmt = null;
            conn = null;
        }

        return listDocumentName;
    }

    public List listBaseServicePointByType(String fixServicePointTypeId) throws Exception
    {
        List list = new ArrayList();

        Connection conn = null;
        Statement stmt;
        ResultSet rs;
        StringBuffer sql = new StringBuffer(
                "SELECT base_service_point_id, description FROM base_service_point WHERE active='1' AND fix_service_point_type_id='").append(
                fixServicePointTypeId).append("' ORDER BY description");

        try
        {
            conn = DocScanDAOFactory.getConnection();
            stmt = conn.createStatement();

            Utility.printCoreDebug(this, sql.toString());
            rs = stmt.executeQuery(sql.toString());

            for (; rs.next();)
            {
                BaseServicePointVO aSpVO = new BaseServicePointVO();

                aSpVO.setObjectID(rs.getString("base_service_point_id"));
                aSpVO.setDescription(rs.getString("description"));

                list.add(aSpVO);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        } finally
        {
            sql = null;
            rs = null;
            stmt = null;
            conn = null;
        }

        return list;
    }

    public List searchPatient(String hn, String patName, String searchCount) throws Exception
    {
        List listPatientVO = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rst = null;
        StringBuffer sqlCmd = null;

        if (Utility.isNotNull(hn) || Utility.isNotNull(patName))
        {
            try
            {

                sqlCmd = new StringBuffer(
                        "SELECT DISTINCT patient_id, format_hn(hn) AS hn, firstname, lastname, imed_get_patient_name(patient_id) AS patient_name, fix_gender_id, bpkget_fix_gender_by_id(fix_gender_id) AS fix_gender_description, birthdate FROM patient WHERE active='1' ");

                if (Utility.isNotNull(hn))
                {
                    sqlCmd.append(" AND hn='").append(Utility.formatHnToDb(hn)).append("'");
                }

                if (Utility.isNotNull(patName))
                {
                    // ส่วนของ Patient name
                    sqlCmd.append(" AND (");
                    // sqlCmd.append("imed_get_patient_name(patient_id) ILIKE '%").append(patName).append("%' ");
                    sqlCmd.append("firstname ILIKE '%").append(patName).append("%' OR ");
                    sqlCmd.append("lastname ILIKE '%").append(patName).append("%' ");
                    sqlCmd.append(") ");
                }
                // sqlCmd.append(" ORDER BY imed_get_patient_name(patient_id) ");
                sqlCmd.append(" ORDER BY firstname, lastname ");
                sqlCmd.append(" LIMIT ").append(searchCount).append(" OFFSET 0");

                conn = DocScanDAOFactory.getConnection();
                stmt = conn.createStatement();

                Utility.printCoreDebug(this, sqlCmd.toString());
                rst = stmt.executeQuery(sqlCmd.toString());
                for (; rst.next();)
                {
                    PatientVO aPatientVO = new PatientVO();

                    aPatientVO.setObjectID(rst.getString("patient_id"));
                    aPatientVO.setHn(rst.getString("hn"));
                    aPatientVO.setPatientName(rst.getString("patient_name"));
                    aPatientVO.setBirthdate(rst.getString("birthdate"));
                    aPatientVO.setFixGenderId(rst.getString("fix_gender_id"));
                    aPatientVO.setFixGenderDescription(rst.getString("fix_gender_description"));

                    listPatientVO.add(aPatientVO);
                }

                rst.close();
                stmt.close();
                conn.close();

            } catch (Exception ex)
            {
                if (conn != null)
                {
                    try
                    {
                        conn.close();
                    } catch (Exception ex2)
                    {
                    }
                }
                ex.printStackTrace();
                throw ex;
            } finally
            {
                rst = null;
                stmt = null;
                conn = null;
            }
        } else
        {
        }
        return listPatientVO;
    }

    public List searchPatientInWard(String hn, String an, String firstname, String lastname, String baseServicePointId) throws Exception
    {
        List listAdmitVO = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rst = null;
        StringBuffer sqlCmd = new StringBuffer();
        try
        {
            sqlCmd.append("SELECT  ");
            sqlCmd.append("visit.patient_id, ");
            sqlCmd.append("visit.visit_id, ");
            sqlCmd.append("admit.admit_id, ");
            sqlCmd.append("visit.hn AS original_hn, ");
            sqlCmd.append("format_hn(visit.hn) AS hn, ");
            sqlCmd.append("format_an(admit.an) AS an, ");
            sqlCmd.append("admit.admit_date, ");
            sqlCmd.append("admit.admit_time, ");
            sqlCmd.append("bpkget_patient_name(visit.patient_id) AS patient_name, ");
            sqlCmd.append("bed_management.base_service_point_id, ");
            sqlCmd.append("bpkget_service_description(bed_management.base_service_point_id) AS base_service_point_description, ");
            sqlCmd.append("bed_management.room_number, ");
            sqlCmd.append("bed_management.bed_number, ");
            sqlCmd.append("ipd_attending_physician.employee_id, ");
            sqlCmd.append("bpkget_employee_name(ipd_attending_physician.employee_id) AS employee_name ");
            sqlCmd.append("FROM admit ");
            sqlCmd.append("INNER JOIN visit ON admit.visit_id = visit.visit_id AND visit.active='1' ");
            sqlCmd.append("INNER JOIN patient ON visit.patient_id = patient.patient_id AND patient.active='1' ");
            sqlCmd.append("INNER JOIN bed_management ON admit.admit_id = bed_management.admit_id AND bed_management.current_bed='1' ");
            sqlCmd.append("LEFT JOIN ipd_attending_physician ON admit.admit_id = ipd_attending_physician.admit_id AND ipd_attending_physician.priority='1' AND is_current='1' ");
            sqlCmd.append("WHERE ");
            sqlCmd.append("admit.active='1' ");
            if (Utility.isNotNull(hn))
            {
                sqlCmd.append(" AND visit.hn='").append(Utility.formatHnToDb(hn)).append("' ");
            }
            if (Utility.isNotNull(an))
            {
                sqlCmd.append(" AND admit.an='").append(Utility.formatAnToDb(an)).append("' ");
            }
            if (Utility.isNotNull(firstname))
            {
                sqlCmd.append(" AND patient.firstname ILIKE '%").append(firstname).append("%' ");
            }
            if (Utility.isNotNull(lastname))
            {
                sqlCmd.append(" AND patient.lastname ILIKE '%").append(lastname).append("%' ");
            }
            if (Utility.isNotNull(baseServicePointId))
            {
                sqlCmd.append(" AND bed_management.base_service_point_id='").append(baseServicePointId).append("' ");
            }
            sqlCmd.append("ORDER BY bpkget_service_description(bed_management.base_service_point_id), bed_management.bed_number, an");

            conn = DocScanDAOFactory.getConnection();
            stmt = conn.createStatement();
            Utility.printCoreDebug(this, sqlCmd.toString());
            rst = stmt.executeQuery(sqlCmd.toString());

            for (; rst.next();)
            {
                AdmitVO tmpAdmitVO = new AdmitVO();
                tmpAdmitVO.setObjectID(rst.getString("admit_id"));
                tmpAdmitVO.setVisitId(rst.getString("visit_id"));
                tmpAdmitVO.setPatientId(rst.getString("patient_id"));
                tmpAdmitVO.setAdmitDate(rst.getString("admit_date"));
                tmpAdmitVO.setAdmitTime(rst.getString("admit_time"));
                tmpAdmitVO.setPatientName(rst.getString("patient_name"));
                tmpAdmitVO.setOriginalHn(rst.getString("original_hn"));
                tmpAdmitVO.setHn(rst.getString("hn"));
                tmpAdmitVO.setAn(rst.getString("an"));
                tmpAdmitVO.setBaseServicePointDescription(rst.getString("base_service_point_description"));
                tmpAdmitVO.setRoomNumber(rst.getString("room_number"));
                tmpAdmitVO.setBedNumber(rst.getString("bed_number"));
                tmpAdmitVO.setBaseServicePointId(rst.getString("base_service_point_id"));
                tmpAdmitVO.setEmployeeId(rst.getString("employee_id"));
                tmpAdmitVO.setEmployeeName(rst.getString("employee_name"));

                listAdmitVO.add(tmpAdmitVO);
            }

            rst.close();
            stmt.close();
            conn.close();

        } catch (Exception ex)
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                } catch (Exception ex2)
                {
                }
            }
            ex.printStackTrace();
            throw ex;
        } finally
        {
        }
        return listAdmitVO;
    }

    public VisitVO readVisit(String visitId)
    {
        VisitVO aVisitVO = new VisitVO();
        if (Utility.isNotNull(visitId))
        {
            Connection conn = DocScanDAOFactory.getConnection();
            Statement stmt = null;
            ResultSet rs = null;
            StringBuilder sqlCmd = new StringBuilder("SELECT patient_id, format_hn(hn) AS hn, imed_get_patient_name(patient_id) AS patient_name, visit_id, vn AS orginalvn, format_vn(vn) AS vn, visit_date, visit_time, get_first_pdx_name(visit.visit_id) AS pdx ");
            sqlCmd.append(" FROM visit ");
            sqlCmd.append(" WHERE visit_id='").append(visitId).append("'");

            try
            {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sqlCmd.toString());

                if (rs.next())
                {
                    aVisitVO.setPatientId(rs.getString("patient_id"));
                    aVisitVO.setHn(rs.getString("hn"));
                    aVisitVO.setPatientName(rs.getString("patient_name"));
                    aVisitVO.setVn(rs.getString("vn"));
                    aVisitVO.setVisitDate(rs.getString("visit_date"));
                    aVisitVO.setVisitTime(rs.getString("visit_time"));
                    aVisitVO.setPDx(rs.getString("pdx"));
                }
                rs.close();
                stmt.close();
                conn.close();

            } catch (Exception ex)
            {
                ex.printStackTrace();
            } finally
            {
                sqlCmd = null;
                rs = null;
                stmt = null;
                conn = null;
            }
        }
        return aVisitVO;
    }

    public EmployeeRoleVO getEmployeeRoleVO(String username, String password) throws Exception
    {
        EmployeeRoleVO aEmployeeRoleVO = null;
        Connection conn = null;
        Statement stmt;
        ResultSet rs;
        StringBuffer sql = new StringBuffer(
                "SELECT employee.employee_id, bpkget_employee_name(employee.employee_id) employee_name, employee_role.admin_manage_item_auth, bpk_employee_role.admin_manage_item_extend_auth FROM employee ");
        sql.append(" INNER JOIN employee_role ON employee.employee_id=employee_role.employee_id ");
        sql.append(" LEFT JOIN bpk_employee_role ON employee.employee_id=bpk_employee_role.employee_id ");
        sql.append(" WHERE employee.employee_id='").append(username).append("' AND employee.password = md5('").append(password).append("') ORDER BY employee_role_id LIMIT 1");

        try
        {
            conn = DocScanDAOFactory.getConnection();
            stmt = conn.createStatement();

            Utility.printCoreDebug(this, sql.toString());
            rs = stmt.executeQuery(sql.toString());

            if (rs.next())
            {
                aEmployeeRoleVO = new EmployeeRoleVO();

                aEmployeeRoleVO.setEmployeeId(rs.getString("employee_id"));
                aEmployeeRoleVO.setEmployeeName(rs.getString("employee_name"));
                aEmployeeRoleVO.setAdminManageItemAuth(rs.getString("admin_manage_item_auth"));
                aEmployeeRoleVO.setAdminManageItemExtendAuth(rs.getString("admin_manage_item_extend_auth"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        } finally
        {
            sql = null;
            rs = null;
            stmt = null;
            conn = null;
        }

        return aEmployeeRoleVO;
    }

    public String getDefaultServicePointByEmployeeId(String username) throws Exception
    {
        String baseServicePointId = null;
        Connection conn = null;
        Statement stmt;
        ResultSet rs;
        StringBuffer sql = new StringBuffer(
                "SELECT base_service_point_id FROM employee WHERE employee_id='").append(username).append("'");

        try
        {
            conn = DocScanDAOFactory.getConnection();
            stmt = conn.createStatement();

            Utility.printCoreDebug(this, sql.toString());
            rs = stmt.executeQuery(sql.toString());

            if (rs.next())
            {
                baseServicePointId = rs.getString("base_service_point_id");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        } finally
        {
            sql = null;
            rs = null;
            stmt = null;
            conn = null;
        }

        return baseServicePointId;
    }

    private String validateEmployeeId(String doctorCode) throws Exception
    {
        String employeeId = "";
        if (Utility.isNotNull(doctorCode) && !"".equals(doctorCode.trim()))
        {
            Connection conn = null;
            Statement stmt;
            ResultSet rs;
            StringBuffer sql = new StringBuffer(
                    "SELECT employee.employee_id FROM employee WHERE employee.employee_id='").append(doctorCode).append("' OR employee.employee_code = '").append(doctorCode).append("' ORDER BY employee_id LIMIT 1");

            try
            {
                conn = DocScanDAOFactory.getConnection();
                stmt = conn.createStatement();

                Utility.printCoreDebug(this, sql.toString());
                rs = stmt.executeQuery(sql.toString());

                if (rs.next())
                {
                    employeeId = rs.getString("employee_id");
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception ex)
            {
                ex.printStackTrace();
                throw ex;
            } finally
            {
                sql = null;
                rs = null;
                stmt = null;
                conn = null;
            }
        }
        return employeeId;
    }
}
