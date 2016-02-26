package com.iMed.iMedApp.iMedInterfaceFXApp.iMedInterfaceFXVO;

public class FXJasperMappingVO {
    private String fxJasperMappingId;
    private String fxFormId;
    private String jasperPath;
	public String getFxJasperMappingId(){
		return this.fxJasperMappingId;
	}
	public String getFxFormId(){
		return this.fxFormId;
	}
	public String getJasperPath(){
		return this.jasperPath;
	}
	public void setFxJasperMappingId(String fxJasperMappingId){
		this.fxJasperMappingId = fxJasperMappingId;
	}
	public void setFxFormId(String fxFormId){
		this.fxFormId = fxFormId;
	}
	public void setJasperPath(String jasperPath){
		this.jasperPath = jasperPath;
	}

};