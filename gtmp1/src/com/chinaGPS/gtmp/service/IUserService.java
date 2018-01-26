package com.chinaGPS.gtmp.service;

import java.util.HashMap;
import java.util.List;

import com.chinaGPS.gtmp.entity.ModulePOJO;
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.entity.UserRolePOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;
/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IUserService
 * @Description:用户Service层
 * @author:zfy
 * @date:2012-12-17 下午04:19:20
 */
public interface IUserService{
	/**
	 * 登录
	 * @param userPOJO
	 * @return
	 */
	public UserPOJO getUserByLoginNamePwd(UserPOJO userPOJO) throws Exception;
	
	/**
	 * 判断登录名是否存在
	 * @Title:checkLoginNameRepeat
	 * @Description:
	 * @param userPOJO
	 * @return
	 * @throws
	 */
	public UserPOJO checkLoginNameRepeat(UserPOJO userPOJO) throws Exception;
	
	
	/**
	 * 获得用户分页数据
	 * @param userPOJO
	 * @param pageSelect
	 * @return
	 */
	public HashMap<String, Object> getUsers(UserPOJO userPOJO,PageSelect pageSelect) throws Exception;
	
	/**
	 * 
	 * 根据用户ID查询出用户信息
	 * @param id
	 * @return
	 */
	public UserPOJO getById(Long id) throws Exception;
	
	/**
	 * 根据用户id删除用户信息
	 * @param id
	 * @return
	 */
	public boolean deleteUserById(Long id) throws Exception;
	
	/**
	 * 保存用户信息
	 * @param userPOJO
	 * @return
	 */
	public boolean saveOrUpdate(UserPOJO userPOJO) throws Exception;
	
	/**
	 * 批量设置用户角色，先删除原来的角色
	 * @param userId
	 * @param userRoleList
	 * @return
	 */
	public boolean setUserRoles(List<UserRolePOJO> userRoleList) throws Exception;
	
	/**
	 * 根据用户id删除用户所有的角色
	 * @param userId
	 * @return
	 */
	public boolean removeUserRolesByUId(Long userId) throws Exception;
	
	/**
	 * 添加用户角色绑定关系
	 * @param userRolePOJO
	 * @return
	 */
	public boolean setUserRole(UserRolePOJO userRolePOJO) throws Exception;
	
	/**
	 * 获得用户角色的绑定关系
	 * @param userRolePOJO
	 * @return
	 */
	public List<UserRolePOJO> getUserRoles(UserRolePOJO userRolePOJO) throws Exception;
	
	/**
	 * @Title:getList
	 * @Description:获取当前用户所有角色的可访问模块
	 * @param roles
	 * @return
	 * @throws
	 */
	public List<ModulePOJO> getAccessibleModues(List<RolePOJO> roles) throws Exception;
	
}