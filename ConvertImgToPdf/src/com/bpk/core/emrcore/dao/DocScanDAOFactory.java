package com.bpk.core.emrcore.dao;

import com.bpk.core.emrcore.dao.DocScanDAO;
import com.bpk.utility.dao.LoginDAO;
import com.bpk.utility.fix.FixOrdersheetType;
import groovy.json.StringEscapeUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author SURACHAI.TO
 */
public class DocScanDAOFactory
{

    private static String configFile = new String("config.properties");
    private static String url = null;
    private static String username = null;
    private static String password = null;
    private static String docScanOutputPath = null;
    private static String docScanInputPath = null;
    private static String docScanInputUrl = null;
    private static String docScanInputPathSuccess = null;
    private static String docScanInputPathFail = null;
    private static String docScanUrl = null;

    public static Connection getConnection()
    {
        Connection conn = null;
        try
        {
            Class.forName("org.postgresql.Driver");

            BufferedReader in = null;
            String line = null;
            try
            {
                in = new BufferedReader(new FileReader(new File(configFile)));
                for (int i = 0; (line = in.readLine()) != null; i++)
                {
                    String key = line.substring(0, line.indexOf("="));
                    String value = line.substring(line.indexOf("=") + 1).trim();

                    //System.out.println("Key = '" + key+"'");
                    //System.out.println("Value = '" + value+"'");

                    if ("url".equalsIgnoreCase(key))
                    {
                        url = value;
                    } else if ("username".equalsIgnoreCase(key))
                    {
                        username = value;
                    } else if ("password".equalsIgnoreCase(key))
                    {
                        password = value;
                    } else if ("doc_scan_output_path".equalsIgnoreCase(key))
                    {
                        docScanOutputPath = value;
                    } else if ("doc_scan_input_url".equalsIgnoreCase(key))
                    {
                        docScanInputUrl = value;
                    } else if ("doc_scan_input_path".equalsIgnoreCase(key))
                    {
                        docScanInputPath = value;
                    } else if ("doc_scan_url".equalsIgnoreCase(key))
                    {
                        docScanUrl = value;
                    } else if (("bpk.docscan.ordersheet."+FixOrdersheetType.STAT).equalsIgnoreCase(key))
                    {
                        FixOrdersheetType.setLanguage(key, StringEscapeUtils.unescapeJava(value));
                    } else if (("bpk.docscan.ordersheet."+FixOrdersheetType.HOME).equalsIgnoreCase(key))
                    {
                        FixOrdersheetType.setLanguage(key, StringEscapeUtils.unescapeJava(value));
                    } else if (("bpk.docscan.ordersheet."+FixOrdersheetType.CONTINUE).equalsIgnoreCase(key))
                    {
                        FixOrdersheetType.setLanguage(key, StringEscapeUtils.unescapeJava(value));
                    } else if (("bpk.docscan.ordersheet."+FixOrdersheetType.NORMAL).equalsIgnoreCase(key))
                    {
                        FixOrdersheetType.setLanguage(key, StringEscapeUtils.unescapeJava(value));
                    } else if (("bpk.docscan.ordersheet."+FixOrdersheetType.PLAN_DISCHARGE).equalsIgnoreCase(key))
                    {
                        FixOrdersheetType.setLanguage(key, StringEscapeUtils.unescapeJava(value));
                    }
                }
                in.close();
            } catch (Exception ex)
            {
                ex.printStackTrace();
            } finally
            {
                in = null;
            }

            // conn = DriverManager.getConnection("jdbc:postgresql://192.168.13.15:5432/imed_test", "postgres", "postgres");
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
        }
        return conn;
    }

