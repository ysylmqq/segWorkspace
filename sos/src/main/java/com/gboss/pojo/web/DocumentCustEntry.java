package com.gboss.pojo.web;

import com.gboss.pojo.Customer;
import com.gboss.pojo.Documents;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Vehicle;

/**
 * @Package:com.gboss.pojo.web
 * @ClassName:DocumentCustEntry
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-23 上午11:30:41
 */
public class DocumentCustEntry {

	private Long cust_id;
	private Long vehicle_id;
	private Long unit_id;
	private Customer customer;
	private Vehicle vehicle;
	private Unit unit;
	private Documents documents;
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public Documents getDocuments() {
		return documents;
	}
	public void setDocuments(Documents documents) {
		this.documents = documents;
	}

}
