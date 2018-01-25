package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.UpgradeDao;
import com.gboss.pojo.Preload;
import com.gboss.pojo.Upgrade;
import com.gboss.pojo.UpgradeFile;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:UpgradeDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2015-1-27 上午8:52:51
 */
@Repository("upgradeDao")  
@Transactional
public class UpgradeDaoImpl extends BaseDaoImpl implements UpgradeDao {

	@Override
	public List<HashMap<String, Object>> getSimUpgradeList(Long companyId,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select s.call_letter,s.vin,s.barcode,u.cur_ver,u.ug_ver,u.flag,u.s_time,u.e_time,u.upgrade_name,s.scan_time,IFNULL(c.code1,0) conf_code, IFNULL(c.flag,0) conf_flag , c.is_on,s.equip_code ");
		sb=getConditionHql(companyId, sb,conditionMap);
		sb.append(" order by s.sim_id desc");
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn > 0 && pageSize > 0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	
	
	
	 private StringBuffer getConditionHql(Long id, StringBuffer sb,Map conditionMap){
		 	sb.append(" FROM t_ba_sim s LEFT JOIN t_st_unit_upgrade u on s.call_letter = u.call_letter ");
		 	sb.append(" LEFT JOIN t_ba_vehicle_conf c ON s.call_letter = c.call_letter ");
		 	sb.append(" where s.subco_no=").append(id);
		 	if(StringUtils.isNotNullOrEmpty(conditionMap.get("call_letter"))){
				sb.append(" and s.call_letter like '%").append(conditionMap.get("call_letter")).append("%'");
			}
		 	if(StringUtils.isNotNullOrEmpty(conditionMap.get("vin"))){
				sb.append(" and s.vin like '%").append(conditionMap.get("vin")).append("%'");
			}
		 
		 	if(StringUtils.isNotNullOrEmpty(conditionMap.get("barcode"))){
		 		sb.append(" and s.barcode like '%").append(conditionMap.get("barcode")).append("%'");
			}
		 	
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("flag"))){
		 		sb.append(" and u.flag = ").append(conditionMap.get("flag"));
			}
			
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("conf_flag")) && Integer.parseInt(conditionMap.get("conf_flag").toString())!= 0){
				sb.append(" and c.flag = ").append(conditionMap.get("conf_flag"));
			}
			
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("is_on"))){
				sb.append(" and c.is_on = ").append(conditionMap.get("is_on"));
			}
			
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("cur_ver"))){
		 		sb.append(" and u.cur_ver='").append(conditionMap.get("cur_ver")).append("'");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("upgrade_name"))){
				sb.append(" and u.upgrade_name='").append(conditionMap.get("upgrade_name")).append("'");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("startDate"))){
				sb.append(" and s.scan_time >='").append(conditionMap.get("startDate")).append("'");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))){
				sb.append(" and s.scan_time >='").append(conditionMap.get("endDate")).append("'");
			}
			return sb;
	    }


	@Override
	public int countSimUpgrade(Long companyId, Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.sim_id) ");
		sb=getConditionHql(companyId, sb,conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public String getSimUpgradeletters(
			Long companyId, Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select group_concat(s.call_letter) as call_letter ");
		sb=getConditionHql(companyId, sb,conditionMap);
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if( query.list().size() > 0){
			return (String) query.list().get(0);
		}else{
			return null;
			
		}
	}

	@Override
	public List<Upgrade> getUpgradeList() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Upgrade.class); 
		//criteria.add(Restrictions.not(Restrictions.eq("flag", 4)));		
		criteria.add( Restrictions.or(Restrictions.eq("flag", 1),  Restrictions.eq("flag", 2),  Restrictions.eq("flag", 3),  Restrictions.eq("flag", 5) )); 
		List<Upgrade>  list = criteria.list();
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
		/*StringBuffer sb=new StringBuffer();
		sb.append(" select u.call_letter, u.cur_ver from t_st_unit_upgrade u ");
		sb.append(" where u.flag <> ").append(4);
		sb.append(" order by u.id desc");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();*/
	}
	
	@Override
	public List<Upgrade> getUpgradeAllList() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Upgrade.class); 
		List<Upgrade>  list = criteria.list();
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
		
		

	@Override
	public Upgrade getUpgradeBycall_letter(String call_letter) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Upgrade.class); 
		criteria.add(Restrictions.eq("call_letter", call_letter));
		List<Upgrade>  list = criteria.list();
		if(criteria.list().size() > 0){
			return list.get(0);
		}
		return null;
	}



	@Override
	public List<Upgrade> getUpgradeListforCur_ver() {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" from Upgrade as u group by u.cur_ver ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		return query.list();
	}



	@Override
	public Preload getSimInfo(String vin, String barcode) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Preload.class); 
		if(StringUtils.isNotBlank(vin)){
			criteria.add(Restrictions.eq("vin", vin));
		}
		if(StringUtils.isNotBlank(barcode)){
			criteria.add(Restrictions.eq("barcode", barcode));
		}
		List<Preload>  list = criteria.list();
		if(criteria.list().size() > 0){
			return list.get(0);
		}
		return null;
	}



	@Override
	public List<Preload> getSimAll() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Preload.class); 
	    criteria.add(Restrictions.eq("subco_no", Long.valueOf(201)));
		List<Preload>  list = criteria.list();
		return list;
	}

}

