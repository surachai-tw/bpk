package com.bpk.app.emrapp;

import com.bpk.core.emrcore.dao.DocScanDAO;
import com.bpk.core.emrcore.dao.DocScanDAOFactory;
import com.bpk.persistence.emrdto.BpkDocumentScanVO;
import com.bpk.persistence.emrdto.PatientVO;
import com.bpk.utility.EventNames;
import com.bpk.utility.Utility;
import com.bpk.utility.fix.FixBooleanStatus;
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
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author SURACHAI.TO
 */
public class ImageScanFromPath implements Runnable
{
    /** Frm ��� */
    private JFrame parentFrame = null;

    /** ��㹡���кؼ��ӡ�� Scan */
    private String currentUser = null;
    /** ��㹡���кؼ��ӡ�� Scan */
    private String currentSpId = null;
    /** �� List file ��辺� Path */
    private String[] scanFilenames = null;
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
    /** ���纨ӹǹ�Ҿ��� Scan �������㺹�  */
    private int numCover = 0;
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
    /** ��㹡�õ�Ǩ�ͺ�������к� Check barcode ������͡���������� 㹡óշ��� Manual Inpput */
    private boolean securityCheckBarcode = false;

    /** ��㹡�õ�Ǩ�ͺ Priority �ͧ��� */
    private String bpkFixOrdersheetTypeId = null;

    /** ��㹡�õ�Ǩ�ͺ ����ͧ�� */
    private String isSendToPharmacy = FixBooleanStatus.FALSE;

    /** ��㹡�õ�Ǩ�ͺ ����ͧ����ҡ�� */
    private String isSendToNutrition = FixBooleanStatus.FALSE;

    public ImageScanFromPath(JFrame frm)
    {
        this.parentFrame = frm;
    }

    public JFrame getParentFrame()
    {
        return this.parentFrame;
    }

