<%@ page contentType="text/html; charset=windows-874"%>
<html>
<head>
<jsp:include page="charset.jsp" flush="false"/>
<link type="text/css" rel="stylesheet" href="../css/extend.css">
<SCRIPT LANGUAGE="JavaScript">
var status_timeoutId = "";
var status_failed = "fail";
var status_success = "success";
var status_working = "statusWorking";
function repStatus(reportTxt, stat, sec)
{
	try
	{
		status_tdStatus.innerText = reportTxt;
		if(stat != null)
		{	
			if(stat.length > 0)
			{
				status_tdStatus.className = stat;
			}
			else
			{
				status_tdStatus.className = status_failed;
			}
		}
		else
		{
			status_tdStatus.className = status_failed;
		}
		clearTimeout(status_timeoutId);
		status_timeoutId = setTimeout(clearStatus, (sec != null) ? sec*1000 : 5000);
	}
	catch(e)
	{
		;
	}
}
function repWorkStatus(text)
{
	if(text== null || text == "")
	{
		text = "โปรแกรมกำลังทำงาน...";
	}
	status_tdStatus.innerText = text;
	status_tdStatus.className = status_working;
	
	clearTimeout(status_timeoutId);
	text +=".";
	if( text.length >= 10 ) //ถ้า . มีครบ 10 ครั้งแล้วจะเริ่มใหม่
	{
		var numberofpoint = text.substr( text.length - 10 , text.length );
		if( numberofpoint == "..........")
		{
			text = text.substr( 0 , text.length -10 );
		}
	}
	status_timeoutId = setTimeout("repWorkStatus('"+text+"');" , 5000);
}

function repStatusSuccess(text)
{
	if(text== null || text == "")
	{
		text = "จบการทำงาน";
	}
	repStatus(text, status_success);
}

function clearStatus()
{
	status_tdStatus.innerText = " ";
	status_tdStatus.className = "statusBar";
	clearTimeout(status_timeoutId);
}
</SCRIPT>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td id="status_tdStatus" class="statusBar" height="100%">&nbsp;</td>
    </tr>
  </table>
</body>
</html>