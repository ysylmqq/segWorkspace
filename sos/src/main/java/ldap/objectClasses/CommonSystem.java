package ldap.objectClasses;

public class CommonSystem {
	
	private String dn;
	
	private String systemid;	//系统id
	
	private String systemname;	//系统名称

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getSystemname() {
		return systemname;
	}

	public void setSystemname(String systemname) {
		this.systemname = systemname;
	}

}
