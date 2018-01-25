package org.com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @EnableDiscoveryClient 把服务注册到eureka当中，并且发现eureka当中可用的服务
 * @LoadBalanced 客户端负载均衡，均分请求，是对spring当中RestTemplete的高级封装
 */
@SpringBootApplication  //
@EnableDiscoveryClient
@EnableCircuitBreaker // 断路器的配置
@EnableHystrixDashboard
@EnableZuulProxy
public class SpringCloudConsumerMain {

	/**
	 * 通过java的方式注入到spring的IOC容器当中
	 * @return
	 */
	@Bean
	@LoadBalanced 
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConsumerMain.class, args);
	}
}
