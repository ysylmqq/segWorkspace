package com.gboss.service;

import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Series;

/**
 * @Package:com.gboss.service
 * @ClassName:SeriesService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-9 下午3:10:00
 */
public interface SeriesService extends BaseService {
	
	public List<Series> getSeriesList(Long brand_id);
	
	public Boolean isExist(String name);
	
	public int delete(Long id) throws SystemException;
	
	public Boolean isExist(String name, Long id);

}

