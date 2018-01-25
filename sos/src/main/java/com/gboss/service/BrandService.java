package com.gboss.service;

import java.util.HashMap;
import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Brand;

/**
 * @Package:com.gboss.service
 * @ClassName:BrandService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-9 下午3:03:04
 */
public interface BrandService extends BaseService {
	
	public List<Brand> getBrandList() throws SystemException;
	
	public List<HashMap<String, Object>> brandTree(String keyword) throws SystemException;
	
	public Boolean isExist(String name) throws SystemException;
	
	public Boolean isExist(String name,Long id) throws SystemException;
	
	public int delete(Long id) throws SystemException;

}

