<%@ page contentType="text/html; charset=windows-874"%>
<html>
<head>
<jsp:include page="inc/charset.jsp" flush="false"/>
<link type="text/css" rel="stylesheet" href="css/extend.css">
<script type="text/javascript" src="js/coreobject/BpkEmployeeVO.js"></script>
<script type="text/javascript" src="js/TableUtility.js"></script>
<script LANGUAGE="JavaScript">
<!--

function tblBpkEmployeeVO_OnDblClick()
{
	/*
	var sr = event.srcElement;
	if(sr.tagName == "TD")
	{
		var se = sr.parentElement;
		selectVisit(se.aDoctorVO.patient_id);
	}
	*/
}

/// ปุ่มล้าง
function listDoctor_btnClearKeyWord_onclick()
{
	/*
	var txtEmpId = document.getElementById("listDoctor_txtDoctorEid");
	var slc = document.getElementById("listDoctor_cmbWard");
	var cnt = document.getElementById("tbdListPatient");

	txtEmpId.value = "";
	txtEmpId.employee_eid = "";
	slc.value = "null";
	listDoctor_objNavigator.reset();

	var cntSize = cnt.childNodes.length;
	for(var i = 0 ; i < cntSize ; i++)
	{
		cnt.removeChild(cnt.childNodes[0]);
	}
	*/
}

//-->
</script>
</head>

<body marginwidth="0" marginheight="0" topmargin="0" leftmargin="0" width="100%" style="height:100%">
<table width="100%" style="height:100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td style="height:100%">
			<table id="tblBpkEmployeeVO" width="100%" border="0" cellspacing="1" cellpadding="0" style="cursor:default" onmouseover="tableOver(event, this);" onmouseout="tableOut(event, this);" ondblclick="">
			</table>
		</td>
	</tr>
</table>
</body>
</html>