package com.bpk.bop;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author SURACHAI.TO
 */
public class RunAllocate
{
    public RunAllocate()
    {
    }

    /**
     *
     * @param backDate หมายถึงให้ย้อนไปกี่วัน
     * @return YYYY-MM-DD
     */
    public static String getCurrentDateForExecute(int backDate)
    {
        NumberFormat nf2 = NumberFormat.getInstance();
        nf2.setGroupingUsed(false);
        nf2.setMaximumIntegerDigits(2);
        nf2.setMinimumIntegerDigits(2);
        nf2.setMaximumFractionDigits(0);
        nf2.setMinimumFractionDigits(0);
        NumberFormat nf4 = NumberFormat.getInstance();
        nf4.setGroupingUsed(false);
        nf4.setMaximumIntegerDigits(4);
        nf4.setMinimumIntegerDigits(4);
        nf4.setMaximumFractionDigits(0);
        nf4.setMinimumFractionDigits(0);
        Calendar aCal = Calendar.getInstance(new Locale("en", "UK"));
        try
        {
            if(backDate!=0)
            {
                aCal.set(Calendar.DAY_OF_MONTH, aCal.get(Calendar.DAY_OF_MONTH)+backDate);
            }

            String YY = nf4.format(aCal.get(Calendar.YEAR));
            String MM = nf2.format(aCal.get(Calendar.MONTH)+1);
            String DD = nf2.format(aCal.get(Calendar.DAY_OF_MONTH));

            return YY+"-"+MM+"-"+DD;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            nf2 = null;
            nf4 = null;
            aCal = null;
        }
        return "";
    }

    private static boolean autoRun = false;

    /**
     * @param args the command line arguments
     *
    public static void main(String[] args)
    {
        // , "2015-05-02", "2015-05-03", "2015-05-04", "2015-05-05", "2015-05-06", "2015-05-07", "2015-05-08", "2015-05-09", "2015-05-10"
        // String[] reportDate = {"2015-05-01", "2015-05-02", "2015-05-03", "2015-05-04", "2015-05-05", "2015-05-06", "2015-05-07", "2015-05-08", "2015-05-09", "2015-05-10"};
        String[] reportDate = null;

        if(args.length==1 && "auto".equalsIgnoreCase(args[0]))
        {
            autoRun = true;
        }
        else if(args.length==2)
        {
            List<String> listDate = new ArrayList<String>();
            String fromDate = args[0];
            String toDate = args[1];

            Calendar runCal = Calendar.getInstance();
            runCal.setTime(getDateChristFromString(fromDate));

            Calendar stopCal = Calendar.getInstance();
            stopCal.setTime(getDateChristFromString(toDate));

            if (runCal.compareTo(stopCal) <= 0)
            {
                for (; runCal.compareTo(stopCal) <= 0;)
                {
                    listDate.add(convertDate2StdFormat(runCal.getTime()));
                    runCal.set(Calendar.DAY_OF_MONTH, runCal.get(Calendar.DAY_OF_MONTH) + 1);
                }

                reportDate = listDate.toArray(new String[listDate.size()]);
            }
            else
            {
                System.out.println("FromDate must be before ToDate.");
                return;
            }
        }

        if(autoRun)
        {
            reportDate = new String[1];
            reportDate[0] = RunAllocate.getCurrentDateForExecute(-1);
        }

        AllocateDAO aDebitDAO = new AllocateDAO();
        for(int k=0; reportDate!=null && k<reportDate.length; k++)
        {
            try
            {
                //|-- OPD ------------------------------------------------------                
                List<String> listVisitId = aDebitDAO.listOpdVisitIdForReceiveDae(reportDate[k]);
                // new ArrayList();
                // listVisitId.add("115050215421582701");
                int sizei=listVisitId.size();
                long startMs = System.currentTimeMillis();
                System.out.println("Prepare to allocate "+sizei+" visit on date "+reportDate[k]);
                for(int i=0; i<sizei; i++)
                {
                    aDebitDAO.makeAlloacteOpd(listVisitId.get(i), reportDate[k]);
                }
                long finishMs = System.currentTimeMillis();
                System.out.println("Allocated "+reportDate[k]+" finish in "+(((float)(finishMs-startMs)/1000)/60)+" Minutes");                                 
                //|-------------------------------------------------------------

                //|-- IPD ------------------------------------------------------
                listVisitId = aDebitDAO.listIpdVisitIdForDischargeDae(reportDate[k]);
                // new ArrayList();
                // listVisitId.add("115050215421582701");
                sizei=listVisitId.size();
                startMs = System.currentTimeMillis();
                System.out.println("Prepare to allocate "+sizei+" visit on date "+reportDate[k]);
                for(int i=0; i<sizei; i++)
                {
                    aDebitDAO.makeAlloacteIpd(listVisitId.get(i), reportDate[k]);
                }
                finishMs = System.currentTimeMillis();
                System.out.println("Allocated "+reportDate[k]+" finish in "+(((float)(finishMs-startMs)/1000)/60)+" Minutes");
                //|-------------------------------------------------------------

            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            finally
            {
            }
        }
    }
     */

