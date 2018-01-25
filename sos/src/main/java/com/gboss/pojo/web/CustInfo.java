package com.gboss.pojo.web;

import java.util.List;

import com.gboss.pojo.Barcode;
import com.gboss.pojo.Collection;
import com.gboss.pojo.Combo;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Driver;
import com.gboss.pojo.FeeInfo;
import com.gboss.pojo.LargeCustLock;
import com.gboss.pojo.Linkman;
import com.gboss.pojo.Pack;
import com.gboss.pojo.Preload;
import com.gboss.pojo.Servicetime;
import com.gboss.pojo.Stop;
import com.gboss.pojo.UbiSales;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Vehicle;

/**
 * @Package:com.gboss.pojo.web
 * @ClassName:CustInfo
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-28 下午4:11:26
 */
public class CustInfo {

	private Long cust_id;
	private Long vehicle_id;
	private Long unit_id;
	private String opid;
	private String loginname;//登录名
	private String mobile;//APP登录手机号
	private Vehicle vehicle;// 车辆信息
	private Customer customer;// 客户信息
	private Servicetime servicetime;// 服务截止时间
	private Unit unit;// 车台信息
	private Preload sim;// sim卡信息
	private Collection collection;// 托收资料信息
	private List<Collection> collections;//用户存在多个托收资料
	private List<Linkman> custphones;// 客户多个联系电话
	private List<Barcode> barcodes;// 终端资料多个条形码
	private List<Driver> drivers;//司机资料
	private FeeInfo serviceInfo;//服务费计费信息
	private FeeInfo simfeeInfo;//SIM卡计费信息
	private FeeInfo insuranceInfo;//保险计费信息
	private FeeInfo webgisInfo;//网上查车计费信息
	private FeeInfo hmfeeInfo;//海马前装计费信息
	private Pack pack;//服务包
	private Combo combo;//套餐
	private String unittype;
	private String model;
	private String managerName;//销售员
	private List<Stop> stops;//办停信息
	private String guarantee;//担保公司
	private Long guarantee_id;//担保公司ID
	private String tbox_code;//TBOX条码
	private String sname;//4s店名称
	private String s_name;//保险公司简称
	
	//2016年4月21日17:10:46
	//UBI终端 保险销售员
	private UbiSales sales;
	public UbiSales getSales() {
		return sales;
	}
	public void setSales(UbiSales sales) {
		this.sales = sales;
	}

	private LargeCustLock largeCustLock;
	
	private String search;

	public Long getCust_id() {
		return cust_id;
	}

	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
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
	
	public String getOpid() {
		return opid;
	}

	public void setOpid(String opid) {
		this.opid = opid;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Servicetime getServicetime() {
		return servicetime;
	}

	public void setServicetime(Servicetime servicetime) {
		this.servicetime = servicetime;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}
	
	public List<Linkman> getCustphones() {
		return custphones;
	}

	public void setCustphones(List<Linkman> custphones) {
		this.custphones = custphones;
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

	public FeeInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(FeeInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public FeeInfo getWebgisInfo() {
		return webgisInfo;
	}

	public void setWebgisInfo(FeeInfo webgisInfo) {
		this.webgisInfo = webgisInfo;
	}

	public List<Barcode> getBarcodes() {
		return barcodes;
	}

	public void setBarcodes(List<Barcode> barcodes) {
		this.barcodes = barcodes;
	}

	public String getUnittype() {
		return unittype;
	}

	public void setUnittype(String unittype) {
		this.unittype = unittype;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Stop> getStops() {
		return stops;
	}

	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public LargeCustLock getLargeCustLock() {
		return largeCustLock;
	}

	public void setLargeCustLock(LargeCustLock largeCustLock) {
		this.largeCustLock = largeCustLock;
	}

	public List<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(List<Driver> drivers) {
		this.drivers = drivers;
	}

	public String getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	public Long getGuarantee_id() {
		return guarantee_id;
	}

	public void setGuarantee_id(Long guarantee_id) {
		this.guarantee_id = guarantee_id;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getTbox_code() {
		return tbox_code;
	}

	public void setTbox_code(String tbox_code) {
		this.tbox_code = tbox_code;
	}

	public FeeInfo getHmfeeInfo() {
		return hmfeeInfo;
	}

	public void setHmfeeInfo(FeeInfo hmfeeInfo) {
		this.hmfeeInfo = hmfeeInfo;
	}

	public Pack getPack() {
		return pack;
	}

	public void setPack(Pack pack) {
		this.pack = pack;
	}

	public Combo getCombo() {
		return combo;
	}

	public void setCombo(Combo combo) {
		this.combo = combo;
	}

	public Preload getSim() {
		return sim;
	}

	public void setSim(Preload sim) {
		this.sim = sim;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	
}
