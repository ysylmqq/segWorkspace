package com.chinagps.center.dao;

import java.util.List;

import com.chinagps.center.common.SystemException;
import com.chinagps.center.pojo.Model;

public interface ModelDao extends BaseDao {
	
	public List<Model> listByModelName(String name)throws SystemException;
}
