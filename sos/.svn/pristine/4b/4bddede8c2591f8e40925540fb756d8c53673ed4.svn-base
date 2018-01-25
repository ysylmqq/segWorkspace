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
import com.gboss.dao.NewsDao;
import com.gboss.pojo.NewsImages;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:NewsDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-17 上午9:13:17
 */
@Repository("newsDao")
@Transactional
public class NewsDaoImpl extends BaseDaoImpl implements NewsDao {

	@Override
	public List<HashMap<String, Object>> getNewsList(Long companyId) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select n.id as id, n.title as title ,n.new_origin as new_origin, n.is_top as is_top, n.add_time as add_time, n.op_name as op_name,");
		sb.append(" n.content as content,n.img_large as img_large,n.img_little as img_little,n.state as state ");
		sb.append(" from t_ba_news as n  where 1=1 ");
		sb.append(" and n.org_id=").append(companyId);
		sb.append(" order by n.id desc limit 3");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countNews(Long companyId,Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(n.id) ");
		sb=getConditionBy(companyId, sb, conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public List<HashMap<String, Object>> findNews(Long companyId,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select n.id as id, n.title as title,n.content as content,n.org_id as orgId,n.op_name as opName, ");
		sb.append(" n.is_top as isTop,n.new_origin as newOrigin,n.state as state,n.add_time as addTime, ");
		sb.append(" n.img_large as imgLarge , n.img_little as imgLittle, n.cheker_info as checkInfo, n.is_online as isOnline,");
		sb.append(" n.is_submit as isSubmit,n.submit_time as submitTime, n.check_state as checkState,n.check_time as checkTime ");

		sb=getConditionBy(companyId, sb,conditionMap);
		sb.append(" order by n.add_time");
		/*if (StringUtils.isNotBlank(order)) {
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}*/
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	

	 private StringBuffer getConditionBy(Long companyId, StringBuffer sb,Map conditionMap){
			sb.append(" from t_ba_news n ");
			sb.append(" WHERE n.org_id =").append(companyId);
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("title"))){
					sb.append(" and n.title like '%").append(conditionMap.get("title")).append("%'");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("checkState"))){
					sb.append(" and n.check_state = ").append(conditionMap.get("checkState"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("isSubmit"))){
					sb.append(" and n.is_submit = ").append(conditionMap.get("isSubmit"));
				}
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate")) && StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
					/*sb.append(" and n.add_time >='").append(conditionMap.get("startDate")).append("'");
					sb.append(" and n.add_time <='").append(conditionMap.get("endDate")).append("'");*/
					
					sb.append(" and DATE_FORMAT(n.add_time, '%Y-%m-%d') >='").append(conditionMap.get("startDate")).append("'");
					sb.append(" and DATE_FORMAT(n.add_time, '%Y-%m-%d') <='").append(conditionMap.get("endDate")).append("'");
				}
			}
			
