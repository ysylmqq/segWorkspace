package com.gboss.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.UnitDao;
import com.gboss.dao.VehicleDao;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Vehicle;
import com.gboss.service.VehicleService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:VehicleServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:22:09
 */

@Service("vehicleService")
@Transactional
public class VehicleServiceImpl extends BaseServiceImpl implements
		VehicleService {

	@Autowired
	@Qualifier("VehicleDao")
	private VehicleDao vehicleDao;

	@Autowired
	@Qualifier("UnitDao")
	private UnitDao unitDao;

	@Override
	public Long add(Vehicle vehicle) {
		save(vehicle);
		return vehicle.getVehicle_id();
	}

	@Override
	public boolean is_repeat(Vehicle vehicle) {
		return vehicleDao.is_repeat(vehicle);
	}

	@Override
	public void delete(Long id) {
		delete(Vehicle.class, id);
	}

	@Override
	public Page<Vehicle> search(PageSelect<Vehicle> pageSelect, Long subco_no) {
		return vehicleDao.search(pageSelect, subco_no);
	}

	@Override
	public Page<Vehicle> searchlargecustVehicle(PageSelect<Vehicle> pageSelect,
			Long subco_no) {
		return vehicleDao.search2(pageSelect, subco_no);
	}

	@Override
	public Page<Vehicle> searchprivateVehicle(PageSelect<Vehicle> pageSelect,
			Long subco_no) {
		return vehicleDao.search3(pageSelect, subco_no);
	}

	@Override
	public List<Vehicle> getVehiclesByCustid(Long cust_id) {
		return vehicleDao.getVehiclesByCustid(cust_id);
	}

	@Override
	public Vehicle getVehicleByid(Long id) {
		return vehicleDao.getVehicleByid(id);
	}

	@Override
	public Long getIdByPlate(String plate_no) {
		return vehicleDao.getIdByPlate(plate_no);
	}

	@Override
	public Page<HashMap<String, Object>> findVehiclePage(
			PageSelect<Map<String, Object>> pageSelect, Long companyno)
			throws SystemException {
		int total = vehicleDao.countVehicleInfo(pageSelect.getFilter(),
				companyno);
		List<HashMap<String, Object>> list = vehicleDao.getVehicleInfo(
				pageSelect.getFilter(), companyno, pageSelect.getOrder(),
				pageSelect.getIs_desc(), pageSelect.getPageNo(),
				pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list,
				pageSelect.getPageSize());
	}

	@Override
	public List<HashMap<String, Object>> getVehicleTree(Customer customer) {
		List<HashMap<String, Object>> treelist = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", customer.getCustomer_id());
		map.put("name", customer.getCustomer_name());
		List<Vehicle> list = vehicleDao.getVehiclesByCustid(customer
				.getCustomer_id());
		for (Vehicle vehicle : list) {
			HashMap<String, Object> child = new HashMap<String, Object>();
			Unit unit = unitDao.getByCustAndVehicle(customer.getCustomer_id(),
					vehicle.getVehicle_id());
			child.put("id", vehicle.getVehicle_id());
			String edate = "";
		/*	// 这里的pay_model指的是需要显示哪种服务截止时间
			if (stime != null && stime.getService_edate() != null
					&& customer.getPay_model() == 1) {
				edate = DateUtil.format(stime.getService_edate(),
						DateUtil.YMD_DASH);
				child.put("name", vehicle.getPlate_no() + "(服务截止时间:" + edate
						+ ")");
			} else if (stime != null && stime.getSim_edate() != null
					&& customer.getPay_model() == 2) {
				edate = DateUtil
						.format(stime.getSim_edate(), DateUtil.YMD_DASH);
				child.put("name", vehicle.getPlate_no() + "(SIM截止时间:" + edate
						+ ")");
			} else {
				child.put("name", vehicle.getPlate_no());
			}*/
			items.add(child);
		}
		map.put("items", items);
		treelist.add(map);
		return treelist;
	}

	@Override
	public boolean is_exist(Vehicle vehicle) {
		return vehicleDao.is_exist(vehicle);
	}

	@Override
	public List<HashMap<String, Object>> countServiceFeePage(Long companyno,
			Map map) throws SystemException {
		return vehicleDao.findServiceFeePage(companyno, map, null, false, 0, 0);
	}

	@Override
	public Page<HashMap<String, Object>> findServiceFeePage(Long companyno,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = vehicleDao.countServiceFee(companyno,
				pageSelect.getFilter());
		List<HashMap<String, Object>> list = vehicleDao.findServiceFeePage(
				companyno, pageSelect.getFilter(), pageSelect.getOrder(),
				pageSelect.getIs_desc(), pageSelect.getPageNo(),
				pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list,
				pageSelect.getPageSize());
	}

	@Override
	public Page<HashMap<String, Object>> findOnlineNoFeePage(Long companyno,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = vehicleDao.countOnlineNoFee(companyno,
				pageSelect.getFilter());
		List<HashMap<String, Object>> list = vehicleDao.findOnlineNoFeePage(
				companyno, pageSelect.getFilter(), pageSelect.getOrder(),
				pageSelect.getIs_desc(), pageSelect.getPageNo(),
				pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list,
				pageSelect.getPageSize());
	}

	@Override
	public List<HashMap<String, Object>> findOnlineNoFeeList(Long companyno,
			Map map) throws SystemException {
		return vehicleDao
				.findOnlineNoFeePage(companyno, map, null, false, 0, 0);
	}

	@Override
	public List<HashMap<String, Object>> webgisList(String parameter,
			Long subco_no) {
		return vehicleDao.webgisList(parameter, subco_no);
	}

}
