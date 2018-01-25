package com.gboss.dao.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.RepairDao;
import com.gboss.pojo.Repair;
import com.gboss.pojo.Vehicle;
import com.gboss.util.PageSelect;
import com.gboss.util.RandomUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dar.impl
 * @ClassName:RepairDaoImpl
 * @author:xiaoke
 * @date:2015-2-11 上午10:50:24
 */
@Repository("RepairDao")
@Transactional
public class RepairDaoImpl extends BaseDaoImpl implements RepairDao {

	@Override
	public List<Map<String, Object>> findRepairs(Map<String, Object> param,
			String order, boolean isDesc, int pageNo, int pageSize) throws SystemException{
		StringBuffer sb=new StringBuffer();
		sb.append(" select r.rp_id rpId, r.subco_no subcoNo, r.customer_id customerId, r.vehicle_id vehicleId, r.unit_id unitId, r.job_no jobNo,");
		sb.append(" r.customer_name customerName, r.phone,r.plate_no plateNo, r.unittype, r.barcode, r.fix_time fixTime, r.place, ");
		sb.append(" r.reg_time regTime, r.rp_type rpType, r.symptom, r.reg_op_id regOpId, r.reg_op_name regOpName, r.reg_remark regRemark, ");
		sb.append(" r.org_id orgId, dr.status, dr.stamp, dr.op_id acpOpId, dr.op_name acpOpName, r.apt_time aptTime, r.apt_name aptName,");
		sb.append(" r.apt_phone aptPhone, r.rp_place rpPlace, dr.trace_result traceResult, r.worker, r.worker_result workerResult");
		sb.append(" from t_mt_repair r inner join t_mt_repair_dt dr on r.rp_id=dr.rp_id ");
		sb = buildRepairLogPage(param, sb);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append("  order by r.stamp desc");
		}
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
	
	private StringBuffer buildRepairLogPage(Map<String, Object> param, StringBuffer sb){
		sb.append(" where 1=1");
		if(Utils.isNotNullOrEmpty(param.get("vehicle_id"))){
			sb.append(" and r.vehicle_id=").append(param.get("vehicle_id"));
		}
		if(Utils.isNotNullOrEmpty(param.get("startTime"))){
			sb.append(" and dr.stamp>='").append(param.get("startTime")).append(" 0:00:00'");
		}
		if(Utils.isNotNullOrEmpty(param.get("endTime"))){
			sb.append(" and dr.stamp<='").append(param.get("endTime")).append(" 23:59:59'");
		}
		if(Utils.isNotNullOrEmpty(param.get("subco_no"))){
			sb.append(" and r.subco_no=").append(param.get("subco_no"));
		}
		if(Utils.isNotNullOrEmpty(param.get("unit_id"))){
			sb.append(" and r.unit_id=").append(param.get("unit_id"));
		}
		return sb;
	}
	
