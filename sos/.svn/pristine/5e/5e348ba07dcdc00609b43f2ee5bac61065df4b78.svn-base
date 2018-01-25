package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.TaskFlowDao;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Onduty;
import com.gboss.pojo.TaskFlow;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:TaskFlowDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-18 上午9:54:04
 */
@Repository("taskFlowDao")  
@Transactional 
public class TaskFlowDaoImpl extends BaseDaoImpl implements TaskFlowDao {

	@Override
	public TaskFlow getTaskFlow(Long taskId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TaskFlow.class); 
		criteria.add(Restrictions.eq("task_id", taskId));
		if (criteria.list().size() != 0) {
			return (TaskFlow) criteria.list().get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getTaskFlowsDetail(Long task_id)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select f.operator as operator,f.charger as charger,f.org_name as orgNames,f.stamp as stamp ");
		sb.append(" from t_ba_taskflow f ");
		sb.append(" where f.task_id = ").append(task_id);
		sb.append(" order by f.stamp asc ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

}

