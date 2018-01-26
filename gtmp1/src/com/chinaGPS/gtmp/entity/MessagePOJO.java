package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 文 件 名 :VehicleTypePOJO.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：zfy
 * 创 建 日 期：2013-4-17
 * 描 述： t_dic_vehicle_type表对应的机械类型类
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0
 */
@Component
@Scope("prototype")
public class MessagePOJO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//新增主键
	private String title;//标题
	private String content;//信息内容
	//private String workProvince;  //工作省份
	//private String workCity;//工作城市
	private String contact;//联系人
	private String phoneNumber;//联系方式	
	private String createName;//发布人
	private Long createId;//发布人ID
	private Integer isValid;//有效性：0 有效 1 无效
	private Date createTime;//发布时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	


	
	
		
}
