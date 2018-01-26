package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 * @Package:com.chinaGPS.gtmp.entity
 * @ClassName:DicUnitTypePOJO
 * @Description:终端类型字典POJO
 * @author:zfy
 * @date:2013-4-9 上午10:08:18
 */
@Component
@Scope("prototype")
public class DicUnitTypePOJO implements Serializable{
	private String unitTypeSn;
	
	private String unitType;
	
	private Integer isValid;//有效性：0 有效 1 无效
	private String supperierSn;//终端供应商编号
	private Date stamp;
	private String  updateId;//用于判断是否新增还是修改
	private String supperierName;
	private Long userId;//创建人
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public String getUnitTypeSn() {
	    return unitTypeSn;
	}
	public void setUnitTypeSn(String unitTypeSn) {
	    this.unitTypeSn = unitTypeSn;
	}
	public String getUnitType() {
	    return unitType;
	}
	public void setUnitType(String unitType) {
	    this.unitType = unitType;
	}
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	public String getSupperierSn() {
		return supperierSn;
	}
	public void setSupperierSn(String supperierSn) {
		this.supperierSn = supperierSn;
	}
	public String getSupperierName() {
		return supperierName;
	}
	public void setSupperierName(String supperierName) {
		this.supperierName = supperierName;
	}
}
