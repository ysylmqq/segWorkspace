package com.chinagps.center.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.chinagps.center.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Model
 * @Description:车型
 * @date:2015-11-17 下午5:30:13
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "t_ba_model")
public class Model extends BaseEntity {

	private Long id;
	private Long seriesId;// 车系ID
	private String name;// 车型名称
	private Integer is_alid = 1;// 有效性：0 有效 1 无效,默认为有效
	private Date stamp;// 最后修改时间
	private Float standart_oil;// 车型标准油耗
	private Float displacement;// 排量
	private String img;// 车型图片
	private String img1;// 备用图片
	private String car_type_year;// 车型年份
	private Integer car_type_level;// 车型级别
	private String is_turbo;// 是否涡轮增压
	private String gearbox_type;// 变速箱类型
	private Integer gearNum;// 档位
	private Integer carDoorNum;// 车门个数
	private String car_bodywork;// 车身结构
	private Integer car_long;// 车长(单位:mm)
	private Integer car_width;// 车宽(单位:mm)
	private Integer car_height;// 车高(单位:mm)
	private Float car_max_speed;// 最大速度
	private Float avg_oil;// 平均油耗
	private String remark;// 备注
	private String remark1;// 备注1
	private String remark2;// 备注2
	private Integer center_control;//是否支持中控控制 0=√, 1=*, 2=O, 3=其它, 4=支持, 5=不支持
	private Integer horn_control;//是否支持喇叭控制 
	private Integer window_control;//是否支持车窗控制
	private Integer light_control;//是否支持危险灯控制
	private Integer is_import;//是否是excel导入, 0=否, 1=是
	private Integer oil_volume;//油箱容量, 单位L, 默认50L
	

	@Id
	@Column(name = "model_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "series_id")
	public Long getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Long seriesId) {
		this.seriesId = seriesId;
	}

	@Column(name = "model_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "is_valid")
	public Integer getIs_alid() {
		return is_alid;
	}

	public void setIs_alid(Integer is_alid) {
		this.is_alid = is_alid;
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

	@Column(name = "standart_oil")
	public Float getStandart_oil() {
		return standart_oil;
	}

	public void setStandart_oil(Float standart_oil) {
		this.standart_oil = standart_oil;
	}

	@Column(name = "displacement")
	public Float getDisplacement() {
		return displacement;
	}

	public void setDisplacement(Float displacement) {
		this.displacement = displacement;
	}

	@Column(name = "img")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Column(name = "img1")
	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	@Column(name = "car_type_year")
	public String getCar_type_year() {
		return car_type_year;
	}

	public void setCar_type_year(String car_type_year) {
		this.car_type_year = car_type_year;
	}

	@Column(name = "car_type_level")
	public Integer getCar_type_level() {
		return car_type_level;
	}

	public void setCar_type_level(Integer car_type_level) {
		this.car_type_level = car_type_level;
	}

	@Column(name = "is_turbo")
	public String getIs_turbo() {
		return is_turbo;
	}

	public void setIs_turbo(String is_turbo) {
		this.is_turbo = is_turbo;
	}

	@Column(name = "gearbox_type")
	public String getGearbox_type() {
		return gearbox_type;
	}

	public void setGearbox_type(String gearbox_type) {
		this.gearbox_type = gearbox_type;
	}

	@Column(name = "gear_num")
	public Integer getGearNum() {
		return gearNum;
	}

	public void setGearNum(Integer gearNum) {
		this.gearNum = gearNum;
	}

	@Column(name = "car_door_num")
	public Integer getCarDoorNum() {
		return carDoorNum;
	}

	public void setCarDoorNum(Integer carDoorNum) {
		this.carDoorNum = carDoorNum;
	}

	@Column(name = "car_bodywork")
	public String getCar_bodywork() {
		return car_bodywork;
	}

	public void setCar_bodywork(String car_bodywork) {
		this.car_bodywork = car_bodywork;
	}

	@Column(name = "car_long")
	public Integer getCar_long() {
		return car_long;
	}

	public void setCar_long(Integer car_long) {
		this.car_long = car_long;
	}

	@Column(name = "car_width")
	public Integer getCar_width() {
		return car_width;
	}

	public void setCar_width(Integer car_width) {
		this.car_width = car_width;
	}

	@Column(name = "car_height")
	public Integer getCar_height() {
		return car_height;
	}

	public void setCar_height(Integer car_height) {
		this.car_height = car_height;
	}

	@Column(name = "car_max_speed")
	public Float getCar_max_speed() {
		return car_max_speed;
	}

	public void setCar_max_speed(Float car_max_speed) {
		this.car_max_speed = car_max_speed;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "remark1")
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	@Column(name = "remark2")
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	@Column(name = "center_control")
	public Integer getCenter_control() {
		return center_control;
	}

	public void setCenter_control(Integer center_control) {
		this.center_control = center_control;
	}
	
	@Column(name = "horn_control")
	public Integer getHorn_control() {
		return horn_control;
	}

	public void setHorn_control(Integer horn_control) {
		this.horn_control = horn_control;
	}
	
	@Column(name = "window_control")
	public Integer getWindow_control() {
		return window_control;
	}

	public void setWindow_control(Integer window_control) {
		this.window_control = window_control;
	}

	@Column(name = "avg_oil")
	public Float getAvg_oil() {
		return avg_oil;
	}

	public void setAvg_oil(Float avg_oil) {
		this.avg_oil = avg_oil;
	}

	@Column(name = "light_control")
	public Integer getLight_control() {
		return light_control;
	}

	public void setLight_control(Integer light_control) {
		this.light_control = light_control;
	}

	@Column(name = "is_import")
	public Integer getIs_import() {
		return is_import;
	}

	public void setIs_import(Integer is_import) {
		this.is_import = is_import;
	}

	@Column(name = "oil_volume")
	public Integer getOil_volume() {
		return oil_volume;
	}

	public void setOil_volume(Integer oil_volume) {
		this.oil_volume = oil_volume;
	}

	
}
