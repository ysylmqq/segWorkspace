package org.com.springcloud.service;

import java.util.Random;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

@Service
public class UserService {

	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 *	缓存结果 
	 *genericCacheKey 缓存策略 ，对应类当中的方法
	 */
	@CacheResult(cacheKeyMethod="genericCacheKey")
	public String testCache(){
		return restTemplate.getForEntity("http://PROVIDER/getList/", String.class).getBody();
	}
	
	@HystrixCommand(fallbackMethod="getListBack")//使用的是异步方式
	public Future<String> getListAsync(){
		System.err.println("异步方式调用");
		return new AsyncResult<String>(){
			@Override
			public String invoke() {
				return restTemplate.getForEntity("http://PROVIDER/getList/", String.class).getBody();
			}
		};
		//return restTemplate.getForEntity("http://PROVIDER/getList/", String.class).getBody();
	}
	
	//默认2s没有返回时 执行getListBack方法。
	//@CacheResult(cacheKeyMethod="genericCacheKey") 出现错误 是因为断路器开启了
	@HystrixCommand(fallbackMethod="getListBack")//默认使用的是同步的方式 
	public String getList(){
		int sleep = new Random().nextInt(1000);
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return restTemplate.getForEntity("http://PROVIDER/getList/", String.class).getBody();
	}
	
	/**
	 * 服务降级方法，必须和服务方法在同一个类当中，方法的修饰词可以是public,private,protected
	 * 对服务降级方法可以继续添加@HystrixCommand命令
	 * @throws InterruptedException 
	 * 
	 */
	@HystrixCommand(fallbackMethod="error")
	public String getListBack(Throwable e) throws InterruptedException{
		int sleep = new Random().nextInt(1000);
		Thread.sleep(sleep);
		System.err.println(e.getMessage());
		return "getListBackError";
	}
	
	public String error(Throwable e){
		System.err.println(e.getMessage());
		return "error";
	}
	
	//缓存all
	public String genericCacheKey(){
		return "test";
	}
}
