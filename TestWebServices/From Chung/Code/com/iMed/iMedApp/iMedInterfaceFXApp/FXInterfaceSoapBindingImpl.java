/**
 * FXInterfaceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.iMed.iMedApp.iMedInterfaceFXApp;

import java.io.*;
import java.util.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

import com.iMed.iMedApp.iMedInterfaceFXApp.ibatis.*;
import com.iMed.iMedApp.iMedInterfaceFXApp.iMedInterfaceFXVO.*;

import com.iMed.iMedCore.utility.*;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.*;
/**
// ใช้ไม่ได้เนื่องจากต้องการ JDK 1.5 ขึ้นไป
import ca.uhn.hl7v2.model.*;
import ca.uhn.hl7v2.model.v26.datatype.ST;
import ca.uhn.hl7v2.model.v26.message.*;
import ca.uhn.hl7v2.model.v26.segment.*;
import ca.uhn.hl7v2.parser.*;
*/
public class FXInterfaceSoapBindingImpl implements com.iMed.iMedApp.iMedInterfaceFXApp.FXInterface{

    //ตัวแปรสำหรับเก็บ KEY ที่ Login เข้ามา เพื่อยันยันว่าได้ทำการ Login เข้ามาจริง ๆ
    //จะเก็บ key เอาไว้เพียงแค่ 1000 key เท่านั้น หากมากกว่านี้จะลบรายการเก่าทิ้ง
    private static List _listLoginKey;

    /** ค่าคงที่สำหรับบอกสถานะการทำงาน */
    public static final String SUCCESS = "1";
    public static final String FAIL = "0";

    public String fxLogin(String username , String password) throws java.rmi.RemoteException {
        String resultString = null;
        try{
            FXDAO dao = new FXDAO();
            //บันทึกการเรียกใช้งาน WebService เอาไว้เป็น Log
            WSLogVO logVO = new WSLogVO();
            logVO.setWsLogId(String.valueOf(System.currentTimeMillis()));
            logVO.setWsTypeId("InterfaceFX"); //บอกว่าเป็นการ interface กับ Fuji Xerox
            logVO.setMethodName("fxLogin");
            logVO.setParameterSnd(username+","+password);

            FXEmployeeRoleVO roleVO = dao.loginViaWebService( username , password );
            if( roleVO != null ){
                //Generate Key สำหรับใช้ยืนยันว่า Login ผ่านแล้ว (method อื่นจะเรียกใช้ได้ ต้องมี key เก็บไว้ในหน่วยความจำก่อน)
                String newKey = username+String.valueOf(System.currentTimeMillis());
                //ถ้ามีมากกว่า 1000 key แล้ว จะลบรายการแรกสุดทิ้ง
                if( _listLoginKey == null ){
                    _listLoginKey = new ArrayList();
                }
                if( _listLoginKey.size() >= 1000 ){
                    _listLoginKey.remove(0);
                }
                _listLoginKey.add(newKey);
                StringBuffer result = new StringBuffer();
                //ประกอบข้อมูลจาก VO เป็นข้อความที่จะ return กลับไป
                result.append(newKey);
                result.append("#");
                result.append(roleVO.getIsScanAllow()).append(roleVO.getIsFormPrintAllow()).append(roleVO.getIsFaxclaimAllow());
                result.append("#");
                result.append(roleVO.getFxFormIdAllow());
                logVO.setResult("SUCCESS,AND Return ["+result+"]");
                resultString = result.toString();
            } else {
                logVO.setResult("FAIL");
                resultString = FAIL;
            }
            dao.saveWebServiceLog(logVO);
        } catch (Exception ex){
            ex.printStackTrace();
            resultString = FAIL;
        }
        return resultString;
    }

