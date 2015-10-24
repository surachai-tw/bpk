<%@ page contentType="text/html; charset=windows-874"%>
<%@ page import="com.bpk.utility.BpkUtility"%>
<html>
<head>
<script language="JavaScript">
<!--
<%
	session.removeAttribute("filter_employeeName");
	session.removeAttribute("filter_clinic");
	session.removeAttribute("filter_specialty");
	session.removeAttribute("filter_optionWithSchedule");

	session.removeAttribute("filter_searchCount");
	session.removeAttribute("filter_searchPage");


	BpkUtility.printDebug(this, "Remove Attribute from session");
%>
	window.location = "../../listDoctorJSP.jsp";
	top.statusFrame.repStatusSuccess("");
//-->
</script>
</head>
<body>
	<p>Executing...</p>
</body>
</html>