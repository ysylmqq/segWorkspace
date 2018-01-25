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
 * @ClassName:Follow
 * @Description:已删除
 * @author:bzhang
 * @date:2014-4-22 下午1:48:38
 */
@Entity
@Table(name = "t_ba_follow")
public class Follow extends BaseEntity {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Long id;// 回单id
	private Long dispatch_id;// 派工单id
	private Long worker_id;// 电工id
	private String worker;// 电工姓名
	private Date outset_time;// 出发时间
	private Date arrival_time;// 到达时间
	private String duration;// 预计时长
	private Date departure_time;// 离开时间
	private Integer is_end;// 是否结束
	private Long num;// 数量
	private Long user_id;// 操作员id
	private Date stamp;// 操作时间
	private String remark;// 备注
	private Long task_id;// 工单id

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "dispatch_id")
	public Long getDispatch_id() {
		return dispatch_id;
	}

	public void setDispatch_id(Long dispatch_id) {
		this.dispatch_id = dispatch_id;
	}

	@Column(name = "worker_id")
	public Long getWorker_id() {
		return worker_id;
	}

	public void setWorker_id(Long worker_id) {
		this.worker_id = worker_id;
	}

	@Column(name = "worker")
	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	@Column(name = "outset_time",nullable=true,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getOutset_time() {
		return outset_time;
	}

	public void setOutset_time(Date outset_time) {
		this.outset_time = outset_time;
	}

	@Column(name = "arrival_time",nullable=true,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(Date arrival_time) {
		this.arrival_time = arrival_time;
	}

	@Column(name = "duration")
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Column(name = "departure_time",nullable=true,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getDeparture_time() {
		return departure_time;
	}

	public void setDeparture_time(Date departure_time) {
		this.departure_time = departure_time;
	}

	@Column(name = "is_end")
	public Integer getIs_end() {
		return is_end;
	}

	public void setIs_end(Integer is_end) {
		this.is_end = is_end;
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

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "num")
	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	@Transient
	public Long getTask_id() {
		return task_id;
	}

	public void setTask_id(Long task_id) {
		this.task_id = task_id;
	}
}
