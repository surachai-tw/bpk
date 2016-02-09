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
        private String printDate;
        private String printTime;
        private String documentName;

        /** สำหรับใข้แสดงผลเท่านั้น */
        private String hn;
        /** สำหรับใข้แสดงผลเท่านั้น */
        private String patientName;
        private String vn;
        private String doctorEid;
        private String option;

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

    /**
     * @return the hn
     */
    public String getHn()
    {
        return Utility.getStringVO(hn);
    }

    /**
     * @param hn the hn to set
     */
    public void setHn(String hn)
    {
        this.hn = hn;
    }

    /**
     * @return the patientName
     */
    public String getPatientName()
    {
        return Utility.getStringVO(patientName);
    }

    /**
     * @param patientName the patientName to set
     */
    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    /**
     * @return the printDate
     */
    public String getPrintDate()
    {
        return Utility.getStringVO(printDate);
    }

    /**
     * @param printDate the printDate to set
     */
    public void setPrintDate(String printDate)
    {
        this.printDate = printDate;
    }

    /**
     * @return the printTime
     */
    public String getPrintTime()
    {
        return Utility.getStringVO(printTime);
    }

    /**
     * @param printTime the printTime to set
     */
    public void setPrintTime(String printTime)
    {
        this.printTime = printTime;
    }

    /**
     * @return the documentName
     */
    public String getDocumentName()
    {
        return Utility.getStringVO(documentName);
    }

    /**
     * @param documentName the documentName to set
     */
    public void setDocumentName(String documentName)
    {
        this.documentName = documentName;
    }

    /**
     * @return the vn
     */
    public String getVn()
    {
        return Utility.getStringVO(vn);
    }

    /**
     * @param vn the vn to set
     */
    public void setVn(String vn)
    {
        this.vn = vn;
    }

    /**
     * @return the doctorEid
     */
    public String getDoctorEid()
    {
        return Utility.getStringVO(doctorEid);
    }

    /**
     * @param doctorEid the doctorEid to set
     */
    public void setDoctorEid(String doctorEid)
    {
        this.doctorEid = doctorEid;
    }

    /**
     * @return the option
     */
    public String getOption()
    {
        return Utility.getStringVO(option);
    }

    /**
     * @param option the option to set
     */
    public void setOption(String option)
    {
        this.option = option;
    }

    public static String getDateFromReadText(String yyyymmddhhmmss)
    {
        if(Utility.isNotNull(yyyymmddhhmmss) && yyyymmddhhmmss.length()>=8)
        {
            String yyyymmdd = yyyymmddhhmmss.substring(0, 8);

            return yyyymmdd.substring(0, 4)+"-"+yyyymmdd.substring(4, 6)+"-"+yyyymmdd.substring(6);
        }

        return "";
    }

    public static String getTimeFromReadText(String yyyymmddhhmmss)
    {
        if(Utility.isNotNull(yyyymmddhhmmss) && yyyymmddhhmmss.length()>=8)
        {
            String hhmmss = yyyymmddhhmmss.substring(8);

            return hhmmss.substring(0, 2)+":"+hhmmss.substring(2, 4)+":"+hhmmss.substring(4);
        }

        return "";
    }

}
