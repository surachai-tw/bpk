<%@ page contentType="text/html; charset=WINDOWS-874"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="com.bpk.utility.BpkUtility"%>
<%@ page import="com.bpk.dto.ResultFlag"%>
<%@ page import="com.bpk.dto.BpkEmployeeVO"%>
<%@ page import="com.bpk.dao.DAOFactory"%>
<%@ page import="com.bpk.dao.DoctorProfileDAO"%>
<!DOCTYPE html>
<html>
<head>
<title>ข้อมูลแพทย์</title>
<jsp:include page="inc/charset.jsp" flush="false"/>
<script type="text/javascript" src="js/coreobject/BpkEmployeeVO.js"></script>
<script language="javascript">
<!--

// สำหรับส่วนของข้อมูลทั่วไป
var aBpkEmployeeVO = new BpkEmployeeVO();
// สำหรับเก็บข้อมูลใน Table 
var listBpkEmployeeVO = new Array();
// สำหรับเก็บข้อมูลก่อน Save
var newSlotBpkEmployeeVO = new Array();
<%
// ขอขอ้มูลของ Employee
DoctorProfileDAO aDAO = DAOFactory.newDoctorProfileDAO();

String employeeId = request.getParameter("employeeId");
BpkUtility.printDebug(this, "employeeId = "+employeeId);
HashMap param = new HashMap();
param.put("employeeId".toUpperCase(), employeeId);
HashMap result = aDAO.readDoctorProfile(param);
BpkEmployeeVO aBpkEmployeeVO = null;
if(ResultFlag.STATUS_SUCCESS.equals(result.get(ResultFlag.STATUS)))
{
	aBpkEmployeeVO = (BpkEmployeeVO)result.get(ResultFlag.RESULT_DATA);
%>
	aBpkEmployeeVO.employeeName = "<%=aBpkEmployeeVO.getEmployeeName()%>";

	aBpkEmployeeVO.employeeId = "<%=aBpkEmployeeVO.getEmployeeId()%>";

	aBpkEmployeeVO.licenseNo = "<%=aBpkEmployeeVO.getLicenseNo()%>";
	aBpkEmployeeVO.qualification = "<%=aBpkEmployeeVO.getQualification()%>";
	aBpkEmployeeVO.educational = "<%=aBpkEmployeeVO.getEducational()%>";
	aBpkEmployeeVO.institute = "<%=aBpkEmployeeVO.getInstitute()%>";
	aBpkEmployeeVO.board = "<%=aBpkEmployeeVO.getBoard()%>";
	aBpkEmployeeVO.specialty = "<%=aBpkEmployeeVO.getSpecialty()%>";
	aBpkEmployeeVO.others = "<%=aBpkEmployeeVO.getOthers()%>";
<%

	List listBpkEmployeeVO = aBpkEmployeeVO.getAllSlot();
	if(listBpkEmployeeVO!=null)
	{
%>
	var tmpBpkEmployeeVO = null;
<%
		for(int i=0, sizei=listBpkEmployeeVO.size(); i<sizei; i++)
		{
			BpkEmployeeVO tmpBpkEmployeeVO = (BpkEmployeeVO)listBpkEmployeeVO.get(i);
			BpkUtility.printDebug(this, "tmpBpkEmployeeVO.getDayName() = "+tmpBpkEmployeeVO.getDayName());
%>			
			tmpBpkEmployeeVO = new BpkEmployeeVO();
			tmpBpkEmployeeVO.employeeId = "<%=employeeId%>";

			tmpBpkEmployeeVO.dayId = "<%=tmpBpkEmployeeVO.getDayId()%>";
			tmpBpkEmployeeVO.dayName = "<%=tmpBpkEmployeeVO.getDayName()%>";
			tmpBpkEmployeeVO.clinicDescription = "<%=tmpBpkEmployeeVO.getClinicDescription()%>";
			tmpBpkEmployeeVO.startTime = "<%=tmpBpkEmployeeVO.getStartTime()%>";
			tmpBpkEmployeeVO.endTime = "<%=tmpBpkEmployeeVO.getEndTime()%>";
			tmpBpkEmployeeVO.limitNumAppoint = "<%=tmpBpkEmployeeVO.getLimitNumAppoint()%>";
			listBpkEmployeeVO[<%=i%>] = tmpBpkEmployeeVO;
<%			
		}
	}
}


%>

//-->
</script>
</head>
<%
	String debug = System.getProperty("bpk.debug");
	String frameHeight = "100%,0,15";

	if(BpkUtility.isTrue(debug))
	{
		frameHeight = "70%,20%,15";
	}
%>
<frameset rows="<%=frameHeight%>" frameborder="NO" border="0" framespacing="0">
  <frame src="editDoctorProfileMain.jsp" name="mainFrame" id="mainFrame" scrolling="yes">
  <frame src="editDoctorProfileJSP.jsp" name="editDoctorProfileJSPFrame" id="editDoctorProfileJSPFrame" 
	scrolling="yes" noresize>
  <frame src="inc/windowStatus.jsp" name="statusFrame" scrolling="no" noresize>
</frameset>

<noframes>
<body>
</body>
</noframes>

</html>