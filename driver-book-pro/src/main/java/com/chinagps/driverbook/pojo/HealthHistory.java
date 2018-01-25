package com.chinagps.driverbook.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 历史体检POJO
 * 
 * @author Ben
 *
 */
public class HealthHistory extends BaseEntity {
	private static final long serialVersionUID = 5357419547543263817L;

	/** 体检ID */
	private Long id;
	/** 车台呼号 */
	private String callLetter;
	/** 得分 */
	private Integer score;
	/** 体检日期 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date stamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCallLetter() {
		return callLetter;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}
