package com.iMed.iMedApp.iMedInterfaceFXApp;

import java.io.*;
import java.util.*;

import org.jboss.varia.scheduler.Schedulable;

import com.iMed.iMedApp.iMedInterfaceFXApp.ibatis.*;
import com.iMed.iMedCore.utility.*;
import com.iMed.iMedCore.utility.fix.*;
/** ����Ѻ��� Jboss ���¡���ҹ Scheduler Service ���ͷӡ����ҹ�ٻ��й�����ٻ�� iMed
*/
public class FXInterfaceScheduler implements Schedulable {
    /** format �Ţ HN �ͧ����ٻ����Ҩҡ fuji xerox 
       Ẻ������Ţ HN �еç�Ѻ�����㹰ҹ�����Ţͧ iMed  */
    private final String HN_MATCH_FORMAT="0";
    /** format �Ţ HN �ͧ����ٻ����Ҩҡ fuji xerox
      Ẻ����� HN �����Ţ�� (�.�.) 2 ��ѡ ��������Ţ Running �����ѡ���� �� 531 (�դ�� 53 , running = 1) , 53256 (�դ�� 53 , running = 256) */
    private final String HN_YEAR_AND_RUNNING_FORMAT="1";


    /** ��Ҥ���� � imed.properties �������ǡѺ�к� interface */
    private final String IMED_PROP_HN_FORMAT = "FX_HN_DATA_FORMAT";
    private final String IMED_PROP_ENABLED = "FX_INTERFACE_ENABLED";
    private final String IMED_PROP_SHARE_FOLDER = "FX_PATIENT_IMG_SHARE_FOLDER";
    private final String IMED_PROP_FAIL_FOLDER = "FX_PATIENT_IMG_FAIL_FOLDER";
    private final String IMED_PROP_IMED_FOLDER = "FX_PATIENT_IMG_IMED_FOLDER";

    /** method ����Ѻ implement Schedulable �ͧ JBOSS ����������¡��ҹ�� Service �ͧ JBoss �� */
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
    /** =============================== �� implement schedulable �ͧ JBoss ============================*/


