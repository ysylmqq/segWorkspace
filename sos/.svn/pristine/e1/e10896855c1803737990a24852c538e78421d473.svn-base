package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:SupplyBranch
 * @Description:总部供货合同子公司关联实体类
 * @author:zfy
 * @date:2013-8-20 下午3:52:42
 */
@Entity
@Table(name = "t_sel_supply_branch")
public class SupplyBranch extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//关联ID
	private Long supplyId;//供货合同ID
	private Long orgId;//子公司ID
	private String orgName;//子公司Name
	public SupplyBranch() {
	}
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "supply_id")
	public Long getSupplyId() {
		return this.supplyId;
	}

	public void setSupplyId(Long supplyId) {
		this.supplyId = supplyId;
	}
	@Column(name = "org_id")
	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	@Column(name = "org_name")
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	

}
