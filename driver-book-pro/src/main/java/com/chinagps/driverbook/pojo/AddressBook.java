package com.chinagps.driverbook.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 通讯录POJO
 * 
 * @author Ben
 *
 */
public class AddressBook extends BaseEntity {
	private static final long serialVersionUID = -6006529652695081158L;

	/** 主键ID */
	private String id;
	/** 客户ID */
	private String customerId;
	/** 版本号 */
	private String uploadVersion;
	/** 设备类型（0：Android 1：iOS */
	private Integer deviceType;
	/** 最后修改时间 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastStamp;
	/** 车台呼号 */
	private String callLetter;
	/** 联系人总数 */
	private Integer totalCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getUploadVersion() {
		return uploadVersion;
	}

	public void setUploadVersion(String uploadVersion) {
		this.uploadVersion = uploadVersion;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public Date getLastStamp() {
		return lastStamp;
	}

	public void setLastStamp(Date lastStamp) {
		this.lastStamp = lastStamp;
	}

	public String getCallLetter() {
		return callLetter;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
