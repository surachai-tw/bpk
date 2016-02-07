package com.bpk.persistence.emrdto;

import com.bpk.utility.Utility;
import com.bpk.utility.XPersistent;

public class BpkDocumentScanCommentsVO extends XPersistent
{
	private String bpkDocumentScanId;
	private String commentsMessage;
	private String commentsEid;
	private String commentsDate;
	private String commentsTime;

	public String getBpkDocumentScanId()
	{
		return Utility.getStringVO(bpkDocumentScanId);
	}

	public void setBpkDocumentScanId(String bpkDocumentScanId)
	{
		this.bpkDocumentScanId = bpkDocumentScanId;
	}

	public String getCommentsMessage()
	{
		return Utility.getStringVO(commentsMessage);
	}

	public void setCommentsMessage(String commentsMessage)
	{
		this.commentsMessage = commentsMessage;
	}

	public String getCommentsEid()
	{
		return Utility.getStringVO(commentsEid);
	}

	public void setCommentsEid(String commentsEid)
	{
		this.commentsEid = commentsEid;
	}

	public String getCommentsDate()
	{
		return Utility.getStringVO(commentsDate);
	}

	public void setCommentsDate(String commentsDate)
	{
		this.commentsDate = commentsDate;
	}

	public String getCommentsTime()
	{
		return Utility.getStringVO(commentsTime);
	}

	public void setCommentsTime(String commentsTime)
	{
		this.commentsTime = commentsTime;
	}
}