    /* For test by visit_id */
    public static void main(String[] args)
    {
        AllocateDAO aAllocateDAO = new AllocateDAO();
        long startMs = System.currentTimeMillis();
        String date = "2015-09-13";
        System.out.println("Prepare to allocate visit on date "+date);
        aAllocateDAO.makeAlloacteIpd("115091304594064901", date);
        long finishMs = System.currentTimeMillis();
        System.out.println("Allocated "+date+" finish in "+(((float)(finishMs-startMs)/1000)/60)+" Minutes");
        //|-------------------------------------------------------------
    }

    public static Date getDateChristFromString(String yyyy_mm_dd)
    {
        if (yyyy_mm_dd != null && (yyyy_mm_dd.length() == 10))
        {
            String yyyy = yyyy_mm_dd.substring(0, 4);
            String mm = yyyy_mm_dd.substring(5, 7);
            String dd = yyyy_mm_dd.substring(8, 10);

            Calendar cal = Calendar.getInstance(Locale.UK);
            int y = Integer.parseInt(yyyy);
            if (y > 2500)
            {
                y = y - 543;
            }
            cal.set(Calendar.YEAR, y);
            cal.set(Calendar.MONTH, Integer.parseInt(mm) - 1);
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dd));

            return cal.getTime();
        }

        return null;
    }

    public static String convertDate2StdFormat(java.util.Date aDate)
    {
        if (aDate != null)
        {
            Calendar aCal = Calendar.getInstance(Locale.ENGLISH);
            StringBuilder stdDate = new StringBuilder();
            NumberFormat nf2 = NumberFormat.getInstance();
            NumberFormat nf4 = NumberFormat.getInstance();

            nf2.setMaximumFractionDigits(0);
            nf2.setMinimumFractionDigits(0);
            nf2.setMaximumIntegerDigits(2);
            nf2.setMinimumIntegerDigits(2);
            nf2.setGroupingUsed(false);

            nf4.setMaximumFractionDigits(0);
            nf4.setMinimumFractionDigits(0);
            nf4.setMaximumIntegerDigits(4);
            nf4.setMinimumIntegerDigits(4);
            nf4.setGroupingUsed(false);

            try
            {
                aCal.setTime(aDate);

                int year = aCal.get(Calendar.YEAR);
                int month = aCal.get(Calendar.MONTH) + 1;
                int date = aCal.get(Calendar.DAY_OF_MONTH);

                return stdDate.append(nf4.format(year)).append("-").append(nf2.format(month)).append("-").append(nf2.format(date)).toString();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                aCal = null;
            }
            return stdDate.toString();
        }
        return "";
    }
}
