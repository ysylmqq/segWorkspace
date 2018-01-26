package com.chinaGPS.gtmp.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 表格动态列
 */
@Component
@Scope("prototype")
public class DynamicColumn implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 值 */
	private String field;
	/** 标题 */
	private String title;
	/** 宽度 */
	private Integer width;
	/** 是否能改变大小 */
	private Boolean resizable;
	/** 是否隐藏 */
	private Boolean hidden;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Boolean getResizable() {
		return resizable;
	}

	public void setResizable(Boolean resizable) {
		this.resizable = resizable;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

}
