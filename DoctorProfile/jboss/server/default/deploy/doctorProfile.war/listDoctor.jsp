<%@ page contentType="text/html; charset=WINDOWS-874"%>
<%@ page import="com.iMed.iMedCore.utility.*"%>
<html>
<head>
<title>รายชื่อแพทย์</title>
<jsp:include page="inc/charset.jsp" flush="false"/>
<script language="javascript">
<!--

// For filter 
var filter_empId;
var filter_clinic;
var filter_specialty;
var filter_optionWithSchedule;
var filter_searchPage;
var filter_searchCount;

// สำหรับเก็บข้อมูลใน Table 
var listBpkEmployeeVO = new Array();

//-->
</script>
</head>

<frameset rows="70%,20%,15" frameborder="NO" border="0" framespacing="0">
  <frame src="listDoctorMain.jsp" name="mainFrame" id="mainFrame" scrolling="yes">
  <frame src="listDoctorJSP.jsp" name="listDoctorJSPFrame" id="listDoctorJSPFrame" 
	scrolling="yes" noresize>
  <frame src="inc/windowStatus.jsp" name="statusFrame" scrolling="no" noresize>
</frameset>

<noframes>
<body>
</body>
</noframes>

</html>