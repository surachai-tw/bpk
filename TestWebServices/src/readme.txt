1.Code ส่วนนี้ ทำไว้สำหรับ Web Service AXIS (เพราะจะ interface กับทาง FX-Fuji Xerox ด้วย WebService เป็นหลัก

2.เริ่มด้วยการกำหนด method ที่จะให้เรียกผ่าน WebService ไว้ในไฟล์ FXInterface.java ในโฟลเดอร์เดียวกับไฟล์นี้ก่อน
(เขียนเป็น interface เอาไว้)

3.เมื่อเขียนเสร็จรัน batch ไฟล์ 1_compile_FXInterface.bat เพื่อ compile เป็น .class

4. จากนั้นรัน batch ไฟล์ 2_Generate_WSDL_From_Class.bat เพื่อสร้างไฟล์ FXInterface.wsdl

5. จากนั้นรันไฟล์ batch ไฟล์ 3_Generate_Java_From_WSDL.bat เพื่อให้ Generate ไฟล์ออกมา
ไฟล์จะอยู่ในโฟลเดอร์ com\iMed\iMedApp\iMedInterfaceFXApp ที่อยู่ต่อจากโฟลเดอร์นี้อีกทีนึง

6. รันตัว Server ที่ลง Axis เอาไว้แล้วขึ้นมา จากนั้นรันไฟล์ batch 4_deploy_wsdd.bat เพื่อ deploy WebService
(deploy ไปแล้วก็จริง แต่ถ้าเรียกใช้ตอนนี้จะ error เพราะเรายังไม่ได้ใส่ class ลงไป)

7. copy code ที่ได้จากข้อ 5 ไปใส่ลงในโฟลเดอร์ Code เพื่อป้องกันการเผลอรัน batch ไฟล์ข้อ 5 โดยไม่ได้ตั้งใจ 
ซึ่งจะทำให้ Code ที่เราเขียนเพิ่มถูก Generate ทับ

8. แก้ไขไฟล์ใน Code\com\iMed\iMedApp\iMedInterfaceFXApp\FXInterfaceSoapBindingImpl.java สำหรับเขียน Code บอกว่าแต่ละ method จะให้ทำงานอะไร

9. batch สำหรับ compile  / สร้าง jar อยู่ในโฟลเดอร์ Code

*** ต้องแก้ setSoapAction("http://tempuri.org/IConnWSSql/GetConnectionString");
*** ต้องแก้ setSOAPActionURI("http://tempuri.org/IConnWSSql/GetConnectionString");
