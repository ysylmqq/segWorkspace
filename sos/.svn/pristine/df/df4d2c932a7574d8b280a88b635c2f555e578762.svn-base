package com.gboss.dao.impl;

import java.util.ArrayList;
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
import com.gboss.dao.SellPerformanceDao;
import com.gboss.pojo.BorrowDetails;
import com.gboss.pojo.Plan;
import com.gboss.pojo.SellPerformance;
import com.gboss.util.StringUtils;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:SellPerformanceDaoImpl
 * @Description:销售业绩持久化层实现类
 * @author:zfy
 * @date:2013-8-6 上午8:39:48
 */
@Repository("sellPerformanceDao")  
@Transactional  
public class SellPerformanceDaoImpl extends BaseDaoImpl implements SellPerformanceDao {

	@Override
	public List<Plan> getPlan(HashMap<String,String> conditionMap) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Plan.class); 
		if(conditionMap!=null){
			if(StringUtils.isNotBlank(conditionMap.get("id"))){
				criteria.add(Restrictions.not(Restrictions.eq("id", Long.valueOf(conditionMap.get("id")))));
			}
			if(StringUtils.isNotBlank(conditionMap.get("year"))){
				criteria.add(Restrictions.eq("year", conditionMap.get("year")));
			}
			if(StringUtils.isNotBlank(conditionMap.get("monthQuota"))){
				String[] month=conditionMap.get("monthQuota").split(",");
				criteria.add(Restrictions.in("monthQuota", month));
			}
		}
		return criteria.list();
	}

	@Override
	public List<Map<String, Object>> getSaleProducts(HashMap<String,String> conditionMap) {
		StringBuffer sb=new StringBuffer();
		sb.append("select new map(sc.userId as userId,sc.orgId as orgId,sc.companyId as companyId,sum(sp.num) as saleNum) from SalesProduct as sp , Salescontract as sc ");
		sb.append(" where sp.contractId=sc.id ");
		//只统计出上一个月的销售数量
		if(conditionMap!=null){
			String preYearMonth=conditionMap.get("preYearMonth");
			if(StringUtils.isNotBlank(preYearMonth)){//如2013-04
				sb.append(" and sc.signDate like '").append(preYearMonth).append("%'");
			}
		}
		sb.append(" group by sc.userId,sc.orgId,sc.companyId ");
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Map<String, Object>> getNetIn(
			HashMap<String, String> conditionMap) {
		StringBuffer sb=new StringBuffer();
		sb.append("select new map(bu.manager as userId,bu.org_id as orgId,bu.company_id as companyId,count(*) as netNum)");
		sb.append(" from Unit as bu ");
		sb.append(" where bu.status=1 ");
		//只统计出上一个月的入网数量
		if(conditionMap!=null){
			String preYearMonth=conditionMap.get("preYearMonth");
			if(StringUtils.isNotBlank(preYearMonth)){//如2013-04
				sb.append(" and bu.enter_time like '").append(preYearMonth).append("%'");
			}
		}
		sb.append(" group by bu.manager,bu.org_id,bu.company_id ");
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Map<String, Object>> getReturnMoney(
			HashMap<String, String> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select new map(w.user_id as userId,w.org_id as orgId,w.company_id as companyId,sum(w.off_amount) as retMoney)");
		sb.append(" from Writeoff as w");
		sb.append(" where 1=1");
		//只统计出上一个月的回款额度
		if(conditionMap!=null){
			String preYearMonth=conditionMap.get("preYearMonth");
			if(StringUtils.isNotBlank(preYearMonth)){//如2013-04
				sb.append(" and w.time like '").append(preYearMonth).append("%'");
			}
		}
		sb.append(" group by w.user_id,w.org_id,w.company_id ");
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();
	}

	@Override
	public SellPerformance getSellPerformance(SellPerformance sellPerformance) throws SystemException{
		List<SellPerformance> results=getSellPerformanceCondtion(sellPerformance).list();
		if(results!=null&&results.size()>0){
			return results.get(0);
		}
		return null;
	}

	@Override
	public List<SellPerformance> findSellPerformances(
			SellPerformance sellPerformance) throws SystemException{
		return getSellPerformanceCondtion(sellPerformance).list();
	}
	
	private Criteria getSellPerformanceCondtion(SellPerformance sellPerformance){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SellPerformance.class); 
		if(sellPerformance!=null){
			if(sellPerformance.getId()!=null){
				criteria.add(Restrictions.not(Restrictions.eq("id", sellPerformance.getId())));
			}
			if(StringUtils.isNotBlank(sellPerformance.getYear())){
				criteria.add(Restrictions.eq("year", sellPerformance.getYear()));
			}
			if(StringUtils.isNotBlank(sellPerformance.getMonth())){
				criteria.add(Restrictions.eq("month", sellPerformance.getMonth()));
			}
			if(sellPerformance.getType()!=null){
				criteria.add(Restrictions.eq("type", sellPerformance.getType()));
			}
			if(sellPerformance.getUserOrgId()!=null){
				criteria.add(Restrictions.eq("userOrgId", sellPerformance.getUserOrgId()));
			}
		}
		return criteria;
	}

	@Override
	public List<Map<String, Object>> getSaleProductsDetail(
			HashMap<String, String> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select new map(sp.id as productId,p.name as productName,sum(sp.num) as saleNum)");
		sb.append(" from SalesProduct as sp , Salescontract as sc,Product as p  ");
		sb.append(" where sp.contractId=sc.id and sp.id=p.id");
		//只统计出上一个月的销售数量
		if(conditionMap!=null){
			String yearMonth=conditionMap.get("yearMonth");
			if(StringUtils.isNotBlank(yearMonth)){//如2013-04
				sb.append(" and sc.signDate like '").append(yearMonth).append("%'");
			}
			if(conditionMap.get("userId")!=null){
				sb.append(" and sc.userId=").append(conditionMap.get("userId"));
			}
			if(conditionMap.get("orgId")!=null){
				sb.append(" and sc.orgId=").append(conditionMap.get("orgId"));
			}
			if(conditionMap.get("companyId")!=null){
				sb.append(" and sc.companyId=").append(conditionMap.get("companyId"));
			}
		}
		sb.append(" group by sp.id,p.name ");
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Map<String, Object>> getNetInDetail(
			HashMap<String, String> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select new map(bu.product_id as productId,count(*) as netNum)");
		sb.append(" from Unit as bu ");
		sb.append(" where bu.status=1 ");
		//只统计出上一个月的入网数量
		if(conditionMap!=null){
			String yearMonth=conditionMap.get("yearMonth");
			if(StringUtils.isNotBlank(yearMonth)){//如2013-04
				sb.append(" and bu.enter_time like '").append(yearMonth).append("%'");
			}
			if(conditionMap.get("userId")!=null){
				sb.append(" and bu.user_id=").append(conditionMap.get("userId"));
			}
			if(conditionMap.get("orgId")!=null){
				sb.append(" and bu.org_id=").append(conditionMap.get("orgId"));
			}
			if(conditionMap.get("companyId")!=null){
				sb.append(" and bu.company_id=").append(conditionMap.get("companyId"));
			}
		}
		sb.append(" group by bu.product_id ");
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Map<String, Object>> getReturnMoneyDetail(
			HashMap<String, String> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select new map(wd.commodityId as productId,sum(wd.money) as retMoney)");
		sb.append(" from Writeoff as w,WriteoffDetails as wd");
		sb.append(" where w.id=wd.writeoffId");
		//只统计出上一个月的回款额度
		if(conditionMap!=null){
			String yearMonth=conditionMap.get("yearMonth");
			if(StringUtils.isNotBlank(yearMonth)){//如2013-04
				sb.append(" and w.time like '").append(yearMonth).append("%'");
			}
			if(conditionMap.get("userId")!=null){
				sb.append(" and w.manager_id=").append(conditionMap.get("userId"));
			}
			if(conditionMap.get("orgId")!=null){
				sb.append(" and w.org_id=").append(conditionMap.get("orgId"));
			}
			if(conditionMap.get("companyId")!=null){
				sb.append(" and w.company_id=").append(conditionMap.get("companyId"));
			}
		}
		sb.append(" group by wd.commodityId ");
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Map<String, Object>> findBorrows(BorrowDetails borrowDetails) throws SystemException{
		StringBuffer sb = new StringBuffer();
		sb.append(" select b.product_id as productId,p.name as productName");
		sb.append(" ,sum(b.num)-sum(case when b.writeoffs_num is null then 0 else b.writeoffs_num end) as num");
		if(borrowDetails!=null && borrowDetails.getCompanyId()!=null ){
			sb.append(" ,b.org_id as orgId");
		} else {
			sb.append(" ,b.borrower_id as borrowerId");
		}
		sb.append(" from t_whs_borrow_details as b ");
		sb.append(" left join t_sel_product p on p.id=b.product_Id");
		sb.append(" where 1=1 ");
		if(borrowDetails!=null){
			if(borrowDetails.getBorrowerId()!=null){
				sb.append(" and b.borrower_id=").append(borrowDetails.getBorrowerId());
				sb.append(" group by b.product_id,b.borrower_id");
			}else if(borrowDetails.getOrgId()!=null){
				sb.append(" and b.org_id=").append(borrowDetails.getOrgId());
				sb.append(" group by b.product_id,b.borrower_id");
			}else if(borrowDetails.getCompanyId()!=null){
				sb.append(" and b.company_id=").append(borrowDetails.getCompanyId());
				sb.append(" group by b.product_id,b.org_id");
			}
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<Map<String, Object>> findStockInDetail(
			HashMap<String, String> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select new map(sid.productId as productId,p.name as productName,sid.inNum as num, si.stamp as stamp,'入库' as type,si.whsId as whsId,'' as whsName,si.channelId as ChannelId ,(select c.name from Channel c where c.id=si.channelId) as channelName,'' as money) ");
		sb.append(" from Stockin as si , StockinDetails as sid ,Product as p");
		sb.append(" where si.id=sid.stockinId and sid.productId=p.id");
		if(conditionMap!=null){
			String yearMonth=conditionMap.get("yearMonth");
			if(StringUtils.isNotBlank(yearMonth)){//如2013-04
				sb.append(" and si.stamp like '").append(yearMonth).append("%'");
			}
			if(conditionMap.get("userId")!=null){
				sb.append(" and si.managersId=").append(conditionMap.get("userId"));
			}
		}
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Map<String, Object>> findStockOutDetail(
			HashMap<String, String> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select new map(sid.productId as productId,p.name as productName,sid.outNum as num, si.stamp as stamp,'出库' as type,si.whsId as whsId,'' as whsName,si.channelId as ChannelId ,(select c.name from Channel c where c.id=si.channelId) as channelName,'' as money) ");
		sb.append(" from Stockout as si , StockoutDetails as sid ,Product as p ");
		sb.append(" where si.id=sid.stockoutId and sid.productId=p.id");
		if(conditionMap!=null){
			String yearMonth=conditionMap.get("yearMonth");
			if(StringUtils.isNotBlank(yearMonth)){//如2013-04
				sb.append(" and si.stamp like '").append(yearMonth).append("%'");
			}
			if(conditionMap.get("userId")!=null){
				sb.append(" and si.managersId=").append(conditionMap.get("userId"));
			}
		}
		//结果示例 [{"ChannelId":"1","stamp":"2013-07-08","channelName":"渠道1","num":21,"money":"","whsName":"长沙生产力促进中心","whsId":"1","type":"出库","productName":"双镜头行车记录仪","productId":"1"}]
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Map<String, Object>> findWriteoffDetails(
			HashMap<String, String> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select new map(sid.commodityId as productId,p.name as productName,sid.offQuantity as num, si.time as stamp,'销账' as type,'' as whsId,'' as whsName,si.channel_id as ChannelId ,(select c.name from Channel c where c.id=si.channel_id) as channelName,si.off_amount as money) ");
		sb.append(" from Writeoff as si , WriteoffDetails as sid ,Product as p ");
		sb.append(" where si.id=sid.writeoffId and sid.commodityId=p.id");
		if(conditionMap!=null){
			String yearMonth=conditionMap.get("yearMonth");
			if(StringUtils.isNotBlank(yearMonth)){//如2013-04
				sb.append(" and si.time like '").append(yearMonth).append("%'");
			}
			if(conditionMap.get("userId")!=null){
				sb.append(" and si.manager_id=").append(conditionMap.get("userId"));
			}
		}
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<SellPerformance> statSellPerformance(
			HashMap<String, Object> conditionMap) throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SellPerformance.class); 
		if(conditionMap!=null){
			if(conditionMap.get("year")!=null){
				criteria.add(Restrictions.eq("year", conditionMap.get("year")));
			}
			if(conditionMap.get("type")!=null){
				criteria.add(Restrictions.eq("type", Integer.parseInt(String.valueOf(conditionMap.get("type")))));
			}
			if(conditionMap.get("month")!=null){
				String[] month=(String[])conditionMap.get("month");
				criteria.add(Restrictions.in("month", month));
			}
			if(conditionMap.get("userOrgIds")!=null){
				List<Long> userOrgIds=(List<Long>)conditionMap.get("userOrgIds");
				criteria.add(Restrictions.in("userOrgId", userOrgIds));
			}
		}
		return criteria.list();
	}

	@Override
	public List<SellPerformance> statAllSellPerformance(String year)
			throws SystemException {
		String hql = " select month,sum(plan_sell_num) as plan_sell_num,sum(real_sell_num) as real_sell_num,"
				    + " avg(sell_num_rate) as sell_num_rate,sum(plan_net) as plan_net,"
				    + " sum(real_net) as real_net,avg(net_rate) as net_rate,"
				    + " sum(plan_return_money) as plan_return_money,sum(real_return_money) as real_return_money,"
				    + " avg(return_money_rate) as return_money_rate from SellPerformance "
				    + " where year = '" + year + "' group by month ";
		List list = listAll(hql);
		List<SellPerformance> sellPerformances = new ArrayList<SellPerformance>();
		for(int i=0;i<list.size();i++){
			Object[] objs = (Object[]) list.get(i);
			String month = (String) objs[0];
			int plan_sell_num = (Integer) objs[1];
			int real_sell_num = (Integer) objs[2];
			float sell_num_rate = (Float) objs[3];
			int plan_net = (Integer) objs[4];
			int real_net = (Integer) objs[5];
			float net_rate = (Float) objs[6];
			float plan_return_money = (Integer) objs[7];
			float real_return_money = (Integer) objs[8];
			float return_money_rate = (Float) objs[9];
			SellPerformance sellPerformance = new SellPerformance();
			sellPerformance.setMonth(month);
			sellPerformance.setPlanSellNum(plan_sell_num);
			sellPerformance.setRealSellNum(real_sell_num);
			sellPerformance.setSellNumRate(sell_num_rate);
			sellPerformance.setPlanNet(plan_net);
			sellPerformance.setRealNet(real_net);
			sellPerformance.setNetRate(net_rate);
			sellPerformance.setPlanReturnMoney(plan_return_money);
			sellPerformance.setRealReturnMoney(real_return_money);
			sellPerformance.setReturnMoneyRate(return_money_rate);
			sellPerformances.add(sellPerformance);
		}
		return sellPerformances;
	}

}

