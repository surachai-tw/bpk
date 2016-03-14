
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import com.bytescout.barcodereader.BarcodeType;
import com.bytescout.barcodereader.FoundBarcode;
import com.bytescout.barcodereader.Reader;
import java.awt.Graphics2D;
import java.util.EnumSet;

/**
 *
 * @author SURACHAI.TO
 */
public class RunScanWithThreshold
{

    public static void main(String args[])
    {
        try
        {
            System.out.println("Original, TEXT = " + getTextFromBarcode("D:\\BPKHIS\\trunk\\Sources\\CodeJSP\\jboss\\server\\default\\deploy\\bpkimage.war\\backup\\bak\\TEST_SYMBOL.jpg"));

            /*
            for (int i=100; i>=1; i--)
            {
            BufferedImage img = ImageIO.read(new File("D:\\BPK\\trunk\\BarCodeReader\\file\\sample\\SCAN.jpg"));
            ImageTool.toBlackAndWhite(img, i);
            String newname = "D:\\BPK\\trunk\\BarCodeReader\\file\\sample\\CHECK_" + i + ".jpg";
            ImageIO.write(img, "jpg", new File(newname));

            System.out.println("Precision = "+i+", TEXT = "+getTextFromBarcode(newname));
            }
             * 
             */
        } catch (Exception ex)
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
            /*
            BufferedImage in = ImageIO.read(new File(filename));
            BufferedImage newImage = new BufferedImage(
                    in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = newImage.createGraphics();
            g.drawImage(in, 0, 0, null);
            g.dispose();
            FoundBarcode[] foundBarcodes = reader.readFromImage(newImage);
             *
             */
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
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
        }
        return result;
    }
}
