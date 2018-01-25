package bhz.sys.entity;

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
 * @Description:车辆实体表
 * @author:xiaoke
 * @date:2014-3-24 
 */
@Entity
@Table(name = "t_ba_vehicle")
public class Vehicle extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long vehicle_id;//'车辆ID',
	private Long subco_no;//分公司ID
	private String plate_no;//'车牌号码',
	private String def_no;//'客户公司自编号',
	private String second_no;//'出租车号/第二车牌, 深圳正规出租车有, 港牌',
	private Integer plate_color;//'车牌颜色, 系统值2110, 1=蓝, 2=黄, 3=黑, 4=白',
	private Integer vehicle_type;//'车辆类型, 系统值2030, 1=小型轿车',
	private Integer vehicle_status;//'车辆状态, 系统值2060, 0=正常, 1=故障, 2=维修, 3=警情',
	private Integer flag;//'资料状态, 0=快速入网,  1=未审核(补录), 2=已审核', 
	private Long brand;//'品牌',
	private Long series;//'车系',
	private Long model;//'车型',
	private String vin;//'车辆识别号码/车架号, 17位',
	private String color;//'车辆颜色',
	private String engine_no;//'发动机号',
	private Integer oil_type;//'燃油类型, 系统值2120, 1=汽油, 2=柴油, 3=天然气, 4=电, 9=其它',
	private String factory;//'生产厂家',
	private Date buy_date;//'购车日期',
	private String chassis_no;//'底盘号, 货车可能有, 预留, 有说:合格证的车架号=检验单的底盘号=行车证的车辆识别码',
	private Float buy_money;//'购买金额(元)',
	private Date register_date;//'初次登记日期',
	private Integer vload;//'载重/乘客数',
	private String vehicle_license;//'行驶证号码',
	private Date vl_bdate;//'行驶证发放日期',
	private Date vl_edate;//'行驶证有效日期',
	private String voc_no;//'车辆营运证, 营运车辆有',
	private Date voc_bdate;//'车辆营运证开始时间',
	private Date voc_edate;//'车辆营运证结束时间',
	private Long op_id;//'操作员ID',
	private Date stamp;//'操作时间',
	private String remark;//'坐席备注',
	private String remark2;//'备注2, 其他系统用',
	
	@Id
	@Column(name = "vehicle_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	
	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "plate_no")
	public String getPlate_no() {
		return plate_no;
	}
	public void setPlate_no(String plate_no) {
		this.plate_no = plate_no;
	}
	
	@Column(name = "def_no")
	public String getDef_no() {
		return def_no;
	}
	public void setDef_no(String def_no) {
		this.def_no = def_no;
	}
	
	@Column(name = "second_no")
	public String getSecond_no() {
		return second_no;
	}
	public void setSecond_no(String second_no) {
		this.second_no = second_no;
	}
	
	@Column(name = "plate_color")
	public Integer getPlate_color() {
		return plate_color;
	}
	public void setPlate_color(Integer plate_color) {
		this.plate_color = plate_color;
	}
	
	@Column(name = "vehicle_type")
	public Integer getVehicle_type() {
		return vehicle_type;
	}
	public void setVehicle_type(Integer vehicle_type) {
		this.vehicle_type = vehicle_type;
	}
	
	@Column(name = "vehicle_status")
	public Integer getVehicle_status() {
		return vehicle_status;
	}
	public void setVehicle_status(Integer vehicle_status) {
		this.vehicle_status = vehicle_status;
	}
	
	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	@Column(name = "brand")
	public Long getBrand() {
		return brand;
	}
	public void setBrand(Long brand) {
		this.brand = brand;
	}
	
	@Column(name = "series")
	public Long getSeries() {
		return series;
	}
	public void setSeries(Long series) {
		this.series = series;
	}
	
	@Column(name = "model")
	public Long getModel() {
		return model;
	}
	public void setModel(Long model) {
		this.model = model;
	}
	
	@Column(name = "vin")
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	
	@Column(name = "color")
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	@Column(name = "engine_no")
	public String getEngine_no() {
		return engine_no;
	}
	public void setEngine_no(String engine_no) {
		this.engine_no = engine_no;
	}
	
	@Column(name = "oil_type")
	public Integer getOil_type() {
		return oil_type;
	}
	public void setOil_type(Integer oil_type) {
		this.oil_type = oil_type;
	}
	
	@Column(name = "factory")
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	
	@Column(name = "buy_date")
	public Date getBuy_date() {
		return buy_date;
	}
	public void setBuy_date(Date buy_date) {
		this.buy_date = buy_date;
	}
	
	@Column(name = "chassis_no")
	public String getChassis_no() {
		return chassis_no;
	}
	public void setChassis_no(String chassis_no) {
		this.chassis_no = chassis_no;
	}
	
	@Column(name = "buy_money")
	public Float getBuy_money() {
		return buy_money;
	}
	public void setBuy_money(Float buy_money) {
		this.buy_money = buy_money;
	}
	
	@Column(name = "register_date")
	public Date getRegister_date() {
		return register_date;
	}
	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}
	
	@Column(name = "vload")
	public Integer getVload() {
		return vload;
	}
	public void setVload(Integer vload) {
		this.vload = vload;
	}
	
	@Column(name = "vehicle_license")
	public String getVehicle_license() {
		return vehicle_license;
	}
	public void setVehicle_license(String vehicle_license) {
		this.vehicle_license = vehicle_license;
	}
	
	@Column(name = "vl_bdate")
	public Date getVl_bdate() {
		return vl_bdate;
	}
	public void setVl_bdate(Date vl_bdate) {
		this.vl_bdate = vl_bdate;
	}
	
	@Column(name = "vl_edate")
	public Date getVl_edate() {
		return vl_edate;
	}
	public void setVl_edate(Date vl_edate) {
		this.vl_edate = vl_edate;
	}
	
	@Column(name = "voc_no")
	public String getVoc_no() {
		return voc_no;
	}
	public void setVoc_no(String voc_no) {
		this.voc_no = voc_no;
	}
	
	@Column(name = "voc_bdate")
	public Date getVoc_bdate() {
		return voc_bdate;
	}
	public void setVoc_bdate(Date voc_bdate) {
		this.voc_bdate = voc_bdate;
	}
	
	@Column(name = "voc_edate")
	public Date getVoc_edate() {
		return voc_edate;
	}
	public void setVoc_edate(Date voc_edate) {
		this.voc_edate = voc_edate;
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
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "remark2")
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

}
