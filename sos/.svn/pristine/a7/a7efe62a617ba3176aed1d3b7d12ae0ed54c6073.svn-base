package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gboss.util.CustomDateSerializer;

/**
 * @Package:com.gboss.pojo
 * @ClassName:DispatchTask
 * @Description:派工实体类
 * @author:bzhang
 * @date:2014-3-27 下午4:08:22
 * 已删除
 */
@Entity
@Table(name = "t_ba_dispatch")
public class DispatchTask extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long task_id;// 任务单I
	private Long reservation_id;// 预约id
	private Integer status;// 派工单状态
	private Date dispatch_time;// 派工时间
	private Date start_time;// 开始时间
	private String duration;// 预计时长
	private Long org_id;// 营业处id
	private Long user_id;// 操作员id
	private Date stamp;// 操作时间
	private String electricianIds;
	private String electricianNames;
	private String electrician_phone;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "task_id")
	public Long getTask_id() {
		return task_id;
	}

	public void setTask_id(Long task_id) {
		this.task_id = task_id;
	}

	@Column(name = "reservation_id")
	public Long getReservation_id() {
		return reservation_id;
	}

	public void setReservation_id(Long reservation_id) {
		this.reservation_id = reservation_id;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "dispatch_time", nullable = true, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getDispatch_time() {
		return dispatch_time;
	}

	public void setDispatch_time(Date dispatch_time) {
		this.dispatch_time = dispatch_time;
	}

	
	@Column(name = "start_time", nullable = true, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	@Column(name = "duration")
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Column(name = "org_id")
	public Long getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}

	@Column(name = "user_id")
	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	@Column(name = "stamp", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	@Transient
	public String getElectricianIds() {
		return electricianIds;
	}

	public void setElectricianIds(String electricianIds) {
		this.electricianIds = electricianIds;
	}

	@Transient
	public String getElectricianNames() {
		return electricianNames;
	}

	public void setElectricianNames(String electricianNames) {
		this.electricianNames = electricianNames;
	}

	@Transient
	public String getElectrician_phone() {
		return electrician_phone;
	}

	public void setElectrician_phone(String electrician_phone) {
		this.electrician_phone = electrician_phone;
	}

}