    public String[] getTextFromBarcodeWithThreshold(int precision, String scanFilename, String docScanInputPath)
    {
        String[] result = null;
        try
        {
            BufferedImage img = ImageIO.read(new File(docScanInputPath + scanFilename));
            ImageTool.toBlackAndWhite(img, precision);
            String newname = docScanInputPath + "THRESHOLD_" + scanFilename;
            ImageIO.write(img, "jpg", new File(newname));

            result = getTextFromBarcode(newname);
            if (result != null && result.length > 0)
            {
                for (int i = 0; i < result.length; i++)
                {
                    Utility.printCoreDebug(this, "Precision = " + precision + ", TEXT[" + i + "] = " + result[i]);
                }
            }

            // Delete threshold file 
            File file = new File(newname);
            file.delete();
        } catch (Exception ex)
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
        String[] coverText = null;
        boolean isCover = false;
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

        List listSrcFileSuccessForDel = new ArrayList();
        List listDestFileSuccessForMove = new ArrayList();
        List listSrcFileFailForDel = new ArrayList();
        List listDestFileFailForMove = new ArrayList();

        int tmpStatusBeforeMatch = 10;
        int tmpStatusAfterMatch = 80;
        if (scanFilenames != null && scanFilenames.length > 0)
        {
            this.setNumScan(scanFilenames.length);
            this.setNumCover(0);
            this.setNumMatched(0);
            this.setNumFail(0);
            
            for (int i = 0; i < scanFilenames.length && !this.isInterrupt; i++)
            {
                this.status = 0;
                this.statusText = "Prepared scan...";
                this.lastImage = DocScanDAOFactory.getDocScanInputPath() + scanFilenames[i];
                PatientVO aPatientVO = null;
                try
                {
                    String[] resultText = null;
                    String[] allReadText = null;

                    // �óշ��������駤�� this.bpkDocumentScanVO ����� ���� Matching HN
                    // �óշ���駤�� this.bpkDocumentScanVO ����� ����Ǩ�ͺ���� ��ҵ��Ǩ�ͺ����ͧ Security �������
                    if (this.bpkDocumentScanVO == null || (this.bpkDocumentScanVO != null && this.isSecurityCheckBarcode()))
                    {
                        this.status = tmpStatusBeforeMatch;
                        this.statusText = "Matching HN in database";
                        // Read text from barcode
                        int j = 101;
                        boolean isHnValid = false;
                        CHECK_HN_VALID:
                        do
                        {
                            // �ͺ�á ������ͧ�� Threshold
                            if (j == 101)
                            {
                                resultText = getTextFromBarcode(getLastImage());
                            } else
                            {
                                resultText = getTextFromBarcodeWithThreshold(j, scanFilenames[i], DocScanDAOFactory.getDocScanInputPath());
                            }

                            j -= 4;
                            int tmpStatus = (int) (((float) (101 - j) / 101) * (tmpStatusAfterMatch - tmpStatusBeforeMatch));
                            this.status = tmpStatusBeforeMatch + tmpStatus;

                            if (resultText != null && resultText.length > 0)
                            {
                                // resultText ��� BARCODE �����������
                                for (int x = 0; x < resultText.length; x++)
                                {
                                    // Library �ͧ bytescout ���� " " �繵�� split �����Ҩ�������ͧ���� + ���
                                    System.out.println(resultText[x]);
                                    allReadText = resultText[x].split(" ");
                                    // Library �ͧ bytescout ���� "+" �繵�� split ������������ͧ���� + ������
                                    // allReadText = resultText[x].split(Pattern.quote("+"));
                                    if (allReadText != null && allReadText.length > 1)
                                    {
                                        // for(int k=0; k<allReadText.length; k++)
                                        //{
                                        // allReadText[0] ��� HN ���� ����ͧ��Ǩ�ͺ
                                        isHnValid = aDocScanDAO.isPatientExistByHn(allReadText[0]);
                                        if (isHnValid)
                                        {
                                            break CHECK_HN_VALID;
                                        }
                                        ///}
                                    }
                                }
                            }
                        } while (j >= 1 && !this.isInterrupt);

                        if (!this.isInterrupt)
                        {
                            for (int chki = 0; allReadText != null && chki < allReadText.length; chki++)
                            {
                                System.out.println("allReadText[" + chki + "] = " + allReadText[chki]);
                            }
                            if (allReadText != null && allReadText.length >= 7 && "COVER".equalsIgnoreCase(allReadText[6]))
                            {
                                isCover = true;
                                coverText = allReadText;
                            } else
                            {
                                isCover = false;
                            }

                            this.status = tmpStatusAfterMatch;
                            this.statusText = "Create record in database";

                            // �����㺹� ����ͧ Scan ��Ӥ������㺶Ѵ�㹡óշ������� Barcode
                            // ��������㺹� �����ҹ��� Barcode ����� ���������Ţͧ㺹�
                            // ��������㺹� �����ҹ��� Barcode ��������� HN 㹹�� ���������Ţͧ㺹�
                            if (!isCover && (allReadText == null || !isHnValid) && coverText != null)
                            {
                                allReadText = coverText;
                            }

                        }
                    }

                    // ��������㺹� ����բ����� Barcode �ú ��� Scan ��һ���
                    // this.bpkDocumentScanVO ��㹡óշ�� Scan ���¡���к� HN �ͧ
                    if ((!isCover && allReadText != null) || this.bpkDocumentScanVO != null)
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
                                newBpkDocumentScanVO.setOriginalHn(aPatientVO.getOriginalHn());
                                newBpkDocumentScanVO.setPatientName(aPatientVO.getPatientName());
                                newBpkDocumentScanVO.setVn(allReadText != null && allReadText.length >= 2 ? allReadText[1] : "");
                                newBpkDocumentScanVO.setDoctorEid(allReadText != null && allReadText.length >= 3 ? allReadText[2] : "");
                                newBpkDocumentScanVO.setPrintDate(BpkDocumentScanVO.getDateFromReadText(allReadText != null && allReadText.length >= 4 ? allReadText[3] : ""));
                                newBpkDocumentScanVO.setPrintTime(BpkDocumentScanVO.getTimeFromReadText(allReadText != null && allReadText.length >= 4 ? allReadText[3] : ""));
                                newBpkDocumentScanVO.setFolderName(allReadText != null && allReadText.length >= 5 ? allReadText[4] : "OTR");
                                newBpkDocumentScanVO.setDocumentName(allReadText != null && allReadText.length >= 6 ? allReadText[5] : "");
                                newBpkDocumentScanVO.setOption(allReadText != null && allReadText.length >= 7 ? allReadText[6] : "");
                                newBpkDocumentScanVO.setScanEid(this.getCurrentUser());
                                newBpkDocumentScanVO.setUpdateEid(this.getCurrentUser());

                                newBpkDocumentScanVO.setBpkFixOrdersheetTypeId(this.getBpkFixOrdersheetTypeId());
                                newBpkDocumentScanVO.setIsSendToPharmacy(isSendToPharmacy);
                                newBpkDocumentScanVO.setIsSendToNutrition(isSendToNutrition);
                            } else
                            {
                                newBpkDocumentScanVO = new BpkDocumentScanVO();
                                newBpkDocumentScanVO.setPatientId(aPatientVO.getObjectID());
                                newBpkDocumentScanVO.setHn(aPatientVO.getHn());
                                newBpkDocumentScanVO.setOriginalHn(aPatientVO.getOriginalHn());
                                newBpkDocumentScanVO.setPatientName(aPatientVO.getPatientName());
                                newBpkDocumentScanVO.setVn(bpkDocumentScanVO.getVn());
                                newBpkDocumentScanVO.setDoctorEid(bpkDocumentScanVO.getDoctorEid());
                                newBpkDocumentScanVO.setPrintDate(bpkDocumentScanVO.getPrintDate());
                                newBpkDocumentScanVO.setPrintTime(bpkDocumentScanVO.getPrintTime());
                                newBpkDocumentScanVO.setFolderName(Utility.isNotNull(bpkDocumentScanVO.getFolderName()) ? bpkDocumentScanVO.getFolderName() : "OTR");
                                newBpkDocumentScanVO.setDocumentName(bpkDocumentScanVO.getDocumentName());
                                newBpkDocumentScanVO.setOption(bpkDocumentScanVO.getOption());
                                newBpkDocumentScanVO.setScanEid(this.getCurrentUser());
                                newBpkDocumentScanVO.setUpdateEid(this.getCurrentUser());

                                newBpkDocumentScanVO.setBpkFixOrdersheetTypeId(bpkDocumentScanVO.getBpkFixOrdersheetTypeId());
                                newBpkDocumentScanVO.setIsSendToPharmacy(isSendToPharmacy);
                                newBpkDocumentScanVO.setIsSendToNutrition(isSendToNutrition);
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
                                    /*
                                File srcFile = new File(DocScanDAOFactory.getDocScanInputPath() + System.getProperty("file.separator") + pdfFilename);
                                File destPath = new File(DocScanDAOFactory.getDocScanOutputPath() + DocScanDAOFactory.getHnImageFolder(aPatientVO.getOriginalHn()) + System.getProperty("file.separator") + newBpkDocumentScanVO.getFolderName() + System.getProperty("file.separator"));
                                if (!destPath.exists())
                                {
                                destPath.mkdirs();
                                }
                                File destFile = new File(destPath.toString() + System.getProperty("file.separator") + newBpkDocumentScanVO.getImageFileName());
                                Path rstPath = Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                 */
                                newBpkDocumentScanVO.setPdfBytes(this.convertFile2Bytes(DocScanDAOFactory.getDocScanInputPath() + System.getProperty("file.separator") + pdfFilename));

                                // ź file �鹷ҧ ��� copy ��������
                                    /*
                                File chkRstPath = new File(rstPath.toString());
                                if (chkRstPath.exists())
                                {
                                srcFile.delete();
                                }*/

                                // ��ǹ�ͧ thumbnail Resize image ������ ������ Preview
                                String thumbnailFilename = newBpkDocumentScanVO.getImageFileName() != null ? "THUMBNAIL_" + newBpkDocumentScanVO.getImageFileName().toUpperCase().replaceAll(".PDF", ".JPG") : "THUMBNAIL.JPG";
                                newBpkDocumentScanVO.setThumbnailImageFileName(thumbnailFilename);
                                BufferedImage originalImg = ImageIO.read(new File(DocScanDAOFactory.getDocScanInputPath() + scanFilenames[i]));
                                int type = originalImg.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImg.getType();
                                BufferedImage resizeImageHintJpg = this.resizeImageWithHint(originalImg, type);
                                String resizeFile = DocScanDAOFactory.getDocScanInputPath() + thumbnailFilename;
                                ImageIO.write(resizeImageHintJpg, "jpg", new File(resizeFile));

                                // ��ǹ�ͧ THUMBNAIL ��ͧ copy file ��������·ҧ���ç�Ѻ folder
                                    /*
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
                                }*/
                                newBpkDocumentScanVO.setJpgBytes(this.convertFile2Bytes(DocScanDAOFactory.getDocScanInputPath() + System.getProperty("file.separator") + thumbnailFilename));

                                // ��Ẻ Http Upload
                                String rst = sendDocScan(newBpkDocumentScanVO);
                                // Utility.printCoreDebug(this, "Result from sendDocScan = '"+rst+"'");
                                if (rst != null && FixBooleanStatus.TRUE.equals(rst.trim()))
                                {
                                    boolean chkRstDel = true;

                                    // ź file �鹷ҧ ��� copy ��������
                                    File srcFile = new File(DocScanDAOFactory.getDocScanInputPath() + System.getProperty("file.separator") + pdfFilename);
                                    Utility.printCoreDebug(this, srcFile.toString());
                                    do
                                    {
                                        chkRstDel = srcFile.delete();
                                        if (!chkRstDel)
                                        {
                                            //    Utility.printCoreDebug(this, "Delete pdf fail");
                                            Thread.sleep(1000);
                                        }
                                    } while (!chkRstDel);

                                    // ź file �鹷ҧ ��� copy ��������
                                    srcFile = new File(DocScanDAOFactory.getDocScanInputPath() + System.getProperty("file.separator") + thumbnailFilename);
                                    Utility.printCoreDebug(this, srcFile.toString());
                                    do
                                    {
                                        chkRstDel = srcFile.delete();
                                        if (!chkRstDel)
                                        {
                                            //   Utility.printCoreDebug(this, "Delete thumnail fail");
                                            Thread.sleep(1000);
                                        }
                                    } while (!chkRstDel);
                                }

                                // �óշ��ӧҹ�������� move file ������ success folder
                                File scanSrcFile = new File(getLastImage());
                                File scanDestFile = new File(DocScanDAOFactory.getDocScanInputPathSuccess() + scanFilenames[i]);
                                listSrcFileSuccessForDel.add(scanSrcFile);
                                listDestFileSuccessForMove.add(scanDestFile);
                                this.numSuccess++;

                                this.setLastBpkDocumentScanVO(newBpkDocumentScanVO);
                            } else
                            {
                                // �óշ��ӧҹ����������� move file ������ fail folder
                                File scanSrcFile = new File(getLastImage());
                                File scanDestFile = new File(DocScanDAOFactory.getDocScanInputPathFail() + scanFilenames[i]);
                                listSrcFileFailForDel.add(scanSrcFile);
                                listDestFileFailForMove.add(scanDestFile);
                                // Files.move(scanSrcFile.toPath(), scanDestFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                this.numFail++;
                            }
                        } else
                        {
                            // �óշ��ӧҹ�������� move file ������ fail folder
                            File scanSrcFile = new File(getLastImage());
                            File scanDestFile = new File(DocScanDAOFactory.getDocScanInputPathFail() + scanFilenames[i]);
                            listSrcFileFailForDel.add(scanSrcFile);
                            listDestFileFailForMove.add(scanDestFile);
                            // Files.move(scanSrcFile.toPath(), scanDestFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            this.numFail++;
                        }
                    } else
                    {
                        // �óշ��ӧҹ�������� move file ������ fail folder
                        File scanSrcFile = new File(getLastImage());
                        File scanDestFile = new File(DocScanDAOFactory.getDocScanInputPathFail() + scanFilenames[i]);
                        listSrcFileFailForDel.add(scanSrcFile);
                        listDestFileFailForMove.add(scanDestFile);
                        // Files.move(scanSrcFile.toPath(), scanDestFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        if (!isCover)
                        {
                            this.numFail++;
                        } else
                        {
                            this.setNumCover(this.getNumCover() + 1);
                        }
                    }

                } catch (Exception ex)
                {
                    ex.printStackTrace();
                } finally
                {
                }

