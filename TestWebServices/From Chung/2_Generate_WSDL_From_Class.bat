@ECHO OFF
ECHO Move Class File to com\iMed\iMedApp\iMedInterfaceFXApp
move FXInterface.class com\iMed\iMedApp\iMedInterfaceFXApp
ECHO Generating WSDL File
java -cp .;axisall.jar org.apache.axis.wsdl.Java2WSDL -o FXInterface.wsdl -l"http://127.0.0.1:8080/axis/services/FXInterface" -n"http://ws.apache.org" -p"com.iMed.iMedApp.iMedInterfaceFXApp"="http://ws.apache.org" com.iMed.iMedApp.iMedInterfaceFXApp.FXInterface
PAUSE