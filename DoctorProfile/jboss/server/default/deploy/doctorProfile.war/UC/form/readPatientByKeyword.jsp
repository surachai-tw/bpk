<%@ page contentType="text/html; charset=windows-874"%>
<html>
<head>
<script language="JavaScript">
<!--

function loadForm()
{
	var form = document.getElementById("form");

	form.elements["filter_readPatientByKeyword"].value = top.filter_readPatientByKeyword;

	form.submit();
}

//-->
</script>
</head>

<body onload="loadForm();">
	<form name="form" id="form" action="action/readPatientByKeyword.jsp" method="POST">
		<input type="hidden" name="filter_readPatientByKeyword" id="filter_readPatientByKeyword"/>
	</form>
</body>

</html>