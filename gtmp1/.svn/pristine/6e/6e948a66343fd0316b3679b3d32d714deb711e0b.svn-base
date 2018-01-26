package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 * @Package:com.chinaGPS.gtmp.entity.permission
 * @ClassName:OnePOJO
 * @Description:T_p_user表对应的用户类。
 * @author:zfy
 * @date:2012-12-14 下午05:32:25
 */
@Component
@Scope("prototype")
public class UserPOJO implements Serializable{
	private static final long serialVersionUID = 8592642972469364762L;
	/** 主键ID */
	private Long userId;	
	/** 登录名 */
	private String loginName;			
	/** 密码 */
	private String password;		
	/** 用户名称 */
	private String userName;		
	/** 性别 */
	private String sex;
	/** 联系电话 */
	private String tel;	
	/** 邮件 */
	private String email;			
	/** 部门ID */
	private Long departId;	
	/** 部门名称 */
	private String departName;	
	/** 有效性 */
	private Integer isValid;
	/** 创建时间 */
	private Date stamp;
	/** 用户角色 */
	private List<RolePOJO> roles;
	/** 部门信息 */
	private DepartPOJO departInfo;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getLoginName() {
		return loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserName() {
	    return userName;
	}
	
	public void setUserName(String userName) {
	    this.userName = userName;
	}
	
	public String getSex() {
	    return sex;
	}
	
	public void setSex(String sex) {
	    this.sex = sex;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getDepartId() {
		return departId;
	}
	
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
	public Integer getIsValid() {
	    return isValid;
	}
	
	public void setIsValid(Integer isValid) {
	    this.isValid = isValid;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss") 
	public Date getStamp() {
		return stamp;
	}
	
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getDepartName() {
		return departName;
	}
	
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
	public List<RolePOJO> getRoles() {
	    return roles;
	}
	
	public void setRoles(List<RolePOJO> roles) {
	    this.roles = roles;
	}
	
	public DepartPOJO getDepartInfo() {
	    return departInfo;
	}
	
	public void setDepartInfo(DepartPOJO departInfo) {
	    this.departInfo = departInfo;
	}
	
}