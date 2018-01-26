package cc.chinagps.seat.auth;

import java.io.Serializable;

import com.googlecode.jsonplugin.annotations.JSON;


public class Organization implements Serializable {

	private static final long serialVersionUID = -731292158683514600L;
	
	private String address;
	private String companyName;
	private String companytype;
	private String phone;
	private String phone2;
	public String getAddress() {
		return address;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getCompanytype() {
		return companytype;
	}
	/**
	 * 是否是4s店
	 * @return
	 */
	@JSON(name = "s4Shop")
	public boolean is4SShop() {
		return companytype != null && companytype.equals("6"); 
	}
	/**
	 * 是否是车厂或4s店
	 * @return
	 */
	@JSON(serialize = false)
	public boolean isGarage4SShop() {
		return companytype != null && (companytype.equals("5") || companytype.equals("6")); 
	}
	public String getPhone() {
		return phone;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@JSON(name = "companyname")
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
}
