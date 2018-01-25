package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.BarcodeDao;
import com.gboss.pojo.Barcode;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:BarcodeDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-15 下午7:54:13
 */
@Repository("BarcodeDao")  
@Transactional 
public class BarcodeDaoImpl extends BaseDaoImpl implements BarcodeDao {

	@Override
	public List<Barcode> getByUnit_id(Long unit_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Barcode.class); 
		if(unit_id!=null){
			criteria.add(Restrictions.eq("unit_id", unit_id));
		}
		List<Barcode> list = criteria.list();
		return list;
	}

	@Override
	public void deleteByUnit_id(Long unit_id) {
		String hql = "delete from Barcode where unit_id = " + unit_id;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public Barcode getByUnit_idAndType(Long unit_id, Integer type) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Barcode.class); 
		if(unit_id!=null){
			criteria.add(Restrictions.eq("unit_id", unit_id));
		}
		if(type != null){
			criteria.add(Restrictions.eq("type", type));
		}
		List<Barcode> list = criteria.list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}

