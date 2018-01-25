package com.gboss.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Package:com.gboss.pojo
 * @ClassName:MaintainItems
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-27 下午3:40:18
 */
@Entity
@Table(name = "t_app_maintain_items")
public class MaintainItems extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long model;
	private String engine_oil;
	private List<Long> engine_oil_ids;
	
	private String oil_filter;
	private List<Long> oil_filter_ids;
	
	private String air_filter;
	private List<Long> air_filter_ids;
	
	private String fuel_filter;
	private List<Long> fuel_filter_ids;
	
	private String spark_plugs;
	private List<Long> spark_plugs_ids;
	
	private String oil_channels;
	private List<Long> oil_channels_ids;

	private String ac_system;
	private List<Long> ac_system_ids;
	
	private String transmission_fluid;
	private List<Long> transmission_fluid_ids;
	
	
	private String break_fluid;
	private List<Long> break_fluid_ids;
	
	
	private String break_front;
	private List<Long> break_front_ids;
	
	
	private String break_rear;
	private List<Long> break_rear_ids;
	
	
	private String belt;
	private List<Long> belt_ids;
	
	
	private String power_steering;
	private List<Long> power_steering_ids;
	
	
	private String fuel_consumption_average;
	private List<Long> fuel_consumption_average_ids;
	
	private String coolant_fluid;
	private List<Long> coolant_fluid_ids;
	

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "model")
	public Long getModel() {
		return model;
	}

	public void setModel(Long model) {
		this.model = model;
	}

	@Column(name = "engine_oil")
	public String getEngine_oil() {
		return engine_oil;
	}

	public void setEngine_oil(String engine_oil) {
		this.engine_oil = engine_oil;
	}

	@Column(name = "oil_filter")
	public String getOil_filter() {
		return oil_filter;
	}

	public void setOil_filter(String oil_filter) {
		this.oil_filter = oil_filter;
	}

	@Column(name = "air_filter")
	public String getAir_filter() {
		return air_filter;
	}

	public void setAir_filter(String air_filter) {
		this.air_filter = air_filter;
	}

	@Column(name = "fuel_filter")
	public String getFuel_filter() {
		return fuel_filter;
	}

	public void setFuel_filter(String fuel_filter) {
		this.fuel_filter = fuel_filter;
	}

	@Column(name = "spark_plugs")
	public String getSpark_plugs() {
		return spark_plugs;
	}

	public void setSpark_plugs(String spark_plugs) {
		this.spark_plugs = spark_plugs;
	}

	@Column(name = "oil_channels")
	public String getOil_channels() {
		return oil_channels;
	}

	public void setOil_channels(String oil_channels) {
		this.oil_channels = oil_channels;
	}

	@Column(name = "coolant_fluid")
	public String getCoolant_fluid() {
		return coolant_fluid;
	}

	public void setCoolant_fluid(String coolant_fluid) {
		this.coolant_fluid = coolant_fluid;
	}

	@Column(name = "ac_system")
	public String getAc_system() {
		return ac_system;
	}

	public void setAc_system(String ac_system) {
		this.ac_system = ac_system;
	}

	@Column(name = "transmission_fluid")
	public String getTransmission_fluid() {
		return transmission_fluid;
	}

	public void setTransmission_fluid(String transmission_fluid) {
		this.transmission_fluid = transmission_fluid;
	}

	@Column(name = "break_fluid")
	public String getBreak_fluid() {
		return break_fluid;
	}

	public void setBreak_fluid(String break_fluid) {
		this.break_fluid = break_fluid;
	}

	@Column(name = "break_front")
	public String getBreak_front() {
		return break_front;
	}

	public void setBreak_front(String break_front) {
		this.break_front = break_front;
	}

	@Column(name = "break_rear")
	public String getBreak_rear() {
		return break_rear;
	}

	public void setBreak_rear(String break_rear) {
		this.break_rear = break_rear;
	}

	@Column(name = "belt")
	public String getBelt() {
		return belt;
	}

	public void setBelt(String belt) {
		this.belt = belt;
	}

	@Column(name = "power_steering")
	public String getPower_steering() {
		return power_steering;
	}

	public void setPower_steering(String power_steering) {
		this.power_steering = power_steering;
	}

	@Column(name = "fuel_consumption_average")
	public String getFuel_consumption_average() {
		return fuel_consumption_average;
	}

	public void setFuel_consumption_average(String fuel_consumption_average) {
		this.fuel_consumption_average = fuel_consumption_average;
	}

	@Transient
	public List<Long> getEngine_oil_ids() {
		return engine_oil_ids;
	}

	public void setEngine_oil_ids(List<Long> engine_oil_ids) {
		this.engine_oil_ids = engine_oil_ids;
	}

	@Transient
	public List<Long> getOil_filter_ids() {
		return oil_filter_ids;
	}

	public void setOil_filter_ids(List<Long> oil_filter_ids) {
		this.oil_filter_ids = oil_filter_ids;
	}

	@Transient
	public List<Long> getAir_filter_ids() {
		return air_filter_ids;
	}

	public void setAir_filter_ids(List<Long> air_filter_ids) {
		this.air_filter_ids = air_filter_ids;
	}

	@Transient
	public List<Long> getFuel_filter_ids() {
		return fuel_filter_ids;
	}

	public void setFuel_filter_ids(List<Long> fuel_filter_ids) {
		this.fuel_filter_ids = fuel_filter_ids;
	}

	@Transient
	public List<Long> getSpark_plugs_ids() {
		return spark_plugs_ids;
	}

	public void setSpark_plugs_ids(List<Long> spark_plugs_ids) {
		this.spark_plugs_ids = spark_plugs_ids;
	}

	@Transient
	public List<Long> getOil_channels_ids() {
		return oil_channels_ids;
	}

	public void setOil_channels_ids(List<Long> oil_channels_ids) {
		this.oil_channels_ids = oil_channels_ids;
	}

	@Transient
	public List<Long> getAc_system_ids() {
		return ac_system_ids;
	}

	public void setAc_system_ids(List<Long> ac_system_ids) {
		this.ac_system_ids = ac_system_ids;
	}

	@Transient
	public List<Long> getTransmission_fluid_ids() {
		return transmission_fluid_ids;
	}

	public void setTransmission_fluid_ids(List<Long> transmission_fluid_ids) {
		this.transmission_fluid_ids = transmission_fluid_ids;
	}

	@Transient
	public List<Long> getBreak_fluid_ids() {
		return break_fluid_ids;
	}

	public void setBreak_fluid_ids(List<Long> break_fluid_ids) {
		this.break_fluid_ids = break_fluid_ids;
	}

	@Transient
	public List<Long> getBreak_front_ids() {
		return break_front_ids;
	}

	public void setBreak_front_ids(List<Long> break_front_ids) {
		this.break_front_ids = break_front_ids;
	}

	@Transient
	public List<Long> getBreak_rear_ids() {
		return break_rear_ids;
	}

	public void setBreak_rear_ids(List<Long> break_rear_ids) {
		this.break_rear_ids = break_rear_ids;
	}

	@Transient
	public List<Long> getBelt_ids() {
		return belt_ids;
	}

	public void setBelt_ids(List<Long> belt_ids) {
		this.belt_ids = belt_ids;
	}

	@Transient
	public List<Long> getPower_steering_ids() {
		return power_steering_ids;
	}

	public void setPower_steering_ids(List<Long> power_steering_ids) {
		this.power_steering_ids = power_steering_ids;
	}

	@Transient
	public List<Long> getFuel_consumption_average_ids() {
		return fuel_consumption_average_ids;
	}

	public void setFuel_consumption_average_ids(
			List<Long> fuel_consumption_average_ids) {
		this.fuel_consumption_average_ids = fuel_consumption_average_ids;
	}

	@Transient
	public List<Long> getCoolant_fluid_ids() {
		return coolant_fluid_ids;
	}

	public void setCoolant_fluid_ids(List<Long> coolant_fluid_ids) {
		this.coolant_fluid_ids = coolant_fluid_ids;
	}
	

}
