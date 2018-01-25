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

/**
 * @Package:com.gboss.pojo
 * @ClassName:Repair
 * @Description:TODO
 * @author:lican
 * @date:2015-4-22 下午5:11:08
 */
@Entity
@Table(name = "t_mt_repair")
public class Repair extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private Long rpId;//'预约登记ID',
	private Long subcoNo;//'LDAP分公司根节点ID, 对应我们的分公司, 内部机构',
	private Long customerId;//'客户ID, 一般为最终车主用户',
	private Long vehicleId;//'车辆ID',
	private Long unitId;//'车台ID',
	private String jobNo;//'维护单号',
	private String customerName;//车主姓名
	private String phone;//车主联系电话
	private String plateNo;//车牌号码
	private String unittype;//车台类型
	private String barcode;//主机编号
	private Date fixTime;//安装时间
	private String place;//车辆所在位置,
	private Date regTime;//维修登记日期
	private Integer rpType;//业务类型
	private String symptom;//故障现象
	private Long regOpId;//'录入操作员ID',
	private String regOpName;//操作员名称
	private String regRemark;//维修登记备注
	private Long orgId;//指派处理机构
	private Integer status;//维修状态 工单状态, 1=已登记, 2=已受理, 3=已预约, 4=已派工, 5=维修中, 6=维修完成, 7=未维修完成
	private Date stamp;//操作时间
	private Long acpOpId;//受理人员ID
	private String acpOpName;//受理人员
	private Date aptTime;//预约时间
	private String aptName;//预约人员
	private String aptPhone;//预约人电话
	private String rpPlace;//维修地点
	private String traceResult;//跟踪情况
	private String worker;//电工
	private String workerResult;//处理结果

	@Id
	@Column(name = "rp_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getRpId() {
		return rpId;
	}
	public void setRpId(Long rpId) {
		this.rpId = rpId;
	}
	@Column(name = "subco_no")
	public Long getSubcoNo() {
		return subcoNo;
	}
	public void setSubcoNo(Long subcoNo) {
		this.subcoNo = subcoNo;
	}
	@Column(name = "customer_id")
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	@Column(name = "vehicle_id")
	public Long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	@Column(name = "unit_id")
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	@Column(name = "job_no")
	public String getJobNo() {
		return jobNo;
	}
	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}
	@Column(name = "customer_name")
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "plate_no")
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	@Column(name = "unittype")
	public String getUnittype() {
		return unittype;
	}
	public void setUnittype(String unittype) {
		this.unittype = unittype;
	}
	@Column(name = "barcode")
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	@Column(name = "fix_time")
	public Date getFixTime() {
		return fixTime;
	}
	public void setFixTime(Date fixTime) {
		this.fixTime = fixTime;
	}
	@Column(name = "place")
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	@Column(name = "reg_time")
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	@Column(name = "rp_type")
	public Integer getRpType() {
		return rpType;
	}
	public void setRpType(Integer rpType) {
		this.rpType = rpType;
	}
	@Column(name = "symptom")
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	@Column(name = "reg_op_id")
	public Long getRegOpId() {
		return regOpId;
	}
	public void setRegOpId(Long regOpId) {
		this.regOpId = regOpId;
	}
	@Column(name = "reg_op_name")
	public String getRegOpName() {
		return regOpName;
	}
	public void setRegOpName(String regOpName) {
		this.regOpName = regOpName;
	}
	@Column(name = "reg_remark")
	public String getRegRemark() {
		return regRemark;
	}
	public void setRegRemark(String regRemark) {
		this.regRemark = regRemark;
	}
	@Column(name = "org_id")
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "stamp")
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	@Column(name = "acp_op_id")
	public Long getAcpOpId() {
		return acpOpId;
	}
	public void setAcpOpId(Long acpOpId) {
		this.acpOpId = acpOpId;
	}
	@Column(name = "acp_op_name")
	public String getAcpOpName() {
		return acpOpName;
	}
	public void setAcpOpName(String acpOpName) {
		this.acpOpName = acpOpName;
	}
	@Column(name = "apt_time")
	public Date getAptTime() {
		return aptTime;
	}
	public void setAptTime(Date aptTime) {
		this.aptTime = aptTime;
	}
	@Column(name = "apt_name")
	public String getAptName() {
		return aptName;
	}
	public void setAptName(String aptName) {
		this.aptName = aptName;
	}
	@Column(name = "apt_phone")
	public String getAptPhone() {
		return aptPhone;
	}
	public void setAptPhone(String aptPhone) {
		this.aptPhone = aptPhone;
	}
	@Column(name = "rp_place")
	public String getRpPlace() {
		return rpPlace;
	}
	public void setRpPlace(String rpPlace) {
		this.rpPlace = rpPlace;
	}
	@Column(name = "trace_result")
	public String getTraceResult() {
		return traceResult;
	}
	public void setTraceResult(String traceResult) {
		this.traceResult = traceResult;
	}
	@Column(name = "worker")
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	@Column(name = "worker_result")
	public String getWorkerResult() {
		return workerResult;
	}
	public void setWorkerResult(String workerResult) {
		this.workerResult = workerResult;
	}
}

