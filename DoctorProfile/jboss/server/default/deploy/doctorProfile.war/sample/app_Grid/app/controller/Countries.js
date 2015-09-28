Ext.define('CN.controller.Countries', {
            extend : 'Ext.app.Controller',
 
            //define the stores
            stores : ['Countries'],
            //define the models
            models : ['Country'],
            //define the views
            views : ['CountryList'],
            refs: [{
                ref: 'myGrid', 
                selector: 'grid'
            }], 
            
            init : function() {
                this.control({
                    
                    'viewport > panel' : {
                        render : this.onPanelRendered
                    },
                    'countrylist #gridTrigger' : {
                                keyup : this.onTriggerKeyUp,
                                triggerClear : this.onTriggerClear
                            }
                    
                });
            },
 
            onPanelRendered : function() {
                //just a console log to show when the panel si rendered
                console.log('The panel was rendered');
            },
            
            onTriggerKeyUp : function(t) {
                    console.log('You typed something!');
                    
                        var thisRegEx = new RegExp(t.getValue(), "i");
                        var store = this.getCountriesStore();
                        var grid = this.getMyGrid();
                        store.filterBy(function(rec) {
                            for (var i = 0; i < grid.columns.length; i++) {
                                // Do not search the fields that are passed in as omit columns
                                if (grid.omitColumns) {
                                    if (grid.omitColumns.indexOf(grid.columns[i].dataIndex) === -1) {
                                        if (thisRegEx.test(rec.get(grid.columns[i].dataIndex))) {
                                            if (!grid.filterHidden && grid.columns[i].isHidden()) {
                                                continue;
                                            } else {
                                                return true;
                                            };
                                        };
                                    };
                                } else {
                                    if (thisRegEx.test(rec.get(grid.columns[i].dataIndex))) {
                                        if (!grid.filterHidden && grid.columns[i].isHidden()) {
                                            continue;
                                        } else {
                                            return true;
                                        };
                                    };
                                };
                            }
                            return false;
                        });
                },
                
                onTriggerClear : function() {
                    console.log('Trigger got reset!');
                    var store = this.getCountriesStore();
                    store.clearFilter();
                }
            
    });