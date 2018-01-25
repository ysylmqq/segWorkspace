package com.chinagps.center.dao;

import java.util.List;

import com.chinagps.center.common.SystemException;
import com.chinagps.center.pojo.SynUrl;

public interface SynUrlDao extends BaseDao {

	public List<SynUrl> listAll()throws SystemException;
	
	public List<SynUrl> listByCompanyId(Long subco_no)throws SystemException;
}
