package com.hm.bigdata.dao;

import com.hm.bigdata.entity.ldap.Users;

public interface UsersDao{
	public Users findByName(String name);
}
