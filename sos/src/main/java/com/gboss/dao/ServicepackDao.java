package com.gboss.dao;

import java.util.List;

import com.gboss.pojo.Servicepack;

/**
 * @Package:com.gboss.dao
 * @ClassName:ServicepackDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-6 下午3:48:11
 */
public interface ServicepackDao extends BaseDao{
	
	public List<Servicepack> getServicepacks(Long subco_no);

}

