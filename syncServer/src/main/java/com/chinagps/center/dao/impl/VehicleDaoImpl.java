package com.chinagps.center.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chinagps.center.common.SystemException;
import com.chinagps.center.dao.VehicleDao;
import com.chinagps.center.pojo.Vehicle;
import com.chinagps.center.utils.StringUtils;

@Repository("VehicleDao")
public class VehicleDaoImpl extends BaseDaoImpl implements VehicleDao {

	@Override
	public Vehicle getByPlateNo(String plate_no) throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Vehicle.class); 
		if(StringUtils.isNotBlank(plate_no)){
			criteria.add(Restrictions.eq("plate_no", plate_no));
		}
		List<Vehicle> vehicles=criteria.list();
		if(StringUtils.isNotNullOrEmpty(vehicles)){
			return vehicles.get(0);
		}
		return null;
	}

	@Override
	public Long add(Vehicle vehicle) throws SystemException {
		super.save(vehicle);
		return vehicle.getVehicle_id();
	}

}
