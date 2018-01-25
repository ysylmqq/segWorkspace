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
 * @ClassName:ObdFault
 * @Description:TODO
 * @author:zhangb
 * @date:2014-12-15 下午2:11:18
 */
@Entity
@Table(name = "t_ba_obd_fault")
public class ObdFault extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long file_id;
	private Long subco_no;//分公司id
	private String file_name;//文件名
	private String url;//文件访问地址
	private Date st_date;//st_date
	private Date stamp;
	
	@Id
	@Column(name = "file_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getFile_id() {
		return file_id;
	}

	public void setFile_id(Long file_id) {
		this.file_id = file_id;
	}

	@Column(name = "subco_no")
	public Long getSubco_no() {
		return subco_no;
	}

	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}

	@Column(name = "file_name")
	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "st_date")
	public Date getSt_date() {
		return st_date;
	}

	public void setSt_date(Date st_date) {
		this.st_date = st_date;
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

}
