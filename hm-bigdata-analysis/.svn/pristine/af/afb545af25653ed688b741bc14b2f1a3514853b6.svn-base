package com.hm.bigdata.entity.po;

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

/**
 * @Package:com.gboss.pojo
 * @ClassName:Vehicle
 * @Description:警情
 * @author:xiaoke
 * @date:2014-3-24 
 */
@Entity
@Table(name = "t_hm_alarm_analysis")
public class Alarm extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private int analysis_id;
	
	/**
	 * 呼号
	 */
	private String call_letter;

	/**
	 * 定位时间
	 */
	private Date gps_time;
	
	/**
	 * 是否定位，默认为:1,1表示定位,0表示没有定位
	 */
	private int is_location;
	
	/**
	 * 纬度
	 */
	private int lat;
	
	/**
	 * 纬度
	 */
	private int lng;
	
	/**
	 * 速度
	 */
	private int speed;
	
	/**
	 * 方向
	 */
	private int course;
	
	/**
	 * 总里程
	 */
	private int total_distance;
	
	/**
	 * 油耗
	 */
	private int oil;
	
	/**
	 * 油耗百分比
	 */
	private int oil_percent;
	
	/**
	 * 温度1
	 */
	private int temperature1;
	
	/**
	 * 温度2
	 */
	private int temperature2;
	
	/**
	 * 警情状态
	 */
	private String status;
	
	/**
	 * 警情类型.1侧翻,2碰撞,3主电切断
	 */
	private String analysis_type;
	
	/**
	 * 操作时间
	 */
	private Date stamp;
	
	@Id
	@Column(name = "analysis_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public int getAnalysis_id() {
		return analysis_id;
	}

	public void setAnalysis_id(int analysis_id) {
		this.analysis_id = analysis_id;
	}

	@Column(name = "call_letter")
	public String getCall_letter() {
		return call_letter;
	}

	public void setCall_letter(String call_letter) {
		this.call_letter = call_letter;
	}

	@Column(name = "gps_time")
	public Date getGps_time() {
		return gps_time;
	}

	public void setGps_time(Date gps_time) {
		this.gps_time = gps_time;
	}
	
	@Column(name = "is_location")
	public int getIs_location() {
		return is_location;
	}

	public void setIs_location(int is_location) {
		this.is_location = is_location;
	}

	@Column(name = "lat")
	public int getLat() {
		return lat;
	}

	public void setLat(int lat) {
		this.lat = lat;
	}

	@Column(name = "lng")
	public int getLng() {
		return lng;
	}

	public void setLng(int lng) {
		this.lng = lng;
	}

	@Column(name = "speed")
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Column(name = "course")
	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}

	@Column(name = "total_distance")
	public int getTotal_distance() {
		return total_distance;
	}

	public void setTotal_distance(int total_distance) {
		this.total_distance = total_distance;
	}

	@Column(name = "oil")
	public int getOil() {
		return oil;
	}

	public void setOil(int oil) {
		this.oil = oil;
	}

	@Column(name = "oil_percent")
	public int getOil_percent() {
		return oil_percent;
	}

	public void setOil_percent(int oil_percent) {
		this.oil_percent = oil_percent;
	}

	@Column(name = "temperature1")
	public int getTemperature1() {
		return temperature1;
	}

	public void setTemperature1(int temperature1) {
		this.temperature1 = temperature1;
	}

	@Column(name = "temperature2")
	public int getTemperature2() {
		return temperature2;
	}

	public void setTemperature2(int temperature2) {
		this.temperature2 = temperature2;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "analysis_type")
	public String getAnalysis_type() {
		return analysis_type;
	}

	public void setAnalysis_type(String analysis_type) {
		this.analysis_type = analysis_type;
	}

	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
}
