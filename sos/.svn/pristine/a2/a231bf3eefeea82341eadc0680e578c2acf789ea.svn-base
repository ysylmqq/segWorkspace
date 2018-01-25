package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.SupplyContractDao;
import com.gboss.pojo.SupplyBranch;
import com.gboss.pojo.SupplyDetails;
import com.gboss.pojo.Supplycontracts;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:SupplyContractDaoImpl
 * @Description:总部供货合同管理数据持久层实现类
 * @author:zfy
 * @date:2013-8-20 下午4:02:55
 */
@Repository("supplyContractDao")  
@Transactional  
public class SupplyContractDaoImpl extends BaseDaoImpl implements
		SupplyContractDao {

	@Override
	public boolean checkSupplyContractCode(Supplycontracts supplycontracts)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id) from Supplycontracts as s");
		sb.append(" where 1=1 ");
		if(supplycontracts!=null){
			if(supplycontracts.getCode()!=null){
				sb.append(" and s.code='").append(supplycontracts.getCode()).append("'");
			}
			if(supplycontracts.getId()!=null){
				sb.append(" and s.id!=").append(supplycontracts.getId());
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
	public boolean checkSupplyContractName(Supplycontracts supplycontracts)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(s.id) from Supplycontracts as s");
		sb.append(" where 1=1 ");
		if(supplycontracts!=null){
			if(StringUtils.isNotBlank(supplycontracts.getName())){
				sb.append(" and s.name='").append(supplycontracts.getName()).append("'");
			}
			if(supplycontracts.getId()!=null){
				sb.append(" and s.id!=").append(supplycontracts.getId());
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
	public int deleteDetailsByContractId(Long contractId)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" delete SupplyDetails ");
		sb.append(" where supplyId=").append(contractId);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}

	@Override
	public int deleteBranchsByContractId(Long contractId)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" delete SupplyBranch ");
		sb.append(" where supplyId=").append(contractId);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}

	@Override
	public List<SupplyDetails> findSupplyDetais(Long contractId)
			throws SystemException {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(SupplyDetails.class);
		if(contractId!=null){
			criteria.add(Restrictions.eq("supplyId", contractId));
		}
		List<SupplyDetails> result=criteria.list();
		return result;
	}


	@Override
	public List<Map<String, Object>> findSupplyContracts(
			Map<String, Object> map, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select new map(s.id as id,s.code as code,s.name as name");
		sb.append(" ,s.type as type,s.validDate as validDate,s.matureDate as matureDate,s.status as status");
		sb.append(" ,s.userId as userId,s.stamp as stamp,s.remark as remark)");
		sb.append(" from Supplycontracts as s");
		sb = getCondition(sb, map);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append(" order by stamp desc");
		}
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		if (pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public List<SupplyBranch> findSupplyBranchs(List<Long> contractIds)
			throws SystemException {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(SupplyBranch.class);
		if(contractIds!=null && !contractIds.isEmpty()){
			criteria.add(Restrictions.in("supplyId", contractIds));
		}
		List<SupplyBranch> result=criteria.list();
		return result;
	}

	@Override
	public Supplycontracts getSuppplyContractById(Long contractId) {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Supplycontracts.class);
		if(contractId!=null){
			criteria.add(Restrictions.eq("id", contractId));
		}
		Supplycontracts result=(Supplycontracts)criteria.uniqueResult();
		sessionFactory.getCurrentSession().flush();
		if(result!=null){
			sessionFactory.getCurrentSession().evict(result);
		}
		//将result从session中移除，则持久化对象，转成托管状态，再后面的修改或者重新赋值添加，就不会出现脏数据的情况
		//避免出现错误：identifier of an instance of com.gboss.pojo.Supplycontracts was altered from xx to yy
		return result;
	}
	@Override
	public int countSupplyContracts(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(*) from Supplycontracts as s ");
		sb = getCondition(sb, conditionMap);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return ((Long)query.uniqueResult()).intValue();
	}
	
	 private StringBuffer getCondition(StringBuffer sb,Map conditionMap){
			if(conditionMap!=null){
				sb.append(" where 1=1");
				if(conditionMap.get("validDate")!=null && !"null".equals(conditionMap.get("validDate"))){
					sb.append(" and s.validDate like '%").append(conditionMap.get("validDate")).append("%'");
				}
				if(conditionMap.get("matureDate")!=null && !"null".equals(conditionMap.get("matureDate"))){
					sb.append(" and s.matureDate like '%").append(conditionMap.get("matureDate")).append("%'");
				}
				if(conditionMap.get("status")!=null){
					sb.append(" and s.status=").append(conditionMap.get("status"));
				}
				if(conditionMap.get("nameOrCode")!=null){
					sb.append(" and (s.code like '%").append(StringUtils.replaceSqlKey(conditionMap.get("nameOrCode"))).append("%'");
					sb.append(" or s.name like '%").append(StringUtils.replaceSqlKey(conditionMap.get("nameOrCode"))).append("%')");
				}
				//分公司
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){
					sb.append(" and s.id in(").append("select supplyId from SupplyBranch where orgId=").append(conditionMap.get("companyId")).append(")");
				}
			}
			return sb;
		 } 

	@Override
	public List<HashMap<String, Object>> findContractProductsByContractId(
			Long contractId) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select new map(c.id as id,c.supplyId as supplyId");
		hqlSb.append(" ,c.productId as productId,p.name as productName,p.code as productCode,p.norm as norm,c.price as price");
		hqlSb.append(" ,c.stamp as stamp,c.remark as remark)");
		hqlSb.append(" from SupplyDetails as c , Product as p");
		hqlSb.append(" where c.productId=p.id ");
		if (contractId!=null) {
			hqlSb.append(" and c.supplyId=").append(contractId);
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		return query.list();
	}

	@Override
	public int getMaxSupplyContractNo(String date)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select max(substring(s.code,length(s.code)-3,4)) from Supplycontracts as s");
		sb.append(" where 1=1");
		if(StringUtils.isNotBlank(date)){
			sb.append(" and stamp like '").append(date).append("%'");
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
	public int updateStatus(Supplycontracts supplycontracts)
			throws SystemException {
		StringBuffer phqlSb=new StringBuffer();
		phqlSb.append(" update Supplycontracts ");
		phqlSb.append(" set status=").append(supplycontracts.getStatus());
		if(supplycontracts.getCheckUserId()!=null){
			phqlSb.append(" ,checkUserId=").append(supplycontracts.getCheckUserId());
		}
		if(supplycontracts.getCheckStamp()!=null){
			phqlSb.append(" ,checkStamp='").append(DateUtil.format(supplycontracts.getCheckStamp(),DateUtil.YMD_DASH_WITH_FULLTIME24)).append("'");
		}
		phqlSb.append(" where id=").append(supplycontracts.getId());
		Query query = sessionFactory.getCurrentSession().createQuery(phqlSb.toString());  
		return query.executeUpdate();
	}

	@Override
	public int updateDetaisStatus(Supplycontracts supplycontracts)
			throws SystemException {
		StringBuffer phqlSb=new StringBuffer();
		phqlSb.append(" update SupplyDetails ");
		phqlSb.append(" set status=").append(supplycontracts.getStatus());
	    phqlSb.append(" where supplyId=").append(supplycontracts.getId());
		Query query = sessionFactory.getCurrentSession().createQuery(phqlSb.toString());  
		return query.executeUpdate();
	}
}

