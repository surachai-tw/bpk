<%@ page contentType="text/html; charset=windows-874"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.iMed.iMedCore.utility.Utility"%>
<%@ page import="com.iMed.iMedCore.utility.ServletRequestThai"%>
<%@ page import="com.bpk.utility.BpkUtility"%>
<%@ page import="com.bpk.dao.DAOFactory"%>
<%@ page import="com.bpk.dao.DoctorProfileDAO"%>
<%@ page import="com.bpk.dto.ResultFlag"%>
<%@ page import="com.bpk.dto.BpkEmployeeVO"%>
<html>
<head>
<script type="text/javascript" src="../../js/coreobject/BpkEmployeeVO.js"></script>
<script language="JavaScript">
<!--

var aBpkEmployeeVO = top.aBpkEmployeeVO;

<%
	DoctorProfileDAO aDAO = null;
	try
	{
		ServletRequestThai req = new ServletRequestThai(request);
		String employeeId = (String)req.getParameterThai("employeeId");
		Utility.printCoreDebug(this, "employeeId = "+employeeId);

		aDAO = DAOFactory.newDoctorProfileDAO();
		HashMap param = new HashMap();
		param.put("employeeId".toUpperCase(), employeeId);
		HashMap result = aDAO.readDoctorProfile(param);

		// BpkUtility.printDebug(this, "result = "+result);

		String status = (String)result.get(ResultFlag.STATUS);
		if(ResultFlag.STATUS_SUCCESS.equals(status))
		{
			Object aObj = result.get(ResultFlag.RESULT_DATA);
			if(aObj!=null && aObj instanceof BpkEmployeeVO)
			{
				BpkEmployeeVO aBpkEmployeeVO = (BpkEmployeeVO)aObj;
%>				
				aBpkEmployeeVO.employeeId = "<%=aBpkEmployeeVO.getEmployeeId()%>";
				aBpkEmployeeVO.employeeName = "<%=aBpkEmployeeVO.getEmployeeName()%>";
				aBpkEmployeeVO.licenseNo = "<%=aBpkEmployeeVO.getLicenseNo()%>";
				aBpkEmployeeVO.qualification = "<%=aBpkEmployeeVO.getQualification()%>";
				aBpkEmployeeVO.educational = "<%=aBpkEmployeeVO.getEducational()%>";
				aBpkEmployeeVO.institute = "<%=aBpkEmployeeVO.getInstitute()%>";
				aBpkEmployeeVO.board = "<%=aBpkEmployeeVO.getBoard()%>";
				aBpkEmployeeVO.specialty = "<%=aBpkEmployeeVO.getSpecialty()%>";
				aBpkEmployeeVO.others = "<%=aBpkEmployeeVO.getOthers()%>";
<%
				/*
				BpkUtility.printDebug(this, "aBpkEmployeeVO.getEmployeeId() = "+aBpkEmployeeVO.getEmployeeId());
				BpkUtility.printDebug(this, "aBpkEmployeeVO.getEmployeeName() = "+aBpkEmployeeVO.getEmployeeName());
				BpkUtility.printDebug(this, "aBpkEmployeeVO.getLicenseNo() = "+aBpkEmployeeVO.getLicenseNo());
				BpkUtility.printDebug(this, "aBpkEmployeeVO.getQualification() = "+aBpkEmployeeVO.getQualification());
				BpkUtility.printDebug(this, "aBpkEmployeeVO.getEducational() = "+aBpkEmployeeVO.getEducational());
				BpkUtility.printDebug(this, "aBpkEmployeeVO.getInstitute() = "+aBpkEmployeeVO.getInstitute());
				BpkUtility.printDebug(this, "aBpkEmployeeVO.getBoard() = "+aBpkEmployeeVO.getBoard());
				BpkUtility.printDebug(this, "aBpkEmployeeVO.getSpecialty() = "+aBpkEmployeeVO.getSpecialty());
				BpkUtility.printDebug(this, "aBpkEmployeeVO.getOthers() = "+aBpkEmployeeVO.getOthers());
				*/

				List listBpkEmployeeVO = aBpkEmployeeVO.getAllSlot();
				if(Utility.isNotNull(listBpkEmployeeVO))
				{
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
						tmpBpkEmployeeVO.limitNumAppoint = "<%=tmpBpkEmployeeVO.getLimitNumAppoint()%>";
						listBpkEmployeeVO[<%=i%>] = tmpBpkEmployeeVO;
<%			
					}
				}
%>				
				top.mainFrame.setDataToForm();
				top.mainFrame.setSlotToForm();
				top.statusFrame.repStatusSuccess("จบการทำงาน");
<%
			}
		}		
		else
		{
%>
			top.statusFrame.repStatus("ไม่พบข้อมูล");
<%
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	finally
	{
		aDAO = null;
%>
	window.location = "../../editDoctorProfileJSP.jsp";

//-->
</script>
<%
	}
%>
</head>
<body>
	<p>Executing...</p>
</body>
</html>