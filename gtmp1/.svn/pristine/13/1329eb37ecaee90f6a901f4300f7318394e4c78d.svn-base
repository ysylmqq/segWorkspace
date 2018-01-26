package com.chinaGPS.gtmp.entity;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * 文 件 名 :UserAlarmTypesPOJO.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建人：zfy
 * 创 建 日 期：2013-7-19
 * 描 述：T_USER_ALARM_TYPES表对应的用户警情过滤、综合查询关注项设置
 * 修 改 人：
 * 修 改日 期：
 * 修 改 原 因:
 * 版 本 号：1.0
 */
@Component
@Scope("prototype")
public class UserAlarmTypesPOJO implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 用户ID */
	private Long userId;
	/** 警情类型集合 */
	private String alarmTypes;
	/** 综合查询关注项集合 */
	private String showColumns;
	/** 操作时间 */
	private String stamp;
	
	private int startTime;
	private int endTime;

	public String getAlarmTypes() {
		return alarmTypes;
	}

	public void setAlarmTypes(String alarmTypes) {
		this.alarmTypes = alarmTypes;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getShowColumns() {
		return showColumns;
	}

	public void setShowColumns(String showColumns) {
		this.showColumns = showColumns;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public String getStamp() {
		return stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

}
