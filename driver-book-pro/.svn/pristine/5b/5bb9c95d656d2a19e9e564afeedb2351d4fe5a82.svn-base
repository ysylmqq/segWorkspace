package com.chinagps.driverbook.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinagps.driverbook.dao.BaseSqlMapper;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.BaseService;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
	@Autowired
	private BaseSqlMapper<T> sqlMapper;

	public int add(T entity) throws Exception {
		return sqlMapper.add(entity);
	}

	public ReturnValue edit(T entity) throws Exception {
		int editCount = sqlMapper.edit(entity);
		ReturnValue rv = new ReturnValue();
		if (editCount > 0) {
			rv.setSuccess(true);
		} else {
			rv.editError();
		}
		return rv;
	}

	public int removeById(String id) throws Exception {
		return sqlMapper.removeById(id);
	}

	public T find(T entity) throws Exception {
		return this.sqlMapper.find(entity);
	}

	public T findById(String id) throws Exception {
		return this.sqlMapper.findById(id);
	}

	public int countAll(T entity) throws Exception {
		return sqlMapper.countAll(entity);
	}

	public List<T> findByPage(T entity, RowBounds rowBounds) throws Exception {
		return sqlMapper.findByPage(entity, rowBounds);
	}

	public List<T> findList(T entity) throws Exception {
		return sqlMapper.findList(entity);
	}

}
