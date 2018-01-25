package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.UnitserviceDao;
import com.gboss.pojo.Unitservice;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:UnitserviceDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-15 下午5:34:13
 */

@Repository("UnitserviceDao")  
@Transactional
public class UnitserviceDaoImpl extends BaseDaoImpl implements UnitserviceDao{

	@Override
	public Unitservice getByVehicle_id(Long vehicle_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Unitservice.class); 
		if(vehicle_id!=null){
			criteria.add(Restrictions.eq("vehicle_id", vehicle_id));
		}
		List<Unitservice> list = criteria.list();
		if(list==null||list.size()==0){
			return null;
		}
		return list.get(0);
	}

}

