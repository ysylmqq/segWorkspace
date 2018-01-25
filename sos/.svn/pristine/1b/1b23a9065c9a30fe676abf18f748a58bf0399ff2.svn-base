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
 * @ClassName:Paytxt
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-4 下午12:07:16
 */

@Entity
@Table(name = "t_fee_paytxt")
public class Paytxt extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long paytxt_id;//'托收记录ID',
	private Long subco_no;//'分公司ID',
	private Long customer_id;//'客户ID/用户ID',
	private String txt_name;//'托收文件名',
	private Integer agency;//'托收机构, 系统值3000, 1=银盛, 2=金融中心, 3=银联',
	private Integer account_type;//'银行帐号类型, 0=银行卡号, 1=存折号',
	private String bank_no;//'客户的银行编号',
	private String cust_name;//'客户的银行帐户名',
	private String idcard;//'银行帐号对应户主的身份证号码',
	private String account_no;//'客户的银行账号',
	private String contract_no;//'合同号, 其实一个应该就可以了',
	private String pay_contract_no;//'托收合同号',
	private Integer record_num;//'记录数',
	private Float fee_sum;//'费用总额',
	private Date stamp;//'时间戳',
	private Long op_id;//'操作员',
	private String pay_tag;//'托收返回码',
	private Date pay_time;//'托收返回时间',
	
	@Id
	@Column(name = "paytxt_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getPaytxt_id() {
		return paytxt_id;
	}
	public void setPaytxt_id(Long paytxt_id) {
		this.paytxt_id = paytxt_id;
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
	
	@Column(name = "txt_name")
	public String getTxt_name() {
		return txt_name;
	}
	public void setTxt_name(String txt_name) {
		this.txt_name = txt_name;
	}
	
	@Column(name = "agency")
	public Integer getAgency() {
		return agency;
	}
	public void setAgency(Integer agency) {
		this.agency = agency;
	}
	
	@Column(name = "account_type")
	public Integer getAccount_type() {
		return account_type;
	}
	public void setAccount_type(Integer account_type) {
		this.account_type = account_type;
	}
	
	@Column(name = "bank_no")
	public String getBank_no() {
		return bank_no;
	}
	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}
	
	@Column(name = "cust_name")
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	
	@Column(name = "idcard")
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	@Column(name = "account_no")
	public String getAccount_no() {
		return account_no;
	}
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	
	@Column(name = "contract_no")
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	
	@Column(name = "pay_contract_no")
	public String getPay_contract_no() {
		return pay_contract_no;
	}
	public void setPay_contract_no(String pay_contract_no) {
		this.pay_contract_no = pay_contract_no;
	}
	
	@Column(name = "record_num")
	public Integer getRecord_num() {
		return record_num;
	}
	public void setRecord_num(Integer record_num) {
		this.record_num = record_num;
	}
	
	@Column(name = "fee_sum")
	public Float getFee_sum() {
		return fee_sum;
	}
	public void setFee_sum(Float fee_sum) {
		this.fee_sum = fee_sum;
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
	
	@Column(name = "pay_tag")
	public String getPay_tag() {
		return pay_tag;
	}
	public void setPay_tag(String pay_tag) {
		this.pay_tag = pay_tag;
	}
	
	@Column(name = "pay_time")
	public Date getPay_time() {
		return pay_time;
	}
	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}

}

