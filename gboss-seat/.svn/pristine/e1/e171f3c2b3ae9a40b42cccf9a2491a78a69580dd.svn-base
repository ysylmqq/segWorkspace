package cc.chinagps.seat.bean.table;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * Entity implementation class for Entity: AlarmStatus
 *
 */
@Entity
@Table(name="t_al_alarm")
public class AlarmTable implements Serializable {

	private static final long serialVersionUID = 4665520492612796807L;
	
	/**
	 * 处警状态:预警(初始值)
	 */
	public static final int HANDLE_STATUS_INIT = 0;
	
	/**
	 * 处警状态:分配中
	 */
	public static final int HANDLE_STATUS_DISPATCHING = 1;
	
	/**
	 * 处警状态:处警中
	 */
	public static final int HANDLE_STATUS_OPERATIONG = 2;
	
	/**
	 * 处警状态:挂警
	 */
	public static final int HANDLE_STATUS_SUSPEND = 3;

	/**
	 * 处警状态:结束
	 */
	public static final int HANDLE_STATUS_FINISH = 4;
	
	@NotNull
	@Id
	@Column(name = "alarm_sn")
	private String alarmSn;
	@Column(name = "unit_id")
	private BigInteger unitId;
	@Column(name = "alarm_time")
	@Temporal(TIMESTAMP)
	private Date alarmTime;
	@Column(name = "alarm_name")
	private String alarmName;
	@Column(name = "accept_time")
	@Temporal(TIMESTAMP)
	private Date acceptTime;
	@Column(name = "accepttime_span")
	private Long acceptTimeSpan;
	@Column(name = "handle_time")
	@Temporal(TIMESTAMP)
	private Date handleTime;
	@Column(name = "handletime_span")
	private Long handleTimeSpan;
	/**
	 * 报警内容
	 */
	@Transient
	private String content;
	
	@Column(name="log_phone")
	private String log_phone;
	
	@Column(name="log_op_id")
	private BigInteger log_op_id;
	
	@Column(name="log_flag")
	private Integer log_flag;
	
	
	@Column(name="log_res")
	private Integer log_res;
	
	/**
	 * 接警简报
	 */
	@Column(name = "handle_content")
	private String brief;
	@Column(name = "handle_status")
	private Integer handleStatus;
	@Column(name = "handle_seatid")
	private Long opId;
	@Column(name = "handle_seatname")
	private String opName;
	@Transient
	private boolean realAlarm;
	/**
	 * 警情id
	 */
	@Transient
	private long alarmStatusId;
	@Column(name = "is_del")
	private Integer deleted; 
	@Column(name = "handletime_span", insertable = false, updatable = false)
	private Long handletimeSpan;	
	
	@Column(name = "call_letter")
	private String callLetter;	
	@Column(name = "status_ids")
	private String statusIds;	
	
	public String getAlarmSn() {
		return alarmSn;
	}
	@JSON(serialize = false)
	public BigInteger getUnitId() {
		return unitId;
	}
	@JSON(name = "alarm_time", format = "yyyy-MM-dd HH:mm:ss")
	public Date getAlarmTime() {
		return alarmTime;
	}
	public String getAlarmName() {
		return alarmName;
	}
	@JSON(name = "accept_time", format = "yyyy-MM-dd HH:mm:ss")
	public Date getAcceptTime() {
		return acceptTime;
	}
	@JSON(name = "handle_time", format = "yyyy-MM-dd HH:mm:ss")
	public Date getHandleTime() {
		return handleTime;
	}
	@JSON(serialize = false)
	public Long getHandleTimeSpan() {
		return handleTimeSpan;
	}
	@JSON(serialize = false)
	public String getContent() {
		return content;
	}
	@JSON(name = "result")
	public String getBrief() {
		return brief;
	}
	@JSON(serialize = false)
	public Integer getHandleStatus() {
		return handleStatus;
	}
	public Long getOpId() {
		return opId;
	}
	@JSON(serialize = false)
	public Integer getDeleted() {
		return deleted;
	}
	public String getOpName() {
		return opName;
	}
	public boolean isRealAlarm() {
		return realAlarm;
	}
	public long getAlarmStatusId() {
		return alarmStatusId;
	}
	@JSON(serialize = false)
	public Long getHandletimeSpan() {
		return handletimeSpan;
	}
	public void setAlarmSn(String alarmSn) {
		this.alarmSn = alarmSn;
	}
	public void setUnitId(BigInteger unitId) {
		this.unitId = unitId;
	}
	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}
	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}
	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	public void setHandleTimeSpan(Long handleTimeSpan) {
		this.handleTimeSpan = handleTimeSpan;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public void setRealAlarm(boolean isRealAlarm) {
		this.realAlarm = isRealAlarm;
	}
	public void setAlarmStatusId(long alarmStatusId) {
		this.alarmStatusId = alarmStatusId;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public Long getAcceptTimeSpan() {
		return acceptTimeSpan;
	}
	public void setAcceptTimeSpan(Long acceptTimeSpan) {
		this.acceptTimeSpan = acceptTimeSpan;
	}
	public String getCallLetter() {
		return callLetter;
	}
	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}
	public String getStatusIds() {
		return statusIds;
	}
	public void setStatusIds(String statusIds) {
		this.statusIds = statusIds;
	}
	public void setHandletimeSpan(Long handletimeSpan) {
		this.handletimeSpan = handletimeSpan;
	}
	public String getLog_phone() {
		return log_phone;
	}
	public void setLog_phone(String log_phone) {
		this.log_phone = log_phone;
	}
	public BigInteger getLog_op_id() {
		return log_op_id;
	}
	public void setLog_op_id(BigInteger p_id) {
		this.log_op_id = p_id;
	}
	public Integer getLog_flag() {
		return log_flag;
	}
	public void setLog_flag(Integer log_flag) {
		this.log_flag = log_flag;
	}
	public Integer getLog_res() {
		return log_res;
	}
	public void setLog_res(Integer log_res) {
		this.log_res = log_res;
	}
	
}
