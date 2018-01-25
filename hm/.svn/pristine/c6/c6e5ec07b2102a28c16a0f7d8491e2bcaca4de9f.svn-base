package ldap.objectClasses;

import java.util.List;

import com.gboss.comm.SystemConst;

public class CommonOperator {
	
	private String dn;
	
	private String opid;
	
	private String opname;//姓名
	
	private String remark;//备注
	
	private String loginname;//登录名
	
	private String userPassword;//密码
	
	private String idcard;//身份证
	
	private String jobnumber;//工号
	
	private String phone;//电话
	
	private String status;//状态,0离职1系统内部用户10客户
	
	private String mainUrl;//主页URL
	
	private String url;//具体每个系统中的主页URL
	
	private String mainModuleid;//主模块ID
	
	private String moduleid;//具体每个系统中的主模块ID
	
	private String companyname;//公司名称
	
	private List<String> companynos;//公司ID
	
	private List<String> rolecompanynos;
	
	private List<String> roleid;//权限ID
	
	private String rolename;//角色名称
	
	private String sex;//性别
	
	private String fax;//传真
	
	private String mail;//邮箱
	
	private String mobile;//手机
	
	private String post;//职位
	
	private String numberplate;//车牌号
	
	private String customerid;//客户id
	
	private String order;//排序
	
	@Override
	public String toString(){
		return "用户[用户名称:"+getOpname()+",登录名:"+getLoginname()+",工号:"+getJobnumber()+"]";
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getOpid() {
		return opid;
	}

	public void setOpid(String opid) {
		this.opid = opid;
	}

	public String getOpname() {
		return opname;
	}

	public void setOpname(String opname) {
		this.opname = opname;
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

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getIdcard() {
		if(idcard==null||idcard.equals("null")){
			return "";
		}
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getJobnumber() {
		if(jobnumber==null||jobnumber.equals("null")){
			return "";
		}
		return jobnumber;
	}

	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}

	public String getPhone() {
		if(phone==null||phone.equals("null")){
			return "";
		}
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public String getMainModuleid() {
		return mainModuleid;
	}

	public void setMainModuleid(String mainModuleid) {
		this.mainModuleid = mainModuleid;
	}

	public String getCompanyname() {
		if(companyname==null||companyname.equals("null")){
			return "";
		}
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public List<String> getCompanynos() {
		return companynos;
	}

	public void setCompanynos(List<String> companynos) {
		this.companynos = companynos;
	}

	public List<String> getRolecompanynos() {
		return rolecompanynos;
	}

	public void setRolecompanynos(List<String> rolecompanynos) {
		this.rolecompanynos = rolecompanynos;
	}

	public List<String> getRoleid() {
		return roleid;
	}

	public void setRoleid(List<String> roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getSex() {
		if(sex==null||sex.equals("null")){
			return "";
		}
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getFax() {
		if(fax==null||fax.equals("null")){
			return "";
		}
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMail() {
		if(mail==null||mail.equals("null")){
			return "";
		}
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMobile() {
		if(mobile==null||mobile.equals("null")){
			return "";
		}
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPost() {
		if(post==null||post.equals("null")){
			return "";
		}
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	public String getNumberplate() {
		return numberplate;
	}

	public void setNumberplate(String numberplate) {
		this.numberplate = numberplate;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getOrder() {
		if(order==null||order.equals("null")){
			return "0";
		}
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getUrl() {
		if(mainUrl==null||mainUrl.equals("null")){
			return "";
		}
		if(mainUrl.indexOf(SystemConst.SYSTEMID)==-1){
			return "";
		}
		String str = "<"+SystemConst.SYSTEMID+":";
		url = mainUrl.substring(mainUrl.indexOf(str)+str.length(), mainUrl.indexOf(":"+SystemConst.SYSTEMID+">"));
		return url;
	}

	public String getModuleid() {
		if(mainModuleid==null||mainModuleid.equals("null")){
			return "";
		}
		if(mainModuleid.indexOf(SystemConst.SYSTEMID)==-1){
			return "";
		}
		String str = "<"+SystemConst.SYSTEMID+":";
		moduleid = mainModuleid.substring(mainModuleid.indexOf(str)+str.length(), mainModuleid.indexOf(":"+SystemConst.SYSTEMID+">"));
		return moduleid;
	}
	
}
