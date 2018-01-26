package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 * @Package:com.chinaGPS.gtmp.entity
 * @ClassName:DicSupperierPOJO
 * @Description:终端供应商字典POJO
 * @author:zfy
 * @date:2013-4-9 上午10:08:18
 */
@Component
@Scope("prototype")
public class DicSupperierPOJO implements Serializable{
	private String supperierSn;
	
	private String supperierName;
	
	private Integer isValid;//有效性：0 有效 1 无效
	private Date stamp;
	
	private String  updateId;//用于判断是否新增还是修改
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
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	
}
