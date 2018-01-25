package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.OrderDao;
import com.gboss.pojo.Order;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:ServiceItemDaoImp
 * @Description:订单管理持久层实现类
 * @author:zfy
 * @date:2013-8-9 下午2:21:52
 */
@Repository("orderDao")  
@Transactional  
public class OrderDaoImp extends BaseDaoImpl implements OrderDao {
	protected static final Logger LOGGER = LoggerFactory.getLogger(OrderDaoImp.class);

	@Override
	public int deleteCustomerAddresss(List<Long> ids) throws SystemException {
		String hql="delete CustomerAddress WHERE id IN (:alist)";  
		Query query = sessionFactory.getCurrentSession().createQuery(hql);  
		query.setParameterList("alist", ids); 
		return query.executeUpdate();
	}

	@Override
	public List<HashMap<String, Object>> findCustomerAddress(Map<String, Object> map)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select cd.id as id, cd.name as name, cd.address as address");
		sb.append(" ,cd.phone as phone,cd.tel as tel");
		sb.append(" ,cd.email as email,cd.postcode as postcode,cd.is_default as isDefault");
		sb.append(" ,cd.remark as remark");
		
		if(map!=null && map.get("orderId")!=null){
			sb.append(" ,od.transport_type as transportType,od.specify_freight as specifyFreight,od.pay_type as payType,od.remark as oremark");
		}
		sb.append(" from t_whs_customer_address as cd ");
		if(map!=null && map.get("orderId")!=null){
			sb.append(" left join t_whs_order_address as od on cd.id=od.address_id");
		}
		sb.append(" where 1=1");
		if (map !=null) {
			if(StringUtils.isNotNullOrEmpty(map.get("companyId"))){
				sb.append(" and cd.company_Id=").append(map.get("companyId"));
			}
			if(StringUtils.isNotNullOrEmpty(map.get("orderId"))){
				sb.append(" and od.order_Id=").append(map.get("orderId"));
			}
			if(map.get("name")!=null){
				sb.append(" and cd.name like '%").append(StringUtils.replaceSqlKey(map.get("name"))).append("%'");
			}
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findOrders(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select new map(o.id as id,o.orderNo as orderNo,o.totalPrice as totalPrice");
		sb.append(" ,o.companyId as companyId,o.status as status,o.isCompleted as isCompleted");
		sb.append(" ,o.receiptId as receiptId,o.receiptName as receiptName,o.userId as userId,o.stamp as stamp");
		sb.append(" ,o.whsName as whsName,o.remark as remark)");
		sb=getCondition4Order(sb,conditionMap);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		if (pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	 private StringBuffer getCondition4Order(StringBuffer sb,Map conditionMap){
			sb.append(" from Order as o ");
			sb.append(" where 1=1 ");
			if(conditionMap!=null){
				if(conditionMap.get("orderNo")!=null){//订单号
					sb.append(" and o.orderNo like '%").append(StringUtils.replaceSqlKey(conditionMap.get("orderNo"))).append("%'");
				}
				if(conditionMap.get("isCompleted")!=null){//是否完成，1是，0否
					sb.append(" and o.isCompleted=").append(conditionMap.get("isCompleted"));
				}
				if(conditionMap.get("status")!=null){//状态：1：生效，0未生效
					sb.append(" and o.status=").append(conditionMap.get("status"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("receiptId"))){//订单接收人id
					sb.append(" and o.receiptId=").append(conditionMap.get("receiptId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//分公司id
					sb.append(" and o.companyId=").append(conditionMap.get("companyId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("orgId"))){//部门id
					sb.append(" and o.orgId=").append(conditionMap.get("orgId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("whsId"))){//仓库id
					sb.append(" and o.whsId=").append(conditionMap.get("whsId"));
				}
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {//仓库集合
					sb.append(" and o.whsId in(:alist)");
				}
				if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
					sb.append(" and o.stamp >='").append(conditionMap.get("startDate")).append("'");
				}
				if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
					sb.append(" and o.stamp <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
				}
			}
			return sb;
		 }

	@Override
	public int countOrders(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer packHqlSb=new StringBuffer();
		packHqlSb.append(" select count(*) ");
		packHqlSb=getCondition4Order(packHqlSb,conditionMap);
		Query query = sessionFactory.getCurrentSession().createQuery(packHqlSb.toString());  
		if (StringUtils.isNotNullOrEmpty(conditionMap.get("wareHouseIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("wareHouseIds"));
		}
		return ((Long)query.uniqueResult()).intValue();
	}

	@Override
	public List<HashMap<String, Object>> findOrderDetailsByOrderId(
			Long orderId) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select new map(c.id as id,c.orderId as orderId,c.supplycontractsId as supplycontractsId");
		hqlSb.append(" ,c.productId as productId,p.code as productCode,p.name as productName,p.norm as norm,c.num as num");
		hqlSb.append(" ,c.inNum as inNum,c.price as price,c.remark as remark)");
		hqlSb.append(" from OrderDetails as c , Product as p");
		hqlSb.append(" where c.productId=p.id ");
		if (orderId!=null) {
			hqlSb.append(" and c.orderId=").append(orderId);
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		return query.list();
	}

	@Override
	public int deleteDetailsByOrderId(Long orderId) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" delete OrderDetails ");
		sb.append(" where orderId=").append(orderId);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}

	@Override
	public int deleteAddressByOrderId(Long orderId) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" delete OrderAddress ");
		sb.append(" where orderId=").append(orderId);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}

	@Override
	public boolean checkOrderIsUsing(Long orderId) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(e.id) from Stockin as e");
		sb.append(" where 1=1 ");
		if(orderId!=null){
			sb.append(" and e.orderId=").append(orderId);
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		if((Long)query.uniqueResult()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public int updateCustomAddressIsDefault(Long id, Long companyId)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" update CustomerAddress ");
		sb.append(" set isDefault=0");
		sb.append(" where id<>").append(id);
		sb.append(" and companyId=").append(companyId);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}

	@Override
	public int getMaxOrderNo(Long companyId, String date)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select max(substring(s.orderNo,length(s.orderNo)-3,4)) from Order as s");
		sb.append(" where 1=1");
		if(companyId!=null){
			sb.append(" and s.companyId=").append(companyId);
		}
		if(StringUtils.isNotBlank(date)){
			sb.append(" and s.stamp like '").append(date).append("%'");
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
	public boolean checkOrderNo(Order order) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id) from Order as s");
		sb.append(" where 1=1 ");
		if(order!=null){
			if(StringUtils.isNotBlank(order.getOrderNo())){
				sb.append(" and s.orderNo='").append(order.getOrderNo()).append("'");
			} 
			if(order.getId()!=null){
				sb.append(" and s.id!=").append(order.getId());
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
	public Float getPriceByProductId(Long companyId, Long productId,String stamp,Integer status)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select s.price as price");
		sb.append("  from t_sel_supply_details as s left join t_sel_supply_branch as b");
		sb.append("  on s.supply_id=b.supply_id where 1=1");
		if(companyId!=null){
			sb.append(" and b.org_id=").append(companyId);
		}
		if(productId!=null){
			sb.append(" and s.product_id=").append(productId);
		}
		if(StringUtils.isNotBlank(stamp)){
			sb.append(" and s.valid_date<='").append(stamp).append("'");
			sb.append(" and s.mature_date>='").append(stamp).append("'");
		}
		if(status!=null){
			sb.append(" and s.status=").append(status);
		}
		sb.append(" order by s.stamp desc");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> result=query.list();
		if(result!=null && !result.isEmpty()){
			return Float.valueOf(result.get(0).get("price").toString());
		}else{
			return 0f;
		}
	} 
	
	@Override
	public boolean checkOrderDetailsNotCompleted(Long orderId) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id) from OrderDetails as s");
		sb.append(" where 1=1 ");
			if(orderId!=null){
				sb.append(" and s.orderId=").append(orderId);
				sb.append(" and s.isCompleted=0");
			}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		if((Long)query.uniqueResult()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Float getOrderPriceByProductId(Long companyId, Long productId)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select od.price as price");
		sb.append(" from t_whs_order as o left join t_whs_order_details as od on o.id=od.order_id");
		sb.append(" where o.status=1");
		if(companyId!=null){
			sb.append(" and o.company_Id=").append(companyId);
		}
		if(productId!=null){
			sb.append(" and od.product_id=").append(productId);
		}
		sb.append(" order by o.stamp desc");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> result=query.list();
		if(result!=null && !result.isEmpty()){
			return Float.valueOf(result.get(0).get("price").toString());
		}else{
			return 0f;
		}
	}
}

