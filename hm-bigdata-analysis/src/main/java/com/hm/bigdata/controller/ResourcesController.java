package com.hm.bigdata.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hm.bigdata.comm.SystemConst;
import com.hm.bigdata.comm.SystemException;
import com.hm.bigdata.service.SubcoService;
import com.hm.bigdata.util.StringUtils;

import ldap.objectClasses.CommonModule;
import ldap.objectClasses.CommonOperator;
import ldap.objectClasses.CommonRole;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

@Controller
public class ResourcesController extends BaseController{
	
	@Autowired
	@Qualifier("subcoService")
	private SubcoService subcoService; 
	
	/**
	 * 查询左侧的导航栏
	 * @param commonModule
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	/*@RequestMapping(value = "/moduleList", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> moduleList(@Validated CommonModule commonModule, BindingResult bindingResult,HttpServletRequest request){
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try {
			CommonOperator operator = (CommonOperator) request.getSession().getAttribute(SystemConst.ACCOUNT_USER);
			if(operator==null){
				return new HashMap<String, Object>();
			}
			OpenLdap ldap = OpenLdapManager.getInstance();
			String filt = ""; // 
			if(commonModule.getModuleid()==null||commonModule.getModuleid().equals("")){
				filt = "(&(objectclass=commonModule)(systemid="+SystemConst.SYSTEMID+"))";
			}else{
				filt = "(&(objectclass=commonModule)(systemid="+SystemConst.SYSTEMID+")(parentmoduleid="+commonModule.getModuleid()+"))";
			}
			List<CommonModule> moduleList = ldap.searchModule("", filt);
			List<String> modules = new ArrayList<String>();
			List<String> roleids = null;
			if(request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS)!=null){
				roleids = (List<String>)request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS);
			}
			if(roleids==null ){
				return new HashMap<String, Object>();
			}
			for (String roleid : roleids) {
				String filter = "(&(objectclass=commonRole)(roleid="+roleid+"))";
				List<CommonRole> roleList = ldap.searchRole("", filter);
				for(CommonRole role:roleList){
					if(!modules.contains(role.getModuleids())){
						modules.addAll(role.getModuleids());
					}
				}
			}
			Map<String, Map<String, Object>> temp = new HashMap<String, Map<String, Object>>();
			list = new ArrayList<Map<String, Object>>();
			for(int i=0;i<moduleList.size();i++){
				CommonModule module = moduleList.get(i);
				String menuId = module.getModuleid();
				if(modules!=null&&!modules.contains(menuId)){
					continue;
				}
				String parentModuleId = module.getParentmoduleid();
				Map<String, Object> item = new HashMap<String, Object>();			
				temp.put(menuId, item);
				if(parentModuleId == null){
					list.add(item);
				}
				item.put("description", module.getModulename());
				item.put("url", module.getControl1());
				item.put("type", module.getControl2());
				item.put("permissionid", menuId);
				item.put("parentid", module.getParentmoduleid());
				initControl2(item, module.getControl2());
				
				Map<String, Object> parent = temp.get(parentModuleId);
				if(parent != null){
					Object childList = parent.get("items");
					if(childList == null){
						parent.put("items", new ArrayList<Map<String, Object>>());
					}
					List<Map<String, Object>> childListAgin = (List<Map<String, Object>>) parent.get("items");
					childListAgin.add(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> itemsMap = new HashMap<String,Object>();
		itemsMap.put("main_menu_items", list);
		
		map.put("success", true);
		map.put("datas", itemsMap);
		return map;
	}*/
	
