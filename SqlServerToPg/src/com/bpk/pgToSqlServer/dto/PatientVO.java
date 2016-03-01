package com.bpk.pgToSqlServer.dto;

import com.bpk.pgToSqlServer.utility.Utility;

/**
 *
 * @author surachai.tw
 */
public class PatientVO
{
    private String patientId;
    private String hn;
    private String prename;
    private String firstname;
    private String lastname;
    private String firstnameEn;
    private String lastnameEn;
    private String fixGenderId;
    private String birthdate;
    private String birthdateTrue;
    private String fixNationalityId;
    private String active;
    private String isDeath;
    private String registerDate;
    private String registerTime;
    private String updateDate;
    private String updateTime;

    /**
     * @return the patientId
     */
    public String getPatientId()
    {
        return Utility.getStringVO(patientId);
    }

    /**
     * @param patientId the patientId to set
     */
    public void setPatientId(String patientId)
    {
        this.patientId = patientId;
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
     * @return the prename
     */
    public String getPrename()
    {
        return Utility.getStringVO(prename);
    }

    /**
     * @param prename the prename to set
     */
    public void setPrename(String prename)
    {
        this.prename = prename;
    }

    /**
     * @return the firstname
     */
    public String getFirstname()
    {
        return Utility.getStringVO(firstname);
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname()
    {
        return Utility.getStringVO(lastname);
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    /**
     * @return the firstnameEn
     */
    public String getFirstnameEn()
    {
        return Utility.getStringVO(firstnameEn);
    }

    /**
     * @param firstnameEn the firstnameEn to set
     */
    public void setFirstnameEn(String firstnameEn)
    {
        this.firstnameEn = firstnameEn;
    }

    /**
     * @return the lastnameEn
     */
    public String getLastnameEn()
    {
        return Utility.getStringVO(lastnameEn);
    }

    /**
     * @param lastnameEn the lastnameEn to set
     */
    public void setLastnameEn(String lastnameEn)
    {
        this.lastnameEn = lastnameEn;
    }

    /**
     * @return the fixGenderId
     */
    public String getFixGenderId()
    {
        return Utility.getStringVO(fixGenderId);
    }

    /**
     * @param fixGenderId the fixGenderId to set
     */
    public void setFixGenderId(String fixGenderId)
    {
        this.fixGenderId = fixGenderId;
    }

    /**
     * @return the birthdate
     */
    public String getBirthdate()
    {
        return Utility.getStringVO(birthdate);
    }

    /**
     * @param birthdate the birthdate to set
     */
    public void setBirthdate(String birthdate)
    {
        this.birthdate = birthdate;
    }

    /**
     * @return the birthdateTrue
     */
    public String getBirthdateTrue()
    {
        return Utility.getStringVO(birthdateTrue);
    }

    /**
     * @param birthdateTrue the birthdateTrue to set
     */
    public void setBirthdateTrue(String birthdateTrue)
    {
        this.birthdateTrue = birthdateTrue;
    }

    /**
     * @return the fixNationalityId
     */
    public String getFixNationalityId()
    {
        return Utility.getStringVO(fixNationalityId);
    }

    /**
     * @param fixNationalityId the fixNationalityId to set
     */
    public void setFixNationalityId(String fixNationalityId)
    {
        this.fixNationalityId = fixNationalityId;
    }

    /**
     * @return the active
     */
    public String getActive()
    {
        return Utility.getActiveVO(active);
    }

    /**
     * @param active the active to set
     */
    public void setActive(String active)
    {
        this.active = active;
    }

    /**
     * @return the isDeath
     */
    public String getIsDeath()
    {
        return Utility.getActiveVO(isDeath);
    }

    /**
     * @param isDeath the isDeath to set
     */
    public void setIsDeath(String isDeath)
    {
        this.isDeath = isDeath;
    }

    /**
     * @return the registerDate
     */
    public String getRegisterDate()
    {
        return Utility.getStringVO(registerDate);
    }

    /**
     * @param registerDate the registerDate to set
     */
    public void setRegisterDate(String registerDate)
    {
        this.registerDate = registerDate;
    }

    /**
     * @return the registerTime
     */
    public String getRegisterTime()
    {
        return Utility.getStringVO(registerTime);
    }

    /**
     * @param registerTime the registerTime to set
     */
    public void setRegisterTime(String registerTime)
    {
        this.registerTime = registerTime;
    }

    /**
     * @return the updateDate
     */
    public String getUpdateDate()
    {
        return Utility.getStringVO(updateDate);
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }

    /**
     * @return the updateTime
     */
    public String getUpdateTime()
    {
        return Utility.getStringVO(updateTime);
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
}
