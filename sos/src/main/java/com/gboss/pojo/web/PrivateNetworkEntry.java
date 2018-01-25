package com.gboss.pojo.web;

import java.util.List;

import com.gboss.pojo.Barcode;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Driver;
import com.gboss.pojo.FeeInfo;
import com.gboss.pojo.Linkman;
import com.gboss.pojo.Servicetime;

/**
 * 用于私家车客户入网页面传值到Controller的POJO类
 * 
 * @author xiaok
 * 
 */
public class PrivateNetworkEntry {

	private Long opid;// ldap中opid
	private Long cust_id;// 客户ID
	private Integer type;// 入网类型
	private String loginname;// 注册名
	private String server_pwd;// 服务密码
	private String private_pwd;//隐私密码
	private String customer_name;// 客户名称
	private String idcard;// 身份证
	private String birthday;// 生日
	private Integer sex;// 性别
	private String email;// 邮箱
	private Integer vip;// VIP等级
	private Integer state;// 入网状态
	private String fileno;// 归档合同号
	private String location;// 归档位置
	private String remark;// 备注
	private List<Linkman> custphones;// 客户多个联系电话
	private Customer customer_ts;//过户用户
	private List<Linkman> custphones_ts;//过户用户联系电话
	private Long pcustid;//过户集团客户id
	private Integer ts_type;//过户类型
	private Long vehicle_id;//车辆id
	private String number_plate;// 车牌号
	private Integer plate_color;// 车牌颜色
	private Integer vehicle_type;// 车辆类型
	private Long brand;// 品牌
	private Long series;// 车系
	private Long cartype;// 车型
	private String cartype_name;
	private String code;// 车辆识别号
	private String engine_no;// 发动机号
	private String chassis_no;// 车架号
	private String plate_no;// 底盘号
	private String factory;// 生产厂家
	private String color;// 车辆颜色
	private String buy_time;// 购车时间
	private String production_date;// 生产时间
	private String seatremark;//坐席备注
	private String driving_no;//行驶证号码
	private String grant_time;//发放日期
	private String valid_time;//有效日期
	private Long id_4s;//4s店机构ID
	private Integer insurance_id;//保险公司id
	private String is_bdate;//保单服务开始时间
	private String is_edate;//保单截止开始时间
	private Integer is_corp;//是否从公司购买商业保险, 0=否, 1=是
	private Integer is_pilfer;//是否享有综合盗抢险, 0=否, 1=是
	private String equip_code;//配置简码, 行驶证上车辆型号 
	private String vl_owner;//行驶证所有人
	private String vl_type;//行驶证车辆类型, 现阶段不知道有哪些, 都用汉字来表示
	private Integer vload;//'载重/乘客数',
	private String vl_use;//行驶证使用性质, 营运/非营运
	private Integer vl_quality;//总质量(KG)
	private Integer vl_ap_quality;//核定载质量(KG)
	private Integer vl_qt_quality;//准牵引总质量(KG)
	private String vl_vsize;//外廓尺寸(mm), 长*宽*高
	private String vl_doc_no;//档案编号
	private String vl_remark;//行驶证备注
	private String taxPayerId;//纳税人识别号

	
	private int ubi_sales_id;//UBI保险销售经理 @2016年4月19日18:02:36
	public int getUbi_sales_id() {
		return ubi_sales_id;
	}

	public void setUbi_sales_id(int ubi_sales_id) {
		this.ubi_sales_id = ubi_sales_id;
	}

	private Long unit_id;//终端id
	private String unitcode;// 终端型号
	private Long product_id;// 商品ID
	private String name;// 销售名称
	private Integer mode;// 通信模式（0数据流量1短信）
	private String call_letter;// 车载号码
	private Integer operators;// SIM卡运营商
	private Integer simtype;// SIM卡类型
	private String sim_start_time;// SIM卡开始时间
	private String manager_name;// 销售经理姓名
	private Long manager_id;// 销售经理ID
	private String branch;// 销售网点
	private Long pack_id;// 销售套餐ID
	private String tamper_code;// 防拆码
	private String special;// 特批单
	private Integer node;//网关编号
	private String time;//安装时间
	private String service_date;//开通时间
	private Integer sim_fee_cycle;//收费周期
	private String unitarea;//入网地
	private Integer tamper_box;//防拆盒
	private Integer tamper_smart;//智能防拆
	private Integer tamper_wireless;//无线防拆
	private Integer is_sz;//是否入网深圳, 1=是, 0=否
	private String imei;//imei码
	private Long unittype_id;
	private String unittype;
	private Long guarantee_id;
	private String guarantee;
	private String worker;
	private Long sim_id;
	private Long combo_id;
	private Long prePack_id;

