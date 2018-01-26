package com.chinaGPS.gtmp.service;

import java.util.HashMap;
import java.util.List;

import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * 
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IRoleService
 * @Description:角色操作
 * @author:肖克
 * @date:Dec 14, 2012 11:56:35 AM
 *
 */
public interface IRoleService{
	
	/**
	 * 
	 * @Title:getRoleByRoleName
	 * @Description:通过角色名获得角色信息
	 * @param rolePOJO
	 * @return
	 * @throws
	 */
	public RolePOJO getRoleByRoleName(RolePOJO rolePOJO) throws Exception;
	
	/**
	 * 
	 * @Title:getRoles
	 * @Description:获得角色信息列表，翻页
	 * @param rolePOJO
	 * @param pageSelect
	 * @return
	 * @throws
	 */
	public HashMap<String, Object> getRoles(RolePOJO rolePOJO,PageSelect pageSelect) throws Exception;
	
	/**
	 * 
	 * @Title:getList
	 * @Description:获得角色信息列表，ComboBox
	 * @param rolePOJO
	 * @return
	 * @throws
	 */
	public List<RolePOJO> getList(RolePOJO rolePOJO) throws Exception;
	
	/**
	 * 
	 * @Title:getById
	 * @Description:通过角色ID获得角色信息
	 * @param id
	 * @return
	 * @throws
	 */
	public RolePOJO getById(Long id) throws Exception;
	
	/**
	 * 
	 * @Title:saveOrUpdate
	 * @Description:新增或修改角色信息
	 * @param rolePOJO
	 * @return
	 * @throws
	 */
	public boolean saveOrUpdate(RolePOJO rolePOJO) throws Exception;
	
}