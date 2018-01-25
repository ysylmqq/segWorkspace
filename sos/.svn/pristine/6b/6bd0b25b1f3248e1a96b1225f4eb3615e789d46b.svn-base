package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.ServicefeeDao;
import com.gboss.pojo.Servicefee;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:ServicefeeDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-8 下午2:19:28
 */
@Repository("ServicefeeDao")  
@Transactional
public class ServicefeeDaoImpl extends BaseDaoImpl implements ServicefeeDao{

	@Override
	public List<Servicefee> getByVehicle_id(Long vehicle_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Servicefee.class); 
		if(vehicle_id!=null){
			criteria.add(Restrictions.eq("vehicle_id", vehicle_id));
		}
		List<Servicefee> list = criteria.list();
		return list;
	}

}

