Ext.define('MyApp.controller.Countries', {
            extend : 'Ext.app.Controller',
 
            //define the stores
            stores : ['Countries'],
            //define the models
            models : ['Country'],
            //define the views
            views : ['CountryList'],
            //define refs
            refs: [{
                ref: 'myCountryList', 
                selector: 'countryList'
            }],
            
            init : function() {
                this.control({
                    
                    'viewport > panel' : {
                        render : this.onPanelRendered
                    },
                    'countryList button[id=hideShow]' : {
                        toggle : this.onSummaryToggle   
                    },
                    'countryList button[id=grouping]' : {
                        toggle : this.onSummaryDisable   
                    },
                    'countryList button[id=collapse]' : {
                        toggle : this.onGroupCollapse   
                    }
                    
                });
            },
 
            onPanelRendered : function() {
                //just a console log to show when the panel si rendered
                console.log('The panel was rendered');
            },
            
            onSummaryToggle : function(button, pressed) {
                console.log('Sumary toggle button was pressed: ' + pressed);
                var buttonText = pressed ? 'Hide Summary' : 'Show Summary';
                button.setText(buttonText);
                var view = this.getMyCountryList().getView();
                view.getFeature('groupSummary').toggleSummaryRow(pressed);
                view.refresh();
            },
            
            onSummaryDisable : function(button, pressed) {
                console.log('Sumary enable/disable button was pressed: ' + pressed);
                var view = this.getMyCountryList().getView();
                if(pressed){
                    button.setText('Disable Grouping');
                    view.getFeature('groupSummary').enable();
                }
                else {
                    button.setText('Enable Grouping');
                    view.getFeature('groupSummary').disable();
                }
            },
            
            onGroupCollapse : function(button, pressed) {
                console.log('Sumary enable/disable button was pressed: ' + pressed);
                var view = this.getMyCountryList().getView();
                var groupFeature = view.getFeature('groupSummary');
                if(pressed){
                    button.setText('Collapse All');
                    view.getEl().query('.x-grid-group-hd').forEach(function (group) {
                        var groupBody = Ext.fly(group.nextSibling, '_grouping');
                        groupFeature.expand(groupBody);
                    });
                }
                else {
                    button.setText('Expand All');
                    view.getEl().query('.x-grid-group-hd').forEach(function (group) {
                        var groupBody = Ext.fly(group.nextSibling, '_grouping');
                        groupFeature.collapse(groupBody);
                    });
                }
            }
            
    });