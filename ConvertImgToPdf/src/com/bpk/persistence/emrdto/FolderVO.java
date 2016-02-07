package com.bpk.persistence.emrdto;

import java.util.List;

import com.bpk.utility.XPersistent;

public class FolderVO extends XPersistent
{
	public List listBpkDocumentScanVO;

	public List getListBpkDocumentScanVO()
	{
		return listBpkDocumentScanVO;
	}

	public void setListBpkDocumentScanVO(List listBpkDocumentScanVO)
	{
		this.listBpkDocumentScanVO = listBpkDocumentScanVO;
	}

}
