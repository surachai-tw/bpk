import com.bpk.app.ssb.*;

/** ������ҧ������¡�� WebService  ���� Apache AXIS 1.4
*** ��͹�ѹ Code ��������Դ Server ��͹
*/

public class Example 
{
    public static void main(String[] args)
	{
        try
		{
            SsbInterfaceServiceLocator serviceLocator = new SsbInterfaceServiceLocator();
            SsbInterface service = serviceLocator.getConnWSSqlSvcWsdl();
            String result = service.getConnectionString();
			// String result = service.executeDataTable("15", "PYHBPK_PATIENT");
            System.out.println("RESULT = "+result);
        } 
		catch(Exception ex)
		{
            ex.printStackTrace();
        }
    }
};