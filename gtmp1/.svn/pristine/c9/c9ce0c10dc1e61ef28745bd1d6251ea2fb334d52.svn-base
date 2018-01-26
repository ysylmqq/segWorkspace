package com.chinaGPS.gtmp.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.CompositePOJO;
import com.chinaGPS.gtmp.entity.CompositeQueryConditionPOJO;
import com.chinaGPS.gtmp.entity.ConditionsPOJO;
import com.chinaGPS.gtmp.entity.DealerAreaPOJO;
import com.chinaGPS.gtmp.entity.DealerVehiclePOJO;
import com.chinaGPS.gtmp.entity.VehicleSalePOJO;
import com.chinaGPS.gtmp.entity.VehicleUnitPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.OwnerMapper;
import com.chinaGPS.gtmp.service.OwnerService;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:DealerAreaServiceImpl
 * @Description:
 * @author:lxj
 * @date:Dec 11, 2012 2:43:44 PM
 * 
 */
@Service
public class OwnerServiceImpl extends BaseServiceImpl<DealerAreaPOJO>
		implements OwnerService {
	@Resource
	private OwnerMapper<DealerAreaPOJO> ownerMapper;

	@Override
	protected BaseSqlMapper<DealerAreaPOJO> getMapper() {
		return this.ownerMapper;
	}

	@Override
	public List<VehicleUnitPOJO> getVehilclesInDealer(HashMap map)
			throws Exception {
		List<VehicleUnitPOJO> list = ownerMapper.getVehilclesInDealer(map);
		/*for(VehicleUnitPOJO vehicleUnitPOJO:list){
			String temp = vehicleUnitPOJO.getVehicleState();
			if(null!=temp && !"".equals(temp))
			vehicleUnitPOJO.setVehicleState(temp.replace("熄火", "ACC关").replace("点火", "ACC开"));
		}*/
		return list;
	}

	@Override
	public List<DealerVehiclePOJO> getVehilcles(HashMap map) throws Exception {
		return ownerMapper.getVehilcles(map);
	}

	@Override
	public HashMap queryComposite(
			CompositeQueryConditionPOJO compositeQueryConditionPOJO,
			PageSelect select) throws Exception {
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("condition", compositeQueryConditionPOJO);
		int total = ownerMapper.countComposite(map);
		List<CompositePOJO> resultList = ownerMapper.queryComposite(map,
				new RowBounds(select.getOffset(), select.getRows()));
		/*for(CompositePOJO compositePOJO:resultList){
			String temp = compositePOJO.getVehicleState();
			if(null!=temp && !"".equals(temp))
				compositePOJO.setVehicleState(temp.replace("熄火", "ACC关").replace("点火", "ACC开"));
		}*/
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public HashMap queryHistoryCondition(Map<String, Serializable> map,
			PageSelect select) throws Exception {
		int total = ownerMapper.countHistoryCondition(map);
		List<ConditionsPOJO> resultList = ownerMapper
				.queryHistoryCondition(map, new RowBounds(select.getOffset(),
						select.getRows()));
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public HashMap getVehicleSaleByPage(Map<String, Serializable> map,
			PageSelect select) throws Exception {
		int total = ownerMapper.countVehicleSale(map);
		List<VehicleSalePOJO> resultList = ownerMapper
				.getVehicleSaleByPage(map, new RowBounds(select.getOffset(),
						select.getRows()));
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public List<Map> getVehicle4sale(Map<String, Object> map) throws Exception {
		return ownerMapper.getVehicle4sale(map);
	}

	@Override
	public List<Map> getOwner4sale(Map<String, Object> map) throws Exception {
		return ownerMapper.getOwner4sale(map);
	}

	@Override
	public int addDealerVechicle(VehicleSalePOJO entity) throws Exception {
		return ownerMapper.addDealerVechicle(entity);
	}

	@Override
	public int editDealerVechicle(VehicleSalePOJO entity) throws Exception {
		return ownerMapper.editDealerVechicle(entity);
	}

	@Override
	public int editVechicle(VehicleSalePOJO entity) throws Exception {
		return ownerMapper.editVechicle(entity);
	}

	@Override
	public int getVehilclesCount(HashMap map) throws Exception {
		return ownerMapper.getVehilclesCount(map);
	}

}