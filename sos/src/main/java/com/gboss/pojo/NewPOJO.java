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
@Table(name = "t_ba_news")
public class NewPOJO extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long orgId;//所属机构
	private String orgName;//机构名称
	private String title;//标题
	private String summary;//摘要
	private String content;//内容
	private Integer isTop;//是否置顶 0=否, 1=是
	private Date addTime;// 录入时间
	private String newOrigin;//资讯来源
	private Long opId;//录入人ID	
	private String opName;//录入人姓名
	private Integer isSubmit;//是否提交审核 ，0未提交，1已提交
	private Date submitTime;//提交审核时间
	private Long chekerId;//审核人ID
	private String chekerName;//审核人姓名
	private String range;//发布指定机构范围
	private String rangeVehicle;//发布指定车辆
	private String rangeCarType;//发布指定车辆类型
	private String rangeArea;//发布指定地区范围
	private Date startTime;//有效起始时间
	private Date endTime;//有效结束时间
	private Date produceStartTime;//车辆生产区间起始时间
	private Date produceEndTime;//车辆生产结束时间
	private Integer isSaige;//是否赛格总部
	private Date checkTime;//审核时间
	private Integer checkState;//审核状态 0未审核，1审核通过，2审核不通过
	private String checkInfo;//审核信息
	private Integer isOnline;//是否发布 	0未上架，1上架
	private String lastOpId;//最后操作人ID
	private Date lastTime;//最后操作时间
	private Integer state;//删除状态
	private String imgLarge;//资讯大图
	private String imgLittle;//资讯小图

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
    @Column(name="org_id")
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	@Column(name="org_name")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name="title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name="content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="is_top")
	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	@Column(name = "add_time", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Column(name="new_origin")
	public String getNewOrigin() {
		return newOrigin;
	}

	public void setNewOrigin(String newOrigin) {
		this.newOrigin = newOrigin;
	}

	@Column(name="op_id")
	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	@Column(name="op_name")
	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	@Column(name="is_submit")
	public Integer getIsSubmit() {
		return isSubmit;
	}

	public void setIsSubmit(Integer isSubmit) {
		this.isSubmit = isSubmit;
	}

	@Column(name="checker_id")
	public Long getChekerId() {
		return chekerId;
	}

	public void setChekerId(Long chekerId) {
		this.chekerId = chekerId;
	}

	@Column(name="checker_name")
	public String getChekerName() {
		return chekerName;
	}

	public void setChekerName(String chekerName) {
		this.chekerName = chekerName;
	}

	@Column(name="`range`")
	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	@Column(name="range_vehicle")
	public String getRangeVehicle() {
		return rangeVehicle;
	}

	public void setRangeVehicle(String rangeVehicle) {
		this.rangeVehicle = rangeVehicle;
	}

	@Column(name="range_cartype")
	public String getRangeCarType() {
		return rangeCarType;
	}

	public void setRangeCarType(String rangeCarType) {
		this.rangeCarType = rangeCarType;
	}

	@Column(name="range_area")
	public String getRangeArea() {
		return rangeArea;
	}

	public void setRangeArea(String rangeArea) {
		this.rangeArea = rangeArea;
	}

	@Column(name = "start_time", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "produce_start_time", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getProduceStartTime() {
		return produceStartTime;
	}

	public void setProduceStartTime(Date produceStartTime) {
		this.produceStartTime = produceStartTime;
	}

	@Column(name = "produce_end_time", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getProduceEndTime() {
		return produceEndTime;
	}

	public void setProduceEndTime(Date produceEndTime) {
		this.produceEndTime = produceEndTime;
	}

	@Column(name="is_saige")
	public Integer getIsSaige() {
		return isSaige;
	}

	public void setIsSaige(Integer isSaige) {
		this.isSaige = isSaige;
	}

	@Column(name = "check_time", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	@Column(name="check_state")	
	public Integer getCheckState() {
		return checkState;
	}

	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}

	@Column(name="cheker_info")
	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

	@Column(name="is_online")
	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	@Column(name="last_op_id")
	public String getLastOpId() {
		return lastOpId;
	}

	public void setLastOpId(String lastOpId) {
		this.lastOpId = lastOpId;
	}


	@Column(name = "last_time", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	@Column(name="state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name="img_large")
	public String getImgLarge() {
		return imgLarge;
	}

	public void setImgLarge(String imgLarge) {
		this.imgLarge = imgLarge;
	}

	@Column(name="img_little")
	public String getImgLittle() {
		return imgLittle;
	}

	public void setImgLittle(String imgLittle) {
		this.imgLittle = imgLittle;
	}
	@Column(name="submit_time")
	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	
}
