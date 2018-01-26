package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 服务站POJO
 * 
 * @author Ben
 * 
 */
@Component @Scope("prototype")
public class ServiceStationPOJO implements Serializable {
	private static final long serialVersionUID = 723778630529685499L;
	/** 主键ID */
	private Long id;
	/** 服务站名称 */
	private String stationName;
	/** 地址 */
	private String address;
	/** 联系人 */
	private String contact;
	/** 联系方式 */
	private String phoneNumber;
	/** 经度 */
	private Float longitude;
	/** 纬度 */
	private Float latitude;
	/** 有效性 */
	private String isValid;
	/** 创建人 */
	private Long createMan;
	/** 创建时间 */
	private Date createTime;
	
	/** 部门ID */
	private Long departId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public Long getCreateMan() {
		return createMan;
	}

	public void setCreateMan(Long createMan) {
		this.createMan = createMan;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}
}
