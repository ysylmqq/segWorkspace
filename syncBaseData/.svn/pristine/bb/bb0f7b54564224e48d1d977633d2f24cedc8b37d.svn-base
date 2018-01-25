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
public class SeriesServiceImpl extends BaseServiceImpl implements SeriesService {

	@Autowired  
	@Qualifier("SeriesDao")
	private SeriesDao seriesDao;
	
	 
	public List<Series> getSeriesList(Long brand_id) {
		return seriesDao.getSeriesList(brand_id);
	}

	 
	public Boolean isExist(String name) {
		return seriesDao.isExist(name);
	}

	 
	public int delete(Long id) throws SystemException {
		return seriesDao.delete(id);
	}

	 
	public Boolean isExist(String name, Long id) {
		return seriesDao.isExist(name, id);
	}

	 
	public Series getSeriesBySync_id(Long sync_id) {
		return seriesDao.getSeriesBySync_id(sync_id);
	}

	 
	public Series getSeriesByName(String name) {
		return seriesDao.getSeriesByName(name);
	}
	

}

