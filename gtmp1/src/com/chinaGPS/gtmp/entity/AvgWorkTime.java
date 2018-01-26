package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Package:com.chinaGPS.gtmp.entity
 * @ClassName:DicCommandTyp
 * @Description:
 * @author:zfy
 * @date:2013-4-22 上午10:53:11
 */
@Component
@Scope("prototype")
public class AvgWorkTime implements Serializable{
    private String  vehicleModel;  //机械类型id
    private String  modelName;  //机械类型name
    private String  aCount;//0-100
    private String  bCount;//100-200
    private String  cCount;//200-500
    private String  dCount;//500-1000
    private String  eCount;//1000-2000
    private String  fCount;//2000-3000
    private String  gCount;//3000以上
    private static final long serialVersionUID = 1L;
	public String getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	public String getaCount() {
		return aCount;
	}
	public void setaCount(String aCount) {
		this.aCount = aCount;
	}
	public String getbCount() {
		return bCount;
	}
	public void setbCount(String bCount) {
		this.bCount = bCount;
	}
	public String getcCount() {
		return cCount;
	}
	public void setcCount(String cCount) {
		this.cCount = cCount;
	}
	public String getdCount() {
		return dCount;
	}
	public void setdCount(String dCount) {
		this.dCount = dCount;
	}
	public String geteCount() {
		return eCount;
	}
	public void seteCount(String eCount) {
		this.eCount = eCount;
	}
	public String getfCount() {
		return fCount;
	}
	public void setfCount(String fCount) {
		this.fCount = fCount;
	}
	public String getgCount() {
		return gCount;
	}
	public void setgCount(String gCount) {
		this.gCount = gCount;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
    
}

