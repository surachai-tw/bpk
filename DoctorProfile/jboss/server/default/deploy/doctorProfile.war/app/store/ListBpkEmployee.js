var myData;
// var myTempData;

Ext.define('Employee.store.ListBpkEmployee', {
    extend: 'Ext.data.Store',
    model: 'Employee.model.BpkEmployeeVO',
    autoLoad: true,
    groupField: 'ClinicGroup',
    
    proxy: {
        type: 'ajax',
        url: 'ListDoctorServlet?optionWithSchedule=1',
        reader: {
            type: 'json',
            totalProperty: 'totalCount',
            root: 'ListBpkEmployee',
            successProperty: 'success'
        }
     }, 

     pageSize: 10,
     listeners: {
         beforeload: function(store, operation, options){
        	 try
        	 {
        		 console.log("ListBpkEmployee.js, My Store data is loading!");
        	 }
        	 catch(e)
        	 {
        		 
        	 }
         }
     }     

});