Ext.define('MyApp.store.Countries', {
    extend: 'Ext.data.Store',
    model: 'MyApp.model.Country',
    autoLoad: true,
    groupField: 'myGroup',
    
    proxy: {
        type: 'ajax',
        // url: 'http://demo.mysamplecode.com/ExtJs/CountryServlet',
        url: 'http://localhost:8080/doctorProfile/sample/app_GroupGrid/app/model/Countries.json',
        reader: {
            type: 'json',
            totalProperty: 'totalCount',
            root: 'countries',
            successProperty: 'success'
        },
     }
});