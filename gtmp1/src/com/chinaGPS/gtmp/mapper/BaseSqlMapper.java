package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chinaGPS.gtmp.entity.TestCommandPOJO;

/**
 * @Package:com.chinaGPS.gtmp.mapper
 * @ClassName:BaseSqlMapper
 * @Description:基本操作接口，如增删查改，其他接口继承该接口可以简化申明过程,非公共接口可以在具体的接口中去申明
 * @author:zfy
 * @date:2012-12-17 下午04:13:10
 *
 */
public interface BaseSqlMapper<T>  {
	
	public int add(T entity) throws Exception;
	
	public int edit(T entity) throws Exception;
	
	public int remove(T entity) throws Exception;
	
	public int removeById(Long id) throws Exception;
	
	public T get(T entity) throws Exception;
	
	public T selectVehicleMod(T entity) throws Exception;
	
	public T getById(Long id) throws Exception;
	
	public int countAll(Map<String, Serializable> map) throws Exception;
	
	public int countAllTest(Map<String, Serializable> map) throws Exception;
	
	public int selectVehicleUnitCount(T entity) throws Exception;
	
	public List<T> getList(T entity) throws Exception;
	
	public List<T> getListCode(T entity) throws Exception;
	
	public List<T> getListArg(T entity) throws Exception;
	
	public List<T> getList(Map map) throws Exception;
	
	public List<T> getByPage(Map<String, Serializable> map,RowBounds rowBounds) throws Exception;
	
	public List<T> getVihcleList(Map map) throws Exception;
	
	public List<TestCommandPOJO> getByPageTest(Map<String, Serializable> map,RowBounds rowBounds) throws Exception;
	
	public Long getPrimarykeyValBySeq(String seq_name) throws Exception;
	
}
