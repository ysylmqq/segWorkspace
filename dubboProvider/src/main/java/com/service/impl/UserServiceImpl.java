package com.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.entity.User;
import com.service.UserService;

/**
 * dubbox:分布式服务治理框架
 * 笔记:
 * 1、dubbo和rest风格区别：
 * 			dubbo是通过从ioc容器当中获取service来执行，内部调用
 * 		    rest风格是直接就可以通过路径来进行访问的，对外调用
 * 2、大部分情况下,使用POST实现增改,GET实现删查询
 * 3、建议将annotation放到服务实现类，这样annotation和java实现代码位置更接近，更便于开发和维护。
 *   另外更重要的是，我们一般倾向于避免对接口的污染，保持接口的纯净性和广泛适用性。
 *   如果是rest风格，注解要放到接口当中，不放到接口当中，可能会出现问题。
 * 4、数据格式:可以使用URL后缀（.json和.xml）来指定想用的数据格式.
 * 		在annotation（@Produces）中既可以用MediaType.TEXT_XML，也可以用MediaType.APPLICATION_XML，
 *      但是TEXT_XML是更常用的，并且如果要利用上述的URL后缀方式来指定数据格式，只能配置为TEXT_XML才能生效。
 *      可以指定多个，默认是第一个，可以根据后缀进行区分。.xml.json
 *      注意中文：添加ContentType.TEXT_XML_UTF_8
 
 * 5、注解可以写到接口或实现类当中都可以。注意:@PathParam(value = "id"),要在写注解的地方，添加这个。
 * 6、@Consumes和@Produces可以写到接口上，也可以写到方法当中
 * 7、MediaType是jboss原生的,ContentType是阿里进行了封装，两个都可以
 * 8、在生产环境当中，打成一个jar，启动
 * 9、序列化：序列化kryo效率比较高，比java原生的效率高的多(4倍左右,压缩之后文件比较小)  http://dangdangdotcom.github.io/dubbox/serialization.html
 *   Kryo和FST的性能依然普遍优于hessian和dubbo序列化。
 *   用法：serialization="kryo" optimizer="com.alibaba.dubbo.demo.SerializationOptimizerImpl"
 *       implements SerializationOptimizer
 * 10、GZIP数据压缩:减少网络传输时间和带宽占用，但这种方式会也增加CPU开销。
 * 11、添加自定义的Filter、Interceptor
 * 		过滤器或拦截器分为客户端过滤器和服务器端过滤器,
 * 			ContainerResponseFilter:provider端过滤器接口。
 * 			ClientResponseFilter：consumer端对应的接口。
 *     使用:<dubbo:protocol name="rest"  extension="com.util.CacheControlFilter"/>  在过滤器上添加@Provider注解
 * 12、自定义异常，实现ExceptionMapper<Exception>接口  extends RpcExceptionMapper类，具体的参见文档
 * 13、在参数当中添加@Context HttpServletRequest request 可以获取请求当中的信息
 *     还支持通过RpcContext来获取HttpServletRequest和HttpServletResponse
 * 14、dubbox集群的搭建：只需要在不同的节点部署dubbox程序就行了,天生支持集群模式
 *   负载均衡模式：权重，轮训,随机，最小连接。通过admin管控台来管理，也可以在代码当中写死，不推荐，推荐使用管控台来设置负载均衡策略
 *      实现：loadbalance=""
 *   集群容错模式:
 *    Failover Cluster:(工作当中常用这种吗，但是把重试次数设置为0)
		失败自动切换，当出现失败，重试其它服务器。(缺省)
		通常用于读操作，但重试会带来更长延迟。
		可通过retries="0"来设置重试次数(不含第一次)。
		
	  Failfast Cluster:
		快速失败，只发起一次调用，失败立即报错。
		通常用于非幂等性的写操作，比如新增记录。
		
	  Failsafe Cluster:
		失败安全，出现异常时，直接忽略。
		通常用于写入审计日志等操作。
		
	  Failback Cluster:
		失败自动恢复，后台记录失败请求，定时重发。
		通常用于消息通知操作。
		
	  Forking Cluster:(同时向所有的服务器发送请求，只要有一个成功返回就结束，基本上不用)
		并行调用多个服务器，只要一个成功即返回。
		通常用于实时性要求较高的读操作，但需要浪费更多服务资源。
		可通过forks="2"来设置最大并行数。
		
	  Broadcast Cluster:
		广播调用所有提供者，逐个调用，任意一台报错则报错。(2.1.0开始支持)
		通常用于通知所有提供者更新缓存或日志等本地资源信息
             使用在注解当中配置cluster=""属性 ,
   16、dubbo可以依赖于另外一个dubbo服务。在provider当中调用另外一个provider接口，dubbo:reference标签
*  17、dubbo当中的线程模型: 所有的请求处理线程到达provider端时，处理情况。
*     <dubbo:protocol name="rest" server="tomcat" port="7777" serialization="kryo"   optimizer="com.util.SerializationOptimizerImpl" contextpath="dubboProvider" accepts="500" keepalive="false" dispatcher="all" threadpool="fixed" threads="50"/> 
*     dispatcher:请求派发情况(经常用的是all)
*     		all:所有的请求都发送到线程池，包括请求\响应\连接\断开\心跳检测
*     	    direct:所有请求都不发送到线程池，直接在IO线程上处理
*     		message:只有响应消息发送到线程池，其他请求在IO线程上处理
*     		execution:只有请求消息派发到线程池
*     		connection:只有断开线程使用IO线程处理，其他请求派发到线程池当中
*     threads:处理请求的线程池(经常用的是fixed)
*     		fixed:固定大小的线程池
*     		cached:缓冲线程池，空闲一分钟清除线程，需要时重建
*     		limited:池中的线程池数量一直会增加，不会减少。为了避免突如其来的大流量二设计的
*     accepts:是最多接受的连接对象
*     thread:是处理这些连接对象的线程池的大小
*     executes:并发控制的数量
* 18、只是注册或只是订阅服务:(在开发的时候经常会用到这种直连的方式，不经过zookeeper注册中心)
* 		dubbo服务端可以直接连接到服务端，不经过zookeeper注册中心:
* 		<dubbo:reference interface="com.service.SimpleService" id="simpleService" check="false"
	  		url="dubbo://192.168.139.129:20880" 
	  	/>
*		<dubbo:registry address="zookeeper://192.168.139.128:2181?backup=192.168.139.129:2181,192.168.139.130:2181" subscribe="fasle" register="false"/>
*       register="false"表示不把服务注册到注册中心。
*       subscribe="fasle"表示禁止从注册中心订阅服务。
* 19、分组（开发当中经常用到）:同一个接口有多个实现时，通过分组来实现
* 		dubbo:service group="user" 在注解当中来实现
* 		dubbo:reference group="user" 在消费者当中来实习
* 20、结果缓存(把执行的结果缓存起来)
* 		dubbo:reference:在消费者客户端配置  cache="lru"
* 			lru:最近最少使用原则
* 			threadpool:当前线程缓存,同一个用户多次发送相同的请求时，会自动缓存到当前线程当中
* 			jcache:可以桥接各种缓存实现
* 21、并发控制  注解接口上 executes=10，并发控制为10 可以配置在接口，方法上都可以
* 22、令牌验证:防止消费者绕过zookeeper中心调用服务。
* 		可以在服务上添加,也可以在协议上添加全局的
* 23、路由规则很重要:黑名单，白名单，读写分离，跨机房部署等
*  注意：dubbox2.8.4的admin-dubbox不兼容dubbo2.5.4。admin管控台要和使用dubbo版本一致
*       retries=0 重试的次数尽量设置为0，因为存在连接延时是，不为0时，会自动重连，相当于发送了两次请求，更新或插入的时候很危险（要做幂等性验证）
* 24、Dubbo协议：采用NIO(同步非阻塞)复用单一长连接，并使用线程池并发处理请求，减少握手和加大并发效率，在大文件传输时，单一连接会成为瓶颈  
**/
@Service("userService")
//这个是dubbo的注解（同时提供dubbo本地，和rest方式）
@com.alibaba.dubbo.config.annotation.Service(interfaceClass=com.service.UserService.class,protocol = {"rest", "dubbo"}, retries=0,timeout=2000, connections=100)

