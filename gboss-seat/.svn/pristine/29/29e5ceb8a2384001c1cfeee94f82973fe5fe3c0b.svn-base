package cc.chinagps.seat.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.bean.table.SmsTemplateTable;
import cc.chinagps.seat.dao.SmsTemplateDao;

@Repository("smsTemplateDao")
public class SmsTemplateDaoImpl extends BasicDao implements SmsTemplateDao {

	@Override
	public void saveOrUpdate(SmsTemplateTable stt) {
		
		String sql = " update t_sms_template t set t.sms ='" + stt.getSms() +"' where t.st_id =  " + stt.getStId() ;
		if(stt.getStCode() != 0){
			sql = " update t_sms_template t set t.sms ='" + stt.getSms() +"' where t.st_code =  " + stt.getStCode() ;
		}
		getSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public void delteSms(SmsTemplateTable sms) {
		getSession().delete(sms);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SmsTemplateTable> getAllSmsTemps(Map<String, Object> params) {
		String hql = " from SmsTemplateTable sst where 1=1 and sst.is_view = 1 ";
		String stCode = (String) params.get("stCode");
		if(stCode != null && !"".equals(stCode) ){
			hql.concat(" sst.stCode='").concat(stCode).concat("'");
		}
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	@Override
	public SmsTemplateTable getSmsTemplateByID(Integer stId) {
		return (SmsTemplateTable) getSession().get(SmsTemplateTable.class, stId);
	}

	@Override
	public int batchSmsDel(Integer[] stIds) {
		if(stIds!=null){
			String stids = Arrays.deepToString(stIds).replace("[", "").replace("]", "").trim();
			String sql = " delete from t_sms_template where st_id in (" + stids + ")";
			return getSession().createSQLQuery(sql).executeUpdate();
		}
		return -1;
	}

	@Override
	public int getNewStCode() {
		String hql  = "select max(s.stCode) from SmsTemplateTable s  ";
		Integer maxStCode = (Integer) getSession().createQuery(hql).uniqueResult();
		return maxStCode;
	}

	@Override
	public int updateType(String stCode, String stName) {
		String hql = " update SmsTemplateTable s set s.stName=:stName where s.stCode =:stCode ";
		Query query = getSession().createQuery(hql).setParameter("stCode", stCode).setParameter("stName", stName);
		return query.executeUpdate();
	}

	@Override
	public void delType(String stCode) {
		String hql = " delete from SmsTemplateTable s where s.stCode =:stCode ";
		Query query = getSession().createQuery(hql).setParameter("stCode", stCode);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getSendsmsLists(ReportCommonQuery query,
			Map<String, String> param) {
		
		Query sqlquery = getQuerySQL(query, param);
		
	    if (query.getLength() > 0) {
	    	sqlquery.setMaxResults(query.getLength());
		}
		return sqlquery.setFirstResult(query.getStart()).list();
	}

	private Query getQuerySQL(ReportCommonQuery query, Map<String, String> param) {
		String sql = "SELECT  DATE_FORMAT(t.send_time,'%Y-%m-%d %H:%i:%s')  send_time,v.plate_no,t.mobile,t.op_name,";
	    sql += " t.content FROM t_sms_send t LEFT JOIN t_ba_unit u ON u.unit_id = t.unit_id " +
	    		"LEFT JOIN t_ba_vehicle v ON u.vehicle_id = v.vehicle_id";
	    sql += " where  t.send_time >= :startTime ";
	    sql += " and  t.send_time <= :endTime ";
	    
	    String mobile = param.get("mobile");
	    if(StringUtils.hasLength(mobile)){
	    	sql += " AND t.mobile LIKE '%"+mobile+"%' ";
	    }
	    
	    String content = param.get("content");
	    if(StringUtils.hasLength(content)){
	    	sql += " AND t.content LIKE '%"+content+"%' ";
	    }
	    
	    String vehicle_id = param.get("vehicle_id");
	    if(StringUtils.hasLength(vehicle_id)){
	    	sql += " AND v.vehicle_id = " + vehicle_id;
	    }
	    sql += " order by t.send_time desc ";
	    Query sqlquery = getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	    sqlquery.setParameter("startTime", query.getBeginTime()).setParameter("endTime", query.getEndTime());
		return sqlquery;
	}

	@Override
	public ReportCommonResponse getSendsmsListsCount(ReportCommonQuery query, Map<String, String> param) {
		Query  queryExe = getQuerySQL(query,param);
		long count = queryExe.list().size();
		ReportCommonResponse commResp = new ReportCommonResponse();
		commResp.setRecordsFiltered(count);
		commResp.setRecordsTotal(count);
		return commResp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getSendsmsStatisticsLists(
			ReportCommonQuery query, Map<String, String> param) {
		Query sqlquery = getStatisticsSQL(query, param);
	    if (query.getLength() > 0) {
	    	sqlquery.setMaxResults(query.getLength());
		}
		return sqlquery.setFirstResult(query.getStart()).list();
	}

	private Query getStatisticsSQL(ReportCommonQuery query,
			Map<String, String> param) {
		String sql = " SELECT DATE_FORMAT(t.send_time,'%Y-%m-%d %H:%i:%s') send_time, m.st_name, COUNT(t.sms_id) send_num FROM t_sms_send t LEFT JOIN t_sms_template m ON m.st_code = t.st_code " ;
		sql += " where  t.send_time >= :startTime ";
		sql += " and  t.send_time <= :endTime ";

		String st_code = param.get("st_code");
	    if(StringUtils.hasLength(st_code)){
	    	sql += " AND t.st_code = " + st_code;
	    }
		sql += " GROUP BY t.send_time, t.st_code ";
		sql += " order by t.send_time desc ";
	  Query sqlquery = getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	  sqlquery.setParameter("startTime", query.getBeginTime()).setParameter("endTime", query.getEndTime());
	  return sqlquery;
	}

	@Override
	public ReportCommonResponse getSendsmsStatisticsCount(
			ReportCommonQuery query, Map<String, String> param) {
		Query  queryExe = getStatisticsSQL(query,param);
		long count = queryExe.list().size();
		ReportCommonResponse commResp = new ReportCommonResponse();
		commResp.setRecordsFiltered(count);
		commResp.setRecordsTotal(count);
		return commResp;
	}

}
