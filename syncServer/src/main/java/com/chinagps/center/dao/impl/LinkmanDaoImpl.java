package com.chinagps.center.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chinagps.center.common.SystemException;
import com.chinagps.center.dao.LinkmanDao;
import com.chinagps.center.pojo.Linkman;

@Repository("LinkmanDao")
public class LinkmanDaoImpl extends BaseDaoImpl implements LinkmanDao {

	@Override
	public List<Linkman> listByVehicleId(Long vehicle_id)throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Linkman.class); 
		if(vehicle_id != null){
			criteria.add(Restrictions.eq("vehicle_id", vehicle_id));
		}
		List<Linkman> linkmans=criteria.list();
		return linkmans;
	}
}
