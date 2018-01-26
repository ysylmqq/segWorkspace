package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: CollectionTable
 *
 */
@Entity
@Table(name="t_ba_collection")
public class CollectionTable implements Serializable {

	private static final long serialVersionUID = 5361319089392278534L;
	
	@Id
	@Column(name = "collection_id")
	private BigInteger collectionId;
	@Column(name = "customer_id")
	private BigInteger customerId;
	private String bank;
	private String address;
	private Timestamp stamp;
	public BigInteger getCollectionId() {
		return collectionId;
	}
	public BigInteger getCustomerId() {
		return customerId;
	}
	public String getBank() {
		return bank;
	}
	public String getAddress() {
		return address;
	}
	public Timestamp getStamp() {
		return stamp;
	}
	public void setCollectionId(BigInteger collectionId) {
		this.collectionId = collectionId;
	}
	public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setStamp(Timestamp stamp) {
		this.stamp = stamp;
	}
}
