Ext.define('Employee.controller.BpkEmployeeManage',
{
	extend : 'Ext.app.Controller',

	// define the stores
	stores : [ 'ListBpkEmployee' ],
	// define the models
	models : [ 'BpkEmployeeVO' ],
	// define the views
	views : [ 'GridBpkEmployee' ],
	refs : [
	{
		ref : 'myGrid',
		selector : 'grid'
	} ],

	init : function()
	{
		this.control(
		{
			// control function makes it easy to listen to events on
			// your view classes and take some action with a handler function
			// when you click Submit button
			'GridBpkEmployee button[action=find]' :
			{
				click : this.onFindRequest
			},
			'GridBpkEmployee button[action=reset]' :
			{
				click : this.onResetRequest
			},
			/*
			 * 'GridBpkEmployee button[id=hideShow]' : { toggle :
			 * this.onSummaryToggle },
			 */
			'GridBpkEmployee button[id=grouping]' :
			{
				toggle : this.onSummaryDisable
			},
			'GridBpkEmployee button[id=collapse]' :
			{
				toggle : this.onGroupCollapse
			},
			'viewport > panel' :
			{
				render : this.onPanelRendered
			},
			'GridBpkEmployee #gridTrigger' :
			{
				keyup : this.onTriggerKeyUp,
				triggerClear : this.onTriggerClear
			}
		});
	},

	/*
	 * onSummaryToggle : function(button, pressed) { console.log('Sumary toggle
	 * button was pressed: ' + pressed); var buttonText = pressed ? 'No Sum' :
	 * 'Sum'; button.setText(buttonText); var view = this.getMyGrid().getView();
	 * view.getFeature('groupSummary').toggleSummaryRow(pressed);
	 * view.refresh(); },
	 */

	onSummaryDisable : function(button, pressed)
	{
		try
		{
			console.log('Sumary enable/disable button was pressed: ' + pressed);
		}
		catch (e)
		{

		}

		var view = this.getMyGrid().getView();
		if (pressed)
		{
			button.setText('Group');
			view.getFeature('groupSummary').enable();
		}
		else
		{
			button.setText('No Group');
			view.getFeature('groupSummary').disable();
		}
	},

	onPanelRendered : function()
	{
		try
		{
			console.log('The panel was rendered');
		}
		catch (e)
		{

		}

		this.getListBpkEmployeeStore().setProxy(
		{
			type : 'pagingmemory',
			// data : myTempData,
			data : myData,
			reader :
			{
				type : 'json',
				totalProperty : 'totalCount',
				root : 'ListBpkEmployee',
				successProperty : 'success'
			}
		});
		this.getListBpkEmployeeStore().load();
	},

	/*
	onTriggerKeyUp : function(t)
	{
		try
		{
			console.log('You typed something!');
		}
		catch (e)
		{

		}

		var thisRegEx = new RegExp(t.getValue(), "i");
		var grid = this.getMyGrid();
		records = [];
		Ext.each(myData.ListBpkEmployee, function(record)
		{
			for ( var i = 0; i < grid.columns.length; i++)
			{
				// Do not search the fields that
				// are passed in as omit columns
				if (grid.omitColumns)
				{
					if (grid.omitColumns.indexOf(grid.columns[i].dataIndex) === -1)
					{
						if (thisRegEx.test(record[grid.columns[i].dataIndex]))
						{
							if (!grid.filterHidden && grid.columns[i].isHidden())
							{
								continue;
							}
							else
							{
								records.push(record);
								break;
							}
							;
						}
						;
					}
					;
				}
				else
				{
					if (thisRegEx.test(record[grid.columns[i].dataIndex]))
					{
						if (!grid.filterHidden && grid.columns[i].isHidden())
						{
							continue;
						}
						else
						{
							records.push(record);
							break;
						}
						;
					}
					;
				}
				;
			}
		});
		myTempData.ListBpkEmployee = records;
		myTempData.totalCount = records.length;
		this.getListBpkEmployeeStore().load();
	},

	onTriggerClear : function()
	{
		try
		{
			console.log('Trigger got reset!');
		}
		catch (e)
		{

		}

		myTempData.ListBpkEmployee = myData.ListBpkEmployee;
		myTempData.totalCount = myData.totalCount;
		this.getListBpkEmployeeStore().load();
	},
	*/

	onResetRequest : function()
	{
		try
		{
			console.log('Reset Filter');
		}
		catch (e)
		{

		}

		Ext.getCmp("txtClinicDescription").setValue('');
		Ext.getCmp("txtSpecialty").setValue('');
		Ext.getCmp("txtEmployeeName").setValue('');

		// Ext.getCmp("chkDayInWeekAllDay").setValue(true);

		Ext.getCmp("radOptionWithSchedule").setValue(true);
	},

	onFindRequest : function(button)
	{
		// just a console log to show when Find by Ajax
		// request starts
		try
		{
			setCursorByID('btnFind', 'wait');
			 
			console.log('Find Doctor');
		}
		catch (e)
		{

		}

		Ext.getCmp("pagingtoolbar").moveFirst();
		
		var pClinicDescription = Ext.getCmp("txtClinicDescription").value;
		var pSpecialty = Ext.getCmp("txtSpecialty").value;
		var pEmployeeName = Ext.getCmp("txtEmployeeName").value;
		var pOptionWithSchedule = Ext.getCmp("radOptionWithSchedule").value;

		/**
		 * น่าจะไม่ได้ใช้ var pDayInWeekMon =
		 * Ext.getCmp("chkDayInWeekMon").value; var pDayInWeekTue =
		 * Ext.getCmp("chkDayInWeekTue").value; var pDayInWeekWed =
		 * Ext.getCmp("chkDayInWeekWed").value; var pDayInWeekThu =
		 * Ext.getCmp("chkDayInWeekThu").value; var pDayInWeekFri =
		 * Ext.getCmp("chkDayInWeekFri").value; var pDayInWeekSat =
		 * Ext.getCmp("chkDayInWeekSat").value; var pDayInWeekSun =
		 * Ext.getCmp("chkDayInWeekSun").value;
		 */

		Ext.Ajax.request(
		{
			url : 'ListDoctorServlet',
			method : 'POST',
			params :
			{
				// loginData : Ext.encode(form.getValues())
				// clinicDescription : 'อายุรกรรม',
				clinicDescription : pClinicDescription,
				specialty : pSpecialty,
				employeeName : pEmployeeName,
				optionWithSchedule : pOptionWithSchedule ? '1' : '0'
			/**
			 * น่าจะไม่ได้ใช้ , dayInWeekMon : pDayInWeekMon ? '1' : '0',
			 * dayInWeekTue : pDayInWeekTue ? '1' : '0', dayInWeekWed :
			 * pDayInWeekWed ? '1' : '0', dayInWeekThu : pDayInWeekThu ? '1' :
			 * '0', dayInWeekFri : pDayInWeekFri ? '1' : '0', dayInWeekSat :
			 * pDayInWeekSat ? '1' : '0', dayInWeekSun : pDayInWeekSun ? '1' :
			 * '0'
			 */
			},
			scope : this,
			// method to call when the request is
			// successful
			success : this.onFindSuccess,
			// method to call when the request is a
			// failure
			failure : this.onFindFailure
		});
	},

	onFindFailure : function(err)
	{
		// Alert the user about communication error
		Ext.MessageBox.alert('Error occured during Login', 'Please try again!');
	},

	onFindSuccess : function(response, opts)
	{
		// พักค่าไว้ ก่อนนำไป Update กลับให้ Filter
		var pClinicDescription = Ext.getCmp("txtClinicDescription").value;
		var pSpecialty = Ext.getCmp("txtSpecialty").value;
		var pEmployeeName = Ext.getCmp("txtEmployeeName").value;
		var pOptionWithSchedule = Ext.getCmp("radOptionWithSchedule").value;

		// Initialize the ieUserAgent object
		// alert(ieUserAgent.init());
		if (ieUserAgent.init() == 1)
		{
			localStorage.setItem("txtClinicDescription", pClinicDescription);
			localStorage.setItem("txtSpecialty", pSpecialty);
			localStorage.setItem("txtEmployeeName", pEmployeeName);
			localStorage.setItem("radOptionWithSchedule", pOptionWithSchedule);
			localStorage.setItem("radOptionWithoutSchedule", !pOptionWithSchedule);

			sessionStorage.setItem("txtClinicDescription", pClinicDescription);
			sessionStorage.setItem("txtSpecialty", pSpecialty);
			sessionStorage.setItem("txtEmployeeName", pEmployeeName);
			sessionStorage.setItem("radOptionWithSchedule", pOptionWithSchedule);
			sessionStorage.setItem("radOptionWithoutSchedule", !pOptionWithSchedule);

			// window.location.reload();
			window.location.href = "app/loadParameterToSession.jsp?isCompatibilityView=1&txtEmployeeName=" + pEmployeeName + "&txtSpecialty=" + pSpecialty
					+ "&txtClinicDescription=" + pClinicDescription + "&radOptionWithoutSchedule=" + pOptionWithSchedule + "&radOptionWithoutSchedule="
					+ (!pOptionWithSchedule);
		}

		/**
		 * น่าจะไม่ได้ใช้ var pDayInWeekMon =
		 * Ext.getCmp("chkDayInWeekMon").value; var pDayInWeekTue =
		 * Ext.getCmp("chkDayInWeekTue").value; var pDayInWeekWed =
		 * Ext.getCmp("chkDayInWeekWed").value; var pDayInWeekThu =
		 * Ext.getCmp("chkDayInWeekThu").value; var pDayInWeekFri =
		 * Ext.getCmp("chkDayInWeekFri").value; var pDayInWeekSat =
		 * Ext.getCmp("chkDayInWeekSat").value; var pDayInWeekSun =
		 * Ext.getCmp("chkDayInWeekSun").value;
		 */

		// Received response from the server
		// console.log('response.responseText = '+response.responseText);
		try
		{
			myData = Ext.decode(response.responseText);
			// myTempData = Ext.decode(response.responseText);
		}
		catch (e)
		{
			myData = '{}';
			// myTempData = '{}';
		}

		Ext.create('Ext.container.Viewport',
		{
			items : [
			{
				xtype : 'GridBpkEmployee'
			} ]
		});

		Ext.getCmp("txtClinicDescription").setValue(pClinicDescription);
		Ext.getCmp("txtSpecialty").setValue(pSpecialty);
		Ext.getCmp("txtEmployeeName").setValue(pEmployeeName);
		if (pOptionWithSchedule)
			Ext.getCmp("radOptionWithSchedule").setValue(true);
		else
			Ext.getCmp("radOptionWithoutSchedule").setValue(true);

		/**
		 * น่าจะไม่ได้ใช้ Ext.getCmp("chkDayInWeekMon").setValue(pDayInWeekMon);
		 * Ext.getCmp("chkDayInWeekTue").setValue(pDayInWeekTue);
		 * Ext.getCmp("chkDayInWeekWed").setValue(pDayInWeekWed);
		 * Ext.getCmp("chkDayInWeekThu").setValue(pDayInWeekThu);
		 * Ext.getCmp("chkDayInWeekFri").setValue(pDayInWeekFri);
		 * Ext.getCmp("chkDayInWeekSat").setValue(pDayInWeekSat);
		 * Ext.getCmp("chkDayInWeekSun").setValue(pDayInWeekSun);
		 */

	}
});