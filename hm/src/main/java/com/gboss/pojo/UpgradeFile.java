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
 * @Package:com.gboss.pojo
 * @ClassName:Upgrade
 * @Description:TODO
 * @author:bzhang
 * @date:2015-01-26 下午2:11:18
 */
@Entity
@Table(name = "t_st_upgrade_file")
public class UpgradeFile extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;// 
	private Long subco_no;
	private String filename;
	private String ip;
	private Integer port;
	private Long op_id;
	private String op_ip;
	private Integer is_del=0;
	private Date stamp;// 时间
	private String remark;
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}

	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}
	
	@Column(name = "filename")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "port")
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Column(name = "op_id")
	public Long getOp_id() {
		return op_id;
	}

	public void setOp_id(Long op_id) {
		this.op_id = op_id;
	}

	@Column(name = "op_ip")
	public String getOp_ip() {
		return op_ip;
	}

	public void setOp_ip(String op_ip) {
		this.op_ip = op_ip;
	}

	@Column(name = "is_del")
	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	@Column(name = "stamp", nullable = false, updatable = true, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}

	@Override
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	
	
}
