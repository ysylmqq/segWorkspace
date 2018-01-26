package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_ba_driver")
public class DriverTable implements Serializable {

	private static final long serialVersionUID = -7680401409043594593L;
	
	@Id
	@Column(name = "driver_id")
	private BigInteger dirverId;
	
	@Column(name = "customer_id")
	private BigInteger customerId;
	
	@Column(name = "vehicle_id")
	private BigInteger vehicleId;
	
	@Column(name = "driver_name")
	private String name;
	
	private String phone;

	public BigInteger getDirverId() {
		return dirverId;
	}

	public BigInteger getCustomerId() {
		return customerId;
	}
	public BigInteger getVehicleId() {
		return vehicleId;
	}
	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public void setDirverId(BigInteger dirverId) {
		this.dirverId = dirverId;
	}

	public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
	}
	public void setVehicleId(BigInteger vehicleId) {
		this.vehicleId = vehicleId;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
