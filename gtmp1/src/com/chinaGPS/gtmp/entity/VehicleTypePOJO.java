package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 文 件 名 :VehicleTypePOJO.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：zfy
 * 创 建 日 期：2013-4-17
 * 描 述： t_dic_vehicle_type表对应的机械类型类
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0
 */
@Component
@Scope("prototype")
public class VehicleTypePOJO implements Serializable{
	
	private String typeId;
	private String typeName;
	private Integer listNo;
	private Integer isValid;//有效性：0 有效 1 无效
	private Date stamp;
	
	
	private String modelId;  //机械型号
	private String modelName;
	private String vehicleCode;//机械代号
	private String vehicleArg;//机械配置
	private Long indexId;//新增主键
	
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
	    return typeName;
	}
	public void setTypeName(String typeName) {
	    this.typeName = typeName;
	}
	public Integer getListNo() {
	    return listNo;
	}
	public void setListNo(Integer listNo) {
	    this.listNo = listNo;
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
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getVehicleCode() {
		return vehicleCode;
	}
	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}
	public String getVehicleArg() {
		return vehicleArg;
	}
	public Long getIndexId() {
		return indexId;
	}
	public void setIndexId(Long indexId) {
		this.indexId = indexId;
	}
	public void setVehicleArg(String vehicleArg) {
		this.vehicleArg = vehicleArg;
	}
		
}
