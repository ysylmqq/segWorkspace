package com.chinaGPS.gtmp.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chinaGPS.gtmp.entity.CustomerPayPOJO;
import com.chinaGPS.gtmp.entity.CustomerSimPOJO;


public interface BaseService<T>{
	
   public boolean add(T entity) throws Exception;
	
	public boolean edit(T entity) throws Exception;
	
	public boolean remove(T entity) throws Exception;
	
	public boolean removeById(Long id) throws Exception;
	
	public T getById(Long id) throws Exception;
	
	public T get(T entity) throws Exception;
	
	/**通过实体的各个状态查找相关
	 * @param entity 实体
	 * @return
	 * @throws Exception
	 */
	public List<T> getList(T entity) throws Exception;
	
	public List<T> getListCode(T entity) throws Exception;
	
	public List<T> getListArg(T entity) throws Exception;
	
	public List<T> getByPage(Map<String, Serializable> map,RowBounds rowBounds)throws Exception;
	
	public int countAll(Map<String, Serializable> map)throws Exception;
	
	/**
	 * 根据序列名取主键值
	 * @param seq_name
	 * @return 返回当前给定的序列的值
	 */
	public Long getPKValBySeqName(String seq_name) throws Exception;
}
