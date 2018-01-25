package com.gboss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.WriteoffDetailsDao;
import com.gboss.pojo.WriteoffDetails;
import com.gboss.service.WriteoffDetailsService;
import com.gboss.util.UUIDCreater;

@Service("WriteoffDetailsService")
@Transactional
public class WriteoffDetailsServiceImpl extends BaseServiceImpl implements WriteoffDetailsService {
	
	@Autowired
	@Qualifier("WriteoffDetailsDao")
    private WriteoffDetailsDao writeoffDetailsDao;
	
	@Override
	public void add(WriteoffDetails writeoffDetails) {
		save(writeoffDetails);
		writeoffDetailsDao.updateBorrow(writeoffDetails);
	}

	@Override
	public void delByWriteoffId(Long writeoff_id) {
		writeoffDetailsDao.delByWriteoffId(writeoff_id);
	}

}
