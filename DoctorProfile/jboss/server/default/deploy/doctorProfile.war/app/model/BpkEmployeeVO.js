Ext.define('Employee.model.BpkEmployeeVO', {
    extend: 'Ext.data.Model',
    fields: [
             'bpkClinicId',
             'clinicDescription',
             'licenseNo',
             'specialty', 
             'educational',
             'qualification', 
             'institute', 
             'board', 
             'others', 
             'employeeId',
             'employeeName', 
             'dayId', 
             'dayName', 
             'startTime', 
             'endTime', 
             {
                 name: 'ClinicGroup',
                 mapping: 'continent',
                 convert: function(v, record) {
                         return v + record.data.employeeName + '<br/>' + 
                         (record.data.licenseNo!='' ? '['+record.data.licenseNo+']' : '') + 
                         (record.data.specialty!='' ? ', '+record.data.specialty : '') + 
                         (record.data.institute!='' ? ', '+record.data.institute : '');
                 }
             }]
});