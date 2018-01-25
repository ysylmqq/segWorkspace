package com.chinagps.driverbook.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 统计POJO
 * @author Ben
 *
 */
public class Statistics extends BaseEntity {
	private static final long serialVersionUID = -5395278224023520492L;

	/** 主键ID */
	private String id;
	/** 车台呼号 */
	private String callLetter;
	/** 统计结果 */
	private String content;
	/** 统计月份 */
	@JsonFormat(pattern="yyyy-MM", timezone = "GMT+8")
	private Date month;
	/** 执行结果（0：成功 1：失败） */
	private Integer result;
	/** 时间戳 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date stamp;
	/** 百公里平均油耗 */
	private Double oilcast;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCallLetter() {
		return callLetter;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public Double getOilcast() {
		return oilcast;
	}

	public void setOilcast(Double oilcast) {
		this.oilcast = oilcast;
	}
}
