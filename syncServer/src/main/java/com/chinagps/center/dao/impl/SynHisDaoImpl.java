package com.chinagps.center.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.chinagps.center.common.SystemException;
import com.chinagps.center.dao.SynHisDao;
import com.chinagps.center.pojo.SynHistory;

@Repository("SynHisDao")
public class SynHisDaoImpl extends BaseDaoImpl implements SynHisDao {

	@Override
	public Map<String, Object> getLastSynHis(Long subco_no, Integer type)throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("select his_id, subco_no, DATE_FORMAT(syn_time, '%Y-%m-%d %T') syn_time, start_time, end_time");
		sb.append(" from t_syn_his");
		sb.append(" where subco_no=").append(subco_no);
		sb.append(" and type=").append(type);
		sb.append(" order by syn_time desc");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		if(list != null && !list.isEmpty()){
			return (Map<String, Object>) list.get(0);
		}
		return new HashMap<String, Object>();
	}

	@Override
	public SynHistory getBySubconoAndType(Long subco_no, Integer type) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SynHistory s where 1=1");
		if(subco_no != null){
			sb.append(" and s.subco_no=").append(subco_no);
		}
		if(type != null){
			sb.append(" and s.type=").append(type);
		}
		Query query=sessionFactory.getCurrentSession().createQuery(sb.toString());
		List<SynHistory> list = query.list();
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
}
