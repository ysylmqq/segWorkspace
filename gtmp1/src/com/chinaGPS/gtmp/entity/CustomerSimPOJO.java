package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class CustomerSimPOJO  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String simNo;

    private Date startTime;

    private Date endTime;

    private BigDecimal freePeriod;

    private BigDecimal status;

    private Date stopStartTime;

    private Date stopEndTime;

    private String remark;
    
    private String stopReason;
    
    private BigDecimal allFee;
    
    private BigDecimal stopFeeMonth;

    private String operId;

    private Date createTime;
    
    private String isServer;
    
	private String vehicleDef;//整机编号，此标识为玉柴用户可使用的机械标识。同一机构下需要防重处理。

	private String unitSn;//终端序列号：终端上传，供应商确定，不能有重复
	
	private String vehicleCode;//机械代号
	
	private String vehicleArg;//机械配置
	
    private String modelName;//机械型号
	
	private String distributor;//经销商

	private String[] dealerIds;// 经销商组

    private String userName; //操作者用户名
    
    private BigDecimal stopSaveFee;//停机保号累计费用
	
	public BigDecimal getStopSaveFee() {
		return stopSaveFee;
	}

	public void setStopSaveFee(BigDecimal stopSaveFee) {
		this.stopSaveFee = stopSaveFee;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String[] getDealerIds() {
		return dealerIds;
	}

	public void setDealerIds(String[] dealerIds) {
		this.dealerIds = dealerIds;
	}

    public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo == null ? null : simNo.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getFreePeriod() {
        return freePeriod;
    }

    public void setFreePeriod(BigDecimal freePeriod) {
        this.freePeriod = freePeriod;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public Date getStopStartTime() {
        return stopStartTime;
    }

    public void setStopStartTime(Date stopStartTime) {
        this.stopStartTime = stopStartTime;
    }

    public Date getStopEndTime() {
        return stopEndTime;
    }

    public void setStopEndTime(Date stopEndTime) {
        this.stopEndTime = stopEndTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
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

	public String getIsServer() {
		return isServer;
	}

	public void setIsServer(String isServer) {
		this.isServer = isServer;
	}

	public BigDecimal getAllFee() {
		return allFee;
	}

	public void setAllFee(BigDecimal allFee) {
		this.allFee = allFee;
	}

	public BigDecimal getStopFeeMonth() {
		return stopFeeMonth;
	}

	public void setStopFeeMonth(BigDecimal stopFeeMonth) {
		this.stopFeeMonth = stopFeeMonth;
	}
	
}