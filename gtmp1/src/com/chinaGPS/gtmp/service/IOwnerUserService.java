package com.chinaGPS.gtmp.service;

import java.util.List;

import com.chinaGPS.gtmp.entity.OwnerPOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.entity.UserRolePOJO;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IDealerAreaService
 * @Description:经销商片区接口
 * @author:zfy
 * @date:Dec 11, 2012 2:25:45 PM
 */
public interface IOwnerUserService extends BaseService<OwnerPOJO> {
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