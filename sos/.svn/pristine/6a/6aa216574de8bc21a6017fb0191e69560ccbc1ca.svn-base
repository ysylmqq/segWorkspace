package com.gboss.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.MaintainRuleDao;
import com.gboss.pojo.MaintainItems;
import com.gboss.pojo.MaintainRule;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:MaintainRuleDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-27 上午10:51:19
 */
@Repository("maintainRuleDao")
@Transactional
public class MaintainRuleDaoImpl extends BaseDaoImpl implements MaintainRuleDao {

	@Override
	public MaintainRule getMaintainRuleByModelId(Long model_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				MaintainRule.class);
		criteria.add(Restrictions.eq("model", model_id));
		if (criteria.list() != null && criteria.list().size() > 0) {
			return (MaintainRule) criteria.list().get(0);
		}
		return null;
	}

	@Override
	public MaintainItems getMaintainItemsByModelId(Long model_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				MaintainItems.class);
		criteria.add(Restrictions.eq("model", model_id));
		if (criteria.list() != null && criteria.list().size() > 0) {
			return (MaintainItems) criteria.list().get(0);
		}
		return null;
	}

}
