package com.bpk.app.ssb;
/** Main Class ����Ѻ��� Axis Generate Class ����Ѻ�� WebService �͡����� */

public interface SsbInterface 
{
	public String GetConnectionString();
	public String ExecuteDataTable(String commandText, String tablename);
	public String ExecuteNonQuery(String commandText);
};