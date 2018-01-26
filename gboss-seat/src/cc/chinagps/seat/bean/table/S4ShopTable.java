package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;


public class S4ShopTable implements Serializable {
	private static final long serialVersionUID = -3410589161880481689L;
	private BigInteger customerId;
	private String phone;
	private String name;
	public BigInteger getCustomerId() {
		return customerId;
	}
	public String getPhone() {
		return phone;
	}
	public String getName() {
		return name;
	}
	public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setName(String name) {
		this.name = name;
	}
}
