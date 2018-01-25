package com.chinagps.driverbook.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.chinagps.driverbook.pojo.ReturnValue;

/**
 * Service基类
 * 
 * @author Ben
 *
 * @param <T>
 */
public interface BaseService<T> {

	/**
	 * 新増
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int add(T entity) throws Exception;

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public ReturnValue edit(T entity) throws Exception;

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int removeById(String id) throws Exception;

	/**
	 * 通过实体的各个状态查找对应的记录
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public T find(T entity) throws Exception;
	
	/**
	 * 通过ID查找
	 * 
	 * @param id 主键ID
	 * @return
	 * @throws Exception
	 */
	public T findById(String id) throws Exception;

	/**
	 * 根据查询条件统计数量
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int countAll(T entity) throws Exception;

	/**
	 * 分页查询列表
	 * 
	 * @param entity 查询条件
	 * @param rowBounds RowBounds实例
	 * @return
	 * @throws Exception
	 */
	public List<T> findByPage(T entity, RowBounds rowBounds) throws Exception;

	/**
	 * 通过实体的各个状态查找列表
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public List<T> findList(T entity) throws Exception;
}
