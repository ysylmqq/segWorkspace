package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * The persistent class for the t_ba_linkman database table.
 * 
 */
@Entity
@Table(name="t_ba_linkman")
public class LinkmanTable implements Serializable {

	private static final long serialVersionUID = -1519946921451251141L;

	@Id
	@GeneratedValue
	@Column(name = "linkman_id")
	private BigInteger id;
	
	@NotNull
	@Column(name = "customer_id")
	private BigInteger customerId;
	@NotNull
	@Column(name = "vehicle_id")
	private BigInteger vehicleId;
	
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "linkman")
	private String name;
	
	@NotNull
	@Size(min = 1, max = 200)
	private String phone;

	public static final String LINKMANTYPE_FIRST_CONTACT = "1";
	@Column(name = "linkman_type")
	private String linkmanType;
	/**
	 * 联系人类型转义后字符串
	 */
	@Transient
	private String linkmanTypeString;
	
	public BigInteger getId() {
		return id;
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
	public String getLinkmanType() {
		return linkmanType;
	}
	public String getLinkmanTypeString() {
		return linkmanTypeString;
	}
	public void setId(BigInteger id) {
		this.id = id;
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
	public void setLinkmanType(String linkmanType) {
		this.linkmanType = linkmanType;
	}
	public void setLinkmanTypeString(String linkmanTypeString) {
		this.linkmanTypeString = linkmanTypeString;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkmanTable other = (LinkmanTable) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}