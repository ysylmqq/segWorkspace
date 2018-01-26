package cc.chinagps.seat.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cc.chinagps.seat.bean.table.BaseEntity;

public class BasicDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
//	@Autowired
//	@Qualifier("writeSessionFactory")
//	private SessionFactory writeSessionFactory;

//	public SessionFactory getSessionFactory() {
//		return sessionFactory;
//	}

	public Session getSession() {
		//事务方式获取session
		Session session = sessionFactory.getCurrentSession();
		if (session == null) {
			session = sessionFactory.openSession();
		}
//		// 非事务方式获取session
//		Session session = sessionFactory.openSession();
		return session;
	}
	
	public Session getWriteSession() {
//		Session session = writeSessionFactory.getCurrentSession();
//		if (session == null) {
//			session = writeSessionFactory.openSession();
//		}
//		// Session session = getSessionFactory().openSession();
//		return session;
		return getSession();
	}	
}