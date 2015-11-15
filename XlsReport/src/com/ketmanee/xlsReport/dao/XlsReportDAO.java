package com.ketmanee.xlsReport.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.NumberFormat;

/**
 *
 * @author surachai.tw
 */
public class XlsReportDAO
{

    private static String configFile = new String("config.properties");
    private static String pgurl = null;
    private static String pgusername = null;
    private static String pgpassword = null;
    private static String excelurl = null;
    private static String pathsql = null;
    private static String pathoutput = null;

    public String getDefaultPathSql()
    {
        return pathsql;
    }

    public String getDefaultPathOutput()
    {
        return pathoutput;
    }

    public XlsReportDAO()
    {
        BufferedReader in = null;
        String line = null;
        try
        {
            in = new BufferedReader(new FileReader(new File(configFile)));
            for (int i = 0; (line = in.readLine()) != null && i < 9; i++)
            {
                String key = line.substring(0, line.indexOf("="));
                String value = line.substring(line.indexOf("=") + 1).trim();

                //System.out.println("Key = '" + key+"'");
                //System.out.println("Value = '" + value+"'");

                if ("pgurl".equalsIgnoreCase(key))
                {
                    pgurl = value;
                }
                else if ("pgusername".equalsIgnoreCase(key))
                {
                    pgusername = value;
                }
                else if ("pgpassword".equalsIgnoreCase(key))
                {
                    pgpassword = value;
                }
                else if ("excelurl".equalsIgnoreCase(key))
                {
                    excelurl = value;
                }
                else if ("pathsql".equalsIgnoreCase(key))
                {
                    pathsql = value;
                }
                else if ("pathoutput".equalsIgnoreCase(key))
                {
                    pathoutput = value;
                }
            }
            in.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            in = null;
        }
    }

    public void exportDataTest(String sql)
    {
        Connection connSrc = null, connDest = null;
        String odbcName = "WorkingReport";
        Statement stmtSrc = null, stmtDest = null;
        ResultSet rsSrc = null;
        try
        {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Class.forName("org.postgresql.Driver");

            connSrc = DriverManager.getConnection("jdbc:postgresql://localhost:5432/imed_bpk9", "surachai.tw", "0815373675");
            // connSrc = DriverManager.getConnection("jdbc:postgresql://localhost:5432/imed_kdh2", "postgres", "password");
            connDest = DriverManager.getConnection("jdbc:odbc:" + odbcName, "", "");

            stmtSrc = connSrc.createStatement();
            stmtDest = connDest.createStatement();

            rsSrc = stmtSrc.executeQuery("select item_code, common_name FROM item ORDER BY common_name");

            try
            {
                stmtDest.executeUpdate("DROP TABLE item");
            }
            catch (Exception ex)
            {
            }

            try
            {
                stmtDest.executeUpdate("CREATE TABLE item ( id INTEGER, item_code VARCHAR(255), item_name VARCHAR(255))");
            }
            catch (Exception ex)
            {
            }

            /*
            stmtDest.executeUpdate("INSERT INTO item VALUES('001', '001', 'PARACETAMOL')");
            stmtDest.executeUpdate("INSERT INTO item VALUES('002', '002', 'ภาษาไทย')");
            stmtDest.executeUpdate("INSERT INTO item VALUES('003', '003', 'BRUFEN')");
             */

            for (int i = 1; rsSrc.next(); i++)
            {
                String itemName = rsSrc.getString("common_name");
                itemName = itemName.replaceAll("'", "''");
                // String newItemName = new String(itemName.getBytes("TIS-620"), "ISO-8859-1");
                String newItemName = itemName;
                stmtDest.executeUpdate("INSERT INTO item VALUES('" + i + "', '" + rsSrc.getString("item_code") + "', '" + newItemName + "')");
            }

            rsSrc.close();
            stmtSrc.close();
            stmtDest.close();
            connDest.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            rsSrc = null;
            stmtSrc = null;
            stmtDest = null;
            connSrc = null;
            connDest = null;
        }
    }

