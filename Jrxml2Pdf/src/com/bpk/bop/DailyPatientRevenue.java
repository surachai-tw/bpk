package com.bpk.bop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.sql.Connection;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

/**
 *
 * @author SURACHAI.TO
 */
public class DailyPatientRevenue
{

    private static boolean autoRun = false;

    public static void main(String args[])
    {
        String reportDate[] = new String[2];
        if (args.length == 1 && "auto".equalsIgnoreCase(args[0]))
        {
            autoRun = true;
            reportDate[0] = DailyPatientRevenue.getCurrentDateForExecute(-1);
            reportDate[1] = DailyPatientRevenue.getCurrentDateForExecute(-1);
        }

        if (args.length == 2 || autoRun)
        {
            if (args.length == 2 && !autoRun)
            {
                reportDate[0] = args[0];
                reportDate[1] = args[1];
            }

            Calendar runCal = Calendar.getInstance();
            runCal.setTime(getDateChristFromString(reportDate[0]));

            Calendar stopCal = Calendar.getInstance();
            stopCal.setTime(getDateChristFromString(reportDate[1]));

            if (runCal.compareTo(stopCal) > 0)
            {
                System.out.println("FromDate must be before ToDate.");
                return;
            }

            for (; runCal.compareTo(stopCal) <= 0;)
            {
                reportDate[0] = convertDate2StdFormat(runCal.getTime());

                HashMap hm = null;
                // System.out.println("Usage: ReportGenerator ....");

                BufferedReader in = null;
                Connection conn = null;
                try
                {
                    System.out.println("Start ....");

                    in = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "DailyPatientRevenue.properties")));
                    String line = null;
                    String pathjrxml = null;
                    String filejrxml = null;
                    String pathoutput = null;
                    for (int i = 0; (line = in.readLine()) != null && i < 9; i++)
                    {
                        String key = line.substring(0, line.indexOf("="));
                        String value = line.substring(line.indexOf("=") + 1).trim();

                        //System.out.println("Key = '" + key+"'");
                        //System.out.println("Value = '" + value+"'");

                        if ("pathjrxml".equalsIgnoreCase(key))
                        {
                            pathjrxml = value;
                        }
                        else if ("filejrxml".equalsIgnoreCase(key))
                        {
                            filejrxml = value;
                        }
                        else if ("pathoutput".equalsIgnoreCase(key))
                        {
                            pathoutput = value;
                        }
                    }
                    in.close();

                    // Get jasper report
                    String[] filenames = null;
                    if (filejrxml != null)
                    {
                        filenames = filejrxml.split(",");
                    }

                    if (filenames != null && filenames.length > 0)
                    {
                        for (int i = 0; i < filenames.length; i++)
                        {
                            JasperCompileManager.compileReportToFile(pathjrxml + System.getProperty("file.separator") + filenames[i] + ".jrxml", pathjrxml + System.getProperty("file.separator") + filenames[i] + ".jasper");
                        }
                    }

                    // Create arguments
                    hm = new HashMap();
                    hm.put("dBeginDate", reportDate[0]);
                    hm.put("dEndDate", reportDate[0]);

                    conn = DAOFactory.getConnection();
                    // Generate jasper print
                    JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(pathjrxml + System.getProperty("file.separator") + filenames[filenames.length - 1] + ".jasper", hm, conn);

                    // ���ҧ Folder �����
                    String outputFolder = mkdirFromDate(pathoutput, reportDate[0]);

                    System.out.println("yyyy-mm-DD = " + reportDate[0]);

                    // Export pdf file
                    JasperExportManager.exportReportToPdfFile(jprint, outputFolder + System.getProperty("file.separator") + (new SimpleDateFormat("dd MMM yyyy", new Locale("en", "US")).format((java.util.Date) (java.sql.Date.valueOf(reportDate[0])))) + ".pdf");

                    System.out.println("Done exporting reports to pdf");

                    conn.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                runCal.set(Calendar.DAY_OF_MONTH, runCal.get(Calendar.DAY_OF_MONTH) + 1);
            }
        }
        else
        {
            System.out.println("Please define FromDate and ToDate.");
            System.out.println();
            System.out.println("For example for range of date");
            System.out.println("java " + (new DailyPatientRevenue()).getClass().toString().replaceAll("class ", "") + " 2015-08-01 2015-08-11");
            System.out.println();
            System.out.println("For example for automatic range (FromDate = Yesterday, ToDate = Today)");
            System.out.println("java " + (new DailyPatientRevenue()).getClass().toString().replaceAll("class ", "") + " auto");
        }
    }

    /**
     *
     * @param backDate ���¶֧�����͹仡���ѹ
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
            if (backDate != 0)
            {
                aCal.set(Calendar.DAY_OF_MONTH, aCal.get(Calendar.DAY_OF_MONTH) + backDate);
            }

            String YY = nf4.format(aCal.get(Calendar.YEAR));
            String MM = nf2.format(aCal.get(Calendar.MONTH) + 1);
            String DD = nf2.format(aCal.get(Calendar.DAY_OF_MONTH));

            return YY + "-" + MM + "-" + DD;
        }
        catch (Exception ex)
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
        
    /**
     * ���ҧ Folder �ҡ �ѹ��� 
     */
    public static String mkdirFromDate(String rootDir, String stdDateFormat)
    {
        String path = null;
        if (stdDateFormat != null && !"".equals(stdDateFormat))
        {
            String[] folders = stdDateFormat.split("-");
            for (int i = 0; i < 2; i++)
            {
                path = i == 0 ? rootDir + System.getProperty("file.separator") + folders[i] + System.getProperty("file.separator") + "Daily" : rootDir + System.getProperty("file.separator") + folders[i - 1] + System.getProperty("file.separator") + "Daily" + System.getProperty("file.separator") + folders[i];
                File root = new File(path);

                if (!root.exists())
                {
                    root.mkdirs();
                }
            }
        }
        return path;
    }
}
