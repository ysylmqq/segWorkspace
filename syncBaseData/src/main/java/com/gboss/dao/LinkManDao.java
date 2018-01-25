package com.gboss.dao;

import com.gboss.comm.SystemException;
import com.gboss.pojo.LinkMan;

public interface LinkManDao extends BaseDao{
	
	public LinkMan getLinkMan(long vechiel_id) throws SystemException ;
	
}
