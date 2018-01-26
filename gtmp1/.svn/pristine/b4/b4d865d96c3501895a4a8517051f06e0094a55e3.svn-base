package com.chinaGPS.gtmp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.OwnerPOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.entity.UserRolePOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.OwnerUserMapper;
import com.chinaGPS.gtmp.service.IOwnerUserService;

/**
 * 机主用户Service实现类
 * @author Ben
 *
 */
@Service
public class OwnerUserServiceImpl extends BaseServiceImpl<OwnerPOJO> implements IOwnerUserService {
	@Resource
	private OwnerUserMapper<OwnerPOJO> ownerUserMapper;

	@Override
	public UserPOJO getUserByLoginNamePwd(UserPOJO userPOJO) throws Exception {
		return ownerUserMapper.getUserByLoginNamePwd(userPOJO);
	}

	@Override
	public List<UserRolePOJO> getOwnerRoles(UserRolePOJO userRolePOJO) throws Exception {
		return ownerUserMapper.getOwnerRoles(userRolePOJO);
	}

	@Override
	protected BaseSqlMapper<OwnerPOJO> getMapper() {
		return this.ownerUserMapper;
	}

}