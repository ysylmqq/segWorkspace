package cc.chinagps.seat.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.table.UnitTypeTable;
import cc.chinagps.seat.dao.UnitTypeTableDao;
import cc.chinagps.seat.service.UnitTypeTableService;

@Service("unitTypeService")
public class UnitTypeTableServiceImpl extends BasicService implements
		UnitTypeTableService {

	@Autowired
	private UnitTypeTableDao unitTypeDao;
	
	@Override
	public List<UnitTypeTable> unitTypesList(Map<String, String> params) {
		return unitTypeDao.unitTypesList(params);
	}

}
