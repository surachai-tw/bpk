<%@ page contentType="text/html; charset=WINDOWS-874"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.iMed.iMedCore.utility.HttpSessionVariable"%>
<%@ page import="com.iMed.iMedCore.utility.Utility"%>
<%@ page import="com.iMed.iMedCore.utility.fix.FixAppointmentStatus"%>
<%@ page import="com.iMed.iMedCore.utility.fix.FixAppointmentType"%>
<%@ page import="com.iMed.iMedCore.utility.fix.FixAppointmentMethod"%>
<%@ page import="com.bpk.utility.BpkUtility"%>
<%@ page import="com.bpk.dao.DAOFactory"%>
<%@ page import="com.bpk.dao.DoctorProfileDAO"%>
<%@ page import="com.bpk.dto.ResultFlag"%>
<%@ page import="com.bpk.dto.BpkEmployeeVO"%>
<%@ page import="com.bpk.dto.BpkClinicVO"%>
<%
	try
	{
%>
<html>
<head>
<%
		String employeeId = request.getParameter("employeeId");
		String viewDate = request.getParameter("viewDate");
		BpkUtility.printDebug(this, "employeeId = "+employeeId);
		BpkUtility.printDebug(this, "viewDate = "+viewDate);

		// �͢����Ţͧ Employee
		DoctorProfileDAO aDAO = DAOFactory.newDoctorProfileDAO();
		HashMap resultDetail = aDAO.getEmployeeDetail(employeeId);
		BpkEmployeeVO aBpkEmployeeVO = null;
		if(ResultFlag.STATUS_SUCCESS.equals(resultDetail.get(ResultFlag.STATUS)))
		{
			aBpkEmployeeVO = (BpkEmployeeVO)resultDetail.get(ResultFlag.RESULT_DATA);
		}

		Calendar aCal = Calendar.getInstance(new Locale("en", "US"));
		String nowDate = Utility.getDateStringFromDate(aCal.getTime());
		BpkUtility.printDebug(this, "nowDate = "+nowDate);
		aCal.setLenient(true);
		// ����Ѻ���ʴ��ŷ����ǻ�ԷԹ
		Calendar aCalMonth = Calendar.getInstance(new Locale("en", "US")); 
		aCalMonth.setLenient(true);
		if(viewDate!=null && viewDate.length()==10)
		{
			aCal.set(Calendar.YEAR, Integer.parseInt(viewDate.substring(0, 4)));
			aCal.set(Calendar.MONTH, Integer.parseInt(viewDate.substring(5, 7))-1);
			aCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(viewDate.substring(8)));

			aCalMonth.setTime(aCal.getTime());
		}
