package org.com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class SpringCloudDashcloudMain {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudDashcloudMain.class, args);
	}
}
