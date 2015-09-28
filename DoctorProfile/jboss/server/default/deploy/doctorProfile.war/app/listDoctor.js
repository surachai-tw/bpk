Ext.Loader.setPath('Ext.ux', 'lib/extjs/ux');

Ext.Loader.setConfig(
{
	enabled : true
});

Ext.require([ 'Ext.data.*', 'Ext.grid.*', 'Ext.ux.data.PagingMemoryProxy' ]);

// Check Compatibility View Setting
// Example from https://gist.github.com/jasongaylord/5733469
// alert(navigator.userAgent);
// Create new ieUserAgent object
var ieUserAgent =
{
	init : function()
	{
		// Get the user agent string
		var ua = navigator.userAgent;
		this.compatibilityMode = false;

		// alert (ua);

		if (ua.indexOf("MSIE") == -1) {
			this.version = 0;
			return 0;
		}

		if (ua.indexOf("compatible") == -1) {
			this.compatibilityMode = false;
			return 0;

		}
		else {
			this.compatibilityMode = true;
			return 1;
		}
	}
};

function setCursorByID(id, cursorStyle) 
{
	 var elem;
	 if (document.getElementById &&
	    (elem=document.getElementById(id)) ) 
	 {
	  if (elem.style) elem.style.cursor = cursorStyle;
	 }
}

Ext.application(
{
	name : 'Employee',

	appFolder : 'app',

	controllers : [ 'BpkEmployeeManage' ],

	launch : function()
	{
		try {
			console.log("Launching the application!");
		}
		catch (e) {

		}

		Ext.Ajax.request(
		{
			loadMask : true,
			url : 'ListDoctorServlet?optionWithSchedule=1',
			scope : this,
			success : function(response, callOptions)
			{
				myData = Ext.decode(response.responseText);
				// myTempData = Ext.decode(response.responseText);
				Ext.create('Ext.container.Viewport',
				{
					items : [
					{
						xtype : 'GridBpkEmployee'
					} ]
				});

				// กรณีที่เป็น Compatibility View ต้องใช้วิธีนี้ 
				if(ieUserAgent.init()==1)
				{
					// ขอค่าจาก localStorage มา Set ลงในหน้าจอ ถ้ามีค่าอยู่ก่อนแล้ว
					// alert("txtEmployeeName =
					// "+localStorage.getItem("txtEmployeeName"));
					var pClinicDescription = localStorage.getItem("txtClinicDescription");
					var pSpecialty = localStorage.getItem("txtSpecialty");
					var pEmployeeName = localStorage.getItem("txtEmployeeName");
					var pOptionWithSchedule = localStorage.getItem("radOptionWithSchedule");
	
					Ext.getCmp("txtClinicDescription").setValue(pClinicDescription);
					Ext.getCmp("txtSpecialty").setValue(pSpecialty);
					Ext.getCmp("txtEmployeeName").setValue(pEmployeeName);
					if (pOptionWithSchedule)
						Ext.getCmp("radOptionWithSchedule").setValue(true);
					else
						Ext.getCmp("radOptionWithoutSchedule").setValue(true);
				}
			},
			failure : function(response, callOptions)
			{
			// Use the response
			}
		});

	}

});