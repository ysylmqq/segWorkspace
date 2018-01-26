package com.chinaGPS.gtmp.entity;

// Generated 2013-4-28 9:47:15 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 文 件 名 :TLastPositionPOJO.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：X
 * 创 建 人：蒋昌兵
 * 创 建 日 期：2013-05-06
 * 描 述： t_last_position表对应的类
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0	
 */
@Component
@Scope("prototype")
public class TLastPositionPOJO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String unitId;  //终端ID
	private Float lon;//车台经纬度
	private Float lat;//车台经纬度
	private Integer direction;//正北0度，0-360
	private Float speed;//单位：千米/小时
	private Integer locationState;//0-GPS模块不定位，1-GPS模块定位
	private String vehicleState;//待定
	private Date gpsTime;//GPS时间
	private String referencePosition;//参考位置
	private String province;//省
	private String city;//市
	private String county;//县
	private Integer isLogin;//是否在线 0 在线 1不在线
	private Date stamp;//最后修改时间

	public TLastPositionPOJO() {
	}

	public TLastPositionPOJO(String unitId) {
		this.unitId = unitId;
	}

	

	public String getUnitId() {
		return this.unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}


	public String getVehicleState() {
		return this.vehicleState;
	}

	public void setVehicleState(String vehicleState) {
		this.vehicleState = vehicleState;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getGpsTime() {
		return this.gpsTime;
	}

	public void setGpsTime(Date gpsTime) {
		this.gpsTime = gpsTime;
	}

	public String getReferencePosition() {
		return this.referencePosition;
	}

	public void setReferencePosition(String referencePosition) {
		this.referencePosition = referencePosition;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getStamp() {
		return this.stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
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

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public Integer getLocationState() {
		return locationState;
	}

	public void setLocationState(Integer locationState) {
		this.locationState = locationState;
	}

	public Integer getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}

	public Float getSpeed() {
		return speed;
	}

	public void setSpeed(Float speed) {
		this.speed = speed;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
