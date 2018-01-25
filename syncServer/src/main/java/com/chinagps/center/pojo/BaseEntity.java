package com.chinagps.center.pojo;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;


public abstract class BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date stamp;//'操作时间',

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
}
