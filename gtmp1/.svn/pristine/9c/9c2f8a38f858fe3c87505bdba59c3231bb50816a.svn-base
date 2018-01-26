package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Package:com.chinaGPS.gtmp.entity
 * @ClassName:CompositePOJO
 * @Description:综合查询结果
 * @author:zfy
 * @date:2013-4-24 上午08:49:14
 */
@Component
@Scope("prototype")
public class CompositePOJO implements Serializable{
    /**
     * 机械信息
     */
    private Long vehicleId; // 主键

    private String vehicleDef;// 整机编号，此标识为玉柴用户可使用的机械标识。同一机构下需要防重处理。

    private Long typeId;// 对应字典：T_DIC_VEHICLE_TYPE

    private Long modelId;// 对应字典：T_DIC_VEHICLE_MODEL

    private String fixMan;// 终端安装人

    private Date fixDate;// 终端安装时间
    
    private Integer status;//1：测试组，2：已测组，3：销售组，8：法务,9：停用组
    private Integer isValid;//有效性：0 有效 1 无效
    /**
     * 销售信息
     */
    private Date saleDate;// 销售时间
    private String dealerName;//经销商
    private String ownerName;//机主名称
    private String ownerPhone;//机主电话
    /**
     * 终端信息
     */
    private String unitId;// 主键：供应商ID+终端序列号
    private String supperierSn;// 对应字典：终端上传，T_DIC_SUPPERIER
    private String unitSn;// 终端序列号：终端上传，供应商确定，不能有重复
    private String unitTypeSn;// 终端类型：终端上传，T_DIC_UNIT_TYPE
	private String unitType;// 终端类型
    private String materialNo;// 物料编号
    private String simNo;// SIM卡号
    private String steelNo;// 钢号
    private String hardwareVersion;// 硬件版本号：终端上传
    private String softwareVersion;// 软件版本号：终端上传
    private Date productionDate;// 生产日期

    /**
     * GPS信息
     */
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
    private Integer isLogin;// 是否在线 0 在线 1不在线
    private Integer alarmFlag;// 警情标志位 1 警情上报
    private String nowTime; //memcache记录时间
    /**
     * 工况信息
     */
    private Integer engineCoolantTemperature; // 发动机冷却液温度
    private Float engineCoolantLevel; // 发动机冷却液液位
    private Float engineOilPressure;// 发动机机油压力
    private Float engineFuelLevel; // 发动机燃油油位
    private Integer engineSpeed; // 发动机转速
    private Integer hydraulicOilTemperature; // 液压油温度
    private Float systemVoltage; // 系统电压
    // 0xA1
    private Float beforePumpMainPressure; // 挖掘机前泵主压压力
    private Float afterPumpMainPressure; // 挖掘机后泵主压压力
    private Float pilotPressure1; // 挖掘机先导压力1
    private Float pilotPressure2; // 挖掘机先导压力2
    private Float beforePumpPressure; // 挖掘机前泵负压压力
    private Float afterPumpPressure; // 挖掘机后泵负压压力
    // 0xA2
    private Integer proportionalCurrent1; // 挖掘机比例阀电流1
    private Integer proportionalCurrent2; // 挖掘机比例阀电流2
    private Integer gear;           //挖机档位
    private Float totalWorkingHours; // 累计工作小时
    private Float correctHours;//修正时间
    // 0xA3
    private Integer highEngineCoolantTemperVal;//发动机冷却液温度高报警设置值
    private Float lowEngineOilPressureAlarmValue;//发动机机油压力低报警设置值
    private Float highVoltageAlarmValue;//系统电压高报警设置值
    private Float lowVoltageAlarmValue;//系统电压低报警设置值
    private Integer highHydraulicOilTemperAlarmVal;//液压油温高报警值
    private Integer toothNumberValue; // 飞轮齿数设置值
    // 0xA4 系统信息
    private String monitorSoftwareCode; // 监控器（显示器）供应商内部软件代号
    private String monitorYcSoftwareCode; // 监控器（显示器）玉柴内部软件版本号
    private String controllerSoftwareCode; // 控制器供应商内部软件代号
    private String controllerYcSoftwareCode; // 控制器玉柴内部软件代号
	private Integer faultCode; // 故障码
    private Integer engineOilTemperature; //发动机机油温度
    private String productCode; //产品编号
    private String rawData;//原始数据
    private Integer isWork;// 是否工作
    private Date stamp;
    private Integer alock;//a锁
    private Integer block;//b锁
    private Integer clock;//使能
    /**
     * 中文名称
     */
    private String typeName;//机械类型
	
