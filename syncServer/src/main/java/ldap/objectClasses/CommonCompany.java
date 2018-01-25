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
	
	private String coordinates;//经纬度
	
	private String phone;//电话(维修电话) 主营，资质
	//电话需要分类：维修电话、预约电话
	//图片（原图、缩略图），地址，备注
	
	private String phone2;//电话2(预约电话)
	
	private String phone3;//电话3(来电电话)
	
	private String major;
	
	private String aptitude;
	
	private String image;
	
	private String image2;
	
	private String remark;
	
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

	public String getCoordinates() {
		if(coordinates==null||coordinates.equals("null")){
			return "";
		}
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
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

	public String getPhone2() {
		if(phone2==null||phone2.equals("null")){
			return "";
		}
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getMajor() {
		if(major==null||major.equals("null")){
			return "";
		}
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getAptitude() {
		if(aptitude==null||aptitude.equals("null")){
			return "";
		}
		return aptitude;
	}

	public void setAptitude(String aptitude) {
		this.aptitude = aptitude;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
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

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

}
