<%@ page contentType="text/html; charset=windows-874"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.iMed.iMedCore.utility.fix.FixDayOfWeek"%>
<%@ page import="com.bpk.dto.ResultFlag"%>
<%@ page import="com.bpk.dto.BpkClinicVO"%>
<%@ page import="com.bpk.dao.DAOFactory"%>
<%@ page import="com.bpk.dao.DoctorProfileDAO"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="inc/charset.jsp" flush="false"/>
<link type="text/css" rel="stylesheet" href="css/extend.css" />
<script type="text/javascript" src="js/coreobject/BpkEmployeeVO.js"></script>
<script type="text/javascript" src="js/iMedUtility.js"></script>
<script type="text/javascript" src="js/TableUtility.js"></script>
<script language="JavaScript">
<!--

function txtUsername_OnKeyUp()
{
	if(event.keyCode==13)
	{
		var aBpkEmployeeVO = top.aBpkEmployeeVO;
		aBpkEmployeeVO.employeeId = txtUsername.value;

		top.statusFrame.repWorkStatus("กำลังเรียกข้อมูลแพทย์...");
		top.editDoctorProfileJSPFrame.UCForm.UC.value = "readDoctorProfile";
		top.editDoctorProfileJSPFrame.UCForm.submit();
	}
}

function clearTableSlot()
{
	var tbl = ifrmSession.tblBpkEmployeeVO;
	for( ; tbl.rows.length > 0; )
		tbl.deleteRow(0);
}

function setSlotToForm()
{
	clearTableSlot();

	var listBpkEmployeeVO = top.listBpkEmployeeVO;
	if(listBpkEmployeeVO!=null)
	{
		for(var i=0; i<listBpkEmployeeVO.length; i++)
		{
			var tmpBpkEmployeeVO = listBpkEmployeeVO[i];
			btnAdd_OnClick(tmpBpkEmployeeVO);

			// Set ค่าให้ textbox ของ time
			setComboboxElementForDay(i, tmpBpkEmployeeVO.dayName);
			setComboboxElementForClinic(i, tmpBpkEmployeeVO.clinicDescription);
			setTexboxStartTime(i, tmpBpkEmployeeVO.startTime);
			setTexboxEndTime(i, tmpBpkEmployeeVO.endTime);
			setTexboxLimitNumAppoint(i, tmpBpkEmployeeVO.limitNumAppoint);
		}
	}
}

function setDataToForm()
{
	var aBpkEmployeeVO = top.aBpkEmployeeVO;

	txtUsername.value = aBpkEmployeeVO.employeeId;
	txtEmployeeName.value = aBpkEmployeeVO.employeeName;
	txtLicense.value = aBpkEmployeeVO.licenseNo;
	txtQualification.value = aBpkEmployeeVO.qualification;
	txtEducational.value = aBpkEmployeeVO.educational;
	txtInstitute.value = aBpkEmployeeVO.institute;
	txtBoard.value = aBpkEmployeeVO.board;
	txtSpecialty.value = aBpkEmployeeVO.specialty;
	txtOthers.value = aBpkEmployeeVO.others;

	txtUsername.disabled = true;
}

function btnResetDoctorProfile_OnClick()
{
	var aBpkEmployeeVO = top.aBpkEmployeeVO;
	top.listBpkEmployeeVO = new Array();

	aBpkEmployeeVO.employeeId = "";
	aBpkEmployeeVO.employeeName = "";
	aBpkEmployeeVO.licenseNo = "";
	aBpkEmployeeVO.qualification = "";
	aBpkEmployeeVO.educational = "";
	aBpkEmployeeVO.institute = "";
	aBpkEmployeeVO.board = "";
	aBpkEmployeeVO.specialty = "";
	aBpkEmployeeVO.others = "";

	setDataToForm();
	clearTableSlot();

	txtUsername.disabled = false;
}

