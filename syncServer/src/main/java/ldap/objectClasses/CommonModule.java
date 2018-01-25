package ldap.objectClasses;

public class CommonModule {
	
	private String dn;
	
	private String moduleid;
	
	private String modulename;
	
	private String parentmoduleid;
	
	private String isIpLimited;
	
	private String limitedIpAddr;
	
	private String allowedIpAddr;
	
	private String systemid;
	
	private String remark;
	
	private String order;
	
	private String control1;
	
	private String control2;
	
	private String modulelevel;
	
	public String toString(){
		return "模块[模块名称:"+getModulename()+",控制1:"+getControl1()+",控制2:"+getControl2()+"]";
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getModuleid() {
		return moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String getParentmoduleid() {
		return parentmoduleid;
	}

	public void setParentmoduleid(String parentmoduleid) {
		this.parentmoduleid = parentmoduleid;
	}

	public String getIsIpLimited() {
		return isIpLimited;
	}

	public void setIsIpLimited(String isIpLimited) {
		this.isIpLimited = isIpLimited;
	}

	public String getLimitedIpAddr() {
		if(limitedIpAddr==null||limitedIpAddr.equals("null")){
			return "";
		}
		return limitedIpAddr;
	}

	public void setLimitedIpAddr(String limitedIpAddr) {
		this.limitedIpAddr = limitedIpAddr;
	}

	public String getAllowedIpAddr() {
		if(allowedIpAddr==null||allowedIpAddr.equals("null")){
			return "";
		}
		return allowedIpAddr;
	}

	public void setAllowedIpAddr(String allowedIpAddr) {
		this.allowedIpAddr = allowedIpAddr;
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getRemark() {
		if(remark==null||remark.equals("null")){
			return "";
		}
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrder() {
		if(order==null||order.equals("null")||order.equals("")){
			return "0";
		}
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getControl1() {
		if(control1==null||control1.equals("null")){
			return "";
		}
		return control1;
	}

	public void setControl1(String control1) {
		this.control1 = control1;
	}

	public String getControl2() {
		if(control2==null||control2.equals("null")){
			return "";
		}
		return control2;
	}

	public void setControl2(String control2) {
		this.control2 = control2;
	}

	public String getModulelevel() {
		if(modulelevel==null){
			return "1";
		}
		return modulelevel;
	}

	public void setModulelevel(String modulelevel) {
		this.modulelevel = modulelevel;
	}

}
