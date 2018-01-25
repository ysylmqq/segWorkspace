package com.gboss.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.CustomerDao;
import com.gboss.pojo.Customer;
import com.gboss.util.StringUtils;

@Repository("CustomerDao")
public class CustomerDaoImpl extends BaseDaoImpl implements CustomerDao {

	public boolean is_repeat(Customer customer) {
		
		if (StringUtils.isBlank(customer.getCustomer_name())) {
			return false;
		}
		
		String sql = " select * from t_ba_customer t where 1=1  ";
		
		int count = 0;
		if (customer != null) {
			if (customer.getCustomer_id() != null) {
				sql += " and customer_id <> "+customer.getCustomer_id();
			}
			if (customer.getCustomer_name() != null) {
				sql += " and customer_name = '"+customer.getCustomer_name()+"'";
			}
			if (customer.getCust_type() != null) {
				sql += " and cust_type = "+customer.getCust_type() ;
			}
			count = jdbcTemplate.queryForObject(sql, Integer.class);
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public String getCustomerPhones(Long customerId) throws SystemException {
		StringBuffer ssb = new StringBuffer();
		ssb.append(" SELECT group_concat(phone) as phone FROM t_ba_linkman ");
		ssb.append(" where customer_id = ").append(customerId);
		String sql = ssb.toString();
		List<String> list = jdbcTemplate.queryForList(sql, String.class);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}


	 
	public Customer getCustomer(Long id) {
		
		String sql = " select * from t_ba_customer t where 1=1  ";
		if (id != null) {
			sql+=" and customer_id="+id;
		}
		List<Customer> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Customer>(Customer.class));
		
		if (list!= null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	 
	 
	public Customer getCustomer(String customer_name) {
		
		String sql = " select * from t_ba_customer t where 1=1  ";
		if (customer_name != null) {
			sql += " and customer_name=" +customer_name;
		}
		
		List<Customer> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Customer>(Customer.class));
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	 
	public Long add(Customer customer) {
//		save(customer);
		return null;
	}


}
