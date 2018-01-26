package cc.chinagps.seat.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_seat_event_report")
public class EventReport implements java.io.Serializable {

	private static final long serialVersionUID = 8670457644615134582L;
	private Long e_id;
	private String e_brief;
	private String e_no;
	private int e_state;
	private Long vehicle_id;
	private Date ac_stamp;
	private String place;
	private String is_std_depot;
	private String vehicle_state;
	private Long unit_id;
	private String monitor;
	private Long op_id;
	private String op_name;
	private Double speed;
	private Double driver_time;
	private String up_way;
	private Long upgrade_id;
	private Long insurance_id;
	private String ic_name;
	private String alarm_work;
	private String alarm_driver;
	private Date alarm_notice_time;
	private Date departure_time;
	private String departure;
	private String alarm_plate;
	private Date arrival_time;
	private Date back_time;
	private String work_result;
	private String event_content;
	private String cstm_request;
	private String ac_remark;
	private String collabora_dept;
	private Long ac_op_id;
	private String ac_op_name;
	private Date forward_date;
	private String handle_result;
	private String ret_remark;
	private String cs;
	private Date ret_date;
	private String remark;
	private Date stamp;
	private String callin_brief;

	@Column(name="callin_brief")
	public String getCallin_brief() {
		return callin_brief;
	}

	public void setCallin_brief(String callin_brief) {
		this.callin_brief = callin_brief;
	}

	/** default constructor */
	public EventReport() {
	}

	/** minimal constructor */
	public EventReport(Date acStamp, Date alarmNoticeTime,
			Date departureTime, Date arrivalTime, Date backTime,
			Date forwardDate, Date retDate, Date stamp) {
		this.ac_stamp = acStamp;
		this.alarm_notice_time = alarmNoticeTime;
		this.departure_time = departureTime;
		this.arrival_time = arrivalTime;
		this.back_time = backTime;
		this.forward_date = forwardDate;
		this.ret_date = retDate;
		this.stamp = stamp;
	}

	/** full constructor */
	public EventReport(String EBrief, String ENo, 
			Long vehicleId, Date acStamp, String place, String isStdDepot,
			String vehicle_state, Long unitId, String monitor, Long opId,
			String opName, Double speed, Double driverTime, String upWay,
			Long upgradeId, Long insuranceId, String isName, String alarmWork,
			String alarmDriver, Date alarmNoticeTime,
			Date departureTime, String departure, String alarmPlate,
			Date arrivalTime, Date backTime, String workResult,
			String eventContent, String cstmRequest, String acRemark,
			String collaboraDept, Long acOpId, String acOpName,
			Date forwardDate, String handleResult, String retRemark,
			String cs, Date retDate, String remark, Date stamp) {
		this.e_brief = EBrief;
		this.e_no = ENo;
		this.vehicle_id = vehicleId;
		this.ac_stamp = acStamp;
		this.place = place;
		this.is_std_depot = isStdDepot;
		this.vehicle_state = vehicle_state;
		this.unit_id = unitId;
		this.monitor = monitor;
		this.op_id = opId;
		this.op_name = opName;
		this.speed = speed;
		this.driver_time = driverTime;
		this.up_way = upWay;
		this.upgrade_id = upgradeId;
		this.insurance_id = insuranceId;
		this.ic_name = isName;
		this.alarm_work = alarmWork;
		this.alarm_driver = alarmDriver;
		this.alarm_notice_time = alarmNoticeTime;
		this.departure_time = departureTime;
		this.departure = departure;
		this.alarm_plate = alarmPlate;
		this.arrival_time = arrivalTime;
		this.back_time = backTime;
		this.work_result = workResult;
		this.event_content = eventContent;
		this.cstm_request = cstmRequest;
		this.ac_remark = acRemark;
		this.collabora_dept = collaboraDept;
		this.ac_op_id = acOpId;
		this.ac_op_name = acOpName;
		this.forward_date = forwardDate;
		this.handle_result = handleResult;
		this.ret_remark = retRemark;
		this.cs = cs;
		this.ret_date = retDate;
		this.remark = remark;
		this.stamp = stamp;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "e_id",   nullable = false)
	public Long getE_id() {
		return this.e_id;
	}

	public void setE_id(Long e_id) {
		this.e_id = e_id;
	}

	public int getE_state() {
		return e_state;
	}

	public void setE_state(int e_state) {
		this.e_state = e_state;
	}

	@Column(name = "e_brief")
	public String getE_brief() {
		return this.e_brief;
	}

	public void setE_brief(String e_brief) {
		this.e_brief = e_brief;
	}

	@Column(name = "e_no")
	public String getE_no() {
		return this.e_no;
	}

	public void setE_no(String e_no) {
		this.e_no = e_no;
	}

	@Column(name = "vehicle_id")
	public Long getVehicle_id() {
		return this.vehicle_id;
	}

	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	@Column(name = "ac_stamp")
	public Date getAc_stamp() {
		return this.ac_stamp;
	}

	public void setAc_stamp(Date ac_stamp) {
		this.ac_stamp = ac_stamp;
	}

	@Column(name = "place")
	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Column(name = "is_std_depot")
	public String getIs_std_depot() {
		return this.is_std_depot;
	}

	public void setIs_std_depot(String is_std_depot) {
		this.is_std_depot = is_std_depot;
	}

	@Column(name = "vehicle_state")
	public String getVehicle_state() {
		return this.vehicle_state;
	}

	public void setVehicle_state(String vehicle_state) {
		this.vehicle_state = vehicle_state;
	}

