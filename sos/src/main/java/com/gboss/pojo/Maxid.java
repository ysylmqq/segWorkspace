package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Maxid
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-6 下午2:33:01
 */
@Entity
@Table(name = "t_ba_maxid")
public class Maxid extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private Long subco_no;//'分公司ID',
	private Long ct_no;//'合同编号',
	
	@Id
	@Column(name = "subco_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "ct_no")
	public Long getCt_no() {
		return ct_no;
	}
	public void setCt_no(Long ct_no) {
		this.ct_no = ct_no;
	}

}

