package cc.chinagps.seat.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.table.ComplaintTable;
import cc.chinagps.seat.dao.ComplaintDao;

@Repository
public class ComplaintDaoImpl extends BasicDao implements ComplaintDao {

	@Override
	public  void saveOrUpdate(ComplaintTable complaintTable) {
		getSession().saveOrUpdate(complaintTable);
	}
	@Override
	public List<HashMap<String, Object>> getComplaint(ReportCommonQuery query,
			Map<String, String>param) {
		StringBuffer sb = new StringBuffer();
		sb.append("select s.*,v.plate_no,c.customer_name,v.subco_no,sv.sname,u.call_letter ");
        sb = getComplaintHql(sb, param,query);   
        sb.append(" order by s.stamp desc");
        Query complaintQuery = getSession().createSQLQuery(sb.toString());
        complaintQuery.setParameter("startTime", query.getBeginTime()).setParameter("endTime", query.getEndTime());
        if (query.getLength() > 0) {
        	complaintQuery.setMaxResults(query.getLength());
		}
        complaintQuery.setFirstResult(query.getStart());
        complaintQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return complaintQuery.list();	
	}
	private StringBuffer getComplaintHql(StringBuffer sb,Map<String, String>param ,ReportCommonQuery query){
		sb.append(" from t_seat_complaint s ");
		sb.append(" left join t_ba_vehicle v on s.vehicle_id = v.vehicle_id");
		sb.append(" left join t_ba_unit u on v.vehicle_id = u.vehicle_id");
		sb.append(" left join t_ba_customer c on u.customer_id = c.customer_id");
		sb.append(" left join t_sys_value sv on v.subco_no = sv.svalue");
		sb.append(" where s.flag=0 and sv.stype=1010");
		sb.append(" AND s.stamp >= :startTime ");
		sb.append(" AND s.stamp <= :endTime ");
		String subco_no = param.get("subco_no");
		if(subco_no!=null&&!subco_no.equals("")){
			sb.append(" and v.subco_no=").append(subco_no);
		}	
		
		String status_id = param.get("status_id");
		if(status_id != null && !"".equals(status_id)){
			sb.append(" and s.status_id =").append(status_id);
		}
		
		String op_name = param.get("op_name");
		if(op_name != null && !"".equals(op_name)){
			sb.append(" and s.op_name like '%").append(op_name).append("%'");
		}
		
		String ac_op_name = param.get("ac_op_name");
		if(ac_op_name != null && !"".equals(ac_op_name)){
			sb.append(" and s.ac_op_name like '%").append(ac_op_name).append("%'");
		}
		
		String vehicle_id= param.get("vehicle_id");
		if(vehicle_id!=null && !vehicle_id.trim().equals("")){
			sb.append(" and v.vehicle_id=").append(vehicle_id);
		}
		String partNo = param.get("partNo");
		if (partNo != null && !partNo.equals("")) {
			sb.append(" and (u.callLetter like ")
				.append("'%").append(partNo).append("%' ")
				.append("or v.plate_no like ")
				.append("'%").append(partNo).append("%') ");
		}
		String customer_name = param.get("customer_name");
		if(customer_name!=null&&!customer_name.equals("")){
			sb.append(" and c.customer_name like")
			.append("'%").append(customer_name).append("%' ");
		}
		String cp_phone = param.get("cp_phone");
		if (cp_phone != null && !cp_phone.equals("")) {
			sb.append(" and  s.cp_phone = ").append(cp_phone);
		}
		String cp_id = param.get("cp_id");
		if (cp_id != null && !cp_id.equals("")) {
			sb.append(" and  s.cp_id = ").append(cp_id);
		}
		return sb;
		}
	@Override
	public int getComplaintCount(ReportCommonQuery query,
			Map<String, String> param) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) ");
		sb = getComplaintHql(sb, param,query);
		Query complaintQuery = getSession().createSQLQuery(
				sb.toString());
		complaintQuery.setParameter("startTime", query.getBeginTime()).setParameter("endTime", query.getEndTime());
		return ((BigInteger) complaintQuery.uniqueResult()).intValue();
	}
	
	@Override
	public int deleteComplaint(long id) {
		String sql = " update t_seat_complaint set flag=1 where cp_id="+id;
		Query query = getSession().createSQLQuery(sql);
		return query.executeUpdate();
	}
	
	public ComplaintTable getById(long id){
		Criteria criteria = getSession().createCriteria(ComplaintTable.class); 
		criteria.add(Restrictions.eq("cp_id", id));
		List<ComplaintTable>  list = criteria.list();
		if(criteria.list().size() > 0){
			return list.get(0);
		}
		return null;
	}
}
