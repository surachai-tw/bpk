<%@ page contentType="text/html; charset=WINDOWS-874"%>
<%@ page import="com.bpk.utility.BpkUtility"%>
<!DOCTYPE html>
<html>
<head>
<title>ทำนัดผู้ป่วย</title>
<jsp:include page="inc/charset.jsp" flush="false"/>
<script type="text/javascript" src="js/iMedUtility.js"></script>
<script type="text/javascript" src="js/coreobject/BpkPatientVO.js"></script>
<script language="javascript">
<!--

// ใช้สำหรับ search ผู้ป่วย
var filter_readPatientByKeyword = null;

// เก็บข้อมูลของผู้ป่วย
var aBpkPatientVO = new BpkPatientVO();

//-->
</script>
</head>
<%
	String debug = System.getProperty("bpk.debug");
	String frameHeight = "100%,0,15";

	if(BpkUtility.isTrue(debug))
	{
		frameHeight = "70%,20%,15";
	}
%>
<frameset rows="<%=frameHeight%>" frameborder="NO" border="0" framespacing="0">
  <frame src="makeAppointmentMain.jsp?employeeId=<%=request.getParameter("employeeId")%>&viewDate=<%=request.getParameter("viewDate")%>" name="mainFrame" id="mainFrame" scrolling="yes">
  <frame src="makeAppointmentJSP.jsp" name="makeAppointmentJSPFrame" id="makeAppointmentJSPFrame" 
	scrolling="yes" noresize>
  <frame src="inc/windowStatus.jsp" name="statusFrame" scrolling="no" noresize>
</frameset>

<noframes>
<body>
</body>
</noframes>

</html>