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
import com.gboss.dao.FeePaymentDao;
import com.gboss.pojo.FeePayment;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:FeePaymentDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-7-14 下午1:55:48
 */
@Repository("feePaymentDao")
@Transactional
public class FeePaymentDaoImpl extends BaseDaoImpl implements FeePaymentDao {

	@Override
	public List<HashMap<String, Object>> getPaymentRecords(Long cust_id,
			Long vehicle_id, Long unit_id) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select p.stamp as stamp,p.ac_amount as ac_amount,p.real_amount as real_amount, p.feetype_id as feetype_id,p.bw_no as bw_no, ");
		sb.append(" p.item_name as item_name,p.agent_name as agent_name,p.remark as remark from t_fee_payment p ");
		sb.append(" where 1=1 ");
		if (null != cust_id) {
			sb.append(" and p.customer_id=").append(cust_id);
		}
		if (null != unit_id) {
			sb.append(" and p.unit_id=").append(unit_id);
		}
		if (null != vehicle_id) {
			sb.append(" and p.vehicle_id=").append(vehicle_id);
		}
		sb.append(" order by p.stamp desc limit 6");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public Boolean getFeePayment(Long unitId, Integer feetype_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				FeePayment.class);
		if (unitId != null) {
			criteria.add(Restrictions.eq("unit_id", unitId));
		}
		if (null != feetype_id) {
			criteria.add(Restrictions.eq("feetype_id", feetype_id));
		}
		List<FeePayment> list = criteria.list();
		if (list == null || list.size() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public FeePayment getLastFeePayment(Long unitId, Integer feetype_id) {
		StringBuffer hqlSb = new StringBuffer();
		hqlSb.append(" from FeePayment as c");
		if (unitId != null) {
			hqlSb.append(" where c.unit_id=").append(unitId);
		}
		hqlSb.append(" and c.feetype_id=").append(feetype_id);
		hqlSb.append(" order by c.payment_id desc ");
		Query query = sessionFactory.getCurrentSession().createQuery(
				hqlSb.toString());
		List<FeePayment> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<HashMap<String, Object>> findPaymentRecordsPage(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select p.payment_id as id, p.stamp as stamp,p.ac_amount as ac_amount,p.real_amount as real_amount, p.feetype_id as feetype_id,p.bw_no as bw_no, ");
		sb.append(" p.pay_model as pay_model,p.item_name as item_name,p.agent_name as agent_name,p.remark as remark ");
		sb = getConditionBy(sb, conditionMap);
		sb.append(" order by p.stamp desc ");
		if (pn>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pn, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countPaymentRecords(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select DISTINCT count(p.payment_id) ");
		sb = getConditionBy(sb, conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		return ((BigInteger) query.uniqueResult()).intValue();
	}

	private StringBuffer getConditionBy(StringBuffer sb, Map conditionMap) {
		if (conditionMap != null) {
			sb.append(" from t_fee_payment p where 1 = 1 ");
			Boolean flag = false;
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("cust_id"))) {
				sb.append(" and p.customer_id= ").append(
						conditionMap.get("cust_id"));
				flag = true;
			}

			if (StringUtils.isNotNullOrEmpty(conditionMap.get("unit_id"))) {
				sb.append(" and p.unit_id= ").append(
						conditionMap.get("unit_id"));
				flag = true;
			}

			if (StringUtils
					.isNotNullOrEmpty(conditionMap.get("vehicle_id"))) {
				sb.append(" and p.vehicle_id= ").append(
						conditionMap.get("vehicle_id"));
				flag = true;
			}

			if (!flag) {
				sb.append(" and p.customer_id= ").append(0);
			}

		}

		return sb;
	}

	private StringBuffer getConditionBy2(StringBuffer sb, Map conditionMap) {
		sb.append(" FROM t_fee_payment p, t_ba_vehicle v, t_ba_unit u ");
		sb.append(" where p.vehicle_id = v.vehicle_id and p.unit_id = u.unit_id ");
		if (conditionMap != null) {
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("cust_id"))) {
				sb.append(" and p.customer_id= ").append(
						conditionMap.get("cust_id"));
			}

			if (StringUtils.isNotNullOrEmpty(conditionMap.get("plate_no"))) {
				sb.append(" and v.plate_no like '%")
						.append(conditionMap.get("plate_no")).append("%'");
			}

			if (StringUtils.isNotNullOrEmpty(conditionMap.get("call_letter"))) {
				sb.append(" and u.call_letter like '%")
						.append(conditionMap.get("call_letter")).append("%'");
			}

		}
		return sb;
	}

	@Override
	public List<HashMap<String, Object>> findPaymentRecordsPage2(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select p.stamp as stamp,p.ac_amount as ac_amount,p.real_amount as real_amount, p.feetype_id as feetype_id,p.bw_no as bw_no, ");
		sb.append(" p.item_name as item_name,p.agent_name as agent_name,p.remark as remark,v.plate_no as plate_no, u.call_letter as call_letter ");
		sb = getConditionBy2(sb, conditionMap);
		sb.append(" order by p.stamp desc ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countPaymentRecords2(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(p.payment_id) ");
		sb = getConditionBy2(sb, conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		return ((BigInteger) query.uniqueResult()).intValue();
	}

	@Override
	public List<HashMap<String, Object>> findFeeDetailPage(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select v.plate_no,p.feetype_id,p.pay_model,p.s_months,p.s_days,p.ac_amount,p.real_amount, date_format(p.s_date,'%Y-%m-%d') starttime, date_format(p.e_date,'%Y-%m-%d') endtime ");
		sb = getConditionDetail(sb, conditionMap);
		sb.append(" order by p.vehicle_id desc ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn > 0 && pageSize > 0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	public StringBuffer getConditionDetail(StringBuffer sb, Map conditionMap) {
		sb.append(" FROM t_fee_payment_dt p, t_ba_vehicle v ");
		sb.append(" where p.vehicle_id = v.vehicle_id  ");
		if (conditionMap != null) {
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("id"))) {
				sb.append(" and p.payment_id = ")
						.append(conditionMap.get("id"));
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("plate_no"))) {
				sb.append(" and v.plate_no like '%")
						.append(conditionMap.get("plate_no")).append("%'");
			}

		}
		return sb;
	}

	@Override
	public int countFeeDetail(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(p.payment_sub_id) ");
		sb = getConditionDetail(sb, conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		return ((BigInteger) query.uniqueResult()).intValue();
	}
}
