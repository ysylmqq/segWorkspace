package cc.chinagps.seat.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.bean.table.BriefTable;
import cc.chinagps.seat.bean.table.ServerTypeTable;
import cc.chinagps.seat.dao.CallBriefDao;
import cc.chinagps.seat.util.Consts;

@Repository
public class CallBriefDaoImpl  extends BasicDao implements CallBriefDao {

	/****呼入呼出明细报表******/
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getCallInBriefs(BriefTable brief,
			ReportCommonQuery query, Map<String, String> param) {
		Session session = getSession();
		Query  queryExe = getQuerySQL(session,brief,query,param);
		if (query.getLength() > 0) {
			queryExe.setMaxResults(query.getLength());
		}
		return queryExe.setFirstResult(query.getStart()).list();
	}

	@Override
	public ReportCommonResponse getCallInBriefsCount(BriefTable brief, ReportCommonQuery query,
			Map<String, String> param) {
		Session session = getSession();
		Query  queryExe = getQuerySQL(session,brief,query,param);
		long count = queryExe.list().size();
		ReportCommonResponse commResp = new ReportCommonResponse();
		commResp.setRecordsFiltered(count);
		commResp.setRecordsTotal(count);
		return commResp;
	}
	/****呼入呼出明细报表******/
	
	/*呼入呼出公共SQL*/
	public Query getQuerySQL(Session session, BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT IFNULL(b.result,'') result,");
		sql.append(" IFNULL(v.plate_no,'') plate_no,IFNULL(c.customer_name,'') customer_name,b.flag ,u.call_letter,b.phone,DATE_FORMAT(b.stamp,'%Y-%m-%d %H:%i:%s') stamp,b.op_id,b.op_name, b.p_st_id super_servertype_name , CASE WHEN b.type=1 then '来电' else '去电' END type , ");
		sql.append(" (select sv.sname from t_sys_value sv where sv.stype = 1010 and sv.svalue = b.subco_no) company_name,b.content content ");
		sql.append(" FROM t_seat_brief b ");
		sql.append("  LEFT JOIN  t_ba_vehicle v ON  b.vehicle_id = v.vehicle_id  ");
		sql.append("  LEFT JOIN  t_ba_cust_vehicle cv ON cv.vehicle_id = b.vehicle_id ");
		sql.append("  LEFT JOIN  t_ba_customer c ON c.customer_id = cv.customer_id ");
		sql.append("  LEFT JOIN  t_ba_unit u ON u.vehicle_id = b.vehicle_id");
		sql.append(" WHERE 1=1");
		sql.append(" AND b.stamp >= :startTime ");
		sql.append(" AND b.stamp <= :endTime ");
		String type= "1";
		type=  param.get("type");
		if(!isNull(type)){
			sql.append(" AND b.type=").append(type);
		}
		
		//车牌号码或者车载电话
		String  plateno_or_call_letter =  param.get("keywords") ;
		if(!isNull(plateno_or_call_letter)){
			String plate_no_like = plateno_or_call_letter.split(" ")[0];
			String call_letter_like = plateno_or_call_letter.split(" ")[0];
			sql.append(" AND v.plate_no like '%"+plate_no_like+"%' ");
			sql.append(" AND u.call_letter like '%"+call_letter_like+"%' ");
		}
		//分公司ID
		String subco_no = param.get("subco_no");
		if(!isNull(subco_no)){
			sql.append(" AND b.subco_no = "+subco_no + " ");
		}
		//客户名称
		String customer_name = param.get("customer_name");
		if(!isNull(customer_name)){
			sql.append(" AND c.customer_name like '%"+customer_name + "%' ");
		}
		//呼入电话
		String phone = param.get("phone");
		if(!isNull(phone)){
			sql.append(" AND b.phone like '%"+phone + "%' ");
		}
		//受理工号
		String op_id = param.get("op_id");
		if(!isNull(op_id)){
			sql.append(" AND b.op_id = "+op_id + " ");
		}
		
		//服务大类
		String p_st_ids = param.get("p_st_ids");
		if(!isNull(p_st_ids)){
			sql.append(" AND b.p_st_id in("+ p_st_ids + ") ");
		}
		
		//服务子类
		String st_ids = param.get("st_ids");
		if(!isNull(st_ids)){
			sql.append(" AND b.b_id IN (select DISTINCT s_b.b_id from t_seat_brief_dt s_b where s_b.st_id in ("+st_ids+"))");
		}
		
		//呼号或者车牌对应的车辆ID
		String vehicle_id = param.get("vehicle_id");
		if(!isNull(vehicle_id)){
			sql.append(" AND b.vehicle_id = " + vehicle_id + " " );
		}
		sql.append(" order by b.stamp desc ");
		
		Query queryObj = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		queryObj.setParameter("startTime", query.getBeginTime()).setParameter("endTime", query.getEndTime());
		
		return queryObj;
	}
	
