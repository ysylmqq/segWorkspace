package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gboss.dao.EquipCodeDao;
import com.gboss.pojo.Equipcode;
import com.gboss.service.EquipcodeService;

@Service("equipcodeService")
public class EquipcodeServiceImpl extends BaseServiceImpl implements
		EquipcodeService {

	@Autowired
	private EquipCodeDao equipCodeDao;
	public List<Equipcode> getAllEquidcode(HashMap<String, Object> params)
			throws Exception {
		return equipCodeDao.getAllEquipCode(params);
	}
	
	@Override
	public List<Equipcode> getEquipCodeList() throws Exception {
		// TODO Auto-generated method stub
		return equipCodeDao.getEquipCodeList();
	}

	@Override
	public List<Equipcode> getEquipCodeListByCode(String equipCode) throws Exception {
		// TODO Auto-generated method stub
		return equipCodeDao.getEquipCodeListByCode(equipCode);
	}

}
