import com.iMed.iMedApp.iMedInterfaceFXApp.*;
/** ตัวอย่างการเรียกใช้ WebService  ด้วย Apache AXIS 1.4
*** ก่อนรัน Code อย่าลืมเปิด Server ก่อน
*/
public class Example {
    public static void main(String[] args){
        try{
            
            FXInterfaceServiceLocator serviceLocator = new FXInterfaceServiceLocator();
            FXInterface service = serviceLocator.getFXInterface();
            String result = service.fxLogin("admin" , "demo");
            System.out.println("RESULT = "+result);
            //ดึงค่าออกมา 
            String[] arrResult = result.split("#");
            if( arrResult.length == 3 ){
                System.out.println("  KEY = "+arrResult[0]);
                System.out.println("  MAIN AUTH = "+arrResult[1]);
                System.out.println("  FORM AUTH = "+arrResult[2]);
            }
            result = service.fxPrintForm( arrResult[0] , "VISIT_SLIP" , "8675309" , "FX_PRINTER");
            System.out.println("RESULT 2 = "+result);
            result = service.fxVerifyHnVnAn( arrResult[0] , "490000021121" , "" , "");
            System.out.println("RESULT 3 = "+result);
            String hl7message = "MSH|^~\\&|Fuji Xerox||||||QBP^Q15^QBP Q15|||2.6|1\r";
            hl7message += "QPD|Z.01^List FX Interface FORM^FX|FX_1|admin";
            result = service.fxListForm( hl7message );
            System.out.println("RESULT 4 = "+result);
            /**
            String hl7message = "MSH|^~\\&|Fuji Xerox||||||QBP^Q15^QBP Q15|||2.6|1\r";
            hl7message += "QPD|Z.01^List FX Interface FORM^FX|FX_1|admin";
            String[] lines = hl7message.split("\r");
            System.out.println(lines[0]);
            System.out.println(lines[1]);
//*/
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
};