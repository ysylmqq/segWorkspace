package cc.chinagps.seat.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_seat_respones")
public class AlarmResponse implements java.io.Serializable {

	private static final long serialVersionUID = -5390151960602995251L;
	
	private Long res_id;
	private Long vehicle_id;
	private Integer res_type;
	private Long map_id;
	private String alarm_plate;
	private Timestamp alarm_date;
	private Timestamp notice_time;
	private Timestamp departure_time;
	private String departure;
	private Timestamp arrival_time;
	private Timestamp back_time;
	private String res_place;
	private String alarm_mans;
	private String alarm_subco_name;
	private Long customer_id;
	private Integer phone_signal;
	private Integer gps_signal;
	private Integer is_std_depot;
	private Integer is_monitor;
	private Integer has_cullet;
	private String search_scope;
	private Integer search_result;
	private Integer has_police;
	private String police_station;
	private String env_remark;
	private String vehicle_outside;
	private String doorlock_remark;
	private String vehicle_inside;
	private Integer unit_chk;
	private Integer line_damage;
	private String damage_part;
	private Integer locate_func;
	private Integer alarm_func;
	private Integer call_func;
	private Integer lock_oil_func;
	private Integer defences_func;
	private Integer nav_func;
	private String cstm_request;
	private String cstm_sign;
	private String op_name;
	private Long op_id;
	private Timestamp stamp;

	/** default constructor */
	public AlarmResponse() {
	}

	/** minimal constructor */
	public AlarmResponse(Timestamp alarmDate, Timestamp noticeTime,
			Timestamp departureTime, Timestamp arrivalTime, Timestamp backTime,
			Timestamp stamp) {
		this.alarm_date = alarmDate;
		this.notice_time = noticeTime;
		this.departure_time = departureTime;
		this.arrival_time = arrivalTime;
		this.back_time = backTime;
		this.stamp = stamp;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "res_id", unique = true, nullable = false)
	public Long getRes_id() {
		return this.res_id;
	}

	public void setRes_id(Long res_id) {
		this.res_id = res_id;
	}
	
	@Column(name="alarm_mans")
	public String getAlarm_mans() {
		return alarm_mans;
	}

	public void setAlarm_mans(String alarm_mans) {
		this.alarm_mans = alarm_mans;
	}

	@Column(name="damage_part")
	public String getDamage_part() {
		return damage_part;
	}

	public void setDamage_part(String damage_part) {
		this.damage_part = damage_part;
	}

	@Column(name="alarm_subco_name")
	public String getAlarm_subco_name() {
		return alarm_subco_name;
	}

	public void setAlarm_subco_name(String alarm_subco_name) {
		this.alarm_subco_name = alarm_subco_name;
	}
	
	@Column(name="vehicle_id")
	public Long getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	@Column(name = "res_type")
	public Integer getRes_type() {
		return this.res_type;
	}

	public void setRes_type(Integer res_type) {
		this.res_type = res_type;
	}

	@Column(name = "map_id")
	public Long getMap_id() {
		return this.map_id;
	}

	public void setMap_id(Long map_id) {
		this.map_id = map_id;
	}

	@Column(name = "alarm_plate", length = 20)
	public String getAlarm_plate() {
		return this.alarm_plate;
	}

	public void setAlarm_plate(String alarmPlate) {
		this.alarm_plate = alarmPlate;
	}

	@Column(name = "alarm_date", nullable = false, length = 19)
	public Timestamp getAlarm_date() {
		return this.alarm_date;
	}

	public void setAlarm_date(Timestamp alarmDate) {
		this.alarm_date = alarmDate;
	}

	@Column(name = "notice_time", nullable = false, length = 19)
	public Timestamp getNoticeTime() {
		return this.notice_time;
	}

	public void setNoticeTime(Timestamp noticeTime) {
		this.notice_time = noticeTime;
	}

	@Column(name = "departure_time", nullable = false, length = 19)
	public Timestamp getDeparture_time() {
		return this.departure_time;
	}

	public void setDeparture_time(Timestamp departureTime) {
		this.departure_time = departureTime;
	}

