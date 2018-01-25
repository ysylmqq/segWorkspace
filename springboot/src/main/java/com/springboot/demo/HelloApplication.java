package com.springboot.demo;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * springboot的核心方法
 * 简化了配置文件的配置，全部使用了Java的方式来实现
 * 1、 这个方法即是一个配置文件，也是一个controller
 *  @SpringBootApplication:是springboot的核心注解，主要目的是开启自动配置。
 *    主要包含:
 *    		@ComponentScan:扫描那些包。默认扫描的是HelloApplication所在的包以及子包。
 *  	    @Configuration:相当于这个类是application.xml配置文件。
 *  			注意:在Spring Boot项目中推荐使用@SpringBootConfiguration替代@Configuration,前者是后者的封装
 *  		@EnableAutoConfiguration:启动自动注入的功能
 *  			注意:启用自动配置，该注解会使Spring Boot根据项目中依赖的jar包自动配置项目的配置项：
				    如：我们添加了spring-boot-starter-web的依赖，项目中也就会引入SpringMVC的依赖，
				    Spring Boot就会自动配置tomcat和SpringMVC
 *  2、全局配置文件:
 *  	spring boot使用一个全局的配置文件 application.properties或application.yml,
 *  	在resources目录下或者类路径下的/config下，一般我们放到resources下
 *  3、支持xml方式的配置
 *  	虽然springboot提倡零配置，但是有时候不得不用xml方式进行配置
 *  	@ImportResource({"classpath:tes1.xml","classpath:tes2.xml"})来实现
 *  4、自动装配:springboot的核心原理
 *  	Spring Boot在进行SpringApplication对象实例化时会加载org.springframework.boot包当中的
 *  	META-INF/spring.factories文件，同时也会把spring-boot-autoconfigure当中的
 * 		META-INF/spring.factories将该配置文件中的配置载入到Spring容器。根据类路径当中是否包含相应的class文件来决定是否
 * 		初始化这个主键(比如redis)
 *      在判断类路径当中是否包含class文件是根据条件注解来实现的
 *      	 @ConditionalOnClass({ JedisConnection.class, RedisOperations.class, Jedis.class })
 *  	上面如果类路径当中包含JedisConnection，RedisOperations，Jedis这个三个类，springboot就自动装载redis
 *  
 */
//@Controller
@RestController // rest风格
@SpringBootApplication(exclude = { RedisAutoConfiguration.class })
public class HelloApplication {

	@Autowired
	private RestTemplate restTemplate;
	
	@Bean
	public RestTemplate restTemplate(){
		return  new RestTemplate();
	}
	
	
    @RequestMapping("hello")
    @ResponseBody
    public String hello() {
    	//restTemplate.getForObject(url, responseType)
        return "hello world！ ysy！";
    }
    
    @GetMapping("/getTest/{id}")
    public String getTest(@PathVariable Long id) {
    	//通过restTemplate去调用其他的服务
    	System.err.println("id "+id);
        return "hello world！ ysy！";
    }
    
    @GetMapping("ysy")
    public String helloJsp() {
        return "hello";
    }

    /**
     * 消息转发器，类似于xml当中的配置
     * @return
     */
   @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
	   //默认UTF-8
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    public static void main(String[] args) {
         SpringApplication.run(HelloApplication.class, args);
       /* SpringApplication application = new SpringApplication(HelloApplication.class);
        application.setBannerMode(Mode.OFF); // 关闭banner
        application.run(args);*/
    }

}
