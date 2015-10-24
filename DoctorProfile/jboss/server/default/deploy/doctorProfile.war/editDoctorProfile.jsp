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
<title>������ᾷ��</title>
<jsp:include page="inc/charset.jsp" flush="false"/>
<script type="text/javascript" src="js/coreobject/BpkEmployeeVO.js"></script>
<script language="javascript">
<!--

// For filter 
var filter_employeeName;
var filter_clinic;
var filter_specialty;
var filter_optionWithSchedule;
var filter_searchPage;
var filter_searchCount;

// ����Ѻ��ǹ�ͧ�����ŷ����
var aBpkEmployeeVO = new BpkEmployeeVO();
// ����Ѻ�红������ Table 
var listBpkEmployeeVO = new Array();
<%
// �͢����Ţͧ Employee
DoctorProfileDAO aDAO = DAOFactory.newDoctorProfileDAO();

String employeeId = request.getParameter("employeeId");
BpkUtility.printDebug(this, "employeeId = "+employeeId);
HashMap resultDetail = aDAO.getEmployeeDetail(employeeId);
BpkEmployeeVO aBpkEmployeeVO = null;
if(ResultFlag.STATUS_SUCCESS.equals(resultDetail.get(ResultFlag.STATUS)))
{
	aBpkEmployeeVO = (BpkEmployeeVO)resultDetail.get(ResultFlag.RESULT_DATA);
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
}

HashMap param = new HashMap();
param.put("employeeId", employeeId);
HashMap resultList = aDAO.getSlotDoctor(param);
if(ResultFlag.STATUS_SUCCESS.equals(resultList.get(ResultFlag.STATUS)))
{
	List listBpkEmployeeVO = (List)resultList.get(ResultFlag.RESULT_DATA);

	if(listBpkEmployeeVO!=null)
	{
%>
	var tmpBpkEmployeeVO = new BpkEmployeeVO();
<%
		for(int i=0, sizei=listBpkEmployeeVO.size(); i<sizei; i++)
		{
			BpkEmployeeVO tmpBpkEmployeeVO = (BpkEmployeeVO)listBpkEmployeeVO.get(i);
%>
			tmpBpkEmployeeVO.employeeId = "<%=employeeId%>";
			tmpBpkEmployeeVO.dayId = "<%=tmpBpkEmployeeVO.getDayId()%>";
			tmpBpkEmployeeVO.dayName = "<%=tmpBpkEmployeeVO.getDayName()%>";
			tmpBpkEmployeeVO.clinicDescription = "<%=tmpBpkEmployeeVO.getClinicDescription()%>";
			tmpBpkEmployeeVO.startTime = "<%=tmpBpkEmployeeVO.getStartTime()%>";
			tmpBpkEmployeeVO.endTime = "<%=tmpBpkEmployeeVO.getEndTime()%>";
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