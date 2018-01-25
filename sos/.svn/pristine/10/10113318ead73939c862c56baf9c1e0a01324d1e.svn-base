package com.gboss.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gboss.dao.UsersDao;
import com.gboss.pojo.ldap.Resources;
import com.gboss.pojo.ldap.Roles;
import com.gboss.pojo.ldap.Users;

//2
public class MyUserDetailServiceImpl implements UserDetailsService {
	
	private UsersDao usersDao;
	public UsersDao getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}
	
	//登录验证
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//这里应该可以不用再查了
		Users users = this.usersDao.findByName(username);
		
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users);
		
		boolean enables = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		//封装成spring security的user
		User userdetail = new User(users.getName(), users.getPassword(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
		return userdetail;
	}
	
	//取得用户的权限
	public Set<GrantedAuthority> obtionGrantedAuthorities(Users user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		List<Resources> resources = new ArrayList<Resources>();
		Set<Roles> roles = user.getRoles();
		
		for(Roles role : roles) {
			Set<Resources> tempRes = role.getResources();
			for(Resources res : tempRes) {
				resources.add(res);
			}
		}
		System.out.println("-------------------------");
		for(Resources res : resources) {
			System.out.println(res.getName());
			authSet.add(new GrantedAuthorityImpl(res.getName()));
		}
		System.out.println("-------------------------");
		return authSet;
	}
}