function btnSaveDoctorProfile_OnClick()
{
	var aBpkEmployeeVO = top.aBpkEmployeeVO;

	aBpkEmployeeVO.employeeId = txtUsername.value;
	aBpkEmployeeVO.licenseNo = txtLicense.value;
	aBpkEmployeeVO.qualification = txtQualification.value;
	aBpkEmployeeVO.educational = txtEducational.value;
	aBpkEmployeeVO.institute = txtInstitute.value;
	aBpkEmployeeVO.board = txtBoard.value;
	aBpkEmployeeVO.specialty = txtSpecialty.value;
	aBpkEmployeeVO.others = txtOthers.value;

	var newSlotBpkEmployeeVO = top.newSlotBpkEmployeeVO;
	for(var i=0; i<runningId; i++)
	{
		try
		{
			var selDay = eval("ifrmSession.selDay"+i);
			var selClinic = eval("ifrmSession.selClinic"+i);
			var txtStartTimeHh = eval("ifrmSession.txtStartTimeHh"+i);
			var txtStartTimeMm = eval("ifrmSession.txtStartTimeMm"+i);
			var txtEndTimeHh = eval("ifrmSession.txtEndTimeHh"+i);
			var txtEndTimeMm = eval("ifrmSession.txtEndTimeMm"+i);
			var txtLimitNumAppoint = eval("ifrmSession.txtLimitNumAppoint"+i);

			if(chkAll.checked)
			{
				chk.checked = true;
			}
			else
			{
				chk.checked = false;
			}
		}
		catch (e)
		{
			// alert(e);
		}
	}

	top.statusFrame.repWorkStatus("กำลังบันทึกข้อมูลแพทย์...");
	top.editDoctorProfileJSPFrame.UCForm.UC.value = "saveDoctorProfile";
	top.editDoctorProfileJSPFrame.UCForm.submit();
}

function getComboboxElementForDay(id)
{
	var cmb = "<select id=\"selDay"+id+"\" style=\"height:24px;width:80px\">";
	cmb += ("<option value=\"<%=FixDayOfWeek.SUNDAY%>\" style=\"color:red;\">"+getDayNameByFixDayOfWeek(<%=FixDayOfWeek.SUNDAY%>, true)+"</option>");
	cmb += ("<option value=\"<%=FixDayOfWeek.MONDAY%>\">"+getDayNameByFixDayOfWeek(<%=FixDayOfWeek.MONDAY%>, true)+"</option>");
	cmb += ("<option value=\"<%=FixDayOfWeek.TUESDAY%>\">"+getDayNameByFixDayOfWeek(<%=FixDayOfWeek.TUESDAY%>, true)+"</option>");
	cmb += ("<option value=\"<%=FixDayOfWeek.WEDNESDAY%>\">"+getDayNameByFixDayOfWeek(<%=FixDayOfWeek.WEDNESDAY%>, true)+"</option>");
	cmb += ("<option value=\"<%=FixDayOfWeek.THURSDAY%>\">"+getDayNameByFixDayOfWeek(<%=FixDayOfWeek.THURSDAY%>, true)+"</option>");
	cmb += ("<option value=\"<%=FixDayOfWeek.FRIDAY%>\">"+getDayNameByFixDayOfWeek(<%=FixDayOfWeek.FRIDAY%>, true)+"</option>");
	cmb += ("<option value=\"<%=FixDayOfWeek.SATURDAY%>\" style=\"color:gray;\">"+getDayNameByFixDayOfWeek(<%=FixDayOfWeek.SATURDAY%>, true)+"</option>");
	cmb += "</select>";

	return cmb;
}

function setComboboxElementForDay(id, day)
{
	try
	{
		var selDay = eval("ifrmSession.selDay"+id);
		for(var i=0; i<selDay.options.length; i++)
		{
			if(selDay.options[i].text==day)
			{
				selDay.selectedIndex = i;
				break;
			}
		}
	}
	catch (e)
	{
		alert(e);
	}	
}

function getComboboxElementForClinic(id)
{
	var cmb = "<select id=\"selClinic"+id+"\" style=\"height:24px;width:200px\">";
<%
	
	DoctorProfileDAO aDao = DAOFactory.newDoctorProfileDAO();
	HashMap result = aDao.listAllClinic();
	if(result!=null && ResultFlag.STATUS_SUCCESS.equals(result.get(ResultFlag.STATUS)))
	{
		List listBpkClinicVO = (List)result.get(ResultFlag.RESULT_DATA);
		for(int i=0, sizei=listBpkClinicVO.size(); i<sizei; i++)
		{
			BpkClinicVO aBpkClinicVO = (BpkClinicVO)listBpkClinicVO.get(i);
%>
			cmb += "<option value=\"<%=aBpkClinicVO.getSpId()%>\"><%=aBpkClinicVO.getClinicDescription()%></option>";
<%
		}
	}
	
%>
	cmb += "</select>";

	return cmb;
}

function setComboboxElementForClinic(id, clinic)
{
	try
	{
		var selClinic = eval("ifrmSession.selClinic"+id);
		for(var i=0; i<selClinic.options.length; i++)
		{
			if(selClinic.options[i].text==clinic)
			{
				selClinic.selectedIndex = i;
				break;
			}
		}
	}
	catch (e)
	{
		alert(e);
	}	
}

