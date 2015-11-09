<%@ page contentType="text/html; charset=Windows-874"%>
<%@ page import="com.iMed.iMedCore.utility.HttpSessionVariable"%>
<%
	String uc = request.getParameter("UC");
	System.out.println(session.getAttribute(HttpSessionVariable.USERID)+"@"+request.getRemoteAddr()+", "+uc);
	if("readPatientByKeyword".equals(uc))
	{
%>
<jsp:include page="form/readPatientByKeyword.jsp" flush="false"/>
<%
	}
	else if("saveAppointment".equals(uc))
	{
%>
<jsp:include page="form/saveAppointment.jsp" flush="false"/>
<%
	}
	else
	{
%>
<script language="JavaScript">
<!--
	alert("Bad use case command");
	location.href = "../makeAppointmentJSP.jsp";
//-->
</script>
<%
	}
%>