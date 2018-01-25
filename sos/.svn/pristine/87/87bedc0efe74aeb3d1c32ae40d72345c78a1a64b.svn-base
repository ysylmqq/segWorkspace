package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.ServicetimeDao;
import com.gboss.pojo.Servicetime;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:ServicetimeDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-13 上午10:46:10
 */
@Repository("ServicetimeDao")  
@Transactional 
public class ServicetimeDaoImpl extends BaseDaoImpl implements ServicetimeDao {

	@Override
	public Servicetime getByUnitid(Long unit_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Servicetime.class); 
		if(unit_id!=null){
			criteria.add(Restrictions.eq("unit_id", unit_id));
		}
		List<Servicetime> list = criteria.list();
		if(list==null||list.size()==0){
			return null;
		}
		return list.get(0);
	}

}

