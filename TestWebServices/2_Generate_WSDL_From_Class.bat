@ECHO OFF
ECHO Move Class File to com\bpk\app\ssb
move SsbInterface.class com\bpk\app\ssb
ECHO Generating WSDL File
java -cp .;axisall.jar org.apache.axis.wsdl.Java2WSDL -o SsbInterface.wsdl -l"http://172.16.2.60:9002/ConnWSSql.svc?wsdl" -n"http://tempuri.org" -p"com.bpk.app.ssb.SsbInterface"="http://tempuri.org" com.bpk.app.ssb.SsbInterface
PAUSE