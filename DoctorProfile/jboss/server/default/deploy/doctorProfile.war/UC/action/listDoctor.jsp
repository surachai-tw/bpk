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
		String filterEmpId = (String)req.getParameterThai("filter_empId");
		String filterEmployeename = (String)req.getParameterThai("filter_employeeName");
		String filterClinic = (String)req.getParameterThai("filter_clinic");
		String filterSpecialty = (String)req.getParameterThai("filter_specialty");
		String filterOptionWithSchedule = (String)req.getParameterThai("filter_optionWithSchedule");

		String filterSearchCount = (String)req.getParameterThai("filter_searchCount");
		String filterSearchPage = (String)req.getParameterThai("filter_searchPage");
		HashMap result;
				
		DoctorProfileDAO aDAO = DAOFactory.newDoctorProfileDAO();
		HashMap param = new HashMap();
		param.put("employeeId", filterEmpId);
		param.put("employeeName", filterEmployeename);

		param.put("clinicDescription", filterClinic);
		
		param.put("specialityDescription", filterSpecialty);
		
		param.put("optionWithSchedule", filterOptionWithSchedule);
		result = aDAO.listDoctor(param);

		// BpkUtility.printDebug(this, "result = "+result);

		String status = (String)result.get(ResultFlag.STATUS);
		if(ResultFlag.STATUS_SUCCESS.equals(status))
		{
			// String pageCurrent = (String)result.get(EventNames.PAGE_CURRENT);
			// String pageCount = (String)result.get(EventNames.PAGE_COUNT);

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
%>			
			// alert("check method = "+top.mainFrame.ifrmListDoctor);
			top.mainFrame.showListBpkEmployeeVO();
			top.statusFrame.repStatusSuccess("SUCCESS");
<%
		}
		else
		{
			System.out.println("Result Status = "+status);
%>
			top.statusFrame.repStatus("ไม่พบรายชื่อผู้ป่วยในวอร์ด");
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