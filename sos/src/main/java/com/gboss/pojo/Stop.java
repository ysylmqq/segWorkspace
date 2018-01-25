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

/**
 * @Package:com.gboss.pojo
 * @ClassName:Stop
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-7-16 下午5:08:44
 */
@Entity
@Table(name = "t_ba_stop")
public class Stop extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private Long id;//'办停ID',
	private Long subco_no;//'分公司, 对应我们的分公司, 内部机构',
	private Long customer_id;//'客户ID',
	private Long vehicle_id;//'车辆ID',
	private Long unit_id;//'车台ID'
	private Integer type;//'办停类型, 1=服务费, 2=SIM卡, 3=盗抢险, 4=网上查车',
	private Integer type_id;//'停止使用原因类型id',
	private Integer is_tear;//'是否拆机,0=否,1=是',
	private Long user_id;//'操作员ID',
	private Long org_id;//'营业处ID',
	private Date stamp;//'操作时间',
	private String remark;//'备注',
	private Date fee_sedate;//'服务截止时间'
	private Long fi_bk_id;//'历史计费信息id'
	private Integer flag;//'停用标记, 1=停用, 2=用户营业处办理停用, 3=欠费三个月中心催缴停用, 4=其他原因停用'
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "customer_id")
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	
	@Column(name = "vehicle_id")
	public Long getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	
	@Column(name = "unit_id")
	public Long getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(Long unit_id) {
		this.unit_id = unit_id;
	}
	
	@Column(name = "type")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "type_id")
	public Integer getType_id() {
		return type_id;
	}
	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}
	
	@Column(name = "is_tear")
	public Integer getIs_tear() {
		return is_tear;
	}
	public void setIs_tear(Integer is_tear) {
		this.is_tear = is_tear;
	}
	
	@Column(name = "user_id")
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	@Column(name = "org_id")
	public Long getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}
	
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
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
	
	@Column(name = "fee_sedate")
	public Date getFee_sedate() {
		return fee_sedate;
	}
	public void setFee_sedate(Date fee_sedate) {
		this.fee_sedate = fee_sedate;
	}
	
	@Column(name = "fi_bk_id")
	public Long getFi_bk_id() {
		return fi_bk_id;
	}
	public void setFi_bk_id(Long fi_bk_id) {
		this.fi_bk_id = fi_bk_id;
	}
	
	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
}

