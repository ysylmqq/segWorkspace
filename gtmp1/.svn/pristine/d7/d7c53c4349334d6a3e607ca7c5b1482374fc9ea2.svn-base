package com.chinaGPS.gtmp.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * T_p_user_role表对应的用户角色类。
 * 
 * @author zfy
 * 
 */
@Component
@Scope("prototype")
public class UserRolePOJO implements Serializable {
	private static final long serialVersionUID = -6164933694933456547L;
	/** 用户ID */
	private Long userId;
	/** 角色ID */
	private Long roleId;
	/** 用户类别：0-玉柴用户 1-机主用户 */
	private Integer userType;

	public UserRolePOJO() {
	}

	public UserRolePOJO(Long userId, Long roleId, Integer userType) {
		super();
		this.userId = userId;
		this.roleId = roleId;
		this.userType = userType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
}