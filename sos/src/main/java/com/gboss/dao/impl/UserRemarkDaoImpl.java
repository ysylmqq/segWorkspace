package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class UserRemarkDaoImpl extends BaseDaoImpl implements UserRemarkDao {

	@Override
	public List<HashMap<String, Object>> findUserRemark(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select new map(u.id as id,u.content as content,u.isAlert as isAlert,u.stamp as stamp,u.userId as userId)");
		sb.append(" from UserRemark as u");
		sb.append(" where 1=1 ");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("userId"))){//用户ID
				sb.append(" and u.userId=").append(conditionMap.get("userId"));
			}
			if(conditionMap.get("isAlert")!=null){//登陆后是否提示 1：是、0：否
				sb.append(" and u.isAlert=").append(conditionMap.get("isAlert"));
			}
			if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
				sb.append(" and u.stamp >='").append(conditionMap.get("startDate")).append("'");
			}
			if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
				sb.append(" and u.stamp <='").append(conditionMap.get("endDate")).append("'");
			}
		}
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		if(pageNo>0 && pageSize>0){
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countUserRemarks(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select count(*) ");
		sb.append(" from UserRemark as u");
		sb.append(" where 1=1 ");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("userId"))){//用户ID
				sb.append(" and u.userId=").append(conditionMap.get("userId"));
			}
			if(conditionMap.get("isAlert")!=null){//登陆后是否提示 1：是、0：否
				sb.append(" and u.isAlert=").append(conditionMap.get("isAlert"));
			}
		}
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		return ((Long)query.uniqueResult()).intValue();
	}

	@Override
	public int deleteUserRemark(List<Long> idList) throws SystemException {
		String hql="delete UserRemark WHERE id IN (:alist)";  
		Query query = sessionFactory.getCurrentSession().createQuery(hql);  
		query.setParameterList("alist", idList); 
		return query.executeUpdate();
	}

	@Override
	public int updateRemarkIsAlert(Long id, Long userId)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" update UserRemark ");
		sb.append(" set isAlert=0");
		sb.append(" where id<>").append(id);
		sb.append(" and userId=").append(userId);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}

}

