package bhz.mst.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import bhz.com.constant.SystemConst;
import bhz.com.util.PageUtil;
import bhz.mst.dao.BaseDao;
import bhz.mst.entity.BaseEntity;


@Repository("BaseDao")
public class BaseDaoImpl implements BaseDao {
	@Autowired
	@Qualifier("mysql1SessionFactory")
	protected SessionFactory mysql1SessionFactory;
	
	/*@Autowired
	@Qualifier("ora1SessionFactory")
	protected SessionFactory ora1SessionFactory;*/

	public <T extends BaseEntity> void save(T model) {
		mysql1SessionFactory.getCurrentSession().save(model);
		//sessionFactory.getCurrentSession().flush();
	}

	public <T extends BaseEntity> void saveOrUpdate(T model) {
		
		mysql1SessionFactory.getCurrentSession().saveOrUpdate(model);
		//sessionFactory.getCurrentSession().flush();
	}

	public <T extends BaseEntity> void update(T model) {
		
		mysql1SessionFactory.getCurrentSession().update(model);
		//sessionFactory.getCurrentSession().flush();
	}

	public <T extends BaseEntity> void merge(T model) {
		
		mysql1SessionFactory.getCurrentSession().merge(model);
		//sessionFactory.getCurrentSession().flush();
	}

	public <T extends BaseEntity, PK extends Serializable> void delete(
			Class<T> entityClass, PK id) {
		
		mysql1SessionFactory.getCurrentSession().delete(get(entityClass, id));
		//sessionFactory.getCurrentSession().flush();
	}

	public <T extends BaseEntity> void deleteObject(T model) {
		
		mysql1SessionFactory.getCurrentSession().delete(model);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity, PK extends Serializable> T get(
			Class<T> entityClass, PK id) {
		
		return (T) mysql1SessionFactory.getCurrentSession().get(entityClass, id);
	}

	public <T extends BaseEntity> int countAll(Class<T> entityClass) {
		
		Criteria criteria = mysql1SessionFactory.getCurrentSession().createCriteria(entityClass);
        criteria.setProjection(Projections.rowCount());
        return ((Long) criteria.uniqueResult()).intValue();
	}

	public <T extends BaseEntity> List<T> listAll(Class<T> entityClass) {
		
		Criteria criteria = mysql1SessionFactory.getCurrentSession().createCriteria(entityClass);
		@SuppressWarnings("unchecked")
		List<T> list = criteria.list();
        return list;
	}

	public <T extends BaseEntity> List<T> listAll(Class<T> entityClass, int pn) {
		
		return listAll(entityClass, pn, SystemConst.DEFAULT_PAGE_SIZE);
	}

	public <T extends BaseEntity> List<T> listAll(Class<T> entityClass, int pn,
			int pageSize) {
		
		Criteria criteria = mysql1SessionFactory.getCurrentSession().createCriteria(entityClass);
        criteria.setFirstResult(PageUtil.getPageStart(pn, pageSize));
        criteria.setMaxResults(pageSize);
        @SuppressWarnings("unchecked")
		List<T> list = criteria.list();
        return list;
	}	

	public <T extends BaseEntity> int countAll(String hql) {
		
		return mysql1SessionFactory.getCurrentSession().createQuery(hql).list().size();
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> listAll(String hql) {
		
		return mysql1SessionFactory.getCurrentSession().createQuery(hql).list();
	}

	public <T extends BaseEntity> List<T> listAll(String hql, int pn) {
		
		return this.listAll(hql, pn, SystemConst.DEFAULT_PAGE_SIZE);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> listAll(final String hql , int pn,
			int pageSize) {
		
		return mysql1SessionFactory.getCurrentSession().createQuery(hql).setFirstResult(PageUtil.getPageStart(pn, pageSize)).setMaxResults(pageSize).list();
	}
	
	public Session getMysql1CurrentSession(){
		return mysql1SessionFactory.getCurrentSession();
	}

	@Override
	public Session openSession() {
		return mysql1SessionFactory.openSession();
	}

	@Override
	public <T extends BaseEntity> void save(Session session, T model) {
		if(session==null){
			session=mysql1SessionFactory.getCurrentSession();
		}
		 session.save(model);
	}
}
