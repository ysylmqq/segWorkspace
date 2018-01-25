package com.gboss.dao.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.LinkManDao;
import com.gboss.pojo.LinkMan;


@Repository("linkManDao")  
public class LinkManDaoImpl extends BaseDaoImpl implements LinkManDao {

	public LinkMan getLinkMan(long vehicle_id) throws SystemException {
		String sql = "select * from t_ba_linkman t where linkman_type = 1  ";
		sql += " and vehicle_id=" + vehicle_id;
//		System.out.println(sql);
		List<LinkMan> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<LinkMan>(LinkMan.class));
		if (list!= null && list.size() > 0  ) {
			return list.get(0);
		}
		return null;
	}
	
	public static void main(String[] args) {
		ApplicationContext beanfactory = new ClassPathXmlApplicationContext("applicationContext.xml");
		LinkManDao syncHelper = (LinkManDao)beanfactory.getBean("linkManDao");
//		try {
//			LinkMan man = syncHelper.getLinkMan(64594);
//			System.out.println(man);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		LinkMan man = new LinkMan();
		man.setAppsign(1);
		man.setCustomer_id(1234l);
		man.setLinkman("张三");
		man.setLinkman_id(123456l);
		man.setLinkman_type(10);
		man.setPhone("18711083009");
		man.setTitle("测试标题");
		man.setVehicle_id(6543l);
//		man.setLinkman_id(syncHelper.save(man));
		System.out.println("====success======="+man.getLinkman_id());
		
	}

}
