<%@ page contentType="text/html; charset=windows-874"%>
<html>
<head>
<script language="JavaScript"  src="../js/iMedUtility.js"></script>
<script language="JavaScript"  src="../js/coreobject/BpkAppointmentVO.js"></script>
<script language="JavaScript">
<!--

function loadForm()
{
	var form = document.getElementById("form");	
	var aBpkAppointmentVO = top.aBpkAppointmentVO;

	form.elements["appointmentId"].value = aBpkAppointmentVO.appointmentId;
	form.elements["patientId"].value = aBpkAppointmentVO.patientId;
	form.elements["baseSpId"].value = aBpkAppointmentVO.baseSpId;
	form.elements["basicAdvice"].value = aBpkAppointmentVO.basicAdvice;
	form.elements["note"].value = aBpkAppointmentVO.note;
	form.elements["appointDate"].value = aBpkAppointmentVO.appointDate;
	form.elements["appointTime"].value = aBpkAppointmentVO.appointTime;
	form.elements["doctorEid"].value = aBpkAppointmentVO.doctorEid;
	form.elements["doctorAssignerEid"].value = aBpkAppointmentVO.doctorAssignerEid;
	form.elements["fixAppointmentStatusId"].value = aBpkAppointmentVO.fixAppointmentStatusId;
	form.elements["fixAppointmentTypeId"].value = aBpkAppointmentVO.fixAppointmentTypeId;
	form.elements["fixAppointmentMethodId"].value = aBpkAppointmentVO.fixAppointmentMethodId;

	form.submit();
}

//-->
</script>
</head>

<body onload="loadForm();">
	<form name="form" id="form" action="action/saveAppointment.jsp" method="POST">
		<input type="hidden" name="appointmentId" id="appointmentId"/>
		<input type="hidden" name="patientId" id="patientId"/>
		<input type="hidden" name="baseSpId" id="baseSpId"/>
		<input type="hidden" name="basicAdvice" id="basicAdvice"/>
		<input type="hidden" name="note" id="note"/>
		<input type="hidden" name="appointDate" id="appointDate"/>
		<input type="hidden" name="appointTime" id="appointTime"/>
		<input type="hidden" name="doctorEid" id="doctorEid"/>
		<input type="hidden" name="doctorAssignerEid" id="doctorAssignerEid"/>
		<input type="hidden" name="fixAppointmentStatusId" id="fixAppointmentStatusId"/>
		<input type="hidden" name="fixAppointmentTypeId" id="fixAppointmentTypeId"/>
		<input type="hidden" name="fixAppointmentMethodId" id="fixAppointmentMethodId"/>
	</form>
</body>

</html>