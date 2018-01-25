package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.EquipCodeDao;
import com.gboss.pojo.EquipCode;

@Repository("equipCodeDao")
//@Transactional
public class EquipCodeDaoImpl extends BaseDaoImpl implements EquipCodeDao {

	public List<EquipCode> getAllEquipCode(HashMap<String, Object> pramas) {
//		String  sql = " SELECT * FROM t_ba_equip e WHERE EXISTS ( SELECT 'X' FROM t_ba_model m WHERE m.model_id = e.model_id AND EXISTS ( SELECT 'X' FROM t_ba_series t WHERE t.series_id = m.series_id AND EXISTS ( SELECT 'X' FROM t_ba_brand b WHERE b.brand_id = 201 AND t.brand_id = b.brand_id ))) ";
		String  sql = " SELECT * FROM t_ba_equip e  WHERE e.subco_no = 201 AND e.code1 <> 0  ";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(EquipCode.class);
		@SuppressWarnings("unchecked")
		List<EquipCode> list =  query.list();
		if( list != null && list.size() > 0 ){
			return list;
		}
		return null;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		EquipCodeDao dbiDao = context.getBean("equipCodeDao",EquipCodeDao.class);
		System.out.println(context);
		System.out.println(dbiDao.getAllEquipCode(new HashMap<String, Object>()));
	}

}
