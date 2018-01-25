package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gboss.dao.EquipCodeDao;
import com.gboss.pojo.EquipCode;
import com.gboss.service.EquipCodeService;

@Service("equipCodeService")
public class EquipCodeServiceImpl extends BaseServiceImpl implements
		EquipCodeService {
	
	@Autowired
	private EquipCodeDao equipCodeDao;

	public List<EquipCode> getAllEquipCode(HashMap<String, Object> pramas) {
		return equipCodeDao.getAllEquipCode(pramas);
	}

}
