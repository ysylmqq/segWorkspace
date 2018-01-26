package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 * 
 * @Package:com.chinaGPS.gtmp.entity
 * @ClassName:UnitPOJO
 * @Description:终端POJO
 * @author:zfy
 * @date:2013-4-8 下午04:08:04
 */
@Component
@Scope("prototype")
public class UnitPOJO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String unitId;//主键：供应商ID+终端序列号
	private String supperierSn;//对应字典：终端上传，T_DIC_SUPPERIER
	private String unitSn;//终端序列号：终端上传，供应商确定，不能有重复
	private String unitTypeSn;//终端类型：终端上传，T_DIC_UNIT_TYPE
	private String supperierName;
	private String materialNo;//物料编号
	private String simNo;//SIM卡号
	private String steelNo;//钢号
	private String hardwareVersion;//硬件版本号：终端上传
	private String softwareVersion;//软件版本号：终端上传
	private Date productionDate;//生产日期
	private Date registedTime;//登记时间
	private Long userId;//登记操作人
	private Integer isValid;//有效性：0 有效 1 无效
	private String remark;
	private Integer state;//1：待安装，2：已安装，9：返修
	private Date stamp;
	private Integer intype;//0：警车，1：警员
	
	private  Date openTime;//SIM卡开始服务时间
	private Date endTime;//sim卡服务结束时间
	private BigDecimal payAmount;//sim里面的金额

	private String unitType;//终端类型
	private String username;//登记人名称
	public String getUnitType() {
	    return unitType;
	}
	public void setUnitType(String unitType) {
	    this.unitType = unitType;
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
	@JSON(format="yyyy-MM-dd") 
	public Date getProductionDate() {
	    return productionDate;
	}
	public void setProductionDate(Date productionDate) {
	    this.productionDate = productionDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getRegistedTime() {
	    return registedTime;
	}
	public void setRegistedTime(Date registedTime) {
	    this.registedTime = registedTime;
	}
	public Long getUserId() {
	    return userId;
	}
	public void setUserId(Long userId) {
	    this.userId = userId;
	}
	public Integer getIsValid() {
	    return isValid;
	}
	public void setIsValid(Integer isValid) {
	    this.isValid = isValid;
	}
	public String getRemark() {
	    return remark;
	}
	public void setRemark(String remark) {
	    this.remark = remark;
	}
	public Integer getState() {
	    return state;
	}
	public void setState(Integer state) {
	    this.state = state;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getStamp() {
	    return stamp;
	}
	public void setStamp(Date stamp) {
	    this.stamp = stamp;
	}
	public static long getSerialVersionUID() {
	    return serialVersionUID;
	}
	public String getSupperierName() {
		return supperierName;
	}
	public void setSupperierName(String supperierName) {
		this.supperierName = supperierName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getIntype() {
		return intype;
	}
	public void setIntype(Integer intype) {
		this.intype = intype;
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
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
}
