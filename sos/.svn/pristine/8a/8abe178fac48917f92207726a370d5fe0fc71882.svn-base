package com.gboss.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.CustVehicleDao;
import com.gboss.pojo.CustVehicle;
import com.gboss.pojo.FeeInfo;
import com.gboss.pojo.Unit;
import com.gboss.service.CustVehicleService;
import com.gboss.service.FeeInfoService;
import com.gboss.service.UnitService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:CustVehicleServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-27 下午4:09:54
 */
@Service("CustVehicleService")
@Transactional
public class CustVehicleServiceImpl extends BaseServiceImpl implements CustVehicleService {

	@Autowired
	@Qualifier("CustVehicleDao")
	private CustVehicleDao custVehicleDao;
	
	@Autowired
	private FeeInfoService feeInfoService;
	
	
	@Autowired
	private UnitService unitService;
	
	@Override
	public Long add(CustVehicle custVehicle) {
		save(custVehicle);
		return custVehicle.getCv_id();
	}

	@Override
	public void delete(Long id) {
		delete(CustVehicle.class, id);
	}

	@Override
	public List<CustVehicle> getByVehicleid(Long vehicle_id) {
		return custVehicleDao.getByVehicleid(vehicle_id);
	}
	
	@Override
	public Page<HashMap<String, Object>> findLargeVehicles(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=custVehicleDao.countLargeVehicles(pageSelect.getFilter());
		List<HashMap<String, Object>> list=custVehicleDao.findLargeVehicles(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		if(list != null && list.size() >0){
			for (HashMap<String, Object> map : list) {
				Unit unit = null;
				Long vehicle_id = map.get("id") == null ? null : Long.valueOf(map.get("id").toString());
				Long unit_id = map.get("unit_id") == null ? null : Long.valueOf(map.get("unit_id").toString());
				FeeInfo serviceInfo = null;//服务费
				FeeInfo simfeeInfo = null;//SIM卡费
				FeeInfo insuranceInfo = null;//保险费
				FeeInfo webgisInfo = null;//网上查车计费
				if(unit_id!=null){
					serviceInfo = feeInfoService.getFeeInfo(unit_id, 1);
					simfeeInfo = feeInfoService.getFeeInfo(unit_id, 2);
					insuranceInfo = feeInfoService.getFeeInfo(unit_id, 3);
					webgisInfo = feeInfoService.getFeeInfo(unit_id, 4);
				}else if(vehicle_id!=null){
					List<Unit> unitList = unitService.getByVehicle_id(vehicle_id);
					if(unitList.size()>0){
						unit = unitList.get(0);
						serviceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 1);
						simfeeInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 2);
						insuranceInfo = feeInfoService.getinsuranceInfo(vehicle_id);
						webgisInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 4);
					}
				}
				if(null != serviceInfo){
					if(null != serviceInfo.getFee_date()){
						map.put("service_startTime", serviceInfo.getFee_date().toString().substring(0, 10));
					}
					map.put("service_next", serviceInfo.getFee_cycle());
					map.put("service_money", serviceInfo.getMonth_fee());
					if(null != serviceInfo.getFee_sedate())
					map.put("service_endTime", serviceInfo.getFee_sedate().toString().substring(0, 10));
					getMap(serviceInfo, map);
				}
				if(null != simfeeInfo){
					if(null != simfeeInfo.getFee_date()){
						map.put("sim_startTime", simfeeInfo.getFee_date().toString().substring(0, 10));
					}
					if(null != simfeeInfo.getFee_sedate()){
						map.put("sim_endTime", simfeeInfo.getFee_sedate().toString().substring(0, 10));
					}
					map.put("sim_next", simfeeInfo.getFee_cycle());
					map.put("sim_money", simfeeInfo.getMonth_fee());
					getMap(simfeeInfo, map);
				}
				if(null != webgisInfo){
					if(null != webgisInfo.getFee_date()){
						map.put("web_startTime", webgisInfo.getFee_date().toString().substring(0, 10));
					}
					if(null != webgisInfo.getFee_sedate()){
						map.put("web_endTime", webgisInfo.getFee_sedate().toString().substring(0, 10));
					}
					map.put("web_next", webgisInfo.getFee_cycle());
					map.put("web_money", webgisInfo.getMonth_fee());
					getMap(webgisInfo, map);
				}
				if(null != insuranceInfo){
					if(null != insuranceInfo.getFee_date()){
						map.put("policy_startTime", insuranceInfo.getFee_date().toString().substring(0, 10));
					}
					if(null != insuranceInfo.getFee_sedate()){
						map.put("policy_endTime", insuranceInfo.getFee_sedate().toString().substring(0, 10));
					}
					map.put("policy_next", insuranceInfo.getFee_cycle());
					map.put("policy_money", insuranceInfo.getMonth_fee());
					getMap(insuranceInfo, map);
				}
				
			}
		}
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}
	
	
	public void getMap(FeeInfo info, HashMap<String, Object> map) {
		if (null != info) {
			Date startDate = info.getFee_sedate();
			Date endDate = new Date();
			if (startDate.getTime() < endDate.getTime()) {
				int monthday = 0;
				int arrearage_fee = 0;
				try {
					Calendar starCal = Calendar.getInstance();
					starCal.setTime(startDate);
					int sYear = starCal.get(Calendar.YEAR);
					int sMonth = starCal.get(Calendar.MONTH);
					int sDay = starCal.get(Calendar.DATE);
					Calendar endCal = Calendar.getInstance();
					endCal.setTime(endDate);
					int eYear = endCal.get(Calendar.YEAR);
					int eMonth = endCal.get(Calendar.MONTH);
					int eDay = endCal.get(Calendar.DATE);

					monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));

					if (sDay > eDay && monthday >= 1) {
						monthday = monthday - 1;
					}
					starCal.add(starCal.MONTH, monthday);
					int total = 0;
					while (startDate.getTime() < endDate.getTime()) {
						starCal.add(starCal.DATE, 1);// 月份加一}
						startDate = starCal.getTime();
						total++;
					}
					if(total > 0)
						total = total -1;
					/*System.out.println(monthday + "-------------------" + total);
					//封装欠费数据
					info.setA_days(total);
					info.setA_months(monthday);*/
					arrearage_fee = (int) (monthday * info.getMonth_fee() + info
							.getMonth_fee() * total / 30);
					
					map.put("a_days"+info.getFeetype_id(), total);
					map.put("a_months"+info.getFeetype_id(), monthday);
					map.put("arrearage_fee"+info.getFeetype_id(), arrearage_fee);
					//info.setArrearage_fee(arrearage_fee);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}


	/*@Override
	public HashMap<String, Object> getTotalFeeMsg(Long cust_id, String locktime)
			throws SystemException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<HashMap<String, Object>> list=custVehicleDao.findLargeVehicleList(cust_id, locktime);
		Float service_fee = 0F;
		Float sim_fee = 0F;
		Float policy_fee = 0F;
		Float web_fee = 0F;
		int arrearage_fee = 0;
		if(list != null && list.size() >0){
			for (HashMap<String, Object> map : list) {
				Unit unit = null;
				Long vehicle_id = map.get("vehicle_id") == null ? null : Long.valueOf(map.get("vehicle_id").toString());
				Long unit_id = map.get("unit_id") == null ? null : Long.valueOf(map.get("unit_id").toString());
				FeeInfo serviceInfo = null;//服务费
				FeeInfo simfeeInfo = null;//SIM卡费
				FeeInfo insuranceInfo = null;//保险费
				FeeInfo webgisInfo = null;//网上查车计费
				if(unit_id!=null){
					serviceInfo = feeInfoService.getFeeInfo(unit_id, 1);
					simfeeInfo = feeInfoService.getFeeInfo(unit_id, 2);
					insuranceInfo = feeInfoService.getFeeInfo(unit_id, 3);
					webgisInfo = feeInfoService.getFeeInfo(unit_id, 4);
				}else if(vehicle_id!=null){
					List<Unit> unitList = unitService.getByVehicle_id(vehicle_id);
					if(unitList.size()>0){
						unit = unitList.get(0);
						serviceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 1);
						simfeeInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 2);
						insuranceInfo = feeInfoService.getinsuranceInfo(vehicle_id);
						webgisInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 4);
					}
				}
				if(null != serviceInfo){
					service_fee +=  serviceInfo.getMonth_fee();
					arrearage_fee = getArrearageFee(arrearage_fee, serviceInfo);
				}
				
				if(null != simfeeInfo){
					sim_fee += simfeeInfo.getMonth_fee();
					arrearage_fee = getArrearageFee(arrearage_fee, simfeeInfo);
				}
				
				if(null != webgisInfo){
					web_fee += webgisInfo.getMonth_fee();
					arrearage_fee = getArrearageFee(arrearage_fee, webgisInfo);
				}
				
				if(null != insuranceInfo){
					policy_fee += insuranceInfo.getMonth_fee();
					arrearage_fee = getArrearageFee(arrearage_fee, insuranceInfo);
				}
			}
			result.put("service_fee", service_fee);
			result.put("sim_fee", sim_fee);
			result.put("web_fee", web_fee);
			result.put("policy_fee", policy_fee);
			result.put("arrearage_fee", arrearage_fee);
		}
		return result;
	}*/
	
	
	@Override
	public HashMap<String, Object> getTotalFeeMsg(Long cust_id, String locktime)
			throws SystemException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Float service_fee = 0F;
		Float sim_fee = 0F;
		Float policy_fee = 0F;
		Float web_fee = 0F;
		int arrearage_fee = 0;
		List<FeeInfo> feeInfoList = feeInfoService.getListBycustId(cust_id, locktime);
		if(feeInfoList != null && feeInfoList.size() > 0){
			for (FeeInfo info : feeInfoList) {
				if(info.getFeetype_id() == 1){
					service_fee = service_fee + info.getMonth_fee();
				}else if(info.getFeetype_id() == 2){
					sim_fee = sim_fee + info.getMonth_fee();
				}else if(info.getFeetype_id() == 3){
					policy_fee = policy_fee + info.getMonth_fee();
				}else{
					web_fee = web_fee + info.getMonth_fee();
				}
				arrearage_fee = getArrearageFee(arrearage_fee, info);
			}
		}
			result.put("service_fee", service_fee);
			result.put("sim_fee", sim_fee);
			result.put("web_fee", web_fee);
			result.put("policy_fee", policy_fee);
			result.put("arrearage_fee", arrearage_fee);
		return result;
	}
	
	
	
	
	public int getArrearageFee(int arrearage_fee, FeeInfo info) {
		if (null != info) {
			Date date = new Date();
			if (info.getFee_sedate().getTime() < date.getTime()) {
				long total_days = (date.getTime() - info.getFee_sedate()
						.getTime()) / (3600 * 24 * 1000);
				arrearage_fee = (int) (arrearage_fee+total_days*info.getMonth_fee()/30);
			}
		}
		return arrearage_fee;
	}
	
	
	

	@Override
	public List<HashMap<String, Object>> findLargeVehicleList(Long cust_id,String lockTime)
			throws SystemException {
		List<HashMap<String, Object>> list=custVehicleDao.findLargeVehicleList(cust_id, lockTime);
		return list;
	}

	@Override
	public List<CustVehicle> getByCustId(Long cust_id)throws SystemException {
		return custVehicleDao.getByCustomerid(cust_id);
	}

}

