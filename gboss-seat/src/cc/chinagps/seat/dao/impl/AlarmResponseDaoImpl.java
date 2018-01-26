package cc.chinagps.seat.dao.impl;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.AlarmResponse;
import cc.chinagps.seat.comm.SeatException;
import cc.chinagps.seat.dao.AlarmResponseDao;

@Repository("alarmResponseDao")
public class AlarmResponseDaoImpl extends BasicDao implements AlarmResponseDao {

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAlarmResponse(Long vehicle_id, Long stolen_id)
			throws SeatException {
		Query query = getAlarmResponseSQL(vehicle_id, stolen_id);
		return (Map<String, Object>) query.uniqueResult();
	}
	
	public Query getAlarmResponseSQL(Long vehicle_id, Long stolen_id){
		StringBuffer SQL = new StringBuffer();
		SQL.append(" SELECT v.plate_no , c.customer_name ,v.vehicle_id ,  ");
		SQL.append(" v.model_name,v.color,v.engine_no,c.subco_name cstm_subco_name ,sv.id stolen_id , ");
		SQL.append(" GROUP_CONCAT(lm.linkman,':',lm.phone) linkmans ,r.res_id, r.vehicle_id rv_id , r.res_type, " );
		SQL.append(" r.map_id, r.alarm_plate, r.alarm_mans ,r.alarm_subco_name , DATE_FORMAT(r.alarm_date,'%Y-%m-%d %H:%s:%i' ) alarm_date, DATE_FORMAT(r.notice_time,'%Y-%m-%d %H:%s:%i' )  notice_time ,DATE_FORMAT(r.departure_time,'%Y-%m-%d %H:%s:%i' )  departure_time, r.departure, DATE_FORMAT(r.arrival_time ,'%Y-%m-%d %H:%s:%i' ) arrival_time, " );
		SQL.append(" DATE_FORMAT(r.back_time,'%Y-%m-%d %H:%s:%i' ) back_time, r.res_place, r.customer_id, r.phone_signal, r.gps_signal, r.is_std_depot, r.is_monitor,  " );
		SQL.append(" r.has_cullet, r.search_scope, r.search_result, r.has_police, r.police_station, r.env_remark,  " );
		SQL.append(" r.vehicle_outside, r.doorlock_remark, r.vehicle_inside, r.unit_chk, r.line_damage, r.locate_func,  " );
		SQL.append(" r.alarm_func, r.call_func, r.lock_oil_func, r.defences_func, r.nav_func, r.cstm_request, r.cstm_sign, " );
		SQL.append(" r.op_name, r.op_id, r.stamp   " );
		SQL.append(" FROM t_ba_vehicle v LEFT JOIN t_ba_unit u  ON v.vehicle_id = u.vehicle_id  ");
		SQL.append(" LEFT JOIN t_ba_linkman lm ON lm.customer_id = u.customer_id AND lm.vehicle_id = v.vehicle_id ");
		SQL.append(" LEFT JOIN t_ba_customer c ON c.customer_id = u.customer_id ");
		SQL.append(" LEFT JOIN t_seat_respones r ON r.vehicle_id = v.vehicle_id ");
		SQL.append(" LEFT JOIN t_seat_stolen_vehicle sv on sv.vehicleId = v.vehicle_id ");
		SQL.append(" LEFT JOIN t_al_vehiclestatus av ON av.unit_id = u.unit_id ");
		SQL.append(" LEFT JOIN t_al_status st on st.status_id = av.status_id  ");
		SQL.append(" WHERE v.vehicle_id = :vehicle_id and sv.id  =:stolen_id ");
		SQL.append(" GROUP BY v.vehicle_id ");
		Query query = getSession().createSQLQuery(SQL.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setLong("vehicle_id", vehicle_id).setLong("stolen_id", stolen_id);
		return query;
	}

	@Override
	public Long saveAlarmResponse(AlarmResponse alarmResponse)
			throws SeatException {
		getSession().saveOrUpdate(alarmResponse);
		return alarmResponse.getRes_id();
	}

}