    public static DocScanDAO newDocScanDAO()
    {
        try
        {
            Connection conn = getConnection();
            conn.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return new DocScanDAO();
    }

    /**
     * @return the docScanOutputPath
     */
    public static String getDocScanOutputPath()
    {
        // return docScanOutputPath+System.getProperty("file.separator");
        return docScanOutputPath != null && !docScanOutputPath.endsWith("\\") ? docScanOutputPath + System.getProperty("file.separator") : docScanOutputPath;
    }

    /**
     * @return the docScanOutputPath
     */
    public static String getDocScanInputPath()
    {
        return docScanInputPath != null && !docScanInputPath.endsWith("\\") ? docScanInputPath + System.getProperty("file.separator") : docScanInputPath;
    }

    public static String getDocScanInputUrl()
    {
        return docScanInputUrl;
    }

    /**
     * @return the docScanOutputPath
     */
    public static boolean setDocScanInputPath(String path)
    {
        File chkPath = new File(path);
        if (chkPath.exists() && chkPath.isDirectory())
        {
            docScanInputPath = path;

            updateFile();
            return true;
        }
        return false;
    }

    public static void updateFile()
    {
        try
        {
            // System.out.println("connectionFile = " + connectionFile);
            StringBuilder newConnFile = new StringBuilder("url");
            newConnFile.append("=").append(url);
            newConnFile.append(System.getProperty("line.separator"));
            newConnFile.append("username");
            newConnFile.append("=").append(username);
            newConnFile.append(System.getProperty("line.separator"));
            newConnFile.append("password");
            newConnFile.append("=").append(password);
            newConnFile.append(System.getProperty("line.separator"));
            newConnFile.append("doc_scan_input_path");
            newConnFile.append("=").append(getDocScanInputPath());
            newConnFile.append(System.getProperty("line.separator"));
            newConnFile.append("doc_scan_input_url");
            newConnFile.append("=").append(getDocScanInputUrl());
            newConnFile.append(System.getProperty("line.separator"));
            newConnFile.append("doc_scan_output_path");
            newConnFile.append("=").append(getDocScanOutputPath());
            newConnFile.append(System.getProperty("line.separator"));
            newConnFile.append("doc_scan_url");
            newConnFile.append("=").append(getDocScanUrl());
            newConnFile.append(System.getProperty("line.separator"));
            newConnFile.append("bpk.docscan.ordersheet."+FixOrdersheetType.STAT);
            newConnFile.append("=").append(StringEscapeUtils.escapeJava(FixOrdersheetType.getLanguage("bpk.docscan.ordersheet."+FixOrdersheetType.STAT)));
            newConnFile.append(System.getProperty("line.separator"));
            newConnFile.append("bpk.docscan.ordersheet."+FixOrdersheetType.HOME);
            newConnFile.append("=").append(StringEscapeUtils.escapeJava(FixOrdersheetType.getLanguage("bpk.docscan.ordersheet."+FixOrdersheetType.HOME)));
            newConnFile.append(System.getProperty("line.separator"));
            newConnFile.append("bpk.docscan.ordersheet."+FixOrdersheetType.CONTINUE);
            newConnFile.append("=").append(StringEscapeUtils.escapeJava(FixOrdersheetType.getLanguage("bpk.docscan.ordersheet."+FixOrdersheetType.CONTINUE)));
            newConnFile.append(System.getProperty("line.separator"));
            newConnFile.append("bpk.docscan.ordersheet."+FixOrdersheetType.NORMAL);
            newConnFile.append("=").append(StringEscapeUtils.escapeJava(FixOrdersheetType.getLanguage("bpk.docscan.ordersheet."+FixOrdersheetType.NORMAL)));
            newConnFile.append(System.getProperty("line.separator"));
            newConnFile.append("bpk.docscan.ordersheet."+FixOrdersheetType.PLAN_DISCHARGE);
            newConnFile.append("=").append(StringEscapeUtils.escapeJava(FixOrdersheetType.getLanguage("bpk.docscan.ordersheet."+FixOrdersheetType.PLAN_DISCHARGE)));
            /*
            newConnFile.append("doc_scan_input_path_success");
            newConnFile.append("=").append(getDocScanInputPathSuccess());
            newConnFile.append(System.getProperty("line.separator"));
            newConnFile.append("doc_scan_input_path_fail");
            newConnFile.append("=").append(getDocScanInputPathFail());
            newConnFile.append(System.getProperty("line.separator"));
             */
            // ��¹ File config DB ����
            FileOperation.writeFile(configFile, newConnFile.toString());
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * @return the docScanUrl
     */
    public static String getDocScanUrl()
    {
        return docScanUrl;
    }

    /**
     * @return the docScanInputPathSuccess
     */
    public static String getDocScanInputPathSuccess()
    {
        File folder = new File(getDocScanInputPath() + System.getProperty("file.separator") + "success");
        if (!folder.exists())
        {
            folder.mkdir();
        }
        return folder.toString() + System.getProperty("file.separator");
    }

    /**
     * @return the docScanInputPathFail
     */
    public static String getDocScanInputPathFail()
    {
        File folder = new File(getDocScanInputPath() + System.getProperty("file.separator") + "fail");
        if (!folder.exists())
        {
            folder.mkdir();
        }
        return folder.toString() + System.getProperty("file.separator");
    }
}
