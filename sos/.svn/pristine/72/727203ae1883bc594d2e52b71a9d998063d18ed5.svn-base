package com.gboss.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.CarTypeDao;
import com.gboss.pojo.Model;
import com.gboss.service.CarTypeService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:CarTypeServiceImpl
 * @Description:车型业务层实现类
 * @author:bzhang
 * @date:2014-3-25 下午5:22:45
 */
@Service("carTypeService")
@Transactional
public class CarTypeServiceImpl extends BaseServiceImpl implements
		CarTypeService {
	
protected static final Logger LOGGER = LoggerFactory.getLogger(ChannelServiceImpl.class);
	
	@Autowired  
	@Qualifier("carTypeDao")
	private CarTypeDao carTypeDao;

	@Override
	public List<Model> getCarTypeList()throws SystemException{
		return carTypeDao.getCarTypeList();
	}

	@Override
	public List<Model> getCarTypeList(Long series_id)throws SystemException{
		return carTypeDao.getCarTypeList(series_id);
	}

	@Override
	public Page<Model> findCarType(PageSelect<Model> pageSelect)throws SystemException{
		return carTypeDao.findCarType(pageSelect);
	}

}

