package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.OperatorUnitDao;
import com.gboss.pojo.OperatorUnit;
import com.gboss.pojo.Unit;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:OperatorUnitDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2015-2-10 上午11:28:18
 */
@Repository("OperatorUnitDao")  
@Transactional
public class OperatorUnitDaoImpl extends BaseDaoImpl implements OperatorUnitDao {

	@Override
	public void updateOperatorUnit(OperatorUnit operUnit) {
		StringBuffer sb = new StringBuffer();
		Long opid = operUnit.getOp_id();
		Long unitid = operUnit.getUnit_id();
		sb.append("update OperatorUnit set op_id = ");
		sb.append(opid).append(" where unit_id = ");
		sb.append(unitid);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.executeUpdate();
	}

	@Override
	public OperatorUnit getByVehicle(Long vehicle_id) throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OperatorUnit.class); 
		if(vehicle_id!=null){
			criteria.add(Restrictions.eq("vehicle_id", vehicle_id));
		}
		List<OperatorUnit> list = criteria.list();
		if(list==null||list.size()==0){
			return null;
		}
		return list.get(0);
	}

}

