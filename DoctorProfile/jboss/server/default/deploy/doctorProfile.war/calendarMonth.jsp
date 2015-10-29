<%@ page contentType="text/html; charset=WINDOWS-874"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.iMed.iMedCore.utility.Utility"%>
<%@ page import="com.bpk.utility.BpkUtility"%>
<%@ page import="com.bpk.dao.DAOFactory"%>
<%@ page import="com.bpk.dao.DoctorProfileDAO"%>
<%@ page import="com.bpk.dto.ResultFlag"%>
<%@ page import="com.bpk.dto.BpkEmployeeVO"%>
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
  <title> ��ԷԹ��÷ӧҹ -  <%=aBpkEmployeeVO!=null ? aBpkEmployeeVO.getEmployeeName() : ""%></title>
  <jsp:include page="inc/charset.jsp" flush="false"/>
  <link type="text/css" rel="stylesheet" href="css/extend.css" />
  <script type="text/javascript" src="js/bpkUtility.js"></script>
  <script type="text/javascript">
  <!--

	function btnMakeAppointment_onClick()
	{
	}

	function btnClose_OnClick()
	{
		divBalloon.style.display = "none";
	}

	function btnEditDetail_OnClick()
	{
		location.href = "editDoctorProfile.jsp?employeeId=<%=aBpkEmployeeVO.getEmployeeId()%>";
	}

	function btnBack_OnClick()
	{
		location.href = "listDoctor.jsp";
	}

	function btnToday_OnClick()
	{
		location.href = "calendarMonth.jsp?viewDate=<%=Utility.getCurrentDate()%>&employeeId=<%=aBpkEmployeeVO.getEmployeeId()%>";
	}

	function btnPrevMonth_OnClick()
	{
<%
		Calendar aPrevCal = Calendar.getInstance(new Locale("th", "TH"));
		aPrevCal.setTime(aCalMonth.getTime());
		int chkPrevMonth = aPrevCal.get(Calendar.MONTH);
		if(chkPrevMonth!=Calendar.JANUARY)
		{
			chkPrevMonth--;
		}
		else
		{
			chkPrevMonth = Calendar.DECEMBER;
			aPrevCal.set(Calendar.YEAR, aPrevCal.get(Calendar.YEAR)-1);
		}
		aPrevCal.set(Calendar.DAY_OF_MONTH, 1);
		aPrevCal.set(Calendar.MONTH, chkPrevMonth);
%>
		location.href = "calendarMonth.jsp?viewDate=<%=new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "US")).format(aPrevCal.getTime())%>&employeeId=<%=aBpkEmployeeVO.getEmployeeId()%>";
	}

	function btnNextMonth_OnClick()
	{
<%
		Calendar aNextCal = Calendar.getInstance(new Locale("th", "TH"));
		aNextCal.setTime(aCalMonth.getTime());
		int chkNextMonth = aNextCal.get(Calendar.MONTH);
		if(chkNextMonth!=Calendar.DECEMBER)
		{
			chkNextMonth++;
		}
		else
		{
			chkNextMonth = Calendar.JANUARY;
			aNextCal.set(Calendar.YEAR, aPrevCal.get(Calendar.YEAR)+1);
		}
		aNextCal.set(Calendar.DAY_OF_MONTH, 1);
		aNextCal.set(Calendar.MONTH, chkNextMonth);
%>
		location.href = "calendarMonth.jsp?viewDate=<%=new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "US")).format(aNextCal.getTime())%>&employeeId=<%=aBpkEmployeeVO.getEmployeeId()%>";
	}

	function clearTableData()
	{
		for( ; tblCalendarMonth.rows.length > 0; )
			tbl.deleteRow(0);
	}

	function drawCalendarMonthHeader()
	{
		var row = tblCalendarMonth.insertRow();
		row.style.height = "8";
		var cell = row.insertCell();
		cell.innerText = "�ҷԵ��";
		cell.style.color = "red";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.style.border = "solid 1px #E8E8E8";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "�ѹ���";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.style.border = "solid 1px #E8E8E8";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "�ѧ���";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.style.border = "solid 1px #E8E8E8";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "�ظ";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.style.border = "solid 1px #E8E8E8";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "����ʺ��";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.style.border = "solid 1px #E8E8E8";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "�ء��";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.style.border = "solid 1px #E8E8E8";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "�����";
		cell.style.color = "gray";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.style.border = "solid 1px #E8E8E8";
		cell.width = "12%";
	}

	function showBalloonDisplay(date, rangeOfTime, clinicDescription, appNum, limitNum)
	{
		var dd = date.substring(date.lastIndexOf('-', date.length-1)+1);
		var mm = date.substring(date.indexOf('-', 0)+1, date.lastIndexOf('-', date.length-1));
		var yyyy = date.substring(0, date.indexOf('-', 0));

		divBalloon.style.display = "";
		lblSelectedDateTime.innerText = parseInt(dd, 10)+" "+getThaiMonth(parseInt(mm, 10)-1)+" "+(parseInt(yyyy, 10)+543)+", "+rangeOfTime;
		lblSelectedClinic.innerText = clinicDescription;		
		lblCountWorkload.innerText = "�ӹǹ��ùѴ���·����� " + appNum + " / " + limitNum;

		var e = window.event;
		var posX = e.clientX;
		var posY = e.clientY;
		// var height = document.body.clientHeight;

		divBalloon.style.left = posX;
		divBalloon.style.top = posY;
	}

	function getDayDisplay(date, slots, displayColor, appNum)
	{
		var dd = date.substring(date.lastIndexOf('-', date.length-1)+1);
		var mm = date.substring(date.indexOf('-', 0)+1, date.lastIndexOf('-', date.length-1));
		var yyyy = date.substring(0, date.indexOf('-', 0));

		var display = "<table border=\"0\" cellspacing=\"1\" cellpadding=\"0\" width=\"100%\"";
		
		display += ">";
		display += ("<tr><td style=\"color:"+displayColor+"\">");
		display += ("&nbsp;"+parseInt(dd, 10));
		if(parseInt(dd, 10)==1)
			display += (" "+getThaiMonth(parseInt(mm, 10)-1));
		display += "</td></tr>";		
		for(var i=0; i<5; i++)
		{
			if(slots.indexOf('+')==-1)
			{
				display += "<tr><td";
				if(slots!=null && slots!="")
				{
					display += " class=\"slotsAppointment\"";
					display += (" onClick=\"showBalloonDisplay('"+date+"', '"+slots.substring(0, slots.indexOf('^', 0))+"', '"+slots.substring(slots.indexOf('^', 0)+1, slots.lastIndexOf('^', slots.length-1))+"', "+appNum+", "+slots.substring(slots.lastIndexOf('^', slots.length-1)+1)+");\"");
					display += ">";
				}
				else
					display += ">";
				var innerText = slots.substring(0, slots.lastIndexOf('^', slots.length-1));
				display += innerText.replace('^', ' ');
				display += "</td></tr>";
				break;
			}
			else
			{
				var line = slots.substring(0, slots.indexOf('+'));
				slots = slots.substring(slots.indexOf('+')+1);
				if(line!=null && line!="")
				{
					display += "<tr><td";
					if(line!=null && line!="")
					{
						display += " class=\"slotsAppointment\"";
						display += (" onClick=\"showBalloonDisplay('"+date+"', '"+line.substring(0, line.indexOf('^', 0))+"', '"+line.substring(line.indexOf('^', 0)+1, line.lastIndexOf('^', line.length-1))+"', "+appNum+", "+line.substring(line.lastIndexOf('^', line.length-1)+1)+");\"");
						display += ">";
					}
					else
						display += ">";
					var innerText = line.substring(0, line.lastIndexOf('^', line.length-1));
					display += innerText.replace('^', ' ');
					display += "</td></tr>";
				}
				else
				{
					display += "<tr><td>";
					display += "&nbsp;";
					display += "</td></tr>";
				}
			}
		}

		display += "</table>";
		return display;
	}

	function drawCalendarMonth()
	{
		drawCalendarMonthHeader();
<%
		Calendar aCalStart = Calendar.getInstance(new Locale("en", "US")); 
		aCalStart.setLenient(true);
		aCalStart.setTime(aCal.getTime());

		// Ŵ价��� 1 �ѹ �����ѹ�ҷԵ�� 
		chkCalStart:
		for(int i=aCalStart.get(Calendar.DAY_OF_YEAR); ; )
		{
			if(i-1<0)
			{
				// aCalStart.set(Calendar.YEAR, aCalStart.get(Calendar.YEAR)-1);
				aCalStart.set(Calendar.MONTH, Calendar.DECEMBER);
				aCalStart.set(Calendar.DAY_OF_MONTH, 31);
				i = aCalStart.get(Calendar.DAY_OF_YEAR);
			}
			else
			{
				aCalStart.set(Calendar.DAY_OF_YEAR, --i);
			}

			if(aCalStart.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY && aCalStart.get(Calendar.MONTH)!=aCal.get(Calendar.MONTH))
			{
				break chkCalStart;
			}
		}

		// �ѹ�ش���µ�ͧ���ѹ����� 
		Calendar aCalEnd = Calendar.getInstance(new Locale("en", "US"));
		aCalEnd.setLenient(true);
		aCalEnd.setTime(aCal.getTime());
		aCalEnd.set(Calendar.DAY_OF_MONTH, 27);
		// ����价��� 1 �ѹ �����ѹ����� ���ͧ����ѹ���͹����� 
		chkCalEnd:
		for(int i=aCalEnd.get(Calendar.DAY_OF_YEAR); ; )
		{
			aCalEnd.set(Calendar.DAY_OF_YEAR, ++i);

			if(aCalEnd.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY && aCalEnd.get(Calendar.MONTH)!=aCal.get(Calendar.MONTH))
			{
				break chkCalEnd;
			}
		}

		// ��¡�â����Ţͧ�ѹ���Ѵ��������ͧ������Ф�
		List listBpkEmployeeVO = null;
		HashMap param = new HashMap();
		BpkUtility.printDebug(this, "Check employeeId = "+employeeId);
		param.put("employeeId".toUpperCase(), employeeId);
		param.put("startDate".toUpperCase(), new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "US")).format(aCalStart.getTime()));
		param.put("endDate".toUpperCase(), new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "US")).format(aCalEnd.getTime()));
		HashMap result = aDAO.getSlotDoctor(param);
		if(ResultFlag.STATUS_SUCCESS.equals(result.get(ResultFlag.STATUS)))
		{
			listBpkEmployeeVO = (List)result.get(ResultFlag.RESULT_DATA);

			/*
			for(int i=0; i<listBpkEmployeeVO.size(); i++)
			{
				BpkEmployeeVO aBpkEmployeeVO = (BpkEmployeeVO)listBpkEmployeeVO.get(i);
			}
			*/
		}
