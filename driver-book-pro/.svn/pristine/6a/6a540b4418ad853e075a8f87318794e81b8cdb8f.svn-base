package com.chinagps.driverbook.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AppTe implements Serializable {
	private static final long serialVersionUID = 3116009988133489878L;

	/** 移动设备国际身份码, 电信为MEID, GSM为IMEI */
	private String imei;
	/** 分公司ID */
	private Long subcoNo;
	/** 操作员ID */
	private Long opId;
	/** 操作时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date stamp;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Long getSubcoNo() {
		return subcoNo;
	}

	public void setSubcoNo(Long subcoNo) {
		this.subcoNo = subcoNo;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}
