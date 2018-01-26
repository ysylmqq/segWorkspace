package com.chinaGPS.gtmp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.OwnerPOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.entity.UserRolePOJO;

/**
 * 机主用户Mapper
 * @author Ben
 *
 */
@Component
public interface OwnerUserMapper<T extends OwnerPOJO> extends BaseSqlMapper<T> {
	/**
	 * 机主用户登录
	 * @param userPOJO
	 * @return
	 * @throws Exception
	 */
	public UserPOJO getUserByLoginNamePwd(UserPOJO userPOJO) throws Exception;
	
	/**
	 * 获取机主用户权限列表
	 * @param userRolePOJO
	 * @return
	 * @throws Exception
	 */
	public List<UserRolePOJO> getOwnerRoles(UserRolePOJO userRolePOJO) throws Exception;
}