    private String modelName;//机械型号
    
    private String supperierName;//终端供应商名称
    
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
    @JSON(format="yyyy-MM-dd") 
    public Date getFixDate() {
        return fixDate;
    }
    public void setFixDate(Date fixDate) {
        this.fixDate = fixDate;
    }
    public String getUnitId() {
        return unitId;
    }
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
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
    public String getUnitTypeSn() {
        return unitTypeSn;
    }
    public void setUnitTypeSn(String unitTypeSn) {
        this.unitTypeSn = unitTypeSn;
    }
    public String getMaterialNo() {
        return materialNo;
    }
    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
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
    public String getHardwareVersion() {
        return hardwareVersion;
    }
    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }
    public String getSoftwareVersion() {
        return softwareVersion;
    }
    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }
    public Date getProductionDate() {
        return productionDate;
    }
    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
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
    public String getSupperierName() {
        return supperierName;
    }
    public void setSupperierName(String supperierName) {
        this.supperierName = supperierName;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getEngineCoolantTemperature() {
        return engineCoolantTemperature;
    }
    public void setEngineCoolantTemperature(Integer engineCoolantTemperature) {
        this.engineCoolantTemperature = engineCoolantTemperature;
    }
    public Float getEngineCoolantLevel() {
        return engineCoolantLevel;
    }
    public void setEngineCoolantLevel(Float engineCoolantLevel) {
        this.engineCoolantLevel = engineCoolantLevel;
    }
    public Float getEngineOilPressure() {
        return engineOilPressure;
    }
    public void setEngineOilPressure(Float engineOilPressure) {
        this.engineOilPressure = engineOilPressure;
    }
    public Float getEngineFuelLevel() {
        return engineFuelLevel;
    }
    public void setEngineFuelLevel(Float engineFuelLevel) {
        this.engineFuelLevel = engineFuelLevel;
    }
    public Integer getEngineSpeed() {
        return engineSpeed;
    }
    public void setEngineSpeed(Integer engineSpeed) {
        this.engineSpeed = engineSpeed;
    }
    public Integer getHydraulicOilTemperature() {
        return hydraulicOilTemperature;
    }
    public void setHydraulicOilTemperature(Integer hydraulicOilTemperature) {
        this.hydraulicOilTemperature = hydraulicOilTemperature;
    }
    public Float getSystemVoltage() {
        return systemVoltage;
    }
    public void setSystemVoltage(Float systemVoltage) {
        this.systemVoltage = systemVoltage;
    }
    public Float getBeforePumpMainPressure() {
        return beforePumpMainPressure;
    }
    public void setBeforePumpMainPressure(Float beforePumpMainPressure) {
        this.beforePumpMainPressure = beforePumpMainPressure;
    }
    public Float getAfterPumpMainPressure() {
        return afterPumpMainPressure;
    }
    public void setAfterPumpMainPressure(Float afterPumpMainPressure) {
        this.afterPumpMainPressure = afterPumpMainPressure;
    }
    public Float getPilotPressure1() {
        return pilotPressure1;
    }
    public void setPilotPressure1(Float pilotPressure1) {
        this.pilotPressure1 = pilotPressure1;
    }
    public Float getPilotPressure2() {
        return pilotPressure2;
    }
    public void setPilotPressure2(Float pilotPressure2) {
        this.pilotPressure2 = pilotPressure2;
    }
    public Float getBeforePumpPressure() {
        return beforePumpPressure;
    }
    public void setBeforePumpPressure(Float beforePumpPressure) {
        this.beforePumpPressure = beforePumpPressure;
    }
    public Float getAfterPumpPressure() {
        return afterPumpPressure;
    }
    public void setAfterPumpPressure(Float afterPumpPressure) {
        this.afterPumpPressure = afterPumpPressure;
    }
    public Integer getProportionalCurrent1() {
        return proportionalCurrent1;
    }
    public void setProportionalCurrent1(Integer proportionalCurrent1) {
        this.proportionalCurrent1 = proportionalCurrent1;
    }
    public Integer getProportionalCurrent2() {
        return proportionalCurrent2;
    }
    public void setProportionalCurrent2(Integer proportionalCurrent2) {
        this.proportionalCurrent2 = proportionalCurrent2;
    }
    public Integer getGear() {
        return gear;
    }
    public void setGear(Integer gear) {
        this.gear = gear;
    }
    public Float getTotalWorkingHours() {
        return totalWorkingHours;
    }
    public void setTotalWorkingHours(Float totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }
    public Float getLowEngineOilPressureAlarmValue() {
        return lowEngineOilPressureAlarmValue;
    }
    public void setLowEngineOilPressureAlarmValue(
    	Float lowEngineOilPressureAlarmValue) {
        this.lowEngineOilPressureAlarmValue = lowEngineOilPressureAlarmValue;
    }
    public Float getHighVoltageAlarmValue() {
        return highVoltageAlarmValue;
    }
    public void setHighVoltageAlarmValue(Float highVoltageAlarmValue) {
        this.highVoltageAlarmValue = highVoltageAlarmValue;
    }
    public Float getLowVoltageAlarmValue() {
        return lowVoltageAlarmValue;
    }
    public void setLowVoltageAlarmValue(Float lowVoltageAlarmValue) {
        this.lowVoltageAlarmValue = lowVoltageAlarmValue;
    }
    public Integer getToothNumberValue() {
        return toothNumberValue;
    }
    public void setToothNumberValue(Integer toothNumberValue) {
        this.toothNumberValue = toothNumberValue;
    }
    public String getMonitorSoftwareCode() {
        return monitorSoftwareCode;
    }
    public void setMonitorSoftwareCode(String monitorSoftwareCode) {
        this.monitorSoftwareCode = monitorSoftwareCode;
    }
    public String getMonitorYcSoftwareCode() {
        return monitorYcSoftwareCode;
    }
    public void setMonitorYcSoftwareCode(String monitorYcSoftwareCode) {
        this.monitorYcSoftwareCode = monitorYcSoftwareCode;
    }
    public String getControllerSoftwareCode() {
        return controllerSoftwareCode;
    }
    public void setControllerSoftwareCode(String controllerSoftwareCode) {
        this.controllerSoftwareCode = controllerSoftwareCode;
    }
    public String getControllerYcSoftwareCode() {
        return controllerYcSoftwareCode;
    }
    public void setControllerYcSoftwareCode(String controllerYcSoftwareCode) {
        this.controllerYcSoftwareCode = controllerYcSoftwareCode;
    }
    public Integer getIsWork() {
        return isWork;
    }
    public void setIsWork(Integer isWork) {
        this.isWork = isWork;
    }
    public Integer getHighEngineCoolantTemperVal() {
        return highEngineCoolantTemperVal;
    }
    public void setHighEngineCoolantTemperVal(Integer highEngineCoolantTemperVal) {
        this.highEngineCoolantTemperVal = highEngineCoolantTemperVal;
    }
    public Integer getHighHydraulicOilTemperAlarmVal() {
        return highHydraulicOilTemperAlarmVal;
    }
    public void setHighHydraulicOilTemperAlarmVal(
    	Integer highHydraulicOilTemperAlarmVal) {
        this.highHydraulicOilTemperAlarmVal = highHydraulicOilTemperAlarmVal;
    }
    public String getRawData() {
        return rawData;
    }
    public void setRawData(String rawData) {
        this.rawData = rawData;
    }
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
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
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	   @JSON(format="yyyy-MM-dd HH:mm:ss")
	    public Date getStamp() {
	        return stamp;
	    }
	    public void setStamp(Date stamp) {
	        this.stamp = stamp;
	    }
		public Float getCorrectHours() {
			return correctHours;
		}
		public void setCorrectHours(Float correctHours) {
			this.correctHours = correctHours;
		}
		public Integer getIsLogin() {
			return isLogin;
		}
		public void setIsLogin(Integer isLogin) {
			this.isLogin = isLogin;
		}
		public Integer getFaultCode() {
			return faultCode;
		}
		public void setFaultCode(Integer faultCode) {
			this.faultCode = faultCode;
		}
		public Integer getEngineOilTemperature() {
			return engineOilTemperature;
		}
		public void setEngineOilTemperature(Integer engineOilTemperature) {
			this.engineOilTemperature = engineOilTemperature;
		}
		public String getProductCode() {
			return productCode;
		}
		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}
		public Integer getAlock() {
			return alock;
		}
		public void setAlock(Integer alock) {
			this.alock = alock;
		}
		public Integer getBlock() {
			return block;
		}
		public void setBlock(Integer block) {
			this.block = block;
		}
		public Integer getClock() {
			return clock;
		}
		public void setClock(Integer clock) {
			this.clock = clock;
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
}
