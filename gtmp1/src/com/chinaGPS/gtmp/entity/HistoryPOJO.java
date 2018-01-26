package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Package:com.chinaGPS.gtmp.entity
 * @ClassName:HistoryPOJO
 * @Description:
 * @author:zfy
 * @date:2013-4-19 下午04:07:02
 */
@Component
@Scope("prototype")
public class HistoryPOJO implements Serializable {
	private String vehicleDef;
	private Long historyId;
    private String unitId;
    private Float lon;
    private Float lat;
    private Integer direction;//方向 正北0度，0-360
    private Float speed;//速度 单位：千米/小时
    private Integer locationState;//定位状态 0-GPS模块不定位，1-GPS模块定位
    private String vehicleState;//车辆状态
    private Date gpsTime;
    private String referencePosition;//参考位置
    private String rawData;//原始包数据
    private Date stamp;
    
    private Date startTime;
    private Date endTime;
    private Integer alarmFlag;// 警情标志位 1 警情上报
    private Date nowTime; //memcache记录时间
    public String getVehicleDef() {
		return vehicleDef;
	}
	public void setVehicleDef(String vehicleDef) {
		this.vehicleDef = vehicleDef;
	}
    public Long getHistoryId() {
        return historyId;
    }
    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }
    public String getUnitId() {
        return unitId;
    }
    public void setUnitId(String unitId) {
        this.unitId = unitId;
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
    public Float getSpeed() {
        return speed;
    }
    public void setSpeed(Float speed) {
        this.speed = speed;
    }
    public Integer getLocationState() {
        return locationState;
    }
    public void setLocationState(Integer locationState) {
        this.locationState = locationState;
    }
    public String getVehicleState() {
        return vehicleState;
    }
    public void setVehicleState(String vehicleState) {
        this.vehicleState = vehicleState;
    }
    @JSON(format="yyyy-MM-dd HH:mm:ss") 
    public Date getGpsTime() {
        return gpsTime;
    }
    public void setGpsTime(Date gpsTime) {
        this.gpsTime = gpsTime;
    }
    public String getReferencePosition() {
        return referencePosition;
    }
    public void setReferencePosition(String referencePosition) {
        this.referencePosition = referencePosition;
    }
    public String getRawData() {
        return rawData;
    }
    public void setRawData(String rawData) {
        this.rawData = rawData;
    }
    @JSON(format="yyyy-MM-dd HH:mm:ss") 
    public Date getStamp() {
        return stamp;
    }
    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }
    @JSON(format="yyyy-MM-dd HH:mm:ss") 
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    @JSON(format="yyyy-MM-dd HH:mm:ss") 
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
	public Integer getAlarmFlag() {
		return alarmFlag;
	}
	public void setAlarmFlag(Integer alarmFlag) {
		this.alarmFlag = alarmFlag;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getNowTime() {
		return nowTime;
	}
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}
	
}

