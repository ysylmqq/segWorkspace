package com.gboss.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Package:com.gboss.util
 * @ClassName:HibernateUtil 
 * @Description:Hibernate初始化类，用于获取Session、SessionFactory 及关闭Session
 * @author:zfy
 * @date:2013-8-7 上午11:24:22
 */
@Repository("HibernateUtil") 
@Transactional
public class HibernateUtil {
	@Autowired
	@Qualifier("sessionFactory")
	private  SessionFactory factory;
    /** 
     * 获取Session对象 
     * @return Session对象 
     */  
    public  Session getSession() {  
        //如果SessionFacroty不为空，则开启Session  
        Session session = (factory != null) ? factory.openSession() : null;  
        return session;  
    }  
    /** 
     * 获取SessionFactory对象 
     * @return SessionFactory对象 
     */  
    public  SessionFactory getSessionFactory() {  
        return factory;  
    }  
    /** 
     * 关闭Session 
     * @param session对象 
     */  
    public  void closeSession(Session session) {  
        if (session != null) {  
            if (session.isOpen()) {  
                session.close(); // 关闭Session  
            }  
        }  
    }  
}

