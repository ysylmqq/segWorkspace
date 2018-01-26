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
import com.chinaGPS.gtmp.entity.TreeNode;
import com.chinaGPS.gtmp.service.IModuleService;
import com.chinaGPS.gtmp.util.OperationLog;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 文 件 名 :ModuleAction.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：zfy
 * 创 建 日 期：2012-4-28
 * 描 述： 模块管理控制器
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0
 */

@Scope("prototype")
@Controller 
public class ModuleAction extends BaseAction implements ModelDriven<ModulePOJO> {
	private static final long serialVersionUID = 2351883867958639630L;
	private static Logger logger = LoggerFactory.getLogger(ModuleAction.class);
	
	@Resource
    private IModuleService moduleService;
    @Resource
	private ModulePOJO modulePOJO;

	/**
	 * 查询模块信息，返回分页数据
	 */
	public String search() {
		List<TreeNode> mapAreaList = new ArrayList<TreeNode>();
		try {
			List<ModulePOJO> moduleList = moduleService.getList(modulePOJO);
			Map mapRreaMap = new HashMap();
			for (ModulePOJO module : moduleList) {
				TreeNode node = new TreeNode();
				node.setId(module.getModuleId().toString());
				node.setText(module.getModuleName());
				if (module.getParentId() != null) {
					node.setParentId(module.getParentId().toString());
				}
				mapRreaMap.put(module.getModuleId(), node);

			}
			for (ModulePOJO module2 : moduleList) {
				if (module2.getParentId() != null) { // 有父节点
					TreeNode pnode = (TreeNode) mapRreaMap.get(module2
							.getParentId());
					TreeNode cnode = (TreeNode) mapRreaMap.get(module2
							.getModuleId());
					pnode.addChild(cnode);

				} else {// 无父节点
					mapAreaList.add((TreeNode) mapRreaMap.get(module2
							.getModuleId()));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(mapAreaList);
		return NONE;
	}
	/**
	 * 查询模块信息，返回分页数据
	 */
	public String search4Role() {
		List<TreeNode> mapAreaList = new ArrayList<TreeNode>();
		try {
			List<ModulePOJO> moduleList = moduleService
					.getList4Role(modulePOJO);
			Map mapRreaMap = new HashMap();
			for (ModulePOJO module : moduleList) {
				TreeNode node = new TreeNode();
				node.setId(module.getModuleId().toString());
				node.setText(module.getModuleName());
				if (module.getParentId() != null) {
					node.setParentId(module.getParentId().toString());
				}
				if ("1".equals(module.getUrl())) {
					node.setChecked(true);
				}
				mapRreaMap.put(module.getModuleId(), node);

			}
			for (ModulePOJO module2 : moduleList) {
				if (module2.getParentId() != null) { // 有父节点
					TreeNode pnode = (TreeNode) mapRreaMap.get(module2
							.getParentId());
					TreeNode cnode = (TreeNode) mapRreaMap.get(module2
							.getModuleId());
					if (pnode != null && cnode != null) {
						pnode.addChild(cnode);
					}

				} else {// 无父节点
					mapAreaList.add((TreeNode) mapRreaMap.get(module2
							.getModuleId()));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(mapAreaList);
		return NONE;
	}
	
	/**
	 * 查询某个模块信息，返回到修改页面
	 */
	public String getById() {
		try {
			modulePOJO = moduleService.getById(modulePOJO.getModuleId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(modulePOJO);
		return NONE;
	}
	
	/**
	 * 新增或修改模块
	 * @return
	 */
	@OperationLog(description = "保存模块")
	public String saveOrUpdate() {
		boolean flag = true;
		String msg = OP_SUCCESS;
		try {
			if (moduleService.getModuleByModuleName(modulePOJO) == null) {
				moduleService.saveOrUpdate(modulePOJO);
			} else {
				flag = false;
				msg = "该模块名称已存在";
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
	 * 修改模块
	 * @return
	 */
	@OperationLog(description = "修改模块")
	public String update() {
		boolean flag = true;
		String msg = OP_SUCCESS;
		try {
			moduleService.saveOrUpdate(modulePOJO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = false;
			msg = OP_FAIL;
		}
		renderMsgJson(flag, msg);
		return NONE;
	}
	
	/**
	 * 分配模块
	 * @return
	 */
	@OperationLog(description = "分配模块")
	public String setModules() {
		boolean flag = true;
		String msg = OP_SUCCESS;
		try {
			moduleService.removeRoleModule(modulePOJO);
			String moduleIds = modulePOJO.getModuleIds();
			String ids[] = moduleIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				ModulePOJO newModule = new ModulePOJO();
				newModule.setRoleId(modulePOJO.getRoleId());
				newModule.setModuleId(Long.valueOf(ids[i]));
				moduleService.addRoleModule(newModule);
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
	 * 获得模块树
	 * @return
	 */
	public String getTree() {
		List<Map> nodelist = new ArrayList<Map>();
		try {
			List<ModulePOJO> modulelist = moduleService.getChildren(-1L);
			List<ModulePOJO> checkedlist = moduleService.getChecked(modulePOJO.getRoleId());
			nodelist = this.getChildrenTree(modulelist, checkedlist);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(nodelist);
		return NONE;
	}
	
	private List<Map> getChildrenTree(List<ModulePOJO> modulelist, List<ModulePOJO> checkedlist) {
		List<Map> nodelist = new ArrayList();
		try {
			for (ModulePOJO md : modulelist) {
				boolean b = false;
				for (ModulePOJO checkedmd : checkedlist) {
					if (checkedmd.getModuleId().equals(md.getModuleId())) {
						b = true;
					}
				}
				Map node = new HashMap();
				node.put("id", md.getModuleId());
				node.put("text", md.getModuleName());
				node.put("ischecked", b);

				List<ModulePOJO> list = moduleService.getChildren(md.getModuleId());
				if (list == null || list.size() < 1) {
					node.put("isleaf", true);
				} else {
					node.put("isleaf", false);
					node.put("children", this.getChildrenTree(list, checkedlist));
				}
				nodelist.add(node);
			}
			return nodelist;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public ModulePOJO getModel() {
	    return modulePOJO;
	}
	
}