	private List<Barcode> barcodes;// 终端资料多个条形码
	private FeeInfo serviceInfo;//服务费计费信息
	private FeeInfo simfeeInfo;//SIM卡计费信息
	private FeeInfo insuranceInfo;//保险计费信息
	private FeeInfo webgisInfo;//网上查车计费信息
	private FeeInfo preloadInfo;//网上查车计费信息
	private Servicetime servicetime;//服务截止时间
 
	private Long collection_id;// 托收资料ID
	private String account;// 银行账号
	private String collection_name;// 账号名
	private Integer agency;// 托收机构（1银联2银盛...）
	private String bank;// 银行
	private String bankcode;//银行编码
	private Integer account_type;// 账号类型（1存折2借记卡...）
	private Integer is_delivery;// 是否投递（1是2否3电子账单）
	private String addressee;// 发票收件人
	private String post_code;// 邮政编码
	private String pay_ct_no;// 托收合同号
	private String tel;//收件人联系电话
	private String province;// 投递地址省
	private String city;// 投递地址市
	private String area;// 投递地址区
	private String address;// 具体地址
	private String packids;//套餐id
	private String ac_id_no;
	
	private Long subcoNo;
	
	private List<Driver> drivers;//司机

	private String bk_type;
	private String bk_remark;
	
	public Long getOpid() {
		return opid;
	}

	public void setOpid(Long opid) {
		this.opid = opid;
	}

	public Long getCust_id() {
		return cust_id;
	}

	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getServer_pwd() {
		return server_pwd;
	}