    public String fxPrintForm(String key , String formId , String hn , String printerName) throws java.rmi.RemoteException {
        try{
            //บันทึก Login การเรียกเอาไว้
            FXDAO dao = new FXDAO();
            WSLogVO logVO = new WSLogVO();
            logVO.setWsLogId(String.valueOf(System.currentTimeMillis()));
            logVO.setWsTypeId("InterfaceFX"); //บอกว่าเป็นการ interface กับ Fuji Xerox
            logVO.setMethodName("fxPrintForm");
            logVO.setParameterSnd(key+","+formId+","+hn+","+printerName);

            //ตรวจสอบ Key ก่อนว่าถูกต้องหรือไม่
            if( _listLoginKey == null || !_listLoginKey.contains(key)){
                logVO.setResult("FAIL,Key Verify not valid "+key);
                dao.saveWebServiceLog(logVO);
                return FAIL;
            }
            //เอา formId ไปหา path Jasper และ เอา printerName ไปหา ชื่อที่ต้องสั่งพิมพ์
            String jasperPath = dao.getJasperPathByFXFormId( formId );
            if( Utility.isNull(jasperPath)){
                logVO.setResult("FAIL,Can't find JasperPath by FormID "+formId);
                dao.saveWebServiceLog(logVO);
                return FAIL;
            }
            String serverPrinterName = dao.getServerPrinterNameByFXPrinterName( printerName );
            if( Utility.isNull(serverPrinterName)){
                logVO.setResult("FAIL,Can't find Mapping ServerPrinterName By PrinterName "+printerName);
                dao.saveWebServiceLog(logVO);
                return FAIL;
            }
            JasperPrint jasperPrint = new JasperPrint();
            File reportFile = new File(System.getProperty("user.home"), jasperPath );//exmaple = jasperPath = "/iMedConf/reports/AdmitAllow.jasper"
            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map parameters = new HashMap();
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            //Lookup Printer ที่ Map กับ Server
            boolean foundPrinter = false;
            PrintService printService[] = PrintServiceLookup.lookupPrintServices( null, null );
            for( int i = 0; i < printService.length; i++ ){
               if( printService[i].getName().equals(serverPrinterName) ) {
                  if( jasperPrint != null ){
                      foundPrinter = true;
                     JasperPrintManager.printReport( printService[i],jasperPrint, false );
                  }
                  break;
               }
            }
            if( foundPrinter == true ){
                logVO.setResult("SUCCESS,Print to Printer "+serverPrinterName);
                dao.saveWebServiceLog(logVO);
            } else {
                logVO.setResult("FAIL,Can't find ServerPrinterName "+serverPrinterName);
                dao.saveWebServiceLog(logVO);
                return FAIL;
            }
        } catch (Exception ex){
            ex.printStackTrace();
            return FAIL;
        }
        return SUCCESS;
    }

    public String fxVerifyHnVnAn(String key , String hn , String vn , String an) throws java.rmi.RemoteException {
        try{
            //บันทึก Login การเรียกเอาไว้
            FXDAO dao = new FXDAO();
            WSLogVO logVO = new WSLogVO();
            logVO.setWsLogId(String.valueOf(System.currentTimeMillis()));
            logVO.setWsTypeId("InterfaceFX"); //บอกว่าเป็นการ interface กับ Fuji Xerox
            logVO.setMethodName("fxVerifyHnVnAn");
            logVO.setParameterSnd(key+","+hn+","+vn+","+an);

            //ตรวจสอบ key ก่อนว่าถูกต้องหรือไม่
            if( _listLoginKey == null || !_listLoginKey.contains(key)){
                logVO.setResult("FAIL,Key Verify not valid "+key);
                dao.saveWebServiceLog(logVO);
                return FAIL;
            }
            //เช็คก่อนว่าส่งค่าใดค่าหนึ่งมา
            if( Utility.isNotNull(hn) || Utility.isNotNull(vn) || Utility.isNotNull(an)){
                boolean hnPass = false;
                boolean vnPass = false;
                boolean anPass = false;
                if( Utility.isNotNull(hn) ){
                    String patientId = dao.verifyHN(hn);
                    if( Utility.isNotNull(patientId)){
                        hnPass = true;
                    }
                } else { //ไม่ส่ง hn มา ให้ถือว่าผ่านไป
                    hnPass = true;
                }
                if( Utility.isNotNull(vn) ){
                    FXVerifyResultVO resultVO = dao.verifyVN(vn);
                    if( resultVO != null ){
                        if( Utility.isNotNull(resultVO.getVisitId())) {
                            vnPass = true;
                        }
                    }
                } else { //ไม่ส่ง vn มา ให้ถือว่าผ่าน
                    vnPass = true;
                }
                if( Utility.isNotNull(an)){
                    FXVerifyResultVO resultVO = dao.verifyAN(an);
                    if( resultVO != null ){
                        if( Utility.isNotNull(resultVO.getAdmitId())) {
                            anPass = true;
                        }
                    }
                } else {
                    anPass = true;
                }
                if( hnPass == false || vnPass == false || anPass == false ){
                    //แสดงว่าไม่ถูกต้อง
                    logVO.setResult("FAIL,parameter send is not valid");
                    dao.saveWebServiceLog(logVO);
                    return FAIL;
                } else {
                    logVO.setResult("SUCCESS,found data");
                    dao.saveWebServiceLog(logVO); 
                }
            } else {
                //แสดงว่าไม่ส่งค่าไหนมาเลยจะ return error กลับไป
                logVO.setResult("FAIL,no parameter found");
                dao.saveWebServiceLog(logVO);
                return FAIL;
            }
        } catch (Exception ex){
            ex.printStackTrace();
            return FAIL;
        }
        return SUCCESS;
    }

