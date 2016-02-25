@ECHO OFF
ECHO Compiling Code...
SET CP=..\..\..\..\..\..\Buildoutput\utility.jar;..\..\..\..\..\..\lib\ibatis\ibatis-2.3.0.677.jar;..\..\..\..\..\..\lib\printer\jasperreports-0.6.1_imed.jar;..\..\..\..\..\..\lib\schedule\scheduler-plugin.jar;
javac -classpath .;..\axisall.jar;%CP%;%CP2%; -d classes com\iMed\iMedApp\iMedInterfaceFXApp\ibatis\*.java
javac -classpath .;..\axisall.jar;%CP%;%CP2%; -d classes com\iMed\iMedApp\iMedInterfaceFXApp\iMedInterfaceFXVO\*.java
javac -classpath .;..\axisall.jar;%CP%;%CP2%; -d classes com\iMed\iMedApp\iMedInterfaceFXApp\*.java
PAUSE