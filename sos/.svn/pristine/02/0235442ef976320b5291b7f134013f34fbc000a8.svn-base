package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.BankcodeDao;
import com.gboss.pojo.Bankcode;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:BankcodeDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-4 下午4:12:40
 */
@Repository("BankcodeDao")  
@Transactional 
public class BankcodeDaoImpl extends BaseDaoImpl implements BankcodeDao {

	@Override
	public List<Bankcode> getBankcodeList(Long subco_no, Integer agency) {
		String hql = "from Bankcode where subco_no in ("+subco_no+",0) and agency = " + agency;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Override
	public List<Bankcode> getBankcodeList(Long subco_no) {
		String hql = "from Bankcode where subco_no in ("+subco_no+",0)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

}

