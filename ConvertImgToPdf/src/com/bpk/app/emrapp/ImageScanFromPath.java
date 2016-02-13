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
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.EnumSet;
import java.util.HashMap;

/**
 *
 * @author SURACHAI.TO
 */
public class ImageScanFromPath implements Runnable
{

    /** �� List file ��辺� Path */
    public String[] scanFilenames = null;
    /** ���ʴ� Status �����ҧ��÷ӧҹ� Thread */
    private int status = 0;
    /** ���ʴ� Status �����ҧ��÷ӧҹ� Thread */
    private String statusText = "";
    /** ���纨ӹǹ�Ҿ��� Scan ����� */
    private int numScan = 0;
    /** ���纨ӹǹ�Ҿ��� Scan �����  */
    private int numSuccess = 0;
    /** ���纨ӹǹ�Ҿ��� Scan ��й�����к������  */
    private int numFail = 0;
    /** ������Ѻ��Ǩ�ͺ�óշ�衴 Cancel �����ҧ��� Scan */
    private boolean isInterrupt = false;
    /** ������Ѻ List file ����Ҩҡ Scanner ��ҹ�� */
    private boolean isForScannerOnly = false;
    /** ������Ѻ��˹���� Scan Ẻ�к� HN/VN */
    private BpkDocumentScanVO bpkDocumentScanVO = null;
    /** ������Ѻ�ʴ��š�÷ӧҹ��ҹ�� */
    private BpkDocumentScanVO lastBpkDocumentScanVO = null;
    /** ������Ѻ�ʴ��š�÷ӧҹ��ҹ�� */
    private String lastImage = null;
    private String folderName = null;
    private DocScanDAO aDocScanDAO = DocScanDAOFactory.newDocScanDAO();

