package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.UnitTypeDao;
import com.gboss.pojo.Brand;
import com.gboss.pojo.SysValue;
import com.gboss.pojo.UnitType;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:UnitTypeDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-30 上午11:23:28
 */
@Repository("UnitTypeDao")  
@Transactional
public class UnitTypeDaoImpl extends BaseDaoImpl implements UnitTypeDao {

	@Override
	public Page<UnitType> findUnitType(PageSelect<UnitType> pageSelect) {
		String hql = "from UnitType "
				   + "where 1=1 ";
		Map filter = pageSelect.getFilter();
		Set<String> keys = filter.keySet();
		for(Iterator it=keys.iterator();it.hasNext();){
			String key = (String)it.next();
			 if (filter.get(key) instanceof Integer) {
				 Integer new_name = (Integer) filter.get(key);
				 hql += " and " + key + "=" + new_name ;
			}else if (filter.get(key) instanceof String) {
				String value = (String)filter.get(key);
				hql += " and " + key + " like '%" + value + "%' ";
			}
			
		}
		if(StringUtils.isNotBlank(pageSelect.getStart_date())){
			hql += " and stamp > '" + pageSelect.getStart_date() + "'";
		}
		if(StringUtils.isNotBlank(pageSelect.getEnd_date())){
			hql += " and stamp < '" + pageSelect.getEnd_date() + "'";
		}
		if(StringUtils.isNotBlank(pageSelect.getOrder())){
			hql += " order by " + pageSelect.getOrder();
			if(pageSelect.getIs_desc()){
				hql += " desc ";
			}else{
				hql += " asc ";
			}
		}
		List list = listAll(hql, pageSelect.getPageNo(), pageSelect.getPageSize());
		int count = countAll(hql);
		Page<UnitType> page = PageUtil.getPage(count, pageSelect.getPageNo(), list, pageSelect.getPageSize());
		return page;
	}

	@Override
	public Boolean isExist(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UnitType.class); 
		criteria.add(Restrictions.eq("unittype", name));
		if(criteria.list().size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<SysValue> getSysValueList(Integer typeId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SysValue.class); 
		criteria.add(Restrictions.eq("stype", typeId));
		List<SysValue> sysValueList =  criteria.list();
		return sysValueList;
	}

	@Override
	public int countUnitTypes(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(t.unittype_id) ");
		sb=getConditionBy(sb, conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public List<HashMap<String, Object>> findUnitTypes(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select t.unittype_id as id,t.memo as memo, t.unittype as unittype, v.sname as sname, t.typeclass as typeclass,t.protocol_id as protocol_id ");
		sb=getConditionBy(sb,conditionMap);
		sb.append(" order by unittype_id desc");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	
	
	 
	 private StringBuffer getConditionBy(StringBuffer sb,Map conditionMap){
			sb.append(" FROM t_ba_unittype t inner JOIN t_sys_value v ON t.protocol_id = v.svalue ");
			sb.append(" and v.stype =").append(SystemConst.CAR_TYPE);
			
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("name"))){
					sb.append(" and t.unittype like '%").append(conditionMap.get("name")).append("%'");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("type"))){
					sb.append(" and t.protocol_id = ").append(conditionMap.get("type"));
				}
			}
			return sb;
	    }

	@Override
	public Boolean isExist(String name, Long id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UnitType.class); 
		criteria.add(Restrictions.eq("unittype", name));
		criteria.add(Restrictions.not(Restrictions.eq("unittype_id", id)));
		if(criteria.list().size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public String getUnittypeByid(Long id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UnitType.class);
		criteria.add(Restrictions.eq("unittype_id", id));
		if(criteria.list().size() > 0){
			UnitType unittype = (UnitType)criteria.list().get(0);
			return unittype.getUnittype();
		}
		return null;
	}

	

}

