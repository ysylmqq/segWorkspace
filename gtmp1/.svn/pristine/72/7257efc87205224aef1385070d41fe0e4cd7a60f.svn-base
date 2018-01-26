package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 文 件 名 :VehiclePOJO.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：肖克
 * 创 建 日 期：2012-12-11
 * 描 述： t_vehicle表对应的机械类
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0	
 */
@Component
@Scope("prototype")
public class VehiclePOJO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long vehicleId; //主键
	
	private String unitId;//终端号编号 进行绑定 
	
	private String vehicleDef;//整机编号，此标识为玉柴用户可使用的机械标识。同一机构下需要防重处理。
	
	private Long typeId;//对应字典：T_DIC_VEHICLE_TYPE
	
	private Long modelId;//对应字典：T_DIC_VEHICLE_MODEL
	
	private Long modelIdNew;//对应字典：T_DIC_VEHICLE_MODEL
	
	private String fixMan;//终端安装人
	
	private Date fixDate;//终端安装时间
	
	private Date fixDateStart;//终端安装开始时间 (机械测试开始时间)
	
	private Date fixDateEnd;//终端安装结束时间 (机械测试结束时间)
	
	private Integer status;//1：测试组，2：已测组，3：销售组，8：法务组，9：停用组
	
	private Integer testUserId;//测试人ID
	
	private Date stamp;//最后修改时间
	
	private Date statusTime;//状态修改时间
	
	private String userName;//停用人
	
	private String statusName;//状态中文 1：测试组，2：已测组，3：销售组，8：法务组，9：停用组
	
	private String typeName;//机械类型
	
	private String modelName;//机械型号
	
	private Integer isValid;//有效性：0 有效 1 无效
	
	private String unitSn;//终端序列号：终端上传，供应商确定，不能有重复
	
	private String materialNo;//物料编号
	
	private String simNo;//SIM卡号
	
	private String steelNo;//钢号
	
	private Integer testFlag;//测试记录条数

	private Date saleDate;//最后修改时间
	
	private String muid;//机械删附近的标志位
	
	private Integer removeFlag;//解绑定标志位   0  未解绑定  1 解绑定
	
	private String vehicleCode;//机械代号
	
	private String vehicleArg;//机械配置
	
	private String dealerName;
	private String areaName;
	private String vehicleStatus;
	private float totalworkinghours; 
	private String dealerId;	//经销商
	private String[] dealerIds;// 经销商组

	/** 融资租赁权限（0：无，1：有） */
	private Integer leaseFlag;
	
	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getVehicleDef() {
		return vehicleDef;
	}

	public void setVehicleDef(String vehicleDef) {
		this.vehicleDef = vehicleDef;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getFixMan() {
		return fixMan;
	}

	public void setFixMan(String fixMan) {
		this.fixMan = fixMan;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		if (status != null) {
			if(status==1){
				statusName = "测试组";
			}else if(status==2){
				statusName = "已测组";
			}else if(status==3){
				statusName = "销售组";
			}else if(status==8){
				statusName = "法务组";
			}else if(status==9){
				statusName = "停用组";
			}
		} 
		return statusName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getFixDate() {
		return fixDate;
	}

	public void setFixDate(Date fixDate) {
		this.fixDate = fixDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getStamp() {
	    return stamp;
	}
	public void setStamp(Date stamp) {
	    this.stamp = stamp;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	@JSON(format="yyyy-MM-dd") 
	public Date getFixDateStart() {
		return fixDateStart;
	}

	public void setFixDateStart(Date fixDateStart) {
		this.fixDateStart = fixDateStart;
	}
	
	@JSON(format="yyyy-MM-dd") 
	public Date getFixDateEnd() {
		return fixDateEnd;
	}

	public void setFixDateEnd(Date fixDateEnd) {
		this.fixDateEnd = fixDateEnd;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	
	public static long getSerialVersionUID() {
	    return serialVersionUID;
	}

	public String getUnitSn() {
		return unitSn;
	}

	public void setUnitSn(String unitSn) {
		this.unitSn = unitSn;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public Long getModelIdNew() {
		return modelIdNew;
	}

	public void setModelIdNew(Long modelIdNew) {
		this.modelIdNew = modelIdNew;
	}

	public String getSteelNo() {
		return steelNo;
	}

	public void setSteelNo(String steelNo) {
		this.steelNo = steelNo;
	}

	public Integer getTestFlag() {
		return testFlag;
	}

	public void setTestFlag(Integer testFlag) {
		this.testFlag = testFlag;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getSaleDate() {
	    return saleDate;
	}

	public void setSaleDate(Date saleDate) {
	    this.saleDate = saleDate;
	}

	public Integer getTestUserId() {
		return testUserId;
	}

	public void setTestUserId(Integer testUserId) {
		this.testUserId = testUserId;
	}

	public String getMuid() {
		return muid;
	}

	public void setMuid(String muid) {
		this.muid = muid;
	}

	public Integer getRemoveFlag() {
		return removeFlag;
	}

	public void setRemoveFlag(Integer removeFlag) {
		this.removeFlag = removeFlag;
	}

	public Integer getLeaseFlag() {
		return leaseFlag;
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

	public void setVehicleArg(String vehicleArg) {
		this.vehicleArg = vehicleArg;
	}

	public void setLeaseFlag(Integer leaseFlag) {
		this.leaseFlag = leaseFlag;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public float getTotalworkinghours() {
		return totalworkinghours;
	}

	public void setTotalworkinghours(float totalworkinghours) {
		this.totalworkinghours = totalworkinghours;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String[] getDealerIds() {
		return dealerIds;
	}

	public void setDealerIds(String[] dealerIds) {
		this.dealerIds = dealerIds;
	}


}
