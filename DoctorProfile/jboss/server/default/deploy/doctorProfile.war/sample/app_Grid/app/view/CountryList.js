Ext.define('CN.view.CountryList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.countrylist',
    title : 'List of Countries',
    store : 'Countries',
    loadMask: true,
    height: 400,
    width: 1000,
    omitColumns: ['lifeExpectancy','gnp'],
    dockedItems: [{
        xtype: 'toolbar',
        dock: 'bottom',
        displayInfo: true,
        items: [{
                    xtype : 'trigger',
                    itemId : 'gridTrigger',
                    fieldLabel: 'Filter Grid Data',
                    triggerCls : 'x-form-clear-trigger',
                    emptyText : 'Start typing to filter grid',
                    size : 30,
                    minChars : 1,
                    enableKeyEvents : true,
                    onTriggerClick : function(){
                        this.reset();
                        this.fireEvent('triggerClear');
                    }
                }
        ]
    }],
    initComponent: function() {
        
        this.columns = [
            {header: 'Country Code', dataIndex: 'code',  flex: 1},
            {header: 'Name', dataIndex: 'name', flex: 1},
            {header: 'Continent', dataIndex: 'continent', flex: 1},
            {header: 'Region', dataIndex: 'region', flex: 1},
            {header: 'Life Expectancy', dataIndex: 'lifeExpectancy', flex: 1},
            {header: 'GNP', dataIndex: 'gnp', flex: 1}
        ];
 
        this.callParent(arguments);
    }
  
});