Ext.define('DA.controller.BpkAllergyTempManage',
{
	extend : 'Ext.app.Controller',

	// define the stores
	stores : [ 'ListBpkAllergyTemp' ],
	// define the models
	models : [ 'BpkAllergyTempVO' ],
	// define the views
	views : [ 'GridBpkAllergyTemp' ],
	refs : [
	{
		ref : 'myGrid',
		selector : 'grid'
	} ],

	init : function()
	{
		this.control(
		{

			'viewport > panel' :
			{
				render : this.onPanelRendered
			},
			'GridBpkAllergyTemp #gridTrigger' :
			{
				keyup : this.onTriggerKeyUp,
				triggerClear : this.onTriggerClear
			}
		});
	},

	/*
	 * onPanelRendered : function() { //just a console log to show when the
	 * panel si rendered console.log('The panel was rendered'); },
	 */
	onPanelRendered : function()
	{
		try
		{
			console.log('The panel was rendered');
		}
		catch (e)
		{

		}
		this.getListBpkAllergyTempStore().setProxy(
		{
			type : 'pagingmemory',
			data : myTempData,
			reader :
			{
				type : 'json',
				totalProperty : 'totalCount',
				root : 'ListBpkAllergyTemp',
				successProperty : 'success'
			}
		});
		this.getListBpkAllergyTempStore().load();
	},

	/*
	 * onTriggerKeyUp : function(t) { console.log('You typed something!');
	 * 
	 * var thisRegEx = new RegExp(t.getValue(), "i"); var store =
	 * this.getListBpkAllergyTempStore(); var grid = this.getMyGrid();
	 * store.filterBy(function(rec) { for (var i = 0; i < grid.columns.length;
	 * i++) { // Do not search the fields that are passed in as omit columns if
	 * (grid.omitColumns) { if
	 * (grid.omitColumns.indexOf(grid.columns[i].dataIndex) === -1) { if
	 * (thisRegEx.test(rec.get(grid.columns[i].dataIndex))) { if
	 * (!grid.filterHidden && grid.columns[i].isHidden()) { continue; } else {
	 * return true; }; }; }; } else { if
	 * (thisRegEx.test(rec.get(grid.columns[i].dataIndex))) { if
	 * (!grid.filterHidden && grid.columns[i].isHidden()) { continue; } else {
	 * return true; }; }; }; } return false; }); },
	 */
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
		Ext.each(myData.ListBpkAllergyTemp, function(record)
		{
			for ( var i = 0; i < grid.columns.length; i++)
			{
				// Do not search the fields that are passed in as omit columns
				if (grid.omitColumns)
				{
					try
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
					catch (e)
					{
						if (thisRegEx.test(record[grid.columns[i].dataIndex]))
						{
							records.push(record);
							break;
						}
					}
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
		myTempData.ListBpkAllergyTemp = records;
		myTempData.totalCount = records.length;
		this.getListBpkAllergyTempStore().load();
	},

	/*
	 * onTriggerClear : function() { console.log('Trigger got reset!'); var
	 * store = this.getListBpkAllergyTempStore(); store.clearFilter(); }
	 */
	onTriggerClear : function()
	{
		try
		{
			console.log('Trigger got reset!');
		}
		catch (e)
		{

		}
		myTempData.ListBpkAllergyTemp = myData.ListBpkAllergyTemp;
		myTempData.totalCount = myData.totalCount;
		this.getCountriesStore().load();
	}
});