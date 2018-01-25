package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.DatalockDao;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:DatalockDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-4 下午12:20:33
 */

@Repository("DatalockDao")  
@Transactional
public class DatalockDaoImpl extends BaseDaoImpl implements DatalockDao {

	@Override
	public List<Long> getLockCustomer() {
		List<Long> list = new ArrayList<Long>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select customer_id from t_fee_paytxt ");
		sb.append(" where subco_no in ");
		sb.append(" (select subco_no from t_fee_datalock where locktag = 1) ");
		sb.append(" union ");
		sb.append(" select customer_id from t_fee_cust_datalock where locktag = 1 ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sb.toString());
		List qlist = query.list();
		for(Object obj:qlist){
			BigInteger bigvalue = (BigInteger)obj;
			list.add(bigvalue.longValue());
		}
		return list;
	}

}

