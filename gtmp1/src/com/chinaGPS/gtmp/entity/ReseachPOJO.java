package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 文 件 名 :VehiclePOJO.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：肖克
 * 创 建 日 期：2012-12-11
 * 描 述： t_vehicle表对应的机械类
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0	
 */
@Component
@Scope("prototype")
public class ReseachPOJO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id; //主键
	
	private Long userId; //提交人ID
	
	private String userName;//提交人
	
	private Long answerId; //回复人ID
	
	private String answerName;//回复姓名
	
	private String textContent;//问题标题
	
	private String content;//正文
	
	private String textAnswer;//回答内容 	
	
	private Date lastAskDate;//最后询问时间
	
	private Date lastAnswerDate;//最后回答时间
	
	private Integer isValid;//是否有效   有效性：0 有效 1 无效
	
	private Long researchId; //调查问卷ID
	
	private Integer count;//回复总条数

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public String getTextAnswer() {
		return textAnswer;
	}

	public void setTextAnswer(String textAnswer) {
		this.textAnswer = textAnswer;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getLastAskDate() {
		return lastAskDate;
	}

	public void setLastAskDate(Date lastAskDate) {
		this.lastAskDate = lastAskDate;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getLastAnswerDate() {
		return lastAnswerDate;
	}

	public void setLastAnswerDate(Date lastAnswerDate) {
		this.lastAnswerDate = lastAnswerDate;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public String getAnswerName() {
		return answerName;
	}

	public void setAnswerName(String answerName) {
		this.answerName = answerName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}



	public Integer getCount() {
		return count;
	}

	public Long getResearchId() {
		return researchId;
	}

	public void setResearchId(Long researchId) {
		this.researchId = researchId;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	



}
