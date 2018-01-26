package cc.chinagps.seat.dao.impl;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.EventReport;
import cc.chinagps.seat.comm.SeatException;
import cc.chinagps.seat.dao.EventReportDao;

@Repository("eventReportDao")
public class EventReportDaoImpl extends BasicDao implements EventReportDao {
	
	public long saveImpEventReport(EventReport eventReport) throws SeatException {
		getSession().saveOrUpdate(eventReport);
		return eventReport.getE_id();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getEventReport(Long vehicle_id ,Long stolen_id) throws SeatException {
		
		Query query = getEventReportSQL(vehicle_id,stolen_id);
		return (Map<String,Object>) query.uniqueResult();
	}
	
	public Query getEventReportSQL(Long vehicle_id,Long stolen_id){
		StringBuffer SQL = new StringBuffer();
		SQL.append(" SELECT er.e_id,v.vehicle_id,er.e_no,er.callin_brief,u.unit_id,u.unittype_id , c.customer_name , v.plate_no  ,er.e_brief  ,v.model_name  ,");
		SQL.append(" er.place , er.is_std_depot   , er.vehicle_state ,er.ic_name,");
		SQL.append(" DATE_FORMAT(u.fix_time,'%Y-%m-%d %H:%s:%i' ) fix_time ,ut.unittype ,er.monitor ,");
		SQL.append(" er.speed ,er.driver_time ,up.flag , DATE_FORMAT(up.e_time,'%Y-%m-%d %H:%s:%i' ) e_time ,");
		SQL.append(" er.up_way ,case when u.tamper_box = 0 then '否' when u.tamper_box =1 then '是' end as tamper_box , case when u.tamper_wireless = 0 then '否' when u.tamper_wireless =1 then '是' end as tamper_wireless  ,");
		SQL.append(" case when v.is_pilfer = 0 then '否' when v.is_pilfer =1 then '是' end as is_pilfer   ,i.ic_no ,i.insurance_id ,er.alarm_work ,");
		SQL.append(" er.alarm_driver  ,DATE_FORMAT(er.alarm_notice_time,'%Y-%m-%d %H:%s:%i' )  alarm_notice_time  , DATE_FORMAT(er.departure_time,'%Y-%m-%d %H:%s:%i' )  departure_time   ,er.departure  ,");
		SQL.append(" er.alarm_plate  , DATE_FORMAT(er.arrival_time,'%Y-%m-%d %H:%s:%i' )  arrival_time  , DATE_FORMAT(er.back_time,'%Y-%m-%d %H:%s:%i' )  back_time  ,er.work_result  ,");
		SQL.append(" er.event_content  ,er.cstm_request  ,er.ac_remark  ,er.collabora_dept  ,");
		SQL.append(" er.ac_op_name  , DATE_FORMAT(er.forward_date,'%Y-%m-%d %H:%s:%i' ) forward_date  ,er.handle_result  ,er.ret_remark  ,");
		SQL.append(" er.cs  , DATE_FORMAT(er.ret_date,'%Y-%m-%d %H:%s:%i' ) ret_date  ,er.remark , sv.id stolen_id , DATE_FORMAT(er.ac_stamp,'%Y-%m-%d %H:%s:%i' ) ac_stamp, st.status_name ");
		SQL.append(" from t_ba_vehicle v ");
		SQL.append(" LEFT JOIN t_ba_unit u ON v.vehicle_id = u.vehicle_id  and v.subco_no = u.subco_no ");
		SQL.append(" LEFT JOIN t_ba_unittype ut ON ut.unittype_id = u.unittype_id ");
		SQL.append(" LEFT JOIN t_ba_insurance i ON v.vehicle_id = i.vehicle_id ");
		SQL.append(" LEFT JOIN t_ba_customer c ON c.customer_id = u.customer_id ");
		SQL.append(" LEFT JOIN t_seat_event_report er ON er.vehicle_id = v.vehicle_id and er.e_state = 0  ");
		SQL.append(" LEFT JOIN t_st_unit_upgrade up  ON up.call_letter = u.call_letter ");
		SQL.append(" LEFT JOIN t_seat_stolen_vehicle sv on sv.vehicleId = v.vehicle_id ");
		SQL.append(" LEFT JOIN t_al_vehiclestatus av ON av.unit_id = u.unit_id ");
		SQL.append(" LEFT JOIN t_al_status st on st.status_id = av.status_id ");
		SQL.append(" WHERE v.vehicle_id = :vehicle_id and sv.id = :stolen_id");
		Query query = getSession().createSQLQuery(SQL.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setLong("vehicle_id", vehicle_id).setLong("stolen_id", stolen_id);
		return query;
	}
	
	public static void main(String[] args) {
		ApplicationContext bf = new ClassPathXmlApplicationContext("applicationContext-dao.xml");
	}
	
}

