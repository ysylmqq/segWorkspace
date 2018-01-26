package com.chinaGPS.gtmp.service;

import java.util.HashMap;
import java.util.List;

import com.chinaGPS.gtmp.entity.ModulePOJO;
import com.chinaGPS.gtmp.entity.RoleModulePOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * 文 件 名 :IModuleService.java
 * CopyRright (c) 2012:赛格导航
 * 创 建 人：肖克
 * 创 建 日 期：2012-12-7
 * 描 述： 模块操作
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0
 */
public interface IModuleService{
	
	/**
	 * @Title:getModuleByModuleName
	 * @Description:通过模块名获得模块信息
	 * @param modulePOJO
	 * @return
	 * @throws
	 */
	public ModulePOJO getModuleByModuleName(ModulePOJO modulePOJO) throws Exception;
	
	/**
	 * @Title:getModules
	 * @Description:获得模块列表，翻页
	 * @param modulePOJO
	 * @param HashMap<String, Object>
	 * @return
	 * @throws
	 */
	public HashMap<String, Object> getModules(ModulePOJO modulePOJO,PageSelect pageSelect) throws Exception;
	
	/**
	 * @Title:getChildren
	 * @Description:获得子模块
	 * @param parentId
	 * @return
	 * @throws
	 */
	public List<ModulePOJO> getChildren(Long parentId) throws Exception;
	
	/**
	 * @Title:getList
	 * @Description:获得模块列表，ComboBox
	 * @param modulePOJO
	 * @return
	 * @throws
	 */
	public List<ModulePOJO> getList(ModulePOJO modulePOJO) throws Exception;
	
	/**
	 * @Title:getChecked
	 * @Description:获得被选中了的模块列表
	 * @param roleId
	 * @return
	 * @throws
	 */
	public List<ModulePOJO> getChecked(Long roleId) throws Exception;
	
	/**
	 * @Title:getById
	 * @Description:通过模块ID获得模块信息
	 * @param id
	 * @return
	 * @throws
	 */
	public ModulePOJO getById(Long id) throws Exception;
	
	/**
	 * @Title:saveOrUpdate
	 * @Description:新增或修改模块信息
	 * @param modulePOJO
	 * @return
	 * @throws
	 */
	public boolean saveOrUpdate(ModulePOJO modulePOJO) throws Exception;
	
	/**
	 * @Title:addRoleModule
	 * @Description:新增角色模块关联关系
	 * @param modulePOJO
	 * @throws
	 */
	public void addRoleModule(ModulePOJO modulePOJO) throws Exception;
	
	/**
	 * @Title:removeRoleModule
	 * @Description:删除角色模块关联关系
	 * @param modulePOJO
	 * @throws
	 */
	public void removeRoleModule(ModulePOJO modulePOJO) throws Exception;
	
	public int addRoleModules(List<RoleModulePOJO> list) throws Exception;
	
	public List<ModulePOJO> getList4Role(ModulePOJO modulePOJO) throws Exception;
	
}