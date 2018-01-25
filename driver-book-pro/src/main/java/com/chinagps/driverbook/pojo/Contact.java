package com.chinagps.driverbook.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 联系人POJO
 * 
 * @author Ben
 *
 */
public class Contact extends BaseEntity {
	private static final long serialVersionUID = 2798124529531446924L;

	/** 主键ID */
	private String id;
	/** 联系人姓名 */
	private String contactName;
	/** 联系内容 */
	private String contactInfo;
	/** Android通讯录minitype, (电话, 邮箱) */
	private Integer minitype;
	/** minitype子类型, (手机, 办公, 家庭) */
	private Integer type;
	/** 上传版本号 */
	private String uploadVersion;
	/** 通讯录版本号 */
	private String contactVersion;
	/** 联系人标识 */
	private String contactKey;
	/** 自定义标签 */
	private String label;
	/** 时间戳 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date stamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public Integer getMinitype() {
		return minitype;
	}

	public void setMinitype(Integer minitype) {
		this.minitype = minitype;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUploadVersion() {
		return uploadVersion;
	}

	public void setUploadVersion(String uploadVersion) {
		this.uploadVersion = uploadVersion;
	}

	public String getContactVersion() {
		return contactVersion;
	}

	public void setContactVersion(String contactVersion) {
		this.contactVersion = contactVersion;
	}

	public String getContactKey() {
		return contactKey;
	}

	public void setContactKey(String contactKey) {
		this.contactKey = contactKey;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

}
