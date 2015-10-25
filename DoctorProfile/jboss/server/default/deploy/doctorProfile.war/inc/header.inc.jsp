<%@ page contentType="text/html; charset=windows-874"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Locale"%>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="2">
				<tr width="100%" >
					<td width="20%"><img src="images/logo.png"/></td>
					<td width="*">&nbsp;</td>
					<td width="10%" style="vertical-align:middle"><b><%=new SimpleDateFormat("d MMMM yyyy", new Locale("th", "TH")).format(new Date())%></b></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr width="100%" ><td id="tdListDoctor" class="bgHilite" style="vertical-align:middle" width="250px" height="20px">&nbsp;<b>ระบบข้อมูลแพทย์</b></td></tr>
			</table>
		</td>
	</tr>
</table>
