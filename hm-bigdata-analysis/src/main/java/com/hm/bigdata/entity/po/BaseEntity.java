package com.hm.bigdata.entity.po;

import org.apache.commons.lang.builder.ToStringBuilder;


public abstract class BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
