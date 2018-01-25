package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.StockSetupDao;
import com.gboss.pojo.Setup;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:StockSetupDaoImpl
 * @Description:库存设置数据持久层实现类
 * @author:zfy
 * @date:2013-8-29 下午3:22:52
 */
@Repository("stockSetupDao")  
@Transactional 
public class StockSetupDaoImpl extends BaseDaoImpl implements StockSetupDao {

	@Override
	public List<HashMap<String, Object>> findStocks(
			Map<String, Object> conditionMap, String order, boolean isDesc, int pageNo, int pageSize)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select new map(s.id as id,s.whsId as whsId,s.whsName as whsName,s.productId as productId,p.code as productCode,p.name as productName,p.norm as norm,s.minStock as minStock,s.overstockTime as overstockTime) ");
		sb.append(" from Setup as s ,Product as p");
		sb.append(" where s.productId=p.id ");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
				sb.append(" and s.whsId=").append(conditionMap.get("whsId"));
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
				sb.append(" and s.whsId in(:alist)");
			}
			if(conditionMap.get("productName")!=null){//商品名称
				sb.append(" and p.name like '%").append(StringUtils.replaceSqlKey(conditionMap.get("productName"))).append("%'");
			}
			if(conditionMap.get("minStock")!=null){//最小库存
				sb.append(" and s.minStock=").append(Integer.valueOf((String)conditionMap.get("minStock")));
			}
			if(conditionMap.get("overstockTime")!=null){//积压时长
				sb.append(" and s.overstockTime=").append(Integer.valueOf((String)conditionMap.get("overstockTime")));
			}
		}
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
		query.setMaxResults(pageSize);
		return query.list();
	}

	@Override
	public int countStocks(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select count(*) ");
		sb.append(" from Setup as s ,Product p");
		sb.append(" where s.productId=p.id ");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
				sb.append(" and s.whsId=").append(conditionMap.get("whsId"));
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
				sb.append(" and s.whsId in(:alist)");
			}
			if(conditionMap.get("productName")!=null){//商品名称
				sb.append(" and p.name like '%").append(StringUtils.replaceSqlKey(conditionMap.get("productName"))).append("%'");
			}
			if(conditionMap.get("minStock")!=null){//最小库存
				sb.append(" and s.minStock=").append(Integer.valueOf((String)conditionMap.get("minStock")));
			}
			if(conditionMap.get("overstockTime")!=null){//积压时长
				sb.append(" and s.overstockTime=").append(Integer.valueOf((String)conditionMap.get("overstockTime")));
			}
		}
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		return ((Long)query.uniqueResult()).intValue();
	}

	@Override
	public boolean checkProductInSetup(Setup setup) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id) from Setup as s");
		sb.append(" where 1=1 ");
		if(setup!=null){
			if(setup.getProductId()!=null){
				sb.append(" and s.productId=").append(setup.getProductId());
			}
			if(setup.getWhsId()!=null){//仓库id
				sb.append(" and s.whsId=").append(setup.getWhsId());
			}
			if(setup.getId()!=null){
				sb.append(" and s.id!=").append(setup.getId());
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
	public int deleteSetups(List<Long> idList) throws SystemException {
		String hql="delete Setup WHERE id IN (:alist)";  
		Query query = sessionFactory.getCurrentSession().createQuery(hql);  
		query.setParameterList("alist", idList); 
		return query.executeUpdate();
	}

	@Override
	public int updateSetups(List<Long> ids, Integer minStock,
			Integer overstockTime, Long userId) throws SystemException {
		StringBuffer packHqlSb=new StringBuffer();
		packHqlSb.append(" update Setup ");
		packHqlSb.append(" set userId=").append(userId);
		if(minStock!=null){
			packHqlSb.append(",minStock=").append(minStock);
		}
		if(minStock!=null){
			packHqlSb.append(",overstockTime=").append(overstockTime);
		}
		if(ids!=null&&!ids.isEmpty()){
			packHqlSb.append(" where id in (:ids)");
		}
		Query query = sessionFactory.getCurrentSession().createQuery(packHqlSb.toString());  
		if(ids!=null&&!ids.isEmpty()){
			query.setParameterList("ids", ids); 
		}
		return query.executeUpdate();
	}

}

