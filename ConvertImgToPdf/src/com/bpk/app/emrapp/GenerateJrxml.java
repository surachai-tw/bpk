package com.bpk.app.emrapp;

import com.bpk.utility.MyString;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SURACHAI.TO
 */
public class GenerateJrxml
{
    // public final static String TEMP_FILENAME = "TempFilename";
    public final static String HEADER1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"";
    public final static String HEADER2 = "\" pageWidth=\"595\" pageHeight=\"842\" whenNoDataType=\"AllSectionsNoDetail\" columnWidth=\"595\" leftMargin=\"0\" rightMargin=\"0\" topMargin=\"0\" bottomMargin=\"0\" isSummaryNewPage=\"true\"><property name=\"ireport.zoom\" value=\"1.0\"/><property name=\"ireport.x\" value=\"0\"/><property name=\"ireport.y\" value=\"0\"/><parameter name=\"SUBREPORT_DIR\" class=\"java.lang.String\" isForPrompting=\"false\"><defaultValueExpression><![CDATA[\"";
    // public final static String PARAM_SUB_REPORT_DIR = "D:\\\\BPKHIS\\\\trunk\\\\Sources\\\\CodeJSP\\\\jboss\\\\server\\\\default\\\\deploy\\\\bpkjasper.war\\\\";
    public final static String REPORT_ELEMENT1 = "\"]]></defaultValueExpression></parameter><background><band height=\"842\" splitType=\"Stretch\"><image hAlign=\"Center\" vAlign=\"Middle\"><reportElement x=\"0\" y=\"0\" width=\"595\" height=\"842\"/><imageExpression class=\"java.lang.String\"><![CDATA[\"";
    // public final static String IMAGE_FULLPATH = "D:\\\\BPKHIS\\\\trunk\\\\Sources\\\\CodeJSP\\\\jboss\\\\server\\\\default\\\\deploy\\\\bpkimage.war\\\\imed_doc_new\\\\46\\\\0000003XX\\\\946000000391\\\\";
    public final static String REPORT_ELEMENT2 = "\"]]></imageExpression></image></band></background><title><band splitType=\"Stretch\"/></title><pageHeader><band splitType=\"Stretch\"/></pageHeader><columnHeader><band splitType=\"Stretch\"/></columnHeader><detail><band splitType=\"Stretch\"/></detail><columnFooter><band splitType=\"Stretch\"/></columnFooter><pageFooter><band splitType=\"Stretch\"/></pageFooter><summary>";
    public final static String HAS_NEXT_PAGE1 = "<band height=\"842\" splitType=\"Stretch\"><subreport><reportElement x=\"0\" y=\"0\" width=\"595\" height=\"842\"/><connectionExpression><![CDATA[$P{REPORT_CONNECTION}]]></connectionExpression><subreportExpression class=\"java.lang.String\"><![CDATA[$P{SUBREPORT_DIR} + \"";
    public final static String HAS_NEXT_PAGE2 = "\"]]></subreportExpression></subreport></band>";
    public final static String NO_NEXT_PAGE = "<band splitType=\"Stretch\"/>";
    public final static String FOOTER = "</summary></jasperReport>";

    /***
     * สำหรับสร้าง JRXML File
     * @param reportName
     * @param inputImageAbsFilename
     * @param isHasNextPage
     * @param indexPage
     * @param outputJrxmlPath
     * @return
     */
    public static String generateJrxmlPage(String reportName, String inputImageAbsFilename, boolean isHasNextPage, int indexPage, String outputJrxmlPath)
    {
        String imgReportName = reportName;
        reportName = reportName.toUpperCase();
        reportName = reportName.replaceAll(".JPG", "");
        reportName = reportName.replaceAll(".JPEG", "");
        reportName = reportName.replaceAll(".GIF", "");
        reportName = reportName.replaceAll(".PNG", "");
        reportName = reportName.replaceAll(".BMP", "");

        StringBuilder jrxmlBuilder = new StringBuilder(HEADER1);
        jrxmlBuilder.append(reportName);
        jrxmlBuilder.append(HEADER2);
        jrxmlBuilder.append(MyString.addSlash(outputJrxmlPath));
        jrxmlBuilder.append(REPORT_ELEMENT1);
        jrxmlBuilder.append(MyString.addSlash(inputImageAbsFilename)).append(imgReportName);
        jrxmlBuilder.append(REPORT_ELEMENT2);
        if (isHasNextPage)
        {
            jrxmlBuilder.append(HAS_NEXT_PAGE1);
            jrxmlBuilder.append((reportName + "_" + (indexPage + 1) + ".jasper"));
            jrxmlBuilder.append(HAS_NEXT_PAGE2);
        }
        else
        {
            jrxmlBuilder.append(NO_NEXT_PAGE);
        }
        jrxmlBuilder.append(FOOTER);

        String filename = reportName + (isHasNextPage ? ("_" + indexPage) : "") + ".jrxml";
        String jrxmlAbsFilename = outputJrxmlPath + filename;
        writeFile(jrxmlAbsFilename, jrxmlBuilder.toString());

        return filename;
    }