	@Column(name = "unit_id")
	public Long getUnit_id() {
		return this.unit_id;
	}

	public void setUnit_id(Long unit_id) {
		this.unit_id = unit_id;
	}

	@Column(name = "monitor")
	public String getMonitor() {
		return this.monitor;
	}

	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}

	@Column(name = "op_id")
	public Long getOp_id() {
		return this.op_id;
	}

	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}

	@Column(name = "op_name")
	public String getOp_name() {
		return this.op_name;
	}

	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}

	@Column(name = "speed", precision = 22, scale = 0)
	public Double getSpeed() {
		return this.speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	@Column(name = "driver_time")
	public Double getDriver_time() {
		return this.driver_time;
	}

	public void setDriver_time(Double driver_time) {
		this.driver_time = driver_time;
	}

	@Column(name = "up_way")
	public String getUp_way() {
		return this.up_way;
	}

	public void setUp_way(String up_way) {
		this.up_way = up_way;
	}

	@Column(name = "upgrade_id")
	public Long getUpgrade_id() {
		return this.upgrade_id;
	}

	public void setUpgrade_id(Long upgrade_id) {
		this.upgrade_id = upgrade_id;
	}

	@Column(name = "insurance_id")
	public Long getInsurance_id() {
		return this.insurance_id;
	}

	public void setInsurance_id(Long insurance_id) {
		this.insurance_id = insurance_id;
	}

	@Column(name = "ic_name")
	public String getIc_name() {
		return this.ic_name;
	}

	public void setIc_name(String ic_name) {
		this.ic_name = ic_name;
	}

	@Column(name = "alarm_work")
	public String getAlarm_work() {
		return this.alarm_work;
	}

	public void setAlarm_work(String alarm_work) {
		this.alarm_work = alarm_work;
	}

	@Column(name = "alarm_driver")
	public String getAlarm_driver() {
		return this.alarm_driver;
	}

	public void setAlarm_driver(String alarm_driver) {
		this.alarm_driver = alarm_driver;
	}

	@Column(name = "alarm_notice_time")
	public Date getAlarm_notice_time() {
		return this.alarm_notice_time;
	}

	public void setAlarm_notice_time(Date alarm_notice_time) {
		this.alarm_notice_time = alarm_notice_time;
	}

	@Column(name = "departure_time")
	public Date getDeparture_time() {
		return this.departure_time;
	}

	public void setDeparture_time(Date departure_time) {
		this.departure_time = departure_time;
	}

	@Column(name = "departure")
	public String getDeparture() {
		return this.departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	@Column(name = "alarm_plate")
	public String getAlarm_plate() {
		return this.alarm_plate;
	}

	public void setAlarm_plate(String alarm_plate) {
		this.alarm_plate = alarm_plate;
	}

	@Column(name = "arrival_time")
	public Date getArrival_time() {
		return this.arrival_time;
	}

	public void setArrival_time(Date arrival_time) {
		this.arrival_time = arrival_time;
	}

	@Column(name = "back_time")
	public Date getBack_time() {
		return this.back_time;
	}

	public void setBack_time(Date back_time) {
		this.back_time = back_time;
	}

	@Column(name = "work_result")
	public String getWork_result() {
		return this.work_result;
	}

	public void setWork_result(String work_result) {
		this.work_result = work_result;
	}

	@Column(name = "event_content")
	public String getEvent_content() {
		return this.event_content;
	}

	public void setEvent_content(String event_content) {
		this.event_content = event_content;
	}

	@Column(name = "cstm_request")
	public String getCstm_request() {
		return this.cstm_request;
	}

	public void setCstm_request(String cstm_request) {
		this.cstm_request = cstm_request;
	}

	@Column(name = "ac_remark")
	public String getAc_remark() {
		return this.ac_remark;
	}

	public void setAc_remark(String ac_remark) {
		this.ac_remark = ac_remark;
	}

	@Column(name = "collabora_dept")
	public String getCollabora_dept() {
		return this.collabora_dept;
	}

	public void setCollabora_dept(String collabora_dept) {
		this.collabora_dept = collabora_dept;
	}

	@Column(name = "ac_op_id")
	public Long getAc_op_id() {
		return this.ac_op_id;
	}

	public void setAc_op_id(Long ac_op_id) {
		this.ac_op_id = ac_op_id;
	}

	@Column(name = "ac_op_name")
	public String getAc_op_name() {
		return this.ac_op_name;
	}

	public void setAc_op_name(String ac_op_name) {
		this.ac_op_name = ac_op_name;
	}

	@Column(name = "forward_date")
	public Date getForward_date() {
		return this.forward_date;
	}

	public void setForward_date(Date forward_date) {
		this.forward_date = forward_date;
	}

	@Column(name = "handle_result")
	public String getHandle_result() {
		return this.handle_result;
	}

	public void setHandle_result(String handle_result) {
		this.handle_result = handle_result;
	}

	@Column(name = "ret_remark")
	public String getRet_remark() {
		return this.ret_remark;
	}

	public void setRet_remark(String ret_remark) {
		this.ret_remark = ret_remark;
	}

	@Column(name = "cs")
	public String getCs() {
		return this.cs;
	}

	public void setCs(String cs) {
		this.cs = cs;
	}

	@Column(name = "ret_date")
	public Date getRet_date() {
		return this.ret_date;
	}

	public void setRet_date(Date ret_date) {
		this.ret_date = ret_date;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "stamp")
	public Date getStamp() {
		return this.stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}