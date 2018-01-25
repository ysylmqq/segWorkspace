package com.chinagps.driverbook.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 保养POJO
 * @author Ben
 *
 */
public class Maintain extends BaseEntity {
	private static final long serialVersionUID = -7674460141792436038L;

	/** 主键ID */
	private String id;
	/** 车辆ID */
	private String vehicleId;
	/** 当前总里程 */
	private Integer totalMileage;
	/** 下次保养时间 */
	private Integer maintainTime;
	/** 下次保养里程 */
	private Integer maintainMileage;
	/** 保养通知开关（0：关闭 1：打开） */
	private Integer notice;
	/** 最后修改时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date stamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Integer getTotalMileage() {
		return totalMileage;
	}

	public void setTotalMileage(Integer totalMileage) {
		this.totalMileage = totalMileage;
	}

	public Integer getMaintainTime() {
		return maintainTime;
	}

	public void setMaintainTime(Integer maintainTime) {
		this.maintainTime = maintainTime;
	}

	public Integer getMaintainMileage() {
		return maintainMileage;
	}

	public void setMaintainMileage(Integer maintainMileage) {
		this.maintainMileage = maintainMileage;
	}

	public Integer getNotice() {
		return notice;
	}

	public void setNotice(Integer notice) {
		this.notice = notice;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
}
