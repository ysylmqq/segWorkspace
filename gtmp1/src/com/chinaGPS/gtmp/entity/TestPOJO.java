package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 文 件 名 :VehiclePOJO.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：蒋昌兵
 * 创 建 日 期：2013-04-27
 * 描 述： t_test表
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0	
 */
@Component
@Scope("prototype")
public class TestPOJO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long testId; //主键
	
	private String unitId;//终端ID
	
	private Long testUserId;//测试人
	
	private Date testTime;//测试时间
	
	private Integer testLocation;//定位测试
	
	private Integer testAEnable;//使能A锁
	
	private Integer testADisable;//解除A锁
	
	private Integer testBEnable;//使能B锁
	
	private Integer testBDisable;//解除B锁
	
	private Integer testProtectEnable;//使能防拆保护
	
	private Integer testProtectDisable;//禁止防拆保护
	
	private Integer test_reserve1;//工况测试
	
	private Integer testResult;//测试结果
	
	private Integer testFlag;//是否通过测试
	
	private Long qaUserId;//质检人
	
	private Date qaTime;//质检时间
	
	private Integer qaResult;//质检结果
	
	private Date stamp;//最后修改时间
	
	private int hasdata;
	
	private Long vehicleId; 
	private String vehicleDef;
	private String testUserName;//测试人姓名
	private String qaUserName;//质检人姓名
	private String supperierSn;//对应字典：终端上传，T_DIC_SUPPERIER
	private Date testTime2;//测试时间  
	private Date qaTime2;//质检时间
	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Long getTestUserId() {
	    return testUserId;
	}

	public void setTestUserId(Long testUserId) {
	    this.testUserId = testUserId;
	}

	public String getVehicleDef() {
	    return vehicleDef;
	}

	public void setVehicleDef(String vehicleDef) {
	    this.vehicleDef = vehicleDef;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getTestTime() {
		return testTime;
	}

	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}

	public Integer getTestLocation() {
		return testLocation;
	}

	public void setTestLocation(Integer testLocation) {
		this.testLocation = testLocation;
	}

	public Integer getTestAEnable() {
		return testAEnable;
	}

	public void setTestAEnable(Integer testAEnable) {
		this.testAEnable = testAEnable;
	}

	public Integer getTestADisable() {
		return testADisable;
	}

	public void setTestADisable(Integer testADisable) {
		this.testADisable = testADisable;
	}

	public Integer getTestBEnable() {
		return testBEnable;
	}

	public void setTestBEnable(Integer testBEnable) {
		this.testBEnable = testBEnable;
	}

	public Integer getTestBDisable() {
		return testBDisable;
	}

	public void setTestBDisable(Integer testBDisable) {
		this.testBDisable = testBDisable;
	}

	public Integer getTestProtectEnable() {
		return testProtectEnable;
	}

	public void setTestProtectEnable(Integer testProtectEnable) {
		this.testProtectEnable = testProtectEnable;
	}

	public Integer getTestProtectDisable() {
		return testProtectDisable;
	}

	public void setTestProtectDisable(Integer testProtectDisable) {
		this.testProtectDisable = testProtectDisable;
	}

	public Integer getTestResult() {
		return testResult;
	}

	public void setTestResult(Integer testResult) {
		this.testResult = testResult;
	}

	public Long getQaUserId() {
		return qaUserId;
	}

	public void setQaUserId(Long qaUserId) {
		this.qaUserId = qaUserId;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getQaTime() {
		return qaTime;
	}

	public void setQaTime(Date qaTime) {
		this.qaTime = qaTime;
	}

	public Integer getQaResult() {
		return qaResult;
	}

	public void setQaResult(Integer qaResult) {
		this.qaResult = qaResult;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSupperierSn() {
	    return supperierSn;
	}

	public void setSupperierSn(String supperierSn) {
	    this.supperierSn = supperierSn;
	}

	public Date getTestTime2() {
	    return testTime2;
	}

	public void setTestTime2(Date testTime2) {
	    this.testTime2 = testTime2;
	}

	public Date getQaTime2() {
	    return qaTime2;
	}

	public void setQaTime2(Date qaTime2) {
	    this.qaTime2 = qaTime2;
	}

	public String getTestUserName() {
	    return testUserName;
	}

	public void setTestUserName(String testUserName) {
	    this.testUserName = testUserName;
	}

	public Long getVehicleId() {
	    return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
	    this.vehicleId = vehicleId;
	}

	public String getQaUserName() {
		return qaUserName;
	}

	public void setQaUserName(String qaUserName) {
		this.qaUserName = qaUserName;
	}

	public Integer getTest_reserve1() {
		return test_reserve1;
	}

	public void setTest_reserve1(Integer test_reserve1) {
		this.test_reserve1 = test_reserve1;
	}

	public int getHasdata() {
		return hasdata;
	}

	public void setHasdata(int hasdata) {
		this.hasdata = hasdata;
	}

	public Integer getTestFlag() {
		return testFlag;
	}

	public void setTestFlag(Integer testFlag) {
		this.testFlag = testFlag;
	}
	
	
}
