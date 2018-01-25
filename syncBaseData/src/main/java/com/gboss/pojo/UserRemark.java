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
/**
 * @Package:com.gboss.pojo
 * @ClassName:UserRemark
 * @Description:备忘表实体类
 * @author:zfy
 * @date:2013-11-18 上午10:00:02
 */
@Entity
@Table(name = "t_sys_user_remark")
public class UserRemark extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;//用户ID
	private String content;//内容
	private Integer isAlert;//登陆后是否提示 1：是、0：否
	private Date stamp;//时间

	public UserRemark() {
	}

	public UserRemark(Long id) {
		this.id = id;
	}

	public UserRemark(Long id, Long userId, String content,
			Integer isAlert, Date stamp) {
		this.id = id;
		this.userId = userId;
		this.content = content;
		this.isAlert = isAlert;
		this.stamp = stamp;
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
	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Column(name = "is_alert")
	public Integer getIsAlert() {
		return this.isAlert;
	}

	public void setIsAlert(Integer isAlert) {
		this.isAlert = isAlert;
	}
	 
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStamp() {
		return this.stamp;
	}

	 
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}
