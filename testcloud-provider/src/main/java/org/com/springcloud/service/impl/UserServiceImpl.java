package org.com.springcloud.service.impl;

import java.util.List;
import java.util.Map;

import org.com.springcloud.dao.UserDao;
import org.com.springcloud.entity.User;
import org.com.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService{

	@Autowired 
	private UserDao userDao;
	
	@Override
	public User getUserById(Long id) {
		return this.userDao.getUserById(id);
	}

	@Override
	public List<Map<String, Object>> getDbType() {
		return this.userDao.getDbType();
	}
}