	/**是否为空!*/
	private boolean isNull(String input){
		if(input == null || "".equals(input))
		return true;
		return false;
	}

	
	/**
	 * 未使用
	 */
	@Override
	@Deprecated
	public List<Map<String, Object>> getCallInSubStatistics(BriefTable brief,
			ReportCommonQuery query, Map<String, String> param) {
		return null;
	}

	
	/**
	 * 呼入呼出业务报表大类汇总
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getCallInSuperStatistics(BriefTable brief,
			ReportCommonQuery query, Map<String, String> param) {
		Session session = getSession();
		Query  queryExe = getSuperStatisticsSQL(session,brief,query,param);
		List<Map<String, Object>> list = queryExe.list();
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		int index=1;
		for(Map<String, Object> map:list){
			map.put("sn", index);
			Integer super_st_id = (Integer)map.get("super_p_id");
			param.put("p_id", super_st_id.toString());
			Integer s_subco_no = Integer.valueOf((String)map.get("s_subco_no"));
			param.put("s_subco_no", s_subco_no.toString());
			String super_time = (String)map.get("super_time");
			param.put("super_time",super_time);
			/**
			 * 呼入呼出业务报表小类汇总SQL
			 */
			queryExe = getSubStatisticsSQL(session,brief,query,param);
			List<Map<String, Object>> subList = queryExe.list();
			map.put("sub_data", subList);
			index = index+1; 
			ret.add(map);
		}
		return ret;
	}
	
	/**
	 * 呼入呼出业务报表大类汇总SQL
	 */
	public Query getSuperStatisticsSQL(Session session, BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param){
		StringBuffer sql = new StringBuffer();
		String default_ftm = setDateFmt(param);
	 	sql.append(" SELECT distinct count(*) super_num ,DATE_FORMAT(t.stamp,'"+default_ftm+"') super_time ,t.p_st_id super_p_id,ss.st_name super_st_name ");
	 	sql.append(" ,Ifnull((select sv.svalue from t_sys_value sv where sv.stype = 1010 and sv.svalue = t.subco_no),0)  s_subco_no ");
	 	sql.append(" ,IFNULL((select sv.sname from t_sys_value sv where sv.stype = 1010 and sv.svalue = t.subco_no),'') company_name ");
		sql.append(" FROM t_seat_brief t  ");
		sql.append(" LEFT JOIN t_seat_servicetype ss on t.p_st_id = ss.st_id  ");
//		sql.append(" LEFT JOIN t_ba_vehicle v on v.vehicle_id = t.vehicle_id  ");
		sql.append(" WHERE 1=1 ");
		
		String type = "1";
		type =  param.get("type");
		if(!isNull(type)){
			sql.append(" AND t.type=").append(type);
		}
		//分公司
		String subco_no = param.get("subco_no");
		if(!isNull(subco_no) && Integer.parseInt(subco_no) != 0){
			sql.append(" AND t.subco_no = "+subco_no + " ");
		}
		
		//接通情况
		String flag = param.get("isConnect");
		if(!isNull(flag) && Integer.parseInt(flag) != 0){
			int nflag = Integer.parseInt(flag);
			if(nflag==1)
			sql.append(" AND t.flag = 0 ");//接通
			else if(nflag ==2 )sql.append(" AND t.flag <> 0 ");//未接通
		}
		
		//呼号或者车牌对应的车辆ID
		String vehicle_id = param.get("vehicle_id");
		if(!isNull(vehicle_id)){
			sql.append(" AND t.vehicle_id = " + vehicle_id + " " );
		}
		
		//服务大类
		String p_st_ids = param.get("p_st_ids");
		//服务子类
		String st_ids = param.get("st_ids");
		
		//服务小类为空 大类不为空
		if(!isNull(p_st_ids) && isNull(st_ids) ){
			sql.append(" AND t.p_st_id in("+ p_st_ids + ") ");
		}
		
		//服务小类不为空 大类为空
		if(!isNull(st_ids) && isNull(p_st_ids)){
//			sql.append(" AND t.p_st_id in (select s.p_id from t_seat_servicetype s where s.st_id in ("+st_ids+")) ");
//			sql.append(" AND ss.st_id in ("+st_ids+") ");
			sql.append(" AND t.b_id in (select d.b_id from  t_seat_brief_dt d where d.st_id in ("+st_ids+"))");
		}
		
		//大类和小类都不为空
		if(!isNull(st_ids) && !isNull(p_st_ids)){
			
			String[] sub_st_ids = st_ids.split(",");
			String last_ret_pids = p_st_ids;
			
			for(String st_id:sub_st_ids){
				ServerTypeTable stt = Consts.SERVICE_TYPE_CACHE.get(st_id);
				if(stt != null){
					if(stt.getP_id()!=0){
						String temp_p_id = String.valueOf(stt.getP_id());
						if(last_ret_pids.indexOf(temp_p_id)==-1){
							//获取父类ID
							last_ret_pids.concat(temp_p_id).concat(",");
						}
					}else{
						//父类ID
						last_ret_pids.concat(String.valueOf(stt.getSt_id())).concat(",");
					}
				}
			}
			
			sql.append(" AND t.p_st_id in ("+ last_ret_pids+")  ");
		}
		
		//时间段
		sql.append(" AND t.stamp >= :startTime ");
		sql.append(" AND t.stamp <= :endTime ");
		
		sql.append(" GROUP BY super_time ,t.p_st_id ,s_subco_no ");
		sql.append(" ORDER BY t.stamp DESC ");
		
		
		Query queryObj = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		queryObj.setParameter("startTime", query.getBeginTime()).setParameter("endTime", query.getEndTime());
		
		return queryObj;
	}

	/*颗粒度*/
	private String setDateFmt(Map<String, String> param) {
		String kld = param.get("kld");
		String default_ftm = "%Y-%m-%d";
		if(kld!=null && !"".equals(kld)){
			if(kld.equals("1")){//小时
				default_ftm = "%Y-%m-%d %H";
			}
			if(kld.equals("2")){//天
				default_ftm = "%Y-%m-%d";
			}
			if(kld.equals("3")){//月
				default_ftm = "%Y-%m";
			}
			if(kld.equals("4")){//年
				default_ftm = "%Y";
			}
		}
		return default_ftm;
	}
	
	/**
	 * 呼入呼出业务报表小类汇总SQL
	 * 
	 * 若是传入父ID则会同时有小类
	 */
	public Query getSubStatisticsSQL(Session session, BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param){
		StringBuffer sql = new StringBuffer();
		String default_ftm = setDateFmt(param);
		sql.append("select distinct DATE_FORMAT(t.stamp,'"+default_ftm+"') sub_stamp , COUNT(*) sub_num,s.st_name sub_st_name ");
		sql.append(",(select sv.sname from t_sys_value sv where sv.stype = 1010 and sv.svalue = b.subco_no) sub_company_name ");
		sql.append("from t_seat_brief_dt t  ");
		sql.append("LEFT JOIN t_seat_servicetype s  on t.st_id = s.st_id ");
		sql.append("LEFT JOIN t_seat_brief b 	ON b.b_id		= t.b_id ");
//		sql.append("LEFT JOIN t_ba_vehicle v 	ON v.vehicle_id = b.vehicle_id  ");
		sql.append("where 1=1 ");
		String type = "1";
		type =  param.get("type");
		if(!isNull(type)){
			sql.append(" AND b.type=").append(type);
		}
		
		//分公司
		String subco_no = param.get("s_subco_no");
		if(!isNull(subco_no) && Integer.parseInt(subco_no) != 0){
			sql.append(" AND b.subco_no = "+subco_no + " ");
		}
		
		//呼号或者车牌对应的车辆ID
		String vehicle_id = param.get("vehicle_id");
		if(!isNull(vehicle_id)){
			sql.append(" AND b.vehicle_id = " + vehicle_id + " " );
		}
		
		// 界面传入的子类：st_ids 界面传入的父类：p_st_ids 后台处理时加入的p_id:父类ID
		//服务大类
		String p_id = param.get("p_id");
		String p_st_ids = param.get("p_st_ids");
		
		//服务子类
		String st_ids = param.get("st_ids");
		if(!isNull(p_st_ids) && isNull(st_ids) ){
			sql.append(" AND s.p_id in("+ p_st_ids + ") ");
		}
		
		if(!isNull(st_ids) && isNull(p_st_ids)){
			sql.append(" AND t.st_id in ("+st_ids+") ");
		}
		
		//大类和小类
		if(!isNull(st_ids) && !isNull(p_st_ids)){
			
			String[] sub_st_ids = st_ids.split(",");
			String last_ret_pids = p_st_ids;
			
			for(String st_id:sub_st_ids){
				ServerTypeTable stt = Consts.SERVICE_TYPE_CACHE.get(st_id);
				if(stt != null){
					if(stt.getP_id()!=0){
						String temp_p_id = String.valueOf(stt.getP_id());
						if(last_ret_pids.indexOf(temp_p_id)==-1){
							//获取父类ID
							last_ret_pids.concat(temp_p_id).concat(",");
						}
					}else{
						//父类ID
						last_ret_pids.concat(String.valueOf(stt.getSt_id())).concat(",");
					}
				}
			}
			
			sql.append("  AND s.p_id in ("+ last_ret_pids+") ");
			
		}
		
		//默认按照父类子类来查询
		if(!isNull(p_id) && isNull(p_st_ids) && isNull(st_ids)){
			sql.append(" AND s.p_id in("+ p_id + ") ");
		}
		
		sql.append(" AND DATE_FORMAT(t.stamp,'"+default_ftm+"') = :super_time ");
		sql.append("GROUP BY t.st_id,sub_company_name ");
		
		Query queryObj = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		queryObj.setParameter("super_time", param.get("super_time"));
		return queryObj;
	}

	/**
	 * 呼出接通情况汇总报表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getCalloutFlagStatistics(BriefTable brief,
			ReportCommonQuery query, Map<String, String> param) {
		Session session = getSession();
		Query  queryExe = getSuperStatisticsSQL(session,brief,query,param);
		List<Map<String, Object>> list = queryExe.list();
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		int index=1;
		for(Map<String, Object> map:list){
			map.put("sn", index);
			Integer super_st_id = (Integer)map.get("super_p_id");
			param.put("p_id", super_st_id.toString());
			Integer s_subco_no = Integer.valueOf((String)map.get("s_subco_no"));
			param.put("s_subco_no", s_subco_no.toString());
			String super_time = (String)map.get("super_time");
			param.put("super_time",super_time);
			/**
			 * 呼入呼出业务报表小类汇总SQL
			 */
			queryExe = getCalloutStatisticsSQL(session,brief,query,param);
			List<Map<String, Object>> subList = queryExe.list();
			map.put("sub_data", subList);
			index = index+1; 
			ret.add(map);
		}
		return ret;
	}
	
	
	public Query getCalloutStatisticsSQL(Session session, BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param){
		StringBuffer sql = new StringBuffer();
		String default_ftm = setDateFmt(param);
		
	 	sql.append(" SELECT DATE_FORMAT(t.stamp,'"+default_ftm+"') sub_stamp , COUNT(*) sub_num,t.flag ");
		sql.append(" ,(SELECT sv.sname FROM t_sys_value sv WHERE sv.stype = 1010 AND sv.svalue = t.subco_no) sub_company_name ");
		sql.append(" FROM t_seat_brief t  ");
		sql.append(" LEFT JOIN t_seat_servicetype s  ON t.p_st_id = s.st_id ");
//		sql.append(" LEFT JOIN t_ba_vehicle v ON v.vehicle_id = t.vehicle_id  ");
		sql.append(" WHERE t.type = 2 ");
		
		//分公司
		String subco_no = param.get("s_subco_no");
		if(!isNull(subco_no) && Integer.parseInt(subco_no) != 0){
			sql.append(" AND t.subco_no = "+subco_no + " ");
		}
		
		//呼号或者车牌对应的车辆ID
		String vehicle_id = param.get("vehicle_id");
		if(!isNull(vehicle_id)){
			sql.append(" AND t.vehicle_id = " + vehicle_id + " " );
		}
		
		//接通情况
		String flag = param.get("isConnect");
		if(!isNull(flag) && Integer.parseInt(flag) != 0){
			int nflag = Integer.parseInt(flag);
			if(nflag==1)
			sql.append(" AND t.flag = 0 ");//接通
			else if(nflag ==2 )sql.append(" AND t.flag <> 0 ");//未接通
		}
		
		
		//服务大类
		String p_st_ids = param.get("p_id");
		
		//服务子类
		String st_ids = param.get("st_ids");
		
		if(!isNull(p_st_ids) && isNull(st_ids) ){
			sql.append(" AND t.p_st_id in("+ p_st_ids + ") ");
		}
		
//		if(!isNull(st_ids) && isNull(p_st_ids)){
//			sql.append(" AND t.p_st_id in (f_get_p_st_ids('"+st_ids+"')) ");
//		}
//		
//		//大类和小类
//		if(!isNull(st_ids) && !isNull(p_st_ids)){
//			sql.append(" AND t.p_st_id in ("+ p_st_ids + ", f_get_p_st_ids('"+st_ids+"')) ");
//		}
		
		sql.append(" AND DATE_FORMAT(t.stamp,'"+default_ftm+"') = :super_time ");
		sql.append(" GROUP BY t.flag ,sub_company_name ");
		sql.append(" ORDER BY sub_stamp DESC ");
		
		Query queryObj = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		queryObj.setParameter("super_time", param.get("super_time"));
		return queryObj;
	}

	/**
	 * 外呼统计报表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getCalloutStatistics(BriefTable brief,
			ReportCommonQuery query, Map<String, String> param) {
		Session session = getSession();
		Query query1 = getCalloutStatisticsQuery(session,brief,query,param);
		return query1.list();
	}
	
	public Query getCalloutStatisticsQuery(Session session,BriefTable brief,
			ReportCommonQuery query, Map<String, String> param){
		String default_ftm = setDateFmt(param);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT "); 
		sql.append(" (select sv.sname from t_sys_value sv where sv.stype = 1010 and sv.svalue = t.subco_no) company_name ");  
		sql.append(" , DATE_FORMAT(t.stamp, '"+default_ftm+"') stamp  ,COUNT(*) all_call_num , s.st_name , "); 
		sql.append(" ( select count(*) from t_seat_brief b WHERE b.flag = 0 and b.p_st_id = t.p_st_id and b.stamp = t.stamp  ) success_call_num, "); 
		sql.append(" IFnull(SUM(t.talk_time),0) all_talk_time , "); 
		sql.append(" CONCAT(FORMAT((( select count(*) from t_seat_brief b WHERE b.flag = 0 and b.p_st_id = t.p_st_id and b.stamp = t.stamp )/ count(*) )*100,2),'%') success_rate , "); 
		sql.append(" FORMAT(IFNULL((select AVG(b.talk_time) from t_seat_brief b WHERE b.flag = 0 and  t.type = 2 and b.p_st_id = t.p_st_id and b.stamp = t.stamp GROUP BY b.p_st_id ),0),1) avg_talk_time "); 
		sql.append(" FROM t_seat_brief t ");  
		sql.append(" LEFT JOIN t_seat_servicetype s on s.st_id = t.p_st_id "); 
//		sql.append(" LEFT JOIN t_ba_vehicle v on v.vehicle_id = t.vehicle_id "); 
		sql.append(" where t.type = 2 and t.flag is not null  ");
		
		//条件
		//分公司
		String subco_no = param.get("subco_no");
		if(!isNull(subco_no) && Integer.parseInt(subco_no) != 0){
			sql.append(" AND t.subco_no = "+subco_no + " ");
		}
		
		//呼号或者车牌对应的车辆ID
		String vehicle_id = param.get("vehicle_id");
		if(!isNull(vehicle_id)){
			sql.append(" AND t.vehicle_id = " + vehicle_id + " " );
		}
		
		//服务大类
		String p_st_ids = param.get("p_st_ids");
		if(!isNull(p_st_ids) ){
			sql.append(" AND t.p_st_id in("+ p_st_ids + ") ");
		}
		sql.append(" AND t.stamp >= :startTime ");
		sql.append(" AND t.stamp <= :endTime ");
		sql.append(" GROUP BY stamp , t.p_st_id , company_name "); 
		sql.append(" ORDER BY stamp DESC "); 
		
		Query queryObj = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		queryObj.setParameter("startTime", query.getBeginTime()).setParameter("endTime", query.getEndTime());
		return queryObj;
	}

	@Override
	public ReportCommonResponse getCalloutStatisticsCount(BriefTable brief,
			ReportCommonQuery query, Map<String, String> param) {
		Session session = getSession();
		Query  queryExe = getCalloutStatisticsQuery(session,brief,query,param);
		long count = queryExe.list().size();
		ReportCommonResponse commResp = new ReportCommonResponse();
		commResp.setRecordsFiltered(count);
		commResp.setRecordsTotal(count);
		return commResp;
	}
	
	
	
	

}
