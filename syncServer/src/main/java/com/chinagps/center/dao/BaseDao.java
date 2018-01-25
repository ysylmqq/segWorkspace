package com.chinagps.center.dao;

import java.io.Serializable;
import java.util.List;

import com.chinagps.center.pojo.BaseEntity;

public interface BaseDao {
	
	public <T extends BaseEntity> void save(T model);

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
}
