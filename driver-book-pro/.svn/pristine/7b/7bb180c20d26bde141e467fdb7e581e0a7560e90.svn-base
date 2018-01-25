package com.chinagps.driverbook.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * APP版本POJO
 * 
 * @author Ben
 *
 */
public class AppVersion extends BaseEntity {
	private static final long serialVersionUID = -5718550184907299781L;

	/** 主键ID **/
	private String id;
	/** 平台系统（1：大平台 2：海马） */
	private Integer origin;
	/** 版本名 **/
	private String versionName;
	/** 手机客户端程序文件下载地址 **/
	private String url;
	/** 更新说明 **/
	private String caption;
	/** 更新时间 **/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date stamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}
