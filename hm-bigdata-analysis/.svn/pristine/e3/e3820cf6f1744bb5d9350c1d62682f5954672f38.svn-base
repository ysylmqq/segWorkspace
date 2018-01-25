package ldap.objectClasses;

public class CommonCompany {
	
	private String dn;
	
	private String companyno;
	
	private String companyname;//公司简称
	
	private String parentcompanyno;//父ID
	
	private String order;//排序
	
	private String companylevel;//机构层级
	
	private String opid;//机构管理员

	private String address;//地址
	
	private String time;//成立时间
	
	private String cnfullname;//中文全写
	
	private String enfullname;//英文全写

	private String companycode;//自定义编号

	private String companytype;//机构类型,1:一般部门,2:营业处,3:投资公司,4:仓库
	
	public String toString(){
		return "机构[机构名称:"+getCompanyname()+",地址:"+getAddress()+",成立时间:"+getTime()+"]";
	}
	
	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getCompanyno() {
		return companyno;
	}

	public void setCompanyno(String companyno) {
		this.companyno = companyno;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getParentcompanyno() {
		return parentcompanyno;
	}

	public void setParentcompanyno(String parentcompanyno) {
		this.parentcompanyno = parentcompanyno;
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

	public String getCompanylevel() {
		if(companylevel==null){
			return "1";
		}
		return companylevel;
	}

	public void setCompanylevel(String companylevel) {
		this.companylevel = companylevel;
	}

	public String getOpid() {
		return opid;
	}

	public void setOpid(String opid) {
		this.opid = opid;
	}

	public String getAddress() {
		if(address==null||address.equals("null")){
			return "";
		}
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTime() {
		if(time==null||time.equals("null")){
			return "";
		}
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCnfullname() {
		if(cnfullname==null||cnfullname.equals("null")){
			return "";
		}
		return cnfullname;
	}

	public void setCnfullname(String cnfullname) {
		this.cnfullname = cnfullname;
	}

	public String getEnfullname() {
		if(enfullname==null||enfullname.equals("null")){
			return "";
		}
		return enfullname;
	}

	public void setEnfullname(String enfullname) {
		this.enfullname = enfullname;
	}

	public String getCompanycode() {
		if(companycode==null||companycode.equals("null")){
			return "";
		}
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	public String getCompanytype() {
		if(companytype==null||companytype.equals("null")){
			return "1";
		}
		return companytype;
	}

	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}

}
