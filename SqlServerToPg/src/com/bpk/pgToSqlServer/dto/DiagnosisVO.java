package com.bpk.pgToSqlServer.dto;

import com.bpk.pgToSqlServer.utility.Utility;

/**
 *
 * @author surachai.tw
 */
public class DiagnosisVO
{
    private String patientId;
    private String visitId;
    private String beginningDiagnosis;
    private String icd10Code;
    private String icd10Description;
    private String fixDiagnosisTypeId;
    private String diagnosisType;

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
     * @return the beginningDiagnosis
     */
    public String getBeginningDiagnosis()
    {
        return Utility.getStringVO(beginningDiagnosis);
    }

    /**
     * @param beginningDiagnosis the beginningDiagnosis to set
     */
    public void setBeginningDiagnosis(String beginningDiagnosis)
    {
        this.beginningDiagnosis = beginningDiagnosis;
    }

    /**
     * @return the icd10Code
     */
    public String getIcd10Code()
    {
        return Utility.getStringVO(icd10Code);
    }

    /**
     * @param icd10Code the icd10Code to set
     */
    public void setIcd10Code(String icd10Code)
    {
        this.icd10Code = icd10Code;
    }

    /**
     * @return the icd10Description
     */
    public String getIcd10Description()
    {
        return Utility.getStringVO(icd10Description);
    }

    /**
     * @param icd10Description the icd10Description to set
     */
    public void setIcd10Description(String icd10Description)
    {
        this.icd10Description = icd10Description;
    }

    /**
     * @return the fixDiagnosisTypeId
     */
    public String getFixDiagnosisTypeId()
    {
        return Utility.getStringVO(fixDiagnosisTypeId);
    }

    /**
     * @param fixDiagnosisTypeId the fixDiagnosisTypeId to set
     */
    public void setFixDiagnosisTypeId(String fixDiagnosisTypeId)
    {
        this.fixDiagnosisTypeId = fixDiagnosisTypeId;
    }

    /**
     * @return the diagnosisType
     */
    public String getDiagnosisType()
    {
        return Utility.getStringVO(diagnosisType);
    }

    /**
     * @param diagnosisType the diagnosisType to set
     */
    public void setDiagnosisType(String diagnosisType)
    {
        this.diagnosisType = diagnosisType;
    }
}
