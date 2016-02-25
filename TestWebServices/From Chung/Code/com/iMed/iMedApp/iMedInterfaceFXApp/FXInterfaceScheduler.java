package com.iMed.iMedApp.iMedInterfaceFXApp;

import java.io.*;
import java.util.*;

import org.jboss.varia.scheduler.Schedulable;

import com.iMed.iMedApp.iMedInterfaceFXApp.ibatis.*;
import com.iMed.iMedCore.utility.*;
import com.iMed.iMedCore.utility.fix.*;
/** สำหรับให้ Jboss เรียกใช้ผ่าน Scheduler Service เพื่อทำการอ่านรูปและนำเข้ารูปไปใน iMed
*/
public class FXInterfaceScheduler implements Schedulable {
    /** format เลข HN ของไฟล์รูปที่มาจาก fuji xerox 
       แบบนี้คือเลข HN จะตรงกับที่เก็บในฐานข้อมูลของ iMed  */
    private final String HN_MATCH_FORMAT="0";
    /** format เลข HN ของไฟล์รูปที่มาจาก fuji xerox
      แบบนี้คือ HN จะเป็นเลขปี (พ.ศ.) 2 หลัก ตามด้วยเลข Running กี่หลักก็ได้ เช่น 531 (ปีคือ 53 , running = 1) , 53256 (ปีคือ 53 , running = 256) */
    private final String HN_YEAR_AND_RUNNING_FORMAT="1";


    /** ค่าคงที่ ใน imed.properties ที่เกี่ยวกับระบบ interface */
    private final String IMED_PROP_HN_FORMAT = "FX_HN_DATA_FORMAT";
    private final String IMED_PROP_ENABLED = "FX_INTERFACE_ENABLED";
    private final String IMED_PROP_SHARE_FOLDER = "FX_PATIENT_IMG_SHARE_FOLDER";
    private final String IMED_PROP_FAIL_FOLDER = "FX_PATIENT_IMG_FAIL_FOLDER";
    private final String IMED_PROP_IMED_FOLDER = "FX_PATIENT_IMG_IMED_FOLDER";

    /** method สำหรับ implement Schedulable ของ JBOSS เพื่อให้เรียกใช้งานเป็น Service ของ JBoss ได้ */
    private String mName;
    private int mValue;
    public FXInterfaceScheduler(String mName, int mValue) {
        this.mName = mName;
        this.mValue = mValue;
    }

    public void perform(Date pTimeOfCall, long pRemainingRepetitions) {
      try {
        runImportPatientImage();
      } catch(Exception ex) {
        ex.printStackTrace();
      }
    }
    /** =============================== จบ implement schedulable ของ JBoss ============================*/


