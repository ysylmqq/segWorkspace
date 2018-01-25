package org.com.springcloud.service;

import org.com.springcloud.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *	添加了一个Service层，来进行调用 
 *
 */
@FeignClient(name="PROVIDER",fallback=UserServiceFallBack.class)
public interface UserService {
	
	/**
	 *	调用provider当中的方法 
	 */
	@RequestMapping("/getList")
	public String getList();
	
	@RequestMapping("/getUserById")
	public User getUserById(@RequestParam(value="id") Long id); 
}
