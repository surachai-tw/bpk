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
var page_current;
var page_count;

top.listBpkEmployeeVO = new Array();

var aBpkEmployeeVO = null;

<%
	try
	{
		ServletRequestThai req = new ServletRequestThai(request);
		String filterEmpId = (String)req.getParameterThai("filter_empId");
		String filterEmployeename = (String)req.getParameterThai("filter_employeeName");
		String filterClinic = (String)req.getParameterThai("filter_clinic");
		String filterSpecialty = (String)req.getParameterThai("filter_specialty");
		String filterOptionWithSchedule = (String)req.getParameterThai("filter_optionWithSchedule");

		String filterSearchCount = (String)req.getParameterThai("filter_searchCount");
		String filterSearchPage = (String)req.getParameterThai("filter_searchPage");
		HashMap result;
				
		DoctorProfileDAO aDAO = DAOFactory.newDoctorProfileDAO();
		HashMap param = new HashMap();
		param.put("employeeId", filterEmpId);
		param.put("employeeName", filterEmployeename);

		param.put("clinicDescription", filterClinic);
		
		param.put("specialityDescription", filterSpecialty);
		
		param.put("optionWithSchedule", filterOptionWithSchedule);

		param.put("count", filterSearchCount);

		param.put("page", filterSearchPage);

		result = aDAO.listDoctor(param);

		// BpkUtility.printDebug(this, "result = "+result);

		String status = (String)result.get(ResultFlag.STATUS);
		if(ResultFlag.STATUS_SUCCESS.equals(status))
		{
			// String pageCurrent = (String)result.get(EventNames.PAGE_CURRENT);
			// String pageCount = (String)result.get(EventNames.PAGE_COUNT);

			List listBpkEmployeeVO = (List)result.get(ResultFlag.RESULT_DATA);
			if(Utility.isNotNull(listBpkEmployeeVO))
			{
				for(int i=0, sizei=listBpkEmployeeVO.size(); i<sizei; i++)
				{
					BpkEmployeeVO tmpBpkEmployeeVO = (BpkEmployeeVO)listBpkEmployeeVO.get(i);
%>
					aBpkEmployeeVO = new BpkEmployeeVO();
					aBpkEmployeeVO.employeeName = "<%=tmpBpkEmployeeVO.getEmployeeName()%>";
					aBpkEmployeeVO.specialty = "<%=tmpBpkEmployeeVO.getSpecialty()%>";
					aBpkEmployeeVO.clinicDescription = "<%=tmpBpkEmployeeVO.getClinicDescription()%>";
					aBpkEmployeeVO.dayName = "<%=tmpBpkEmployeeVO.getDayName()%>";
					aBpkEmployeeVO.startTime = "<%=tmpBpkEmployeeVO.getStartTime()%>";
					aBpkEmployeeVO.endTime = "<%=tmpBpkEmployeeVO.getEndTime()%>";
					aBpkEmployeeVO.employeeId = "<%=tmpBpkEmployeeVO.getEmployeeId()%>";

					top.listBpkEmployeeVO[<%=i%>] = aBpkEmployeeVO;
<%
				}
			}

			Integer totalRec = (Integer)result.get(ResultFlag.TOTAL_RECORD);
			if(Integer.parseInt(filterSearchCount)==0)
				filterSearchCount = "1";
			int pageCount = (int)Math.ceil((double)totalRec.intValue()/Integer.parseInt(filterSearchCount));
			if(pageCount==0)
				pageCount = 1;

			// �óշ���ѧ�ըӹǹ������������������ʴ���
			if(Integer.parseInt(filterSearchPage)<pageCount-1)
			{
%>
				top.mainFrame.setEableNavigatorNext(true);
<%
			}
			else
			{
%>
				top.mainFrame.setEableNavigatorNext(false);
<%
			}

			// �óշ����˹�� 2 �繵�� �����͹��Ѻ���
			if(Integer.parseInt(filterSearchPage)>0 && listBpkEmployeeVO.size()>0)
			{
%>
				top.mainFrame.setEableNavigatorPrev(true);
<%
			}
			else
			{
%>
				top.mainFrame.setEableNavigatorPrev(false);
<%
			}
%>
			var cmbObj = top.mainFrame.listDoctor_cmbPage;
			var pageCount = "<%=pageCount%>";
			while(cmbObj.options.length)
			{
				cmbObj.remove(0);
			}
			if(pageCount>1)
			{
				for(i=0; i<pageCount; i++)
				{
					var option = document.createElement("option");
					option.text = (i+1)+" / "+pageCount;
					option.value = i;
					cmbObj.add(option, i);
				}
				cmbObj.selectedIndex = <%=Integer.parseInt(filterSearchPage)%>;
			}
			
			// alert("check method = "+top.mainFrame.ifrmListDoctor);
			top.mainFrame.showListBpkEmployeeVO();
			top.statusFrame.repStatusSuccess("����÷ӧҹ");
<%
		}
		else
		{
			System.out.println("Result Status = "+status);
%>
			top.statusFrame.repStatus("��辺������");
<%
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	finally
	{
%>

	window.location = "../../listDoctorJSP.jsp";

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