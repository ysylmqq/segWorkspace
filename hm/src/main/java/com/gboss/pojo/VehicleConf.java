package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_ba_vehicle_conf")
public class VehicleConf extends BaseEntity {

	private static final long serialVersionUID = -3515392214278844539L;
	
	private String call_letter;//呼号
	
	private int is_on;//体检功能是否打开, 0=关, 1=开, 为关时下发体检指令会返回失败
	
	private Long code1;//子系统配置, 每1位表示1个子系统, 1=有, 0=无, 从低位1到高位64依次为ABS,ESP/DCU,PEPS,TPMS,SRS,EPS,EMS,IMMO,BCM,TCU,ICM,APM
	
	private int flag;//标记, 0=未配置(初始状态, 预留), 1=已发查询, 2=已发设置, 3=设置失败, 4=设置成功
	
	private Date stamp;//操作时间
	
	private Date s_time	;//查询配置指令下发时间
	
	private Date e_time	;//设置配置指令完成时间
	
	private int send_conf_count = 0;//发送查询配置次数
	private int send_setconf_count = 0;//发送查询配置次数
	
	private Date send_queryconf;//发送查询配置指令时间
	private Date send_setconf;//发送升级指令时间
	
	@Transient
	public int getSend_setconf_count() {
		return send_setconf_count;
	}
	
	public void setSend_setconf_count(int send_setconf_count) {
		this.send_setconf_count = send_setconf_count;
	}
	
	@Transient
	public Date getSend_queryconf() {
		return send_queryconf;
	}

	public void setSend_queryconf(Date send_queryconf) {
		this.send_queryconf = send_queryconf;
	}
	
	@Transient
	public Date getSend_setconf() {
		return send_setconf;
	}

	public void setSend_setconf(Date send_setconf) {
		this.send_setconf = send_setconf;
	}

	@Transient
	public int getSend_conf_count() {
		return send_conf_count;
	}

	public void setSend_conf_count(int send_conf_count) {
		this.send_conf_count = send_conf_count;
	}
	@Id
	@Column(name="call_letter")
	public String getCall_letter() {
		return call_letter;
	}
	public void setCall_letter(String call_letter) {
		this.call_letter = call_letter;
	}
	
	@Column(name="is_on")
	public int getIs_on() {
		return is_on;
	}
	public void setIs_on(int is_on) {
		this.is_on = is_on;
	}
	
	@Column(name="code1")
	public Long getCode1() {
		return code1;
	}
	public void setCode1(Long code1) {
		this.code1 = code1;
	}
	
	@Column(name="stamp")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	@Column(name="flag")
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	@Column(name="s_time")
	public Date getS_time() {
		return s_time;
	}
	public void setS_time(Date s_time) {
		this.s_time = s_time;
	}
	
	@Column(name="e_time")
	public Date getE_time() {
		return e_time;
	}
	public void setE_time(Date e_time) {
		this.e_time = e_time;
	}
	
	


}
