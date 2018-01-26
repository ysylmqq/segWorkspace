package cc.chinagps.seat.bean;

import cc.chinagps.seat.bean.table.DriverTable;
import cc.chinagps.seat.bean.table.InsuranceTable;
import cc.chinagps.seat.bean.table.LinkmanTable;
import cc.chinagps.seat.bean.table.S4ShopTable;

/**
 * 电话信息
 * @author Administrator
 *
 */
public class PhoneInfo {
	
	/**
	 *客户联系人电话
	 */
	public static final int TYPE_CUSTOMER_LINKMAN = 1;
	
	/**
	 * 司机电话
	 */
	public static final int TYPE_DRIVER = 2;
	
	/**
	 * 保险公司电话
	 */
	public static final int TYPE_INSURANCE_AGENT = 3;
	
	/**
	 * 4S店电话
	 */
	public static final int TYPE_4S_SHOP = 4;
	
	private int type;
	
	/**
	 * 联系人姓名
	 */
	private String name;
	private String number;
	private String linkmanType;
	private String linkmanTypeString;
	
	private PhoneInfo(int type, String name, String number) {
		this.type = type;
		this.name = name;
		this.number = number;
	}
	
	private PhoneInfo(int type, String name, String number, 
			String linkmanType, String linkmanTypeString) {
		this(type, name, number);
		this.linkmanType = linkmanType;
		this.linkmanTypeString = linkmanTypeString;
	}
	
	public static PhoneInfo newCustomerPhoneInfo(LinkmanTable linkman) {
		return new PhoneInfo(TYPE_CUSTOMER_LINKMAN, linkman.getName(), 
				linkman.getPhone(), linkman.getLinkmanType(),
				linkman.getLinkmanTypeString());
	}
	
	public static PhoneInfo newDriverPhoneInfo(DriverTable driver) {
		return new PhoneInfo(TYPE_DRIVER, driver.getName(), 
				driver.getPhone());
	}
	
	public static PhoneInfo newInsuranceAgentPhoneInfo(
			InsuranceTable insurance) {
		return new PhoneInfo(TYPE_INSURANCE_AGENT, insurance.getName(),
				insurance.getPhone());
	}
	
	public static PhoneInfo new4sShopPhoneInfo(S4ShopTable s4Shop) {
		return new PhoneInfo(TYPE_4S_SHOP, s4Shop.getName(), 
				s4Shop.getPhone());
	}

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}
	public String getLinkmanType() {
		return linkmanType;
	}
	public String getLinkmanTypeString() {
		return linkmanTypeString;
	}
	public void setType(int type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	public void setLinkmanType(String linkmanType) {
		this.linkmanType = linkmanType;
	}
	public void setLinkmanTypeString(String linkmanTypeString) {
		this.linkmanTypeString = linkmanTypeString;
	}
}
