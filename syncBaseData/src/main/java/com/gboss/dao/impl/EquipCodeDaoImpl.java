package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.EquipCodeDao;
import com.gboss.pojo.Equipcode;

@Repository("equipCodeDao")
@Transactional
public class EquipCodeDaoImpl extends BaseDaoImpl implements EquipCodeDao {

	public List<Equipcode> getAllEquipCode(HashMap<String, Object> pramas) {
		String  sql = " SELECT * FROM t_ba_equip e  WHERE e.subco_no = 201 AND e.code1 <> 0  ";
		List<Equipcode> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Equipcode>(Equipcode.class));
		if( list != null && list.size() > 0 ){
			return list;
		}
		return null;
	}
	
	public List<Equipcode> getEquipCodeList() {
		String  sql = " SELECT * FROM t_ba_equip e  WHERE e.subco_no = 201";
		List<Equipcode> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Equipcode>(Equipcode.class));
		if( list != null && list.size() > 0 ){
			return list;
		}
		return null;
	}
	
	public List<Equipcode> getEquipCodeListByCode(String equipCode) {
		String  sql = " SELECT * FROM t_ba_equip e  WHERE e.subco_no = 201 and equip_code ='"+equipCode+"'";
		List<Equipcode> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Equipcode>(Equipcode.class));
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
