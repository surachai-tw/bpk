<%@ page contentType="text/html; charset=windows-874"%>
<%@ page import="com.iMed.iMedCore.utility.*"%>
<%@ page import="com.iMed.iMedCore.utility.fix.*"%>
<%@ page import="com.iMed.iMedCore.utility.feature.IMedFeature"%>
<html>
<head>
<jsp:include page="inc/charset.jsp" flush="false"/>
<link type="text/css" rel="stylesheet" href="css/extend.css" />
<!-- script language="JavaScript" src="../../js/utility.js"></script-->
<!-- script language="JavaScript" src="../../js/TextField.js"></script-->

<script language="JavaScript">
<!--

function listDoctor_findDoctor()
{
	top.filter_empId = listDoctor_txtDoctorEid.employee_eid;
	top.filter_clinic = listDoctor_txtClinic.value;
	top.filter_specialty = listDoctor_txtSpecialty.value;
	top.filter_optionWithSchedule = radoptionWithSchedule.value;
	top.filter_searchPage = listDoctor_cmbPage.options[listDoctor_cmbPage.selectedIndex].value;
	top.filter_searchCount = listDoctor_cmbLimitPage.options[listDoctor_cmbLimitPage.selectedIndex].value;

	top.statusFrame.repWorkStatus("กำลังทำการค้นหารายชื่อแพทย์...");
	top.listDoctorJSPFrame.UCForm.UC.value = "listDoctor";
	top.listDoctorJSPFrame.UCForm.submit();
}

function listDoctor_btnSearch_OnClick()
{
	listDoctor_cmbPage.selectedIndex = 0;

	listDoctor_findDoctor();
}

function listDoctor_btnClearKeyWord_onclick()
{
	var txtEmpId = document.getElementById("listDoctor_txtDoctorEid");
	var txtClinic = document.getElementById("listDoctor_txtClinic");
	var txtSpecialty = document.getElementById("listDoctor_txtSpecialty");
	var radoptionWithSchedule = document.getElementById("radoptionWithSchedule");
	var cmbLimitPage = document.getElementById("cmbLimitPage");

	try
	{
		txtEmpId.value = "";
		txtClinic.value = "";
		txtSpecialty.value = "";
		radoptionWithSchedule.checked = true;

		cmbPage.selectedIndex = 0;
		cmbLimitPage.selectedIndex = 1;
	}
	catch (e)
	{
		alert(e);
	}
}

function listDoctor_txtDoctorEid_OnKeyUp()
{
	if(event.keyCode==13)
	{
		listDoctor_btnSearch_OnClick();
	}
}

function listDoctor_txtClinic_OnKeyUp()
{
	if(event.keyCode==13)
	{
		listDoctor_btnSearch_OnClick();
	}
}

function listDoctor_txtSpecialty_OnKeyUp()
{
	if(event.keyCode==13)
	{
		listDoctor_btnSearch_OnClick();
	}
}

function clearTableData()
{
	var tbl = ifrmListDoctor.tblBpkEmployeeVO;
	// var tbl = document.getElementById("ifrmListDoctor.tblBpkEmployeeVO");
	for( ; tbl.rows.length > 0; )
		tbl.deleteRow(0);
}

var lineChildSetItem = "lineChildSetItem";
var lineOdd = "lineOdd";
var lineEven = "lineEven";

function showListBpkEmployeeVO()
{
	clearTableData();
	var tblBpkEmployeeVO = ifrmListDoctor.tblBpkEmployeeVO;
	var listBpkEmployeeVO = top.listBpkEmployeeVO;
	var size = listBpkEmployeeVO.length;
	var previousEmpName = "";
	var isReNew = true;
	var lineStyle = lineOdd;
	for(var i=0; i<size; i++)
	{
		var aBpkEmployeeVO = listBpkEmployeeVO[i];
		var row = tblBpkEmployeeVO.insertRow();
		row.aBpkEmployeeVO = aBpkEmployeeVO;
		row.employee_id = aBpkEmployeeVO.employeeId;
		row.height = 21;

		if(i>0)
		{
			if(listBpkEmployeeVO[i-1].employeeId == row.employee_id)
				isReNew = false;
			else
				isReNew = true;
		}

		var cell = row.insertCell();
		if(previousEmpName==aBpkEmployeeVO.employeeName)
		{
			cell.innerText = "";
		}
		else
		{
			cell.innerHTML = aBpkEmployeeVO.employeeName;
		}
		cell.width = "16%";

		cell = row.insertCell();
		if(previousEmpName==aBpkEmployeeVO.employeeName)
		{
			cell.innerText = "";
		}
		else
		{
			cell.innerHTML = aBpkEmployeeVO.clinicDescription;
		}
		cell.width = "16%";

		cell = row.insertCell();
		if(previousEmpName==aBpkEmployeeVO.employeeName)
		{
			cell.innerText = "";
		}
		else
		{
			cell.innerHTML = aBpkEmployeeVO.specialty;
		}
		cell.width = "29%";

		cell = row.insertCell();
		cell.innerHTML = aBpkEmployeeVO.dayName;
		cell.width = "18%";

		cell = row.insertCell();
		cell.innerHTML = aBpkEmployeeVO.startTime;
		cell.width = "6%";
		cell.style.textAlign = "right";

		cell = row.insertCell();
		cell.innerHTML = aBpkEmployeeVO.endTime;
		cell.width = "6%";
		cell.style.textAlign = "right";

		cell = row.insertCell();
		cell.innerText = " ";
		cell.width = "10%";

		if(isReNew)
		{
			if(lineStyle==lineOdd)
				lineStyle = lineEven;
			else 
				lineStyle = lineOdd;
		}
		row.className = lineStyle;

		previousEmpName = aBpkEmployeeVO.employeeName;
	}
}

