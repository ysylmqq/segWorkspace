package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * @Package:com.gboss.pojo
 * @ClassName:CheckDetails
 * @Description:盘点明细实体类
 * @author:zfy
 * @date:2013-8-30 上午10:45:17
 */
@Entity
@Table(name = "t_whs_check_details")
public class CheckDetails extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//盘点明细ID
	private Long checkId;//盘点ID
	private Long productId;//产品ID
	private Long lastSaveNum;//上月帐面结存数量=上月月未帐面结存数量+调账数
	private Long curInNum;//本月入库数量
	private Long curOutNum;//本月出库数量
	private Long curSaveNum;//月未帐面结存数量
	private Long curObjectNum;//月未实物盘存
	private Long overShortNum;//盘盈数/盘亏数（系统自动计算）
	private Long changeNum;//调账数(出库为负数、入库为正数)
	private String remark;//备注
	
	private String productName;//商品名称
	private String norm;//商品规格
	private String productCode;//商品编码

	public CheckDetails() {
	}

	public CheckDetails(Long id) {
		this.id = id;
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
	@Column(name = "product_id")
	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	@Column(name = "last_save_num")
	public Long getLastSaveNum() {
		return this.lastSaveNum;
	}

	public void setLastSaveNum(Long lastSaveNum) {
		this.lastSaveNum = lastSaveNum;
	}
	@Column(name = "cur_in_num")
	public Long getCurInNum() {
		return this.curInNum;
	}

	public void setCurInNum(Long curInNum) {
		this.curInNum = curInNum;
	}
	@Column(name = "cur_save_num")
	public Long getCurSaveNum() {
		return this.curSaveNum;
	}

	public void setCurSaveNum(Long curSaveNum) {
		this.curSaveNum = curSaveNum;
	}
	@Column(name = "cur_object_num")
	public Long getCurObjectNum() {
		return this.curObjectNum;
	}

	public void setCurObjectNum(Long curObjectNum) {
		this.curObjectNum = curObjectNum;
	}

	@Column(name = "over_short_num")
	public Long getOverShortNum() {
		return this.overShortNum;
	}

	public void setOverShortNum(Long overShortNum) {
		this.overShortNum = overShortNum;
	}
	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "check_id")
	public Long getCheckId() {
		return checkId;
	}

	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}
	@Column(name = "cur_out_num")
	public Long getCurOutNum() {
		return curOutNum;
	}

	public void setCurOutNum(Long curOutNum) {
		this.curOutNum = curOutNum;
	}
	@Column(name = "change_num")
	public Long getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(Long changeNum) {
		this.changeNum = changeNum;
	}
	@Transient
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Transient
	public String getNorm() {
		return norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
	}
	@Transient
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	
}
