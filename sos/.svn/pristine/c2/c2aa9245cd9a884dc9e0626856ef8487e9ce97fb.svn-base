package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.DispatchTaskDao;
import com.gboss.pojo.DispatchTask;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:DispatchTaskDaoImpl
 * @Description:派工单数据持久层实现类
 * @author:bzhang
 * @date:2014-3-27 下午6:29:21
 */
@Repository("dispatchTaskDao")  
@Transactional
public class DispatchTaskDaoImpl extends BaseDaoImpl implements DispatchTaskDao {

	@Override
	public List<HashMap<String, Object>> getElectricians(Long taskId)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select  de.electrician as id, de.electrician_name  as name");
		sb.append(" from  t_ba_dispatch_electrician de ");
		sb.append("  where de.task_id = ").append(taskId);
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return query.list();
	}
	
	
	
	@Override
	public DispatchTask findTaskByDistach(Long id) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select d from Task as t, DispatchTask d where d.task_id = t.id ");
		hqlSb.append(" and t.id = ").append(id);
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString()); 
		List<DispatchTask> list=query.list();
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}



}

