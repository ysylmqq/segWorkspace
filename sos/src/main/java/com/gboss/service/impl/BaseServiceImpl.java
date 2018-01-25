package com.gboss.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.dao.BaseDao;
import com.gboss.pojo.BaseEntity;
import com.gboss.service.BaseService;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

@Service("BaseService")
@Transactional
public class BaseServiceImpl implements BaseService {
	
	@Autowired
    @Qualifier("BaseDao")
	protected BaseDao baseDao;

	public <T extends BaseEntity> void save(T model) {
		// TODO Auto-generated method stub
		baseDao.save(model);
	}

	public <T extends BaseEntity> void saveOrUpdate(T model) {
		// TODO Auto-generated method stub
		baseDao.saveOrUpdate(model);
	}

	public <T extends BaseEntity> void update(T model) {
		// TODO Auto-generated method stub
		baseDao.update(model);
	}

	public <T extends BaseEntity> void merge(T model) {
		// TODO Auto-generated method stub
		baseDao.merge(model);
	}

	public <T extends BaseEntity, PK extends Serializable> void delete(
			Class<T> entityClass, PK id) {
		// TODO Auto-generated method stub
		baseDao.delete(entityClass, id);
	}

	public <T extends BaseEntity> void deleteObject(T model) {
		// TODO Auto-generated method stub
		baseDao.deleteObject(model);
	}

	public <T extends BaseEntity, PK extends Serializable> T get(
			Class<T> entityClass, PK id) {
		// TODO Auto-generated method stub
		return baseDao.get(entityClass, id);
	}

	public <T extends BaseEntity> int countAll(Class<T> entityClass) {
		// TODO Auto-generated method stub
		return baseDao.countAll(entityClass);
	}

	public <T extends BaseEntity> List<T> listAll(Class<T> entityClass) {
		// TODO Auto-generated method stub
		return baseDao.listAll(entityClass);
	}

	public <T extends BaseEntity> Page<T> listAll(Class<T> entityClass, int pn) {
		// TODO Auto-generated method stub
		return listAll(entityClass, pn, SystemConst.DEFAULT_PAGE_SIZE);
	}

	public <T extends BaseEntity> Page<T> listAll(Class<T> entityClass, int pn,
			int pageSize) {
		// TODO Auto-generated method stub
		int total = countAll(entityClass);
        List<T> items = baseDao.listAll(entityClass, pn, pageSize);
        return PageUtil.getPage(total, pn, items, pageSize);
	}

	public <T extends BaseEntity> int countAll(String hql) {
		// TODO Auto-generated method stub
		return baseDao.countAll(hql);
	}

	public <T extends BaseEntity> List<T> listAll(String hql) {
		// TODO Auto-generated method stub
		return baseDao.listAll(hql);
	}

	public <T extends BaseEntity> Page<T> listAll(String hql, int pn) {
		// TODO Auto-generated method stub
		return listAll(hql, pn, SystemConst.DEFAULT_PAGE_SIZE);
	}
	
	public <T extends BaseEntity> Page<T> listAll(String hql, int pn,
			int pageSize) {
		// TODO Auto-generated method stub
		int total = countAll(hql);
        List<T> items = baseDao.listAll(hql, pn, pageSize);
        return PageUtil.getPage(total, pn, items, pageSize);
	}

}
