package com.gboss.dao;

import java.util.HashMap;
import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Model;

/**
 * @Package:com.gboss.dao
 * @ClassName:ModelDao
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-24 下午5:05:00
 */
public interface ModelDao extends BaseDao {

	public List<Model> getModelList(Long series_id);
	
	public Boolean isExist(String name);
	
	public Boolean isExist(String name,Long id);
	
	public String getModelByid(Long id);
	
	public HashMap<String, Object> getMaintainRuleMsg(Long model_id)throws SystemException;
	
	public int delete(Long id) throws SystemException;

}

