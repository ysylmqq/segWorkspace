package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Channelcontacts
 * @Description:代理商联系人实体类
 * @author:zfy
 * @date:2013-8-26 上午11:56:42
 */
@Entity
@Table(name = "t_sel_channelcontacts")
public class Channelcontacts extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//联系人ID
	private Long channelId;//渠道ID
	private String name;//姓名
	private String dept;//部门
	private String postion;//职位
	private String phone;//联系电话
	private String email;//邮箱
	private String qq;//QQ
	private String remark;//备注

	public Channelcontacts() {
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
	@Column(name = "channel_id")
	public Long getChannelId() {
		return this.channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "dept")
	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	@Column(name = "postion")
	public String getPostion() {
		return this.postion;
	}

	public void setPostion(String postion) {
		this.postion = postion;
	}
	@Column(name = "phone")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "email")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "qq")
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
