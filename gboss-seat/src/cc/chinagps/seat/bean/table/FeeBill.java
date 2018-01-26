package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: FeeBill
 *
 */
@Entity
@Table(name="t_fee_bill")
public class FeeBill implements Serializable {
	private static final long serialVersionUID = 1580366993639475338L;
	@Id
	@Column(name = "bill_id")
	private String id;
	private Float amount;
	@Column(name = "customer_id")
	private BigInteger customerId;
	public String getId() {
		return id;
	}
	public Float getAmount() {
		return this.amount;
	}
	public BigInteger getCustomerId() {
		return customerId;
	}
	public void setId(String id) {
		this.id = id;
	}   
	public void setAmount(Float amount) {
		this.amount = amount;
	}
    public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
	}
}
