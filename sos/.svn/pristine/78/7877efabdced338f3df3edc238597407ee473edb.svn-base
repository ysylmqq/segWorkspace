package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.DictDao;
import com.gboss.pojo.Dict;
import com.gboss.service.DictService;

@Service("DictService")
@Transactional
public class DictServiceImpl extends BaseServiceImpl implements DictService {

	@Autowired  
	@Qualifier("DictDao")
    private DictDao dictDao;
	
	@Override
	public List<Dict> getDicts(int type_id) throws SystemException{
		return dictDao.getDicts(type_id);
	}

	@Override
	public List<Dict> getDicts(int type_id, String name) throws SystemException{
		return dictDao.getDicts(type_id, name);
	}

}
