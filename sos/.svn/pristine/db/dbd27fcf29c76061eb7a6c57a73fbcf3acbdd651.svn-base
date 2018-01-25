package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.ServicepackDao;
import com.gboss.pojo.Servicepack;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:ServicepackDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-6 下午4:11:25
 */
@Repository("ServicepackDao")  
@Transactional
public class ServicepackDaoImpl extends BaseDaoImpl implements ServicepackDao {

	@Override
	public List<Servicepack> getServicepacks(Long subco_no) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Servicepack.class);
		if(subco_no!=null){
			criteria.add(Restrictions.eq("subco_no", subco_no));				
		}
		criteria.add(Restrictions.eq("flag", 1));
		criteria.add(Restrictions.eq("is_checked", 1));
		List<Servicepack> list = criteria.list();
		return list;
	}

}

