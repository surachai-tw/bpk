package com.bpk.bop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
public class RunFetch
{
    private static boolean autoRun = false;

    public static void main(String args[])
    {
        String[] reportDate = new String[2];
        if(args.length==1 && "auto".equalsIgnoreCase(args[0]))
        {
            autoRun = true;
            reportDate[0] = RunFetch.getCurrentDateForExecute(-1);
            reportDate[1] = RunFetch.getCurrentDateForExecute(0);
        }
                
        if(args.length == 2 || autoRun)
        {
            if(args.length == 2 && !autoRun)
            {
                reportDate[0] = args[0];
                reportDate[1] = args[1];
            }

            Calendar runCal = Calendar.getInstance();
            runCal.setTime(getDateChristFromString(reportDate[0]));

            Calendar stopCal = Calendar.getInstance();
            stopCal.setTime(getDateChristFromString(reportDate[1]));

            if (runCal.compareTo(stopCal) <= 0)
            {
                for (; runCal.compareTo(stopCal) <= 0;)
                {
                    fetchAllFileForDate(convertDate2StdFormat(runCal.getTime()));
                    runCal.set(Calendar.DAY_OF_MONTH, runCal.get(Calendar.DAY_OF_MONTH) + 1);
                }
            }
            else
            {
                System.out.println("FromDate must be before ToDate.");
            }
        }
        else
        {
            System.out.println("Please define FromDate and ToDate.");
            System.out.println();
            System.out.println("For example for range of date");
            System.out.println("java " + (new RunFetch()).getClass().toString().replaceAll("class ", "") + " 2015-08-01 2015-08-11");
            System.out.println();
            System.out.println("For example for automatic range (FromDate = Yesterday, ToDate = Today)");
            System.out.println("java " + (new RunFetch()).getClass().toString().replaceAll("class ", "") + " auto");
        }
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

    private static void fetchAllFileForDate(String fromDate)
    {
        FetchDAO aFetchDAO = new FetchDAO();
        BufferedReader in = null;
        try
        {
            System.out.println("Date: " + fromDate);
            List<String> listSqlFile = listSqlFileFromPath();
            for (int i = 0; i < listSqlFile.size(); i++)
            {
                String fileName = (String) listSqlFile.get(i);

                String line = null;
                StringBuilder sql = new StringBuilder();
                in = new BufferedReader(new FileReader(new File(fileName)));
                for (int j = 0; (line = in.readLine()) != null; j++)
                {
                    sql.append(line).append(" ");
                }
                in.close();

                // Replace parameter ด้วย fromDate
                String sqlCmdText = sql.toString();
                StringBuilder sqlCmdExe = new StringBuilder();
                for (int idxOfParam = sqlCmdText.indexOf("$P"), startIdx = 0; idxOfParam != -1;)
                {
                    sqlCmdExe.append(sqlCmdText.substring(startIdx, idxOfParam));
                    String tmpSqlCmdText = sqlCmdText.substring(idxOfParam);

                    sqlCmdExe.append(fromDate);

                    sqlCmdText = tmpSqlCmdText;
                    sqlCmdText = sqlCmdText.substring(sqlCmdText.indexOf("}") + 1);
                    idxOfParam = sqlCmdText.indexOf("$P");
                }
                sqlCmdExe.append(sqlCmdText);
                // System.out.println(sqlCmdExe.toString());

                System.out.println("     File #" + (i + 1) + " execute " + aFetchDAO.execute(sqlCmdExe.toString()) + " records");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            in = null;
            aFetchDAO = null;
        }
    }

    private static List<String> listSqlFileFromPath()
    {
        List<String> listSqlFile = null;
        try
        {
            String path = System.getProperty("user.dir") + System.getProperty("file.separator") + "sql" + System.getProperty("file.separator");
            // System.out.println("Path = " + path);
            File pathSql = new File(path);
            if (pathSql.exists())
            {
                File[] files = pathSql.listFiles(new FileFilterSql("SQL File", "sql"));
                listSqlFile = new ArrayList(files.length);

                for (int i = 0; i < files.length; i++)
                {
                    // System.out.println(files[i].getName());
                    if (files[i].isFile())
                    {
                        listSqlFile.add(files[i].getAbsolutePath());
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
        }

        return listSqlFile;
    }
}
