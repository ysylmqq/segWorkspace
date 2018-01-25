package com.chinagps.driverbook.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 历史通知POJO
 * 
 * @author Ben
 *
 */
public class NoticeHis extends BaseEntity {
	private static final long serialVersionUID = 1774264484840755019L;

	/** 主键ID */
	private Long hisId;
	/** 客户ID */
	private Long customerId;
	/** 终端ID */
	private Long unitId;
	/** 车台呼号 */
	private String callLetter;
	/** 车牌号码 */
	private String plateNo;
	/** 推送类型（1：反馈 2：遥控指令 3：警情 4：资讯 5：故障） */
	private Integer nType;
	/** 标题 */
	private String title;
	/** 推送内容 */
	private String content;
	/** 执行结果（0：未成功 1：成功） */
	private Integer res;
	/** 结果说明 */
	private String reason;
	/** 时间戳 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date stamp;
	
	/** 开始时间 */
	private Date beginTime;
	/** 结束时间 */
	private Date endTime;

	public Long getHisId() {
		return hisId;
	}

	public void setHisId(Long hisId) {
		this.hisId = hisId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getCallLetter() {
		return callLetter;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public Integer getnType() {
		return nType;
	}

	public void setnType(Integer nType) {
		this.nType = nType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getRes() {
		return res;
	}

	public void setRes(Integer res) {
		this.res = res;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
