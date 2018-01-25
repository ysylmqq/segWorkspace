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

@Entity
@Table(name="t_ba_operator")
public class Operator extends BaseEntity {
	
	private static final long serialVersionUID = 4452999427612481156L;
	private Long op_id;//操作人ID
	private String subco_no;//分公司编号
	private String op_name;//操作员姓名
	private Long customer_id;//客户ID
	private String login_name;//登录名 登录名称
	private String plate_no;//车牌号码 车牌号码,私家车可以车牌登录
	private String mobile;//手机 绑定手机号码
	private String mail;//邮箱
	private String pwd;//密码 密码, SHA-1加密
	private int sex;//性别 性别, 1=男, 2=女
	private String post_code;//邮编
	private String fax;//传真
	private String idcard;//身份证号码
	private int status;//状态
	private Date stamp;//时间戳
	
	@Id
	@Column(name="op_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getOp_id() {
		return op_id;
	}
	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}
	
	@Column(name="subco_no")
	public String getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(String subco_no) {
		this.subco_no = subco_no;
	}
	@Column(name="op_name")
	public String getOp_name() {
		return op_name;
	}
	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}
	@Column(name="customer_id")
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	@Column(name="login_name")
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	
	@Column(name="plate_no")
	public String getPlate_no() {
		return plate_no;
	}
	public void setPlate_no(String plate_no) {
		this.plate_no = plate_no;
	}
	
	@Column(name="mobile")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name="mail")
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@Column(name="pwd")
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@Column(name="sex")
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	@Column(name="post_code")
	public String getPost_code() {
		return post_code;
	}
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	
	@Column(name="fax")
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	@Column(name="idcard")
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	@Column(name="status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
}
