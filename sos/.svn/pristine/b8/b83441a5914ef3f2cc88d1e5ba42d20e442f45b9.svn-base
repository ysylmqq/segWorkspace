package com.gboss.dao.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.PolicyDao;
import com.gboss.pojo.Policy;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:PolicyDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-29 下午4:13:17
 */
@Repository("policyDao")
@Transactional
public class PolicyDaoImpl extends BaseDaoImpl implements PolicyDao {

	@Override
	public int getMaxPolicyNo(String date) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("select max(substring(s.policy_no,length(s.policy_no)-3,4)) from Policy as s");
		sb.append(" where 1=1");
		if (StringUtils.isNotBlank(date)) {
			sb.append(" and s.stamp like '").append(date).append("%'");
		}
		Query query = sessionFactory.getCurrentSession().createQuery(
				sb.toString());
		Object result = query.uniqueResult();
		if (result != null) {
			return Integer.valueOf(query.uniqueResult().toString());
		} else {
			return 0;
		}
	}

	@Override
	public HashMap<String, Object> getDetailMsgByCarNum(String carNum)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT t.custco_no as opid, t.customer_name AS NAME,t.id_no AS idcard,group_concat(p.phone) AS phone,v.plate_no AS carNum, u.product_code AS code, v.vehicle_id as vId, ");
		sb.append(" v.buy_money AS money,v.engine_no AS ennum,v.chassis_no AS chassnum,v.vin AS vCode,u.call_letter AS ucall, u.create_date as registerTime, u.fix_time as uTime ");
		sb.append(" FROM t_ba_customer t,t_ba_vehicle v,t_ba_unit u,t_ba_custphone p ");
		sb.append(" WHERE v.customer_id = t.customer_id AND u.vehicle_id = v.vehicle_id AND p.customer_id = t.customer_id ");
		sb.append(" and v.number_plate='").append(carNum).append("'");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (query.list().size() > 0) {
			return (HashMap<String, Object>) query.list().get(0);
		} else
			return null;
	}

	@Override
	public List<HashMap<String, Object>> getTodayPolicy(Long companyId)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select p.policy_no as policyno ,v.plate_no carNum,c.subco_no as opid, p.sales as saler, p.stamp as time,c.customer_name AS loginname ");
		sb.append(" from t_ba_insurance p, t_ba_vehicle v, t_ba_customer c ");
		sb.append(" where p.vehicle_id = v.vehicle_id and p.customer_id = c.customer_id and date(p.stamp)=curdate() ");
		sb.append(" and p.subco_no=").append(companyId);
		sb.append(" order by p.insurance_id desc limit 3");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public Policy getPolicy(Long vehicle_id) throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Policy.class);
		if (vehicle_id != null) {
			criteria.add(Restrictions.eq("vehicle_id", vehicle_id));
			List list = criteria.list();
			if (list.size() > 0) {
				return (Policy) criteria.list().get(0);
			} else {
				return null;
			}
		}else{
			return null;
		}
	}

	

	@Override
	public int countPolicys(Long companyno, Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(p.insurance_id) ");
		sb=getConditionHql(sb,conditionMap,companyno);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	
	 private StringBuffer getConditionHql(StringBuffer sb,Map conditionMap,Long companyno){
		 	sb.append(" FROM t_ba_insurance p ");
			sb.append(" where p.status = ").append(SystemConst.POLICY_NORMAL);
			if(null != companyno){
				sb.append(" and p.subco_no=").append(companyno);
			}
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("loginname"))){
					sb.append(" and p.customer_name like '%").append(conditionMap.get("customer_name")).append("%'");
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("carNum"))){
					sb.append(" and p.plate_no='").append(conditionMap.get("carNum")).append("'");
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("status"))){
					sb.append(" and p.status=").append(conditionMap.get("status"));
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("company"))){
					sb.append(" and p.ic_no=").append(conditionMap.get("company"));
				}
				
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate")) && StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
					sb.append(" and p.stamp >='").append(DateUtil.dateforSearch(conditionMap, "startDate")).append("'");
					sb.append(" and p.stamp <='").append(DateUtil.dateforSearch(conditionMap, "endDate")).append("'");
				}
			}
			return sb;
	    }
	 
	 private StringBuffer getCusInfoConditionHql(StringBuffer sb,Map conditionMap, Long commpanyNo){
		 	sb.append(" FROM (select t.unit_id as tid, u.customer_id as id, u.birthday as ubirthday, u.id_no as tidcard, u.subco_no as companyno, u.customer_name as name, u.customer_id as customer_id, ");
			sb.append(" v.plate_no as carNum,v.vehicle_id as vid,v.buy_money AS vmoney,v.engine_no AS ennum,  v.color as color,  ");
			sb.append(" v.chassis_no AS chassnum,v.vin AS vCode,t.create_date as registerTime,v.vehicle_status as vstatus, ");
			sb.append(" y.unittype as ucall,t.product_code as prCode,t.fix_time as fix_time, v.model as model ");
			sb.append(" from t_ba_unit t,t_ba_customer u,t_ba_vehicle v, t_ba_unittype y ");
			/*sb.append(" WHERE t.customer_id = u.customer_id AND t.vehicle_id = v.vehicle_id AND u.cust_type = 0");*/
			sb.append(" WHERE t.customer_id = u.customer_id AND t.vehicle_id = v.vehicle_id ");
			
			if(conditionMap!=null){
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("date")) ) {
					sb.append(" and DATE_FORMAT(t.create_date, '%Y-%m')= '").append(conditionMap.get("date")).append("'");
				}else{
					String date = DateUtil.format(new Date(), DateUtil.YM_DASH);
					sb.append(" and DATE_FORMAT(t.create_date, '%Y-%m')= '").append(date).append("'");
				}
			}
			
			sb.append(" and t.unittype_id = y.unittype_id  group by v.vehicle_id  ");
			sb.append(" ) r LEFT JOIN t_ba_insurance p on  r.vid = p.vehicle_id ");
			/*
			sb.append(" from t_ba_customer u, t_ba_vehicle v, t_ba_unit t where  u.customer_id = t.customer_id ");
			sb.append(" and t.vehicle_id = v.vehicle_id and u.cust_type = 0  group by u.customer_id ) r LEFT JOIN t_ba_insurance p on  r.vid = p.vehicle_id ");*/
			sb.append(" and  p.status <> ").append(SystemConst.POLICY_DELETEED);
			sb.append(" where 1=1 ");
			if(null != commpanyNo){
				sb.append(" and r.companyno=").append(commpanyNo);
			}
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("customerName"))){
					sb.append(" and r.name like '%").append(conditionMap.get("customerName")).append("%'");
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("carNum"))){
					sb.append(" and r.carNum='").append(conditionMap.get("carNum")).append("'");
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("vstatus"))){
					sb.append(" and r.vstatus=").append(conditionMap.get("vstatus"));
				}
				
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("pstatus"))){
					if(conditionMap.get("pstatus").toString().equals("10")){
						sb.append(" and p.insurance_id  is null ");
					}else{
						sb.append(" and p.insurance_id  is not null and p.status <> ").append(SystemConst.POLICY_DELETEED);
					}
					
				}
				
				
				/*if(conditionMap!=null){
					if (StringUtils.isNotNullOrEmpty(conditionMap.get("date")) ) {
						sb.append(" and DATE_FORMAT(r.registerTime, '%Y-%m')= '").append(conditionMap.get("date")).append("'");
					}else{
						String date = DateUtil.format(new Date(), DateUtil.YM_DASH);
						sb.append(" and DATE_FORMAT(r.registerTime, '%Y-%m')= '").append(date).append("'");
					}
				}*/
				
				/*
				if (StringUtils.isNotNullOrEmpty(conditionMap.get("startDate")) && StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
					sb.append(" and r.registerTime >='").append(DateUtil.dateforSearch(conditionMap, "startDate")).append("'");
					sb.append(" and r.registerTime <='").append(DateUtil.dateforSearch(conditionMap, "endDate")).append("'");
				}*/
			}
			return sb;
	    }

	@Override
	public HashMap<String, Object> getEditMsgByCarNum(Long id)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select p.id as id, p.status as status, p.policyno as policyno,p.company as company,p.loginname as loginname,p.person as  person, ");
		sb.append(" p.idcard as idcard,p.phone as phone,p.birthday as birthday,p.start_time as start_time,p.end_time as end_time, ");
		sb.append(" p.money as money,p.fee as fee,p.address as address,p.sales_person as sales_person,p.is_buypilfer as is_buypilfer, ");
		sb.append(" p.is_contain as is_contain,p.payment as payment,t.idcard as tidcard,v.number_plate as carNum, ");
		sb.append(" u.code AS code, v.id as vId,v.money AS vmoney,v.engine_no AS ennum,v.chassis_no AS chassnum, ");
		sb.append(" v.CODE AS vCode,u.call_letter AS ucall, v.register_time as registerTime,u.time as uTime ");
		sb.append(" from t_ba_insurance p,t_ba_vehicle v,t_ba_unit u,t_ba_custphone c,t_ba_customer t ");
		sb.append(" where p.vehicle_id = v.id and u.vehicle_id = v.id and v.customer_id = c.customer_id and t.id = v.customer_id ");
		sb.append(" and p.id=").append(id);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (query.list().size() > 0) {
			return (HashMap<String, Object>) query.list().get(0);
		} else
			return null;
	}
	
	@Override
	public HashMap<String, Object> getCusMsg(Long vehicle_id)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select u.code AS code,u.call_letter AS ucall,u.time as uTime ");
		sb.append(" from t_ba_unit u ");
		sb.append(" where  u.vehicle_id=").append(vehicle_id);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (query.list().size() > 0) {
			return (HashMap<String, Object>) query.list().get(0);
		} else
			return null;
	}

	@Override
	public int operatePolicy(List<Long> ids, Integer type) throws SystemException {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" update Policy set status=:st where id in (:ids) ");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		query.setParameter("st", type);
		query.setParameterList("ids", ids); 
		return query.executeUpdate();
	}
	@Override
	public List<HashMap<String, Object>> findPolicys(Long companyno, 
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT p.insurance_id AS id,p.policy_no AS policy_no,p.customer_name AS loginname, p.address as address, ");
		sb.append(" p.policyholder AS policyholder,p.ph_id_no AS ph_id_no,p.birthday AS birthday,p.ratio as ratio,p.op_id as op_id, ");
		sb.append(" p.plate_no AS carNum,p.engine_no AS engine_no,p.vin AS vin,p.register_date AS register_date,p.remark as remark, ");
		sb.append(" p.is_bdate AS is_bdate,p.is_edate AS is_edate,p.amount AS amount,p.phone AS phone,p.model as model,p.color as color, ");
		sb.append(" p.tel AS tel,p.unittype AS unittype,p.fix_time AS fix_time,p.stamp AS stamp,p. STATUS AS STATUS,p.is_buy_tp as is_buy_tp, ");
		sb.append(" p.sales AS sales_person,p.ic_no AS company,p.customer_id as customer_id,p.vehicle_id as vehicle_id,p.unit_id as unit_id ");
		sb=getConditionHql(sb,conditionMap, companyno);
		sb.append(" order by p.insurance_id desc");
		/*if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}*/
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	
	
	@Override
	public List<HashMap<String, Object>> findCustomerInfo(
			Map<String, Object> conditionMap, Long companyno, String order,
			boolean isDesc, int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select p.insurance_id as pid, p.status as pstatus, p.policy_no as policyno,p.ic_no as company,p.policyholder as  person, ");
		sb.append(" p.ph_id_no as idcard,p.tel as phone,p.birthday as birthday,p.is_bdate as start_time,p.is_edate as end_time, ");
		sb.append(" p.amount as money,p.fee as fee,p.address as address,p.sales as sales_person,p.is_buy_tp as is_buypilfer, ");
		sb.append(" p.is_contain as is_contain,p.payment as payment, p.subco_no as subco_no, p.phone as pphone,p.stamp as stamp,p.cust_id_no as cust_id_no, ");
		sb.append(" r.tidcard as tidcard, r.id as id,r.name as name,r.carNum as carNum,r.color as color,r.tid as tid, ");
		sb.append(" r.vid as vid,r.vmoney AS vmoney,r.ennum as ennum,r.chassnum as chassnum, r.ubirthday as ubirthday,");
		sb.append(" r.ucall as ucall,r.prCode as prCode,r.fix_time as fix_time, r.model as model, r.customer_id as customer_id, ");
		sb.append(" r.vCode as vCode,r.registerTime as registerTime,r.vstatus as vstatus");
		sb=getCusInfoConditionHql(sb,conditionMap, companyno);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countCustomerInfo(Map<String, Object> conditionMap, Long commpanyNo)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(r.id) ");
		sb=getCusInfoConditionHql(sb,conditionMap, commpanyNo);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public HashMap<String, Object> getPolicyMsgByNum(Long pid)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT p.insurance_id AS pid,p.subco_no AS subco_no,p.customer_id AS customer_id, p.status as status, ");
		sb.append(" p.customer_name AS customer_name,p.policy_no AS policy_no,p.vehicle_id AS vehicle_id, ");
		sb.append(" p.plate_no AS plate_no,p.ic_no AS ic_no,p.vehicle_price AS vehicle_price,p.amount AS amount, ");
		sb.append(" p.fee AS fee,p.policyholder AS policyholder,p.ph_id_no AS ph_id_no,p.birthday AS birthday,p.phone AS phone, ");
		sb.append(" p.model as plate_code,p.engine_no as engine_no, p.vin as vin,p.cust_id_no as cust_id_no,p.sales as sales, ");
		sb.append(" p.register_date as register_date,p.fix_time as fix_time,p.unittype as gps_code,p.unit_sn as barcode, ");
		sb.append(" p.tel AS tel,p.is_bdate AS is_bdate,p.is_edate AS is_edate,p.address AS address FROM t_ba_insurance p WHERE  ");
		sb.append(" p.insurance_id = ").append(pid);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (query.list().size() > 0) {
			return (HashMap<String, Object>) query.list().get(0);
		} else
			return null;
	}

	@Override
	public Policy getPolicyBynum(String policy_num) throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Policy.class); 
				criteria.add(Restrictions.not(Restrictions.eq("policy_no", policy_num)));
				List list = criteria.list();
				if(list !=null && list.size() >0){
					return  (Policy)(list.get(0));	
				}
				return null;		
	}

	@Override
	public List<HashMap<String, Object>> findPolicysRecords(
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT p.insurance_id AS id,p.policy_no AS policy_no,p.customer_name AS loginname,p.sales as sales, ");
		sb.append(" p.policyholder AS policyholder,p.ph_id_no AS ph_id_no,p.birthday AS birthday,p.color as color,p.ratio as ratio, ");
		sb.append(" p.plate_no AS carNum,p.engine_no AS engine_no,p.vin AS vin,p.register_date AS register_date,p.vehicle_price AS vehicle_price, ");
		sb.append(" p.is_bdate AS is_bdate,p.is_edate AS is_edate,p.amount AS amount,p.phone AS phone,p.is_print as is_print,  ");
		sb.append(" p.tel AS tel,p.unittype AS unittype,p.fix_time AS fix_time,p.stamp AS stamp,p. STATUS AS status,p.unit_sn as barcode, ");
		sb.append(" p.sales AS sales_person,p.ic_no AS company,p.customer_id as customer_id,p.vehicle_id as vehicle_id,p.unit_id as unit_id, ");
		sb.append(" p.fee as fee,p.is_buy_tp as is_buy_tp,p.is_contain as is_contain,p.address as address,p.cust_id_no as cust_id_no, p.model as plate_code  ");
		sb=getConditionHql(sb,conditionMap);
		sb.append(" order by p.insurance_id desc");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (pn>0 && pageSize>0) {
			query.setFirstResult(PageUtil.getPageStart(pn, pageSize));
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	@Override
	public int countPolicysRecords(Map<String, Object> conditionMap)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(p.insurance_id) ");
		sb=getConditionHql(sb,conditionMap);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	

	 private StringBuffer getConditionHql(StringBuffer sb,Map conditionMap){
		 	sb.append(" FROM t_ba_insurance p ");
			sb.append(" where 1=1 ");
			//sb.append(" and p.status <> ").append(SystemConst.POLICY_DELETEED);
			if(conditionMap!=null){
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("vehicle_id"))){
					sb.append(" and p.vehicle_id=").append(conditionMap.get("vehicle_id"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("unit_id"))){
					sb.append(" and p.unit_id=").append(conditionMap.get("unit_id"));
				}
				if(StringUtils.isNotNullOrEmpty(conditionMap.get("cust_id"))){
					sb.append(" and p.customer_id=").append(conditionMap.get("cust_id"));
				}
			}
			return sb;
	    }

	@Override
	public Boolean isRightTime(Long insurance_id, Long vehicle_id, Date startDate, Date endDate)
			throws SystemException {
		StringBuffer sb=new StringBuffer();
		
		String start_date = DateUtil.format(startDate, DateUtil.YMD_DASH_WITH_FULLTIME);
		String end_date = DateUtil.format(endDate, DateUtil.YMD_DASH_WITH_FULLTIME);
		sb.append(" select count(p.insurance_id)  FROM t_ba_insurance p where 1=1 ");
		sb.append(" and p.vehicle_id=").append(vehicle_id);
		sb.append(" and p.is_edate > '").append(start_date).append("'");
		sb.append(" and p.is_bdate < '").append(end_date).append("'");
		sb.append(" and p.status = 2 ");
		if(insurance_id != null){
			sb.append(" and p.insurance_id <> ").append(insurance_id);
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		int total =  ((BigInteger)query.uniqueResult()).intValue();
		return total > 0 ? false : true;
	}

	@Override
	public boolean is_exist(Long insurance_id, String policy_no) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Policy.class);
		criteria.add(Restrictions.eq("policy_no", policy_no));
		if(insurance_id != null){
			criteria.add(Restrictions.not(Restrictions.eq("insurance_id",
					insurance_id)));
		}
		if (criteria.list().size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Long> getPolicys(Policy policy) {
		StringBuffer sb=new StringBuffer();
		sb.append(" select p.insurance_id ");
		sb.append(" FROM t_ba_insurance p ");
		sb.append(" where 1=1 and status!=9 ");
		if(policy!=null){
			if(policy.getUnit_id()!=null){
				sb.append(" and p.unit_id = ").append(policy.getUnit_id());
			}
			if(policy.getVehicle_id()!=null){
				sb.append(" and p.vehicle_id = ").append(policy.getVehicle_id());
			}
			if(policy.getStamp()!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String stamp = sdf.format(policy.getStamp());
				sb.append(" and p.is_edate > '"+stamp+"'");
				sb.append(" and p.is_bdate < '"+stamp+"'");
			}
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		List list = query.list();
		List<Long> result = new ArrayList<Long>();
		for(Object object:list){
			BigInteger insurance_id = (BigInteger)object;
			result.add(insurance_id.longValue());
		}
		return result;
	}
}
