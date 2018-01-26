package cc.chinagps.seat.auth;

import java.math.BigInteger;

import com.gboss.comm.SystemConst;
import com.googlecode.jsonplugin.annotations.JSON;

public class CompanyInfo {
	public static final CompanyInfo HEADQUARTERS = new CompanyInfo(
			new BigInteger(SystemConst.HEADQUARTERS));
	
	private BigInteger companyNo;
	private String companyName;
	public CompanyInfo() {
	}
	public CompanyInfo(BigInteger companyNo) {
		this.companyNo = companyNo;
	}
	@JSON(name = "companyno")
	public BigInteger getCompanyNo() {
		return companyNo;
	}
	@JSON(name = "companyname")
	public String getCompanyName() {
		return companyName;
	}
	@JSON(name = "companyno")
	public void setCompanyNo(BigInteger companyNo) {
		this.companyNo = companyNo;
	}
	@JSON(name = "companyname")
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((companyNo == null) ? 0 : companyNo.hashCode());
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
		CompanyInfo other = (CompanyInfo) obj;
		if (companyNo == null) {
			if (other.companyNo != null)
				return false;
		} else if (!companyNo.equals(other.companyNo))
			return false;
		return true;
	}
}