%>
  <title> �Ѵ������ -  <%=aBpkEmployeeVO!=null ? aBpkEmployeeVO.getEmployeeName() : ""%></title>
  <jsp:include page="inc/charset.jsp" flush="false"/>
  <link type="text/css" rel="stylesheet" href="css/extend.css" />
  <script type="text/javascript" src="js/coreobject/BpkPatientVO.js"></script>
  <script type="text/javascript" src="js/iMedUtility.js"></script>
  <script type="text/javascript" src="js/bpkUtility.js"></script>
  <script type="text/javascript">
  <!--

	function btnSaveAppointment_OnClick()
	{
		var aBpkAppointmentVO = top.aBpkAppointmentVO;
		var aBpkPatientVO = top.aBpkPatientVO;

		// ��ǹ�ͧ PatientID
		aBpkAppointmentVO.patientId = aBpkPatientVO.patientId;

		aBpkAppointmentVO.baseSpId = selClinicId.value;
		aBpkAppointmentVO.basicAdvice = txaAppointAdvice.value;
		aBpkAppointmentVO.note = txaAppointNote.value;
		
		// �ѧ��������ǹ�ͧ��� validate �ѹ��� 
		aBpkAppointmentVO.appointDate = (txtAppointDateYYYY.value-543)+'-'+txtAppointDateMM.value+'-'+txtAppointDateDD.value;
		aBpkAppointmentVO.appointTime = txtAppointTimeHH.value+':'+txtAppointTimeMM.value+':00';

		aBpkAppointmentVO.doctorEid = "<%=employeeId%>";
		aBpkAppointmentVO.doctorAssignerEid = "<%=employeeId%>";
		aBpkAppointmentVO.fixAppointmentStatusId = "<%=FixAppointmentStatus.INCOMPLETE%>";
		aBpkAppointmentVO.fixAppointmentTypeId = "<%=FixAppointmentType.SINGLE_APPOINTMENT%>";
		aBpkAppointmentVO.fixAppointmentMethodId = "<%=FixAppointmentMethod.TELEPHONE%>";

		top.statusFrame.repWorkStatus("���ѧ�ѹ�֡�����š�ùѴ����...");
		top.makeAppointmentJSPFrame.UCForm.UC.value = "saveAppointment";
		top.makeAppointmentJSPFrame.UCForm.submit();
	}

	function btnAppointNoteHelp_onClick(btn)
	{
		txaAppointNote.value = Trim(txaAppointNote.value + ' ' + btn.title);
	}

	function btnAppointAdviceHelp_onClick(btn)
	{
		txaAppointAdvice.value = Trim(txaAppointAdvice.value + ' ' + btn.title);
	}

	function btnClearKeyWord_onclick()
	{
		txtPatientName.value = "";
		top.aBpkPatientVO = new BpkPatientVO();

		setPatientDataToForm();
	}

	function btnSearch_OnClick()
	{
		findPatient();
	}

	function setPatientDataToForm()
	{
		var aBpkPatientVO = top.aBpkPatientVO;

		lblPatientHn.innerText = aBpkPatientVO.hn;
		lblPatientName.innerText = aBpkPatientVO.patientName;
		lblPatientBirthdate.innerText = aBpkPatientVO.birthdate;
		lblPatientAge.innerText = aBpkPatientVO.age;

		lblPatientTelephone.innerText = aBpkPatientVO.telephone;
		lblPatientPid.innerText = aBpkPatientVO.pid;
		lblPatientNation.innerText = aBpkPatientVO.nation;
		lblPatientRace.innerText = aBpkPatientVO.race;
	}

	function txtPatientName_onKeyUp()
	{
		if(event.keyCode==13)
		{
			findPatient();
		}
	}

	function findPatient()
	{
		top.filter_readPatientByKeyword = Trim(txtPatientName.value);

		top.statusFrame.repWorkStatus("���ѧ���¡�����ż�����...");
		top.makeAppointmentJSPFrame.UCForm.UC.value = "readPatientByKeyword";
		top.makeAppointmentJSPFrame.UCForm.submit();
	}

	function btnBack_OnClick()
	{
		top.location.href = "calendarMonth.jsp?viewDate=<%=viewDate%>&employeeId=<%=employeeId%>";
	}

	function onLoad()
	{
		try
		{
			// �óշ��������Ҿ�ͧᾷ��� folder ������ٻ noimage.jpg
			var img = "<%=aBpkEmployeeVO!=null ? "../doctorProfileImage/"+aBpkEmployeeVO.getEmployeeId()+".jpg" : "../doctorProfileImage/false.jpg" %>";
			
			if(!imageExists(img))
			{
				imgDoctor.src = "images/noimage.jpg";
			}
<%
			String time = request.getParameter("time");	
%>
			var time = "<%=time!=null ? time : ""%>";
			txtAppointTimeHH.value = getTimeHH(time);
			txtAppointTimeMM.value = getTimeMM(time);

			var viewDate = "<%=viewDate%>";
			txtAppointDateDD.value = getDateDD(viewDate);
			txtAppointDateMM.value = getDateMM(viewDate);
			txtAppointDateYYYY.value = parseInt(getDateYYYY(viewDate))+543;
<%
			String clinic = request.getParameter("clinic");	
%>
			var clinic = "<%=clinic!=null ? clinic : ""%>";
			try
			{
				for(var i=0; i<selClinicId.options.length; i++)
				{
					if(selClinicId.options[i].text==clinic)
					{
						selClinicId.selectedIndex = i;
					}
				}
			}
			catch (e)
			{
				alert("onLoad.clinic, e = "+e.message);
			}
		}
		catch (e)
		{
			alert("onLoad, e = "+e.message);
		}
	}

  //-->
  </script>
</head>

