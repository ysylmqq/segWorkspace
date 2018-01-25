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
@Transactional
public class ModelServiceImpl extends BaseServiceImpl implements ModelService {

	@Autowired
	@Qualifier("modelDao")
	private ModelDao modelDao;

	@Override
	public List<Model> getModelList(Long series_id)throws SystemException{
		return modelDao.getModelList(series_id);
	}

	@Override
	public Boolean isExist(String name)throws SystemException{
		return modelDao.isExist(name);
	}

	@Override
	public HashMap<String, Object> getMaintainRuleMsg(Long model_id)
			throws SystemException {
		return modelDao.getMaintainRuleMsg(model_id);
	}

	@Override
	public int delete(Long id) throws SystemException {
		return modelDao.delete(id);
	}

	@Override
	public Boolean isExist(String name, Long id)throws SystemException{
		return modelDao.isExist(name,id);
	}

	@Override
	public String getModelByid(Long id)throws SystemException{
		return modelDao.getModelByid(id);
	}

}
