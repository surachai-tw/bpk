package com.bpk.app.emrapp;

import com.bpk.core.emrcore.dao.DocScanDAOFactory;
import com.bpk.core.emrcore.dao.DocScanDAO;
import com.bpk.persistence.emrdto.BpkDocumentScanVO;
import com.bpk.persistence.emrdto.FolderVO;
import com.bpk.persistence.emrdto.PatientVO;
import com.bpk.persistence.emrdto.VisitVO;
import com.bpk.utility.Utility;
import java.util.List;

/**
 *
 * @author SURACHAI.TO
 */
public class ConvertDocScan implements Runnable
{
    private PatientVO patientVO = null;

    private int status = 0;
    private String statusText = "";

    public void run()
    {
        List listVisitVO = null;
        DocScanDAO aDao = DocScanDAOFactory.newDocScanDAO();
        this.statusText = "Connect to database ...";
        this.status = 1;
        try
        {
            this.statusText = "Query from database ...";
            this.status = 5;
            listVisitVO = aDao.listVisitByPatientId(this.getPatientVO().getObjectID());
            if (Utility.isNotNull(listVisitVO))
            {
                for (int i = 0, sizei = listVisitVO.size(); i < sizei; i++)
                {
                    VisitVO tmpVisitVO = (VisitVO) listVisitVO.get(i);

                    List listFolderVO = tmpVisitVO.getListFolderVO();
                    if (Utility.isNotNull(listFolderVO))
                    {
                        for (int j = 0, sizej = listFolderVO.size(); j < sizej; j++)
                        {
                            FolderVO tmpFolderVO = (FolderVO) listFolderVO.get(j);

                            List listBpkDocumentScanVO = tmpFolderVO.getListBpkDocumentScanVO();

                            if (Utility.isNotNull(listBpkDocumentScanVO))
                            {
                                for (int k = 0, sizek = listBpkDocumentScanVO.size(); k < sizek; k++)
                                {
                                    BpkDocumentScanVO tmpBpkDocumentScanVO = (BpkDocumentScanVO) listBpkDocumentScanVO.get(k);

                                    String url = DocScanDAOFactory.getDocScanUrl() + Utility.getHnImageFolder(this.getPatientVO().getOriginalHn()) + "/" + tmpFolderVO.getObjectID() + "/" + tmpBpkDocumentScanVO.getImageFileName();
                                    Utility.printCoreDebug(this, url);
                                    String path = DocScanDAOFactory.getDocScanOutputPath() + Utility.getHnImageFolder(this.getPatientVO().getOriginalHn(), System.getProperty("file.separator")) + System.getProperty("file.separator") + tmpFolderVO.getObjectID() + System.getProperty("file.separator");
                                    Utility.printCoreDebug(this, path);

                                    // Generate JRXML File
                                    String jrxmlFilename = GenerateJrxml.generateJrxmlPage(tmpBpkDocumentScanVO.getImageFileName(), path, false, 0, path);
                                    // Convert PDF File 
                                    Jrxml2Pdf.convertPage(jrxmlFilename, path, path);

                                    // Delete JRXML after convert
                                    GenerateJrxml.deleteJrxmlFile(path+jrxmlFilename);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        this.statusText = "Finished";
        this.status = 100;
    }

    /**
     * @return the patientVO
     */
    public PatientVO getPatientVO()
    {
        return patientVO;
    }

    /**
     * @param patientVO the patientVO to set
     */
    public void setPatientVO(PatientVO patientVO)
    {
        this.patientVO = patientVO;
    }


    public int getStatus()
    {
        return status;
    }

    public String getStatusText()
    {
        return statusText;
    }

}
