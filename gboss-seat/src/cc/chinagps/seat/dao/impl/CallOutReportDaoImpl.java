package cc.chinagps.seat.dao.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.dao.CallOutReportDao;

@Repository("callOutReportDao")
public class CallOutReportDaoImpl  extends BasicDao  implements CallOutReportDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getResults(ReportCommonQuery query,
			Map<String, String> param) {
		String action = param.get("action");
		Query queryExe = null;
		if("marketing".equals(action)){
			queryExe = getSellSql(query, param);
		}
		if("fee".equals(action)){
			queryExe = getFeeCallSQL(query, param);
		}
		if (query.getLength() > 0) {
			queryExe.setMaxResults(query.getLength());
		}
		return queryExe.setFirstResult(query.getStart()).list();
	}
	
	@Override
	public ReportCommonResponse getCount(ReportCommonQuery query,
			Map<String, String> param) {
		String action = param.get("action");
		Query queryExe = null;
		if("marketing".equals(action)){
			queryExe = getSellSql(query, param);
		}
		if("fee".equals(action)){
			queryExe = getFeeCallSQL(query, param);
		}
		ReportCommonResponse commResp = new ReportCommonResponse();
		if(queryExe!=null){
			long count = queryExe.list().size();
			commResp.setRecordsFiltered(count);
			commResp.setRecordsTotal(count);
		}
		return commResp;
	}
	
	/**
	 * 服务费催缴
	 * @param query
	 * @param param
	 * @return
	 */
	public Query getFeeCallSQL(ReportCommonQuery query,Map<String, String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT "); 
		sql.append(" 	c.customer_name,v.plate_no,u.call_letter,v.vehicle_id,c.pay_model,");
		sql.append(" u.unittype_id,DATE_FORMAT(u.service_date,'%Y-%m-%d %H:%i:%s') service_date,c.subco_name,c.subco_no,u.sales,u.sales_id,");
		sql.append(" 	f.month_fee,f.ac_amount,DATE_FORMAT( f.fee_sedate , '%Y-%m-%d %H:%i:%s' ) fee_sedate ,ut.unittype ");
		sql.append(" FROM t_fee_info f  ");
		sql.append(" LEFT JOIN t_ba_unit u ON  u.unit_id = f.unit_id ");
		sql.append(" LEFT JOIN t_ba_vehicle v ON v.vehicle_id = f.vehicle_id ");
		sql.append(" LEFT JOIN t_ba_customer c ON c.customer_id = f.customer_id ");
		sql.append(" LEFT JOIN t_ba_unittype ut ON ut.unittype_id = u.unittype_id ");
		sql.append(" WHERE f.ac_amount > 0 and f.flag = 0 and DATE_FORMAT( f.fee_sedate , '%Y-%m-%d %H:%i:%s' ) < DATE_FORMAT( :fee_sedate ,'%Y-%m-%d %H:%i:%s' ) ");
		System.out.println("#服务费催缴#"+sql);
		Query hb_query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		hb_query.setParameter("fee_sedate", param.get("fee_sedate"));
		return hb_query;
	}
	
	/**
	 * 营销
	 * @param query
	 * @param param
	 * @return
	 */
	public Query getSellSql(ReportCommonQuery query,Map<String, String> param){
		StringBuffer sql = new StringBuffer();
	 	sql.append(" SELECT u.vehicle_id,u.service_date,c.customer_name,v.plate_no,u.call_letter,v.color,");
		sql.append(" 	c.id_no,DATE_FORMAT(v.register_date,'%Y-%m-%d %H:%i:%s') register_date,");
		sql.append(" 	group_concat(l.phone,'|',l.linkman) linkman,v.model_name,");
		sql.append(" 	v.engine_no,v.vin,v.is_corp,"); /*是否从公司购买商业保险, 0=否, 1=是 */
		sql.append(" 	v.is_pilfer , c.subco_no,c.subco_name   ");/*是否享有综合盗抢险, 0=否, 1=是  */
		sql.append(" FROM t_ba_unit u ");
		sql.append(" LEFT JOIN t_ba_vehicle v ON u.vehicle_id = v.vehicle_id and v.subco_no = u.subco_no ");
		sql.append(" LEFT JOIN t_ba_linkman l ON l.customer_id = u.customer_id  AND l.vehicle_id = u.vehicle_id ");
		sql.append(" LEFT JOIN t_ba_customer c ON u.customer_id = c.customer_id AND c.subco_no = u.subco_no ");
		sql.append(" WHERE u.service_date >= :startTime ");
		sql.append(" AND u.service_date <= :endTime ");
		
//		if(query.getBeginTime() !=null && query.getEndTime()!=null){
//			SimpleDateFormat sdf_yMdHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			//同一天
//			sql.append(" and u.service_date <= date_sub( str_to_date('").append(sdf_yMdHms.format(query.getEndTime())).append("'), interval 9 month ) ");
//			sql.append(" and u.service_date > date_sub(str_to_date('").append(sdf_yMdHms.format(query.getBeginTime())).append("'), interval 1 year ) ");
//		}else{
//			sql.append(" and u.service_date <= date_sub( now(), interval 9 month ) ");
//			sql.append(" and u.service_date > date_sub( now() , interval 1 year ) ");
//		}
	
		String subco_no_s = param.get("subco_no");
		if(StringUtils.hasText(subco_no_s)){
			sql.append("  AND c.subco_no = ").append(subco_no_s).append(" "); 
		}
		
		String call_letter = param.get("call_letter");
		if(StringUtils.hasText(call_letter)){
			sql.append("  AND u.call_letter like '%").append(call_letter).append("%' "); 
		}
		
		String plate_no = param.get("plate_no");
		if(StringUtils.hasText(plate_no)){
			sql.append("  AND v.plate_no like '%").append(plate_no).append("%' "); 
		}
		
		String customer_name = param.get("customer_name");
		if(StringUtils.hasText(customer_name)){
			sql.append("  AND l.customer_name like '%").append(customer_name).append("%' "); 
		}
		
		sql.append(" GROUP BY u.unit_id ORDER BY u.unit_id desc ");
		System.out.println("#营销#"+sql);
		Query hb_query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		hb_query.setParameter("startTime", query.getBeginTime()).setParameter("endTime", query.getEndTime());
		
		return hb_query;
	}

}
