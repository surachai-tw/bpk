package com.bpk.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bpk.utility.BpkUtility;

@SuppressWarnings("serial")
public class BpkPatientVO implements Serializable, JSONVO 
{
	private String patientId;
	private String hn;
	private String patientName;
	private String birthdate;
	private String age;
	private String pid;
	private String nation;
	private String race;
	private String telephone;
	
	
	public String toJSON()
	{
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("}");
		
		return json.toString();
	}
	
	public static void main(String args[])
	{
		BpkPatientVO aBpkEmployeeVO = new BpkPatientVO();
		
		System.out.println(aBpkEmployeeVO.toJSON());
	}

	public String getPatientId()
	{
		return BpkUtility.getValidateString(patientId);
	}

	public void setPatientId(String patientId)
	{
		this.patientId = patientId;
	}

	public String getHn()
	{
		return BpkUtility.getValidateString(hn);
	}

	public void setHn(String hn)
	{
		this.hn = hn;
	}

	public String getPatientName()
	{
		return BpkUtility.getValidateString(patientName);
	}

	public void setPatientName(String patientName)
	{
		this.patientName = patientName;
	}

	public String getBirthdate()
	{
		return BpkUtility.getValidateString(birthdate);
	}

	public void setBirthdate(String birthdate)
	{
		this.birthdate = birthdate;
	}

	public String getAge()
	{
		return BpkUtility.getValidateString(age);
	}

	public void setAge(String age)
	{
		this.age = age;
	}

	public String getPid()
	{
		return BpkUtility.getValidateString(pid);
	}

	public void setPid(String pid)
	{
		this.pid = pid;
	}

	public String getNation()
	{
		return BpkUtility.getValidateString(nation);
	}

	public void setNation(String nation)
	{
		this.nation = nation;
	}

	public String getRace()
	{
		return BpkUtility.getValidateString(race);
	}

	public void setRace(String race)
	{
		this.race = race;
	}

	public String getTelephone()
	{
		return BpkUtility.getValidateString(telephone);
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}
	

}
