package com.chinagps.driverbook.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.AlarmControlMapper;
import com.chinagps.driverbook.dao.UnitMapper;
import com.chinagps.driverbook.pojo.AlarmControl;
import com.chinagps.driverbook.pojo.AlarmControlExample;
import com.chinagps.driverbook.pojo.AlarmControlExample.Criteria;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.pojo.Unit;
import com.chinagps.driverbook.service.IAlarmControlService;
import com.chinagps.driverbook.service.IUnitService;

@Service
@Scope("prototype")
public class UnitServiceImpl implements IUnitService {
	
	@Autowired
	private UnitMapper unitMapper;

	@Override
	public int add(Unit entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ReturnValue edit(Unit entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int removeById(String id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Unit find(Unit entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit findById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countAll(Unit entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Unit> findByPage(Unit entity, RowBounds rowBounds)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Unit> findList(Unit entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnValue getCallLetterByBarcode(String imei, String barcode,
			ReturnValue rv) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnValue getCallLetterByVin(String imei, String vin,
			ReturnValue rv) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit findIsUsed(String callLetter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUnitCallLetterSubcoNo(String subco_no) {
		return unitMapper.getUnitCallLetterSubcoNo(subco_no);
	}

	
}
