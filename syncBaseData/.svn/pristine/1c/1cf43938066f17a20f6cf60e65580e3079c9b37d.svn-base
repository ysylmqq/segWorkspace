package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="t_ba_equip")
@Entity
public class Equipcode extends BaseEntity {

	private static final long serialVersionUID = 2041202204944456519L;
	private long 	equip_id;	//车型配置ID
	private long 	subco_no=201L;	//分公司ID 默认201
	private int 	model_id=0;	//车型ID 默认0
	private String 	equip_code;	//车型配置简码
	private long 	code1;		//子系统配置, 每1位表示1个子系统, 1=有, 0=无, 从低位1到高位64依次为ABS,ESP/DCU,PEPS,TPMS,SRS,EPS,EMS,IMMO,BCM,TCU,ICM,APM
	private String 	equip_name;	//配置名称
	private int 	is_media;	//是否有音响主机, 0=无, 1=有
	
	@Id
	@Column(name="equip_id")
	public long getEquip_id() {
		return equip_id;
	}
	public void setEquip_id(long equip_id) {
		this.equip_id = equip_id;
	}
	
	@Column(name="subco_no")
	public long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name="model_id")
	public int getModel_id() {
		return model_id;
	}
	public void setModel_id(int model_id) {
		this.model_id = model_id;
	}
	
	@Column(name="equip_code")
	public String getEquip_code() {
		return equip_code;
	}
	public void setEquip_code(String equip_code) {
		this.equip_code = equip_code;
	}
	
	@Column(name="code1")
	public long getCode1() {
		return code1;
	}
	public void setCode1(long code1) {
		this.code1 = code1;
	}
	
	@Column(name="equip_name")
	public String getEquip_name() {
		return equip_name;
	}
	public void setEquip_name(String equip_name) {
		this.equip_name = equip_name;
	}
	
	@Column(name="is_media")
	public int getIs_media() {
		return is_media;
	}
	public void setIs_media(int is_media) {
		this.is_media = is_media;
	}
}
