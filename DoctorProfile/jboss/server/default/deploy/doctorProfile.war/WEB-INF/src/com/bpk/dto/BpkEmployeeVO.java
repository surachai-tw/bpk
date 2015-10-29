package com.bpk.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bpk.utility.BpkUtility;

@SuppressWarnings("serial")
public class BpkEmployeeVO implements Serializable, JSONVO 
{
	private String bpkClinicId;
	private String clinicDescription;
	private String licenseNo;
	private String qualification;
	private String educational;
	private String institute;
	private String board;
	private String specialty;
	private String others;
	
	@SuppressWarnings("rawtypes")
	private List listBpkEmployeeVO;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addSlot(BpkEmployeeVO newBpkEmployeeVO)
	{
		if(listBpkEmployeeVO==null)
			listBpkEmployeeVO = new ArrayList();
		listBpkEmployeeVO.add(newBpkEmployeeVO);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addSlot(List newListBpkEmployeeVO)
	{
		if(this.listBpkEmployeeVO==null)
			this.listBpkEmployeeVO = new ArrayList();
		this.listBpkEmployeeVO.addAll(newListBpkEmployeeVO);
	}
	
	@SuppressWarnings("rawtypes")
	public void removeAllSlot()
	{
		listBpkEmployeeVO = new ArrayList();
	}	
	
	@SuppressWarnings("rawtypes")
	public List getAllSlot()
	{
		return this.listBpkEmployeeVO;
	}
	
	public String getLicenseNo()
	{
		return BpkUtility.getValidateString(licenseNo);
	}
	public void setLicenseNo(String licenseNo)
	{
		this.licenseNo = licenseNo;
	}
	
	public String getQualification()
	{
		return BpkUtility.getValidateString(qualification);
	}
	public void setQualification(String qualification)
	{
		this.qualification = qualification;
	}
	
	public String getEducational()
	{
		return BpkUtility.getValidateString(educational);
	}
	public void setEducational(String educational)
	{
		this.educational = educational;
	}
	
	public String getInstitute()
	{
		return BpkUtility.getValidateString(institute);
	}
	public void setInstitute(String institute)
	{
		this.institute = institute;
	}
	
	public String getBoard()
	{
		return BpkUtility.getValidateString(board);
	}
	public void setBoard(String board)
	{
		this.board = board;
	}
	
	public String getSpecialty()
	{
		return BpkUtility.getValidateString(specialty);
	}
	public void setSpecialty(String specialty)
	{
		this.specialty = specialty;
	}
	
	public String getOthers()
	{
		return BpkUtility.getValidateString(others);
	}
	public void setOthers(String others)
	{
		this.others = others;
	}
	
	public String getBpkInstituteId()
	{
		return BpkUtility.getValidateString(institute);
	}
	public void setBpkInstituteId(String bpkInstituteId)
	{
		this.institute = bpkInstituteId;
	}
	/*
	public String getInstituteDescription()
	{
		return BpkUtility.getValidateString(board);
	}
	public void setInstituteDescription(String instituteDescription)
	{
		this.board = instituteDescription;
	}
	*/

	private String employeeId;
	private String employeeName;
	private String dayId;
	private String dayName;
	private String startTime;
	private String endTime;
	private String limitNumAppoint;
	
	public String getLimitNumAppoint()
	{
		String num = BpkUtility.getValidateString(limitNumAppoint);
		
		return BpkUtility.isNumeric(num) ? num : "0";
	}

	public void setLimitNumAppoint(String limitNumAppoint)
	{
		this.limitNumAppoint = limitNumAppoint;
	}

	private String dayDisplayOrder;
	
	public String getDayDisplayOrder()
	{
		return BpkUtility.getValidateString(dayDisplayOrder);
	}
	public void setDayDisplayOrder(String dayDisplayOrder)
	{
		this.dayDisplayOrder = dayDisplayOrder;
	}
	public String getDayId()
	{
		return BpkUtility.getValidateString(dayId);
	}
	public void setDayId(String dayId)
	{
		this.dayId = dayId;
	}
	public String getDayName()
	{
		return BpkUtility.getValidateString(dayName);
	}
	public void setDayName(String dayName)
	{
		this.dayName = dayName;
	}
	public String getStartTime()
	{
		return BpkUtility.getValidateString(startTime);
	}
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}
	public String getEndTime()
	{
		return BpkUtility.getValidateString(endTime);
	}
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}
	public String getBpkClinicId() {
		return BpkUtility.getValidateString(bpkClinicId);
	}
	public void setBpkClinicId(String bpkClinicId) {
		this.bpkClinicId = bpkClinicId;
	}
	
	public String getClinicDescription() {
		return BpkUtility.getValidateString(clinicDescription);
	}
	public void setClinicDescription(String clinicDescription) {
		this.clinicDescription = clinicDescription;
	}
	
	/*
	public String getBpkSpecialityId() {
		return BpkUtility.getValidateString(qualification);
	}
	public void setBpkSpecialityId(String bpkSpecialityId) {
		this.qualification = bpkSpecialityId;
	}
	*/
	
	/*
	public String getSpecialityDescription() {
		return BpkUtility.getValidateString(educational);
	}
	public void setSpecialityDescription(String specialityDescription) {
		this.educational = specialityDescription;
	}
	*/
	
	public String getEmployeeId() {
		return BpkUtility.getValidateString(employeeId);
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return BpkUtility.getValidateString(employeeName);
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public String toJSON()
	{
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("\"bpkClinicId\"").append(":").append("\"").append(this.getBpkClinicId()).append("\",");
		json.append("\"clinicDescription\"").append(":").append("\"").append(this.getClinicDescription()).append("\",");
		json.append("\"licenseNo\"").append(":").append("\"").append(this.getLicenseNo()).append("\",");
		json.append("\"qualification\"").append(":").append("\"").append(this.getQualification()).append("\",");
		json.append("\"educational\"").append(":").append("\"").append(this.getEducational()).append("\",");
		json.append("\"institute\"").append(":").append("\"").append(this.getInstitute()).append("\",");
		json.append("\"board\"").append(":").append("\"").append(this.getBoard()).append("\",");
		json.append("\"specialty\"").append(":").append("\"").append(this.getSpecialty()).append("\",");
		json.append("\"others\"").append(":").append("\"").append(this.getOthers()).append("\",");
		json.append("\"employeeId\"").append(":").append("\"").append(this.getEmployeeId()).append("\",");
		json.append("\"employeeName\"").append(":").append("\"").append(this.getEmployeeName()).append("\",");
		json.append("\"dayId\"").append(":").append("\"").append(this.getDayId()).append("\",");
		json.append("\"dayName\"").append(":").append("\"").append(this.getDayName()).append("\",");
		json.append("\"startTime\"").append(":").append("\"").append(this.getStartTime()).append("\",");
		json.append("\"endTime\"").append(":").append("\"").append(this.getEndTime()).append("\"");
		json.append("}");
		
		return json.toString();
	}
	
	public static void main(String args[])
	{
		BpkEmployeeVO aBpkEmployeeVO = new BpkEmployeeVO();
		
		aBpkEmployeeVO.setBpkClinicId("1");
		aBpkEmployeeVO.setClinicDescription("อายุรกรรม");
		aBpkEmployeeVO.setEmployeeId("3");
		aBpkEmployeeVO.setEmployeeName("นพ.ทดสอบ");
		
		System.out.println(aBpkEmployeeVO.toJSON());
	}
	
	/** ตรวจสอบว่าเป็นช่วงเวลาเดียวกันหรือไม่ */
	public boolean isSameTime(BpkEmployeeVO otherBpkEmployeeVO)
	{
		if(this.getClinicDescription()!=null && this.getClinicDescription().equals(otherBpkEmployeeVO.getClinicDescription()) && 
		   this.getEmployeeName()!=null && this.getEmployeeName().equals(otherBpkEmployeeVO.getEmployeeName()) && 
		   otherBpkEmployeeVO!=null && this.getStartTime()!=null && this.getEndTime()!=null)
		{
			if(this.getStartTime().equals(otherBpkEmployeeVO.getStartTime()) && this.getEndTime().equals(otherBpkEmployeeVO.getEndTime()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void addDay(BpkEmployeeVO addBpkEmployeeVO)
	{
		if(this.getDayId()!=null && this.getDayName()!=null && addBpkEmployeeVO!=null && addBpkEmployeeVO.getDayId()!=null && addBpkEmployeeVO.getDayName()!=null)
		{
			this.setDayId(new StringBuilder(this.getDayId()).append(" ").append(addBpkEmployeeVO.getDayId()).toString());
			this.setDayName(new StringBuilder(this.getDayName()).append(" ").append(addBpkEmployeeVO.getDayName()).toString());
		}
	}
	
	/** รวบให้เวลาเดียวกันแต่คนละวันที่ เป็นบรรทัดเดียวกัน */
	public static List<BpkEmployeeVO> groupToLine(List<BpkEmployeeVO> listBpkEmployeeVO)
	{
		if(listBpkEmployeeVO!=null && listBpkEmployeeVO.size()>1)
		{
			if("d26578".equals(((BpkEmployeeVO)listBpkEmployeeVO.get(0)).getEmployeeId()))
			{
				for(int i=1; i<listBpkEmployeeVO.size(); i++)
				{
					BpkEmployeeVO tmpBpkEmployeeVO = (BpkEmployeeVO)listBpkEmployeeVO.get(i);
					BpkUtility.printDebug(new BpkEmployeeVO(), tmpBpkEmployeeVO.getDayId()+". "+tmpBpkEmployeeVO.getDayName()+", "+tmpBpkEmployeeVO.getStartTime()+" - "+tmpBpkEmployeeVO.getEndTime());
				}
			}
			try
			{
				BpkEmployeeVO prevBpkEmployeeVO = null;
				for(int i=1; i<listBpkEmployeeVO.size(); i++)
				{
					prevBpkEmployeeVO = (BpkEmployeeVO)listBpkEmployeeVO.get(i-1);
					BpkEmployeeVO curBpkEmployeeVO = (BpkEmployeeVO)listBpkEmployeeVO.get(i);
					
					if(prevBpkEmployeeVO.isSameTime(curBpkEmployeeVO))
					{
						prevBpkEmployeeVO.addDay(curBpkEmployeeVO);
						listBpkEmployeeVO.remove(i);
						
						i--;
					}
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				
			}
		}
		return listBpkEmployeeVO;
	}
}
