package com.gboss.dao;

import com.gboss.comm.SystemException;
import com.gboss.pojo.MaintainItems;
import com.gboss.pojo.MaintainRule;

/**
 * @Package:com.gboss.dao
 * @ClassName:MaintainRuleDao
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-27 上午10:49:40
 */
public interface MaintainRuleDao extends BaseDao {
	
	public MaintainRule getMaintainRuleByModelId(Long model_id)throws SystemException;
	
	public MaintainItems getMaintainItemsByModelId(Long model_id)throws SystemException;

}

