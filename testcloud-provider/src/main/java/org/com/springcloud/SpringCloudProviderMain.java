package org.com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


/**
 * @EnableEurekaClient 这个注解只能Eureka(注册中心)可用, 在做Eurreka的高可用的时候用的
 * 对于往Eureka中心注册的服务时，使用@EnableDiscoveryClient
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
public class SpringCloudProviderMain {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate(){
		return  new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudProviderMain.class, args);
	}
}
