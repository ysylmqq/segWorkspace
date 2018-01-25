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
 * @ClassName:Model
 * @Description:车型
 * @author:bzhang
 * @date:2014-3-25 下午3:55:03
 */
@Entity
@Table(name = "t_ba_model")
public class Model extends BaseEntity {

	private static final long serialVersionUID = 1L;

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
	private Integer center_control;
	private Integer horn_control;
	private Integer window_control;
	private Integer oil_volume;
	
	private Long code1;//配置信息 0110101010101
	private String equip_code;//配置简码
	
	@Column(name = "code1")
	public Long getCode1() {
		return code1;
	}

	public void setCode1(Long code1) {
		this.code1 = code1;
	}
	
	@Column(name = "equip_code")
	public String getEquip_code() {
		return equip_code;
	}

	public void setEquip_code(String equip_code) {
		this.equip_code = equip_code;
	}

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

	@Override
	@Column(name = "stamp", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}

	@Override
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

	@Column(name = "avg_oil")
	public Float getAve_oil() {
		return avg_oil;
	}

	public void setAve_oil(Float ave_oil) {
		this.avg_oil = ave_oil;
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

	@Column(name = "oil_volume")
	public Integer getOil_volume() {
		return oil_volume;
	}

	public void setOil_volume(Integer oil_volume) {
		this.oil_volume = oil_volume;
	}

	
}
