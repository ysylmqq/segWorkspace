package com.gboss.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.SysDao;
import com.gboss.pojo.SysValue;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;
import com.gboss.util.page.PageUtil;
/**
 * @Package:com.chinagps.fee.dao.impl
 * @ClassName:SysDaoImpl
 * @Description:系统参数类型、值  数据持久层实现类
 * @author:zfy
 * @date:2014-6-11 上午9:33:50
 */
@Repository("sysDao") 
public class SysDaoImpl  extends BaseDaoImpl implements SysDao {

	@Override
	public List<SysValue> findSysValue(SysValue sysValue)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" from SysValue");
		sb.append(" where 1=1");
		if(sysValue!=null){
			if(StringUtils.isNotNullOrEmpty(sysValue.getStype())){
				sb.append(" and stype=").append(sysValue.getStype());
			}
			if(StringUtils.isNotNullOrEmpty(sysValue.getIsDel())){
				sb.append(" and isDel=").append(sysValue.getIsDel());
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.list();
	}

	@Override
	public List<SysValue> findAllBank() {
		StringBuffer sb=new StringBuffer();
		sb.append(" from SysValue");
		sb.append(" where 1=1 and stype in (3010,3011) and isDel = 0");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return query.list();
	}
	
	@Override
	public List<Map<String,Object>> findOplogs(Map conditionMap, String order,
			boolean isDesc, int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select o.user_id,o.user_name,o.subco_no,o.stamp,o.model_id,v.sname,o.op_type,o.remark");
		sb.append(" from t_sys_operatelog o left join t_sys_value v on");
		sb.append(" o.model_id=v.svalue and v.stype=998");
		sb = getCondition4Oplogs(sb, conditionMap);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append("  order by o.stamp desc");
		}
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countOplogs(Map conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(*) from Operatelog as o");
		sb=getCondition4Oplogs(sb,conditionMap);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());  
		return ((Long)query.uniqueResult()).intValue();
	}

	 private StringBuffer getCondition4Oplogs(StringBuffer sb,Map conditionMap){
			sb.append(" where o.model_id between 20000 and 29999");
			if(conditionMap!=null){
				if(Utils.isNotNullOrEmpty(conditionMap.get("startDate"))){
					sb.append(" and o.stamp>='").append(conditionMap.get("startDate")).append(" 00:00:00'");
				}
				if(Utils.isNotNullOrEmpty(conditionMap.get("endDate"))){
					sb.append(" and o.stamp<='").append(conditionMap.get("endDate")).append(" 23:59:59'");
				}
				if(Utils.isNotNullOrEmpty(conditionMap.get("subcoNo"))){
					sb.append(" and o.subco_no=").append(conditionMap.get("subcoNo"));
				}
				if(Utils.isNotNullOrEmpty(conditionMap.get("userId"))){
					sb.append(" and o.user_id=").append(conditionMap.get("userId"));
				}
				if(Utils.isNotNullOrEmpty(conditionMap.get("userName"))){
					sb.append(" and o.user_name like '%").append(StringUtils.replaceSqlKey(conditionMap.get("userName"))).append("%'");
				}
				if(Utils.isNotNullOrEmpty(conditionMap.get("modelId"))){
					sb.append(" and o.model_id=").append(conditionMap.get("modelId"));
				}
				if(Utils.isNotNullOrEmpty(conditionMap.get("opType"))){
					sb.append(" and o.op_type=").append(conditionMap.get("opType"));
				}
			}
			return sb;
	    }

}
