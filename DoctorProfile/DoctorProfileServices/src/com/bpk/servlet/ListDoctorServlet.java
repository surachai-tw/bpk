package com.bpk.servlet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.util.List;
import java.util.HashMap;
import java.io.*;

import com.bpk.dao.DoctorProfileDAO;
import com.bpk.dto.BpkEmployeeVO;
import com.bpk.dto.ResultFlag;
import com.bpk.utility.BpkUtility;
// import com.google.gson.JsonObject;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2558
 * </p>
 * <p>
 * Company:
 * 
 * @author Surachai Torwong
 * @version 1.0
 */

@SuppressWarnings("serial")
public class ListDoctorServlet extends HttpServlet
{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("application/json; charset=TIS-620");
		PrintWriter out = response.getWriter();

		String pClinic = null;
		String pSpecialty = null;
		String pEmployeeName = null;
		String pOptionWithSchedule = null;
		/** น่าจะไม่ได้ใช้ */
		// String pDayInWeekMon = null, pDayInWeekTue = null, pDayInWeekWed = null, pDayInWeekThu = null, pDayInWeekFri = null, pDayInWeekSat = null, pDayInWeekSun = null; 
		HashMap<String, String> param = new HashMap<String, String>();
		HashMap<String, List<BpkEmployeeVO>> result = null;
		StringBuilder json = new StringBuilder();

		Object aObjCompat = request.getSession().getAttribute("isCompatibilityView");
		String isCompatibilityView = aObjCompat==null ? "0" : aObjCompat.toString();
		BpkUtility.printDebug(this, "BpkUtility.isTrue(isCompatibilityView) = "+BpkUtility.isTrue(isCompatibilityView));
		
		if(BpkUtility.isTrue(isCompatibilityView))
		{
			pClinic = BpkUtility.getValidateStringForServlet(request, "clinicDescription");
			pSpecialty = BpkUtility.getValidateStringForServlet(request, "specialty");
			pEmployeeName = BpkUtility.getValidateStringForServlet(request, "employeeName");
			pOptionWithSchedule = BpkUtility.getValidateStringForServlet(request, "optionWithSchedule");

			/** น่าจะไม่ได้ใช้ 
			pDayInWeekMon = BpkUtility.getValidateStringForServlet(request, "dayInWeekMon");
			pDayInWeekTue = BpkUtility.getValidateStringForServlet(request, "dayInWeekTue");
			pDayInWeekWed = BpkUtility.getValidateStringForServlet(request, "dayInWeekWed");
			pDayInWeekThu = BpkUtility.getValidateStringForServlet(request, "dayInWeekThu");
			pDayInWeekFri = BpkUtility.getValidateStringForServlet(request, "dayInWeekFri");
			pDayInWeekSat = BpkUtility.getValidateStringForServlet(request, "dayInWeekSat");
			pDayInWeekSun = BpkUtility.getValidateStringForServlet(request, "dayInWeekSun");
			*/ 
			
			HttpSession session = request.getSession();
			
			if(pClinic==null || "".equals(pClinic))
			{
				Object aObj = session.getAttribute("txtClinicDescription");
				pClinic = aObj==null ? "" : aObj.toString();
				BpkUtility.printDebug(this, "GET pClinic from SESSION");
			}
			
			if(pSpecialty==null || "".equals(pSpecialty))
			{
				Object aObj = session.getAttribute("txtSpecialty");
				pSpecialty = aObj==null ? "" : aObj.toString();
				BpkUtility.printDebug(this, "GET pSpecialty from SESSION");
			}
			
			if(pEmployeeName==null || "".equals(pEmployeeName))
			{
				Object aObj = session.getAttribute("txtEmployeeName");
				pEmployeeName = aObj==null ? "" : aObj.toString();
				BpkUtility.printDebug(this, "GET pEmployeeName from SESSION");
			}
			
			if(pOptionWithSchedule==null || "".equals(pOptionWithSchedule))
			{
				Object aObj = session.getAttribute("radOptionWithSchedule");
				pOptionWithSchedule = aObj==null ? "1" : aObj.toString();
				BpkUtility.printDebug(this, "GET pOptionWithSchedule from SESSION");				
			}
			
			/*
			session.removeAttribute("txtClinicDescription");
			session.removeAttribute("txtSpecialty");
			session.removeAttribute("txtEmployeeName");
			session.removeAttribute("radOptionWithSchedule");
			*/
		}
		else
		{
			pClinic = request.getParameter("clinicDescription");
			pSpecialty = request.getParameter("specialty");
			pEmployeeName = request.getParameter("employeeName");
			pOptionWithSchedule = request.getParameter("optionWithSchedule");
		}
		
		try
		{
			BpkUtility.printDebug(this, "pClinic = "+pClinic);
			BpkUtility.printDebug(this, "pSpecialty = "+pSpecialty);
			BpkUtility.printDebug(this, "pEmployeeName = "+pEmployeeName);
			BpkUtility.printDebug(this, "pOptionWithSchedule = "+pOptionWithSchedule);
			
			DoctorProfileDAO aDAO = new DoctorProfileDAO();
			param.put("clinicDescription", pClinic);
			param.put("specialityDescription", pSpecialty);
			param.put("employeeName", pEmployeeName);
			param.put("optionWithSchedule", pOptionWithSchedule);
			
			/** น่าจะไม่ได้ใช้ 
			param.put("dayInWeekMon", pDayInWeekMon);
			param.put("dayInWeekTue", pDayInWeekTue);
			param.put("dayInWeekWed", pDayInWeekWed);
			param.put("dayInWeekThu", pDayInWeekThu);
			param.put("dayInWeekFri", pDayInWeekFri);
			param.put("dayInWeekSat", pDayInWeekSat);
			param.put("dayInWeekSun", pDayInWeekSun);
			*/
			
			result = aDAO.listDoctor(param);

			if (ResultFlag.STATUS_SUCCESS.equals(result.get(ResultFlag.STATUS)))
			{
				List<BpkEmployeeVO> listBpkEmployeeVO = result.get(ResultFlag.RESULT_DATA);

				json.append('[');
				for (int i = 0, sizei = listBpkEmployeeVO.size(); i < sizei; i++)
				{
					BpkEmployeeVO tmpBpkEmployeeVO = listBpkEmployeeVO.get(i);

					json.append(tmpBpkEmployeeVO.toJSON());

					if (i + 1 < sizei)
					{
						json.append(",");
					}
				}
				json.append(']');
				
				// BpkUtility.printDebug(this, json.toString());
				//JsonObject jsonResult = new JsonObject();
				//jsonResult.addProperty("success", true);
				//jsonResult.addProperty("ListBpkEmployee", json.toString());
				//jsonResult.addProperty("totalCount", listBpkEmployeeVO.size());
				out.print(json.toString());
				//out.print(jsonResult.toString());
				out.close();
				BpkUtility.printDebug(this, "REQUEST TO " + this.getClass().getSimpleName() + " SUCCESS");
			}
			else
			{
				BpkUtility.printDebug(this, "REQUEST TO " + this.getClass().getSimpleName() + " FAIL");				
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		} finally
		{
			out = null;
		}

		/*
		 * out.print('['); out.print(" {"); out.print("\"clinic\": \"");
		 * 
		 * out.print("Medicine");
		 * 
		 * out.print("\","); out.print("\"speciality\": \"");
		 * 
		 * out.print("Medicine children");
		 * 
		 * out.print("\","); out.print("\"doctorname\": \"");
		 * 
		 * out.print("Dr.Pana");
		 * 
		 * out.print("\" }"); out.print(']'); out.close() ;
		 */		
	}

}