function btnFirst_onClick()
{
	listDoctor_cmbPage.selectedIndex = 0;

	listDoctor_findDoctor();
}

function btnPrev_onClick()
{
	--listDoctor_cmbPage.selectedIndex;

	listDoctor_findDoctor();
}

function listDoctor_cmbPage_onChange()
{
	listDoctor_findDoctor();
}

function listDoctor_cmbLimitPage_onChange()
{
}

function btnNext_onClick()
{
	++listDoctor_cmbPage.selectedIndex;

	listDoctor_findDoctor();
}

function btnLast_onClick()
{
	listDoctor_cmbPage.selectedIndex = listDoctor_cmbPage.options.length-1;

	listDoctor_findDoctor();
}

function setEableNavigatorNext(enabled)
{
	btnNext.disabled = !enabled;
	btnLast.disabled = !enabled;
}

function setEableNavigatorPrev(enabled)
{
	btnPrev.disabled = !enabled;
	btnFirst.disabled = !enabled;
}

//-->
</script>

</head>
<body marginwidth="0" marginheight="0" topmargin="0" leftmargin="0">

<!-- table tab -->
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr width="100%" >
					<td id="tdListDoctor" class="bgHilite" width="250px" height="20px">&nbsp;<b>ตารางออกตรวจแพทย์</b>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" style="height:40px" border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td style="vertical-align:middle; height:40px">&nbsp;
						แพทย์:&nbsp;<input id="listDoctor_txtDoctorEid" type="text" class="txtAutoComplete" style="width:170px" employee_eid="" onKeyUp="listDoctor_txtDoctorEid_OnKeyUp();">&nbsp;&nbsp;&nbsp;&nbsp;
						คลินิก:&nbsp;<input id="listDoctor_txtClinic" type="text" class="txtBorder" style="width:170px" onKeyUp="listDoctor_txtClinic_OnKeyUp();">&nbsp;&nbsp;&nbsp;&nbsp;
						ความชำนาญ:&nbsp;<input id="listDoctor_txtSpecialty" type="text" class="txtBorder" style="width:170px" onKeyUp="listDoctor_txtSpecialty_OnKeyUp();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="radoptionWithSchedule" id="radoptionWithSchedule" checked>เฉพาะที่มีตาราง&nbsp;&nbsp;
						<input type="radio" name="radoptionWithSchedule" id="radoptionWithoutSchedule">ทั้งหมด&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="listDoctor_btnSearch" type="button" class="btnStyleL" value="ค้น" onclick="listDoctor_btnSearch_OnClick();">&nbsp;
			<input id="listDoctor_btnClearKeyWord" type="button" class="btnStyleL" value="ล้าง" onclick="listDoctor_btnClearKeyWord_onclick();">&nbsp;
			<input type="button" id="btnFirst" value="|<" onClick="btnFirst_onClick();"  class="btnStyleS" disabled>
			<input type="button" id="btnPrev" onClick="btnPrev_onClick();" value="<"  class="btnStyleS" disabled>
			<select id="listDoctor_cmbPage" onChange="listDoctor_cmbPage_onChange();">
				<jsp:include page="inc/cmbPage.inc.jsp" flush="false"/>
			</select>
			<select id="listDoctor_cmbLimitPage" onChange="listDoctor_cmbLimitPage_onChange();">
				<jsp:include page="inc/cmbLimitPage.inc.jsp" flush="true" />
			</select>
			<input type="button" id="btnNext" onClick="btnNext_onClick();" value=">"  class="btnStyleS" disabled>
			<input type="button" id="btnLast" value=">|" onClick="btnLast_onClick();"  class="btnStyleS" disabled>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td style="height:21px">
			<table width="100%" style="border-top: solid 1px #CDCDCD; height:21px" cellspacing="0" cellpadding="0">
				<tr align="center" class="contentHeader">
					<td width="16%"><b>แพทย์</b></td>
					<td width="16%"><b>คลินิก</b></td>
					<td width="29%"><b>ความชำนาญ</b></td>
					<td width="18%"><b>วันในสัปดาห์</b></td>
					<td width="6%"><b>เริ่มเวลา</b></td>
					<td width="6%"><b>ถึงเวลา</b></td>
					<td width="*">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
		<tr>
		<td class="bgForm" style="padding-top:2px; height:100%">
			<div id="divListDoctor" style="height:100%">
				<iframe id="ifrmListDoctor" src="listIfrmDoctor.jsp" width="100%" style="height:100%" frameborder="no"></iframe>
			</div>
		</td>
	</tr>
</table>

</body>
</html>