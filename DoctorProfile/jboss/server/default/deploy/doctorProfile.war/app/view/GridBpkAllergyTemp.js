Ext.define('DA.view.GridBpkAllergyTemp',
{
	extend : 'Ext.grid.Panel',
	alias : 'widget.GridBpkAllergyTemp',
	title : 'BPK9 Allergy Temp',
	store : 'ListBpkAllergyTemp',
	loadMask : true,
	autoheight : true,
	// height: 512,
	width : 1024,
	omitColumns : [ 'ID' ],
	dockedItems : [
	{
		// xtype: 'toolbar',
		xtype : 'pagingtoolbar',
		store : 'ListBpkAllergyTemp',
		dock : 'top',
		displayInfo : true,
		listeners :
		{
			afterrender : function()
			{
				this.child('#refresh').hide();
				// alert('TEST');
			}
		},
		items : [
		{
			xtype : 'tbseparator'
		},
		{
			xtype : 'trigger',
			itemId : 'gridTrigger',
			fieldLabel : 'Filter Data',
			triggerCls : 'x-form-clear-trigger',
			emptyText : 'กรอกข้อมูลเพื่อค้นหา',
			size : 30,
			minChars : 1,
			enableKeyEvents : true,
			onTriggerClick : function()
			{
				this.reset();
				this.fireEvent('triggerClear');
			}
		} ]
	} ],
	initComponent : function()
	{
		this.columns = [
		{
			header : 'HN',
			dataIndex : 'hn',
			flex : 40
		},
		{
			header : 'Patient Name',
			dataIndex : 'patientName',
			flex : 80
		},
		{
			header : 'Note',
			dataIndex : 'note',
			flex : 200
		},
		{
			header : 'Last modified by',
			dataIndex : 'employeeName',
			flex : 80
		},
		{
			header : 'Last modified date/time',
			dataIndex : 'modifyDateTime',
			flex : 60,
			align : 'right',
			renderer : function(value)
			{
				return Ext.Date.format(Ext.Date.parse(value, 'Y-m-d H:i:s'), 'D j M Y H:i');
				// return Ext.Date.parse(value, 'Y-m-d
				// H:i:s').toLocaleDateString();
			}
		},
		{
			header : 'ID',
			dataIndex : 'drugAllergyTempId',
			flex : 0,
			hidden : true
		} ];

		this.callParent(arguments);
	}, 
	listeners :
	{
		itemdblclick: function(dv, record, item, index, e) 
	    {
			// alert(record.get('hn'));
			try
			{
				// alert(top.opener.top.mainFrame.patientInfo_txtHnNumber.hn);
				var hn = record.get('hn');
				top.opener.top.mainFrame.patientInfo_txtHnNumber.value = hn;			
	
				var hn1 = hn.substring(0, 4).replace('-', '');
				var hn2 = hn.substring(5);
				
				top.opener.top.mainFrame.mainSetStatusWork("กำลังดึงข้อมูลผู้ป่วย HN "+hn);
				top.opener.top.mainFrame.mainPatientVO.hn = hn1+'000'+hn2;
				top.opener.top.jspFrame.UCForm.UC.value = "readEditPatientByHN";
				top.opener.top.jspFrame.UCForm.submit();
			}
			catch(e)
			{
				
			}
		}
	}

});