package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_fee_sim_m")
public class FeeSimM extends BaseEntity {

	public int getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(int subco_no) {
		this.subco_no = subco_no;
	}
	
	@Id
	@Column(name = "call_letter")
	@GeneratedValue(strategy = GenerationType.AUTO)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public String getCall_letter() {
		return call_letter;
	}
	public void setCall_letter(String call_letter) {
		this.call_letter = call_letter;
	}
	@Column(name = "period")
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	
	@Column(name = "month")
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	@Column(name = "days")
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	
	@Column(name = "voice_time")
	public int getVoice_time() {
		return voice_time;
	}
	public void setVoice_time(int voice_time) {
		this.voice_time = voice_time;
	}
	
	@Column(name = "data")
	public long getData() {
		return data;
	}
	public void setData(long data) {
		this.data = data;
	}
	
	@Column(name = "flag")
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	
	private static final long serialVersionUID = 6035485265453997396L;
	
	private int subco_no;//分公司编码
	private String call_letter;//车载电话
	private int period;	//阶段 当前阶段, 1=入网前(SIM开通激活到4s店入网), 2=销售前(4s店入网到客户开通), 3=客户使用(客户开通到客户停用), 4=客户停用后
	private String month;//月份
	private int days;//使用天数
	private int voice_time;//累计通话时长(分钟)
	private long data;//累计流量(kb)
	private int flag;//标记, 0=未同步, 1=已调用
	private Date stamp;//操作时间

	


}
