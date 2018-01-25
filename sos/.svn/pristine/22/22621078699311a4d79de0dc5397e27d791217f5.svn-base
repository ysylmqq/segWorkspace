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
 * @ClassName:Simpack
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-12-11 下午3:49:43
 */
@Entity
@Table(name = "t_fee_sim_pack")
public class Simpack extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private Long pack_id;		//服务包ID
	private Long subco_no;		//分公司id
	private Integer feetype_id;	//计费类型, 系统值3100, 1=服务费, 2=SIM卡, 3=盗抢险, 4=网上查车, 101=前装服务费
	private String pack_code;	//套餐编号
	private String pack_name;	//套餐名
	private Integer sim_type;	//卡类型, 1=语音卡, 2=流量卡
	private Long combo_id;		//套餐ID
	private Integer fee_cycle;	//服务周期(月)
	private Float per_fee;		//单价, 预留
	private Float price;		//价格(元)
	private Integer flag;		//标志, 1=正常, 0=删除
	private Long org_id;		//所属机构ID, 预留, 对某个网点有效
	private Long op_id;			//操作員id
	private Date create_date;	//创建时间
	private Date stamp;			//操作时间
	private Integer is_checked;	//是否审核, 1=已审核, 0=未审核
	private Long chk_op_id;		//审核人ID
	private Date chk_time;		//审核时间
	private String remark;		//备注

	@Id
	@Column(name = "pack_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Column(name = "feetype_id")
	public Integer getFeetype_id() {
		return feetype_id;
	}

	public void setFeetype_id(Integer feetype_id) {
		this.feetype_id = feetype_id;
	}

	@Column(name = "pack_code")
	public String getPack_code() {
		return pack_code;
	}

	public void setPack_code(String pack_code) {
		this.pack_code = pack_code;
	}

	@Column(name = "pack_name", nullable=false)
	public String getPack_name() {
		return pack_name;
	}

	public void setPack_name(String pack_name) {
		this.pack_name = pack_name;
	}

	@Column(name = "sim_type", nullable=false)
	public Integer getSim_type() {
		return sim_type;
	}

	public void setSim_type(Integer sim_type) {
		this.sim_type = sim_type;
	}

	@Column(name = "combo_id", nullable=false)
	public Long getCombo_id() {
		return combo_id;
	}

	public void setCombo_id(Long combo_id) {
		this.combo_id = combo_id;
	}

	@Column(name = "fee_cycle", nullable=false)
	public Integer getFee_cycle() {
		return fee_cycle;
	}

	public void setFee_cycle(Integer fee_cycle) {
		this.fee_cycle = fee_cycle;
	}

	@Column(name = "per_fee")
	public Float getPer_fee() {
		return per_fee;
	}

	public void setPer_fee(Float per_fee) {
		this.per_fee = per_fee;
	}

	@Column(name = "price", nullable=false)
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Column(name = "flag", nullable=false)
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

	@Column(name = "op_id", nullable=false)
	public Long getOp_id() {
		return op_id;
	}

	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}

	@Column(name = "create_date",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
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

