package com.bpk.dao;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import com.bpk.dto.BpkEmployeeVO;
import com.bpk.dto.ResultFlag;
import com.bpk.utility.BpkUtility;
import com.bpk.utility.Sorter;

public class DoctorProfileDAO
{

	public List<String> listDayDescription()
	{
		StringBuilder sql = new StringBuilder("SELECT description FROM bpk_fix_day_of_week ORDER BY display_order");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rst = null;
		List<String> listDay = new ArrayList<String>();
		try
		{
			conn = DAOFactory.getConnection();
			stmt = conn.createStatement();
			// BpkUtility.printDebug(this, sql.toString());
			rst = stmt.executeQuery(sql.toString());

			for(;rst.next();)
			{
				listDay.add(rst.getString("description"));
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
			rst = null;
			stmt = null;
			conn = null;
		}
		return listDay;
	}
	
	@SuppressWarnings("rawtypes")
	public HashMap listDoctor(HashMap param)
	{
		String pClinic = null;
		String pSpeciality = null;
		String pDoctor = null;
		// String pDay = null;
		String pOptionWithSchedule = null;
		/** คิดว่าไม่น่าจะได้ใช้  */
		// String pDayInWeekMon = null, pDayInWeekTue = null, pDayInWeekWed = null, pDayInWeekThu = null, pDayInWeekFri = null, pDayInWeekSat = null, pDayInWeekSun = null;
		
		if (param != null)
		{
			pClinic = BpkUtility.getValidateString(param.get("clinicDescription"));
			pSpeciality = BpkUtility.getValidateString(param.get("specialityDescription"));
			pDoctor = BpkUtility.getValidateString(param.get("employeeName"));
			pOptionWithSchedule = BpkUtility.getValidateString(param.get("optionWithSchedule"));
			
			/** คิดว่าไม่น่าจะได้ใช้ 
			pDayInWeekMon = BpkUtility.getValidateString(param.get("dayInWeekMon"));
			pDayInWeekTue = BpkUtility.getValidateString(param.get("dayInWeekTue"));
			pDayInWeekWed = BpkUtility.getValidateString(param.get("dayInWeekWed"));
			pDayInWeekThu = BpkUtility.getValidateString(param.get("dayInWeekThu"));
			pDayInWeekFri = BpkUtility.getValidateString(param.get("dayInWeekFri"));
			pDayInWeekSat = BpkUtility.getValidateString(param.get("dayInWeekSat"));
			pDayInWeekSun = BpkUtility.getValidateString(param.get("dayInWeekSun"));
			*/
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<BpkEmployeeVO> listBpkEmployeeVO = new ArrayList<BpkEmployeeVO>();
		StringBuilder sql = new StringBuilder();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rst = null;
		Sorter sorter = new Sorter();
		try
		{
			sql.append("SELECT tmp.*, ");
			sql.append("COALESCE(schedule.dayid, '') dayid, ");
			sql.append("COALESCE(schedule.dayname, '') dayname, ");
			sql.append("COALESCE(schedule.start_time, '') start_time, ");
			sql.append("COALESCE(schedule.end_time, '') end_time, ");
			sql.append("COALESCE(schedule.display_order, '') display_order ");
			sql.append("FROM ");
			sql.append("(	");
			sql.append("    SELECT DISTINCT 		");
			sql.append("    COALESCE(drsch.spid, '') bpk_clinic_id, 		");
			sql.append("    trim(replace(replace(replace(bpkget_service_description(drsch.spid), '..', ''), 'ห้องตรวจ', ''), 'จุดซักประวัติ', '')) AS clinic_description, 		");
			sql.append("    COALESCE(spc.qualification, '') qualification, 		");
			sql.append("    COALESCE(spc.educational, '') educational, 		");
			sql.append("    COALESCE(spc.institute, '') institute, 		");
			sql.append("    COALESCE(spc.board, '') board, 		");
			sql.append("    COALESCE(spc.specialty, '') specialty, 		");
			sql.append("    COALESCE(spc.others, '') others, 		");
			sql.append("    COALESCE(e.profession_code, '') license_no, 		");
			sql.append("    e.employee_id, 		");
			sql.append("    bpkget_employee_name(e.employee_id) AS employee_name 	");
			sql.append("    FROM 		employee AS e		");
			sql.append("    LEFT JOIN bpk_employee_doctor AS spc ON e.employee_id=spc.employee_id 		");
			sql.append("    LEFT JOIN doctor_schedule AS drsch ON e.employee_id=drsch.employee_id 	");
			sql.append("    WHERE e.fix_employee_type_id='2'		");
			sql.append("    AND e.active='1' ");
			sql.append(") tmp ");
			sql.append("LEFT JOIN ");
			sql.append("(");
			sql.append("    SELECT schd.spid bpk_clinic_id, schd.employee_id, schd.fix_day_of_week dayid, schd.limit_num_appoint limit_appointment, dayofweek.description dayname, substring(schd.start_time, 1, 5) start_time, substring(schd.end_time, 1, 5) end_time, dayofweek.display_order ");
			sql.append("    FROM doctor_schedule schd");
			sql.append("    INNER JOIN bpk_fix_day_of_week dayofweek ON schd.fix_day_of_week=dayofweek.bpk_fix_day_of_week_id         ");
			sql.append(") schedule ");
			sql.append("ON tmp.bpk_clinic_id = schedule.bpk_clinic_id AND tmp.employee_id = schedule.employee_id ");
			sql.append("WHERE 1=1 ");
			
			// sql.append("AND tmp.employee_name LIKE '%").append(pDoctor).append("%' ");			
			if(pDoctor!=null && !"".equals(pDoctor))
			{
				String[] aDoctor = BpkUtility.splitString(pDoctor, " ");				
				sql.append(" AND (");
				for(int i=0; i<aDoctor.length; i++)
				{
					sql.append("tmp.employee_name LIKE '%").append(aDoctor[i]).append("%' ");
					
					if(i+1<aDoctor.length)
						sql.append(" AND ");
				}
				sql.append(") ");				
			}
			
			// sql.append("AND tmp.specialty LIKE '%").append(pSpeciality).append("%' ");
			if(pSpeciality!=null && !"".equals(pSpeciality))
			{
				String[] aSpeciality = BpkUtility.splitString(pSpeciality, " ");				
				sql.append(" AND (");
				for(int i=0; i<aSpeciality.length; i++)
				{
					sql.append("tmp.specialty LIKE '%").append(aSpeciality[i]).append("%' ");
					
					if(i+1<aSpeciality.length)
						sql.append(" AND ");
				}
				sql.append(") ");				
			}

			// sql.append("AND tmp.clinic_description LIKE '%").append(pClinic).append("%' ");
			if(pClinic!=null && !"".equals(pClinic))
			{
				String[] aClinic = BpkUtility.splitString(pClinic, " ");				
				sql.append(" AND (");
				for(int i=0; i<aClinic.length; i++)
				{
					sql.append("tmp.clinic_description LIKE '%").append(aClinic[i]).append("%' ");
					
					if(i+1<aClinic.length)
						sql.append(" AND ");
				}
				sql.append(") ");				
			}
			
			if(BpkUtility.isTrue(pOptionWithSchedule))
			{
				sql.append(" AND tmp.clinic_description NOT LIKE '' ");
			}
			
			// ส่วนนี้ ต้องเรียงด้วยเวลา ก่อน วัน เพราะต้องยุบวัน เข้าไปในกลุ่มเวลาเดียวกัน
			sql.append("ORDER BY tmp.employee_name, tmp.clinic_description, tmp.specialty, schedule.start_time, schedule.end_time, schedule.display_order");

			conn = DAOFactory.getConnection();
			stmt = conn.createStatement();
			BpkUtility.printDebug(this, sql.toString());
			rst = stmt.executeQuery(sql.toString());

			for (; rst.next();)
			{
				BpkEmployeeVO tmpBpkEmployeeVO = new BpkEmployeeVO();

				tmpBpkEmployeeVO.setBpkClinicId(rst.getString("bpk_clinic_id"));
				tmpBpkEmployeeVO.setClinicDescription(rst.getString("clinic_description"));
				
				// เลข ว.ของแพทย์
				tmpBpkEmployeeVO.setLicenseNo(rst.getString("license_no"));
				
				// คุณวุฒิ (เวชศาสตร์ครอบครัว, อายุรศาสตร์ทั่วไป, อายุรศาสตร์โรคหัวใจ, etc.)
				tmpBpkEmployeeVO.setQualification(rst.getString("qualification"));
				
				// พบ.
				tmpBpkEmployeeVO.setEducational(rst.getString("educational"));
				
				// สถาบัน (จุฬาฯ, ม.มหิดล, ม.มหิดล (ศิริราช), etc.)
				tmpBpkEmployeeVO.setInstitute(rst.getString("institute"));
				
				// (วว.เววชศาสตร์ครอบครัว (จุฬาฯ), วว.ประสาทวิทยา (รามา), etc.)
				tmpBpkEmployeeVO.setBoard(rst.getString("board"));
				
				// (อายุรศาสตร์โรคไต จุฬาฯ, อายุรศาสตร์มะเร็งวิทยา (ราชวิถี), etc.)
				tmpBpkEmployeeVO.setSpecialty(rst.getString("specialty"));
				
				tmpBpkEmployeeVO.setOthers(rst.getString("others"));
				
				tmpBpkEmployeeVO.setEmployeeId(rst.getString("employee_id"));
				tmpBpkEmployeeVO.setEmployeeName(rst.getString("employee_name"));

				tmpBpkEmployeeVO.setDayId(rst.getString("dayid"));
				tmpBpkEmployeeVO.setDayName(rst.getString("dayname"));
				tmpBpkEmployeeVO.setStartTime(rst.getString("start_time"));
				tmpBpkEmployeeVO.setEndTime(rst.getString("end_time"));

				tmpBpkEmployeeVO.setDayDisplayOrder(rst.getString("display_order"));
				
				listBpkEmployeeVO.add(tmpBpkEmployeeVO);
			}

			// Group day in 1 line
			listBpkEmployeeVO = BpkEmployeeVO.groupToLine(listBpkEmployeeVO);
			
			// Replace double day, if setup wrong 
			List listDayDesc = listDayDescription();
			if(listDayDesc!=null)
			{
				for(int i=0, sizei=listBpkEmployeeVO.size(); i<sizei; i++)
				{
					for(int j=0, sizej=listDayDesc.size(); j<sizej; j++)
					{
						String tmpDay = listDayDesc.get(j).toString();
						
						BpkEmployeeVO tmpBpkEmployeeVO = (BpkEmployeeVO)listBpkEmployeeVO.get(i);
						tmpBpkEmployeeVO.setDayName(tmpBpkEmployeeVO.getDayName().replaceAll(tmpDay+" "+tmpDay, tmpDay));
					}
				}
			}
			// BpkUtility.printDebug(this, "Before Sort listBpkEmployeeVO.size() = "+listBpkEmployeeVO.size());

			// Re-Sort with Employee, Clinic, Day, Time
	        int size = listBpkEmployeeVO.size();
	        String[] index = new String[size];
	        for(int i = 0; i < size; i++)
	        {
	        	BpkEmployeeVO tmpBpkEmployeeVO = (BpkEmployeeVO) listBpkEmployeeVO.get(i);
	        	index[i] = new StringBuilder(tmpBpkEmployeeVO.getEmployeeName()).append(tmpBpkEmployeeVO.getClinicDescription()).append(tmpBpkEmployeeVO.getDayDisplayOrder()).append(tmpBpkEmployeeVO.getStartTime()).toString();
	        }
	        
	        /*
	        for(int i=0; i<index.length; i++)
	        {
	        	System.out.println(index[i]);
	        }
	        */
	        
	        sorter.sort(listBpkEmployeeVO, index, Sorter.ASCENDING);			
	        
	        /*
	        System.out.println("-------------CHECK SORT-------------");
	        for(int i=0; i<listBpkEmployeeVO.size(); i++)
	        {
	        	BpkEmployeeVO tmpBpkEmployeeVO = (BpkEmployeeVO) listBpkEmployeeVO.get(i);
	        	System.out.println(new StringBuilder(tmpBpkEmployeeVO.getEmployeeName()).append(tmpBpkEmployeeVO.getClinicDescription()).append(tmpBpkEmployeeVO.getDayDisplayOrder()).append(tmpBpkEmployeeVO.getStartTime()).toString());
	        }
			BpkUtility.printDebug(this, "After Sort listBpkEmployeeVO.size() = "+listBpkEmployeeVO.size());
			*/
			
			if (listBpkEmployeeVO != null && listBpkEmployeeVO.size() > 0)
			{
				result.put(ResultFlag.STATUS, ResultFlag.STATUS_SUCCESS);
				result.put(ResultFlag.RESULT_DATA, listBpkEmployeeVO);
			} else
			{
				result.put(ResultFlag.STATUS, ResultFlag.STATUS_FAIL);
			}

			rst.close();
			stmt.close();
			conn.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		} finally
		{
			sql = null;
			stmt = null;
			rst = null;
			conn = null;
			sorter = null;
		}
		return result;
	}

}
