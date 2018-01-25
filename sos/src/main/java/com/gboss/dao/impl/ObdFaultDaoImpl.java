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
import com.gboss.dao.ObdFaultDao;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:ObdFaultDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-12-25 上午11:15:47
 */
@Repository("obdFaultDao")
@Transactional
public class ObdFaultDaoImpl extends BaseDaoImpl implements ObdFaultDao {

	@Override
	public List<HashMap<String, Object>> findobdFaultList(Long companyId,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select b.file_id as id,b.subco_no,b.file_name,b.url,date_format(b.st_date,'%Y-%m-%d') as st_date,b.stamp ");
		sb=getConditionHql(companyId, sb,conditionMap);
		sb.append(" order by b.file_id desc");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	
	

	 private StringBuffer getConditionHql(Long id, StringBuffer sb,Map conditionMap){
		 	sb.append(" from t_ba_obd_fault b where 1=1   ");
		 	sb.append(" and b.subco_no=").append(id);
		 	if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate"))) {
				sb.append(" and b.st_date >='").append(DateUtil.dateforSearch(conditionMap, "startDate")).append("'");
			}
		 	
		 	if (StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
				sb.append(" and b.st_date <='").append(DateUtil.dateforSearch(conditionMap, "endDate")).append("'");
			}
		 	
			return sb;
	    }

	@Override
	public int countObdFault(Long companyId, Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(b.file_id) ");
		sb=getConditionHql(companyId, sb,conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}


}

