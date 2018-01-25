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
 * @ClassName:PaymentItem
 * @Description:TODO
 * @author:bzhang
 * @date:2014-8-22 上午10:26:06
 */
@Entity
@Table(name = "t_fee_payment_item")
public class PaymentItem extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long payment_item_id;//缴费子ID
	private Long payment_id;//缴费ID
	private Long subco_no;//分公司ID
	private Long customer_id;//客户ID
	private Integer feetype_id;//计费项目类型, 系统值3100, 1=服务费, 2=SIM卡, 3=保险, 4=网上查车, 5=终端产品
	private float ac_amount;//应收费用总额, 现金缴费, 促销活动
	private float real_amount;//实收金额
	private Date stamp;//时间戳
	private Long op_id;//操作员id
	
	
	@Id
	@Column(name = "payment_item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getPayment_item_id() {
		return payment_item_id;
	}
	
	public void setPayment_item_id(Long payment_item_id) {
		this.payment_item_id = payment_item_id;
	}
	
	@Column(name = "payment_id")
	public Long getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(Long payment_id) {
		this.payment_id = payment_id;
	}
	
	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "customer_id")
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	
	@Column(name = "feetype_id")
	public Integer getFeetype_id() {
		return feetype_id;
	}
	public void setFeetype_id(Integer feetype_id) {
		this.feetype_id = feetype_id;
	}
	
	@Column(name = "ac_amount")
	public float getAc_amount() {
		return ac_amount;
	}
	public void setAc_amount(float ac_amount) {
		this.ac_amount = ac_amount;
	}
	
	@Column(name = "real_amount")
	public float getReal_amount() {
		return real_amount;
	}
	public void setReal_amount(float real_amount) {
		this.real_amount = real_amount;
	}
	
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}
	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}
	
}

