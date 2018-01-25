package com.chinagps.center.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.chinagps.center.common.SystemException;
import com.chinagps.center.dao.CustomerDao;
import com.chinagps.center.pojo.Customer;
import com.chinagps.center.utils.StringUtils;

@Repository("CustomerDao")
public class CustomerDaoImpl extends BaseDaoImpl implements CustomerDao {

	@Override
	public Customer getCustById(Long customer_id) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" from Customer c where 1=1");
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
		if(customer_id!=null){
			sb.append(" and c.customer_id=").append(customer_id);
//			criteria.add(Restrictions.eq("customer_id", customer_id));
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		List<Customer> customers = query.list();
//		List<Customer> customers=criteria.list();
		if(StringUtils.isNotNullOrEmpty(customers)){
			return customers.get(0);
		}
		return null;
	}

	@Override
	public Long add(Customer customer) throws SystemException {
		super.save(customer);
		return customer.getCustomer_id();
	}

}
