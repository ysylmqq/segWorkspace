package ldap.objectClasses;

public class CommonModule {
	
	private String dn;
	
	private String moduleid;	//模块id
	
	private String modulename;	//模块名称
	
	private String parentmoduleid;	//父模块id
	
	private String isIpLimited;	//是否有ip限制设置
	
	private String limitedIpAddr;	//限制ip地址 ","号分隔
	
	private String allowedIpAddr;	//允许ip地址 ","号分隔
	
	private String systemid;	//系统id
	
	private String remark;	//备注
	
	private String order;	//排序
	
	private String control1;	//页面地址
	
	private String control2;	//图标名称
	
	private String modulelevel;	//模块深度 1：主菜单 2:二级子菜单

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
		return limitedIpAddr;
	}

	public void setLimitedIpAddr(String limitedIpAddr) {
		this.limitedIpAddr = limitedIpAddr;
	}

	public String getAllowedIpAddr() {
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
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrder() {
		if(order==null){
			return "0";
		}
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getControl1() {
		return control1;
	}

	public void setControl1(String control1) {
		this.control1 = control1;
	}

	public String getControl2() {
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
