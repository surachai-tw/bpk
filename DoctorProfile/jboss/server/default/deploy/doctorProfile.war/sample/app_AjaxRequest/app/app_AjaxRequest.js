Ext.Loader.setConfig({ 
    enabled: true
    });
 
Ext.application({
    name: 'MyAjaxApp',
 
    appFolder: 'app',
    
    controllers: [
                  'AjaxExample'
              ],
 
    launch: function() {
        Ext.create('Ext.container.Viewport', {
            items: [
                {
                    xtype: 'ajaxRequestForm',
                }
            ]
        });
    }
});