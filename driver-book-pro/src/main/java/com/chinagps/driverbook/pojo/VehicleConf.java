package com.chinagps.driverbook.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * APP 体检功能，子系统配置信息，下发配置指令
 * 2015/07/03 
 *
 */
public class VehicleConf extends BaseEntity {

	private static final long serialVersionUID = 8230376312047793818L;
	
	private String callLetter;//车载电话

	private Long code1;	//子系统配置, 每1位表示1个子系统, 1=有, 0=无, 
						// 1-64位依次为ABS,ESP,SRS,EMS,IMMO,PEPS,BCM,TCU,TPMS,TBOX,APM,ICM,EPS
	private int isOn;	//体检功能是否打开, 0=关, 1=开, 为关时下发体检指令会返回失败
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date  sTime;//下发查询时间
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date  eTime;//设置完成时间
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date stamp;
	
	private int flag;//配置指令状态  标记, 0=未配置(初始状态, 预留), 1=已发查询, 2=已发设置, 3=设置失败, 4=设置成功
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getCallLetter() {
		return callLetter;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}

	public int getIsOn() {
		return isOn;
	}

	public void setIsOn(int isOn) {
		this.isOn = isOn;
	}


	public Long getCode1() {
		return code1;
	}

	public void setCode1(Long code1) {
		this.code1 = code1;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	public Date getsTime() {
		return sTime;
	}

	public void setsTime(Date sTime) {
		this.sTime = sTime;
	}

	public Date geteTime() {
		return eTime;
	}

	public void seteTime(Date eTime) {
		this.eTime = eTime;
	}

	

}