	@Column(name = "departure", length = 200)
	public String getDeparture() {
		return this.departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	@Column(name = "arrival_time", nullable = false, length = 19)
	public Timestamp getArrival_time() {
		return this.arrival_time;
	}

	public void setArrival_time(Timestamp arrival_time) {
		this.arrival_time = arrival_time;
	}

	@Column(name = "back_time", nullable = false, length = 19)
	public Timestamp getBack_time() {
		return this.back_time;
	}

	public void setBack_time(Timestamp backTime) {
		this.back_time = backTime;
	}

	@Column(name = "res_place", length = 200)
	public String getRes_place() {
		return this.res_place;
	}

	public void setRes_place(String resPlace) {
		this.res_place = resPlace;
	}

	@Column(name = "customer_id")
	public Long getCustomer_id() {
		return this.customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	@Column(name = "phone_signal")
	public Integer getPhone_signal() {
		return this.phone_signal;
	}

	public void setPhone_signal(Integer phoneSignal) {
		this.phone_signal = phoneSignal;
	}

	@Column(name = "gps_signal")
	public Integer getGps_signal() {
		return this.gps_signal;
	}

	public void setGps_signal(Integer gps_signal) {
		this.gps_signal = gps_signal;
	}

	@Column(name = "is_std_depot")
	public Integer getIs_std_depot() {
		return this.is_std_depot;
	}

	public void setIs_std_depot(Integer is_std_depot) {
		this.is_std_depot = is_std_depot;
	}

	@Column(name = "is_monitor")
	public Integer getIs_monitor() {
		return this.is_monitor;
	}

	public void setIs_monitor(Integer is_monitor) {
		this.is_monitor = is_monitor;
	}

	@Column(name = "has_cullet")
	public Integer getHas_cullet() {
		return this.has_cullet;
	}

	public void setHas_cullet(Integer has_cullet) {
		this.has_cullet = has_cullet;
	}

	@Column(name = "search_scope", length = 200)
	public String getSearch_scope() {
		return this.search_scope;
	}

	public void setSearch_scope(String search_scope) {
		this.search_scope = search_scope;
	}

	@Column(name = "search_result")
	public Integer getSearch_result() {
		return this.search_result;
	}

	public void setSearch_result(Integer search_result) {
		this.search_result = search_result;
	}

	@Column(name = "has_police")
	public Integer getHas_police() {
		return this.has_police;
	}

	public void setHas_police(Integer has_police) {
		this.has_police = has_police;
	}

	@Column(name = "police_station", length = 30)
	public String getPolice_station() {
		return this.police_station;
	}

	public void setPolice_station(String police_station) {
		this.police_station = police_station;
	}

	@Column(name = "env_remark", length = 100)
	public String getEnv_remark() {
		return this.env_remark;
	}

	public void setEnv_remark(String env_remark) {
		this.env_remark = env_remark;
	}

	@Column(name = "vehicle_outside", length = 200)
	public String getVehicle_outside() {
		return this.vehicle_outside;
	}

	public void setVehicle_outside(String vehicle_outside) {
		this.vehicle_outside = vehicle_outside;
	}

	@Column(name = "doorlock_remark", length = 100)
	public String getDoorlock_remark() {
		return this.doorlock_remark;
	}

	public void setDoorlock_remark(String doorlock_remark) {
		this.doorlock_remark = doorlock_remark;
	}

	@Column(name = "vehicle_inside", length = 100)
	public String getVehicle_inside() {
		return this.vehicle_inside;
	}

	public void setVehicle_inside(String vehicle_inside) {
		this.vehicle_inside = vehicle_inside;
	}

	@Column(name = "unit_chk")
	public Integer getUnit_chk() {
		return this.unit_chk;
	}

	public void setUnit_chk(Integer unit_chk) {
		this.unit_chk = unit_chk;
	}

	@Column(name = "line_damage")
	public Integer getLine_damage() {
		return this.line_damage;
	}

	public void setLine_damage(Integer line_damage) {
		this.line_damage = line_damage;
	}

	@Column(name = "locate_func")
	public Integer getLocate_func() {
		return this.locate_func;
	}

	public void setLocate_func(Integer locate_func) {
		this.locate_func = locate_func;
	}

	@Column(name = "alarm_func")
	public Integer getAlarm_func() {
		return this.alarm_func;
	}

	public void setAlarm_func(Integer alarm_func) {
		this.alarm_func = alarm_func;
	}

	@Column(name = "call_func")
	public Integer getCall_func() {
		return this.call_func;
	}

	public void setCall_func(Integer call_func) {
		this.call_func = call_func;
	}

	@Column(name = "lock_oil_func")
	public Integer getLock_oil_func() {
		return this.lock_oil_func;
	}

	public void setLock_oil_func(Integer lock_oil_func) {
		this.lock_oil_func = lock_oil_func;
	}

	@Column(name = "defences_func")
	public Integer getDefences_func() {
		return this.defences_func;
	}

	public void setDefences_func(Integer defences_func) {
		this.defences_func = defences_func;
	}

	@Column(name = "nav_func")
	public Integer getNav_func() {
		return this.nav_func;
	}

	public void setNav_func(Integer nav_func) {
		this.nav_func = nav_func;
	}

	@Column(name = "cstm_request", length = 1000)
	public String getCstm_request() {
		return this.cstm_request;
	}

	public void setCstm_request(String cstm_request) {
		this.cstm_request = cstm_request;
	}

	@Column(name = "cstm_sign", length = 50)
	public String getCstm_sign() {
		return this.cstm_sign;
	}

	public void setCstm_sign(String cstm_sign) {
		this.cstm_sign = cstm_sign;
	}

	@Column(name = "op_name", length = 50)
	public String getOp_name() {
		return this.op_name;
	}

	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}

	@Column(name = "op_id")
	public Long getOp_id() {
		return this.op_id;
	}

	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}

	@Column(name = "stamp", nullable = false, length = 19)
	public Timestamp getStamp() {
		return this.stamp;
	}

	public void setStamp(Timestamp stamp) {
		this.stamp = stamp;
	}

}