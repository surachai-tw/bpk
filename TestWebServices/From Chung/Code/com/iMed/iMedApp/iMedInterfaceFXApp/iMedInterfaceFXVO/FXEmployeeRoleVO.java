package com.iMed.iMedApp.iMedInterfaceFXApp.iMedInterfaceFXVO;

/** สำหรับเก็บสิทธิการใช้งานของผู้ใช้ ว่าสามารถพิมพ์เอกสารอะไรได้บ้าง */
public class FXEmployeeRoleVO {
    private String fxEmployeeRoleId;
    private String employeeId;
    private String employeeName;
    private String isScanAllow;
    private String isFormPrintAllow;
    private String fxFormIdAllow;
    private String isFaxclaimAllow;

	public String getEmployeeId(){
		return this.employeeId;
	}
	public String getEmployeeName(){
		return this.employeeName;
	}
	public String getIsScanAllow(){
		return this.isScanAllow;
	}
	public String getIsFormPrintAllow(){
		return this.isFormPrintAllow;
	}
	public String getFxFormIdAllow(){
		return this.fxFormIdAllow;
	}
	public String getIsFaxclaimAllow(){
		return this.isFaxclaimAllow;
	}
	public void setEmployeeId(String employeeId){
		this.employeeId = employeeId;
	}
	public void setEmployeeName(String employeeName){
		this.employeeName = employeeName;
	}
	public void setIsScanAllow(String isScanAllow){
		this.isScanAllow = isScanAllow;
	}
	public void setIsFormPrintAllow(String isFormPrintAllow){
		this.isFormPrintAllow = isFormPrintAllow;
	}
	public void setFxFormIdAllow(String fxFormIdAllow){
		this.fxFormIdAllow = fxFormIdAllow;
	}
	public void setIsFaxclaimAllow(String isFaxclaimAllow){
		this.isFaxclaimAllow = isFaxclaimAllow;
	}
	public String getFxEmployeeRoleId(){
		return this.fxEmployeeRoleId;
	}
	public void setFxEmployeeRoleId(String fxEmployeeRoleId){
		this.fxEmployeeRoleId = fxEmployeeRoleId;
	}

};