package org.com.springcloud.dao;


import java.util.List;
import java.util.Map;

import org.com.springcloud.entity.User;

public interface UserDao {

	public User getUserById(Long id);
	
	public List<Map<String, Object>> getDbType();
}
