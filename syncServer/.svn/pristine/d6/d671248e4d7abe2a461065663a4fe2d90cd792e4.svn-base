package com.chinagps.center.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.chinagps.center.common.SystemException;
import com.chinagps.center.dao.SynCompanyDao;
import com.chinagps.center.pojo.SynCompany;

@Repository("SynCompanyDao")
public class SynCompanyDaoImpl extends BaseDaoImpl implements SynCompanyDao {

	@Override
	public List<SynCompany> listAll() throws SystemException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SynCompany.class); 
		List<SynCompany> list=criteria.list();
		return list;
	}

	@Override
	public List<SynCompany> listBySubcoNo(Long subco_no) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append("From SynCompany sc where sc.subco_no=").append(subco_no);
		List<SynCompany> list = listAll(sb.toString());
		return list;
	}

}
