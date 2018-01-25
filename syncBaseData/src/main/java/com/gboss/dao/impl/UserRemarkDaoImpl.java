package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.Query;

import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.UserRemarkDao;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:UserRemarkDaoImpl
 * @Description:备忘录数据持久层实现类
 * @author:zfy
 * @date:2013-11-18 上午10:16:54
 */
@Repository("userRemarkDao")  
 
public class UserRemarkDaoImpl extends BaseDaoImpl implements UserRemarkDao {

	@Override
	public List<HashMap<String, Object>> findUserRemark(Map<String, Object> conditionMap, String order, boolean isDesc, int pageNo, int pageSize) throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countUserRemarks(Map<String, Object> conditionMap) throws SystemException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteUserRemark(List<Long> idList) throws SystemException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateRemarkIsAlert(Long id, Long userId) throws SystemException {
		// TODO Auto-generated method stub
		return 0;
	}

	 
	

}

