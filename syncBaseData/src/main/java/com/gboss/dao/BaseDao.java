package com.gboss.dao;

import java.io.Serializable;
import java.util.List;

import com.gboss.pojo.BaseEntity;
import com.gboss.pojo.FeeInfo;

public interface BaseDao {
	
	public <T extends BaseEntity> Long save(T model) throws Exception ;

    public <T extends BaseEntity> void saveOrUpdate(T model);
    
    public <T extends BaseEntity> void update(T model);
    
    public <T extends BaseEntity> void batchDelete(Class<T> entityClass,List<T> lists);
    
    public <T extends BaseEntity> void batchSave(Class<T> entityClass,List<T> lists);
    
    public <T extends BaseEntity> void batchUpdate(Class<T> class1, List<T> delete_feeinfos);
    
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
