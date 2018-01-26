package cc.chinagps.seat.auth;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.jsonplugin.annotations.JSON;

public class User {
	private String opIdString;
	private String opName;
	private String loginName;
	private String userPassword;
	private List<String> companyInfoIdList;
	private String[] companyInfoName;
	private List<CompanyInfo> companyInfoList;
	public Long getOpId() {
		return Long.parseLong(opIdString);
	}
	@JSON(serialize = false)
	public String getOpName() {
		return opName;
	}
	@JSON(name = "company")
	public List<CompanyInfo> getCompanyInfoList() {
		return companyInfoList;
	}
	@JSON(name = "login_name")
	public String getLoginName() {
		return loginName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	@JSON(name = "opid")
	public void setOpIdString(String opIdString) {
		this.opIdString = opIdString;
	}
	@JSON(name = "opname")
	public void setOpName(String opName) {
		this.opName = opName;
	}
	@JSON(name = "loginname")
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	@JSON(name = "companynos")
	public void setCompanyInfoIdList(List<String> companyInfoIdList) {
		this.companyInfoIdList = companyInfoIdList;
		setCompanyInfoList();
	}
	@JSON(name = "companyname")
	public void setCompanyInfoNames(String companyInfoNames) {
		this.companyInfoName = companyInfoNames.split(",");
		setCompanyInfoList();
	}
	private void setCompanyInfoList() {
		if (this.companyInfoIdList == null || 
				this.companyInfoIdList.size() <= 0) {
			return;
		}
		if (this.companyInfoName == null ||
				this.companyInfoName.length <= 0) {
			return;
		}
		int idLength = this.companyInfoIdList.size();
		int nameLength = this.companyInfoName.length;
		int minLength = Math.min(idLength, nameLength);
		this.companyInfoList = new ArrayList<CompanyInfo>(minLength);
		for (int i = 0; i < minLength; i++) {
			CompanyInfo companyInfo = new CompanyInfo(
					new BigInteger(companyInfoIdList.get(i)));
			companyInfo.setCompanyName(this.companyInfoName[i]);
			this.companyInfoList.add(companyInfo);
		}
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
