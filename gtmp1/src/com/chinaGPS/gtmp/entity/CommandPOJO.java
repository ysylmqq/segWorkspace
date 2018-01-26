package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * t_send_command表对应的实体。
 * 
 * @author xk
 * 
 */
@Component
@Scope("prototype")
public class CommandPOJO implements Serializable {
	private static final long serialVersionUID = -5606044425084129666L;
	/** 指令ID */
	private Long commandId;
	/**  */
	private Integer commandSn;
	/** 终端ID */
	private String unitId;
	/** SIM卡号 */
	private String simNo;
	/** 用户ID */
	private Long userId;
	/** 指令类型ID */
	private Integer commandTypeId;
	private String param;
	/** 网关回应 */
	private String gatewayBack;
	/** 终端回应 */
	private String unitBack;
	private String rawData;
	private Date stamp;
	/** 0：GPS 1：GSM */
	private Integer pathFlag;
	/** 用户名称 */
	private String userName;
	/** 指令类型名称 */
	private String commandTypeName;
	/** 整机编号 */
	private String vehicleDef;
	/** 终端序列号 */
	private String unitSn;
	private Date stamp2;
	private String pHeartbeatContent;
	/** 经销商数组 */
	private String[] dealerIds;
	/** 机型ID */
	private String vehicleModel;
	/** 机械代号 */
	private String vehicleCode;
	/** 配置号 */
	private String vehicleArg;
	/** 机型名称 */
	private String vehicleModelName;
	
	private String dealerName;
	private String areaName;
	private String vehicleStatus;
	private String departName;

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
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

	public String getVehicleModelName() {
		return vehicleModelName;
	}

	public void setVehicleModelName(String vehicleModelName) {
		this.vehicleModelName = vehicleModelName;
	}

	public Long getCommandId() {
		return commandId;
	}

	public void setCommandId(Long commandId) {
		this.commandId = commandId;
	}

	public Integer getCommandSn() {
		if (commandSn == null) {
			return Long.valueOf(commandId % 16000000).intValue();
		}
		return commandSn;
	}

	public void setCommandSn(Integer commandSn) {
		this.commandSn = commandSn;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getCommandTypeId() {
		return commandTypeId;
	}

	public void setCommandTypeId(Integer commandTypeId) {
		this.commandTypeId = commandTypeId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getGatewayBack() {
		return gatewayBack;
	}

	public void setGatewayBack(String gatewayBack) {
		this.gatewayBack = gatewayBack;
	}

	public String getUnitBack() {
		return unitBack;
	}

	public void setUnitBack(String unitBack) {
		this.unitBack = unitBack;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCommandTypeName() {
		return commandTypeName;
	}

	public void setCommandTypeName(String commandTypeName) {
		this.commandTypeName = commandTypeName;
	}

	public String getVehicleDef() {
		return vehicleDef;
	}

	public void setVehicleDef(String vehicleDef) {
		this.vehicleDef = vehicleDef;
	}

	public String getUnitSn() {
		return unitSn;
	}

	public void setUnitSn(String unitSn) {
		this.unitSn = unitSn;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getStamp2() {
		return stamp2;
	}

	public void setStamp2(Date stamp2) {
		this.stamp2 = stamp2;
	}

	public String getpHeartbeatContent() {
		return pHeartbeatContent;
	}

	public void setpHeartbeatContent(String pHeartbeatContent) {
		this.pHeartbeatContent = pHeartbeatContent;
	}

	public Integer getPathFlag() {
		return pathFlag;
	}

	public void setPathFlag(Integer pathFlag) {
		this.pathFlag = pathFlag;
	}

	public String[] getDealerIds() {
		return dealerIds;
	}

	public void setDealerIds(String[] dealerIds) {
		this.dealerIds = dealerIds;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
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

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	

}
