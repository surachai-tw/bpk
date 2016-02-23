@echo off
rem Add all jars....
for %%i in ("lib\*.jar") do call "cpappend.bat" %%i

java -Xms1024m -Xmx1024m com.bpk.app.emrapp.FrmImageScan -cp %1 %2 %3 %4 %5 %6 %7 %8 %9 