package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.gboss.dao.AccountDao;
import com.gboss.util.StringUtils;

@Repository("accountDao")
public class AccountDaoImpl extends BaseDaoImpl implements AccountDao {

	public Map<String, Object> getAccountInfoByVin(String vin) {
		List<Map<String, Object>>  list = jdbcTemplate.queryForList(getSQL(vin));
		if(list !=null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public static void main(String[] args) {
		ApplicationContext beanfactory = new ClassPathXmlApplicationContext("applicationContext.xml");
		AccountDao DAO= (AccountDao)beanfactory.getBean("accountDao");
//		System.out.println(DAO.getAccountInfoByVin("LMVAFLFC9FA002129"));
		HashMap<String, Object> map = (HashMap<String, Object>) DAO.getAccountInfoByVin("LMVAFLFC9FA002129");
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		String value = "123456".concat(",");
		while(it.hasNext()){
			String key_temp = it.next();
			String val = String.valueOf(map.get(key_temp));
			
			if(StringUtils.isNullOrEmpty(val)|| "null".equals(val)){
				val = "";
			}
			value = value.concat(val).concat(",");
		}
		System.out.println(value.substring(0,value.length()-1));
		System.out.println(value.substring(0,value.length()-1).split(",").length);
	}
	
	public String getSQL(String vin){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT ");
//		sql.append(" ifnull(c.subco_code, c.subco_no) AS subco_code, ");
		sql.append(" c.subco_code, ");
		sql.append(" c.subco_no, ");
		sql.append(" c.cust_type, ");
		sql.append(" c.custco_no, ");
		sql.append(" c.custco_code, ");
		sql.append(" u.call_letter ");
		sql.append(" FROM ");
		sql.append(" t_ba_customer c, ");
		sql.append(" t_ba_unit u, ");
		sql.append(" t_ba_sim s, ");
		sql.append(" t_ba_vehicle v, ");
		sql.append(" t_ba_cust_vehicle cv ");
		sql.append(" WHERE ");
		sql.append(" c.subco_no = u.subco_no ");
		sql.append(" and cv.vehicle_id = v.vehicle_id ");
		sql.append(" and cv.customer_id = c.customer_id ");
		sql.append(" AND v.subco_no = c.subco_no ");
		sql.append(" AND v.vin = s.vin ");
		sql.append(" AND v.vehicle_id = u.vehicle_id ");
		sql.append(" AND s.subco_no = c.subco_no ");
		sql.append(" AND s.call_letter = u.call_letter ");
		sql.append(" AND c.customer_id = u.customer_id ");
		sql.append(" AND c.subco_no = 201 ");
		sql.append(" AND v.vin ='").append(vin).append("'");
//		System.out.println("====>>>"+sql);
		return sql.toString();
	}

}
