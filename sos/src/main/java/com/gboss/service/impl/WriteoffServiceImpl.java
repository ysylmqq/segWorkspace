package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.WriteoffDao;
import com.gboss.dao.WriteoffDetailsDao;
import com.gboss.pojo.Writeoff;
import com.gboss.pojo.WriteoffDetails;
import com.gboss.service.WriteoffService;
import com.gboss.util.DateUtil;
import com.gboss.util.UUIDCreater;
import com.gboss.util.Utils;

@Service("WriteoffService")
@Transactional
public class WriteoffServiceImpl extends BaseServiceImpl implements WriteoffService {
	
	@Autowired
	@Qualifier("WriteoffDao")
    private WriteoffDao writeoffDao;
	
	@Autowired
	@Qualifier("WriteoffDetailsDao")
    private WriteoffDetailsDao writeoffDetailsDao;
	
	@Override
	public void add(Writeoff writeoff) {
		writeoffDao.save(writeoff);
		Long id=writeoff.getId();
		
		List<WriteoffDetails> writeoffDetails = writeoff.getWriteoffDetails();
		if(writeoffDetails!=null){
			for(WriteoffDetails detail:writeoffDetails){
				detail.setWriteoffId(id);
				writeoffDao.save(detail);
				writeoffDetailsDao.updateBorrow(detail);
			}
		}
	}

	@Override
	public float getLastBalance(Long manager_id) {
		return writeoffDao.getLastBalance(manager_id);
	}

}
