package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class AlarmPOJO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String alarmId;
	private String unitId;
	private String alarmTypeId;
	private String alarmStatus;
	private Integer alarmSource;
	private Float lon;
	private Float lat;
	private Integer speed;
	private Integer direction;
	private String gpsTime;
	private String referencePosition;
	private String locationState;// 1：定位，0：正在定位
	private String rawData;
	private String stamp;
	private Integer isRead;// 是否已读，0表示已读；1表示未读

	private Date startTime;
	private Date endTime;
	private String dealerName;
	private String dealerId;	//经销商
	private String[] dealerIds;// 经销商组

	private String vehicleDef;
	private String alarmTypeName;
	private String[] alarmTypeIds;
	//警情大类：1=GPS警情;2=挖机警情 by hhf 2015-11-30
	private String alarmTypeGenre;
	/** 报表类型 */
	private int reportType;
	/** 机型ID */
	private String vehicleModel;
	/** 机型名称 */
	private String vehicleModelName;
	/** 机器代号 */
	private String vehicleCode;
	/** 配置号 */
	private String vehicleArg;
	/**机械状态组*/
	private String vehicleStatus;
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

	public int getReportType() {
		return reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}
	public String getDealerName(){
		return dealerName;
	}
	public void setDealerName(String dealerName){
		this.dealerName = dealerName;
		
	}
	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getVehicleModelName() {
		return vehicleModelName;
	}

	public void setVehicleModelName(String vehicleModelName) {
		this.vehicleModelName = vehicleModelName;
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

	public String getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getAlarmTypeId() {
		return alarmTypeId;
	}

	public void setAlarmTypeId(String alarmTypeId) {
		this.alarmTypeId = alarmTypeId;
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public String getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(String gpsTime) {
		this.gpsTime = gpsTime;
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

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public String getStamp() {
		return stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

	public String getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(String alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public Integer getAlarmSource() {
		return alarmSource;
	}

	public void setAlarmSource(Integer alarmSource) {
		this.alarmSource = alarmSource;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getVehicleDef() {
		return vehicleDef;
	}

	public void setVehicleDef(String vehicleDef) {
		this.vehicleDef = vehicleDef;
	}

	public String getAlarmTypeName() {
		return alarmTypeName;
	}

	public void setAlarmTypeName(String alarmTypeName) {
		this.alarmTypeName = alarmTypeName;
	}

	public String[] getAlarmTypeIds() {
		return alarmTypeIds;
	}

	public void setAlarmTypeIds(String[] alarmTypeIds) {
		this.alarmTypeIds = alarmTypeIds;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getAlarmTypeGenre() {
		return alarmTypeGenre;
	}

	public void setAlarmTypeGenre(String alarmTypeGenre) {
		this.alarmTypeGenre = alarmTypeGenre;
	}

	public String getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}
	
	
}
