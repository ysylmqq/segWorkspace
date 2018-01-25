package com.hm.bigdata.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hm.bigdata.dao.VehicleDao;
import com.hm.bigdata.entity.po.Vehicle;
import com.hm.bigdata.service.VehicleService;
import com.hm.bigdata.util.PageSelect;
import com.hm.bigdata.util.page.Page;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:VehicleServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:22:09
 */

@Service("vehicleService")
@Transactional(value="mysql1TxManager")
public class VehicleServiceImpl extends BaseServiceImpl implements VehicleService {

	@Autowired
	@Qualifier("VehicleDao")
	private VehicleDao vehicleDao;
	

	@Override
	public Page<HashMap<String, Object>> search(PageSelect<Vehicle> pageSelect, Long subco_no) {
		return vehicleDao.search(pageSelect, subco_no);
	}


	@Override
	public List<Map<String, Object>> findAllVehicles(Map<String, Object> map) {
		return vehicleDao.findAllVehicles(map);
	}


	@Override
	public List<Map<String, Object>> findHmVehicles(Map<String, Object> map) {
		return vehicleDao.findHmVehicles(map);
	}
}

