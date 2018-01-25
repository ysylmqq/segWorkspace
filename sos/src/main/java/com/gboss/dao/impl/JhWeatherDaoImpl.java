package com.gboss.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.JhWeatherDao;
import com.gboss.pojo.WhJhWeather;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:CustphoneDaoImpl
 * @Description:TODO
 * @author:yusongya
 * @date:2017-6-9 下午5:24:47
 */
@Repository  
@Transactional
public class JhWeatherDaoImpl extends BaseDaoImpl implements JhWeatherDao {

	@Override
	public WhJhWeather getJhWeatherByCity(String city) {
		StringBuffer sb=new StringBuffer();
		Date now = new Date();
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(now);
		String start = dateStr+" 00:00:00";
		String end = dateStr+" 23:59:59";
		sb.append("  SELECT city,now_weather,today_weather,future_weather,stamp  FROM t_wh_weather WHERE city ='"+city+"'  and stamp >= '"+start+"'  and stamp < '"+end +"' ORDER BY stamp DESC LIMIT 1  ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		List<Map<String, Object>>  list =  query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if(list == null || list.isEmpty()){
			return null;
		}else{
			WhJhWeather whJhWeather = new WhJhWeather();
			Map<String, Object> map = list.get(0);
			whJhWeather.setCity(city);
			whJhWeather.setNowWeather((String)map.get("now_weather"));
			whJhWeather.setTodayWeather((String)map.get("today_weather"));
			whJhWeather.setFutureWeather((String)map.get("future_weather"));
			return whJhWeather;
		}
	}


}

