package com.chinagps.driverbook.pojo;


/**
 * 资讯图片POJO
 * 
 * @author Ben
 *
 */
public class NewsImage extends BaseEntity {
	private static final long serialVersionUID = -2440552060683768070L;

	/** 主键ID */
	private String id;
	/** 图片地址 */
	private String src;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

}
