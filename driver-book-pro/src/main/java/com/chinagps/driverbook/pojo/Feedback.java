package com.chinagps.driverbook.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户意见反馈POJO
 * 
 * @author Ben
 *
 */
public class Feedback extends BaseEntity {
	private static final long serialVersionUID = 1656409081915589623L;

	/** 主键ID */
	private Long id;
	/** 客户ID */
	private Long customerId;
	/** 使用软件版本号 */
	private String versionName;
	/** 设备类型（1：Android 2：iOS） */
	private Integer device;
	/** 反馈来源（1：车圣互联 2：海马） */
	private Integer origin;
	/** 反馈内容 */
	private String content;
	/** 回复内容 */
	private String response;
	/** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date stamp;
	/** 回复时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date responseStamp;
	/** 回复人ID */
	private Long opId;
	/** 回复人姓名 */
	private String opName;
	/** 0：未处理，1.已读，2.已推送回复 */
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Integer getDevice() {
		return device;
	}

	public void setDevice(Integer device) {
		this.device = device;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public Date getResponseStamp() {
		return responseStamp;
	}

	public void setResponseStamp(Date responseStamp) {
		this.responseStamp = responseStamp;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
