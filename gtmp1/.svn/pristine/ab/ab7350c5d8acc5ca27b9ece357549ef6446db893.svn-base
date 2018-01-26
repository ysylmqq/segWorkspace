package com.chinaGPS.gtmp.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.ModulePOJO;
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.entity.UserRolePOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.UserMapper;
import com.chinaGPS.gtmp.service.IUserService;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:UserServiceImpl
 * @Description:用户Service层实现类
 * @author:zfy
 * @date:2012-12-17 下午04:10:46
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserPOJO> implements IUserService{
    @Resource
    private UserMapper<UserPOJO> userMapper;
    
    @Override
    protected BaseSqlMapper<UserPOJO> getMapper() {
		return this.userMapper;
    }
    
	@Override
	public UserPOJO getUserByLoginNamePwd(UserPOJO userPOJO) throws Exception {
		HashMap<String, Serializable> map=new HashMap<String, Serializable>();
		map.put("user", userPOJO);
		return userMapper.getUserByLoginNamePwd(map);
	}
	
	@Override
	public HashMap<String, Object> getUsers(UserPOJO userPOJO, PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("user", userPOJO);
		int total = userMapper.countAll(map);
		List<UserPOJO> resultList =  userMapper.getByPage(map, new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
	
	@Override
	public UserPOJO getById(Long id) throws Exception {
		return (UserPOJO) userMapper.getById(id);
	}
	
	@Override
	public boolean deleteUserById(Long id) throws Exception {
		return userMapper.removeById(id) > 0 ? true : false;
	}
	
	@Override
	public boolean saveOrUpdate(UserPOJO userPOJO) throws Exception {
		boolean flag = false;
		if (userPOJO.getUserId() != null) {
			if (userMapper.edit(userPOJO) > 0) {
				flag = true;
			}
		} else {
			if (userMapper.add(userPOJO) > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 删掉以前的角色，重新设置用户角色
	 */
	@Override
	public boolean setUserRoles(List<UserRolePOJO> userRoleList) throws Exception {
		//先删除
		UserRolePOJO userRolePOJO = userRoleList.get(0);
		userMapper.removeByUserRole(userRolePOJO);
		//后添加
		if(userRoleList != null && !userRoleList.isEmpty()){
		   int result = userMapper.insertUserRoleBatch(userRoleList);
		}
		return true;
	}
	
	@Override
	public boolean setUserRole(UserRolePOJO userRolePOJO) throws Exception {
		return userMapper.insertUserRole(userRolePOJO) > 0 ? true : false;		
	}
	
	@Override
	public boolean removeUserRolesByUId(Long userId) throws Exception {
		return userMapper.removeUserRolesByUId(userId) > 0 ? true : false;
	}
	
	@Override
	public List<UserRolePOJO> getUserRoles(UserRolePOJO userRolePOJO) throws Exception {
		return userMapper.getUserRoles(userRolePOJO);
	}
	
	@Override
	public UserPOJO checkLoginNameRepeat(UserPOJO userPOJO) throws Exception {
	    return userMapper.get(userPOJO);
	}
	
	@Override
	public List<ModulePOJO> getAccessibleModues(List<RolePOJO> roles) throws Exception {
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("roles", roles);
		return userMapper.getAccessibleModues(map);
	}
}