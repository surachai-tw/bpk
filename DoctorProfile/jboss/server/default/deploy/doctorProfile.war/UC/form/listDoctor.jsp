<%@ page contentType="text/html; charset=windows-874"%>
<html>
<head>
<script language="JavaScript">
<!--
function loadForm()
{
	var form = document.getElementById("form");
			
	form.elements["filter_empId"].value = top.filter_empId;
	form.elements["filter_clinic"].value = top.filter_clinic;
	form.elements["filter_specialty"].value = top.filter_specialty;
	form.elements["filter_optionWithSchedule"].value = top.filter_optionWithSchedule;

	form.elements["filter_searchCount"].value = top.filter_searchCount;
	form.elements["filter_searchPage"].value = top.filter_searchPage;

	form.submit();
}

//-->
</script>

</head>
<body onload="loadForm();">
<form name="form" id="form" action="action/listDoctor.jsp" method="POST">

	<input type="hidden" name="filter_empId" id="filter_empId"/>
	<input type="hidden" name="filter_clinic" id="filter_clinic"/>
	<input type="hidden" name="filter_specialty" id="filter_specialty"/>
	<input type="hidden" name="filter_optionWithSchedule" id="filter_optionWithSchedule"/>
	<input type="hidden" name="filter_searchCount" id="filter_searchCount"/>
	<input type="hidden" name="filter_searchPage" id="filter_searchPage"/>

	<input type="hidden" name="filter_arrIndex" id="filter_arrIndex"/>
</form>
</body>
</html>