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
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gboss.util.CustomDateSerializer;

/**
 * 
 * @ClassName:Task.java
 * @Description 任务单（新装、维修、出警和其他）
 * @author bzhang
 * @date 2014-3-21
 */
@Entity
@Table(name = "t_ba_task")
public class Task extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long task_id;// 任务单id
	private Long subco_no;//分公司id
	private String bill_no;// 工单号
	private Long customer_id;// 客户id
	private String phone;// 联系电话
	private Long vehicle_id;// 车辆id
	private Integer task_type;// 接单类型
	private String place;// 地点
	private String product_code;// 产品型号
	private Long sales_id;// 销售经理id
	private String symptom;// 故障现象
	private String requirements;// 客户要求
	private String note;// 注意事项
	private Long org_id;// 工单发起组织id
	private Long op_id;// 工单发起者
	private Date stamp;// 接单时间
	private Long model_id;// 车型id
	private String product_name;// 商品名称
	private Integer status;// 状态
	private Integer is_office;// 是否在营业厅
	private String color;// 车辆颜色
	private String sales;// 销售经理姓名
	private String plate_no;
	
	
	
	/*private String electricianIds;
	private String electricianNames;
	private String electrician_phone;
	private Date start_time;
	private String duration;
	private String dispatcher;// 调度人员id，用逗号隔开
	private String charger;// 调度人员 逗号隔开
*/
	
	
	@Id
	@Column(name = "task_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getTask_id() {
		return task_id;
	}
	public void setTask_id(Long task_id) {
		this.task_id = task_id;
	}
	
	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "bill_no")
	public String getBill_no() {
		return bill_no;
	}
	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}
	
	@Column(name = "customer_id")
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "vehicle_id")
	public Long getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	
	@Column(name = "task_type")
	public Integer getTask_type() {
		return task_type;
	}
	public void setTask_type(Integer task_type) {
		this.task_type = task_type;
	}
	
	@Column(name = "place")
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	@Column(name = "product_code")
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	
	@Column(name = "sales_id")
	public Long getSales_id() {
		return sales_id;
	}
	public void setSales_id(Long sales_id) {
		this.sales_id = sales_id;
	}
	
	@Column(name = "symptom")
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	
	@Column(name = "requirements")
	public String getRequirements() {
		return requirements;
	}
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}
	
	@Column(name = "note")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}
	
	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}
	
	@Column(name = "org_id")
	public Long getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}
	
	@Column(name = "stamp", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	@Column(name = "model_id")
	public Long getModel_id() {
		return model_id;
	}
	public void setModel_id(Long model_id) {
		this.model_id = model_id;
	}
	
	@Column(name = "product_name")
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "is_office")
	public Integer getIs_office() {
		return is_office;
	}
	public void setIs_office(Integer is_office) {
		this.is_office = is_office;
	}
	
	@Column(name = "color")
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	@Column(name = "sales")
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	
	@Column(name = "plate_no")
	public String getPlate_no() {
		return plate_no;
	}
	public void setPlate_no(String plate_no) {
		this.plate_no = plate_no;
	}
	
}
