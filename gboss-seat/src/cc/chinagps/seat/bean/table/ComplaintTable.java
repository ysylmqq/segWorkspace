package cc.chinagps.seat.bean.table;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

import cc.chinagps.seat.util.Consts;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * Entity implementation class for Entity: AlarmStatus
 *
 */
@Entity
@Table(name="t_seat_complaint")
public class ComplaintTable extends BaseEntity {

	
	private static final long serialVersionUID = 4665520492612796807L;
	@NotNull
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cp_id")
	private long cp_id; 
	@Column(name="cp_no")
	private String cp_no;
	@Column(name="accept_time")
	private Date accept_time; 
	@Column(name="vehicle_id")
	private String vehicle_id; 
	@Column(name="cp_name")
	private String cp_name; 
	@Column(name="cp_phone")
	private String cp_phone; 
	@Column(name="cp_type_remark")
	private String cp_type_remark; 
	@Column(name="cp_type_id")
	private int cp_type_id; 
	@Column(name="cp_content")
	private String cp_content; 
	@Column(name="acceptance")
	private String acceptance;
	@Column(name="cp_request")
	private String cp_request; 
	@Column(name="follow_rec")
	private String follow_rec;
	@Column(name="follow_time")
	private Date follow_time;
	@Column(name="status_id")
	private int status_id; //工单状态
	@Column(name="flag")
	private int flag; //删除标记
	@Column(name="op_id")
	private long op_id; //处理人
	@Column(name="op_name")
	private String op_name; //处理人
	
	@Column(name="ac_op_id")
	private long ac_op_id; //受理人
	@Column(name="ac_op_name")
	private String ac_op_name; //受理人
	
	public String getOp_name() {
		return op_name;
	}
	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}
	public String getAc_op_name() {
		return ac_op_name;
	}
	public void setAc_op_name(String ac_op_name) {
		this.ac_op_name = ac_op_name;
	}
	public long getAc_op_id() {
		return ac_op_id;
	}
	public void setAc_op_id(long ac_op_id) {
		this.ac_op_id = ac_op_id;
	}
	@Temporal(TIMESTAMP)
	@Column(insertable = false, updatable = false)
	private Date stamp;
	
	@JSON(format = Consts.DATE_FORMAT_PATTERN)
	public Date getAccept_time() {
		return accept_time;
	}
	public void setAccept_time(Date accept_time) {
		this.accept_time = accept_time;
	}
	public String getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(String vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	public String getAcceptance() {
		return acceptance;
	}
	public void setAcceptance(String acceptance) {
		this.acceptance = acceptance;
	}
	@JSON(format = Consts.DATE_FORMAT_PATTERN)
	public Date getFollow_time() {
		return follow_time;
	}
	public void setFollow_time(Date follow_time) {
		this.follow_time = follow_time;
	}
	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	
	public long getCp_id() {
		return cp_id;
	}
	public void setCp_id(long cp_id) {
		this.cp_id = cp_id;
	}
	public String getCp_no() {
		return cp_no;
	}
	public void setCp_no(String cp_no) {
		this.cp_no = cp_no;
	}
	public String getCp_name() {
		return cp_name;
	}
	public void setCp_name(String cp_name) {
		this.cp_name = cp_name;
	}
	public String getCp_phone() {
		return cp_phone;
	}
	public void setCp_phone(String cp_phone) {
		this.cp_phone = cp_phone;
	}
	public String getCp_type_remark() {
		return cp_type_remark;
	}
	public void setCp_type_remark(String cp_type_remark) {
		this.cp_type_remark = cp_type_remark;
	}
	public int getCp_type_id() {
		return cp_type_id;
	}
	public void setCp_type_id(int cp_type_id) {
		this.cp_type_id = cp_type_id;
	}
	public String getCp_content() {
		return cp_content;
	}
	public void setCp_content(String cp_content) {
		this.cp_content = cp_content;
	}
	public String getCp_request() {
		return cp_request;
	}
	public void setCp_request(String cp_request) {
		this.cp_request = cp_request;
	}
	public String getFollow_rec() {
		return follow_rec;
	}
	public void setFollow_rec(String follow_rec) {
		this.follow_rec = follow_rec;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public long getOp_id() {
		return op_id;
	}
	public void setOp_id(long op_id) {
		this.op_id = op_id;
	}
	@JSON(format = Consts.DATE_FORMAT_PATTERN)
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
}
