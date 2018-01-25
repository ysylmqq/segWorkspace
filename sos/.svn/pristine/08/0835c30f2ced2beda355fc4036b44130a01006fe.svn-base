package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:PackItem
 * @Description:套餐服务项关系实体类
 * @author:zfy
 * @date:2013-8-9 下午12:04:48
 */
@Entity
@Table(name = "t_sel_pack_item")
public class PackItem extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//关联ID
	private Long packId;//套餐ID
	private Long itemId;//服务项ID
	private Integer weights;//权重
	private String remark;//服务项描述

	public PackItem() {
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
	@Column(name = "pack_id")
	public Long getPackId() {
		return this.packId;
	}

	public void setPackId(Long packId) {
		this.packId = packId;
	}
	@Column(name = "item_id")
	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	@Column(name = "weights")
	public Integer getWeights() {
		return this.weights;
	}

	public void setWeights(Integer weights) {
		this.weights = weights;
	}
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
