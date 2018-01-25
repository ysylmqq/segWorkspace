package com.chinagps.driverbook.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 驾驶员POJO
 * 
 * @author Ben
 *
 */
public class Driver extends BaseEntity {
	private static final long serialVersionUID = -6271102550354261636L;

	/** 驾驶员ID */
	private Long driverId;
	/** 分公司, 对应我们的分公司, 内部机构 */
	private Long subcoNo;
	/** 客户ID */
	private Long customerId;
	/** 驾驶员姓名 */
	private String driverName;
	/** 证件类型（1：居民身份证 2：士官证 3：学生证 4：驾驶证 5：护照 6：港澳通行证 99：其它） */
	private Integer idtype;
	/** 准驾车型 */
	private String drClass;
	/** 驾照有效起始日期 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date drBdate;
	/** 驾照到期日期 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date drEdate;

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public Long getSubcoNo() {
		return subcoNo;
	}

	public void setSubcoNo(Long subcoNo) {
		this.subcoNo = subcoNo;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public Integer getIdtype() {
		return idtype;
	}

	public void setIdtype(Integer idtype) {
		this.idtype = idtype;
	}

	public String getDrClass() {
		return drClass;
	}

	public void setDrClass(String drClass) {
		this.drClass = drClass;
	}

	public Date getDrBdate() {
		return drBdate;
	}

	public void setDrBdate(Date drBdate) {
		this.drBdate = drBdate;
	}

	public Date getDrEdate() {
		return drEdate;
	}

	public void setDrEdate(Date drEdate) {
		this.drEdate = drEdate;
	}

}
