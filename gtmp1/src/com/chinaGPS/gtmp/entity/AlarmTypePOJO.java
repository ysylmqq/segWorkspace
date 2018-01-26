package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class AlarmTypePOJO implements Serializable{
	
	
	/**
	 * 警情类型表
	 */
	private static final long serialVersionUID = 1L;
	private String alarmTypeId;
	private String alarmTypeName;
	private Integer isValid;//有效性：0 有效 1 无效
	private Date stamp;
	public String getAlarmTypeId() {
	    return alarmTypeId;
	}
	public void setAlarmTypeId(String alarmTypeId) {
	    this.alarmTypeId = alarmTypeId;
	}
	public String getAlarmTypeName() {
	    return alarmTypeName;
	}
	public void setAlarmTypeName(String alarmTypeName) {
	    this.alarmTypeName = alarmTypeName;
	}
	public Integer getIsValid() {
	    return isValid;
	}
	public void setIsValid(Integer isValid) {
	    this.isValid = isValid;
	}
	public Date getStamp() {
	    return stamp;
	}
	public void setStamp(Date stamp) {
	    this.stamp = stamp;
	}
	
}
