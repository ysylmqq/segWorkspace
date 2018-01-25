package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Insurer
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-12-24 上午10:52:57
 */
@Entity
@Table(name = "t_ba_ic")
public class Insurer extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private Integer ic_id;//'保险公司ID',
	private Long sync_id;//同步id
	private String s_name;//'名称简称, 下拉选择名称',
	private String c_name;//'中文名称',
	private String e_name;//'英文名称(第二语言), 预留, 国外地名',
	private String py;//'汉语拼音',
	private String ab;//'缩写(首字母)',
	private String tel;//'客服电话',
	private String tel_400;//'400电话',
	private String tel_800;//'800电话',
	private Integer sno;//'排序号',
	private String remark;//'备注',
	private Date stamp;
	
	@Id
	@Column(name = "ic_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getIc_id() {
		return ic_id;
	}
	public void setIc_id(Integer ic_id) {
		this.ic_id = ic_id;
	}
	
	@Column(name = "s_name")
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	
	@Column(name = "c_name")
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	
	@Column(name = "e_name")
	public String getE_name() {
		return e_name;
	}
	public void setE_name(String e_name) {
		this.e_name = e_name;
	}
	
	@Column(name = "py")
	public String getPy() {
		return py;
	}
	public void setPy(String py) {
		this.py = py;
	}
	
	@Column(name = "ab")
	public String getAb() {
		return ab;
	}
	public void setAb(String ab) {
		this.ab = ab;
	}
	
	@Column(name = "tel")
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Column(name = "tel_400")
	public String getTel_400() {
		return tel_400;
	}
	public void setTel_400(String tel_400) {
		this.tel_400 = tel_400;
	}
	
	@Column(name = "tel_800")
	public String getTel_800() {
		return tel_800;
	}
	public void setTel_800(String tel_800) {
		this.tel_800 = tel_800;
	}
	
	@Column(name = "sno")
	public Integer getSno() {
		return sno;
	}
	public void setSno(Integer sno) {
		this.sno = sno;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	@Column(name = "sync_id")
	public Long getSync_id() {
		return sync_id;
	}
	public void setSync_id(Long sync_id) {
		this.sync_id = sync_id;
	}
	
	

}

