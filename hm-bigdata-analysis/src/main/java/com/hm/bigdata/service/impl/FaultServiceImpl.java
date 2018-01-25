package com.hm.bigdata.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hm.bigdata.dao.FaultDao;
import com.hm.bigdata.entity.po.Fault;
import com.hm.bigdata.service.FaultService;
import com.hm.bigdata.util.PageSelect;
import com.hm.bigdata.util.page.Page;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:VehicleServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:22:09
 */

@Service("faultService")
@Transactional(value="mysql1TxManager")
public class FaultServiceImpl extends BaseServiceImpl implements FaultService {

	@Autowired
	private FaultDao faultDao;
	
	@Override
	public Page<HashMap<String, Object>> search(PageSelect<Fault> pageSelect, Long subco_no) {
		return faultDao.search(pageSelect, subco_no);
	}

	@Override
	public List<Map<String, Object>> findAllAlarms(Map<String, Object> map) {
		return faultDao.findAllFaults(map);
	}

	@Override
	public void saveFaultInfo(Fault fault) {
		faultDao.save(fault);
	}

	@Override
	public List<Map<String, Object>> getFalutCodeByName(String faultName) {
		return faultDao.getFalutCodeByName(faultName);
	}

}

