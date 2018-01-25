package com.hm.bigdata.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.hm.bigdata.dao.AlarmDao;
import com.hm.bigdata.dao.ChartDataDao;
import com.hm.bigdata.entity.po.Alarm;
import com.hm.bigdata.util.PageSelect;
import com.hm.bigdata.util.StringUtils;
import com.hm.bigdata.util.Utils;
import com.hm.bigdata.util.page.Page;
import com.hm.bigdata.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:VehicleDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:01:56
 */

@Repository("chartDao")
public class ChartDaoImpl extends BaseDaoImpl implements ChartDataDao {

	@Override
	public List<Map<String, Object>> getChartData(int year, int month, int type) {
		StringBuffer sb =new StringBuffer( " SELECT name ,COUNT as value ,type FROM t_hm_chart_data where 1=1   ");
		
		sb.append(" and year = ").append(year);
		sb.append(" and month = ").append(month);
		sb.append(" and type = ").append(type);

		Query query = mysql1SessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list=query.list();
		return list;
	}

}
