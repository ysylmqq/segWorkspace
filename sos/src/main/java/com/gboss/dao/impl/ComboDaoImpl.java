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
import com.gboss.dao.ComboDao;
import com.gboss.pojo.Combo;
import com.gboss.pojo.Model;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:ComboDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-9-18 下午2:56:42
 */
@Repository("comboDao")  
@Transactional  
public class ComboDaoImpl extends BaseDaoImpl implements ComboDao {

	@Override
	public boolean isExist(Combo combo)throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.combo_id) from Combo as s");
		sb.append(" where 1=1 ");
		if(combo!=null){
			if(StringUtils.isNotBlank(combo.getCombo_name())){
				sb.append(" and s.combo_name='").append(combo.getCombo_name()).append("'");
			}
			if(combo.getCombo_id()!=null){
				sb.append(" and s.combo_id <> ").append(combo.getCombo_id());
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		if((Long)query.uniqueResult()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<HashMap<String, Object>> findComboes(Long conpanyId,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select c.combo_id as id , c.combo_code ,c.combo_name,c.month_fee,c.data,c.voice_time,c.sim_type ");
		sb=getConditionHql(conpanyId, sb,conditionMap);
		sb.append(" order by c.combo_id desc");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	 private StringBuffer getConditionHql(Long conpanyId, StringBuffer sb,Map conditionMap){
		 	sb.append(" FROM t_fee_sim_combo c where c.flag = 1 ");
		 	if(StringUtils.isNotNullOrEmpty(conditionMap.get("name"))){
				sb.append(" and c.combo_name like '%").append(conditionMap.get("name")).append("%'");
			}
		 	if(StringUtils.isNotNullOrEmpty(conditionMap.get("type"))){
				sb.append(" and c.sim_type=").append(conditionMap.get("type"));
			}
		 	if(StringUtils.isNotNullOrEmpty(conditionMap.get("telco"))){
				sb.append(" and c.telco=").append(conditionMap.get("telco"));
			}
		 	if(StringUtils.isNotNullOrEmpty(conditionMap.get("code"))){
		 		sb.append(" and c.combo_code='").append(conditionMap.get("code")).append("'");
			}
		 	//	sb.append(" and c.subco_no=").append(conpanyId);
			return sb;
	    }

	@Override
	public int countComboes(Long conpanyId, Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(c.combo_id) ");
		sb=getConditionHql(conpanyId, sb,conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public int delete(List<Long> ids) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update Combo set flag=:st where combo_id in (:ids) ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		query.setParameter("st", 9);
		query.setParameterList("ids", ids); 
		return query.executeUpdate();
	}

	@Override
	public Combo getComboByname(String name) throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Combo.class); 
		criteria.add(Restrictions.eq("combo_name", name));
		List<Combo> list = criteria.list();
		if(list != null && list .size() >0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Combo> getComboList() throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Combo.class); 
		List<Combo> list = criteria.list();
		return list;
	}



}

