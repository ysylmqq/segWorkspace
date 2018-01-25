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

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.RecordDao;
import com.gboss.pojo.Record;
import com.gboss.pojo.Vehicle;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:RecordDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-8-13 下午3:11:28
 */
@Repository("recordDao")
@Transactional
public class RecordDaoImpl extends BaseDaoImpl implements RecordDao {

	@Override
	public List<HashMap<String, Object>> findRecord(Long companyno,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT p.loc_id AS id,p.b_type AS b_type,p.loc_name AS loc_name, ");
		sb.append(" p.location AS location,p.flag AS flag,p.stamp AS stamp,p.remark as remark ");
		sb=getConditionHql(sb,conditionMap, companyno);
		sb.append(" order by p.loc_id desc");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countRecord(Long companyno, Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(p.loc_id) ");
		sb=getConditionHql(sb,conditionMap,companyno);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	

	 private StringBuffer getConditionHql(StringBuffer sb,Map conditionMap,Long companyno){
		 	sb.append(" FROM t_ba_doc_loc p ");
			sb.append(" where p.flag = 0 ");
			if(null != companyno){
				sb.append(" and p.subco_no=").append(companyno);
			}
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("loc_name"))){
					sb.append(" and p.loc_name like '%").append(conditionMap.get("loc_name")).append("%'");
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("b_type"))){
					sb.append(" and p.b_type=").append(conditionMap.get("b_type"));
				}
				
			}
			return sb;
	    }

	@Override
	public boolean is_exist(Record record) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Record.class);
		int count = 0;
		if (record != null) {
			if (record.getLoc_id() != null) {
				criteria.add(Restrictions.not(Restrictions.eq("loc_id",
						record.getLoc_id())));
			}
			if (record.getLoc_name() != null && !record.getLoc_name().equals("")) {
				criteria.add(Restrictions.eq("loc_name",record.getLoc_name()));
			}
			count = criteria.list().size();
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	

	@Override
	public int delete(List<Long> ids) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update Record set flag=:st where loc_id in (:ids) ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		query.setParameter("st", 9);
		query.setParameterList("ids", ids); 
		return query.executeUpdate();
	}

}

