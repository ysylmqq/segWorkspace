package com.hm.bigdata.dao;

import java.util.List;

import com.hm.bigdata.entity.ldap.Resources;

public interface ResourcesDao{
	
	List<Resources> findAll();

}
