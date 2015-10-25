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

	var aBpkEmployeeVO = null;

<%
	try
	{
		ServletRequestThai req = new ServletRequestThai(request);
		String employeeId = (String)req.getParameterThai("employeeId");
		String licenseNo = (String)req.getParameterThai("licenseNo");
		String qualification = (String)req.getParameterThai("qualification");
		String educational = (String)req.getParameterThai("educational");
		String institute = (String)req.getParameterThai("institute");
		String board = (String)req.getParameterThai("board");
		String specialty = (String)req.getParameterThai("specialty");
		String others = (String)req.getParameterThai("others");


		Utility.printCoreDebug(this, "employeeId = "+employeeId);
		Utility.printCoreDebug(this, "licenseNo = "+licenseNo);
		Utility.printCoreDebug(this, "qualification = "+qualification);
		Utility.printCoreDebug(this, "educational = "+educational);
		Utility.printCoreDebug(this, "institute = "+institute);
		Utility.printCoreDebug(this, "board = "+board);
		Utility.printCoreDebug(this, "specialty = "+specialty);
		Utility.printCoreDebug(this, "others = "+others);

		HashMap result;
				
		DoctorProfileDAO aDAO = DAOFactory.newDoctorProfileDAO();

		HashMap param = new HashMap();
		BpkEmployeeVO aBpkEmployeeVO = new BpkEmployeeVO();
		aBpkEmployeeVO.setEmployeeId(employeeId);
		aBpkEmployeeVO.setLicenseNo(licenseNo);
		aBpkEmployeeVO.setQualification(qualification);
		aBpkEmployeeVO.setEducational(educational);
		aBpkEmployeeVO.setInstitute(institute);
		aBpkEmployeeVO.setBoard(board);
		aBpkEmployeeVO.setSpecialty(specialty);
		aBpkEmployeeVO.setOthers(others);
		param.put("bpkEmployeeVO".toUpperCase(), aBpkEmployeeVO);

		result = aDAO.saveDoctorProfile(param);
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
	window.location = "../../editDoctorProfileJSP.jsp";

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