    /** ����Ѻ���¡��ҹ ���� import �ٻ�Ҿ���� ����к� iMed */
    public void runImportPatientImage(){
        try{
            //�礡�͹��� Enabled ��������
            if( FixBooleanStatus.TRUE.equals(Utility.getIMedProperties(IMED_PROP_ENABLED))){
                System.out.println("[FX INTERFACE] RUNNING...");
                String shareFolder = Utility.getIMedProperties(IMED_PROP_SHARE_FOLDER);
                String failFolder = Utility.getIMedProperties(IMED_PROP_FAIL_FOLDER);
                String imedFolder = Utility.getIMedProperties(IMED_PROP_IMED_FOLDER);
                //��Ǩ�ͺ����ͷ�� 3 ������������� �ҡ����ͨ����ӵ��
                File fShareFolder = new File(shareFolder);
                if( fShareFolder.exists() && fShareFolder.isDirectory()){
                    File fFailFolder = new File(failFolder);
                    if( fFailFolder.exists() && fFailFolder.isDirectory()){
                        File fIMedFolder = new File(imedFolder);
                        if( fIMedFolder.exists() && fIMedFolder.isDirectory()){
                            File[] arrayFiles = fShareFolder.listFiles();
                            if( arrayFiles == null || arrayFiles.length <= 0 ){
                                //��辺�ٻ� � ����ͧ�����
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
                                //��������ٻ jpg ���� jpeg ��������
                                fName = f.getName().toLowerCase();
                                if( fName.indexOf(".jpg") != -1 || fName.indexOf(".jpeg") != -1){
                                    //�Ѵ�¡�����Ţͧ��������͡��
                                    fNames = fName.substring( 0 , fName.indexOf(".")).split("_");
                                    hn = fNames[0];
                                    //�ŧ HN ���ç�Ѻ format �� Database
                                    hn = convertFormatHN2iMed(hn);
                                    if( hn != null ){
                                        fileIMedPathName = getIMedPatientPictureFolderPath( hn );
                                        if( fileIMedPathName != null ){
                                            fileIMedPathName = imedFolder + File.separator + fileIMedPathName;
                                            File folder = new File(fileIMedPathName);
                                            if( folder.exists() == false ){
                                                folder.mkdirs(); //�ҡ�ѧ�����������ҧ�����
                                            }
                                            File fOnImed = new File( fileIMedPathName , f.getName());
                                            //�ӡ���������
                                            if( f.renameTo( fOnImed) == true ){
                                                System.out.println("[FX INTERFACE] IMPORT FILE ["+f.getName()+"] TO ["+fOnImed.getAbsolutePath()+"]");
                                            } else {
                                                //�Դ��ͼԴ��Ҵ ����������� fail
                                                System.out.println("[FX INTERFACE] FAIL 2 IMPORT FILE "+fOnImed.getAbsolutePath()+"]");
                                                f.renameTo( new File( fFailFolder , f.getName()));
                                            }
                                        } else {
                                            System.out.println("[FX INTERFACE] CAN't GET IMED PATH NAME FOR HN "+hn);
                                            //����� fail folder
                                            f.renameTo( new File( fFailFolder , f.getName()));
                                        }
                                    } else {
                                        //�ʴ���� format ���١��ͧ
                                        System.out.println("[FX INTERFACE] CAN't CONVERT HN FORMATTED FOR HN "+hn+" PLEASE CHECK SETTING");
                                        //����� fail folder
                                        f.renameTo( new File( fFailFolder , f.getName()));
                                    }
                                } else {
                                    System.out.println("[FX INTERFACE] FOUND NO jpeg Files move 2 fail folder");
                                    if( f.isFile() ){
                                        //����� fail folder
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


    /** method ����Ѻ�ŧ HN �ͧ�ٻ�Ҿ �� format �������� Database 
    * ���͡ format �ҡ���� iMed.properties
    */
    private String convertFormatHN2iMed(String hnUnformatted) {
        if( HN_MATCH_FORMAT.equals(Utility.getIMedProperties(IMED_PROP_HN_FORMAT))){
            //Ẻ������Ţ�ç�������� ����ͧ������ ����Ҩӹǹ��ѡ�ç������
            if( hnUnformatted != null && hnUnformatted.length() == 12 ){ //�е�ͧ��� 12 ��ѡ
                return hnUnformatted;
            } else {
                return null;
            }
        } else if( HN_YEAR_AND_RUNNING_FORMAT.equals(Utility.getIMedProperties(IMED_PROP_HN_FORMAT))){
            //Ẻ��������Ţ�� 2 ��ѡ ������� running �е�ͧ�ŧ���ٻẺ��� iMed ��� �� 2 ��ѡ running 10 ��ѡ (��� 0 ��˹���Ţ running)
            if( hnUnformatted != null && hnUnformatted.length() >= 3 ){ //���ҧ��Өе�ͧ���� 3 ��ѡ
                String year = hnUnformatted.substring(0 , 2);
                String running = hnUnformatted.substring( 2 , hnUnformatted.length());
                //����Ţ running ���ú 10 ��ѡ
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
    /** method ����Ѻ ��� HN �ҡ��������ٻ �����ҧ�� path ���������� iMed 
    * �����˵� �е�Ǩ�ͺ��͹��� HN ���������㹰ҹ�������������
      fileName ��� ��������ٻ��ҹ�� (����ͧ�� path)
      @return null �ҡ�Դ��ͼԴ��Ҵ
      @return String �ͧ path �������ö��仵�͡Ѻ iMed Folder �¨��ն֧ folder ����ͧ���������������ҹ��
    */
    private String getIMedPatientPictureFolderPath(String hn ) throws Exception {
        //�礡�͹��� HN �������������㹰ҹ�������������
        FXDAO fxDAO = new FXDAO();
        String patientId = fxDAO.checkHN( hn );
        if( patientId != null ){
            //�ʴ������㹰ҹ������
            String hnRunningNumber = hn.substring( hn.length()-7 , hn.length()); //����Ţ running 7 ��ѡ�ͧ HN ��
            long longHn = Long.parseLong(hnRunningNumber);
            long hnMod = longHn%100;
            long folderMod = longHn-hnMod; //����Ţ HN ���ź������ 100 �͡
            long endFolderMod = 0;
            if( hnMod >= 1 ){
              //�ó���� 100 ŧ���ŧ��� (�����
              folderMod++; //+1 ����
              endFolderMod = folderMod+99;
            } else {
              endFolderMod = folderMod;
              folderMod = folderMod-99;
            }
            String startFolderName = String.valueOf(folderMod);
            String endFolderName = String.valueOf(endFolderMod);
            //��� 0 ��˹�����ú 7 ��ѡ
            for( ; startFolderName.length() < 7 ; ){
              startFolderName = "0"+startFolderName;
            }
            for( ; endFolderName.length() < 7 ; ){
              endFolderName = "0"+endFolderName;
            }
            String finalFolderName = hn.substring(0,2)+File.separator+startFolderName+"-"+endFolderName;
            /**
            //�Ҷ֧�ç����;� patient_id ��� hn ���Ǩзӡ�����ҧ����������赡ŧ��������зӡ�������ٻ���� ����� insert ŧ Database
            //�����ٻ �ҧ BPK9 ��ͧ������Ѵ HN �͡
                    newFileName = hn+".jpg"; //���Ţ HN �繪����ٻ
                    File imedPatImgFolder = new File( imedPatientImageFolder , hn.substring(0,2)+"/"+finalFolderName );
                    if( imedPatImgFolder.exists() == false ){
                        imedPatImgFolder.mkdirs();
                    }
                    //�����ٻ����
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