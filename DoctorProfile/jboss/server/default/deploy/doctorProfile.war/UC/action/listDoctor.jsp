<%@ page contentType="text/html; charset=windows-874"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.iMed.iMedCore.utility.Utility"%>
<%@ page import="com.iMed.iMedCore.utility.ServletRequestThai"%>
<%@ page import="com.bpk.utility.BpkUtility"%>
<%@ page import="com.bpk.dao.DAOFactory"%>
<%@ page import="com.bpk.dao.DoctorProfileDAO"%>
<%@ page import="com.bpk.dto.ResultFlag"%>
<%@ page import="com.bpk.dto.BpkEmployeeVO"%>
<html>
<head>
<script type="text/javascript" src="../../js/coreobject/BpkEmployeeVO.js"></script>
<script language="JavaScript">
<!--
var page_current;
var page_count;

top.listBpkEmployeeVO = new Array();

var aBpkEmployeeVO = null;

<%
	try
	{
		ServletRequestThai req = new ServletRequestThai(request);
		String filterEmployeeName = (String)req.getParameterThai("filter_employeeName");
		String filterClinic = (String)req.getParameterThai("filter_clinic");
		String filterSpecialty = (String)req.getParameterThai("filter_specialty");
		String filterOptionWithSchedule = (String)req.getParameterThai("filter_optionWithSchedule");

		String filterSearchCount = (String)req.getParameterThai("filter_searchCount");
		String filterSearchPage = (String)req.getParameterThai("filter_searchPage");

		BpkUtility.printDebug(this, "filterEmployeeName = "+filterEmployeeName);
		BpkUtility.printDebug(this, "filterClinic = "+filterClinic);
		BpkUtility.printDebug(this, "filterSpecialty = "+filterSpecialty);
		BpkUtility.printDebug(this, "filterOptionWithSchedule = "+filterOptionWithSchedule);
		BpkUtility.printDebug(this, "filterSearchCount = "+filterSearchCount);
		BpkUtility.printDebug(this, "filterSearchPage = "+filterSearchPage);

		session.setAttribute("filter_employeeName", filterEmployeeName);
		session.setAttribute("filter_clinic", filterClinic);
		session.setAttribute("filter_specialty", filterSpecialty);
		session.setAttribute("filter_optionWithSchedule", filterOptionWithSchedule);

		session.setAttribute("filter_searchCount", filterSearchCount);
		session.setAttribute("filter_searchPage", filterSearchPage);
		BpkUtility.printDebug(this, "Set value "+filterSearchPage+" to session 'filter_searchPage'");

		HashMap result;
				
		DoctorProfileDAO aDAO = DAOFactory.newDoctorProfileDAO();

		HashMap param = new HashMap();
		param.put("employeeName", filterEmployeeName);
		param.put("clinicDescription", filterClinic);
		param.put("specialityDescription", filterSpecialty);
		param.put("optionWithSchedule", filterOptionWithSchedule);
		param.put("count", filterSearchCount);
		param.put("page", filterSearchPage);

		result = aDAO.listDoctor(param);
		// BpkUtility.printDebug(this, "result = "+result);

		String status = (String)result.get(ResultFlag.STATUS);
		if(ResultFlag.STATUS_SUCCESS.equals(status))
		{
			List listBpkEmployeeVO = (List)result.get(ResultFlag.RESULT_DATA);
			if(Utility.isNotNull(listBpkEmployeeVO))
			{
				for(int i=0, sizei=listBpkEmployeeVO.size(); i<sizei; i++)
				{
					BpkEmployeeVO tmpBpkEmployeeVO = (BpkEmployeeVO)listBpkEmployeeVO.get(i);
%>
					aBpkEmployeeVO = new BpkEmployeeVO();
					aBpkEmployeeVO.employeeName = "<%=tmpBpkEmployeeVO.getEmployeeName()%>";
					aBpkEmployeeVO.specialty = "<%=tmpBpkEmployeeVO.getSpecialty()%>";
					aBpkEmployeeVO.clinicDescription = "<%=tmpBpkEmployeeVO.getClinicDescription()%>";
					aBpkEmployeeVO.dayName = "<%=tmpBpkEmployeeVO.getDayName()%>";
					aBpkEmployeeVO.startTime = "<%=tmpBpkEmployeeVO.getStartTime()%>";
					aBpkEmployeeVO.endTime = "<%=tmpBpkEmployeeVO.getEndTime()%>";
					aBpkEmployeeVO.employeeId = "<%=tmpBpkEmployeeVO.getEmployeeId()%>";

					top.listBpkEmployeeVO[<%=i%>] = aBpkEmployeeVO;
<%
				}
			}

			Integer totalRec = (Integer)result.get(ResultFlag.TOTAL_RECORD);
			if(Integer.parseInt(filterSearchCount)==0)
				filterSearchCount = "1";
			int pageCount = (int)Math.ceil((double)totalRec.intValue()/Integer.parseInt(filterSearchCount));
			if(pageCount==0)
				pageCount = 1;

			// กรณีที่ยังมีจำนวนที่เหลืออยู่ไม่ได้แสดงผล
			if(Integer.parseInt(filterSearchPage)<pageCount-1)
			{
%>
				top.mainFrame.setEableNavigatorNext(true);
<%
			}
			else
			{
%>
				top.mainFrame.setEableNavigatorNext(false);
<%
			}

			// กรณีที่เป็นหน้า 2 เป็นต้นไป ให้ย้อนกลับไปได้
			if(Integer.parseInt(filterSearchPage)>0 && listBpkEmployeeVO.size()>0)
			{
%>
				top.mainFrame.setEableNavigatorPrev(true);
<%
			}
			else
			{
%>
				top.mainFrame.setEableNavigatorPrev(false);
<%
			}
%>
			// เลือก ComboBox ส่วนของหน้าต่างๆใหม่ โดยคำนวณจากหน้าทั้งหมด
			var cmbObj = top.mainFrame.listDoctor_cmbPage;
			var pageCount = "<%=pageCount%>";
			while(cmbObj.options.length)
			{
				cmbObj.remove(0);
			}
			if(pageCount>1)
			{
				for(i=0; i<pageCount; i++)
				{
					var option = document.createElement("option");
					option.text = (i+1)+" / "+pageCount;
					option.value = i;
					cmbObj.add(option, i);
				}
				cmbObj.selectedIndex = <%=Integer.parseInt(filterSearchPage)%>;
			}
			
			top.mainFrame.showListBpkEmployeeVO();
			top.statusFrame.repStatusSuccess("จบการทำงาน");
<%
		}
		else
		{
%>
			top.mainFrame.showListBpkEmployeeVO();
			top.statusFrame.repStatus("ไม่พบข้อมูล");
<%
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	finally
	{
%>

	window.location = "../../listDoctorJSP.jsp";

//-->
</script>
<%
	}
%>
</head>
<body>
	<p>Executing...</p>
</body>
</html>