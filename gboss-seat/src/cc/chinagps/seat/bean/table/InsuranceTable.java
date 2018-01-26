package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.googlecode.jsonplugin.annotations.JSON;

@Entity
@Table(name = "t_ba_insurance")
public class InsuranceTable implements Serializable {
	private static final long serialVersionUID = 3619565534674859530L;
	@Id
	@Column(name = "insurance_id")
	private BigInteger insuranceId;
	@Column(name = "customer_id")
	private BigInteger customerId;
	@Column(name = "vehicle_id")
	private BigInteger vehicleId;
	@Column(name = "ic_no")
	private String icNo;
	private String phone;
	@Transient
	private String name;
	private Float amount;
	@Column(name = "is_buy_tp")
	private Long buyTp;
	@JSON(serialize = false)
	public BigInteger getInsuranceId() {
		return insuranceId;
	}
	@JSON(serialize = false)
	public BigInteger getVehicleId() {
		return vehicleId;
	}
	@JSON(serialize = false)
	public BigInteger getCustomerId() {
		return customerId;
	}
	@JSON(serialize = false)
	public String getIcNo() {
		return icNo;
	}
	@JSON(serialize = false)
	public String getPhone() {
		return phone;
	}
	@JSON(name = "ic_name")
	public String getName() {
		return name;
	}
	public Float getAmount() {
		return amount;
	}
	public Long getBuyTp() {
		return buyTp;
	}
	public void setInsuranceId(BigInteger insuranceId) {
		this.insuranceId = insuranceId;
	}
	public void setVehicleId(BigInteger vehicleId) {
		this.vehicleId = vehicleId;
	}
	public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
	}
	public void setIcNo(String icNo) {
		this.icNo = icNo;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public void setBuyTp(Long buyTp) {
		this.buyTp = buyTp;
	}
}
