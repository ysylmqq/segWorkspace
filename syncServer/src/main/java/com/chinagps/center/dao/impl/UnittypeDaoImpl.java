package com.chinagps.center.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chinagps.center.dao.UnittypeDao;
import com.chinagps.center.pojo.Linkman;
import com.chinagps.center.pojo.UnitType;
import com.chinagps.center.utils.StringUtils;

@Repository("UnittypeDao")
public class UnittypeDaoImpl extends BaseDaoImpl implements UnittypeDao {

	@Override
	public List<UnitType> listByName(String names) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UnitType.class); 
		if(StringUtils.isNotBlank(names)){
			criteria.add(Restrictions.eq("unittype", names));
		}
		List<UnitType> linkmans=criteria.list();
		return linkmans;
	}
}
