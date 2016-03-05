import com.bpk.pgToSqlServer.ui.SyncAllByHn;
import com.bpk.pgToSqlServer.dao.DocScanDAO;
import com.bpk.pgToSqlServer.dao.SsbDAO;
import com.bpk.pgToSqlServer.dto.PatientVO;
import com.bpk.pgToSqlServer.utility.Utility;
import java.util.List;

/**
 *
 * @author surachai.tw
 */
public class SyncJob implements Runnable
{

    private int numBackDay;
    private int status = 100;

    public static void main(String args[])
    {
        Utility.printCoreDebug(new SyncJob(), "arguments.length = " + args.length);
        if (args.length > 0)
        {
            int numBackDay = 0;
            try
            {
                numBackDay = Integer.parseInt(args[0]);
            } catch (Exception ex)
            {
                numBackDay = 1;
            }
            for (;;)
            {
                syncJob(numBackDay);
                try
                {
                    Thread.sleep(5 * 60 * 1000);
                } catch (Exception ex)
                {
                }
            }
        }
    }
    static SyncJob aSyncJob = new SyncJob();

    public static void syncJob(int numBackDay)
    {
        if (numBackDay >= 0 && aSyncJob.getStatus() == 100)
        {
            Utility.printCoreDebug(new SyncJob(), "Num baack day: " + numBackDay);
            aSyncJob.setNumBackDay(numBackDay);

            Thread aThread = new Thread(aSyncJob);
            aThread.start();
        } else if (aSyncJob.getStatus() < 100)
        {
            Utility.printCoreDebug(new SyncJob(), "A previous job is not complete yet");
        }
    }

    public void run()
    {
        List listHnLastVisit = null;
        SsbDAO aSsbDAO = new SsbDAO();
        DocScanDAO aDocScanDAO = new DocScanDAO();
        try
        {
            listHnLastVisit = aSsbDAO.listHnByLastVisitDate(numBackDay);
            if (Utility.isNotNull(listHnLastVisit))
            {
                System.out.println("All number of last HN visit = " + listHnLastVisit.size());
                for (int i = 0, sizei = listHnLastVisit.size(); i < sizei; i++)
                {
                    String hn = (String) listHnLastVisit.get(i);
                    List listPatientVO = aSsbDAO.listPatientByHnWithPrefix(hn);

                    for (int j = 0, sizej = listPatientVO.size(); j < sizej; j++)
                    {
                        PatientVO aPatientVO = (PatientVO) listPatientVO.get(j);

                        aDocScanDAO.updatePatient(aPatientVO);
                        SyncAllByHn.syncAllByHn(hn);
                    }
                    status = (int) Math.floor(100 * (float) i / sizei);
                }
            } else
            {
                Utility.printCoreDebug(this, "Not found hn visit");
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
     * @return the numBackDay
     */
    public int getNumBackDay()
    {
        return numBackDay;
    }

    /**
     * @param numBackDay the numBackDay to set
     */
    public void setNumBackDay(int numBackDay)
    {
        this.numBackDay = numBackDay;
    }
}
