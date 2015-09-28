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
public class RunRevenueFetch
{

    public static void main(String args[])
    {
        if (args.length == 2)
        {
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
            System.out.println("For example");
            System.out.println("java " + (new RunRevenueFetch()).getClass().toString() + " 2015-08-01 2015-08-11");
        }
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

                System.out.println("     File #" + (i + 1) + " insert " + aFetchDAO.execute(sqlCmdExe.toString()) + " records");
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
