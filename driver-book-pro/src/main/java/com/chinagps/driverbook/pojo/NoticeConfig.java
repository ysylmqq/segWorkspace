package com.chinagps.driverbook.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 消息设置POJO
 * 
 * @author Ben
 *
 */
public class NoticeConfig extends BaseEntity {
	private static final long serialVersionUID = 7995835468140830207L;

	/** 客户ID */
	private Long customerId;
	/** 到期保养提醒 */
	private Integer maintain;
	/** 服务到期提醒 */
	private Integer charge;
	/** 车辆故障提醒 */
	private Integer fault;
	/** 非法开车提醒 */
	private Integer illegal;
	/** 脱网提醒 */
	private Integer offline;
	/** 熄火未关灯提醒 */
	private Integer light;
	/** 熄火未关门提醒 */
	private Integer door;
	/** 熄火未锁门提醒 */
	private Integer unlock;
	/** 是否删除（0：未删除 1：已删除） */
	private Integer isDel;
	/** 最后修改时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date stamp;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Integer getMaintain() {
		return maintain;
	}

	public void setMaintain(Integer maintain) {
		this.maintain = maintain;
	}

	public Integer getCharge() {
		return charge;
	}

	public void setCharge(Integer charge) {
		this.charge = charge;
	}

	public Integer getFault() {
		return fault;
	}

	public void setFault(Integer fault) {
		this.fault = fault;
	}

	public Integer getIllegal() {
		return illegal;
	}

	public void setIllegal(Integer illegal) {
		this.illegal = illegal;
	}

	public Integer getOffline() {
		return offline;
	}

	public void setOffline(Integer offline) {
		this.offline = offline;
	}

	public Integer getLight() {
		return light;
	}

	public void setLight(Integer light) {
		this.light = light;
	}

	public Integer getDoor() {
		return door;
	}

	public void setDoor(Integer door) {
		this.door = door;
	}

	public Integer getUnlock() {
		return unlock;
	}

	public void setUnlock(Integer unlock) {
		this.unlock = unlock;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}
