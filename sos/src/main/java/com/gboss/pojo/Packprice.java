package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Packprice
 * @Description:套餐价格实体类
 * @author:zfy
 * @date:2013-8-9 下午12:06:48
 */
@Entity
@Table(name = "t_sel_packprice")
public class Packprice extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//套餐价格ID
	private Long packId;//套餐ID
	private Integer type;//用户类型， 0 ：普通卡，1:银卡，2：金卡，3：白金卡
	private Float monthPrice;//月价格
	private Float yearPrice;//年价格

	public Packprice() {
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
	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name = "month_price")
	public Float getMonthPrice() {
		return this.monthPrice;
	}

	public void setMonthPrice(Float monthPrice) {
		this.monthPrice = monthPrice;
	}
	@Column(name = "year_price")
	public Float getYearPrice() {
		return this.yearPrice;
	}

	public void setYearPrice(Float yearPrice) {
		this.yearPrice = yearPrice;
	}

}
