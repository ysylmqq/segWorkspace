package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.ModelDao;
import com.gboss.pojo.Model;
import com.gboss.service.ModelService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:ModelServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-24 下午4:59:57
 */
@Service("modelService")
public class ModelServiceImpl extends BaseServiceImpl implements ModelService {

	@Autowired
	@Qualifier("modelDao")
	private ModelDao modelDao;

	 
	public List<Model> getModelList(Long series_id) {
		return modelDao.getModelList(series_id);
	}

	 
	public Boolean isExist(String name) {
		return modelDao.isExist(name);
	}

	 
	public HashMap<String, Object> getMaintainRuleMsg(Long model_id)
			throws SystemException {
		return modelDao.getMaintainRuleMsg(model_id);
	}

	 
	public int delete(Long id) throws SystemException {
		return modelDao.delete(id);
	}

	 
	public Boolean isExist(String name, Long id) {
		return modelDao.isExist(name,id);
	}

	 
	public String getModelByid(Long id) {
		return modelDao.getModelByid(id);
	}

	 
	public Model getModelBySync_id(Long sync_id) {
		return modelDao.getModelBySync_id(sync_id);
	}

	 
	public Model getModelByName(String name) {
		return null;
	}

}