	@RequestMapping(value = "/moduleList", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> moduleList(@Validated CommonModule commonModule, BindingResult bindingResult,HttpServletRequest request){
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try {
			CommonOperator operator = (CommonOperator) request.getSession().getAttribute(SystemConst.ACCOUNT_USER);
			if(operator==null){
				return new HashMap<String, Object>();
			}
			OpenLdap ldap = OpenLdapManager.getInstance();
			String filt = ""; // 
			if(commonModule.getModuleid()==null||commonModule.getModuleid().equals("")){
				filt = "(&(objectclass=commonModule)(systemid="+SystemConst.SYSTEMID+"))";
			}else{
				filt = "(&(objectclass=commonModule)(systemid="+SystemConst.SYSTEMID+")(parentmoduleid="+commonModule.getModuleid()+"))";
			}
			List<CommonModule> moduleList = ldap.searchModule("", filt);
			List<String> modules = new ArrayList<String>();
			List<String> roleids = null;
			if(request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS)!=null){
				roleids = (List<String>)request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS);
			}
			if(roleids==null ){
				return new HashMap<String, Object>();
			}
			for (String roleid : roleids) {
				String filter = "(&(objectclass=commonRole)(roleid="+roleid+"))";
				List<CommonRole> roleList = ldap.searchRole("", filter);
				for(CommonRole role:roleList){
					if(!modules.contains(role.getModuleids())){
						modules.addAll(role.getModuleids());
					}
				}
			}
			Map<String, Map<String, Object>> temp = new HashMap<String, Map<String, Object>>();
			list = new ArrayList<Map<String, Object>>();
			for(int i=0;i<moduleList.size();i++){
				CommonModule module = moduleList.get(i);
				String menuId = module.getModuleid();
				if(modules!=null&&!modules.contains(menuId)){
					continue;
				}
				String parentModuleId = module.getParentmoduleid();
				Map<String, Object> item = new HashMap<String, Object>();			
				temp.put(menuId, item);
				if(parentModuleId == null){
					list.add(item);
				}
				item.put("description", module.getModulename());
				item.put("url", module.getControl1());
				item.put("type", module.getControl2());
				item.put("permissionid", menuId);
				item.put("parentid", module.getParentmoduleid());
				initControl2(item, module.getControl2());
				
				Map<String, Object> parent = temp.get(parentModuleId);
				if(parent != null){
					Object childList = parent.get("items");
					if(childList == null){
						parent.put("items", new ArrayList<Map<String, Object>>());
					}
					List<Map<String, Object>> childListAgin = (List<Map<String, Object>>) parent.get("items");
					childListAgin.add(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> itemsMap = new HashMap<String,Object>();
		
		itemsMap.put("main_menu_items", listToMap(list));
		map.put("success", true);
		map.put("datas", itemsMap);
		return map;
	}
	
	
	public List<Map<String, Object>> listToMap(List<Map<String, Object>> list){
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		int c = 0;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			c++;
			Map<String, Object> resultMap = new HashMap<String,Object>();
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			resultMap.put("permissionid", map.get("permissionid"));
			resultMap.put("description", map.get("description"));
			resultMap.put("url", map.get("url"));
			resultMap.put("categoryid", "1");
			resultMap.put("isvalid", "1");
			resultMap.put("itemname",  String.valueOf(System.currentTimeMillis()+c));
			resultMap.put("parentid", map.get("parentid") == null ? '0': map.get("parentid"));
			
			List<Map<String, Object>> item = (List<Map<String, Object>>) map.get("items");

			if (item != null && item.size() > 0) {
				resultMap.put("havsubitems", "1");
			}
			resultList.add(resultMap);
			
			if (item != null && item.size() > 0) {
				for (Iterator iterator2 = item.iterator(); iterator2.hasNext();) {
					Map<String, Object> resultMap2 = new HashMap<String,Object>();

					Map<String, Object> map2 = (Map<String, Object>) iterator2.next();
					resultMap2.put("permissionid", map2.get("permissionid"));
					resultMap2.put("description", map2.get("description"));
					resultMap2.put("url", map2.get("url"));
					resultMap2.put("categoryid", "1");
					resultMap2.put("havsubitems", "0");
					resultMap2.put("isvalid", "1");
					resultMap2.put("parentid", map2.get("parentid") == null ? '0': map2.get("parentid"));
					resultList.add(resultMap2);
				}
			}
		}
		return resultList;
	}
	
	
	private void initControl2(Map<String, Object> item, String control2){
		if(StringUtils.isBlank(control2)){
			return;
		}
		String []configs = control2.split(",");
		for(String config: configs){
			int sp = config.indexOf(":");
			if(sp > 0){
				item.put(config.substring(0, sp), config.substring(sp + 1));
			}
		}
	}
	
}
