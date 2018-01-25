package com.gboss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.FollowDao;
import com.gboss.service.FollowService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:FollowServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-22 下午2:28:07
 */
@Repository("followService")  
@Transactional  
public class FollowServiceImpl extends BaseServiceImpl implements FollowService {

	@Autowired  
	@Qualifier("followDao")
	private FollowDao followDao;
	
}

