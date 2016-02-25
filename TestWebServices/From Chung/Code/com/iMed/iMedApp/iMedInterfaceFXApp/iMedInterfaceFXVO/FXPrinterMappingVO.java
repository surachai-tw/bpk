package com.iMed.iMedApp.iMedInterfaceFXApp.iMedInterfaceFXVO;

public class FXPrinterMappingVO {
    private String fxPrinterMappingId;
    private String fxPrinterName;
    private String serverPrinterName;

	public String getFxPrinterMappingId(){
		return this.fxPrinterMappingId;
	}
	public String getFxPrinterName(){
		return this.fxPrinterName;
	}
	public String getServerPrinterName(){
		return this.serverPrinterName;
	}
	public void setFxPrinterMappingId(String fxPrinterMappingId){
		this.fxPrinterMappingId = fxPrinterMappingId;
	}
	public void setFxPrinterName(String fxPrinterName){
		this.fxPrinterName = fxPrinterName;
	}
	public void setServerPrinterName(String serverPrinterName){
		this.serverPrinterName = serverPrinterName;
	}

};