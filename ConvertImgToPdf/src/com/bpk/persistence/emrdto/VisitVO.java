package com.bpk.persistence.emrdto;

import java.util.List;

import com.bpk.utility.Utility;
import com.bpk.utility.XPersistent;

public class VisitVO extends XPersistent
{
	private String vn;
	private String an;
	private String fixVisitTypeId;
	private String visitDate;
	private String visitTime;
	private String admitDate;
	private String admitTime;
	private String financialDischargeDate;
	private String financialDischargeTime;

	private List listFolderVO;

	public String getVn()
	{
		return Utility.getStringVO(vn);
	}

	public void setVn(String vn)
	{
		this.vn = vn;
	}

	public String getAn()
	{
		return Utility.getStringVO(an);
	}

	public void setAn(String an)
	{
		this.an = an;
	}

	public String getVisitDate()
	{
		return Utility.getStringVO(visitDate);
	}

	public void setVisitDate(String visitDate)
	{
		this.visitDate = visitDate;
	}

	public String getVisitTime()
	{
		return Utility.getStringVO(visitTime);
	}

	public void setVisitTime(String visitTime)
	{
		this.visitTime = visitTime;
	}

	public String getAdmitDate()
	{
		return Utility.getStringVO(admitDate);
	}

	public void setAdmitDate(String admitDate)
	{
		this.admitDate = admitDate;
	}

	public String getAdmitTime()
	{
		return Utility.getStringVO(admitTime);
	}

	public void setAdmitTime(String admitTime)
	{
		this.admitTime = admitTime;
	}

	public String getFinancialDischargeDate()
	{
		return Utility.getStringVO(financialDischargeDate);
	}

	public void setFinancialDischargeDate(String financialDischargeDate)
	{
		this.financialDischargeDate = financialDischargeDate;
	}

	public String getFinancialDischargeTime()
	{
		return Utility.getStringVO(financialDischargeTime);
	}

	public void setFinancialDischargeTime(String financialDischargeTime)
	{
		this.financialDischargeTime = financialDischargeTime;
	}

	/**
	 * @return the fixVisitTypeId
	 */
	public String getFixVisitTypeId()
	{
		return Utility.getStringVO(fixVisitTypeId);
	}

	/**
	 * @param fixVisitTypeId
	 *            the fixVisitTypeId to set
	 */
	public void setFixVisitTypeId(String fixVisitTypeId)
	{
		this.fixVisitTypeId = fixVisitTypeId;
	}

	/**
	 * @return the listFolderVO
	 */
	public List getListFolderVO()
	{
		return listFolderVO;
	}

	/**
	 * @param listFolderVO
	 *            the listFolderVO to set
	 */
	public void setListFolderVO(List listFolderVO)
	{
		this.listFolderVO = listFolderVO;
	}

}
