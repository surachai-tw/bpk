package com.bpk.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import com.bpk.dto.BpkClinicVO;
import com.bpk.dto.BpkEmployeeVO;
import com.bpk.dto.ResultFlag;
import com.bpk.utility.BpkUtility;
import com.bpk.utility.Sorter;
import com.iMed.iMedCore.utility.XPersistent;
import com.iMed.iMedCore.utility.fix.FixDayOfWeek;
import com.iMed.iMedCore.utility.fix.FixServicePointGroup;

public class DoctorProfileDAO
{

	DoctorProfileDAO()
	{
	}

	/**
	 * ��ǹ�ͧ Day �����ҨҴ��ǹ�������� bpk_fix_day_of_week
	 * @return
	 */
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
			BpkUtility.printDebug(this, "listDayDescription, "+sql.toString());
			rst = stmt.executeQuery(sql.toString());

			for (; rst.next();)
			{
				listDay.add(rst.getString("description"));
			}

			rst.close();
			stmt.close();
			conn.close();
		}
		catch (Exception ex)
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

	/**
	 * ��ǹ�ͧ ���� Clinic ����Ǻ��ǹ�ͧ �Ѵ��ͧ ��� ��ͧ��Ǩ �� 1 ���ǡѹ 
	 * ��ǹ�ͧ Primary Key ����� maximum Primary Key 
	 * @return HashMap 
	 */
	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public HashMap listAllClinic()
	{
		HashMap result = new HashMap();
		List<BpkClinicVO> listBpkClinicVO = new ArrayList<BpkClinicVO>();
		StringBuilder sql = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rst = null;
		try
		{
			sql = new StringBuilder(
					"SELECT max(base_service_point_id) AS spid, trim(replace(replace(replace(description, '..', ''), '��ͧ��Ǩ', ''), '�ش�ѡ����ѵ�', '')) AS clinic_description ");
			sql.append(" FROM base_service_point WHERE fix_service_point_group_id IN ('").append(FixServicePointGroup.DOCTOR).append("', '")
					.append(FixServicePointGroup.NURSE_OPD).append("')");
			sql.append(" GROUP BY trim(replace(replace(replace(description, '..', ''), '��ͧ��Ǩ', ''), '�ش�ѡ����ѵ�', ''))");
			sql.append(" ORDER BY trim(replace(replace(replace(description, '..', ''), '��ͧ��Ǩ', ''), '�ش�ѡ����ѵ�', '')) COLLATE \"th_TH\"");

			conn = DAOFactory.getConnection();
			stmt = conn.createStatement();
			BpkUtility.printDebug(this, "listAllClinic, "+sql.toString());
			rst = stmt.executeQuery(sql.toString());

			for (; rst.next();)
			{
				BpkClinicVO aBpkClinicVO = new BpkClinicVO();

				aBpkClinicVO.setSpId(rst.getString("spid"));
				aBpkClinicVO.setClinicDescription(rst.getString("clinic_description"));

				listBpkClinicVO.add(aBpkClinicVO);
			}

			rst.close();
			stmt.close();
			conn.close();

			result.put(ResultFlag.STATUS, ResultFlag.STATUS_SUCCESS);
			result.put(ResultFlag.RESULT_DATA, listBpkClinicVO);
		}
		catch (Exception ex)
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

		return result;
	}

	/**
	 * List ��ª������ 
	 * @param param
	 * @return HashMap 
	 */
	@SuppressWarnings("rawtypes")
	public HashMap listDoctor(HashMap param)
	{
		String pClinic = null;
		String pSpeciality = null;
		String pDoctor = null;
		// String pDay = null;
		String pOptionWithSchedule = null;
		String pCount = null;
		String pPage = null;
		/** �Դ�������Ҩ����� */
		// String pDayInWeekMon = null, pDayInWeekTue = null, pDayInWeekWed =
		// null, pDayInWeekThu = null, pDayInWeekFri = null, pDayInWeekSat =
		// null, pDayInWeekSun = null;

		if (param != null)
		{
			pClinic = BpkUtility.getValidateString(param.get("clinicDescription"));
			pSpeciality = BpkUtility.getValidateString(param.get("specialityDescription"));
			pDoctor = BpkUtility.getValidateString(param.get("employeeName"));
			pOptionWithSchedule = BpkUtility.getValidateString(param.get("optionWithSchedule"));
			pCount = BpkUtility.getValidateString(param.get("count"));
			pPage = BpkUtility.getValidateString(param.get("page"));

			BpkUtility.printDebug(this, "listDoctor PARAM pClinic = "+pClinic);
			BpkUtility.printDebug(this, "listDoctor PARAM pSpeciality = "+pSpeciality);
			BpkUtility.printDebug(this, "listDoctor PARAM pDoctor = "+pDoctor);
			BpkUtility.printDebug(this, "listDoctor PARAM pOptionWithSchedule = "+pOptionWithSchedule);
			BpkUtility.printDebug(this, "listDoctor PARAM pCount = "+pCount);
			BpkUtility.printDebug(this, "listDoctor PARAM pPage = "+pPage);

			/**
			 * �Դ�������Ҩ����� pDayInWeekMon =
			 * BpkUtility.getValidateString(param.get("dayInWeekMon"));
			 * pDayInWeekTue =
			 * BpkUtility.getValidateString(param.get("dayInWeekTue"));
			 * pDayInWeekWed =
			 * BpkUtility.getValidateString(param.get("dayInWeekWed"));
			 * pDayInWeekThu =
			 * BpkUtility.getValidateString(param.get("dayInWeekThu"));
			 * pDayInWeekFri =
			 * BpkUtility.getValidateString(param.get("dayInWeekFri"));
			 * pDayInWeekSat =
			 * BpkUtility.getValidateString(param.get("dayInWeekSat"));
			 * pDayInWeekSun =
			 * BpkUtility.getValidateString(param.get("dayInWeekSun"));
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
			sql.append("    trim(replace(replace(replace(bpkget_service_description(drsch.spid), '..', ''), '��ͧ��Ǩ', ''), '�ش�ѡ����ѵ�', '')) AS clinic_description, 		");
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
			if (pDoctor != null && !"".equals(pDoctor))
			{
				String[] aDoctor = BpkUtility.splitString(pDoctor, " ");
				sql.append(" AND ( (");
				// ��ǹ�ͧ���� ������ٻẺ��� Split �ӡ�͹��
				for (int i = 0; i < aDoctor.length; i++)
				{
					sql.append("tmp.employee_name LIKE '%").append(aDoctor[i]).append("%' ");

					if (i + 1 < aDoctor.length)
						sql.append(" AND ");
				}
				sql.append(") ");

				// ��ǹ�ͧ username
				sql.append(" OR (");
				for (int i = 0; i < aDoctor.length; i++)
				{
					sql.append("tmp.employee_id LIKE '%").append(aDoctor[i]).append("%' ");

					if (i + 1 < aDoctor.length)
						sql.append(" OR ");
				}
				sql.append(") ");

				// ��ǹ�ͧ�Ţ �. ���� License No
				sql.append(" OR (");
				for (int i = 0; i < aDoctor.length; i++)
				{
					sql.append("tmp.license_no LIKE '%").append(aDoctor[i]).append("%' ");

					if (i + 1 < aDoctor.length)
						sql.append(" OR ");
				}
				sql.append(") ) ");
			}

			// sql.append("AND tmp.specialty LIKE '%").append(pSpeciality).append("%' ");
			if (pSpeciality != null && !"".equals(pSpeciality))
			{
				String[] aSpeciality = BpkUtility.splitString(pSpeciality, " ");
				sql.append(" AND (");
				for (int i = 0; i < aSpeciality.length; i++)
				{
					sql.append("tmp.specialty LIKE '%").append(aSpeciality[i]).append("%' ");

					if (i + 1 < aSpeciality.length)
						sql.append(" AND ");
				}
				sql.append(") ");
			}

			// sql.append("AND tmp.clinic_description LIKE '%").append(pClinic).append("%' ");
			if (pClinic != null && !"".equals(pClinic))
			{
				String[] aClinic = BpkUtility.splitString(pClinic, " ");
				sql.append(" AND (");
				for (int i = 0; i < aClinic.length; i++)
				{
					sql.append("tmp.clinic_description LIKE '%").append(aClinic[i]).append("%' ");

					if (i + 1 < aClinic.length)
						sql.append(" AND ");
				}
				sql.append(") ");
			}

			if (BpkUtility.isTrue(pOptionWithSchedule))
			{
				sql.append(" AND tmp.clinic_description NOT LIKE '' ");
			}

			// TEST
			// sql.append(" AND tmp.employee_id NOT LIKE 'd26502' ");

			// ��ǹ��� ��ͧ���§�������� ��͹ �ѹ ���е�ͧ�غ�ѹ
			// ����㹡�����������ǡѹ
			sql.append("ORDER BY tmp.employee_name COLLATE \"th_TH\", tmp.clinic_description COLLATE \"th_TH\", tmp.specialty COLLATE \"th_TH\", schedule.start_time, schedule.end_time, schedule.display_order ");

			conn = DAOFactory.getConnection();
			stmt = conn.createStatement();
			String sqlCount = "SELECT Count(*) cnt FROM (" + sql.toString() + ") AS tmp";
			BpkUtility.printDebug(this, "listDoctor, "+sqlCount.toString());
			rst = stmt.executeQuery(sqlCount);
			if (rst.next())
			{
				int records = rst.getInt("cnt");
				result.put(ResultFlag.TOTAL_RECORD, new Integer(records));
				rst.close();
			}
			rst = null;

			// ��ͧ Hard code ����͹ ���ͧ�ҡ� Production �ҡ Query �Թ 608
			// �о� Error �͹ JSON.decode
			// sql.append(" LIMIT 400");
			sql.append(" LIMIT ").append(pCount).append(" OFFSET ").append(Integer.parseInt(pPage) * Integer.parseInt(pCount));

			BpkUtility.printDebug(this, "listDoctor, "+sql.toString());
			rst = stmt.executeQuery(sql.toString());

			for (; rst.next();)
			{
				BpkEmployeeVO tmpBpkEmployeeVO = new BpkEmployeeVO();

				tmpBpkEmployeeVO.setBpkClinicId(rst.getString("bpk_clinic_id"));
				tmpBpkEmployeeVO.setClinicDescription(rst.getString("clinic_description"));

				// �Ţ �.�ͧᾷ��
				tmpBpkEmployeeVO.setLicenseNo(rst.getString("license_no"));

				// �س�ز� (�Ǫ��ʵ���ͺ����, �������ʵ������,
				// �������ʵ���ä����, etc.)
				tmpBpkEmployeeVO.setQualification(rst.getString("qualification"));

				// ��.
				tmpBpkEmployeeVO.setEducational(rst.getString("educational"));

				// ʶҺѹ (�����, �.��Դ�, �.��Դ� (�����Ҫ), etc.)
				tmpBpkEmployeeVO.setInstitute(rst.getString("institute"));

				// (��.��Ǫ��ʵ���ͺ���� (�����), ��.����ҷ�Է�� (����), etc.)
				tmpBpkEmployeeVO.setBoard(rst.getString("board"));

				// (�������ʵ���ä� �����, �������ʵ��������Է�� (�Ҫ�Զ�),
				// etc.)
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
			BpkUtility.printDebug(this, "listDoctor, group each same time to 1 line");
			listBpkEmployeeVO = BpkEmployeeVO.groupToLine(listBpkEmployeeVO);

			// Replace double day, if setup wrong
			List listDayDesc = listDayDescription();
			if (listDayDesc != null)
			{
				for (int i = 0, sizei = listBpkEmployeeVO.size(); i < sizei; i++)
				{
					for (int j = 0, sizej = listDayDesc.size(); j < sizej; j++)
					{
						String tmpDay = listDayDesc.get(j).toString();

						BpkEmployeeVO tmpBpkEmployeeVO = (BpkEmployeeVO) listBpkEmployeeVO.get(i);
						tmpBpkEmployeeVO.setDayName(tmpBpkEmployeeVO.getDayName().replaceAll(tmpDay + " " + tmpDay, tmpDay));
					}
				}
			}
			// BpkUtility.printDebug(this,
			// "Before Sort listBpkEmployeeVO.size() = "+listBpkEmployeeVO.size());

			// Re-Sort with Employee, Clinic, Day, Time
			int size = listBpkEmployeeVO.size();
			String[] index = new String[size];
			for (int i = 0; i < size; i++)
			{
				BpkEmployeeVO tmpBpkEmployeeVO = (BpkEmployeeVO) listBpkEmployeeVO.get(i);
				index[i] = new StringBuilder(tmpBpkEmployeeVO.getEmployeeName()).append(tmpBpkEmployeeVO.getClinicDescription())
						.append(tmpBpkEmployeeVO.getDayDisplayOrder()).append(tmpBpkEmployeeVO.getStartTime()).toString();
			}

			/**
			 * Check data for(int i=0; i<index.length; i++) {
			 * BpkUtility.printDebug(this, "index[i])); }
			 */

			sorter.sort(listBpkEmployeeVO, index, Sorter.ASCENDING);

			/*
			 * System.out.println("-------------CHECK SORT-------------");
			 * for(int i=0; i<listBpkEmployeeVO.size(); i++) { BpkEmployeeVO
			 * tmpBpkEmployeeVO = (BpkEmployeeVO) listBpkEmployeeVO.get(i);
			 * System.out.println(new
			 * StringBuilder(tmpBpkEmployeeVO.getEmployeeName
			 * ()).append(tmpBpkEmployeeVO
			 * .getClinicDescription()).append(tmpBpkEmployeeVO
			 * .getDayDisplayOrder
			 * ()).append(tmpBpkEmployeeVO.getStartTime()).toString()); }
			 */
			BpkUtility.printDebug(this, "listDoctor, after sort listBpkEmployeeVO.size() = " + listBpkEmployeeVO.size());

			if (listBpkEmployeeVO != null && listBpkEmployeeVO.size() > 0)
			{
				// BpkUtility.printDebug(this,
				// "SUCCESS and Add listBpkEmployeeVO");
				result.put(ResultFlag.STATUS, ResultFlag.STATUS_SUCCESS);
				result.put(ResultFlag.RESULT_DATA, listBpkEmployeeVO);
			}
			else
			{
				// BpkUtility.printDebug(this, "FAIL and No listBpkEmployeeVO");
				result.put(ResultFlag.STATUS, ResultFlag.STATUS_FAIL);
			}

			rst.close();
			stmt.close();
			conn.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			sql = null;
			stmt = null;
			rst = null;
			conn = null;
			sorter = null;
		}
		return result;
	}

	@SuppressWarnings(
	{ "rawtypes", "unused" })
	public HashMap getSlotDoctor(HashMap param)
	{
		String pEmployeeId = null;
		String pStartDate = null;
		String pEndDate = null;
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<BpkEmployeeVO> listBpkEmployeeVO = new ArrayList<BpkEmployeeVO>();
		StringBuilder sql = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rst = null;
		try
		{
			if (param != null)
			{
				pEmployeeId = BpkUtility.getValidateString(param.get("employeeId".toUpperCase()));
				pStartDate = BpkUtility.getValidateString(param.get("startDate".toUpperCase()));
				pEndDate = BpkUtility.getValidateString(param.get("endDate".toUpperCase()));
				
				BpkUtility.printDebug(this, "getSlotDoctor PARAM pEmployeeId = "+pEmployeeId);
				BpkUtility.printDebug(this, "getSlotDoctor PARAM pStartDate = "+pStartDate);
				BpkUtility.printDebug(this, "getSlotDoctor PARAM pEndDate = "+pEndDate);
			}

			sql = new StringBuilder(
					"SELECT DISTINCT doctor_schedule.fix_day_of_week dayid, dayofweek.description dayname, trim(replace(replace(replace(bpkget_service_description(doctor_schedule.spid), '..', ''), '��ͧ��Ǩ', ''), '�ش�ѡ����ѵ�', '')) AS clinic_description, substring(doctor_schedule.start_time, 1, 5) start_time, substring(doctor_schedule.end_time, 1, 5) end_time, dayofweek.display_order, doctor_schedule.limit_num_appoint ");
			sql.append(" FROM doctor_schedule ");
			sql.append(" INNER JOIN bpk_fix_day_of_week dayofweek ON doctor_schedule.fix_day_of_week = dayofweek.bpk_fix_day_of_week_id ");
			sql.append(" WHERE doctor_schedule.employee_id = '").append(pEmployeeId).append("'");
			sql.append(" ORDER BY dayofweek.display_order, start_time, end_time ");
			BpkUtility.printDebug(this, sql.toString());

			conn = DAOFactory.getConnection();
			stmt = conn.createStatement();
			BpkUtility.printDebug(this, "getSlotDoctor, "+sql.toString());
			rst = stmt.executeQuery(sql.toString());

			for (; rst.next();)
			{
				BpkEmployeeVO tmpBpkEmployeeVO = new BpkEmployeeVO();

				tmpBpkEmployeeVO.setDayId(rst.getString("dayid"));
				tmpBpkEmployeeVO.setDayName(rst.getString("dayname"));
				tmpBpkEmployeeVO.setClinicDescription(rst.getString("clinic_description"));
				tmpBpkEmployeeVO.setStartTime(rst.getString("start_time"));
				tmpBpkEmployeeVO.setEndTime(rst.getString("end_time"));
				tmpBpkEmployeeVO.setLimitNumAppoint(rst.getString("limit_num_appoint"));

				BpkUtility.printDebug(this, tmpBpkEmployeeVO.getDayId() + " " + tmpBpkEmployeeVO.getDayName() + " " + tmpBpkEmployeeVO.getStartTime());
				listBpkEmployeeVO.add(tmpBpkEmployeeVO);
			}

			rst.close();
			stmt.close();
			conn.close();

			if (listBpkEmployeeVO != null && listBpkEmployeeVO.size() > 0)
			{
				// BpkUtility.printDebug(this,
				// "SUCCESS and Add listBpkEmployeeVO");
				result.put(ResultFlag.STATUS, ResultFlag.STATUS_SUCCESS);
				result.put(ResultFlag.RESULT_DATA, listBpkEmployeeVO);
			}
			else
			{
				// BpkUtility.printDebug(this, "FAIL and No listBpkEmployeeVO");
				result.put(ResultFlag.STATUS, ResultFlag.STATUS_FAIL);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			rst = null;
			stmt = null;
			conn = null;
		}
		return result;
	}

	/***
	 * ��Ǩ�ͺ��� �ѹ���������� ����㹪�ǧ���ҷ��ç�Ѻ Slot �������� List listBpkEmployeeVO ������� 
	 * ��ҵç�����੾�� Slot ����͡� ��ٻẺ�ͧ range ��� Clinic (��� range �Ѻ Clinic ���� ^) 
	 * ��ҵç�ҡ���� 1 ��ǧ ����蹴�������ͧ���� + 
	 * @param chkDate
	 *            ������ٻẺ�ͧ yyyy-mm-dd ����� �.�.
	 * @param listBpkEmployeeVO
	 *            �����Ţͧ EmployeeVO 1 ����ҹ��
	 * @return
	 */
	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public static String getTimeInRange(String chkDate, List listBpkEmployeeVO)
	{
		BpkUtility.printDebug(new DoctorProfileDAO(), "static getTimeInRange PARAM chkDate = "+chkDate+", listBpkEmployeeVO.size() = "+(listBpkEmployeeVO!=null ? listBpkEmployeeVO.size() : null));
		StringBuilder result = new StringBuilder();
		List listTimeInRange = new ArrayList();
		if (listBpkEmployeeVO != null && listBpkEmployeeVO.size() > 0)
		{
			Calendar aCal = null;
			try
			{
				aCal = Calendar.getInstance(new Locale("en", "US"));
				Date date = BpkUtility.getDateFromString(chkDate);
				aCal.setTime(date);

				int dayOfWeek = aCal.get(Calendar.DAY_OF_WEEK);
				String day = null;
				switch (dayOfWeek)
				{
				case Calendar.SUNDAY:
					day = FixDayOfWeek.SUNDAY;
					break;

				case Calendar.MONDAY:
					day = FixDayOfWeek.MONDAY;
					break;

				case Calendar.TUESDAY:
					day = FixDayOfWeek.TUESDAY;
					break;

				case Calendar.WEDNESDAY:
					day = FixDayOfWeek.WEDNESDAY;
					break;

				case Calendar.THURSDAY:
					day = FixDayOfWeek.THURSDAY;
					break;

				case Calendar.FRIDAY:
					day = FixDayOfWeek.FRIDAY;
					break;

				case Calendar.SATURDAY:
					day = FixDayOfWeek.SATURDAY;
					break;

				default:
					day = "";
				}

				for (int i = 0, sizei = listBpkEmployeeVO.size(); i < sizei; i++)
				{
					BpkEmployeeVO tmpBpkEmployeeVO = (BpkEmployeeVO) listBpkEmployeeVO.get(i);
					if (tmpBpkEmployeeVO != null && day.equals(tmpBpkEmployeeVO.getDayId()))
					{
						listTimeInRange.add(tmpBpkEmployeeVO);
					}
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				aCal = null;
			}
		}

		// ���͡� ���� String �ͧ���� ��蹴��� +
		String separator = "+";
		for (int i = 0, sizei = listTimeInRange.size(); i < sizei; i++)
		{
			BpkEmployeeVO tmpBpkEmployeeVO = (BpkEmployeeVO) listTimeInRange.get(i);

			result.append(tmpBpkEmployeeVO.getStartTime()).append(" - ").append(tmpBpkEmployeeVO.getEndTime()).append("^")
					.append(tmpBpkEmployeeVO.getClinicDescription()).append("^")
					.append(tmpBpkEmployeeVO.getLimitNumAppoint())
					.append(separator);
		}
		if (result.length() > 0)
			result = result.deleteCharAt(result.length() - 1);
		// BpkUtility.printDebug(this, "result = "+result.toString());
		return result.toString();
	}

	/**
	 * �͢����Ţͧ Employee
	 * �բ����Ũҡ employee ��� ��� 
	 * bpk_employee_doctor �������ǹ�������� 
	 * 
	 * @param employeeId
	 * @return
	 */
	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	public HashMap getEmployeeDetail(String employeeId)
	{
		BpkUtility.printDebug(this, "getEmployeeDetail, PARAM employeeId = "+employeeId);
		HashMap result = new HashMap();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rst = null;
		StringBuilder sql = null;
		try
		{
			sql = new StringBuilder("SELECT ");
			sql.append("  COALESCE(spc.qualification, '') qualification, ");
			sql.append("  COALESCE(spc.educational, '') educational, ");
			sql.append("  COALESCE(spc.institute, '') institute, ");
			sql.append("  COALESCE(spc.board, '') board, ");
			sql.append("  COALESCE(spc.specialty, '') specialty, ");
			sql.append("  COALESCE(spc.others, '') others, ");
			sql.append("  COALESCE(e.profession_code, '') license_no, ");
			sql.append("  e.employee_id, ");
			sql.append("  bpkget_employee_name(e.employee_id) AS employee_name ");
			sql.append("FROM employee AS e ");
			sql.append("LEFT JOIN bpk_employee_doctor AS spc ON e.employee_id=spc.employee_id ");
			sql.append("WHERE e.employee_id='").append(employeeId).append("'");
			conn = DAOFactory.getConnection();
			stmt = conn.createStatement();
			BpkUtility.printDebug(this, "getEmployeeDetail, "+sql.toString());
			rst = stmt.executeQuery(sql.toString());

			if (rst.next())
			{
				BpkEmployeeVO aBpkEmployeeVO = new BpkEmployeeVO();
				aBpkEmployeeVO.setQualification(rst.getString("qualification"));
				aBpkEmployeeVO.setEducational(rst.getString("educational"));
				aBpkEmployeeVO.setInstitute(rst.getString("institute"));
				aBpkEmployeeVO.setBoard(rst.getString("board"));
				aBpkEmployeeVO.setSpecialty(rst.getString("specialty"));
				aBpkEmployeeVO.setOthers(rst.getString("others"));
				aBpkEmployeeVO.setLicenseNo(rst.getString("license_no"));
				aBpkEmployeeVO.setEmployeeId(employeeId);
				aBpkEmployeeVO.setEmployeeName(rst.getString("employee_name"));

				result.put(ResultFlag.STATUS, ResultFlag.STATUS_SUCCESS);
				result.put(ResultFlag.RESULT_DATA, aBpkEmployeeVO);
			}
			else
			{
				result.put(ResultFlag.STATUS, ResultFlag.STATUS_FAIL);
			}

			rst.close();
			stmt.close();
			conn.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			sql = null;
			conn = null;
			stmt = null;
			rst = null;
		}
		return result;
	}

	/**
	 * �ѹ�֡��ǹ�ͧ employee.profession_code 
	 * �����ǹ�������� bpk_employee_doctor 
	 * ����֧��ǹ�ͧ doctor_schedule, ��ǹ�ͧ doctor_schedule ��ź�͡�������ͧ employeeId ���� ������������������� 
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	public HashMap saveDoctorProfile(HashMap param)
	{
		HashMap result = new HashMap();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rst = null;
		StringBuilder sql = null;
		int rec = 0;
		try
		{
			if (param != null)
			{
				String bpkEmployeeDoctorId = null;
				Object aObj = param.get("bpkEmployeeVO".toUpperCase());
				if (aObj != null && aObj instanceof BpkEmployeeVO)
				{
					
					BpkEmployeeVO aBpkEmployeeVO = (BpkEmployeeVO) aObj;

					if(aBpkEmployeeVO!=null)
					{
						conn = DAOFactory.getConnection();
						stmt = conn.createStatement();
						stmt.executeUpdate("BEGIN");
						
						///////////////////////////////////////////////////////////
						// TABLE bpk_employee_doctor
						///////////////////////////////////////////////////////////
						// Check ����� bpk_employee_doctor ��������������� ��������� ���� Update
						// �������������͹ ���� Insert
						sql = new StringBuilder("SELECT bpk_employee_doctor_id FROM bpk_employee_doctor WHERE employee_id='")
								.append(aBpkEmployeeVO.getEmployeeId()).append("' ORDER BY bpk_employee_doctor_id DESC LIMIT 1");
	
						BpkUtility.printDebug(this, sql.toString());
						rst = stmt.executeQuery(sql.toString());
						if (rst.next())
						{
							bpkEmployeeDoctorId = rst.getString("bpk_employee_doctor_id");
						}
						rst.close();
						stmt.close();
	
						// ��ǹ�ͧ License ��ͧ Update � table ��Ңͧ iMed
						sql = new StringBuilder("UPDATE employee SET profession_code='").append(aBpkEmployeeVO.getLicenseNo()).append("' WHERE employee_id='")
								.append(aBpkEmployeeVO.getEmployeeId()).append("'");
						stmt = conn.createStatement();
						BpkUtility.printDebug(this, sql.toString());
						rec = stmt.executeUpdate(sql.toString());
						BpkUtility.printDebug(this, rec+" rows effected");

						if (bpkEmployeeDoctorId != null && !"".equals(bpkEmployeeDoctorId))
						{
							// �óշ���բ��������� ���繡�� Update
	
							// ��ǹ���� ��ͧ Update � table �ͧ bpk
							sql = new StringBuilder("UPDATE bpk_employee_doctor SET ");
							sql.append(" qualification = '").append(aBpkEmployeeVO.getQualification()).append("', ");
							sql.append(" educational = '").append(aBpkEmployeeVO.getEducational()).append("', ");
							sql.append(" institute = '").append(aBpkEmployeeVO.getInstitute()).append("', ");
							sql.append(" board = '").append(aBpkEmployeeVO.getBoard()).append("', ");
							sql.append(" specialty = '").append(aBpkEmployeeVO.getSpecialty()).append("', ");
							sql.append(" others = '").append(aBpkEmployeeVO.getOthers()).append("'");
							sql.append(" WHERE bpk_employee_doctor_id='").append(bpkEmployeeDoctorId).append("'");
	
							BpkUtility.printDebug(this, sql.toString());
							rec = stmt.executeUpdate(sql.toString());
							BpkUtility.printDebug(this, rec+" rows effected");
						}
						else
						{
							// ����������͹ ���� Insert
							sql = new StringBuilder(
									"INSERT INTO bpk_employee_doctor(employee_id, qualification, educational, institute, board, specialty, others) VALUES('");
							sql.append(aBpkEmployeeVO.getEmployeeId()).append("', '");
							sql.append(aBpkEmployeeVO.getQualification()).append("', '");
							sql.append(aBpkEmployeeVO.getEducational()).append("', '");
							sql.append(aBpkEmployeeVO.getInstitute()).append("', '");
							sql.append(aBpkEmployeeVO.getBoard()).append("', '");
							sql.append(aBpkEmployeeVO.getSpecialty()).append("', '");
							sql.append(aBpkEmployeeVO.getOthers()).append("')");
	
							BpkUtility.printDebug(this, sql.toString());
							rec = stmt.executeUpdate(sql.toString());
							BpkUtility.printDebug(this, rec+" rows effected");
						}
						
						///////////////////////////////////////////////////////////
						// TABLE doctor_schedule 
						///////////////////////////////////////////////////////////
						// ��÷ӧҹ�����ٻẺ�ͧ���ź�͡��͹ �������������������� 
						sql = new StringBuilder("DELETE FROM doctor_schedule WHERE employee_id='").append(aBpkEmployeeVO.getEmployeeId()).append("'");
						BpkUtility.printDebug(this, sql.toString());
						rec = stmt.executeUpdate(sql.toString());
						BpkUtility.printDebug(this, rec+" rows effected");
						
						List listSlotBpkEmployeeVO = aBpkEmployeeVO.getAllSlot();
						if(listSlotBpkEmployeeVO!=null)
						{
							for(int i=0, sizei=listSlotBpkEmployeeVO.size(); i<sizei; i++)
							{
								BpkEmployeeVO tmpBpkEmployeeVO = (BpkEmployeeVO)listSlotBpkEmployeeVO.get(i);								
								
								sql = new StringBuilder("INSERT INTO doctor_schedule(doctor_schedule_id, employee_id, spid, fix_day_of_week, start_time, end_time, limit_num_appoint) VALUES('");
								sql.append(XPersistent.generateObjectID()).append("', '");
								sql.append(aBpkEmployeeVO.getEmployeeId()).append("', '");
								sql.append(tmpBpkEmployeeVO.getBpkClinicId()).append("', '");
								sql.append(tmpBpkEmployeeVO.getDayId()).append("', '");
								sql.append(tmpBpkEmployeeVO.getStartTime()).append(":00', '");
								sql.append(tmpBpkEmployeeVO.getEndTime()).append(":00', '");
								sql.append(tmpBpkEmployeeVO.getLimitNumAppoint()).append("')");
								BpkUtility.printDebug(this, sql.toString());
								rec = stmt.executeUpdate(sql.toString());
								BpkUtility.printDebug(this, rec+" rows effected");								
							}
						}
						
						stmt.executeUpdate("COMMIT");
						rec = 1;
						stmt.close();
						conn.close();
					}
					
					if (rec>0)
					{
						result.put(ResultFlag.STATUS, ResultFlag.STATUS_SUCCESS);
					}
					else
					{
						result.put(ResultFlag.STATUS, ResultFlag.STATUS_FAIL);
					}
				}
				else
				{
					result.put(ResultFlag.STATUS, ResultFlag.STATUS_FAIL);
				}
			}
			else
			{
				result.put(ResultFlag.STATUS, ResultFlag.STATUS_FAIL);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			try
			{
				stmt.executeUpdate("ROLLBACK");
				stmt.close();
				conn.close();
			}
			catch(Exception ex2)
			{
				ex2.printStackTrace();
			}
		}
		finally
		{
			conn = null;
			stmt = null;
			rst = null;
			sql = null;
		}
		return result;
	}

	/**
	 * ���¡���� getEmployeeDetail ��� getSlotDoctor 
	 * @param param
	 * @return
	 */
	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public HashMap readDoctorProfile(HashMap param)
	{
		HashMap result = new HashMap();
		try
		{
			String employeeId = null;
			if (param != null)
			{
				Object aObj = param.get("employeeId".toUpperCase());
				if (aObj != null && aObj instanceof String)
				{
					employeeId = (String) aObj;

					HashMap tmpRst1 = this.getEmployeeDetail(employeeId);
					HashMap tmpRst2 = this.getSlotDoctor(param);
					if (tmpRst1 != null)
					{
						if (ResultFlag.STATUS_SUCCESS.equals(tmpRst1.get(ResultFlag.STATUS)))
						{
							result.put(ResultFlag.STATUS, ResultFlag.STATUS_SUCCESS);
							Object aObj1 = tmpRst1.get(ResultFlag.RESULT_DATA);
							if (aObj1 != null && aObj1 instanceof BpkEmployeeVO)
							{
								BpkEmployeeVO aBpkEmployeeVO = (BpkEmployeeVO) aObj1;

								if (tmpRst2!=null && ResultFlag.STATUS_SUCCESS.equals(tmpRst2.get(ResultFlag.STATUS)))
								{
									Object aObj2 = tmpRst2.get(ResultFlag.RESULT_DATA);
									if (aObj2 != null && aObj2 instanceof List)
									{
										List listBpkEmployeeVO = (List) aObj2;
										aBpkEmployeeVO.addSlot(listBpkEmployeeVO);
									}
								}

								result.put(ResultFlag.RESULT_DATA, aBpkEmployeeVO);
							}
						}
					}
				}
				else
				{
					result.put(ResultFlag.STATUS, ResultFlag.STATUS_FAIL);
				}
			}
			else
			{
				result.put(ResultFlag.STATUS, ResultFlag.STATUS_FAIL);
			}
		}
		catch (Exception ex)
		{
		}
		finally
		{

		}
		return result;
	}
}
