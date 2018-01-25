package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.StopDao;
import com.gboss.pojo.Stop;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:StopDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-7-16 下午5:25:38
 */
@Repository("StopDao")  
@Transactional
public class StopDaoImpl extends BaseDaoImpl implements StopDao {

	@Override
	public List<Stop> getListByid(Long vehicle_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Stop.class); 
		if(vehicle_id!=null){
			criteria.add(Restrictions.eq("vehicle_id", vehicle_id));
		}
		List<Stop> list = criteria.list();
		return list;
	}

	@Override
	public void deleteStop(Long vehicle_id, Integer type) {
		String hql = "delete from Stop where vehicle_id="+vehicle_id+" and type="+type;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public boolean isExist(Long vehicle_id, Integer type) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Stop.class); 
		if(vehicle_id!=null){
			criteria.add(Restrictions.eq("vehicle_id", vehicle_id));
		}
		if(type!=null){
			criteria.add(Restrictions.eq("type", type));
		}
		List<Stop> list = criteria.list();
		if(list.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public List<HashMap<String, Object>> findStops(Map<String, Object> params) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT s.stamp,s.vehicle_id,s.unit_id,s.fee_sedate,c.customer_name, s.type_id,");
		sb.append("case s.type_id when 1 then '车子将/已转卖/报废' when 2 then '产品质量问题' when 3 then '4S店服务品质问题' when 4 then '服务中心服务质量问题' when 5 then '营业处服务质量问题' when 6 then '使用价值不大' when 7 then '个人原因' when 8 then '其他原因' when 9 then '暂时停止' end as type_name ,");
		sb.append(" v.plate_no,u.sales,u.call_letter,u.fix_time,u.service_date,s.remark ");
		sb.append(" from t_ba_stop s,t_ba_vehicle v,t_ba_unit u,t_ba_customer c ");
		sb.append(" WHERE s.customer_id = c.customer_id and s.unit_id = u.unit_id ");
		sb.append("  and s.vehicle_id = v.vehicle_id and u.vehicle_id = v.vehicle_id and u.customer_id = c.customer_id ");
		sb.append(" and s.subco_no =").append(params.get("subcoNo"));
		if(Utils.isNotNullOrEmpty(params.get("date"))){
			sb.append(" and  date_format(s.stamp,'%Y-%m')  = '").append(params.get("date")).append("'");
		}
		if(Utils.isNotNullOrEmpty(params.get("startDate"))){
			sb.append(" and s.stamp>='").append(params.get("startDate")).append(" 00:00:00'");
		}
		if(Utils.isNotNullOrEmpty(params.get("endDate"))){
			sb.append(" and s.stamp<='").append(params.get("endDate")).append(" 23:59:59'");
		}
		if(Utils.isNotNullOrEmpty(params.get("type"))){
			sb.append(" and s.type =").append(params.get("type"));
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countStops(Map<String, Object> params) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT count(s.unit_id) ");
		sb = buildStopsSql(sb, params);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	private StringBuffer buildStopsSql(StringBuffer sb, Map<String, Object> params) {
		sb.append(" from t_ba_stop s,t_ba_vehicle v,t_ba_unit u,t_ba_customer c ");
		sb.append(" WHERE s.customer_id = c.customer_id and s.unit_id = u.unit_id ");
		sb.append("  and s.vehicle_id = v.vehicle_id and u.vehicle_id = v.vehicle_id and u.customer_id = c.customer_id ");
		if(Utils.isNotNullOrEmpty(params.get("subcoNo"))){
			sb.append(" and s.subco_no =").append(params.get("subcoNo"));
		}
		String companyCode = Utils.isNullOrEmpty(params.get("companyCode")) ? "" : params.get("companyCode").toString();
		if(!"0".equals(companyCode) && StringUtils.isNotBlank(companyCode)){
			sb.append(" and c.subco_code='").append(companyCode).append("'");
		}
		if(Utils.isNotNullOrEmpty(params.get("date"))){
			sb.append(" and  date_format(s.stamp,'%Y-%m')  = '").append(params.get("date")).append("'");
		}
		if(Utils.isNotNullOrEmpty(params.get("startDate"))){
			sb.append(" and s.stamp>='").append(params.get("startDate")).append(" 00:00:00'");
		}
		if(Utils.isNotNullOrEmpty(params.get("endDate"))){
			sb.append(" and s.stamp<='").append(params.get("endDate")).append(" 23:59:59'");
		}
		if(Utils.isNotNullOrEmpty(params.get("type"))){
			sb.append(" and s.type =").append(params.get("type"));
		}
		if(Utils.isNotNullOrEmpty(params.get("unit_id"))){
			sb.append(" and u.unit_id =").append(params.get("unit_id"));
		}
		if(Utils.isNotNullOrEmpty(params.get("vehicle_id"))){
			sb.append(" and v.vehicle_id =").append(params.get("vehicle_id"));
		}
		return sb;
	}

	@Override
	public List<HashMap<String, Object>> findStopsPage(Map<String, Object> params, String order, boolean is_desc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT case s.type_id when 1 then '车子将/已转卖/报废' when 2 then '产品质量问题' when 3 then '4S店服务品质问题' when 4 then '服务中心服务质量问题' when 5 then '营业处服务质量问题' when 6 then '使用价值不大' when 7 then '个人原因' when 8 then '其他原因' when 9 then '暂时停止' end as type_name ,s.stamp,s.vehicle_id,s.unit_id,s.fee_sedate,c.customer_name, s.type_id,");
		sb.append(" v.plate_no,u.sales,u.call_letter,u.fix_time,u.service_date,s.remark ");
		sb = buildStopsSql(sb, params);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pageNo>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pageNo, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
}

