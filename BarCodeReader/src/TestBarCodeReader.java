import com.bytescout.barcodereader.BarcodeType;
import com.bytescout.barcodereader.FoundBarcode;
import com.bytescout.barcodereader.Reader;
import java.util.EnumSet;

/**
 *
 * @author SURACHAI.TO
 */
public class TestBarCodeReader
{

    public static void main(String args[])
    {
        try
        {
            Reader reader = new Reader();
            reader.setRegistrationName("demo");
            reader.setRegistrationKey("demo");
            // Set barcode types to find:
            reader.setBarcodeTypesToFind(EnumSet.of(BarcodeType.Code39, BarcodeType.Code39Ext, BarcodeType.Code128, BarcodeType.QRCode));

            // Demonstrate barcode decoding from image file:
            FoundBarcode[] foundBarcodes = reader.readFromFile("D:\\BPK\\trunk\\BarCodeReader\\file\\TEST-05.jpg");
            if (foundBarcodes.length > 0)
            {
                for (int i = 0; i < foundBarcodes.length; i++)
                {
                    String locationId = foundBarcodes[i].getValue();
                    System.out.println("LOC =" + locationId);

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
    }
}
