package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.DispatchElectricianDao;
import com.gboss.pojo.DispatchElectrician;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:DispatchElectricianDaoImpl
 * @Description:派工單和電工数据持久层实现类
 * @author:bzhang
 * @date:2014-3-27 下午6:32:39
 */
@Repository("dispatchElectricianDao")  
@Transactional
public class DispatchElectricianDaoImpl extends BaseDaoImpl implements
		DispatchElectricianDao {

	@Override
	public HashMap<String, Object> getElectriciansDetail(Long userId, String time) {
		StringBuffer sb=new StringBuffer();
		sb.append(" select de.phone as phone, dt.start_time as time, dt.duration as duration, ");
		sb.append(" t.billno as billno , t.type as type, t.place as place ");
		sb.append(" from  t_ba_dispatch_electrician de, t_ba_dispatch dt, t_ba_task t where ");
		sb.append(" de.dispatch_id = dt.id and dt.task_id = t.id");
		sb.append(" and de.electrician = ").append(userId);
		sb.append(" and dt.dispatch_time = '").append(time).append("'");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(query.list().size() !=0){
			return (HashMap<String, Object>) query.list().get(0);
		}
		return null;
	}

	@Override
	public List<HashMap<String, Object>> getElectriciansBytaskId(Long taks_id) {
		StringBuffer sb=new StringBuffer();
		sb.append(" select de.electrician as id, de.electrician_name as name ");
		sb.append(" from t_ba_dispatch_electrician de ");
		sb.append("  where de.task_id = ").append(taks_id);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> getElectriciansByDispatchId(
			Long dispatch_id) {
		StringBuffer sb=new StringBuffer();
		sb.append(" select de.electrician as id, de.electrician_name as name ");
		sb.append(" from t_ba_dispatch_electrician de ");
		sb.append("  where de.dispatch_id = ").append(dispatch_id);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		return query.list();
	}

}

