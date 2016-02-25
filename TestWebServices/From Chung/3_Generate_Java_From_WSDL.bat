@ECHO OFF
java -cp .;axisall.jar org.apache.axis.wsdl.WSDL2Java -o . -s -S true -N"http://ws.apache.org"="com.iMed.iMedApp.iMedInterfaceFXApp" FXInterface.wsdl
pause