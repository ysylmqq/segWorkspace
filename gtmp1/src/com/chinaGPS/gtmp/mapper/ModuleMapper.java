package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.ModulePOJO;
import com.chinaGPS.gtmp.entity.RoleModulePOJO;

@Component
public interface ModuleMapper<T extends ModulePOJO> extends BaseSqlMapper<T> {
	
	public ModulePOJO getModuleByModuleName(Map<String, Serializable> map) throws Exception;
	
	public List<ModulePOJO> getChildren(Long parentId) throws Exception;
	
	public List<ModulePOJO> getChecked(Long roleId) throws Exception;
	
	public void addRoleModule(ModulePOJO modulePOJO) throws Exception;
	
	public void removeRoleModule(ModulePOJO modulePOJO) throws Exception;
	
	public int addRoleModules(List<RoleModulePOJO> list) throws Exception;
	
	/**
	 * @Title:getList4Role
	 * @Description:查询角色设置的模块
	 * @param modulePOJO
	 * @return
	 * @throws
	 */
	public List<ModulePOJO> getList4Role(ModulePOJO modulePOJO) throws Exception;
	
}