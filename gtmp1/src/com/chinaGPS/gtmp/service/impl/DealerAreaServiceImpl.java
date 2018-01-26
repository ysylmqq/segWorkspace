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
import com.chinaGPS.gtmp.mapper.DealerAreaMapper;
import com.chinaGPS.gtmp.service.IDealerAreaService;
import com.chinaGPS.gtmp.util.HttpURLConnectionUtil;
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
public class DealerAreaServiceImpl extends BaseServiceImpl<DealerAreaPOJO>
		implements IDealerAreaService {
	@Resource
	private DealerAreaMapper<DealerAreaPOJO> dealerAreaMapper;

	@Override
	protected BaseSqlMapper<DealerAreaPOJO> getMapper() {
		return this.dealerAreaMapper;
	}

	@Override
	public List<VehicleUnitPOJO> getVehilclesInDealer(HashMap map)
			throws Exception {
		List<VehicleUnitPOJO> list = dealerAreaMapper.getVehilclesInDealer(map);
		for(VehicleUnitPOJO vehicleUnitPOJO:list){
			if(vehicleUnitPOJO.getLat()!=null && vehicleUnitPOJO.getLon()!=null&&!vehicleUnitPOJO.getLat().equals("0")&&!vehicleUnitPOJO.getLon().equals("0")){
				String ret = HttpURLConnectionUtil.getAddress(HttpURLConnectionUtil.getBaiduLonlat(""+vehicleUnitPOJO.getLon()+","+vehicleUnitPOJO.getLat()));
				if(ret!=null && !ret.equals("")){
					vehicleUnitPOJO.setReferencePosition(ret);
				}
			}		
		}
		return list;
	}

	@Override
	public List<DealerVehiclePOJO> getVehilcles(HashMap map) throws Exception {
		return dealerAreaMapper.getVehilcles(map);
	}

	@Override
	public HashMap queryComposite(
			CompositeQueryConditionPOJO compositeQueryConditionPOJO,
			PageSelect select) throws Exception {
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("condition", compositeQueryConditionPOJO);
		int total = dealerAreaMapper.countComposite(map);
		List<CompositePOJO> resultList = dealerAreaMapper.queryComposite(map,
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
		int total = dealerAreaMapper.countHistoryCondition(map);
		List<ConditionsPOJO> resultList = dealerAreaMapper
				.queryHistoryCondition(map, new RowBounds(select.getOffset(),
						select.getRows()));
		/*for(ConditionsPOJO conditionsPOJO:resultList){
			int engineSpeed = conditionsPOJO.getEngineSpeed();//发动机转速
			Float engineOilPressure = conditionsPOJO.getEngineOilPressure();//机油压力
			Float systemVoltage = conditionsPOJO.getSystemVoltage();//系统电压
			boolean a=false;
			boolean b=false;
			boolean c=false;
			if (engineSpeed > 600)
				a = true;
			if (engineOilPressure > 0.1)
				b = true;
			if ((systemVoltage >= 26.1 && systemVoltage <= 29)
					|| (systemVoltage >= 12.5 && systemVoltage <= 15))
				c = true;
            if(a&b || b&c ||a&c){
            	conditionsPOJO.setIsWork(1);
            }else{
            	conditionsPOJO.setIsWork(0);
            }
		}*/
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public HashMap getVehicleSaleByPage(Map<String, Serializable> map,
			PageSelect select) throws Exception {
		int total = dealerAreaMapper.countVehicleSale(map);
		List<VehicleSalePOJO> resultList = dealerAreaMapper
				.getVehicleSaleByPage(map, new RowBounds(select.getOffset(),
						select.getRows()));
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public List<Map> getVehicle4sale(Map<String, Object> map) throws Exception {
		return dealerAreaMapper.getVehicle4sale(map);
	}

	@Override
	public List<Map> getOwner4sale(Map<String, Object> map) throws Exception {
		return dealerAreaMapper.getOwner4sale(map);
	}

	@Override
	public int addDealerVechicle(VehicleSalePOJO entity) throws Exception {
		return dealerAreaMapper.addDealerVechicle(entity);
	}

	@Override
	public int editDealerVechicle(VehicleSalePOJO entity) throws Exception {
		return dealerAreaMapper.editDealerVechicle(entity);
	}

	@Override
	public int editVechicle(VehicleSalePOJO entity) throws Exception {
		return dealerAreaMapper.editVechicle(entity);
	}

	@Override
	public int getVehilclesCount(HashMap map) throws Exception {
		return dealerAreaMapper.getVehilclesCount(map);
	}

	@Override
	public int isDataByDealer(VehicleSalePOJO entity) throws Exception {
		return dealerAreaMapper.isDataByDealer(entity);
	}

	@Override
	public int insertWorkinfo(ConditionsPOJO entity) throws Exception {
		return dealerAreaMapper.insertWorkinfo(entity);
	}

}