<body onload="onLoad();" marginwidth="0" marginheight="0" topmargin="0" leftmargin="0">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="2">
	<tr>
		<td><jsp:include page="inc/header.inc.jsp" flush="false"/></td>
	</tr>
	<tr>
		<td>
		  <table width="100%" height="100%" border="0" cellspacing="2" cellpadding="0">
			<tr>
			<td width="240">
				<table width="100%" height="100%" border="0" cellspacing="2" cellpadding="0">
					<tr><td class="doctorHeader" style="height:32px">&nbsp;<%=aBpkEmployeeVO!=null ? aBpkEmployeeVO.getEmployeeName() : ""%></td></tr>
					<tr><td style="width:105px;height:105px;vertical-align:bottom;text-align:right"><%=aBpkEmployeeVO!=null ? "<img id=\"imgDoctor\" src=\"../doctorProfileImage/"+aBpkEmployeeVO.getEmployeeId()+".jpg\"/>" : "&nbsp;"%></td></tr>
					<tr><td style="height:21px"><b><%=aBpkEmployeeVO!=null && aBpkEmployeeVO.getLicenseNo()!=null && !"".equals(aBpkEmployeeVO.getLicenseNo()) ? "License:&nbsp;"+aBpkEmployeeVO.getLicenseNo() : ""%></b><%=aBpkEmployeeVO!=null && aBpkEmployeeVO.getLicenseNo()!=null && !"".equals(aBpkEmployeeVO.getLicenseNo()) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "" %><b><%=aBpkEmployeeVO!=null ? "User:&nbsp;"+aBpkEmployeeVO.getEmployeeId() : ""%></b></td></tr>
					<tr><td style="height:21px"><%=aBpkEmployeeVO!=null ? aBpkEmployeeVO.getQualification() : ""%></td></tr>
					<tr><td style="height:21px"><%=aBpkEmployeeVO!=null ? aBpkEmployeeVO.getEducational() : ""%>&nbsp;<%=aBpkEmployeeVO!=null ? aBpkEmployeeVO.getInstitute() : ""%></td></tr>
					<tr><td style="height:21px"><%=aBpkEmployeeVO!=null ? aBpkEmployeeVO.getBoard() : ""%></td></tr>
					<tr><td style="height:21px"><%=aBpkEmployeeVO!=null ? aBpkEmployeeVO.getSpecialty() : ""%></td></tr>
					<tr><td style="height:21px"><%=aBpkEmployeeVO!=null ? aBpkEmployeeVO.getOthers() : ""%></td></tr>
					<tr><td height="*" class="bgFormLabelTop">&nbsp;</td></tr>
				</table>
			</td>
			<td>
				<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100%" style="height:32px">
							<table border="0" cellspacing="0" cellpadding="2" width="100%">
								<tr>
									<td style="vertical-align:middle"><div id="btnBack" onClick="btnBack_OnClick();" class="btnStyleBackAfterSearch" style="vertical-align:middle;width:72px;"><center><img src="css/back.png"/></center></div></td><td><div id="divFindPatient">���Ҽ�����: <input type="text" id="txtPatientName" class="txtBorder" style="height:24px;width:240px" onKeyUp="txtPatientName_onKeyUp();">&nbsp;<input id="btnSearch" type="button" class="btnStyleL" value="��" onclick="btnSearch_OnClick();"/>&nbsp;<input id="btnClearKeyWord" type="button" class="btnStyleL" value="��ҧ" onclick="btnClearKeyWord_onclick();"/></div></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr><td width="100%">
						<div id="divPatientDetail" width="100%">
							<table width="100%" border="0" cellspacing="2" cellpadding="0">
								<tr>
									<td style="height:24px">HN:</td>
									<td id="lblPatientHn">&nbsp;</td>
									<td>���ͼ�����:</td>
									<td id="lblPatientName">&nbsp;</td>
									<td>�Դ�ѹ���:</td>
									<td id="lblPatientBirthdate">&nbsp;</td>
									<td>����:</td>
									<td id="lblPatientAge">&nbsp;</td>
									<td rowspan="2" style="width:105px;vertical-align:top;text-align:right"><img id="imgPatient" src="images/noimage.jpg"/></td>
								</tr>
								<tr>
									<td style="height:24px">Tel:</td>
									<td id="lblPatientTelephone">&nbsp;</td>
									<td>PID:</td>
									<td id="lblPatientPid">&nbsp;</td>
									<td>�ѭ�ҵ�:</td>
									<td id="lblPatientNation">&nbsp;</td>
									<td>���ͪҵ�:</td>
									<td id="lblPatientRace">&nbsp;</td>
								</tr>
							</table>
						</div>
						<div id="divApptDetail" width="100%">
							<table width="100%" border="0" cellspacing="0" cellpadding="2">
								<tr>
									<td style="width:100px;height:32px">���Ѻ��ԡ��:</td>
									<td colspan="2"><input id="txtAppointDateDD" class="txtNumber" maxlength="2" style="width:24px" onKeyPress="return isNumber(event.keyCode);"/>/<input id="txtAppointDateMM" class="txtNumber" maxlength="2" style="width:24px" onKeyPress="return isNumber(event.keyCode);"/>/<input id="txtAppointDateYYYY" class="txtNumber" maxlength="4" style="width:48px" onKeyPress="return isNumber(event.keyCode);"/><input type="button" class="btnStyleAddRemove" value="...">&nbsp;<input id="txtAppointTimeHH" class="txtNumber" maxlength="2" style="width:24px" onKeyPress="return isHour(this);"/>:<input id="txtAppointTimeMM" class="txtNumber" maxlength="2" style="width:24px" onKeyPress="return isMinute(this);"/></td>
								</tr>
								<tr>
									<td style="height:32px">�ٹ����ᾷ��:</td>
									<td colspan="2"><select id="selClinicId">
										<option value="">--- ��س����͡�ٹ����ᾷ�� ---</option>
