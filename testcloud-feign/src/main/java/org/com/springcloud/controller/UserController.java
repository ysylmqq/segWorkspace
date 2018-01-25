package org.com.springcloud.controller;

import org.com.springcloud.entity.User;
import org.com.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *	通过feign的方式来进行调用， 添加一个interface接口层，在controller当中调用接口当中
 *	的方法 
 *
 */
@RestController
public class UserController {

	@Autowired
	private  UserService userService;
	
	@GetMapping("/getList")
	public String getList(){
		System.err.println("feign getList");
		return userService.getList();
	}
	
	@GetMapping("/getUserById/{id}")
	public User getUserById(@PathVariable Long id){
		System.err.println("feign getUserById "+id);
		return userService.getUserById(id);
	}
}
