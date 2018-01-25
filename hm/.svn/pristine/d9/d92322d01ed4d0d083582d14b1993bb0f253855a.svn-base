package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.VehicleConfDao;
import com.gboss.pojo.VehicleConf;

@Repository("vehicleConfDao")
@Transactional
public class VehicleConfDaoImpl extends BaseDaoImpl implements VehicleConfDao {

	public VehicleConf getVehicleConfByCallLetter(String call_letter) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VehicleConf.class);
		if (call_letter != null) {
			criteria.add(Restrictions.eq("call_letter", call_letter));
		}
		if (criteria.list().size() > 0) {
			return (VehicleConf) criteria.list().get(0);
		}
		return null;
	}

	public List<VehicleConf> getVehicleConfs() {
//		String sql = " select * from t_ba_vehicle_conf t where t.flag != 4 and t.code1 = 0 ";
		String sql = " select * from t_ba_vehicle_conf t ";//所有都不删除
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.addEntity(VehicleConf.class);
		List<VehicleConf> list = query.list();
		if(list!=null && list.size()>0)
			return list;
		
		return null;
	}

	
}
