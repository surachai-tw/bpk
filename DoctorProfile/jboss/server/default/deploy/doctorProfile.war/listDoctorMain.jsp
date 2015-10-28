<%@ page contentType="text/html; charset=windows-874"%>
<%@ page import="com.bpk.utility.BpkUtility"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="inc/charset.jsp" flush="false"/>
<script type="text/javascript" src="js/bpkUtility.js"></script>
<link type="text/css" rel="stylesheet" href="css/extend.css" />

<script language="JavaScript">
<!--

function onLoad()
{
<%
	String filter_employeeName = (String)session.getAttribute("filter_employeeName");
	String filter_clinic = (String)session.getAttribute("filter_clinic");
	String filter_specialty = (String)session.getAttribute("filter_specialty");
	String filter_optionWithSchedule = (String)session.getAttribute("filter_optionWithSchedule");

	String filter_searchPage = (String)session.getAttribute("filter_searchPage");
	BpkUtility.printDebug(this, "Get value "+filter_searchPage+" from session 'filter_searchPage'");

	String filter_searchCount = (String)session.getAttribute("filter_searchCount");
%>
	var tmp_listDoctor_txtDoctor = "<%=filter_employeeName!=null ? filter_employeeName : ""%>";
	var tmp_listDoctor_txtClinic = "<%=filter_clinic!=null ? filter_clinic : ""%>";
	var tmp_listDoctor_txtSpecialty = "<%=filter_specialty!=null ? filter_specialty : ""%>";
	var tmp_radoptionWithSchedule = "<%=filter_optionWithSchedule!=null ? filter_optionWithSchedule : ""%>";

	var tmp_listDoctor_cmbPage = "<%=filter_searchPage!=null ? filter_searchPage : ""%>";
	var tmp_listDoctor_cmbLimitPage = "<%=filter_searchCount!=null ? filter_searchCount : ""%>";

	listDoctor_txtDoctor.value = tmp_listDoctor_txtDoctor;
	listDoctor_txtClinic.value = tmp_listDoctor_txtClinic;
	listDoctor_txtSpecialty.value = tmp_listDoctor_txtSpecialty;

	if(tmp_radoptionWithSchedule!=null)
	{
		radoptionWithSchedule.checked = true;
	}
	else
	{
		radoptionWithoutSchedule.checked = true;
	}
	// alert("radoptionWithoutSchedule = "+radoptionWithoutSchedule.checked);

	listDoctor_findDoctor();

	// ส่วนนี้ ต้องการให้เลือกหน้าที่เคยเลือกไว้ให้ด้วย แต่ยังไม่ work
	try
	{
		listDoctor_cmbPage.value = (tmp_listDoctor_cmbPage!=null && tmp_listDoctor_cmbPage!="" ? tmp_listDoctor_cmbPage : 0);	
	}
	catch (e)
	{
		try
		{
			for(var i=0; i<listDoctor_cmbPage.options.length; i++)
			{
				if(tmp_listDoctor_cmbPage==listDoctor_cmbPage.options[i].value)
				{
					listDoctor_cmbPage.selectedIndex = i;
				}
			}
		}
		catch (e2)
		{
			alert(e2);
		}
	}

	try
	{
		listDoctor_cmbLimitPage.value = (tmp_listDoctor_cmbLimitPage!=null && tmp_listDoctor_cmbLimitPage!="" ? tmp_listDoctor_cmbLimitPage : "20");
	}
	catch (e)
	{
	}
}