			sb.append(" and n.state = 0");
			return sb;
	    }

	@Override
	public int operateNews(List<Long> ids, Integer type) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update News set state=:st where id in (:ids) ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		query.setParameter("st", type);
		query.setParameterList("ids", ids); 
		return query.executeUpdate();
	}

	@Override
	public int operateNews(List<Long> ids, Integer type, Integer updateType,Map<String,Object>param) {
		StringBuffer hqlSb=new StringBuffer();
		String updateString = "";
		
		switch(updateType){
		case 1:
			updateString = " update News set is_submit=:st, check_state=0,submit_time=now(),last_op_id=:opId,last_time=now() where id in (:ids) ";
//			query.setParameter("subt", new java.util.Date());
			break;
		case 2:
			if(param!=null && param.containsKey("feedBack")){
				 updateString = " update News set check_state=:st , cheker_info=:ci, check_time=now(), last_op_id=:opId,last_time=now() where id in (:ids) ";	
			}
			else
			   updateString = " update News set check_state=:st , check_time=now(), last_op_id=:opId,last_time=now() where id in (:ids) ";
			break;
		case 3:
			updateString = " update News set is_online=:st, check_state=:st, check_time=now(),last_op_id=:opId,last_time=now()  where id in (:ids) ";
			break;
		case 4:
			updateString = " update News set is_online=:st,last_op_id=:opId,last_time=now()  where id in (:ids) ";

			break;
		case 5:
			break;
		}
		
		hqlSb.append(updateString);
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		query.setParameter("st", type);
		query.setParameterList("ids", ids); 
	    if(param!=null && param.containsKey("opId")){
	    	query.setParameter("opId", param.get("opId"));
	    }
	    else
	    	query.setParameter("opId", -1);
		if(param!=null && param.containsKey("feedBack")){
			query.setParameter("ci", param.get("feedBack").toString()); 			
		}
		return query.executeUpdate();
	}

	@Override
	public int delete(List<Long> ids) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" delete News n where n.id in (:ids) ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		query.setParameterList("ids", ids); 
		return query.executeUpdate();
	}

	@Override
	public List<NewsImages> getNewsImages(Long newsId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(NewsImages.class); 
		criteria.add(Restrictions.eq("news_id", newsId));
		criteria.add(Restrictions.eq("type", 2));
		List<NewsImages> ImgList =  criteria.list();
		return ImgList;
	}

	@Override
	public int countNews(Map conditionMap) {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(n.id) ");
		sb.append(" from t_ba_news n ");
		
		if(conditionMap!=null){
			sb.append(" WHERE n.state = 0");
			
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("title"))){
				sb.append(" and n.title like '%").append(conditionMap.get("title")).append("%'");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("checkState"))){
				sb.append(" and n.check_state = ").append(conditionMap.get("checkState"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("isSubmit"))){
				sb.append(" and n.is_submit = ").append(conditionMap.get("isSubmit"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("orgName"))){
				sb.append(" and n.org_name like '%").append(conditionMap.get("orgName")).append("%'");
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate")) && StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
				
				sb.append(" and DATE_FORMAT(n.add_time, '%Y-%m-%d') >='").append(conditionMap.get("startDate")).append("'");
				sb.append(" and DATE_FORMAT(n.add_time, '%Y-%m-%d') <='").append(conditionMap.get("endDate")).append("'");
			}
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();		
	}

	@Override
	public List<HashMap<String, Object>> findNews(Map conditionMap, String order,
			boolean is_desc, int pn, int pageSize) {
		StringBuffer sb=new StringBuffer();
		sb.append(" select n.id as id, n.title as title,n.org_id as orgId,n.org_name orgName,n.op_name as opName, ");
		sb.append(" n.is_top as isTop,n.new_origin as newOrigin,n.state as state,n.add_time as addTime, n.start_time startTime, ");
//		sb.append(" n.img_large as imgLarge , n.img_little as imgLittle,n.content as content, ");
		sb.append(" n.is_submit as isSubmit,n.submit_time as submitTime, n.check_state as checkState,n.check_time as checkTime, is_online as isOnline");

        sb.append(" from t_ba_news n ");
		
		if(conditionMap!=null){
			sb.append(" WHERE n.state = 0");
			
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("title"))){
				sb.append(" and n.title like '%").append(conditionMap.get("title")).append("%'");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("checkState"))){
				sb.append(" and n.check_state = ").append(conditionMap.get("checkState"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("isSubmit"))){
				sb.append(" and n.is_submit = ").append(conditionMap.get("isSubmit"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("orgName"))){
				sb.append(" and n.org_name like '%").append(conditionMap.get("orgName")).append("%'");
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate")) && StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
				
				sb.append(" and DATE_FORMAT(n.add_time, '%Y-%m-%d') >='").append(conditionMap.get("startDate")).append("'");
				sb.append(" and DATE_FORMAT(n.add_time, '%Y-%m-%d') <='").append(conditionMap.get("endDate")).append("'");
			}
		}
		sb.append(" order by n.last_time desc");
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	
}
