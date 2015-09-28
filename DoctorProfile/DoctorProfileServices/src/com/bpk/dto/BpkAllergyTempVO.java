package com.bpk.dto;

import java.io.Serializable;

import com.bpk.utility.BpkUtility;

@SuppressWarnings("serial")
public class BpkAllergyTempVO implements Serializable, JSONVO 
{
	private String hn;
	private String drugAllergyTempId;
	private String patientId;
	private String patientName;
	private String note;
	private String modifyEid;
	private String employeeName;
	private String modifyDate;
	private String modifyTime;
	
	
	@Override
	public String toJSON() {
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("\"hn\"").append(":").append("\"").append(this.getHn()).append("\",");
		json.append("\"drugAllergyTempId\"").append(":").append("\"").append(this.getDrugAllergyTempId()).append("\",");
		json.append("\"patientId\"").append(":").append("\"").append(this.getPatientId()).append("\",");
		json.append("\"patientName\"").append(":").append("\"").append(this.getPatientName()).append("\",");
		json.append("\"note\"").append(":").append("\"").append(this.getNote()).append("\",");
		json.append("\"modifyEid\"").append(":").append("\"").append(this.getModifyEid()).append("\",");
		json.append("\"employeeName\"").append(":").append("\"").append(this.getEmployeeName()).append("\",");
		json.append("\"modifyDateTime\"").append(":").append("\"").append(this.getModifyDateTime()).append("\",");
		json.append("\"modifyDate\"").append(":").append("\"").append(this.getModifyDate()).append("\",");
		json.append("\"modifyTime\"").append(":").append("\"").append(this.getModifyTime()).append("\"");
		json.append("}");
		
		return json.toString();
	}

	public String getHn() {
		return BpkUtility.getValidateString(hn);
	}

	public void setHn(String hn) {
		this.hn = hn;
	}

	public String getDrugAllergyTempId() {
		return BpkUtility.getValidateString(drugAllergyTempId);
	}

	public void setDrugAllergyTempId(String drugAllergyTempId) {
		this.drugAllergyTempId = drugAllergyTempId;
	}

	public String getPatientId() {
		return BpkUtility.getValidateString(patientId);
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getNote() {
		return BpkUtility.getValidateString(note);
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getModifyEid() {
		return BpkUtility.getValidateString(modifyEid);
	}

	public void setModifyEid(String modifyEid) {
		this.modifyEid = modifyEid;
	}

	public String getEmployeeName() {
		return BpkUtility.getValidateString(employeeName);
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getModifyDate() {
		return BpkUtility.getValidateString(modifyDate);
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyTime() {
		String time = BpkUtility.getValidateString(modifyTime);
		
		if(time==null || "".equals(time))
			time = "00:00:00";
		
		if(time.length()==5 && time.indexOf(':')!=-1)
			time = time+":00";
		
		return time;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyDateTime()
	{
		return this.getModifyDate()+" "+this.getModifyTime();
	}

	public String getPatientName() {
		return BpkUtility.getValidateString(patientName);
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

}
