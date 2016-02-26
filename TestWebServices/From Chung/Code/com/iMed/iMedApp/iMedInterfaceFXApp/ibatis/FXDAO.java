package com.iMed.iMedApp.iMedInterfaceFXApp.ibatis;

import com.iMed.iMedApp.iMedInterfaceFXApp.iMedInterfaceFXVO.*;
import com.ibatis.sqlmap.client.SqlMapClient;
import java.util.*;
import java.sql.*;

import com.iMed.iMedCore.utility.*;

public class FXDAO {
  private SqlMapClient sqlMap;
  private Hashtable result;
  private Map params;

  public FXDAO()
  {
    sqlMap = FXSqlMapConfig.getSqlMapInstance();
    result = new Hashtable();
    params = new HashMap();
  }


  public FXEmployeeRoleVO loginViaWebService(String username, String password) throws SQLException {
    FXEmployeeRoleVO roleVO = null;
    try {
      sqlMap.startTransaction();
      FXLoginVO loginVO = new FXLoginVO();
      loginVO.setUsername( username );
      //Password ที่ส่งมาต้องแปลงเป็น MD5 ก่อน
      String passwordMD5 = Utility.toMD5(password);
      loginVO.setPassword(passwordMD5);
      String employeeId = (String)sqlMap.queryForObject("loginViaWebService", loginVO );
      if( Utility.isNotNull(employeeId)){
          //ดึงข้อมูลสิทธิของผู้ใช้งานออกมาจากตาราง fx_employee_role
          roleVO = (FXEmployeeRoleVO)sqlMap.queryForObject("getFXEmployeeRoleVOByEmployeeId" , employeeId );
      }
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
    return roleVO;
  }

  public void saveWebServiceLog(WSLogVO logVO) throws SQLException {
    try {
      sqlMap.startTransaction();
      sqlMap.insert("saveWebServiceLog" , logVO );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
  }

  public String getJasperPathByFXFormId(String formId) throws SQLException {
    String jasperPath = null;
    try {
      sqlMap.startTransaction();
      jasperPath = (String)sqlMap.queryForObject("getJasperPathByFXFormId" , formId );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
    return jasperPath;
  }

  public String getServerPrinterNameByFXPrinterName(String fxPrinterName) throws SQLException {
    String serverPrinterName = null;
    try {
      sqlMap.startTransaction();
      serverPrinterName = (String)sqlMap.queryForObject("getServerPrinterNameByFXPrinterName" , fxPrinterName );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
    return serverPrinterName;
  }
   /** @return patient_id */
  public String verifyHN(String hn) throws SQLException {
    String patientId = null;
    try {
      FXVerifyVO verifyVO = new FXVerifyVO();
      verifyVO.setHn(hn);
      sqlMap.startTransaction();
      patientId = (String)sqlMap.queryForObject("verifyHN" , verifyVO );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
    return patientId;
  }

  public FXVerifyResultVO verifyVN(String vn) throws SQLException {
    FXVerifyResultVO resultVO = null;
    try {
      FXVerifyVO verifyVO = new FXVerifyVO();
      verifyVO.setVn(vn);
      sqlMap.startTransaction();
      resultVO = (FXVerifyResultVO)sqlMap.queryForObject("verifyVN" , verifyVO );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
    return resultVO;
  }

  public FXVerifyResultVO verifyAN(String an) throws SQLException {
    FXVerifyResultVO resultVO = null;
    try {
      FXVerifyVO verifyVO = new FXVerifyVO();
      verifyVO.setAn(an);
      sqlMap.startTransaction();
      resultVO = (FXVerifyResultVO)sqlMap.queryForObject("verifyAN" , verifyVO );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
    return resultVO;
  }

  public List listFXJasperMapping() throws SQLException {
      List listFXJasperMappingVO = null;
      try{
          sqlMap.startTransaction();
          listFXJasperMappingVO = (List)sqlMap.queryForList("listFXJasperMapping");
          sqlMap.commitTransaction();
      } finally {
          sqlMap.endTransaction();
      }
      return listFXJasperMappingVO;
  }

  public void addFXJasperMapping(FXJasperMappingVO fxJasperMappingVO) throws SQLException {
    try{
      sqlMap.startTransaction();
      sqlMap.insert("addFXJasperMapping" , fxJasperMappingVO );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
  }

  public void removeFXJasperMapping(String fxJasperMappingId) throws SQLException {
    try{
      sqlMap.startTransaction();
      sqlMap.delete("removeFXJasperMapping" , fxJasperMappingId );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
  }

  public List listFXPrinterMapping() throws SQLException {
    List listFXPrinterMappingVO = null;
    try{
      sqlMap.startTransaction();
      listFXPrinterMappingVO = (List)sqlMap.queryForList("listFXPrinterMapping" );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
    return listFXPrinterMappingVO;
  }

  public void addFXPrinterMapping(FXPrinterMappingVO fxPrinterMappingVO) throws SQLException {
    try{
      sqlMap.startTransaction();
      sqlMap.insert("addFXPrinterMapping" , fxPrinterMappingVO );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
  }

  public void removeFXPrinterMapping(String fxPrinterMappingId) throws SQLException {
    try{
      sqlMap.startTransaction();
      sqlMap.delete("removeFXPrinterMapping" , fxPrinterMappingId );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
  }

  public void addFXFormGroup(FXFormGroupVO fxFormGroupVO) throws SQLException {
    try{
      sqlMap.startTransaction();
      sqlMap.insert("addFXFormGroup" , fxFormGroupVO );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
  }

  public void removeFXFormGroup(String fxFormGroupId) throws SQLException {
    try{
      sqlMap.startTransaction();
      sqlMap.delete("removeFXFormGroup" , fxFormGroupId );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
  }

  public void removeFXFormAndGroupByFormGroupId(String fxFormGroupId) throws SQLException {
    try{
      sqlMap.startTransaction();
      sqlMap.delete("removeFXFormAndGroupByFormGroupId" , fxFormGroupId );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
  }

  public List listFXFormGroup() throws SQLException {
    List listFXFormGroupVO = null;
    try{
      sqlMap.startTransaction();
      listFXFormGroupVO = (List)sqlMap.queryForList("listFXFormGroup" );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
    return listFXFormGroupVO;
  }

  public List listFXFormAndGroup() throws SQLException {
    List listFXFormAndGroupVO = null;
    try{
      sqlMap.startTransaction();
      listFXFormAndGroupVO = (List)sqlMap.queryForList("listFXFormAndGroup" );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
    return listFXFormAndGroupVO;
  }

  public void addFXFormAndGroup(FXFormAndGroupVO fxFormAndGroupVO) throws SQLException {
    try{
      sqlMap.startTransaction();
      sqlMap.insert("addFXFormAndGroup" , fxFormAndGroupVO );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
  }

  public void removeFXFormAndGroup(String fxFormAndGroupId) throws SQLException {
    try{
      sqlMap.startTransaction();
      sqlMap.delete("removeFXFormAndGroup" , fxFormAndGroupId );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
  }

  public List listFXEmployeeRole() throws SQLException {
    List listFXEmployeeRoleVO = null;
    try{
      sqlMap.startTransaction();
      listFXEmployeeRoleVO = (List)sqlMap.queryForList("listFXEmployeeRole" );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
    return listFXEmployeeRoleVO;
  }

  public List listAllEmployee() throws SQLException {
    List listFXEmployeeRoleVO = null;
    try{
      sqlMap.startTransaction();
      listFXEmployeeRoleVO = (List)sqlMap.queryForList("listAllEmployee" );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
    return listFXEmployeeRoleVO;
  }

  public void addFXEmployeeRole(FXEmployeeRoleVO fxEmployeeRoleVO) throws SQLException {
    try{
      sqlMap.startTransaction();
      sqlMap.insert("addFXEmployeeRole" , fxEmployeeRoleVO );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
  }

  public void editFXEmployeeRole(FXEmployeeRoleVO fxEmployeeRoleVO) throws SQLException {
    try{
      sqlMap.startTransaction();
      sqlMap.update("editFXEmployeeRole" , fxEmployeeRoleVO );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
  }

  public void removeFXEmployeeRole(String fxEmployeeRoleId) throws SQLException {
    try{
      sqlMap.startTransaction();
      sqlMap.delete("removeFXEmployeeRole" , fxEmployeeRoleId );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
  }
  /** ทำการตรวจสอบว่า HN ที่ส่งมามีในระบบหรือไม่ , ปล HN ที่ส่งมาต้องแปลง format ให้อยู่ในรูปแบบของ iMed เรียบร้อยแล้ว 
  @return patientId หากเจอข้อมูล
  */
  public String checkHN(String hn) throws SQLException {
    String patientId = null;
    try {
      FXVerifyVO verifyVO = new FXVerifyVO();
      sqlMap.startTransaction();
      patientId = (String)sqlMap.queryForObject("checkHN" , hn );
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
    return patientId;
  }


}