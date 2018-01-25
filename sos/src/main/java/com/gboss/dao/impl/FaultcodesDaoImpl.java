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
import com.gboss.dao.FaultcodesDao;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:FaultcodesDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-28 上午10:13:41
 */
@Repository("faultcodesDao")  
@Transactional
public class FaultcodesDaoImpl extends BaseDaoImpl implements FaultcodesDao {

	@Override
	public List<HashMap<String, Object>> findFaultcodesPage(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select f.faultcodes as code ,f.exclude as  exclude ");
		sb = getConditionHql(sb, conditionMap);
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
	public int countFaultcodes(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(f.faultcodes) ");
		sb = getConditionHql(sb,conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	 private StringBuffer getConditionHql(StringBuffer sb,Map conditionMap){
		 	sb.append(" from t_ba_faultcodes f ");
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("faultcodes"))){
					sb.append(" where f.faultcodes like '%").append(conditionMap.get("faultcodes")).append("%'");
				}
			}
			return sb;
	    }
}

