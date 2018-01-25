package bhz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import bhz.com.util.Page;
import bhz.com.util.PageSelect;
import bhz.sys.dao.VehicleDao;
import bhz.sys.entity.Vehicle;
import bhz.sys.facade.VehicleService;


@Service("vehicleService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass=bhz.sys.facade.VehicleService.class, 
	protocol = {"dubbo"},timeout=60000)
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

