package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.CustVehicleDao;
import com.gboss.pojo.CustVehicle;
import com.gboss.pojo.LargeCustLock;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:CustVehicleDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-29 下午1:55:00
 */
@Repository("CustVehicleDao")
@Transactional
public class CustVehicleDaoImpl extends BaseDaoImpl implements CustVehicleDao {

	@Override
	public List<CustVehicle> getByVehicleid(Long vehicle_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				CustVehicle.class);
		if (vehicle_id != null) {
			criteria.add(Restrictions.eq("vehicle_id", vehicle_id));
		}
		List<CustVehicle> list = criteria.list();
		return list;
	}
	
	@Override
	public List<CustVehicle> getByCustomerid(Long cust_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				CustVehicle.class);
		if (cust_id != null) {
			criteria.add(Restrictions.eq("customer_id", cust_id));
		}
		List<CustVehicle> list = criteria.list();
		return list;
	}

	@Override
	public List<HashMap<String, Object>> findLargeVehicles(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT r.plate_no AS plate_no,r.customer_id as customer_id,r.vehicle_id as id,  ");
		sb.append(" r.color AS color,u.product_name AS product_name,u.unit_id as unit_id,u.call_letter as call_letter ");
		sb = getConditionHql(sb, conditionMap);
		sb.append(" order by r.stamp desc ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn > 0 && pageSize > 0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countLargeVehicles(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT count(r.vehicle_id) ");
		sb = getConditionHql(sb, conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		return ((BigInteger) query.uniqueResult()).intValue();
	}

	private StringBuffer getConditionHql(StringBuffer sb, Map conditionMap) {
		sb.append(" FROM ( SELECT v.plate_no AS plate_no, ");
		sb.append(" v.color AS color,c.stamp AS stamp,v.vehicle_id as vehicle_id, ");
		sb.append(" c.customer_id AS customer_id ");
		sb.append(" FROM t_ba_cust_vehicle c, ");
		sb.append(" t_ba_vehicle v WHERE c.vehicle_id = v.vehicle_id ");
		sb.append(" AND c.customer_id = ").append(conditionMap.get("cust_id"));
		sb.append(" ) r LEFT JOIN t_ba_unit u ON u.vehicle_id = r.vehicle_id where 1=1  ");
		sb.append(" and u.reg_status = 0 ");
		if (conditionMap != null) {
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("plate_no"))) {
				sb.append(" and r.plate_no like '%")
						.append(conditionMap.get("plate_no")).append("%'");
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("call_letter"))) {
				sb.append(" and u.call_letter like '%")
						.append(conditionMap.get("call_letter")).append("%'");
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("lockTime"))) {
				sb.append(" and u.create_date <='").append(conditionMap.get("lockTime")).append("'");
			}
			
		}
		return sb;
	}

	@Override
	public List<HashMap<String, Object>> findLargeVehicleList(Long cust_id, String lockTime)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT r.plate_no AS plate_no,r.customer_id as customer_id,r.vehicle_id as vehicle_id, u.unit_id as unit_id  ");
		Map map = new HashMap();
		map.put("cust_id", cust_id);
		map.put("lockTime", lockTime);
		sb = getConditionHql(sb, map);
		sb.append(" order by r.stamp desc ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public Long add(CustVehicle cv) throws SystemException {
		save(cv);
		return cv.getCv_id();
	}

}