%>
		var row, cell, displayColor;
<%
		// Run �ѹ�������� ���֧�ѹ����˹�
		for(int i=aCalStart.get(Calendar.DAY_OF_YEAR); aCalStart.getTime().before(aCalEnd.getTime());)
		{
%>			displayColor = "black";
<%
			String date = Utility.getDateStringFromDate(aCalStart.getTime());
			int day = aCalStart.get(Calendar.DAY_OF_WEEK);
			int startMonth = aCalStart.get(Calendar.MONTH);
			int thisMonth = aCalMonth.get(Calendar.MONTH);
			if(day==Calendar.SUNDAY)
			{
%>			
			// ������ѹ�ҷԵ�� ��ͧ��鹺�÷Ѵ���� 
			row = tblCalendarMonth.insertRow();
			row.height = 21;
<%			}
%>
			cell = row.insertCell();

			cell.style.textAlign = "left";
			cell.style.border = "solid 1px #E8E8E8";
			cell.width = "12%";
<%
			if(day==Calendar.SUNDAY)
			{	
%>			displayColor = "red";
<%			}
			else if(day==Calendar.SATURDAY)
			{
%>			displayColor = "gray";
<%			}

			if(startMonth!=thisMonth)
			{
%>			displayColor = "lightgray";
<%			}
			String timeInRange = DoctorProfileDAO.getTimeInRange(BpkUtility.convertDate2StdFormat(aCalStart.getTime()), listBpkEmployeeVO);

			// �ѹ�Ѩ�غѹ���������繪Ѵ
			if(nowDate.equals(Utility.getDateStringFromDate(aCalStart.getTime())))
			{
%>
			cell.style.backgroundColor = "#EAEAEA";
			cell.style.border = "solid 1px #666666";
<%			
			}
%>
			cell.innerHTML = getDayDisplay("<%=date%>", "<%=timeInRange%>", displayColor, 0);
<%
			i = i+1;
			aCalStart.set(Calendar.DAY_OF_YEAR, i);
		}
