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
@Table(name = "t_wh_weather")
public class WhJhWeather extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;//'主键',
	private String city="";//'聚合支持的天气预报的城市名',
	private String nowWeather="";//'现在的天气',
	private String todayWeather="";//'今天的天气',
	private String futureWeather="";//'未来（6）天的天气',
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
	
	@Column(name = "city")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true) //  更新的时候 这个时间不更新
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	@Column(name = "now_weather")
	public String getNowWeather() {
		return nowWeather;
	}
	public void setNowWeather(String nowWeather) {
		this.nowWeather = nowWeather;
	}
	
	@Column(name = "today_weather")
	public String getTodayWeather() {
		return todayWeather;
	}
	public void setTodayWeather(String todayWeather) {
		this.todayWeather = todayWeather;
	}
	
	@Column(name = "future_weather")
	public String getFutureWeather() {
		return futureWeather;
	}
	public void setFutureWeather(String futureWeather) {
		this.futureWeather = futureWeather;
	}
}
