package com.iMed.iMedApp.iMedInterfaceFXApp.iMedInterfaceFXVO;
import java.sql.Timestamp;

public class WSLogVO {
    private String wsLogId;
    private String wsTypeId;
    private String methodName;
    private String parameterSnd;
    private Timestamp callTime;
    private String result;

	public String getWsLogId(){
		return this.wsLogId;
	}
	public String getWsTypeId(){
		return this.wsTypeId;
	}
	public String getMethodName(){
		return this.methodName;
	}
	public String getParameterSnd(){
		return this.parameterSnd;
	}
	public Timestamp getCallTime(){
		return this.callTime;
	}
	public String getResult(){
		return this.result;
	}
	public void setWsLogId(String wsLogId){
		this.wsLogId = wsLogId;
	}
	public void setWsTypeId(String wsTypeId){
		this.wsTypeId = wsTypeId;
	}
	public void setMethodName(String methodName){
		this.methodName = methodName;
	}
	public void setParameterSnd(String parameterSnd){
		this.parameterSnd = parameterSnd;
	}
	public void setCallTime(Timestamp callTime){
		this.callTime = callTime;
	}
	public void setResult(String result){
		this.result = result;
	}

};