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

//**
//tab menu
function onload()
{
	tabHilight(tdListDoctor);
	top.statusFrame.repStatusSuccess();
}

/*เคลียค่าสำหรับ onover onout
function onload()
{
	divListDoctor.style.backgroundColor = "white";
	divListDoctor.style.color = "black";
	tdListVisitAssignDoctor.style.backgroundColor = "white";
	tdListVisitAssignDoctor.style.color = "black";
}*/

function tabOnMouseOver(obj)
{
	/*
	if(obj.style.backgroundColor != "#637DB5")
	{
		obj.style.backgroundColor = "#6699FF";
		tdListVisitAssignDoctor.style.color = "black";
	}
	*/
}

function tabOnMousetOut(obj)
{
	/*
	if(obj.style.backgroundColor != "#637DB5")
	{
		obj.style.backgroundColor = "#FFFFFF";
		tdListVisitAssignDoctor.style.color = "black";
	}
	*/
}

function tabHilight(objId)
{
	//tdListDoctor.style.backgroundColor = "#637DB5";
	//tdListDoctor.style.color = "white";
	//tdListVisitAssignDoctor.style.backgroundColor = "white";
	//tdListVisitAssignDoctor.style.color = "black";
}
//**
//end tab menu
function ifrmDiagnosisAndOperationAutoComplete_OnBlur()
{
	ifrmDiagnosisAndOperationAutoComplete.mainFrame.findEmployeeMain_hide();
	ifrmSubDiagnosisAndOperationAutoComplete.mainFrame.findDiagAndOperationSub_hide();
}

function listDoctor_btnSearch_OnClick()
{
	var txtEmpId = document.getElementById("listDoctor_txtDoctorEid");
	var txtClinic = document.getElementById("listDoctor_txtClinic");
	var txtSpecialty = document.getElementById("listDoctor_txtSpecialty");

	parent.filter_empId = txtEmpId.employee_eid;
	parent.filter_clinic = txtClinic.value;
	parent.filter_specialty = txtSpecialty.value;
	parent.filter_optionWithSchedule = radoptionWithSchedule.value;
//	parent.search_page = listPatientInWard_objNavigator.getSearchPage();
//	parent.search_count = listPatientInWard_objNavigator.getSearchCount();
	
	top.statusFrame.repWorkStatus("กำลังทำการค้นหารายชื่อแพทย์...");
	top.listDoctorJSPFrame.UCForm.UC.value = "listDoctor";
	top.listDoctorJSPFrame.UCForm.submit();
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
	for( ; tbl.rows.length > 1; )
		tbl.deleteRow(1);
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
		cell.width = "18%";

		cell = row.insertCell();
		if(previousEmpName==aBpkEmployeeVO.employeeName)
		{
			cell.innerText = "";
		}
		else
		{
			cell.innerHTML = aBpkEmployeeVO.clinicDescription;
		}
		cell.width = "18%";

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
		cell.width = "6%";

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

//-->
</script>

</head>
<body marginwidth="0" marginheight="0" topmargin="0" leftmargin="0" onload="onload();">

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
						<input type="radio" name="radoptionWithSchedule" id="radoptionWithoutSchedule">ทั้งหมด&nbsp;&nbsp;&nbsp;&nbsp;
						<input id="listDoctor_btnSearch" type="button" class="btnStyleL" value="ค้น" onclick="listDoctor_btnSearch_OnClick();">&nbsp;
						<input id="listDoctor_btnClearKeyWord" type="button" class="btnStyleL" value="ล้าง" onclick="listDoctor_btnClearKeyWord_onclick();">&nbsp;
					</td>
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