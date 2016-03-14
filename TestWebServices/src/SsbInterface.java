package com.bpk.app.ssb;
/** Main Class สำหรับให้ Axis Generate Class สำหรับทำ WebService ออกมาให้ */

public interface SsbInterface 
{
	public String GetConnectionString();
	public String ExecuteDataTable(String commandText, String tablename);
	public String ExecuteNonQuery(String commandText);
};