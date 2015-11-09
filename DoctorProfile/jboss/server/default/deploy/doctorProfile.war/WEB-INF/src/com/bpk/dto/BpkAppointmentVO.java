package com.bpk.dto;

import java.io.Serializable;

import com.bpk.utility.BpkUtility;

@SuppressWarnings("serial")
public class BpkAppointmentVO implements Serializable, JSONVO 
{
	private String appointmentId;
	private String patientId;
	private String baseSpId;
	private String doctorEid;
	private String doctorAssignerEid;
	private String baseDepartmentId;
	private String basicAdvice;
	private String note;
	private String appointDate;
	private String appointTime;
	private String modifyEid;
	private String modifyDate;
	private String modifyTime;
	private String fixAppointmentStatusId;
	private String fixAppointmentTypeId;
	private String fixAppointmentMethodId;
	
	
	public String getFixAppointmentTypeId()
	{
		return BpkUtility.getValidateString(fixAppointmentTypeId);
	}


	public void setFixAppointmentTypeId(String fixAppointmentTypeId)
	{
		this.fixAppointmentTypeId = fixAppointmentTypeId;
	}


	public String getFixAppointmentMethodId()
	{
		return BpkUtility.getValidateString(fixAppointmentMethodId);
	}


	public void setFixAppointmentMethodId(String fixAppointmentMethodId)
	{
		this.fixAppointmentMethodId = fixAppointmentMethodId;
	}


	public String getAppointmentId()
	{
		return appointmentId;
	}


	public void setAppointmentId(String appointmentId)
	{
		this.appointmentId = appointmentId;
	}


	public String getPatientId()
	{
		return patientId;
	}


	public void setPatientId(String patientId)
	{
		this.patientId = patientId;
	}


	public String getBaseSpId()
	{
		return baseSpId;
	}


	public void setBaseSpId(String baseSpId)
	{
		this.baseSpId = baseSpId;
	}


	public String getDoctorEid()
	{
		return doctorEid;
	}


	public void setDoctorEid(String doctorEid)
	{
		this.doctorEid = doctorEid;
	}


	public String getDoctorAssignerEid()
	{
		return doctorAssignerEid;
	}


	public void setDoctorAssignerEid(String doctorAssignerEid)
	{
		this.doctorAssignerEid = doctorAssignerEid;
	}


	public String getBaseDepartmentId()
	{
		return baseDepartmentId;
	}


	public void setBaseDepartmentId(String baseDepartmentId)
	{
		this.baseDepartmentId = baseDepartmentId;
	}


	public String getBasicAdvice()
	{
		return basicAdvice;
	}


	public void setBasicAdvice(String basicAdvice)
	{
		this.basicAdvice = basicAdvice;
	}


	public String getNote()
	{
		return note;
	}


	public void setNote(String note)
	{
		this.note = note;
	}


	public String getAppointDate()
	{
		return appointDate;
	}


	public void setAppointDate(String appointDate)
	{
		this.appointDate = appointDate;
	}


	public String getAppointTime()
	{
		return appointTime;
	}


	public void setAppointTime(String appointTime)
	{
		this.appointTime = appointTime;
	}


	public String getModifyEid()
	{
		return modifyEid;
	}


	public void setModifyEid(String modifyEid)
	{
		this.modifyEid = modifyEid;
	}


	public String getModifyDate()
	{
		return modifyDate;
	}


	public void setModifyDate(String modifyDate)
	{
		this.modifyDate = modifyDate;
	}


	public String getModifyTime()
	{
		return modifyTime;
	}


	public void setModifyTime(String modifyTime)
	{
		this.modifyTime = modifyTime;
	}


	public String getFixAppointmentStatusId()
	{
		return fixAppointmentStatusId;
	}


	public void setFixAppointmentStatusId(String fixAppointmentStatusId)
	{
		this.fixAppointmentStatusId = fixAppointmentStatusId;
	}


	@Override
	public String toJSON()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
