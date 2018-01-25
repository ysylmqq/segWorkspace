package com.hm.bigdata.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

import com.hm.bigdata.entity.po.BaseEntity;

public interface BaseDao {
	
	public <T extends BaseEntity> void save(T model);
	
	public <T extends BaseEntity> void save(Session session,T model);

    public <T extends BaseEntity> void saveOrUpdate(T model);
    
    public <T extends BaseEntity> void update(T model);
    
    public <T extends BaseEntity> void merge(T model);

    public <T extends BaseEntity, PK extends Serializable> void delete(Class<T> entityClass, PK id);

    public <T extends BaseEntity> void deleteObject(T model);

    public <T extends BaseEntity, PK extends Serializable> T get(Class<T> entityClass, PK id);
    
    public <T extends BaseEntity> int countAll(Class<T> entityClass);
    
    public <T extends BaseEntity> List<T> listAll(Class<T> entityClass);
    
    public <T extends BaseEntity> List<T> listAll(Class<T> entityClass, int pn);
    
    public <T extends BaseEntity> List<T> listAll(Class<T> entityClass, int pn, int pageSize);
    
    public <T extends BaseEntity> int countAll(final String hql);
    
    public <T extends BaseEntity> List<T> listAll(final String hql);
    
    public <T extends BaseEntity> List<T> listAll(final String hql, int pn);
    
    public <T extends BaseEntity> List<T> listAll(final String hql, int pn, int pageSize);
    
	public Session getMysql1CurrentSession();
	
	public Session openSession();
}
