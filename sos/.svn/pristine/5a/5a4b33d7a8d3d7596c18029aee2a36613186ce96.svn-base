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
 * @ClassName:Unit
 * @Description:车台实体表
 * @author:xiaoke
 * @date:2014-3-24 
 */
@Entity
@Table(name = "t_ba_unit_bk")
public class Unitbk extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long bku_id;//历史记录ID
	private Long unit_id;//'终端ID',
	private Long subco_no;//分公司ID
	private Long customer_id;//'客户ID',
	private Long vehicle_id;//'车辆ID',
	private Long product_id;//'商品ID',
	private String product_code;//'商品型号, 对应之前的车台类型',
	private String product_name;//'终端销售名称',
	private Long unittype_id;
	private Integer mode;//'通信模式, 1=短信, 2=数据流量, 3=流量+短信  ',
	private Integer data_node;//'流量通道网关编号, 无填0',
	private Integer sms_node;//'短信通道网关编号, 无填0',
	private String call_letter;//'车载号码',
	private Integer telecom;//'SIM卡运营商, 1=移动, 2=联通, 3=电信',
	private Integer sim_type;//'SIM卡类型, 1=自带卡, 2=公司卡',
	private String attribution;//'SIM卡归属地, 意义不大',
	private Long sales_ct_id;//'销售合同ID, 无填0',
	private Long sales_id;//'销售经理ID, 无填0',
	private String sales;//'销售经理姓名',
	private Long org_id;//'销售机构ID, 营业网点, 集客可能与客户表机构不同',
	private String branch;//'销售网点名称, 集客可能与客户表机构不同',
	private Long pack_id;//'销售套餐ID',
	private String worker;//'安装电工',
	private Long worker_id;//'安装电工ID',
	private Date fix_time;//'安装时间',
	private String place;//'安装地点',
	private String tamper_code;//'防拆码',
	private String special_no;//'特批单',
	private Long op_id;//'操作员ID',
	private Date stamp;//'操作时间',
	private Integer pay_model;//'付费方式, 集团客户可能每车不同, 系统值3050, 0=托收, 1=现金, 2=转账, 3=刷卡',
	private Integer flag;//'标志, 0=正常, 1=停用, 2=用户停用,  3=欠费停用, 4=其他原因停用',
	private Date create_date;//'入网时间',
	private Date service_date;//'服务开通时间/计费时间, 停用后可能又开通, 时间会不同',
	private Date stop_date;//'停用时间',
	private String location;//'归档位置, 不知集客所有资料是归档到一起',
	private Integer reg_status;//'入网状态, 系统值2050, 0=在网, 1=离网, 2=欠费离网, 3=非入网, 4=研发测试, 5=电工测试',
	private Integer trade;//'所属行业(网上查车行业版本), 系统值2040, 0=私家车, 1=物流车, 2=出租车, 3=混凝土, 默认与客户相同, 考虑客户有多个行业情况',
	private String area;//入网地
	
	@Id
	@Column(name = "bku_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getBku_id() {
		return bku_id;
	}
	public void setBku_id(Long bku_id) {
		this.bku_id = bku_id;
	}
	
	@Column(name = "unit_id")
	public Long getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(Long unit_id) {
		this.unit_id = unit_id;
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
	
	@Column(name = "product_id")
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	
	@Column(name = "product_code")
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	
	@Column(name = "product_name")
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
	@Column(name = "mode")
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	
	@Column(name = "data_node")
	public Integer getData_node() {
		return data_node;
	}
	public void setData_node(Integer data_node) {
		this.data_node = data_node;
	}
	
	@Column(name = "sms_node")
	public Integer getSms_node() {
		return sms_node;
	}
	public void setSms_node(Integer sms_node) {
		this.sms_node = sms_node;
	}
	
	@Column(name = "call_letter")
	public String getCall_letter() {
		return call_letter;
	}
	public void setCall_letter(String call_letter) {
		this.call_letter = call_letter;
	}
	
	@Column(name = "telecom")
	public Integer getTelecom() {
		return telecom;
	}
	public void setTelecom(Integer telecom) {
		this.telecom = telecom;
	}
	
	@Column(name = "sim_type")
	public Integer getSim_type() {
		return sim_type;
	}
	public void setSim_type(Integer sim_type) {
		this.sim_type = sim_type;
	}
	
	@Column(name = "attribution")
	public String getAttribution() {
		return attribution;
	}
	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}
	
	@Column(name = "sales_ct_id")
	public Long getSales_ct_id() {
		return sales_ct_id;
	}
	public void setSales_ct_id(Long sales_ct_id) {
		this.sales_ct_id = sales_ct_id;
	}
	
	@Column(name = "sales_id")
	public Long getSales_id() {
		return sales_id;
	}
	public void setSales_id(Long sales_id) {
		this.sales_id = sales_id;
	}
	
	@Column(name = "sales")
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	
	@Column(name = "org_id")
	public Long getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}
	
	@Column(name = "branch")
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	@Column(name = "pack_id")
	public Long getPack_id() {
		return pack_id;
	}
	public void setPack_id(Long pack_id) {
		this.pack_id = pack_id;
	}
	
	@Column(name = "worker")
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	
	@Column(name = "worker_id")
	public Long getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(Long worker_id) {
		this.worker_id = worker_id;
	}
	
	@Column(name = "fix_time")
	public Date getFix_time() {
		return fix_time;
	}
	public void setFix_time(Date fix_time) {
		this.fix_time = fix_time;
	}
	
	@Column(name = "place")
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	@Column(name = "tamper_code")
	public String getTamper_code() {
		return tamper_code;
	}
	public void setTamper_code(String tamper_code) {
		this.tamper_code = tamper_code;
	}
	
	@Column(name = "special_no")
	public String getSpecial_no() {
		return special_no;
	}
	public void setSpecial_no(String special_no) {
		this.special_no = special_no;
	}
	
	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}
	public void setOp_id(Long op_id) {
		this.op_id = op_id;
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
	
	@Column(name = "pay_model")
	public Integer getPay_model() {
		return pay_model;
	}
	public void setPay_model(Integer pay_model) {
		this.pay_model = pay_model;
	}
	
	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	@Column(name = "create_date")
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	@Column(name = "service_date")
	public Date getService_date() {
		return service_date;
	}
	public void setService_date(Date service_date) {
		this.service_date = service_date;
	}
	
	@Column(name = "stop_date")
	public Date getStop_date() {
		return stop_date;
	}
	public void setStop_date(Date stop_date) {
		this.stop_date = stop_date;
	}
	
	@Column(name = "location")
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Column(name = "reg_status")
	public Integer getReg_status() {
		return reg_status;
	}
	public void setReg_status(Integer reg_status) {
		this.reg_status = reg_status;
	}
	
	@Column(name = "trade")
	public Integer getTrade() {
		return trade;
	}
	public void setTrade(Integer trade) {
		this.trade = trade;
	}
	
	@Column(name = "area")
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	@Column(name = "unittype_id")
	public Long getUnittype_id() {
		return unittype_id;
	}
	public void setUnittype_id(Long unittype_id) {
		this.unittype_id = unittype_id;
	}
	
}
