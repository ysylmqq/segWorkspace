package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.RepairDao;
import com.gboss.dao.VehicleDao;
import com.gboss.pojo.Repair;
import com.gboss.service.RepairService;
import com.gboss.util.PageSelect;
import com.gboss.util.Utils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:RepairServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2015-2-11 上午11:35:45
 */
@Service("RepairService")
@Transactional
public class RepairServiceImpl extends BaseServiceImpl implements RepairService {
	
	@Autowired
	@Qualifier("RepairDao")
	private RepairDao repairDao;
	
	@Autowired
	@Qualifier("VehicleDao")
	private VehicleDao vehicleDao;

	@Override
	public Page<Map<String, Object>> findRepairPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException{
		int total=repairDao.countRepairs(pageSelect.getFilter());
		List<Map<String,Object>> repairs=repairDao.findRepairs(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), repairs, pageSelect.getPageSize());
	}

	@Override
	public Repair getRepairByVehicleId(Map<String, Object> param)throws SystemException {
		return repairDao.getRepairByVehicleId(param);
	}

	@Override
	public Map<String, Object> saveVehicleReserve(Map<String, Object> param) throws SystemException {
		//查询数据库是否存在记录，存在则修改，不存在则添加
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Repair reserve = repairDao.getRepairByVehicleId(param);
			if(reserve == null){
				repairDao.addReserve(param);
				reserve = repairDao.getRepairByVehicleId(param);
				param.put("jobNo", reserve.getJobNo());
				repairDao.addRepairLog(param);
				map.put("success", true);
				map.put("msg", "维修登记成功！");
				map.put("reserve", reserve);
			}else{
				if(reserve.getStatus() == 1){
					repairDao.updateReserve(param);
					repairDao.addRepairLog(param);
					map.put("success", true);
					map.put("msg", "维修登记修改成功！");
				}else{
					map.put("success", false);
					map.put("msg", "已分派维修，不能修改！");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> sendReserve(Map<String, Object> param)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			repairDao.assignJob(param);
			repairDao.addRepairLog(param);
			map.put("success", true);
			map.put("msg", "分派成功!");
		}catch(Exception e){
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	@Override
	public Page<Repair> search(PageSelect<Repair> pageSelect, Long subcoNo)
			throws SystemException {
		return repairDao.search(pageSelect, subcoNo);
	}

	@Override
	public Map<String, Object> saveRepairReserve(Map<String, Object> param)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			repairDao.saveRepairReserve(param);
			repairDao.addRepairLog(param);
			map.put("success", true);
			map.put("msg", "预约成功!");
		}catch(Exception e){
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> saveRepairPaiGong(Map<String, Object> param)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			repairDao.saveRepairPaiGong(param);
			repairDao.addRepairLog(param);
			map.put("success", true);
			map.put("msg", "派工成功!");
		}catch(Exception e){
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> saveRepairDoing(Map<String, Object> param)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			repairDao.saveRepairDoing(param);
			repairDao.addRepairLog(param);
			if(Utils.isNotNullOrEmpty(param.get("vehicle_id"))){
				vehicleDao.updateVehicleStataus(param, 2);
			}
			map.put("success", true);
			map.put("msg", "维修中!");
		}catch(Exception e){
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> saveRepairSuccess(Map<String, Object> param)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			repairDao.saveRepairSuccess(param);
			repairDao.addRepairLog(param);
			if(Utils.isNotNullOrEmpty(param.get("vehicle_id"))){
				vehicleDao.updateVehicleStataus(param, 0);
			}
			map.put("success", true);
			map.put("msg", "维修完成!");
		}catch(Exception e){
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> saveRepairUndo(Map<String, Object> param)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			repairDao.saveRepairUndo(param);
			repairDao.addRepairLog(param);
			if(Utils.isNotNullOrEmpty(param.get("vehicle_id"))){
				vehicleDao.updateVehicleStataus(param, 0);
			}
			map.put("success", true);
			map.put("msg", "未维修完成!");
		}catch(Exception e){
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> findRepairDt(Map<String, Object> map)
			throws SystemException {
		List<Map<String, Object>> dataList=repairDao.findRepairs(map, null, false, 0, 0);
		buildRepairDtList(dataList);
		return dataList;
	}

	private void buildRepairDtList(List<Map<String, Object>> list){
		if(Utils.isNotNullOrEmpty(list)){
			for(Map<String, Object> map : list){
				String status = (Utils.isNotNullOrEmpty(map.get("status"))? map.get("status").toString():"");
				if(StringUtils.equals("1", status)){
					map.put("statusName", "已登记");
				}else if(StringUtils.equals("2", status)){
					map.put("statusName", "已受理");
				}else if(StringUtils.equals("3", status)){
					map.put("statusName", "已预约");
				}else if(StringUtils.equals("4", status)){
					map.put("statusName", "已派工");
				}else if(StringUtils.equals("5", status)){
					map.put("statusName", "维修中");
				}else if(StringUtils.equals("6", status)){
					map.put("statusName", "维修完成");
				}else if(StringUtils.equals("7", status)){
					map.put("statusName", "未维修完成");
				}
			}
		}
	}

	@Override
	public Page<Map<String, Object>> findRepairOrderPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=repairDao.countRepairOrder(pageSelect.getFilter());
		List<Map<String,Object>> repairs=repairDao.findRepairOrder(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), repairs, pageSelect.getPageSize());
	}
}

