package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * t_remote_upgrade表对应的实体:远程升级。
 * @author zfy
 */
@Component
@Scope("prototype")
public class RemoteUpgradePOJO implements Serializable{
	private Long upgradeId;
        private String supperierSn;//供应商编号
        private String upgradeType;//00-普通升级；01-强制升级
        private String deviceType;//00-GPS终端；01-控制器；02-显示器。
        private String softwareVersion;//程序版本号
        private String unitTypeSn;//终端类型
        private String ip;//IP地址
	private String apn;//APN
	private String serverPort;//服务器端口号
	private String localPort;//本地端口号
	private String unitId;//终端ID
	private String userId;//操作人
	private String operateTime;
	private String result;//升级状态 00-文件格式正确；01-不存在任何文件；02-供应商标识不对；03-终端型号不对；04-程序版本不对；05-GPS终端升级成功；06-控制器升级成功；07-显示器升级成功
	private String upgradeState;//终端应答 00:接收成功;01-校验错；其他-其他传输失败类型
	private Date stamp;
	private Integer pathFlag;//0：GPS   1：GSM
	
	private String vehicleDef;//整机编号，此标识为玉柴用户可使用的机械标识。同一机构下需要防重处理。
	private String userName;
	private String unitSn;//终端序列号
	private String simNo;//Sim卡号
	private Date stamp2;
	private String[] dealerIds;// 查询更多条件中的经销商数组
	public Long getUpgradeId() {
	    return upgradeId;
	}
	public void setUpgradeId(Long upgradeId) {
	    this.upgradeId = upgradeId;
	}
	public String getSupperierSn() {
	    return supperierSn;
	}
	public void setSupperierSn(String supperierSn) {
	    this.supperierSn = supperierSn;
	}
	public String getUpgradeType() {
	    return upgradeType;
	}
	public void setUpgradeType(String upgradeType) {
	    this.upgradeType = upgradeType;
	}
	public String getDeviceType() {
	    return deviceType;
	}
	public void setDeviceType(String deviceType) {
	    this.deviceType = deviceType;
	}
	public String getSoftwareVersion() {
	    return softwareVersion;
	}
	public void setSoftwareVersion(String softwareVersion) {
	    this.softwareVersion = softwareVersion;
	}
	public String getUnitTypeSn() {
		return unitTypeSn;
	}
	public void setUnitTypeSn(String unitTypeSn) {
		this.unitTypeSn = unitTypeSn;
	}
	public String getIp() {
	    return ip;
	}
	public void setIp(String ip) {
	    this.ip = ip;
	}
	public String getApn() {
	    return apn;
	}
	public void setApn(String apn) {
	    this.apn = apn;
	}
	public String getServerPort() {
	    return serverPort;
	}
	public void setServerPort(String serverPort) {
	    this.serverPort = serverPort;
	}
	public String getLocalPort() {
	    return localPort;
	}
	public void setLocalPort(String localPort) {
	    this.localPort = localPort;
	}
	public String getUnitId() {
	    return unitId;
	}
	public void setUnitId(String unitId) {
	    this.unitId = unitId;
	}
	public String getUserId() {
	    return userId;
	}
	public void setUserId(String userId) {
	    this.userId = userId;
	}
	public String getOperateTime() {
	    return operateTime;
	}
	public void setOperateTime(String operateTime) {
	    this.operateTime = operateTime;
	}
	public String getResult() {
	    return result;
	}
	public void setResult(String result) {
	    this.result = result;
	}
	public String getVehicleDef() {
	    return vehicleDef;
	}
	public void setVehicleDef(String vehicleDef) {
	    this.vehicleDef = vehicleDef;
	}
	public String getUserName() {
	    return userName;
	}
	public void setUserName(String userName) {
	    this.userName = userName;
	}
	public String getUnitSn() {
	    return unitSn;
	}
	public void setUnitSn(String unitSn) {
	    this.unitSn = unitSn;
	}
	public Integer getPathFlag() {
	    return pathFlag;
	}
	public void setPathFlag(Integer pathFlag) {
	    this.pathFlag = pathFlag;
	}
	public String getUpgradeState() {
	    return upgradeState;
	}
	public void setUpgradeState(String upgradeState) {
	    this.upgradeState = upgradeState;
	}
	public String getSimNo() {
		return simNo;
	}
	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}
	public String[] getDealerIds() {
		return dealerIds;
	}
	public void setDealerIds(String[] dealerIds) {
		this.dealerIds = dealerIds;
	}
	 @JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	public Date getStamp2() {
		return stamp2;
	}
	public void setStamp2(Date stamp2) {
		this.stamp2 = stamp2;
	}
	
}
