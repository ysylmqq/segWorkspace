package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.PaymentSimDao;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:PaymentDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-12-29 下午4:29:55
 */
@Repository("paymentSimDao")
@Transactional
public class PaymentDaoImpl extends BaseDaoImpl implements PaymentSimDao {

	@Override
	public List<HashMap<String, Object>> findRecordsPage(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select p.payment_id as id, date_format(p.stamp,'%Y-%m-%d')as stamp,p.ac_amount,p.remark as remark,p.s_days,p.s_months, ");
		sb.append(" date_format(p.s_date,'%Y-%m-%d') as s_date, date_format(p.e_date,'%Y-%m-%d') as e_date,c.combo_name ");
		sb = getConditionBy(sb, conditionMap);
		sb.append(" order by p.stamp desc ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countRecords(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(p.payment_id) ");
		sb = getConditionBy(sb, conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		return ((BigInteger) query.uniqueResult()).intValue();
	}

	private StringBuffer getConditionBy(StringBuffer sb, Map conditionMap) {
		sb.append(" FROM t_fee_payment_sim p left join t_fee_sim_combo c on p.combo_id = c.combo_id ");
		sb.append(" where p.sim_id=").append(conditionMap.get("sim_id"));
		return sb;
	}

	

	private StringBuffer getConditionByPage(Long companyId,StringBuffer sb, Map conditionMap) {
		sb.append(" FROM t_fee_payment_sim p ,t_fee_sim_combo c, t_ba_sim s ");
		sb.append(" where p.combo_id = c.combo_id and p.sim_id = s.sim_id ");
		sb.append(" and p.subco_no=").append(companyId);
		if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate"))) {
			sb.append(" and p.stamp >='").append(DateUtil.dateforSearch(conditionMap, "startDate")).append("'");
		}
	 	
	 	if (StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
			sb.append(" and p.stamp <='").append(DateUtil.dateforSearch(conditionMap, "endDate")).append("'");
		}
	 	
	 	if(StringUtils.isNotNullOrEmpty(conditionMap.get("call_letter"))){
			sb.append(" and s.call_letter like '%").append(conditionMap.get("call_letter")).append("%'");
		}
		return sb;
	}
	@Override
	public List<HashMap<String, Object>> findPaymentSimPage(Long companyId,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select p.payment_id as id, date_format(p.stamp,'%Y-%m-%d')as stamp,p.ac_amount,p.remark as remark,p.s_days,p.s_months, ");
		sb.append(" date_format(p.s_date,'%Y-%m-%d') as s_date, date_format(p.e_date,'%Y-%m-%d') as e_date,c.combo_name,s.call_letter,s.vin ");
		sb = getConditionByPage(companyId,sb, conditionMap);
		sb.append(" order by p.stamp desc ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countPaymentSimPage(Long companyId,Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(p.payment_id) ");
		sb = getConditionByPage(companyId,sb, conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		return ((BigInteger) query.uniqueResult()).intValue();
	}

}
