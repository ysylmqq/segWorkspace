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
import com.gboss.dao.MealDao;
import com.gboss.pojo.Combo;
import com.gboss.pojo.Meal;
import com.gboss.pojo.Model;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:ComboDaoImpl
 * @Description:私家车入网获取套餐名称
 * @author:hansm
 * @date:2016-6-08 下午3:20:13
 */
@Repository("mealDao")  
@Transactional  
public class MealDaoImpl extends BaseDaoImpl implements MealDao {

	@Override
	public boolean isExist(Meal meal)throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.combo_id) from t_fee_sim_combo as s");
		sb.append(" where 1=1 ");
		if(meal!=null){
			if(StringUtils.isNotBlank(meal.getCombo_name())){
				sb.append(" and s.combo_name='").append(meal.getCombo_name()).append("'");
			}
			if(meal.getCombo_id()!=null){
				sb.append(" and s.combo_id <> ").append(meal.getCombo_id());
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
	public List<Meal> getMealList() throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Meal.class); 
		List<Meal> list = criteria.list();
		return list;
	}

	 private StringBuffer getConditionHql(Long companyId, StringBuffer sb,Map conditionMap){
		 	sb.append(" FROM t_fee_sim_combo c where c.flag = 1 ");
		 	//if(StringUtils.isNotNullOrEmpty(conditionMap.get("name"))){
			//	sb.append(" and c.combo_name like '%").append(conditionMap.get("name")).append("%'");
			//}
		 		sb.append(" and c.subco_no=").append(companyId);
			return sb;
	    }
	
	@Override
	public List<HashMap<String, Object>> findMeales(Long companyId,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select c.combo_id as id , c.subco_no ,c.combo_code ,c.combo_name,c.month_fee,c.data,c.voice_time,c.sim_type ");
		sb=getConditionHql(companyId, sb,conditionMap);
		sb.append(" order by c.combo_id desc");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

}