    /** สำหรับ Query รายการ Form ทั้งหมดที่อนุญาตให้พิมพ์ผ่านทาง FX Interface ได้ ใช้มาตรฐานข้อมูล HL7 Query QBP/RDY ในการติดต่อข้อมูล */
    public String fxListForm(String hl7message) throws java.rmi.RemoteException {
        System.out.println("HL7MESSAGE RECEIVE =============================");
        System.out.println(hl7message);
        String hl7messageReturn = "";
        try{
            //parse ข้อมูล HL7 ที่ส่งมา
            /**
            // Library ของ Hapi ใช้ไม่ได้ เนื่องจากต้องการ JDK 1.5 ขึ้นไป 
            boolean isValid = false;
            try{ 
                QBP_Q15 qbp_q15_message = new QBP_Q15();
                qbp_q15_message.parse(hl7message);
                isValid = true;
            } catch (Exception exHl7){
                exHl7.printStackTrace();
            }
            QPD qpdSegment = qbp_q15_message.getQPD();
            //ถ้า QueryID = Z.01 จะทำการ Query ข้อมูล Form มา
            List listFXFormAndGroupVO = null;
            String status = null;
            if( "Z.01" == qpdSegment.getMessageQueryName().getCwe1_Identifier().getValue() ){
                //ดึงข้อมูลรายการ Form And Group ทั้งหมดมา
                FXDAO dao = new FXDAO();
                listFXFormAndGroupVO = dao.listFXFormAndGroup();
                if( Utility.isNotNull(listFXFormAndGroupVO)){
                    ; //ไม่ทำอะไร
                } else {
                    status = "NOT_FOUND"; //ไม่พบข้อมูล
                }
            } else {
                // ถ้า ID Query ไม่ถูกก็บอกไปว่า reject
                isValid = false;
            }

            //Message ตอบกลับ RDY_K15
            RDY_K15 returnMessage = new RDY_K15();
            MSH mshRdy = returnMessage.getMSH();
            mshRdy.getFieldSeparator().setValue("|");
            mshRdy.getEncodingCharacters().setValue("^~\\&");
            mshRdy.getSendingApplication().getNamespaceID().setValue("iMed");
            mshRdy.getSequenceNumber().setValue("1");
            mshRdy.getMessageType().getMsg1_MessageCode().setValue("RDY");
            mshRdy.getMessageType().getMsg2_TriggerEvent().setValue("K15");
            mshRdy.getMessageType().getMsg3_MessageStructure().setValue("RDY K15");
            mshRdy.getVersionID().getVersionID().setValue("2.6");

            QAK qakRdy = returnMessage.getQAK();
            qakRdy.getQak1_QueryTag().setValue(qpdSegment.getQpd2_QueryTag().getValue()); //queryTag ต้องตรงกับที่ส่งมาจาก client
            qakRdy.getQak3_MessageQueryName().getCwe1_Identifier().setValue("Z.01"); // มาจากตาราง HL7 0471 - QueryName
            qakRdy.getQak3_MessageQueryName().getCwe2_Text().setValue("List FX Interface FORM");
            qakRdy.getQak3_MessageQueryName().getCwe3_NameOfCodingSystem().setValue("iMed");
            if( isValid == false ){
                qakRdy.getQak2_QueryResponseStatus().setValue("AR"); //เอามาจาก ตาราง HL7 0208 - Query Response status , มี 4 ค่าคือ OK , NF , AE , AR
            } else {
                //ตรวจสอบว่าเจอข้อมูลไหม
                if( "NOT_FOUND".equals(status)){
                    qakRdy.getQak2_QueryResponseStatus().setValue("NF"); //กำหนดสถานะเป็น NF = NO ERROR , NOT FOUND
                } else {
                    //วนลูปสร้างข้อมูล DSP
                    FXFormAndGroupVO formAndGroupVO = null;
                    for(int i=0 ; i < listFXFormAndGroupVO.size() ; i++){
                        formAndGroupVO = (FXFormAndGroupVO)listFXFormAndGroupVO.get(i);
                        DSP dspMes = returnMessage.insertDSP(i);
                        dspMes.getDsp3_DataLine().setValue( formAndGroupVO.getFXFormGroupId()+","+formAndGroupVO.getFxFormGroupDescription()+","+formAndGroupVO.getFxFormId()+","+formAndGroupVO.getFxFormDescription());
                    }
                    qakRdy.getQak4_HitCountTotal().setValue(String.valueOf(listFXFormAndGroupVO.size()));
                }
            }
            System.out.println("RETURN MESSAGE =================");
            hl7messageReturn = parser.encode(returnMessage);
            String[] lineData = hl7messageReturn.split("\r");
            for(int i=0 ; i < lineData.length ; i++){
              System.out.println(lineData[i]);
            }
            */

            //ใช้การ substring แทน
            /**
            SEND MESSAGE ========================
            MSH|^~\&|Fuji Xerox||||||QBP^Q15^QBP Q15|||2.6|1
            QPD|Z.01^List FX Interface FORM^FX|FX_1|Key2LoadData
            RETURN MESSAGE =================
            MSH|^~\&|iMed||||||RDY^K15^RDY K15|||2.6|1
            QAK|FX_1|OK|Z.01^List FX Interface FORM^iMed|2
            DSP|||FORM GRP 1,OPD_CARD,บัตรประจำตัวผู้ป่วย
            DSP|||FORM GRP 1,PATIENT_CARD,บัตรประจำตัวผู้ป่วย
            */
            //ตัดบรรทัดก่อน
            boolean isValid = false;
            String status = null; //สถานะ การทำงาน
            System.out.println("r = " + hl7message.indexOf("\r"));
            System.out.println("n = " + hl7message.indexOf("\n"));
            System.out.println("rn = " + hl7message.indexOf("\r\n"));
            //String[] lineHL7s = hl7message.split("\r");
            String[] lineHL7s = hl7message.split("\n"); //\r ของ HL7message เมื่อส่งมาจะถูกแปลงเป็น \n
            //ดึงข้อมูลบรรทัด QPD ออกมา
            String lineHL7 = null;
            String[] pipeArray = null;
            String[] helmetArray = null; //array ของ ^
            List listFXFormAndGroupVO = null;
            FXFormAndGroupVO formAndGroupVO = null;

            StringBuffer hl7rdyK15 = new StringBuffer();
            hl7rdyK15.append("MSH|^~\\&|iMed||||||RDY^K15^RDY K15|||2.6|1\r");
            StringBuffer hl7qakSegment = new StringBuffer();
            hl7qakSegment.append("QAK|");
            StringBuffer hl7dspSegment = new StringBuffer();
            for(int i=0 ; i < lineHL7s.length ; i++){
                lineHL7 = lineHL7s[i];
                System.out.println("LINE "+(i+1)+" = "+lineHL7);
                if( lineHL7.indexOf("QPD") != -1 ){
                    System.out.println("FOUND QPD Segment DATA....IN LINE "+lineHL7);
                    //เอา identifier ออกมา
                    pipeArray = lineHL7.split("\\|");
                    if( pipeArray.length < 4 ){
                        System.out.println("PIPE SPLIT IS NOT CORRECT FOUND "+pipeArray.length);
                        break; //ออกลูปไปเลย ไม่ต้องทำต่อ
                    }
                    //เช็คว่า key ที่ส่งมา คือ admin ใช่หรือไม่ หากไม่ใช่จะ ข้ามไปเลย
                    if( "admin".equals(pipeArray[3]) == false ){
                        System.out.println("KEY IS NOT CORRENT NEED 'admin'");
                        break;
                    }

                    //Query Tag มาจาก QPD ตัวที่ 3 ใส่ลงใน QAK อันที่ 2
                    hl7qakSegment.append(pipeArray[2]).append("|");

                    helmetArray = pipeArray[1].split("\\^");
                    if( helmetArray.length < 3 ){
                        System.out.println("^ SPLIT IS NOT CORRECT , FOUND "+helmetArray);
                        break;//จำนวนไม่ถูกต้อง
                    }
                    String identifierId = helmetArray[0];
                    if( "Z.01".equals(identifierId)){
                        isValid = true; //ข้อมูลที่ส่งมาถูกต้อง
                        //ทำการ Query ข้อมูล
                        FXDAO dao = new FXDAO();
                        listFXFormAndGroupVO = dao.listFXFormAndGroup();
                        if( Utility.isNotNull(listFXFormAndGroupVO)){
                            status = "SUCCESS";
                            //ใส่สถานะเป็น OK ใน QAK Segment
                            hl7qakSegment.append("OK|");

                            //วนลูปสร้าง DSP Segment เพื่อส่งข้อมูลออกไป
                            for(int a=0 ; a < listFXFormAndGroupVO.size() ; a++){
                                formAndGroupVO = (FXFormAndGroupVO)listFXFormAndGroupVO.get(a);
                                hl7dspSegment.append("DSP|||");
                                hl7dspSegment.append(formAndGroupVO.getFxFormGroupId()).append(",");
                                hl7dspSegment.append(formAndGroupVO.getFxFormGroupDescription()).append(",");
                                hl7dspSegment.append(formAndGroupVO.getFxFormId()).append(",");
                                hl7dspSegment.append(formAndGroupVO.getFxFormDescription());
                                if( a != ( listFXFormAndGroupVO.size() -1 )){
                                    hl7dspSegment.append("\r");
                                }
                            }
                        } else {
                            //ใส่สถานะเป็น NF (Not Found) ใน QAK Segment
                            hl7qakSegment.append("NF|");
                            status = "NOT_FOUND"; //ไม่พบข้อมูล
                        }
                        //ใส่ Query Name กลับเข้าไปใน QAK
                        hl7qakSegment.append(helmetArray[0]).append("^").append(helmetArray[1]).append("^iMed|");
                        if( "SUCCESS".equals(status)) {
                            //ใส่จำนวนที่พบใน QAK
                            hl7qakSegment.append(String.valueOf(listFXFormAndGroupVO.size())).append("\r");
                        } else {
                            hl7qakSegment.append("0\r"); //ไม่พบใส่ 0
                        }
                    } else {
                        //ใส่ ID ของ query identifier มาไม่ถูกต้อง จะ reject กลับไป
                        System.out.println("Query ID IS "+identifierId);
                        hl7qakSegment.append("AR||0\r");
                    }
                }
            }
            if( isValid == false ){
                System.out.println("DATA INVALID!");
                //ข้อมูลไม่ถูกต้องจะสร้าง QAK กลับไปว่าไม่ถูกต้อง
                hl7qakSegment = new StringBuffer();
                hl7qakSegment.append("QAK||AR||0\r");
            }
            //ทำการประกอบข้อมูล
            hl7rdyK15.append(hl7qakSegment.toString());
            hl7rdyK15.append(hl7dspSegment.toString());
            hl7messageReturn = hl7rdyK15.toString();
            System.out.println("RETURN MESSAGE =================");
            String[] lineData = hl7messageReturn.split("\r");
            for(int i=0 ; i < lineData.length ; i++){
              System.out.println(lineData[i]);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return hl7messageReturn;
    }

}
