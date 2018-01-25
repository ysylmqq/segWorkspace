package com.chinagps.driverbook.pojo;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 车辆信息POJO
 * 
 * @author Ben
 *
 */
public class Vehicle extends BaseEntity {
	private static final long serialVersionUID = 556043300271224672L;

	/** 客户ID */
	private String customerId;
	/** 车辆ID */
	private String vehicleId;
	/** 车台呼号 */
	private String callLetter;
	/** 车牌号码 */
	private String plateNo;
	/** 品牌 */
	private Integer brand;
	/** 品牌名称 */
	private String brandName;
	/** 车系 */
	private Integer series;
	
	/** 归属公司  */
	private Integer subcoNo;
	
	public Integer getSubcoNo() {
		return subcoNo;
	}

	public void setSubcoNo(Integer subcoNo) {
		this.subcoNo = subcoNo;
	}

	/** 车系名称 */
	private String seriesName;
	/** 车型 */
	private Integer model;
	/** 车型名称 */
	private String modelName;
	/** 初次登录日期 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date registerDate;
	/** 燃油标号 */
	private Integer oilGrade;
	/** 自定义油价 */
	private Double price;
	/** 产品型号（用于区分新老车台） */
	private String productCode;
	/** 油箱容积（单位：L，默认50L） */
	private Integer oilVolume;
	/** 服务密码 */
	private String servicePwd;
	
	/** 服务截止时间 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date feeSedate;
	/** 是否到期 */
	private Boolean expired;
	/** 4S店ID */
	private Long fsId;
	/** 车驾号 */
	private String vin;
	/** 保险公司ID */
	private Integer insuranceId;
	/** 保险公司 */
	private String insuranceName;
	/** 行驶证发证日期 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date vlBdate;
	
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getCallLetter() {
		return callLetter;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public Integer getBrand() {
		return brand;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getSeries() {
		return series;
	}

	public void setSeries(Integer series) {
		this.series = series;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Integer getOilGrade() {
		return oilGrade;
	}

	public void setOilGrade(Integer oilGrade) {
		this.oilGrade = oilGrade;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getOilVolume() {
		return oilVolume;
	}

	public void setOilVolume(Integer oilVolume) {
		this.oilVolume = oilVolume;
	}

	public String getServicePwd() {
		return servicePwd;
	}

	public void setServicePwd(String servicePwd) {
		this.servicePwd = servicePwd;
	}

	public Date getFeeSedate() {
		return feeSedate;
	}

	public void setFeeSedate(Date feeSedate) {
		this.feeSedate = feeSedate;
	}

	public Boolean getExpired() {
		if (this.feeSedate != null) {
			return this.feeSedate.before(Calendar.getInstance().getTime());
		}
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	public Long getFsId() {
		return fsId;
	}

	public void setFsId(Long fsId) {
		this.fsId = fsId;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Integer getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(Integer insuranceId) {
		this.insuranceId = insuranceId;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public Date getVlBdate() {
		return vlBdate;
	}

	public void setVlBdate(Date vlBdate) {
		this.vlBdate = vlBdate;
	}

}
