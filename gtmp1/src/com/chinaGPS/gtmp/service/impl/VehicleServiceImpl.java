package com.chinaGPS.gtmp.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.swing.JFileChooser;

import oracle.sql.ARRAY;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.misc.GC.LatencyRequest;

import com.chinaGPS.gtmp.entity.TLastConditionsPOJO;
import com.chinaGPS.gtmp.entity.TLastPositionPOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.UnitMapper;
import com.chinaGPS.gtmp.mapper.VehicleMapper;
import com.chinaGPS.gtmp.service.IVehicleService;
import com.chinaGPS.gtmp.util.HttpURLConnectionUtil;
import com.chinaGPS.gtmp.util.oracleArray.ListToARRAY;
import com.chinaGPS.gtmp.util.page.PageSelect;
@Service
public class VehicleServiceImpl extends BaseServiceImpl<VehiclePOJO> implements IVehicleService {
    @Resource
    private VehicleMapper<VehiclePOJO> vehicleMapper;
    @Resource
    private UnitMapper<UnitPOJO> unitMapper;
    @Override
    protected BaseSqlMapper<VehiclePOJO> getMapper() {
		return this.vehicleMapper;
    }

    @Override
	public VehiclePOJO getVehicleByVehicleName(VehiclePOJO vehiclePOJO) throws Exception {
    	HashMap<String, Serializable> map=new HashMap<String, Serializable>();
    	map.put("vehicle", vehiclePOJO);
		return vehicleMapper.getVehicleByVehicleName(map);
	}
    
