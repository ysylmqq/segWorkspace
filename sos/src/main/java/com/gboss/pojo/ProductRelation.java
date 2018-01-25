package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @Package:com.gboss.pojo
 * @ClassName:ProductRelation
 * @Description:内部商品关系实体类
 * @author:zfy
 * @date:2013-7-31 下午3:14:30
 */
@Entity
@Table(name = "t_sel_product_relation")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ProductRelation extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//关联ID
	private Long productId;//内部商品ID
	private Long itemId;//配件商品ID
	private String num;//用量
	private Integer version;//版本
	private Integer level;//等级，0：主料，1：替代料
	private String remark;

	public ProductRelation() {
	}

	public ProductRelation(Long id) {
		this.id = id;
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

	@Column(name = "product_Id")
	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Column(name = "item_Id")
	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	@Column(name = "num")
	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	@Column(name = "level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}
