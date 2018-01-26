package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.ModulePOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.entity.UserRolePOJO;

/**
 * 
 * @Package:com.chinaGPS.gtmp.mapper
 * @ClassName:UserMapper
 * @Description:用户dao层Mapper
 * @author:zfy
 * @date:2012-12-17 下午04:07:15
 */
@Component
public interface UserMapper<T extends UserPOJO> extends BaseSqlMapper<T> {
        
    	/**
    	 * @Title:getUserByLoginNamePwd
    	 * @Description:登录
    	 * @param map
    	 * @return
    	 * @throws
    	 */
	public UserPOJO getUserByLoginNamePwd(Map<String, Serializable> map) throws Exception;
	
	/**
	 * @Title:insertUserRoleBatch
	 * 批量保存用户角色信息
	 * @param userRoleList
	 * @return
	 * @throws
	 */
	public int insertUserRoleBatch(List<UserRolePOJO> userRoleList) throws Exception;
	
	/**
	 * @Title:insertUserRole
	 * @Description:用户设置角色
	 * @param userRoleList
	 * @return
	 * @throws
	 */
	public int insertUserRole(UserRolePOJO userRoleList) throws Exception;
	
	/**
	 * @Title:removeUserRolesByUId
	 * @Description:根据用户ID删除用户信息
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 * @throws
	 */
	public int removeUserRolesByUId(Long userId) throws Exception;
	
	public int removeByUserRole(UserRolePOJO userRolePOJO) throws Exception;
	
	/**
	 * @Title:getUserRoles
	 * @Description:根据条件查询用户
	 * @param userRolePOJO
	 * @return
	 * @throws DataAccessException
	 * @throws
	 */
	public List<UserRolePOJO> getUserRoles(UserRolePOJO userRolePOJO) throws Exception;
	
	/**
	 * @Title:getAccessibleModues
	 * @Description:获得用户可访问的模块集合
	 * @param map
	 * @return
	 * @throws
	 */
	public List<ModulePOJO> getAccessibleModues(HashMap<String, Object> map) throws Exception;
}