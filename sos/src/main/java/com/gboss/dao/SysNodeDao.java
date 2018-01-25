package com.gboss.dao;

import java.util.List;

import com.gboss.pojo.SysNode;

/**
 * @Package:com.gboss.dao
 * @ClassName:SysNodeDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-25 下午3:32:58
 */
public interface SysNodeDao extends BaseDao {
	
	public List<SysNode> findSysNode(SysNode sysNode);

}

