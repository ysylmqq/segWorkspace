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
 * @ClassName:Bankcode
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-4 下午3:45:29
 */
@Entity
@Table(name = "t_fee_bankcode")
public class Bankcode extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private Long bc_id;//'银行代码ID',
	private Long subco_no;//'分公司ID, 0=分公司可以用',
	private Integer agency;//'托收机构, 系统值3000, 1=银盛, 2=金融中心, 3=银联',
	private String bank_name;//'银行名称',
	private String bank_code;//'银行代码',
	private Date stamp;//'时间戳',
	
	@Id
	@Column(name = "bc_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getBc_id() {
		return bc_id;
	}
	public void setBc_id(Long bc_id) {
		this.bc_id = bc_id;
	}
	
	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "agency")
	public Integer getAgency() {
		return agency;
	}
	public void setAgency(Integer agency) {
		this.agency = agency;
	}
	
	@Column(name = "bank_name")
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	
	@Column(name = "bank_code")
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	
	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}