function listDoctor_findDoctor()
{
	top.filter_employeeName = listDoctor_txtDoctor.value;
	top.filter_clinic = listDoctor_txtClinic.value;
	top.filter_specialty = listDoctor_txtSpecialty.value;
	top.filter_optionWithSchedule = radoptionWithSchedule.value;

	try
	{
		top.filter_searchPage = listDoctor_cmbPage.value;
	}
	catch (e)
	{
		try
		{
			// การขอค่าแบบนี้ใช้กับ IE แบบไม่เลือก Compatibility View ไม่ได้
			top.filter_searchPage = listDoctor_cmbPage.options[listDoctor_cmbPage.selectedIndex].value;
		}
		catch (f)
		{
			alert("searchPage, e = "+e+", f = "+f);
		}
	}
	
	try
	{
		top.filter_searchCount = listDoctor_cmbLimitPage.value;
	}
	catch (e)
	{
		try
		{
			top.filter_searchCount = listDoctor_cmbLimitPage.options[listDoctor_cmbLimitPage.selectedIndex].value;
		}
		catch (f)
		{
			alert("searchCount, e = "+e+", f = "+f);
		}
	}

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
	try
	{
		listDoctor_txtDoctor.value = "";
		listDoctor_txtDoctor.employee_eid = "";
		listDoctor_txtClinic.value = "";
		listDoctor_txtSpecialty.value = "";
		radoptionWithSchedule.checked = true;

		listDoctor_cmbPage.selectedIndex = 0;
		listDoctor_cmbLimitPage.selectedIndex = 1;

		top.filter_employeeName = listDoctor_txtDoctor.value;
		top.filter_clinic = listDoctor_txtClinic.value;
		top.filter_specialty = listDoctor_txtSpecialty.value;
		top.filter_optionWithSchedule = radoptionWithSchedule.value;

		try
		{
			top.filter_searchPage = listDoctor_cmbPage.options[listDoctor_cmbPage.selectedIndex].value;
		}
		catch (e)
		{
		}
		
		try
		{
			top.filter_searchCount = listDoctor_cmbLimitPage.options[listDoctor_cmbLimitPage.selectedIndex].value;
		}
		catch (e)
		{
		}

		top.statusFrame.repWorkStatus("ล้างตัวกรอง...");
		top.listDoctorJSPFrame.UCForm.UC.value = "resetFilterListDoctor";
		top.listDoctorJSPFrame.UCForm.submit();
	}
	catch (e)
	{
		alert(e);
	}
}

function listDoctor_txtDoctor_OnKeyUp()
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
	var tbl = null;
	if(isCompatView())
	{
		tbl = ifrmListDoctor.tblBpkEmployeeVO;
	}
	else
	{
		try
		{
			var ifrmdocument = ifrmListDoctor.contentWindow;
			tbl = ifrmdocument.getElementById("tblBpkEmployeeVO");
		}
		catch (e)
		{
			tbl = ifrmListDoctor.tblBpkEmployeeVO;
		}
	}
	for( ; tbl.rows.length > 0; )
		tbl.deleteRow(0);
}

var lineChildSetItem = "lineChildSetItem";
var lineOdd = "lineOdd";
var lineEven = "lineEven";

