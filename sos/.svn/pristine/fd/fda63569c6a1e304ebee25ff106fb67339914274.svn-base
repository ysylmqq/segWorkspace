package com.gboss.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.CustphoneDao;
import com.gboss.pojo.Linkman;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:CustphoneDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午5:24:47
 */
@Repository("CustphoneDao")  
@Transactional
public class CustphoneDaoImpl extends BaseDaoImpl implements CustphoneDao {

	@Override
	public List<Linkman> listCustphone(Long cust_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Linkman.class);
		criteria.add(Restrictions.eq("customer_id", cust_id));
		criteria.addOrder(Order.desc("linkman_id"));
		return criteria.list();
	}

	@Override
	public void deleteByCust_id(Long cust_id) {
		String hql = "delete from Linkman where customer_id = " + cust_id;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public List<Linkman> getLinkmanList(Long customer_id) {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" from Linkman ");
		hqlSb.append(" where customer_id= ").append(customer_id);
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSb.toString());  
		return query.list();
		
	}

	@Override
	public Page<Linkman> findLinkman(PageSelect<Linkman> pageSelect) {
		String hql = "select a from Linkman a,Customer b "
				   + "where 1=1 and a.customer_id = b.customer_id ";
		Map filter = pageSelect.getFilter();
		Set<String> keys = filter.keySet();
		for(Iterator it=keys.iterator();it.hasNext();){
			String key = (String)it.next();
			if(key.equals("subco_no")){
				String new_name = (String) filter.get(key);
				if(!"2".equals(new_name)){
					hql += " and b.subco_no = " + new_name;
				}
			}else if(key.equals("phone")&&filter.get(key) instanceof String){
				String value = (String)filter.get(key);
				hql += " and a.phone like '%" + value + "%' ";
			}else if(key.equals("nocust_type")){
				Integer new_name = (Integer) filter.get(key);
				hql += " and b.cust_type !=" + new_name;
			}
		}
		if(StringUtils.isNotBlank(pageSelect.getStart_date())){
			hql += " and a.stamp > '" + pageSelect.getStart_date() + "'";
		}
		if(StringUtils.isNotBlank(pageSelect.getEnd_date())){
			hql += " and a.stamp < '" + pageSelect.getEnd_date() + "'";
		}
		List list = listAll(hql, pageSelect.getPageNo(), pageSelect.getPageSize());
		int count = countAll(hql);
		Page<Linkman> page = PageUtil.getPage(count, pageSelect.getPageNo(), list, pageSelect.getPageSize());
		return page;
	}

}