    /***
     * Delete file ที่เคย Gen ออกไป
     * @param jrxmlAbsFilename
     */
    public static void deleteJrxmlFile(String jrxmlAbsFilename)
    {
        try
        {
            File file = new File(jrxmlAbsFilename);
            file.delete();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     *
     * @param reportName
     * @param imageAbsFilename
     * @param isHasNextPage
     * @param indexPage
     * @return     
    public static String generateJrxmlPage(String reportName, String imageAbsFilename, boolean isHasNextPage, int indexPage)
    {
        StringBuilder jrxmlBuilder = new StringBuilder(HEADER1);
        jrxmlBuilder.append(reportName);
        jrxmlBuilder.append(HEADER2);
        jrxmlBuilder.append(PARAM_SUB_REPORT_DIR);
        jrxmlBuilder.append(REPORT_ELEMENT1);
        jrxmlBuilder.append(imageAbsFilename);
        jrxmlBuilder.append(REPORT_ELEMENT2);
        if(isHasNextPage)
        {
            jrxmlBuilder.append(HAS_NEXT_PAGE1);
            jrxmlBuilder.append((reportName + "_" + (indexPage+1) + ".jasper"));
            jrxmlBuilder.append(HAS_NEXT_PAGE2);
        }
        else
        {
            jrxmlBuilder.append(NO_NEXT_PAGE);
        }
        jrxmlBuilder.append(FOOTER);

        String filename = PARAM_SUB_REPORT_DIR + reportName + "_" + indexPage + ".jrxml";
        writeFile(filename, jrxmlBuilder.toString());
        
        return filename;
    }
     **/

    /** Write text data to file */
    public static boolean writeFile(String filename, String data)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(filename);
            Writer out = new OutputStreamWriter(fos);
            out.write(data);
            out.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /*
    public static void main(String args[])
    {
        String vn = "946000000391";
        String[] folder =
        {
            "OPD"
        };//{"BBU", "DOS", "HIP", "HPT", "IPS", "LAB", "ODR", "OPD"};

        List xmlFilenames = new ArrayList();

        for (int i = 0; i < folder.length; i++)
        {
            File path2Read = new File(IMAGE_FULLPATH + folder[i]);
            File[] allFiles = path2Read.listFiles();

            if (allFiles != null && allFiles.length > 0)
            {
                for(int j=0; j<allFiles.length; j++)
                {
                    String imgAbsFilename = allFiles[j].getAbsolutePath();
                    char[] arrAbsFilename = imgAbsFilename.toCharArray();
                    StringBuilder newAbsFilename = new StringBuilder();
                    for(int k=0; k<arrAbsFilename.length; k++)
                    {
                        newAbsFilename.append(arrAbsFilename[k]);
                        if('\\'==arrAbsFilename[k])
                            newAbsFilename.append(arrAbsFilename[k]);
                    }
                    String filename = generateJrxmlPage(TEMP_FILENAME, newAbsFilename.toString(), j+1<allFiles.length, j);
                    xmlFilenames.add(filename);

                    String[] arr = (String[])xmlFilenames.toArray(new String[xmlFilenames.size()]);
                    for(int k=1; k<arr.length; k++)
                    {
                        System.out.println("arr["+(arr.length-k)+"] = "+arr[arr.length-k]);
                    }
                    // Jrxml2Pdf.convert(vn, folder[i], arr, PARAM_SUB_REPORT_DIR);

                }
            }
        }
    }
     */
}
