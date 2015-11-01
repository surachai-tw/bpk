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
<%@ page import="com.bpk.dto.BpkPatientVO"%>
<html>
<head>
<script type="text/javascript" src="../../js/coreobject/BpkPatientVO.js"></script>
<script language="JavaScript">
<!--

var aBpkPatientVO = new BpkPatientVO();

try
{
<%
	DoctorProfileDAO aDAO = null;
	try
	{
		ServletRequestThai req = new ServletRequestThai(request);
		String filterReadPatientByKeyword = (String)req.getParameterThai("filter_readPatientByKeyword");
		BpkUtility.printDebug(this, "filterReadPatientByKeyword = "+filterReadPatientByKeyword);

		aDAO = DAOFactory.newDoctorProfileDAO();
		HashMap result = null;
		HashMap param = new HashMap();
		param.put("filterReadPatientByKeyword".toUpperCase(), filterReadPatientByKeyword);

		// ดึงข้อมูลผู้ป่วยจาก Keyword ที่ส่งไป
		// ถ้าเจอคนเดียว ให้แสดงผลเลย ถ้าเจอมากกว่า 1 ให้แสดงผลเป็น List ให้เลือก 
		result = aDAO.readPatientByKeyword(param);

		if(result!=null)
		{
			String status = (String)result.get(ResultFlag.STATUS);
			if(ResultFlag.STATUS_SUCCESS.equals(status))
			{
				Object aObj = result.get(ResultFlag.RESULT_DATA);
				if(aObj!=null && aObj instanceof List)
				{
					List listBpkPatientVO = (List)aObj;
					BpkPatientVO aBpkPatientVO = null;
					if(listBpkPatientVO.size()==1)
					{
						aBpkPatientVO = (BpkPatientVO)listBpkPatientVO.get(0);
%>
						aBpkPatientVO.patientId = "<%=aBpkPatientVO.getPatientId()%>";
						aBpkPatientVO.hn = "<%=aBpkPatientVO.getHn()%>";
						aBpkPatientVO.patientName = "<%=aBpkPatientVO.getPatientName()%>";
						aBpkPatientVO.birthdate = "<%=aBpkPatientVO.getBirthdate()%>";
						aBpkPatientVO.age = "<%=aBpkPatientVO.getAge()%>";
						aBpkPatientVO.pid = "<%=aBpkPatientVO.getPid()%>";
						aBpkPatientVO.nation = "<%=aBpkPatientVO.getNation()%>";
						aBpkPatientVO.race = "<%=aBpkPatientVO.getRace()%>";
						aBpkPatientVO.telephone = "<%=aBpkPatientVO.getTelephone()%>";

						top.aBpkPatientVO = aBpkPatientVO;
						top.mainFrame.setPatientDataToForm();
<%
					}
%>
					top.statusFrame.repStatusSuccess("จบการทำงาน");
<%
				}
			}
			else
			{
%>
				top.statusFrame.repStatus("ไม่พบข้อมูล");
<%
			}
		}
		else
		{
%>
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
		aDAO = null;
	}
%>
}
catch (e)
{
	alert(e);
}

window.location = "../../makeAppointmentJSP.jsp";

//-->
</script>
</head>
<body>
	<p>Executing...</p>
</body>
</html>