    public String getTextFromBarcodeWithThreshold(int precision, String scanFilename, String docScanInputPath)
    {
        String result = null;
        try
        {
            BufferedImage img = ImageIO.read(new File(docScanInputPath + scanFilename));
            ImageTool.toBlackAndWhite(img, precision);
            String newname = docScanInputPath + "THRESHOLD_" + scanFilename;
            ImageIO.write(img, "jpg", new File(newname));

            result = getTextFromBarcode(newname);
            Utility.printCoreDebug(this, "Precision = " + precision + ", TEXT = " + result);

            // Delete threshold file 
            File file = new File(newname);
            file.delete();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

    public void setIsForScannerOnly(boolean isForScannerOnly)
    {
        this.isForScannerOnly = isForScannerOnly;
    }

    public void run()
    {
        // List file in folder 
        File scanPath = new File(DocScanDAOFactory.getDocScanInputPath());
        scanFilenames = scanPath.list(new FilenameFilter()
        {

            public boolean accept(File dir, String name)
            {
                return name.toLowerCase().endsWith(".jpg")
                        && !name.toUpperCase().startsWith("THRESHOLD_")
                        && !name.toUpperCase().startsWith("THUMBNAIL_")
                        && (isForScannerOnly ? name.toUpperCase().startsWith("_BPK") : true);
            }
        });

        int tmpStatusBeforeMatch = 10;
        int tmpStatusAfterMatch = 80;
        if (scanFilenames != null && scanFilenames.length > 0)
        {
            for (int i = 0; i < scanFilenames.length && !this.isInterrupt; i++)
            {
                this.status = 0;
                this.lastImage = DocScanDAOFactory.getDocScanInputPath() + scanFilenames[i];
                PatientVO aPatientVO = null;
                try
                {
                    String resultText = null;
                    String[] allReadText = null;

                    this.status = tmpStatusBeforeMatch;
                    this.statusText = "Matching HN in database";
                    // Read text from barcode 
                    int j = 101;
                    CHECK_HN_VALID:
                    do
                    {
                        // �ͺ�á ������ͧ�� Threshold
                        if (j == 101)
                        {
                            resultText = getTextFromBarcode(getLastImage());
                        }
                        else
                        {
                            resultText = getTextFromBarcodeWithThreshold(j, scanFilenames[i], DocScanDAOFactory.getDocScanInputPath());
                        }

                        j -= 4;
                        int tmpStatus = (int) (((float) (101 - j) / 101) * (tmpStatusAfterMatch - tmpStatusBeforeMatch));
                        this.status = tmpStatusBeforeMatch + tmpStatus;

                        if (resultText != null)
                        {
                            allReadText = resultText.split(" ");
                            if (allReadText != null && allReadText.length > 1)
                            {
                                if (aDocScanDAO.isPatientExistByHn(allReadText[0]))
                                {
                                    break CHECK_HN_VALID;
                                }
                            }
                        }
                    }
                    while (j >= 1 && !this.isInterrupt);

                    if (!this.isInterrupt)
                    {
                        for (int chki = 0; allReadText != null && chki < allReadText.length; chki++)
                        {
                            System.out.println("allReadText[" + chki + "] = " + allReadText[chki]);
                        }

                        this.status = tmpStatusAfterMatch;
                        this.statusText = "Create record in database";
                        if (resultText != null || this.bpkDocumentScanVO != null)
                        {
                            HashMap result = aDocScanDAO.readPatient(this.bpkDocumentScanVO == null ? allReadText[0] : this.bpkDocumentScanVO.getHn());
                            Object aObj = result.get(EventNames.RESULT_DATA);
                            if (aObj != null && aObj instanceof PatientVO)
                            {
                                aPatientVO = (PatientVO) aObj;

                                BpkDocumentScanVO newBpkDocumentScanVO = null;
                                if (this.bpkDocumentScanVO == null)
                                {
                                    // GetBarcodeServlet?code=958000003000+5802060286+D15317+20150206163207+LAB+LAB-RESULT&type=QR_CODE
                                    newBpkDocumentScanVO = new BpkDocumentScanVO();
                                    newBpkDocumentScanVO.setPatientId(aPatientVO.getObjectID());
                                    newBpkDocumentScanVO.setHn(aPatientVO.getHn());
                                    newBpkDocumentScanVO.setPatientName(aPatientVO.getPatientName());
                                    newBpkDocumentScanVO.setVn(allReadText != null && allReadText.length >= 2 ? allReadText[1] : "");
                                    newBpkDocumentScanVO.setDoctorEid(allReadText != null && allReadText.length >= 3 ? allReadText[2] : "");
                                    newBpkDocumentScanVO.setPrintDate(BpkDocumentScanVO.getDateFromReadText(allReadText != null && allReadText.length >= 4 ? allReadText[3] : ""));
                                    newBpkDocumentScanVO.setPrintTime(BpkDocumentScanVO.getTimeFromReadText(allReadText != null && allReadText.length >= 4 ? allReadText[3] : ""));
                                    newBpkDocumentScanVO.setFolderName(allReadText != null && allReadText.length >= 5 ? allReadText[4] : "OTR");
                                    newBpkDocumentScanVO.setDocumentName(allReadText != null && allReadText.length >= 6 ? allReadText[5] : "");
                                    newBpkDocumentScanVO.setOption(allReadText != null && allReadText.length >= 7 ? allReadText[6] : "");
                                }
                                else
                                {
                                    newBpkDocumentScanVO = new BpkDocumentScanVO();
                                    newBpkDocumentScanVO.setPatientId(aPatientVO.getObjectID());
                                    newBpkDocumentScanVO.setHn(aPatientVO.getHn());
                                    newBpkDocumentScanVO.setPatientName(aPatientVO.getPatientName());
                                    newBpkDocumentScanVO.setVn(bpkDocumentScanVO.getVn());
                                    newBpkDocumentScanVO.setDoctorEid(bpkDocumentScanVO.getDoctorEid());
                                    newBpkDocumentScanVO.setPrintDate(bpkDocumentScanVO.getPrintDate());
                                    newBpkDocumentScanVO.setPrintTime(bpkDocumentScanVO.getPrintTime());
                                    newBpkDocumentScanVO.setFolderName(Utility.isNotNull(bpkDocumentScanVO.getFolderName()) ? bpkDocumentScanVO.getFolderName() : "OTR");
                                    newBpkDocumentScanVO.setDocumentName(bpkDocumentScanVO.getDocumentName());
                                    newBpkDocumentScanVO.setOption(bpkDocumentScanVO.getOption());
                                }

                                newBpkDocumentScanVO = aDocScanDAO.createBpkDocumentScan(newBpkDocumentScanVO);

                                this.status = 90;
                                this.statusText = "Copying file to path";
                                if (Utility.isNotNull(newBpkDocumentScanVO.getObjectID()))
                                {
                                    // Generate JRXML File
                                    String jrxmlFilename = GenerateJrxml.generateJrxmlPage(scanFilenames[i], DocScanDAOFactory.getDocScanInputPath(), false, 0, DocScanDAOFactory.getDocScanInputPath());
                                    // Convert PDF File
                                    String pdfFilename = Jrxml2Pdf.convertPage(jrxmlFilename, DocScanDAOFactory.getDocScanInputPath(), DocScanDAOFactory.getDocScanInputPath());

                                    // Delete JRXML after convert
                                    GenerateJrxml.deleteJrxmlFile(DocScanDAOFactory.getDocScanInputPath() + jrxmlFilename);

                                    // ���������� ObjectID ���� ��ͧ copy file ��������·ҧ���ç�Ѻ folder
                                    File srcFile = new File(DocScanDAOFactory.getDocScanInputPath() + System.getProperty("file.separator") + pdfFilename);
                                    File destPath = new File(DocScanDAOFactory.getDocScanOutputPath() + DocScanDAOFactory.getHnImageFolder(aPatientVO.getOriginalHn()) + System.getProperty("file.separator") + newBpkDocumentScanVO.getFolderName() + System.getProperty("file.separator"));
                                    if (!destPath.exists())
                                    {
                                        destPath.mkdirs();
                                    }
                                    File destFile = new File(destPath.toString() + System.getProperty("file.separator") + newBpkDocumentScanVO.getImageFileName());
                                    Path rstPath = Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                    // ź file �鹷ҧ ��� copy ��������
                                    File chkRstPath = new File(rstPath.toString());
                                    if (chkRstPath.exists())
                                    {
                                        srcFile.delete();
                                    }

                                    // ��ǹ�ͧ thumbnail Resize image ������ ������ Preview
                                    String thumbnailFilename = newBpkDocumentScanVO.getImageFileName()!=null ? "THUMBNAIL_"+newBpkDocumentScanVO.getImageFileName().toUpperCase().replaceAll(".PDF", ".jpg") : "THUMBNAIL.jpg";
                                    BufferedImage originalImg = ImageIO.read(new File(DocScanDAOFactory.getDocScanInputPath()+scanFilenames[i]));
                                    int type = originalImg.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImg.getType();
                                    BufferedImage resizeImageHintJpg = this.resizeImageWithHint(originalImg, type);
                                    String resizeFile = DocScanDAOFactory.getDocScanInputPath()+thumbnailFilename;
                                    ImageIO.write(resizeImageHintJpg, "jpg", new File(resizeFile));

                                    // ��ǹ�ͧ THUMBNAIL ��ͧ copy file ��������·ҧ���ç�Ѻ folder
                                    srcFile = new File(DocScanDAOFactory.getDocScanInputPath() + System.getProperty("file.separator") + thumbnailFilename);
                                    if (!destPath.exists())
                                    {
                                        destPath.mkdirs();
                                    }
                                    destFile = new File(destPath.toString() + System.getProperty("file.separator") + thumbnailFilename);
                                    rstPath = Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                    // ź file �鹷ҧ ��� copy ��������
                                    chkRstPath = new File(rstPath.toString());
                                    if (chkRstPath.exists())
                                    {
                                        srcFile.delete();
                                    }


                                    // �óշ��ӧҹ�������� move file ������ success folder
                                    File scanSrcFile = new File(getLastImage());
                                    File scanDestFile = new File(DocScanDAOFactory.getDocScanInputPathSuccess() + scanFilenames[i]);
                                    Files.move(scanSrcFile.toPath(), scanDestFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                    this.numSuccess++;

                                    this.setLastBpkDocumentScanVO(newBpkDocumentScanVO);
                                }
                                else
                                {
                                    // �óշ��ӧҹ�������� move file ������ fail folder
                                    File scanSrcFile = new File(getLastImage());
                                    File scanDestFile = new File(DocScanDAOFactory.getDocScanInputPathFail() + scanFilenames[i]);
                                    Files.move(scanSrcFile.toPath(), scanDestFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                    this.numFail++;
                                }
                            }
                            else
                            {
                                // �óշ��ӧҹ�������� move file ������ fail folder
                                File scanSrcFile = new File(getLastImage());
                                File scanDestFile = new File(DocScanDAOFactory.getDocScanInputPathFail() + scanFilenames[i]);
                                Files.move(scanSrcFile.toPath(), scanDestFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                this.numFail++;
                            }
                        }
                        else
                        {
                            // �óշ��ӧҹ�������� move file ������ fail folder
                            File scanSrcFile = new File(getLastImage());
                            File scanDestFile = new File(DocScanDAOFactory.getDocScanInputPathFail() + scanFilenames[i]);
                            Files.move(scanSrcFile.toPath(), scanDestFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            this.numFail++;
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
                this.status = 100;
                this.statusText = "Finished";
                this.numScan++;
            }
        }
        else
        {
            this.status = 100;
            this.statusText = "File not found";
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
            reader.setBarcodeTypesToFind(EnumSet.of(BarcodeType.QRCode));

            // Demonstrate barcode decoding from image file:
            Utility.printCoreDebug(new ImageScanFromPath(), "getTextFromBarcode, filename = " + filename);
            FoundBarcode[] foundBarcodes = reader.readFromFile(filename);
            if (foundBarcodes.length > 0)
            {
                for (int i = 0; i < foundBarcodes.length; i++)
                {
                    result = foundBarcodes[i].getValue();

                    // System.out.println("result at "+i+" = "+result);

                    if (result != null && result.indexOf("(") != -1)
                    {
                        result = result.substring(0, result.indexOf("(")).trim();
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
    private static final int MAX_DIM_FOR_THUMBNAIL = 200;

    public static Dimension getNewImageDimension(BufferedImage originalImage)
    {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int newWidth = 0;
        int newHeight = 0;

        if (height > width)
        {
            newHeight = MAX_DIM_FOR_THUMBNAIL;
            newWidth = (int) (newHeight * (float) width / (float) height);
        }
        else
        {
            newWidth = MAX_DIM_FOR_THUMBNAIL;
            newHeight = (int) (newWidth * (float) height / (float) width);
        }

        return new Dimension(newWidth, newHeight);
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type)
    {
        Dimension newDim = getNewImageDimension(originalImage);
        BufferedImage resizedImage = new BufferedImage(newDim.width, newDim.height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newDim.width, newDim.height, null);
        g.dispose();

        return resizedImage;
    }

    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type)
    {
        Dimension newDim = getNewImageDimension(originalImage);
        BufferedImage resizedImage = new BufferedImage(newDim.width, newDim.height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newDim.width, newDim.height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
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

    /**
     * @return the numScan
     */
    public int getNumScan()
    {
        return numScan;
    }

    /**
     * @param numScan the numScan to set
     */
    public void setNumScan(int numScan)
    {
        this.numScan = numScan;
    }

    /**
     * @return the numSuccess
     */
    public int getNumSuccess()
    {
        return numSuccess;
    }

    /**
     * @param numSuccess the numSuccess to set
     */
    public void setNumMatched(int numMatched)
    {
        this.numSuccess = numMatched;
    }

    /**
     * @return the numFail
     */
    public int getNumFail()
    {
        return numFail;
    }

    /**
     * @param numFail the numFail to set
     */
    public void setNumFail(int numFail)
    {
        this.numFail = numFail;
    }

    /**
     * @return the lastBpkDocumentScanVO
     */
    public BpkDocumentScanVO getLastBpkDocumentScanVO()
    {
        return lastBpkDocumentScanVO;
    }

    /**
     * @param lastBpkDocumentScanVO the lastBpkDocumentScanVO to set
     */
    public void setLastBpkDocumentScanVO(BpkDocumentScanVO lastBpkDocumentScanVO)
    {
        this.lastBpkDocumentScanVO = lastBpkDocumentScanVO;
    }

    /**
     * @return the lastImage
     */
    public String getLastImage()
    {
        return lastImage;
    }

    /**
     * @return the isInterrupt
     */
    public boolean isIsInterrupt()
    {
        return isInterrupt;
    }

    /**
     * @param isInterrupt the isInterrupt to set
     */
    public void setIsInterrupt(boolean isInterrupt)
    {
        this.isInterrupt = isInterrupt;
    }

    /**
     * @return the bpkDocumentScanVO
     */
    public BpkDocumentScanVO getBpkDocumentScanVO()
    {
        return bpkDocumentScanVO;
    }

    /**
     * @param bpkDocumentScanVO the bpkDocumentScanVO to set
     */
    public void setBpkDocumentScanVO(BpkDocumentScanVO bpkDocumentScanVO)
    {
        this.bpkDocumentScanVO = bpkDocumentScanVO;
    }
}
