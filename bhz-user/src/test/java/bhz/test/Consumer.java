package bhz.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import bhz.sys.entity.SysUser;
import bhz.sys.facade.SysUserFacade;


public class Consumer {

	
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "dubbo-consumer.xml" });
		context.start();
		SysUserFacade sf = (SysUserFacade) context.getBean("sysUserService");
		
		System.out.println(sf);
		SysUser user = sf.getUser();
		System.out.println(user.getName());
		
		
		
	}

}