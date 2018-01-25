package org.com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.route.SimpleHostRoutingFilter;
import org.springframework.context.annotation.Bean;

@SpringCloudApplication
@EnableZuulProxy
public class SpringCloudZuulMain {
	
	@Bean
	public AccessFilter accessFilter(){
		return new AccessFilter();
	}

	@Bean
	public ErrorFilter errorFilter(){
		return new ErrorFilter();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudZuulMain.class, args);
	}
}
