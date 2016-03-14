@ECHO OFF
java -cp .;axisall.jar org.apache.axis.wsdl.WSDL2Java -o . -s -S true -N"http://tempuri.org"="com.bpk.app.ssb" SsbInterface.wsdl
pause