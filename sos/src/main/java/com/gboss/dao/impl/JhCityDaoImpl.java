package com.gboss.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.JhCityDao;
import com.gboss.pojo.Brand;
import com.gboss.pojo.WhJhCity;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:CustphoneDaoImpl
 * @Description:TODO
 * @author:yusongya
 * @date:2017-6-9 下午5:24:47
 */
@Repository 
@Transactional
public class JhCityDaoImpl extends BaseDaoImpl implements JhCityDao {

	@Override
	public List<Map<String, Object>> findTodayFlushCity() {
		StringBuffer sb=new StringBuffer();
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowStr = sdf.format(nowDate);
		sb.append(" SELECT city,last_query_time FROM t_wh_jhcity  WHERE  last_query_time>= '"+nowStr+" 00:00:00' AND last_query_time < '"+nowStr+" 23:59:59' ");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public WhJhCity findWhJhCityByCity(String city) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WhJhCity.class); 
		criteria.add(Restrictions.eq("city", city));
		List<WhJhCity> list = criteria.list();
		if(list !=null && list.size() != 0){
			return list.get(0);
		}
		return null;
	}


}

