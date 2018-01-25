package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.DocumentDao;
import com.gboss.pojo.Documents;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:DocumentDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-23 下午3:39:03
 */
@Repository("DocumentDao")  
@Transactional
public class DocumentDaoImpl extends BaseDaoImpl implements DocumentDao {

	@Override
	public List<Documents> search(Long unit_id) {
		String hql = "from Documents "
				   + "where 1=1 and unit_id = " + unit_id;
		List list = listAll(hql);
		return list;
	}

	@Override
	public void auditsuccsee(Long id) {
		StringBuffer hql=new StringBuffer();
		hql.append(" update Documents ");
		hql.append(" set flag=1,remark='已通过'");
		hql.append(" where unit_id=").append(id);
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());  
		query.executeUpdate();
	}

	@Override
	public void auditfail(Long id, String remark) {
		StringBuffer hql=new StringBuffer();
		hql.append(" update Documents ");
		hql.append(" set flag=0,remark='").append(remark).append("' ");
		hql.append(" where unit_id=").append(id);
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());  
		query.executeUpdate();
	}

}

