function removeComma(numberStr)
{
	for(; numberStr.indexOf(",") != -1;)
	{
		numberStr = numberStr.replace(",", "");
	}
	return Number(numberStr);
}

function removeDot(str)
{
	for(; str.indexOf(".") != -1;)
	{
		str = str.replace(".", "");
	}
	return str;
}

function removeSlash(str)
{
	return str.replace(/\\\"/g, "\"");
}
function nl2br(html)
{
	return html.replace(/\n/g,"<br>");
}
function isNumber(c)
{
	//ทำให้การกรอกตัวเลขที่อื่นผิดถ้ามีปัญหาการกรอกเลขฐาน 16 ให้ไปเรียก isHex แทน
	//return ((c >47 && c <58) || (c>=97 && c<=102));
	return (c >47 && c <58);
}

function isEngCharacter(c)
{
	return ((c >= 65 && c <= 90) || (c >= 97 && c <= 122));
}

function isHex(c)
{
	return ((c>=48 && c<=57) || (c>=65 && c<=70) || (c>=97 && c<=102));
}

function RTrim(s)
{
	while(s.charAt(s.length-1) == " ")
		s = s.substr(0,s.length-1);
 	return s;
}

function LTrim(s)
{
	while(s.charAt(0) == " ")
		s = s.substr(1);
 	return s;
}

function Trim(s)
{
	return LTrim(RTrim(s));
}

