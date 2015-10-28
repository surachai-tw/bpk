/**
* ใช้ตรวจสอบการมีอยู่ของรูปภาพ
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
	var thaimonth = ["ม.ค.","ก.พ.","มี.ค.","เม.ย.","พ.ค.","มิ.ย.","ก.ค.","ส.ค.","ก.ย.","ต.ค.","พ.ย.","ธ.ค."];
	
	return thaimonth[index];
}