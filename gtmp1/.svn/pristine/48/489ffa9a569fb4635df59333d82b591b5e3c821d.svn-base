package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class SimServerPOJO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String simNo;

    private String unitId;

    private Date openTime;

    private Date endTime;

    private BigDecimal feeMonth;

    private BigDecimal status;

    private Date stopTime;

    private String stopReason;

    private String remark;

    private String operId;

    private Date createTime;
    
    private BigDecimal payAmount;
    
    private BigDecimal allFee;

    
    private String vehicleDef;//整机编号，此标识为玉柴用户可使用的机械标识。同一机构下需要防重处理。

	private String unitSn;//终端序列号：终端上传，供应商确定，不能有重复
	
	private String vehicleCode;//机械代号
	
	private String vehicleArg;//机械配置
	
	private  String isServer;
	
	private  float  allProfit; 
	
	private float customerAllPay;//客户一共缴费
	
	private float companyAllPay;//公司一共缴费

	private String modelName;//机械型号
	
	private String distributor;//经销商

	private String[] dealerIds;// 经销商组
	
	private String userName; //操作者用户名
	
	private float customerSimAllPay; //客户所有的SIM缴费记录总和
	
	private float customerStopAllFee; //客户所有的SIM缴费记录总和
	
	private BigDecimal custStatus;// 客户开卡情况 
	
	public BigDecimal getCustStatus() {
		return custStatus;
	}

	public void setCustStatus(BigDecimal custStatus) {
		this.custStatus = custStatus;
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

	public String getIsServer() {
		return isServer;
	}

	public void setIsServer(String isServer) {
		this.isServer = isServer;
	}

	public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo == null ? null : simNo.trim();
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getFeeMonth() {
        return feeMonth;
    }

    public void setFeeMonth(BigDecimal feeMonth) {
        this.feeMonth = feeMonth;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public String getStopReason() {
        return stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason == null ? null : stopReason.trim();
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

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getAllFee() {
		return allFee;
	}

	public void setAllFee(BigDecimal allFee) {
		this.allFee = allFee;
	}

	public float getCustomerAllPay() {
		return customerAllPay;
	}

	public void setCustomerAllPay(float customerAllPay) {
		this.customerAllPay = customerAllPay;
	}

	public float getCompanyAllPay() {
		return companyAllPay;
	}

	public void setCompanyAllPay(float companyAllPay) {
		this.companyAllPay = companyAllPay;
	}

	public float getAllProfit() {
		return allProfit;
	}

	public void setAllProfit(float allProfit) {
		this.allProfit = allProfit;
	}

	public float getCustomerSimAllPay() {
		return customerSimAllPay;
	}

	public void setCustomerSimAllPay(float customerSimAllPay) {
		this.customerSimAllPay = customerSimAllPay;
	}

	public float getCustomerStopAllFee() {
		return customerStopAllFee;
	}

	public void setCustomerStopAllFee(float customerStopAllFee) {
		this.customerStopAllFee = customerStopAllFee;
	}
	
}