package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Package:com.chinaGPS.gtmp.entity
 * @ClassName:DealerPOJO
 * @Description:
 * @author:zfy
 * @date:2013-4-15 下午04:06:51
 */
@Component
@Scope("prototype")
public class DealerAreaPOJO implements Serializable {
        private String id;
	private String name;
	private String pid;
	private Integer dtype;//0片区，1经销商
	private Integer isValid;//0有效，1无效
	private Date stamp;
	
	private List<DealerVehiclePOJO> dealerVehicleList;
	
	public List<DealerVehiclePOJO> getDealerVehicleList() {
	    return dealerVehicleList;
	}
	public void setDealerVehicleList(List<DealerVehiclePOJO> dealerVehicleList) {
	    this.dealerVehicleList = dealerVehicleList;
	}
	public Date getStamp() {
	    return stamp;
	}
	public void setStamp(Date stamp) {
	    this.stamp = stamp;
	}
	public String getId() {
	    return id;
	}
	public void setId(String id) {
	    this.id = id;
	}
	public String getName() {
	    return name;
	}
	public void setName(String name) {
	    this.name = name;
	}
	public String getPid() {
	    return pid;
	}
	public void setPid(String pid) {
	    this.pid = pid;
	}
	public Integer getIsValid() {
	    return isValid;
	}
	public void setIsValid(Integer isValid) {
	    this.isValid = isValid;
	}
	public Integer getDtype() {
	    return dtype;
	}
	public void setDtype(Integer dtype) {
	    this.dtype = dtype;
	}
}

