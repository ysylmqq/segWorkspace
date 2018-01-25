package org.com.springcloud.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * 使用命令的方式
 * setter是HystrixCommand命令当中封装的参数，必须要写，不然会报错
 *
 */
public class UserCommand  extends HystrixCommand<String>{

	private RestTemplate restTemplate;
	
	public UserCommand(Setter setter) {
		super(setter);
		this.restTemplate = new RestTemplate();
	}

	@Override
	protected String run() throws Exception {
		int sleep = new Random().nextInt(3000);
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//return restTemplate.getForEntity("http://PROVIDER/getList/", String.class).getBody();
		return restTemplate.getForEntity("http://localhost:8081/getList/", String.class).getBody();
	}

	@Override
	protected String getFallback() {
		System.err.println("getFallBack");
		return super.getFallback();
	}

}
