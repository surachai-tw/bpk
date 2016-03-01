package com.bpk.pgToSqlServer.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
public class SqlServerToPgDAO
{

    private static String configFile = "config.properties";
    private static String srcUrl = null;
    private static String srcUsername = null;
    private static String srcPassword = null;
    private static String destUrl = null;
    private static String destUsername = null;
    private static String destPassword = null;
    private static String pathsql = null;

    public String getDefaultPathSql()
    {
        return pathsql;
    }

    public SqlServerToPgDAO()
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

                if ("srcUrl".equalsIgnoreCase(key))
                {
                    srcUrl = value;
                }
                else if ("srcUsername".equalsIgnoreCase(key))
                {
                    srcUsername = value;
                }
                else if ("srcPassword".equalsIgnoreCase(key))
                {
                    srcPassword = value;
                }
                else if("destUrl".equalsIgnoreCase(key))
                {
                    destUrl = value;
                }
                else if ("destUsername".equalsIgnoreCase(key))
                {
                    destUsername = value;
                }
                else if ("destPassword".equalsIgnoreCase(key))
                {
                    destPassword = value;
                }
                else if ("pathsql".equalsIgnoreCase(key))
                {
                    pathsql = value;
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

    public Connection getSourceConnection()
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Connection connSrc = DriverManager.getConnection(srcUrl, srcUsername, srcPassword);

            return connSrc;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
        }
        return null;
    }

    public Connection getDestinationConnection()
    {
        try
        {
            Class.forName("org.postgresql.Driver");

            Connection connDest = DriverManager.getConnection(destUrl, destUsername, destPassword);

            return connDest;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
        }
        return null;
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
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("org.postgresql.Driver");

            connSrc = DriverManager.getConnection(srcUrl, srcUsername, srcPassword);
            connDest = DriverManager.getConnection(destUrl, destUsername, destPassword);

            stmtSrc = connSrc.createStatement();
            stmtDest = connDest.createStatement();

            // System.out.println(sql);
            rsSrc = stmtSrc.executeQuery(sql);

            StringBuilder sqlColumnBlank = new StringBuilder();
            StringBuilder sqlColumn = new StringBuilder();
            ResultSetMetaData metadata = rsSrc.getMetaData();
            int columns = metadata.getColumnCount();
            String[] columnNames = new String[columns];
            String[] columnTypes = new String[columns];
            for (int i = 0; i < columns; i++)
            {
                columnNames[i] = metadata.getColumnLabel(i + 1);
                if (i == 0)
                {
                    sqlColumnBlank.append("\"").append(columnNames[i]).append("\"");
                }
                sqlColumn.append("\"").append(columnNames[i]).append("\"");

                String columnType = metadata.getColumnTypeName(i + 1);
                if ("int4".equals(columnType))
                {
                    columnType = "INTEGER";
                }
                columnTypes[i] = columnType;

                if (i + 1 < columns)
                {
                    sqlColumn.append(", ");
                }
                else
                {
                }
            }

            StringBuilder sqlInsert = null;
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
            }

            rsSrc.close();
            stmtSrc.close();
            connSrc.close();

            stmtDest.close();
            connDest.close();

            return true;
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
