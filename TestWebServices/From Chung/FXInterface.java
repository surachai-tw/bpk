package com.iMed.iMedApp.iMedInterfaceFXApp;
/** Main Class สำหรับให้ Axis Generate Class สำหรับทำ WebService ออกมาให้ */

public interface FXInterface {
    public String fxLogin(String username , String password);

    public String fxPrintForm(String key , String formId , String hn , String printerName);

    public String fxVerifyHnVnAn(String key , String hn , String vn , String an);

    public String fxListForm(String hl7Message);
};