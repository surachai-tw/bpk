package com.bpk.app.emrapp;

import com.bpk.core.emrcore.dao.DocScanDAO;
import com.bpk.core.emrcore.dao.DocScanDAOFactory;
import com.bpk.persistence.emrdto.BpkDocumentScanVO;
import com.bpk.persistence.emrdto.PatientVO;
import com.bpk.utility.EventNames;
import com.bpk.utility.Utility;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import com.bytescout.barcodereader.BarcodeType;
import com.bytescout.barcodereader.FoundBarcode;
import com.bytescout.barcodereader.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.EnumSet;
import java.util.HashMap;

/**
 *
 * @author SURACHAI.TO
 */
public class ImageScan implements Runnable
{
    public static final String[] SCAN_FILENAME = {"SCAN-03.jpg"};

    private int status = 0;
    private String statusText = "";

    private String folderName = null;
    private DocScanDAO aDocScanDAO = DocScanDAOFactory.newDocScanDAO();

    public String getTextFromBarcodeWithThreshold(int precision, String scanFilename, String docScanInputPath)
    {
        String result = null;
        try
        {
            BufferedImage img = ImageIO.read(new File(docScanInputPath+scanFilename));
            ImageTool.toBlackAndWhite(img, precision);
            String newname = docScanInputPath+"THRESHOLD_"+scanFilename;
            ImageIO.write(img, "jpg", new File(newname));

            result = getTextFromBarcode(newname);
            Utility.printCoreDebug(this, "Precision = "+precision+", TEXT = "+result);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

    public void run()
    {
        if(SCAN_FILENAME!=null)
        {
            for(int i=0; i<SCAN_FILENAME.length; i++)
            {
                this.status = 0;
                PatientVO aPatientVO = null;
                try
                {
                    String resultText = null;

                    this.status = 10;
                    this.statusText = "Matching HN in database";
                    // Read text from barcode 
                    int j = 101;
                    do
                    {
                        // รอบแรก จะไม่ต้องทำ Threshold
                        if(j==101)
                        {
                            resultText = getTextFromBarcode(DocScanDAOFactory.getDocScanInputPath()+SCAN_FILENAME[i]);
                        }
                        else
                        {
                            resultText = getTextFromBarcodeWithThreshold(j, SCAN_FILENAME[i], DocScanDAOFactory.getDocScanInputPath());
                        }
                        this.status = (101-j+1)/2;
                        j--;
                    }
                    while(!aDocScanDAO.isPatientExistByHn(resultText) && j>=1);

                    if(resultText!=null)
                    {
                        HashMap result = aDocScanDAO.readPatient(resultText);
                        Object aObj = result.get(EventNames.RESULT_DATA);
                        if(aObj!=null && aObj instanceof PatientVO)
                        {
                            aPatientVO = (PatientVO)aObj;

                            BpkDocumentScanVO newBpkDocumentScanVO = new BpkDocumentScanVO();
                            newBpkDocumentScanVO.setPatientId(aPatientVO.getObjectID());
                            // ส่วนของ visit id ต้องเช็กกับ user อีกที ว่าหายังไง?
                            // 1. มาจาก input หน้าจอ
                            // 2. มาจาก barcode ของการ scan
                            // 3. มาจาก visit ที่หาได้ในปัจจุบัน
                            // ส่วนของ Folder จะให้มาจากการเลือกจากหน้าจอก่อน แต่ก็อาจจะมาจากการอ่าน barcode ก็ได้
                            newBpkDocumentScanVO.setFolderName(this.getFolderName());
                            this.status = 50;
                            this.statusText = "Created record in database";
                            newBpkDocumentScanVO = aDocScanDAO.createBpkDocumentScan(newBpkDocumentScanVO);

                            this.status = 75;
                            this.statusText = "Copying file to path";
                            if(Utility.isNotNull(newBpkDocumentScanVO.getObjectID()))
                            {
                                // Generate JRXML File
                                String jrxmlFilename = GenerateJrxml.generateJrxmlPage(SCAN_FILENAME[i], DocScanDAOFactory.getDocScanInputPath(), false, 0, DocScanDAOFactory.getDocScanInputPath());
                                // Convert PDF File
                                String pdfFilename = Jrxml2Pdf.convertPage(jrxmlFilename, DocScanDAOFactory.getDocScanInputPath(), DocScanDAOFactory.getDocScanInputPath());

                                // Delete JRXML after convert
                                GenerateJrxml.deleteJrxmlFile(DocScanDAOFactory.getDocScanInputPath()+jrxmlFilename);
                                
                                // ถ้าได้ข้อมูล ObjectID มาได้ ต้อง copy file ไปไว้ที่ปลายทางให้ตรงกับ folder
                                File srcFile = new File(DocScanDAOFactory.getDocScanInputPath() + System.getProperty("file.separator") + pdfFilename);
                                File destFile = new File(DocScanDAOFactory.getDocScanOutputPath()+DocScanDAOFactory.getHnImageFolder(aPatientVO.getOriginalHn()) + System.getProperty("file.separator") + newBpkDocumentScanVO.getFolderName()+System.getProperty("file.separator")+newBpkDocumentScanVO.getImageFileName());
                                Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                // ถ้าได้ข้อมูล ObjectID มาได้ ต้อง copy file ไปไว้ที่ปลายทางให้ตรงกับ folder
                                // File srcFile = new File(DocScanDAOFactory.getDocScanInputPath()+SCAN_FILENAME[i]);
                                // File destFile = new File(DocScanDAOFactory.getDocScanOutputPath()+DocScanDAOFactory.getHnImageFolder(aPatientVO.getOriginalHn()) + System.getProperty("file.separator") + newBpkDocumentScanVO.getFolderName()+System.getProperty("file.separator")+newBpkDocumentScanVO.getImageFileName());
                                // Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            }
                        }
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                finally
                {
                }
                this.status = 100;
                this.statusText = "Finished";
            }
        }
    }

    public int getStatus()
    {
        return status;
    }

    public static String getTextFromBarcode(String filename)
    {
        String result = null;
        try
        {
            Reader reader = new Reader();
            reader.setRegistrationName("demo");
            reader.setRegistrationKey("demo");
            // Set barcode types to find:
            reader.setBarcodeTypesToFind(EnumSet.of(BarcodeType.Code39, BarcodeType.Code39Ext, BarcodeType.Code128, BarcodeType.QRCode));

            // Demonstrate barcode decoding from image file:
            Utility.printCoreDebug(new ImageScan(), "getTextFromBarcode, filename = "+filename);
            FoundBarcode[] foundBarcodes = reader.readFromFile(filename);
            if (foundBarcodes.length > 0)
            {
                for (int i = 0; i < foundBarcodes.length; i++)
                {
                    result = foundBarcodes[i].getValue();

                    if(result!=null && result.indexOf("(")!=-1)
                    {
                        result = result.substring(0,result.indexOf("(")).trim();
                    }

                    /*
                    if(i==0)
                    {
                    String myData =foundBarcodes[i].getValue();
                    qty= myData.substring(0,myData.indexOf("("));
                    }
                    if(i==1)
                    {
                    String myData =foundBarcodes[i].getValue();
                    plateId= myData.substring(0,myData.indexOf("("));
                    }
                     */
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
        return result;
    }

    public String getStatusText()
    {
        return statusText;
    }

    /**
     * @return the folderName
     */
    public String getFolderName()
    {
        return Utility.getStringVO(folderName);
    }

    /**
     * @param folderName the folderName to set
     */
    public void setFolderName(String folderName)
    {
        this.folderName = folderName;
    }

}