public class UserServiceImpl implements UserService{
	
	public void testget(HttpServletRequest request) {
	    System.out.println("Client address is " + request.getRemoteAddr());
	  //http://localhost:8888/provider/userService/testget
	  		System.out.println("测试...get");
	}
	
	public User getUser(HttpServletRequest request) {
	    System.out.println("Client address is " + request.getRemoteAddr());
    	User user = new User();
    	user.setId("1001");
    	user.setName("张三");
    	return user;
	}

	
	public User getUser( Integer id) {
		System.out.println("测试传入int类型的id: " + id);
    	User user = new User();
    	user.setId("1001");
    	user.setName("张三");
    	return user;
	}
	
	
	
	public User getUser(Integer id, String name) {

		System.out.println("测试俩个参数：");
		System.out.println("id: " + id);
		System.out.println("name: " + name);
    	User user = new User();
    	user.setId("1001");
    	user.setName("张三");
    	return user;
	}
	
	public void testpost() {
    	System.out.println("测试...post");
	}
	
	public User postUser(User user) {
    	System.out.println(user.getName());
    	System.out.println("测试...postUser");
    	User user1 = new User();
    	user1.setId("1001");
    	user1.setName("张三");
    	return user1;
	}

	public User postUser( String id) {
		System.out.println(id);
		System.out.println("测试...get");
    	User user = new User();
    	user.setId("1001");
    	user.setName("张三");
    	return user;
	}


	
}
