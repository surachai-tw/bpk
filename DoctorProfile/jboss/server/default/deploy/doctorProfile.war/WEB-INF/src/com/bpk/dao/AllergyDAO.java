package com.bpk.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bpk.dto.BpkAllergyTempVO;
import com.bpk.dto.ParameterFlag;
import com.bpk.dto.ResultFlag;
import com.bpk.utility.BpkUtility;

public class AllergyDAO {


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap listAllergyTempForCorrect(HashMap param)
	{
		HashMap result = new HashMap();
		List<BpkAllergyTempVO> listBpkAllergyTempVO = new ArrayList<BpkAllergyTempVO>();
		StringBuilder sql = new StringBuilder();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rst = null;
		try
		{
			int limit = 0;
			try
			{	
				Object objLimit = param.get(ParameterFlag.LIMIT);
				limit = Integer.parseInt((String)objLimit);
			}
			catch(Exception ex)
			{
				limit = 1000;
			}
			
			sql.append(" SELECT DISTINCT ");
			sql.append(" (SELECT format_hn(hn) FROM patient WHERE patient_id=datemp.patient_id) hn, ");
			sql.append(" bpkget_patient_name(patient_id) patient_name, ");
			sql.append(" drug_allergy_temp_id, patient_id, note, ");
			sql.append(" modify_eid, ");
			sql.append(" bpkget_employee_name(modify_eid) employee_name,");
			sql.append(" modify_date, modify_time  ");
			sql.append(" FROM ");
			sql.append(" (");
			sql.append("    SELECT DISTINCT ");
			sql.append("    datemp.* ");
			sql.append("    FROM drug_allergy_temp datemp ");
			sql.append("    INNER JOIN (SELECT patient_id, max(modify_date||modify_time) dadatetime FROM drug_allergy GROUP BY patient_id) da ON datemp.patient_id=da.patient_id AND (datemp.modify_date||datemp.modify_time)>(dadatetime) ");
			// sql.append("    INNER JOIN drug_allergy da ON datemp.patient_id=da.patient_id AND (datemp.modify_date||datemp.modify_time)>(da.modify_date||da.modify_time)");
			sql.append("    ORDER BY datemp.modify_date DESC, datemp.modify_time DESC LIMIT 1000");
			sql.append(" ) datemp");
			sql.append(" WHERE modify_eid NOT IN (SELECT employee_id FROM employee WHERE fix_employee_type_id='3') ");
			sql.append(" AND trim(note)<>''");
			sql.append(" ORDER BY modify_date DESC, modify_time DESC LIMIT ").append(limit);
			
			conn = DAOFactory.getConnection();
			stmt = conn.createStatement();
			BpkUtility.printDebug(this, sql.toString());
			rst = stmt.executeQuery(sql.toString());
			
			for(;rst.next();)
			{
				BpkAllergyTempVO tmpBpkAllergyTempVO = new BpkAllergyTempVO();
				
				tmpBpkAllergyTempVO.setHn(rst.getString("hn"));
				tmpBpkAllergyTempVO.setDrugAllergyTempId(rst.getString("drug_allergy_temp_id"));
				tmpBpkAllergyTempVO.setPatientId(rst.getString("patient_id"));
				tmpBpkAllergyTempVO.setPatientName(rst.getString("patient_name"));
				tmpBpkAllergyTempVO.setNote(rst.getString("note"));
				tmpBpkAllergyTempVO.setModifyEid(rst.getString("modify_eid"));
				tmpBpkAllergyTempVO.setEmployeeName(rst.getString("employee_name"));
				tmpBpkAllergyTempVO.setModifyDate(rst.getString("modify_date"));
				tmpBpkAllergyTempVO.setModifyTime(rst.getString("modify_time"));
				
				listBpkAllergyTempVO.add(tmpBpkAllergyTempVO);
			}
			
			if(listBpkAllergyTempVO!=null && listBpkAllergyTempVO.size()>0)
			{
				result.put(ResultFlag.STATUS, ResultFlag.STATUS_SUCCESS);
				result.put(ResultFlag.RESULT_DATA, listBpkAllergyTempVO);
			}
			else
			{
				result.put(ResultFlag.STATUS, ResultFlag.STATUS_FAIL);
			}
			
			rst.close();
			stmt.close();
			conn.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			sql = null;
			stmt = null;
			rst = null;
			conn = null;
		}
		return result;
	}
}
