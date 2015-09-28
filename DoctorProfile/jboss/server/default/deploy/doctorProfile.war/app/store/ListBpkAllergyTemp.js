var myData;
var myTempData;

Ext.define('DA.store.ListBpkAllergyTemp',
{
	extend : 'Ext.data.Store',
	model : 'DA.model.BpkAllergyTempVO',
	autoLoad : true,

	proxy :
	{
		type : 'ajax',
		url : 'ListAllergyTempForCorrectServlet',
		// url:
		// 'http://localhost:8080/doctorProfile/app/model/ListBpkAllergyTemp.json',
		reader :
		{
			type : 'json',
			totalProperty : 'totalCount',
			root : 'ListBpkAllergyTemp',
			successProperty : 'success'
		}
	},
	pageSize : 20,
	listeners :
	{
		beforeload : function(store, operation, options)
		{
			try
			{
				console.log("My Store data is loading!");
			}
			catch (e)
			{

			}
		}
	}

});