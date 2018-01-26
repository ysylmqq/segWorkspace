package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class SimPayPOJO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal simPayId;

    private String simNo;

    private Date startTime;

    private Date endTime;

    private BigDecimal payAmount;

    private BigDecimal feeMonth;

    private String remark;

    private String operId;

    private Date createTime;
    
	private BigDecimal status;

	private BigDecimal isLast;
   
	
	private String vehicleDef;//整机编号，此标识为玉柴用户可使用的机械标识。同一机构下需要防重处理。

	private String unitSn;//终端序列号：终端上传，供应商确定，不能有重复
	
	private String vehicleCode;//机械代号
	
	private String vehicleArg;//机械配置
	
	private String modelName;//机械型号
	
	private String distributor;//经销商

	private String[] dealerIds;// 经销商组
	
	private String userName; //操作者用户名
	
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

	public BigDecimal getStatus() {
		return status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public BigDecimal getIsLast() {
		return isLast;
	}

	public void setIsLast(BigDecimal isLast) {
		this.isLast = isLast;
	}

    

    public BigDecimal getSimPayId() {
        return simPayId;
    }

    public void setSimPayId(BigDecimal simPayId) {
        this.simPayId = simPayId;
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

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getFeeMonth() {
        return feeMonth;
    }

    public void setFeeMonth(BigDecimal feeMonth) {
        this.feeMonth = feeMonth;
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

	public String getUnitSn() {
		return unitSn;
	}

	public void setUnitSn(String unitSn) {
		this.unitSn = unitSn;
	}

	
	public String getVehicleDef() {
		return vehicleDef;
	}

	public void setVehicleDef(String vehicleDef) {
		this.vehicleDef = vehicleDef;
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
    
}