package com.bpk.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
// import com.iMed.iMedCore.utility.serviceLocator.ServiceLocator;

public class DAOFactory {

	// private static String connectionFile = new String("/usr/jboss-3.2.7/server/default/deploy/doctorProfile.war/WEB-INF/classes/connection.properties");
	// private static String connectionFile = new String("D:/AngularJS/jboss-4.2.3.GA/server/default/deploy/doctorProfile.war/WEB-INF/classes/connection.properties");
	private static String connectionFile = new String("../server/default/deploy/doctorProfile.war/WEB-INF/classes/connection.properties");

    static Connection getConnection()
    {
    	Connection conn = null;
        BufferedReader in = null;
        String line = null;
        String url = null, username = null, password = null;

        try
    	{
    		// System.out.println("user.dir = "+System.getProperty("user.dir"));
            in = new BufferedReader(new FileReader(new File(connectionFile)));
            for (int i = 0; (line = in.readLine()) != null && i < 9; i++)
            {
                String key = line.substring(0, line.indexOf("="));
                String value = line.substring(line.indexOf("=") + 1).trim();

                //System.out.println("Key = '" + key+"'");
                //System.out.println("Value = '" + value+"'");
                
                if ("URL".equalsIgnoreCase(key))
                {
                    url = value;
                } else if ("USERNAME".equalsIgnoreCase(key))
                {
                	username = value;
                } else if ("PASSWORD".equalsIgnoreCase(key))
                {
                	password = value;
                }
            }
            in.close();
    	 		
    		Class.forName("org.postgresql.Driver");
    		return DriverManager.getConnection(url, username, password);
    	}    	
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	finally
    	{    		
    		in = null;
    		line = null;
    		url = null;
    		username = null;
    		password = null;
    	}
    	return conn;
    }
    
    public static DoctorProfileDAO newDoctorProfileDAO()
    {
    	return new DoctorProfileDAO();
    }
    
    /*
    static Connection getConnection()
    {
    	try
    	{
    		return ServiceLocator.getDataSource("java:comp/env/jdbc/iMedDB").getConnection();
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    		return null;
    	}
    }
    */

}
