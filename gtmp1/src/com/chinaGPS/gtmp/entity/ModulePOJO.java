package com.chinaGPS.gtmp.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 文 件 名 :ModulePOJO.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：zfy
 * 创 建 日 期：2013-4-27
 * 描 述： t_p_module表对应的模块类
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0
 */
@Component
@Scope("prototype")
public class ModulePOJO implements Serializable{
	private Long moduleId;
	private String moduleName;
	private Long parentId;
	private String url;
	private String control1;
	private String control2;
	private String control3;
	private Integer listNo;
	private Integer moduleType;
	private Integer isValid;
	
	private String stamp;
	
	private Long roleId;
	
	private String moduleIds;

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getControl1() {
		return control1;
	}

	public void setControl1(String control1) {
		this.control1 = control1;
	}

	public String getControl2() {
		return control2;
	}

	public void setControl2(String control2) {
		this.control2 = control2;
	}

	public String getControl3() {
		return control3;
	}

	public void setControl3(String control3) {
		this.control3 = control3;
	}


	public String getStamp() {
		return stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getModuleIds() {
		return moduleIds;
	}

	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}

	public String getUrl() {
	    return url;
	}

	public void setUrl(String url) {
	    this.url = url;
	}

	public Integer getListNo() {
	    return listNo;
	}

	public void setListNo(Integer listNo) {
	    this.listNo = listNo;
	}

	public Integer getModuleType() {
	    return moduleType;
	}

	public void setModuleType(Integer moduleType) {
	    this.moduleType = moduleType;
	}

	public Integer getIsValid() {
	    return isValid;
	}

	public void setIsValid(Integer isValid) {
	    this.isValid = isValid;
	}
	
}
