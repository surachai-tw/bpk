<%@ page contentType="text/html; charset=windows-874"%>
<html>
<head>
<script language="JavaScript"  src="../js/iMedUtility.js"></script>
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

	var listBpkEmployeeVO = top.newSlotBpkEmployeeVO;
	for(var i = 0; i < listBpkEmployeeVO.length; i++)
	{
		 form.dayId.options.add(new Option("" , listBpkEmployeeVO[i].dayId));
		 form.bpkClinicId.options.add(new Option("" , listBpkEmployeeVO[i].bpkClinicId));
		 form.startTime.options.add(new Option("" , listBpkEmployeeVO[i].startTime));
		 form.endTime.options.add(new Option("" , listBpkEmployeeVO[i].endTime));
		 form.limitNumAppoint.options.add(new Option("" , listBpkEmployeeVO[i].limitNumAppoint));
	}
	selectAllForm(form);

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

		<select name="dayId" id="dayId" multiple></select>
		<select name="bpkClinicId" id="bpkClinicId" multiple></select>
		<select name="startTime" id="startTime" multiple></select>
		<select name="endTime" id="endTime" multiple></select>
		<select name="limitNumAppoint" id="limitNumAppoint" multiple></select>
	</form>
</body>

</html>