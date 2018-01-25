package com.gboss.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Servicepack
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-6 下午3:17:40
 */
@Entity
@Table(name = "t_fee_service_pack")
public class Servicepack extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private Long pack_id;//'套餐ID',
	private Long subco_no;//'分公司, 0=总部定义, 大于0=分公司自定义',
	private String pack_code;//'套餐编号',
	private String name;//'套餐名称',
	private Integer ismain;//'是否主套餐, 1=是, 0=否',
	private Float price;//'价格, 不定义为0',
	private Integer flag;//'是否新用户有效, 1=是, 0=否',
	private Long org_id;//'所属机构ID, 预留, 对某个网点有效',
	private Long op_id;//'操作员ID',
	private Date stamp;//'操作时间',
	private Integer is_checked;//'是否审核, 1=已审核, 0=未审核',
	private Long chk_op_id;//'审核人ID',
	private Date chk_time;//'审核时间',
	private String remark;//'备注, 服务项描述',
	
	@Id
	@Column(name = "pack_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getPack_id() {
		return pack_id;
	}
	public void setPack_id(Long pack_id) {
		this.pack_id = pack_id;
	}
	
	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "pack_code")
	public String getPack_code() {
		return pack_code;
	}
	public void setPack_code(String pack_code) {
		this.pack_code = pack_code;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "ismain")
	public Integer getIsmain() {
		return ismain;
	}
	public void setIsmain(Integer ismain) {
		this.ismain = ismain;
	}
	
	@Column(name = "price")
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	@Column(name = "org_id")
	public Long getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}
	
	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}
	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}
	
	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	@Column(name = "is_checked")
	public Integer getIs_checked() {
		return is_checked;
	}
	public void setIs_checked(Integer is_checked) {
		this.is_checked = is_checked;
	}
	
	@Column(name = "chk_op_id")
	public Long getChk_op_id() {
		return chk_op_id;
	}
	public void setChk_op_id(Long chk_op_id) {
		this.chk_op_id = chk_op_id;
	}
	
	@Column(name = "chk_time")
	public Date getChk_time() {
		return chk_time;
	}
	public void setChk_time(Date chk_time) {
		this.chk_time = chk_time;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}

