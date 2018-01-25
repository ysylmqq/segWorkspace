package com.gboss.dao;

import com.gboss.pojo.ldap.Users;

public interface UsersDao{
	public Users findByName(String name);
}
