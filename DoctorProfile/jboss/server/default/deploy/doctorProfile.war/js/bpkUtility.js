/**
* ���Ǩ�ͺ���������ͧ�ٻ�Ҿ
*/
function imageExists(image_url)
{
	var http = new XMLHttpRequest();

	http.open('HEAD', image_url, false);
	http.send();

	return http.status != 404;
}

// Check Compatibility View Setting
// Example from https://gist.github.com/jasongaylord/5733469
// alert(navigator.userAgent);
// Create new ieUserAgent object
function isCompatView()
{
	// Get the user agent string
	var ua = navigator.userAgent;
	this.compatibilityMode = false;

	if (ua.indexOf("MSIE") == -1) 
	{
		this.version = 0;
		return false;
	}

	if (ua.indexOf("compatible") == -1) 
	{
		this.compatibilityMode = false;
	}
	else 
	{
		this.compatibilityMode = true;
	}

	return this.compatibilityMode;
}

function getThaiMonth(index)
{
	var thaimonth = ["�.�.","�.�.","��.�.","��.�.","�.�.","��.�.","�.�.","�.�.","�.�.","�.�.","�.�.","�.�."];
	
	return thaimonth[index];
}

function getTimeHH(hhmm)
{
	return hhmm.substring(0, 2);
}

function getTimeMM(hhmm)
{
	return hhmm.substring(3, 5);
}

function getDateDD(yyyy_mm_dd)
{
	return yyyy_mm_dd.substring(yyyy_mm_dd.lastIndexOf('-', yyyy_mm_dd.length-1)+1);
}

function getDateMM(yyyy_mm_dd)
{
	return yyyy_mm_dd.substring(yyyy_mm_dd.indexOf('-', 0)+1, yyyy_mm_dd.lastIndexOf('-', yyyy_mm_dd.length-1));
}

function getDateYYYY(yyyy_mm_dd)
{
	return yyyy_mm_dd.substring(0, yyyy_mm_dd.indexOf('-', 0));
}

function isFutureDate(pdate)
{
	var today = new Date().getTime();
	var dd = getDateDD(pdate);
	var mm = getDateMM(pdate);
	var yyyy = getDateYYYY(pdate);

	var checkDate = new Date(yyyy, mm, dd, 23, 59, 59, 999).getTime();

	return today < checkDate;
}