function BpkAppointmentVO()
{
	this.appointmentId = "";
	this.patientId = "";
	this.baseSpId = "";
	this.doctorEid = "";
	this.doctorAssignerEid = "";
	this.baseDepartmentId = "";
	this.basicAdvice = "";
	this.note = "";
	this.appointDate = "";
	this.appointTime = "";
	this.modifyEid = "";
	this.modifyDate = "";
	this.modifyTime = "";
	this.fixAppointmentStatusId = "";
	this.fixAppointmentTypeId = "";
	this.fixAppointmentMethodId = "";

	this.reset = function ()
	{
		this.appointmentId = "";
		this.patientId = "";
		this.baseSpId = "";
		this.doctorEid = "";
		this.doctorAssignerEid = "";
		this.baseDepartmentId = "";
		this.basicAdvice = "";
		this.note = "";
		this.appointDate = "";
		this.appointTime = "";
		this.modifyEid = "";
		this.modifyDate = "";
		this.modifyTime = "";
		this.fixAppointmentStatusId = "";
		this.fixAppointmentTypeId = "";
		this.fixAppointmentMethodId = "";
	}
}