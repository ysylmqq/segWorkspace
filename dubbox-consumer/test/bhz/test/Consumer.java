package bhz.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import bhz.entity.Simple;
import bhz.service.SimpleService;
import bhz.service.UserService;

public class Consumer {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "dubbo-consumer.xml" });
		context.start();

		SimpleService ss = (SimpleService) context.getBean("simpleService");
		String hello = ss.sayHello("tom");
		System.out.println(hello);
		Simple simple = ss.getSimple();
		System.out.println(simple.getName());
		System.out.println(simple.getAge());
		System.out.println(simple.getMap().get("zhang1"));
		

		UserService us = (UserService) context.getBean("userService");
		
		System.out.println(us.getUser().getName());
		System.out.println(us.getUser().getId());
		
		
		
	}

}