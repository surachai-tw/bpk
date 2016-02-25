package com.iMed.iMedApp.iMedInterfaceFXApp.iMedInterfaceFXVO;

public class FXFormAndGroupVO {
    private String fxFormAndGroupId;
    private String fxFormGroupId;
    private String fxFormId;
    private String fxFormDescription;

    //เพิ่มเติมสำหรับแสดงผล
    private String fxFormGroupDescription;

	public String getFxFormAndGroupId(){
		return this.fxFormAndGroupId;
	}
	public String getFxFormGroupId(){
		return this.fxFormGroupId;
	}
	public String getFxFormId(){
		return this.fxFormId;
	}
	public String getFxFormDescription(){
		return this.fxFormDescription;
	}
	public void setFxFormAndGroupId(String fxFormAndGroupId){
		this.fxFormAndGroupId = fxFormAndGroupId;
	}
	public void setFxFormGroupId(String fxFormGroupId){
		this.fxFormGroupId = fxFormGroupId;
	}
	public void setFxFormId(String fxFormId){
		this.fxFormId = fxFormId;
	}
	public void setFxFormDescription(String fxFormDescription){
		this.fxFormDescription = fxFormDescription;
	}
	public String getFxFormGroupDescription(){
		return this.fxFormGroupDescription;
	}
	public void setFxFormGroupDescription(String fxFormGroupDescription){
		this.fxFormGroupDescription = fxFormGroupDescription;
	}

};