%>
	}

	function onLoad()
	{
		drawCalendarMonth();

		try
		{
			// �óշ��������Ҿ�ͧᾷ��� folder ������ٻ noimage.jpg
			var img = "<%=aBpkEmployeeVO!=null ? "../doctorProfileImage/"+aBpkEmployeeVO.getEmployeeId()+".jpg" : "../doctorProfileImage/false.jpg" %>";
			
			if(!imageExists(img))
			{
				imgDoctor.src = "images/noimage.jpg";
			}
		}
		catch (e)
		{
			alert("imgDoctor exception e = "+e.message);
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
					<tr><td style="height:42px"><%
					
					if(aBpkEmployeeVO!=null)
					{
					
					%><input type="button" id="btnEditDetail" onClick="btnEditDetail_OnClick();"
							 value="���" class="btnStyle" style="vertical-align:middle;width:72px;"/><%
					}
					else
					{
						%>&nbsp;<%
					}
										 %></td></tr>
					<tr><td height="*" class="bgFormLabelTop">&nbsp;</td></tr>
				</table>
			</td>
			<td>
				<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100%" style="height:32px">
							<table border="0" cellspacing="0" cellpadding="4" width="100%">
								<tr>
									<td style="vertical-align:middle"><div id="btnBack" onClick="btnBack_OnClick();" class="btnStyleBackAfterSearch" style="vertical-align:middle;width:72px;"><center><img src="css/back.png"/></center></div></td>
									<td style="vertical-align:middle"><input type="button" id="btnToday" 
										 onClick="btnToday_OnClick();"
										 value="�ѹ���" class="btnStyle" style="vertical-align:middle;width:72px;"/></td>
									<td style="vertical-align:middle">
										<table border="0" cellspacing="0" cellpadding="1">
											<tr><td><input type="button" id="btnPrevMonth" 
														onClick="btnPrevMonth_OnClick();"
														value="<" class="btnStyleNavigator"/></td>
												<td><input type="button" id="btnNextMonth" 
														onClick="btnNextMonth_OnClick();"
														value=">" class="btnStyleNavigator"/></td></tr>
										 </table></td>
									<td width="80%" class="bgFormLabelTop" style="vertical-align:middle"><b>&nbsp;<%=new SimpleDateFormat("MMMM yyyy", new Locale("th", "TH")).format(aCalMonth.getTime())%></b></td>
									<td style="vertical-align:middle"><input type="button" id="btnDay" value="1 �ѹ" class="btnStyle" style="width:64px"/></td>
									<td style="vertical-align:middle"><input type="button" id="btnWeek" value="�ѻ����" class="btnStyle" style="width:64px"/></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr><td width="100%" style="height:480px">
					<div id="divBalloon" class="divBpkBalloon" style="display:none">
						<table width="100%" height="100%" border="0" cellspacing="2" cellpadding="0">
							<tr><td style="text-align:right; height:24px"><img id="btnClose" onClick="btnClose_OnClick();" class="btnStyleClose" style="vertical-align:middle;width:24px;" src="css/close.png"/></td></tr>
							<tr><td id="lblSelectedDateTime" class="slotHeaderDate">&nbsp;</td></tr>
							<tr><td id="lblSelectedClinic" class="slotHeaderClinic">&nbsp;</td></tr>
							<tr><td id="lblCountWorkload" style="align:left">&nbsp;</td></tr>
							<tr><td style="text-align:right"><input type="button" id="btnMakeAppointment" value=" �ӹѴ " class="btnStyle" onClick="btnMakeAppointment_onClick();">&nbsp;</td></tr>
						</table>
					</div>
					<div id="divCalendarMonth" width="100%" style="height:480px">
						<table id="tblCalendarMonth" class="tableShow" width="100%" height="100%" border="1" cellspacing="0" cellpadding="0"/>
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
