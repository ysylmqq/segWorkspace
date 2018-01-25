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
 * @ClassName:Servicetime
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-13 上午10:27:06
 */
@Entity
@Table(name = "t_fee_servicetime")
public class Servicetime extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private Long service_id;//'服务截止时间id',
	private Long subco_no;//'分公司ID',
	private Long customer_id;//'客户ID',
	private Long vehicle_id;//'车辆ID',
	private Long unit_id;//'车台ID',
	private Date service_edate;//'服务费截止时间',
	private Date sim_edate;//'SIM截止时间',
	private Date insurance_edate;//'保险截止时间',
	private Date webgis_edate;//网上查车截止时间
	private Date stamp;//'时间戳, 更新时间',
	
	@Id
	@Column(name = "service_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getService_id() {
		return service_id;
	}
	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}
	
	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "customer_id")
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	
	@Column(name = "vehicle_id")
	public Long getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	
	@Column(name = "unit_id")
	public Long getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(Long unit_id) {
		this.unit_id = unit_id;
	}
	
	@Column(name = "service_edate")
	public Date getService_edate() {
		return service_edate;
	}
	public void setService_edate(Date service_edate) {
		this.service_edate = service_edate;
	}
	
	@Column(name = "sim_edate")
	public Date getSim_edate() {
		return sim_edate;
	}
	public void setSim_edate(Date sim_edate) {
		this.sim_edate = sim_edate;
	}
	
	@Column(name = "insurance_edate")
	public Date getInsurance_edate() {
		return insurance_edate;
	}
	public void setInsurance_edate(Date insurance_edate) {
		this.insurance_edate = insurance_edate;
	}
	
	@Column(name = "webgis_edate")
	public Date getWebgis_edate() {
		return webgis_edate;
	}
	public void setWebgis_edate(Date webgis_edate) {
		this.webgis_edate = webgis_edate;
	}
	
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}

