<%@ page contentType="text/html; charset=TIS-620" %>
<%@ page import="com.bpk.utility.BpkUtility" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=TIS-620">
<title>Load parameters to session</title>
</head>
<body>
<%
	// ส่วนนี้ ใช้ request.getParameter("txtEmployeeName"); ไม่ได้ 
	String pEmployeeName = BpkUtility.getValidateStringForServlet(request, "txtEmployeeName");
	String pSpecialty = BpkUtility.getValidateStringForServlet(request, "txtSpecialty");
	String pClinic = BpkUtility.getValidateStringForServlet(request, "txtClinicDescription");
	String pOptionWithSchedule = BpkUtility.getValidateStringForServlet(request, "radOptionWithSchedule");
	String isCompatibilityView = request.getParameter("isCompatibilityView");
	// System.out.println("Param txtEmployeeName = "+pEmployeeName);

	// ส่วนนี้ ไม่ Work 
	// Object aObj = session.getAttribute("txtEmployeeName");
	// System.out.println("Session txtEmployeeName = "+aObj);
	
	session.setAttribute("txtEmployeeName", pEmployeeName);
	session.setAttribute("txtSpecialty", pSpecialty);
	session.setAttribute("txtClinicDescription", pClinic);
	session.setAttribute("radOptionWithSchedule", pOptionWithSchedule);
	session.setAttribute("radOptionWithoutSchedule", String.valueOf(!Boolean.parseBoolean(pOptionWithSchedule)));
	session.setAttribute("isCompatibilityView", isCompatibilityView);
%>
<script type="text/javascript">
	window.location.href = "../listDoctor.html";
</script>
</body>
</html>