    private static void copyFileUsingStream(File source, File dest) throws IOException
    {
        InputStream is = null;
        OutputStream os = null;
        try
        {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0)
            {
                os.write(buffer, 0, length);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            is.close();
            os.close();
        }
    }

    public boolean exportData(String sheetName, String sql)
    {
        Connection connSrc = null, connDest = null;
        Statement stmtSrc = null, stmtDest = null;
        ResultSet rsSrc = null;
        NumberFormat nf8 = NumberFormat.getInstance();
        nf8.setMaximumFractionDigits(8);
        nf8.setMinimumFractionDigits(8);
        nf8.setGroupingUsed(false);

        try
        {
            File delFile = new File("working" + System.getProperty("file.separator") + "WorkingReport.xls");
            delFile.delete();

            // Copy original file lib to working pathlib
            // System.out.println(System.getProperty("user.dir"));
            File srcFile = new File("lib" + System.getProperty("file.separator") + "WorkingReport.xls");
            File destFile= new File("working" + System.getProperty("file.separator") + "WorkingReport.xls");
            File destNewFile = new File("working" + System.getProperty("file.separator") + sheetName + ".xls");
            copyFileUsingStream(srcFile, destFile);

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Class.forName("org.postgresql.Driver");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            connSrc = DriverManager.getConnection(pgurl, pgusername, pgpassword);
            // connSrc = DriverManager.getConnection("jdbc:postgresql://localhost:5432/imed_kdh2", "postgres", "password");
            connDest = DriverManager.getConnection(excelurl);

            stmtSrc = connSrc.createStatement();
            stmtDest = connDest.createStatement();

            System.out.println(sql);
            rsSrc = stmtSrc.executeQuery(sql);

            StringBuilder sqlTable = new StringBuilder("CREATE TABLE \"").append(sheetName).append("\" ( ");
            StringBuilder sqlColumnBlank = new StringBuilder();
            StringBuilder sqlColumn = new StringBuilder();
            ResultSetMetaData metadata = rsSrc.getMetaData();
            int columns = metadata.getColumnCount();
            String[] columnNames = new String[columns];
            String[] columnTypes = new String[columns];
            Object[] summary = new Object[columns];
            for (int i = 0; i < columns; i++)
            {
                columnNames[i] = metadata.getColumnLabel(i + 1);
                if (i == 0)
                {
                    sqlColumnBlank.append("\"").append(columnNames[i]).append("\"");
                }
                sqlColumn.append("\"").append(columnNames[i]).append("\"");
                sqlTable.append("\"").append(columnNames[i]).append("\"");

                String columnType = metadata.getColumnTypeName(i + 1);
                if ("int4".equals(columnType))
                {
                    columnType = "INTEGER";
                }
                sqlTable.append(" ").append(columnType);
                columnTypes[i] = columnType;

                // ส่วนของ Summary
                if (columnType.toLowerCase().indexOf("float") != -1 || columnType.toLowerCase().indexOf("int") != -1 || columnType.toLowerCase().indexOf("numeric") != -1)
                {
                    summary[i] = new Float(0);
                }
                else
                {
                    summary[i] = new String();
                }

                if (i + 1 < columns)
                {
                    sqlColumn.append(", ");
                    sqlTable.append(", ");
                }
                else
                {
                    sqlTable.append(")");
                }
            }

            try
            {
                stmtDest.executeUpdate("DROP TABLE \"" + sheetName + "\"");
            }
            catch (Exception ex)
            {
            }

            try
            {
                // System.out.println(sqlTable.toString());
                stmtDest.executeUpdate(sqlTable.toString());
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                rsSrc.close();
                stmtSrc.close();
                connSrc.close();
                stmtDest.close();
                connDest.close();

                return false;
            }

            StringBuilder sqlInsert = null;
            StringBuilder sqlBlank = new StringBuilder("INSERT INTO \"").append(sheetName).append("\"(").append(sqlColumnBlank).append(") VALUES(");
            StringBuilder sqlSum = new StringBuilder("INSERT INTO \"").append(sheetName).append("\"(").append(sqlColumn).append(") VALUES(");
            for (int i = 1; rsSrc.next(); i++)
            {
                sqlInsert = new StringBuilder("INSERT INTO \"").append(sheetName).append("\"(").append(sqlColumn).append(") VALUES(");
                for (int j = 0; j < columns; j++)
                {
                    String data = rsSrc.getString(columnNames[j]);

                    data = data != null ? data.replaceAll("'", "''") : "";

                    if (columnTypes[j].toLowerCase().indexOf("float") != -1 || columnTypes[j].toLowerCase().indexOf("numeric") != -1 || columnTypes[j].toLowerCase().indexOf("double") != -1)
                    {
                        try
                        {
                            // Parse ดูก่อน ถ้าทำได้แสดงว่าเป็น Numeric
                            Float num = Float.parseFloat(data);
                            sqlInsert.append("'").append(nf8.format(num)).append("'");
                        }
                        catch (Exception ex)
                        {
                            sqlInsert.append("'").append(data).append("'");
                        }
                    }
                    else
                    {
                        sqlInsert.append("'").append(data).append("'");
                    }

                    try
                    {
                        summary[j] = new Float(Float.parseFloat(nf8.format(Float.parseFloat(summary[j].toString()))) + Float.parseFloat(nf8.format(Float.parseFloat(data))));
                    }
                    catch (Exception ex)
                    {
                    }

                    if (j + 1 < columns)
                    {
                        sqlInsert.append(", ");
                    }
                }
                sqlInsert.append(")");

                try
                {
                    // System.out.println(sqlInsert.toString());
                    stmtDest.executeUpdate(sqlInsert.toString());
                }
                catch (Exception ex)
                {
                    System.out.println(sqlInsert.toString());
                }

                try
                {
                    // DROP ได้ไม่ Error แต่จะไม่หายไปจาก Workbook
                    // https://support.microsoft.com/en-us/kb/178717
                    // stmtDest.executeUpdate("DROP TABLE \"Sheet1$\"");
                    // Unsupported SQL:
                    //     The driver will not support DELETE, UPDATE, or ALTER TABLE statements. While it is possible to
                    // update values, DELETE statements will not remove a row from a table based on an Excel spreadsheet.
                    // These operations are not supported. Basically, you can only append (insert) to a table.
                }
                catch (Exception ex)
                {
                    // ex.printStackTrace();
                }
            }

            // ส่วนของ SUMMARY
            for (int i = 0; i < summary.length; i++)
            {
                try
                {
                    Float.parseFloat(summary[i].toString());

                    if (i == 0)
                    {
                        sqlBlank.append("''");
                    }

                    sqlSum.append("'").append(summary[i].toString()).append("'");

                    if (i + 1 < summary.length)
                    {
                        // sqlBlank.append(", ");
                        sqlSum.append(", ");
                    }
                }
                catch (Exception ex)
                {
                    if (i == 0)
                    {
                        sqlBlank.append("''");
                    }

                    sqlSum.append("''");

                    if (i + 1 < summary.length)
                    {
                        // sqlBlank.append(", ");
                        sqlSum.append(", ");
                    }
                }
            }
            sqlBlank.append(")");
            sqlSum.append(")");

            try
            {
                stmtDest.executeUpdate(sqlBlank.toString());
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                System.out.println(sqlBlank.toString());
            }

            // System.out.println("SUMMARY "+sqlSum.toString());
            try
            {
                stmtDest.executeUpdate(sqlSum.toString());
            }
            catch (Exception ex)
            {
                System.out.println(sqlSum.toString());
            }

            rsSrc.close();
            stmtSrc.close();
            connSrc.close();

            stmtDest.close();
            connDest.close();

            // เปลี่ยนชื่อ file ให้เป็นตามแต่ละคำสั่ง
            boolean resultXls = false;
            resultXls = destFile.renameTo(destNewFile);
            return resultXls;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            rsSrc = null;
            stmtSrc = null;
            stmtDest = null;
            connSrc = null;
            connDest = null;
        }

        return false;
    }
}
