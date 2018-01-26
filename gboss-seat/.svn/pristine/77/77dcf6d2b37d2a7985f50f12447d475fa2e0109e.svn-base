package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.googlecode.jsonplugin.annotations.JSON;

@Entity
@Table(name = "t_fee_paytxt")
public class FeePaytxtTable implements Serializable {
	private static final long serialVersionUID = 411913826855061159L;
	
	@Id
	@Column(name = "paytxt_id")
	private BigInteger paytxtId;
	@Column(name = "account_no")
	private String accountNo;
	@Column(name = "cust_name")
	private String custName;
	@Column(name = "agency")
	private String agency;
	@Transient
	private String agencyName;
	@Transient
	private String address;
	@Transient
	private String bank;
	@Transient
	private Timestamp stamp;
	@Column(name = "pay_tag")
	private String payTag;
	@Transient
	private String payTagString;
	@Column(name = "customer_id")
	private BigInteger customerId;
	@Transient
	private double amount;
	@Transient
	private Timestamp printTime;
	@JSON(serialize = false)
	public BigInteger getPaytxtId() {
		return paytxtId;
	}
	@JSON(name = "account_no")
	public String getAccountNo() {
		return accountNo;
	}
	@JSON(name = "cust_name")
	public String getCustName() {
		return custName;
	}
	@JSON(serialize = false)
	public String getAgency() {
		return agency;
	}
	@JSON(name = "agency")
	public String getAgencyName() {
		return agencyName;
	}
	public String getAddress() {
		return address;
	}
	public String getBank() {
		return bank;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getStamp() {
		return stamp;
	}
	@JSON(serialize = false)
	public String getPayTag() {
		return payTag;
	}
	@JSON(name = "pay_tag")
	public String getPayTagString() {
		return payTagString;
	}
	@JSON(serialize = false)
	public BigInteger getCustomerId() {
		return customerId;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss", name="print_time")
	public Timestamp getPrintTime() {
		return printTime;
	}
	public double getAmount() {
		return amount;
	}
	public void setPaytxtId(BigInteger paytxtId) {
		this.paytxtId = paytxtId;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public void setStamp(Timestamp stamp) {
		this.stamp = stamp;
	}
	public void setPayTag(String payTag) {
		this.payTag = payTag;
	}
	public void setPayTagString(String payTagString) {
		this.payTagString = payTagString;
	}
	public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public void setPrintTime(Timestamp printTime) {
		this.printTime = printTime;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
