package com.chinagps.driverbook.pojo;


/**
 * 客户信息POJO
 * 
 * @author Ben
 *
 */
public class Customer extends BaseEntity {
	private static final long serialVersionUID = 1546298824602648637L;

	/** 客户ID */
	private String customerId;
	/** 客户姓名 */
	private String customerName;
	/** 性别（0：男 1：女） */
	private Integer sex;
	/** 车辆ID */
//	private String vehicleId;
	/** 服务密码 */
	private String servicePwd;
	/** 新服务密码 */
	private String newServicePwd;
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

//	public String getVehicleId() {
//		return vehicleId;
//	}
//
//	public void setVehicleId(String vehicleId) {
//		this.vehicleId = vehicleId;
//	}

	public String getServicePwd() {
		return servicePwd;
	}

	public void setServicePwd(String servicePwd) {
		this.servicePwd = servicePwd;
	}

	public String getNewServicePwd() {
		return newServicePwd;
	}

	public void setNewServicePwd(String newServicePwd) {
		this.newServicePwd = newServicePwd;
	}

}
