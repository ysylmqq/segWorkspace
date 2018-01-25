package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="t_ba_equip")
@Entity
public class EquipCode extends BaseEntity {

	private static final long serialVersionUID = -6918945657854483419L;
	
	@Column(name="equip_code")
	public String getEquip_code() {
		return equip_code;
	}
	
	public void setEquip_code(String equip_code) {
		this.equip_code = equip_code;
	}
	
	@Id
	@Column(name="equip_id")
	public Long getEquip_id() {
		return equip_id;
	}
	
	public void setEquip_id(Long equip_id) {
		this.equip_id = equip_id;
	}
	
	@Column(name="model_id")
	public Long getModel_id() {
		return model_id;
	}
	
	public void setModel_id(Long model_id) {
		this.model_id = model_id;
	}
	
	@Column(name="code1")
	public Long getCode1() {
		return code1;
	}
	
	public void setCode1(Long code1) {
		this.code1 = code1;
	}
	
	/**
	 * @return the subco_no
	 */
	@Column(name="subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	/**
	 * @param subco_no the subco_no to set
	 */
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	
	private String equip_code;//配置信息（数据从海马接口进）
	private Long   equip_id;//主键
	private Long   model_id;//车型Id
	private Long   subco_no;//分公司ID
	private Long   code1;//子系统配置, 每1位表示1个子系统, 1=有, 0=无, 从低位1到高位64依次为ABS,ESP/DCU,PEPS,TPMS,SRS,EPS,EMS,IMMO,BCM,TCU,ICM,APM
	
}
