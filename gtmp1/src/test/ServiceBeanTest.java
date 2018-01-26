package test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.chinaGPS.gtmp.entity.DepartPOJO;
import com.chinaGPS.gtmp.entity.ModulePOJO;
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.service.IUserService;

/**
 * @Package:test
 * @ClassName:ServiceBeanTest
 * @Description:java调用数据库存储过程测试类
 * @author:lxj
 * @date:Dec 18, 2012 2:53:21 PM
 * 
 */
public class ServiceBeanTest {
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		IUserService service = (IUserService) ctx.getBean("userServiceImpl");
		UserPOJO userPOJO = new UserPOJO();
		// userPOJO.setUserId(Long.valueOf(43));
		userPOJO.setLoginName("sysadmin");
		// userPOJO.setPassword("sysadmin");//sysadmin
		/*
		 * userPOJO.setName("系统管理员"); userPOJO.setEmail("516797846@qq.com");
		 * userPOJO.setTel("15116351819"); userPOJO.setGender("女");
		 * userPOJO.setDepartId(Long.valueOf(1)); userPOJO.setIsValid(1);
		 */
		userPOJO = service.getUserByLoginNamePwd(userPOJO);
		DepartPOJO depart = userPOJO.getDepartInfo();
		System.out.println(depart.getDepartName());
		List<RolePOJO> rolelist = userPOJO.getRoles();
		RolePOJO role = null;
		System.out.println(rolelist.size());
		for (int i = 0; i < rolelist.size(); i++) {
			role = rolelist.get(i);
			System.out.println(role.getRoleName());
		}
		// 可访问的模块
		List<ModulePOJO> moduleList = service.getAccessibleModues(rolelist);
		// 首页:如果是多角色，则取第一个角色的首页
		ModulePOJO modulePOJO = moduleList.get(0);
		for (ModulePOJO module : moduleList) {
			System.out.println(module.getModuleName());
			if (module.getControl2() != null
					&& !"".equals(module.getControl2())) {
				System.out.println(module.getControl2());
				break;
			}
		}
		// System.out.println(userPOJO==null?"have no":userPOJO.getUserId());
		// System.out.println(service.saveOrUpdate(userPOJO));

	}
}