function showListBpkEmployeeVO()
{
	clearTableData();
	var tbl = null;
	if(isCompatView())
	{
		tbl = ifrmListDoctor.tblBpkEmployeeVO;
	}
	else
	{
		try
		{
			var ifrmdocument = ifrmListDoctor.contentWindow;
			tbl = ifrmdocument.getElementById("tblBpkEmployeeVO");
		}
		catch (e)
		{
			tbl = ifrmListDoctor.tblBpkEmployeeVO;
		}
	}
	var listBpkEmployeeVO = top.listBpkEmployeeVO;
	var size = listBpkEmployeeVO.length;
	var previousEmpName = "";
	// var isReNew = true;
	var lineStyle = lineOdd;
	for(var i=0; i<size; i++)
	{
		var aBpkEmployeeVO = listBpkEmployeeVO[i];
		var row = tbl.insertRow();
		row.aBpkEmployeeVO = aBpkEmployeeVO;
		row.employee_id = aBpkEmployeeVO.employeeId;
		row.height = 21;

		/*** ReNew ยังไม่ต้องใช้ 
		if(i>0)
		{
			if(listBpkEmployeeVO[i-1].employeeId == row.employee_id)
				isReNew = false;
			else
				isReNew = true;
		}
		*/

		var cell = row.insertCell();
		if(previousEmpName==aBpkEmployeeVO.employeeName)
		{
			cell.innerText = "";
		}
		else
		{
			cell.innerHTML = "<a href=\"calendarMonth.jsp?employeeId="+aBpkEmployeeVO.employeeId+"\" target=\"_top\">"+aBpkEmployeeVO.employeeName+"</a>";
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

		/*** ReNew ยังไม่ต้องใช้ 
		if(isReNew)
		{
			if(lineStyle==lineOdd)
				lineStyle = lineEven;
			else 
				lineStyle = lineOdd;
		}
		*/
		row.className = i%2==0 ? lineOdd : lineEven;

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
<body marginwidth="0" marginheight="0" topmargin="0" leftmargin="0" onLoad="onLoad();">

<!-- table tab -->
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="2">
	<tr>
		<td><jsp:include page="inc/header.inc.jsp" flush="false"/></td>
	</tr>
	<tr>
		<td>
			<table width="85%" class="bgFormLabelTop" border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td style="vertical-align:middle; height:32px">&nbsp;
						แพทย์:&nbsp;<input id="listDoctor_txtDoctor" type="text" placeholder=" ชื่อ - นามสกุล " class="txtBorder" style="width:170px;height:24px;vertical-align:middle" employee_eid="" onKeyUp="listDoctor_txtDoctor_OnKeyUp();"/>&nbsp;&nbsp;&nbsp;&nbsp;
						ศูนย์การแพทย์:&nbsp;<input id="listDoctor_txtClinic" type="text" placeholder=" ศูนย์การแพทย์ " class="txtBorder" style="width:170px;height:24px;vertical-align:middle" onKeyUp="listDoctor_txtClinic_OnKeyUp();"/>&nbsp;&nbsp;&nbsp;&nbsp;
						ความเชี่ยวชาญ:&nbsp;<input id="listDoctor_txtSpecialty" type="text" placeholder=" ความเชี่ยวชาญ " class="txtBorder" style="width:170px;height:24px;vertical-align:middle" onKeyUp="listDoctor_txtSpecialty_OnKeyUp();"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="radoptionWithSchedule" id="radoptionWithSchedule" checked/>เฉพาะที่มีตาราง&nbsp;&nbsp;
						<input type="radio" name="radoptionWithSchedule" id="radoptionWithoutSchedule"/>ทั้งหมด&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="listDoctor_btnSearch" type="button" class="btnStyleL" value="ค้น" onclick="listDoctor_btnSearch_OnClick();"/>&nbsp;
			<input id="listDoctor_btnClearKeyWord" type="button" class="btnStyleL" value="ล้าง" onclick="listDoctor_btnClearKeyWord_onclick();"/>&nbsp;
			<input type="button" id="btnFirst" value="|<" onClick="btnFirst_onClick();"  class="btnStyleNavigator" disabled/>
			<input type="button" id="btnPrev" onClick="btnPrev_onClick();" value="<"  class="btnStyleNavigator" disabled/>
			<select id="listDoctor_cmbPage" onChange="listDoctor_cmbPage_onChange();">
				<jsp:include page="inc/cmbPage.inc.jsp" flush="false"/>
			</select>
			<select id="listDoctor_cmbLimitPage" onChange="listDoctor_cmbLimitPage_onChange();">
				<jsp:include page="inc/cmbLimitPage.inc.jsp" flush="true" />
			</select>
			<input type="button" id="btnNext" onClick="btnNext_onClick();" value=">"  class="btnStyleNavigator" disabled/>
			<input type="button" id="btnLast" value=">|" onClick="btnLast_onClick();"  class="btnStyleNavigator" disabled/>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td style="height:21px">
			<table width="100%" style="border-top: solid 1px #CDCDCD; height:21px" cellspacing="0" cellpadding="0">
				<tr align="center" class="contentHeader">
					<td width="16%"><b>แพทย์</b></td>
					<td width="16%"><b>ศูนย์การแพทย์</b></td>
					<td width="29%"><b>ความเชี่ยวชาญ</b></td>
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