package cc.chinagps.seat.bean.table;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import cc.chinagps.seat.util.Consts;

import com.googlecode.jsonplugin.annotations.JSON;

@Entity
@Table(name = "t_seat_brief")
public class BriefTable implements Serializable{
	private static final String SEPARATOR = ",";
	
	private static final long serialVersionUID = -720977559111438684L;
	
	@Id
	@GeneratedValue
	@Column(name="b_id")
	private BigInteger b_id;
	
	@Column(name="vehicle_id")
	private BigInteger vehicle_id;
	
	@Column(name="p_st_id")
	private Integer p_st_id;
	
	@Column(name="talk_time")
	private Integer talk_time;//通话时长(秒)
	
	@Column(name="flag")
	private Integer flag;//接通情况, 0=接通, 1=忙线, 2=无人接听, 3=关机, 4=空号, 5=断线, 6=拒接, 7=错号, 8=故障, 9=其他
	
	@Column(name="type")
	private Byte type;
	
	@Size(max = 500)
	@Column(length = 500)
	private String content;
	
	@Column(name="subco_no")
	private BigInteger subco_no;
	
	@Column(name = "c_phone")
	private String c_phone;	//中心电话号码
	
	/**
	 * 服务类型(不包含其它类型)，与serviceTypeOther拼接后为serviceContent
	 */
	@Transient
	private String[] serviceType;
	
	/**
	 * 其它服务类型
	 */
	@Transient
	private String serviceTypeOther;
	@Size(max = 500)
	@Column(length = 500 , name="result")
	private String result;
	@Size(max = 200)
	@Column(name = "phone", length = 500)
	private String phone;
	@Column(name = "op_id")
	private Integer op_id;
	@Column(name = "op_name")
	private String op_name;
	
	@Temporal(TIMESTAMP)
	@Column(insertable = false, updatable = false)
	private Date stamp;
	
	
	/**
	 * 通过{@code serviceType}和{@code serviceTypeOther}生成{@code serviceContent}
	 */
	public void generateServiceContent() {
		StringBuilder builder = new StringBuilder();
		if (serviceType != null && serviceType.length > 0) {
			for (String type : serviceType) {
				builder.append(type);
				builder.append(SEPARATOR);
			}
			if (builder.length() > 0) {
				builder.deleteCharAt(builder.length() - 1);
			}
		}
		if (serviceTypeOther != null) {
//			if (builder.length() > 0) {
//				builder.append(SEPARATOR);
//			}
//			builder.append(serviceTypeOther);
		}
		this.content = builder.toString();
	}
	public String getServiceContent() {
		return content;
	}
	@JSON(serialize = false)
	public String[] getServiceType() {
		return serviceType;
	}
	@JSON(serialize = false)
	public String getServiceTypeOther() {
		return serviceTypeOther;
	}
	public String getResult() {
		return result;
	}
	
	
	@JSON(format = Consts.DATE_FORMAT_PATTERN)
	public Date getStamp() {
		return stamp;
	}
	public Byte getType() {
		return type;
	}
//	public UnitTable getUnit() {
//		return unit;
//	}
	public void setServiceType(String[] serviceType) {
		this.serviceType = serviceType;
	}
	public void setServiceTypeOther(String serviceTypeOther) {
		this.serviceTypeOther = serviceTypeOther;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	
	public BigInteger getB_id() {
		return b_id;
	}
	public void setB_id(BigInteger b_id) {
		this.b_id = b_id;
	}
	public BigInteger getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(BigInteger vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getOp_id() {
		return op_id;
	}
	public void setOp_id(Integer op_id) {
		this.op_id = op_id;
	}
	public String getOp_name() {
		return op_name;
	}
	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}
	public Integer getP_st_id() {
		return p_st_id;
	}
	public void setP_st_id(Integer p_st_id) {
		this.p_st_id = p_st_id;
	}
	public Integer getTalk_time() {
		return talk_time;
	}
	public void setTalk_time(Integer talk_time) {
		this.talk_time = talk_time;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getC_phone() {
		return c_phone;
	}
	
	public void setC_phone(String c_phone) {
		this.c_phone = c_phone;
	}
	
	public BigInteger getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(BigInteger subco_no) {
		this.subco_no = subco_no;
	}
}
