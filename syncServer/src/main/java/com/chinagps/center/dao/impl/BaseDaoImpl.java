package com.chinagps.center.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chinagps.center.common.SystemConst;
import com.chinagps.center.dao.BaseDao;
import com.chinagps.center.pojo.BaseEntity;
import com.chinagps.center.utils.page.PageUtil;

@Repository("BaseDao")
public class BaseDaoImpl implements BaseDao {
	@Autowired
	protected SessionFactory sessionFactory;

	public <T extends BaseEntity> void save(T model) {
		sessionFactory.getCurrentSession().save(model);
		//sessionFactory.getCurrentSession().flush();
	}

	public <T extends BaseEntity> void saveOrUpdate(T model) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(model);
		//sessionFactory.getCurrentSession().flush();
	}

	public <T extends BaseEntity> void update(T model) {
		// TODO Auto-generated method stub
		model.setStamp(null);
		sessionFactory.getCurrentSession().update(model);
		//sessionFactory.getCurrentSession().flush();
	}

	public <T extends BaseEntity> void merge(T model) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().merge(model);
		//sessionFactory.getCurrentSession().flush();
	}

	public <T extends BaseEntity, PK extends Serializable> void delete(
			Class<T> entityClass, PK id) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(get(entityClass, id));
		//sessionFactory.getCurrentSession().flush();
	}

	public <T extends BaseEntity> void deleteObject(T model) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(model);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity, PK extends Serializable> T get(
			Class<T> entityClass, PK id) {
		// TODO Auto-generated method stub
		T t = (T) sessionFactory.getCurrentSession().get(entityClass, id);
		sessionFactory.evict(entityClass, id);
		return t;
		
	}

	public <T extends BaseEntity> int countAll(Class<T> entityClass) {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
        criteria.setProjection(Projections.rowCount());
        return ((Long) criteria.uniqueResult()).intValue();
	}

	public <T extends BaseEntity> List<T> listAll(Class<T> entityClass) {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
//        criteria.setProjection(Projections.rowCount());
		@SuppressWarnings("unchecked")
		List<T> list = criteria.list();
        return list;
	}

	public <T extends BaseEntity> List<T> listAll(Class<T> entityClass, int pn) {
		// TODO Auto-generated method stub
		return listAll(entityClass, pn, SystemConst.DEFAULT_PAGE_SIZE);
	}

	public <T extends BaseEntity> List<T> listAll(Class<T> entityClass, int pn,
			int pageSize) {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
        criteria.setFirstResult(PageUtil.getPageStart(pn, pageSize));
        criteria.setMaxResults(pageSize);
        @SuppressWarnings("unchecked")
		List<T> list = criteria.list();
        return list;
	}	

	public <T extends BaseEntity> int countAll(String hql) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery(hql).list().size();
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> listAll(String hql) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	public <T extends BaseEntity> List<T> listAll(String hql, int pn) {
		// TODO Auto-generated method stub
		return this.listAll(hql, pn, SystemConst.DEFAULT_PAGE_SIZE);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> listAll(final String hql , int pn,
			int pageSize) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery(hql).setFirstResult(PageUtil.getPageStart(pn, pageSize)).setMaxResults(pageSize).list();
	}

}
