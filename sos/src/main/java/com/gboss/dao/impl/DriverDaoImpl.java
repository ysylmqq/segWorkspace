package com.gboss.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.DriverDao;
import com.gboss.pojo.Driver;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:DriverDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-21 上午11:23:52
 */
@Repository("DriverDao")  
@Transactional
public class DriverDaoImpl extends BaseDaoImpl implements DriverDao {

	@Override
	public List<Long> getDriverids(Long vehicle_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Driver.class); 
		if(vehicle_id!=null){
			criteria.add(Restrictions.eq("vehicle_id", vehicle_id));
		}
		List<Driver> list = criteria.list();
		List<Long> driverids = new ArrayList<Long>();
		for(Driver driver:list){
			driverids.add(driver.getDriver_id());
		}
		return driverids;
	}

	@Override
	public List<Driver> getDrivers(Long vehicle_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Driver.class); 
		if(vehicle_id!=null){
			criteria.add(Restrictions.eq("vehicle_id", vehicle_id));
		}
		criteria.addOrder(Order.desc("driver_id"));
		List<Driver> list = criteria.list();
		return list;
	}

	@Override
	public void deleteByVehicleid(Long vehicle_id) {
		String hql = "delete from Driver where vehicle_id = " + vehicle_id;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void deleteByCustomerid(Long cust_id) {
		String hql = "delete from Driver where customer_id = " + cust_id;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}

}

