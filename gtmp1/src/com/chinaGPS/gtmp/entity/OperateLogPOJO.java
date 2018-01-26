package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Package:com.chinaGPS.gtmp.entity
 * @ClassName:OperateLogPOJO
 * @Description:操作日志
 * @author:zfy
 * @date:2013-4-25 上午09:41:19
 */
@Component
@Scope("prototype")
public class OperateLogPOJO implements Serializable {
	private static final long serialVersionUID = -4681042648714949070L;
	
	/** 日志ID */
	private String logId;
	/** 用户ID */
	private Long userId;
	/** 操作类型 */
	private String logType;
	/** 日志内容 */
	private String logContent;
	private String ip;
	/** 操作时间 */
	private Date stamp;
	/** 用户名 */
	private String userName;
	/** 操作结束时间 */
	private Date stamp2;

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getStamp2() {
		return stamp2;
	}

	public void setStamp2(Date stamp2) {
		this.stamp2 = stamp2;
	}
}
