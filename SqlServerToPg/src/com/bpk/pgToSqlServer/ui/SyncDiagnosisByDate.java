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
public class SyncDiagnosisByDate implements Runnable
{
    private int backDate = 365;
    private int status;

    public void run()
    {
        List listVisitDate = null;
        SsbDAO aSsbDAO = new SsbDAO();
        DocScanDAO aDocScanDAO = new DocScanDAO();
        try
        {
            listVisitDate = aDocScanDAO.listVisitDate(getBackDate());
            if (Utility.isNotNull(listVisitDate))
            {
                System.out.println("All visit date in last "+getBackDate()+" days = " + listVisitDate.size());
                for (int i = 0, sizei = listVisitDate.size(); i < sizei; i++)
                {
                    String visitDate = (String) listVisitDate.get(i);

                    List listVisitVO = aDocScanDAO.listVisitByVisitDate(visitDate);
                    if (Utility.isNotNull(listVisitVO))
                    {
                        System.out.println("Visit date '" + visitDate + "' size = " + listVisitVO.size() + " visits");
                        for (int j = 0, sizej = listVisitVO.size(); j < sizej; j++)
                        {
                            VisitVO aVisitVO = (VisitVO) listVisitVO.get(j);

                            List listDiagnosisVO = aSsbDAO.listDxByHnAndVisitId(aVisitVO.getHn(), aVisitVO.getVisitId());

                            for(int k=0, sizek=listDiagnosisVO.size(); k<sizek; k++)
                            {
                                DiagnosisVO aDiagnosisVO = (DiagnosisVO)listDiagnosisVO.get(k);

                                aDocScanDAO.updateDiagnosis(aDiagnosisVO);
                            }
                        }
                    }
                    status = (int) Math.floor(100 * (float) i / sizei);
                }
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
     * @return the backDate
     */
    public int getBackDate()
    {
        return backDate;
    }

    /**
     * @param backDate the backDate to set
     */
    public void setBackDate(int backDate)
    {
        this.backDate = backDate;
    }
}