    /** สำหรับเรียกใช้งาน เพื่อ import รูปภาพคนไข้ เข้าระบบ iMed */
    public void runImportPatientImage(){
        try{
            //เช็คก่อนว่า Enabled หรือเปล่า
            if( FixBooleanStatus.TRUE.equals(Utility.getIMedProperties(IMED_PROP_ENABLED))){
                System.out.println("[FX INTERFACE] RUNNING...");
                String shareFolder = Utility.getIMedProperties(IMED_PROP_SHARE_FOLDER);
                String failFolder = Utility.getIMedProperties(IMED_PROP_FAIL_FOLDER);
                String imedFolder = Utility.getIMedProperties(IMED_PROP_IMED_FOLDER);
                //ตรวจสอบว่าเจอทั้ง 3 โฟลเดอร์หรือไม่ หากไม่เจอจะไม่ทำต่อ
                File fShareFolder = new File(shareFolder);
                if( fShareFolder.exists() && fShareFolder.isDirectory()){
                    File fFailFolder = new File(failFolder);
                    if( fFailFolder.exists() && fFailFolder.isDirectory()){
                        File fIMedFolder = new File(imedFolder);
                        if( fIMedFolder.exists() && fIMedFolder.isDirectory()){
                            File[] arrayFiles = fShareFolder.listFiles();
                            if( arrayFiles == null || arrayFiles.length <= 0 ){
                                //ไม่พบรูปใด ๆ ที่ต้องนำเข้า
                                System.out.println("[FX INTERFACE] NOT FOUND ANY IMAGE 2 IMPORT");
                                return;
                            }
                            System.out.print("[FX INTERFACE] FOUND "+arrayFiles.length+",");
                            String fName = null;
                            String[] fNames = null;
                            String hn = null;
                            String fileIMedPathName = null;
                            for(int i=0 ; i < arrayFiles.length ; i++){
                                File f = arrayFiles[i];
                                //เช็คว่าเป็นรูป jpg หรือ jpeg หรือเปล่า
                                fName = f.getName().toLowerCase();
                                if( fName.indexOf(".jpg") != -1 || fName.indexOf(".jpeg") != -1){
                                    //ตัดแยกข้อมูลของชื่อไฟล์ออกมา
                                    fNames = fName.substring( 0 , fName.indexOf(".")).split("_");
                                    hn = fNames[0];
                                    //แปลง HN ให้ตรงกับ format บน Database
                                    hn = convertFormatHN2iMed(hn);
                                    if( hn != null ){
                                        fileIMedPathName = getIMedPatientPictureFolderPath( hn );
                                        if( fileIMedPathName != null ){
                                            fileIMedPathName = imedFolder + File.separator + fileIMedPathName;
                                            File folder = new File(fileIMedPathName);
                                            if( folder.exists() == false ){
                                                folder.mkdirs(); //หากยังไม่มีให้สร้างขึ้นมา
                                            }
                                            File fOnImed = new File( fileIMedPathName , f.getName());
                                            //ทำการย้ายไฟล์
                                            if( f.renameTo( fOnImed) == true ){
                                                System.out.println("[FX INTERFACE] IMPORT FILE ["+f.getName()+"] TO ["+fOnImed.getAbsolutePath()+"]");
                                            } else {
                                                //เกิดข้อผิดพลาด ย้ายไปโฟลเดอร์ fail
                                                System.out.println("[FX INTERFACE] FAIL 2 IMPORT FILE "+fOnImed.getAbsolutePath()+"]");
                                                f.renameTo( new File( fFailFolder , f.getName()));
                                            }
                                        } else {
                                            System.out.println("[FX INTERFACE] CAN't GET IMED PATH NAME FOR HN "+hn);
                                            //ย้ายไป fail folder
                                            f.renameTo( new File( fFailFolder , f.getName()));
                                        }
                                    } else {
                                        //แสดงว่า format ไม่ถูกต้อง
                                        System.out.println("[FX INTERFACE] CAN't CONVERT HN FORMATTED FOR HN "+hn+" PLEASE CHECK SETTING");
                                        //ย้ายไป fail folder
                                        f.renameTo( new File( fFailFolder , f.getName()));
                                    }
                                } else {
                                    System.out.println("[FX INTERFACE] FOUND NO jpeg Files move 2 fail folder");
                                    if( f.isFile() ){
                                        //ย้ายไป fail folder
                                        f.renameTo( new File( fFailFolder , f.getName()));
                                    }
                                }
                            }
                        } else {
                            System.out.println("[FX INTERFACE] NOT FOUND IMED FOLDER ["+imedFolder+"]");
                        }
                        fIMedFolder = null;
                    } else {
                        System.out.println("[FX INTERFACE] NOT FOUND FAIL FOLDER ["+failFolder+"]");
                    }
                    fFailFolder = null;
                } else {
                    System.out.println("[FX INTERFACE] NOT FOUND SHARE FOLDER ["+shareFolder+"]");
                }
                fShareFolder = null;
            } else {
                System.out.println("[FX INTERFACE] NOT ENABLED");
            }
        } catch (Exception ex){
            ex.printStackTrace();
            System.out.println("[FX INTERFACE] EXCEPTION "+ex.toString());
        }
    }


