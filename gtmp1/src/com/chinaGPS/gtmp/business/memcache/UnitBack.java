package com.chinaGPS.gtmp.business.memcache;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * @Package:com.chinaGPS.gtmp.business.memcache
 * @ClassName:UnitBack
 * @Description:
 * @author:xk
 * @date:Apr 19, 2013 10:20:33 AM
 */
public class UnitBack {
    private String unitId;
    private String commandSn;
    private String unitback; // 命令应答 00-接收成功；01-校验错；其他-其他传输失败类型
    private String ip;
    private String port;
    private String apn;
    private String number; // 短信中心号码
    //心跳功能应答     00-接收成功；01-校验错；02-控制器未上电；其他-其他传输失败类型
    private String pantStatus; 
    //平台查询控制器版本及ID信息
    private String versionCanId;  //by zfy 2013-7-12
    
    private String vehicleDef;//整机编号 by zfy 2013-7-11
    private Date stamp;//时间戳 by zfy 2013-7-11
    private String responseType;//网关回应或者终端回应 add by zfy 2013-7-11
    private String remark;//备注 add by zfy 2013-7-11
 
    public String getCommandSn() {
        return commandSn;
    }
    public void setCommandSn(String commandSn) {
        this.commandSn = commandSn;
    }
    public String getUnitback() {
        return unitback;
    }
    public void setUnitback(String unitback) {
        this.unitback = unitback;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getApn() {
        return apn;
    }
    public void setApn(String apn) {
        this.apn = apn;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getUnitId() {
        return unitId;
    }
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
	public String getPantStatus() {
		return pantStatus;
	}
	public void setPantStatus(String pantStatus) {
		this.pantStatus = pantStatus;
	}
	public String getVehicleDef() {
		return vehicleDef;
	}
	public void setVehicleDef(String vehicleDef) {
		this.vehicleDef = vehicleDef;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getVersionCanId() {
		return versionCanId;
	}
	public void setVersionCanId(String versionCanId) {
		this.versionCanId = versionCanId;
	}
	
}

