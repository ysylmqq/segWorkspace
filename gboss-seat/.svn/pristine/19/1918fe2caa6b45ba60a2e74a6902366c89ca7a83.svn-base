package cc.chinagps.seat.bean.table;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MarkCompanyTablePK implements Serializable {
	private static final long serialVersionUID = -1272708908272967956L;
	@Column(name = "markerId")
	private Long id;
	@Column(name = "subco_no")
	private BigInteger companyNo;
	public Long getId() {
		return id;
	}
	public BigInteger getCompanyNo() {
		return companyNo;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCompanyNo(BigInteger companyNo) {
		this.companyNo = companyNo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((companyNo == null) ? 0 : companyNo.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		MarkCompanyTablePK other = (MarkCompanyTablePK) obj;
		if (companyNo == null) {
			if (other.companyNo != null)
				return false;
		} else if (!companyNo.equals(other.companyNo))
			return false;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (id.longValue() != other.id.longValue()) {
			return false;
		}
		return true;
	}
}
