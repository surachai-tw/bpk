Ext.define('MyApp.model.Country', {
    extend: 'Ext.data.Model',
    fields: [
             'code',
             'name',
             'continent',
             'region',
             'lifeExpectancy',
             'gnp',
             {
                 name: 'myGroup',
                 mapping: 'continent',
                 convert: function(v, record) {
                         return v + ': ' + record.data.region;
                 }
            }]
});