function getTexboxStartTime(id)
{
	var txt = "<input type=\"text\" id=\"txtStartTimeHh"+id+"\" class=\"txtNumber\" style=\"width:24px\" maxlength=\"2\" onKeyPress=\"return isNumber(event.keyCode);\"/>";
	txt += ":<input type=\"text\" id=\"txtStartTimeMm"+id+"\" class=\"txtNumber\" style=\"width:24px\" maxlength=\"2\" onKeyPress=\"return isNumber(event.keyCode);\"/>";

	return txt;
}

function setTexboxStartTime(id, date)
{
	try
	{
		eval("ifrmSession.txtStartTimeHh"+id).value = date.substring(0, 2);
		eval("ifrmSession.txtStartTimeMm"+id).value = date.substring(3, 5);
	}
	catch (e)
	{
		alert(e);
	}
}

function getTexboxEndTime(id)
{
	var txt = "<input type=\"text\" id=\"txtEndTimeHh"+id+"\" class=\"txtNumber\" style=\"width:24px\" maxlength=\"2\" onKeyPress=\"return isNumber(event.keyCode);\"/>";
	txt += ":<input type=\"text\" id=\"txtEndTimeMm"+id+"\" class=\"txtNumber\" style=\"width:24px\" maxlength=\"2\" onKeyPress=\"return isNumber(event.keyCode);\"/>";

	return txt;
}

function setTexboxEndTime(id, date)
{
	try
	{
		eval("ifrmSession.txtEndTimeHh"+id).value = date.substring(0, 2);
		eval("ifrmSession.txtEndTimeMm"+id).value = date.substring(3, 5);
	}
	catch (e)
	{
		alert(e);
	}
}

function getTexboxLimitNumAppoint(id)
{
	var txt = "<input type=\"text\" id=\"txtLimitNumAppoint"+id+"\" class=\"txtNumber\" style=\"width:24px\" maxlength=\"3\" onKeyPress=\"return isNumber(event.keyCode);\"/>";

	return txt;
}

function setTexboxLimitNumAppoint(id, num)
{
	try
	{
		eval("ifrmSession.txtLimitNumAppoint"+id).value = num;
	}
	catch (e)
	{
		alert(e);
	}
}

var runningId = 0;

function btnAdd_OnClick(pBpkEmployeeVO)
{
	var tblBpkEmployeeVO = ifrmSession.tblBpkEmployeeVO;
	var row = tblBpkEmployeeVO.insertRow();
	row.style.height = "24px";
	row.rowid = runningId;

	var cell = row.insertCell();
	cell.style.width = "24px";
	cell.style.textAlign = "center";
	cell.style.verticalAlign = "middle";
	cell.innerHTML = "<input type=\"checkbox\" id=\"chk"+(runningId)+"\"/>";

	cell = row.insertCell();
	cell.style.width = "80px";
	cell.style.verticalAlign = "middle";
	cell.innerHTML = getComboboxElementForDay(runningId);

	cell = row.insertCell();
	cell.style.width = "200px";
	cell.style.verticalAlign = "middle";
	cell.innerHTML = getComboboxElementForClinic(runningId);

	cell = row.insertCell();
	cell.style.width = "80px";
	cell.style.verticalAlign = "middle";
	cell.style.textAlign = "right";
	cell.innerHTML = getTexboxStartTime(runningId);

	cell = row.insertCell();
	cell.style.width = "80px";
	cell.style.verticalAlign = "middle";
	cell.style.textAlign = "center";
	cell.innerHTML = getTexboxEndTime(runningId);

	cell = row.insertCell();
	cell.style.width = "100px";
	cell.style.verticalAlign = "middle";
	cell.style.textAlign = "right";
	cell.innerHTML = getTexboxLimitNumAppoint(runningId);

	runningId++;
	clearTableStyle(tblBpkEmployeeVO, false);
}

function chkAll_OnClick()
{
	for(var i=0; i<runningId; i++)
	{
		try
		{
			var chk = eval("ifrmSession.chk"+i);

			if(chkAll.checked)
			{
				chk.checked = true;
			}
			else
			{
				chk.checked = false;
			}
		}
		catch (e)
		{
			// alert(e);
		}
	}
}

