package org.com.springcloud.service;

import java.util.List;
import java.util.Map;

import org.com.springcloud.entity.User;

public interface UserService {

	public User getUserById(Long id);
	
	public List<Map<String, Object>> getDbType();

}
