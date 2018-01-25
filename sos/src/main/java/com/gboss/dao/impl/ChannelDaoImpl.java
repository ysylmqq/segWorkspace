package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.ChannelDao;
import com.gboss.pojo.Channel;
import com.gboss.pojo.ChannelGroup;
import com.gboss.pojo.Channelcontacts;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:ChannelDaoImpl
 * @Description:代理商管理数据持久层实现类
 * @author:zfy
 * @date:2013-8-26 下午2:39:28
 */
@Repository("channelDao")  
@Transactional 
public class ChannelDaoImpl extends BaseDaoImpl implements ChannelDao {

	@Override
	public int deleteContractsByChannelId(Long channelId)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" delete Channelcontacts ");
		sb.append(" where channelId=").append(channelId);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}

	@Override
	public int deleteSalesManagerByChannelId(Long channelId)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" delete ChannelSalesmanager ");
		sb.append(" where channelId=").append(channelId);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.executeUpdate();
	}

	@Override
	public List<HashMap<String, Object>> findChannels(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select c.id as id,c.name as name,c.address as address");
		sb.append(" ,c.month_sell as monthSell,c.dict_id as dictId,d.sname as dictName");
		sb.append(" ,c.group_id as groupId,cg.cn_name as groupName,c.company_id as companyId,c.remark as remark");
		sb.append(" from t_sel_channel as c left join t_sys_value as d on c.dict_id=d.value_id and d.stype=1000");
		sb.append(" left join t_sel_channel_group as cg on c.group_Id=cg.id");
		sb.append(" where 1=1");
		sb = getConditionHql(sb,conditionMap);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countChannels(Map<String, Object> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(c.id)");
		sb.append(" from t_sel_channel as c left join t_sys_value as d on c.dict_id=d.value_id and d.stype=1000");
		sb.append(" left join t_sel_channel_group as cg on c.group_Id=cg.id");
		sb.append(" where 1=1");
		sb=getConditionHql(sb,conditionMap);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	public StringBuffer getConditionHql(StringBuffer hqlSb,Map<String,Object> conditionMap){
		if(conditionMap!=null){
			if(conditionMap.get("name")!=null && StringUtils.isNotBlank(conditionMap.get("name").toString())){
				hqlSb.append(" and c.name like '%").append(StringUtils.replaceSqlKey(conditionMap.get("name"))).append("%'");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("dictId"))){
				hqlSb.append(" and c.dict_id=").append(conditionMap.get("dictId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){
				hqlSb.append(" and c.company_id=").append(conditionMap.get("companyId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("orgId"))){
				hqlSb.append(" and c.org_id=").append(conditionMap.get("orgId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("exceptId"))){
				hqlSb.append(" and c.id<>").append(conditionMap.get("exceptId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("exceptDicId"))){
				hqlSb.append(" and c.dict_id<>").append(conditionMap.get("exceptDicId"));
			}
		}
		return hqlSb;
	}

	@Override
	public int updateSalesManagersIsDel(Long channelId)
			throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update ChannelSalesmanager as c");
		hqlSb.append(" set isdel=1 ");
		hqlSb.append(" where channelId=").append(channelId);
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		return query.executeUpdate();
	}

	@Override
	public List<HashMap<String, Object>> findChannels(Map<String, Object> conditionMap)
			throws SystemException {
		return findChannels(conditionMap,null,false,0,0);
	}

	@Override
	public List<Channelcontacts> findContactsByChannelId(Long channelId)
			throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" from Channelcontacts as c");
		if (channelId!=null) {
			hqlSb.append(" where c.channelId=").append(channelId);
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findChannelManagers(Map<String, Object> conditionMap,
			String order, boolean isDesc, int pageNo, int pageSize)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select new map(csm.id as id,csm.channelId as channelId,c.name as channelName");
		sb.append(" ,csm.managerId as managerId,csm.managerName as managerName,csm.isdel as isdel,csm.stamp as stamp,csm.remark as remark) ");
		sb.append(" from ChannelSalesmanager as csm , Channel as c");
		sb.append(" where csm.channelId=c.id ");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("channelId"))){//代理商id
				sb.append(" and csm.channelId=").append(conditionMap.get("channelId"));
			}
			if(conditionMap.get("name")!=null){//代理商名称
				sb.append(" and c.name like '%").append(StringUtils.replaceSqlKey(conditionMap.get("name"))).append("%'");
			}
			if(conditionMap.get("remark")!=null && StringUtils.isNotBlank((String)conditionMap.get("remark"))){//备注
				sb.append(" and csm.remark like '%").append(StringUtils.replaceSqlKey(conditionMap.get("remark"))).append("%'");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("managerId"))){//销售经理id
				sb.append(" and csm.managerId=").append(conditionMap.get("managerId"));
			}
			if(conditionMap.get("isdel")!=null){//是否过时
				sb.append(" and csm.isdel=").append(conditionMap.get("isdel"));
			}
			//编辑时判断是否存在
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("id2"))){//id
				sb.append(" and csm.id!=").append(conditionMap.get("id2"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){
				sb.append(" and c.companyId=").append(conditionMap.get("companyId"));
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
			sb.append(" order by stamp desc");
		}
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		if(pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countChannelManagers(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(*)");
		sb.append(" from ChannelSalesmanager as csm , Channel as c");
		sb.append(" where csm.channelId=c.id ");
		if(conditionMap!=null){
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("channelId")) ){//代理商id
				sb.append(" and csm.channelId=").append(conditionMap.get("channelId"));
			}
			if(conditionMap.get("name")!=null){//代理商名称
				sb.append(" and c.name like '%").append(StringUtils.replaceSqlKey(conditionMap.get("name"))).append("%'");
			}
			if(conditionMap.get("remark")!=null && StringUtils.isNotBlank((String)conditionMap.get("remark"))){//备注
				sb.append(" and csm.remark like '%").append(StringUtils.replaceSqlKey(conditionMap.get("remark"))).append("%'");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("managerId")) ){//销售经理id
				sb.append(" and csm.managerId=").append(conditionMap.get("managerId"));
			}
			if(conditionMap.get("isdel")!=null){//是否过时
				sb.append(" and csm.isdel=").append(conditionMap.get("isdel"));
			}
			//编辑时判断是否存在
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("id2"))){//id
				sb.append(" and csm.id!=").append(conditionMap.get("id2"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){
				sb.append(" and c.companyId=").append(conditionMap.get("companyId"));
			}
		}
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		return ((Long)query.uniqueResult()).intValue();
	}

	@Override
	public int deleteSalesManagers(List<Long> idList) throws SystemException {
		String hql="delete ChannelSalesmanager WHERE id IN (:alist)";  
		Query query = sessionFactory.getCurrentSession().createQuery(hql);  
		query.setParameterList("alist", idList); 
		return query.executeUpdate();
	}

	@Override
	public boolean checkChannelName(Channel channel) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(c.id) from Channel as c");
		sb.append(" where 1=1 ");
		if(channel!=null){
			if(StringUtils.isNotBlank(channel.getName())){
				sb.append(" and c.name='").append(channel.getName()).append("'");
			}
			if(channel.getId()!=null){
				sb.append(" and c.id!=").append(channel.getId());
			}
			if(channel.getCompanyId()!=null){
				sb.append(" and c.companyId=").append(channel.getCompanyId());
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
	public boolean checkChannelGroupName(ChannelGroup channelGroup, boolean isEn)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(c.id) from ChannelGroup as c");
		sb.append(" where 1=1 ");
		if(channelGroup!=null){
			//英文
			if(StringUtils.isNotBlank(channelGroup.getEnName()) && isEn){
				sb.append(" and c.enName='").append(channelGroup.getEnName()).append("'");
			}
			//中文
			if(StringUtils.isNotBlank(channelGroup.getCnName()) && !isEn){
				sb.append(" and c.cnName='").append(channelGroup.getCnName()).append("'");
			}
			if(channelGroup.getId()!=null){
				sb.append(" and c.id!=").append(channelGroup.getId());
			}
			if(channelGroup.getCompanyId()!=null){
				sb.append(" and c.companyId=").append(channelGroup.getCompanyId());
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
	public boolean checkChannelGroupIsUsing(Long groupId)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(c.id) from Channel as c");
		sb.append(" where 1=1 ");
		if(groupId!=null){
			sb.append(" and c.groupId=").append(groupId);
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		if((Long)query.uniqueResult()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<HashMap<String, Object>> findChannelGroups(
			Map<String, Object> conditionMap) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select new map(c.id as id,c.enName as enName,c.cnName as cnName");
		hqlSb.append(" ,c.orgId as orgId,c.companyId as companyId,c.userId as userId");
		hqlSb.append(" ,c.stamp as stamp,c.remark as remark)");
		hqlSb.append(" from ChannelGroup as c");
		hqlSb.append(" where 1=1");
		if(conditionMap!=null){
			if(conditionMap.get("enName")!=null){
				hqlSb.append(" and c.enName like '%").append(StringUtils.replaceSqlKey(conditionMap.get("enName"))).append("%'");
			}
			if(conditionMap.get("cnName")!=null){
				hqlSb.append(" and c.cnName like '%").append(StringUtils.replaceSqlKey(conditionMap.get("cnName"))).append("%'");
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("companyId"))){
				hqlSb.append(" and c.companyId=").append(conditionMap.get("companyId"));
			}
			if(StringUtils.isNotNullOrEmpty(conditionMap.get("orgId"))){
				hqlSb.append(" and c.orgId=").append(conditionMap.get("orgId"));
			}
		}
		hqlSb.append(" order by c.stamp desc");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		return query.list();
	}

}

