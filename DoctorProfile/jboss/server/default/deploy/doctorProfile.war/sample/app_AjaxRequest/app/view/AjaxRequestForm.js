Ext.define('MyAjaxApp.view.AjaxRequestForm', {
    extend: 'Ext.form.Panel',
    alias : 'widget.ajaxRequestForm',
    width: 500,
    frame: true,
    title: 'ExtJs Ajax Request Example',
    bodyPadding: '10 10 0',
 
    defaults: {
        anchor: '100%',
        allowBlank: false,
        msgTarget: 'side',
        labelWidth: 75
    },
 
    items: [{
        xtype: 'textfield',
        name: 'userId',
        fieldLabel: 'User Id'
    },{
        xtype: 'textfield',
        name: 'password',
        fieldLabel: 'Password',
        inputType: 'password',
    }],
 
    buttons: [{
        text: 'Submit',
        action: 'login'
    },
    {
        text: 'Reset Form',
        handler: function() {
            this.up('form').getForm().reset();
        }
    }]
});
