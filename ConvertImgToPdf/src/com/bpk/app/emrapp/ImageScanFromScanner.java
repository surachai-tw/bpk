package com.bpk.app.emrapp;

import SK.gnome.twain.TwainException;
import SK.gnome.twain.TwainManager;
import SK.gnome.twain.TwainSource;
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
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.Transparency;
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
public class ImageScanFromScanner implements Runnable
{

    private int status = 0;
    private String statusText = "";
    private boolean isInterrupt = false;

    public void connectScanner()
    {
        int idx = 0;
        this.status = 0;
        this.statusText = "Connect to scanner...";
        try
        {
            DocScanDAOFactory.newDocScanDAO();
            TwainSource source = TwainManager.selectSource(null);

            if (source != null)
            {
                source.maskUnsupportedCapabilityException(false);
                source.maskBadValueException(false);
                try
                {
                    source.setFeederEnabled(true);
                    source.setAutoFeed(true);
                }
                catch (Exception ex)
                {
                    // ex.printStackTrace();
                    Utility.printCoreDebug(this, ex.getMessage());
                }
                this.status = 2;
                this.statusText = "Get images from scanner...";

                /*
                MediaTracker tracker = new MediaTracker(this);
                tracker.addImage(image, 0);
                try
                {
                tracker.waitForAll();
                }
                catch (InterruptedException e)
                {
                e.printStackTrace();
                }
                tracker.removeImage(image);
                 */

                // Create a buffered image with a format that's compatible with the screen
                BufferedImage bimage = null;
                do
                {
                    java.awt.Image image = Toolkit.getDefaultToolkit().createImage(source);
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    try
                    {
                        // Determine the type of transparency of the new buffered image
                        int transparency = Transparency.OPAQUE;

                        // Create the buffered image
                        GraphicsDevice gs = ge.getDefaultScreenDevice();
                        GraphicsConfiguration gc = gs.getDefaultConfiguration();

                        bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
                    }
                    catch (HeadlessException e)
                    {
                        // The system does not have a screen
                        e.printStackTrace();
                    }

                    if (bimage == null)
                    {
                        // Create a buffered image using the default color model
                        int type = BufferedImage.TYPE_INT_RGB;
                        bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
                    }

                    // Copy image to buffered image
                    Graphics g = bimage.createGraphics();

                    // Paint the image onto the buffered image
                    g.drawImage(image, 0, 0, null);
                    g.dispose();

                    File outputfile = new File(DocScanDAOFactory.getDocScanInputPath() + "_BPK" + Utility.getCurrentDate().replaceAll("-", "") + Utility.getCurrentTime().replaceAll(":", "") + (idx++) + ".jpg");
                    ImageIO.write(bimage, "jpg", outputfile);
                }
                while (source!= null && source.hasMoreImages());
                source = null;

                // aLblImg.setIcon(new ImageIcon(bimage));
                // aLblImg.setIcon(rescaleImage(new File(DocScanDAOFactory.getDocScanInputPath() + "SCANFILE.jpg"), aPnlImagePreview.getHeight(), aPnlImagePreview.getWidth()));
            }
            this.status = 8;
            this.statusText = "Prepare to match with HN...";
        }
        catch (TwainException ex)
        {
            ex.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
        }
    }

    public void run()
    {
        connectScanner();
    }

    public void setStatus(int value)
    {
        status = value;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatusText(String txt)
    {
        statusText = txt;
    }

    public String getStatusText()
    {
        return statusText;
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

}
