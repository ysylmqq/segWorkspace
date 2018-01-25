package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @Package:com.gboss.pojo
 * @ClassName:SalesPack
 * @Description:销售合同套餐实体类
 * @author:zfy
 * @date:2013-8-27 上午9:40:47
 */
@Entity
@Table(name = "t_sel_sales_pack")
public class SalesPack extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//销售套餐ID
	private Long contractId;//销售合同ID
	private String name;//销售套餐名称
	private String legend;//套餐说明
	private String remark;//备注

	public SalesPack() {
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
	@Column(name = "contract_id")
	public Long getContractId() {
		return this.contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "legend")
	public String getLegend() {
		return this.legend;
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
