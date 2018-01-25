package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gboss.util.CustomDateSerializer;

@Entity
@Table(name = "t_fee_sim_h")
public class SimH extends BaseEntity {

	private static final long serialVersionUID = 5851853865187257907L;
	//分公司
	private Integer subcoNo;
	//车载电话
	private String callLetter;
	//年月, YYYYMM
	private String month;
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
	
	@Column(name = "subco_no")
	public Integer getSubcoNo() {
		return subcoNo;
	}
	public void setSubcoNo(Integer subcoNo) {
		this.subcoNo = subcoNo;
	}
	@Id
	@Column(name = "call_letter")
	public String getCallLetter() {
		return callLetter;
	}
	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}
	@Column(name = "month")
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
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
