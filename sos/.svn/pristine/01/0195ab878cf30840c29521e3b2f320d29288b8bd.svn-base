package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Faultcodes
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-28 上午10:02:43
 * 已删除
 */
@Entity
@Table(name = "t_ba_faultcodes")
public class Faultcodes extends BaseEntity {
	
	/** 
	 * @Fields serialVersionUID : TODO 
	 */ 
	private static final long serialVersionUID = 1L;
	
	private String faultcodes;
	private String exclude;
	
	@Id
	@Column(name = "faultcodes")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String getFaultcodes() {
		return faultcodes;
	}
	public void setFaultcodes(String faultcodes) {
		this.faultcodes = faultcodes;
	}
	
	@Column(name = "exclude")
	public String getExclude() {
		return exclude;
	}
	public void setExclude(String exclude) {
		this.exclude = exclude;
	}
	
	

}

