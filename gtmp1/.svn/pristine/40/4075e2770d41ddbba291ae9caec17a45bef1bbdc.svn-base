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
public class VehicleUnitPOJO implements Serializable{
	
	private Long vehicleId; //主键
	
	private String unitId;//终端号编号 进行绑定 
	
	private String vehicleDef;//整机编号，此标识为玉柴用户可使用的机械标识。同一机构下需要防重处理。
	
	private Long typeId;//对应字典：T_DIC_VEHICLE_TYPE
	
	private Long modelId;//对应字典：T_DIC_VEHICLE_MODEL
	
	private Integer status;//1：测试组，2：已测组，3：销售组，8:法务组，9：停用组
	
	private String supperierSn;//对应字典：终端上传，T_DIC_SUPPERIER
	private String unitSn;//终端序列号：终端上传，供应商确定，不能有重复
	private String unitTypeSn;//终端类型：终端上传，T_DIC_UNIT_TYPE 
	private String materialNo;//物料编号
	private String simNo;//SIM卡号
	private String steelNo;//钢号
	private Integer isLogin;//是否在线，0在线，1不在线
	
    private String lon;
    private String lat;
    private String speed;
    private String direction;
    private String gpsTime;
    private String vehicleState;
    private String province;    //省
    private String city;        //市
    private String county;      //县
    private String referencePosition;// 参考位置
    private String locationState;// 定位状态 1 定位 0 不定位
    private Integer alarmFlag;// 警情标志位 1 警情上报
    private String nowTime; //memcache记录时间
    private int alock; 
    private int block;
    private int clock;
    private String lockState;
	public String getSupperierSn() {
	    return supperierSn;
	}

	public void setSupperierSn(String supperierSn) {
	    this.supperierSn = supperierSn;
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

	public String getSteelNo() {
	    return steelNo;
	}

	public void setSteelNo(String steelNo) {
	    this.steelNo = steelNo;
	}

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

	public Integer getStatus() {
	    return status;
	}

	public void setStatus(Integer status) {
	    this.status = status;
	}

	public String getMaterialNo() {
	    return materialNo;
	}

	public void setMaterialNo(String materialNo) {
	    this.materialNo = materialNo;
	}

	public String getUnitTypeSn() {
	    return unitTypeSn;
	}

	public void setUnitTypeSn(String unitTypeSn) {
	    this.unitTypeSn = unitTypeSn;
	}

	public Integer getIsLogin() {
	    return isLogin;
	}

	public void setIsLogin(Integer isLogin) {
	    this.isLogin = isLogin;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(String gpsTime) {
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

	public Integer getAlarmFlag() {
		return alarmFlag;
	}

	public void setAlarmFlag(Integer alarmFlag) {
		this.alarmFlag = alarmFlag;
	}
	
	public String getNowTime() {
		return nowTime;
	}

	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}

	public int getAlock() {
		return alock;
	}

	public void setAlock(int alock) {
		this.alock = alock;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public int getClock() {
		return clock;
	}

	public void setClock(int clock) {
		this.clock = clock;
	}

	public String getLockState() {
		String ABlock = "";
		String Clock="";
		 if(getAlock()==2){
             ABlock="A锁";
         }
         if(getBlock()==2){
             if(!ABlock.equals("")){
                 ABlock=ABlock+'/';
             }
             ABlock=ABlock+"B锁";
         }
         if(getClock()==2){
             Clock="禁止防拆";
         }
         if(getClock()==1){
             Clock="使能防拆";
         }
         lockState = ABlock+" "+Clock;
		return lockState;
	}

	public void setLockState(String lockState) {
		this.lockState = lockState;
	}
	 
	
}
