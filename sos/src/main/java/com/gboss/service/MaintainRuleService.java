package com.gboss.service;

import com.gboss.comm.SystemException;
import com.gboss.pojo.MaintainItems;
import com.gboss.pojo.MaintainRule;

/**
 * @Package:com.gboss.service
 * @ClassName:MaintainRuleService
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-27 上午10:55:38
 */
public interface MaintainRuleService extends BaseService {

	public MaintainRule getMaintainRuleByModelId(Long model_id)throws SystemException;
	
	public MaintainItems getMaintainItemsByModelId(Long model_id)throws SystemException;
}

