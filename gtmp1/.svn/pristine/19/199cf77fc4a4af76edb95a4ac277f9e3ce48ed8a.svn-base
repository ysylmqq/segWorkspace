package com.chinaGPS.gtmp.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.mapper.RoleMapper;
import com.chinaGPS.gtmp.service.IRoleService;
import com.chinaGPS.gtmp.util.page.PageSelect;

@Service
public class RoleServiceImpl implements IRoleService{
    @Resource
    private RoleMapper<RolePOJO> roleMapper;

    @Override
	public RolePOJO getRoleByRoleName(RolePOJO rolePOJO) throws Exception {
    	HashMap<String, Serializable> map=new HashMap<String, Serializable>();
    	map.put("role", rolePOJO);
		return roleMapper.getRoleByRoleName(map);
	}
    
	@Override
	public HashMap<String, Object> getRoles(RolePOJO rolePOJO,
			PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("role", rolePOJO);
		int total = roleMapper.countAll(map);
		List<RolePOJO> resultList =  roleMapper.getByPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
	
	@Override
	public RolePOJO getById(Long id) throws Exception {
		return roleMapper.getById(id);
	}
	
	@Override
	public boolean saveOrUpdate(RolePOJO rolePOJO) throws Exception {
		boolean flag=false;
		if(rolePOJO.getRoleId()!=null){
			if(roleMapper.edit(rolePOJO)>0){
				flag=true;
			}
		}else{
			if(roleMapper.add(rolePOJO)>0){
				flag=true;
			}
		}
		return flag;
	}

	@Override
	public List<RolePOJO> getList(RolePOJO rolePOJO) throws Exception {
		Map map = new HashMap();
		map.put("role", rolePOJO);
		return roleMapper.getList(map);
	}
	
}