function btnCopy_OnClick()
{
	btnAdd_OnClick();

	for(var i=runningId-2 ;; i--)
	{
		if(i-2<0)
			i = 0;

		try
		{
			var selDay = eval("ifrmSession.selDay"+i);
			var selClinic = eval("ifrmSession.selClinic"+i);
			var txtStartTimeHh = eval("ifrmSession.txtStartTimeHh"+i);
			var txtStartTimeMm = eval("ifrmSession.txtStartTimeMm"+i);
			var txtEndTimeHh = eval("ifrmSession.txtEndTimeHh"+i);
			var txtEndTimeMm = eval("ifrmSession.txtEndTimeMm"+i);
			var txtLimitNumAppoint = eval("ifrmSession.txtLimitNumAppoint"+i);

			var newSelDay = eval("ifrmSession.selDay"+(runningId-1));
			var newSelClinic = eval("ifrmSession.selClinic"+(runningId-1));
			var newTxtStartTimeHh = eval("ifrmSession.txtStartTimeHh"+(runningId-1));
			var newTxtStartTimeMm = eval("ifrmSession.txtStartTimeMm"+(runningId-1));
			var newTxtEndTimeHh = eval("ifrmSession.txtEndTimeHh"+(runningId-1));
			var newTxtEndTimeMm = eval("ifrmSession.txtEndTimeMm"+(runningId-1));
			var newTxtLimitNumAppoint = eval("ifrmSession.txtLimitNumAppoint"+(runningId-1));

			newSelDay.value = selDay.value;
			newSelClinic.value = selClinic.value;
			newTxtStartTimeHh.value = txtStartTimeHh.value;
			newTxtStartTimeMm.value = txtStartTimeMm.value;
			newTxtEndTimeHh.value = txtEndTimeHh.value;
			newTxtEndTimeMm.value = txtEndTimeMm.value;
			newTxtLimitNumAppoint.value = txtLimitNumAppoint.value;

			break;
		}
		catch (e)
		{
			// alert(e);
		}
	}
}

function btnRemove_OnClick()
{
	for(var i=0; i<runningId; i++)
	{
		try
		{
			var chk = eval("ifrmSession.chk"+i);
			var tbl = eval("ifrmSession.tblBpkEmployeeVO");

			if(chk.checked)
			{
				tbl.deleteRow(chk.parentNode.parentNode.rowIndex);
			}
		}
		catch (e)
		{
			// alert(e);
		}
	}
}

function btnBack_OnClick()
{
	var aBpkEmployeeVO = top.aBpkEmployeeVO;

	if(aBpkEmployeeVO!=null)
		top.location.href = "calendarMonth.jsp?employeeId="+aBpkEmployeeVO.employeeId;
	else
		top.location.href = "calendarMonth.jsp";
}

function btnFind_OnClick()
{
	top.location.href = "listDoctor.jsp";
}

function tabGeneral_onClick()
{
	tabGeneral.className = "tabBpkEnable";
	divGeneral.style.display = "";

	tabSession.className = "tabBpkDisable";
	divSession.style.display = "none";
}

function tabSession_onClick()
{
	tabGeneral.className = "tabBpkDisable";
	divGeneral.style.display = "none";

	tabSession.className = "tabBpkEnable";
	divSession.style.display = "";
}

function onLoad()
{
	var aBpkEmployeeVO = top.aBpkEmployeeVO;
	if(aBpkEmployeeVO!=null)
	{
		setDataToForm();
	}

	setSlotToForm();
	tabGeneral_onClick();
}

//-->
</script>
</head>

