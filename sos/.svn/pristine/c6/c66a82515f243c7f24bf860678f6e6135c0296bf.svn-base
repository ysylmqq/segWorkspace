package com.gboss.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.TaskDao;
import com.gboss.pojo.ProductRelation;
import com.gboss.pojo.Task;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * 
 * @ClassName:TaskDaoImpl.java
 * @Description: 任务单管理数据持久层实现类
 * @author: bzhang
 * @date :2014-3-21
 */
@Repository("taskDao")  
@Transactional 
public class TaskDaoImpl extends BaseDaoImpl implements TaskDao {
	
	
	@Override
	public int getMaxTaskNo(Long orgId, String date)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append("select max(substring(s.bill_no,length(s.bill_no)-3,4)) from Task as s");
		sb.append(" where 1=1");
		if(orgId!=null){
			sb.append(" and s.org_id=").append(orgId);
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
	public int countTasksPage(Map<String, Object> conditionMap, Long companyId,
			Long userId) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select r.id ");
		sb=getConditionHqlPage(sb, conditionMap, companyId, userId);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list().size();
	}
	
	
	@Override
	public int countTasks(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(r.id) ");
		sb=getConditionHql(sb,conditionMap, false);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	
	@Override
	public int countTasksforReservation(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(t.id) ");
		sb=getConditionHqlForReservation(sb,conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	@Override
	public List<HashMap<String, Object>> findTasks(Map<String, Object> conditionMap, String order,
			boolean isDesc, int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select r.id as id, r.symptom as symptom, r.note as note, r.billnum as billnum, r.type as type, r.billtype as billtype, r.status as status, r.time as time, group_concat(de.electrician_name) as eNames, ");
		sb.append(" r.managerName as managerName, r.requirements as requirements, r.place as place, r.isoffice as isoffice, ");
		sb.append(" r.productName as productName, r.customerId as customerId, r.customerName as customerName, ");
		sb.append(" r.carNum as carNum, r.vStatus as vStatus, r.code as code,r.color as color, r.cName as cName, de.dispatch_id as dispatchId ");
		sb=getConditionHql(sb,conditionMap, true);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	
	
	@Override
	public List<HashMap<String, Object>> findTasksPage(Map<String, Object> conditionMap, String order,
			boolean isDesc, int pn, int pageSize, Long companyId, Long userId) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select r.id as id, r.phone as phone, r.symptom as symptom, r.note as note, r.billnum as billnum, r.type as type, r.status as status, r.time as time,");
		sb.append(" r.managerName as managerName, r.requirements as requirements, r.place as place, r.isoffice as isoffice, ");
		sb.append(" r.productName as productName, r.customerId as customerId, r.customerName as customerName, r.subco_no as subco_no, ");
		sb.append(" r.carNum as carNum, r.vStatus as vStatus, r.code as code,r.color as color, r.cName as cName");
		sb=getConditionHqlPage(sb,conditionMap, companyId, userId);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	
	
	
	@Override
	public List<HashMap<String, Object>> findTasksForReservation(Map<String, Object> conditionMap, String order,
			boolean isDesc, int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select t.id as id, t.billno as billnum, t.type as type, t.status as status, t.time as time, ");
		sb.append(" t.manager_name as managerName, t.requirements as requirements, t.place as place, t.isoffice as isoffice, ");
		sb.append(" t.product_name as productName, t.customer_id as customerId, u.customer_name as customerName, ");
		sb.append(" v.number_plate as carNum, v.vehicle_status as vStatus, v.code as code, v.color as color, c.name as cName ");
		sb=getConditionHqlForReservation(sb,conditionMap);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	
	 private StringBuffer getConditionHql(StringBuffer sb,Map conditionMap, Boolean flag){
			sb.append(" from ( SELECT t.id AS id,  t.symptom as symptom, t.note as note, t.bill_no AS billnum, t.task_type AS type,  t.status AS status, t.stamp AS time,t.sales AS managerName,");
			sb.append(" t.requirements AS requirements, t.place AS place,t.isoffice AS isoffice,t.product_name AS productName,");
			sb.append(" t.customer_id AS customerId,u.customer_name AS customerName,v.plate_no AS carNum,v.vehicle_status AS vStatus,v.vin AS CODE, ");
			sb.append(" v.color AS color,c.model_name AS cName from t_ba_task t, t_ba_customer u, t_ba_vehicle v, t_ba_model c where ");
			sb.append(" t.customer_id = u.customer_id and u.customer_id = v.customer_id and v.vehicle_type = c.model_id  ");
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("tStatus"))){
					sb.append(" and t.status>").append(conditionMap.get("tStatus"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("customerName"))){
					sb.append(" and u.customer_name like '%").append(conditionMap.get("customerName")).append("%'");
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("carNum"))){
					sb.append(" and v.plate_no='").append(conditionMap.get("carNum")).append("'");
				}
				
				/*if(StringUtils.isNotNullOrEmpty(conditionMap.get("eNames"))){
					sb.append(" and eNames like '%").append(conditionMap.get("eNames")).append("%'");
				}*/
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("type"))){
					sb.append(" and t.type = ").append(conditionMap.get("type"));
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("status"))){
					sb.append(" and t.status=").append(conditionMap.get("status"));
				}
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate")) && StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
					sb.append(" and t.stamp >='").append(conditionMap.get("startDate")).append("'");
					sb.append(" and t.stamp <='").append(conditionMap.get("endDate")).append("'");
				}
				sb.append(" )r ");
				if(flag){
					sb.append(" left join t_ba_dispatch_electrician de ON de.task_id = r.id  GROUP BY r.id");
				}
			}
			return sb;
	    }
	 
	 
	 
	 
	 
	 private StringBuffer getConditionHqlPage(StringBuffer sb,Map conditionMap,  Long companyId, Long userId){
			sb.append(" from ( SELECT t.task_id AS id,  t.symptom as symptom, t.note as note, t.bill_no AS billnum, t.task_type AS type, t.status AS status, t.stamp AS time,t.sales AS managerName,");
			sb.append(" t.requirements AS requirements, t.phone as phone, t.place AS place,t.is_office AS isoffice,t.product_name AS productName,t.subco_no AS subco_no,");
			sb.append(" t.customer_id AS customerId,u.customer_name AS customerName,v.plate_no AS carNum,v.vehicle_status AS vStatus,v.vin AS CODE, ");
			sb.append(" v.color AS color,c.model_name AS cName from t_ba_task t, t_ba_customer u, t_ba_vehicle v, t_ba_model c where ");
			sb.append(" t.customer_id = u.customer_id and t.vehicle_id = v.vehicle_id and v.model = c.model_id ");
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("tStatus"))){
					sb.append(" and t.status>").append(conditionMap.get("tStatus"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("customerName"))){
					sb.append(" and u.customer_name like '%").append(conditionMap.get("customerName")).append("%'");
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("carNum"))){
					sb.append(" and v.plate_no='").append(conditionMap.get("carNum")).append("'");
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("type"))){
					sb.append(" and t.task_type = ").append(conditionMap.get("type"));
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("status"))){
					sb.append(" and t.status=").append(conditionMap.get("status"));
				}
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate")) && StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
					sb.append(" and t.stamp >='").append(DateUtil.dateforSearch(conditionMap, "startDate")).append("'");
					sb.append(" and t.stamp <='").append(DateUtil.dateforSearch(conditionMap, "endDate")).append("'");
				}
				sb.append(" )r ");
					sb.append(" left join t_ba_taskflow f ON  r.id = f.task_id ");
					sb.append(" WHERE r.subco_no = ").append(companyId);
					sb.append(" OR (FIND_IN_SET("+userId.toString() +", f.charger_id)) group by r.id  ");
			}
			return sb;
	    }
	 
	 
	 private StringBuffer getConditionHqlForReservation(StringBuffer sb,Map conditionMap) {
		 	sb.append(" from t_ba_task t, t_ba_customer u, t_ba_vehicle v, t_ba_cartype c ");
			sb.append(" where t.customer_id = u.customer_id and u.customer_id = v.customer_id and v.cartype = c.id ");
		 	//预约和工单管理共用
			//sb.append(" and t.status > ").append(SystemConst.DELETEED_TASK);
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("tStatus"))){
					sb.append(" and t.status>").append(conditionMap.get("tStatus"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("customerName"))){
					sb.append(" and u.customer_name like '%").append(conditionMap.get("customerName")).append("%'");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("type"))){
					sb.append(" and t.type=").append(conditionMap.get("type"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("status"))){
					sb.append(" and t.status=").append(conditionMap.get("status"));
				}
				
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate")) && StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
					sb.append(" and t.time >='").append(conditionMap.get("startDate")).append("'");
					sb.append(" and t.time <='").append(conditionMap.get("endDate")).append("'");
				}
				
			}
			return sb;
	    }


	@Override
	public List<HashMap<String, Object>> getTaskReHistory(Long id)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select r.person as person, r.phone as phone, r.place as place, r.reservation_time as time, t.type as type ");
		sb.append(" from t_ba_reservation r, t_ba_task t where r.task_id = t.id ");
		sb.append(" and r.task_id = ").append(id);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int deleteTasks(List<Long> ids) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update Task set status=:st where id in (:ids) ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		query.setParameter("st", SystemConst.DELETEED_TASK_STATUS);
		query.setParameterList("ids", ids); 
		return query.executeUpdate();
	}
	
	@Override
	public int endTasks(List<Long> ids) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update Task set status=:st where id in (:ids) ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		query.setParameter("st", SystemConst.END_RES_TASK_STATUS);
		query.setParameterList("ids", ids); 
		return query.executeUpdate();
	}



	@Override
	public List<Task> getTaskByElctrician(Long id) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select t  from Task t, DispatchElectrician d  ");
		hqlSb.append(" where d.task_id = t.id ");
		hqlSb.append(" and d.electrician =").append(id);
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		return query.list();
	}


	@Override
	public HashMap<String, Object> getTaskDetailMsg(Long id)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select  u.customer_name as customerName , v.plate_no as carNum ,uu.call_letter as letter, c.model_name as cName ,group_concat(p.phone) as phone, t.symptom as symptom, t.place as place ");
		sb.append(" from t_ba_task t,t_ba_customer u,t_ba_model c,t_ba_vehicle v,t_ba_unit uu,t_ba_linkman p ");
		sb.append(" where t.customer_id = u.id and t.vehicle_id= v.vehicle_id and c.model_id = v.model  ");
		sb.append(" and uu.vehicle_id = v.vehicle_id and p.customer_id = u.customer_id ");
		sb.append(" and t.id = ").append(id);
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(query.list().size() > 0){
			return (HashMap<String, Object>) query.list().get(0);
		}else
			return null;
	}
	
	@Override
	public Task findTaskByDistach(Long id) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select t from Task as p, DispatchTask d where d.task_id = t.id ");
		hqlSb.append(" and p.id = ").append(id);
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString()); 
		List<Task> list=query.list();
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<Task> getTaskByVId(Long vehicle_id, Integer type)
			throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select t  from Task t ");
		hqlSb.append(" where t.vehicle_id =").append(vehicle_id);
		hqlSb.append(" and  t.task_type =").append(type);
		hqlSb.append(" and  t.status >").append(SystemConst.DELETEED_TASK);
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		return query.list();
	}

	@Override
	public int countTasksBycl(Map<String, Object> conditionMap) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(t.task_id) ");
		sb=getConditionBycl(sb,conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	
	 
	 private StringBuffer getConditionBycl(StringBuffer sb,Map conditionMap){
			sb.append(" FROM t_ba_unit u,t_ba_customer c,t_ba_task t,t_ba_vehicle v,t_ba_model m ");
			sb.append(" WHERE u.customer_id = c.customer_id AND u.vehicle_id = v.vehicle_id AND u.vehicle_id = t.vehicle_id AND v.model = m.model_id ");
			sb.append(" and  t.status >").append(SystemConst.DELETEED_TASK_STATUS);
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("call_letter"))){
					sb.append(" and u.call_letter ='").append(conditionMap.get("call_letter")).append("'");
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("type"))){
					sb.append(" and t.task_type = ").append(conditionMap.get("type"));
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("status"))){
					sb.append(" and t.status=").append(conditionMap.get("status"));
				}
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate")) && StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
					sb.append(" and t.stamp >='").append(DateUtil.dateforSearch(conditionMap, "startDate")).append("'");
					sb.append(" and t.stamp <='").append(DateUtil.dateforSearch(conditionMap, "endDate")).append("'");
				}
			}
			return sb;
	    }

	@Override
	public List<HashMap<String, Object>> findTasksBycl(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT t.task_id as id,t.bill_no AS bill_no,t.task_type AS type,t.STATUS AS status, t.product_name as product_name, t.color as color,");
		sb.append(" t.symptom as symptom, t.place as place,t.requirements as requirements, t.note as note, t.is_office as is_office, ");
		sb.append(" t.vehicle_id AS vehicle_id,t.phone AS phone,t.stamp AS stamp,c.customer_name AS customer_name, t.sales as sales,");
		sb.append(" v.plate_no AS car_num,m.model_name AS model_name");
		sb=getConditionBycl(sb,conditionMap);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}


}
