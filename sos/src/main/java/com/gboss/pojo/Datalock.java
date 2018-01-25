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
 * @ClassName:Datalock
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-4 上午11:59:33
 */

@Entity
@Table(name = "t_fee_datalock")
public class Datalock extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private Long subco_no;
	private Integer locktag;
	private Date stamp;
	
	@Id
	@Column(name = "subco_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//可以用increment，或者seqence(oracle),identity(mysql,ms sql)
	public Long getSubco_no() {
		return subco_no;
	}
	public void setSubco_no(Long subco_no) {
		this.subco_no = subco_no;
	}

	@Column(name = "locktag")
	public Integer getLocktag() {
		return locktag;
	}
	public void setLocktag(Integer locktag) {
		this.locktag = locktag;
	}
	
	@Column(name = "stamp",nullable=false,updatable=true,insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}

