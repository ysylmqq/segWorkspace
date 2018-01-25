package com.gboss.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.gboss.dao.ResourcesDao;
import com.gboss.pojo.ldap.Resources;

//1 加载资源与权限的对应关系
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	//由spring调用
	public MySecurityMetadataSource(ResourcesDao resourcesDao) {
		this.resourcesDao = resourcesDao;
		loadResourceDefine();
	}

	private ResourcesDao resourcesDao;
	
	private MyAccessDecisionManager myAccessDecisionManager;
	
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	//加载所有资源与权限的关系
	private void loadResourceDefine() {
		if(resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Resources> resources = this.resourcesDao.findAll();
			for (Resources resource : resources) {
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				//以权限名封装为Spring的security Object
				ConfigAttribute configAttribute = new SecurityConfig(resource.getName());
				configAttributes.add(configAttribute);
				resourceMap.put(resource.getUrl(), configAttributes);
			}
		}
		
		Set<Entry<String, Collection<ConfigAttribute>>> resourceSet = resourceMap.entrySet();
		//Iterator<Entry<String, Collection<ConfigAttribute>>> iterator = resourceSet.iterator();
		
	}
	//返回所请求资源所需要的权限
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		System.out.println("requestUrl is " + requestUrl);
		if("/".equals(requestUrl)){
			myAccessDecisionManager.reSetAuth();
		}
		if(resourceMap == null) {
			loadResourceDefine();
		}
		return resourceMap.get(requestUrl);
	}

	public ResourcesDao getResourcesDao() {
		return resourcesDao;
	}

	public void setResourcesDao(ResourcesDao resourcesDao) {
		this.resourcesDao = resourcesDao;
	}
	
	public MyAccessDecisionManager getMyAccessDecisionManager() {
		return myAccessDecisionManager;
	}
	public void setMyAccessDecisionManager(
			MyAccessDecisionManager myAccessDecisionManager) {
		this.myAccessDecisionManager = myAccessDecisionManager;
	}
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}
