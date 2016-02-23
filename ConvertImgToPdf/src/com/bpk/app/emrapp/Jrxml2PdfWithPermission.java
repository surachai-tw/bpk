package com.bpk.app.emrapp;

import com.bpk.core.emrcore.dao.DocScanDAOFactory;
import com.lowagie.text.pdf.PdfWriter;
// import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.LocalJasperReportsContext;
import net.sf.jasperreports.export.PdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.type.PdfPrintScalingEnum;

/**
 *
 * @author SURACHAI.TO
 */
public class Jrxml2PdfWithPermission
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
            // JasperExportManager.exportReportToPdfFile(jprint, outputPdfPath + pdfFilename);

            /*
            LocalJasperReportsContext ctx = new LocalJasperReportsContext(DefaultJasperReportsContext.getInstance());
            JasperExportManager exporterPdf = JasperExportManager.getInstance(ctx);
            exporterPdf.exportReportToPdfFile(jprint, outputPdfPath + pdfFilename);
             *
             */

            JRPdfExporter exporterPdf = new JRPdfExporter();

            List listJprint = new ArrayList();
            listJprint.add(jprint);
            exporterPdf.setExporterInput(SimpleExporterInput.getInstance(listJprint));
            exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(outputPdfPath + pdfFilename));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            System.out.println("configuration.getUserPassword() = "+configuration.getUserPassword());
            // configuration.setPermissions(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING);
            configuration.setOwnerPassword("SURACHAI");
            // configuration.setUserPassword("AAA");
            configuration.setEncrypted(Boolean.TRUE);
            configuration.setMetadataAuthor("SURACHAI TORWONG");
            configuration.setMetadataTitle("BPK");
            configuration.setMetadataSubject("Document Scan");
            configuration.set128BitKey(Boolean.TRUE);
            configuration.setPrintScaling(PdfPrintScalingEnum.DEFAULT);
            // configuration.setPermissions(PdfWriter.ALLOW_PRINTING);
            System.out.println("configuration.getUserPassword() = "+configuration.getUserPassword());
            // configuration.setCreatingBatchModeBookmarks(true);
            exporterPdf.setConfiguration(configuration);

            exporterPdf.exportReport();

            /*
            JRPdfExporter exporterPdf = new JRPdfExporter();

            exporterPdf.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
            exporterPdf.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(new File(outputPdfPath + pdfFilename)));
            // exporterPdf.setParameter(JRPdfExporterParameter.IS_TAGGED, true);
            // exporterPdf.setParameter(JRPdfExporterParameter.TAG_LANGUAGE, "English");

            exporterPdf.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
            // exporterPdf.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, Boolean.TRUE);
            exporterPdf.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, Boolean.TRUE);
            // exporterPdf.setParameter(JRPdfExporterParameter.USER_PASSWORD, "YourUserPW");
            exporterPdf.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, "YourOwnerPW");
            // exporterPdf.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print({bUI: true, bSilent: false, bShrinkToFit: true});");
            System.out.println("PdfWriter.AllowDegradedPrinting = "+ PdfWriter.AllowDegradedPrinting);
            System.out.println("PdfWriter.AllowPrinting = "+PdfWriter.AllowPrinting);
            System.out.println("PdfWriter.ALLOW_PRINTING = "+PdfWriter.ALLOW_PRINTING);
            System.out.println("PdfWriter.AllowCopy = "+PdfWriter.AllowCopy);
            System.out.println("PdfWriter.AllowCopy | PdfWriter.AllowPrinting = "+(PdfWriter.AllowCopy | PdfWriter.AllowPrinting | PdfWriter.AllowDegradedPrinting));
            // exporterPdf.setParameter(JRPdfExporterParameter.PERMISSIONS, new Integer(PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_MODIFY_CONTENTS | PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_MODIFY_ANNOTATIONS | PdfWriter.ALLOW_FILL_IN | PdfWriter.ALLOW_SCREENREADERS | PdfWriter.ALLOW_ASSEMBLY | PdfWriter.ALLOW_DEGRADED_PRINTING));
            exporterPdf.setParameter(JRPdfExporterParameter.PERMISSIONS, new Integer(PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_MODIFY_CONTENTS | PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_MODIFY_ANNOTATIONS | PdfWriter.ALLOW_FILL_IN | PdfWriter.ALLOW_SCREENREADERS | PdfWriter.ALLOW_ASSEMBLY | PdfWriter.ALLOW_DEGRADED_PRINTING));

            exporterPdf.exportReport();
             *
             */

            System.out.println("Done exporting reports to pdf");

            conn.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        // สำหรับเคลียร์ file ออกหลัง convert
        File jasperFile = new File(inputJrxmlPath + jasperFilename);
        jasperFile.delete();

        return pdfFilename;
    }

    public static void main(String args[])
    {
        String jrxmlFilename = "TempFilename.jrxml";
        String pathInput = "D:\\BPKHIS\\trunk\\Sources\\CodeJSP\\jboss\\server\\default\\deploy\\bpkjasper.war\\";
        String pathOutput = "D:\\BPKHIS\\trunk\\Sources\\CodeJSP\\jboss\\server\\default\\deploy\\bpkjasper.war\\";

        convertPage(jrxmlFilename, pathInput, pathOutput);
    }
}