<body width="100%" marginwidth="0" marginheight="0" topmargin="0" leftmargin="0" onLoad="onLoad();">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="2">
	<tr>
		<td><jsp:include page="inc/header.inc.jsp" flush="false"/></td>
	</tr>
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr>
					<td id="tabGeneral" class="tabBpkEnable" style="width:144px" onClick="tabGeneral_onClick();">ข้อมูลทั่วไป</td>
					<td id="tabSession" class="tabBpkDisable" style="width:144px" onClick="tabSession_onClick();">ตารางออกตรวจ</td>
					<td width="*">&nbsp;</td>
				</tr>
			</table>
		<td>
	</tr>
	<tr>
		<td>
			<div id="divGeneral" class="divBpkRelative" style="display:none;height:320px" width="100%">
				<table border="0" cellspacing="0" cellpadding="2" width="100%">
					<tr><td style="vertical-align:middle">Username:</td><td><input type="text" id="txtUsername" placeholder="iMed Username" class="txtDisable" style="width:170px;height:24px;vertical-align:middle" 
					onKeyUp="txtUsername_OnKeyUp();" disabled/></td></tr>
					<tr><td style="vertical-align:middle">Name:</td><td><input type="text" id="txtEmployeeName" placeholder="Doctor Name" class="txtDisable" style="width:170px;height:24px;vertical-align:middle" 
					onKeyUp="txtUsername_OnKeyUp();" disabled/></td></tr>
					<tr><td style="vertical-align:middle">License:</td><td style="height:21px"><input type="text" id="txtLicense" placeholder="License Number" class="txtBorder" style="width:170px;height:24px;vertical-align:middle"/></td></tr>
					<tr><td style="vertical-align:middle">Qualification:</td><td style="height:21px"><input type="text" id="txtQualification" placeholder="Qualification" class="txtBorder" style="width:170px;height:24px;vertical-align:middle"/></td></tr>
					<tr><td style="vertical-align:middle">Educational:</td><td style="height:21px"><input type="text" id="txtEducational" placeholder="Educational" class="txtBorder" style="width:170px;height:24px;vertical-align:middle"/></td></tr>
					<tr><td style="vertical-align:middle">Institute:</td><td style="height:21px"><input type="text" id="txtInstitute" placeholder="Institute" class="txtBorder" style="width:170px;height:24px;vertical-align:middle"/></td></tr>
					<tr><td style="vertical-align:middle">Board:</td><td style="height:21px"><input type="text" id="txtBoard" placeholder="Board" class="txtBorder" style="width:170px;height:24px;vertical-align:middle"/></td></tr>
					<tr><td style="vertical-align:middle">Specialty:</td><td style="height:21px"><input type="text" id="txtSpecialty" placeholder="Specialty" class="txtBorder" style="width:170px;height:24px;vertical-align:middle"/></td></tr>
					<tr><td style="vertical-align:middle">Others:</td><td style="height:21px"><input type="text" id="txtOthers" placeholder="Others" class="txtBorder" style="width:170px;height:24px;vertical-align:middle"/></td></tr>
				</table>
			</div>
			<div id="divSession" class="divBpkRelative" style="display:none;height:320px" width="100%">
				<table border="0" cellspacing="0" cellpadding="2" width="100%">
					<tr>
						<td colspan="3" style="width:564px">
							<table border="0" cellspacing="0" cellpadding="0" width="100%" style="border-top: solid 1px #CDCDCD; height:21px;width:540px">
							<tr align="center" class="contentHeader">
								<td style="width:24px"><input type="checkbox" id="chkAll" onClick="chkAll_OnClick();"></td>
								<td style="width:80px"><b>วันในสัปดาห์</b></td>
								<td style="width:220px"><b>ศูนย์การแพทย์</b></td>
								<td style="width:80px"><b>เริ่มเวลา</b></td>
								<td style="width:80px"><b>ถึงเวลา</b></td>
								<td style="width:100px"><b>ตรวจได้(คน)</b></td>
							</tr>
							</table>
						</td>
						<td rowspan="3" style="vertical-align:top"><div id="btnAdd" onClick="btnAdd_OnClick(null);" class="btnStyleAddRemove" style="vertical-align:middle;width:72px;">เพิ่ม</div>&nbsp;<div id="btnRemove" onClick="btnRemove_OnClick();" class="btnStyleAddRemove" style="vertical-align:middle;width:72px;">ลบ</div>&nbsp;<div id="btnCopy" onClick="btnCopy_OnClick();" class="btnStyleAddRemove" style="vertical-align:middle;width:72px;">คัดลอก</div></td>
					</tr>
					<tr>
						<td colspan="3" style="width:540px"><iframe id="ifrmSession" src="listIfrmSession.jsp" width="100%" style="height:100%" frameborder="no"></iframe></td>
					</tr>
				</table>
			</div>
		<td>
	</tr>
	<tr>
		<td>
			<table border="0" cellspacing="0" cellpadding="2" width="100%" height="100%">
					<tr>
					<td style="vertical-align:middle; width:75px"><div id="btnBack" onClick="btnBack_OnClick();" class="btnStyleBackAfterSearch" style="vertical-align:middle;width:72px;"><center><img src="css/back.png"/></center></div></td>
					<td style="vertical-align:middle; width:75px"><div id="btnFind" onClick="btnFind_OnClick();" class="btnStyleBackAfterSearch" style="vertical-align:middle;width:72px;"><center><img src="css/find.png"/></center></div></td>
					<td style="vertical-align:middle; width:*">
						<input id="btnSaveDoctorProfile" value="บันทึก" type="button" class="btnStyle" style="vertical-align:middle;width:72px;" onClick="btnSaveDoctorProfile_OnClick();"/>&nbsp;
						<input id="btnResetDoctorProfile" value="ล้าง" type="button" class="btnStyle" style="vertical-align:middle;width:72px;" onClick="btnResetDoctorProfile_OnClick();"/></td>
					</tr>
			</table>
		</td>
	</tr>
</table>
 </body>
</html>
