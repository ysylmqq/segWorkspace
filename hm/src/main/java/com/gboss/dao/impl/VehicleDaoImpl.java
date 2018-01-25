package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.VehicleDao;
import com.gboss.pojo.Vehicle;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:VehicleDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:01:56
 */

@Repository("VehicleDao")
@Transactional
public class VehicleDaoImpl extends BaseDaoImpl implements VehicleDao {

	@Override
	public boolean is_repeat(Vehicle vehicle) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Vehicle.class);
		int count = 0;
		if (vehicle != null) {
			if (vehicle.getVehicle_id() != null) {
				criteria.add(Restrictions.not(Restrictions.eq("vehicle_id",
						vehicle.getVehicle_id())));
			}
			if (vehicle.getPlate_no() != null) {
				criteria.add(Restrictions.eq("plate_no", vehicle.getPlate_no()));
			}
			count = criteria.list().size();
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Page<Vehicle> search(PageSelect<Vehicle> pageSelect, Long subco_no) {
		String hql = "from Vehicle where 1=1 ";
		if(subco_no!=2L){
			if(subco_no==101L){
				hql += " and (subco_no=" + subco_no + " or subco_no = 2)";
			}else{
				hql += " and subco_no=" + subco_no;
			}
		}
		Map filter = pageSelect.getFilter();
		Set<String> keys = filter.keySet();
		for (Iterator it = keys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			if (filter.get(key) instanceof Integer) {
				Integer new_name = (Integer) filter.get(key);
				hql += " and " + key + "=" + new_name;
			} else if (filter.get(key) instanceof String) {
				String value = (String) filter.get(key);
				hql += " and " + key + " like '%" + value + "%' ";
			}

		}
		if (StringUtils.isNotBlank(pageSelect.getStart_date())) {
			hql += " and stamp > '" + pageSelect.getStart_date() + "'";
		}
		if (StringUtils.isNotBlank(pageSelect.getEnd_date())) {
			hql += " and stamp < '" + pageSelect.getEnd_date() + "'";
		}
		if (StringUtils.isNotBlank(pageSelect.getOrder())) {
			hql += " order by " + pageSelect.getOrder();
			if (pageSelect.getIs_desc()) {
				hql += " desc ";
			} else {
				hql += " asc ";
			}
		}
		List list = listAll(hql, pageSelect.getPageNo(),
				pageSelect.getPageSize());
		int count = countAll(hql);
		Page<Vehicle> page = PageUtil.getPage(count, pageSelect.getPageNo(),
				list, pageSelect.getPageSize());
		return page;
	}

	@Override
	public Page<Vehicle> search2(PageSelect<Vehicle> pageSelect, Long subco_no) {
		String hql = " select v from Vehicle v,CustVehicle cv,Customer c "
				   + " where v.vehicle_id = cv.vehicle_id "
				   + " and c.customer_id = cv.customer_id and c.cust_type != 0 ";
		if(subco_no!=2L){
			if(subco_no==101L){
				hql += " and (v.subco_no=" + subco_no + " or v.subco_no = 2)";
			}else{
				hql += " and v.subco_no=" + subco_no;
			}
		}
		Map filter = pageSelect.getFilter();
		Set<String> keys = filter.keySet();
		for (Iterator it = keys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			if (filter.get(key) instanceof Integer) {
				Integer new_name = (Integer) filter.get(key);
				hql += " and " + key + "=" + new_name;
			} else if (filter.get(key) instanceof String) {
				String value = (String) filter.get(key);
				hql += " and " + key + " like '%" + value + "%' ";
			}
			if ("vin".equals(key)){
				hql += " and vin != '' "; 
			}
			if ("engine_no".equals(key)){
				hql += " and engine_no != '' "; 
			}
		}
		if (StringUtils.isNotBlank(pageSelect.getStart_date())) {
			hql += " and stamp > '" + pageSelect.getStart_date() + "'";
		}
		if (StringUtils.isNotBlank(pageSelect.getEnd_date())) {
			hql += " and stamp < '" + pageSelect.getEnd_date() + "'";
		}
		if (StringUtils.isNotBlank(pageSelect.getOrder())) {
			hql += " order by " + pageSelect.getOrder();
			if (pageSelect.getIs_desc()) {
				hql += " desc ";
			} else {
				hql += " asc ";
			}
		}
		List list = listAll(hql, pageSelect.getPageNo(),
				pageSelect.getPageSize());
		int count = countAll(hql);
		Page<Vehicle> page = PageUtil.getPage(count, pageSelect.getPageNo(),
				list, pageSelect.getPageSize());
		return page;
	}

	@Override
	public Page<Vehicle> search3(PageSelect<Vehicle> pageSelect, Long subco_no) {
		String hql = " select v from Vehicle v,CustVehicle cv,Customer c "
				   + " where v.vehicle_id = cv.vehicle_id "
				   + " and c.customer_id = cv.customer_id and c.cust_type = 0 ";
		if(subco_no!=2L){
			if(subco_no==101L){
				hql += " and (v.subco_no=" + subco_no + " or v.subco_no = 2)";
			}else{
				hql += " and v.subco_no=" + subco_no;
			}
		}
		Map filter = pageSelect.getFilter();
		Set<String> keys = filter.keySet();
		for (Iterator it = keys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			if (filter.get(key) instanceof Integer) {
				Integer new_name = (Integer) filter.get(key);
				hql += " and " + key + "=" + new_name;
			} else if (filter.get(key) instanceof String) {
				String value = (String) filter.get(key);
				hql += " and " + key + " like '%" + value + "%' ";
			} 
			if ("vin".equals(key)){
				hql += " and vin != '' "; 
			}
			if ("engine_no".equals(key)){
				hql += " and engine_no != '' "; 
			}
		}
		if (StringUtils.isNotBlank(pageSelect.getStart_date())) {
			hql += " and stamp > '" + pageSelect.getStart_date() + "'";
		}
		if (StringUtils.isNotBlank(pageSelect.getEnd_date())) {
			hql += " and stamp < '" + pageSelect.getEnd_date() + "'";
		}
		if (StringUtils.isNotBlank(pageSelect.getOrder())) {
			hql += " order by " + pageSelect.getOrder();
			if (pageSelect.getIs_desc()) {
				hql += " desc ";
			} else {
				hql += " asc ";
			}
		}
		List list = listAll(hql, pageSelect.getPageNo(),
				pageSelect.getPageSize());
		int count = countAll(hql);
		Page<Vehicle> page = PageUtil.getPage(count, pageSelect.getPageNo(),
				list, pageSelect.getPageSize());
		return page;
	}

	@Override
	public List<Vehicle> getVehiclesByCustid(Long cust_id) {
		String hql = "from Vehicle "
				+ "where 1=1 and vehicle_id in "
				+ "(select vehicle_id from CustVehicle where customer_id="
				+ cust_id + ")";
		List<Vehicle> list = listAll(hql);
		return list;
	}

	@Override
	public Vehicle getVehicleByid(Long id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Vehicle.class);
		if (id != null) {
			criteria.add(Restrictions.eq("vehicle_id", id));
		}
		if (criteria.list().size() > 0) {
			return (Vehicle) criteria.list().get(0);
		}
		return null;
	}

	@Override
	public Long getIdByPlate(String plate_no) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Vehicle.class);
		if (plate_no != null) {
			criteria.add(Restrictions.eq("plate_no", plate_no));
		}
		if (criteria.list().size() > 0) {
			Vehicle vehicle = (Vehicle) criteria.list().get(0);
			return vehicle.getVehicle_id();
		}
		return 0L;
	}

	@Override
	public List<HashMap<String, Object>> getVehicleInfo(
			Map<String, Object> conditionMap, Long companyno, String order,
			boolean isDesc, int pn, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT r.vehicle_id AS vehicle_id, r.tidcard as tidcard, ");
		sb.append(" r.color AS color,r.vin AS vin,r.register_date AS register_date, ");
		sb.append(" r.model_name AS model_name,r.customer_id as customer_id, ");
		sb.append(" r.customer_name AS customer_name,p.stamp as stamp, p.is_transfer as is_transfer, ");
		
		sb.append(" p.insurance_id as insurance_id, p.customer_name as name, p.plate_no as carNum, p.policy_no as policyno,");
		sb.append(" p.is_bdate as start_time, p.is_edate as end_time, p.address as address, p.sales as sales,");
		sb.append(" p.amount as amount, p.fee as fee, p.is_buy_tp as is_buy_tp, p.payment as payment,");
		sb.append(" p.is_contain as is_contain, p.policyholder as policyholder,p.subco_no as subco_no,p.ic_no as company,");
		
		sb.append(" r.carNum as car_num,r.vid as vid,r.vmoney AS vmoney,r.ennum AS ennum,  ");
		sb.append(" r.chassnum AS chassnum,r.vCode AS vCode,r.registerTime as registerTime,r.vstatus as vstatus, ");
		sb.append(" r.ucall as ucall,r.prCode as prCode,r.fix_time as fix_time ");
		
		
		
		sb = getConditionHql(sb, conditionMap, companyno);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn > 0 && pageSize > 0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	
	 private StringBuffer getConditionHql(StringBuffer sb,Map conditionMap, Long commpanyNo){
		 
		 	sb.append(" FROM ( SELECT v.vehicle_id AS vehicle_id, ");
			sb.append(" v.color AS color,v.vin AS vin, ");
			sb.append(" v.register_date AS register_date,m.model_name AS model_name, ");
			sb.append(" c.customer_name AS customer_name , t.customer_id as customer_id, ");
			
			sb.append(" v.plate_no as carNum,v.vehicle_id as vid,v.buy_money AS vmoney,v.engine_no AS ennum,  ");
			sb.append(" v.chassis_no AS chassnum,v.vin AS vCode,v.register_date as registerTime,v.vehicle_status as vstatus, ");
			sb.append(" t.call_letter as ucall,t.product_code as prCode,t.fix_time as fix_time, c.id_no as tidcard ");
			
			sb.append("	FROM t_ba_vehicle v,t_ba_model m, t_ba_unit t,t_ba_customer c WHERE v.model = m.model_id ");
			sb.append(" AND t.vehicle_id = v.vehicle_id AND t.customer_id = c.customer_id");
			sb.append(" and v.subco_no =").append(commpanyNo);
			sb.append(" AND c.cust_type = 0 AND v.flag = 1 GROUP BY v.vehicle_id ) r ");
			sb.append(" LEFT JOIN t_ba_insurance p ON r.vehicle_id = p.vehicle_id ");
			sb.append(" WHERE p.insurance_id IS NOT NULL ");
			sb.append(" and p.status <> ").append(SystemConst.POLICY_DELETEED);
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("customerName"))){
					sb.append(" and r.customer_name like '%").append(conditionMap.get("customerName")).append("%'");
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("car_num"))){
						sb.append(" and r.carNum='").append(conditionMap.get("car_num")).append("'");
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("is_transfer"))){
					sb.append(" and p.is_transfer=").append(conditionMap.get("is_transfer"));
				}
					
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate")) && StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
						sb.append(" and p.stamp >='").append(DateUtil.dateforSearch(conditionMap, "startDate")).append("'");
						sb.append(" and p.stamp <='").append(DateUtil.dateforSearch(conditionMap, "endDate")).append("'");
				}
			}
			return sb;
			}
	 


	@Override
	public int countVehicleInfo(Map<String, Object> conditionMap,
			Long commpanyNo) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(r.vehicle_id) ");
		sb = getConditionHql(sb, conditionMap, commpanyNo);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		return ((BigInteger) query.uniqueResult()).intValue();
	}

	@Override
	public Long add(Vehicle vehicle) {
		save(vehicle);
		return vehicle.getVehicle_id();
	}

	@Override
	public boolean is_exist(Vehicle vehicle) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Vehicle.class);
		if(vehicle.getVin() != null && !vehicle.getVin().equals("")){
			criteria.add(Restrictions.eq("vin", vehicle.getVin()));
		}else{
			return false;
		}
		if(vehicle.getVehicle_id() != null){
			criteria.add(Restrictions.not(Restrictions.eq("vehicle_id",
					vehicle.getVehicle_id())));
		}
		if (criteria.list().size() > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public List<HashMap<String, Object>> countServiceFeePage(Long companyno,
			Map map) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT v.plate_no,b.vehicle_id,c.customer_name,u.call_letter,u.fix_time,u.sales,b.total ");
		sb.append(" FROM(SELECT sum(p.real_amount) AS total,p.vehicle_id,p.customer_id FROM t_fee_payment_dt p  ");
		sb.append(" WHERE p.feetype_id = 1 ");
		sb.append(" and p.subco_no = ").append(companyno);
		if (StringUtils.isNotNullOrEmpty(map.get("start_date"))) {
			sb.append(" and p.stamp >='").append(map.get("start_date")).append("'");
		}
		
		if (StringUtils.isNotNullOrEmpty(map.get("end_date"))) {
			sb.append(" and p.stamp <='").append(map.get("end_date")).append("'");
		}
		sb.append(" GROUP BY p.vehicle_id) AS b, ");
		sb.append(" t_ba_unit u,t_ba_vehicle v,t_ba_customer c WHERE ");
		if (StringUtils.isNotNullOrEmpty(map.get("fee"))){
			if(map.get("fee").toString().equals("1")){
				sb.append(" b.total < 40");
			}else{
				sb.append(" b.total >= 40");
			}
		}
		sb.append(" AND b.vehicle_id = u.vehicle_id AND b.vehicle_id = v.vehicle_id AND b.customer_id = u.customer_id and b.customer_id = c.customer_id ");
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<HashMap<String, Object>> findServiceFeePage(Long companyno,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT a.vehicle_id, a.plate_no,a.customer_name,a.call_letter,a.fix_time,a.sales,b.month_fee as total,b.fee_date,b.fee_sedate ");
		sb=getConditionFee(sb,conditionMap,companyno);
		sb.append(" order by a.customer_name ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	
	 private StringBuffer getConditionFee(StringBuffer sb,Map<String, Object> conditionMap, Long commpanyNo){
		 	sb.append(" FROM( SELECT v.vehicle_id,v.plate_no,c.customer_name,u.call_letter,u.fix_time,u.sales FROM  ");
			sb.append(" t_ba_vehicle v, t_ba_unit u,t_ba_customer c WHERE v.vehicle_id = u.vehicle_id ");
			sb.append(" AND u.customer_id = c.customer_id ");
			sb.append(" and v.subco_no = ").append(commpanyNo);
			sb.append(" AND u.reg_status = 0) a left JOIN  ");
			sb.append(" ( select p.vehicle_id, p.month_fee, p.fee_date,p.fee_sedate from t_fee_info p WHERE ");
			sb.append("  p.subco_no  = ").append(commpanyNo);
			sb.append(" AND p.feetype_id = 1 ");
			sb.append(" ) b  on a.vehicle_id = b.vehicle_id ");
			sb.append(" where ");
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("fee"))){
				if(conditionMap.get("fee").toString().equals("1")){
					sb.append(" (( b.month_fee  < 40 ");
					
					if (StringUtils.isNotNullOrEmpty(conditionMap.get("start_date"))) {
						sb.append(" and b.fee_sedate >='").append(conditionMap.get("start_date")).append("'");
					}
					if (StringUtils.isNotNullOrEmpty(conditionMap.get("end_date"))) {
						sb.append(" and b.fee_date <='").append(conditionMap.get("end_date")).append("'");
					}	
					sb.append(" )  or b.month_fee is null ) ");	
				}else{
					sb.append("  b.month_fee  >= 40 ");
					if (StringUtils.isNotNullOrEmpty(conditionMap.get("start_date"))) {
						sb.append(" and b.fee_sedate >='").append(conditionMap.get("start_date")).append("'");
					}
					if (StringUtils.isNotNullOrEmpty(conditionMap.get("end_date"))) {
						sb.append(" and b.fee_date <='").append(conditionMap.get("end_date")).append("'");
					}
				}
			}
			
			return sb;
	}
	 
	
	
	@Override
	public int countServiceFee(Long companyno, Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(a.vehicle_id) ");
		sb=getConditionFee(sb,conditionMap,companyno);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public List<HashMap<String, Object>> findOnlineNoFeePage(Long companyno,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT c.customer_name ,v.plate_no, u.call_letter,u.fix_time,u.sales,u.create_date ");
		sb=getConditionOnlineNOFee(sb,conditionMap,companyno);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	
	 private StringBuffer getConditionOnlineNOFee(StringBuffer sb,Map<String, Object> conditionMap, Long commpanyNo){
		 	sb.append(" FROM t_ba_unit u, t_ba_customer c, t_ba_vehicle v WHERE ");
		 	sb.append(" u.subco_no = ").append(commpanyNo);
		 	sb.append(" AND u.reg_status = 0 and u.customer_id = c.customer_id and u.vehicle_id = v.vehicle_id ");
		 	if (StringUtils.isNotNullOrEmpty(conditionMap.get("start_date"))) {
				sb.append(" and u.create_date >='").append(conditionMap.get("start_date")).append("'");
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("end_date"))) {
				sb.append(" and u.create_date <='").append(conditionMap.get("end_date")).append("'");
			}
		 	sb.append(" AND u.vehicle_id NOT IN ( SELECT DISTINCT f.vehicle_id FROM t_fee_info f) ");
			return sb;
	}

	@Override
	public int countOnlineNoFee(Long companyno, Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT count(u.unit_id) ");
		sb=getConditionOnlineNOFee(sb,conditionMap,companyno);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public List<HashMap<String, Object>> webgisList(String parameter, Long subco_no) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select distinct v.vehicle_id,v.plate_no,u.unit_id,u.call_letter from t_ba_vehicle v join t_ba_unit u on u.vehicle_id = v.vehicle_id ");
		sb.append(" where (v.plate_no like '%").append(parameter);
		sb.append("%' or u.call_letter like '%").append(parameter);
		sb.append("%') and v.subco_no = ").append(subco_no);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public Vehicle getVehicleByCallLetter(String call_letter) {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" select v from Vehicle as v,Unit as u");
		hqlSb.append(" where u.vehicle_id = v.vehicle_id and u.call_letter='").append(call_letter).append("'");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		List<Vehicle> list = query.list();
		if(list != null && list.size() ==1)
			return list.get(0);
		return null;
	}

}
