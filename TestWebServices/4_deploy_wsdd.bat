@ECHO OFF
ECHO Plase Open Server with SsbInterface in war FIRST!
PAUSE
ECHO Deploying Service...
java -cp .;axisall.jar org.apache.axis.client.AdminClient src\com\bpk\app\ssb\deploy.wsdd
REM java -cp .;axisall.jar org.apache.axis.client.AdminClient -lhttp://localhost:8080/bpkhis.war com\bpk\app\ssb\deploy.wsdd
REM java -cp .;axisall.jar org.apache.axis.client.AdminClient
PAUSE