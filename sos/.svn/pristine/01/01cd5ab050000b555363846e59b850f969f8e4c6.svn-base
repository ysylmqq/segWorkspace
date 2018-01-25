package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.MidCustDao;
import com.gboss.pojo.MidCust;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:MidCustDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-29 下午4:28:31
 */
@Repository("MidCustDao")  
@Transactional
public class MidCustDaoImpl extends BaseDaoImpl implements MidCustDao {

	@Override
	public MidCust getMidCustByUnitid(Long unit_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MidCust.class);
		if(unit_id!=null){
			criteria.add(Restrictions.eq("unit_id", unit_id));
		}else{
			return null;
		}
		List<MidCust> list = criteria.list();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void deleteMidCust(Long unit_id) {
		String hql = "delete from MidCust where unit_id="+unit_id;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}

}

