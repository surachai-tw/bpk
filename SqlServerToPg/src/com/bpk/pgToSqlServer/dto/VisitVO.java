package com.bpk.pgToSqlServer.dto;

import com.bpk.pgToSqlServer.utility.Utility;

/**
 *
 * @author surachai.tw
 */
public class VisitVO
{

    private String hn;
    private String patientId;
    private String visitId;
    private String originalVn;
    private String vn;
    private String visitDate;
    private String visitTime;
    private String doctorCode;
    private String doctor;
    private String originalAn;
    private String an;
    private String admitDate;
    private String admitTime;
    private String active;

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
     * @return the visitId
     */
    public String getVisitId()
    {
        return Utility.getStringVO(visitId);
    }

    /**
     * @param visitId the visitId to set
     */
    public void setVisitId(String visitId)
    {
        this.visitId = visitId;
    }

    /**
     * @return the originalVn
     */
    public String getOriginalVn()
    {
        return Utility.getStringVO(originalVn);
    }

    /**
     * @param originalVn the originalVn to set
     */
    public void setOriginalVn(String originalVn)
    {
        this.originalVn = originalVn;
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
     * @return the visitDate
     */
    public String getVisitDate()
    {
        if (visitDate != null && visitDate.length() == 8 && visitDate.indexOf("-") == -1)
        {
            return visitDate.substring(0, 4) + '-' + visitDate.substring(4, 6) + '-' + visitDate.substring(6);
        } else
        {
            return Utility.getStringVO(visitDate);
        }
    }

    /**
     * @param visitDate the visitDate to set
     */
    public void setVisitDate(String visitDate)
    {
        this.visitDate = visitDate;
    }

    /**
     * @return the visitTime
     */
    public String getVisitTime()
    {
        return Utility.getStringVO(visitTime);
    }

    /**
     * @param visitTime the visitTime to set
     */
    public void setVisitTime(String visitTime)
    {
        this.visitTime = visitTime;
    }

    /**
     * @return the doctorCode
     */
    public String getDoctorCode()
    {
        return Utility.getStringVO(doctorCode);
    }

    /**
     * @param doctorCode the doctorCode to set
     */
    public void setDoctorCode(String doctorCode)
    {
        this.doctorCode = doctorCode;
    }

    /**
     * @return the doctor
     */
    public String getDoctor()
    {
        return Utility.getStringVO(doctor);
    }

    /**
     * @param doctor the doctor to set
     */
    public void setDoctor(String doctor)
    {
        this.doctor = doctor;
    }

    /**
     * @return the originalAn
     */
    public String getOriginalAn()
    {
        return Utility.getStringVO(originalAn);
    }

    /**
     * @param originalAn the originalAn to set
     */
    public void setOriginalAn(String originalAn)
    {
        this.originalAn = originalAn;
    }

    /**
     * @return the an
     */
    public String getAn()
    {
        return Utility.getStringVO(an);
    }

    /**
     * @param an the an to set
     */
    public void setAn(String an)
    {
        this.an = an;
    }

    /**
     * @return the admitDate
     */
    public String getAdmitDate()
    {
        return Utility.getStringVO(admitDate);
    }

    /**
     * @param admitDate the admitDate to set
     */
    public void setAdmitDate(String admitDate)
    {
        this.admitDate = admitDate;
    }

    /**
     * @return the admitTime
     */
    public String getAdmitTime()
    {
        return Utility.getStringVO(admitTime);
    }

    /**
     * @param admitTime the admitTime to set
     */
    public void setAdmitTime(String admitTime)
    {
        this.admitTime = admitTime;
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
}
