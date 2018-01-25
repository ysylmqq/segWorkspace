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
 * @ClassName:Collection
 * @Description:TODO
 * @author:yusongya
 * @date:2017-6-9 上午11:49:17
 */

@Entity
@Table(name = "t_wh_jhcity")
public class WhJhCity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;//'主键',
	private String city="";//'聚合支持的天气预报的城市名',
	private Date lastQueryTime;//'根据这个判断，当时是否访问，如果当天访问了，设置为当前时间，在同步数据的时候根据时间是否在当天去同步',
	private Date stamp;//'操作时间',
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "city",updatable=false)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "last_query_time",nullable=true,updatable=true,insertable=true) //更新的时候这个时间要更新
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLastQueryTime() {
		return lastQueryTime;
	}
	public void setLastQueryTime(Date lastQueryTime) {
		this.lastQueryTime = lastQueryTime;
	}
	
	
	@Column(name = "stamp",nullable=false,updatable=false,insertable=true) //  更新的时候 这个时间不更新
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}
	
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	
}
