package com.gboss.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.FollowDao;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:FollowDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-22 下午2:26:47
 */
@Repository("followDao")  
@Transactional
public class FollowDaoImpl extends BaseDaoImpl implements FollowDao {

}

