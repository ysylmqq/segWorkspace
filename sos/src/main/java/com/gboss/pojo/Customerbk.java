package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gboss.util.CustomDateSerializer;

@Entity
@Table(name = "t_ba_customer_bk")
public class Customerbk extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long bkc_id;//历史记录id
	private Long customer_id;//'客户ID,跟托收相关',
	private Long subco_no;//'LDAP分公司根节点ID, 对应我们的分公司, 内部机构',
	private String subco_code;//'LDAP分公司子机构代码, 如客户入网分公司的分公司, 无填''0'', 每级2位字符',
	private String subco_name;//'分公司名称',
	private Long custco_no;//'LDAP客户根节点ID, 集团客户有效',
	private String custco_code;//'LDAP客户子机构代码, 用于集团客户车辆的归属关系, 无填''0'', 每级2位字符',
	private String customer_name;//'客户姓名/入网名',
	private Integer cust_type;//'客户类型, 0=私家车客户, 1=集团客户',
	private String address;//'联系地址',
	private Integer idtype;//'证件类型, 系统参数2010, 1=居民身份证, 2=士官证, 3=学生证, 4=驾驶证, 5=护照, 6=港澳通行证, 99=其它',
	private String id_no;//'身份证/营业执照(公司)',
	private Date id_bdate;//'身份证开始日期',
	private Date id_edate;//'身份证到期日期',
	private Date birthday;//'生日/成立日期(公司)',
	private Integer sex;//'性别/经济类型(公司), 0=男, 1=女; 经济类型, 系统值2090',
	private Integer trade;//'所属行业(网上查车行业版本), 系统值2040, 0=私家车, 1=物流车, 2=出租车, 3=混凝土',
	private String bloodtype;//'救援资料,血型',
	private String email;//'邮箱',
	private String fax;//'传真号码',
	private String service_pwd;//'服务密码, 加密要能解密',
	private Integer vip;//'VIP等级, 系统值2020, 1=普通卡, 2=银卡, 3=金卡, 4=白金卡, 99=免费',
	private String bl_no;//'经营许可证号Business license No, 从事行业运输才需要',
	private Date bl_bdate;//'经营许可证开始时间',
	private Date bl_edate;//'经营许可证到期时间',
	private String contract_no;//'归档合同号',
	private String location;//'归档位置, 不知集客所有资料是归档到一起',
	private Date archive_time;//'归档时间',
	private Integer flag;//'标志, 0=未审核, 1=审核通过, 2=删除',
	private Integer pay_model;//'付费方式, 系统值3050, 冗余',
	private Long op_id;//'操作员ID',
	private Date stamp;//'时间戳',
	private String remark;//'备注',
	
	@Id
	@Column(name = "bkc_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getBkc_id() {
		return bkc_id;
	}
	public void setBkc_id(Long bkc_id) {
		this.bkc_id = bkc_id;
	}
	
	@Column(name = "customer_id")
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	
	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "subco_code")
	public String getSubco_code() {
		return subco_code;
	}
	public void setSubco_code(String subco_code) {
		this.subco_code = subco_code;
	}
	
	@Column(name = "subco_name")
	public String getSubco_name() {
		return subco_name;
	}
	public void setSubco_name(String subco_name) {
		this.subco_name = subco_name;
	}
	
	@Column(name = "custco_no")
	public Long getCustco_no() {
		return custco_no;
	}
	public void setCustco_no(Long custco_no) {
		this.custco_no = custco_no;
	}
	
	@Column(name = "custco_code")
	public String getCustco_code() {
		return custco_code;
	}
	public void setCustco_code(String custco_code) {
		this.custco_code = custco_code;
	}
	
	@Column(name = "customer_name")
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
	@Column(name = "cust_type")
	public Integer getCust_type() {
		return cust_type;
	}
	public void setCust_type(Integer cust_type) {
		this.cust_type = cust_type;
	}
	
	@Column(name = "address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "idtype")
	public Integer getIdtype() {
		return idtype;
	}
	public void setIdtype(Integer idtype) {
		this.idtype = idtype;
	}
	
	@Column(name = "id_no")
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	
	@Column(name = "id_bdate")
	public Date getId_bdate() {
		return id_bdate;
	}
	public void setId_bdate(Date id_bdate) {
		this.id_bdate = id_bdate;
	}
	
	@Column(name = "id_edate")
	public Date getId_edate() {
		return id_edate;
	}
	public void setId_edate(Date id_edate) {
		this.id_edate = id_edate;
	}
	
	@Column(name = "birthday")
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Column(name = "sex")
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@Column(name = "trade")
	public Integer getTrade() {
		return trade;
	}
	public void setTrade(Integer trade) {
		this.trade = trade;
	}
	
	@Column(name = "bloodtype")
	public String getBloodtype() {
		return bloodtype;
	}
	public void setBloodtype(String bloodtype) {
		this.bloodtype = bloodtype;
	}
	
	@Column(name = "email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "fax")
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@Column(name = "service_pwd")
	public String getService_pwd() {
		return service_pwd;
	}
	public void setService_pwd(String service_pwd) {
		this.service_pwd = service_pwd;
	}
	
	@Column(name = "vip")
	public Integer getVip() {
		return vip;
	}
	public void setVip(Integer vip) {
		this.vip = vip;
	}
	
	@Column(name = "bl_no")
	public String getBl_no() {
		return bl_no;
	}
	public void setBl_no(String bl_no) {
		this.bl_no = bl_no;
	}
	
	@Column(name = "bl_bdate")
	public Date getBl_bdate() {
		return bl_bdate;
	}
	public void setBl_bdate(Date bl_bdate) {
		this.bl_bdate = bl_bdate;
	}
	
	@Column(name = "bl_edate")
	public Date getBl_edate() {
		return bl_edate;
	}
	public void setBl_edate(Date bl_edate) {
		this.bl_edate = bl_edate;
	}
	
	@Column(name = "contract_no")
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	
	@Column(name = "location")
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Column(name = "archive_time")
	public Date getArchive_time() {
		return archive_time;
	}
	public void setArchive_time(Date archive_time) {
		this.archive_time = archive_time;
	}
	
	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	@Column(name = "pay_model")
	public Integer getPay_model() {
		return pay_model;
	}
	public void setPay_model(Integer pay_model) {
		this.pay_model = pay_model;
	}
	
	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}
	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}
	
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
