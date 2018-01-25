package com.gboss.dao.impl;

import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.LinkmanDao;
import com.gboss.pojo.Linkman;

@Repository("linkmanDao")
public class LinkmanDaoImpl extends BaseDaoImpl implements LinkmanDao {

	@Override
	public Long add(Linkman linkMan) throws SystemException {
		save(linkMan);
		return linkMan.getLinkman_id();
	}

}
