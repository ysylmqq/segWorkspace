package com.chinagps.center.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.chinagps.center.common.SystemException;
import com.chinagps.center.dao.UnitDao;
import com.chinagps.center.pojo.Unit;
import com.chinagps.center.utils.StringUtils;

@Repository("UnitDao")
public class UnitDaoImpl extends BaseDaoImpl implements UnitDao {

	@Override
	public Map<String, Object> getByCallLetter(String call_letter)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c.subco_no, c.customer_id, u.unit_id, v.vehicle_id, c.customer_name, u.call_letter, v.plate_no");
		sb.append(" , c.cust_type");
		sb.append(" from t_ba_customer c INNER JOIN t_ba_unit u on u.customer_id=c.customer_id");
		sb.append(" INNER JOIN t_ba_vehicle v on u.vehicle_id=v.vehicle_id");
		sb.append(" where u.call_letter='").append(call_letter).append("'");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		if(list != null && !list.isEmpty()){
			return (Map<String, Object>) list.get(0);
		}
		return new HashMap<String, Object>();
	}

	@Override
	public Unit getUnitById(Long unit_id) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" from Unit u where 1=1");
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Unit.class); 
		if(unit_id != null){
			sb.append(" and u.unit_id=").append(unit_id);
//			criteria.add(Restrictions.eq("unit_id", unit_id));
		}
//		List<Unit> units = criteria.list();
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		List<Unit> units = query.list();
		if(StringUtils.isNotNullOrEmpty(units)){
			return units.get(0);
		}
		return null;
	}

	@Override
	public Long add(Unit unit) throws SystemException {
		super.save(unit);
		return unit.getUnit_id();
	}

	@Override
	public Unit getUnitByCallLetter(String call_letter) throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Unit.class); 
		if(StringUtils.isNotBlank(call_letter)){
			criteria.add(Restrictions.eq("call_letter", call_letter));
		}
		List<Unit> units = criteria.list();
		if(StringUtils.isNotNullOrEmpty(units)){
			return units.get(0);
		}
		return null;
	}

}
