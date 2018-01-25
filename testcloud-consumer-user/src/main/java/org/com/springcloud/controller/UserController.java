package org.com.springcloud.controller;

import java.util.concurrent.ExecutionException;

import org.com.springcloud.entity.User;
import org.com.springcloud.service.UserCommand;
import org.com.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * 服务提供方
 * 	1、由于采用硬编码的方法把远程服务的地址写在代码当中，以及服务器端down时，服务的消费方不能及时的发现，所以出现了
 * 	    注册中心(服务注册表)，类似于dubbox当中的zookeeper。
 * 	2、服务注册表：
 * 			服务注册表是一个记录当前可用服务实例的网络信息的数据库，是服务发现机制的核心。
 * 			服务注册表提供查询API和管理API，使用查询API获得可用的服务实例，使用管理API实现注册和注销；
 *		在服务启动时，把服务的相关信息注册到服务注册表
 *		定时的进行心跳检测
 *  3、服务的发现方式有两种
 *  	A>客户端发现服务 Eureka 和zookeeper
 *      B>服务器端发现 Consul + nginx
 *    springCloud对Eureka支持最好，然后是Consul,最差的是zookeeper
 *  4、EureKa重点详解
 *  	Eureka是Netflix开发的服务发现框架，本身是一个基于REST的服务，主要用于定位运行在AWS域中的中间层服务，
 *  	以达到负载均衡和中间层服务故障转移的目的。Spring Cloud将它集成在其子项目spring-cloud-netflix中，
 *  	以实现Spring Cloud的服务发现功能。
 *  5、ribbon：主要是作为客户端负载均衡来实现的。
 *  	在spring的RestTemplete模板的基础之上进行了封装，实现了客户端的负载均衡。
 *  	服务提供者在往Eureka注册中心注册服务时，把服务实例serviceInstance发送给Eureka， 在Eureka当中存放的还是双层的map。
 *  	Map<application.name ,Map<eureka.instance.name ,服务实例信息>> 同一个服务的服务名相同，当中包含多个服务实例，
 *  	因为每一个服务都是使用集群的方式来进行部署的。
 *      Eureka注册中心还会维持一个Map，存放的是每个客户端的心跳，用来检测客户端是否可用。心跳是有客户端主动发送的，eureka只是
 *      更新就行，过期的直接踢出。
 *      同时客户端也会定期的从Eureka注册中心获取可用的服务实例列表，来实现客户端的负载均衡，默认是轮训的方式。
 *      根据服务名来获取可用的服务。http://PROVIDER/getList/
 *  6、Hystrix:短路器,起着保护整个系统的功能，防止系统崩溃
 *  	使用：在启动了类上添加 @EnableCircuitBreaker注解
 *        a、直接在调用的方法上添加@HystrixCommand(fallbackMethod="error") 并且指定断路器执行的方法
 *        b、通过继承HystrixCommand命令来实现run方法
 */
@RestController
public class UserController {

	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/getUser/{id}")
	public User getUserById(@PathVariable Long id){
		System.err.println("本地服务器");
		//return restTemplate.getForObject("http://localhost:8080/getUser/"+id, User.class);
		return restTemplate.getForObject("http://PROVIDER/getUser/{1}/", User.class,id);
	}
	
	@GetMapping("/getList")
	public String getList(){
		System.err.println("本地服务器");
		//http://PROVIDER/getList/ 对应eureka当中的application应用户名，后端会自动进行负载均衡
		return restTemplate.getForEntity("http://PROVIDER/getList/", String.class).getBody();
	}
	
	
	@GetMapping("/consumer/testList")
	public String testList(){
		System.err.println("本地服务器");
		//http://PROVIDER/getList/ 对应eureka当中的application应用户名，后端会自动进行负载均衡
		return userService.getList();
	}
	
	/**
	 * 测试缓存 
	 */
	@GetMapping("/testCache")
	public String testCache(){
		System.err.println("本地服务器 testCache  "+System.currentTimeMillis());
		return userService.testCache();
	}
	
	/**
	 * 
	 * Hystrix异步调用
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@GetMapping("/getListAsync")
	public String getListAsync() throws InterruptedException, ExecutionException{
		System.err.println("本地服务器 getListAsync  "+System.currentTimeMillis());
		return userService.getListAsync().get();
	}
	
	
	/**
	 * 
	 * execute
	 */
	@GetMapping("/execute")
	public String execute() throws InterruptedException, ExecutionException{
		System.err.println("本地服务器 execute  "+System.currentTimeMillis());
		String str = new UserCommand(com.netflix.hystrix.HystrixCommand.Setter.withGroupKey(
		        HystrixCommandGroupKey.Factory.asKey("")).andCommandPropertiesDefaults(
		                HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(5000))).execute();
		return str;
	}
}
