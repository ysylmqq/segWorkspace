package com.gboss.service;

import com.gboss.comm.SystemException;
import com.gboss.pojo.LinkMan;


public interface LinkmanService extends BaseService {
	
	public void saveLinkMan(LinkMan man) throws SystemException;
	
	public LinkMan getLinkMan(long vechiel_id) throws SystemException;
	
}
