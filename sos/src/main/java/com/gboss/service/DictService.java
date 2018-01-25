package com.gboss.service;

import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Dict;

public interface DictService extends BaseService {
	
	public List<Dict> getDicts(int type_id)throws SystemException;
	
	public List<Dict> getDicts(int type_id, String name)throws SystemException;

}
