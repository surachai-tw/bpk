
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
import java.util.EnumSet;

/**
 *
 * @author SURACHAI.TO
 */
public class RunScanWithThresholdNotWork
{
    public static boolean IS_RESIZE = false;
    
    public static void main(String args[])
    {
        try
        {
            String originalFile = "D:\\BPK\\trunk\\BarCodeReader\\file\\sample\\SCAN.jpg";
            BufferedImage originalImg = ImageIO.read(new File(originalFile));
            int type = originalImg.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImg.getType();

            // ย่อ file
            BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImg, type);
            String resizeFile = "D:\\BPK\\trunk\\BarCodeReader\\file\\sample\\RESIZE.jpg";
            ImageIO.write(resizeImageHintJpg, "jpg", new File(resizeFile));

            BufferedImage imgForRead = null;
            if(IS_RESIZE)
                imgForRead = resizeImageHintJpg;
            else
                imgForRead = originalImg;
                    
            
            for (int i = 100; i >= 0; i--)
            {
                ImageTool.toBlackAndWhite(imgForRead, i);
                String newname = "D:\\BPK\\trunk\\BarCodeReader\\file\\sample\\CHECK_" + i + ".jpg";
                ImageIO.write(imgForRead, "jpg", new File(newname));

                System.out.println("precision = " + i + ", TEXT = " + getTextFromBarcode(newname));
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
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
            FoundBarcode[] foundBarcodes = reader.readFromFile(filename);
            if (foundBarcodes.length > 0)
            {
                for (int i = 0; i < foundBarcodes.length; i++)
                {
                    result = foundBarcodes[i].getValue();

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

    private static final int MAX_DIM = 2200;

    public static Dimension getNewImageDimension(BufferedImage originalImage)
    {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int newWidth = 0;
        int newHeight = 0;

        if(height>width)
        {
            newHeight = MAX_DIM;
            newWidth = (int)(newHeight*(float)width/(float)height);
        }
        else
        {
            newWidth = MAX_DIM;
            newHeight = (int)(newWidth*(float)height/(float)width);
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
}
