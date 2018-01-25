package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.SysDao;
import com.gboss.dao.SysNodeDao;
import com.gboss.pojo.SysNode;
import com.gboss.service.SysNodeService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:SysNodeServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-25 下午3:47:09
 */
@Service("SysNodeService")
@Transactional
public class SysNodeServiceImpl extends BaseServiceImpl implements SysNodeService {

	@Autowired  
	@Qualifier("SysNodeDao")
	private SysNodeDao sysNodeDao;
	
	@Override
	public List<SysNode> findSysNode(SysNode sysNode) {
		return sysNodeDao.findSysNode(sysNode);
	}

}

