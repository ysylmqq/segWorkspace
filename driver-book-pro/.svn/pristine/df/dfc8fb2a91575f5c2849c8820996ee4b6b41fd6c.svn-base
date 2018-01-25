package com.chinagps.driverbook.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 资讯浏览日志POJO
 * 
 * @author Ben
 *
 */
public class NewsRecord extends BaseEntity {
	private static final long serialVersionUID = -2440552060683768070L;

	/** 主键ID */
	private Long id;
	/** 资讯ID */
	private Long newsId;
	/** 客户ID */
	private Long customerId;
	/** 设备类型（0：Android 1：iOS） */
	private Integer device;
	/** 浏览时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date stamp;
	
	public NewsRecord() {}
	
	public NewsRecord(Long newsId, Long customerId, Integer device) {
		this.newsId = newsId;
		this.customerId = customerId;
		this.device = device;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNewsId() {
		return newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Integer getDevice() {
		return device;
	}

	public void setDevice(Integer device) {
		this.device = device;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}
