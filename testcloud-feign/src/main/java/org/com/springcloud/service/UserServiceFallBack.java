package org.com.springcloud.service;

import org.com.springcloud.entity.User;

public class UserServiceFallBack implements UserService {

	@Override
	public String getList() {
		return "errorGetList";
	}

	@Override
	public User getUserById(Long id) {
		User user = new User();
		user.setAge("errorAge");
		user.setName("errorName");
		user.setSex("errorSex");
		return user;
	}

}
