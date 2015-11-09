<%@ page contentType="text/html; charset=windows-874"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.iMed.iMedCore.utility.Utility"%>
<%@ page import="com.iMed.iMedCore.utility.ServletRequestThai"%>
<%@ page import="com.iMed.iMedCore.utility.HttpSessionVariable"%>
<%@ page import="com.bpk.utility.BpkUtility"%>
<%@ page import="com.bpk.dao.DAOFactory"%>
<%@ page import="com.bpk.dao.DoctorProfileDAO"%>
<%@ page import="com.bpk.dto.ResultFlag"%>
<%@ page import="com.bpk.dto.BpkAppointmentVO"%>
<html>
<head>
<script type="text/javascript" src="../../js/coreobject/BpkAppointmentVO.js"></script>
<script language="JavaScript">
<!--

	var aBpkEmployeeVO = null;

<%
	try
	{
		ServletRequestThai req = new ServletRequestThai(request);
		String appointmentId = (String)req.getParameterThai("appointmentId");
		String patientId = (String)req.getParameterThai("patientId");
		String baseSpId = (String)req.getParameterThai("baseSpId");
		String basicAdvice = (String)req.getParameterThai("basicAdvice");
		String note = (String)req.getParameterThai("note");
		String appointDate = (String)req.getParameterThai("appointDate");
		String appointTime = (String)req.getParameterThai("appointTime");
		String doctorEid = (String)req.getParameterThai("doctorEid");
		String doctorAssignerEid = (String)req.getParameterThai("doctorAssignerEid");
		String fixAppointmentStatusId = (String)req.getParameterThai("fixAppointmentStatusId");
		String fixAppointmentTypeId = (String)req.getParameterThai("fixAppointmentTypeId");
		String fixAppointmentMethodId = (String)req.getParameterThai("fixAppointmentMethodId");

		BpkUtility.printDebug(this, "appointmentId = "+appointmentId);
		BpkUtility.printDebug(this, "patientId = "+patientId);
		BpkUtility.printDebug(this, "baseSpId = "+baseSpId);
		BpkUtility.printDebug(this, "basicAdvice = "+basicAdvice);
		BpkUtility.printDebug(this, "note = "+note);
		BpkUtility.printDebug(this, "appointDate = "+appointDate);
		BpkUtility.printDebug(this, "appointTime = "+appointTime);
		BpkUtility.printDebug(this, "doctorEid = "+doctorEid);
		BpkUtility.printDebug(this, "doctorAssignerEid = "+doctorAssignerEid);
		BpkUtility.printDebug(this, "fixAppointmentStatusId = "+fixAppointmentStatusId);
		BpkUtility.printDebug(this, "fixAppointmentTypeId = "+fixAppointmentTypeId);
		BpkUtility.printDebug(this, "fixAppointmentMethodId = "+fixAppointmentMethodId);

		HashMap param = new HashMap();
		BpkAppointmentVO aBpkAppointmentVO = new BpkAppointmentVO();
		aBpkAppointmentVO.setAppointmentId(appointmentId);
		aBpkAppointmentVO.setPatientId(patientId);
		aBpkAppointmentVO.setBaseSpId(baseSpId);
		aBpkAppointmentVO.setBasicAdvice(basicAdvice);
		aBpkAppointmentVO.setNote(note);
		aBpkAppointmentVO.setAppointDate(appointDate);
		aBpkAppointmentVO.setAppointTime(appointTime);
		aBpkAppointmentVO.setDoctorEid(doctorEid);
		aBpkAppointmentVO.setDoctorAssignerEid(doctorAssignerEid);
		aBpkAppointmentVO.setFixAppointmentStatusId(fixAppointmentStatusId);
		aBpkAppointmentVO.setFixAppointmentTypeId(fixAppointmentTypeId);
		aBpkAppointmentVO.setFixAppointmentMethodId(fixAppointmentMethodId);
		aBpkAppointmentVO.setModifyEid((String)session.getAttribute(HttpSessionVariable.USER_NAME));

		HashMap result;
		
		DoctorProfileDAO aDAO = DAOFactory.newDoctorProfileDAO();
		param.put("BpkAppointmentVO".toUpperCase(), aBpkAppointmentVO);

		result = aDAO.saveAppointment(param);
		// BpkUtility.printDebug(this, "result = "+result);

		String status = (String)result.get(ResultFlag.STATUS);
		if(ResultFlag.STATUS_SUCCESS.equals(status))
		{
%>
	top.statusFrame.repStatusSuccess("จบการทำงาน");
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
	window.location = "../../makeAppointmentJSP.jsp";

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