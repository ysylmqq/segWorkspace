package com.gboss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemException;
import com.gboss.dao.OrgDao;
import com.gboss.pojo.Org;
import com.gboss.service.OrgService;

@Service("OrgService")
public class OrgServiceImpl extends BaseServiceImpl implements OrgService {
	
	@Qualifier("OrgDao")
	@Autowired
	private OrgDao orgDao;

	@Override
	public Integer saveOrg(Org org) throws SystemException{
		return orgDao.saveOrg(org);
	}

}
