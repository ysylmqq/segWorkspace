package com.gboss.service;

import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Model;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:CarTypeService
 * @Description:车型业务层接口
 * @author:bzhang
 * @date:2014-3-25 下午5:20:21
 */
public interface CarTypeService extends BaseService {
	
	public List<Model> getCarTypeList()throws SystemException;
	
	public List<Model> getCarTypeList(Long series_id)throws SystemException;
	
	public Page<Model> findCarType(PageSelect<Model> pageSelect)throws SystemException;

}

