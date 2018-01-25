package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gboss.util.CustomDateSerializer;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Brand
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-9 下午2:11:18
 */
@Entity
@Table(name = "t_ba_brand")
public class Brand extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long brand_id;// 品牌ID
	private String name;// 品牌名称
	private Integer is_valid= 1;// 有效性：1 有效 0 无效
	private Date stamp;// 时间

	@Id
	@Column(name = "brand_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}

	@Column(name = "brand_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "is_valid")
	public Integer getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(Integer is_valid) {
		this.is_valid = is_valid;
	}

	 
	@Column(name = "stamp", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}

	 
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}
