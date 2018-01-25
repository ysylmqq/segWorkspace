package ldap.objectClasses;

import java.util.List;

public class CommonRole {
	
	private String dn;
	
	private String roleid;
	
	private String rolename;
	
	private String systemid;
	
	private String remark;
	
	private String companyno;
	
	private String isadmin;
	
	private List<String> moduleids;

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompanyno() {
		return companyno;
	}

	public void setCompanyno(String companyno) {
		this.companyno = companyno;
	}

	public String getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}

	public List<String> getModuleids() {
		return moduleids;
	}

	public void setModuleids(List<String> moduleids) {
		this.moduleids = moduleids;
	}

}
