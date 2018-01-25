package com.chinagps.driverbook.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 每日油价POJO
 * 
 * @author Ben
 * 
 */
public class OilPrice extends BaseEntity {
	private static final long serialVersionUID = 1710001794284826469L;
	
	/** 主键ID */
	private String id;
	/** 省份 */
	private String province;
	/** 90号汽油价格 */
	private Double price90;
	/** 93号汽油价格 */
	private Double price93;
	/** 97号汽油价格 */
	private Double price97;
	/** 0号柴油价格 */
	private Double price0;
	/** 创建时间 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date stamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Double getPrice90() {
		return price90;
	}

	public void setPrice90(Double price90) {
		this.price90 = price90;
	}

	public Double getPrice93() {
		return price93;
	}

	public void setPrice93(Double price93) {
		this.price93 = price93;
	}

	public Double getPrice97() {
		return price97;
	}

	public void setPrice97(Double price97) {
		this.price97 = price97;
	}

	public Double getPrice0() {
		return price0;
	}

	public void setPrice0(Double price0) {
		this.price0 = price0;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
}