                if (i + 1 == scanFilenames.length)
                {
                    this.lastImage = null;
                    //"D:\\TMP.jpg";
                }
                this.status = 100;
                this.statusText = "Finished";
            }
        } else
        {
            this.status = 100;
            this.statusText = "File not found";
        }

        // Delete file after process
        // ���ź�ѹ�� �Ҩ���Դ�ѭ�� is being used by another process.
        for (int i = 0, sizei = listSrcFileSuccessForDel.size(); i < sizei; i++)
        {
            boolean chkRstMove = true;
            // ���ź�����Ҩ������
            do
            {
                try
                {
                    File scanSrcFile = (File) listSrcFileSuccessForDel.get(i);
                    File scanDestFile = (File) listDestFileSuccessForMove.get(i);
                    Files.move(scanSrcFile.toPath(), scanDestFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    chkRstMove = true;
                } catch (Exception ex)
                {
                    chkRstMove = false;
                    // ex.printStackTrace();
                }
            } while (!chkRstMove);
        }
        for (int i = 0, sizei = listSrcFileFailForDel.size(); i < sizei; i++)
        {
            boolean chkRstMove = true;
            // ���ź�����Ҩ������
            do
            {
                try
                {
                    File scanSrcFile = (File) listSrcFileFailForDel.get(i);
                    File scanDestFile = (File) listDestFileFailForMove.get(i);
                    Files.move(scanSrcFile.toPath(), scanDestFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    chkRstMove = true;
                } catch (Exception ex)
                {
                    chkRstMove = false;
                    // ex.printStackTrace();
                }
            } while (!chkRstMove);
        }

    }

    public int getStatus()
    {
        return status;
    }

    public static String[] getTextFromBarcode(String filename)
    {
        List listResult = new ArrayList();
        try
        {
            Reader reader = new Reader();
            reader.setRegistrationName("demo");
            reader.setRegistrationKey("demo");
            // Set barcode types to find:
            reader.setBarcodeTypesToFind(EnumSet.of(BarcodeType.QRCode, BarcodeType.Code39, BarcodeType.Code128));

            // Demonstrate barcode decoding from image file:
            // Utility.printCoreDebug(new ImageScanFromPath(), "getTextFromBarcode(" + filename+")");
            FoundBarcode[] foundBarcodes = reader.readFromFile(filename);
            if (foundBarcodes.length > 0)
            {
                for (int i = 0; i < foundBarcodes.length; i++)
                {
                    String result = foundBarcodes[i].getValue();

                    // System.out.println("result at "+i+" = "+result);

                    if (result != null && result.indexOf("(") != -1)
                    {
                        result = result.substring(0, result.indexOf("(")).trim();

                        listResult.add(result);
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
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
        }
        return (String[]) listResult.toArray(new String[listResult.size()]);
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
        } else
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

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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

    public byte[] convertFile2Bytes(String absFilename)
    {
        File file = new File(absFilename);
        byte[] bytes = null;

        try
        {
            FileInputStream fis = new FileInputStream(file);
            //System.out.println(file.exists() + "!!");
            //InputStream in = resource.openStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024 * 1024];
            try
            {
                for (int readNum; (readNum = fis.read(buf)) != -1;)
                {
                    bos.write(buf, 0, readNum); //no doubt here is 0
                    //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                    System.out.println("Read " + readNum + " bytes");
                }
            } catch (IOException ex)
            {
                // Logger.getLogger(genJpeg.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }

            bytes = bos.toByteArray();

            //below is the different part
            /*
            File someFile = new File("java2.pdf");
            FileOutputStream fos = new FileOutputStream(someFile);
            fos.write(bytes);
            fos.flush();
            fos.close();
             */
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return bytes;
    }

    public static String sendDocScan(BpkDocumentScanVO aBpkDocumentScanVO)
    {
        try
        {
            // Create an object we can use to communicate with the servlet
            String url = DocScanDAOFactory.getDocScanInputUrl();
            Utility.printCoreDebug(new ImageScanFromPath(null), url);
            URL servletURL = new URL(url);
            URLConnection servletConnection = servletURL.openConnection();
            servletConnection.setDoOutput(true);  // to allow us to write to the URL
            servletConnection.setUseCaches(false);  // to ensure that we do contact
            // the servlet and don't get
            // anything from the browser's
            // cache

            ObjectOutputStream outputToServlet = new ObjectOutputStream(servletConnection.getOutputStream());
            outputToServlet.writeObject(aBpkDocumentScanVO);
            outputToServlet.flush();
            outputToServlet.close();

            // ���������ǹ����͡ ���Шз�����������ö upload ��
            InputStream in = servletConnection.getInputStream();
            StringBuilder response = new StringBuilder();
            int chr;
            while ((chr = in.read()) != -1)
            {
                response.append((char) chr);
            }
            in.close();
            // Utility.printCoreDebug(new ImageScanFromPath(), "Responsed = "+response.toString());
            return response.toString();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * @return the currentUser
     */
    public String getCurrentUser()
    {
        return Utility.getStringVO(currentUser);
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(String currentUser)
    {
        this.currentUser = currentUser;
    }

    /**
     * @return the numCover
     */
    public int getNumCover()
    {
        return numCover;
    }

    /**
     * @param numCover the numCover to set
     */
    public void setNumCover(int numCover)
    {
        this.numCover = numCover;
    }

    /**
     * TRUE - ���¶֧ ����Ǩ�ͺ
     * FALSE - ���¶֧ ����ͧ��Ǩ�ͺ 
     * @return the securityCheckBarcode
     */
    public boolean isSecurityCheckBarcode()
    {
        return securityCheckBarcode;
    }

    /**
     * @param securityCheckBarcode the securityCheckBarcode to set
     */
    public void setSecurityCheckBarcode(boolean securityCheckBarcode)
    {
        this.securityCheckBarcode = securityCheckBarcode;
    }

    public void setCurrentSpId(String currentSpId)
    {
        this.currentSpId = currentSpId;
    }

    /**
     * @return the bpkFixOrdersheetTypeId
     */
    public String getBpkFixOrdersheetTypeId()
    {
        return Utility.getStringVO(bpkFixOrdersheetTypeId);
    }

    /**
     * @param bpkFixOrdersheetTypeId the bpkFixOrdersheetTypeId to set
     */
    public void setBpkFixOrdersheetTypeId(String bpkFixOrdersheeetTypeId)
    {
        this.bpkFixOrdersheetTypeId = bpkFixOrdersheeetTypeId;
    }

    /**
     * @return the isSendToPharmacy
     */
    public String getIsSendToPharmacy()
    {
        return Utility.getActiveVO(isSendToPharmacy);
    }

    /**
     * @param isSendToPharmacy the isSendToPharmacy to set
     */
    public void setIsSendToPharmacy(String isSendToPharmacy)
    {
        this.isSendToPharmacy = isSendToPharmacy;
    }

    /**
     * @return the isSendToNutrition
     */
    public String getIsSendToNutrition()
    {
        return Utility.getActiveVO(isSendToNutrition);
    }

    /**
     * @param isSendToNutrition the isSendToNutrition to set
     */
    public void setIsSendToNutrition(String isSendToNutrition)
    {
        this.isSendToNutrition = isSendToNutrition;
    }
}
