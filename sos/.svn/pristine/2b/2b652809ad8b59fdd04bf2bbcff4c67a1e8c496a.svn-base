package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.BoundDao;
import com.gboss.dao.RenewDao;
import com.gboss.dao.UnitDao;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Renew;
import com.gboss.pojo.RenewHistory;
import com.gboss.pojo.Unit;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:RenewDaoImpl
 * @Description:
 * @author:hansm
 * @date:2016-6-12 下午3:09:01
 */

@Repository("RenewDao")  
@Transactional
public class RenewDaoImpl extends BaseDaoImpl implements RenewDao {
		
    //查询海马续费列表
	@Override
	public List<HashMap<String, Object>> findHmTBoxRenew(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT tfh.`customer_name` ,tfh.`telphone` ,tfh.`vin`,tfh.`engine_no`,tfh.`plate_no`,tfh.`barcode`, ");
		sb.append(" tfh.`call_letter`, tfh.`equip_code`,DATE_FORMAT(tfh.service_start_date,'%Y-%m-%d') service_start_date , ");
		sb.append(" DATE_FORMAT(tfh.service_end_date,'%Y-%m-%d') service_end_date, ");
		sb.append(" DATE_FORMAT(tfh.combo_change_date,'%Y-%m-%d') combo_change_date ,  ");
		sb.append(" tfh.`combo_type`,DATE_FORMAT(tfh.service_end_newdate,'%Y-%m-%d') service_end_newdate,DATE_FORMAT(tfh.stamp,'%Y-%m-%d') stamp ");
		//用于每行查询
		sb.append(" ,tfh.`vin` svin ");
		sb.append(" FROM t_fee_hm_renew tfh ");
		sb.append(" where LENGTH(tfh.vin) > 1   ");
		sb = getCondition4UnitInNets(sb, conditionMap);
		sb.append(" ORDER BY tfh.stamp desc");
		if (pageNo>0 && pageSize>0) {
			sb.append(" limit ");
			sb.append(PageUtil.getPageStart(pageNo, pageSize));
			sb.append(",");
			sb.append(pageSize);
		}
		
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int countHmTBoxRenew(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(*)");
		sb.append(" FROM t_fee_hm_renew tfh ");
		sb.append(" where LENGTH(tfh.vin) > 1   ");
		sb = getCondition4UnitInNets(sb, conditionMap);
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	 private StringBuffer getCondition4UnitInNets(StringBuffer sb,Map conditionMap){
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("vin"))){
					sb.append(" and tfh.vin like '%").append(conditionMap.get("vin")).append("%'");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("plate_no"))){
					sb.append(" and tfh.plate_no like '%").append(conditionMap.get("plate_no")).append("%'");
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("barcode"))){
					sb.append(" and tfh.barcode like '%").append(conditionMap.get("barcode")).append("%'");
				}
			}
			return sb;
		 }

	 @Override
	 public Integer addHistory(RenewHistory renewHistory) {
	 	 save(renewHistory);
		 return renewHistory.getHistory_id();
	 }

	 @Override
	 public Integer add(Renew renew) {
		 save(renew);
		 return renew.getRenew_id();
	 }
	 
	 @Override
		public void updateFeeSedateByVin(String sVin,String sService_end_newdate) throws SystemException {
		
		 	StringBuffer sb=new StringBuffer();
			sb.append(" SELECT tbu.`unit_id`  ");
			sb.append(" FROM t_ba_sim tbs ");
			sb.append(" INNER JOIN t_ba_unit tbu ON tbs.`call_letter`=tbu.`call_letter` ");
			sb.append(" WHERE  tbs.subco_no =201 AND tbs.vin='"+sVin+"'  ");
			SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.list();
			BigInteger sUnitID = null;
			if(list!=null&&list.size()!=0){
				sUnitID = (BigInteger) list.get(0).get("unit_id");
			}else{
				return;
			}
			
			StringBuffer sb1=new StringBuffer();
			sb1.append(" SELECT fee_id   ");
			sb1.append(" FROM t_fee_info  ");
			sb1.append(" WHERE unit_id = '"+sUnitID+"' AND subco_no =201 AND feetype_id=101 ");
			SQLQuery query1=sessionFactory.getCurrentSession().createSQLQuery(sb1.toString());
			query1.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list1 = query1.list();
			BigInteger sFeeID = null;
			if(list1!=null&&list1.size()!=0){
				sFeeID = (BigInteger) list1.get(0).get("fee_id");
			}else{
				return;
			}
			
			StringBuffer sb2=new StringBuffer();
			Query query2 = null;
			try{
				sb2.append(" UPDATE t_fee_info SET fee_sedate = '"+sService_end_newdate+"' ");
				sb2.append(" WHERE fee_id=").append(sFeeID);
				query2 = sessionFactory.getCurrentSession().createSQLQuery(sb2.toString());  
				query2.executeUpdate();
			}catch(Exception e){
				
			}
		}
	 
	 //查询海马TBOX服务期满的客户
	 @Override
	 public List<HashMap<String, Object>> findTBoxServerExpire(
			 Map<String, Object> conditionMap, String order, boolean isDesc,
			 int pageNo, int pageSize) throws SystemException {
		 StringBuffer sb=new StringBuffer();
		 sb.append("SELECT tbc.`customer_name`,tbv.`plate_no`,tbu.`call_letter`,tbv.vin ,  ");
		 sb.append("CASE WHEN tfi.feetype_id=101 THEN '前端安装费' ELSE '未知费用' END feetype_name ,  ");
		 sb.append(" DATE_FORMAT(tfi.fee_date,'%Y-%m-%d') fee_date,DATE_FORMAT(tfi.fee_sedate,'%Y-%m-%d') fee_sedate  ");
		 sb.append(" FROM t_fee_info tfi  ");
		 sb.append(" INNER JOIN t_ba_customer tbc ON tfi.`customer_id`=tbc.`customer_id`  ");
		 sb.append(" INNER JOIN t_ba_vehicle tbv ON tfi.`vehicle_id`=tbv.`vehicle_id`  ");
		 sb.append(" INNER JOIN t_ba_unit tbu ON tfi.`unit_id`=tbu.`unit_id`  ");
		 sb.append(" WHERE fee_sedate< DATE_FORMAT(NOW(), '%Y-%m-%d %H:%I:%S') AND tfi.subco_no=201 AND tfi.feetype_id=101  ");
		 //sb = getCondition4TBoxServerExpire(sb, conditionMap);
		 sb.append(" ORDER BY tfi.fee_sedate desc ");
		 if (pageNo>0 && pageSize>0) {
			 sb.append(" limit ");
			 sb.append(PageUtil.getPageStart(pageNo, pageSize));
			 sb.append(",");
			 sb.append(pageSize);
		 }
		 
		 SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		 query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 return query.list();
	 }
	 
	 @Override
	 public int countTBoxServerExpire(Map<String, Object> conditionMap)
			 throws SystemException {
		 StringBuffer sb=new StringBuffer();
		 sb.append(" select count(*)");
		 sb.append(" FROM t_fee_info tfi  ");
		 sb.append(" INNER JOIN t_ba_customer tbc ON tfi.`customer_id`=tbc.`customer_id`  ");
		 sb.append(" INNER JOIN t_ba_vehicle tbv ON tfi.`vehicle_id`=tbv.`vehicle_id`  ");
		 sb.append(" INNER JOIN t_ba_unit tbu ON tfi.`unit_id`=tbu.`unit_id`  ");
		 sb.append(" WHERE fee_sedate< DATE_FORMAT(NOW(), '%Y-%m-%d %H:%I:%S') AND tfi.subco_no=201 AND tfi.feetype_id=101  ");
		 //sb = getCondition4TBoxServerExpire(sb, conditionMap);
		 SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		 return ((BigInteger)query.uniqueResult()).intValue();
	 }
	
	 private StringBuffer getCondition4TBoxServerExpire(StringBuffer sb,Map conditionMap){
		 if(conditionMap!=null){
			 if(StringUtils.isNotNullOrEmpty(conditionMap.get("subcoNo"))){//分公司id
				 sb.append(" and t.subco_no=").append(conditionMap.get("subcoNo"));
			 }
			 if(conditionMap.get("scan_start")!=null && StringUtils.isNotBlank(conditionMap.get("scan_start").toString())){//工厂扫描开始日期
				 sb.append(" and t.scan_time >='").append(conditionMap.get("scan_start")).append(" 00:00:00'");
			 }
			 if(conditionMap.get("scan_end")!=null && StringUtils.isNotBlank(conditionMap.get("scan_end").toString())){//结束日期
				 sb.append(" and t.scan_time <='").append(conditionMap.get("scan_end")).append(" 23:59:59'");
			 }
			 if(conditionMap.get("stamp_start")!=null && StringUtils.isNotBlank(conditionMap.get("stamp_start").toString())){//同步开始日期
				 sb.append(" and t.stamp >='").append(conditionMap.get("stamp_start")).append(" 00:00:00'");
			 }
			 if(conditionMap.get("stamp_end")!=null && StringUtils.isNotBlank(conditionMap.get("stamp_end").toString())){//结束日期
				 sb.append(" and t.stamp <='").append(conditionMap.get("stamp_end")).append(" 23:59:59'");
			 }
		 }
		 return sb;
	 }
	 
		@Override
		public Integer countTBOXPage(Map<String, Object> params) throws SystemException {
			StringBuffer sb = new StringBuffer();
			sb.append("select count(1) from (");
			sb.append(" select V.customer_id");
			sb.append(" FROM t_fee_hm_renew v ");
			//sb = buildTBOXSql(sb, params);
			sb.append(") t1");
			SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
			return ((BigInteger)query.uniqueResult()).intValue();
		}
		//补充条件
		private StringBuffer buildTBOXSql(StringBuffer sb, Map<String, Object> params) {
			sb.append(" WHERE u.unit_id is null");	//补充表
			if(Utils.isNotNullOrEmpty(params)){
				if(Utils.isNotNullOrEmpty(params.get("subco_no"))){
					sb.append(" and c.subco_no=").append(params.get("subco_no"));
				}
				if(Utils.isNotNullOrEmpty(params.get("startDate"))){
					sb.append(" and v.stamp >='").append(DateUtil.dateforSearch(params, "startDate")).append("'");
				}
			}
			return sb;
		}

		@Override
		public List<Map<String, Object>> findTBOXByPage(Map<String, Object> params, String order, boolean isDesc,
				int pageNo, int pageSize) throws SystemException {
			StringBuffer sb = new StringBuffer();
			sb.append("select t.customer_id");
			sb.append(" FROM t_fee_hm_renew t");
			sb = buildTBOXSql(sb, params);
			if (StringUtils.isNotBlank(order)) {
				sb.append(" order by ").append(order);
				if (isDesc) {
					sb.append(" desc");
				} else {
					sb.append(" asc");
				}
			}else{
				sb.append(" order by t.customer_id desc");
			}
			if (pageNo>0 && pageSize>0) {
				sb.append(" limit ");
				sb.append(PageUtil.getPageStart(pageNo, pageSize));
				sb.append(",");
				sb.append(pageSize);
			}
			SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return query.list();
		}

		@Override
		public void updateFeeRenew(String customer_name, String barCode,
				String call_letter, Date isCombo_change_date,
				String combo_type, String engine_no, String equip_code,
				String plate_no, Date isService_end_date,
				Date isService_end_newdate, Date isService_start_date,
				String telPhone,String vin) throws SystemException {
			StringBuffer sb=new StringBuffer();
			Query query = null;
			try{
				sb.append(" UPDATE tfh SET tfh.`customer_name`= '").append(customer_name).append(",");
				sb.append(" tfh.barCode = '").append(barCode).append("',");
				sb.append(" tfh.call_letter ='").append(call_letter).append("',");
				sb.append(" tfh.isCombo_change_date ='").append(isCombo_change_date).append("',");
				sb.append(" tfh.combo_type ='").append(combo_type).append("',");
				sb.append(" tfh.engine_no ='").append(engine_no).append("',");
				sb.append(" tfh.equip_code='").append(equip_code).append("',");
				sb.append(" tfh.plate_no='").append(plate_no).append("',");
				sb.append(" tfh.isService_end_date='").append(isService_end_date).append("',");
				sb.append(" tfh.isService_end_newdate='").append(isService_end_newdate).append("',");
				sb.append(" tfh.isService_start_date='").append(isService_start_date).append("',");
				sb.append(" tfh.telPhone='").append(telPhone).append("' ");
				sb.append(" where tfh.vin='").append(barCode).append("'");
				query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
				query.executeUpdate();
			}catch(Exception e){
				
			}
			
		}
		
		@Override
		public Integer vinIsExist(String vin) throws SystemException {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(1) ");
			sb.append(" FROM t_fee_hm_renew v ");
			sb.append(" where v.vin = '").append(vin).append("'");
			SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
			return ((BigInteger)query.uniqueResult()).intValue();
		}
		
		//查询海马续费列表历史记录
		@Override
		public List<HashMap<String, Object>> findHistoryRenew(
				Map<String, Object> conditionMap, String order, boolean isDesc,
				int pageNo, int pageSize,String vin) throws SystemException {
			StringBuffer sb=new StringBuffer();
			sb.append(" SELECT tfh.`customer_name` ,tfh.`telphone` ,tfh.`vin`,tfh.`engine_no`,tfh.`plate_no`,tfh.`barcode`, ");
			sb.append(" tfh.`call_letter`, tfh.`equip_code`,DATE_FORMAT(tfh.service_start_date,'%Y-%m-%d') service_start_date , ");
			sb.append(" DATE_FORMAT(tfh.service_end_date,'%Y-%m-%d') service_end_date, ");
			sb.append(" DATE_FORMAT(tfh.combo_change_date,'%Y-%m-%d') combo_change_date ,  ");
			sb.append(" tfh.`combo_type`,DATE_FORMAT(tfh.service_end_newdate,'%Y-%m-%d') service_end_newdate,DATE_FORMAT(tfh.stamp,'%Y-%m-%d') stamp ");
			sb.append(" FROM t_fee_hm_renew_history tfh ");
			sb.append(" where LENGTH(tfh.vin) > 1   ");
			sb = getCondition4HistoryRenew(sb, vin);
			sb.append(" ORDER BY tfh.stamp desc ");
			if (pageNo>0 && pageSize>0) {
				sb.append(" limit ");
				sb.append(PageUtil.getPageStart(pageNo, pageSize));
				sb.append(",");
				sb.append(pageSize);
			}
			
			SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return query.list();
		}

		@Override
		public int countHistoryRenew(Map<String, Object> conditionMap,String vin)
				throws SystemException {
			StringBuffer sb=new StringBuffer();
			sb.append(" select count(*)");
			sb.append(" FROM t_fee_hm_renew_history tfh ");
			sb.append(" where LENGTH(tfh.vin) > 1   ");
			sb = getCondition4HistoryRenew(sb, vin);
			SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
			return ((BigInteger)query.uniqueResult()).intValue();
		}
		 private StringBuffer getCondition4HistoryRenew(StringBuffer sb,String vin){
				if(vin!=null){
					if(StringUtils.isNotNullOrEmpty(vin)){
						sb.append(" and tfh.vin ='").append(vin).append("'");
					}
				}
				return sb;
			 }
}

