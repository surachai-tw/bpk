<%@ page contentType="text/html; charset=windows-874"%>
<html>
<head>
<script language="JavaScript">
<!--

function loadForm()
{
	var form = document.getElementById("form");

	form.elements["employeeId"].value = top.aBpkEmployeeVO.employeeId;

	form.submit();
}

//-->
</script>
</head>

<body onload="loadForm();">
	<form name="form" id="form" action="action/readDoctorProfile.jsp" method="POST">
		<input type="hidden" name="employeeId" id="employeeId"/>
	</form>
</body>

</html>