package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 机主用户POJO
 * 
 * @author Ben
 * 
 */
@Component
@Scope("prototype")
public class OwnerPOJO implements Serializable {
	private static final long serialVersionUID = 1438410553495111082L;
	/** 主键ID */
	private String ownerId;
	/** 机主姓名 */
	private String ownerName;
	/** 机主电话 */
	private String ownerPhoneNumber;
	/** 性别 */
	private String sex;
	/** 登录名 */
	private String loginName;
	/** 密码 */
	private String password;
	/** 有效性 */
	private String isValid;
	/** 创建时间 */
	private Date stamp;

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerPhoneNumber() {
		return ownerPhoneNumber;
	}

	public void setOwnerPhoneNumber(String ownerPhoneNumber) {
		this.ownerPhoneNumber = ownerPhoneNumber;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getStamp() {
		return stamp;
	}
	
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}
