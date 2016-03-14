@ECHO OFF
ECHO Create SsbInterface.jar...
CD /d classes
jar cvf SsbInterface.jar src\com\bpk\app\ssb\*.class 
PAUSE