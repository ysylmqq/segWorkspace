package com.chinagps.driverbook.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * API基础对象
 * 
 * @author Ben
 *
 */
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 663101757873970975L;

	/** 系统来源（1：车圣互联 2：海马APP） */
	private Integer origin;
	/** APP版本号 */
	private String appVersion;
	/** 设备类型（0：Android 1：iOS） */
	private Integer deviceType;
	/** API版本 */
	private String apiVersion;

	@JsonIgnore
	public Integer getOrigin() {
		return origin;
	}

	@JsonProperty
	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	@JsonIgnore
	public String getAppVersion() {
		return appVersion;
	}

	@JsonProperty
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	@JsonIgnore
	public Integer getDeviceType() {
		return deviceType;
	}

	@JsonProperty
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	@JsonIgnore
	public String getApiVersion() {
		return apiVersion;
	}

	@JsonProperty
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

}
