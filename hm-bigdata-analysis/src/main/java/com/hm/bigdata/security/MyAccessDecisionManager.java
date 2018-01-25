package com.hm.bigdata.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jasig.cas.client.util.AssertionHolder;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.hm.bigdata.dao.UsersDao;
import com.hm.bigdata.entity.ldap.Users;

//3
public class MyAccessDecisionManager implements AccessDecisionManager {
	
	private UsersDao usersDao;
	
	private MyUserDetailServiceImpl myUserDetailServiceImpl;
	
	private Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();

	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		if(configAttributes == null) {
			return;
		}
		//所请求的资源拥有的权限(一个资源对多个权限)
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		//用户所拥有的权限authentication,初始化
		if(authSet.size()==0){
			String username = AssertionHolder.getAssertion().getPrincipal().getName();
			Users user = this.usersDao.findByName(username);
			authSet = myUserDetailServiceImpl.obtionGrantedAuthorities(user);
		}
		while(iterator.hasNext()) {
			ConfigAttribute configAttribute = iterator.next();
			//访问所请求资源所需要的权限
			String needPermission = configAttribute.getAttribute();
			System.out.println("needPermission is " + needPermission);
			for(GrantedAuthority ga : authSet) {
				if(needPermission.equals(ga.getAuthority())) {
					return;
				}
			}
		}
		//没有权限让我们去捕捉
		throw new AccessDeniedException("没有权限访问!");
	}
	
	public UsersDao getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	public MyUserDetailServiceImpl getMyUserDetailServiceImpl() {
		return myUserDetailServiceImpl;
	}

	public void setMyUserDetailServiceImpl(
			MyUserDetailServiceImpl myUserDetailServiceImpl) {
		this.myUserDetailServiceImpl = myUserDetailServiceImpl;
	}
	
	public void reSetAuth(){
		authSet = new HashSet<GrantedAuthority>();
	}

	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
