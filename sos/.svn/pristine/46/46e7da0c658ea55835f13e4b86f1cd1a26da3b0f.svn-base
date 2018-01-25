package com.gboss.service;

import java.util.HashMap;
import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Model;

/**
 * @Package:com.gboss.service
 * @ClassName:ModelService
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-24 下午4:58:33
 */
public interface ModelService extends BaseService {
	
	public List<Model> getModelList(Long series_id)throws SystemException;
	
	public Boolean isExist(String name)throws SystemException;
	
	public Boolean isExist(String name,Long id)throws SystemException;
	
	public HashMap<String, Object> getMaintainRuleMsg(Long model_id)throws SystemException;
	
	public int delete(Long id) throws SystemException;
	
	public String getModelByid(Long id)throws SystemException;

}

