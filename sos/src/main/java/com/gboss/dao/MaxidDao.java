package com.gboss.dao;

/**
 * @Package:com.gboss.dao
 * @ClassName:MaxidDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-6 下午2:36:56
 */
public interface MaxidDao extends BaseDao {
	
	public Long getAndAddMaxid(Long subco_no);

}

