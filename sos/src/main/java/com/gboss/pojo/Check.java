package com.gboss.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
/**
 * @Package:com.gboss.pojo
 * @ClassName:Check
 * @Description:盘点实体类
 * @author:zfy
 * @date:2013-8-30 上午10:41:55
 */
@Entity
@Table(name = "t_whs_check")
public class Check extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Long id;//盘点ID
	private String name;//名称
	private Long whsId;//仓库ID
	private String whsName;//仓库名称
	private Long companyId;//公司ID
	private String companyName;//公司名称
	private Long orgId;//所属机构ID
	private String orgName;//所属机构名称
	private Long directorId;//营业处负责人ID
	private String directorName;//营业处负责人名称
	private Date startDate;//盘点开始时间
	private Date endDate;//盘点结束时间
	private Integer status;//盘点状态 1：已开始，2：盘点录入,3：审核结束（只能调账），4.调账结束（所有记录都不能改）
	private Long userId;//盘点人ID
	private String userName;//盘点人名称
	private Long checkUserId;//审核人ID
	private Date checkStamp;//审核时间
	private Date stamp;//操作时间
	
	private List<CheckDetails> checkDetails;//盘点详细
	
	private List<HashMap<String, Object>> checkDetailsMap;//盘点详细，与CheckDetails字段类似
	
	public Check() {
	}

	public Check(Long id) {
		this.id = id;
	}

	public Check(Long id, String name, Long whsId, Long companyId,
			Long orgId, Long directorId, Date startDate, Date endDate,
			Long userId, Date stamp) {
		this.id = id;
		this.name = name;
		this.whsId = whsId;
		this.companyId = companyId;
		this.orgId = orgId;
		this.directorId = directorId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userId = userId;
		this.stamp = stamp;
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
	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "whs_id")
	public Long getWhsId() {
		return this.whsId;
	}

	public void setWhsId(Long whsId) {
		this.whsId = whsId;
	}
	@Column(name = "company_id")
	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	@Column(name = "org_id")
	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	@Column(name = "director_id")
	public Long getDirectorId() {
		return this.directorId;
	}

	public void setDirectorId(Long directorId) {
		this.directorId = directorId;
	}
	@Column(name = "start_date")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Column(name = "end_date")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStamp() {
		return this.stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	@Transient
	public List<CheckDetails> getCheckDetails() {
		return checkDetails;
	}

	public void setCheckDetails(List<CheckDetails> checkDetails) {
		this.checkDetails = checkDetails;
	}
	@Transient
	public List<HashMap<String, Object>> getCheckDetailsMap() {
		return checkDetailsMap;
	}

	public void setCheckDetailsMap(List<HashMap<String, Object>> checkDetailsMap) {
		this.checkDetailsMap = checkDetailsMap;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "whs_name")
	public String getWhsName() {
		return whsName;
	}

	public void setWhsName(String whsName) {
		this.whsName = whsName;
	}
	@Column(name = "company_name")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Column(name = "org_name")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(name = "director_name")
	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "check_user_id")
	public Long getCheckUserId() {
		return checkUserId;
	}
	public void setCheckUserId(Long checkUserId) {
		this.checkUserId = checkUserId;
	}
	@Column(name = "check_stamp")
	public Date getCheckStamp() {
		return checkStamp;
	}
	public void setCheckStamp(Date checkStamp) {
		this.checkStamp = checkStamp;
	}
}
