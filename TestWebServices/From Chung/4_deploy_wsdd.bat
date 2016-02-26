@ECHO OFF
ECHO Plase Open Server with fx_interface.war FIRST!
PAUSE
ECHO Deploying Service...
java -cp .;axisall.jar org.apache.axis.client.AdminClient com\iMed\iMedApp\iMedInterfaceFXApp\deploy.wsdd
REM java -cp .;axisall.jar org.apache.axis.client.AdminClient -lhttp://localhost:8080/fx_interface.war com\iMed\iMedApp\iMedInterfaceFXApp\deploy.wsdd
REM java -cp .;axisall.jar org.apache.axis.client.AdminClient
PAUSE