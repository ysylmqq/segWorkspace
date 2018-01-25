package com.gboss.dao;

import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Brand;

/**
 * @Package:com.gboss.dao
 * @ClassName:BrandDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-9 下午2:44:52
 */
public interface BrandDao extends BaseDao {
	
	public List<Brand> getBrandList()throws SystemException;
	
	public List<Brand> serachBrandList(String keyword)throws SystemException;
	
	public Boolean isExist(String name)throws SystemException;
	
	public int delete(Long id) throws SystemException;
	
	public Boolean isExist(String name,Long id)throws SystemException;

}

