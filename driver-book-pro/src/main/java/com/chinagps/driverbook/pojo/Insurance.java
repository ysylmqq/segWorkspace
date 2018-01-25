package com.chinagps.driverbook.pojo;


/**
 * 保险公司POJO
 * 
 * @author Ben
 *
 */
public class Insurance extends BaseEntity {
	private static final long serialVersionUID = -7393170789583943652L;

	/** 保险公司ID */
	private Integer icId;
	/** 名称简称 */
	private String sName;

	public Integer getIcId() {
		return icId;
	}

	public void setIcId(Integer icId) {
		this.icId = icId;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

}
