package com.gboss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.LinkManDao;
import com.gboss.pojo.LinkMan;
import com.gboss.service.LinkmanService;

@Service("linkmanService")
public class LinkmanServiceImpl extends BaseServiceImpl implements LinkmanService {

	@Autowired
	private LinkManDao linkManDao;
	
	 
	public void saveLinkMan(LinkMan man) throws SystemException {
		try {
			save(man);
		} catch (Exception e) {
			SystemConst.E_LOG.error("保存联系人出错");
		}
	}

	public LinkMan getLinkMan(long vechiel_id) throws SystemException {
		return linkManDao.getLinkMan(vechiel_id);
	}

}
