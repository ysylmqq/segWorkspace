package test;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.service.impl.UserServiceImpl;

/**
 * 用户管理 junit 测试
 * 
 * @author zfy
 * 
 */
public class UserServiceImplTest {

	private static UserServiceImpl userServiceImpl;

	private static Logger logger = Logger.getLogger(UserServiceImplTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			Collection<String> files = new ArrayList<String>();
			files.add("classpath:spring/applicationContext-*.xml");
			FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(
					files.toArray(new String[0]));
			userServiceImpl = (UserServiceImpl) ctx.getBean("userService");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 登录
	@Test
	public final void getUserByLoginNamePwd() throws Exception {
		UserPOJO userPOJO = new UserPOJO();
		userPOJO.setLoginName("sysadmin");
		userPOJO.setPassword("sysadmin");
		userPOJO = userServiceImpl.getUserByLoginNamePwd(userPOJO);
		System.out.println("登录名为：sysadmin的用户的名字是：" + userPOJO.getUserName());
	}

	public static void main(String[] args) throws Exception {
		setUpBeforeClass();
		UserServiceImplTest test = new UserServiceImplTest();
		test.getUserByLoginNamePwd();
	}

}
