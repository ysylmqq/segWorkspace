package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.CheckDao;
import com.gboss.pojo.Check;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;
import com.gboss.util.page.PageUtil;
import com.mysql.jdbc.Util;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:CheckDaoImpl
 * @Description:盘点数据持久层实现类
 * @author:zfy
 * @date:2013-9-16 上午11:24:48
 */
@Repository("checkDao")  
@Transactional
public class CheckDaoImpl extends BaseDaoImpl implements CheckDao {

	@Override
	public List<HashMap<String, Object>> findCheckDetails(Map<String,Object> map)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select c.id as id,c.product_id as productId,p.code as productCode,p.name as productName,p.norm as norm,p.unit as unit");
		sb.append(" ,sum(c.last_save_num) as lastSaveNum, sum(c.cur_in_num) as curInNum,sum(c.cur_out_num) as curOutNum");
		sb.append(" ,sum(c.cur_save_num) as curSaveNum,sum(c.cur_object_num) as curObjectNum");
		sb.append(" ,sum(c.over_short_num) as overShortNum,sum(c.change_num) as changeNum,c.remark as remark");
		sb.append(" from t_whs_check_details as c ");
		sb.append(" left join t_sel_product p on p.id=c.product_id");
		sb.append(" left join t_whs_check cc on cc.id=c.check_id");
		sb.append(" where 1=1 ");
		if (map != null) {
			//如果上个月没有盘点，则取上一次的盘点记录
			//上一次盘点ID
			if (StringUtils.isNotNullOrEmpty(map.get("checkId"))) {
				sb.append(" and c.check_id=").append(map.get("checkId"));
			} else {
				sb.append(" and c.check_id in (");
				
				sb.append(" select id from t_whs_check where 1=1");
				if (StringUtils.isNotNullOrEmpty(map.get("companyId") )) {
					sb.append(" and company_id=").append(map.get("companyId"));
				}
				if (StringUtils.isNotNullOrEmpty(map.get("orgId") )) {
					sb.append(" and org_id=").append(map.get("orgId"));
				}
				 if (map.get("startDate") != null && StringUtils.isNotNullOrEmpty(map.get("startDate")) && map.get("endDate") != null && StringUtils.isNotNullOrEmpty(map.get("endDate"))) {
					sb.append(" and start_date >='").append(map.get("startDate")).append("'");
					sb.append(" and end_date<='").append(map.get("endDate")).append(" 23:59:59'");
				}
				if (map.get("yearMonth") != null) {
					sb.append(" and end_date like '").append(StringUtils.replaceSqlKey(map.get("yearMonth"))).append("%'");
				}
				
				sb.append(")");
			}
			if (map.get("overShortNum") != null) {
				sb.append(" and over_short_num!=").append(map.get("overShortNum"));
			}
			if (map.get("curObjectNumNull") != null) {
				sb.append(" and cur_object_num is null");
			}
			//账是否平
			if (map.get("flat") != null && StringUtils.isNotNullOrEmpty(map.get("flat"))) {
				if("1".equals((String)map.get("flat"))){//平的
					sb.append(" and c.cur_save_num=c.cur_object_num");
				}else if("2".equals((String)map.get("flat"))){//不平
					sb.append(" and c.cur_save_num<>c.cur_object_num");
				}
			}
			//商品名称
			if (map.get("productName") != null && StringUtils.isNotNullOrEmpty(map.get("productName"))) {
				sb.append(" and p.name like '%").append(StringUtils.replaceSqlKey(map.get("productName"))).append("%'");
			}
			if (map.get("wareHouseIds")!=null) {
				sb.append(" and cc.whs_id in(:alist)");
			}
		}
		sb.append(" group by c.product_id,c.id");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (map!=null && map.get("wareHouseIds")!=null) {
			query.setParameterList("alist", (List<Long>)map.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findStockInDetails(Map<String, Object> map)
			throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append("select new map(sid.productId as productId,p.code as productCode,p.name as productName" );
		hqlSb.append(",p.norm as norm,p.unit as unit,sum(sid.inNum) as inNum");
		if (map != null && map.get("isDetail")!=null) {//查明细
			hqlSb.append(",si.type as type");
		}
		hqlSb.append(")");
		hqlSb.append(" from StockinDetails as sid");
		hqlSb.append(" , Stockin as si, Product p");
		hqlSb.append(" where sid.stockinId=si.id and sid.productId=p.id");
		if (map != null) {
			if (StringUtils.isNotNullOrEmpty(map.get("wareHouseIds"))) {
				hqlSb.append(" and si.whsId in(:alist)");
			}
			if (map.get("yearMonth")!=null) {
				hqlSb.append(" and si.stamp like '").append(map.get("yearMonth")).append("%'");
			}
			 if (map.get("startDate") != null && StringUtils.isNotNullOrEmpty(map.get("startDate")) && map.get("endDate") != null && StringUtils.isNotNullOrEmpty(map.get("endDate"))) {
				hqlSb.append(" and si.stamp >='").append(map.get("startDate")).append("'");
				hqlSb.append(" and si.stamp<='").append(map.get("endDate")).append(" 23:59:59'");
			}
			/*//入库类型,5为归还
			if (map.get("stockInType")!=null) {
				hqlSb.append(" and type=").append(map.get("stockInType"));
			}*/
			
			hqlSb.append(" group by sid.productId,p.code");
			if (map != null && map.get("isDetail")!=null) {//查明细
				hqlSb.append(",si.type");
			}
		}
		Query query=sessionFactory.getCurrentSession().createQuery(hqlSb.toString());
		if (map!=null && map.get("wareHouseIds")!=null) {
			query.setParameterList("alist", (List<Long>)map.get("wareHouseIds"));
		}
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findStockOutDetails(Map<String, Object> map)
			throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append("select new map(sid.productId as productId,p.code as productCode,p.name as productName");
		hqlSb.append(",p.norm as norm,p.unit as unit,sum(sid.outNum) as outNum");
		if (map != null && map.get("isDetail")!=null) {//查明细
			hqlSb.append(",si.type as type");
		}
		hqlSb.append(")");
		hqlSb.append(" from StockoutDetails as sid");
		hqlSb.append(" , Stockout as si , Product p");
		hqlSb.append(" where sid.stockoutId=si.id and sid.productId=p.id");
		if (map != null) {
			if (StringUtils.isNotNullOrEmpty(map.get("wareHouseIds"))) {
				hqlSb.append(" and si.whsId in(:alist)");
			}
			
			if (map.get("yearMonth")!=null) {
				hqlSb.append(" and si.stamp like '").append(map.get("yearMonth")).append("%'");
			}
			
			 if (map.get("startDate") != null && StringUtils.isNotNullOrEmpty(map.get("startDate")) && map.get("endDate") != null && StringUtils.isNotNullOrEmpty(map.get("endDate"))) {
				hqlSb.append(" and si.stamp >='").append(map.get("startDate")).append("'");
				hqlSb.append(" and si.stamp<='").append(map.get("endDate")).append(" 23:59:59'");
			}
			/*//出库类型,0为销售（车行调剂）,1：借用，5：销售安装（直销），6：升级（直销）
			if (map.get("stockOutType")!=null) {
				hqlSb.append(" and type=").append(map.get("stockOutType"));
			}*/
			hqlSb.append(" group by sid.productId,p.code");
			if (map != null && map.get("isDetail")!=null) {//查明细
				hqlSb.append(",si.type");
			}
		}
		Query query=sessionFactory.getCurrentSession().createQuery(hqlSb.toString());
		if (map!=null && map.get("wareHouseIds")!=null) {
			query.setParameterList("alist", (List<Long>)map.get("wareHouseIds"));
		}
		return query.list();
	}

	@Override
	public Check getCheck(Map<String, Object> map)
			throws SystemException {
		StringBuffer hqlSb = new StringBuffer();
		hqlSb.append("from Check as c where 1=1");
		if (map != null) {
			if (StringUtils.isNotNullOrEmpty(map.get("id") )) {
				hqlSb.append(" and id=").append(map.get("id"));
			}
			if(map.get("name") != null){
				hqlSb.append(" and c.name='").append(map.get("name")).append("'");
			}
			if (StringUtils.isNotNullOrEmpty(map.get("companyId"))) {
				hqlSb.append(" and companyId=").append(map.get("companyId"));
			}
			if (StringUtils.isNotNullOrEmpty(map.get("orgId")) ) {
				hqlSb.append(" and orgId=").append(map.get("orgId"));
			}
		    if (map.get("startDate") != null && StringUtils.isNotBlank(StringUtils.clearNull(map.get("startDate"))) && map.get("endDate") != null && StringUtils.isNotBlank(StringUtils.clearNull(map.get("endDate")))) {
				//hqlSb.append(" and startDate >='").append(map.get("startDate")).append("'");
				//hqlSb.append(" and endDate<='").append(map.get("endDate")).append("'");
		    	hqlSb.append(" and startDate='").append(map.get("startDate")).append("'");
				hqlSb.append(" and endDate='").append(map.get("endDate")).append(" 23:59:59'");
			}
			if (map.get("yearMonth") != null) {
				hqlSb.append(" and endDate like '").append(StringUtils.replaceSqlKey(map.get("yearMonth"))).append("%'");
			}	
			if (map.get("wareHouseIds")!=null) {//仓库集合
				hqlSb.append(" and c.whsId in(:alist)");
			}
		}
		hqlSb.append(" order by endDate desc");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		if (map!=null && map.get("wareHouseIds")!=null) {
			query.setParameterList("alist", (List<Long>)map.get("wareHouseIds"));
		}
		List<Check> checkList = query.list();
		if (checkList != null && !checkList.isEmpty()) {
			return checkList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public int deleteDetailsByCheckId(Long checkId) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" delete CheckDetails ");
		sb.append(" where checkId=").append(checkId);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}

	@Override
	public boolean checkName(Check check) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id) from Check as s");
		sb.append(" where 1=1 ");
		if(check!=null){
			if(StringUtils.isNotBlank(check.getName())){
				sb.append(" and s.name='").append(check.getName()).append("'");
			}
			if(check.getCompanyId()!=null){
				sb.append(" and s.companyId=").append(check.getCompanyId());
			}
			if(check.getId()!=null){
				sb.append(" and s.id!=").append(check.getId());
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		if((Long)query.uniqueResult()>0){
			return true;
		}else{
			return false;
		}
	}
	 private StringBuffer getCondition(StringBuffer sb,Map conditionMap){
			sb.append(" from Check as c ");
			sb.append(" where 1=1 ");
			if(conditionMap!=null){
				if(conditionMap.get("name")!=null){//名称
					sb.append(" and c.name like '%").append(StringUtils.replaceSqlKey(conditionMap.get("name"))).append("%'");
				}
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
					sb.append(" and c.whsId in(:alist)");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){
					sb.append(" and c.whsId=").append(conditionMap.get("whsId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//分公司id
					sb.append(" and c.companyId=").append(conditionMap.get("companyId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("orgId"))){//机构id
					sb.append(" and c.orgId=").append(conditionMap.get("orgId"));
				}
				if(conditionMap.get("status")!=null){//状态
					sb.append(" and c.status=").append(conditionMap.get("status"));
				}
				if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
					sb.append(" and c.stamp >='").append(conditionMap.get("startDate")+" 00:00:00").append("'");
				}
				if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
					sb.append(" and c.stamp <='").append(conditionMap.get("endDate")+" 23:59:59").append("'");
				}
			}
			return sb;
		 }

	@Override
	public List<HashMap<String, Object>> findChecks(Map<String, Object> map,
			String order, boolean isDesc, int pageNo, int pageSize)
			throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select new map(");
		hqlSb.append(" c.id as id,c.name as name,c.whsId as whsId,'' as whsName,c.startDate as startDate");
		hqlSb.append(" ,c.endDate as endDate,c.status as status,c.userId as userId,'' as userName,c.stamp as stamp");
		hqlSb.append(" )");
		hqlSb=getCondition(hqlSb,map);
		if (StringUtils.isNotBlank(order)) {
			hqlSb.append(" order by ").append(order);
			if (isDesc) {
				hqlSb.append(" desc");
			} else {
				hqlSb.append(" asc");
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());
		if (map.get("wareHouseIds")!=null) {
			query.setParameterList("alist", (List<Long>)map.get("wareHouseIds"));
		}
		if (pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countChecks(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select count(*) ");
		hqlSb=getCondition(hqlSb,conditionMap);
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString()); 
		if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		return ((Long)query.uniqueResult()).intValue();
	}

	@Override
	public boolean checkStatus(Long companyId, List<Long> wareHouseIds, Integer status,
			Boolean isEqlStatus) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id) from Check as s");
		sb.append(" where 1=1 ");
		if(StringUtils.isNotNullOrEmpty(companyId)){
			sb.append(" and s.companyId=").append(companyId);
		}
		if(StringUtils.isNotNullOrEmpty(wareHouseIds)){
			sb.append(" and s.whsId in(:alist)");
		}
		if(status!=null){
			if(isEqlStatus!=null && isEqlStatus.booleanValue()){
				sb.append(" and s.status='").append(status).append("'");
			}else{
				sb.append(" and s.status!='").append(status).append("'");
			}	
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		if (StringUtils.isNotNullOrEmpty(wareHouseIds)) {
			query.setParameterList("alist", wareHouseIds);
		}
		if((Long)query.uniqueResult()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public int updateChangeNumByCheckId(Long checkId) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" update CheckDetails set changeNum=0");
		sb.append(" where checkId=").append(checkId);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}

	@Override
	public List<HashMap<String, Object>> findCheckDetails4Reprot(
			Map<String, Object> map) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select cd.product_id as productId,p.code as productCode,p.name as productName,p.norm as norm,p.unit as unit");
		sb.append(" ,sum(cd.last_save_num) as lastSaveNum, sum(cd.cur_in_num) as curInNum,sum(cd.cur_out_num) as curOutNum");
		sb.append(" ,sum(cd.cur_save_num) as curSaveNum,sum(cd.cur_object_num) as curObjectNum");
		sb.append(" ,sum(cd.over_short_num) as overShortNum,sum(cd.change_num) as changeNum");
		sb.append(" from t_whs_check_details as cd ");
		sb.append(" left join t_sel_product p on p.id=cd.product_id");
		sb.append(" where cd.check_id in");
		sb.append(" (select id from t_whs_check c where not exists (select 1 from t_whs_check c2 where c2.whs_id=c.whs_id and c.stamp<c2.stamp");
		if (map != null) {
			if (StringUtils.isNotNullOrEmpty(map.get("companyId"))) {
				sb.append(" and c2.company_id=").append(map.get("companyId"));
			}
			if (StringUtils.isNotNullOrEmpty(map.get("whsId"))) {
				sb.append(" and c2.whs_id=").append(map.get("whsId"));
			}
			if (StringUtils.isNotNullOrEmpty(map.get("wareHouseIds"))) {
				sb.append(" and c2.whs_id in(:alist)");
			}
		}
		sb.append(" )");
		if (map != null) {
			if (StringUtils.isNotNullOrEmpty(map.get("companyId"))) {
				sb.append(" and c.company_id=").append(map.get("companyId"));
			}
			if (StringUtils.isNotNullOrEmpty(map.get("whsId"))) {
				sb.append(" and c.whs_id=").append(map.get("whsId"));
			}
			if (StringUtils.isNotNullOrEmpty(map.get("wareHouseIds"))) {
				sb.append(" and c.whs_id in(:alist)");
			}
		}
		sb.append(" )");
		if (map != null) {
			//商品id
			if (StringUtils.isNotNullOrEmpty(map.get("productId"))) {
				sb.append(" and cd.product_id=").append(map.get("productId"));
			}
		}
		sb.append(" group by cd.product_id");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (map!=null && map.get("wareHouseIds")!=null) {
			query.setParameterList("alist", (List<Long>)map.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	
}