	@Override
	public HashMap<String, Object> getVehicles(VehiclePOJO vehiclePOJO, PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("vehicle", vehiclePOJO);
		int total = vehicleMapper.countAll(map);
		List<VehiclePOJO> resultList =  vehicleMapper.getByPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
	
	@Override
	public List<VehiclePOJO> getByVihicleList(VehiclePOJO vehiclePOJO) throws Exception {
		Map map = new HashMap();
		map.put("vehicle", vehiclePOJO);
		List<VehiclePOJO> resultList =  vehicleMapper.getVihcleList(map);
		return resultList;
	}
	
	@Override
	public VehiclePOJO selectVehicleMod(VehiclePOJO vehiclePOJO) throws Exception {
		VehiclePOJO result =  vehicleMapper.selectVehicleMod(vehiclePOJO);
		return result;
	}
	
	
	
	
	
	
	@Override
	public HashMap<String, Object> getVehiclesTest(VehiclePOJO vehiclePOJO, PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("vehicle", vehiclePOJO);
		int total = vehicleMapper.countAllTest(map);
		List<TestCommandPOJO> resultList =  vehicleMapper.getByPageTest(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
	
	@Override
	public List<TestCommandPOJO> selectExcel(VehiclePOJO vehiclePOJO) throws Exception {
		Map map = new HashMap();
		map.put("vehicle", vehiclePOJO);
		List<TestCommandPOJO> resultList =  vehicleMapper.selectExcel(map);
		return resultList;
	}
	
	
	
	@Override
	public VehiclePOJO getById(Long id) throws Exception {
		return (VehiclePOJO)vehicleMapper.getById(id);
	}
	
	@Override
	public TLastPositionPOJO selectLastPosition(VehiclePOJO vehiclePOJO) throws Exception {
		TLastPositionPOJO lastPosition = vehicleMapper.selectLastPosition(vehiclePOJO);
		if(lastPosition!=null){
			if(lastPosition.getLat()!=null && lastPosition.getLon()!=null&&!lastPosition.getLat().equals("0")&&!lastPosition.getLon().equals("0")){
				String ret = HttpURLConnectionUtil.getAddress(HttpURLConnectionUtil.getBaiduLonlat(""+lastPosition.getLon()+","+lastPosition.getLat()));
				if(ret!=null && !ret.equals("")){
					lastPosition.setReferencePosition(ret);
				}
			}
		}
		return lastPosition;
	}
	
	@Override
	public TLastConditionsPOJO selectLastCondition(VehiclePOJO vehiclePOJO)  throws Exception {
		return (TLastConditionsPOJO)vehicleMapper.selectLastCondition(vehiclePOJO);
	}
	
	@Override
	public boolean saveOrUpdateTest(VehiclePOJO vehiclePOJO) throws Exception {
		boolean flag=false;
		if(vehiclePOJO.getUnitId()!=null){
			if(vehicleMapper.addTest(vehiclePOJO)>0){
				vehicleMapper.editTest(vehiclePOJO);
				flag=true;
			}
		}
		return flag;
	}
	
	@Override
	public boolean saveExcel(VehiclePOJO vehiclePOJO) throws Exception {
		boolean flag=false;
		int num = 0;
		UnitPOJO pojo = vehicleMapper.searchByUnitId(vehiclePOJO.getUnitSn());
        //操作数据库代码
		num = vehicleMapper.add(vehiclePOJO);
		if (num == 0) {
			return flag;
		}
		if (num > 0) {
			if(pojo != null){
			// 返回机械注册的id
			Long vId2 = vehiclePOJO.getVehicleId();
			// 更新unit表 1：待安装，2：已安装，9：返修
			UnitPOJO unitPOJO = new UnitPOJO();
			unitPOJO.setUnitId(pojo.getUnitId());
			unitPOJO.setState(2);
			unitMapper.edit(unitPOJO);
			VehiclePOJO vPOJO = new VehiclePOJO();
			vPOJO.setVehicleId(vId2);
			vPOJO.setUnitId(pojo.getUnitId());
			vehicleMapper.addVU(vPOJO);
			}
			flag = true;
		}
		return flag;
	}
	
	@Override
	public boolean updateStatus(VehiclePOJO vehiclePOJO) throws Exception {
		return vehicleMapper.editStatus(vehiclePOJO) > 0 ? true : false;
	}
	

	@Override
	public List<UnitPOJO> getUsefulUnitInfoList() throws Exception {
		return vehicleMapper.getUsefulUnitInfoList();
	}
	
	@Override
	public boolean saveOrUpdate(VehiclePOJO vehiclePOJO) throws Exception {
		boolean flag=false;
		if(vehiclePOJO.getVehicleId()!=null){
			//查找该vehicleId在联合表中有没有，如果没有就插入一条
			int vuCount = vehicleMapper.selectVehicleUnitCount(vehiclePOJO);
			if(vuCount==0){
				vehiclePOJO.setStatus(1);
				vehicleMapper.addVU(vehiclePOJO);
			}
			if(vehicleMapper.edit(vehiclePOJO)>0){
				int num = vehicleMapper.updateUnitStatus1(vehiclePOJO);
				if(num>0){
				flag=true;
				}
			}
		}else{
			//返回机械注册的id
			if(vehicleMapper.add(vehiclePOJO)>0){
				//返回机械注册的id
				Long vId2 = vehiclePOJO.getVehicleId();
				//更新unit表   1：待安装，2：已安装，9：返修
				UnitPOJO unitPOJO = new UnitPOJO();
				unitPOJO.setUnitId(vehiclePOJO.getUnitId());
				unitPOJO.setState(2);
				unitMapper.edit(unitPOJO);
				VehiclePOJO  vPOJO = new VehiclePOJO();
				vPOJO.setVehicleId(vId2);
				vPOJO.setUnitId(vehiclePOJO.getUnitId());
				vehicleMapper.addVU(vPOJO);
				flag=true;
			}
		}
		return flag;
	}
	
	@Override
	public boolean alterVehicle(VehiclePOJO vehiclePOJO) throws Exception {
		return vehicleMapper.alterVehicle(vehiclePOJO) > 0 ? true : false;
	}
	
	@Override
	public boolean updateUnitStatus(VehiclePOJO vehiclePOJO) throws Exception {
		return vehicleMapper.updateUnitStatus(vehiclePOJO) > 0 ? true : false;
	}
	
	@Override
	public boolean deleteVehicleUnit(VehiclePOJO vehiclePOJO) throws Exception {
		return vehicleMapper.deleteVehicleUnit(vehiclePOJO) > 0 ? true : false;
	}
	
	@Override
	public List<VehiclePOJO> getList(VehiclePOJO vehiclePOJO) throws Exception {
		return vehicleMapper.getList(vehiclePOJO);
	}
	
	@Override
	public int getTestById(Long vehicleId) throws Exception {
		return vehicleMapper.getTestById(vehicleId);
	}
	
	@Override
	public UnitPOJO searchByUnitId(String unitId) throws Exception {
		return vehicleMapper.searchByUnitId(unitId);
	}
	
	@Override
	public List<String> getUnitIdList() throws Exception {
		return vehicleMapper.getUnitIdList();
	}
	
	@Override
	public boolean addVehicles(List<VehiclePOJO> vehicles) throws Exception {
		boolean flag = false;
		if (vehicles!=null) {
			int resNum = 0;
			String resultMsg = null;
			ARRAY vehicleList;
			vehicleList = ListToARRAY.getArray("TYP_VEHICLE", "TYP_VEHICLE_LIST", (ArrayList)vehicles);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("vehicleInfo",vehicleList);
			map.put("resNum",resNum);
			map.put("resMsg",resultMsg);
			if (vehicleMapper.addBatchByProc(map) > 0)
				flag = true;
		}
		return flag;
	}
	
	@Override
	public boolean delVehicleById(Long vehicleId) throws Exception {
		return vehicleMapper.removeById(vehicleId) > 0 ? true : false;
	}
	
	@Override
	public List<TestCommandPOJO> getCommandType() throws Exception {
		return vehicleMapper.getCommandType();
	}
	
	@Override
	public int delVehicles(List<String> vehicleIds) throws Exception {
		return vehicleMapper.deleteVehicles(vehicleIds);
	}
	
	@Override
	public int updateVehiclesIsValid(List<String> vehicleIds) throws Exception {
		return vehicleMapper.updateVehiclesIsValid(vehicleIds);
	}

	@Override
	@Transactional
	public boolean unbindVehicleUnit(VehiclePOJO vehiclePOJO) throws Exception {
		// 修改vehicle表
		vehiclePOJO.setTestFlag(0);// 没测试：0 有测试 1
		vehiclePOJO.setStatus(1);// 1：测试组，2：已测组，3：销售组，8:法务组，9：停用组
		vehiclePOJO.setRemoveFlag(1);
		int i = vehicleMapper.alterVehicle(vehiclePOJO);
		// 通过vehicleId查询出unitId
		vehiclePOJO = vehicleMapper.getById(vehiclePOJO.getVehicleId());
		int j = 0;
		int k = 0;
		if(vehiclePOJO.getUnitId()!=null){
			j = vehicleMapper.updateUnitStatus(vehiclePOJO);
			k = vehicleMapper.deleteVehicleUnit(vehiclePOJO);
		}else{
			j = 1;
			k = 1;
		}
		// 根据unitId删除t_vehicle_unit表中对应的值
		if (i > 0 && j > 0 && k > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean deleteInRecycle(List<String> vehicleIds) throws Exception {
		int i = 0;
		int j = 0;
		int k = 0;
		
		for (String vehicleId : vehicleIds) {
			VehiclePOJO vehiclePOJO = vehicleMapper.getById(Long.valueOf(vehicleId));
			// 更新unit表中的STATE字段 1：待安装，2：已安装，3：解绑定，9：返修
			if(vehiclePOJO.getUnitId() != null && !"".equals(vehiclePOJO.getUnitId())) { // 如果机械未解绑定，就执行解绑定操作
				i++;
				j += vehicleMapper.updateUnitStatus(vehiclePOJO);
				// 根据unitId删除t_vehicle_unit表中对应的值
				k += vehicleMapper.deleteVehicleUnit(vehiclePOJO);
			}
		}
		// 物理删除机械信息
		int l = vehicleMapper.deleteVehicles(vehicleIds);
		if (i == j && i == k && l == vehicleIds.size()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public VehiclePOJO selectVehicleByVehicleId(String vehicleId)
			throws Exception {
		return vehicleMapper.selectVehicleByVehicleId(vehicleId);
	}

	@Override
	public void updateVehicleSaleDate(VehiclePOJO VehiclePOJO) throws Exception {
		vehicleMapper.updateVehicleSaleDate(VehiclePOJO);		
	}
	
}