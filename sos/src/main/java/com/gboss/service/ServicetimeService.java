package com.gboss.service;

import com.gboss.pojo.Servicetime;

/**
 * @Package:com.gboss.service
 * @ClassName:ServicetimeService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-13 上午10:53:40
 */
public interface ServicetimeService extends BaseService {
	
	public Servicetime getByUnitid(Long unit_id);

}

