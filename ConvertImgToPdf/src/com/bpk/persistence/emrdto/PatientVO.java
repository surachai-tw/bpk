package com.bpk.persistence.emrdto;

import java.util.List;

import com.bpk.utility.Utility;
import com.bpk.utility.XPersistent;

public class PatientVO extends XPersistent
{
	private String hn;
        private String originalHn;
	private String patientName;
	private String fixGenderId;
	private String fixGenderDescription;
	private String fixNationalityId;
	private String fixNationalityDescription;
	private String birthdate;
	private String age;

	private List listVisitVO;

	public String getHn()
	{
		return Utility.getStringVO(hn);
	}

	public void setHn(String hn)
	{
		this.hn = hn;
	}

	public String getPatientName()
	{
		return Utility.getStringVO(patientName);
	}

	public void setPatientName(String patientName)
	{
		this.patientName = patientName;
	}

	public String getFixGenderId()
	{
		return Utility.getStringVO(fixGenderId);
	}

	public void setFixGenderId(String fixGenderId)
	{
		this.fixGenderId = fixGenderId;
	}

	public String getBirthdate()
	{
		return Utility.getStringVO(birthdate);
	}

	public void setBirthdate(String birthdate)
	{
		this.birthdate = birthdate;
	}

	public String getAge()
	{
		return Utility.isNull(age) ? Utility.getAgeFromBirthdate(birthdate) : Utility.getStringVO(age);
	}

	public void setAge(String age)
	{
		this.age = age;
	}

	public String getFixGenderDescription()
	{
		return Utility.getStringVO(fixGenderDescription);
	}

	public void setFixGenderDescription(String fixGenderDescription)
	{
		this.fixGenderDescription = fixGenderDescription;
	}

	/**
	 * @return the listVisitVO
	 */
	public List getListVisitVO()
	{
		return listVisitVO;
	}

	/**
	 * @param listVisitVO
	 *            the listVisitVO to set
	 */
	public void setListVisitVO(List listVisitVO)
	{
		this.listVisitVO = listVisitVO;
	}

	/**
	 * @return the fixNationalityId
	 */
	public String getFixNationalityId()
	{
		return Utility.getStringVO(fixNationalityId);
	}

	/**
	 * @param fixNationalityId
	 *            the fixNationalityId to set
	 */
	public void setFixNationalityId(String fixNationalityId)
	{
		this.fixNationalityId = fixNationalityId;
	}

	/**
	 * @return the fixNationalityDescription
	 */
	public String getFixNationalityDescription()
	{
		return Utility.getStringVO(fixNationalityDescription);
	}

	/**
	 * @param fixNationalityDescription
	 *            the fixNationalityDescription to set
	 */
	public void setFixNationalityDescription(String fixNationalityDescription)
	{
		this.fixNationalityDescription = fixNationalityDescription;
	}

    /**
     * @return the originalHn
     */
    public String getOriginalHn()
    {
        return Utility.getStringVO(originalHn);
    }

    /**
     * @param originalHn the originalHn to set
     */
    public void setOriginalHn(String originalHn)
    {
        this.originalHn = originalHn;
    }

}
