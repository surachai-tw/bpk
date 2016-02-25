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

			try
			{
				String result = service.getConnectionString();
				System.out.println("RESULT #1 = "+result);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			
			try
			{
				String result = service.executeDataTable("93000699", "PYHBPK_PATIENT");
				System.out.println("RESULT #2 = "+result);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			
			try
			{
				String result = service.executeNonQuery("SELECT 1+1");
				System.out.println("RESULT #3 = "+result);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
        } 
		catch(Exception ex)
		{
            ex.printStackTrace();
        }
    }
};