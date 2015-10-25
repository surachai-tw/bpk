<%@ page contentType="text/html; charset=windows-874"%>
<html>
<head>
<script language="JavaScript">
<!--

function loadForm()
{
	var form = document.getElementById("form");	
	var aBpkEmployeeVO = top.aBpkEmployeeVO;

	form.elements["employeeId"].value = aBpkEmployeeVO.employeeId;
	form.elements["licenseNo"].value = aBpkEmployeeVO.licenseNo;
	form.elements["qualification"].value = aBpkEmployeeVO.qualification;
	form.elements["educational"].value = aBpkEmployeeVO.educational;
	form.elements["institute"].value = aBpkEmployeeVO.institute;
	form.elements["board"].value = aBpkEmployeeVO.board;
	form.elements["specialty"].value = aBpkEmployeeVO.specialty;
	form.elements["others"].value = aBpkEmployeeVO.others;

	form.submit();
}

//-->
</script>
</head>

<body onload="loadForm();">
	<form name="form" id="form" action="action/saveDoctorProfile.jsp" method="POST">
		<input type="hidden" name="employeeId" id="employeeId"/>
		<input type="hidden" name="licenseNo" id="licenseNo"/>
		<input type="hidden" name="qualification" id="qualification"/>
		<input type="hidden" name="educational" id="educational"/>
		<input type="hidden" name="institute" id="institute"/>
		<input type="hidden" name="board" id="board"/>
		<input type="hidden" name="specialty" id="specialty"/>
		<input type="hidden" name="others" id="others"/>
	</form>
</body>

</html>