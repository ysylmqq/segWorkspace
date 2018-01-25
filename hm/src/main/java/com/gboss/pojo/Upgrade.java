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
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gboss.util.CustomDateSerializer;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Upgrade
 * @Description:TODO
 * @author:bzhang
 * @date:2015-01-26 下午2:11:18
 */
@Entity
@Table(name = "t_st_unit_upgrade")
public class Upgrade extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String call_letter;
	private String ip;
	private Integer port;
	private String cur_ver;
	private String ug_ver;
	private Integer flag=0;
	private Date s_time;
	private Date e_time;
	private Date stamp;// 时间
	private Date put_waitDate ;//加入等待队列时间；
	private Date send_searchcar;//发送查车时间
	private Date send_upgrade;//发送升级指令时间
    private int send_total;//查车指令下发次数
	
	
	private String upgradeName;
	private String vin;//车架号
	private String barcode;//T_BOX条码
	
	private String[] call_letters;
	private Integer is_all =0;
	
	@Id
	@Column(name = "call_letter")
	public String getCall_letter() {
		return call_letter;
	}

	public void setCall_letter(String call_letter) {
		this.call_letter = call_letter;
	}

	@Column(name = "ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "port")
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Column(name = "cur_ver")
	public String getCur_ver() {
		return cur_ver;
	}

	public void setCur_ver(String cur_ver) {
		this.cur_ver = cur_ver;
	}

	@Column(name = "ug_ver")
	public String getUg_ver() {
		return ug_ver;
	}

	public void setUg_ver(String ug_ver) {
		this.ug_ver = ug_ver;
	}

	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name = "s_time")
	public Date getS_time() {
		return s_time;
	}

	public void setS_time(Date s_time) {
		this.s_time = s_time;
	}

	@Column(name = "e_time")
	public Date getE_time() {
		return e_time;
	}

	public void setE_time(Date e_time) {
		this.e_time = e_time;
	}

	@Override
	@Column(name = "stamp", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}

	@Override
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	@Transient
	public String[] getCall_letters() {
		return call_letters;
	}

	public void setCall_letters(String[] call_letters) {
		this.call_letters = call_letters;
	}
	
	@Transient
	public Integer getIs_all() {
		return is_all;
	}
	
	@Transient
	public Date getSend_searchcar() {
		return send_searchcar;
	}

	public void setSend_searchcar(Date send_searchcar) {
		this.send_searchcar = send_searchcar;
	}
	
	@Transient
	public Date getSend_upgrade() {
		return send_upgrade;
	}

	public void setSend_upgrade(Date send_upgrade) {
		this.send_upgrade = send_upgrade;
	}

	public void setIs_all(Integer is_all) {
		this.is_all = is_all;
	}
	
	@Column(name = "upgrade_name")
	public String getUpgradeName() {
		return upgradeName;
	}

	public void setUpgradeName(String upgradeName) {
		this.upgradeName = upgradeName;
	}
	@Transient
	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}
	@Transient
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	@Transient
	public Date getPut_waitDate() {
		return put_waitDate;
	}

	public void setPut_waitDate(Date put_waitDate) {
		this.put_waitDate = put_waitDate;
	}
    @Transient
	public int getSend_total() {
		return send_total;
	}

	public void setSend_total(int send_total) {
		this.send_total = send_total;
	}
	
	
	
}
