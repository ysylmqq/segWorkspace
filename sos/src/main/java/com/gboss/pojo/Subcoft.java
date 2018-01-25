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
 * @ClassName:Subcoft
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-12-11 下午3:53:00
 */
@Entity
@Table(name = "t_fee_subco_ft")
public class Subcoft extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long ft_id;//'关联表ID',
	private Long subco_no;//'分公司ID',
	private Integer feetype_id;//'计费类型, 系统值3100, 1=服务费, 2=SIM卡, 3=盗抢险, 4=网上查车, 101=前装服务费',
	private Integer flag;//'标志, 1=有效, 0=无效',
	private Long op_id;//'操作员ID',
	private Date stamp;//'时间戳',
	
	@Id
	@Column(name = "ft_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getFt_id() {
		return ft_id;
	}
	public void setFt_id(Long ft_id) {
		this.ft_id = ft_id;
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
	
	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
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
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}	

}

