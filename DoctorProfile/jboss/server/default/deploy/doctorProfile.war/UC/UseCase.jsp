<%@ page contentType="text/html; charset=Windows-874"%>
<%@ page import="com.iMed.iMedCore.utility.*"%>
<%
	String uc = request.getParameter("UC");
	System.out.println(session.getAttribute(HttpSessionVariable.USERID)+"@"+request.getRemoteAddr()+", "+uc);
	if("listDoctor".equals(uc))
	{
%>
<jsp:include page="form/listDoctor.jsp" flush="false"/>
<%
	}
	else if("resetFilterListDoctor".equals(uc))
	{
%>
<jsp:include page="form/resetFilterListDoctor.jsp" flush="false"/>
<%
	}
	else
	{
%>
<script language="JavaScript">
<!--
	alert("Bad Usecase command");
	location.href = "../formFilter.jsp";
//-->
</script>
<%
	}
%>