package com.chinaGPS.gtmp.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.AvgWorkTime;
import com.chinaGPS.gtmp.entity.DynamicMalfunctionPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.entity.VehicleUnitPOJO;
import com.chinaGPS.gtmp.entity.VehicleWorkPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.StatisticsQueryMapper;
import com.chinaGPS.gtmp.service.IStatisticQueryService;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:IStatisticsQueryServiceImpl
 * @Description:统计查询Service实现类
 * @author:zfy
 * @date:2013-5-10 上午10:16:38
 */
@Service
public class StatisticsQueryServiceImpl  extends BaseServiceImpl<VehicleWorkPOJO> implements IStatisticQueryService {
	@Resource
	private StatisticsQueryMapper<VehicleWorkPOJO> statisticsQueryMapper;

	@Override
	protected BaseSqlMapper<VehicleWorkPOJO> getMapper() {
		return this.statisticsQueryMapper;
	}
	
	@Override
	public HashMap<String, Object> getUnUploadByPage(
		VehicleWorkPOJO vehicleWorkPOJO, PageSelect pageSelect)
		throws Exception {
	    Map<String,Serializable> map = new HashMap<String, Serializable>();
		map.put("vehicle", vehicleWorkPOJO);
		int total = statisticsQueryMapper.countUnUploadAll(map);
		List<VehicleWorkPOJO> resultList =  statisticsQueryMapper.getUnUploadByPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public HashMap<String, Object> getUnWorkByPage(
		VehicleWorkPOJO vehicleWorkPOJO, PageSelect pageSelect)
		throws Exception {
	    Map<String,Serializable> map = new HashMap<String, Serializable>();
		map.put("vehicle", vehicleWorkPOJO);
		int total = statisticsQueryMapper.countUnWorkAll(map);
		List<VehicleWorkPOJO> resultList =  statisticsQueryMapper.getUnWorkByPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	//public List<HashMap> statisticTimeQuantum(Map<String, Serializable> map) throws Exception {
	public List<AvgWorkTime> statisticTimeQuantum(Map<String, Serializable> map) throws Exception {
	   return statisticsQueryMapper.statisticTimeQuantum(map);
	}

	@Override
	public HashMap<String, Object> getTimeQuanDetailPage(
		VehicleWorkPOJO vehicleWorkPOJO, PageSelect pageSelect)
		throws Exception {
	    Map<String,Serializable> map = new HashMap<String, Serializable>();
		map.put("dealerIds", vehicleWorkPOJO.getDealerIds());//经销商
		//map.put("startHours", vehicleWorkPOJO.getDirection());//工作时间段统计,区间开始
		//map.put("endHours", vehicleWorkPOJO.getSpeed());//工作时间段统计,区间结束
		map.put("workTime", vehicleWorkPOJO.getWorkTime());//工作时间段统
		map.put("modelName", vehicleWorkPOJO.getModelName());//机械型号
		map.put("vehicleCode", vehicleWorkPOJO.getVehicleCode());//机械代号
		map.put("vehicleArg", vehicleWorkPOJO.getVehicleArg());//机械配置
		map.put("vehicleStatus", vehicleWorkPOJO.getVehicleStatus());
		//map.put("startTime", vehicleWorkPOJO.getGpsTime());//平均工作时间统计,开始月
		//map.put("endTime", vehicleWorkPOJO.getStamp());//平均工作时间统计,结束月
		/*if(StringUtils.isNotEmpty(vehicleWorkPOJO.getVehicleState())){
		    map.put("startYears", Integer.parseInt(vehicleWorkPOJO.getVehicleState()));//使用年数，区间开始
		}
		if(StringUtils.isNotEmpty(vehicleWorkPOJO.getReferencePosition())){
		    map.put("endYears", Integer.parseInt(vehicleWorkPOJO.getReferencePosition()));//使用年数，区间结束
		}
		if(StringUtils.isNotEmpty(vehicleWorkPOJO.getOwnerName())){
		    map.put("vehicleDef", vehicleWorkPOJO.getOwnerName());//机械工作时间查询 整机编号
		}*/
		int total = statisticsQueryMapper.countTimeQuanDetail(map);
		List<VehiclePOJO> resultList =  statisticsQueryMapper.getTimeQuanDetailPageVehicle(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public List<Object> statisticAvgWorkHours(HashMap map,
		HashMap propertyMap) throws Exception {
	    List<HashMap> result=statisticsQueryMapper.statisticAvgWorkHours(map);
	        List<Object> list=new ArrayList<Object>();
	        Object object=null;
	        for (HashMap hashMap:result) {
	           Set set= hashMap.keySet();
	           DynamicMalfunctionPOJO work=new DynamicMalfunctionPOJO(propertyMap);
	           Iterator it=set.iterator();
	           while (it.hasNext()) {
	             String column = String.valueOf(it.next());
	             work.setValue(replace_toUpperCase(column.toLowerCase()),String.valueOf(hashMap.get(column)));
	           }
	           object = work.getObject();  
	           list.add(object);
	        }
	        return list;
	}

	@Override
	public List<DynamicMalfunctionPOJO> statisticAvgWorkHoursToPOJO(
		HashMap map, HashMap propertyMap) throws Exception {
	    List<HashMap> result=statisticsQueryMapper.statisticAvgWorkHours(map);
	        List<DynamicMalfunctionPOJO> list=new ArrayList<DynamicMalfunctionPOJO>();
	        Object object=null;
	        for (HashMap hashMap:result) {
	           Set set= hashMap.keySet();
	           DynamicMalfunctionPOJO work=new DynamicMalfunctionPOJO(propertyMap);
	           Iterator it=set.iterator();
	           while (it.hasNext()) {
	             String column = String.valueOf(it.next());
	             work.setValue(replace_toUpperCase(column.toLowerCase()),String.valueOf(hashMap.get(column)));
	           }
	           list.add(work);
	        }
	        return list;
	}

	private String replace_toUpperCase(String str) throws Exception {
	     String result=null;
	     if(StringUtils.isNotEmpty(str)){
	         StringBuffer sb = new StringBuffer();
	         sb.append(str);
	          int count = sb.indexOf("_");
	          while(count!=0){
	              int num = sb.indexOf("_",count);
	              count = num+1;
	              if(num!=-1){
	                  char ss = sb.charAt(count);
	                  char ia = (char) (ss - 32);
	                  sb.replace(count,count+1,ia+"");
	              }
	          }
	         result=sb.toString().replaceAll("_","");
	     }
	    return result;
	}

	@Override
	public List<Object> statisticWorkedYears(Map<String, Object> conditionMap, Map<String, Object> propertyMap) throws Exception {
		List<HashMap<String, Object>> result = statisticsQueryMapper.statisticWorkedYears(conditionMap);
	    List<Object> list = new ArrayList<Object>();
	    Object obj = null;
        for (HashMap<String, Object> hashMap : result) {
           Set<String> set = hashMap.keySet();
           DynamicMalfunctionPOJO work = new DynamicMalfunctionPOJO(propertyMap);
           Iterator<String> it = set.iterator();
           while (it.hasNext()) {
             String column = it.next();
             work.setValue(replace_toUpperCase(column.toLowerCase()),String.valueOf(hashMap.get(column)));
           }
           obj = work.getObject();
           list.add(obj);
        }
        return list;
	}

	@Override
	public List<VehicleUnitPOJO> statisticDistribute(Map<String, Serializable> map) throws Exception {
	    return statisticsQueryMapper.statisticDistribute(map);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Object> statisticWorkHours(HashMap map,HashMap propertyMap) throws Exception {
	    List<HashMap> result=statisticsQueryMapper.statisticWorkHours(map);
	        List<Object> list=new ArrayList<Object>();
	        Object object=null;
	        for (HashMap hashMap:result) {
	           Set set= hashMap.keySet();
	           DynamicMalfunctionPOJO work=new DynamicMalfunctionPOJO(propertyMap);
	           Iterator it=set.iterator();
	           while (it.hasNext()) {
	             String column = String.valueOf(it.next());
	             work.setValue(replace_toUpperCase(column.toLowerCase()),String.valueOf(hashMap.get(column)));
	           }
	           object = work.getObject();  
	           list.add(object);
	        }
	        return list;
	}
       
	@Override
	public List<DynamicMalfunctionPOJO>  statisticWorkHoursToPOJO(HashMap map,HashMap propertyMap) throws Exception  {
	    List<HashMap> result=statisticsQueryMapper.statisticWorkHours(map);
	        List<DynamicMalfunctionPOJO> list=new ArrayList<DynamicMalfunctionPOJO>();
	        Object object=null;
	        for (HashMap hashMap:result) {
	           Set set= hashMap.keySet();
	           DynamicMalfunctionPOJO work=new DynamicMalfunctionPOJO(propertyMap);
	           Iterator it=set.iterator();
	           while (it.hasNext()) {
	             String column = String.valueOf(it.next());
	             work.setValue(replace_toUpperCase(column.toLowerCase()),String.valueOf(hashMap.get(column)));
	           }
	           list.add(work);
	        }
	        return list;
	}

	@Override
	public HashMap<String, Object> getWorkedYearsDetailPage(VehicleWorkPOJO vehicleWorkPOJO, PageSelect pageSelect) throws Exception {
		 Map<String,Serializable> map = new HashMap<String, Serializable>();
		 if (org.apache.commons.lang.StringUtils.isNotEmpty(vehicleWorkPOJO.getDealerId())) {
				vehicleWorkPOJO.setDealerIds(StringUtils.split(vehicleWorkPOJO.getDealerId(), ','));
				map.put("dealerIds", vehicleWorkPOJO.getDealerIds());
				map.put("vehicleStatus", 3);
				
			}
			map.put("dealerId", "");//经销商
			map.put("vehicleModel", vehicleWorkPOJO.getModelName());//机械型号
			map.put("vehicleCode", vehicleWorkPOJO.getVehicleCode());
			map.put("vehicleArg", vehicleWorkPOJO.getVehicleArg());
			if(StringUtils.isNotEmpty(vehicleWorkPOJO.getStartYears())){
			    map.put("startYears", Integer.parseInt(vehicleWorkPOJO.getStartYears()));//使用年数，区间开始
			}
			if(StringUtils.isNotEmpty(vehicleWorkPOJO.getEndYears())){
			    map.put("endYears", Integer.parseInt(vehicleWorkPOJO.getEndYears()));//使用年数，区间结束
			}
			int total = statisticsQueryMapper.countWorkedYearsDetail(map);
			List<VehiclePOJO> resultList =  statisticsQueryMapper.getWorkedYearsDetailPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
			HashMap<String, Object> result=new HashMap<String, Object>();
			result.put("total", total);
			result.put("rows", resultList);
			return result;
	}

	@Override
	public List<VehicleWorkPOJO> statisticDistribute4Export(Map<String, Serializable> map) throws Exception {
		return statisticsQueryMapper.statisticDistribute4Export(map);
	}

	@Override
	public List<VehicleWorkPOJO> statisticWorkHoursYear(Map map) throws Exception {
		return statisticsQueryMapper.statisticWorkHoursYear(map);
	}
}
