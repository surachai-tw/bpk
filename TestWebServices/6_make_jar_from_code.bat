@ECHO OFF
ECHO Create SsbInterface.jar...
CD /d classes
jar cvf SsbInterface.jar com/bpk/app/ssb/*.class 
PAUSE