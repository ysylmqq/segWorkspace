package com.chinaGPS.gtmp.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 空中升级指令内容
 * @author xk
 *
 */
@Component
@Scope("prototype")
public class CommandUpgradePOJO implements Serializable{
    private Long commandId;
    /**
     * unitId 必须项
     */
    private String unitId;            
    private Long userId;
    private String simNo;             //下发终端sim卡号
    private String supperierSn;
    private String updateType; //升级类型 00-普通升级；01-强制升级
    private String deviceType; //设备类型 00-GPS终端；01-控制器；02-显示器
    private String softwareVersion;    //程序版本号 如0100
    private String unitTypeSn;   //终端型号 长度为5的字符串，少于5个字符补字节00
    private String ip;         //ip地址，如192.110.100.66  31H 33H
    private Integer serverPort;      //端口，实际的端口号最大为65535    31H 33H
    private String APN;        //APN，如：CMNET  32H 33H
    private Integer localPort; //本地端口 0~65535
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
    public String getSupperierSn() {
        return supperierSn;
    }
    public void setSupperierSn(String supperierSn) {
        this.supperierSn = supperierSn;
    }
    public String getUpdateType() {
        return updateType;
    }
    public void setUpdateType(String updateType) {
        this.updateType = updateType;
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
    public String getAPN() {
        return APN;
    }
    public void setAPN(String apn) {
        APN = apn;
    }
    public Integer getLocalPort() {
        return localPort;
    }
    public void setLocalPort(Integer localPort) {
        this.localPort = localPort;
    }
    public Integer getServerPort() {
        return serverPort;
    }
    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }
    public Long getCommandId() {
        return commandId;
    }
    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }
	public String getSimNo() {
		return simNo;
	}
	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}
}
