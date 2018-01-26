package cc.chinagps.seat.bean.table;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 座席服务类型
 */
@Entity
@Table(name = "t_seat_servicetype")
public class ServerTypeTable implements Serializable{
	
	private static final long serialVersionUID = 8062480074953101801L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@Column(name = "st_id")
	private  Integer st_id;//服务类型ID
	
	@Column(name = "st_name")
	private String st_name;//服务类型名称
	
	@Column(name = "stamp")
	@Temporal(TIMESTAMP)
	private Date stamp;//处理时间
	
	@Column(name = "call_type")
	private Byte call_type;//1=呼入, 2=呼出
	
	@Column(name = "p_id")
	private Integer p_id;//父级ID
	
	@Column(name = "op_id")
	private BigInteger op_id;//操作者ID
    
	@Transient
	List<ServerTypeTable> childList;
	
	@JSON(name = "stamp", format = "yyyy-MM-dd HH:mm:ss")
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public List<ServerTypeTable> getChildList() {
		return childList;
	}

	public void setChildList(List<ServerTypeTable> childList) {
		this.childList = childList;
	}

	public Integer getSt_id() {
		return st_id;
	}

	public void setSt_id(Integer st_id) {
		this.st_id = st_id;
	}

	public String getSt_name() {
		return st_name;
	}

	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}

	public Byte getCall_type() {
		return call_type;
	}

	public void setCall_type(Byte call_type) {
		this.call_type = call_type;
	}

	public Integer getP_id() {
		return p_id;
	}

	public void setP_id(Integer p_id) {
		this.p_id = p_id;
	}

	public BigInteger getOp_id() {
		return op_id;
	}

	public void setOp_id(BigInteger op_id) {
		this.op_id = op_id;
	}
}
