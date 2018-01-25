package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:CustomerAddress
 * @Description:客户收货地址实体类
 * @author:zfy
 * @date:2013-10-29 下午3:13:55
 */
@Entity
@Table(name = "t_whs_customer_address")
public class CustomerAddress extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//ID
	private Long companyId;//分公司ID
	private String companyName;//公司名称
	private String name;//姓名
	private String address;//省、市、县/区、街道地址
	private String phone;//手机号码和固话必选一个
	private String tel;//手机号码和固话必选一个
	private String email;//邮箱
	private String postcode;//邮政编码
	private Integer isDefault;//是否为默认地址
	private String remark;//备注

	public CustomerAddress() {
	}

	public CustomerAddress(Long id) {
		this.id = id;
	}

	public CustomerAddress(Long id, Long companyId, String name,
			String address, String phone, String tel, String email,
			String postcode, Integer isDefault, String remark) {
		this.id = id;
		this.companyId = companyId;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.tel = tel;
		this.email = email;
		this.postcode = postcode;
		this.isDefault = isDefault;
		this.remark = remark;
	}
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "company_id")
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "address")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "phone")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "tel")
	public String getTel() {
		return this.tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Column(name = "email")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "postcode")
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	@Column(name = "is_default")
	public Integer getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "company_name")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
