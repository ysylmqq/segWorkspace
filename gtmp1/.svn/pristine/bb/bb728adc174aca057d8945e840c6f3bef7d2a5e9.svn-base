package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 * 
 * @Package:com.chinaGPS.gtmp.entity
 * @ClassName:DealerVehiclePOJO
 * @Description:终端POJO
 * @author:zfy
 * @date:2013-4-8 下午04:08:04
 */
@Component
@Scope("prototype")
public class DealerVehiclePOJO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dealerId;//经销商id
	
	private List<VehicleUnitPOJO> vehicleList;
	
	public String getDealerId() {
	    return dealerId;
	}
	public void setDealerId(String dealerId) {
	    this.dealerId = dealerId;
	}
	public List<VehicleUnitPOJO> getVehicleList() {
	    return vehicleList;
	}
	public void setVehicleList(List<VehicleUnitPOJO> vehicleList) {
	    this.vehicleList = vehicleList;
	}
}
