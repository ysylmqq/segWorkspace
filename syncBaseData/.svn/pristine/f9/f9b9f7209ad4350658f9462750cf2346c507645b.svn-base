package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_fee_sim_p")
public class FeeSimP extends BaseEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name = "subco_no")
	public int getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(int subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "call_letter")
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
	
	@Column(name = "s_date")
	public Date getS_date() {
		return s_date;
	}
	public void setS_date(Date s_date) {
		this.s_date = s_date;
	}
	
	@Column(name = "e_date")
	public Date getE_date() {
		return e_date;
	}
	public void setE_date(Date e_date) {
		this.e_date = e_date;
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
	public int getData() {
		return data;
	}
	public void setData(int data) {
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
	private static final long serialVersionUID = 4413262683884869655L;
	
	private int id;//主键
	private int subco_no;//分公司
	private String call_letter;//车载电话
	private int period; //阶段
	private Date s_date;//开始时间
	private Date e_date;//结束时间
	private int days;//使用天数
	private int voice_time;//语音通话时间分钟
	private int data;//流量kb
	private int flag;//标记, 0=未同步, 1=已调用
	private Date stamp;//操作时间
	
	

}
