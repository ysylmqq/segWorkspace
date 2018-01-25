package com.hm.bigdata.dao.impl;

import java.util.ArrayList;
import java.util.List;

import ldap.objectClasses.CommonModule;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import com.hm.bigdata.dao.ResourcesDao;
import com.hm.bigdata.entity.ldap.Resources;



public class ResourcesDaoImpl implements ResourcesDao {

	@Override
	public List<Resources> findAll() {
		OpenLdap ldap = OpenLdapManager.getInstance();
		List<CommonModule> list = ldap.searchModule("", "(objectclass=commonModule)");
		List<Resources> resList = new ArrayList<Resources>();
		for(CommonModule module:list){
			Resources res = new Resources();
			res.setUrl(module.getControl1());
			res.setName(module.getModulename());
			resList.add(res);
		}
		return resList;
	}


}
