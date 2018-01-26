package com.chinaGPS.gtmp.action.permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.ModulePOJO;
import com.chinaGPS.gtmp.entity.RoleModulePOJO;
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.service.IModuleService;
import com.chinaGPS.gtmp.service.IRoleService;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 文 件 名 :RoleAction.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：zfy
 * 创 建 日 期：2013-4-28
 * 描 述： 角色管理控制器
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0
 */

@Scope("prototype")
@Controller 
public class RoleAction extends BaseAction implements ModelDriven<RolePOJO> {
	private static final long serialVersionUID = 173814897567529633L;
	private Logger logger = LoggerFactory.getLogger(RoleAction.class);
	
	@Resource
    private IRoleService roleService;
    @Resource
    private IModuleService moduleService;
    @Resource
    private RolePOJO rolePOJO;
    @Resource
	private PageSelect pageSelect;
	
	private int page;
	private int rows;
	//设置已读集合
	private List<Long> idList;
	
	/**
	 * 查询角色信息，返回分页数据
	 */
	public String search() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			pageSelect.setPage(page);
			pageSelect.setRows(rows);
			result = roleService.getRoles(rolePOJO, pageSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
		return NONE;
	}
	
	/**
	 * 查询角色信息，返回分页数据
	 */
	public String getList() {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("datas", roleService.getList(rolePOJO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
		return NONE;
	}
	
	/**
	 * 获得角色信息，返回到修改页面
	 * @return
	 */
	public String getById(){
		try {
			rolePOJO=roleService.getById(rolePOJO.getRoleId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "edit";
	}
	
	/**
	 * 获得角色信息，返回到设置模块页面
	 * @return
	 */
	public String getRoleById(){
		try {
			rolePOJO=roleService.getById(rolePOJO.getRoleId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return NONE;
	}
	
	/**
	 * 新增或修改角色
	 * @return
	 */
	@OperationLog(description = "保存角色")
	public String saveOrUpdate() {
		boolean flag = true;
		String msg = OP_SUCCESS;
		try {
			if (roleService.getRoleByRoleName(rolePOJO) == null) {

				/*
				 * if(rolePOJO.getRoleId()!=null){
				 * logger("角色修改(roleName="+rolePOJO.getRoleName()+")");//写入操作日志
				 * }else{
				 * logger("角色新增(roleName="+rolePOJO.getRoleName()+")");//写入操作日志 }
				 */
				roleService.saveOrUpdate((rolePOJO));
				flag = true;
				msg = OP_SUCCESS;
			} else {
				flag = false;
				msg = "该角色名称已存在";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = false;
			msg = OP_FAIL;
		}
		renderMsgJson(flag, msg);
		return NONE;
	}
	
	/**
	 * 修改角色
	 * @return
	 */
	@OperationLog(description = "角色修改")
	public String update() {
		boolean flag = true;
		String msg = OP_SUCCESS;
		try {
			roleService.saveOrUpdate((rolePOJO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = false;
			msg = OP_FAIL;
		}
		renderMsgJson(flag, msg);
		return NONE;
	}
	
	@OperationLog(description = "角色分配模块")
	public void addRoleModules() {
		boolean flag = true;
		String msg = OP_SUCCESS;
		try {
			Long roleId = rolePOJO.getRoleId();
			List<RoleModulePOJO> list = new ArrayList<RoleModulePOJO>();
			if (roleId != null) {
				ModulePOJO modulePOJO = new ModulePOJO();
				modulePOJO.setRoleId(roleId);
				// 先移除
				moduleService.removeRoleModule(modulePOJO);
				if (idList != null && !idList.isEmpty()) {
					RoleModulePOJO roleModulePOJO = null;
					for (Long moduleId : idList) {
						roleModulePOJO = new RoleModulePOJO();
						roleModulePOJO.setRoleId(roleId);
						roleModulePOJO.setModuleId(moduleId);
						list.add(roleModulePOJO);
					}
					// 后添加
					moduleService.addRoleModules(list);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = false;
			msg = OP_FAIL;
		}

		renderMsgJson(flag, msg);
	}
	

	@Override
	public RolePOJO getModel() {
	    return rolePOJO;
	}

	public int getPage() {
	    return page;
	}

	public void setPage(int page) {
	    this.page = page;
	}

	public int getRows() {
	    return rows;
	}

	public void setRows(int rows) {
	    this.rows = rows;
	}

	public List<Long> getIdList() {
	    return idList;
	}

	public void setIdList(List<Long> idList) {
	    this.idList = idList;
	}
	
}