<%
	HashMap resultCmb = aDAO.listAllClinic();
	if(resultCmb!=null && ResultFlag.STATUS_SUCCESS.equals(resultCmb.get(ResultFlag.STATUS)))
	{
		List listBpkClinicVO = (List)resultCmb.get(ResultFlag.RESULT_DATA);
		for(int i=0, sizei=listBpkClinicVO.size(); i<sizei; i++)
		{
			BpkClinicVO aBpkClinicVO = (BpkClinicVO)listBpkClinicVO.get(i);
%>
										<option value="<%=aBpkClinicVO.getSpId()%>"><%=aBpkClinicVO.getClinicDescription()%></option>
<%
		}
	}
	
%>										</select></td>
								</tr>
								<tr>
									<td>����Ѻ������:</td>
									<td><textarea id="txaAppointAdvice" class="txtBorder" style="width:320px;height:90px"></textarea></td>
									<td><div style="display:inline-block;padding:25px;">
<%
	int limit = 12;
	HashMap resultAdvice = aDAO.listBaseTemplateAdvice(limit);
	if(resultAdvice!=null && ResultFlag.STATUS_SUCCESS.equals(resultAdvice.get(ResultFlag.STATUS)))
	{
		Object aObjAdvice = resultAdvice.get(ResultFlag.RESULT_DATA);
		if(aObjAdvice!=null && aObjAdvice instanceof List)
		{
			List listBaseTemplateAdvice = (List)aObjAdvice;
			for(int i=0, sizei=listBaseTemplateAdvice.size(); i<sizei; i++)
			{
				String txtString = (String)listBaseTemplateAdvice.get(i);
%>
									<input type="button" id="btnAppointAdviceHelp<%=i%>" class="btnStyleHelpText" style="width:100px;height:24px" value="<%=txtString.length()>10 ? txtString.substring(0, 10)+"..." : txtString%>" title="<%=txtString%>" onClick="btnAppointAdviceHelp_onClick(this);"/>
<%
			}
		}
	}
%>
									</div></td>
								</tr>
								<tr>
									<td>����Ѻ���˹�ҷ��:</td>
									<td><textarea id="txaAppointNote" class="txtBorder" style="width:320px;height:90px"></textarea></td>
									<td><div style="display:inline-block;padding:25px;">
<%
	HashMap resultAppDetail = aDAO.listBaseTemplateAppDetail(limit);
	if(resultAppDetail!=null && ResultFlag.STATUS_SUCCESS.equals(resultAppDetail.get(ResultFlag.STATUS)))
	{
		Object aObjAppDetail = resultAppDetail.get(ResultFlag.RESULT_DATA);
		if(aObjAppDetail!=null && aObjAppDetail instanceof List)
		{
			List listBaseTemplateAppDetail = (List)aObjAppDetail;
			for(int i=0, sizei=listBaseTemplateAppDetail.size(); i<sizei; i++)
			{
				String txtString = (String)listBaseTemplateAppDetail.get(i);
%>
									<input type="button" id="btnAppointNoteHelp<%=i%>" class="btnStyleHelpText" style="width:100px;height:24px" value="<%=txtString.length()>10 ? txtString.substring(0, 10)+"..." : txtString%>" title="<%=txtString%>" onClick="btnAppointNoteHelp_onClick(this);"/>
<%
			}
		}
	}
%>
									</div></td>
								</tr>
								<tr><td colspan="3"><input type="button" id="btnSaveAppointment" onClick="btnSaveAppointment_OnClick();" class="btnStyle" value="�ѹ�֡��ùѴ"/></td></tr>
							</table>
						</div>
						</td></tr>
				</table>
			</td>
			</tr>
		  </table>
		</td>
	</tr>
  </table>
</body>
</html>
<%
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	finally
	{
	}
%>