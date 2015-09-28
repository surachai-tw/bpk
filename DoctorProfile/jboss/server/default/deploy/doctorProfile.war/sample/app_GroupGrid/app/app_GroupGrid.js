Ext.Loader.setConfig({ 
    enabled: true
    });
 
Ext.require([
             'Ext.data.*',
             'Ext.grid.*',
         ]);
 
Ext.application({
    
    name: 'MyApp',
    appFolder: 'app',
    controllers: [
                  'Countries'
              ],
 
    launch: function() {
        Ext.create('Ext.container.Viewport', {
            items: [
                {
                    xtype: 'countryList',
                }
            ]
        });
    }
});