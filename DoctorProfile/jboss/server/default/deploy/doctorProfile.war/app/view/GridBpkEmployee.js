Ext
		.define(
				'Employee.view.GridBpkEmployee',
				{
					id : 'aGridBpkEmployee', 
					extend : 'Ext.grid.Panel',
					alias : 'widget.GridBpkEmployee',
					title : 'BPK9 - ตารางออกตรวจแพทย์',
					store : 'ListBpkEmployee',
					loadMask : true,
					// autoheight: true,
					height : 512,
					width : 1024,
					omitColumns : [ 'bpkClinicId', 'employeeId' ],
					dockedItems : [
					{
						xtype : 'tbspacer',
						height : 10
					}, // add a 10px space
					{
						xtype : 'toolbar',
						height : 36,
						dock : 'top',
						baseCls : 'x-btn-over',
						items : [
						{
							xtype : 'tbspacer',
							width : 2
						},
						{
							xtype : 'textfield',
							name : 'employeeName',
							id : 'txtEmployeeName',
							fieldLabel : 'ชื่อแพทย์:',
							labelWidth : 48,
							enableKeyEvents : true,
							listeners :
							{
								keyup : function(form, e)
								{
									if (e.getKey() == Ext.EventObject.ENTER)
									{
										setCursorByID('txtEmployeeName', 'wait');
										Ext.get('btnFind').el.dom.click();
										// Ext.get('txtEmployeeName').fieldEl.select();
										// Ext.getCmp('txtEmployeeName').focus(true,
										// 200);
									}
									else if (e.getKey() == Ext.EventObject.ESC)
									{
										Ext.getCmp("txtEmployeeName").setValue('');
									}
								}
							}
						},
						{
							xtype : 'tbspacer',
							width : 5
						},
						{
							xtype : 'textfield',
							name : 'clinicDescription',
							id : 'txtClinicDescription',
							fieldLabel : 'คลินิก:',
							labelWidth : 32,
							enableKeyEvents : true,
							listeners :
							{
								keyup : function(form, e)
								{
									if (e.getKey() == Ext.EventObject.ENTER)
									{
										setCursorByID('txtClinicDescription', 'wait');
										Ext.get('btnFind').el.dom.click();
										// Ext.get('txtEmployeeName').fieldEl.select();
										// Ext.getCmp('txtEmployeeName').focus(true,
										// 200);
									}
									else if (e.getKey() == Ext.EventObject.ESC)
									{
										Ext.getCmp("txtClinicDescription").setValue('');
									}
								}
							}
						},
						{
							xtype : 'tbspacer',
							width : 5
						},
						{
							xtype : 'textfield',
							name : 'specialty',
							id : 'txtSpecialty',
							fieldLabel : 'ความชำนาญ:',
							labelWidth : 64,
							enableKeyEvents : true,
							listeners :
							{
								keyup : function(form, e)
								{
									if (e.getKey() == Ext.EventObject.ENTER)
									{
										setCursorByID('txtSpecialty', 'wait');
										Ext.get('btnFind').el.dom.click();
										// Ext.get('txtEmployeeName').fieldEl.select();
										// Ext.getCmp('txtEmployeeName').focus(true,
										// 200);
									}
									else if (e.getKey() == Ext.EventObject.ESC)
									{
										Ext.getCmp("txtSpecialty").setValue('');
									}
								}
							}
						},
						{
							xtype : 'tbspacer',
							width : 5
						}, // add a 12px space
						{
							xtype : 'radiogroup',
							columns : 2,
							width : 210,
							items : [
							{
								xtype : 'radiofield',
								boxLabel : 'เฉพาะที่มีตาราง',
								name : 'framework',
								checked : true,
								inputValue : 'WithSchedule',
								id : 'radOptionWithSchedule'
							},
							{
								xtype : 'radiofield',
								boxLabel : 'ทั้งหมด',
								name : 'framework',
								inputValue : 'WithoutSchedule',
								id : 'radOptionWithoutSchedule'
							} ]
						},
						{
							xtype : 'tbspacer',
							width : 5
						},
						{
							id : 'btnFind',
							xtype : 'button',
							text : ' Find ',
							action : 'find',
							width : 60
						},
						{
							id : 'btnClear',
							xtype : 'button',
							text : ' Clear ',
							action : 'reset',
							width : 60
						} ]
					},
					/**
					 * คิดว่าไม่น่าจะได้ใช้ { xtype: 'checkboxgroup', //
					 * fieldLabel: 'Day in week', columns: 6, allowBlank: true,
					 * itemId: 'days', items: [ { xtype: 'checkbox', boxLabel:
					 * 'จันทร์', name: 'DayInWeek', id: 'chkDayInWeekMon',
					 * inputValue: 'Monday', checked: true }, { xtype:
					 * 'checkbox', boxLabel: 'อังคาร', name: 'DayInWeek', id:
					 * 'chkDayInWeekTue', inputValue: 'Tuesday', checked: true }, {
					 * xtype: 'checkbox', boxLabel: 'พุธ', name: 'DayInWeek',
					 * id: 'chkDayInWeekWed', inputValue: 'Wednesday', checked:
					 * true }, { xtype: 'checkbox', boxLabel: 'พฤหัสบดี', name:
					 * 'DayInWeek', id: 'chkDayInWeekThu', inputValue:
					 * 'Thursday', checked: true }, { xtype: 'checkbox',
					 * boxLabel: 'ศุกร์', name: 'DayInWeek', id:
					 * 'chkDayInWeekFri', inputValue: 'Friday', checked: true }, {
					 * xtype: 'checkbox', boxLabel: 'เลือก/ไม่เลือก ทั้งหมด',
					 * name: 'AllDay', id: 'chkDayInWeekAllDay', inputValue:
					 * 'AllDay', checked: true, listeners: { change:
					 * function(field, newValue, oldValue, eOpts) { //
					 * console.log('change:field.value=' + field.value);
					 * Ext.getCmp("chkDayInWeekMon").setValue(field.value);
					 * Ext.getCmp("chkDayInWeekTue").setValue(field.value);
					 * Ext.getCmp("chkDayInWeekWed").setValue(field.value);
					 * Ext.getCmp("chkDayInWeekThu").setValue(field.value);
					 * Ext.getCmp("chkDayInWeekFri").setValue(field.value);
					 * Ext.getCmp("chkDayInWeekSat").setValue(field.value);
					 * Ext.getCmp("chkDayInWeekSun").setValue(field.value); } } }, {
					 * xtype: 'checkbox', boxLabel: 'เสาร์', name: 'DayInWeek',
					 * id: 'chkDayInWeekSat', inputValue: 'Saturday', checked:
					 * true }, { xtype: 'checkbox', boxLabel: 'อาทิตย์', name:
					 * 'DayInWeek', id: 'chkDayInWeekSun', inputValue: 'Sunday',
					 * checked: true } ] }, { xtype: 'tbspacer', height: 10 }, //
					 * add a 10px space
					 */
					{
						xtype : 'pagingtoolbar',
						id : 'pagingtoolbar',
						store : 'ListBpkEmployee',
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
						items : [/*
									 * { id: 'grouping', xtype : 'button', text:
									 * 'No Group', pressed: true, enableToggle:
									 * true, width: 60 }, { id: 'hideShow',
									 * xtype : 'button', text: 'No Sum',
									 * pressed: true, enableToggle: true, width:
									 * 60 }
									 */
						]
					} ],

					features : [ Ext.create('Ext.grid.feature.GroupingSummary',
					{
						id : 'groupSummary',
						groupHeaderTpl : '{name}'
					// groupHeaderTpl: '{name}
					// ({rows.length}{[values.rows.length > 1 ? "" :
					// ""]})'
					}) ],

					// Ext.util.Format.number(appliedAmount, "0,000.00");
					initComponent : function()
					{
						this.columns = [
						{
							header : 'employeeId',
							dataIndex : 'employeeId',
							flex : 0,
							hidden : true
						},
						/*
						 * {header: 'แพทย์', dataIndex: 'employeeName', flex:120 ,
						 * summaryType: function(records) { var groupName =
						 * records[0].get('ClinicGroup'); return groupName; } },
						 */
						{
							header : 'bpkClinicId',
							dataIndex : 'bpkClinicId',
							flex : 0,
							hidden : true
						},
						{
							header : 'คลินิก',
							dataIndex : 'clinicDescription',
							flex : 120
						},
						{
							header : 'dayId',
							dataIndex : 'dayId',
							flex : 0,
							hidden : true
						},
						{
							header : 'วันในสัปดาห์',
							dataIndex : 'dayName',
							flex : 120
						},
						{
							header : 'เริ่มเวลา',
							dataIndex : 'startTime',
							flex : 40
						},
						{
							header : 'ถึงเวลา',
							dataIndex : 'endTime',
							flex : 40
						}
						// {header: 'ความชำนาญ', dataIndex: 'specialty',
						// flex:120},
						// {header: 'License No', dataIndex: 'licenseNo',
						// flex:60},
						// {header: 'educational', dataIndex: 'educational',
						// flex:0, hidden:
						// true},
						// {header: 'สถาบัน', dataIndex: 'institute', flex:120}
						];

						this.callParent(arguments);
					},
					listeners :
					{
						itemdblclick : function(dv, record, item, index, e)
						{
							// alert(record.get('hn'));
							// try
							// {
								var win;
								if (!win)
								{
									win = Ext.create('widget.window',
													{
														title : 'Layout Window with title <em>after</em> tools',
														header :
														{
															titlePosition : 2,
															titleAlign : 'center'
														},
														closable : true,
														closeAction : 'hide',
														maximizable : true,
														// animateTarget : button,
														animateTarget: aGridBpkEmployee, 
														width : 600,
														minWidth : 350,
														height : 350,
														tools : [
														{
															type : 'pin'
														} ],
														layout :
														{
															type : 'border',
															padding : 5
														},
														items : [
																{
																	region : 'west',
																	title : 'Navigation',
																	width : 200,
																	split : true,
																	collapsible : true,
																	floatable : false
																},
																{
																	region : 'center',
																	xtype : 'tabpanel',
																	items : [
																			{
																				rtl : false,
																				title : 'Bogus Tab',
																				html : '<p>Window configured with:</p><pre style="margin-left:20px"><code>header: {\n    titlePosition: 2,\n    titleAlign: "center"\n},\nmaximizable: true,\ntools: [{type: "pin"}],\nclosable: true</code></pre>'
																			},
																			{
																				title : 'Another Tab',
																				html : 'Hello world 2'
																			},
																			{
																				title : 'Closable Tab',
																				html : 'Hello world 3',
																				closable : true
																			} ]
																} ]
													});
								}
							// }
							// catch (e)
							// {
							// 	alert(e);
							// }
						}
					}

				});