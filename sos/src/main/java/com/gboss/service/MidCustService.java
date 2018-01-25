package com.gboss.service;

import com.gboss.pojo.MidCust;

/**
 * @Package:com.gboss.service
 * @ClassName:MidCustService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-29 下午4:45:45
 */
public interface MidCustService extends BaseService {
	
	public MidCust getMidCustByUnitid(Long unit_id);
	
	public void deleteMidCust(Long unit_id);

}

