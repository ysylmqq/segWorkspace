package com.chinagps.driverbook.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 资讯POJO
 * 
 * @author Ben
 *
 */
public class News extends BaseEntity {
	private static final long serialVersionUID = 4665094332229720369L;
	
	/** 主键ID */
	private Long id;
	/** 机构ID */
	@JsonIgnore
	private String orgId;
	/** 机构名称 */
	@JsonIgnore
	private String orgName;
	/** 资讯标题 */
	private String title;
	/** 摘要 */
	private String summary;
	/** 资讯内容 */
	@JsonIgnore
	private String content;
	/** 资讯大图 */
	private String imgLarge;
	/** 资讯小图 */
	private String imgLittle;
	/** 是否置顶资讯, 0=否, 1=是 */
	private Integer isTop;
	/** 资讯来源 */
	private String newOrigin;
	/** 发布时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgLarge() {
		return imgLarge;
	}

	public void setImgLarge(String imgLarge) {
		this.imgLarge = imgLarge;
	}

	public String getImgLittle() {
		return imgLittle;
	}

	public void setImgLittle(String imgLittle) {
		this.imgLittle = imgLittle;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public String getNewOrigin() {
		return newOrigin;
	}

	public void setNewOrigin(String newOrigin) {
		this.newOrigin = newOrigin;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}
