<%@ page contentType="text/html; charset=Windows-874"%>
<%@ page import="com.iMed.iMedCore.utility.HttpSessionVariable"%>
<%
	String uc = request.getParameter("UC");
	System.out.println(session.getAttribute(HttpSessionVariable.USERID)+"@"+request.getRemoteAddr()+", "+uc);
	if("readDoctorProfile".equals(uc))
	{
%>
<jsp:include page="form/readDoctorProfile.jsp" flush="false"/>
<%
	}
	else if("saveDoctorProfile".equals(uc))
	{
%>
<jsp:include page="form/saveDoctorProfile.jsp" flush="false"/>
<%
	}
	else
	{
%>
<script language="JavaScript">
<!--
	alert("Bad use case command");
	location.href = "../editDoctorProfileJSP.jsp";
//-->
</script>
<%
	}
%>