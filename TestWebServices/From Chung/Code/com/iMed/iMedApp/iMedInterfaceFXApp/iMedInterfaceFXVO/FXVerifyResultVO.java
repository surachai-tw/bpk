package com.iMed.iMedApp.iMedInterfaceFXApp.iMedInterfaceFXVO;

public class FXVerifyResultVO {
    private String patientId;
    private String visitId;
    private String admitId;

	public String getPatientId(){
		return this.patientId;
	}
	public String getVisitId(){
		return this.visitId;
	}
	public String getAdmitId(){
		return this.admitId;
	}
	public void setPatientId(String patientId){
		this.patientId = patientId;
	}
	public void setVisitId(String visitId){
		this.visitId = visitId;
	}
	public void setAdmitId(String admitId){
		this.admitId = admitId;
	}

};