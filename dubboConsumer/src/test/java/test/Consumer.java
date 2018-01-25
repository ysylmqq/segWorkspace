package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.entity.Simple;
import com.service.SimpleService;

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
	}

}