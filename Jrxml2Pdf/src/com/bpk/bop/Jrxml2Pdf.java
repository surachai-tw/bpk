package com.bpk.bop;

import java.util.HashMap;
import java.sql.Connection;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

/**
 *
 * @author SURACHAI.TO
 */
public class Jrxml2Pdf
{

    public static void main(String args[])
    {
        HashMap hm = null;
        // System.out.println("Usage: ReportGenerator ....");

        Connection conn = null;
        try
        {
            System.out.println("Start ....");
            // Get jasper report
            String path = "D:/BPK/trunk/BOP/ReportXML/";
            String[] filenames = {"DailyPatientRevenueBarChart", "DailyPatientRevenuePerCaseBarChart", "DailyPatientRevenuePieChart", "DailyPatientRevenue"};

            for(int i=0; i<filenames.length; i++)
                JasperCompileManager.compileReportToFile(path+filenames[i]+".jrxml", path+filenames[i]+".jasper");

            // Create arguments
            hm = new HashMap();
            hm.put("dBeginDate", "2015-10-01");
            hm.put("dEndDate", "2015-10-01");

            conn = DAOFactory.getConnection();
            // Generate jasper print
            JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(path+filenames[filenames.length-1]+".jasper", hm, conn);

            // Export pdf file
            JasperExportManager.exportReportToPdfFile(jprint, path+"output.pdf");

            System.out.println("Done exporting reports to pdf");

            conn.close();
        }
        catch (Exception e)
        {
            System.out.print("Exceptiion" + e);
        }
    }
}
