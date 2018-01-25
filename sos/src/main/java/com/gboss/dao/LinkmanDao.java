package com.gboss.dao;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Linkman;

public interface LinkmanDao extends BaseDao {

	public Long add(Linkman linkman)throws SystemException;

}
