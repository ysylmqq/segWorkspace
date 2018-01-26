package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 文 件 名 :VehicleSalePOJO.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：zfy
 * 创 建 日 期：2013-06-18
 * 描 述：机械销售信息类
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0	
 */
@Component
@Scope("prototype")
public class VehicleSalePOJO implements Serializable{
	private Long vehicleId; //主键
	
	private String vehicleDef;//整机编号，此标识为玉柴用户可使用的机械标识。同一机构下需要防重处理。
	
	private Integer status;//1：测试组，2：已测组，3：销售组，8:法务组，9：停用组

	private Date saleDate;//最后修改时间
	 /**
     * 销售信息
     */
    private String dealerId;//经销商id
    private String dealerName;//经销商
    private String ownerId;//机主id
    private String ownerName;//机主名称
    private String ownerPhone;//机主电话
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerPhone() {
		return ownerPhone;
	}
	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}
	public String getDealerId() {
		return dealerId;
	}
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public Long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getVehicleDef() {
		return vehicleDef;
	}
	public void setVehicleDef(String vehicleDef) {
		this.vehicleDef = vehicleDef;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	 @JSON(format="yyyy-MM-dd")
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
}
