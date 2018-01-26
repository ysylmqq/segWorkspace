package com.chinaGPS.gtmp.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.service.BaseService;
import com.chinaGPS.gtmp.util.MyException;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
	protected abstract BaseSqlMapper<T> getMapper();

	@Override
	public boolean add(T entity) throws Exception {
		if (entity == null)
			throw new MyException(entity.getClass().getName() + "对象参数信息为Empty！");
		int i = getMapper().add(entity);
		return i > 0 ? true : false;
	}

	@Override
	public boolean remove(T entity) throws Exception {
		int i = getMapper().remove(entity);
		return i > 0 ? true : false;
	}

	@Override
	public boolean removeById(Long id) throws Exception {
		int i = getMapper().removeById(id);
		return i > 0 ? true : false;
	}

	@Override
	public T getById(Long id) throws Exception {
		return (T) getMapper().getById(id);
	}

	public List<T> getList(T entity) throws Exception {
		return getMapper().getList(entity);
	}
	
	public List<T> getListCode(T entity) throws Exception {
		return getMapper().getListCode(entity);
	}
	
	public List<T> getListArg(T entity) throws Exception {
		return getMapper().getListArg(entity);
	}

	@Override
	public List<T> getByPage(Map<String, Serializable> map, RowBounds rowBounds)
			throws Exception {
		return getMapper().getByPage(map, rowBounds);
	}

	@Override
	public int countAll(Map<String, Serializable> map) throws Exception {
		return getMapper().countAll(map);
	}

	@Override
	public boolean edit(T entity) throws Exception {
		int i = getMapper().edit(entity);
		return i > 0 ? true : false;
	}

	/**
	 * 根据序列名取主键值
	 * 
	 * @param seq_name
	 * @return 返回当前给定的序列的值
	 */
	public Long getPKValBySeqName(String seq_name) throws Exception {
		return getMapper().getPrimarykeyValBySeq(seq_name);
	}

	@Override
	public T get(T entity) throws Exception {
		entity = (T) getMapper().get(entity);
		return entity;
	}

}
