package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Onduty
 * @Description:电工实际值班明细(已删除)
 * @author:bzhang
 * @date:2014-3-26 上午11:13:56
 */
@Entity
@Table(name = "t_ba_onduty")
public class Onduty extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Long user_id;//员工id
	
	private Date time;//时间
	
	private String status;//1：上班 2：休息 3：值班 4：出车 5：晚上出车 6：晚上值班 7：假期
	
	private Integer is_busy;//电工状态（0空闲1繁忙）

	@Id
	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	@Id
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "is_busy")
	public Integer getIs_busy() {
		return is_busy;
	}

	public void setIs_busy(Integer is_busy) {
		this.is_busy = is_busy;
	}
	
	
	

	
	
	
}

