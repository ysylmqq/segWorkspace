package com.gboss.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.CustomerDao;
import com.gboss.pojo.Customer;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

@Repository("CustomerDao")
@Transactional
public class CustomerDaoImpl extends BaseDaoImpl implements CustomerDao {

	@Override
	public boolean is_repeat(Customer customer) {
		if (StringUtils.isBlank(customer.getCustomer_name())) {
			return false;
		}
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Customer.class);
		int count = 0;
		if (customer != null) {
			if (customer.getCustomer_id() != null) {
				criteria.add(Restrictions.not(Restrictions.eq("customer_id",
						customer.getCustomer_id())));
			}
			if (customer.getCustomer_name() != null) {
				criteria.add(Restrictions.eq("customer_name",
						customer.getCustomer_name()));
			}
			if (customer.getCust_type() != null) {
				criteria.add(Restrictions.eq("cust_type",
						customer.getCust_type()));
			}
			count = criteria.list().size();
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Page<Customer> search(PageSelect<Customer> pageSelect, Long subco_no) {
		String hql = "from Customer " + "where 1=1 ";
		if (subco_no != 2L) {
			if (subco_no == 101L) {
				hql += " and (subco_no=" + subco_no + " or subco_no = 2)";
			} else {
				hql += " and subco_no=" + subco_no;
			}
		}
		Map filter = pageSelect.getFilter();
		Set<String> keys = filter.keySet();
		for (Iterator it = keys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			if (filter.get(key) instanceof Integer) {
				Integer new_name = (Integer) filter.get(key);
				if (key.equals("nocust_type")) {
					hql += " and cust_type !=" + new_name;
				} else {
					hql += " and " + key + "=" + new_name;
				}
			} else if (filter.get(key) instanceof String) {
				String value = (String) filter.get(key);
				hql += " and " + key + " like '%" + value + "%' ";
			}

		}
		if (StringUtils.isNotBlank(pageSelect.getStart_date())) {
			hql += " and stamp > '" + pageSelect.getStart_date() + "'";
		}
		if (StringUtils.isNotBlank(pageSelect.getEnd_date())) {
			hql += " and stamp < '" + pageSelect.getEnd_date() + "'";
		}
		if (StringUtils.isNotBlank(pageSelect.getOrder())) {
			hql += " order by " + pageSelect.getOrder();
			if (pageSelect.getIs_desc()) {
				hql += " desc ";
			} else {
				hql += " asc ";
			}
		}
		List list = listAll(hql, pageSelect.getPageNo(),
				pageSelect.getPageSize());
		int count = countAll(hql);
		Page<Customer> page = PageUtil.getPage(count, pageSelect.getPageNo(),
				list, pageSelect.getPageSize());
		return page;
	}

	@Override
	public String getCustomerPhones(Long customerId) throws SystemException {
		StringBuffer ssb = new StringBuffer();
		ssb.append(" SELECT group_concat(phone) as phone FROM t_ba_linkman ");
		ssb.append(" where customer_id = ").append(customerId);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(
				ssb.toString());
		if (query.list().size() > 0) {
			return (String) query.list().get(0);
		} else {
			return null;

		}
	}

	@Override
	public HashMap<String, Object> getDetailMsg(Long id) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select  v.color as color, v.vehicle_id as vehicle_id, v.plate_no as number_plate, u.product_code as code, v.model as cartypeId, v.plate_no as carNum, c.model_name as cartypeName ");
		sb.append(" from t_ba_vehicle v, t_ba_model c , t_ba_unit u  where v.model = c.model_id and  u.vehicle_id = v.vehicle_id ");
		sb.append(" and u.customer_id = ").append(id);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (query.list().size() > 0) {
			return (HashMap<String, Object>) query.list().get(0);
		} else
			return null;
	}

	@Override
	public Customer getCustomer(Long id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Customer.class);
		if (id != null) {
			criteria.add(Restrictions.eq("customer_id", id));
		}
		List<Customer> customers = criteria.list();
		if (StringUtils.isNotNullOrEmpty(customers)) {
			return customers.get(0);
		}
		return null;
	}

	@Override
	public HashMap<String, Object> getDetailMsgBycl(String call_letter)
			throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT u.vehicle_id AS vehicle_id,u.customer_id AS customer_id, u.product_code as code, m.model_name as model_name,");
		sb.append(" u.subco_no AS subco_no,c.customer_name AS customer_name,v.plate_no AS car_num,v.color AS color,v.model as model_id ");
		sb.append(" FROM t_ba_unit u,t_ba_customer c,t_ba_vehicle v,t_ba_model m ");
		sb.append(" WHERE u.customer_id = c.customer_id AND u.vehicle_id = v.vehicle_id AND v.model = m.model_id ");
		sb.append(" and u.call_letter ='").append(call_letter).append("'");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (query.list().size() > 0) {
			return (HashMap<String, Object>) query.list().get(0);
		} else
			return null;
	}

	@Override
	public Customer getCustomer(String customer_name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Customer.class);
		if (customer_name != null) {
			criteria.add(Restrictions.eq("customer_name", customer_name));
		}
		List<Customer> list = criteria.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Long add(Customer customer) {
		save(customer);
		return customer.getCustomer_id();
	}

	@Override
	public void updateCustSales(Long customerId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update CustSales set isdel=1 where customer_id=");
		sb.append(customerId);
		Query query = sessionFactory.getCurrentSession().createQuery(
				sb.toString());
		query.executeUpdate();
	}

}
