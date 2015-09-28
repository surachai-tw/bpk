Ext.Loader.setConfig({ 
    enabled: true
    });
 
Ext.application({
    name: 'CN',
 
    appFolder: 'app',
    
    controllers: [
                  'Countries'
              ],
 
    launch: function() {
        Ext.create('Ext.container.Viewport', {
            items: [
                {
                    xtype: 'countrylist',
                }
            ]
        });
    }
});