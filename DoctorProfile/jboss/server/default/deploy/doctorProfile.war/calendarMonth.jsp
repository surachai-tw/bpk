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

		// ขอขอ้มูลของ Employee
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
		// สำหรับใช้แสดงผลที่หัวปฏิทิน
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
  <title> ปฏิทินการทำงาน -  <%=aBpkEmployeeVO!=null ? aBpkEmployeeVO.getEmployeeName() : ""%></title>
  <jsp:include page="inc/charset.jsp" flush="false"/>
  <link type="text/css" rel="stylesheet" href="css/extend.css" />
  <script type="text/javascript" src="js/bpkUtility.js"></script>
  <script type="text/javascript">
  <!--

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
		cell.innerText = "อาทิตย์";
		cell.style.color = "red";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.style.border = "solid 1px #E8E8E8";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "จันทร์";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.style.border = "solid 1px #E8E8E8";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "อังคาร";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.style.border = "solid 1px #E8E8E8";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "พุธ";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.style.border = "solid 1px #E8E8E8";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "พฤหัสบดี";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.style.border = "solid 1px #E8E8E8";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "ศุกร์";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.style.border = "solid 1px #E8E8E8";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "เสาร์";
		cell.style.color = "gray";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.style.border = "solid 1px #E8E8E8";
		cell.width = "12%";
	}

	function getDayDisplay(date, month, slots, displayColor)
	{
		var display = "<table border=\"0\" cellspacing=\"1\" cellpadding=\"0\" width=\"100%\"";
		
		display += ">";
		display += ("<tr><td style=\"color:"+displayColor+"\">");
		display += ("&nbsp;"+date);
		if(date==1)
			display += (" "+month);
		display += "</td></tr>";		
		for(var i=0; i<5; i++)
		{
			if(slots.indexOf('+')==-1)
			{
				display += "<tr><td";
				if(slots!=null && slots!="")
					display += " class=\"slotsAppointment\">";
				else
					display += ">";
				display += slots;
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
						display += " class=\"slotsAppointment\">";
					else
						display += ">";
					display += line;
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
		/*
		if(slots!=null && slots!="")
		{
			display += "<tr><td class=\"slotsAppointment\">";
			display += slots;
			display += "</td></tr>";
		}
		else
		{
			display += "<tr><td>";
			display += "&nbsp;";
			display += "</td></tr>";
		}
		display += "<tr><td>";
		display += "&nbsp;";
		display += "</td></tr>";
		display += "<tr><td>";
		display += "&nbsp;";
		display += "</td></tr>";
		display += "<tr><td>";
		display += "&nbsp;";
		display += "</td></tr>";
		display += "<tr><td>";
		display += "&nbsp;";
		display += "</td></tr>";
		*/
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

		// ลดไปทีละ 1 วัน จนเจอวันอาทิตย์ 
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

		// วันสุดท้ายต้องเป็นวันเสาร์ 
		Calendar aCalEnd = Calendar.getInstance(new Locale("en", "US"));
		aCalEnd.setLenient(true);
		aCalEnd.setTime(aCal.getTime());
		aCalEnd.set(Calendar.DAY_OF_MONTH, 27);
		// เพิ่มไปทีละ 1 วัน จนเจอวันเสาร์ แต่ต้องเลยวันในเดือนไปแล้ว 
		chkCalEnd:
		for(int i=aCalEnd.get(Calendar.DAY_OF_YEAR); ; )
		{
			aCalEnd.set(Calendar.DAY_OF_YEAR, ++i);

			if(aCalEnd.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY && aCalEnd.get(Calendar.MONTH)!=aCal.get(Calendar.MONTH))
			{
				break chkCalEnd;
			}
		}

		// รายการข้อมูลของวันที่นัดได้ทั้งหมดของหมอแต่ละคน
		List listBpkEmployeeVO = null;
		HashMap param = new HashMap();
		param.put("employeeId", employeeId);
		param.put("startDate", new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "US")).format(aCalStart.getTime()));
		param.put("endDate", new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "US")).format(aCalEnd.getTime()));
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
		// Run วันไปเรื่อยๆ จนถึงวันที่กำหนด
		for(int i=aCalStart.get(Calendar.DAY_OF_YEAR); aCalStart.getTime().before(aCalEnd.getTime());)
		{
%>			displayColor = "black";
<%
			int date = aCalStart.get(Calendar.DAY_OF_MONTH);
			int month = aCalStart.get(Calendar.MONTH);
			int day = aCalStart.get(Calendar.DAY_OF_WEEK);
			int startMonth = aCalStart.get(Calendar.MONTH);
			int thisMonth = aCalMonth.get(Calendar.MONTH);
			if(day==Calendar.SUNDAY)
			{
%>			
			// ถ้าเป็นวันอาทิตย์ ต้องขึ้นบรรทัดใหม่ 
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

			// วันปัจจุบันให้ทำให้เห็นชัด
			if(nowDate.equals(Utility.getDateStringFromDate(aCalStart.getTime())))
			{
%>
			cell.style.backgroundColor = "#EAEAEA";
			cell.style.border = "solid 1px #666666";
<%			
			}
%>
			
			cell.innerHTML = getDayDisplay("<%=date%>", getThaiMonth(<%=month%>), "<%=timeInRange%>", displayColor);
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
			// กรณีที่ไม่มีภาพของแพทย์ใน folder ให้ใช้รูป noimage.jpg
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
							 value="แก้ไข" class="btnStyle" style="vertical-align:middle;width:72px;"/><%
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
									<td style="vertical-align:middle"><a href="#"><div id="btnBack" onClick="btnBack_OnClick();" class="btnStyleBackAfterSearch" style="vertical-align:middle;width:72px;"><center><img src="css/back.png"/></center></div></a></td>
									<td style="vertical-align:middle"><input type="button" id="btnToday" 
										 onClick="btnToday_OnClick();"
										 value="วันนี้" class="btnStyle" style="vertical-align:middle;width:72px;"/></td>
									<td style="vertical-align:middle">
										<table border="0" cellspacing="0" cellpadding="1">
											<tr><td><input type="button" id="btnPrevMonth" 
														onClick="btnPrevMonth_OnClick();"
														value=" < " class="btnStyle" style="width:24px"/></td>
												<td><input type="button" id="btnNextMonth" 
														onClick="btnNextMonth_OnClick();"
														value=" > " class="btnStyle" style="width:24px"/></td></tr>
										 </table></td>
									<td width="80%" class="bgFormLabelTop" style="vertical-align:middle"><b>&nbsp;<%=new SimpleDateFormat("MMMM yyyy", new Locale("th", "TH")).format(aCalMonth.getTime())%></b></td>
									<td style="vertical-align:middle"><input type="button" id="btnDay" value="1 วัน" class="btnStyle" style="width:64px"/></td>
									<td style="vertical-align:middle"><input type="button" id="btnWeek" value="สัปดาห์" class="btnStyle" style="width:64px"/></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr><td width="100%"><table id="tblCalendarMonth" class="tableShow" width="100%" height="100%" border="1" cellspacing="0" cellpadding="0"/></td></tr>
				</table>
			</td>
			</tr>
		  </table>
		</td>
	</tr>
  </table>
</body>
</html>
