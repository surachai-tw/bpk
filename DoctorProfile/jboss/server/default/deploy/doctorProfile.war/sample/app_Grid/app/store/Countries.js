Ext.define('CN.store.Countries', {
    extend: 'Ext.data.Store',
    model: 'CN.model.Country',
    autoLoad: true,
    
    proxy: {
        type: 'ajax',
        // url: 'http://demo.mysamplecode.com/ExtJs/CountryServlet',
        url: 'http://localhost:8080/doctorProfile/sample/app_Grid/app/model/Countries.json',
        reader: {
            type: 'json',
            totalProperty: 'totalCount',
            root: 'countries',
            successProperty: 'success'
        },
     }
});