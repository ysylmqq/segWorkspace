package com.chinagps.center.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chinagps.center.common.SystemException;
import com.chinagps.center.dao.SynUrlDao;
import com.chinagps.center.pojo.Customer;
import com.chinagps.center.pojo.SynUrl;

@Repository("SynUrlDao")
public class SynUrlDaoImpl extends BaseDaoImpl implements SynUrlDao {

	@Override
	public List<SynUrl> listAll() throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SynUrl.class); 
		List<SynUrl> list=criteria.list();
		return list;
	}

	@Override
	public List<SynUrl> listByCompanyId(Long subco_no) throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SynUrl.class); 
		if(subco_no != null){
			criteria.add(Restrictions.eq("subco_no", subco_no));
		}
		List<SynUrl> list=criteria.list();
		return list;
	}

}
