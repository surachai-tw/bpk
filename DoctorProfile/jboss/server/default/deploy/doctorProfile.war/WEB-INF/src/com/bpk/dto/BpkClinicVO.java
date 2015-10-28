package com.bpk.dto;

import java.io.Serializable;

import com.bpk.utility.BpkUtility;

@SuppressWarnings("serial")
public class BpkClinicVO implements Serializable, JSONVO 
{
	private String spId;
	private String clinicDescription;

	public String getSpId() {
		return BpkUtility.getValidateString(spId);
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getClinicDescription() {
		return BpkUtility.getValidateString(clinicDescription);
	}

	public void setClinicDescription(String clinicDescription) {
		this.clinicDescription = clinicDescription;
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}
