package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * t_malfunction表对应的实体,故障信息表。
 * 
 * @author zfy
 */
@Component
@Scope("prototype")
public class MalfunctionPOJO implements Serializable {
	private static final long serialVersionUID = -8361762857553617701L;
	// 故障信息
	private Long malfunctionId;
	private String unitId;// 终端ID
	private String malfunctionCode;// 故障码
	private Date stamp;

	private String malfunction;// 故障名称

	/** 报表类型 */
	private int reportType;
	
	// 机械信息
	private String vehicleDef;// 整机编号，此标识为玉柴用户可使用的机械标识。同一机构下需要防重处理。
	private String typeName;// 机械类型
	private String modelName;// 机械型号
	private String vehicleCode;
	private String vehicleArg;

	/** GPS信息 */
	private Float lon;
	private Float lat;
	private Integer speed;
	private Integer direction;
	private Date gpsTime;
	private String vehicleState;
	private String province; // 省
	private String city; // 市
	private String county; // 县
	private String referencePosition;// 参考位置
	private String locationState;// 定位状态 1 定位 0 不定位
	private Integer status;// 1：测试组，2：已测组，3：销售组，8:法务组,9：停用组

	/** 客户信息 */
	private String ownerName;
	private String ownerPhoneNumber;
	/** 区域/经销商ID */
	private String[] dealerIds;
	/** 区域/经销商名称 */
	private String dealerName;
	private Date startTime;
	private Date endTime;

	public Long getMalfunctionId() {
		return malfunctionId;
	}

	public void setMalfunctionId(Long malfunctionId) {
		this.malfunctionId = malfunctionId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getMalfunctionCode() {
		return malfunctionCode;
	}

	public void setMalfunctionCode(String malfunctionCode) {
		this.malfunctionCode = malfunctionCode;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public String getVehicleDef() {
		return vehicleDef;
	}

	public void setVehicleDef(String vehicleDef) {
		this.vehicleDef = vehicleDef;
	}

	public Float getLon() {
		return lon;
	}

	public void setLon(Float lon) {
		this.lon = lon;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(Date gpsTime) {
		this.gpsTime = gpsTime;
	}

	public String getVehicleState() {
		return vehicleState;
	}

	public void setVehicleState(String vehicleState) {
		this.vehicleState = vehicleState;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getReferencePosition() {
		return referencePosition;
	}

	public void setReferencePosition(String referencePosition) {
		this.referencePosition = referencePosition;
	}

	public String getLocationState() {
		return locationState;
	}

	public void setLocationState(String locationState) {
		this.locationState = locationState;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getMalfunction() {
		return malfunction;
	}

	public void setMalfunction(String malfunction) {
		this.malfunction = malfunction;
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
	
	public int getReportType() {
		return reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	public String[] getDealerIds() {
		return dealerIds;
	}

	public void setDealerIds(String[] dealerIds) {
		this.dealerIds = dealerIds;
	}
	
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

}
