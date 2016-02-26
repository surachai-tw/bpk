package com.iMed.iMedApp.iMedInterfaceFXApp.iMedInterfaceFXVO;
/** เก็บข้อมูลสำหรับใช้ Login 
 @author Chung , 27 October 2010
*/
public class FXLoginVO {
    private String username;
    private String password;

	public String getUsername(){
		return this.username;
	}
	public String getPassword(){
		return this.password;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public void setPassword(String password){
		this.password = password;
	}

};