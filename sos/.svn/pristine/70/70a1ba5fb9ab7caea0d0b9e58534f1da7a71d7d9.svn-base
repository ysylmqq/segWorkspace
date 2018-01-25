package com.gboss.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.StockDao;
import com.gboss.pojo.BorrowDetails;
import com.gboss.pojo.Stock;
import com.gboss.pojo.Stockin;
import com.gboss.pojo.Stockout;
import com.gboss.pojo.Writeoffs;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:StockDaoImpl
 * @Description:库存数据持久层实现类
 * @author:zfy
 * @date:2013-8-30 上午11:18:08
 */
@Repository("stockDao")  
@Transactional 
public class StockDaoImpl extends BaseDaoImpl implements StockDao {

	@Override
	public List<HashMap<String, Object>> findCurrentStocks(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select stock.*,p.code as productCode,p.name as productName,p.norm as norm");
		sb.append(" from (");
		sb.append(" select s.id as id,s.whs_id as whsId,s.whs_name as whsName,s.product_id as productId,s.num as num,s.user_id as userId,s.stamp as stamp,s.remark as remark");
		sb = getCondition4CurrentStock(sb, conditionMap);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		sb.append(" ) as stock");
		sb.append(" left join t_sel_product p on stock.productId=p.id");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findStockLtSetup(
			Map<String, Object> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select setup.*,p.code as productCode,p.name as productName,p.norm as norm");
		sb.append(" from (");
		sb.append(" select s.product_id as productId");
		sb.append(" ,s.min_stock as minStock");
		sb.append(" ,sk.num as num,s.whs_id as whsId,s.whs_name as whsName,s.overstock_time as overstockTime");
		sb.append(" ,sk.stamp as stamp");
		sb.append(" from t_whs_setup as s left join t_whs_stock as sk on  s.product_id=sk.product_id and sk.whs_id=s.whs_id ");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
				sb.append(" and sk.whs_id=").append(conditionMap.get("whsId"));
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
				sb.append(" and sk.whs_id in(:alist)");
			}
		}
		sb.append(" where 1=1 ");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
				sb.append(" and s.whs_id=").append(conditionMap.get("whsId"));
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
				sb.append(" and s.whs_id in(:alist)");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){//商品
				sb.append(" and s.product_id=").append(conditionMap.get("productId"));
			}
		}
		sb.append(" and (sk.num<s.min_stock or sk.id is null)");//库存数量小于最小库存
		sb.append(") as setup");
		sb.append(" left join t_sel_product p on setup.productId=p.id");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public Stock getStockByWhsAndProduct(Stock stock)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" from Stock as s");
		sb.append(" where 1=1 ");
		if(stock!=null){
			if(stock.getWhsId()!=null){
				sb.append(" and s.whsId=").append(stock.getWhsId());
			}
			if(stock.getProductId()!=null){
				sb.append(" and s.productId=").append(stock.getProductId());
			}
			if(stock.getId()!=null){
				sb.append(" and s.id<>").append(stock.getId());
			}
			/*if(StringUtils.isNotBlank(stock.getProductName())){
				sb.append(" and s.productName='").append(stock.getProductName()).append("'");
			}*/
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		List<Stock> stocks=query.list();
		if(stocks!=null&&!stocks.isEmpty()){
			return stocks.get(0);
		}else{
			return null;
		}
	}

	@Override
	public int updateStockNum(Stock stock) throws SystemException {
		if(stock!=null&&stock.getStamp()!=null&& stock.getId()!=null){
			StringBuffer sb=new StringBuffer();
			sb.append(" update Stock");
			sb.append(" set stamp='").append(stock.getStamp()).append("'");
			if(stock.getNum()!=null){
				sb.append(", num=").append(stock.getNum());
			}
			if(stock.getUserId()!=null){
				sb.append(", userId=").append(stock.getUserId());
			}
			sb.append(" where id=").append(stock.getId());
			Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
			return query.executeUpdate();
		}else{
			return 0;
		}
		
	}

	@Override
	public List<HashMap<String, Object>> findStockInOutDetailsPage(
			Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select stocko.*");
		sb.append(" ,p.code as productCode, p.name as productName, p.norm as norm");
		sb.append(" , (case when stocko.channelId is not null then c.name");
		sb.append(" when stocko.customerId is not null then bc.customer_name else '' end) as channelName");
		sb.append(" from (");
		sb.append(" select stock.*");
		sb.append(" from (");
		sb = getCondition4StockInOutPage(sb, conditionMap,false, pageNo, pageSize);
		sb.append(" ) as stock");
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append(" order by stock.stamp desc ");
		}
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		sb.append(" ) as stocko");
		sb.append(" left join t_sel_product p on p.id=stocko.productId" );
		sb.append(" left join t_sel_channel c on c.id=stocko.channelId");
		sb.append(" left join t_ba_customer bc on bc.customer_id=stocko.customerId" );
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		/*if (pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}*/
		return query.list();
	}

	@Override
	public int countStockInOutDetails(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select sum(stock.cid) from (");
		sb = getCondition4StockInOutPage(sb, conditionMap,true,  0,  0);
		sb.append(" ) as stock");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		BigDecimal count=(BigDecimal) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public List<HashMap<String, Object>> findStockInDetails3(
			Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select stock.*");
		sb.append(" ,p.code as productCode, p.name as productName, p.norm as norm,p.unit as unit");
		sb.append(" from (");
		sb.append(" select sid.product_id as productId,sid.in_num as inNum,sid.price as price,sid.remark as remark,date_format(si.stamp,'%Y-%m-%d') as stamp,si.code as code,si.whs_name as whsName ");
		sb.append(" from t_whs_stockin_details as sid" );
		sb.append(" left join t_whs_stockin as si on si.id=sid.stockin_id" );
		sb.append(" where 1=1");
		sb=getCondition4StockInDetails3(sb,conditionMap);
		sb.append(" order by si.stamp desc ");
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		sb.append(" ) as stock");
		sb.append(" left join t_sel_product p on p.id=stock.productId" );
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	 private StringBuffer getCondition4StockInDetails3(StringBuffer sb,Map conditionMap){
		 if(conditionMap!=null){
		    	if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
					sb.append(" and si.whs_id=").append(conditionMap.get("whsId"));
				}
		    	if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//仓库id
					sb.append(" and si.company_id=").append(conditionMap.get("companyId"));
				}
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
					sb.append(" and si.whs_id in(:alist)");
				}
				if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
					sb.append(" and si.stamp >='").append(conditionMap.get("startDate")).append("'");
				}
				if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
					sb.append(" and si.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
				}
				if(conditionMap.get("yearMonth")!=null){//年月,格式 如 "2013-09"
					sb.append(" and si.stamp like '").append(conditionMap.get("yearMonth")).append("%'");
				}
				if(conditionMap.get("type")!=null){//入库类型
					sb.append(" and si.type=").append(conditionMap.get("type"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){//商品id
					sb.append(" and sid.product_id=").append(conditionMap.get("productId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("borrowId"))){//借用挂账明细id
					sb.append(" and sid.borrow_id=").append(conditionMap.get("borrowId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("orderId")) ){//采购单号
					sb.append(" and si.order_id=").append(conditionMap.get("orderId"));
				}
		 }
		 return sb;
	}
	 
	@Override
	public List<HashMap<String, Object>> findAllProducts(
			Map<String, Object> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select new map(s.productId as productId,p.code as productCode,p.name as productName,p.norm as norm) ");
		sb = getCondition4CurrentStock(sb,conditionMap);
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findStockIns(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select si.id as id,si.whs_id as whsId,si.whs_name as whsName");
		sb.append(" ,date_format(si.stamp,'%Y-%m-%d') as stamp,si.type as type");
		sb.append(" ,si.code as code,si.order_id as orderId,si.out_whs_name as outWhsName");
		sb.append(" ,(case when si.channel_id is not null then (select name from t_sel_channel where id=si.channel_id) else '' end) as channelName");
		sb.append(" ,si.managers_id as managersId,si.managers_name as managersName,si.remark as remark");
		sb.append(" ,(case when si.order_id is not null then (select order_no from t_whs_order where id=si.order_id) else '' end) as orderNo");
		sb.append(" ,(case when si.stockout_id is not null then (select code from t_whs_stockout s where s.id=si.stockout_id) else '' end) as stockoutCode");
		sb.append(" from t_whs_stockin as si " );
		sb.append(" where 1=1");
		sb=getCondition4StockIn(sb, conditionMap);
	    if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		sb.append(" order by si.stamp desc ");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countStockIns(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select count(*)");
		sb.append(" from t_whs_stockin as si " );
		sb.append(" where 1=1");
		sb=getCondition4StockIn(sb, conditionMap);
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public List<HashMap<String, Object>> findStockOuts(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select si.id as id,si.whs_id as whsId,si.whs_name as whsName,si.is_completed as isCompleted");
		sb.append(" ,date_format(si.stamp,'%Y-%m-%d') as stamp,si.type as type,si.user_id as userId,si.user_name as userName");
		sb.append(" ,si.code as code,si.channel_id as channelId,si.in_whs_id as inWhsId,si.in_whs_name as inWhsName");
		sb.append(" ,si.managers_id as managersId,si.managers_name as managersName,si.remark as remark");
		sb.append(" ,(case when si.type =2 then (select customer_name from t_ba_customer where id=si.customer_id)");//升级
		sb.append(" when si.type =0 then (select name from t_sel_channel  where id=si.channel_id)");//代理销售
		sb.append(" else '' end)");
		sb.append(" as channelName");
		sb.append(" ,si.money as money,si.receipt_no as receiptNo");
		sb.append(" from t_whs_stockout as si " );
		sb.append(" where 1=1");
		sb=getCondition4StockOut(sb, conditionMap);
	    if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		sb.append(" order by si.stamp desc ");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countStockOuts(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(*)");
		sb.append(" from t_whs_stockout as si " );
		sb.append(" where 1=1");
		sb=getCondition4StockOut(sb, conditionMap);
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	 private StringBuffer getCondition4StockOut(StringBuffer sb,Map conditionMap){
		 if(conditionMap!=null){
		    	if(StringUtils.isNotNullOrEmpty(conditionMap.get("type"))){//入库类型
					sb.append(" and si.type=").append(conditionMap.get("type"));
				}
		    	if(StringUtils.isNotNullOrEmpty(conditionMap.get("isCompleted"))){//是否调拨完成
					sb.append(" and si.is_completed=").append(conditionMap.get("isCompleted"));
				}
		    	if(conditionMap.get("code")!=null){//出库单号
					sb.append(" and si.code like '%").append(StringUtils.replaceSqlKey(conditionMap.get("code"))).append("%'");
				}
		    	if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//分公司id
					sb.append(" and si.company_id=").append(conditionMap.get("companyId"));
				}
		    	if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
					sb.append(" and si.whs_id=").append(conditionMap.get("whsId"));
				}
		    	if(StringUtils.isNotNullOrEmpty(conditionMap.get("inWhsId"))){//调入仓库id
					sb.append(" and si.in_whs_id=").append(conditionMap.get("inWhsId"));
				}
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
					sb.append(" and si.whs_id in(:alist)");
				}
				if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
					sb.append(" and si.stamp >='").append(conditionMap.get("startDate")).append("'");
				}
				if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
					sb.append(" and si.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("channelId"))){//代理商id
					sb.append(" and si.channel_id=").append(conditionMap.get("channelId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("customerId"))){//代理商id
					sb.append(" and si.customer_id=").append(conditionMap.get("customerId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("managersId"))){//经办人id
					sb.append(" and si.managers_id=").append(conditionMap.get("managersId"));
				}
			}
		 return sb;
	}
	 
	 private StringBuffer getCondition4StockIn(StringBuffer sb,Map conditionMap){
		 if(conditionMap!=null){
		    	if(conditionMap.get("type")!=null){//入库类型
					sb.append(" and si.type=").append(conditionMap.get("type"));
				}
		    	if(conditionMap.get("code")!=null){//单号
					sb.append(" and si.code like '%").append(conditionMap.get("code")).append("%'");
				}
		    	if(StringUtils.isNotNullOrEmpty(conditionMap.get("orderId"))){//订单id
					sb.append(" and si.order_id=").append(conditionMap.get("orderId"));
				}
		    	if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//分公司id
					sb.append(" and si.company_id=").append(conditionMap.get("companyId"));
				}
		    	if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
					sb.append(" and si.whs_id=").append(conditionMap.get("whsId"));
				}
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
					sb.append(" and si.whs_id in(:alist)");
				}
				if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
					sb.append(" and si.stamp >='").append(conditionMap.get("startDate")).append("'");
				}
				if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
					sb.append(" and si.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("channelId"))){//代理商id
					sb.append(" and si.channel_id=").append(conditionMap.get("channelId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("managersId"))){//经办人id
					sb.append(" and si.managers_id=").append(conditionMap.get("managersId"));
				}
			}
		 return sb;
	}
	
	@Override
	public List<HashMap<String, Object>> findStockInDetails4(
			Map<String, Object> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select p.code as productCode,p.name as productName,p.norm as norm");
		sb.append(" ,sid.cur_num as curNum,sid.in_num as inNum,sid.price as price,sid.remark as remark");
		sb.append(" from t_whs_stockin_details as sid " );
		sb.append(" left join t_sel_product p on p.id=sid.product_id" );
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("stockinId"))){//入库id
				sb.append(" and sid.stockin_id=").append(conditionMap.get("stockinId"));
			}
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findStockOutDetails4(
			Map<String, Object> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select p.code as productCode,p.name as productName,p.norm as norm");
		sb.append(" ,sid.id as id,sid.product_id as productId,sid.cur_num as curNum,sid.out_num as outNum,sid.allocated_num as allocatedNum,sid.price as price");
		sb.append(" ,sid.money as money,sid.remark as remark");
		sb.append(" from t_whs_stockout_details as sid " );
		sb.append(" left join t_sel_product p on p.id=sid.product_id" );
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("stockoutId"))){//出库id
				sb.append(" and sid.stockout_id=").append(conditionMap.get("stockoutId"));
			}
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}


	@Override
	public List<BorrowDetails> getBorrowByWhsAndProduct(
			BorrowDetails borrowDetails) throws SystemException {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(BorrowDetails.class);
		if(borrowDetails!=null){
			if(borrowDetails.getBorrowerId()!=null){
				criteria.add(Restrictions.eq("borrowerId", borrowDetails.getBorrowerId()));
			}
			if(borrowDetails.getProductId()!=null){
				criteria.add(Restrictions.eq("productId", borrowDetails.getProductId()));
			}
		}
		List<BorrowDetails> borrows=criteria.list();
		return borrows;
	}

	@Override
	public int countCurrentStocks(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(*)");
		sb = getCondition4CurrentStock(sb, conditionMap);
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		return ((Long)query.uniqueResult()).intValue();
	}
	
	 private StringBuffer getCondition4CurrentStock(StringBuffer sb,Map conditionMap){
		sb.append(" from t_whs_stock as s ");
		sb.append(" where 1=1 ");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
				sb.append(" and s.whs_id=").append(conditionMap.get("whsId"));
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
				sb.append(" and s.whs_id in(:alist)");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){//商品id
				sb.append(" and s.product_id=").append(conditionMap.get("productId"));
			}
		}
		return sb;
	 } 
	 
	 private StringBuffer getCondition4StockInOutPage(StringBuffer sb,Map conditionMap,boolean isCount,int pageNo,int pageSize){
		 	sb.append(" ( select ");
			if(isCount){//查询总数量
				sb.append(" count(sid.id) as cid");
			}else{
				sb.append(" si.code as stockCode,sid.id as id,si.whs_id as whsId,si.whs_name as whsName,sid.product_id as productId");
				sb.append(" ,(case when si.type =0 then '代理销售出库' ");
				sb.append(" when si.type =1 then '直销出库' ");
				sb.append(" when si.type =2 then '升级出库' ");
				sb.append(" when si.type =3 then '借用出库' ");
				sb.append(" when si.type =4 then '调拨出库' ");
				sb.append(" when si.type =5 then '其他出库' ");
				sb.append(" when si.type =9 then '调账出库' ");
				sb.append(" else  '出库' end)");
				sb.append(" as type");
				sb.append(" ,-sid.out_num as inOutNum,(sid.cur_num-sid.out_num) as surplusNum,si.managers_name as managersName,sid.remark as remark,si.user_name as userName,date_format(si.stamp,'%Y-%m-%d') as stamp");
				sb.append(" ,si.customer_id as customerId,si.channel_id as channelId");
			}
		 	sb.append(" from t_whs_stockout as si ");
			sb.append(" right join t_whs_stockout_details as sid on  si.id=sid.stockout_id ");
			sb.append(" where 1=1");
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("managersId"))){//经办人id
					sb.append(" and si.managers_id=").append(conditionMap.get("managersId"));
				}
				if(conditionMap.get("stockCode")!=null){//出库单号
					sb.append(" and si.code like '%").append(StringUtils.replaceSqlKey(conditionMap.get("stockCode"))).append("%'");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
					sb.append(" and si.whs_id=").append(conditionMap.get("whsId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//分公司id
					sb.append(" and si.company_id=").append(conditionMap.get("companyId"));
				}
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
					sb.append(" and si.whs_id in(:alist)");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("channelId"))){//代理商id
					sb.append(" and si.channel_id=").append(conditionMap.get("channelId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){//商品id
					sb.append(" and sid.product_id=").append(conditionMap.get("productId"));
				}
				if(conditionMap.get("otype")!=null){//出库类型
					sb.append(" and si.type in (").append(conditionMap.get("otype")).append(")");
				}
				if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
					sb.append(" and si.stamp >='").append(conditionMap.get("startDate")).append("'");
				}
				if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
					sb.append(" and si.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
				}
				/*if(conditionMap.get("productName")!=null){//商品名称
					sb.append(" and p.name like '%").append(conditionMap.get("productName")).append("%'");
				}*/
			}
			/*if (pageNo>0 && pageSize>0) {
				sb.append("order by si.stamp desc");
				sb.append(" limit ");
				sb.append(PageUtil.getPageStart(pageNo, pageSize/2));
				sb.append(",");
				sb.append(pageSize/2);
			}*/
			sb.append(" ) union ");
			sb.append(" (select ");
			if(isCount){//查询总数量
				sb.append(" count(sid.id) as cid");
			}else{
				sb.append(" si.code as stockCode,sid.id as id,si.whs_id as whsId,si.whs_name as whsName,sid.product_id");
				sb.append(" ,(case when si.type =0 then '采购入库'"); 
				sb.append(" when si.type =1 then '调拨入库'"); 
				sb.append(" when si.type =2 then '归还入库'"); 
				sb.append(" when si.type =3 then '其他入库' ");
				sb.append(" when si.type =4 then '报废入库' ");
				sb.append(" when si.type =5 then '退货入库' ");
				sb.append(" when si.type =6 then '回收入库' ");
				sb.append(" when si.type =9 then '调账入库' ");
				sb.append(" else  '入库' end)");
				sb.append(" as type");
				sb.append(",sid.in_num as inOutNum,(sid.cur_num+sid.in_num) as surplusNum,si.managers_name as managersName,sid.remark as remark,si.user_name as userName,date_format(si.stamp,'%Y-%m-%d') as stamp");
				sb.append(" ,'' as customer_id,si.channel_id");
			}
			sb.append(" from t_whs_stockin as si " );
			sb.append(" right join t_whs_stockin_details as sid on si.id=sid.stockin_id" );
			
			sb.append(" where 1=1");
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("managersId"))){//经办人id
					sb.append(" and si.managers_id=").append(conditionMap.get("managersId"));
				}
				if(conditionMap.get("stockCode")!=null){//出库单号
					sb.append(" and si.code like '%").append(StringUtils.replaceSqlKey(conditionMap.get("stockCode"))).append("%'");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
					sb.append(" and si.whs_id=").append(conditionMap.get("whsId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//分公司id
					sb.append(" and si.company_id=").append(conditionMap.get("companyId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("channelId"))){//代理商id
					sb.append(" and si.channel_id=").append(conditionMap.get("channelId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){//商品id
					sb.append(" and sid.product_id=").append(conditionMap.get("productId"));
				}
				if(conditionMap.get("itype")!=null){//入库类型
					sb.append(" and si.type=").append(conditionMap.get("itype"));
				}
				if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString()) && !"null".equals(conditionMap.get("startDate"))){//开始日期
					sb.append(" and si.stamp >='").append(conditionMap.get("startDate")).append("'");
				}
				if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
					sb.append(" and si.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
				}
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
					sb.append(" and si.whs_id in(:alist)");
				}
				/*if(conditionMap.get("productName")!=null){//商品名称
					sb.append(" and p.name like '%").append(conditionMap.get("productName")).append("%'");
				}*/
			}
		/*	if (pageNo>0 && pageSize>0) {
				sb.append("order by si.stamp desc");
				sb.append(" limit ");
				sb.append(PageUtil.getPageStart(pageNo, pageSize/2));
				sb.append(",");
				sb.append(pageSize/2);
			}*/
			sb.append(" )");
			return sb;
	 }

	@Override
	public List<HashMap<String, Object>> findStockOverTime(
			Map<String, Object> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select setup.*,p.code as productCode,p.name as productName,p.norm as norm");
		sb.append(" from (");
		sb.append(" select s.product_id as productId,st.min_stock as minStock,s.num,s.whs_id as whsId,s.whs_name as whsName,st.overstock_time as overstockTime,date_format(so.stamp,'%Y-%m-%d') stamp ");
		sb.append(" from t_whs_stock as s  left join t_whs_stockout as so on s.whs_id = so.whs_id");
		sb.append(" inner join t_whs_stockout_details as sod on so.id= sod.stockout_id and s.product_id=sod.product_id");
		sb.append(" inner join t_whs_setup as st on s.whs_id= st.whs_id and st.product_id=sod.product_id");
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
				sb.append(" and so.whs_id=").append(conditionMap.get("whsId"));
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds")) && !((List<Long>)conditionMap.get("wareHouseIds")).isEmpty()) {//仓库集合
				sb.append(" and so.whs_id in(:alist)");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){//商品id
				sb.append(" and s.product_id=").append(conditionMap.get("productId"));
			}
		}
		//查询某仓库的某商品最后一次出库时间
		sb.append(" and so.stamp= (select stamp from vi_last_stockout as v where 1=1");
		if(conditionMap!=null){
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds")) && !((List<Long>)conditionMap.get("wareHouseIds")).isEmpty()) {//仓库集合
				sb.append(" and v.whs_id in(:alist)");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){//商品id
				sb.append(" and v.product_id=").append(conditionMap.get("productId"));
			}
		}
		sb.append(" and so.whs_id=v.whs_id and sod.product_id=v.product_id)");
		sb.append(" and s.num>0 and datediff(now(),so.stamp)>st.overstock_time*30");
		sb.append(" ) as setup");
		sb.append(" left join t_sel_product p on setup.productId= p.id");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<Map<String, Object>> findBorrows(Map<String,Object> conditionMap)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select bo.*,p.code as productCode,p.name as productName,p.norm as norm");
		sb.append(" ,(case when bo.channelId is not null then (select name from t_sel_channel c where c.id=bo.channelId) else '' end) as channelName");
		sb.append(" ,(case when bo.customerId is not null then (select bc.customer_name from t_ba_customer bc where bc.customer_id=bo.customerId) else '' end) as customerName");
		sb.append(" from (");
		sb.append(" select");
		//用remark表示是否只查询明细,1表示只查询出明细,主要为核销用
		if(conditionMap!=null && conditionMap.get("remark")!=null && StringUtils.isNotBlank(conditionMap.get("remark").toString()) && "1".equals(conditionMap.get("remark").toString())){
			sb.append(" b.id as id,date_format(b.stamp,'%Y-%m-%d') as stamp,b.price as price,b.remark as remark,");
			//合同、类型
			sb.append(" b.contract_id as contractId,(select name from t_sel_salescontract sc where sc.id=b.contract_id) as contractName,b.type as type,");
		}
		sb.append(" b.product_id as productId");
		sb.append(" ,sum(b.num) as borrowNum,sum(b.writeoffs_num) as writeoffsNum,sum(b.writeoffs_num2) as writeoffsNum2,sum(b.return_num) as returnNum");
		sb.append(" ,sum(b.num)-sum(case when b.writeoffs_num is null then 0 else b.writeoffs_num end)-sum(case when b.writeoffs_num2 is null then 0 else b.writeoffs_num2 end)-sum(case when b.return_num is null then 0 else b.return_num end) as num");
		sb.append(" ,b.borrower_id as borrowerId,b.borrower_name as borrowerName,b.whs_id as whsId,b.whs_name as whsName,b.channel_id as channelId,b.customer_id as customerId");
		sb.append(" from t_whs_borrow_details as b ");
		sb.append(" where 1=1 ");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("borrowerId"))){
				sb.append(" and b.borrower_id=").append(conditionMap.get("borrowerId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){
				sb.append(" and b.product_id=").append(conditionMap.get("productId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("channelId"))){
				sb.append(" and b.channel_id=").append(conditionMap.get("channelId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){
				sb.append(" and b.company_id=").append(conditionMap.get("companyId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){
				sb.append(" and b.whs_id=").append(conditionMap.get("whsId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("orgId"))){
				sb.append(" and b.org_id=").append(conditionMap.get("orgId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("writeoffsStatus"))){
				sb.append(" and b.status=").append(conditionMap.get("writeoffsStatus"));
			}
			if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
				sb.append(" and b.stamp >='").append(conditionMap.get("startDate")).append("'");
			}
			if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
				sb.append(" and b.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
			}
		/*	if(conditionMap.get("type")!=null){
				sb.append(" and b.type=").append(conditionMap.get("type"));
			}*/
			if(conditionMap.get("type2")!=null){//判断网络进销存（个人）、集客进销存(代理商)
				 if("1".equals(conditionMap.get("type2").toString())) {//网络进销存明细
					sb.append(" and (b.borrower_id is not null and b.borrower_id<>'')");
				}else{
					sb.append(" and (b.channel_id is not null and b.channel_id<>'')");
				}
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
				sb.append(" and b.whs_id in(:alist)");
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("stockoutDetailsIds"))) {//出库明细ID集合
				sb.append(" and b.stockout_details_id in(:stockoutDetailsIds)");
			}
		}
		sb.append(" group by b.product_id,b.borrower_id,b.whs_id");
		//用remark表示是否只查询明细,1表示只查询出明细,主要为核销用
		if(conditionMap!=null && conditionMap.get("remark")!=null && StringUtils.isNotBlank(conditionMap.get("remark").toString()) && "1".equals(conditionMap.get("remark").toString())){
			sb.append(",b.id,b.stamp,b.price");
		}
		sb.append(" order by b.stamp desc");
		sb.append(" )as bo ");
		sb.append(" left join t_sel_product p on p.id=bo.productId");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null) {
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))){
				query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("stockoutDetailsIds"))){
				query.setParameterList("stockoutDetailsIds", (List<Long>)conditionMap.get("stockoutDetailsIds"));
			}
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int getMaxStockNo(Long whsId, String date,boolean isStockIn)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select max(substring(stock.code,length(stock.code)-3,4)) from");
		if(isStockIn){
			sb.append(" Stockin");
		}else{
			sb.append(" Stockout");
		}
		sb.append(" as stock");
		sb.append(" where 1=1");
		if(whsId!=null){
			sb.append(" and whsId=").append(whsId);
		}
		if(StringUtils.isNotBlank(date)){
			sb.append(" and stamp like'").append(date).append("%'");
		}
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		Object result=query.uniqueResult();
		if(result!=null){
			return Integer.valueOf(query.uniqueResult().toString());
		}else{
			return 0;
		}
	}

	@Override
	public int getMaxWriteoffsNo(Long whsId,Long orgId, String date, boolean isHx)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		if(isHx){//核销
			sb.append("select max(substring(w.writeoffs_no,length(w.writeoffs_no)-3,4)) from");
			sb.append(" t_whs_writeoffs_details as wd  left join t_whs_borrow_details as b on b.id=wd.borrow_id");
			sb.append(" left join t_whs_writeoffs as w on w.id=wd.writeoffs_id");
		}else{//销账
			sb.append("select max(substring(w.writeoff_no,length(w.writeoff_no)-3,4)) from");
			sb.append(" t_fee_writeoff_details as wd left join t_whs_borrow_details as b on b.id=wd.borrow_id");
			sb.append(" left join t_fee_writeoff as w on w.id=wd.writeoff_id");
		}
		sb.append(" where 1=1");
		/*if(StringUtils.isNotNullOrEmpty(whsId)){
			sb.append(" and b.whs_Id=").append(whsId);
		}*/
		if(StringUtils.isNotNullOrEmpty(orgId)){
			sb.append(" and w.org_id=").append(orgId);
		}
		if(StringUtils.isNotBlank(date)){
			sb.append(" and w.stamp like '").append(date).append("%'");
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		Object result=query.uniqueResult();
		if(result!=null){
			return Integer.valueOf(query.uniqueResult().toString());
		}else{
			return 0;
		}
	}

	@Override
	public boolean checkStockInCode(Stockin stockin) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id) from Stockin as s");
		sb.append(" where 1=1 ");
		if(stockin!=null){
			if(StringUtils.isNotBlank(stockin.getCode())){
				sb.append(" and s.code='").append(stockin.getCode()).append("'");
			}
			if(stockin.getCompanyId()!=null){
				sb.append(" and s.companyId=").append(stockin.getCompanyId());
			}
			if(stockin.getId()!=null){
				sb.append(" and s.id!=").append(stockin.getId());
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
	public boolean checkStockOutCode(Stockout stockout) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id) from Stockout as s");
		sb.append(" where 1=1 ");
		if(stockout!=null){
			if(StringUtils.isNotBlank(stockout.getCode())){
				sb.append(" and s.code='").append(stockout.getCode()).append("'");
			}
			if(stockout.getCompanyId()!=null){
				sb.append(" and s.companyId=").append(stockout.getCompanyId());
			}
			if(stockout.getId()!=null){
				sb.append(" and s.id!=").append(stockout.getId());
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
	public boolean checkWriteoffsCode(Writeoffs writeoffs)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id) from Writeoffs as s");
		sb.append(" where 1=1 ");
		if(writeoffs!=null){
			if(StringUtils.isNotBlank(writeoffs.getWriteoffsNo())){
				sb.append(" and s.writeoffsNo='").append(writeoffs.getWriteoffsNo()).append("'");
			}
			if(writeoffs.getCompanyId()!=null){
				sb.append(" and s.companyId=").append(writeoffs.getCompanyId());
			}
			if(writeoffs.getId()!=null){
				sb.append(" and s.id!=").append(writeoffs.getId());
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
	public int countStockInDetails3(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(*)");
		sb.append(" from t_whs_stockin_details as sid" );
		sb.append(" left join t_whs_stockin as si on si.id=sid.stockin_id" );
		sb.append(" where 1=1");
		sb=getCondition4StockInDetails3(sb, conditionMap);
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		return ((BigInteger)query.uniqueResult()).intValue();
		
	}

	@Override
	public Long getBorrowTotalNum(Long borrowerId,Long channelId,List<Long> whsIds) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select new map(sum(b.num)-sum(case when b.writeoffsNum is null then 0 else b.writeoffsNum end)-sum(case when b.returnNum is null then 0 else b.returnNum end) as num)");
		sb.append(" from BorrowDetails as b");
		sb.append(" where status=0");
		if(borrowerId!=null){
			sb.append(" and b.borrowerId=").append(borrowerId);
		}
		if(channelId!=null){
			sb.append(" and b.channelId=").append(channelId);
		}
		if (StringUtils.isNotNullOrEmpty(whsIds)) {//仓库集合
			sb.append(" and b.whsId in(:alist)");
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameterList("alist", whsIds); 
		List<Map<String, Object>> result=query.list();
		Long result2=0l;
		if(result!=null && !result.isEmpty()){
			Map<String, Object> rowData=result.get(0);
			if(rowData!=null){
				if(rowData.get("num")!=null){
					result2=Long.valueOf(rowData.get("num").toString());
				}
			}
		}
		return result2;
	}

	@Override
	public int deleteBorrowDetails(List<Long> idList) throws SystemException {
		String hql="delete BorrowDetails WHERE id IN (:alist)";  
		Query query = sessionFactory.getCurrentSession().createQuery(hql);  
		query.setParameterList("alist", idList); 
		return query.executeUpdate();
	}

	@Override
	public int deleteStocks(List<Long> idList) throws SystemException {
		String hql="delete Stock WHERE id IN (:alist)";  
		Query query = sessionFactory.getCurrentSession().createQuery(hql);  
		query.setParameterList("alist", idList); 
		return query.executeUpdate();
	}


	@Override
	public List<HashMap<String, Object>> findCurrentStocks2(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select stock.*,p.code as productCode,p.name as productName,p.norm as norm");
		sb.append(" from (");
		sb.append(" select sk.product_id as productId");
		sb.append(" ,sk.num as num,sk.whs_id as whsId,sk.whs_name as whsName");
		sb.append(" ,(select sum(b.num)-sum(case when b.writeoffs_num is null then 0 else b.writeoffs_num end)-sum(case when b.return_num is null then 0 else b.return_num end) from t_whs_borrow_details as b where b.status=0 and b.product_id=sk.product_id and b.whs_id=sk.whs_id and b.type=3) as borrowNum");//借用数量
		sb.append(" ,(select sum(b.num)-sum(case when b.writeoffs_num is null then 0 else b.writeoffs_num end)-sum(case when b.writeoffs_num2 is null then 0 else b.writeoffs_num2 end)-sum(case when b.return_num is null then 0 else b.return_num end) from t_whs_borrow_details as b where b.status=0 and b.product_id=sk.product_id and b.whs_id=sk.whs_id and b.type in(0,1,2)) as sellUnNum");//销售未还库的数量
		sb.append(" from t_whs_stock sk");
		sb.append(" where 1=1 ");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){//商品id
				sb.append(" and sk.product_id=").append(conditionMap.get("productId"));
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
				sb.append(" and sk.whs_id in(:alist)");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
				sb.append(" and sk.whs_id=").append(conditionMap.get("whsId"));
			}
		}
		sb.append(" group by sk.product_id,sk.whs_id");
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		sb.append(" ) as stock");
		sb.append(" left join t_sel_product p on stock.productId=p.id");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countCurrentStocks2(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select count(*)  ");
		sb.append(" from (");
		sb.append(" select id from t_whs_stock sk");
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){//商品id
				sb.append(" and sk.product_id=").append(conditionMap.get("productId"));
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
				sb.append(" and sk.whs_id in(:alist)");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
				sb.append(" and sk.whs_id=").append(conditionMap.get("whsId"));
			}
		}
		sb.append(" group by sk.product_id,sk.whs_id) as a");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public List<HashMap<String, Object>> findWriteoffs(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select s.id as id,s.writeoffs_no as writeoffsNo,date_format(s.stamp,'%Y-%m-%d') as stamp");
		sb.append(" ,s.receipt_no as receiptNo,s.people_money as peopleMoney,s.managers_name as managersName,s.remark as remark");
		sb.append(" from t_whs_writeoffs as s " );
		sb=getCondition4Writeoffs(sb, conditionMap);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append(" order by s.stamp desc ");
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findWriteoffsXz(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select s.id as id,s.writeoff_no as writeoffNo,date_format(s.stamp,'%Y-%m-%d') as stamp,c.name as channelName");
		sb.append(" ,s.off_amount as offAmount,s.last_balance as lastBalance,s.balance as balance,s.ticketno as ticketno,s.manager_name as managerName,s.remark as remark");
		sb.append(" from t_fee_writeoff as s" );
		sb.append(" left join t_sel_channel c on c.id=s.channel_id" );
		sb=getCondition4WriteoffsXz(sb, conditionMap);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append(" order by s.stamp desc ");
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findProductStock(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select stock.*,p.code as productCode,p.name as productName,p.norm as norm");
		sb.append(" from (");
		sb.append(" select s.product_id as productId");
		sb.append(" ,sum(s.num) as num");
		if(conditionMap!=null){
			//明细
			if(conditionMap.get("type")!=null && "1".equals(conditionMap.get("type"))){
				//总部
				if(conditionMap.get("level")!=null && "0".equals(conditionMap.get("level").toString())){
					sb.append(" ,s.company_name as companyName");
				//分公司
				}else if(conditionMap.get("level")!=null && "1".equals(conditionMap.get("level").toString())){
					sb.append(" ,s.whs_name as whsName");
				}
			}
		}
		sb.append(" from t_whs_stock as s " );
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){//商品id
				sb.append(" and s.product_id=").append(conditionMap.get("productId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
				sb.append(" and s.whs_id=").append(conditionMap.get("whsId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//分公司id
				sb.append(" and s.company_id=").append(conditionMap.get("companyId"));
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
				sb.append(" and s.whs_id in(:alist)");
			}
		}
		sb.append(" group by s.product_id");
		if(conditionMap!=null){
			//明细
			if(conditionMap.get("type")!=null && "1".equals(conditionMap.get("type"))){
				//总部
				if(conditionMap.get("level")!=null && "0".equals(conditionMap.get("level").toString())){
					sb.append(" ,s.company_id");
				//分公司
				}else if(conditionMap.get("level")!=null && "1".equals(conditionMap.get("level").toString())){
					sb.append(" ,s.whs_id");
				}
			}
			
		}
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append(" order by s.stamp asc");
		}
		sb.append(" ) as stock");
		sb.append(" left join t_sel_product p on p.id=stock.productId" );
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countWriteoffs(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id)");
		sb.append(" from t_whs_writeoffs as s " );
		sb=getCondition4Writeoffs(sb, conditionMap);
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public int countWriteoffsXz(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id)");
		sb.append(" from t_fee_writeoff as s " );
		sb=getCondition4WriteoffsXz(sb, conditionMap);
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	 /**
	  * @Title:getCondition4Writeoffs
	  * @Description:核销条件
	  * @param sb
	  * @param conditionMap
	  * @return
	  * @throws
	  */
	 private StringBuffer getCondition4Writeoffs(StringBuffer sb,Map conditionMap){
		 sb.append(" where exists (");
		 sb.append("	select 1 from t_whs_writeoffs_details as sid right join t_whs_borrow_details as b on sid.borrow_id=b.id");
		 sb.append(" where sid.writeoffs_id=s.id");
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("whs_Id"))){//仓库id
					sb.append(" and b.whs_id=").append(conditionMap.get("whsId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//分公司id
					sb.append(" and b.company_id=").append(conditionMap.get("companyId"));
				}
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds")) && !((List<Long>)conditionMap.get("wareHouseIds")).isEmpty()) {//仓库集合
					sb.append(" and b.whs_id in(:alist)");
				}
			}
			sb.append(")");
			
		 if(conditionMap!=null){
			 	if(conditionMap.get("writeoffsNo")!=null && StringUtils.isNotBlank((String)conditionMap.get("writeoffsNo"))){
					sb.append(" and s.writeoffs_no like '%").append(conditionMap.get("writeoffsNo")).append("%'");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("managersId")) ){
					sb.append(" and s.managers_id=").append(conditionMap.get("managersId"));
				}
				if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
					sb.append(" and s.stamp >='").append(conditionMap.get("startDate")).append("'");
				}
				if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
					sb.append(" and s.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
				}
			}
		 return sb;
	}
	 
	/**
	 * @Title:getCondition4WriteoffsXz
	 * @Description:销账条件
	 * @param sb
	 * @param conditionMap
	 * @return
	 * @throws
	 */
	 private StringBuffer getCondition4WriteoffsXz(StringBuffer sb,Map conditionMap){
		 sb.append(" where exists (");
			sb.append("	select 1 from t_fee_writeoff_details as sid right join t_whs_borrow_details as b on sid.borrow_id=b.id");
			sb.append(" where sid.writeoff_id=s.id");
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("whs_Id"))){//仓库id
					sb.append(" and b.whs_id=").append(conditionMap.get("whsId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//分公司id
					sb.append(" and b.company_id=").append(conditionMap.get("companyId"));
				}
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds")) && !((List<Long>)conditionMap.get("wareHouseIds")).isEmpty()) {//仓库集合
					sb.append(" and b.whs_id in(:alist)");
				}
			}
			sb.append(")");
		 if(conditionMap!=null){
				if(conditionMap.get("writeoffNo")!=null && StringUtils.isNotBlank((String)conditionMap.get("writeoffNo"))){
					sb.append(" and s.writeoff_no like '%").append(conditionMap.get("writeoffNo")).append("%'");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("channelId")) ){
					sb.append(" and s.channel_id=").append(conditionMap.get("channelId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("managerId")) ){
					sb.append(" and s.manager_id=").append(conditionMap.get("managerId"));
				}
				if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
					sb.append(" and s.stamp >='").append(conditionMap.get("startDate")).append("'");
				}
				if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
					sb.append(" and s.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
				}
			}
		 return sb;
	}

	@Override
	public List<HashMap<String, Object>> findSaleOutDetails(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select stock.*,p.code as productCode,p.name as productName,p.norm as norm");
		//营业处明细
		if(conditionMap!=null && conditionMap.get("type")!=null && "1".equals(conditionMap.get("type")) && conditionMap.get("level")!=null && "2".equals(conditionMap.get("level").toString())){
			sb.append(" , (case when stock.channelId is not null then c.name");
			sb.append(" when stock.customerId is not null then bc.customer_name else '' end) as channelName");
		}
		sb.append(" from (");
		sb.append(" select");
		sb.append(" sid.product_id as productId");
		sb.append(" ,sum(sid.out_num) as num");
		if(conditionMap!=null){
			//明细
			if(conditionMap.get("type")!=null && "1".equals(conditionMap.get("type"))){
				//总部
				if(conditionMap.get("level")!=null && "0".equals(conditionMap.get("level").toString())){
					sb.append(" ,si.company_name as companyName");
					
				//分公司
				}else if(conditionMap.get("level")!=null && "1".equals(conditionMap.get("level").toString())){
					sb.append(" ,si.whs_name as whsName");
					
				}else{
					sb.append(" ,si.code as stockCode,(case when si.type =0 then '代理销售出库' ");
					sb.append(" when si.type =1 then '直销出库' ");
					sb.append(" when si.type =2 then '升级出库' ");
					sb.append(" else  '出库' end)");
					sb.append(" as type");
					sb.append(" ,si.managers_name as managersName,sid.remark as remark,date_format(si.stamp,'%Y-%m-%d') as stamp");
					sb.append(" ,si.channel_id as channelId,si.customer_id as customerId");
				}
			}
		}
		sb.append(" from t_whs_stockout_details as sid");
		sb.append(" left join t_whs_stockout as si on si.id=sid.stockout_id ");
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){//商品id
				sb.append(" and sid.product_id=").append(conditionMap.get("productId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
				sb.append(" and si.whs_id=").append(conditionMap.get("whsId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//分公司id
				sb.append(" and si.company_id=").append(conditionMap.get("companyId"));
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
				sb.append(" and si.whs_id in(:alist)");
			}
			if(conditionMap.get("otype")!=null){//出库类型
				sb.append(" and si.type in (").append(conditionMap.get("otype")).append(")");
			}
			if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
				sb.append(" and si.stamp >='").append(conditionMap.get("startDate")).append("'");
			}
			if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
				sb.append(" and si.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
			}
		
		}
		sb.append(" group by sid.product_id");
		if(conditionMap!=null){
			//明细
			if(conditionMap.get("type")!=null && "1".equals(conditionMap.get("type"))){
				//总部
				if(conditionMap.get("level")!=null && "0".equals(conditionMap.get("level").toString())){
					sb.append(" ,si.company_id");
				//分公司
				}else if(conditionMap.get("level")!=null && "1".equals(conditionMap.get("level").toString())){
					sb.append(" ,si.whs_id");
				}
			}
			
		}
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append(" order by si.stamp desc");
		}
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		sb.append(" ) as stock");
		sb.append(" left join t_sel_product p on p.id=stock.productId" );
		//营业处、明细
		if(conditionMap!=null && conditionMap.get("type")!=null && "1".equals(conditionMap.get("type")) && conditionMap.get("level")!=null && "2".equals(conditionMap.get("level").toString())){
			sb.append(" left join t_sel_channel c on c.id=stock.channelId");//去掉关联的代理商
			sb.append(" left join t_ba_customer bc on bc.customer_id=stock.customerId" );
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countSaleOutDetails(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(*) from (");
		sb.append(" select si.code ");
		sb.append(" from t_whs_stockout_details as sid");
		sb.append(" left join t_whs_stockout as si on si.id=sid.stockout_id ");
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){//商品id
				sb.append(" and sid.product_id=").append(conditionMap.get("productId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
				sb.append(" and si.whs_id=").append(conditionMap.get("whsId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//分公司id
				sb.append(" and si.company_id=").append(conditionMap.get("companyId"));
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
				sb.append(" and si.whs_id in(:alist)");
			}
			if(conditionMap.get("otype")!=null){//出库类型
				sb.append(" and si.type in (").append(conditionMap.get("otype")).append(")");
			}
			if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
				sb.append(" and si.stamp >='").append(conditionMap.get("startDate")).append("'");
			}
			if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
				sb.append(" and si.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
			}
		
		}
		sb.append(" group by sid.product_id");
		if(conditionMap!=null){
			//明细
			if(conditionMap.get("type")!=null && "1".equals(conditionMap.get("type"))){
				//总部
				if(conditionMap.get("level")!=null && "0".equals(conditionMap.get("level").toString())){
					sb.append(" ,si.company_id");
				//分公司
				}else if(conditionMap.get("level")!=null && "1".equals(conditionMap.get("level").toString())){
					sb.append(" ,si.whs_id");
				}
			}
			
		}
		sb.append(" ) as stock");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public List<HashMap<String, Object>> findWriteoffsDetails(
			Map<String, Object> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select p.code as productCode,p.name as productName,p.norm as norm");
		sb.append(" ,sid.num as num,sid.price as price,sid.is_expired as isExpired,sid.money as money");
		sb.append(" from t_whs_writeoffs_details as sid " );
		sb.append(" left join t_sel_product p on p.id=sid.product_id" );
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("writeoffsId"))){//核销id
				sb.append(" and sid.writeoffs_id=").append(conditionMap.get("writeoffsId"));
			}
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findWriteoffsDetailsXz(
			Map<String, Object> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select p.code as productCode,p.name as productName,p.norm as norm");
		sb.append(" ,sid.quantity as quantity,sid.off_quantity as num,sid.remain_quantity as remainQuantity,sid.money as money");
		sb.append(" from t_fee_writeoff_details as sid " );
		sb.append(" left join t_sel_product p on p.id=sid.commodity_id" );
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("writeoffsId"))){//销账id
				sb.append(" and sid.writeoff_id=").append(conditionMap.get("writeoffsId"));
			}
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	@Override
	public List<HashMap<String, Object>> findWriteoffsOperates(
			Map<String, Object> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select sid.product_id as productId");
		sb.append(" ,'核销' as type,sid.num as inOutNum,si.writeoffs_no as stockCode,date_format(si.stamp,'%Y-%m-%d') as stamp,si.remark as remark");
		sb.append(" from t_whs_writeoffs_details as sid " );
		sb.append(" left join t_whs_writeoffs as si on si.id=sid.writeoffs_id" );
		//sb.append(" left join t_sel_product p on p.id=sid.product_id" );
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId")) ){
				sb.append(" and sid.product_id=").append(conditionMap.get("productId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("borrowId")) ){
				sb.append(" and sid.borrow_id=").append(conditionMap.get("borrowId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("managersId")) ){
				sb.append(" and si.managers_id=").append(conditionMap.get("managersId"));
			}
			if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
				sb.append(" and si.stamp >='").append(conditionMap.get("startDate")).append("'");
			}
			if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
				sb.append(" and si.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
			}
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findWriteoffsXzOperates(
			Map<String, Object> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select sid.commodity_id as productId");
		sb.append(" ,'销账' as type,sid.off_quantity as inOutNum,si.writeoff_no as stockCode,date_format(si.stamp,'%Y-%m-%d') as stamp,si.remark as remark");
		sb.append(" ,sid.money as money");
		sb.append(" from t_fee_writeoff_details as sid " );
		sb.append(" left join t_fee_writeoff as si on si.id=sid.writeoff_id" );
		//sb.append(" left join t_sel_product p on p.id=sid.commodity_id" );
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId")) ){
				sb.append(" and sid.commodity_id=").append(conditionMap.get("productId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("borrowId")) ){
				sb.append(" and sid.borrow_id=").append(conditionMap.get("borrowId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("managersId")) ){
				sb.append(" and si.manager_id=").append(conditionMap.get("managersId"));
			}
			if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
				sb.append(" and si.stamp >='").append(conditionMap.get("startDate")).append("'");
			}
			if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
				sb.append(" and si.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
			}
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	@Override
	public List<HashMap<String, Object>> findStockInDetailsOperates(
			Map<String, Object> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select sid.product_id as productId");
		sb.append(" ,si.code as stockCode,sid.id as id,si.whs_id as whsId,si.whs_name as whsName,sid.product_id");
		sb.append(" ,(case when si.type =0 then '采购入库'"); 
		sb.append(" when si.type =1 then '调拨入库'"); 
		sb.append(" when si.type =2 then '归还入库'"); 
		sb.append(" when si.type =3 then '其他入库' ");
		sb.append(" when si.type =4 then '报废入库' ");
		sb.append(" when si.type =5 then '退货入库' ");
		sb.append(" when si.type =6 then '回收入库' ");
		sb.append(" when si.type =9 then '调账入库' ");
		sb.append(" else  '入库' end)");
		sb.append(" as type");
		sb.append(",sid.in_num as inOutNum,(sid.cur_num+sid.in_num) as surplusNum,si.managers_name as managersName,sid.remark as remark,si.user_name as userName,si.stamp as stamp");
		sb.append(" ,'' as customer_id,si.channel_id");
	    sb.append(" from t_whs_stockin_details as sid " );
	   sb.append(" left join t_whs_stockin as si on si.id=sid.stockin_id" );
	   //sb.append(" left join t_sel_product p on p.id=sid.product_id" );
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId")) ){
				sb.append(" and sid.product_id=").append(conditionMap.get("productId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("managersId")) ){
				sb.append(" and si.managers_id=").append(conditionMap.get("managersId"));
			}
			if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
				sb.append(" and si.stamp >='").append(conditionMap.get("startDate")).append("'");
			}
			if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
				sb.append(" and si.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
			}
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findStockOutDetailsOperates(
			Map<String, Object> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select sid.product_id as productId");
		sb.append(" ,si.code as stockCode,sid.id as id,si.whs_id as whsId,si.whs_name as whsName");
		sb.append(" ,(case when si.type =0 then '代理销售出库' ");
		sb.append(" when si.type =1 then '直销出库' ");
		sb.append(" when si.type =2 then '升级出库' ");
		sb.append(" when si.type =3 then '借用出库' ");
		sb.append(" when si.type =4 then '调拨出库' ");
		sb.append(" when si.type =5 then '其他出库' ");
		sb.append(" when si.type =9 then '调账出库' ");
		sb.append(" else  '出库' end)");
		sb.append(" as type");
		sb.append(" ,-sid.out_num as inOutNum,(sid.cur_num-sid.out_num) as surplusNum,si.managers_name as managersName,sid.remark as remark,si.user_name as userName,si.stamp as stamp");
		sb.append(" ,si.customer_id as customerId,si.channel_id as channelId");
	 	sb.append(" from t_whs_stockout_details as sid ");
		sb.append(" left join t_whs_stockout as si on  si.id=sid.stockout_id ");
		//sb.append(" left join t_sel_product p on p.id=sid.product_id" );
		sb.append(" where 1=1");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){
				sb.append(" and sid.product_id=").append(conditionMap.get("productId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("managersId"))){
				sb.append(" and si.managers_id=").append(conditionMap.get("managersId"));
			}
			if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
				sb.append(" and si.stamp >='").append(conditionMap.get("startDate")).append("'");
			}
			if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
				sb.append(" and si.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
			}
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findBorrowDetails(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select bo.*,p.code as productCode,p.name as productName,p.norm as norm");
		sb.append(" ,(case when bo.channelId is not null then (select name from t_sel_channel c where c.id=bo.channelId) else '' end) as channelName");
		sb.append(" ,(case when bo.customerId is not null then (select bc.customer_name from t_ba_customer bc where bc.customer_id=bo.customerId) else '' end) as customerName");
		sb.append(" from (");
		sb.append(" select");
		sb.append(" b.id as id,b.stamp as stamp,b.price as price,b.remark as remark,b.status");
		//合同、类型
		sb.append(" ,b.contract_id as contractId,(select name from t_sel_salescontract sc where sc.id=b.contract_id) as contractName,b.type as type");
		sb.append(" ,b.product_id as productId");
		sb.append(" ,sum(b.num) as borrowNum,sum(b.writeoffs_num) as writeoffsNum,sum(b.writeoffs_num2) as writeoffsNum2,sum(b.return_num) as returnNum");
		sb.append(" ,sum(b.num)-sum(case when b.writeoffs_num is null then 0 else b.writeoffs_num end)-sum(case when b.writeoffs_num2 is null then 0 else b.writeoffs_num2 end)-sum(case when b.return_num is null then 0 else b.return_num end) as num");
		sb.append(" ,b.borrower_id as borrowerId,b.borrower_name as borrowerName,b.whs_id as whsId,b.whs_name as whsName,b.channel_id as channelId,b.customer_id as customerId");
		sb.append(" , (case when b.to_borrow_id is null then '' else (select borrower_name from t_whs_borrow_details where id=b.to_borrow_id )");
		sb.append(" end) as toBorrowerName");
		sb.append(" from t_whs_borrow_details as b ");
		sb.append(" where 1=1 ");
		sb=getCondition4BorrowDetails(sb,conditionMap);
		sb.append(" group by b.product_id,b.borrower_id,b.whs_id");
		sb.append(",b.id,b.stamp,b.price");
		sb.append(" order by b.stamp desc");
		sb.append(" )as bo ");
		sb.append(" left join t_sel_product p on p.id=bo.productId");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countBorrowDetails(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select count(*)");
		sb.append(" from t_whs_borrow_details as b ");
		sb.append(" where 1=1 ");
		sb=getCondition4BorrowDetails(sb,conditionMap);
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	 private StringBuffer getCondition4BorrowDetails(StringBuffer sb,Map conditionMap){
		 if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("borrowerId"))){
					sb.append(" and b.borrower_id=").append(conditionMap.get("borrowerId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){
					sb.append(" and b.product_id=").append(conditionMap.get("productId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("channelId"))){
					sb.append(" and b.channel_id=").append(conditionMap.get("channelId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){
					sb.append(" and b.company_id=").append(conditionMap.get("companyId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){
					sb.append(" and b.whs_id=").append(conditionMap.get("whsId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("orgId"))){
					sb.append(" and b.org_id=").append(conditionMap.get("orgId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("managersId"))){
					sb.append(" and b.borrower_id=").append(conditionMap.get("managersId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("status"))){
					sb.append(" and b.status=").append(conditionMap.get("status"));
				}
				if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
					sb.append(" and b.stamp >='").append(conditionMap.get("startDate")).append("'");
				}
				if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
					sb.append(" and b.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
				}
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
					sb.append(" and b.whs_id in(:alist)");
				}
			}
		 return sb;
	}

	@Override
	public boolean checkAllocateDetailsNotCompleted(Long stockoutId)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id) from StockoutDetails as s");
		sb.append(" where 1=1 ");
			if(stockoutId!=null){
				sb.append(" and s.stockoutId=").append(stockoutId);
				sb.append(" and s.isCompleted=0");
			}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		if((Long)query.uniqueResult()>0){
			return true;
		}else{
			return false;
		}
	}
}

