package com.bpk.app.emrapp;

import com.bpk.core.emrcore.dao.DocScanDAOFactory;
import java.io.File;
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

    /**
     * สำหรับ Convert JRXML File --> Jasper File --> PDF File
     * @param jrxmlFilename
     * @param inputJrxmlPath
     * @param outputPdfPath
     * @return
     */
    public static String convertPage(String jrxmlFilename, String inputJrxmlPath, String outputPdfPath)
    {
        String pdfFilename = null;
        HashMap hm = new HashMap();
        Connection conn = null;
        String reportName = jrxmlFilename.replaceAll(".jrxml", "");
        String jasperFilename = reportName + ".jasper";

        try
        {
            System.out.println("Start convert ....");
            conn = DocScanDAOFactory.getConnection();

            JasperCompileManager.compileReportToFile(inputJrxmlPath + jrxmlFilename, inputJrxmlPath + jasperFilename);

            // Generate jasper print
            JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(inputJrxmlPath + jasperFilename, hm, conn);

            // Export pdf file
            pdfFilename = reportName + ".pdf";
            JasperExportManager.exportReportToPdfFile(jprint, outputPdfPath + pdfFilename);
            System.out.println("Done exporting reports to pdf");

            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // สำหรับเคลียร์ file ออกหลัง convert
        File jasperFile = new File(inputJrxmlPath + jasperFilename);
        jasperFile.delete();

        return pdfFilename;
    }

    /***
     * For Gen PDF 
     * @param vn
     * @param folderName
     * @param xmlFilenames
     * @return
    public static boolean convert(String vn, String folderName, String[] xmlFilenames, String outputPath)
    {
        boolean result = true;
        HashMap hm = null;
        Connection conn = null;
        
        try
        {
            System.out.println("Start convert ....");
            conn = DocScanDAOFactory.getConnection();

            for(int i=1; i<xmlFilenames.length; i++)
            {
                String jasperFilename = xmlFilenames[i].replaceAll(".jrxml", "")+".jasper";
                System.out.println("jrxml = "+xmlFilenames[xmlFilenames.length-i]);
                System.out.println("jasper = "+jasperFilename);
                JasperCompileManager.compileReportToFile(xmlFilenames[xmlFilenames.length-i], jasperFilename);
            }

            // Generate jasper print
            JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(xmlFilenames[0].replaceAll(".jrxml", "")+".jasper", hm, conn);

            // Export pdf file
            JasperExportManager.exportReportToPdfFile(jprint, outputPath+vn+"_"+folderName+".pdf");
            System.out.println("Done exporting reports to pdf");

            conn.close();
        }
        catch (Exception e)
        {
            System.out.print("Exceptiion" + e);
        }
        return result;
    }*/

    /*
    public static void main(String args[])
    {
        HashMap hm = null;
        // System.out.println("Usage: ReportGenerator ....");

        Connection conn = null;
        try
        {
            System.out.println("Start ....");
            // Get jasper report
            String path = "D:/BPKHIS/trunk/Sources/CodeJSP/jboss/server/default/deploy/bpkjasper.war/";
            String[] filenames = {"TestReport"};

            for(int i=0; i<filenames.length; i++)
                JasperCompileManager.compileReportToFile(path+filenames[i]+".jrxml", path+filenames[i]+".jasper");

            // Create arguments
            hm = new HashMap();
            hm.put("dBeginDate", "2015-10-01");
            hm.put("dEndDate", "2015-10-01");

            conn = DocScanDAOFactory.getConnection();
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
     * 
     */
}
