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
 * @ClassName:Serviceitem
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-6 下午3:38:46
 */
@Entity
@Table(name = "t_fee_service_item")
public class Serviceitem extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private Long item_id;//'服务项ID',
	private String item_code;//'服务项编号',
	private String item_name;//'服务项名称',
	private Integer feetype_id;//'计费类型, 1=服务费, 2=SIM卡, 3=盗抢险, 4=网上查车',
	private Integer flag;//'是否新服务包有效, 1=有效, 0=否(过期)',
	private Float price;//'价格, 不定义为0',
	private Long op_id;//'操作员ID',
	private Date stamp;//'操作时间',
	private String remark;//'服务项备注',
	
	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getItem_id() {
		return item_id;
	}
	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}
	
	@Column(name = "item_code")
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	
	@Column(name = "item_name")
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	
	@Column(name = "feetype_id")
	public Integer getFeetype_id() {
		return feetype_id;
	}
	public void setFeetype_id(Integer feetype_id) {
		this.feetype_id = feetype_id;
	}
	
	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	@Column(name = "price")
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}
	public void setOp_id(Long op_id) {
		this.op_id = op_id;
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