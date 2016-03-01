package com.bpk.pgToSqlServer.ui;

import com.bpk.pgToSqlServer.dao.DocScanDAO;
import com.bpk.pgToSqlServer.dao.SsbDAO;
import com.bpk.pgToSqlServer.dto.PatientVO;
import com.bpk.pgToSqlServer.dto.VisitVO;
import com.bpk.pgToSqlServer.utility.Utility;
import java.util.List;

/**
 *
 * @author surachai.tw
 */
public class SyncVisit implements Runnable
{

    private int status;

    public void run()
    {
        List listHnPrefix = null;
        SsbDAO aSsbDAO = new SsbDAO();
        DocScanDAO aDocScanDAO = new DocScanDAO();
        try
        {
            listHnPrefix = aDocScanDAO.listPrefix(3);
            if (Utility.isNotNull(listHnPrefix))
            {
                System.out.println("All Prefix = " + listHnPrefix.size());
                for (int i = 0, sizei = listHnPrefix.size(); i < sizei; i++)
                {
                    String prefix = (String) listHnPrefix.get(i);

                    List listPatientId = aDocScanDAO.listPatientVOByHnWithPrefix(prefix);
                    if (Utility.isNotNull(listPatientId))
                    {
                        System.out.println("Prefix '" + prefix + "' listPatientId = " + listPatientId.size());
                        for (int j = 0, sizej = listPatientId.size(); j < sizej; j++)
                        {
                            PatientVO aPatientVO = (PatientVO) listPatientId.get(j);

                            List listVisitVO = aSsbDAO.listVisitByHn(aPatientVO.getHn());

                            for(int k=0, sizek=listVisitVO.size(); k<sizek; k++)
                            {
                                VisitVO aVisitVO = (VisitVO)listVisitVO.get(k);

                                aDocScanDAO.updateVisit(aVisitVO);
                                aDocScanDAO.updateIssuedFile(aVisitVO);
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
}
