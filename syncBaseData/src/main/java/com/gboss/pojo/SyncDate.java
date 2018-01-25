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
 * @ClassName:SyncDate
 * @Description:同步数据
 * @author:bzhang
 * @date:2014-3-25 下午3:55:03
 */
@Entity
@Table(name = "t_if_sync")
public class SyncDate extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long sync_id;
	
	private String if_name;// 
	
	private Long subco_no;
	
	private Date begin_time;
	
	private Date end_time;
	
	private Date stamp;// 最后修改时间
	

	@Id
	@Column(name = "sync_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getSync_id() {
		return sync_id;
	}

	public void setSync_id(Long sync_id) {
		this.sync_id = sync_id;
	}
	
	@Column(name = "if_name")
	public String getIf_name() {
		return if_name;
	}

	public void setIf_name(String if_name) {
		this.if_name = if_name;
	}

	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}

	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}

	@Column(name = "begin_time")
	public Date getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}

	@Column(name = "end_time")
	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
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