    /** method สำหรับแปลง HN ของรูปภาพ เป็น format ที่อยู่ใน Database 
    * เลือก format จากค่าใน iMed.properties
    */
    private String convertFormatHN2iMed(String hnUnformatted) {
        if( HN_MATCH_FORMAT.equals(Utility.getIMedProperties(IMED_PROP_HN_FORMAT))){
            //แบบนี้คือเลขตรงอยู่แล้ว ไม่ต้องทำอะไร เช็คว่าจำนวนหลักตรงไหมก็พอ
            if( hnUnformatted != null && hnUnformatted.length() == 12 ){ //จะต้องยาว 12 หลัก
                return hnUnformatted;
            } else {
                return null;
            }
        } else if( HN_YEAR_AND_RUNNING_FORMAT.equals(Utility.getIMedProperties(IMED_PROP_HN_FORMAT))){
            //แบบนี้คือเป็นเลขปี 2 หลัก ตามด้วย running จะต้องแปลงเป็นรูปแบบตาม iMed คือ ปี 2 หลัก running 10 หลัก (เติม 0 นำหน้าเลข running)
            if( hnUnformatted != null && hnUnformatted.length() >= 3 ){ //อย่างต่ำจะต้องส่งมา 3 หลัก
                String year = hnUnformatted.substring(0 , 2);
                String running = hnUnformatted.substring( 2 , hnUnformatted.length());
                //เติมเลข running ให้ครบ 10 หลัก
                for( ; running.length() < 10 ; ){
                    running = "0"+running;
                }
                return year+running;
            } else {
                return null;
            }
        }
        return null;
    }
    /** method สำหรับ เอา HN จากชื่อไฟล์รูป มาสร้างเป็น path ที่จะเก็บไว้ใน iMed 
    * หมายเหตุ จะตรวจสอบก่อนว่า HN นี้มีอยู่ในฐานข้อมูลหรือไม่
      fileName คือ ชื่อไฟล์รูปเท่านั้น (ไม่ต้องมี path)
      @return null หากเกิดข้อผิดพลาด
      @return String ของ path ที่สามารถนำไปต่อกับ iMed Folder โดยจะมีถึง folder ที่ต้องเอาไฟล์เข้าไปใส่เท่านั้น
    */
    private String getIMedPatientPictureFolderPath(String hn ) throws Exception {
        //เช็คก่อนว่า HN ที่ส่งมามีอยู่ในฐานข้อมูลหรือไม่
        FXDAO fxDAO = new FXDAO();
        String patientId = fxDAO.checkHN( hn );
        if( patientId != null ){
            //แสดงว่ามีในฐานข้อมูล
            String hnRunningNumber = hn.substring( hn.length()-7 , hn.length()); //เอาเลข running 7 หลักของ HN มา
            long longHn = Long.parseLong(hnRunningNumber);
            long hnMod = longHn%100;
            long folderMod = longHn-hnMod; //เอาเลข HN เต็มลบด้วยเคษ 100 ออก
            long endFolderMod = 0;
            if( hnMod >= 1 ){
              //กรณีหาร 100 ลงไม่ลงตัว (มีเศษ
              folderMod++; //+1 เข้าไป
              endFolderMod = folderMod+99;
            } else {
              endFolderMod = folderMod;
              folderMod = folderMod-99;
            }
            String startFolderName = String.valueOf(folderMod);
            String endFolderName = String.valueOf(endFolderMod);
            //เติม 0 นำหน้าให้ครบ 7 หลัก
            for( ; startFolderName.length() < 7 ; ){
              startFolderName = "0"+startFolderName;
            }
            for( ; endFolderName.length() < 7 ; ){
              endFolderName = "0"+endFolderName;
            }
            String finalFolderName = hn.substring(0,2)+File.separator+startFolderName+"-"+endFolderName;
            /**
            //มาถึงตรงนี้คือพบ patient_id และ hn แล้วจะทำการสร้างโฟลเดอร์ตามที่ตกลงเอาไว้และทำการย้ายรูปไปใส่ พร้อม insert ลง Database
            //ชื่อรูป ทาง BPK9 ต้องการให้ตัด HN ออก
                    newFileName = hn+".jpg"; //ใช้เลข HN เป็นชื่อรูป
                    File imedPatImgFolder = new File( imedPatientImageFolder , hn.substring(0,2)+"/"+finalFolderName );
                    if( imedPatImgFolder.exists() == false ){
                        imedPatImgFolder.mkdirs();
                    }
                    //ย้ายรูปไปใส่
                    if( fImage.renameTo( new File(imedPatImgFolder , newFileName )) == true ){
                        successCount++;
                    } else {
                        failCount++;
                    }
                    */
            return finalFolderName;
        } else {
            System.out.println("[FX INTERFACE]    NOT FOUND HN ["+hn+"] IN DATABASE");
        }
        fxDAO = null;
        return null;
    }
};