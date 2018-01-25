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
import com.gboss.dao.SalesContractDao;
import com.gboss.pojo.SalesPack;
import com.gboss.pojo.SalesProduct;
import com.gboss.pojo.Salescontract;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:SalesContractDaoImpl
 * @Description:销售合同数据持久层实现类
 * @author:zfy
 * @date:2013-8-27 上午11:22:11
 */
@Repository("salesContractDao")  
@Transactional 
public class SalesContractDaoImpl extends BaseDaoImpl implements SalesContractDao {

	@Override
	public List<HashMap<String, Object>> findSalesContracts(
			Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select sc.id,sc.code ,sc.name ,sc.channel_id as channelId,c.name as channelName,sc.phone,sc.contractor_id as contractorId,sc.contractor_name as contractorName,date_format(sc.sign_date,'%Y-%m-%d') as signDate,date_format(sc.valid_date,'%Y-%m-%d') as validDate,date_format(sc.mature_date,'%Y-%m-%d') as matureDate,sc.status as status,sc.is_filing as isFiling,sc.user_name as userName,sc.remark as remark");
		sb.append(" from t_sel_salescontract as sc left join t_sel_channel as c on sc.channel_id=c.id ");
		sb.append(" where  1=1 ");
		sb=getCondition4SalesContracts(sb,conditionMap);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("orgIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("orgIds"));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
		query.setMaxResults(pageSize);
		return query.list();
	}

	@Override
	public int countSalesContracts(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select count(sc.id) ");
		sb.append(" from t_sel_salescontract as sc left join t_sel_channel as c on sc.channel_Id=c.id");
		sb.append(" where  1=1 ");
		sb=getCondition4SalesContracts(sb,conditionMap);
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		if (conditionMap!=null && StringUtils.isNotNullOrEmpty(conditionMap.get("orgIds"))) {
			query.setParameterList("alist", (List<Long>)conditionMap.get("orgIds"));
		}
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	private StringBuffer getCondition4SalesContracts(StringBuffer sb,Map conditionMap){
		 if(conditionMap!=null){
			 if(conditionMap!=null){
					if(StringUtils.isNotNullOrEmpty(conditionMap.get("name"))){//合同名称
						sb.append(" and sc.name like '").append(StringUtils.replaceSqlKey(conditionMap.get("name"))).append("%'");
					}
					if(StringUtils.isNotNullOrEmpty(conditionMap.get("channelName"))){//代理商名称
						sb.append(" and c.name like '").append(StringUtils.replaceSqlKey(conditionMap.get("channelName"))).append("%'");
					}
					if(StringUtils.isNotNullOrEmpty(conditionMap.get("contractorId"))){//签约人
						sb.append(" and sc.contractor_id=").append(conditionMap.get("contractorId"));
						/*sb.append(" and (sc.contractor_id=").append(conditionMap.get("contractorId"));
						sb.append(" or  ( exists ( select 1 from t_sel_channel_salesmanager cs1 where exists");
						sb.append(" ( select 1 from t_sel_channel_salesmanager where manager_id=").append(conditionMap.get("userId"));
						sb.append(" and cs1.channel_id=channel_id) ");
						sb.append(" and cs1.isdel=1 and cs1.channel_id=sc.channel_id and sc.contractor_id=cs1.manager_id");
						sb.append(")))");*/
					}
					if(StringUtils.isNotNullOrEmpty(conditionMap.get("userId"))){//操作人
						sb.append(" and (sc.user_id=").append(conditionMap.get("userId"));
						//如果前任销售经理已经离职、或者转岗，也需要能看得到前任销售经理签订的合同
						sb.append(" or  ( exists ( select 1 from t_sel_channel_salesmanager cs1 where exists");
						sb.append(" ( select 1 from t_sel_channel_salesmanager where manager_id=").append(conditionMap.get("userId"));
						sb.append(" and cs1.channel_id=channel_id) ");
						sb.append(" and cs1.isdel=1 and cs1.channel_id=sc.channel_id and sc.user_id=cs1.manager_id");
						sb.append(")))");
					}
					if(StringUtils.isNotNullOrEmpty(conditionMap.get("orgIds"))){//销售总监，可以查看多个部门的合同
						sb.append(" and sc.org_id in(:alist)");
					}
					if(StringUtils.isNotNullOrEmpty(conditionMap.get("orgId"))){//部门
						sb.append(" and sc.org_id=").append(conditionMap.get("orgId"));
					}
					if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//公司
						sb.append(" and sc.company_id=").append(conditionMap.get("companyId"));
					}
					if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
						sb.append(" and sc.sign_date >='").append(conditionMap.get("startDate")).append("'");
					}
					if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
						sb.append(" and sc.sign_date <='").append(conditionMap.get("endDate")).append(" 23:59:59'");
					}
					if(StringUtils.isNotNullOrEmpty(conditionMap.get("status"))){//状态
						sb.append(" and sc.status=").append(conditionMap.get("status"));
					}
					if(StringUtils.isNotNullOrEmpty(conditionMap.get("isFiling"))){//归档状态
						sb.append(" and sc.is_filing=").append(conditionMap.get("isFiling"));
					}
				}
			}
		 return sb;
	}
	@Override
	public boolean checkSalescontractCode(Salescontract salescontract)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id) from Salescontract as s");
		sb.append(" where 1=1 ");
		if(salescontract!=null){
			if(StringUtils.isNotBlank(salescontract.getCode())){
				sb.append(" and s.code='").append(salescontract.getCode()).append("'");
			}
			if(salescontract.getCompanyId()!=null){
				sb.append(" and s.companyId=").append(salescontract.getCompanyId());
			}
			if(salescontract.getId()!=null){
				sb.append(" and s.id!=").append(salescontract.getId());
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
	public boolean checkSalescontractName(Salescontract salescontract)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id) from Salescontract as s");
		sb.append(" where 1=1 ");
		if(salescontract!=null){
			if(StringUtils.isNotBlank(salescontract.getName())){
				sb.append(" and s.name='").append(salescontract.getName()).append("'");
			}
			if(salescontract.getCompanyId()!=null){
				sb.append(" and s.companyId=").append(salescontract.getCompanyId());
			}
			if(salescontract.getId()!=null){
				sb.append(" and s.id!=").append(salescontract.getId());
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
	public int deleteSalesProductsByContractId(Long contractId) throws SystemException{
		StringBuffer sb=new StringBuffer();
		sb.append(" delete SalesProduct ");
		sb.append(" where contractId=").append(contractId);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}
	@Override
	public int deleteSalesPacksByContractId(Long contractId) throws SystemException{
		StringBuffer sb=new StringBuffer();
		sb.append(" delete SalesPack ");
		sb.append(" where contractId=").append(contractId);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}
	@Override
	public List<SalesProduct> findSalesProducts(Long contractId)throws SystemException{
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(SalesProduct.class);
		if(contractId!=null){
			criteria.add(Restrictions.eq("contractId", contractId));
		}
		return criteria.list();
	}
	@Override
	public List<SalesPack> findSalesPacks(Long contractId)throws SystemException {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(SalesPack.class);
		if(contractId!=null){
			criteria.add(Restrictions.eq("contractId", contractId));
		}
		return criteria.list();
	}

	@Override
	public SalesProduct getSalesProductByProductId(Long productId,Long channelId,
			Long companyId) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" from SalesProduct as sp");
		sb.append(" where 1=1");
		if(productId!=null){
			sb.append(" and sp.productId=").append(productId);
		}
		sb.append(" and sp.contractId in (");
		sb.append(" select id from Salescontract");
		sb.append(" where validDate<='").append(DateUtil.formatToday()).append("'");
		sb.append(" and matureDate>='").append(DateUtil.formatToday()).append("'");
		sb.append(" and status=1");
		if(companyId!=null){
			sb.append(" and companyId=").append(companyId);
		}
		if(channelId!=null){
			sb.append(" and channelId=").append(channelId);
		}
		sb.append(" order by stamp desc");
		sb.append(")");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		List<SalesProduct> list=query.list();
		if(list!=null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<HashMap<String, Object>> findSalesProductsByContractId(
			Long contractId) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select new map(c.id as id,c.contractId as contractId");
		hqlSb.append(" ,c.productId as productId,p.code as productCode,p.name as productName,p.norm as norm,c.num as num,c.price as price");
		hqlSb.append(" ,c.fixFee as fixFee,c.remark as remark)");
		hqlSb.append(" from SalesProduct as c , Product as p");
		hqlSb.append(" where c.productId=p.id ");
		if (contractId!=null) {
			hqlSb.append(" and c.contractId=").append(contractId);
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findSalesPacksByContractId(
			Long contractId) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" from SalesPack as s");
		hqlSb.append(" where 1=1 ");
		if (contractId!=null) {
			hqlSb.append(" and s.contractId=").append(contractId);
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		return query.list();
	}

	@Override
	public int getMaxSalesContractNo(Long companyId, String date)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select max(substring(s.code,length(s.code)-3,4)) from Salescontract as s");
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
	public int updateStatus(Salescontract salescontracts)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" update Salescontract ");
		sb.append(" set status=").append(salescontracts.getStatus());
		if(salescontracts.getCheckUserId()!=null){
			sb.append(" ,checkUserId=").append(salescontracts.getCheckUserId());
			//sb.append(" ,userId=").append(salescontracts.getUserId());
		}
		/*if(salescontracts.getUserName()!=null){
			sb.append(" ,userName='").append(salescontracts.getUserName()).append("'");
		}*/
		if(salescontracts.getCheckStamp()!=null){
			sb.append(" ,checkStamp='").append(DateUtil.format(salescontracts.getCheckStamp(),DateUtil.YMD_DASH_WITH_FULLTIME24)).append("'");
		}
		sb.append(" where id=").append(salescontracts.getId());
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}

	@Override
	public int updateDetaisStatus(Salescontract salescontracts)
			throws SystemException {
		StringBuffer phqlSb=new StringBuffer();
		phqlSb.append(" update SalesProduct ");
		phqlSb.append(" set status=").append(salescontracts.getStatus());
		phqlSb.append(" where contractId=").append(salescontracts.getId());
		Query query = sessionFactory.getCurrentSession().createQuery(phqlSb.toString());  
		return query.executeUpdate();
	}

	@Override
	public List<HashMap<String, Object>> findSalesContractProducts(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select sc.id,sc.code ,sc.name ,date_format(sc.sign_date,'%Y-%m-%d') as signDate");
		sb.append(",date_format(sc.valid_date,'%Y-%m-%d') as validDate,date_format(sc.mature_date,'%Y-%m-%d') as matureDate,sc.status as status,sc.remark as remark");
		sb.append(" ,p.name as productName,p.code as roductCode,p.norm,sp.price,sp.fix_fee as fixFee");
		sb.append(" from t_sel_sales_product as sp left join t_sel_product as p on sp.product_id=p.id ");
		sb.append(" inner join t_sel_salescontract as sc on sp.contract_id=sc.id ");
		sb=getCond4SalesContractProducts(sb,conditionMap);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
		query.setMaxResults(pageSize);
		return query.list();
	}

	@Override
	public int countSalesContractProducts(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(*)");
		sb.append(" from t_sel_sales_product as sp left join t_sel_product as p on sp.product_id=p.id ");
		sb.append(" inner join t_sel_salescontract as sc on sp.contract_id=sc.id ");
		sb = getCond4SalesContractProducts(sb, conditionMap);
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	 private StringBuffer getCond4SalesContractProducts(StringBuffer sb,Map<String, Object> conditionMap){
		 sb.append(" where  1=1 ");
		 if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("name"))){//合同名称
					sb.append(" and sc.name like '").append(StringUtils.replaceSqlKey(conditionMap.get("name"))).append("%'");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("productId"))){//商品id
					sb.append(" and sp.product_id=").append(conditionMap.get("productId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("channelId"))){//代理商ID
					sb.append(" and sp.channel_id=").append(conditionMap.get("channelId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("orgId"))){//部门
					sb.append(" and sc.org_id=").append(conditionMap.get("orgId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){//公司
					sb.append(" and sc.company_id=").append(conditionMap.get("companyId"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("status"))){//状态
					sb.append(" and sp.status=").append(conditionMap.get("status"));
				}
				if(conditionMap.get("startDate")!=null && StringUtils.isNotBlank(conditionMap.get("startDate").toString())){//开始日期
					sb.append(" and sp.valid_date <='").append(conditionMap.get("startDate")).append("'");
				}
				if(conditionMap.get("endDate")!=null && StringUtils.isNotBlank(conditionMap.get("endDate").toString())){//结束日期
					sb.append(" and sp.mature_date >='").append(conditionMap.get("endDate")).append(" 23:59:59'");
				}
			}
			return sb;
	    }

}

