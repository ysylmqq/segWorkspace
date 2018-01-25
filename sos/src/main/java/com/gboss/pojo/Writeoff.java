package com.gboss.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Writeoff
 * @Description:销帐实体类
 * @author:zfy
 * @date:2013-8-7 上午10:39:36
 */
@Entity
@Table(name = "t_fee_writeoff")
public class Writeoff extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//销账id
	private String writeoffno;//销账单号
    private Long manager_id;//销售经理用户id
    private String manager_name;//销售经理用户name
	private Long channel_id;//渠道id
	private Float off_amount;//本次销账金额
	private Float last_balance;//上次结余金额
	private Float balance;//本次结余金额
	private String ticketno;//转账票号
	private Date time;//销账时间
	private String annex;//附件
	private Long user_id;//操作员id
	private String user_name;//操作员name
	private Date stamp;//操作时间
	private Long org_id;//销售经理的部门id
	private String org_name;//销售经理的部门name
	private Long company_id;//销售经理的公司id
	private String company_name;//销售经理的公司name
	private String remark;
	
	private List<WriteoffDetails> writeoffDetails;//销账详细信息
	
	public Writeoff() {
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
	
	@Column(name = "manager_id")
	public Long getManager_id() {
		return manager_id;
	}
	public void setManager_id(Long manager_id) {
		this.manager_id = manager_id;
	}
	
	@Column(name = "channel_id")
	public Long getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(Long channel_id) {
		this.channel_id = channel_id;
	}
	
	@Column(name = "off_amount")
	public Float getOff_amount() {
		return off_amount;
	}
	public void setOff_amount(Float off_amount) {
		this.off_amount = off_amount;
	}
	
	@Column(name = "last_balance")
	public Float getLast_balance() {
		return last_balance;
	}
	public void setLast_balance(Float last_balance) {
		this.last_balance = last_balance;
	}
	
	@Column(name = "balance")
	public Float getBalance() {
		return balance;
	}
	public void setBalance(Float balance) {
		this.balance = balance;
	}
	
	@Column(name = "ticketno")
	public String getTicketno() {
		return ticketno;
	}
	public void setTicketno(String ticketno) {
		this.ticketno = ticketno;
	}
	
	@Column(name = "time",nullable=false,updatable=true,insertable=true)
    @Temporal(TemporalType.TIMESTAMP)
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	@Column(name = "annex")
	public String getAnnex() {
		return annex;
	}
	public void setAnnex(String annex) {
		this.annex = annex;
	}
	
	@Column(name = "user_id")
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	@Column(name = "org_id")
	public Long getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}
	
	@Column(name = "company_id")
	public Long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}
	
	@Transient
	public List<WriteoffDetails> getWriteoffDetails() {
		return writeoffDetails;
	}
	public void setWriteoffDetails(List<WriteoffDetails> writeoffDetails) {
		this.writeoffDetails = writeoffDetails;
	}
	@Column(name = "writeoff_no")
	public String getWriteoffno() {
		return writeoffno;
	}

	public void setWriteoffno(String writeoffno) {
		this.writeoffno = writeoffno;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "manager_name")
	public String getManager_name() {
		return manager_name;
	}

	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	@Column(name = "user_name")
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	@Column(name = "org_name")
	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	@Column(name = "company_name")
	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	
}
