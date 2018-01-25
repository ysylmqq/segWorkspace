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
 * @ClassName:MidCust
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-29 下午4:07:00
 */
@Entity
@Table(name = "t_ba_mid_cust")
public class MidCust extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private Long mid_id;//'记录ID',
	private Long subco_no;//'分公司ID',
	private Integer cust_type;//'客户类型, 0=私家车客户, 1=集团客户, 2=担保公司',
	private Long customer_id;//'中间客户ID',
	private Long unit_id;//'终端ID',
	private Date stamp;//'操作时间',
	
	@Id
	@Column(name = "mid_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getMid_id() {
		return mid_id;
	}
	public void setMid_id(Long mid_id) {
		this.mid_id = mid_id;
	}
	
	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "cust_type")
	public Integer getCust_type() {
		return cust_type;
	}
	public void setCust_type(Integer cust_type) {
		this.cust_type = cust_type;
	}
	
	@Column(name = "customer_id")
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	
	@Column(name = "unit_id")
	public Long getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(Long unit_id) {
		this.unit_id = unit_id;
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

