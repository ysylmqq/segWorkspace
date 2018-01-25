package com.gboss.service;

import java.util.HashMap;
import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Model;
import com.gboss.pojo.Series;

/**
 * @Package:com.gboss.service
 * @ClassName:ModelService
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-24 下午4:58:33
 */
public interface ModelService extends BaseService {
	
	public List<Model> getModelList(Long series_id);
	
	public Boolean isExist(String name);
	
	public Boolean isExist(String name,Long id);
	
	public HashMap<String, Object> getMaintainRuleMsg(Long model_id)throws SystemException;
	
	public int delete(Long id) throws SystemException;
	
	public String getModelByid(Long id);
	
	public Model getModelBySync_id(Long sync_id);
	
	public Model getModelByName(String name);

}

