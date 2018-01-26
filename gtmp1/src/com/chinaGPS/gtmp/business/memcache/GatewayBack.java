package com.chinaGPS.gtmp.business.memcache;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * @Package:com.chinaGPS.gtmp.business.memcache
 * @ClassName:GatewayBack
 * @Description:
 * @author:xk
 * @date:Apr 19, 2013 10:20:44 AM
 */
public class GatewayBack implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5863353239139099387L;
	private String unitId;
    private String commandSn;
    private String isSuccess; // 成功标识 00 成功 01 下发失败
    private Integer messageNumber; //流水号    
    private String vehicleDef;//整机编号
    private String commandType;//指令类型
    private Integer pathFlag ; //通道标志   0：GPS   1：GSM
    private String sucFlag;//终端回应是否成功标识      1  ：成功
    
    private Date stamp;//时间戳
    private String responseType;//网关回应或者终端回应 add by zfy 2013-7-11
    private String remark;//备注 add by zfy 2013-7-11
    public String getUnitId() {
        return unitId;
    }
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
    public String getCommandSn() {
        return commandSn;
    }
    public void setCommandSn(String commandSn) {
        this.commandSn = commandSn;
    }
    public String getIsSuccess() {
        return isSuccess;
    }
    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }
    public String getVehicleDef() {
        return vehicleDef;
    }
    public void setVehicleDef(String vehicleDef) {
        this.vehicleDef = vehicleDef;
    }
    public String getCommandType() {
        return commandType;
    }
    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }
	public Integer getPathFlag() {
		return pathFlag;
	}
	public void setPathFlag(Integer pathFlag) {
		this.pathFlag = pathFlag;
	}
	public Integer getMessageNumber() {
		return messageNumber;
	}
	public void setMessageNumber(Integer messageNumber) {
		this.messageNumber = messageNumber;
	}
	public String getSucFlag() {
		return sucFlag;
	}
	public void setSucFlag(String sucFlag) {
		this.sucFlag = sucFlag;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getStamp() {
	    stamp=new Date();
	    return stamp;
	}
	public void setStamp(Date stamp) {
	    this.stamp = stamp;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	
	
}

