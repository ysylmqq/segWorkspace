package com.gboss.dao;

import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Dict;

public interface DictDao extends BaseDao{
	
	public List<Dict> getDicts(int type_id)throws SystemException;
	
	public List<Dict> getDicts(int type_id, String name)throws SystemException;

}
