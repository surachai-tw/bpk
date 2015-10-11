<%@ page contentType="text/html; charset=windows-874"%>
<html>
<head>
<jsp:include page="inc/charset.jsp" flush="false"/>
<link type="text/css" rel="stylesheet" href="css/extend.css">
<script type="text/javascript" src="js/coreobject/BpkEmployeeVO.js"></script>
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
		<td style="height:21px">
			<table width="100%" style="border-top: solid 1px #CDCDCD; height:21px" cellspacing="1" cellpadding="0">
				<tr align="center" class="contentHeader">
					<td width="18%">แพทย์</td>
					<td width="18%">คลินิก</td>
					<td width="29%">ความชำนาญ</td>
					<td width="18%">วันในสัปดาห์</td>
					<td width="6%">เริ่มเวลา</td>
					<td width="6%">ถึงเวลา</td>
					<td width="*">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="height:100%">
			<table id="tblBpkEmployeeVO" width="100%" border="0" cellspacing="1" cellpadding="0" style="cursor:default">
			</table>
		</td>
	</tr>
</table>
</body>
</html>