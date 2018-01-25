package com.chinagps.driverbook.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

public interface BaseSqlMapper<T> {

	public int add(T entity) throws Exception;

	public int edit(T entity) throws Exception;

	public int editByid(int id) throws Exception;

	public int remove(T entity) throws Exception;

	public int removeById(String id) throws Exception;

	public T find(T entity) throws Exception;

	public T findById(String id) throws Exception;

	public List<T> findList(T entity) throws Exception;

	public int countAll(T entity) throws Exception;

	public List<T> findByPage(T entity, RowBounds rowBounds) throws Exception;
	
}
