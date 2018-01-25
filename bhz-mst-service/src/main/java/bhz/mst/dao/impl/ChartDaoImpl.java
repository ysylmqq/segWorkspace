package bhz.mst.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import bhz.mst.dao.ChartDataDao;




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
