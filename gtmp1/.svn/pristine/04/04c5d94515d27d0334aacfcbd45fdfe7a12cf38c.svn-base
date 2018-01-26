package com.chinaGPS.gtmp.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.ModulePOJO;
import com.chinaGPS.gtmp.entity.RoleModulePOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.ModuleMapper;
import com.chinaGPS.gtmp.service.IModuleService;
import com.chinaGPS.gtmp.util.page.PageSelect;

@Service
public class ModuleServiceImpl extends BaseServiceImpl<ModulePOJO> implements IModuleService{
    @Resource
    private ModuleMapper<ModulePOJO> moduleMapper;
    @Override
    protected BaseSqlMapper<ModulePOJO> getMapper() {
		return this.moduleMapper;
    }
    @Override
	public ModulePOJO getModuleByModuleName(ModulePOJO modulePOJO) throws Exception {
    	HashMap<String, Serializable> map=new HashMap<String, Serializable>();
    	map.put("module", modulePOJO);
		return moduleMapper.getModuleByModuleName(map);
	}

	@Override
	public HashMap<String, Object> getModules(ModulePOJO modulePOJO,
			PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("module", modulePOJO);
		int total = moduleMapper.countAll(map);
		List<ModulePOJO> resultList =  moduleMapper.getByPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
	
	@Override
	public List<ModulePOJO> getChildren(Long parentId) throws Exception {
		return moduleMapper.getChildren(parentId);
	}
	@Override
	public List<ModulePOJO> getChecked(Long roleId) throws Exception {
		return moduleMapper.getChecked(roleId);
	}
	@Override
	public List<ModulePOJO> getList(ModulePOJO modulePOJO) throws Exception {
		return moduleMapper.getList(modulePOJO);
	}
	@Override
	public void addRoleModule(ModulePOJO modulePOJO) throws Exception {
		moduleMapper.addRoleModule(modulePOJO);
	}
	@Override
	public void removeRoleModule(ModulePOJO modulePOJO) throws Exception {
		moduleMapper.removeRoleModule(modulePOJO);
	}
	@Override
	public ModulePOJO getById(Long id) throws Exception {
		return (ModulePOJO)moduleMapper.getById(id);
	}
	@Override
	public boolean saveOrUpdate(ModulePOJO modulePOJO) throws Exception {
		boolean flag=false;
		if(modulePOJO.getModuleId()!=null){
			if(moduleMapper.edit(modulePOJO)>0){
				flag=true;
			}
		}else{
			if(moduleMapper.add(modulePOJO)>0){
				flag=true;
			}
		}
		return flag;
	}
	
	@Override
	public int addRoleModules(List<RoleModulePOJO> list) throws Exception {
	    return moduleMapper.addRoleModules(list);
	}
	@Override
	public List<ModulePOJO> getList4Role(ModulePOJO modulePOJO) throws Exception {
	    return moduleMapper.getList4Role(modulePOJO);
	}
}