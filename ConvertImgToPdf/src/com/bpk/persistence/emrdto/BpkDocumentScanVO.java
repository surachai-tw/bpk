package com.bpk.persistence.emrdto;

import com.bpk.utility.Utility;
import com.bpk.utility.XPersistent;

public class BpkDocumentScanVO extends XPersistent
{

	private String patientId;
	private String visitId;
	private String folderName;
	private String imageFileName;
	private String scanEid;
	private String scanSpid;
	private String scanDate;
	private String scanTime;
	private String updateRid;
	private String updateSpid;
	private String updateDate;
	private String updateTime;

	public String getPatientId()
	{
		return Utility.getStringVO(patientId);
	}

	public void setPatientId(String patientId)
	{
		this.patientId = patientId;
	}

	public String getVisitId()
	{
		return Utility.getStringVO(visitId);
	}

	public void setVisitId(String visitId)
	{
		this.visitId = visitId;
	}

	public String getFolderName()
	{
		return Utility.getStringVO(folderName);
	}

	public void setFolderName(String folderName)
	{
		this.folderName = folderName;
	}

	public String getImageFileName()
	{
		return Utility.getStringVO(imageFileName);
	}

	public void setImageFileName(String imageFileName)
	{
		this.imageFileName = imageFileName;
	}

	public String getScanEid()
	{
		return Utility.getStringVO(scanEid);
	}

	public void setScanEid(String scanEid)
	{
		this.scanEid = scanEid;
	}

	public String getScanSpid()
	{
		return Utility.getStringVO(scanSpid);
	}

	public void setScanSpid(String scanSpid)
	{
		this.scanSpid = scanSpid;
	}

	public String getScanDate()
	{
		return Utility.getStringVO(scanDate);
	}

	public void setScanDate(String scanDate)
	{
		this.scanDate = scanDate;
	}

	public String getScanTime()
	{
		return Utility.getStringVO(scanTime);
	}

	public void setScanTime(String scanTime)
	{
		this.scanTime = scanTime;
	}

	public String getUpdateRid()
	{
		return Utility.getStringVO(updateRid);
	}

	public void setUpdateRid(String updateRid)
	{
		this.updateRid = updateRid;
	}

	public String getUpdateSpid()
	{
		return Utility.getStringVO(updateSpid);
	}

	public void setUpdateSpid(String updateSpid)
	{
		this.updateSpid = updateSpid;
	}

	public String getUpdateDate()
	{
		return Utility.getStringVO(updateDate);
	}

	public void setUpdateDate(String updateDate)
	{
		this.updateDate = updateDate;
	}

	public String getUpdateTime()
	{
		return Utility.getStringVO(updateTime);
	}

	public void setUpdateTime(String updateTime)
	{
		this.updateTime = updateTime;
	}

}
