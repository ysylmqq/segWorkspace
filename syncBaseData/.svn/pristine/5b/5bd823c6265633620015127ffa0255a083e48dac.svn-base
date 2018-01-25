package com.gboss.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ldap.objectClasses.CommonModule;
import ldap.objectClasses.CommonOperator;
import ldap.objectClasses.CommonRole;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import com.gboss.dao.UsersDao;
import com.gboss.pojo.ldap.Resources;
import com.gboss.pojo.ldap.Roles;
import com.gboss.pojo.ldap.Users;

public class UsersDaoImpl implements UsersDao {

	 
	public Users findByName(String name) {
		Users users = new Users();
		OpenLdap ldap = OpenLdapManager.getInstance();
		CommonOperator user = ldap.getOperator(name);
		users.setName(user.getLoginname());
		List<String> roleids = user.getRoleid();
		Set<Roles> roles = new HashSet<Roles>();
		String filter = "(&(objectclass=commonRole)(|";
		for(String str:roleids){
			filter += "(roleid="+str+")";
		}
		filter += "))";
		List<CommonRole> roleList = ldap.searchRole("", filter);
		for(CommonRole commonRole:roleList){
			Roles role = new Roles();
			List<String> moduleids = commonRole.getModuleids();
			Set<Resources> resources = new HashSet<Resources>();
			filter = "(&(objectclass=commonModule)(|";
			for(String str:moduleids){
				filter += "(moduleid="+str+")";
			}
			filter += "))";
			List<CommonModule> moduleList = ldap.searchModule("", filter);
			for(CommonModule module:moduleList){
				Resources resource = new Resources();
				resource.setUrl(module.getControl1());
				resource.setName(module.getModulename());
				resources.add(resource);
			}
			role.setName(commonRole.getRolename());
			role.setResources(resources);
			roles.add(role);
		}
		users.setRoles(roles);
		return users;
	}

}
