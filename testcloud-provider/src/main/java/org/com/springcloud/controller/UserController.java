package org.com.springcloud.controller;

import java.util.List;
import java.util.Map;

import org.com.springcloud.entity.User;
import org.com.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *	服务消费方 
 *
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@GetMapping("/getUser/{id}")
	public User getUser(@PathVariable Long id){
		System.err.println("远程服务器");
		return userService.getUserById(id);
	}
	
	@GetMapping("/getUserById")
	public User getUserById(@RequestParam(value="id") Long id){
		System.err.println("远程服务器  "+id);
		return userService.getUserById(id);
	}
	
	@GetMapping("/getList")
	public List<Map<String, Object>> getList(){
		System.err.println("远程服务器");
		ServiceInstance instance = discoveryClient.getLocalServiceInstance();
		System.err.println(instance.getHost()+"  "+instance.getServiceId()+" "+instance.getMetadata());
		return userService.getDbType();
	}
}
