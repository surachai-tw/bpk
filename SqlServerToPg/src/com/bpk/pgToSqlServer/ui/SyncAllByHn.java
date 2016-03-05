package com.bpk.pgToSqlServer.ui;

import com.bpk.pgToSqlServer.dao.DocScanDAO;
import com.bpk.pgToSqlServer.dao.SsbDAO;
import com.bpk.pgToSqlServer.dto.DiagnosisVO;
import com.bpk.pgToSqlServer.dto.PatientVO;
import com.bpk.pgToSqlServer.dto.VisitVO;
import com.bpk.pgToSqlServer.utility.Utility;
import java.util.List;

/**
 *
 * @author surachai.tw
 */
public class SyncAllByHn implements Runnable
{

    private String hn;
    private int status;

    public static void main(String args[])
    {
        Utility.printCoreDebug(new SyncAllByHn(), "arguments.length = "+args.length);
        if (args.length > 0)
        {
            syncAllByHn(args[0]);
        }
    }

    public static void syncAllByHn(String hn)
    {
        if(hn!=null)
        {
            SyncAllByHn aSyncAllByHn = new SyncAllByHn();
            Utility.printCoreDebug(new SyncAllByHn(), "Input HN: " + hn);
            aSyncAllByHn.setHn(hn);

            Thread aThreadAllByHn = new Thread(aSyncAllByHn);
            aThreadAllByHn.start();
        }
    }

    public void run()
    {
        SsbDAO aSsbDAO = new SsbDAO();
        DocScanDAO aDocScanDAO = new DocScanDAO();
        try
        {
            List listVisitVO = aSsbDAO.listVisitByHn(getHn());
            if (Utility.isNotNull(listVisitVO))
            {
                Utility.printCoreDebug(this, "Found visit " + listVisitVO.size() + " visits");

                for (int j = 0, sizej = listVisitVO.size(); j < sizej; j++)
                {
                    VisitVO aVisitVO = (VisitVO) listVisitVO.get(j);
                    Utility.printCoreDebug(this, "VN " + aVisitVO.getVn());

                    aDocScanDAO.updateVisit(aVisitVO);
                    aDocScanDAO.updateIssuedFile(aVisitVO);

                    List listDiagnosisVO = aSsbDAO.listDxByHnAndVisitId(hn, aVisitVO.getVisitId());
                    if (Utility.isNotNull(listDiagnosisVO))
                    {
                        Utility.printCoreDebug(this, "Found Dx " + listDiagnosisVO.size());

                        for (int k = 0, sizek = listDiagnosisVO.size(); k < sizek; k++)
                        {
                            DiagnosisVO aDiagnosisVO = (DiagnosisVO) listDiagnosisVO.get(k);
                            Utility.printCoreDebug(this, "ICD10 " + aDiagnosisVO.getIcd10Code());

                            aDocScanDAO.updateDiagnosis(aDiagnosisVO);
                        }
                    }
                    status = (int) Math.floor(100 * (float) j / sizej);
                }
            } else
            {
                Utility.printCoreDebug(this, "No visit found");
            }

            status = 100;
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
        }
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * @return the hn
     */
    public String getHn()
    {
        return hn;
    }

    /**
     * @param hn the hn to set
     */
    public void setHn(String hn)
    {
        this.hn = hn;
    }
}
