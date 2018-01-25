package com.gboss.dao.impl;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.OndutyDao;
import com.gboss.pojo.Onduty;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:OndutyDaoImpl
 * @Description:电工值班数据持久层实现类
 * @author:bzhang
 * @date:2014-3-26 下午3:57:17
 */
@Repository("ondutyDao")
@Transactional
public class OndutyDaoImpl extends BaseDaoImpl implements OndutyDao {

	@Override
	public Onduty getOndutyByIdAndTime(String userId, Date time) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Onduty.class);
		if (userId != null && null != time) {
			criteria.add(Restrictions.eq("user_id", Long.parseLong(userId)));
			criteria.add(Restrictions.eq("time", time));
			if (criteria.list().size() != 0) {
				return (Onduty) criteria.list().get(0);
			}
		}
		return null;
	}

}