	public void setServer_pwd(String server_pwd) {
		this.server_pwd = server_pwd;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getVip() {
		return vip;
	}

	public void setVip(Integer vip) {
		this.vip = vip;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getFileno() {
		return fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Linkman> getCustphones() {
		return custphones;
	}

	public void setCustphones(List<Linkman> custphones) {
		this.custphones = custphones;
	}

	public String getNumber_plate() {
		return number_plate;
	}

	public void setNumber_plate(String number_plate) {
		this.number_plate = number_plate;
	}

	public Integer getPlate_color() {
		return plate_color;
	}

	public void setPlate_color(Integer plate_color) {
		this.plate_color = plate_color;
	}

	public Integer getVehicle_type() {
		return vehicle_type;
	}

	public void setVehicle_type(Integer vehicle_type) {
		this.vehicle_type = vehicle_type;
	}

	public Long getBrand() {
		return brand;
	}

	public void setBrand(Long brand) {
		this.brand = brand;
	}

	public Long getSeries() {
		return series;
	}

	public void setSeries(Long series) {
		this.series = series;
	}

	public Long getCartype() {
		return cartype;
	}

	public void setCartype(Long cartype) {
		this.cartype = cartype;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEngine_no() {
		return engine_no;
	}

	public void setEngine_no(String engine_no) {
		this.engine_no = engine_no;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBuy_time() {
		return buy_time;
	}

	public void setBuy_time(String buy_time) {
		this.buy_time = buy_time;
	}
	
	public String getSeatremark() {
		return seatremark;
	}

	public void setSeatremark(String seatremark) {
		this.seatremark = seatremark;
	}

	public String getUnitcode() {
		return unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public String getCall_letter() {
		return call_letter;
	}

	public void setCall_letter(String call_letter) {
		this.call_letter = call_letter;
	}

	public Integer getOperators() {
		return operators;
	}

	public void setOperators(Integer operators) {
		this.operators = operators;
	}

	public Integer getSimtype() {
		return simtype;
	}

	public void setSimtype(Integer simtype) {
		this.simtype = simtype;
	}

	public String getSim_start_time() {
		return sim_start_time;
	}

	public void setSim_start_time(String sim_start_time) {
		this.sim_start_time = sim_start_time;
	}

	public String getManager_name() {
		return manager_name;
	}

	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}

	public Long getManager_id() {
		return manager_id;
	}

	public void setManager_id(Long manager_id) {
		this.manager_id = manager_id;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Long getPack_id() {
		return pack_id;
	}

	public void setPack_id(Long pack_id) {
		this.pack_id = pack_id;
	}

	public String getTamper_code() {
		return tamper_code;
	}

	public void setTamper_code(String tamper_code) {
		this.tamper_code = tamper_code;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public List<Barcode> getBarcodes() {
		return barcodes;
	}

	public void setBarcodes(List<Barcode> barcodes) {
		this.barcodes = barcodes;
	}

	public Long getCollection_id() {
		return collection_id;
	}

	public void setCollection_id(Long collection_id) {
		this.collection_id = collection_id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCollection_name() {
		return collection_name;
	}

	public void setCollection_name(String collection_name) {
		this.collection_name = collection_name;
	}

	public Integer getAgency() {
		return agency;
	}

	public void setAgency(Integer agency) {
		this.agency = agency;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public Integer getAccount_type() {
		return account_type;
	}

	public void setAccount_type(Integer account_type) {
		this.account_type = account_type;
	}

	public Integer getIs_delivery() {
		return is_delivery;
	}

	public void setIs_delivery(Integer is_delivery) {
		this.is_delivery = is_delivery;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getPost_code() {
		return post_code;
	}

	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	public Long getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(Long unit_id) {
		this.unit_id = unit_id;
	}

	public String getPackids() {
		return packids;
	}

	public void setPackids(String packids) {
		this.packids = packids;
	}

	public String getChassis_no() {
		return chassis_no;
	}

	public void setChassis_no(String chassis_no) {
		this.chassis_no = chassis_no;
	}

	public String getPlate_no() {
		return plate_no;
	}

	public void setPlate_no(String plate_no) {
		this.plate_no = plate_no;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public String getPay_ct_no() {
		return pay_ct_no;
	}

	public void setPay_ct_no(String pay_ct_no) {
		this.pay_ct_no = pay_ct_no;
	}

	public Integer getNode() {
		return node;
	}

	public void setNode(Integer node) {
		this.node = node;
	}

	public Integer getSim_fee_cycle() {
		return sim_fee_cycle;
	}

	public void setSim_fee_cycle(Integer sim_fee_cycle) {
		this.sim_fee_cycle = sim_fee_cycle;
	}

	public String getUnitarea() {
		return unitarea;
	}

	public void setUnitarea(String unitarea) {
		this.unitarea = unitarea;
	}

	public FeeInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(FeeInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public FeeInfo getSimfeeInfo() {
		return simfeeInfo;
	}

	public void setSimfeeInfo(FeeInfo simfeeInfo) {
		this.simfeeInfo = simfeeInfo;
	}

	public FeeInfo getInsuranceInfo() {
		return insuranceInfo;
	}

	public void setInsuranceInfo(FeeInfo insuranceInfo) {
		this.insuranceInfo = insuranceInfo;
	}

	public FeeInfo getWebgisInfo() {
		return webgisInfo;
	}

	public void setWebgisInfo(FeeInfo webgisInfo) {
		this.webgisInfo = webgisInfo;
	}
	
	public FeeInfo getPreloadInfo() {
		return preloadInfo;
	}

	public void setPreloadInfo(FeeInfo preloadInfo) {
		this.preloadInfo = preloadInfo;
	}

	public Long getUnittype_id() {
		return unittype_id;
	}

	public void setUnittype_id(Long unittype_id) {
		this.unittype_id = unittype_id;
	}

	public String getUnittype() {
		return unittype;
	}

	public void setUnittype(String unittype) {
		this.unittype = unittype;
	}

	public String getCartype_name() {
		return cartype_name;
	}

	public void setCartype_name(String cartype_name) {
		this.cartype_name = cartype_name;
	}

	public String getBk_type() {
		return bk_type;
	}

	public void setBk_type(String bk_type) {
		this.bk_type = bk_type;
	}

	public String getBk_remark() {
		return bk_remark;
	}

	public void setBk_remark(String bk_remark) {
		this.bk_remark = bk_remark;
	}

	public String getAc_id_no() {
		return ac_id_no;
	}

	public void setAc_id_no(String ac_id_no) {
		this.ac_id_no = ac_id_no;
	}

	public Servicetime getServicetime() {
		return servicetime;
	}

	public void setServicetime(Servicetime servicetime) {
		this.servicetime = servicetime;
	}

	public Long getGuarantee_id() {
		return guarantee_id;
	}

	public void setGuarantee_id(Long guarantee_id) {
		this.guarantee_id = guarantee_id;
	}

	public String getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	public String getPrivate_pwd() {
		return private_pwd;
	}

	public void setPrivate_pwd(String private_pwd) {
		this.private_pwd = private_pwd;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public Customer getCustomer_ts() {
		return customer_ts;
	}

	public void setCustomer_ts(Customer customer_ts) {
		this.customer_ts = customer_ts;
	}

	public List<Linkman> getCustphones_ts() {
		return custphones_ts;
	}

	public void setCustphones_ts(List<Linkman> custphones_ts) {
		this.custphones_ts = custphones_ts;
	}

	public Long getPcustid() {
		return pcustid;
	}

	public void setPcustid(Long pcustid) {
		this.pcustid = pcustid;
	}

	public Integer getTs_type() {
		return ts_type;
	}

	public void setTs_type(Integer ts_type) {
		this.ts_type = ts_type;
	}

	public String getService_date() {
		return service_date;
	}

	public void setService_date(String service_date) {
		this.service_date = service_date;
	}

	public Long getSubcoNo() {
		return subcoNo;
	}

	public void setSubcoNo(Long subcoNo) {
		this.subcoNo = subcoNo;
	}

	public String getProduction_date() {
		return production_date;
	}

	public void setProduction_date(String production_date) {
		this.production_date = production_date;
	}

	public Long getSim_id() {
		return sim_id;
	}

	public void setSim_id(Long sim_id) {
		this.sim_id = sim_id;
	}

	public Long getCombo_id() {
		return combo_id;
	}

	public void setCombo_id(Long combo_id) {
		this.combo_id = combo_id;
	}

	public Long getPrePack_id() {
		return prePack_id;
	}

	public void setPrePack_id(Long prePack_id) {
		this.prePack_id = prePack_id;
	}

	public Integer getTamper_box() {
		return tamper_box;
	}

	public void setTamper_box(Integer tamper_box) {
		this.tamper_box = tamper_box;
	}

	public Integer getTamper_smart() {
		return tamper_smart;
	}

	public void setTamper_smart(Integer tamper_smart) {
		this.tamper_smart = tamper_smart;
	}

	public Integer getTamper_wireless() {
		return tamper_wireless;
	}

	public void setTamper_wireless(Integer tamper_wireless) {
		this.tamper_wireless = tamper_wireless;
	}

	public Integer getIs_sz() {
		return is_sz;
	}

	public void setIs_sz(Integer is_sz) {
		this.is_sz = is_sz;
	}
	
	public String getDriving_no() {
		return driving_no;
	}

	public void setDriving_no(String driving_no) {
		this.driving_no = driving_no;
	}

	public String getGrant_time() {
		return grant_time;
	}

	public void setGrant_time(String grant_time) {
		this.grant_time = grant_time;
	}

	public String getValid_time() {
		return valid_time;
	}

	public void setValid_time(String valid_time) {
		this.valid_time = valid_time;
	}

	public Long getId_4s() {
		return id_4s;
	}

	public void setId_4s(Long id_4s) {
		this.id_4s = id_4s;
	}

	public Integer getInsurance_id() {
		return insurance_id;
	}

	public void setInsurance_id(Integer insurance_id) {
		this.insurance_id = insurance_id;
	}

	public String getIs_bdate() {
		return is_bdate;
	}

	public void setIs_bdate(String is_bdate) {
		this.is_bdate = is_bdate;
	}

	public String getIs_edate() {
		return is_edate;
	}

	public void setIs_edate(String is_edate) {
		this.is_edate = is_edate;
	}

	public Integer getIs_corp() {
		return is_corp;
	}

	public void setIs_corp(Integer is_corp) {
		this.is_corp = is_corp;
	}

	public Integer getIs_pilfer() {
		return is_pilfer;
	}

	public void setIs_pilfer(Integer is_pilfer) {
		this.is_pilfer = is_pilfer;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public List<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(List<Driver> drivers) {
		this.drivers = drivers;
	}

	public String getEquip_code() {
		return equip_code;
	}

	public void setEquip_code(String equip_code) {
		this.equip_code = equip_code;
	}

	public String getVl_owner() {
		return vl_owner;
	}

	public void setVl_owner(String vl_owner) {
		this.vl_owner = vl_owner;
	}

	public String getVl_type() {
		return vl_type;
	}

	public void setVl_type(String vl_type) {
		this.vl_type = vl_type;
	}

	public Integer getVload() {
		return vload;
	}

	public void setVload(Integer vload) {
		this.vload = vload;
	}

	public String getVl_use() {
		return vl_use;
	}

	public void setVl_use(String vl_use) {
		this.vl_use = vl_use;
	}

	public Integer getVl_quality() {
		return vl_quality;
	}

	public void setVl_quality(Integer vl_quality) {
		this.vl_quality = vl_quality;
	}

	public Integer getVl_ap_quality() {
		return vl_ap_quality;
	}

	public void setVl_ap_quality(Integer vl_ap_quality) {
		this.vl_ap_quality = vl_ap_quality;
	}

	public Integer getVl_qt_quality() {
		return vl_qt_quality;
	}

	public void setVl_qt_quality(Integer vl_qt_quality) {
		this.vl_qt_quality = vl_qt_quality;
	}

	public String getVl_vsize() {
		return vl_vsize;
	}

	public void setVl_vsize(String vl_vsize) {
		this.vl_vsize = vl_vsize;
	}

	public String getVl_doc_no() {
		return vl_doc_no;
	}

	public void setVl_doc_no(String vl_doc_no) {
		this.vl_doc_no = vl_doc_no;
	}

	public String getVl_remark() {
		return vl_remark;
	}

	public void setVl_remark(String vl_remark) {
		this.vl_remark = vl_remark;
	}

	public String getTaxPayerId() {
		return taxPayerId;
	}

	public void setTaxPayerId(String taxPayerId) {
		this.taxPayerId = taxPayerId;
	}
	
}
