package com.gboss.dao;

import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Model;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.dao
 * @ClassName:CarTypeDao
 * @Description:车型接口
 * @author:bzhang
 * @date:2014-3-25 下午5:18:26
 */
public interface CarTypeDao extends BaseDao {
	
	public List<Model> getCarTypeList()throws SystemException;
	
	public List<Model> getCarTypeList(Long series_id)throws SystemException;
	
	public Page<Model> findCarType(PageSelect<Model> pageSelect)throws SystemException;
	
}

