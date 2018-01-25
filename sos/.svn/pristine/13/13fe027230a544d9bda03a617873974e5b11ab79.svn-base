package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gboss.util.CustomDateSerializer;

@Entity
@Table(name = "t_fee_sim_p")
public class SimP extends BaseEntity {
	
	private static final long serialVersionUID = 6660020025748987982L;
	
	//
	private Long id;
	//分公司
	private Integer subcoNo;
	//车载电话
	private String callLetter;
	//当前阶段, 1=入网前(SIM开通激活到4s店入网), 2=销售前(4s店入网到客户开通), 3=客户使用(客户开通到客户停用), 4=客户停用后
	private Integer period;
	//开始时间
	private Date sDate;
	//结束时间
	private Date eDate;
	//天数
	private Integer days;
	//累计通话时长(分钟)
	private Integer voiceTime;
	//累计流量(kb)
	private Long data;
	//标记, 0=未同步, 1=已调用 
	private Integer flag;
	//操作时间
	private Date stamp;
	 
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "subco_no")
	public Integer getSubcoNo() {
		return subcoNo;
	}
	public void setSubcoNo(Integer subcoNo) {
		this.subcoNo = subcoNo;
	}
	@Column(name = "call_letter")
	public String getCallLetter() {
		return callLetter;
	}
	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}
	@Column(name = "period")
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	@Column(name = "s_date")
	public Date getsDate() {
		return sDate;
	}
	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}
	@Column(name = "e_date")
	public Date geteDate() {
		return eDate;
	}
	public void seteDate(Date eDate) {
		this.eDate = eDate;
	}
	@Column(name = "days")
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	@Column(name = "voice_time")
	public Integer getVoiceTime() {
		return voiceTime;
	}
	public void setVoiceTime(Integer voiceTime) {
		this.voiceTime = voiceTime;
	}
	@Column(name = "data")
	public Long getData() {
		return data;
	}
	public void setData(Long data) {
		this.data = data;
	}
	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
}