	@Override
	public Integer countRepairs(Map<String, Object> param)throws SystemException{
		StringBuffer sb=new StringBuffer();
		sb.append(" select count(r.rp_id) ");
		sb.append(" from t_mt_repair r inner join t_mt_repair_dt dr on r.rp_id=dr.rp_id ");
		sb = buildRepairLogPage(param, sb);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	private StringBuffer getCondition4Repairs(StringBuffer sb, Map<String, Object> conditionMap){
		sb.append(" where 1=1 ");
		if(Utils.isNotNullOrEmpty(conditionMap.get("search_type"))){
			String type = conditionMap.get("search_type").toString();
			if(type.equals("center")){	//中心添加、分派维修单
				sb.append(" and r.status not in(6, 7)");
				if(Utils.isNotNullOrEmpty(conditionMap.get("subco_no"))){
					sb.append(" and r.subcoNo=").append(conditionMap.get("subco_no"));
				}
			}else if(type.equals("office")){	//营业处处理维修单
				sb.append(" and r.status not in(1, 6, 7)");
				if(Utils.isNotNullOrEmpty(conditionMap.get("subco_no"))){
					sb.append(" and r.orgId=").append(conditionMap.get("subco_no"));
				}
			}else if(type.equals("latest")){	//上次维修记录
				sb.append(" and r.status in(6, 7)");
			}
		}
		if(conditionMap!=null){
			if(Utils.isNotNullOrEmpty(conditionMap.get("startDate"))){
				sb.append(" and r.stamp>='").append(conditionMap.get("startDate")).append(" 0:00:00'");
			}
			if(Utils.isNotNullOrEmpty(conditionMap.get("endDate"))){
				sb.append(" and r.stamp<='").append(conditionMap.get("endDate")).append(" 23:59:59'");
			}
			if(Utils.isNotNullOrEmpty(conditionMap.get("vehicle_id"))){
				sb.append(" and r.vehicleId=").append(conditionMap.get("vehicle_id"));
			}
			if(Utils.isNotNullOrEmpty(conditionMap.get("unit_id"))){
				sb.append(" and r.unitId=").append(conditionMap.get("unit_id"));
			}
		}
		return sb;
	}

	@Override
	public Repair getRepairByVehicleId(Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" from Repair r");
		sb = getCondition4Repairs(sb, param);
		sb.append(" order by r.stamp desc");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		List<Repair> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public Integer addReserve(Map<String, Object> param) throws SystemException {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String fixTime = Utils.isNullOrEmpty(param.get("fix_time")) ? null : "'"+param.get("fix_time").toString()+"'";
        String jobNo = formatter.format(new Date(System.currentTimeMillis())) + RandomUtil.createNumber(3);
		sb.append(" insert into t_mt_repair (subco_no, customer_id, vehicle_id, unit_id, job_no, customer_name, ")
		.append("phone, plate_no, unittype, barcode, fix_time, place, reg_time, rp_type, symptom, reg_op_id, ")
		.append("reg_op_name, reg_remark, status)");
		sb.append(" values("+param.get("subco_no")+", "+param.get("customer_id")+", "+param.get("vehicle_id")+", "+param.get("unit_id")+", ");
		sb.append(" 'M"+jobNo+"', '"+param.get("customer_name")+"', '"+param.get("phone")+"', '"+param.get("plate_no")+"',");
		sb.append(" '"+param.get("unittype")+"', '"+param.get("barcode")+"', "+fixTime+", '"+param.get("place")+"',");
		sb.append(" now(), "+param.get("repair_type")+", '"+param.get("symptom")+"',");
		sb.append(" "+param.get("op_id")+", '"+param.get("op_name")+"', '"+param.get("reg_remark")+"', "+param.get("status")+")");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return query.executeUpdate();
	}

	@Override
	public Integer addRepairLog(Map<String, Object> param) throws SystemException{
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into t_mt_repair_dt(rp_id, status, stamp, op_id, op_name, trace_result)");
		sb.append(" select r.rp_id, r.status, r.stamp, "+param.get("op_id")+", '"+param.get("op_name")+"', r.trace_result ");
		sb.append(" from t_mt_repair r where r.job_no='"+param.get("jobNo")+"' and r.vehicle_id=").append(param.get("vehicle_id"));
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return query.executeUpdate();
	}
	
	@Override
	public Integer updateReserve(Map<String, Object> param) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" update t_mt_repair set reg_op_name='"+param.get("op_name")+"'");
		sb.append(" , rp_type="+param.get("repair_type")+"");
		sb.append(" , phone='"+param.get("phone")+"'");
		sb.append(" , symptom='"+param.get("symptom")+"'");
		sb.append(" , place='"+param.get("place")+"'");
		sb.append(" , reg_remark='"+param.get("reg_remark")+"'");
		sb.append(" where vehicle_id="+param.get("vehicle_id")+" and status not in (6, 7)");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return query.executeUpdate();
	}

	@Override
	public Integer assignJob(Map<String, Object> param) throws SystemException{
		StringBuffer sb = new StringBuffer();
		sb.append(" update t_mt_repair set status=").append(param.get("status"));
		sb.append(" , org_id=").append(param.get("subco_no"));
		sb.append(" where vehicle_id="+param.get("vehicle_id")+" and status=1");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return query.executeUpdate();
	}

	@Override
	public Page<Repair> search(PageSelect<Repair> pageSelect, Long subcoNo)
			throws SystemException {String hql = "from Repair where 1=1 ";
			if(subcoNo!=2L){
				if(subcoNo==101L){
					hql += " and (orgId=" + subcoNo + " or orgId = 2)";
				}else{
					hql += " and orgId=" + subcoNo;
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
			Page<Repair> page = PageUtil.getPage(count, pageSelect.getPageNo(),
					list, pageSelect.getPageSize());
			return page;
	}

	@Override
	public Integer saveRepairReserve(Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" update t_mt_repair set acp_op_id="+param.get("op_id")+"");
		sb.append(" , acp_op_name='"+param.get("op_name")+"'");
		sb.append(" , apt_time='"+param.get("apt_time")+"'");
		sb.append(" , apt_name='"+param.get("apt_name")+"'");
		sb.append(" , apt_phone='"+param.get("apt_phone")+"'");
		sb.append(" , rp_place='"+param.get("rp_place")+"'");
		sb.append(" , status="+param.get("status")+"");
		sb.append(" , trace_result='"+param.get("trace_result")+"'");
		sb.append(" where vehicle_id="+param.get("vehicle_id")+" and status in (2)");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return query.executeUpdate();
	}

	@Override
	public Integer saveRepairPaiGong(Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" update t_mt_repair set ");
		sb.append(" worker='"+param.get("worker")+"'");
		sb.append(" , status="+param.get("status")+"");
		sb.append(" , trace_result='"+param.get("trace_result")+"'");
		sb.append(" where vehicle_id="+param.get("vehicle_id")+" and status in (3)");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return query.executeUpdate();
	}

	@Override
	public Integer saveRepairDoing(Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" update t_mt_repair set ");
		sb.append(" status="+param.get("status")+"");
		sb.append(" , trace_result='"+param.get("trace_result")+"'");
		sb.append(" where vehicle_id="+param.get("vehicle_id")+" and status in (4)");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return query.executeUpdate();
	}

	@Override
	public Integer saveRepairSuccess(Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" update t_mt_repair set ");
		sb.append(" worker_result='"+param.get("worker_result")+"'");
		sb.append(" , status="+param.get("status")+"");
		sb.append(" , trace_result='"+param.get("trace_result")+"'");
		sb.append(" where vehicle_id="+param.get("vehicle_id")+" and status in (5)");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return query.executeUpdate();
	}

	@Override
	public Integer saveRepairUndo(Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" update t_mt_repair set ");
		sb.append(" worker_result='"+param.get("worker_result")+"'");
		sb.append(" , status="+param.get("status")+"");
		sb.append(" , trace_result='"+param.get("trace_result")+"'");
		sb.append(" where vehicle_id="+param.get("vehicle_id")+" and status in (5)");
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return query.executeUpdate();
	}
	
	private StringBuffer buildRepairOrderSql(Map<String, Object> param, StringBuffer sb){
		sb.append(" where 1=1");
		if(Utils.isNotNullOrEmpty(param.get("subco_no"))){
			sb.append(" and r.subco_no=").append(param.get("subco_no"));
		}
		if(Utils.isNotNullOrEmpty(param.get("startDate"))){
			sb.append(" and r.reg_time>='").append(param.get("startDate")).append(" 0:00:00'");
		}
		if(Utils.isNotNullOrEmpty(param.get("endDate"))){
			sb.append(" and r.reg_time<='").append(param.get("endDate")).append(" 23:59:59'");
		}
		if(Utils.isNotNullOrEmpty(param.get("customerName"))){
			sb.append(" and c.customer_name like'%").append(param.get("customerName")).append("%'");
		}
		if(Utils.isNotNullOrEmpty(param.get("plateNo"))){
			sb.append(" and v.plate_no like'%").append(param.get("plateNo")).append("%'");
		}
		if(Utils.isNotNullOrEmpty(param.get("callLetter"))){
			sb.append(" and u.call_letter like'%").append(param.get("callLetter")).append("%'");
		}
		if(Utils.isNotNullOrEmpty(param.get("status"))){
			sb.append(" and r.status=").append(param.get("status"));
		}
		return sb;
	}

	@Override
	public int countRepairOrder(Map<String, Object> param)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(r.rp_id) ");
		sb.append(" from t_mt_repair r inner join t_ba_customer c on r.customer_id=c.customer_id");
		sb.append(" inner join t_ba_vehicle v on r.vehicle_id=v.vehicle_id");
		sb.append(" inner join t_ba_unit u on r.unit_id=u.unit_id");
		sb = buildRepairOrderSql(param, sb);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	@Override
	public List<Map<String, Object>> findRepairOrder(
			Map<String, Object> param, String order, boolean isDesc,
			int pageNo, int pageSize) throws SystemException {
		StringBuffer sb=new StringBuffer();
		sb.append(" select r.rp_id rpId, r.subco_no subcoNo, r.customer_id customerId, r.vehicle_id vehicleId, r.unit_id unitId, r.job_no jobNo,");
		sb.append(" r.customer_name customerName, r.phone,r.plate_no plateNo, r.unittype, r.barcode, r.fix_time fixTime, r.place, ");
		sb.append(" r.reg_time regTime, r.rp_type rpType, r.symptom, r.reg_op_id regOpId, r.reg_op_name regOpName, r.reg_remark regRemark, ");
		sb.append(" r.org_id orgId, r.status, r.stamp, r.apt_time aptTime, r.apt_name aptName,");
		sb.append(" (case when r.status=1 || r.status=2 then r.reg_op_name else r.acp_op_name end) acpOpName,");
		sb.append(" r.apt_phone aptPhone, r.rp_place rpPlace, r.trace_result traceResult, r.worker, r.worker_result workerResult");
		sb.append(" from t_mt_repair r inner join t_ba_customer c on r.customer_id=c.customer_id");
		sb.append(" inner join t_ba_vehicle v on r.vehicle_id=v.vehicle_id");
		sb.append(" inner join t_ba_unit u on r.unit_id=u.unit_id");
		sb = buildRepairOrderSql(param, sb);
		if (StringUtils.isNotBlank(order)) {
			sb.append(" order by ").append(order);
			if (isDesc) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append("  order by r.stamp desc");
		}
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
}

