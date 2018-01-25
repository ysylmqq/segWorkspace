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
import com.gboss.dao.AnswerDao;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:AnswerDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-28 下午3:55:43
 */
@Repository("answerDao")  
@Transactional 
public class AnswerDaoImpl extends BaseDaoImpl implements AnswerDao {

	@Override
	public List<HashMap<String, Object>> findAnswers(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select a.id as id, t.billno as billno, t.type as type, t.place as place, u.customer_name as name, d.electrician_name as eName, ");
		sb.append(" v.number_plate as carNum, a.mileage as mileage, a.stamp as time ");
		sb=getConditionHql(sb,conditionMap);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countAnswers(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(a.id) ");
		sb=getConditionHql(sb,conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	 private StringBuffer getConditionHql(StringBuffer sb,Map conditionMap){
			sb.append(" from t_ba_task t, t_ba_dispatch_electrician d, t_ba_customer u, t_ba_answer a, t_ba_vehicle v ");
			sb.append(" where t.id = d.task_id and t.customer_id = u.id and a.dispatch_id = d.dispatch_id ");
			sb.append(" and d.electrician = answer_userid and t.customer_id = v.customer_id ");
			if(conditionMap!=null){
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("carNum"))){
					sb.append(" and v.number_plate='").append(conditionMap.get("carNum")).append("'");
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("eName"))){
					sb.append(" and d.electrician_name like '%").append(conditionMap.get("eName")).append("%'");
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("type"))){
					sb.append(" and t.type = ").append(conditionMap.get("type"));
				}
				
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate")) && StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
					sb.append(" and a.stamp >='").append(conditionMap.get("startDate")).append("'");
					sb.append(" and a.stamp <='").append(conditionMap.get("endDate")).append("'");
				}
			}
			return sb;
	    }
	

}

