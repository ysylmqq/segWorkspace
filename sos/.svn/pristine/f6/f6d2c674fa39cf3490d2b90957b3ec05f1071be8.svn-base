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
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gboss.util.CustomDateSerializer;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Policy
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-29 下午3:19:04
 */
@Entity
@Table(name = "t_ba_insurance")
public class Policy extends BaseEntity {

	/** 
	 * @Fields serialVersionUID : TODO 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	
	private Long insurance_id;//保险单ID
	private Long subco_no;//分公司根节点ID
	private Long customer_id;//客户ID
	private String customer_name;//客户名/入网名
	private String policy_no;//保单号
	private Long vehicle_id;//车辆ID
	private String plate_no;//车牌号码
	private Integer ic_no;//投保公司编号, 系统参数3030
	private int vehicle_price;//新车价格(单位元) 
	private float amount;//保险金额(单位万元)
	private float fee;//保险费用,不一定为托收金额
	private Integer is_buy_tp;//是否已购盗抢险, 0=否, 1=是
	private Integer payment;//缴费方式, 0=年, 1=月
	private Integer is_contain;//是否包含套餐内, 0=否, 1=是
	private String policyholder;//投保人(意外险)
	private String ph_id_no;//投保人身份证(意外险)
	private Date birthday;//生日, 可从身份证号码里提取
	private String phone;//投保人电话
	private String tel;//固定电话
	private Date is_bdate;//保单服务开始时间
	private Date is_edate;//保单服务截止时间
	private String address;//联系地址
	private String sales;//销售经理
	private Long sales_id;//销售经理ID
	private Integer is_print;//是否已经打印, 0=否, 1=是
	private Long print_op_id;//打印操作员ID
	private Date print_stamp;//打印日期
	private Long op_id;//录入操作员ID
	private Long unit_id;//车台id
	private Date stamp;
	private Integer status;//状态, 0=办停, 1=待审,  2=已审核, 9=作废
	private Integer ratio = 0;//赔付比例,百分比
	private String cust_id_no;//客户身份证号码
	private Integer is_transfer = 0;//默认未过户
	private Long transferl_id;
	
	
	private String color;//车辆颜色
	private String plate_code;//车型/厂牌型号
	private String engine_no;//发动机号
	private String vin;//车辆识别号码/车架号, 17位
	private String gps_code;//车台类型/GPS型号
	private String barcode;//车台序列号/机身编号
	private Date register_date;//初次登记日期
	private Date fix_time;//安装时间
	private String remark;//备注

	
	
	@Id
	@Column(name = "insurance_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getInsurance_id() {
		return insurance_id;
	}
	public void setInsurance_id(Long insurance_id) {
		this.insurance_id = insurance_id;
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
	
	@Column(name = "customer_name")
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
	@Column(name = "policy_no")
	public String getPolicy_no() {
		return policy_no;
	}
	public void setPolicy_no(String policy_no) {
		this.policy_no = policy_no;
	}
	
	@Column(name = "vehicle_id")
	public Long getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	
	@Column(name = "plate_no")
	public String getPlate_no() {
		return plate_no;
	}
	public void setPlate_no(String plate_no) {
		this.plate_no = plate_no;
	}
	
	@Column(name = "ic_no")
	public Integer getIc_no() {
		return ic_no;
	}
	public void setIc_no(Integer ic_no) {
		this.ic_no = ic_no;
	}
	
	@Column(name = "vehicle_price")
	public int getVehicle_price() {
		return vehicle_price;
	}
	public void setVehicle_price(int vehicle_price) {
		this.vehicle_price = vehicle_price;
	}
	
	@Column(name = "amount")
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	@Column(name = "fee")
	public float getFee() {
		return fee;
	}
	public void setFee(float fee) {
		this.fee = fee;
	}
	
	@Column(name = "is_buy_tp")
	public Integer getIs_buy_tp() {
		return is_buy_tp;
	}
	public void setIs_buy_tp(Integer is_buy_tp) {
		this.is_buy_tp = is_buy_tp;
	}
	
	@Column(name = "payment")
	public Integer getPayment() {
		return payment;
	}
	public void setPayment(Integer payment) {
		this.payment = payment;
	}
	
	@Column(name = "is_contain")
	public Integer getIs_contain() {
		return is_contain;
	}
	public void setIs_contain(Integer is_contain) {
		this.is_contain = is_contain;
	}
	
	@Column(name = "policyholder")
	public String getPolicyholder() {
		return policyholder;
	}
	public void setPolicyholder(String policyholder) {
		this.policyholder = policyholder;
	}
	
	@Column(name = "ph_id_no")
	public String getPh_id_no() {
		return ph_id_no;
	}
	public void setPh_id_no(String ph_id_no) {
		this.ph_id_no = ph_id_no;
	}
	
	@Column(name = "birthday")
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "tel")
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Column(name = "is_bdate")
	public Date getIs_bdate() {
		return is_bdate;
	}
	public void setIs_bdate(Date is_bdate) {
		this.is_bdate = is_bdate;
	}
	
	@Column(name = "is_edate")
	public Date getIs_edate() {
		return is_edate;
	}
	public void setIs_edate(Date is_edate) {
		this.is_edate = is_edate;
	}
	
	@Column(name = "address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "sales")
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	
	@Column(name = "sales_id")
	public Long getSales_id() {
		return sales_id;
	}
	public void setSales_id(Long sales_id) {
		this.sales_id = sales_id;
	}
	
	@Column(name = "is_print")
	public Integer getIs_print() {
		return is_print;
	}
	public void setIs_print(Integer is_print) {
		this.is_print = is_print;
	}
	
	@Column(name = "print_op_id")
	public Long getPrint_op_id() {
		return print_op_id;
	}
	public void setPrint_op_id(Long print_op_id) {
		this.print_op_id = print_op_id;
	}
	
	@Column(name = "print_stamp")
	public Date getPrint_stamp() {
		return print_stamp;
	}
	public void setPrint_stamp(Date print_stamp) {
		this.print_stamp = print_stamp;
	}
	
	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}
	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}
	
	@Column(name = "ratio")
	public Integer getRatio() {
		return ratio;
	}
	public void setRatio(Integer ratio) {
		this.ratio = ratio;
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
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "cust_id_no")
	public String getCust_id_no() {
		return cust_id_no;
	}
	public void setCust_id_no(String cust_id_no) {
		this.cust_id_no = cust_id_no;
	}
	
	@Column(name = "is_transfer")
	public Integer getIs_transfer() {
		return is_transfer;
	}
	public void setIs_transfer(Integer is_transfer) {
		this.is_transfer = is_transfer;
	}
	
	@Column(name = "model")
	public String getPlate_code() {
		return plate_code;
	}
	public void setPlate_code(String plate_code) {
		this.plate_code = plate_code;
	}
	
	@Column(name = "engine_no")
	public String getEngine_no() {
		return engine_no;
	}
	public void setEngine_no(String engine_no) {
		this.engine_no = engine_no;
	}
	
	@Column(name = "vin")
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	
	@Column(name = "unittype")
	public String getGps_code() {
		return gps_code;
	}
	public void setGps_code(String gps_code) {
		this.gps_code = gps_code;
	}
	
	@Column(name = "unit_sn")
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	@Column(name = "register_date")
	public Date getRegister_date() {
		return register_date;
	}
	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}
	
	@Column(name = "fix_time")
	public Date getFix_time() {
		return fix_time;
	}
	public void setFix_time(Date fix_time) {
		this.fix_time = fix_time;
	}
	
	@Column(name = "unit_id")
	public Long getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(Long unit_id) {
		this.unit_id = unit_id;
	}
	
	@Column(name = "color")
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Transient
	public Long getTransferl_id() {
		return transferl_id;
	}
	public void setTransferl_id(Long transferl_id) {
		this.transferl_id = transferl_id;
	}
	

}

