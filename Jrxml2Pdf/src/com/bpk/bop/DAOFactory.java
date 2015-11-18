package com.bpk.bop;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author SURACHAI.TO
 */
public class DAOFactory
{

    private static String configFile = new String("config.properties");
    private static String url = null;
    private static String username = null;
    private static String password = null;

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
                for (int i = 0; (line = in.readLine()) != null && i < 9; i++)
                {
                    String key = line.substring(0, line.indexOf("="));
                    String value = line.substring(line.indexOf("=") + 1).trim();

                    //System.out.println("Key = '" + key+"'");
                    //System.out.println("Value = '" + value+"'");

                    if ("url".equalsIgnoreCase(key))
                    {
                        url = value;
                    }
                    else if ("username".equalsIgnoreCase(key))
                    {
                        username = value;
                    }
                    else if ("password".equalsIgnoreCase(key))
                    {
                        password = value;
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

            // conn = DriverManager.getConnection("jdbc:postgresql://192.168.13.15:5432/imed_test", "postgres", "postgres");
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
        }
        return conn;
    }
}
