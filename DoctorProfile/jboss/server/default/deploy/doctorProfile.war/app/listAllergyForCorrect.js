Ext.Loader.setPath('Ext.ux', 'lib/extjs/ux');

Ext.Loader.setConfig(
{
	enabled : true
});

Ext.require([ 'Ext.data.*', 'Ext.grid.*', 'Ext.ux.data.PagingMemoryProxy' ]);

Ext.application(
{
	name : 'DA',

	appFolder : 'app',

	controllers : [ 'BpkAllergyTempManage' ],

	launch : function()
	{
		try
		{
			console.log("Launching the application!");
		}
		catch (e)
		{

		}
		Ext.Ajax.request(
		{
			loadMask : true,
			url : 'ListAllergyTempForCorrectServlet',
			scope : this,
			success : function(response, callOptions)
			{
				myData = Ext.decode(response.responseText);
				myTempData = Ext.decode(response.responseText);
				Ext.create('Ext.container.Viewport',
				{
					items : [
					{
						xtype : 'GridBpkAllergyTemp'
					} ]
				});
			},
			failure : function(response, callOptions)
			{
			// Use the response
			}
		});
	}

});