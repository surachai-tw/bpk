Ext.define('MyApp.view.CountryList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.countryList',
    title : 'List of Countries Grouped by Continent and Region',
    store : 'Countries',
    loadMask: true,
    height: 400,
    width: 600,
    margin: '10 0 0 10',
    dockedItems: [{
        xtype: 'toolbar',
        dock: 'bottom',
        items: [
                {
                    id: 'hideShow',
                    xtype : 'button',
                    text: 'Hide Summary',
                    pressed: true,
                    enableToggle: true
                },
                { 
                    xtype: 'tbseparator'
                },
                {
                    id: 'grouping',
                    xtype : 'button',
                    text: 'Disable Grouping',
                    pressed: true,
                    enableToggle: true
                },
                { 
                    xtype: 'tbseparator'
                },
                {
                    id: 'collapse',
                    xtype : 'button',
                    text: 'Collapse All',
                    pressed: true,
                    enableToggle: true
                },
        ]
    }],
    
    features: [
        Ext.create('Ext.grid.feature.GroupingSummary', {
            id: 'groupSummary',
            groupHeaderTpl: '{name} ({rows.length} {[values.rows.length > 1 ? "Countries" : "Country"]})'
        })        
    ],
 
    initComponent: function() {
            
        this.columns = [
            {header: 'Country Code', dataIndex: 'code',  flex: 1},
            {header: 'Name', dataIndex: 'name', flex: 2,
                summaryType: function(records){
                    var myGroupName = records[0].get('myGroup');
                    return myGroupName;
                }
            },
            {header: 'Life Expectancy', dataIndex: 'lifeExpectancy', flex: 1,
                summaryType: 'average',   
                summaryRenderer: Ext.util.Format.numberRenderer('0.00')
            },
            {header: 'GNP', dataIndex: 'gnp', flex: 1,
                summaryType: 'average',
                summaryRenderer: Ext.util.Format.numberRenderer('0.00')
            }
        ];
    
        this.callParent(arguments);
    }
 
   
});