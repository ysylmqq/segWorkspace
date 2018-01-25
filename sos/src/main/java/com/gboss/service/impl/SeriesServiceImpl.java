package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.SeriesDao;
import com.gboss.pojo.Series;
import com.gboss.service.SeriesService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:SeriesServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-9 下午3:13:48
 */
@Service("SeriesService")
@Transactional
public class SeriesServiceImpl extends BaseServiceImpl implements SeriesService {

	@Autowired  
	@Qualifier("SeriesDao")
	private SeriesDao seriesDao;
	
	@Override
	public List<Series> getSeriesList(Long brand_id)throws SystemException {
		return seriesDao.getSeriesList(brand_id);
	}

	@Override
	public Boolean isExist(String name)throws SystemException {
		return seriesDao.isExist(name);
	}

	@Override
	public int delete(Long id) throws SystemException {
		return seriesDao.delete(id);
	}

	@Override
	public Boolean isExist(String name, Long id) throws SystemException{
		return seriesDao.isExist(name, id);
	}
}