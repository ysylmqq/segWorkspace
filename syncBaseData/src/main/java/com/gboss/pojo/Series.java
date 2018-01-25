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
 * @ClassName:Series
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-9 下午2:38:07
 */
@Entity
@Table(name = "t_ba_series")
public class Series extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private Long series_id;// 车系ID
	private Long brand_id;// 品牌ID
	private Long sync_id;//同步id
	private String name;// 车系名称
	private Integer is_valid= 1;// 有效性：1 有效 0 无效
	private String producer;// 厂商
	private Date stamp;// 最后操作时间
	private String remark;// 备注

	@Id
	@Column(name = "series_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getSeries_id() {
		return series_id;
	}

	public void setSeries_id(Long series_id) {
		this.series_id = series_id;
	}

	@Column(name = "brand_id")
	public Long getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}

	@Column(name = "series_name")
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

	@Column(name = "producer")
	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}
	
	@Column(name = "sync_id")
	public Long getSync_id() {
		return sync_id;
	}

	public void setSync_id(Long sync_id) {
		this.sync_id = sync_id;
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

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