/** เอาเครื่องหมายพิเศษออกจาก text ที่ส่งมา (หากต้องการเพิ่มเครื่องหมายพิเศษ ใส่ลงในระหว่าง [] ได้เลย */
function removeSpecialChar( text ){
    return text.replace(/[-+*/%$#|]+/g,"");
}

function dateOnKeyPress(keyCode, dObj, mObj, yObj)
{
	if(keyCode == 27)
	{
		//	ESC button
		dObj.value = "";
		mObj.value = "";
		yObj.value = "";
		return false;
	}
	else
	{
		return isNumber(keyCode);
	}
}

function removeSelectList(selectItem)
{
	if(selectItem.selectedIndex != -1)
	{
		selected = selectItem.selectedIndex;
		for(i = 0; i < selectItem.options.length - 1; i++)
		{
			if(i >= selected)
			{
				selectItem.options[i].value = selectItem.options[i+1].value;
				selectItem.options[i].text = selectItem.options[i+1].text;
			}
		}
		selectItem.options.length--;
	}
}

function selectComboboxByValue(obj,value)
{
	if(obj.tagName == 'SELECT')
	{
		if(value != null && value.length > 0)
		{
			for(i = 0; i < obj.options.length; i++)
			{
					if(obj.options[i].value == value)
					{
						obj.selectedIndex = i;
						return;
					}
			}
		}		
		obj.selectedIndex = 0;
	}
}

function setDefaultValue(obj,defaultValue)
{
	var defaultArray = new Array();
	defaultArray["text"] = "";
	defaultArray["checkbox"] = true;
	defaultArray["radio"] = 0;
	defaultArray["select"] = "";
	defaultArray["textarea"] = "";

	if(defaultValue == null)
	{
		if(obj.tagName == "INPUT")
			defaultValue = defaultArray[obj.type];
		else if(obj.tagName == "TEXTAREA")
			defaultValue = defaultArray["TEXTAREA"];
		else if(obj.tagName == "SELECT")
			defaultValue = defaultArray["SELECT"];
		else
			defaultValue = "";
	}

	switch(obj.tagName)
	{
		case "INPUT":
			switch(obj.type)
			{
				case "text":
					obj.value = defaultValue;
					break;
				case "checkbox":
					obj.checked = defaultValue;
					break;
				case "radio":
					obj[defaultValue].checked = true;
					break;
			}
			break;
		case "TEXTAREA":
			obj.value = defaultValue;
			break;
		case "SELECT":
			selectComboboxByValue(obj,defaultValue);
			break;
	}
}

function ItemList()
{
	this.Item = new Array();
	this.addItem = ItemList_addItem;
}

function Item(obj, defaultValue)
{
	this.obj = obj;
	this.defaultValue = defaultValue;
}

function ItemList_addItem(obj, defaultValue)
{
	this.Item[this.Item.length] = new Item(obj, defaultValue);
}

function getTop(oSource)
{
	if(hasParent(oSource))
	{
		if(oSource.tagName == "TR" || oSource.tagName == "TABLE")
		{
			return oSource.offsetTop + getTop(oSource.parentElement);
		}
		else
		{
			return getTop(oSource.parentElement);
		}
	}
	else
	{
		return 0;
	}
}

function getLeft(oSource)
{
	if(hasParent(oSource))
	{
		if(oSource.tagName == "TD" || oSource.tagName == "TABLE")
		{
			return oSource.offsetLeft + getLeft(oSource.parentElement);
		}
		else
		{
			return getLeft(oSource.parentElement);
		}
	}
	else
	{
		return 0;
	}
}

function hasParent(oSource)
{
	if(oSource.parentElement)
	{
		return true;
	}
	else
	{
		return false;
	}
}

function dateTime2ThaiDateTime(date, time, isEng)
{
	return date2ThaiDate(date, isEng) + "," + time2ThaiTimeFormat(time, isEng);
}

function date2ThaiDate(date, isEng, format)
{
	//	date = yyyy-mm-dd
	if(date && date != null && date != "" && date != "null" )	// for Thai Format
	{
		if(isEng == true)
		{
			var arr = date.split("-");
			var thai_day = ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"];
			var thai_month = [["01","02","03","04","05","06","07","08","09","10","11","12"], ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"]];

			if(format == null)
				return arr[2] + " " + thai_month[1][Number(arr[1])-1] + " " + (Number(arr[0]));
			else
			{
				if(format == 1)
				{
					var yTmp = (Number(arr[0]) - (Number(arr[0]) > 2000 ? 2000 : 1900));
					if(yTmp < 10)
						yTmp = "0" + yTmp;
					return arr[2] + "/" + thai_month[0][Number(arr[1])-1] + "/" + yTmp;
				}
				else if(format == 2)
				{
					return arr[2] + " " + thai_month[1][Number(arr[1])-1] + " " + (Number(arr[0]));
				}
				else if(format == 3)
				{
					var tmp_date = new Date(arr[0], Number(arr[1])-1, arr[2]);
					return thai_day[tmp_date.getDay()] + " " + arr[2] + " " + thai_month[1][Number(arr[1])-1] + " " + (Number(arr[0]));
				}
			}
		}
		else
		{
			var arr = date.split("-");
			var thai_day = ["อา.", "จ.", "อ.", "พ.", "พฤ.", "ศ.", "ส."];
			var thai_month = [["01","02","03","04","05","06","07","08","09","10","11","12"], ["ม.ค.","ก.พ.","มี.ค.","เม.ย.","พ.ค.","มิ.ย.","ก.ค.","ส.ค.","ก.ย.","ต.ค.","พ.ย.","ธ.ค."]];

			if(format == null)
				return arr[2] + " " + thai_month[1][Number(arr[1])-1] + " " + (Number(arr[0])+543);
			else
			{
				if(format == 1)
				{
					var yTmp = (Number(arr[0]) - (Number(arr[0]) > 2000 ? 2000 : 1900));
					if(yTmp < 10)
						yTmp = "0" + yTmp;
					/* แสดงเลขปีเป็น 2 หลัก */
					//return arr[2] + "/" + thai_month[0][Number(arr[1])-1] + "/" + (Number(arr[0])+543-2500);
					/* แก้เป็นแสดงเลขปี 4 หลัก */
					return arr[2] + "/" + thai_month[0][Number(arr[1])-1] + "/" + (Number(arr[0])+543);
				}
				else if(format == 2)
				{
					return arr[2] + " " + thai_month[1][Number(arr[1])-1] + " " + (Number(arr[0])+543);
				}
				else if(format == 3)
				{
					var tmp_date = new Date(arr[0], Number(arr[1])-1, arr[2]);
					return thai_day[tmp_date.getDay()] + " " + arr[2] + " " + thai_month[1][Number(arr[1])-1] + " " + (Number(arr[0])+543);
				}
			}
		}
	}
	else
	{
		return null;
	}
}

function getCurrentTime(){
	var date = new Date();
	var hours = date.getHours();
	hours = ((hours+"").length<2)? "0"+hours: hours;
	var minutes = date.getMinutes();
	minutes = ((minutes+"").length<2)? "0"+minutes: minutes;
	var seconds = date.getSeconds();
	seconds = ((seconds+"").length<2)? "0"+seconds: seconds;
	return hours+":"+minutes+":"+seconds;
}  

function time2ThaiTimeFormat(time, isEng)
{
	var arr = time2arr(time);
	if(arr != null)
		return arr[0] + ":" + arr[1] + (isEng == true ? "" : " น.");
	else
		return "";
}
// arr[2] = year , arr[1] = month , arr[2] = date
function date2arr(date)
{
	//	date = yyyy-mm-dd
	if(date != null && date.length > 0 && date.toLowerCase() != "null")
	{
		var tmp = date.split("-");
		var arr = new Array();
		arr[0] = tmp[2];
		arr[1] = tmp[1];
		arr[2] = Number(tmp[0]);
		return arr;
	}
	else
	{
		return null;
	}
}
// arr[0] = hours , arr[1] = minutes
function time2arr(time)
{
	//	time = hh:mm:time
	if(time != null && time.length > 0 && time.toLowerCase() != "null")
	{
		var arr = time.split(":");
		return arr;
	}
	else
	{
		return null;
	}
}

function checkPID(PID)
{
	if(PID != null && PID.length == 13)
	{
		var sum = 0;
		var arr = new Array(13);
		for(i = 13; i > 1; i--)
		{

			sum += Number(PID.charAt(PID.length-i))*i;
		}

		sum = (11-(sum%11))%10;
		return sum == PID.charAt(12);
	}
	else
	{
		return false;
	}
}

function isLeapYear(y)
{
	//	ตรวจสอบปี
	if(Number(y) > 2300)
		y = Number(y)-543;
	return ((y%4 == 0 && y%100 != 0) || y%400 == 0);
}
/* ใช้สำหรับเช็ควันที่ที่กรอกมาว่าถูกต้องหรือเปล่า
	yyyy สามารถส่งมาเป็น พ.ศ. ได้
	mm ถ้าหากให้ผู้ใช้กรอก ต้อง -1 ด้วย (เช่น เดือนตุลาคม ต้องส่งมาเป็น  9)
*/
function isTrueDate(yyyy,mm,dd)
{
	yyyy = Number(yyyy);
	mm = Number(mm);
	dd = Number(dd);
	//alert(yyyy + ":" + mm + ":" + dd);
	var next = new Date(yyyy,mm,dd);
	var lYear = [31,29,31,30,31,30,31,31,30,31,30,31];
	var nlYear = [31,28,31,30,31,30,31,31,30,31,30,31];

	//	เลือกจำนวนวันของแต่ละเดือน
	var year;
	if(isLeapYear(yyyy))
	{
		year = lYear;
	}
	else
	{
		year = nlYear;
	}

	//	ตรวจสอบเดือน
	if(!year[mm])
	{
		return false;
	}
	//	ตรวจสอบวัน
	if(dd > year[mm])
	{
		return false;
	}
	return true;
}


//เช็คว่าวันที่จะต้องอยู่ในช่วง 00.00.00 - 23.59.00 ส่งค่ามาเป็น value (ไม่ใช่ obj)
function isTrueTime( hour , minute,sec )
{
	try
	{
		if( Number(hour) < 0 || Number(hour) > 23 )
			return false;
		if( Number(minute) < 0 || Number(minute) > 59 )
			return false;	
		if(isNaN(sec))
		{
		if( Number(sec) < 0 || Number(sec) > 59 )
			return false;	
		}
		return true;

	}
	catch(e)
	{
		return false;
	}
}

/** ดึงวันที่สุดท้ายของเดือนออกมา 
  yearValue เป็นปี
  monthValue เป็นเลขเดือน เริ่มจาก 1 - 12
  @return -1 หากข้อมูลปีและเดือนที่ส่งมาไม่ถูกต้อง
*/
function getLastDayOfMonth( yearValue , monthValue ){
    if( isNaN(yearValue) || isNaN(yearValue)){
        return -1;
    }
    yearValue = Number(yearValue);
    monthValue = Number(monthValue);
    if( Number(yearValue) >= 2400 ){
        yearValue -= 543
    }
    var firstDate = new Date( yearValue , monthValue-1 , 1);
    var nextMonthDate = new Date(yearValue , monthValue , 1 );
    var timeDiff = nextMonthDate.getTime() - firstDate.getTime();
    var dayDiff = timeDiff/(1000*60*60*24);
    return dayDiff;
}

function setDate(yyyy, mm, dd)
{
	if(yyyy.length > 0 && mm.length > 0 && dd.length > 0)
	{
		if(yyyy.length == 2)
			yyyy = "25" + yyyy;

		if(yyyy.length != 4)
		{
			alert("ปี พ.ศ. ไม่ถูกต้อง");
			return "";
		}
		else if(utility_isTrueDate(yyyy, Number(mm)-1, dd))
		{
			while(yyyy.length < 4)
			{
				yyyy = "0" + yyyy;
			}
			yyyy = Number(yyyy) - 543;

			if(mm.length < 2)
			{
				mm = "0" + mm;
			}

			if(dd.length < 2)
			{
				dd = "0" + dd;
			}
			return yyyy + "-" + mm + "-" + dd;
		}
		else
		{
			return "";
		}
	}
	else
	{
		return "";
	}
}

function utillity_isLeapYear(y)
{
	//	ตรวจสอบปี
	if(Number(y) > 2300)
		y = Number(y)-543;
	return ((y%4 == 0 && y%100 != 0) || y%400 == 0);
}
/* ใช้สำหรับเช็ควันที่ที่กรอกมาว่าถูกต้องหรือเปล่า
	yyyy สามารถส่งมาเป็น พ.ศ. ได้
	mm ถ้าหากให้ผู้ใช้กรอก ต้อง -1 ด้วย (เช่น เดือนตุลาคม ต้องส่งมาเป็น  9)
*/
function utility_isTrueDate(yyyy,mm,dd)
{
	yyyy = Number(yyyy);
	mm = Number(mm);
	dd = Number(dd);
	//alert(yyyy + ":" + mm + ":" + dd);
	var next = new Date(yyyy,mm,dd);
	var lYear = [31,29,31,30,31,30,31,31,30,31,30,31];
	var nlYear = [31,28,31,30,31,30,31,31,30,31,30,31];

	//	เลือกจำนวนวันของแต่ละเดือน
	var year;
	if(utillity_isLeapYear(yyyy))
	{
		year = lYear;
	}
	else
	{
		year = nlYear;
	}

	//	ตรวจสอบเดือน
	if(!year[mm])
	{
		return false;
	}
	//	ตรวจสอบวัน
	if(dd > year[mm])
	{
		return false;
	}
	return true;
}

//เช็คว่าวันที่จะต้องอยู่ในช่วง 00.00 - 23.59 ส่งค่ามาเป็น value (ไม่ใช่ obj)
function utility_isTrueTime( hour , minute )
{
	try
	{
		if( Number(hour) < 0 || Number(hour) > 23 )
			return false;
		if( Number(minute) < 0 || Number(minute) > 59 )
			return false;
	}
	catch(e)
	{
		return false;
	}
}

function setTime(hh, mm)
{
	if(hh.length > 0 && mm.length > 0)
	{
		if(hh.length < 2)
		{
			hh = "0" + hh;
		}

		if(mm.length < 2)
		{
			mm = "0" + mm
		}
		return hh + ":" + mm + ":59";
	}
	return "";
}
/**
* เหมือน setTime แต่เพิ่ม 00 เป็นหน่วยวินาทีแทน setTime ที่ใช้ 59
*/
function setTimeZeroSec(hh , mm ){
	if(hh.length > 0 && mm.length > 0)
	{
		if(hh.length < 2)
		{
			hh = "0" + hh;
		}

		if(mm.length < 2)
		{
			mm = "0" + mm
		}
		return hh + ":" + mm + ":00";
	}
	return "";
}

function setKeyYearFormat(txt_object, length, isYearBefore, siteBranchCode)
{
	if(txt_object.value != "")
	{
		if(siteBranchCode)	//ถ้าเป็นแบบมีเลข base_site ให้บังคับเป็นปีนำหน้า
			isYearBefore = true;

		var runningLength = length-(2 + (siteBranchCode ? 1 : 0));	//ความยาวของ running number
		if(txt_object.value.length == length && (txt_object.value.indexOf("-") == -1))
		{//ใส่ hn แบบที่เก็บใน db
			var arr = new Array();
			arr["value"] = txt_object.value;
			arr["code"] = txt_object.value;
			return arr;
		}
		else
		{
			var slash_pos = txt_object.value.indexOf("-");
			if(slash_pos == -1)
			{
				//ไม่มี slash
				if(txt_object.value.length < runningLength)	//ถ้าระบุเลขสาขา
				{
					var now = new Date();
					var year = (String)((now.getYear()+543)-2500);
					var tmp = txt_object.value;
					if(isYearBefore && isYearBefore == true)
						txt_object.value = year + "-" + txt_object.value;
					else
						txt_object.value = txt_object.value + "-" + year;
					while(tmp.length < runningLength)
						tmp = "0" + tmp;
					tmp = year + tmp;
					
					if(siteBranchCode)
					{
						txt_object.value = siteBranchCode + "-" + txt_object.value;
						tmp = siteBranchCode + tmp;
					}
				}
				var arr = new Array();
				arr["value"] = txt_object.value;
				arr["code"] = tmp;
				return arr;
			}
			else
			{
				if(siteBranchCode)
				{
					var arr = txt_object.value.split("-");
					//	field hn มี - อยู่
					if(arr.length == 3)	//ถ้าระบุเลขสาขา ปี มาด้วย
					{
						if(arr[0].length == 1 && arr[1].length == 2)	//ระบุเลขที่สาขา
						{
							var tmp = txt_object.value.substring(txt_object.value.lastIndexOf("-")+1);

							while(tmp.length < runningLength)
								tmp = "0" + tmp;
							tmp = arr[0] + arr[1] + tmp;

							var arr = new Array();
							arr["value"] = txt_object.value;
							arr["code"] = tmp;
							return arr;
						}
						else
						{
							alert("รูปแบบในการค้นหาผู้ป่วยด้วยผิดพลาด");
							txt_object.select();
							return -1;
						}
					}
					else if(arr.length == 2)	//ระบุแต่ปี
					{
						var dashPos = 0;	//ตำแหน่งของปี
						if(arr[dashPos].length == 2)	//ระบุเลขที่สาขา
						{
							var tmp = txt_object.value.substring(txt_object.value.lastIndexOf("-")+1);

							while(tmp.length < runningLength)
								tmp = "0" + tmp;
							tmp = siteBranchCode + arr[dashPos] + tmp;
							txt_object.value = siteBranchCode + "-" + txt_object.value;
							var arr = new Array();
							arr["value"] = txt_object.value;
							arr["code"] = tmp;
							return arr;
						}
						else
						{
							alert("รูปแบบในการค้นหาผู้ป่วยด้วยผิดพลาด");
							txt_object.select();
							return -1;
						}
					}
				}
				else
				{
					var arr = txt_object.value.split("-");
					//	field hn มี - อยู่
					var dashPos = (isYearBefore && isYearBefore == true) ? 0 : (arr.length-1);
					if(arr[dashPos].length == 2)
					{
						var tmp = "";
						if(isYearBefore && isYearBefore == true)
							tmp = txt_object.value.substring(txt_object.value.lastIndexOf("-")+1);
						else
							tmp = txt_object.value.substring(0, txt_object.value.lastIndexOf("-"));

						while(tmp.length < length-2)
							tmp = "0" + tmp;
						tmp = arr[dashPos] + tmp;
						var arr = new Array();
						arr["value"] = txt_object.value;
						arr["code"] = tmp;
						return arr;
					}
					else
					{
						alert("รูปแบบในการค้นหาผู้ป่วยด้วยผิดพลาด");
						txt_object.select();
						return -1;
					}
				}
			}
		}
	}
	else
	{
		return -1;
	}
}

function setKeyYearFormatXN(txt_object, length, isYearBefore)
{
	if(txt_object.value != "")
	{
		var slash_pos = txt_object.value.indexOf("/");
		if(slash_pos == -1)
		{
			if(txt_object.value.length < length-2)
			{
				var now = new Date();
				var year = (String)((now.getYear()+543)-2500);
				var tmp = txt_object.value;
				if(isYearBefore && isYearBefore == true)
				txt_object.value = year + "/" + txt_object.value;
				else
					txt_object.value = txt_object.value + "/" + year;

				while(tmp.length < length-2)
					tmp = "0" + tmp;
				tmp = year + tmp;
			}
			var arr = new Array();
			arr["value"] = txt_object.value;
			arr["code"] = tmp;
			return arr;
		}
		else
		{
			var arr = txt_object.value.split("/");
			var yearPos = (isYearBefore && isYearBefore == true) ? 0 : 1;
			var runningPos = (isYearBefore && isYearBefore == true) ? 1 : 0;

			//	field hn มี / อยู่
			if(arr[yearPos].length == 2)
			{
				var tmp = "";//txt_object.value.substring(0, txt_object.value.lastIndexOf("/"));
				if(isYearBefore && isYearBefore == true)
					tmp = txt_object.value.substring(txt_object.value.lastIndexOf("/")+1);
				else
					tmp = txt_object.value.substring(0, txt_object.value.lastIndexOf("/"));

				while(tmp.length < length-2)
					tmp = "0" + tmp;
				tmp = arr[yearPos] + tmp;

				var arr = new Array();
				arr["value"] = txt_object.value;
				arr["code"] = tmp;
				return arr;
			}
			else
			{
				alert("รูปแบบในการค้นหาผู้ป่วยด้วยผิดพลาด");
				txt_object.select();
				return -1;
			}
		}
	}
	else
	{
		return -1;
	}
}

function setKeyGeneralFormat(txt_object, length)
{
	if(txt_object.value != "")
	{
		var hnTemp = txt_object.value;
		while(hnTemp.length < length)
			hnTemp = "0" + hnTemp;
		var arr = new Array();
		arr["value"] = txt_object.value;
		arr["code"] = hnTemp;
		return arr;
	}
	else
	{
		var arr = new Array();
		arr["value"] = "";
		arr["code"] = "";
		return arr;
	}
}

function setVNFormat(txt_object, length, visit_type)
{
	//	visit_type = true(ipd), false(opd)
	if(visit_type)
		prefix_vn = "1";
	else
		prefix_vn = "0";
	var slash_pos = txt_object.value.indexOf("/");
	if(slash_pos == -1)
	{
		if(txt_object.value.length < length-3)
		{
			var now = new Date();
			var year = (String)((now.getYear()+543)-2500);
			var tmp = txt_object.value;
			txt_object.value = txt_object.value + "/" + year;

			while(tmp.length < length-3)
				tmp = "0" + tmp;
			tmp = prefix_vn + year + tmp;
		}
		var arr = new Array();
		arr["value"] = txt_object.value;
		arr["code"] = tmp;
		return arr;
	}
	else
	{
		var arr = txt_object.value.split("/");
		if(arr.length == 2)
		{
			if(arr[1].length == 2)
			{
				var tmp = arr[0];
				while(tmp.length < length-3)
					tmp = "0" + tmp;
				tmp = prefix_vn + arr[1] + tmp;

				var arr = new Array();
				arr["value"] = txt_object.value;
				arr["code"] = tmp;
				return arr;
			}
			else
			{
				alert("รูปแบบในการค้นหาผู้ป่วยด้วย VN ผิดพลาด");
				txt_object.select();
				return -1;
			}
		}
		else
		{
			alert("รูปแบบในการค้นหาผู้ป่วยด้วย VN ผิดพลาด");
			txt_object.select();
			return -1;
		}
	}
}

function isFloat(oSource)
{
	if(isNumber(event.keyCode))
	{
		return true;
	}
	else
	{
		if(oSource.value.indexOf(".") < 0)
		{
			if(String.fromCharCode(event.keyCode) == ".")
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}

//ฟังก์ชันสำหรับ onkeypress สำหรับช่องกรอก ชม. ของเวลา
function isHour(obj)
{
	var str = obj.value;
	if( str.length == 0 || str.length == 2 ) //ถ้ายังไม่กรอกอะไร หรือคลุมทั้งสองตัวแล้วกดปุ่ม ตัวแรกต้องกรอกเลข 0 - 2 เท่านั้น
	{
		return (event.keyCode >= 48 && event.keyCode <= 50); 
	}
	else if(str.length == 1)
	{
		if(str == "2")
		{
			return (event.keyCode >= 48 && event.keyCode <= 51);
		}
		return isNumber(event.keyCode);
	}
}
//ฟังก์ชันสำหรับ onkeypress สำหรับช่องกรอก นาที ของเวลา
function isMinute(obj)
{
	var str = obj.value;
	if(str.length == 0 || str.length == 2)
	{
		return (event.keyCode >= 48 && event.keyCode <= 53);
	}
	return isNumber(event.keyCode)
}

function autoTextCursor(oSource, oTarget, mode)
{
	var keyCode = event.keyCode;
	try
	{
		if(keyCode != 9 && keyCode != 16)
		{
			if(oSource.maxLength != "")
			{
				if(oSource.value.length == Number(oSource.maxLength))
				{
					if(mode == true)
						oTarget.focus();
					else
						oTarget.select();
					return true;
				}
			}
			return true;
		}
	}
	catch(ex)
	{

	}
}

function autoTextCursorWithIsFloat(keyCode, oSource, oTarget, mode)
{
	if(keyCode == 13)
	{
		if(mode == true)
			oTarget.focus();
		else
			oTarget.select();
	}
	return isFloat(oSource);
}

function autoTextCursorWithIsNumber(keyCode, oSource, oTarget, mode)
{
	if(keyCode == 13)
	{
		if(mode == true)
			oTarget.focus();
		else
			oTarget.select();
	}
	return isNumber(keyCode);
}

// คำนวณค่า BMI
function calculateBMI(weight, height)
{
   if(weight!="" && height!="")
   {
	   try
	   {
		 // ส่วนสูงใช้หน่วย m แต่ใน Database เก็บเป็น cm
		 var hNumber = Number(height)/100;
		 // น้ำหนักใช้หน่วย Kg
		 var wNumber = Number(weight);

		 var bmi = wNumber/(hNumber*hNumber);

		 // ทำค่า BMI ให้เหลือ 2 ตำแหน่ง
		 return bmi.toFixed(2);
	   }
	   catch(e)
	   {
		 return "";
	   }
   }
   return "";
}

//ใส่ค่าวันที่ปัจจุบันให้กับ object (ส่ง object มาเป็น year , month , date , hour , minute)
function getNowDateTime( objDay, objMonth , objYear ,  objHour , objMinute)
{
	var nowdate = new Date();
	if (nowdate.getDate() < 10) 
		tmp = "0" + nowdate.getDate(); 
	else 
		tmp = nowdate.getDate();
	objDay.value = tmp;
	if ( (nowdate.getMonth()+1) < 10) 
		tmp = "0" + Number(nowdate.getMonth()+1); 
	else 
		tmp = Number(nowdate.getMonth()+1);
	objMonth.value = tmp;
	objYear.value = nowdate.getFullYear()+543;
	if( objHour != null )
	{
		if ( nowdate.getHours() < 10)
		{
			tmp = "0" + Number(nowdate.getHours());
		}
		else
		{
			tmp = nowdate.getHours();
		}
		objHour.value = tmp;
	}
	if( objMinute != null )
	{
		if( nowdate.getMinutes() < 10 )
		{
			tmp = "0" + Number(nowdate.getMinutes());
		}
		else
		{
			tmp = nowdate.getMinutes();
		}
		objMinute.value = tmp;
	}
}
//เปรียบเทียบวัน ถ้า A มากกว่า B จะส่งค่ากลับเป็น "A" ถ้า A น้อยกว่า B จะส่งค่ากลับเป็น "1" และ -1 เมื่อ A มากกว่า B
//ถ้าเท่ากัน จะได้ค่ากลับเป็น 0
//วันที่ A และ B จะอยู่ในรูปแบบ yyyy-mm-dd (ใช้ setDate มาก่อนใช้คำสั่งนี้)
//ถ้า A และ B ไม่ถูกต้องจะส่งกลับเป็น ""
function compareDate( dateA , dateB)
{
	//if((dateA != null && dateA.length > 0 && dateA.toLowerCase() != "null")&&(dateB != null && dateB.length > 0 && dateB.toLowerCase() != "null"))
	if((dateA != "")&&(dateB != ""))
	{
		var tmpA = dateA.split("-");
		var valueA = Number(tmpA[0]+tmpA[1]+tmpA[2]);
		var tmpB = dateB.split("-");
		var valueB = Number(tmpB[0]+tmpB[1]+tmpB[2]);
		if ( valueA > valueB )
		{
			return -1;
		}
		else if(valueA < valueB)
		{
			return 1;
		}
		else if (valueA == valueB)
		{
			return 0;
		}
		else
			return "";
	}
	else
	{
		return "";
	}
	return "";
}
/** ใช้ในการเปรียบเทียบเวลา A กับ B โดยต้องส่งค่า A กับ B มาในรูปแบบเวลาที่เก็บใน database จะเปรียบเทียบถึง นาที เท่านั้น
return -1 เมื่อ A > B
return 0 เมื่อ A = B
return 1 เมื่อ A < B
return "" หากเกิดข้อผิดพลาด
*/
function compareTime( timeA , timeB )
{
	var returnData = "";
	if( timeA != null && timeA != "" && timeB != null && timeB != "" )
	{
		var Aarr = timeA.split(":");
		var Barr = timeB.split(":");
		var AValue = Number( Aarr[0]+Aarr[1] );
		var BValue = Number( Barr[0]+Barr[1] );
		if( AValue > BValue )
			return -1;
		else if( AValue == BValue)
			return 0;
		else if( AValue < BValue)
			return 1;
		else
			return returnData;
	}
	return returnData;
}
//เปรียบเทียบวันและเวลา โดยให้ใช้คำสั่ง setDate กับ SetTime กับวันที่และเวลามาก่อนส่งมาให้ฟังก์ชัน
//จะให้ค่ากลับเป็น "-1" ถ้าวันเวลา A มากกว่า หรือ "1" ถ้าวันเวลา B มากกว่า หรือ "" ในกรณีที่ส่งค่ามาไม่ถูกต้องหรือ "0" ในกรณีที่เท่ากัน
//กรณีต้องการเปรียบเทียบเฉพาะเวลา ให้ใส่ค่า dateA และ dateB เท่ากัน
function compareDateTime( dateA , timeA , dateB , timeB)
{
	if((dateA != null && dateA.length > 0 && dateA.toLowerCase() != "null")&&(dateB != null && dateB.length > 0 && dateB.toLowerCase() != "null")&&(timeA != null && timeA.length > 0 && timeA.toLowerCase() != "null")&&(timeB != null && timeB.length > 0 && timeB.toLowerCase() != "null"))
	{
		var tmpA = dateA.split("-");
		var tmpTimeA = timeA.split(":");
		var valueA = Number(tmpA[0]+tmpA[1]+tmpA[2]+tmpTimeA[0]+tmpTimeA[1]);
		var tmpB = dateB.split("-");
		var tmpTimeB = timeB.split(":");
		var valueB = Number(tmpB[0]+tmpB[1]+tmpB[2]+tmpTimeB[0]+tmpTimeB[1]);
		if ( valueA > valueB )
		{
			return -1;
		}
		else if(valueA < valueB)
		{
			return 1;
		}
		else if(valueA == valueB)
		{
			return 0;
		}
		else
			return "";
	}
	else
	{
		return "";
	}
	return "";
}
//นำค่าตัวเลข LN มาแปลงให้เหลือ 4 หลัก
function shortLN( ln )
{
	if(ln != "" && ln != "null")
	{
		var ln_tmp = ln.substr( ln.length -4 , 4);
		return ln_tmp;
	}
	return "";
}

function getNumberYearFormat(number)
{
	if(number != null && number != "" && number != "null" && number.length > 3)
	{
		var year = number.substr(0,2);
		var running_number = Number(number.substr(2));
		return running_number + "/" + year;
	}
	return "";
}

function getVNANDisplay(number)
{
	if(number != "" && number != "null" && number.length > 4)
	{
		var year = number.substr(1,3);
		var running_number = Number(number.substr(3));
		return running_number + "/" + year;
	}
	return "";
}

function getANDisplay(number)
{
	if(number != "" && number != "null" && number.length > 2)
	{
		var year = number.substr(0,2);
		var running_number = Number(number.substr(2));
		return running_number + "/" + year;
	}
	return "";
}

//	แปลงรูปแบบ VN ใน db มาแสดงที่ GUI
function getVNDisplay(vn)
{
	if(vn != "" && vn != "null" && vn.length > 2)
	{
		var nowDate = new Date();
		var vn_year = vn.substring(0, 2);
		var vn_month = vn.substring(2, 4);
		var vn_date = vn.substring(4,6);

		var now_year = (nowDate.getYear()+543).toString().substring(2);

		if(Number(vn_year) == Number(now_year) && Number(vn_month) == (nowDate.getMonth()+1) && Number(vn_date) == nowDate.getDate())
		{
			return Number(vn.substring(6));
		}
		else
		{
			return Number(vn.substring(6)) + "/" + vn_date + vn_month + vn_year;
		}
	}
	else
	{
		return "";
	}
}

//	แปลงรูปแบบ VN ที่แสดงที่หน้าจอให้เป็นรูปแบบ VN ใน Database
function formatVn(vn_number)
{
	var running_length = 4;
	var date_length = 6;
	var number_only = /\d/;
	var number_and_date = /\d\/\d[^\/]/;

	if(number_and_date.test(vn_number))
	{
		var arr = vn_number.split("/");
		if(arr.length == 2 && arr[1].length == date_length)
		{
			return arr[1].substring(4,6) + arr[1].substring(2,4) + arr[1].substring(0,2) + addZero(arr[0], 4);
		}
	}
	else if(number_only.test(vn_number))
	{
		if(vn_number.indexOf("/") == -1 && vn_number.length <= running_length)
		{
			var nowDate = new Date();
			return (nowDate.getYear()+543).toString().substr(nowDate.getYear().toString().length-2) + addZero((nowDate.getMonth()+1).toString(), 2) + addZero(nowDate.getDate().toString(), 2) + addZero(vn_number, 4);
		}
	}
	return -1;
}

//	แปลงรูปแบบ VN ที่แสดงที่หน้าจอให้เป็นรูปแบบ VN ใน Database
function formatAn(txt_object, length, isYearBefore)
{
	if(txt_object.value != "")
	{
		var prefix_vn = "";
		var slash_pos = txt_object.value.indexOf("/");
		if(slash_pos == -1)
		{
			if(txt_object.value.length < length-3)
			{
				var now = new Date();
				var year = (String)((now.getYear()+543)-2500);
				var tmp = txt_object.value;
				if(isYearBefore && isYearBefore == true)
					txt_object.value = year + "/" + txt_object.value;
				else
					txt_object.value = txt_object.value + "/" + year;

				while(tmp.length < length-3)
					tmp = "0" + tmp;
				tmp = prefix_vn + year + tmp;
			}
			var arr = new Array();
			arr["value"] = txt_object.value;
			arr["code"] = tmp;
			return arr;
		}
		else
		{
			var arr = txt_object.value.split("/");
			if(arr.length == 2)
			{
				var slashPos = (isYearBefore && isYearBefore == true) ? 0 : 1;
				var runningPos = (isYearBefore && isYearBefore == true) ? 1 : 0;
				if(arr[slashPos].length == 2)
				{
					var tmp = arr[runningPos];
					while(tmp.length < length-3)
						tmp = "0" + tmp;
					tmp = prefix_vn + arr[slashPos] + tmp;

					var arr = new Array();
					arr["value"] = txt_object.value;
					arr["code"] = tmp;
					return arr;
				}
				else
				{
					alert("รูปแบบในการค้นหาผู้ป่วยด้วย VN ผิดพลาด");
					txt_object.select();
					return -1;
				}
			}
			else
			{
				alert("รูปแบบในการค้นหาผู้ป่วยด้วย VN ผิดพลาด");
				txt_object.select();
				return -1;
			}
		}
	}
	else
	{
		var arr = new Array();
		arr["value"] = "";
		arr["code"] = "";
		return arr;
	}
}

function addZero(str, len)
{
	for( ; str.length < len; )
		str = "0" + str;
	return str;
}

/* ใช้สำหรับค้นหา ค่าในคอมโบบ็อกซ์ที่มีรหัสตรงกับค่าใน obj รหัส (codeObj) โดยจะต้นใน cmbObj (ซึ่งจะต้องมีโค้ดอยู่ใน .text) จำนวนโค้ดระบุใน NumofCode
  * 01 December 2005 - ชุ้ง - แก้ไข code นี้ให้ตรวจสอบรหัสกับ value (base_service_point_id) ของ cmbObj แทน เนื่องจากพบว่า รพ วัฒนแพทย์
  * ใช้รหัสของจุดบริการเป็น 3 ตัว แทนที่จะเป็น 4 ตัวเหมือนกับ กรุงธน ทำให้ไม่สามารถใช้งานช่องค้นหารหัสจุดบริการได้เลย
  * ปล จะยังรับ NumOfCode อยู่ แต่จะไม่เอามาใช้แล้ว เนื่องจากป้องกันไม่ให้ code เดิม error
  */
function CodeSelectServicePoint( codeObj , cmbObj , NumOfCode )
{
		var spid = codeObj.value;
		//หา spid ที่มีความยาวน้อยที่สุดของ patientInfo_cmbServicePoint เพื่อที่จะบอกได้ว่าควรมี spid ยาวเท่าไรจึงจะเริ่มทำการค้นหาได้
		//หา spid ที่มีความยาวมากที่สุดของ patientInfo_cmbServicePoint เพื่อที่จะได้ reset ค่าที่กรอกไปหากกรอกไปจนถึง code ยาวสุดแล้วยังไม่เจอรหัสที่ตรงกัน
		var spid_least_length = Number.MAX_VALUE;
		var spid_most_length = Number.MIN_VALUE;
		for(var i = 0 ; i < cmbObj.options.length ; i++)
		{
			if( cmbObj.options[i].value.length < spid_least_length )
			{
				spid_least_length = cmbObj.options[i].value.length;
			}
			if( cmbObj.options[i].value.length > spid_most_length )
			{
				spid_most_length = cmbObj.options[i].value.length;
			}
		}
		if( spid.length >= spid_least_length ) //รหัสที่ผู้ใช้กรอกมามีความยาวพอที่จะเริ่มค้นหาได้แล้ว
		{
			for(var i = 0 ; i < cmbObj.options.length ; i++ )
			{
				if( spid == cmbObj.options[i].value )
				{
					cmbObj.selectedIndex = i;
					codeObj.value = "";
					codeObj.focus();
					return true;
				}
			}
			if( spid.length >= spid_most_length)
			{
				codeObj.value = "";
				codeObj.focus();
			}
		}
}
/*function CodeSelectServicePoint( codeObj , cmbObj , NumOfCode )
{
	if (codeObj.value.length == NumOfCode )
	{
		for( i = 0 ; i < cmbObj.options.length ; i++ )
		{
			if (codeObj.value == cmbObj.options[i].value)//cmbObj.options[i].text.substr(0,NumOfCode) )
			{
				cmbObj.selectedIndex = i;
				codeObj.value = "";
				return true;
			}
		}
		codeObj.value = "";
		codeObj.focus();
		return false;
	}
}*/
//ใช้สำหรับกรอง ตัวเลือกในคอมโบบ็อกซ์ให้เหลือแต่เท่าที่ใส่ใน code (กรองเฉพาะตัวอักษรตัวแรกเท่านั้น) ใช้ได้กับคอมโบบ็อกซ์ที่มีเฉพาะ object_id กับ description เท่านั้น
//ArrAllValue คือค่า value ทั้งหมดในรูปแบบ array ของ cmbObj , ArrAllText คือ text ทั้งหมดในรูปแบบ array ของ cmbObj
function CodeFilterCombobox( codeObj , cmbObj , ArrAllValue , ArrAllText)
{
	if(codeObj.value.length > 0 )
	{
		var lstMatchValue = new Array();
		var lstMatchText = new Array();
		
		for(a = 0 , i = 0 ; i < ArrAllValue.length ; i++)
		{
			if( codeObj.value.toUpperCase() == ArrAllText[i].substr(0 ,  codeObj.value.length ).toUpperCase() )
			{
				lstMatchValue[a] = ArrAllValue[i];
				lstMatchText[a] = ArrAllText[i];
				a++;
			}
		}
		if(lstMatchValue.length > 0)
		{
			cmbObj.options.length = 0;
			for(i = 0 ; i < lstMatchValue.length ; i++ )
			{
				cmbObj.options.add( new Option( lstMatchText[i] , lstMatchValue[i] ) );
			}
		}
	}
	else
	{
		cmbObj.options.length = 0;
		for(var i = 0 ; i < ArrAllValue.length ; i++ )
		{
			cmbObj.options.add( new Option( ArrAllText[i] , ArrAllValue[i] ) );
		}
	}
}
//ใช้คู่กับ CodeFilterCombobox จะให้ค่าเป็น array ของ .value ของ option ทั้งหมดของ combobox
function getAllCmbValue( cmbObj )
{
	var tmp_value = new Array();
	for(var i = 0 ; i < cmbObj.options.length ; i++)
		tmp_value.push(cmbObj.options[i].value);
	return tmp_value;
}
//ใช้คู่กับ CodeFilterCombobox จะให้ค่าเป็น array ของ .text ของ option ทั้งหมดของ combobox
function getAllCmbText( cmbObj )
{
	var tmp_text = new Array();
	for(var i = 0 ; i < cmbObj.options.length ; i++)
	{
		tmp_text.push(cmbObj.options[i].text);
	}
	return tmp_text;
}

//	check ว่ามีสิทธิในการสั่ง order บ้างหรือไม่
function hasAuthOrder(order_auth)
{
	for(var i = 0; i < order_auth.length; i++)
	{
		if(order_auth.charAt(i) == '1')
		{
			return true;
		}
	}
	return false;
}
//	check ว่ามีสิทธิในการสั่ง order ประเภทต่างๆ ได้หรือป่าว
function checkAuthOrder(fix_item_type_id, order_auth)
{
	return (order_auth.charAt(fix_item_type_id) == "1");
}

//	selectAll combobox
function selectAllCombobox(oSource)
{
	for(var i = 0; i < oSource.options.length; i++)
	{
		oSource.options[i].selected = true;
	}
}

// เลือกรายการในคอมโบบ็อก (cmbObj) ที่มีข้อความนำหน้าเหมือนกับค่าที่อยู่ที่ใน textfield (codeObj) โดยไม่จำกัดความยาว
function codeSelectCombobox( codeObj , cmbObj )
{
	if( codeObj != null && cmbObj != null )
	{
		if( codeObj.value.length >= 0 )
		{
			for(var i = 0 ; i < cmbObj.options.length ; i++)
			{
				var cmbText = cmbObj.options[i].value;//cmbObj.options[i].text.substr(0 , codeObj.value.length );
				if( codeObj.value.toUpperCase() == cmbText.toUpperCase() )
				{
					cmbObj.selectedIndex = i;
					return;
				}
			}
		}
		//ถ้าไม่เจอ จะชี้ที่ไม่ระบุ
		cmbObj.selectedIndex = 0;
	}
}

// เลือกรายการในคอมโบบ็อก (cmbObj) ที่มีข้อความนำหน้าเหมือนกับค่าที่อยู่ที่ใน textfield (codeObj) โดยไม่จำกัดความยาว
// เปลี่ยนทันทีที่กด KeyBoard แต่ละครั้ง
function codeSelectComboboxByCodeAuto( codeObj , cmbObj )
{
	if( codeObj != null && cmbObj != null )
	{
		if( codeObj.value.length >= 0 )
		{
			for(var i = 0 ; i < cmbObj.options.length ; i++)
			{
				// var cmbText = cmbObj.options[i].value;
				var cmbText = cmbObj.options[i].value.substr(0 , codeObj.value.length );
				if( codeObj.value.toUpperCase() == cmbText.toUpperCase() )
				{
					cmbObj.selectedIndex = i;
					return;
				}
			}
		}
		//ถ้าไม่เจอ จะชี้ที่ไม่ระบุ
		cmbObj.selectedIndex = 0;
	}
}

// เลือกรายการคอมโบบ็อกซ์ (cmbObj) ที่มีค่า value เหมือนกับค่าที่อยู่ใน textfield (codeObj) โดยไม่จำกัดความยาว
function codeSelectComboboxByValue( codeObj ,cmbObj )
{
	if( codeObj != null && cmbObj != null )
	{
		if( codeObj.value.length >= 0 )
		{
			for( i = 0 ; i < cmbObj.options.length ; i++)
			{
				var cmbText = cmbObj.options[i].value.substr(0 , codeObj.value.length );
				if( codeObj.value.toUpperCase() == cmbText.toUpperCase() )
				{
					cmbObj.selectedIndex = i;
					return;
				}
			}
		}
		//ถ้าไม่เจอ จะชี้ที่ไม่ระบุ
		cmbObj.selectedIndex = 0;
	}
}

//นำวันที่และเวลาในรูปแบบ 2004-01-01 และเวลาในรูปแบบ 10:00 (รูปแบบที่เก็บใน database) มาแสดงในวันที่ของคิวต่าง ๆ
//mode เป็นการบอกว่าจะให้แสดงรูปแบบไหน ถ้าไม่กำหนด จะถือว่าเป็นแบบ 1 คือ วว/ดด/ปป,ชม:นาที น.
//                                                            ถ้ากำหนดเป็น 2 จะเป็นแบบ ชม:นาที,วัน(จ,อ,พ,พฤ,ศ,ส,อา) วันที่
function showDateTimeInQueue( queue_date , queue_time , isEng, mode )
{
	if( queue_date != null && queue_time != null )
	{
		if(isEng == null)
			isEng = false;
		if( mode == null || mode == 1  )
		{
			var tmp_date = date2ThaiDate(queue_date, isEng, 1);
			if(tmp_date != null)
			{
				//var tmp_time = time2arr( queue_time);
				if(queue_time != "")
				{
					return tmp_date + ", " + time2ThaiTimeFormat(queue_time, isEng);//tmp_time[0]+":"+tmp_time[1]+" น.";
				}
				else
				{
					return tmp_date;
				}
			}
			else
				return "";
		}
		else if(mode == 2)
		{
			var tmp_time = time2arr(queue_time);
			var show = tmp_time[0]+":"+tmp_time[1];

			var day_nameTh = ["อา.","จ.","อ.","พ.","พฤ.","ศ.","ส."];
			var day_nameEn = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
			var day_name;
			if(isEng == true)
				day_name = day_nameEn;
			else
				day_name = day_nameTh
				;
			var tmp_date = date2arr(queue_date);
			var date_to_show = new Date(Number(tmp_date[2]) , (Number(tmp_date[1])-1) , Number(tmp_date[0]));
			show += "," + day_name[date_to_show.getDay()] + date_to_show.getDate();
			return show;
		}
	}
	else
		return "";
}

//	หาความแตกต่างระหว่างวัน
function getDifDay(from, to)
{
	var dif = from.getTime() - to.getTime();
	return Number(dif/(1000*60*60*24));
}

function setNowDate(yObj, mObj, dObj, nowDateTime, isEng)
{
	var nowdate;
	if(nowDateTime == null || nowDateTime == "undefined" ){
		nowdate = new Date();
    } else {
		nowdate = new Date(nowDateTime);
	}
    var tmp;
	if (nowdate.getDate() < 10) {
		tmp = "0" + nowdate.getDate();
    } else {
		tmp = nowdate.getDate();
    }
	dObj.value = tmp;
	if ((nowdate.getMonth()+1) < 10) {
		tmp = "0" + Number(nowdate.getMonth()+1); 
    } else {
        tmp = Number(nowdate.getMonth()+1);
    }
	mObj.value = tmp;
	yObj.value = nowdate.getFullYear() + ((isEng != null && isEng == true) ? 0 : 543);
}

function setNowTime(hObj, mObj, nowDateTime)
{
	var nowdate;
	if(nowDateTime != null)
		nowdate = new Date();
	else
		nowdate = new Date(nowDateTime);

	if (nowdate.getHours() < 10)
		tmp = "0" + Number(nowdate.getHours());
	else
		tmp = nowdate.getHours();
	hObj.value = tmp;
	if(nowdate.getMinutes() < 10)
		tmp = "0" + Number(nowdate.getMinutes());
	else
		tmp = nowdate.getMinutes();
	mObj.value = tmp;
}

//นำค่าจากตัวแปรลงไปในคอมโบบ็อกซ์ (เลือก index ในคอมโบบ็อกซ์ที่มี value ตรงกับค่าในตัวแปร)
//cmb_obj = id ของคอมโบบ็อกซ์ , var_value = ตัวแปร
function cmbSelectByValue( cmb_obj , var_value )
{
	for( var i = 0 ; i < cmb_obj.options.length ; i++ )
	{
		if( var_value == cmb_obj.options[i].value )
		{
			cmb_obj.selectedIndex = i;
			return true;
		}
	}
	return false;
}
//ให้ค่า text ของ คอมโบบ็อกซ์ออกมาเป็นผลลัพธ์
function getTextFromCmb( cmbObj )
{
	if( cmbObj != null)
	{
		return cmbObj.options[cmbObj.selectedIndex].text;
	}
	return null;
}
//เอาค่าวันที่จาก database ใส่ลงไปใน component (day_obj , month_obj , year_obj ส่งมาแต่ id ) ปล. +ปีให้แล้วด้วย
function setDateToComponent( database_date , day_obj , month_obj , year_obj, isEng)
{
	var arrDate = date2arr(database_date);
	if( arrDate != null )
	{
		day_obj.value = arrDate[0];
		month_obj.value = arrDate[1];
		year_obj.value = Number(arrDate[2]) + ((isEng != null && isEng == true) ? 0 : 543);
	}
	else
	{
		day_obj.value = "";
		month_obj.value = "";
		year_obj.value = "";
	}
}
//เอาค่าเวลาจาก database ใส่ลงไปใน component ( hour_obj , minute_obj )
function setTimeToComponent( database_time , hour_obj , minute_obj )
{
	var arrTime = time2arr(database_time);
	if(arrTime != null )
	{
		hour_obj.value = arrTime[0];
		minute_obj.value = arrTime[1];
	}
	else
	{
		hour_obj.value = "";
		minute_obj.value = "";
	}
}
//นำค่าวันที่ที่ต้องการใส่ลงใน day_obj , month_obj , year_obj โดยสามารถระบุวันได้จาก database_date กับ date_range โดย date_range คือระยะ
//เวลาจาก database_date ที่ต้องการใส่ลงไป เช่น database_time เป็น 2004-10-01 กำหนด range เป็น -1 จะได้วันที่ 2004-09-30 (ถอยหลังไป 1 วัน)
function setSpecifyDateToComponent( database_date , date_range , day_obj , month_obj ,year_obj )
{
	var arrDataBase = date2arr(database_date);
	if( arrDataBase != null )
	{
		date_range = -(date_range);
		var database_date = new Date( Number(arrDataBase[2]) , Number(arrDataBase[1]) - 1, Number(arrDataBase[0]) );
		var specify_date = new Date(database_date - ( date_range*60*60*24*1000 ));
		if( day_obj != null && month_obj != null && year_obj != null)
		{
			day_obj.value = specify_date.getDate();
			if( day_obj.value.length == 1 ) day_obj.value = "0"+day_obj.value;
			month_obj.value = specify_date.getMonth() + 1 ;
			if( month_obj.value.length == 1 ) month_obj.value = "0"+month_obj.value;
			year_obj.value = specify_date.getFullYear() + 543;
		}
	}
}

//	ตรวจสอบว่ามีการพิมพ์ตัวเลขถูกต้องตามรูปแบบของ code ที่กำหนดหรือไม่
function isTrueFormat(oSource, c, hasSiteCode)
{
	var v = oSource.value;
	var sp_code = "-";	//	เครื่องหมาย -
	if(v.length == 0)
	{
		return isNumber(c);
	}
	else if(v.indexOf(sp_code) == -1)
	{
		return (isNumber(c) || (sp_code==String.fromCharCode(c)));
	}
	else if(hasSiteCode)
	{
		if(v.split(sp_code).length < 3)
		{
			return (isNumber(c) || (sp_code==String.fromCharCode(c)));
		}
		else
		{
			return (isNumber(c));
		}
	}
	else if(v.indexOf(sp_code))
	{
		return (isNumber(c));
	}
}

//
function isTrueFormatVN(oSource, c)
{
	var v = oSource.value;
	var sp_code = "/";	//	เครื่องหมาย /
	if(v.indexOf("/") != -1)
	{
		return isNumber(c);
	}
	else
	{
		return (isNumber(c) || (sp_code==String.fromCharCode(c)));
	}
}

function isTrueFormatReceipt(oSource,evtKeyCode)
{
	var v = oSource.value;
	var sp_code = "/";
	var sp_code_2 = "-";
	if(v.indexOf("/") != -1)
	{
		if( v.indexOf("-") != -1 )
		{
			return isNumber(evtKeyCode);
		}
		else
		{
			return (isNumber(evtKeyCode) || (sp_code_2==String.fromCharCode(evtKeyCode)));

		}
	}
	else
	{
		return (isNumber(evtKeyCode) || (sp_code==String.fromCharCode(evtKeyCode)));
	}
}

//หาค่าสูงสุดใน array (ข้อมูลใน array ต้องเป็น number )
function getMaxValueInArray( array )
{
	var max_value = array[0];
	for( var z = 0 ; z < array.length ; z++)
	{
		if( Number(array[z]) > max_value ) max_value = Number(array[z] );
	}
	return max_value;
}
//คำนวณอายุจากวันเกิด ถ้าอายุมากกว่า 1 ปี จะรีเทิร์นเป็น ปี แต่ถ้าน้อยกว่า 1 ปี จะรีเทิร์นเป็น เดือนกับวัน
function calculateAge( birthdate )
{
	var nowDate = new Date();
	var arrBirthDate = date2arr(birthdate);
	var birthDate = new Date();
	if( arrBirthDate != null )
	{
		birthDate = new Date(Number(arrBirthDate[2]), Number(arrBirthDate[1])-1, Number(arrBirthDate[0]));
	}
	var pAge = new Date(nowDate-birthDate);
	var yearOld = "";
	var monthOld = "";
	var dayOld = "";
	if(pAge.getYear() > 1000)
	{
		 yearOld = pAge.getYear()-1970;
	}
	else
	{
		yearOld = pAge.getYear()-70;
	}
	monthOld = pAge.getMonth();
	dayOld = pAge.getDate();
	if( yearOld > 0 )
		return yearOld+" ปี";
	else if( yearOld == 0 && monthOld > 0 )
		return monthOld+"ด."+dayOld+"ว.";
	else if( yearOld == 0 && monthOld == 0)
		return dayOld+".วัน";
}

function txtYearOnBlur(oSource)
{
	var v = oSource.value;
	if(v.length == 2)
	{
		//	check หลักของปีทีเป็นสอง
		v = "25" + v;
		oSource.value = v;
	}
	else if(v.length == 4 && Number(v) < 2400)
	{
		oSource.value = Number(v) + 543;
	}
}

/** แสดงข้อความใน tdObj โดยจะไม่ยอมให้แสดงผลมากกว่าความยาวที่กำหนดไว้ และความสูงที่กำหนดไว้ตอนแรกด้วย 
  * โดยหากเกิน จะทำการตัดข้อความออก จนกว่าจะแสดงผลได้ และจะเติม ... ต่อท้ายไปเป็นการบอกว่าแสดงผลไม่พอ ต้องตัดออก
  * 01 December 2005 - ชุ้ง - แก้ไขการทำงานของฟังก์ชัน
*/
function showTextTD( tdObj , text2show )
{
	if( tdObj != null && text2show != null )
	{
		tdObj.innerText = " ";
		var normalHeight = tdObj.offsetHeight; //ความสูงตอนปกติ
		var normalWidth = tdObj.offsetWidth; //ความกว้างตอนปกติ
		//alert("width = "+tdObj.width+" , height = "+tdObj.height+" , offsetWidth = "+tdObj.offsetWidth+" , offsetHeight = "+tdObj.offsetHeight);
		tdObj.innerText = text2show;
		var isCutText = false ; //บอกว่าต้องตัดข้อความเพื่อให้แสดงผลได้ในความสูงปกติหรือเปล่า
		//alert("after add height = "+tdObj.getAttribute("offsetHeight"));
		while( tdObj.offsetHeight > normalHeight )
		{
			if( isCutText == false ) isCutText = true;
			tdObj.innerText = tdObj.innerText.substring( 0 , tdObj.innerText.length - 1);
		}
		//alert("after add width= "+tdObj.getAttribute("offsetWidth"));
		while( tdObj.offsetWidth > normalWidth )
		{
			if( isCutText == false ) isCutText = true;
			tdObj.innerText = tdObj.innerText.substring( 0 , tdObj.innerText.length - 1);
		}
		if( isCutText )
		{
			tdObj.innerText = tdObj.innerText.substring( 0 , tdObj.innerText.length - 3);
			tdObj.innerText += "...";
		}
	}
}

//เช็คเวลา 2 ช่วงว่าทับกันหรือเปล่า ส่งช่วงเวลามาด้วยคำสั่ง setTime (รูปแบบ 08:00)
//return true เมื่อเวลาทับกัน
//return false เมื่อเวลาไม่ทับกัน
//return null เมื่อส่งพารามิเตอร์มาผิด
function conCurrentPeriodTime( startPeriodTimeA , endPeriodTimeA , startPeriodTimeB , endPeriodTimeB )
{
	if( startPeriodTimeA != "" && startPeriodTimeA != null && endPeriodTimeA != "" && endPeriodTimeA != null && startPeriodTimeB != "" && startPeriodTimeB != null && endPeriodTimeB != "" && endPeriodTimeB != null)
	{
		var startATime = new Date();
		var arrTime = startPeriodTimeA.split(":");
		startATime.setHours(arrTime[0] , arrTime[1] , 0 , 0);
		var endATime = new Date();
		arrTime = endPeriodTimeA.split(":");
		endATime.setHours( arrTime[0] , arrTime[1] , 0 , 0);
		var startBTime = new Date();
		arrTime = startPeriodTimeB.split(":");
		startBTime.setHours(arrTime[0] , arrTime[1] , 0 , 0);
		var endBTime = new Date();
		arrTime = endPeriodTimeB.split(":");
		endBTime.setHours(arrTime[0] , arrTime[1] , 0 , 0);
		//เช็คเงื่อนไข กรณี เวลาเริ่มต้น B อยูในช่วงเวลา A
		if( startBTime >= startATime && startBTime <= endATime )
		{
			return true;
		}
		//กรณี เวลาสิ้นสุด B อยู่ในช่วงเวลา A
		if( endBTime >= startATime && endBTime <= endATime )
		{
			return true;
		}
		//กรณีเวลาเริ่มต้น A อยู่ในช่วงเวลา B
		if( startATime >= startBTime && startATime <= endBTime )
		{
			return true;
		}
		return false;
	}
	else
	{
		return null;
	}
}

//หา obj ที่ถูกเลือกในชุดของ radio obj ที่ชื่อเหมือนกัน 
//คืนค่าเป็น rad obj ที่ถูกเลือกถ้าพบ ถ้าไม่พบว่ามีอันไหนถูกเลือกจะคืนค่าเป็น false
function getRadioObj( radArrayObj )
{
	for( var v = 0 ; v < radArrayObj.length ; v++)
	{
		if( radArrayObj[v].checked == true )
		{
			return radArrayObj[v];
		}
	}
	return false;
}

function str2Number(str)
{
	str = String(str);
	var minus = "";
	if(str.indexOf("-") != -1)
	{
		minus = "-";
		str = str.substr(1);
	}

	var dec = "";
	var digit = "";
	if(str.indexOf(".") != -1)
	{
		dec = str.substr(0, str.indexOf("."));
		if(str.substr(str.indexOf(".")+1) != "00")
			digit = "." + str.substr(str.indexOf(".")+1);
	}
	else
		dec = str;

	var sum_length = dec.length;
	var tmp = "";
	for(var i = sum_length; i > 0; i-=3)
	{
		if(tmp != "")
			tmp = dec.substr(i-3, i >= 3 ? 3 : i) + "," + tmp;
		else
			tmp = dec.substr(i-3, i >= 3 ? 3 : i);
	}
	//กำหนดให้แสดงทศนิยมแค่สองหลัก
	if(digit.length > 3 ) digit = digit.substr(0,3);
	return minus+tmp + digit;
}

function addComma(str)
{
	str = String(str);
	var minus = "";
	if(str.indexOf("-") != -1)
	{
		minus = "-";
		str = str.substr(1);
	}

	var dec = "";
	var digit = "";
	if(str.indexOf(".") != -1)
	{
		dec = str.substr(0, str.indexOf("."));
		digit = "." + str.substr(str.indexOf(".")+1);
	}
	else
		dec = str;

	var sum_length = dec.length;
	var tmp = "";
	for(var i = sum_length; i > 0; i-=3)
	{
		if(tmp != "")
			tmp = dec.substr(i-3, i >= 3 ? 3 : i) + "," + tmp;
		else
			tmp = dec.substr(i-3, i >= 3 ? 3 : i);
	}
	return minus + tmp + digit;
}

function setDate2Component(date, yObj, mObj, dObj)
{
	if (date.getDate() < 10) 
		tmp = "0" + date.getDate(); 
	else 
		tmp = date.getDate();
	dObj.value = tmp;
	if ( (date.getMonth()+1) < 10) 
		tmp = "0" + Number(date.getMonth()+1); 
	else 
		tmp = Number(date.getMonth()+1);

	mObj.value = tmp;
	yObj.value = date.getFullYear()+543;
}

function autoScroll( tblName , tblSelectRowIndex , iframeObj )
{
	if( tblName != null && tblSelectRowIndex != null && iframeObj != null && tblName != "" && tblSelectRowIndex != "" && iframeObj != "" )
	{
		var ifrmHeight = iframeObj.document.body.clientHeight;
		var heightToSelRow = 0 ; 
		for( var i = 0 ; i < tblSelectRowIndex + 1 ; i++)
		{
			heightToSelRow += tblName.rows[i].offsetHeight;
		}
		if( heightToSelRow > ifrmHeight )
		{
			if( event.keyCode == 40 )
			{
				iframeObj.window.scrollBy(0 , (tblName.rows[tblSelectRowIndex].offsetHeight));
			}
			else if( event.keyCode == 38)
			{
				iframeObj.window.scrollBy(0 , -(tblName.rows[tblSelectRowIndex].offsetHeight));
			}
		}
		else if( heightToSelRow < ifrmHeight )
		{
			if( event.keyCode == 38 )
			{
				iframeObj.window.scrollBy(0 , -(tblName.rows[tblSelectRowIndex].offsetHeight));
			}
		}
	}
}

function alertObj( obj )
{
	var id = obj.id;
	var oldColor = obj.style.borderColor;
	setTimeout(id+".style.borderColor='red';",500);
	setTimeout(id+".style.borderColor='"+oldColor+"';",1000);
	setTimeout(id+".style.borderColor='red';",1500);
	setTimeout(id+".style.borderColor='"+oldColor+"';",2000);
	setTimeout(id+".style.borderColor='red';",2500);
	setTimeout(id+".style.borderColor='"+oldColor+"';",3000);
}

function alertTxtObj( txtObj )
{
	setTimeout(txtObj.style.backgroundColor = 'red' , 500);
	setTimeout(txtObj.style.backgroundColor = 'white', 1000);
	setTimeout(txtObj.style.backgroundColor = 'red' , 1500);
	setTimeout(txtObj.style.backgroundColor = 'white', 2000);
	setTimeout(txtObj.style.backgroundColor = 'red' , 2500);
	setTimeout(txtObj.style.backgroundColor = 'white', 3000);
}

function float2arr(float_value)
{
	var arr = new Array();
	if(float_temp != "")
	{
		var float_temp = float_value.toString();
		if(float_temp.indexOf(".") == -1)
		{
			arr[0]= float_temp;
			arr[1] = 0;
		}
		else
		{
			arr[0] = float_temp.substring(0, float_temp.indexOf("."));
			arr[1] = "0." + float_temp.substring(float_temp.indexOf(".")+1);
		}
		return arr;
	}
	else
	{
		arr[0] = 0;
		arr[1] = 0;
		return null;
	}
}

//คืนค่าเป็นข้อความในตัวแปร text สำหรับใช้ในการค้นใน SQL (ดักเครื่องหมาย ' , "" )
function getTextToSQLSearch( txtObj )
{
	var text = txtObj.value;
	var exp =/\'/g;
	text = text.replace( exp , "\\'" );
	exp = /\"/g;
	text = text.replace( exp , "\\\"");
	return text;
}

function isBlank(param)
{
	return param==null || param=="" || param=="null";
}

/* ใช้ในการดึงตัวหนังสือบอกว่าเป็นวันอะไรออกมา โดย
	fixDayOfWeek เอามาจากค่า FixDayOfWeek.java ซึ่งจะอ้างอิงค่าที่ใช้เปรียบเทียบจากไฟล์ ๆ นี้
	isLongText ส่งมาเป็น boolean หากเป็น true จะ return เป็นตัวหนังสือแบบเต็ม เช่น จันทร์ , อังคาร ฯลฯ หากเป็น false จะ return เป็นตัวย่อ เช่น จ. , อ. ฯลฯ

	return ตัวหนังสือบอกว่าเป็นวันอะไรออกไป หากไม่ส่ง isLongText มา จะถือว่าส่งมาเป็น false (แบบสั้น)
	return "" หากไม่พบข้อมูล
*/
function getDayNameByFixDayOfWeek( fixDayOfWeek , isLongText )
{
	var returnText = "";
	var mapDataFixDayOfWeek = new Array( "1" , "2" , "3" , "4" , "5" , "6" , "7") ;
	var mapLongText = new Array("อาทิตย์" , "จันทร์" , "อังคาร" , "พุธ" , "พฤหัสบดี" , "ศุกร์" , "เสาร์" );
	var mapShortText = new Array("อา." , "จ." , "อ." , "พ." , "พฤ." , "ศ." , "ส." );
	if( fixDayOfWeek != null )
	{
		for(var x = 0 ; x < mapDataFixDayOfWeek.length ; x++)
		{
			if( fixDayOfWeek == mapDataFixDayOfWeek[x] )
			{
				if( isLongText == true )
					returnText = mapLongText[x];
				else
					returnText = mapShortText[x];
				break;
			}
		}
	}
	return returnText;
}

/**
	ใช้สำหรับใส่ตอน onkeypress ของ text ที่ใช้ใส่ช่องข้อมูลที่เป็น primary key ซึ่งจะทำให้กรอกได้แต่ ตัวอักษรภาษาอังกฤษ , ตัวเลข , ขีดเส้นใต้
*/
function primaryKeyOnKeypress()
{
	var keyCode = event.keyCode;
	if( (keyCode >= 48 && keyCode <= 57) || (keyCode >= 65 && keyCode <= 90 ) || (keyCode >= 97 && keyCode <= 122 ) || (keyCode == 95 ) )
	{
		return true;
	}
	return false;
}


/**
 */
function setTDColorBG(theCell, newColor)
{
	theCell.style.backgroundColor = newColor;
}

function setTDColorFont(theCell, newColor)
{
	theCell.style.color = newColor;
}
function getDayOfWeek(index)
{
	var thai_day = ["อา.", "จ.", "อ.", "พ.", "พฤ.", "ศ.", "ส."];
	return thai_day[index];
}

//	แปลงชื่อจาก str เป็น array โดยตัดคำโดยใช้ช่องว่าง(spacebar)
function splitNameToArray(name)
{
	var nameArr = name.split(" ");
	var arr = new Array();
	if(nameArr.length > 0)
		arr["prename"] = nameArr[0];
	else
		arr["prename"] = "";

	if(nameArr.length > 1)
		arr["firstname"] = nameArr[1];
	else
		arr["firstname"] = "";

	if(nameArr.length > 2)
	{
		var tmp = "";
		for(var i = 2; i < nameArr.length; i++)
		{
			tmp = tmp + (tmp!="" ? " " : "") + nameArr[i];
		}
		arr["lastname"] = tmp;
	}
	else
		arr["lastname"] = "";
	return arr;
}

function formatDateDbFormat(d, m, y, isEng)
{
	if(d == "" || m == "" || y == "")
		return "";
	else
	{
		if(isEng != null && isEng == true)
			y = Number(y);
		else
			y = Number(y) - 543;

		if(isTrueDate(y, m-1, d))
		{
			if(d.toString().length < 2)
				d = "0" + d;

			if(m.toString().length < 2)
				m = "0" + m;

			return y + "-" + m + "-" + d;
		}
		else
			return -1;
	}
}

function formatTimeDbFormat(h, mm)
{
	if(h == "" || mm == "")
		return "";
	else
	{
		if(isTrueTime(h, mm, 59))
		{
			if(h.toString().length < 2)
				h = "0" + h;

			if(mm.toString().length < 2)
				mm = "0" + mm;

			return h + ":" + mm + ":59";
		}
		else
			return -1;
	}
}

function selectAllForm(form)
{
	for(var i = 0; i < form.elements.length; i++)
	{
		var elem = form.elements[i];
		if(elem.tagName == "SELECT")
		{
			for(var j = 0; j < elem.options.length; j++)
			{
				elem.options[j].selected = true;
			}
		}
	}
}

function getDateDiffInTime(dateBegin, dateEnd)
{

	var start = dateBegin.getTime();
	var end = dateEnd.getTime();
	var mmSec = end-start;
	var minuteTmp = mmSec/(1000*60);
	var hour = parseInt(minuteTmp/60, 10);
	var minute = minuteTmp%60;
	var arr = new Array();
	if(hour<0 || minute<0)
	{
		return -1;
	}
	else
	{
		arr["hour"] = hour;
		arr["minute"] = minute;
		return arr;
	}

}

function getIpdAccFromListOrderItemVO(listOrderItemVO)
{
	var ipdAcc = "";
	for(var i = 0; i < listOrderItemVO.length; i++)
	{
		var orderItemVO = listOrderItemVO[i];
		ipdAcc += "(" + addComma(orderItemVO.quantity) + "*" + addComma(orderItemVO.unit_price_sale) + "),";
	}
	if(ipdAcc != "")
		ipdAcc = ipdAcc.substr(0, ipdAcc.length-1);
	return ipdAcc;
}

function formatNumber(num,decimalNum,bolLeadingZero,bolParens,bolCommas){
    if (isNaN(parseInt(num))) return "NaN";

	var tmpNum = num;
	var iSign = num < 0 ? -1 : 1;		
	
	tmpNum *= Math.pow(10,decimalNum);
	tmpNum = Math.round(Math.abs(tmpNum))
	tmpNum /= Math.pow(10,decimalNum);
	tmpNum *= iSign;	
	
	var tmpNumStr = new String(tmpNum);

	if (!bolLeadingZero && num < 1 && num > -1 && num != 0)
		if (num > 0)
			tmpNumStr = tmpNumStr.substring(1,tmpNumStr.length);
		else
			tmpNumStr = "-" + tmpNumStr.substring(2,tmpNumStr.length);
		
	if (bolCommas && (num >= 1000 || num <= -1000)) {
		var iStart = tmpNumStr.indexOf(".");
		if (iStart < 0)
			iStart = tmpNumStr.length;

		iStart -= 3;
		while (iStart >= 1) {
			tmpNumStr = tmpNumStr.substring(0,iStart) + "," + tmpNumStr.substring(iStart,tmpNumStr.length)
			iStart -= 3;
		}		
	}

	if (bolParens && num < 0)
		tmpNumStr = "(" + tmpNumStr.substring(1,tmpNumStr.length) + ")";

	return tmpNumStr;	
}

function isIEBrowser() {
	return (navigator.appName.toUpperCase().indexOf("MICROSOFT") != -1);
}

function getXMLRequest() {
	if(window.XMLHttpRequest) {
		return new XMLHttpRequest();
	}
	else if(window.ActiveXObject) {
		return new ActiveXObject("Microsoft.XMLHTTP");
	}
	else {
		return false;
	}
}

function getNowTimeMS() {
	var d = new Date();
	return d.getTime();
}

// ajax แบบ asynchronously
function getData(doFunction, url, other) {
	var xmlRequest = getXMLRequest();
	if(xmlRequest) {
		if(url.indexOf("?") != -1)
			xmlRequest.open("GET", url + "&timeStamp=" + getNowTimeMS());
		else
			xmlRequest.open("GET", url + "?timeStamp=" + getNowTimeMS());

		xmlRequest.onreadystatechange = function() {
			if(xmlRequest.readyState == 4 && xmlRequest.status == 200) {
				doFunction(xmlRequest, other);
			}
		}
		xmlRequest.send(null);
	}
}

// ajax แบบ synchronously
function getDataSync(doFunction, url, other)
{
	var xmlRequest = getXMLRequest();
	if(xmlRequest) {
		if(url.indexOf("?") != -1)
			xmlRequest.open("GET", url + "&timeStamp=" + getNowTimeMS(), false);
		else
			xmlRequest.open("GET", url + "?timeStamp=" + getNowTimeMS(), false);

		xmlRequest.onreadystatechange = function() {
			if(xmlRequest.readyState == 4 && xmlRequest.status == 200) {
				doFunction(xmlRequest, other);
			}
		}
		xmlRequest.send(null);
	}
}

function invalidKeyStep(keyArr)
{
	if(keyArr.length > 3)
	{
		if(keyArr[keyArr.length-2] == "144" && keyArr[keyArr.length-4] == "144")
		{
			return true;
		}
	}
	return false;
}

function moveToComponent(oTarget)
{
	oTarget.focus();
	oTarget.blur();
	if(oTarget.type.toUpperCase() == "TEXT")
	{
		oTarget.select();
	}
	else
	{
		oTarget.focus();
	}
}

function getPidArray(pid)
{
	var arr = new Array();
	if(pid.length > 0)
	{
		arr[0] = pid.substr(0,1);
		arr[1] = pid.substr(1,4);
		arr[2] = pid.substr(5,5);
		arr[3] = pid.substr(10,2);
		arr[4] = pid.substr(12);
	}
	return arr;
}

function noUndefined(str)
{
	return (typeof(str) == "undefined") ? "" : str;
}

function formatCurrency(str, isAddComma)
{
	if(isAddComma == true)
		return addComma(str ? Number(str).toFixed(2) : "0.00");
	else
		return str ? Number(str).toFixed(2) : "0.00";
}

function formatCurrencyNotDigit(str, isAddComma)
{
	str = str+"";
	var dotIndex = str.indexOf(".");
	if(dotIndex != -1)
	{
		var digitValue = str.substring(dotIndex+1);
		if(Number(digitValue) > 0)
		{
			return (isAddComma == true) ? addComma(str) : str;
		}
		else
		{
			str = str.substring(0, dotIndex);
			return (isAddComma == true) ? addComma(str) : str;
		}
	}
	else
	{
		return (isAddComma == true) ? addComma(str) : str;
	}
}

function createHTMLElement(tag)
{
	var elem;
	tag = tag.toUpperCase();
	switch(tag)
	{
		case "BUTTON":
		case "CHECKBOX":
		case "RADIO":
			elem = document.createElement("INPUT");
			elem.type = tag;
			break;
		case "TEXTBOX":
			elem = document.createElement("INPUT");
			elem.type = "text";
			elem.className = "txtBorder";
			break;
		default :
			elem = document.createElement(tag);
			break;
	}
	return elem;
}

function formatStringHTML(str)
{
	if(str && str.length > 0)
		return str;
	else
		return "&nbsp;";
}
function setKeyYearFormatLn(txt_object)
{
	if(txt_object.value != "")
	{
		var slash_pos = txt_object.value.indexOf("/");
		if(slash_pos == -1)
		{
			if(txt_object.value.length <= 4)
			{
				var now = new Date();
				var date = addZero((String)(now.getDate()), 2);
				
				var month = addZero((String)(now.getMonth()+1), 2);
				
				var year = ((String)(now.getYear())).substr(2);
				var yearBuddhist = (String)((now.getYear()+543)-2500);
				var tmp = txt_object.value;
				txt_object.value = txt_object.value + "/" + date + month + year;
				tmp = addZero(tmp,4);
				
				tmp = "1" + year + month + date + tmp;
			}
			var arr = new Array();
			arr["value"] = txt_object.value;
			arr["code"] = tmp;
			return arr;
		}
		else
		{
			var arr = txt_object.value.split("/");

			//	field hn มี - อยู่
			if(arr[arr.length-1].length == 6)
			{
				var tmp = txt_object.value.substring(0, txt_object.value.lastIndexOf("/"));
				tmp = addZero(tmp, 4);
				var tmpDate = arr[arr.length -1];
				tmp =  "1" + addZero((String)(tmpDate.substr(4)-43),2)  + tmpDate.substr(2,2) + tmpDate.substr(0,2) + tmp;
				var arr = new Array();
				arr["value"] = txt_object.value;
				arr["code"] = tmp;
				return arr;
			}
			else
			{
				alert("รูปแบบในการค้นหาผู้ป่วยด้วยผิดพลาด");
				txt_object.select();
				return -1;
			}
		}
	}
	else
	{
		return -1;
	}
}

function date2str(date)
{
	var y = date.getYear();
	var m = date.getMonth()+1;
	if(m < 10)
		m = "0" + m;

	var d = date.getDate();
	if(d < 10)
		d = "0" + d;
	return y + "-" + m + "-" + d;
}

function getAgeStrFormAge(age)
{
	var arr = age.split("-");
	var showAge = "";
	if(arr[0] != null && arr[0] != "" && arr[0] != "0")
		showAge = arr[0] + "ปี";
	if(arr[1] != null && arr[1] != "" && arr[1] != "0")
	{
		if(showAge != "")
			showAge += "  ";
		showAge += arr[1] + "เดือน";
	}
	if(arr[2] != null && arr[2] != "" && arr[2] != "0")
	{
		if(showAge != "")
			showAge += "  ";
		showAge += arr[2] + "วัน";
	}
	return showAge;
}

function multiply(x1, x2)
{
	return Number((Math.round((Math.round(Number(x1)*Number(x2)*1000)/1000)*100)/100));
}

function iMedTreeMenu(divMenu, allData)
{
	for(var i = 0; i < allData.length; i++)
	{
		var menu = allData[i];
		//mainmenu
		var mainMenuDiv = createHTMLElement("DIV");
		mainMenuDiv.id = "imedTreeMainMenu" + i;
		mainMenuDiv.style.cursor = "hand";
		mainMenuDiv.innerHTML = "<font style='width:7px'>+</font> " + menu.label;
		mainMenuDiv.divType = "MAIN";
		mainMenuDiv.label = menu.label;

		//submenu
		for(var j = 0; j < menu.submenu.length; j++)
		{
			var subMenu = menu.submenu[j];
			var contentSubMenu = createHTMLElement("DIV");
			contentSubMenu.data = subMenu;
			contentSubMenu.innerHTML = subMenu.label;
			contentSubMenu.style.cursor = "hand";
			contentSubMenu.style.marginLeft = "15px";	//margin submenu
			contentSubMenu.style.display = "none";
			contentSubMenu.divType = "SUB";
			contentSubMenu.hasSub = false;
			contentSubMenu.label = subMenu.label;
			mainMenuDiv.appendChild(contentSubMenu);

			//submenu2
			if(subMenu.submenu)
			{
				contentSubMenu.hasSub = true;
				contentSubMenu.innerHTML = "<font style='width:7px'>-</font> " + subMenu.label;
				contentSubMenu.style.marginLeft = "10px";	//margin submenu
				for(var k = 0; k < subMenu.submenu.length; k++)
				{
					var subMenu2 = subMenu.submenu[k];
					var contentSubMenu2 = createHTMLElement("DIV");
					contentSubMenu2.data = subMenu2;
					contentSubMenu2.innerHTML = subMenu2.label;
					contentSubMenu2.style.cursor = "hand";
					contentSubMenu2.style.marginLeft = "10px";	//margin submenu
					contentSubMenu2.style.marginBottom = "2px";	//margin submenu
					contentSubMenu2.style.display = "none";
					contentSubMenu2.divType = "SUB2";
					contentSubMenu2.label = subMenu2.label;
					contentSubMenu.appendChild(contentSubMenu2);
				}

				contentSubMenu.is_expand = true;
				contentSubMenu.onclick = function() {
				var sr = event.srcElement;

					if(sr.tagName == "FONT")
						sr = sr.parentElement;
					if(sr.tagName ==  "DIV")
					{
						if(sr.divType == "SUB")
						{
							var childs = this.getElementsByTagName("DIV");
							this.is_expand = !this.is_expand;
							var styleDisplay = this.is_expand ? "" : "none";
							//+ or -
							var signExpand = this.getElementsByTagName("font")[0];
							signExpand.innerHTML = (this.is_expand ? "-" : "+");
							//display/hide subMenu
							for(var k = 0; k < childs.length; k++)
							{
								childs[k].style.display = styleDisplay;
							}
						}
						else if(sr.divType == "SUB2")
						{
							//ล้าง hilight ทั้งหมด
							for(var i = 0; i < divMenu.childNodes.length; i++)
							{
								var subchildNodes = divMenu.childNodes[i].getElementsByTagName("DIV");
								
								for(var j = 0; j < subchildNodes.length; j++)
								{
									subchildNodes[j].className = "";
									var lastsubchildNodes = subchildNodes[j].getElementsByTagName("font");

									for(var k = 0; k < lastsubchildNodes.length; k++)
									{
										lastsubchildNodes[k].className = "";
									}
								}
							}
							//set hilight ที่เลือก
							sr.className = "bgHeaderForm";
						}
					}
				}
			}
		}

		mainMenuDiv.is_expand = false;
		mainMenuDiv.onclick = function() {
			var sr = event.srcElement;

			if(sr.tagName == "FONT")
				sr = sr.parentElement;
			if(sr.tagName ==  "DIV")
			{
				if(sr.divType == "MAIN")
				{
					var childs = this.getElementsByTagName("DIV");
					this.is_expand = !this.is_expand;
					var styleDisplay = this.is_expand ? "" : "none";
					//+ or -
					var signExpand = this.getElementsByTagName("font")[0];
					signExpand.innerHTML = (this.is_expand ? "-" : "+");
					//display/hide subMenu
					var subExplain = false;
					for(var k = 0; k < childs.length; k++)
					{
						/*if(childs[k].divType == "SUB")
						{
							if(childs[k].hasSub == true)
								childs[k].style.display = childs[k].is_expand ? "" : "none";
							else
								childs[k].style.display = styleDisplay;
						}*/
						if(childs[k].divType == "SUB")
						{
							childs[k].style.display = styleDisplay;

							if(childs[k].hasSub == true)
							{
								subExplain = childs[k].is_expand;
								//var signExpandSub = childs[k].getElementsByTagName("font")[0];
								//signExpandSub.innerHTML = "-";
							}
						}
						else if(childs[k].divType == "SUB2" && subExplain)
						{
							childs[k].style.display = styleDisplay;
						}
					}
				}
				else if(sr.divType == "SUB")
				{
					if(sr.hasSub == false)
					{
						for(var i = 0; i < divMenu.childNodes.length; i++)
						{
							var subchildNodes = divMenu.childNodes[i].getElementsByTagName("DIV");
							
							for(var j = 0; j < subchildNodes.length; j++)
							{
								subchildNodes[j].className = "";
								
								var lastsubchildNodes = subchildNodes[j].getElementsByTagName("font");

								for(var k = 0; k < lastsubchildNodes.length; k++)
								{
									lastsubchildNodes[k].className = "";
								}
							}
						}
						//set hilight ที่เลือก
						sr.className = "bgHeaderForm";
					}
				}
			}
		}

		divMenu.appendChild(mainMenuDiv);
	}
}