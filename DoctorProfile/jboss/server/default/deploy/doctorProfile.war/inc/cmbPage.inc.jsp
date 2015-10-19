<%@ page contentType="text/html; charset=windows-874"%>

<%@ page import="com.iMed.iMedCore.utility.*"%>

<%
	int numPage = 1;
	String numPageString = (String)request.getParameter("numPage");

	if(Utility.isNull(numPageString))
		numPageString = "0";

	try
	{
		numPage = Integer.parseInt(numPageString);
		if(numPage==0)
			numPage = 1;
	}
	catch(Exception ex)
	{
		numPage = 1;
	}

	// หน้า
%>
	<option value="0">1 / <%=numPage%></option>
<%
	for(int i=1; i<numPage; i++)
	{
%>
		<option value="<%=i%>"><%=(i+1)%> / <%=numPage%></option>
<%		
	}	
%>
