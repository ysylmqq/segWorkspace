package com.gboss.pojo.web;

import com.gboss.pojo.Collectionbk;
import com.gboss.pojo.Customerbk;
import com.gboss.pojo.FeeInfobk;
import com.gboss.pojo.Unitbk;
import com.gboss.pojo.Vehiclebk;

/**
 * @Package:com.gboss.pojo.web
 * @ClassName:Oldview
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-7-14 上午10:20:32
 */
public class Oldview {

	private Vehiclebk vehicle;// 车辆信息
	private Customerbk customer;// 客户信息
	private Unitbk unit;// 车台信息
	private Collectionbk collection;// 托收资料信息
	private FeeInfobk serviceInfo;// 服务费计费信息
	private FeeInfobk simfeeInfo;// SIM卡计费信息
	private FeeInfobk insuranceInfo;// 保险计费信息
	private FeeInfobk webgisInfo;// 网上查车计费信息
	private String unittype;
	private String model;

	public Vehiclebk getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehiclebk vehicle) {
		this.vehicle = vehicle;
	}

	public Customerbk getCustomer() {
		return customer;
	}

	public void setCustomer(Customerbk customer) {
		this.customer = customer;
	}

	public Unitbk getUnit() {
		return unit;
	}

	public void setUnit(Unitbk unit) {
		this.unit = unit;
	}

	public Collectionbk getCollection() {
		return collection;
	}

	public void setCollection(Collectionbk collection) {
		this.collection = collection;
	}

	public FeeInfobk getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(FeeInfobk serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public FeeInfobk getSimfeeInfo() {
		return simfeeInfo;
	}

	public void setSimfeeInfo(FeeInfobk simfeeInfo) {
		this.simfeeInfo = simfeeInfo;
	}

	public FeeInfobk getInsuranceInfo() {
		return insuranceInfo;
	}

	public void setInsuranceInfo(FeeInfobk insuranceInfo) {
		this.insuranceInfo = insuranceInfo;
	}

	public FeeInfobk getWebgisInfo() {
		return webgisInfo;
	}

	public void setWebgisInfo(FeeInfobk webgisInfo) {
		this.webgisInfo = webgisInfo;
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

}
