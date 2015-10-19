<%@ page contentType="text/html; charset=WINDOWS-874"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.iMed.iMedCore.utility.Utility"%>
<%@ page import="com.bpk.dao.DAOFactory"%>
<%@ page import="com.bpk.dao.DoctorProfileDAO"%>
<%@ page import="com.bpk.dto.ResultFlag"%>
<%@ page import="com.bpk.dto.BpkEmployeeVO"%>
<html>
 <head>
  <title> Calendar </title>
  <jsp:include page="inc/charset.jsp" flush="false"/>
  <link type="text/css" rel="stylesheet" href="css/extend.css" />
<%
		String viewDate = request.getParameter("viewDate");
		Calendar aCal = Calendar.getInstance(new Locale("en", "US"));
		aCal.setLenient(true);
		// สำหรับใช้แสดงผลที่หัวปฏิทิน
		Calendar aCalMonth = Calendar.getInstance(new Locale("en", "US")); 
		aCalMonth.setLenient(true);
		System.out.println("viewDate = "+viewDate);
		if(viewDate!=null && viewDate.length()==10)
		{
			aCal.set(Calendar.YEAR, Integer.parseInt(viewDate.substring(0, 4)));
			aCal.set(Calendar.MONTH, Integer.parseInt(viewDate.substring(5, 7))-1);
			aCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(viewDate.substring(8)));

			aCalMonth.setTime(aCal.getTime());
		}
		System.out.println("aCalMonth = "+aCalMonth.getTime());
%>
  <script type="text/javascript">
  <!--
	
	function btnToday_OnClick()
	{
		location.href = "CalendarMonth.jsp?viewDate=<%=Utility.getCurrentDate()%>";
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
		location.href = "CalendarMonth.jsp?viewDate=<%=new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "US")).format(aPrevCal.getTime())%>";
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
		location.href = "CalendarMonth.jsp?viewDate=<%=new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "US")).format(aNextCal.getTime())%>";
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
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "จันทร์";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "อังคาร";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "พุธ";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "พฤหัสบดี";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "ศุกร์";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.width = "12%";

		cell = row.insertCell();
		cell.innerText = "เสาร์";
		cell.style.color = "gray";
		cell.style.textAlign = "center";
		cell.style.verticalAlign = "middle";
		cell.width = "12%";
	}

	function getDayDisplay(date)
	{
		var display = "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">";
		display += "<tr><td>";
		display += date;
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
		display += "<tr><td>";
		display += "&nbsp;";
		display += "</td></tr>";
		display += "<tr><td>";
		display += "&nbsp;";
		display += "</td></tr>";
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
		// System.out.println("aCalStart = "+aCalStart.getTime());
		// ลดไปทีละ 1 วัน จนเจอวันอาทิตย์ 
		chkCalStart:
		for(int i=aCalStart.get(Calendar.DAY_OF_YEAR); ; )
		{ // aCalStart.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY
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
		System.out.println("aCalStart = "+aCalStart.getTime());

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
		System.out.println("aCalEnd = "+aCalEnd.getTime());

		// เรียกใช้ DAO 
		DoctorProfileDAO aDAO = DAOFactory.newDoctorProfileDAO();
		HashMap param = new HashMap();
		param.put("employeeId", request.getParameter("employeeId"));
		param.put("startDate", new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "US")).format(aCalStart.getTime()));
		param.put("endDate", new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "US")).format(aCalEnd.getTime()));
		HashMap result = aDAO.getSlotDoctor(param);
		if(ResultFlag.STATUS_SUCCESS.equals(result.get(ResultFlag.STATUS)))
		{
			List listBpkEmployeeVO = (List)result.get(ResultFlag.RESULT_DATA);

			for(int i=0; i<listBpkEmployeeVO.size(); i++)
			{
				BpkEmployeeVO aBpkEmployeeVO = (BpkEmployeeVO)listBpkEmployeeVO.get(i);
				System.out.println(aBpkEmployeeVO.toJSON());
			}
		}
%>
		var row, cell;
<%
		// Run วันไปเรื่อยๆ จนถึงวันที่กำหนด
		for(int i=aCalStart.get(Calendar.DAY_OF_YEAR); aCalStart.getTime().before(aCalEnd.getTime());)
		{
			int date = aCalStart.get(Calendar.DAY_OF_MONTH);
			int day = aCalStart.get(Calendar.DAY_OF_WEEK);
			if(day==Calendar.SUNDAY)
			{
%>			
			// ถ้าเป็นวันอาทิตย์ ต้องขึ้นบรรทัดใหม่ 
			row = tblCalendarMonth.insertRow();
			row.height = 21;
<%			}
%>
			cell = row.insertCell();
//			cell.innerText = "<%=date%>";
			cell.innerHTML = getDayDisplay("<%=date%>");
			cell.style.textAlign = "left";
			cell.width = "12%";
<%
			if(day==Calendar.SUNDAY)
			{	
%>			cell.style.color = "red";
<%			}
			else if(day==Calendar.SATURDAY)
			{
%>			cell.style.color = "gray";
<%			}
			i = i+1;
			aCalStart.set(Calendar.DAY_OF_YEAR, i);

			// System.out.println("aCalStart.getTime() = "+aCalStart.getTime());
			// System.out.println("aCalEnd.getTime() = "+aCalEnd.getTime());
		}
%>
	}

  //-->
  </script>
 </head>

 <body onload="drawCalendarMonth()" marginwidth="0" marginheight="0" topmargin="0" leftmargin="0">
	<table width="100%" height="100%" border="0" cellspacing="2" cellpadding="0">
		<tr>
			<td valign="middle" align="center" style="height:28px;vertical-align:middle" class="header1"><%=new SimpleDateFormat("MMMM yyyy", new Locale("th", "TH")).format(aCalMonth.getTime())%></td>
		</tr>
		<tr>
			<td width="100%" style="height:32px">
				<table border="0" cellspacing="5" cellpadding="0" width="100%">
					<tr>
						<td><input type="button" id="btnToday" 
							 onClick="btnToday_OnClick();"
							 value="Today" class="btnStyle" style="width:72px;height:24px"/></td>
						<td><input type="button" id="btnPrevMonth" 
							 onClick="btnPrevMonth_OnClick();"
							 value=" < " class="btnStyle" style="width:24px;height:24px">&nbsp;
							<input type="button" id="btnNextMonth" 
							 onClick="btnNextMonth_OnClick();"
							 value=" > " class="btnStyle" style="width:24px;height:24px"></td>
						<td width="80%">&nbsp;</td>
						<td><input type="button" id="btnDay" value="Day" class="btnStyle" style="width:64px;height:24px"></td>
						<td><input type="button" id="btnWeek" value="Week" class="btnStyle" style="width:64px;height:24px"></td>
						<td><input type="button" id="btnMonth" value="Month" class="btnStyle" style="width:64px;height:24px"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td width="100%">
				<table id="tblCalendarMonth" width="100%" height="100%" border="1" cellspacing="0" cellpadding="0">
				</table>
			</tr>
		</tr>
	</table>
 </body>
</html>
